package com.dev.ATSapp.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dev.ATSapp.Entity.Candidate;

public interface CandidateRepository extends JpaRepository<Candidate, Integer> {

//	Optional<Candidate> findByJobDescriptionJobIdAndUserUserId(Integer jobId, Integer userId);

	@Query("SELECT c FROM Candidate c WHERE c.user.id = :userId")
	List<Candidate> findByUserId(Integer userId);

	@Query("SELECT COUNT(c) FROM Candidate c WHERE c.email = ?1")
	long countByCandidateEmail(String candidateEmail);

	List<Candidate> findByUserEmail(String candidateEmail);

	Optional<Candidate> findByCandidateId(Integer candidateId);

	@Query("SELECT c FROM Candidate c WHERE c.email = ?1")
	Optional<Candidate> findByCandidateEmail(String candidateEmail);


}
