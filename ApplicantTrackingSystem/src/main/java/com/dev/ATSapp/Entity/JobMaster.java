package com.dev.ATSapp.Entity;

import com.dev.ATSapp.Enums.CandidateStatus;

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
	private Integer resultId;

	@ManyToOne
	@JoinColumn(name = "candidateId")
	private Candidate candidate;

	@ManyToOne
	@JoinColumn(name = "jobId")
	private JobDescription jobDescription;

	@ManyToOne
	@JoinColumn(name = "companyId")
	private Company company;

	private Integer round1Score;

	private Integer round2Score;

	private Integer round3Score;

	private Integer round4Score;

	private Integer round5Score;
	
	private Integer round6Score;
	
	private Integer averageScore;

	@Enumerated(EnumType.STRING)
	private CandidateStatus candidateStatus;

	@Lob
	@Column(columnDefinition = "MEDIUMTEXT")
	private String feedback;

	private String recruiterName;

	private String recruiterEmail;

	private Long recruiterMobile;
}