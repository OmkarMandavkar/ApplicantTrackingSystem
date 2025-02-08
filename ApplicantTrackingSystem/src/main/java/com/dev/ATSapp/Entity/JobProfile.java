package com.dev.ATSapp.Entity;

import java.util.List;

import com.dev.ATSapp.Enums.Status;

import jakarta.persistence.Column;
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
public class JobProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer jobId;

    @ManyToOne
    @JoinColumn(name = "companyId", nullable = false)
    private Company company;

    @Column(nullable = false)
    private String cname;

    @Column(nullable = false)
    private String position;

    @Column(nullable = false)
    private Integer vacancy;

    private String experience;

    private String qualification;

    private String jobLocation;

    private Integer noticePeriod;

    private String domain;

    @Column(nullable = false)
    private String roleAndResp;

    private String skillsRequired;

    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToMany(mappedBy = "jobProfile")
    private List<InterviewResult> interviewResults;
}