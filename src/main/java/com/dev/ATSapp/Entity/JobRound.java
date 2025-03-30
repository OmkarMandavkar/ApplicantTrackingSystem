package com.dev.ATSapp.Entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.hibernate.annotations.UpdateTimestamp;

import com.dev.ATSapp.Enums.RoundStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
public class JobRound {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer roundId;

	@ManyToOne
	@JoinColumn(name = "applicationId")
	private JobMaster jobMaster;

	private Integer roundNumber;

	@ManyToOne
	@JoinColumn(name = "recruiterId")
	private Recruiter recruiter;

	private Integer score;

	private String feedback;

	private String roundType;
	
	private LocalDate roundDate;
	
	private LocalTime roundTime;

	@UpdateTimestamp
	private LocalDateTime lastUpdatedDate;
	
//	@Enumerated(EnumType.STRING)
//	private InterviewRounds interviewRounds;

	@Enumerated(EnumType.STRING)
	private RoundStatus roundStatus;
	
	private LocalDate offerDate;
	
	private LocalDate acceptDate;
	
	private LocalDate joiningDate;
}