package com.dev.ATSapp.Dto;

import com.dev.ATSapp.Entity.Company;
import com.dev.ATSapp.Entity.JobDescription;
import com.dev.ATSapp.Entity.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CandidateRequest {

	private Integer candidateId;
	private User user;
	private Company company;
	private JobDescription jobDescription;
	private String firstname;
	private String lastname;
	private String email;
	private Long phone;
	private String position;
	private Long salaryExpectations;
	private String workExperience;
	private String qualification;
	private String location;
	private Integer noticePeriod;
	private String skills;
}