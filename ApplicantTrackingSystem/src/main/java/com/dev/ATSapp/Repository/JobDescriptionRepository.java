package com.dev.ATSapp.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dev.ATSapp.Entity.JobDescription;
import com.dev.ATSapp.Enums.Status;

public interface JobDescriptionRepository extends JpaRepository<JobDescription, Integer> {

	@Query("SELECT j FROM JobDescription j WHERE j.company.cname = ?1 AND j.title = ?2")
	Optional<JobDescription> findByCnameAndPosition(String cname, String title);

	@Query("SELECT COUNT(j) FROM JobDescription j WHERE j.status = ?1")
	Long countAllActiveJobs(Status status);

	Optional<JobDescription> getByJobId(Integer jobId);

	@Query("SELECT j FROM JobDescription j WHERE j.company.companyId= ?1")
	List<JobDescription> findAllJobsByCompanyId(Integer companyId);
	
	@Query("SELECT j FROM JobDescription j WHERE j.status = :status ORDER BY j.datePosted DESC")
	List<JobDescription> findAllActiveJob(Status status);

//	@Query("SELECT j FROM JobDescription j WHERE j.status = :status")
//	List<JobDescription> findAllInActiveJob(Status status);


}
