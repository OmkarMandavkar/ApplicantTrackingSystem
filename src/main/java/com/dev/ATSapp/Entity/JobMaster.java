package com.dev.ATSapp.Entity;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.dev.ATSapp.Enums.CandidateStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
public class JobMaster {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer applicationId;

	@ManyToOne
	@JoinColumn(name = "candidateId")
	private Candidate candidate;

	@ManyToOne
	@JoinColumn(name = "jobId")
	private JobDescription jobDescription;

	@ManyToOne
	@JoinColumn(name = "companyId")
	private Company company;

	@OneToMany(mappedBy = "jobMaster")
	private List<JobRound> jobRounds;
	
	@Enumerated(EnumType.STRING)
	private CandidateStatus candidateStatus;

	@CreationTimestamp
	private LocalDate appliedDate;

	@UpdateTimestamp
	private LocalDate lastRoundDate;
}