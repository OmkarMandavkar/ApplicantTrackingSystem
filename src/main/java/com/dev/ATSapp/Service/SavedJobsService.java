package com.dev.ATSapp.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dev.ATSapp.Entity.SavedJobs;
import com.dev.ATSapp.Repository.SavedJobsRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SavedJobsService {

	private final SavedJobsRepository savedJobsRepository;

	public List<SavedJobs> findAllSavedJobs(Integer candidateId) {
		return savedJobsRepository.findByCandidateIdAndSaveStatus(candidateId);
	}

	public boolean hasCandidateSaved(Integer candidateId, Integer jobId) {
		return savedJobsRepository.existsByCandidateIdAndJobIdAndSaveStatus(candidateId, jobId);
	}
}