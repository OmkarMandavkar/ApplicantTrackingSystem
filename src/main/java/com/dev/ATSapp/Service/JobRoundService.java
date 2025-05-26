package com.dev.ATSapp.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.dev.ATSapp.Dto.JobRoundRequest;
import com.dev.ATSapp.Entity.JobMaster;
import com.dev.ATSapp.Entity.JobRound;
import com.dev.ATSapp.Entity.Recruiter;
import com.dev.ATSapp.Enums.CandidateStatus;
import com.dev.ATSapp.Enums.RoundStatus;
import com.dev.ATSapp.Repository.JobMasterRepository;
import com.dev.ATSapp.Repository.JobRoundRepository;
import com.dev.ATSapp.Repository.RecruiterRepository;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JobRoundService {

	private final JobMasterRepository jobMasterRepository;
	private final JobRoundRepository jobRoundRepository;
	private final RecruiterRepository recruiterRepository;
	private final JavaMailSender mailSender;

	public JobRound saveJobRoundData(JobRound jobRound) {
		return jobRoundRepository.save(jobRound);
	}

	public List<JobRound> findByRecruiterIdAndStatus(Integer recruiterId) {
		return jobRoundRepository.findByRecruiterId(recruiterId);
	}

	public void updateJobRound(JobRound jobRound) {
		jobRoundRepository.save(jobRound);
	}

	public Optional<JobRound> attendInterviewByRecruiterId(Integer applicationId, Integer recruiterId) {

		JobMaster jobMaster = jobMasterRepository.findByApplicationId(applicationId);
		jobMaster.setCandidateStatus(CandidateStatus.IN_PROGRESS);
		jobMasterRepository.save(jobMaster);

		return jobRoundRepository.findFirstByJobMasterApplicationIdAndRecruiterRecruiterIdOrderByRoundNumberDesc(
				applicationId, recruiterId);
	}

	public void processInterviewUpdate(JobRound jobRound, JobRoundRequest jobRoundRequest) {
		if (jobRoundRequest.getScore() != null) {
			jobRound.setScore(jobRoundRequest.getScore());
		}
		if (jobRoundRequest.getFeedback() != null) {
			jobRound.setFeedback(jobRoundRequest.getFeedback());
		}
		if (jobRoundRequest.getRoundStatus() != null) {
			jobRound.setRoundStatus(jobRoundRequest.getRoundStatus());
		}
		jobRound.setLastUpdatedDate(LocalDateTime.now());

		jobRoundRepository.save(jobRound);
		JobMaster jobMaster = jobRound.getJobMaster();

		if (jobRound.getRoundStatus() == RoundStatus.SELECTED) {
			createNextRound(jobRound);
		}
		if (jobRound.getRoundStatus() == RoundStatus.REJECTED) {
			jobMaster.setCandidateStatus(CandidateStatus.REJECTED);
		}
		if (jobRound.getRoundStatus() == RoundStatus.COMPLETED) {
			jobMaster.setCandidateStatus(CandidateStatus.SELECTED);
		}
		jobMasterRepository.save(jobMaster);
	}

	private void createNextRound(JobRound currentRound) {

		int maxRounds = currentRound.getJobMaster().getJobDescription().getRoundDetails().size();
		int currentRoundNumber = currentRound.getRoundNumber();

		if (currentRoundNumber >= maxRounds) {
			System.out.println("All rounds completed. No new round created.");
			return;
		}

		JobRound nextRound = new JobRound();
		JobMaster jobMaster = currentRound.getJobMaster();

		nextRound.setJobMaster(currentRound.getJobMaster());
		nextRound.setRoundNumber(currentRound.getRoundNumber() + 1);
		nextRound.setScore(0);
		nextRound.setRoundStatus(RoundStatus.PENDING);
		jobMaster.setCandidateStatus(CandidateStatus.IN_PROGRESS);

		jobMasterRepository.save(jobMaster);

		List<Recruiter> recruiters = recruiterRepository
				.findByCompanyCompanyId(currentRound.getJobMaster().getCompany().getCompanyId());

		if (!recruiters.isEmpty()) {
			Random random = new Random();
			Recruiter selectedRecruiter = recruiters.get(random.nextInt(recruiters.size()));
			nextRound.setRecruiter(selectedRecruiter);
		} else {
			throw new RuntimeException("No recruiters available for this company.");
		}

		jobRoundRepository.save(nextRound);
	}

	public List<JobRound> findAllJobLogs(Integer candidateId) {
		return jobRoundRepository.findByCandidateIdOrderRoundDate(candidateId);
	}

	public Optional<JobRound> fetchDataForEmail(Integer roundId) {
		Optional<JobRound> emailData = jobRoundRepository.findById(roundId);

		return emailData;
	}

	public Optional<JobRound> sendEmailInvitation(Integer roundId, JobRoundRequest jobRoundRequest) throws Exception {
		Optional<JobRound> emailData = jobRoundRepository.findById(roundId);

		if (emailData.isPresent()) {
			JobRound jobRound = emailData.get();
			JobMaster jobMaster = jobRound.getJobMaster();

			jobRound.setRoundDate(jobRoundRequest.getRoundDate());
			jobRound.setRoundTime(jobRoundRequest.getRoundTime());
			jobRound.setRoundStatus(RoundStatus.SCHEDULED);
			jobRound.setRoundType(jobRoundRequest.getRoundType());
			jobRoundRepository.save(jobRound);

			jobMaster.setCandidateStatus(CandidateStatus.SHORTLISTED);
			jobMasterRepository.save(jobMaster);

			sendInterviewEmail(jobMaster.getCandidate().getEmail(), jobRound);

			return Optional.of(jobRound);
		}
		return Optional.empty();
	}

	private void sendInterviewEmail(String candidateEmail, JobRound jobRound) throws Exception {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

		String companyName = jobRound.getJobMaster().getCompany().getCompanyName();
		String candidateName = jobRound.getJobMaster().getCandidate().getUser().getFirstName() + " "
				+ jobRound.getJobMaster().getCandidate().getUser().getLastName();
		String jobTitle = jobRound.getJobMaster().getJobDescription().getTitle();
		String interviewer = jobRound.getRecruiter().getRecruiterFullname();
		String interviewerDesignation = jobRound.getRecruiter().getRecruiterDesignation();
		LocalDate roundDate = jobRound.getRoundDate();
		LocalTime roundTime = jobRound.getRoundTime();
		String formattedTime = roundTime.format(DateTimeFormatter.ofPattern("hh:mm a")).toUpperCase();
		Integer roundNumber = jobRound.getRoundNumber();
		String jobType = jobRound.getRoundType();

		helper.setTo(candidateEmail);
		helper.setSubject(companyName + " | Interview Invite for Round-" + roundNumber + " | " + roundDate + " | "
				+ formattedTime + " IST");

		if (jobRound.getRoundNumber() == 1) {
			String emailContent = "<p>Dear " + candidateName + ",</p>"
					+ "<p>Thank you for your interest in the <strong>" + jobTitle + "</strong> position at <strong>"
					+ companyName + "</strong>.</p>"
					+ "<p>We are pleased to invite you to the first round of the interview process</p>"
					+ "<p>Your interview is scheduled for <b>" + roundDate + "</b> at <b>" + formattedTime
					+ " IST</b> and is expected to last 20-45 minutes.</strong></p>" + "<p>This round will focus on <b>"
					+ jobType + "</b> assessment.</p>"
					+ "<p>Please feel free to reach out if you have any questions.</p>"
					+ "<p>Click <a href='https://your-meeting-link.com' style='color: blue;'>here</a> to join the meeting</p>"
					+ "<p>Best regards,<br>" + interviewer + "<br>" + interviewerDesignation + "<br>" + companyName
					+ "</p>";

			helper.setText(emailContent, true);
		}

		if (jobRound.getRoundNumber() > 1) {
			String emailContent = "<p>Dear " + candidateName + ",</p>"
					+ "<p>Congratulations on moving forward in the hiring process for the <strong>" + jobTitle
					+ "</strong> position at <strong>" + companyName + "</strong>.</p>"
					+ "<p>We were impressed with your previous discussion and would love to continue the conversation.</p>"
					+ "<p>Your interview for the next round is scheduled for <b>" + roundDate + "</b> at <b>"
					+ formattedTime + " IST</b> and is expected to last 20-45 minutes.</strong></p>"
					+ "<p>This round will focus on <b>" + jobType + "</b> assessment.</p>"
					+ "<p>Please feel free to reach out if you have any questions.</p>"
					+ "<p>Click <a href='https://your-meeting-link.com' style='color: blue;'>here</a> to join the meeting</p>"
					+ "<p>Best regards,<br>" + interviewer + "<br>" + interviewerDesignation + ", Talent Acquistion<br>" + companyName
					+ "</p>";

			helper.setText(emailContent, true);
		}

		mailSender.send(message);
	}

	public void sendOfferLetterTemplate(Integer applicationId, JobRound jobRound) throws Exception {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

		String companyName = jobRound.getJobMaster().getCompany().getCompanyName();
		String candidateName = jobRound.getJobMaster().getCandidate().getUser().getFirstName() + " "
				+ jobRound.getJobMaster().getCandidate().getUser().getLastName();
		String jobTitle = jobRound.getJobMaster().getJobDescription().getTitle();
		String candidateEmail = jobRound.getJobMaster().getCandidate().getEmail();
		LocalDate offerDeadline = jobRound.getOfferDate();
		String recruiter = jobRound.getRecruiter().getRecruiterFullname();
		String recruiterDesignation = jobRound.getRecruiter().getRecruiterDesignation();
		String logoSrc = jobRound.getJobMaster().getCompany().getLogoUrl();
		
		helper.setTo(candidateEmail);
		helper.setSubject("Exciting Job Offer from " + companyName);

		String emailContent = "<h3>Dear " + candidateName + ",</h3>"
		        + "<p>We are pleased to offer you the position of <strong>" + jobTitle + "</strong> at <strong>" + companyName + "</strong>. Your skills and experience align perfectly with our team, and we are excited to have you on board.</p>"
		        + "<p>Please find the offer details attached. Let us know if you have any questions. We look forward to your confirmation by <strong>" + offerDeadline + "</strong>.</p>"
		        + "<p>Best regards,<br>" 
		        + recruiter + "<br>" 
		        + recruiterDesignation + ", Talent Acquisition<br>"
		        + companyName + "</p>"
		        + "<img src='" + logoSrc + "' alt='Company Logo' style='max-width: 50px; height: auto; margin-top: 10px;'>";

		helper.setText(emailContent, true);
		mailSender.send(message);	
	}

	public void sendJoiningLetterTemplate(Integer applicationId, JobRound jobRound) throws Exception {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

		String companyName = jobRound.getJobMaster().getCompany().getCompanyName();
		String companyLocation = jobRound.getJobMaster().getCompany().getAddress();
		String candidateName = jobRound.getJobMaster().getCandidate().getUser().getFirstName() + " "
				+ jobRound.getJobMaster().getCandidate().getUser().getLastName();
		String candidateAddress = jobRound.getJobMaster().getCandidate().getLocation();
		String candidateEmail = jobRound.getJobMaster().getCandidate().getEmail();
		String jobTitle = jobRound.getJobMaster().getJobDescription().getTitle();
//		LocalDate offerDeadline = jobRound.getOfferDate();
		String recruiter = jobRound.getRecruiter().getRecruiterFullname();
		String recruiterDesignation = jobRound.getRecruiter().getRecruiterDesignation();
		String workLocation = jobRound.getJobMaster().getCompany().getAddress();
		String logoSrc = jobRound.getJobMaster().getCompany().getLogoUrl();
		String hrContact = recruiter;

		String joiningDate = jobRound.getJoiningDate().format(DateTimeFormatter.ofPattern("MMMM dd, yyyy"));

	    helper.setTo(candidateEmail);
	    helper.setSubject("Confirmation of Joining – " + jobTitle);

	    String emailContent = "<html><body>"
	            + "<p><strong>" + companyName + "</strong><br>"
	            + companyLocation + "<br>"
	            + "<p><strong>Date:</strong> " + joiningDate + "</p>"
	            
	            + "<p><strong>To,</strong><br>"
	            + candidateName + "<br>"
	            + candidateAddress + "</p>"
	            + "<h3>Subject: Confirmation of Joining – " + jobTitle + "</h3>"
	            + "<p>Dear " + candidateName + ",</p>"
	            + "<p>We are delighted to officially welcome you to <strong>" + companyName + "</strong> as a <strong>" + jobTitle + "</strong> in the <strong>" + jobTitle + "</strong>. "
	            + "Your joining date has been confirmed as <strong>" + joiningDate + "</strong>, and we look forward to your contribution to our team.</p>"
	            + "<h4>Your Joining Details:</h4>"
	            + "<ul>"
//	            + "<li><strong>Employee ID:</strong> " + employeeId + "</li>"
	            + "<li><strong>Designation:</strong> " + jobTitle + "</li>"
//	            + "<li><strong>Department:</strong> " + department + "</li>"
//	            + "<li><strong>Reporting Manager:</strong> " + managerName + "</li>"
	            + "<li><strong>Work Location:</strong> " + workLocation + "</li>"
//	            + "<li><strong>Working Hours:</strong> " + 9-5 + "</li>"
	            + "</ul>"
	            + "<p>Kindly ensure that you bring the required documents, including identity proof, educational certificates, and any additional forms as specified by the HR department on your first day.</p>"
	            + "<p>We are excited about the expertise and enthusiasm you bring to our organization and are confident that your journey with <strong>" + companyName + "</strong> will be a fulfilling and successful one. Should you have any queries, please feel free to contact the HR department at <strong>" + hrContact + "</strong>.</p>"
	            + "<p>Once again, welcome to the team!</p>"
	            + "<p><strong>Best Regards,</strong><br>"
	            + recruiter + "<br>"
	            + recruiterDesignation + "<br>"
	            + companyName + "</p>"
	            + "<img src='" + logoSrc + "' alt='Company Logo' style='max-width: 50px; height: auto; margin-top: 10px;'>"
	            + "</body></html>";

	    helper.setText(emailContent, true);
	    mailSender.send(message);
	}


	public List<JobRound> findAllByRecruiterData(Integer recruiterId) {
		return jobRoundRepository.findAllByRecruiterIdOrder(recruiterId);
	}

	public List<JobRound> getScheduledInterviewList(Integer candidateId) {
		return jobRoundRepository.findAllScheduledInterviewByCandidateId(candidateId);
	}

	public List<JobRound> findAllOnboardingApplicants() {
		return jobRoundRepository.findAllOnboardingCandidates();
	}

	public Optional<JobRound> fetchLetterDataForEmail(Integer applicationId) {
		return jobRoundRepository.findByLetterApplicationId(applicationId);
	}

	public Optional<JobRound> sendOfferLetter(Integer applicationId, JobRound jobRound) throws Exception {

	    Optional<JobRound> jobData = jobRoundRepository.findByApplicationIdAndCompletedStatus(applicationId);
	    
	    if (jobData.isPresent()) {
	        JobRound existingJobRound = jobData.get();
	        existingJobRound.setOfferDate(jobRound.getOfferDate());
	        jobRoundRepository.save(existingJobRound);

	        JobMaster jobMaster = existingJobRound.getJobMaster();
	        jobMaster.setCandidateStatus(CandidateStatus.OFFERED);
	        jobMasterRepository.save(jobMaster);
	        
	        sendOfferLetterTemplate(applicationId, existingJobRound);
	        
	        return Optional.of(existingJobRound);
	    }
	    return Optional.empty();	
	}

	public Optional<JobRound> sendJoiningLetter(Integer applicationId, JobRound jobRound) throws Exception {

	    Optional<JobRound> jobData = jobRoundRepository.findByApplicationIdAndCompletedStatus(applicationId);
	    
	    if (jobData.isPresent()) {
	        JobRound existingJobRound = jobData.get();
	        existingJobRound.setJoiningDate(jobRound.getJoiningDate());
	        jobRoundRepository.save(existingJobRound);

	        JobMaster jobMaster = existingJobRound.getJobMaster();
	        jobMaster.setCandidateStatus(CandidateStatus.ONBOARDED);
	        jobMasterRepository.save(jobMaster);
	        
	        sendJoiningLetterTemplate(applicationId, existingJobRound);
	        
	        return Optional.of(existingJobRound);
	    }
	    return Optional.empty();	
	}

	public long getInterviewCountByCompanyId(Integer companyId) {
		return jobRoundRepository.getInterviewCountByCompanyId(companyId);
	}

}