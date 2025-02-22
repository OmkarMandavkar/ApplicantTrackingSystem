package com.dev.ATSapp.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.dev.ATSapp.Dto.CompanyRequest;
import com.dev.ATSapp.Dto.UserRequest;
import com.dev.ATSapp.Entity.Company;
import com.dev.ATSapp.Entity.Recruiter;
import com.dev.ATSapp.Entity.User;
import com.dev.ATSapp.Enums.Role;
import com.dev.ATSapp.Enums.Status;
import com.dev.ATSapp.Repository.CompanyRepository;
import com.dev.ATSapp.Repository.RecruiterRepository;
import com.dev.ATSapp.Repository.UserRepository;

@Service
public class AuthService {

	private UserRepository userRepository;
	private CompanyRepository companyRepository;
	private RecruiterRepository recruiterRepository;

	public AuthService(UserRepository userRepository, CompanyRepository companyRepository,
			RecruiterRepository recruiterRepository) {
		this.userRepository = userRepository;
		this.companyRepository = companyRepository;
		this.recruiterRepository = recruiterRepository;
	}

	public boolean registerUser(UserRequest userRequest) {

		Optional<User> userData = userRepository.findByEmail(userRequest.getEmail());

		if (userData.isPresent()) {
			return false;
		}
		User user = new User();

		user.setFirstname(userRequest.getFirstname());
		user.setLastname(userRequest.getLastname());
		user.setEmail(userRequest.getEmail());
		user.setPassword(userRequest.getPassword());
		user.setMobile(userRequest.getMobile());
		user.setDob(userRequest.getDob());
		user.setRole(userRequest.getRole());
		user.setCity(userRequest.getCity());
		user.setCountry(userRequest.getCountry());
		user.setPincode(userRequest.getPincode());

		if (userRequest.getRole() == Role.SUPER_ADMIN) {
			user.setRoleTypeId(1);
		} else if (userRequest.getRole() == Role.CANDIDATE) {
			user.setRoleTypeId(2);
		} else if (userRequest.getRole() == Role.COMPANY_ADMIN) {
			user.setRoleTypeId(3);
		} else if (userRequest.getRole() == Role.RECRUITER) {
			user.setRoleTypeId(4);
		}

		LocalDate dob = userRequest.getDob();
		int age = calculateAge(dob);
		user.setAge(age);

		userRepository.save(user);
		return true;
	}

	private int calculateAge(LocalDate dob) {
		return Period.between(dob, LocalDate.now()).getYears();
	}

	public boolean registerCompany(CompanyRequest companyRequest) {
		Optional<Company> companyData = companyRepository
				.findByCompanyAdminEmail(companyRequest.getCompanyAdminEmail());

		Optional<User> userData = userRepository.findByEmail(companyRequest.getCompanyAdminEmail());

		if (companyData.isPresent() || userData.isPresent()) {
			return false;
		}

		Company company = new Company();
		company.setCname(companyRequest.getCname());
		company.setDescription(companyRequest.getDescription());
		company.setAddress(companyRequest.getAddress());
		company.setNatureOfBusiness(companyRequest.getNatureOfBusiness());
		company.setEmployeeCount(companyRequest.getEmployeeCount());
		company.setWebsiteLink(companyRequest.getWebsiteLink());
		company.setCompanyAdminFirstName(companyRequest.getCompanyAdminFirstName());
		company.setCompanyAdminLastName(companyRequest.getCompanyAdminLastName());
		company.setCompanyAdminEmail(companyRequest.getCompanyAdminEmail());
		company.setCompanyAdminPassword(companyRequest.getCompanyAdminPassword());
		company.setCompanyAdminMobile(companyRequest.getCompanyAdminMobile());
		company.setCompanyAuthCode(companyAuthCodeGenerator());

		companyRepository.save(company);

		User user = new User();
		user.setFirstname(companyRequest.getCompanyAdminFirstName());
		user.setLastname(companyRequest.getCompanyAdminLastName());
		user.setEmail(companyRequest.getCompanyAdminEmail());
		user.setPassword(companyRequest.getCompanyAdminPassword());
		user.setMobile(companyRequest.getCompanyAdminMobile());
		user.setRole(Role.COMPANY_ADMIN);
		user.setRoleTypeId(3);
		user.setCompany(company);
		userRepository.save(user);

		return true;

	}

	public String companyAuthCodeGenerator() {

		String alphabets = "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String authCode = "";

		Random r = new Random();
		while (authCode.length() < 8) {
			int random = r.nextInt(alphabets.length());
			char ch = alphabets.charAt(random);
			authCode = authCode + ch;
		}
		return authCode;
	}

	public Optional<User> login(String email, String password) {

		Optional<User> userData = userRepository.findByEmail(email);

		if (userData.isPresent()) {
			User user = userData.get();
			user.setStatus(Status.ACTIVE);
			userRepository.save(user);
		}
		return userData;
	}

//	public Optional<User> findByCompanyAdminEmail(String superAdminEmail) {
//		return userRepository.findByEmail(superAdminEmail);
//	}

	public User saveUser(User user) {
		return userRepository.save(user);
	}

	public Optional<User> findByEmail(String superAdminEmail) {
		return userRepository.findByEmail(superAdminEmail);
	}

	public Optional<Recruiter> recruiterLogin(String email, String password) {

		Optional<Recruiter> recruiterData = recruiterRepository.findByRecruiterEmail(email);

		if (recruiterData.isPresent()) {
			Recruiter recruiter = recruiterData.get();
		}
		return recruiterData;
	}
}