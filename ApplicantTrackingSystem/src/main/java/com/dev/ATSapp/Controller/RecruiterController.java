package com.dev.ATSapp.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.dev.ATSapp.Dto.JobRoundRequest;
import com.dev.ATSapp.Dto.RecruiterRequest;
import com.dev.ATSapp.Dto.UserRequest;
import com.dev.ATSapp.Entity.JobDescription;
import com.dev.ATSapp.Entity.JobMaster;
import com.dev.ATSapp.Entity.JobRound;
import com.dev.ATSapp.Entity.Recruiter;
import com.dev.ATSapp.Entity.RoundDetails;
import com.dev.ATSapp.Entity.User;
import com.dev.ATSapp.Service.AuthService;
import com.dev.ATSapp.Service.CandidateService;
import com.dev.ATSapp.Service.CompanyService;
import com.dev.ATSapp.Service.JobDescriptionService;
import com.dev.ATSapp.Service.JobMasterService;
import com.dev.ATSapp.Service.JobRoundService;
import com.dev.ATSapp.Service.RecruiterService;
import com.dev.ATSapp.Service.RoundDetailsService;
import com.dev.ATSapp.Service.SavedJobsService;
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
	private JobRoundService jobRoundService;
	private SuperAdminService superAdminService;
	private SavedJobsService savedJobsService;
	private RecruiterService recruiterService;
	private RoundDetailsService roundDetailsService;

	public RecruiterController(AuthService authService, CandidateService candidateService,
			CompanyService companyService, JobDescriptionService jobDescriptionService,
			JobMasterService jobMasterService, JobRoundService jobRoundService, SuperAdminService superAdminService,
			SavedJobsService savedJobsService, RecruiterService recruiterService,
			RoundDetailsService roundDetailsService) {
		this.authService = authService;
		this.candidateService = candidateService;
		this.companyService = companyService;
		this.jobDescriptionService = jobDescriptionService;
		this.jobMasterService = jobMasterService;
		this.jobRoundService = jobRoundService;
		this.superAdminService = superAdminService;
		this.savedJobsService = savedJobsService;
		this.recruiterService = recruiterService;
		this.roundDetailsService = roundDetailsService;
	}

	@GetMapping("/scheduleInterview")
	public ModelAndView showJobListingPage(HttpSession session) {
//		String recruiterEmail = (String) session.getAttribute("recruiterEmail");
		Integer recruiterId = (Integer) session.getAttribute("recruiterId");

		List<JobRound> viewJobListings = jobRoundService.findByRecruiterIdAndStatus(recruiterId);

		ModelAndView mv = new ModelAndView("recruiterViews/scheduleInterview");
		mv.addObject("viewJobListings", viewJobListings);
		return mv;
	}

	@GetMapping("/viewJobDetailsByJobId")
	public ModelAndView getMethodName(@RequestParam Integer jobId) {

		Optional<JobDescription> jobData = jobDescriptionService.getJobByJobId(jobId);

		ModelAndView mv = new ModelAndView("recruiterViews/viewJobByJobId");
		mv.addObject("jobData", jobData.get());
		mv.addObject("CompanyName", jobData);
		return mv;
	}

	@GetMapping("/viewEditDetails")
	public ModelAndView viewRecruiterData(HttpSession session) {

		String recruiterEmail = (String) session.getAttribute("recruiterEmail");
//		System.out.println("RECRUITEREMAIL " + recruiterEmail);

		Optional<Recruiter> recruiterData = recruiterService.findByRecruiterEmail(recruiterEmail);
//		System.out.println("FULL NAME: " + recruiterData.get().getRecruiterFullname());

		ModelAndView mv = new ModelAndView("recruiterViews/viewRecruiterDetails");
		mv.addObject("recruiterData", recruiterData.get());
		return mv;
	}

	@GetMapping("/updateRecruiterData")
	public ModelAndView showUpdateRecruiterData(HttpSession session) {

		String recruiterEmail = (String) session.getAttribute("recruiterEmail");
		Optional<Recruiter> recruiterData = recruiterService.findByRecruiterEmail(recruiterEmail);

		ModelAndView mv = new ModelAndView("recruiterViews/editRecruiterDetails");
		mv.addObject("recruiterData", recruiterData.get());
		return mv;
	}

	@PostMapping("/updateRecruiterData")
	public ModelAndView updateRecruiterData(HttpSession session, UserRequest userRequest,
			RecruiterRequest recruiterRequest) {

		String recruiterEmail = (String) session.getAttribute("recruiterEmail");
		Optional<User> userData = authService.findByEmail(recruiterEmail);
		Optional<Recruiter> recruiterData = recruiterService.findByRecruiterEmail(recruiterEmail);

		if (recruiterData.isPresent() && userData.isPresent()) {
			User user = userData.get();
			Recruiter recruiter = recruiterData.get();

			user.setPassword(recruiterRequest.getRecruiterPassword());
			recruiter.setRecruiterPassword(recruiterRequest.getRecruiterPassword());
			recruiter.setRecruiterDesignation(recruiterRequest.getRecruiterDesignation());

			authService.saveUser(user);
			recruiterService.saveRecruiterData(recruiter);

			ModelAndView mv = new ModelAndView("redirect:/recruiter/viewEditDetails");
			mv.addObject("message", "Password updated successfully!");
			return mv;
		} else {
			ModelAndView mv = new ModelAndView("recruiterViews/dashboardRecruiter");
			mv.addObject("message", "Recruiter Not Found!");
			return mv;
		}
	}

