package com.dev.ATSapp.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.dev.ATSapp.Dto.JobDescriptionRequest;
import com.dev.ATSapp.Entity.Candidate;
import com.dev.ATSapp.Entity.Company;
import com.dev.ATSapp.Entity.JobDescription;
import com.dev.ATSapp.Entity.JobMaster;
import com.dev.ATSapp.Entity.JobRound;
import com.dev.ATSapp.Entity.Recruiter;
import com.dev.ATSapp.Entity.RoundDetails;
import com.dev.ATSapp.Entity.SavedJobs;
import com.dev.ATSapp.Enums.CandidateStatus;
import com.dev.ATSapp.Enums.InterviewRounds;
import com.dev.ATSapp.Enums.RoundStatus;
import com.dev.ATSapp.Enums.SaveStatus;
import com.dev.ATSapp.Enums.Status;
import com.dev.ATSapp.Repository.CandidateRepository;
import com.dev.ATSapp.Repository.CompanyRepository;
import com.dev.ATSapp.Repository.JobDescriptionRepository;
import com.dev.ATSapp.Repository.JobMasterRepository;
import com.dev.ATSapp.Repository.JobRoundRepository;
import com.dev.ATSapp.Repository.RecruiterRepository;
import com.dev.ATSapp.Repository.RoundDetailsRepository;
import com.dev.ATSapp.Repository.SavedJobsRepository;
import com.dev.ATSapp.Repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JobDescriptionService {

	private final CandidateRepository candidateRepository;
	private final JobDescriptionRepository jobDescriptionRepository;
	private final CompanyRepository companyRepository;
	private final JobMasterRepository jobMasterRepository;
	private final RecruiterRepository recruiterRepository;
	private final SavedJobsRepository savedJobsRepository;
	private final JobRoundRepository jobRoundRepository;
	private final RoundDetailsRepository roundDetailsRepository;

	public List<JobDescription> saveAllJobs(List<JobDescription> jobList) {
		return jobDescriptionRepository.saveAll(jobList);
	}

	public List<JobDescription> getAllJobs() {
		return jobDescriptionRepository.findAll();
	}

	public List<JobDescription> getAllActiveJobs() {
		return jobDescriptionRepository.findAllActiveJob(Status.ACTIVE);
	}

//	public List<JobDescription> getAllInActiveJobs() {
//		return jobDescriptionRepository.findAllInActiveJob(Status.INACTIVE);
//	}

	public boolean createJobDescription(JobDescriptionRequest jobDescriptionRequest, String companyAdminEmail) {

		Optional<Company> companyData = companyRepository.findByCompanyAdminEmail(companyAdminEmail);
		if (companyData.isEmpty()) {
			return false;
		}

		Company company = companyData.get();

		Optional<JobDescription> jobData = jobDescriptionRepository
				.findByCompanyNameAndPosition(company.getCompanyName(), jobDescriptionRequest.getTitle());

		if (jobData.isPresent()) {
			return false;
		}

		JobDescription job = new JobDescription();
		job.setCompany(company);
		job.setTitle(jobDescriptionRequest.getTitle().trim());
		job.setAboutJob(jobDescriptionRequest.getAboutJob().trim());
		job.setEmployementType(jobDescriptionRequest.getEmployementType().trim());
		job.setJobType(jobDescriptionRequest.getJobType().trim());
		job.setVacancy(jobDescriptionRequest.getVacancy());
		job.setExperience(jobDescriptionRequest.getExperience().trim());
		job.setQualification(jobDescriptionRequest.getQualification().trim());
		job.setJobLocation(jobDescriptionRequest.getJobLocation().trim());
		job.setNoticePeriod(jobDescriptionRequest.getNoticePeriod().trim());
		job.setDomain(jobDescriptionRequest.getDomain().trim());
		job.setRoleAndResp(jobDescriptionRequest.getRoleAndResp().trim());
		job.setSkillsRequired(jobDescriptionRequest.getSkillsRequired().trim());
		job.setExpiryDate(jobDescriptionRequest.getExpiryDate());
		job.setSalary(jobDescriptionRequest.getSalary().trim());
		job.setDepartment(jobDescriptionRequest.getDepartment().trim());

		jobDescriptionRepository.save(job);

		if (jobDescriptionRequest.getInterviewRounds() != null
				&& !jobDescriptionRequest.getInterviewRounds().isEmpty()) {
			List<RoundDetails> roundDetailsList = new ArrayList<>();
			int roundNumber = 1;

			for (InterviewRounds round : jobDescriptionRequest.getInterviewRounds()) {
				RoundDetails roundDetails = new RoundDetails();
				roundDetails.setJobDescription(job);
				roundDetails.setRoundNumber(roundNumber++);
				roundDetails.setRoundType(round);

				roundDetailsList.add(roundDetails);
			}

			roundDetailsRepository.saveAll(roundDetailsList);
		}

		return true;
	}

	public Long countAllActiveJobs() {
		return jobDescriptionRepository.countAllActiveJobs(Status.ACTIVE);
	}

	public Optional<JobDescription> updateJobByJobId(Integer jobId, JobDescriptionRequest jobDescriptionRequest) {

		Optional<JobDescription> jobData = jobDescriptionRepository.findById(jobId);

		if (jobData.isPresent()) {

			JobDescription job = jobData.get();

			if (jobDescriptionRequest.getTitle() != null) {
				job.setTitle(jobDescriptionRequest.getTitle());
			}
			if (jobDescriptionRequest.getDomain() != null) {
				job.setDomain(jobDescriptionRequest.getDomain());
			}
			if (jobDescriptionRequest.getEmployementType() != null) {
				job.setEmployementType(jobDescriptionRequest.getEmployementType());
			}
			if (jobDescriptionRequest.getJobType() != null) {
				job.setJobType(jobDescriptionRequest.getJobType());
			}
			if (jobDescriptionRequest.getDepartment() != null) {
				job.setDepartment(jobDescriptionRequest.getDepartment());
			}
			if (jobDescriptionRequest.getQualification() != null) {
				job.setQualification(jobDescriptionRequest.getQualification());
			}
			if (jobDescriptionRequest.getSalary() != null) {
				job.setSalary(jobDescriptionRequest.getSalary());
			}
			if (jobDescriptionRequest.getAboutJob() != null) {
				job.setAboutJob(jobDescriptionRequest.getAboutJob());
			}
			if (jobDescriptionRequest.getRoleAndResp() != null) {
				job.setRoleAndResp(jobDescriptionRequest.getRoleAndResp());
			}
			if (jobDescriptionRequest.getSkillsRequired() != null) {
				job.setSkillsRequired(jobDescriptionRequest.getSkillsRequired());
			}

			if (jobDescriptionRequest.getJobLocation() != null) {
				job.setJobLocation(jobDescriptionRequest.getJobLocation());
			}
			if (jobDescriptionRequest.getVacancy() != null) {
				job.setVacancy(jobDescriptionRequest.getVacancy());
			}
			if (jobDescriptionRequest.getNoticePeriod() != null) {
				job.setNoticePeriod(jobDescriptionRequest.getNoticePeriod());
			}
			if (jobDescriptionRequest.getExpiryDate() != null) {
				job.setExpiryDate(jobDescriptionRequest.getExpiryDate());
			}

			jobDescriptionRepository.save(job);
			return jobData;
		}
		return null;
	}

	public JobDescription updateJobData(Integer jobId, JobDescriptionRequest jobDescriptionRequest) {

		Optional<JobDescription> updateData = jobDescriptionRepository.findById(jobId);

		if (updateData.isPresent()) {

			JobDescription jd = updateData.get();

			jd.setAboutJob(jobDescriptionRequest.getAboutJob().trim());
			jd.setEmployementType(jobDescriptionRequest.getEmployementType().trim());
			jd.setJobType(jobDescriptionRequest.getJobType().trim());
			jd.setTitle(jobDescriptionRequest.getTitle().trim());
			jd.setVacancy(jobDescriptionRequest.getVacancy());
			jd.setExperience(jobDescriptionRequest.getExperience().trim());
			jd.setQualification(jobDescriptionRequest.getQualification().trim());
			jd.setJobLocation(jobDescriptionRequest.getJobLocation().trim());
			jd.setNoticePeriod(jobDescriptionRequest.getNoticePeriod().trim());
			jd.setDomain(jobDescriptionRequest.getDomain().trim());
			jd.setRoleAndResp(jobDescriptionRequest.getRoleAndResp().trim());
			jd.setSkillsRequired(jobDescriptionRequest.getSkillsRequired().trim());
			jd.setExpiryDate(jobDescriptionRequest.getExpiryDate());
			jd.setStatus(jobDescriptionRequest.getStatus() != null ? jobDescriptionRequest.getStatus() : Status.ACTIVE);

			return jobDescriptionRepository.save(jd);
		}

		return null;
	}

	public Optional<JobDescription> getJobByJobId(Integer jobId) {
		return jobDescriptionRepository.findByJobId(jobId);
	}

	public List<JobDescription> getAllJobsByCompanyId(Integer companyId) {
		return jobDescriptionRepository.findAllJobsByCompanyId(companyId);
	}

	public boolean applyForJob(Integer candidateId, Integer jobId) {

		Optional<JobDescription> jobData = jobDescriptionRepository.findByJobId(jobId);
		Optional<Candidate> candidateData = candidateRepository.findByCandidateId(candidateId);

		if (jobData.isPresent() && candidateData.isPresent()) {
			JobDescription job = jobData.get();
			Candidate candidate = candidateData.get();

			boolean exists = jobMasterRepository.existsByCandidateCandidateIdAndJobDescriptionJobId(candidateId, jobId);

			if (exists) {
				return false;
			}

			JobMaster jobMaster = new JobMaster();
			jobMaster.setCandidate(candidate);
			jobMaster.setJobDescription(job);
			jobMaster.setCompany(job.getCompany());
			jobMaster.setCandidateStatus(CandidateStatus.APPLIED);
			jobMasterRepository.save(jobMaster);

			JobRound firstRound = new JobRound();
			firstRound.setJobMaster(jobMaster);
			firstRound.setRoundNumber(1);
			firstRound.setScore(0);
			firstRound.setRoundStatus(RoundStatus.PENDING);

			List<Recruiter> recruiters = recruiterRepository.findByCompanyCompanyId(job.getCompany().getCompanyId());

			if (!recruiters.isEmpty()) {
				Random random = new Random();
				Recruiter selectedRecruiter = recruiters.get(random.nextInt(recruiters.size()));
				firstRound.setRecruiter(selectedRecruiter);
			} else {
				throw new RuntimeException("No recruiters available for this company.");
			}

			jobRoundRepository.save(firstRound);

			return true;
		}
		return false;
	}

	public boolean saveJob(Integer candidateId, Integer jobId) {

		Optional<Candidate> candidateData = candidateRepository.findByCandidateId(candidateId);
		Optional<JobDescription> jobData = jobDescriptionRepository.findByJobId(jobId);

		if (candidateData.isPresent() && jobData.isPresent()) {
			JobDescription job = jobData.get();
			Candidate candidate = candidateData.get();

			boolean exists = savedJobsRepository.existsByCandidateCandidateIdAndJobDescriptionJobId(candidateId, jobId);

			if (exists) {
				return false;
			}

			SavedJobs savedJobs = new SavedJobs();
			savedJobs.setCandidate(candidate);
			savedJobs.setJobDescription(job);
			savedJobs.setSaveStatus(SaveStatus.SAVED);

			savedJobsRepository.save(savedJobs);
			return true;
		}
		return false;
	}

	public Optional<JobDescription> findByCompanyId(Integer companyId) {
		return jobDescriptionRepository.findByCompanyId(companyId);
	}

	public long getCountForActiveJobs(Integer companyId) {
		return jobDescriptionRepository.getActiveJobCount(companyId);
	}

	public long getAllJobCount(Integer companyId) {
		return jobDescriptionRepository.getAllJobCount(companyId);
	}

	public Optional<JobDescription> viewRepostJob(Integer jobId) {
		return jobDescriptionRepository.findById(jobId);
	}

	public Optional<JobDescription> repostJobByJobId(Integer jobId, JobDescriptionRequest jobDescriptionRequest) {

		Optional<JobDescription> jobData = jobDescriptionRepository.findById(jobId);

		if (jobData.isPresent()) {

			JobDescription jobDescription = jobData.get();
			jobDescription.setStatus(Status.ACTIVE);
			jobDescription.setExpiryDate(jobDescriptionRequest.getExpiryDate());
			jobDescription.setDatePosted(LocalDate.now());
			jobDescriptionRepository.save(jobDescription);

			return jobData;
		}

		return Optional.empty();
	}
}
