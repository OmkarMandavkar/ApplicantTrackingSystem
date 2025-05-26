package com.dev.ATSapp.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.dev.ATSapp.Dto.CandidateRequest;
import com.dev.ATSapp.Dto.UserRequest;
import com.dev.ATSapp.Entity.Candidate;
import com.dev.ATSapp.Entity.JobDescription;
import com.dev.ATSapp.Entity.JobMaster;
import com.dev.ATSapp.Entity.JobRound;
import com.dev.ATSapp.Entity.RoundDetails;
import com.dev.ATSapp.Entity.SavedJobs;
import com.dev.ATSapp.Entity.User;
import com.dev.ATSapp.Service.AuthService;
import com.dev.ATSapp.Service.CandidateService;
import com.dev.ATSapp.Service.CompanyService;
import com.dev.ATSapp.Service.JobDescriptionService;
import com.dev.ATSapp.Service.JobMasterService;
import com.dev.ATSapp.Service.JobRoundService;
import com.dev.ATSapp.Service.RoundDetailsService;
import com.dev.ATSapp.Service.SavedJobsService;
import com.dev.ATSapp.Service.SuperAdminService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/candidate")
public class CandidateController {

	private final AuthService authService;
	private final CandidateService candidateService;
	private final JobDescriptionService jobDescriptionService;
	private final JobMasterService jobMasterService;
	private final SavedJobsService savedJobsService;
	private final JobRoundService jobRoundService;
	private final RoundDetailsService roundDetailsService;

	@GetMapping("/viewCandidateDetails")
	public ModelAndView showViewCandidatePage(HttpSession session) {

		String candidateEmail = (String) session.getAttribute("candidateEmail");

		Optional<User> userData = authService.findByEmail(candidateEmail);
		Optional<Candidate> candidateData = candidateService.findByCandidateEmail(candidateEmail);
//		session.setAttribute("candidateId", userData.get().getUserId());

		ModelAndView mv = new ModelAndView("candidateViews/viewCandidateDetails");
		mv.addObject("userData", userData.get());
		mv.addObject("candidateData", candidateData.get());
		return mv;
	}

	@GetMapping("/viewEditCandidateDetails")
	public ModelAndView showViewEditCandidatePage(HttpSession session) {

		String candidateEmail = (String) session.getAttribute("candidateEmail");
		Optional<User> userData = authService.findByEmail(candidateEmail);
		Optional<Candidate> candidateData = candidateService.findByCandidateEmail(candidateEmail);

		ModelAndView mv = new ModelAndView("candidateViews/editCandidateDetails");
		mv.addObject("userData", userData.get());
		mv.addObject("candidateData", candidateData.get());
		return mv;
	}

	@PostMapping("/viewEditCandidateDetails")
	public ModelAndView ViewEditCandidatePage(HttpSession session, UserRequest userRequest,
			CandidateRequest candidateRequest) {

		String candidateEmail = (String) session.getAttribute("candidateEmail");
		Optional<User> userData = authService.findByEmail(candidateEmail);
		Optional<Candidate> candidateData = candidateService.findByCandidateEmail(candidateEmail);

		if (userData.isPresent() || candidateData.isPresent()) {
			User user = userData.get();
			Candidate candidate = candidateData.get();

			if (userRequest.getFirstName() != null) {
				user.setFirstName(userRequest.getFirstName());
//				candidate.setFirstname(candidateRequest.getFirstname());
			}
			if (userRequest.getLastName() != null) {
				user.setLastName(userRequest.getLastName());
//				candidate.setLastname(candidateRequest.getLastname());
			}
			if (userRequest.getDob() != null) {
				user.setDob(userRequest.getDob());
			}
			if (userRequest.getMobile() != null) {
				user.setMobile(userRequest.getMobile());
//				candidate.setMobile(candidateRequest.getMobile());
			}
			if (userRequest.getCountry() != null) {
				user.setCountry(userRequest.getCountry());
				candidate.setLocation(
						userRequest.getCity() + ", " + userRequest.getCountry() + ", " + userRequest.getPincode());
			}
			if (userRequest.getCity() != null) {
				user.setCity(userRequest.getCity());
				candidate.setLocation(
						userRequest.getCity() + ", " + userRequest.getCountry() + ", " + userRequest.getPincode());
			}
			if (userRequest.getPincode() != null) {
				user.setPincode(userRequest.getPincode());
				candidate.setLocation(
						userRequest.getCity() + ", " + userRequest.getCountry() + ", " + userRequest.getPincode());
			}
			if (candidateRequest.getProject1() != null) {
				candidate.setProject1(candidateRequest.getProject1());
			}
			if (candidateRequest.getProject2() != null) {
				candidate.setProject2(candidateRequest.getProject2());
			}
			if (candidateRequest.getProject3() != null) {
				candidate.setProject3(candidateRequest.getProject3());
			}
			if (candidateRequest.getProject4() != null) {
				candidate.setProject4(candidateRequest.getProject4());
			}
			if (candidateRequest.getQualification() != null) {
				candidate.setQualification(candidateRequest.getQualification());
			}
			if (candidateRequest.getSkills() != null) {
				candidate.setSkills(candidateRequest.getSkills());
			}
			if (candidateRequest.getCurrentDesignation() != null) {
				candidate.setCurrentDesignation(candidateRequest.getCurrentDesignation());
			}
			if (candidateRequest.getSalaryExpectations() != null) {
				candidate.setSalaryExpectations(candidateRequest.getSalaryExpectations());
			}
			if (candidateRequest.getWorkExperience() != null) {
				candidate.setWorkExperience(candidateRequest.getWorkExperience());
			}

			authService.saveUser(user);
			candidateService.saveCandidate(candidate);

			ModelAndView mv = new ModelAndView("redirect:/candidate/viewCandidateDetails");
			mv.addObject("candidateData", userData.get());
			mv.addObject("message", "Data updated successfully!");
			return mv;
		} else {
			ModelAndView mv = new ModelAndView("candidateViews/dashboardCandidate");
			mv.addObject("message", "Candidate not found!");
			return mv;
		}
	}

