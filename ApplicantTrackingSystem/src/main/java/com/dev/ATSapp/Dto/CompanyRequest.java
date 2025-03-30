package com.dev.ATSapp.Dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyRequest {

	private Integer companyId;

	private String companyName;

	private String description;

	private String address;

	private String natureOfBusiness;

	private String employeeCount;

	private String websiteLink;

	private String logoUrl;
	
	private String companyAuthCode;

	private String companyAdminFirstName;

	private String companyAdminLastName;

	private String companyAdminEmail;

	private String companyAdminPassword;
	
	private Long companyAdminMobile;

	private LocalDate createdDate;

	private LocalDate updatedDate;
}
