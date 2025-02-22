package com.dev.ATSapp.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dev.ATSapp.Entity.Candidate;

public interface CandidateRepository extends JpaRepository<Candidate, Integer> {

	Optional<Candidate> findByJobDescriptionJobIdAndUserUserId(Integer jobId, Integer userId);

	@Query("SELECT c FROM Candidate c WHERE c.user.id = :userId")
	List<Candidate> findByUserId(Integer userId);

	@Query("SELECT COUNT(c) FROM Candidate c WHERE c.user.id = ?1")
	long countByUserId(Integer userId);

	@Query("SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END FROM Candidate e WHERE e.user.userId = ?1 AND e.jobDescription.jobId = ?2")
	boolean existsByCandidateIdAndJobId(Integer candidateId, Integer jobId);

	List<Candidate> findByUserEmail(String candidateEmail);


}
