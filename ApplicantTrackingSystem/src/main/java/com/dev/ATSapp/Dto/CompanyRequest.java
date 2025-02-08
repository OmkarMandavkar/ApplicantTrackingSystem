package com.dev.ATSapp.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyRequest {

	private String cname;

	private String description;

	private String address;

	private String natureOfBusiness;

	private String employeeCount;

	private String websiteLink;

	private String companyAdminName;

	private String companyAdminEmail;

    private String companyAdminPassword;
	
	private Long companyAdminMobile;

    private String companyAuthCode;

}
