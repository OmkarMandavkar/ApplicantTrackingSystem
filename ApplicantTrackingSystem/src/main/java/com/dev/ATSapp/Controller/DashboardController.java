package com.dev.ATSapp.Controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.dev.ATSapp.Entity.Candidate;
import com.dev.ATSapp.Entity.Company;
import com.dev.ATSapp.Entity.JobDescription;
import com.dev.ATSapp.Entity.User;
import com.dev.ATSapp.Enums.Status;
import com.dev.ATSapp.Service.AuthService;
import com.dev.ATSapp.Service.CandidateService;
import com.dev.ATSapp.Service.CompanyService;
import com.dev.ATSapp.Service.JobDescriptionService;
import com.dev.ATSapp.Service.SuperAdminService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

	private AuthService authService;
	private CandidateService candidateService;
	private CompanyService companyService;
	private JobDescriptionService jobDescriptionService;
	private SuperAdminService superAdminService;

	public DashboardController(AuthService authService, CandidateService candidateService,
			CompanyService companyService, JobDescriptionService jobDescriptionService,
			SuperAdminService superAdminService) {
		this.authService = authService;
		this.candidateService = candidateService;
		this.companyService = companyService;
		this.jobDescriptionService = jobDescriptionService;
		this.superAdminService = superAdminService;
	}

	@GetMapping("/superAdmin")
	public ModelAndView SuperAdminDashboard(HttpSession session) {

		String superAdminEmail = (String) session.getAttribute("userEmail");

		Optional<User> superAdminData = authService.findByEmail(superAdminEmail);
		List<Company> companyData = companyService.getAllCompany();

		String fullname = superAdminData.get().getFirstname() + " " + superAdminData.get().getLastname();
		Long companyCount = companyService.countAllCompany();
		Long activeJobsCount = jobDescriptionService.countAllActiveJobs();
		Long candidateCount = candidateService.countAllCandidate();

		ModelAndView mv = new ModelAndView("superAdminViews/dashboardSuperAdmin");
		mv.addObject("companyList", companyData);
		mv.addObject("userFullName", fullname);
		mv.addObject("companyCount", companyCount);
		mv.addObject("activeJobsCount", activeJobsCount);
		mv.addObject("placedCount", 100);
		mv.addObject("candidateCount", candidateCount);
		return mv;
	}

	@GetMapping("/candidate")
	public ModelAndView showCandidateDashboard(HttpSession session) {

		String candidateImage = (String) session.getAttribute("candidateProfile");
		
		String candidateEmail = (String) session.getAttribute("userEmail");
		Integer abc = (Integer) session.getAttribute("candidateId");
		System.out.println("UserID in dashboard: " +abc);
		
		Optional<User> candidateData = authService.findByEmail(candidateEmail);

		
//		System.out.println(candidateData.get().getEmail());
		
		String candidateName = candidateData.get().getFirstname() + " " + candidateData.get().getLastname();

		ModelAndView mv = new ModelAndView("candidateViews/dashboardCandidate");
		mv.addObject("candidateData", candidateData.get());
		mv.addObject("candidateName", candidateName);
		return mv;
	}

	@GetMapping("/company")
	public ModelAndView showCompanyDashboard(HttpSession session) {

		Integer companyId = (Integer) session.getAttribute("companyId");
		String companyName = (String) session.getAttribute("companyName");
		String companyLogo = (String) session.getAttribute("companyLogo");
//		String companyAdminEmail = (String) session.getAttribute("userEmail");
//		System.out.println(companyLogo);
		
		Optional<Company> companyData = companyService.findByCompanyId(companyId);
		List<JobDescription> jobsData = jobDescriptionService.getAllJobsByCompanyId(companyId);

		boolean hasUpdates = false;
		for (JobDescription job : jobsData) {
			if (job.getExpiryDate() != null && LocalDate.now().isAfter(job.getExpiryDate())) {
				job.setStatus(Status.INACTIVE);
				hasUpdates = true;
			}
		}

		if (hasUpdates) {
			jobDescriptionService.saveAllJobs(jobsData);
		}

		ModelAndView mv = new ModelAndView("companyViews/dashboardCompany");
		mv.addObject("jobsDataList", jobsData);
		mv.addObject("companyName", companyName);
		mv.addObject("companyLogo", companyLogo);
		return mv;

	}

	@GetMapping("/recruiter")
	public String showRecruiterDashboard() {
		return "dashboardRecruiter";
	}
}