	@GetMapping("/viewAllActiveJobs")
	public ModelAndView showAllActiveJobs(HttpSession session) {

		String candidateEmail = (String) session.getAttribute("candidateEmail");
		Optional<User> candidateData = authService.findByEmail(candidateEmail);

		List<JobDescription> activeJobData = jobDescriptionService.getAllActiveJobs();

		ModelAndView mv = new ModelAndView("candidateViews/viewAllActiveJobs");
		mv.addObject("candidateData", candidateData.get());
		mv.addObject("activeJobData", activeJobData);
		return mv;
	}

	@GetMapping("/jobLogs")
	public ModelAndView showJobsLogs(HttpSession session) {

		Integer candidateId = (Integer) session.getAttribute("candidateId");

		List<JobRound> jobLogs = jobRoundService.findAllJobLogs(candidateId);

		ModelAndView mv = new ModelAndView("candidateViews/jobLogs");
		mv.addObject("jobLogs", jobLogs);
		return mv;
	}

	@GetMapping("/viewJobDetailsByJobId")
	public ModelAndView showJobDetailsByJobId(@RequestParam Integer jobId, HttpSession session) {

		Optional<JobDescription> jobData = jobDescriptionService.getJobByJobId(jobId);
		session.setAttribute("jobId", jobData.get().getJobId());
		
		List<RoundDetails> roundData = roundDetailsService.getRoundDetailByJobId(jobId);
				
		Integer candidateId = (Integer) session.getAttribute("candidateId");
		boolean hasApplied = jobMasterService.hasCandidateApplied(candidateId, jobId);
		boolean hasSaved = savedJobsService.hasCandidateSaved(candidateId, jobId);

		ModelAndView mv = new ModelAndView("candidateViews/viewJobDetails");
		mv.addObject("roundData", roundData);
		mv.addObject("jobData", jobData.get());
		mv.addObject("candidateId", candidateId);
		mv.addObject("applied", hasApplied);
		mv.addObject("saved", hasSaved);
		return mv;
	}

//	@GetMapping("/applyJob")
//	public ModelAndView applyJob(HttpSession session, @RequestParam Integer jobId) {
//
//		String candidateEmail = (String) session.getAttribute("candidateEmail");
//		Optional<User> candidateData = authService.findByEmail(candidateEmail);
//
//		Integer candidateId = (Integer) session.getAttribute("candidateId");
//
//		System.out.println("CONTROLLER JAMMER");
//		System.out.println("Candidate Email from session: " + candidateEmail);
//		System.out.println("Candidate ID from session: " + candidateId);
//		System.out.println("Job ID received: " + jobId);
//
//		boolean applied = jobDescriptionService.applyForJob(candidateId, jobId);
//
//		if (applied) {
//			return new ModelAndView("redirect:/candidate/myJobs");
//		} else {
//			ModelAndView mv = new ModelAndView("candidateViews/dashboardCandidate");
//			mv.addObject("candidateData", candidateData.get());
//			mv.addObject("error", "You have already applied for this job!");
//			return mv;
//		}
//	}

