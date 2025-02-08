package com.dev.ATSapp.Entity;

import com.dev.ATSapp.Enums.SelectionStatus;

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
public class InterviewResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer resultId;

    @ManyToOne
    @JoinColumn(name = "candidateId", nullable = false)
    private Candidate candidate;

    @ManyToOne
    @JoinColumn(name = "jobId", nullable = false)
    private JobProfile jobProfile;

    @ManyToOne
    @JoinColumn(name = "companyId", nullable = false)
    private Company company;

    private Integer round1Score;

    private Integer round2Score;

    private Integer round3Score;

    private Integer round4Score;

    private Integer averageScore;

    @Enumerated(EnumType.STRING)
    private SelectionStatus selectionStatus;  

    private String feedback;

    @Column(nullable = false)
    private String recruiterName;

    @Column(nullable = false)
    private String recruiterEmail;

    @Column(nullable = false)
    private String recruiterMobile;
}
