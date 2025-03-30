package com.dev.ATSapp.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dev.ATSapp.Entity.JobRound;

public interface JobRoundRepository extends JpaRepository<JobRound, Integer> {

//	List<JobRound> findByRecruiterId(Integer recruiterId);

//	@Query("SELECT j FROM JobRound j WHERE j.jobMaster.id = :applicationId AND j.recruiter.id = :recruiterId")
//    Optional<JobRound> findByApplicationIdAndRecruiterId(Integer applicationId, Integer recruiterId);

//	@Query("SELECT j FROM JobRound j WHERE j.jobMaster.id = :applicationId AND j.recruiter.id = :recruiterId ORDER BY j.roundNumber DESC")
//	Optional<JobRound> findTopByApplicationIdAndRecruiterId(Integer applicationId, Integer recruiterId);

	Optional<JobRound> findFirstByJobMasterApplicationIdAndRecruiterRecruiterIdOrderByRoundNumberDesc(Integer applicationId, Integer recruiterId);
	
	@Query("SELECT j FROM JobRound j WHERE j.recruiter.id = :recruiterId AND j.roundStatus NOT IN('SELECTED', 'REJECTED', 'COMPLETED')")
	List<JobRound> findByRecruiterId(Integer recruiterId);

	@Query("SELECT j FROM JobRound j WHERE j.jobMaster.candidate.candidateId = :candidateId")
	List<JobRound> findByCandidateId(Integer candidateId);

	@Query("SELECT j FROM JobRound j WHERE j.recruiter.id = :recruiterId")
	List<JobRound> findAllByRecruiterId(Integer recruiterId);

	@Query("SELECT j FROM JobRound j WHERE j.recruiter.id = :recruiterId ORDER BY roundId DESC")
	List<JobRound> findAllByRecruiterIdOrder(Integer recruiterId);

	@Query("SELECT j FROM JobRound j WHERE j.jobMaster.candidate.candidateId = :candidateId ORDER BY roundDate")
	List<JobRound> findByCandidateIdOrderRoundDate(Integer candidateId);

	@Query("SELECT j FROM JobRound j WHERE j.jobMaster.candidate.candidateId = :candidateId AND j.roundStatus = 'SCHEDULED' AND (j.roundDate > CURRENT_DATE OR (j.roundDate = CURRENT_DATE AND j.roundTime > CURRENT_TIME)) ORDER BY j.roundDate ASC, j.roundTime ASC")
	List<JobRound> findAllScheduledInterviewByCandidateId(Integer candidateId);

	@Query("SELECT j FROM JobRound j WHERE j.roundStatus = 'COMPLETED' AND j.jobMaster.candidateStatus IN('IN_PROGRESS','OFFERED', 'ONBOARDED', 'HIRED')")
	List<JobRound> findAllOnboardingCandidates();

	@Query("SELECT j FROM JobRound j WHERE j.jobMaster.id =:applicationId AND j.roundStatus = 'COMPLETED'")
	Optional<JobRound> findByLetterApplicationId(Integer applicationId);

	@Query("SELECT j FROM JobRound j WHERE j.jobMaster.applicationId = :applicationId AND j.roundStatus = 'COMPLETED'")
	Optional<JobRound> findByApplicationIdAndCompletedStatus(Integer applicationId);

	@Query("SELECT j FROM JobRound j WHERE j.jobMaster.applicationId = :applicationId ORDER BY j.roundNumber DESC")
	Optional<JobRound> findByApplicationId(Integer applicationId);

	@Query("SELECT COUNT(j) FROM JobRound j WHERE j.jobMaster.company.id = :companyId AND j.roundStatus IN ('SELECTED', 'REJECTED', 'IN_PROGRESS', 'COMPLETED')")
	long getInterviewCountByCompanyId(Integer companyId);


	
}