	@GetMapping("/applyJob")
	public ModelAndView applyJob(HttpSession session, @RequestParam Integer jobId) {

		String candidateEmail = (String) session.getAttribute("candidateEmail");
		Optional<User> candidateData = authService.findByEmail(candidateEmail);

		Integer candidateId = (Integer) session.getAttribute("candidateId");

//		System.out.println("CONTROLLER JAMMER");
//		System.out.println("Candidate Email from session: " + candidateEmail);
//		System.out.println("Candidate ID from session: " + candidateId);
//		System.out.println("Job ID received: " + jobId);

		boolean applied = jobDescriptionService.applyForJob(candidateId, jobId);

		if (applied) {
			return new ModelAndView("redirect:/candidate/appliedJobs");
		} else {
			ModelAndView mv = new ModelAndView("candidateViews/dashboardCandidate");
			mv.addObject("candidateData", candidateData.get());
			mv.addObject("error", "You have already applied for this job!");
			return mv;
		}
	}

	@GetMapping("/saveJob")
	public ModelAndView saveJobs(HttpSession session, @RequestParam Integer jobId) {
		String candidateEmail = (String) session.getAttribute("candidateEmail");

		Optional<Candidate> candidateData = candidateService.findByCandidateEmail(candidateEmail);
		Integer candidateId = (Integer) session.getAttribute("candidateId");

		boolean saved = jobDescriptionService.saveJob(candidateId, jobId);

		if (saved) {
//			System.out.println("CONTROLLER Saved");
//			System.out.println("Candidate Email from session: " + candidateEmail);
//			System.out.println("Candidate ID from session: " + candidateId);
//			System.out.println("Job ID received: " + jobId);

			return new ModelAndView("redirect:/candidate/savedJobs");
		} else {
			ModelAndView mv = new ModelAndView("candidateViews/dashboardCandidate");
			mv.addObject("candidateData", candidateData.get());
			mv.addObject("error", "You have already saved this job!");
			return mv;
		}
	}

	@GetMapping("/viewStatistics")
	public ModelAndView getCandidateStatistics(HttpSession session) {

		String candidateEmail = (String) session.getAttribute("candidateEmail");
		Optional<User> candidateData = authService.findByEmail(candidateEmail);

		Integer candidateId = (Integer) session.getAttribute("candidateId");

		List<Candidate> candidateList = candidateService.findAllAppliedJobs(candidateId);
//		long appliedCount = candidateService.getAppliedCount(candidateId);

		ModelAndView mv = new ModelAndView("candidateViews/candidateStatistics");
		mv.addObject("candidateData", candidateData.get());
		mv.addObject("candidateList", candidateList);
//		mv.addObject("appliedCount", appliedCount);
		return mv;
	}

	@GetMapping("/appliedJobs")
	public ModelAndView showappliedJobsPage(HttpSession session) {

		Integer candidateId = (Integer) session.getAttribute("candidateId");

		String candidateEmail = (String) session.getAttribute("candidateEmail");
		Optional<User> candidateData = authService.findByEmail(candidateEmail);

		List<JobMaster> jobsApplied = jobMasterService.findAllAppliedJobs(candidateId);

		// show application status
		// display interview invitations received
		// graphs

		ModelAndView mv = new ModelAndView("candidateViews/appliedJobs");
		mv.addObject("JobData", jobsApplied);
		mv.addObject("candidateData", candidateData.get());
//		mv.addObject("appliedCount", appliedCount);
		return mv;
	}

	@GetMapping("/savedJobs")
	public ModelAndView showSavedJobsPage(HttpSession session) {

		Integer candidateId = (Integer) session.getAttribute("candidateId");
		String candidateEmail = (String) session.getAttribute("candidateEmail");

		Optional<User> candidateData = authService.findByEmail(candidateEmail);

		List<SavedJobs> jobSaved = savedJobsService.findAllSavedJobs(candidateId);

		ModelAndView mv = new ModelAndView("candidateViews/savedJobs");
		mv.addObject("candidateData", candidateData.get());
		mv.addObject("SavedJobs", jobSaved);
		return mv;
	}

	@GetMapping("/addAdditionalCandidateDetails")
	public String showAddAdditionalDetailPage() {
		return "candidateViews/additionalCandidateDetails";
	}

	@PostMapping("/addAdditionalCandidateDetails")
	public ModelAndView addAdditionalDetailPage(HttpSession session, CandidateRequest candidateRequest) {

		String candidateEmail = (String) session.getAttribute("candidateEmail");
		boolean candidateData = candidateService.addAdditionalCandidateDetails(candidateEmail, candidateRequest);

		if (candidateData) {
			return new ModelAndView("redirect:/candidate/viewEditCandidateDetails");
		} else {
			ModelAndView mv = new ModelAndView("candidateViews/viewCandidateDetails");
			mv.addObject("message", "Error while fetching user's data");
			return mv;
		}
	}
}