package com.dev.ATSapp.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dev.ATSapp.Entity.JobMaster;

public interface JobMasterRepository extends JpaRepository<JobMaster, Integer> {

	boolean existsByCandidateCandidateIdAndJobDescriptionJobId(Integer candidateId, Integer jobId);

	@Query("SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END FROM JobMaster e WHERE e.candidate.candidateId = ?1 AND e.jobDescription.jobId = ?2")
	boolean existsByCandidateIdAndJobId(Integer candidateId, Integer jobId);

	Optional<JobMaster> findByCandidateCandidateIdAndJobDescriptionJobId(Integer candidateId, Integer jobId);

	@Query("SELECT c FROM JobMaster c WHERE c.candidate.id = :candidateId ORDER BY c.appliedDate DESC")
	List<JobMaster> findByCandidateIdAndAppliedDate(Integer candidateId);

	@Query("SELECT COUNT(c) FROM JobMaster c WHERE c.candidate.candidateId = :candidateId")
	long findByAppliedCountByCandidateId(Integer candidateId);

	@Query("SELECT COUNT(c) FROM JobMaster c WHERE c.candidate.candidateId = :candidateId AND c.candidateStatus = 'REJECTED'")
	long findByCandidateIdAndRejectedStatus(Integer candidateId);

	@Query("SELECT COUNT(j) FROM JobMaster j WHERE j.candidate.id = :candidateId AND j.candidateStatus IN ('SHORTLISTED','IN_PROGRESS','APPLIED')")
	long findByCandidateIdAndUnderReviewStatus(Integer candidateId);

	@Query("SELECT COUNT(c) FROM JobMaster c WHERE c.candidate.candidateId = :candidateId AND c.candidateStatus = 'HIRED'")
	long findByCandidateIdAndSelectedStatus(Integer candidateId);

	@Query("SELECT c FROM JobMaster c WHERE c.company.id = :companyId")
	List<JobMaster> findByCompanyCompanyId(Integer companyId);

	@Query("SELECT c FROM JobMaster c WHERE c.company.id = :companyId ORDER BY c.appliedDate DESC")
	List<JobMaster> findByCompanyCompanyIdAndAppliedDate(Integer companyId);

//	@Query("SELECT new map(SUBSTRING(j.jobDescription.title, 1, 20) as title, COUNT(j.candidate) as candidateCount) "
//			+ "FROM JobMaster j WHERE j.company.companyId = :companyId GROUP BY j.jobDescription.title")
//	List<Map<String, Object>> getJobCandidateCountForCompany(Integer companyId);

	@Query("SELECT new map(SUBSTRING(jd.title, 1, 20) as title, COUNT(jm.candidate) as candidateCount) "
			+ "FROM JobDescription jd LEFT JOIN JobMaster jm ON jd.jobId = jm.jobDescription.jobId "
			+ "WHERE jd.company.companyId = :companyId " + "GROUP BY SUBSTRING(jd.title, 1, 20)")
	List<Map<String, Object>> getJobCandidateCountForCompany(Integer companyId);

	@Query("SELECT f FROM JobMaster f WHERE f.id = :applicationId")
	JobMaster findByApplicationId(Integer applicationId);

	@Query("SELECT COUNT(j) FROM JobMaster j WHERE j.candidate.id =:candidateId AND j.appliedDate BETWEEN :startDate AND :endDate")
	int countByAppliedDateBetween(Integer candidateId, LocalDate startDate, LocalDate endDate);

	@Query("SELECT COUNT(j) FROM JobMaster j WHERE j.id =:jobId")
	long findCountByJobId(Integer jobId);

	@Query("SELECT jm.jobDescription.jobId, COUNT(jm) FROM JobMaster jm GROUP BY jm.jobDescription.jobId")
	List<Object[]> countApplicationsPerJob();

	@Query("SELECT COUNT(j) FROM JobMaster j WHERE j.company.id = :companyId")
	long getCountByCompanyId(Integer companyId);

	@Query("SELECT COUNT(j) FROM JobMaster j WHERE j.company.id = :companyId AND j.candidateStatus = 'COMPLETED'")
	long findByCompanyIdAndSelectedStatus(Integer companyId);

	@Query("SELECT COUNT(j) FROM JobMaster j WHERE j.company.id = :companyId AND j.candidateStatus = 'REJECTED'")
	long findByCompanyIdAndRejectedStatus(Integer companyId);

	@Query("SELECT COUNT(j) FROM JobMaster j WHERE j.company.id = :companyId AND j.candidateStatus = 'HIRED'")
	long findByCompanyIdAndHiredStatus(Integer companyId);

	@Query("SELECT COUNT(j) FROM JobMaster j WHERE j.company.id = :companyId AND j.candidateStatus IN ('APPLIED','IN_PROGRESS', 'SHORTLISTED', 'OFFERED', 'ON_HOLD')")
	long findByCompanyIdAndInProgressStatus(Integer companyId);

}