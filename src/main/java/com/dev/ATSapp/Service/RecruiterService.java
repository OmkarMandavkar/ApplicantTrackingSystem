package com.dev.ATSapp.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.dev.ATSapp.Dto.RecruiterRequest;
import com.dev.ATSapp.Entity.Company;
import com.dev.ATSapp.Entity.Recruiter;
import com.dev.ATSapp.Entity.User;
import com.dev.ATSapp.Enums.Role;
import com.dev.ATSapp.Repository.CompanyRepository;
import com.dev.ATSapp.Repository.RecruiterRepository;
import com.dev.ATSapp.Repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecruiterService {

	private final RecruiterRepository recruiterRepository;
	private final CompanyRepository companyRepository;
	private final UserRepository userRepository;

	public boolean addRecruiter(RecruiterRequest recruiterRequest, String companyAdminEmail) {
	    Optional<Company> companyData = companyRepository.findByCompanyAdminEmail(companyAdminEmail);
	    
	    if (!companyData.isPresent()) {
	        throw new IllegalArgumentException("Company Admin with email " + companyAdminEmail + " not found");
	    }

	    Optional<Recruiter> recruiterData = recruiterRepository.findByRecruiterEmail(recruiterRequest.getRecruiterEmail());

	    if (recruiterData.isPresent()) {
	        return false;
	    }

	    String generatedPassword = passwordGenerator();
	    
	    Recruiter recruiter = new Recruiter();
	    recruiter.setRecruiterEmail(recruiterRequest.getRecruiterEmail());
	    recruiter.setRecruiterFullname(recruiterRequest.getRecruiterFullname()); 
	    recruiter.setRecruiterMobile(recruiterRequest.getRecruiterMobile());
	    recruiter.setRecruiterPassword(generatedPassword);
	    recruiter.setCompany(companyData.get());
	    recruiterRepository.save(recruiter);
	    
	    User user = new User();
	    user.setEmail(recruiterRequest.getRecruiterEmail());
	    user.setFirstName(recruiterRequest.getRecruiterFullname());
	    user.setMobile(recruiterRequest.getRecruiterMobile());
	    user.setPassword(generatedPassword);
	    user.setRole(Role.RECRUITER);
	    user.setCompany(companyData.get());
	    userRepository.save(user);
	    
	    return true;
	}

	public String passwordGenerator() {

		String alphabets = "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String authCode = "";

		Random r = new Random();
		while (authCode.length() < 6) {
			int random = r.nextInt(alphabets.length());
			char ch = alphabets.charAt(random);
			authCode = authCode + ch;
		}
		return authCode;
	}

	public List<Recruiter> fetchAllRecruiters(Integer companyId) {
		return recruiterRepository.findByCompanyId(companyId);
	}

	public Optional<Recruiter> findByEmail(String email) {
		return recruiterRepository.findByRecruiterEmail(email);
	}

	public Optional<Recruiter> findByRecruiterEmail(String recruiterEmail) {
		return recruiterRepository.findByRecruiterEmail(recruiterEmail);
	}

	public Recruiter saveRecruiterData(Recruiter recruiter) {
		return recruiterRepository.save(recruiter);
	}
}
