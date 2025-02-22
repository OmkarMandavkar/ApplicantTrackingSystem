package com.dev.ATSapp.Controller;

import java.util.List;
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
import com.dev.ATSapp.Entity.Recruiter;
import com.dev.ATSapp.Entity.User;
import com.dev.ATSapp.Service.AuthService;
import com.dev.ATSapp.Service.CandidateService;
import com.dev.ATSapp.Service.CompanyService;
import com.dev.ATSapp.Service.JobDescriptionService;
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

	public CompanyController(AuthService authService, CandidateService candidateService, CompanyService companyService,
			JobDescriptionService jobDescriptionService, SuperAdminService superAdminService,
			RecruiterService recruiterService) {
		this.authService = authService;
		this.candidateService = candidateService;
		this.companyService = companyService;
		this.jobDescriptionService = jobDescriptionService;
		this.superAdminService = superAdminService;
		this.recruiterService = recruiterService;
	}

	@GetMapping("/viewDetails")
	public ModelAndView viewCompanyAdminDetails(HttpSession session) {

		String companyAdminEmail = (String) session.getAttribute("userEmail");

		Optional<User> userData = authService.findByEmail(companyAdminEmail);
		Optional<Company> companyData = companyService.findByEmail(companyAdminEmail);

		session.setAttribute("companyName", companyData.get().getCname());

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
				user.setFirstname(userRequest.getFirstname());
				user.setLastname(userRequest.getLastname());
				user.setMobile(userRequest.getMobile());

				company.setCompanyAdminFirstName(userRequest.getFirstname());
				company.setCompanyAdminLastName(userRequest.getLastname());
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
	public ModelAndView getMethodName(@RequestParam Integer jobId) {

		Optional<JobDescription> jobData = jobDescriptionService.getJobByJobId(jobId);

		ModelAndView mv = new ModelAndView("jobDescriptionViews/viewJobByJobId");
		mv.addObject("jobData", jobData.get());
		mv.addObject("CompanyName", jobData);
		return mv;
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
			mv.addObject("message", "Recruiter with email " + recruiterRequest.getRecruiterEmail() + "already exits");
			return mv;
		}

	}

	@GetMapping("/manageCandidate")
	public String showManageCandidatePage() {
		return "companyViews/manageCandidate";
	}
	
	@GetMapping("/viewHiringAnalytics")
	public String showHiringAnalyticsPage() {
		return "companyViews/hiringAnalyticsPage";
	}
	
}
