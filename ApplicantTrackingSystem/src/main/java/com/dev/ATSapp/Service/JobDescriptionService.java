package com.dev.ATSapp.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.dev.ATSapp.Dto.JobDescriptionRequest;
import com.dev.ATSapp.Entity.Candidate;
import com.dev.ATSapp.Entity.Company;
import com.dev.ATSapp.Entity.JobDescription;
import com.dev.ATSapp.Entity.JobMaster;
import com.dev.ATSapp.Entity.Recruiter;
import com.dev.ATSapp.Entity.User;
import com.dev.ATSapp.Enums.Status;
import com.dev.ATSapp.Repository.CandidateRepository;
import com.dev.ATSapp.Repository.CompanyRepository;
import com.dev.ATSapp.Repository.JobDescriptionRepository;
import com.dev.ATSapp.Repository.JobMasterRepository;
import com.dev.ATSapp.Repository.RecruiterRepository;
import com.dev.ATSapp.Repository.UserRepository;

@Service
public class JobDescriptionService {

	private UserRepository userRepository;
	private CandidateRepository candidateRepository;
	private JobDescriptionRepository jobDescriptionRepository;
	private CompanyRepository companyRepository;
	private JobMasterRepository jobMasterRepository;
	private RecruiterRepository recruiterRepository;


	public JobDescriptionService(UserRepository userRepository, CandidateRepository candidateRepository,
			JobDescriptionRepository jobDescriptionRepository, CompanyRepository companyRepository,
			JobMasterRepository jobMasterRepository, RecruiterRepository recruiterRepository) {
		this.userRepository = userRepository;
		this.candidateRepository = candidateRepository;
		this.jobDescriptionRepository = jobDescriptionRepository;
		this.companyRepository = companyRepository;
		this.jobMasterRepository = jobMasterRepository;
		this.recruiterRepository = recruiterRepository;
	}

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

		Company company = companyData.get();

		Optional<JobDescription> jobData = jobDescriptionRepository.findByCnameAndPosition(company.getCname(),
				jobDescriptionRequest.getTitle());

		if (jobData.isPresent()) {
			return false;
		}

		JobDescription job = new JobDescription();
		job.setCompany(company);
		job.setCname(company.getCname());
		job.setTitle(jobDescriptionRequest.getTitle());
		job.setAboutJob(jobDescriptionRequest.getAboutJob());
		job.setEmployementType(jobDescriptionRequest.getEmployementType());
		job.setJobType(jobDescriptionRequest.getJobType());
		job.setVacancy(jobDescriptionRequest.getVacancy());
		job.setExperience(jobDescriptionRequest.getExperience());
		job.setQualification(jobDescriptionRequest.getQualification());
		job.setJobLocation(jobDescriptionRequest.getJobLocation());
		job.setNoticePeriod(jobDescriptionRequest.getNoticePeriod());
		job.setDomain(jobDescriptionRequest.getDomain());
		job.setRoleAndResp(jobDescriptionRequest.getRoleAndResp());
		job.setSkillsRequired(jobDescriptionRequest.getSkillsRequired());
		job.setExpiryDate(jobDescriptionRequest.getExpiryDate());

		jobDescriptionRepository.save(job);
		return true;
	}

	public Long countAllActiveJobs() {
		return jobDescriptionRepository.countAllActiveJobs(Status.ACTIVE);
	}

	public Optional<JobDescription> updateJobByJobId(Integer jobId) {
		return jobDescriptionRepository.findById(jobId);
	}

	public JobDescription updateJobData(Integer jobId, JobDescriptionRequest jobDescriptionRequest) {

		Optional<JobDescription> updateData = jobDescriptionRepository.findById(jobId);

		if (updateData.isPresent()) {

			JobDescription jd = updateData.get();

			jd.setCname(jobDescriptionRequest.getCname());
			jd.setAboutJob(jobDescriptionRequest.getAboutJob());
			jd.setEmployementType(jobDescriptionRequest.getEmployementType());
			jd.setJobType(jobDescriptionRequest.getJobType());
			jd.setTitle(jobDescriptionRequest.getTitle());
			jd.setVacancy(jobDescriptionRequest.getVacancy());
			jd.setExperience(jobDescriptionRequest.getExperience());
			jd.setQualification(jobDescriptionRequest.getQualification());
			jd.setJobLocation(jobDescriptionRequest.getJobLocation());
			jd.setNoticePeriod(jobDescriptionRequest.getNoticePeriod());
			jd.setDomain(jobDescriptionRequest.getDomain());
			jd.setRoleAndResp(jobDescriptionRequest.getRoleAndResp());
			jd.setSkillsRequired(jobDescriptionRequest.getSkillsRequired());
			jd.setExpiryDate(jobDescriptionRequest.getExpiryDate());
			jd.setStatus(jobDescriptionRequest.getStatus() != null ? jobDescriptionRequest.getStatus() : Status.ACTIVE);

			return jobDescriptionRepository.save(jd);
		}

		return null;
	}

	public Optional<JobDescription> getJobByJobId(Integer jobId) {
		return jobDescriptionRepository.getByJobId(jobId);
	}

	public List<JobDescription> getAllJobsByCompanyId(Integer companyId) {
		return jobDescriptionRepository.findAllJobsByCompanyId(companyId);
	}

	public boolean applyForJob(Integer jobId, Integer userId) {
	    
		Optional<JobDescription> jobData = jobDescriptionRepository.findById(jobId);
	    Optional<User> userData = userRepository.findById(userId);

	    if (jobData.isPresent() && userData.isPresent()) {
	        JobDescription job = jobData.get();
	        User user = userData.get();

	        Optional<Candidate> existingCandidate = candidateRepository.findByJobDescriptionJobIdAndUserUserId(jobId, userId);

	        if (existingCandidate.isPresent()) {
	            System.out.println("Duplicate application: User ID = " + userId + " already applied for Job ID = " + jobId);
	            return false;
	        } else {
	            Candidate candidate = new Candidate();
	            candidate.setUser(user);
	            candidate.setJobDescription(job);
	            candidate.setEmail(user.getEmail());
	            candidate.setFirstname(user.getFirstname());
	            candidate.setLastname(user.getLastname());
	            candidate.setLocation(user.getCity() + ", " + user.getCountry());
	            candidate.setPhone(user.getMobile());
	            candidate.setCompany(job.getCompany());

	            JobMaster jobMaster = new JobMaster();
	            jobMaster.setCandidate(candidate);
	            jobMaster.setJobDescription(job);
	            jobMaster.setCompany(job.getCompany());

	            List<Recruiter> recruiters = recruiterRepository.findByCompanyCompanyId(job.getCompany().getCompanyId());

	            if (!recruiters.isEmpty()) {
	                // Select a random recruiter
	                Random random = new Random();
	                Recruiter selectedRecruiter = recruiters.get(random.nextInt(recruiters.size()));

	                jobMaster.setRecruiterName(selectedRecruiter.getRecruiterFullname());
	                jobMaster.setRecruiterEmail(selectedRecruiter.getRecruiterEmail());
	                jobMaster.setRecruiterMobile(selectedRecruiter.getRecruiterMobile());
	            }

	            candidateRepository.save(candidate);
	            jobMasterRepository.save(jobMaster);
	            return true;

	        }
	    } else {
	        return false;
	    }
	}



}
