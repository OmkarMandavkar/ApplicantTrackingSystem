package com.dev.ATSapp.Dto;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import com.dev.ATSapp.Entity.Company;
import com.dev.ATSapp.Entity.JobMaster;
import com.dev.ATSapp.Enums.Status;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JobDescriptionRequest {

	private Integer jobId;
	private Company company;
    private String aboutJob; 
    private String employementType;
    private String jobType;
	private String cname;
	private String title;
	private Integer vacancy;
	private String experience;
	private String qualification;
	private String jobLocation;
	private String noticePeriod;
	private String domain;
	private String roleAndResp;
	private String skillsRequired;
	private LocalDate datePosted;
	private LocalDate expiryDate;
	private Status status;
}