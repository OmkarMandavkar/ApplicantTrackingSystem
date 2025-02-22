package com.dev.ATSapp.Entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
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
public class Recruiter {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer recruiterId;
	
	private String recruiterFullname;
	
	private String recruiterEmail;
	
	private Long recruiterMobile;
	
	private String recruiterPassword;
	
	@ManyToOne
	@JoinColumn(name="companyId")
	private Company company;
	
}
