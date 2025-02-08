package com.dev.ATSapp.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dev.ATSapp.Entity.Candidate;

public interface CandidateRepository extends JpaRepository<Candidate, Integer>{

}
