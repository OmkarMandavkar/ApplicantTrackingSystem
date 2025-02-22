package com.dev.ATSapp.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dev.ATSapp.Entity.JobMaster;
import com.dev.ATSapp.Repository.CandidateRepository;
import com.dev.ATSapp.Repository.CompanyRepository;
import com.dev.ATSapp.Repository.JobDescriptionRepository;
import com.dev.ATSapp.Repository.JobMasterRepository;
import com.dev.ATSapp.Repository.UserRepository;

@Service
public class JobMasterService {

	private UserRepository userRepository;
	private CandidateRepository candidateRepository;
	private CompanyRepository companyRepository;
	private JobDescriptionRepository jobDescriptionRepository;
	private JobMasterRepository jobMasterRepository;
	
	public JobMasterService(UserRepository userRepository, CandidateRepository candidateRepository,
			CompanyRepository companyRepository, JobDescriptionRepository jobDescriptionRepository,
			JobMasterRepository jobMasterRepository) {
		this.userRepository = userRepository;
		this.candidateRepository = candidateRepository;
		this.companyRepository = companyRepository;
		this.jobDescriptionRepository = jobDescriptionRepository;
		this.jobMasterRepository = jobMasterRepository;
	}

	public List<JobMaster> findByRecruiterEmail(String recruiterEmail) {
	    return jobMasterRepository.findByRecruiterEmail(recruiterEmail);
	}

	
	
	
}
