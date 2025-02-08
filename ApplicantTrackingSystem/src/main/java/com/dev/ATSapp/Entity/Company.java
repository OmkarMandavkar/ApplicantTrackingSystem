package com.dev.ATSapp.Entity;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer companyId;

    @Column(nullable = false)
    private String cname;

    private String description;

    private String address;

    private String natureOfBusiness;

    private String employeeCount;

    private String websiteLink;

    private String companyAuthCode;
    
    private String companyAdminName;

    private String companyAdminPassword;
    
    @Column(unique = true)
    private String companyAdminEmail;

    private Long companyAdminMobile;    

    
    @OneToMany(mappedBy = "company")
    private List<JobProfile> jobProfiles;

    @OneToMany(mappedBy = "company")
    private List<InterviewResult> interviewResults;

    @CreationTimestamp
    private LocalDate createdDate;

    @UpdateTimestamp
    private LocalDate updatedDate;
}