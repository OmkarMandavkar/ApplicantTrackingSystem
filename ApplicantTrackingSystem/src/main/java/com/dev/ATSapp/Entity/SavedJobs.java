package com.dev.ATSapp.Entity;

import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;

import com.dev.ATSapp.Enums.SaveStatus;

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
public class SavedJobs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer savedJobId;

    @ManyToOne
    @JoinColumn(name = "candidateId", nullable = false)
    private Candidate candidate;

    @ManyToOne
    @JoinColumn(name = "jobId", nullable = false)
    private JobDescription jobDescription;

    @CreationTimestamp
    private LocalDate savedDate;
    
    @Enumerated(EnumType.STRING)
    private SaveStatus saveStatus;
}