package com.dev.ATSapp.Entity;

import java.time.LocalDate;
import java.util.List;

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
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer userId;

	private String firstname;

	private String lastname;

	@Column(unique = true)
	private String email;

	private String password;

	private Long mobile;

	private LocalDate dob;

	private Integer age;

	private String city;

	private String country;

	private Long pincode;

	private String ImageUrl;
	
	@Enumerated(EnumType.STRING)
	private Role role;

	private Integer roleTypeId;

	@Enumerated(EnumType.STRING)
	private Status status = Status.INACTIVE;

	@OneToMany(mappedBy = "user")
	private List<Candidate> candidates;
	
	@ManyToOne
	@JoinColumn(name = "company_id", nullable = true)
	private Company company;

	@CreationTimestamp
	private LocalDate createdDate;

	@UpdateTimestamp
	private LocalDate updatedDate;
}