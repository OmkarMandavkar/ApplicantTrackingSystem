package com.dev.ATSapp.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dev.ATSapp.Entity.RoundDetails;

public interface RoundDetailsRepository extends JpaRepository<RoundDetails, Integer> {

	@Query("SELECT r FROM RoundDetails r WHERE r.jobDescription.id = :jobId")
	List<RoundDetails> findByJobId(Integer jobId);
}
