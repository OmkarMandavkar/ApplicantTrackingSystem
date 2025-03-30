package com.dev.ATSapp.Service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.dev.ATSapp.Entity.Company;
import com.dev.ATSapp.Entity.User;
import com.dev.ATSapp.Repository.CandidateRepository;
import com.dev.ATSapp.Repository.CompanyRepository;
import com.dev.ATSapp.Repository.JobDescriptionRepository;
import com.dev.ATSapp.Repository.JobMasterRepository;
import com.dev.ATSapp.Repository.UserRepository;

@Service
public class SuperAdminService {

	private UserRepository userRepository;
	private CandidateRepository candidateRepository;
	private CompanyRepository companyRepository;
	private JobMasterRepository interviewResultRepository;
	private JobDescriptionRepository jobProfileRepository;

	public SuperAdminService(UserRepository userRepository, CandidateRepository candidateRepository,
			CompanyRepository companyRepository, JobMasterRepository interviewResultRepository,
			JobDescriptionRepository jobProfileRepository) {
		this.userRepository = userRepository;
		this.candidateRepository = candidateRepository;
		this.companyRepository = companyRepository;
		this.interviewResultRepository = interviewResultRepository;
		this.jobProfileRepository = jobProfileRepository;
	}

//	public Optional<Company> findByCompanyAdminEmail(String userEmail) {
//		return companyRepository.findByCompanyAdminEmail(userEmail);
//	}



}
