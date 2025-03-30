package com.dev.ATSapp.Controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.dev.ATSapp.Entity.Company;
import com.dev.ATSapp.Entity.JobDescription;
import com.dev.ATSapp.Entity.JobRound;
import com.dev.ATSapp.Entity.User;
import com.dev.ATSapp.Enums.Status;
import com.dev.ATSapp.Service.AuthService;
import com.dev.ATSapp.Service.CandidateService;
import com.dev.ATSapp.Service.CompanyService;
import com.dev.ATSapp.Service.JobDescriptionService;
import com.dev.ATSapp.Service.JobMasterService;
import com.dev.ATSapp.Service.JobRoundService;
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
	private JobMasterService jobMasterService;
	private JobRoundService jobRoundService;

	public DashboardController(AuthService authService, CandidateService candidateService,
			CompanyService companyService, JobDescriptionService jobDescriptionService,
			SuperAdminService superAdminService, JobMasterService jobMasterService, JobRoundService jobRoundService) {
		this.authService = authService;
		this.candidateService = candidateService;
		this.companyService = companyService;
		this.jobDescriptionService = jobDescriptionService;
		this.superAdminService = superAdminService;
		this.jobMasterService = jobMasterService;
		this.jobRoundService = jobRoundService;
	}

	@GetMapping("/superAdmin")
	public ModelAndView SuperAdminDashboard(HttpSession session) {

		String superAdminEmail = (String) session.getAttribute("userEmail");

		Optional<User> superAdminData = authService.findByEmail(superAdminEmail);
		List<Company> companyData = companyService.getAllCompany();

		String fullname = superAdminData.get().getFirstName() + " " + superAdminData.get().getLastName();
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

		String candidateEmail = (String) session.getAttribute("candidateEmail");
		Integer candidateId = (Integer) session.getAttribute("candidateId");
		Optional<User> candidateData = authService.findByEmail(candidateEmail);

		long appliedCount = jobMasterService.getAppliedCount(candidateId);
		long rejectedCount = jobMasterService.getRejectedCount(candidateId);
		long underReviewCount = jobMasterService.getUnderReviewCount(candidateId);
		long selectedCount = jobMasterService.getSelectedCount(candidateId);

		List<JobRound> interviewList = jobRoundService.getScheduledInterviewList(candidateId);

		Map<String, Integer> applicationsPerMonth = jobMasterService.getApplicationCountLast6Months(candidateId);

		ModelAndView mv = new ModelAndView("candidateViews/dashboardCandidate");
		mv.addObject("candidateData", candidateData.get());
		mv.addObject("appliedCount", appliedCount);
		mv.addObject("rejectedCount", rejectedCount);
		mv.addObject("selectedCount", selectedCount);
		mv.addObject("underReviewCount", underReviewCount);
		mv.addObject("interviewList", interviewList);
		mv.addObject("applicationsPerMonth", applicationsPerMonth);
		return mv;

	}

	@GetMapping("/company")
	public ModelAndView showCompanyDashboard(HttpSession session) {

		Integer companyId = (Integer) session.getAttribute("companyId");
		String companyName = (String) session.getAttribute("companyName");
		String companyLogo = (String) session.getAttribute("companyLogo");

		Optional<Company> companyData = companyService.findByCompanyId(companyId);
		List<JobDescription> jobsData = jobDescriptionService.getAllJobsByCompanyId(companyId);

		Map<Integer, Long> jobCounts = jobMasterService.getJobApplicationCounts();

		long allJobCount = jobDescriptionService.getAllJobCount(companyId);
		long activeJobCount = jobDescriptionService.getCountForActiveJobs(companyId);
		long allApplicationCount = jobMasterService.getAllApplicationCount(companyId);
		long interviewCount = jobRoundService.getInterviewCountByCompanyId(companyId);
		long selectedCount = jobMasterService.getSelectedCountByCompanyId(companyId);
		long rejectedCount = jobMasterService.getRejectedCountByCompanyId(companyId);
		long hiredCount = jobMasterService.getHiredCountByCompanyId(companyId);
		long inProgressCount = jobMasterService.getInProgressCountByCompanyId(companyId);
		
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
		mv.addObject("jobCounts", jobCounts);
		mv.addObject("companyName", companyName);
		mv.addObject("companyLogo", companyLogo);
		mv.addObject("totalJobCount", allJobCount);
		mv.addObject("activeJobCount", activeJobCount);
		mv.addObject("allApplicationCount", allApplicationCount);
		mv.addObject("selectedCount", selectedCount);
		mv.addObject("rejectedCount", rejectedCount);
		mv.addObject("hiredCount", hiredCount);
		mv.addObject("interviewCount", interviewCount);
		mv.addObject("inProgressCount", inProgressCount);
		return mv;

	}

	@GetMapping("/recruiter")
	public ModelAndView showRecruiterDashboard() {

		ModelAndView mv = new ModelAndView("recruiterViews/dashboardRecruiter");
		return mv;
	}
}