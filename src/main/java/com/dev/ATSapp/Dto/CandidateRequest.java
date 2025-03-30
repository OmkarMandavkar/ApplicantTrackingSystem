package com.dev.ATSapp.Dto;

import com.dev.ATSapp.Entity.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CandidateRequest {

   	private Integer candidateId;

   	private User user;

    private String email;

	private String password;

    private String currentDesignation;

    private String salaryExpectations;
    
    private String qualification;
    
    private String workExperience;

    private String project1;
	
    private String project2;
	
    private String project3;
	
    private String project4;
	
    private String location;

    private String skills;
}