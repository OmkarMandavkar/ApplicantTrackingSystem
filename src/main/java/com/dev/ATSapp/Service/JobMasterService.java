package com.dev.ATSapp.Service;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.dev.ATSapp.Entity.JobMaster;
import com.dev.ATSapp.Repository.CandidateRepository;
import com.dev.ATSapp.Repository.CompanyRepository;
import com.dev.ATSapp.Repository.JobDescriptionRepository;
import com.dev.ATSapp.Repository.JobMasterRepository;
import com.dev.ATSapp.Repository.JobRoundRepository;
import com.dev.ATSapp.Repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JobMasterService {

	private final JobMasterRepository jobMasterRepository;
	
	public List<JobMaster> findAllAppliedJobs(Integer candidateId) {
		return jobMasterRepository.findByCandidateIdAndAppliedDate(candidateId);
	}

	public boolean hasCandidateApplied(Integer candidateId, Integer jobId) {
		return jobMasterRepository.existsByCandidateIdAndJobId(candidateId, jobId);
	}

	public long getAppliedCount(Integer candidateId) {
		return jobMasterRepository.findByAppliedCountByCandidateId(candidateId);
	}

	public long getRejectedCount(Integer candidateId) {
		return jobMasterRepository.findByCandidateIdAndRejectedStatus(candidateId);
	}

	public long getSelectedCount(Integer candidateId) {
		return jobMasterRepository.findByCandidateIdAndSelectedStatus(candidateId);
	}

	public long getUnderReviewCount(Integer candidateId) {
		return jobMasterRepository.findByCandidateIdAndUnderReviewStatus(candidateId);
	}

	public long getSelectedCountByCompanyId(Integer companyId) {
		return jobMasterRepository.findByCompanyIdAndSelectedStatus(companyId);
	}
	
	public long getRejectedCountByCompanyId(Integer companyId) {
		return jobMasterRepository.findByCompanyIdAndRejectedStatus(companyId);
	}
	
	public long getHiredCountByCompanyId(Integer companyId) {
		return jobMasterRepository.findByCompanyIdAndHiredStatus(companyId);
	}

	public long getAllApplicationCount(Integer companyId) {
		return jobMasterRepository.getCountByCompanyId(companyId);
	}

	public long getInProgressCountByCompanyId(Integer companyId) {
		return jobMasterRepository.findByCompanyIdAndInProgressStatus(companyId);
	}
	
	public List<JobMaster> findByCompanyId(Integer companyId) {
		return jobMasterRepository.findByCompanyCompanyId(companyId);
	}

	public List<JobMaster> findByCompanyIdAndOrder(Integer companyId) {
		return jobMasterRepository.findByCompanyCompanyIdAndAppliedDate(companyId);
	}
	
	public Optional<JobMaster> findByApplicationId(Integer applicationId) {
		return jobMasterRepository.findById(applicationId);
	}

	public List<Map<String, Object>> getJobCandidateCount(Integer companyId) {
		return jobMasterRepository.getJobCandidateCountForCompany(companyId);
	}

	public Map<String, Integer> getApplicationCountLast6Months(Integer candidateId) {
		LocalDate today = LocalDate.now();
		Map<String, Integer> applicationData = new LinkedHashMap<>();

		for (int i = 5; i >= 0; i--) {
			LocalDate month = today.minusMonths(i);
			String monthName = month.getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH); // Jan, Feb, etc.
			int count = jobMasterRepository.countByAppliedDateBetween(candidateId, month.withDayOfMonth(1),
					month.withDayOfMonth(month.lengthOfMonth()));
			applicationData.put(monthName, count);
		}
		return applicationData;
	}

	public Optional<JobMaster> findApplicantData(Integer applicationId) {
		return jobMasterRepository.findById(applicationId);
	}

	public Optional<JobMaster> findApplicantStatus(Integer applicationId, JobMaster jobMaster) {

		Optional<JobMaster> applicantData = jobMasterRepository.findById(applicationId);

		if (applicantData.isPresent()) {
			JobMaster job = applicantData.get();

			if (job != null) {
				job.setCandidateStatus(jobMaster.getCandidateStatus());
				jobMasterRepository.save(job);
			}
			return applicantData;
		}
		return Optional.empty();
	}

	public long getJobCountByCompanyId(Integer jobId) {
		return jobMasterRepository.findCountByJobId(jobId);
	}

    public Map<Integer, Long> getJobApplicationCounts() {
        List<Object[]> results = jobMasterRepository.countApplicationsPerJob();
        Map<Integer, Long> jobCounts = new HashMap<>();
        
        for (Object[] result : results) {
            Integer jobId = (Integer) result[0];
            Long count = (Long) result[1];
            jobCounts.put(jobId, count);
        }
        return jobCounts;
    }



}
