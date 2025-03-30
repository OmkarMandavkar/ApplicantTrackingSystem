package com.dev.ATSapp.Dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import com.dev.ATSapp.Entity.JobMaster;
import com.dev.ATSapp.Entity.Recruiter;
import com.dev.ATSapp.Enums.InterviewRounds;
import com.dev.ATSapp.Enums.RoundStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JobRoundRequest {

	private Integer roundId;

	private JobMaster jobMaster;

	private Integer roundNumber;

	private Recruiter recruiter;

	private Integer score;

	private String feedback;

	private String roundType;

	private LocalDate roundDate;

	private LocalTime roundTime;

	private LocalDateTime lastUpdatedDate;

	private InterviewRounds interviewRounds;

	private RoundStatus roundStatus;

	private LocalDate offerDate;

	private LocalDate acceptDate;

	private LocalDate joiningDate;
}