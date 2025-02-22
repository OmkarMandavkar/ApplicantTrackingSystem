package com.dev.ATSapp.Dto;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.dev.ATSapp.Enums.Role;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {

	private String firstname;
	private String lastname;
	private String email;
	private String password;
	private Long mobile;
	private LocalDate dob;
	private Integer age;
	private String city;
	private String country;
	private Long pincode;
	private Role role;
	private Integer roleTypeId;
	private LocalDate createdDate;
	private LocalDate updatedDate;
}
