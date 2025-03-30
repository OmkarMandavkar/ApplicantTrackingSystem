package com.dev.ATSapp.Entity;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Company {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer companyId;

	@Column(nullable = false)
	private String companyName;

	@Lob
	@Column(columnDefinition = "MEDIUMTEXT")
	private String description;

	private String address;

	private String natureOfBusiness;

	private String employeeCount;

	private String websiteLink;

	private String logoUrl;
	
	@Column(unique = true)
	private String companyAuthCode;

	private String companyAdminFirstName;

	private String companyAdminLastName;

	private String companyAdminEmail;

	private String companyAdminPassword;
	
	private Long companyAdminMobile;

	@OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
	private List<User> users;

	@OneToMany(mappedBy = "company")
	private List<JobDescription> jobDescriptions;

	@OneToMany(mappedBy = "company")
	private List<JobMaster> jobMasters;

	@OneToMany(mappedBy = "company")
	private List<Recruiter> recruiters;
	
	@CreationTimestamp
	private LocalDate createdDate;

	@UpdateTimestamp
	private LocalDate updatedDate;
}