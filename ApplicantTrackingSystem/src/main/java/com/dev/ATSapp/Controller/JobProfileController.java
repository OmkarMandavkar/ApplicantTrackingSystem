package com.dev.ATSapp.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dev.ATSapp.Service.AuthService;
import com.dev.ATSapp.Service.CandidateService;
import com.dev.ATSapp.Service.CompanyService;
import com.dev.ATSapp.Service.JobDescriptionService;

@Controller
@RequestMapping("/jobProfile")
public class JobProfileController {

	private AuthService authService;
	private CandidateService candidateService;
	private CompanyService companyService;
	private JobDescriptionService jobProfileService;
	private SuperAdminController superAdminController;

	public JobProfileController(AuthService authService, CandidateService candidateService,
			CompanyService companyService, JobDescriptionService jobProfileService,
			SuperAdminController superAdminController) {
		this.authService = authService;
		this.candidateService = candidateService;
		this.companyService = companyService;
		this.jobProfileService = jobProfileService;
		this.superAdminController = superAdminController;
	}

	
	
}
