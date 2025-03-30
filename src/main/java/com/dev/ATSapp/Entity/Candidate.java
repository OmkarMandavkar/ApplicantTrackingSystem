package com.dev.ATSapp.Entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
    
    @OneToOne
    @JoinColumn(name = "userId")
    private User user;

    @OneToMany(mappedBy = "candidate")
    private List<JobMaster> jobMasters;

    @OneToMany(mappedBy = "candidate")
    private List<SavedJobs> savedJobs;
    
    private String email;

	private String password;

    private String currentDesignation;

    private String salaryExpectations;
    
	@Lob
	@Column(columnDefinition = "MEDIUMTEXT")
    private String qualification;
    
	@Lob
	@Column(columnDefinition = "MEDIUMTEXT")
    private String workExperience;

	@Lob
	@Column(columnDefinition = "MEDIUMTEXT")
    private String project1;
	
	@Lob
	@Column(columnDefinition = "MEDIUMTEXT")
    private String project2;
	
	@Lob
	@Column(columnDefinition = "MEDIUMTEXT")
    private String project3;
	
	@Lob
	@Column(columnDefinition = "MEDIUMTEXT")
    private String project4;
	
    private String location;

	@Lob
	@Column(columnDefinition = "MEDIUMTEXT")
    private String skills;
}