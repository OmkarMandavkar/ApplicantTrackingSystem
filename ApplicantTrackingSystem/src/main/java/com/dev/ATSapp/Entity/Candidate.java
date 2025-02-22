package com.dev.ATSapp.Entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
public class Candidate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer candidateId;

    private String firstname;
    
    private String lastname;
    
    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne
    @JoinColumn(name = "companyId")
    private Company company;

    @ManyToOne
    @JoinColumn(name = "jobId")
    private JobDescription jobDescription;

    @OneToMany(mappedBy = "candidate")
    private List<JobMaster> jobMasters;

    private String email;

    private Long phone;

    private String currentDesignation;

    private Long salaryExpectations;

	@Lob
	@Column(columnDefinition = "MEDIUMTEXT")
    private String workExperience;

	@Lob
	@Column(columnDefinition = "MEDIUMTEXT")
    private String qualification;

    private String location;

	@Lob
	@Column(columnDefinition = "MEDIUMTEXT")
    private String skills;
}