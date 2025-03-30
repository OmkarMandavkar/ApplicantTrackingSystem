package com.dev.ATSapp.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.dev.ATSapp.Dto.CandidateRequest;
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

	public Optional<Candidate> findByEmail(String candidateEmail) {
		return candidateRepository.findByCandidateEmail(candidateEmail);
	}

	public Candidate saveCandidate(Candidate candidate) {
		return candidateRepository.save(candidate);
	}

	public Long countAllCandidate() {
		return candidateRepository.count();
	}

	public List<Candidate> findAllAppliedJobs(Integer userId) {
		return candidateRepository.findByUserId(userId);
	}

	public List<Candidate> findAllAppliedJobs(String candidateEmail) {
		return candidateRepository.findByUserEmail(candidateEmail);
	}

	public boolean addAdditionalCandidateDetails(String candidateEmail, CandidateRequest candidateRequest) {

		Optional<Candidate> candidateData = candidateRepository.findByCandidateEmail(candidateEmail);

		if (candidateData.isPresent()) {
			Candidate candidate = candidateData.get();
			candidate.setCurrentDesignation(candidateRequest.getCurrentDesignation().trim());
			candidate.setSalaryExpectations(candidateRequest.getSalaryExpectations().trim());
			candidate.setQualification(candidateRequest.getQualification().trim());
			candidate.setSkills(candidateRequest.getSkills().trim());
			candidate.setWorkExperience(candidateRequest.getWorkExperience().trim());
			candidate.setProject1(candidateRequest.getProject1().trim());
			candidate.setProject2(candidateRequest.getProject2().trim());
			candidate.setProject3(candidateRequest.getProject3().trim());
			candidate.setProject4(candidateRequest.getProject4().trim());

			candidateRepository.save(candidate);

			return true;
		}
		return false;
	}

	public Optional<Candidate> findByCandidateEmail(String candidateEmail) {
		return candidateRepository.findByCandidateEmail(candidateEmail);
	}

}