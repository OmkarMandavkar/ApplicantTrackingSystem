package com.dev.ATSapp.Entity;

import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.dev.ATSapp.Enums.Role;
import com.dev.ATSapp.Enums.Status;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    private String fullname;

    @Column(unique = true)
    private String email;

    private String password;

    private LocalDate dob;

    private Long mobile;

    private Integer age;
    
    @Enumerated(EnumType.STRING)
    private Role role;

    private Integer roleTypeId;

    @Enumerated(EnumType.STRING)
    private Status status = Status.INACTIVE;
    
    @OneToOne(mappedBy = "user")
    private Candidate candidateProfile;

    @CreationTimestamp
    private LocalDate createdDate;

    @UpdateTimestamp
    private LocalDate updatedDate;
}