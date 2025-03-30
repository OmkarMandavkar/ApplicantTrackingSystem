package com.dev.ATSapp.Controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.dev.ATSapp.Dto.CompanyRequest;
import com.dev.ATSapp.Dto.JobDescriptionRequest;
import com.dev.ATSapp.Dto.RecruiterRequest;
import com.dev.ATSapp.Dto.UserRequest;
import com.dev.ATSapp.Entity.Company;
import com.dev.ATSapp.Entity.JobDescription;
import com.dev.ATSapp.Entity.JobMaster;
import com.dev.ATSapp.Entity.Recruiter;
import com.dev.ATSapp.Entity.User;
import com.dev.ATSapp.Service.AuthService;
import com.dev.ATSapp.Service.CandidateService;
import com.dev.ATSapp.Service.CompanyService;
import com.dev.ATSapp.Service.JobDescriptionService;
import com.dev.ATSapp.Service.JobMasterService;
import com.dev.ATSapp.Service.RecruiterService;
import com.dev.ATSapp.Service.SuperAdminService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/company")
public class CompanyController {

	private AuthService authService;
	private CandidateService candidateService;
	private CompanyService companyService;
	private JobDescriptionService jobDescriptionService;
	private SuperAdminService superAdminService;
	private RecruiterService recruiterService;
	private JobMasterService jobMasterService;

	public CompanyController(AuthService authService, CandidateService candidateService, CompanyService companyService,
			JobDescriptionService jobDescriptionService, SuperAdminService superAdminService,
			RecruiterService recruiterService, JobMasterService jobMasterService) {
		this.authService = authService;
		this.candidateService = candidateService;
		this.companyService = companyService;
		this.jobDescriptionService = jobDescriptionService;
		this.superAdminService = superAdminService;
		this.recruiterService = recruiterService;
		this.jobMasterService = jobMasterService;
	}

	@GetMapping("/viewDetails")
	public ModelAndView viewCompanyAdminDetails(HttpSession session) {

		String companyAdminEmail = (String) session.getAttribute("userEmail");

		Optional<User> userData = authService.findByEmail(companyAdminEmail);
		Optional<Company> companyData = companyService.findByEmail(companyAdminEmail);

		session.setAttribute("companyName", companyData.get().getCompanyName());

		ModelAndView mv = new ModelAndView("companyViews/CompanyAdminViewDetails");
		mv.addObject("companyAdminData", userData.get());
		mv.addObject("companyAdminEmail", companyData.get());
		return mv;
	}

	@GetMapping("/updateCompanyAdminData")
	public ModelAndView showEditCompanyAdminDetails(HttpSession session) {

		String companyAdmin = (String) session.getAttribute("userEmail");
		Optional<User> companyAdminData = authService.findByEmail(companyAdmin);

		ModelAndView mv = new ModelAndView("companyViews/CompanyAdminEditDetails");
		mv.addObject("companyAdminData", companyAdminData.get());
		return mv;
	}

	@PostMapping("/updateCompanyAdminData")
	public ModelAndView EditCompanyAdminDetails(HttpSession session, UserRequest userRequest,
			CompanyRequest companyRequest) {

		String companyAdminEmail = (String) session.getAttribute("userEmail");

		Optional<User> userData = authService.findByEmail(companyAdminEmail);
		Optional<Company> companyData = companyService.findByEmail(companyAdminEmail);

		if (userData.isPresent() && companyData.isPresent()) {
			User user = userData.get();
			Company company = companyData.get();

			if (user.getEmail().equals(company.getCompanyAdminEmail())) {
				user.setFirstName(userRequest.getFirstName());
				user.setLastName(userRequest.getLastName());
				user.setMobile(userRequest.getMobile());

				company.setCompanyAdminFirstName(userRequest.getFirstName());
				company.setCompanyAdminLastName(userRequest.getLastName());
				company.setCompanyAdminMobile(userRequest.getMobile());
			}

			companyService.saveCompanyAdminData(company);
			authService.saveUser(user);

			ModelAndView mv = new ModelAndView("redirect:/company/viewDetails");
			mv.addObject("message", "Company and Admin details updated successfully!");
			return mv;
		} else {
			ModelAndView mv = new ModelAndView("companyViews/dashboardCompany");
			mv.addObject("message", "Company or Admin not found!");
			return mv;
		}
	}

	@GetMapping("/createJobDescription")
	public String showCreateJobDescription() {
		return "jobDescriptionViews/JobDescriptionCreate";
	}

	@PostMapping("/createJobDescription")
	public ModelAndView CreateJobDescription(JobDescriptionRequest jobDescriptionRequest, HttpSession session) {

		String companyAdminEmail = (String) session.getAttribute("userEmail");

		boolean jobData = jobDescriptionService.createJobDescription(jobDescriptionRequest, companyAdminEmail);

		if (jobData) {
			return new ModelAndView("redirect:/dashboard/company");
		} else {
			ModelAndView mv = new ModelAndView("jobDescriptionViews/JobDescriptionCreate");
			mv.addObject("message", "Job Description already exists");
			return mv;
		}
	}

