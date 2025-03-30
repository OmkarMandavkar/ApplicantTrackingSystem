package com.dev.ATSapp.Dto;

import java.time.LocalDate;

import com.dev.ATSapp.Enums.Role;
import com.dev.ATSapp.Enums.Status;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {

	private Integer userId;

	private String firstName;

	private String lastName;

	private String email;

	private String password;

	private Long mobile;

	private LocalDate dob;

	private Integer age;

	private String city;

	private String country;

	private Long pincode;

	private String profileUrl;
	
	private Role role;

	private Status status;

	private LocalDate createdDate;

	private LocalDate updatedDate;
}
