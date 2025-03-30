package com.dev.ATSapp.Dto;

import java.time.LocalDate;

import com.dev.ATSapp.Entity.Candidate;
import com.dev.ATSapp.Entity.Company;
import com.dev.ATSapp.Entity.JobDescription;
import com.dev.ATSapp.Enums.CandidateStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JobMasterRequest {

	private Integer applicationId;

	private Candidate candidate;

	private JobDescription jobDescription;

	private Company company;

	private CandidateStatus candidateStatus;

	private LocalDate appliedDate;

	private LocalDate lastRoundDate;
}