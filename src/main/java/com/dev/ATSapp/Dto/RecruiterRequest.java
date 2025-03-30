package com.dev.ATSapp.Dto;

import com.dev.ATSapp.Entity.Company;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecruiterRequest {

	private Integer recruiterId;

	private String recruiterFullname;
	
	private String recruiterEmail;
	
	private Long recruiterMobile;
	
	private String recruiterPassword;
	
	private String recruiterDesignation;
	
	private Company company;
}
