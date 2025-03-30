package com.dev.ATSapp.Entity;

import com.dev.ATSapp.Enums.InterviewRounds;

import jakarta.persistence.Column;
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
public class RoundDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer interviewId;

	@ManyToOne
	@JoinColumn(name = "jobId", nullable = false)
	private JobDescription jobDescription;
	
	private Integer RoundNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private InterviewRounds roundType;
}