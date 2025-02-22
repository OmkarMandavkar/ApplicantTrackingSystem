package com.dev.ATSapp.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.dev.ATSapp.Dto.UserRequest;
import com.dev.ATSapp.Entity.Candidate;
import com.dev.ATSapp.Entity.JobDescription;
import com.dev.ATSapp.Entity.User;
import com.dev.ATSapp.Service.AuthService;
import com.dev.ATSapp.Service.CandidateService;
import com.dev.ATSapp.Service.CompanyService;
import com.dev.ATSapp.Service.JobDescriptionService;
import com.dev.ATSapp.Service.JobMasterService;
import com.dev.ATSapp.Service.SuperAdminService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/candidate")
public class CandidateController {

	private AuthService authService;
	private CandidateService candidateService;
	private CompanyService companyService;
	private JobDescriptionService jobDescriptionService;
	private JobMasterService jobMasterService;
	private SuperAdminService superAdminService;

	public CandidateController(AuthService authService, CandidateService candidateService,
			CompanyService companyService, JobDescriptionService jobDescriptionService,
			JobMasterService jobMasterService, SuperAdminService superAdminService) {
		this.authService = authService;
		this.candidateService = candidateService;
		this.companyService = companyService;
		this.jobDescriptionService = jobDescriptionService;
		this.jobMasterService = jobMasterService;
		this.superAdminService = superAdminService;
	}

	@GetMapping("/viewCandidateDetails")
	public ModelAndView showViewCandidatePage(HttpSession session) {

		String candidateEmail = (String) session.getAttribute("userEmail");
		Optional<User> candidateData = authService.findByEmail(candidateEmail);
		session.setAttribute("userId", candidateData.get().getUserId());

		ModelAndView mv = new ModelAndView("candidateViews/viewCandidateDetails");
		mv.addObject("candidateData", candidateData.get());
		return mv;
	}

	@GetMapping("/viewEditCandidateDetails")
	public ModelAndView showViewEditCandidatePage(HttpSession session) {

		String candidateEmail = (String) session.getAttribute("userEmail");
		Optional<User> candidateData = authService.findByEmail(candidateEmail);

		ModelAndView mv = new ModelAndView("candidateViews/editCandidateDetails");
		mv.addObject("candidateData", candidateData.get());
		return mv;
	}

	@PostMapping("/viewEditCandidateDetails")
	public ModelAndView ViewEditCandidatePage(HttpSession session, UserRequest userRequest) {

		String candidateEmail = (String) session.getAttribute("userEmail");
		Optional<User> candidateData = authService.findByEmail(candidateEmail);

		if (candidateData.isPresent()) {
			User user = candidateData.get();
			if (userRequest.getFirstname() != null) {
				user.setFirstname(userRequest.getFirstname());
			}
			if (userRequest.getLastname() != null) {
				user.setLastname(userRequest.getLastname());
			}
			if (userRequest.getDob() != null) {
				user.setDob(userRequest.getDob());
			}
			if (userRequest.getMobile() != null) {
				user.setMobile(userRequest.getMobile());
			}
			if (userRequest.getCountry() != null) {
				user.setCountry(userRequest.getCountry());
			}
			if (userRequest.getCity() != null) {
				user.setCity(userRequest.getCity());
			}
			if (userRequest.getPincode() != null) {
				user.setPincode(userRequest.getPincode());
			}

			authService.saveUser(user);

			ModelAndView mv = new ModelAndView("redirect:/candidate/viewCandidateDetails");
			mv.addObject("message", "Data updated successfully!");
			return mv;
		} else {
			ModelAndView mv = new ModelAndView("candidateViews/dashboardCandidate");
			mv.addObject("message", "Candidate not found!");
			return mv;
		}
	}

	@GetMapping("/viewAllActiveJobs")
	public ModelAndView showAllActiveJobs() {

		List<JobDescription> activeJobData = jobDescriptionService.getAllActiveJobs();

		ModelAndView mv = new ModelAndView("candidateViews/viewAllActiveJobs");
		mv.addObject("activeJobData", activeJobData);
		return mv;
	}

	@GetMapping("/viewJobDetailsByJobId")
	public ModelAndView getMethodName(@RequestParam Integer jobId, HttpSession session) {

		Optional<JobDescription> jobData = jobDescriptionService.getJobByJobId(jobId);
		session.setAttribute("jobId", jobData.get().getJobId());

		Integer candidateId = (Integer) session.getAttribute("candidateId");
		boolean hasApplied = candidateService.hasCandidateApplied(candidateId, jobId);

		ModelAndView mv = new ModelAndView("candidateViews/viewJobDetails");
		mv.addObject("jobData", jobData.get());
		mv.addObject("candidateId", candidateId);
		mv.addObject("applied", hasApplied);
		return mv;
	}

	@GetMapping("/applyJob")
	public ModelAndView applyJob(HttpSession session, @RequestParam Integer jobId) {
		Integer userId = (Integer) session.getAttribute("candidateId");

		System.out.println("Session id:" + userId);
		if (jobId == null || userId == null) {
			return new ModelAndView("errorPage").addObject("message", "Missing jobId or userId");
		}

		boolean applied = jobDescriptionService.applyForJob(jobId, userId);

		if (applied) {
			return new ModelAndView("redirect:/candidate/myJobs");
		} else {
			ModelAndView mv = new ModelAndView("dashboard/candidate");
			mv.addObject("error", "You have already applied for this job!");
			return mv;
		}
	}

	@GetMapping("/viewStatistics")
	public ModelAndView getCandidateStatistics(HttpSession session) {
		Integer userId = (Integer) session.getAttribute("candidateId");

		List<Candidate> candidateList = candidateService.findAllAppliedJobs(userId);
		long appliedCount = candidateService.getAppliedCount(userId);


		ModelAndView mv = new ModelAndView("candidateViews/candidateStatistics");
		mv.addObject("candidateList", candidateList);
		mv.addObject("appliedCount", appliedCount);
		return mv;
	}

	@GetMapping("/myJobs")
	public ModelAndView showMyJobsPage(HttpSession session) {

		String candidateEmail = (String) session.getAttribute("candidateEmail");
		Integer candidateId = (Integer) session.getAttribute("candidateId");

		long appliedCount = candidateService.getAppliedCount(candidateId);
		List<Candidate> jobsApplied = candidateService.findAllAppliedJobs(candidateEmail);

		// show application status
		// display interview invitations received
		// graphs
		
		ModelAndView mv = new ModelAndView("candidateViews/myJobs");
		mv.addObject("JobData", jobsApplied);
		mv.addObject("appliedCount", appliedCount);
		return mv;
	}
}