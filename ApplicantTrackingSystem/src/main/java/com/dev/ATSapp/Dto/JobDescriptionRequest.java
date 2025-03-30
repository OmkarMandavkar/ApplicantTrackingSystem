package com.dev.ATSapp.Dto;

import java.time.LocalDate;
import java.util.List;

import com.dev.ATSapp.Entity.Company;
import com.dev.ATSapp.Enums.InterviewRounds;
import com.dev.ATSapp.Enums.Status;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JobDescriptionRequest {

	private Integer jobId;

	private Company company;

	private String title;

	private String aboutJob;

	private String employementType;

	private Integer vacancy;

	private String experience;

	private String qualification;

	private String jobLocation;

	private String jobType;

	private String noticePeriod;

	private String domain;

	private String salary;
	
	private String roleAndResp;

	private String skillsRequired;

    private List<InterviewRounds> interviewRounds;

	private LocalDate datePosted;

	private LocalDate expiryDate;

	private String manager;

	private String department;
	
	private Status status;
}