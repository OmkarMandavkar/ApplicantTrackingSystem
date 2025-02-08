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
	
	private String fullname;

	private String email;

	private String password;

	private LocalDate dob;

	private Long mobile;
	
	private Integer age;

	private Role role;

	private Integer roleId;

	private Status status;
}