	@GetMapping("/viewAllJobDescription")
	public ModelAndView viewAllJobsCompanyPage(HttpSession session) {

		Integer companyId = (Integer) session.getAttribute("companyId");

		List<JobDescription> jobData = jobDescriptionService.getAllJobsByCompanyId(companyId);

		ModelAndView mv = new ModelAndView("companyViews/ViewAllJobs");
		mv.addObject("jobList", jobData);
		mv.addObject("CompanyName", jobData);
		return mv;
	}

	@GetMapping("/viewJobDetailByJobId")
	public ModelAndView showViewJobDetailByJobId(@RequestParam Integer jobId) {

		Optional<JobDescription> jobData = jobDescriptionService.getJobByJobId(jobId);

		ModelAndView mv = new ModelAndView("jobDescriptionViews/viewJobByJobId");
		mv.addObject("jobData", jobData.get());
		mv.addObject("CompanyName", jobData);
		return mv;
	}

	@GetMapping("/viewJobDetail")
	public ModelAndView showViewJobDetail(@RequestParam Integer jobId) {

		Optional<JobDescription> jobData = jobDescriptionService.getJobByJobId(jobId);

		ModelAndView mv = new ModelAndView("companyViews/viewJobByJobId");
		mv.addObject("jobData", jobData.get());
		mv.addObject("CompanyName", jobData);
		return mv;
	}
	
	@GetMapping("/repostJobByJobId")
	public ModelAndView showRepostJobByJobId(@RequestParam Integer jobId) {

		Optional<JobDescription> jobData = jobDescriptionService.viewRepostJob(jobId);
		return new ModelAndView("jobDescriptionViews/repostJob").addObject("jobData", jobData.get());
	}

	@PostMapping("/repostJobByJobId")
	public ModelAndView RepostJobByJobId(@RequestParam Integer jobId, JobDescriptionRequest jobDescriptionRequest) {

		Optional<JobDescription> jobData = jobDescriptionService.repostJobByJobId(jobId, jobDescriptionRequest);
		return new ModelAndView("redirect:/dashboard/company");
	}

	@GetMapping("/updateJobByJobId")
	public ModelAndView showUpdateJobByJobId(@RequestParam Integer jobId) {

		Optional<JobDescription> jobData = jobDescriptionService.getJobByJobId(jobId);
		
		System.out.println("JOB DATA : " + jobData.get().getJobId());
		return new ModelAndView("jobDescriptionViews/JobDescriptionEditDetails").addObject("jobData", jobData.get());
	}

	@PostMapping("/updateJobByJobId")
	public ModelAndView updateJobByJobId(@RequestParam Integer jobId, JobDescriptionRequest jobDescriptionRequest) {

		Optional<JobDescription> jobData = jobDescriptionService.updateJobByJobId(jobId, jobDescriptionRequest);
		
		return new ModelAndView("redirect:/dashboard/company").addObject("jobdata", jobData);
	}

	@GetMapping("/manageRecruiter")
	public ModelAndView showRecruiterPage(HttpSession session) {
		Integer companyId = (Integer) session.getAttribute("companyId");
		List<Recruiter> recruiterList = recruiterService.fetchAllRecruiters(companyId);

		ModelAndView mv = new ModelAndView("companyViews/viewAllRecruiter");
		mv.addObject("recruiterList", recruiterList);
		return mv;
	}

	@GetMapping("/addRecruiter")
	public String showAddRecruiterPage() {
		return "companyViews/createRecruiter";
	}

	@PostMapping("/addRecruiter")
	public ModelAndView recruiterPage(RecruiterRequest recruiterRequest, HttpSession session) {

		String companyAdminEmail = (String) session.getAttribute("userEmail");
		Integer companyId = (Integer) session.getAttribute("companyId");

		boolean recruiterData = recruiterService.addRecruiter(recruiterRequest, companyAdminEmail);

		List<Recruiter> recruiterList = recruiterService.fetchAllRecruiters(companyId);

		if (recruiterData) {
			return new ModelAndView("redirect:/company/manageRecruiter");

		} else {
			ModelAndView mv = new ModelAndView("companyViews/createRecruiter");
//			mv.addObject("message", "Recruiter with email " + recruiterRequest.getRecruiterEmail() + "already exits");
			return mv;
		}

	}

	@GetMapping("/manageCandidate")
	public ModelAndView showManageCandidatePage(HttpSession session) {

//		String companyAdminEmail = (String) session.getAttribute("userEmail");
		Integer companyId = (Integer) session.getAttribute("companyId");

		List<JobMaster> jobData = jobMasterService.findByCompanyIdAndOrder(companyId);

		ModelAndView mv = new ModelAndView("companyViews/manageCandidate");
		mv.addObject("jobData", jobData);
		return mv;
	}

	@GetMapping("/viewHiringAnalytics")
	public ModelAndView getJobCandidateCount(HttpSession session) {

		Integer companyId = (Integer) session.getAttribute("companyId");

		if (companyId == null) {
			return new ModelAndView("errorPage", "message", "Unauthorized access");
		}

		List<Map<String, Object>> jobCounts = jobMasterService.getJobCandidateCount(companyId);
		ModelAndView mv = new ModelAndView("companyViews/hiringAnalyticsPage");
		mv.addObject("jobCounts", jobCounts);
		return mv;
	}

}