//	public void sendRatingViaEmail(Integer pid, Integer id) {
//		try {
//			User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Enter Valid User Id"));
//
//			Presentation presentation = presentationRepository.findByPid(pid)
//					.orElseThrow(() -> new PresentationNotFoundException("Presentation Not found"));
//
//			Rating rating = ratingRepository.findByPidAndId(pid, id)
//					.orElseThrow(() -> new RatingNotFoundException("No rating found"));
//
//			String subjectData = "Dear Candidate," 
//					+ "<br>You have successfully completed the Presentation Assessment"
//					+ "<br><br><b>COURSE:</b> " + presentation.getCourse() 
//					+ "<br><b>TOPIC:</b> " + presentation.getTopic() 
//					+ "<br><b>RATING:</b> " + rating.getTotalScore();
//
//			MimeMessage mimeMessage = mailSender.createMimeMessage();
//			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
//
//			helper.setFrom("omkarmandavkar000@gmail.com");
//			helper.setTo(user.getEmail());
//			helper.setSubject("Presentation Results");
//			helper.setText(subjectData, true); // Set `true` to indicate HTML content
//
//			mailSender.send(mimeMessage);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	@GetMapping("/sendEmailInvitation")
	public ModelAndView showEmailInterviewPage(@RequestParam Integer roundId) {

		Optional<JobRound> emailData = jobRoundService.fetchDataForEmail(roundId);
		List<RoundDetails> roundDetail = roundDetailsService
				.getRoundDetailByJobId(emailData.get().getJobMaster().getJobDescription().getJobId());

		ModelAndView mv = new ModelAndView("recruiterViews/sendEmailInvitation");
		mv.addObject("emailData", emailData.get());
		mv.addObject("roundDetail", roundDetail);
		return mv;
	}

	@PostMapping("/sendEmailInvitation")
	public ModelAndView emailInterviewPage(@RequestParam Integer roundId, JobRoundRequest jobRoundRequest)
			throws Exception {

		Optional<JobRound> emailData = jobRoundService.sendEmailInvitation(roundId, jobRoundRequest);
		List<RoundDetails> roundDetail = roundDetailsService
				.getRoundDetailByJobId(emailData.get().getJobMaster().getJobDescription().getJobId());

		return new ModelAndView("redirect:/recruiter/scheduleInterview").addObject("roundDetail", roundDetail);
	}

	@GetMapping("/attendInterview")
	public ModelAndView showAttendInterviewPage(@RequestParam Integer applicationId,
			@RequestParam Integer recruiterId) {

		Optional<JobRound> jobRounds = jobRoundService.attendInterviewByRecruiterId(applicationId, recruiterId);

		ModelAndView mv = new ModelAndView("recruiterViews/attendInterview");
		mv.addObject("interviewData", jobRounds.get());
		return mv;
	}

	@PostMapping("/attendInterview")
	public ModelAndView attendInterviewPage(@RequestParam Integer applicationId, @RequestParam Integer recruiterId,
			@ModelAttribute JobRoundRequest jobRoundRequest) {

		Optional<JobRound> existingJobRound = jobRoundService.attendInterviewByRecruiterId(applicationId, recruiterId);
		Optional<JobMaster> masterData = jobMasterService.findByApplicationId(applicationId);

		if (existingJobRound.isPresent() && masterData.isPresent()) {
			jobRoundService.processInterviewUpdate(existingJobRound.get(), jobRoundRequest);
			return new ModelAndView("redirect:/recruiter/scheduleInterview");
		} else {
			return new ModelAndView("recruiterViews/attendInterview").addObject("error", "Interview data not found.");
		}
	}

	@GetMapping("/interviewLogs")
	public ModelAndView showInterviewLogs(HttpSession session) {

		Integer recruiterId = (Integer) session.getAttribute("recruiterId");
		List<JobRound> logData = jobRoundService.findAllByRecruiterData(recruiterId);

		if (logData != null) {
			ModelAndView mv = new ModelAndView("recruiterViews/interviewLogs");
			mv.addObject("logData", logData);
			return mv;
		} else {
			return new ModelAndView("recruiterViews/interviewLogs").addObject("message", "No Data Found");
		}
	}

	@GetMapping("/onboarding")
	public ModelAndView viewOnboardingPage() {

		List<JobRound> candidateData = jobRoundService.findAllOnboardingApplicants();

		ModelAndView mv = new ModelAndView("recruiterViews/onboarding");
		mv.addObject("candidateData", candidateData);
		return mv;
	}

	@GetMapping("/sendOfferLetter")
	public ModelAndView showSendOfferLetterPage(@RequestParam Integer applicationId) {

		Optional<JobRound> emailData = jobRoundService.fetchLetterDataForEmail(applicationId);

		ModelAndView mv = new ModelAndView("recruiterViews/sendOfferLetter");
		mv.addObject("emailData", emailData.get());
		return mv;
	}

	@PostMapping("/sendOfferLetter")
	public ModelAndView sendOfferLetterPage(@RequestParam Integer applicationId, JobRound jobRound) throws Exception {

	    Optional<JobRound> emailData = jobRoundService.sendOfferLetter(applicationId, jobRound);
		return new ModelAndView("redirect:/recruiter/onboarding").addObject("adminData", emailData);
	}

	@GetMapping("/sendJoiningLetter")
	public ModelAndView showSendJoiningLetterPage(@RequestParam Integer applicationId) {

		Optional<JobRound> emailData = jobRoundService.fetchLetterDataForEmail(applicationId);

		ModelAndView mv = new ModelAndView("recruiterViews/sendJoiningLetter");
		mv.addObject("emailData", emailData.get());
		return mv;
	}

	@PostMapping("/sendJoiningLetter")
	public ModelAndView sendJoiningLetterPage(@RequestParam Integer applicationId, JobRound jobRound) throws Exception {

	    Optional<JobRound> emailData = jobRoundService.sendJoiningLetter(applicationId, jobRound);
		return new ModelAndView("redirect:/recruiter/onboarding");
	}
	
	@GetMapping("/changeApplicationStatus")
	public ModelAndView showChangeApplicationStatus(@RequestParam Integer applicationId) {
		
		Optional<JobMaster> applicantData = jobMasterService.findApplicantData(applicationId);
		
//		System.out.println(applicantData);
		return new ModelAndView("recruiterViews/changeApplicantStatus").addObject("applicantData", applicantData.get());
	}
	
	@PostMapping("/changeApplicationStatus")
	public ModelAndView ChangeApplicationStatus(@RequestParam Integer applicationId, JobMaster jobMaster) {
		
		Optional<JobMaster> applicantData = jobMasterService.findApplicantStatus(applicationId, jobMaster);
		return new ModelAndView("redirect:/recruiter/onboarding");

	}
}