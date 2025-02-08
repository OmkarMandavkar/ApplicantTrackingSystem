package com.dev.ATSapp.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dev.ATSapp.Service.CandidateService;
import com.dev.ATSapp.Service.CompanyService;
import com.dev.ATSapp.Service.JobProfileService;
import com.dev.ATSapp.Service.UserService;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

	private UserService userService;
	private CandidateService candidateService;
	private CompanyService companyService;
	private JobProfileService jobProfileService;

	public DashboardController(UserService userService, CandidateService candidateService,
			CompanyService companyService, JobProfileService jobProfileService) {
		this.userService = userService;
		this.candidateService = candidateService;
		this.companyService = companyService;
		this.jobProfileService = jobProfileService;
	}

	@GetMapping("/superAdmin")
	public String showSuperAdminDashboard() {
		return "dashboardSuperAdmin";
	}

	@GetMapping("/candidate")
	public String showCandidateDashboard() {
		return "dashboardCandidate";
	}

	@GetMapping("/company")
	public String showCompanyDashboard() {
		return "dashboardCompany";
	}

	@GetMapping("/recruiter")
	public String showRecruiterDashboard() {
		return "dashboardRecruiter";
	}

	@PostMapping("/superAdmin")
	public String SuperAdminDashboard() {
		return "dashboardSuperAdmin";
	}

	@PostMapping("/candidate")
	public String CandidateDashboard() {
		return "dashboardCandidate";
	}

	@PostMapping("/company")
	public String CompanyDashboard() {
		return "dashboardCompany";
	}

	@PostMapping("/recruiter")
	public String RecruiterDashboard() {
		return "dashboardRecruiter";
	}
}
