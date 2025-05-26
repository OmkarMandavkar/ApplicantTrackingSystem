package com.dev.ATSapp.Controller;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.dev.ATSapp.Dto.CompanyRequest;
import com.dev.ATSapp.Dto.UserRequest;
import com.dev.ATSapp.Entity.Candidate;
import com.dev.ATSapp.Entity.Company;
import com.dev.ATSapp.Entity.Recruiter;
import com.dev.ATSapp.Entity.User;
import com.dev.ATSapp.Enums.Role;
import com.dev.ATSapp.Service.AuthService;
import com.dev.ATSapp.Service.CandidateService;
import com.dev.ATSapp.Service.CompanyService;
import com.dev.ATSapp.Service.RecruiterService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;
	private final CandidateService candidateService;
	private final CompanyService companyService;
	private final RecruiterService recruiterService;
	
	@GetMapping({ "/", "/register/user" })
	public String showRegisterUserPage() {
		return "authUserViews/authRegisterUser";
	}

	@PostMapping("/register/user")
	public ModelAndView registerUserPage(UserRequest userRequest) {
	    authService.registerUser(userRequest);
	    return new ModelAndView("redirect:/auth/login");	    
	}

	@GetMapping("/register/company")
	public String showRegisterCompanyPage() {
		return "authUserViews/authRegisterCompany";
	}

	@PostMapping("/register/company")
	public ModelAndView RegisterCompanyPage(CompanyRequest companyRequest) {

		boolean companyData = authService.registerCompany(companyRequest);

		if (companyData) {
			return new ModelAndView("redirect:/auth/login");
		} else {
			ModelAndView mv = new ModelAndView("authUserViews/authRegisterCompany");
			mv.addObject("message", "Company / Admin with email already exits");
			return mv;
		}
	}

	@GetMapping("/login")
	public String showLoginPage() {
		return "authUserViews/authLogin";
	}

	@PostMapping("/login")
	public ModelAndView loginPage(@RequestParam String email, @RequestParam String password, HttpSession session) {

		Optional<User> userData = authService.login(email, password);
		
		if (userData.isPresent() && userData.get().getPassword().equals(password)) {
			session.setAttribute("userEmail", email);

			Role userRole = userData.get().getRole();
			if (userRole == Role.SUPER_ADMIN) {
				return new ModelAndView("redirect:/dashboard/superAdmin");
			}

			else if (userRole == Role.CANDIDATE) {
				Optional<Candidate> candidateData = candidateService.findByEmail(userData.get().getEmail());

				if (candidateData.isPresent()) {
					Candidate candidate = candidateData.get();
					session.setAttribute("candidateId", candidate.getCandidateId());
					session.setAttribute("candidateEmail", candidate.getEmail());
					session.setAttribute("candidateName", candidate.getUser().getFirstName() + " " + candidate.getUser().getLastName());
					session.setAttribute("candidateProfile", candidate.getUser().getProfileUrl());
					return new ModelAndView("redirect:/dashboard/candidate");
				} else {
					ModelAndView mv = new ModelAndView("authUserViews/authLogin");
					mv.addObject("message", "Candidate profile not found.");
					return mv;
				}
			}

			else if (userRole == Role.COMPANY_ADMIN) {
				Optional<Company> companyAdminData = companyService.findByEmail(userData.get().getEmail());

				if (companyAdminData.isPresent()) {
					Company company = companyAdminData.get();
					session.setAttribute("companyId", company.getCompanyId());
					session.setAttribute("companyName", company.getCompanyName());
					session.setAttribute("companyEmail", company.getCompanyAdminEmail());
					session.setAttribute("companyLogo", company.getLogoUrl());
					return new ModelAndView("redirect:/dashboard/company");
				} else {
					ModelAndView mv = new ModelAndView("authUserViews/authLogin");
					mv.addObject("message", "Company Admin profile not found.");
					return mv;
				}
			}

			else if (userRole == Role.RECRUITER) {
				Optional<Recruiter> recruiterData = recruiterService.findByEmail(userData.get().getEmail());

				if (recruiterData.isPresent()) {
					Recruiter recruiter = recruiterData.get();
					session.setAttribute("recruiterId", recruiter.getRecruiterId());
					session.setAttribute("recruiterName", recruiter.getRecruiterFullname());
					session.setAttribute("recruiterEmail", recruiter.getRecruiterEmail());
					return new ModelAndView("redirect:/recruiter/scheduleInterview");
				} else {
					ModelAndView mv = new ModelAndView("authUserViews/authLogin");
					mv.addObject("message", "Recruiter profile not found.");
					return mv;
				}
			}
			return null;
		} else {
			ModelAndView mv = new ModelAndView("authUserViews/authLogin");
			mv.addObject("message", "Invalid Credentials");
			return mv;
		}
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/auth/login";
	}
}