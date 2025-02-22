package com.dev.ATSapp.Controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.dev.ATSapp.Entity.JobMaster;
import com.dev.ATSapp.Service.AuthService;
import com.dev.ATSapp.Service.CandidateService;
import com.dev.ATSapp.Service.CompanyService;
import com.dev.ATSapp.Service.JobDescriptionService;
import com.dev.ATSapp.Service.JobMasterService;
import com.dev.ATSapp.Service.SuperAdminService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/recruiter")
public class RecruiterController {

	private AuthService authService;
	private CandidateService candidateService;
	private CompanyService companyService;
	private JobDescriptionService jobDescriptionService;
	private JobMasterService jobMasterService;
	private SuperAdminService superAdminService;

	public RecruiterController(AuthService authService, CandidateService candidateService,
			CompanyService companyService, JobDescriptionService jobDescriptionService,
			JobMasterService jobMasterService, SuperAdminService superAdminService) {
		this.authService = authService;
		this.candidateService = candidateService;
		this.companyService = companyService;
		this.jobDescriptionService = jobDescriptionService;
		this.jobMasterService = jobMasterService;
		this.superAdminService = superAdminService;
	}

	@GetMapping("/viewJobListings")
	public String showJobListingPage() {
		return "recruiterViews/viewJobListings";
	}
	
	@PostMapping("/viewJobListings")
	public ModelAndView jobListingPage(HttpSession session) {
	    String recruiterEmail = (String) session.getAttribute("recruiterEmail");

	    List<JobMaster> jobList = jobMasterService.findByRecruiterEmail(recruiterEmail);

	    ModelAndView mv = new ModelAndView("recruiterViews/viewJobListings");
	    mv.addObject("recruiterEmail", recruiterEmail);
	    mv.addObject("jobList", jobList);
	    return mv;
	}

	
}