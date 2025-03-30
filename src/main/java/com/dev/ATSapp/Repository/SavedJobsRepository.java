package com.dev.ATSapp.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dev.ATSapp.Entity.SavedJobs;

public interface SavedJobsRepository extends JpaRepository<SavedJobs, Integer>{
	
	boolean existsByCandidateCandidateIdAndJobDescriptionJobId(Integer candidateId, Integer jobId);

	@Query("SELECT c FROM SavedJobs c WHERE c.candidate.candidateId = :candidateId AND c.saveStatus = 'SAVED' ORDER BY savedJobId")
	List<SavedJobs> findByCandidateIdAndSaveStatus(Integer candidateId);

	@Query("SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END FROM SavedJobs e WHERE e.candidate.candidateId = ?1 AND e.jobDescription.jobId = ?2")
	boolean existsByCandidateIdAndJobIdAndSaveStatus(Integer candidateId, Integer jobId);

}
