package com.dev.ATSapp.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.dev.ATSapp.Dto.CompanyRequest;
import com.dev.ATSapp.Dto.UserRequest;
import com.dev.ATSapp.Entity.Candidate;
import com.dev.ATSapp.Entity.Company;
import com.dev.ATSapp.Entity.Recruiter;
import com.dev.ATSapp.Entity.User;
import com.dev.ATSapp.Enums.Role;
import com.dev.ATSapp.Enums.Status;
import com.dev.ATSapp.Repository.CandidateRepository;
import com.dev.ATSapp.Repository.CompanyRepository;
import com.dev.ATSapp.Repository.RecruiterRepository;
import com.dev.ATSapp.Repository.UserRepository;
import com.dev.ExceptionHandling.UserAlreadyRegisteredException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService{

	private final UserRepository userRepository;
	private final CompanyRepository companyRepository;
	private final RecruiterRepository recruiterRepository;
	private final CandidateRepository candidateRepository;
	    
	public void registerUser(UserRequest userRequest) {

		Optional<User> userData = userRepository.findByEmail(userRequest.getEmail());

	    if (userData.isPresent()) {
	        throw new UserAlreadyRegisteredException("User with this email already exists.");
	    }
	    
		User user = new User();
		user.setFirstName(userRequest.getFirstName().trim());
		user.setLastName(userRequest.getLastName().trim());
		user.setEmail(userRequest.getEmail().trim());
		user.setPassword(userRequest.getPassword().trim());
		user.setMobile(userRequest.getMobile());
		user.setDob(userRequest.getDob());
		user.setRole(userRequest.getRole());
		user.setCity(userRequest.getCity().trim());
		user.setCountry(userRequest.getCountry().trim());
		user.setPincode(userRequest.getPincode());

		LocalDate dob = userRequest.getDob();
		int age = calculateAge(dob);
		user.setAge(age);

		userRepository.save(user);

		if (userRequest.getRole() == Role.CANDIDATE) {
			Candidate candidate = new Candidate();
			candidate.setUser(user);
			candidate.setEmail(userRequest.getEmail().trim());
			candidate.setPassword(userRequest.getPassword().trim());
			candidate.setLocation(userRequest.getCity().trim() + ", " + userRequest.getCountry().trim() + "- " + userRequest.getPincode());
			candidateRepository.save(candidate);
		}
	}

	private int calculateAge(LocalDate dob) {
		return Period.between(dob, LocalDate.now()).getYears();
	}

	public boolean registerCompany(CompanyRequest companyRequest) {
		Optional<Company> companyData = companyRepository.findByCompanyAdminEmail(companyRequest.getCompanyAdminEmail());

		Optional<User> userData = userRepository.findByEmail(companyRequest.getCompanyAdminEmail());

		if (companyData.isPresent() || userData.isPresent()) {
			return false;
		}

		Company company = new Company();
		company.setCompanyName(companyRequest.getCompanyName().trim());
		company.setDescription(companyRequest.getDescription().trim());
		company.setAddress(companyRequest.getAddress().trim());
		company.setNatureOfBusiness(companyRequest.getNatureOfBusiness().trim());
		company.setEmployeeCount(companyRequest.getEmployeeCount());
		company.setWebsiteLink(companyRequest.getWebsiteLink().trim());
		company.setCompanyAdminFirstName(companyRequest.getCompanyAdminFirstName().trim());
		company.setCompanyAdminLastName(companyRequest.getCompanyAdminLastName().trim());
		company.setCompanyAdminEmail(companyRequest.getCompanyAdminEmail().trim());
		company.setCompanyAdminPassword(companyRequest.getCompanyAdminPassword().trim());
		company.setCompanyAdminMobile(companyRequest.getCompanyAdminMobile());
		company.setCompanyAuthCode(companyAuthCodeGenerator());
		companyRepository.save(company);

		User user = new User();
		user.setFirstName(companyRequest.getCompanyAdminFirstName().trim());
		user.setLastName(companyRequest.getCompanyAdminLastName().trim());
		user.setEmail(companyRequest.getCompanyAdminEmail().trim());
		user.setPassword(companyRequest.getCompanyAdminPassword().trim());
		user.setMobile(companyRequest.getCompanyAdminMobile());
		user.setRole(Role.COMPANY_ADMIN);
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

//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        User user = userRepository.findByEmail(email)
//            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
//
//        return new org.springframework.security.core.userdetails.User(
//            user.getEmail(),
//            user.getPassword(),
//            List.of(new SimpleGrantedAuthority(user.getRole().name()))
//        );
//    }
	
	public Optional<User> login(String email, String password) {

		Optional<User> userData = userRepository.findByEmail(email);

		if (userData.isPresent()) {
			User user = userData.get();
			user.setStatus(Status.ACTIVE);
			userRepository.save(user);
		}
		return userData;
	}

	public User saveUser(User user) {
		return userRepository.save(user);
	}

	public Optional<User> findByEmail(String superAdminEmail) {
		return userRepository.findByEmail(superAdminEmail);
	}

	public Optional<Recruiter> recruiterLogin(String email, String password) {

		Optional<Recruiter> recruiterData = recruiterRepository.findByRecruiterEmail(email);

//		if (recruiterData.isPresent()) {
//			Recruiter recruiter = recruiterData.get();
//		}
		return recruiterData;
	}
}