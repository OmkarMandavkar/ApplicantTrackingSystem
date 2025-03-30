package com.dev.ATSapp.Entity;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import com.dev.ATSapp.Enums.Status;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
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
public class JobDescription {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer jobId;

	@ManyToOne
	@JoinColumn(name = "companyId", nullable = false)
	private Company company;

	private String title;

	@Lob
	@Column(columnDefinition = "MEDIUMTEXT")
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

	@Lob
	@Column(columnDefinition = "MEDIUMTEXT")
	private String roleAndResp;

	private String skillsRequired;

	@OneToMany(mappedBy = "jobDescription", cascade = CascadeType.ALL)
	private List<RoundDetails> roundDetails;

	@CreationTimestamp
	private LocalDate datePosted;

	private LocalDate expiryDate;

	private String manager;

	private String department;

	@Enumerated(EnumType.STRING)
	private Status status = Status.ACTIVE;

	@OneToMany(mappedBy = "jobDescription")
	private List<SavedJobs> savedJobs;

	@OneToMany(mappedBy = "jobDescription")
	private List<JobMaster> jobMasters;
}