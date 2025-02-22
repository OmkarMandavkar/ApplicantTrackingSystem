package com.dev.ATSapp.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.dev.ATSapp.Entity.Candidate;
import com.dev.ATSapp.Repository.CandidateRepository;
import com.dev.ATSapp.Repository.CompanyRepository;
import com.dev.ATSapp.Repository.JobDescriptionRepository;
import com.dev.ATSapp.Repository.JobMasterRepository;
import com.dev.ATSapp.Repository.UserRepository;

@Service
public class CandidateService {

	private UserRepository userRepository;
	private CandidateRepository candidateRepository;
	private CompanyRepository companyRepository;
	private JobDescriptionRepository jobDescriptionRepository;
	private JobMasterRepository jobMasterRepository;

	public CandidateService(UserRepository userRepository, CandidateRepository candidateRepository,
			CompanyRepository companyRepository, JobDescriptionRepository jobDescriptionRepository,
			JobMasterRepository jobMasterRepository) {
		this.userRepository = userRepository;
		this.candidateRepository = candidateRepository;
		this.companyRepository = companyRepository;
		this.jobDescriptionRepository = jobDescriptionRepository;
		this.jobMasterRepository = jobMasterRepository;
	}

	public Long countAllCandidate() {
		return candidateRepository.count();
	}

	public List<Candidate> findAllAppliedJobs(Integer userId) {
		return candidateRepository.findByUserId(userId);
	}

	public long getAppliedCount(Integer userId) {
		return candidateRepository.countByUserId(userId);
	}

	public boolean hasCandidateApplied(Integer candidateId, Integer jobId) {
		return candidateRepository.existsByCandidateIdAndJobId(candidateId, jobId);
	}

	public List<Candidate> findAllAppliedJobs(String candidateEmail) {
		return candidateRepository.findByUserEmail(candidateEmail);
	}

}
