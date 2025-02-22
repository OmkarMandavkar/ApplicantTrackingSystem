package com.dev.ATSapp.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dev.ATSapp.Dto.RecruiterRequest;
import com.dev.ATSapp.Entity.Company;
import com.dev.ATSapp.Entity.Recruiter;

public interface RecruiterRepository extends JpaRepository<Recruiter, Integer>{

	Optional<Recruiter> findByRecruiterEmail(String recruiterEmail);

	@Query("SELECT s FROM Recruiter s WHERE s.company.id= :companyId")
	List<Recruiter> findByCompanyId(Integer	companyId);

    List<Recruiter> findByCompanyCompanyId(Integer companyId);

	Optional<Recruiter> findByEmail(String email);
	
}
