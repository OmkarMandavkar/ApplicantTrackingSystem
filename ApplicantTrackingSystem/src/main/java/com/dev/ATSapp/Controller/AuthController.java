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
import com.dev.ATSapp.Entity.Company;
import com.dev.ATSapp.Entity.Recruiter;
import com.dev.ATSapp.Entity.User;
import com.dev.ATSapp.Enums.Role;
import com.dev.ATSapp.Service.AuthService;
import com.dev.ATSapp.Service.CandidateService;
import com.dev.ATSapp.Service.CompanyService;
import com.dev.ATSapp.Service.JobDescriptionService;
import com.dev.ATSapp.Service.RecruiterService;
import com.dev.ATSapp.Service.SuperAdminService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/auth")
public class AuthController {

	private AuthService authService;
	private CandidateService candidateService;
	private CompanyService companyService;
	private JobDescriptionService jobDescriptionService;
	private SuperAdminService superAdminService;
	private RecruiterService recruiterService;

	public AuthController(AuthService authService, CandidateService candidateService, CompanyService companyService,
			JobDescriptionService jobDescriptionService, SuperAdminService superAdminService,
			RecruiterService recruiterService) {
		this.authService = authService;
		this.candidateService = candidateService;
		this.companyService = companyService;
		this.jobDescriptionService = jobDescriptionService;
		this.superAdminService = superAdminService;
		this.recruiterService = recruiterService;
	}

	@GetMapping({ "/", "/register/user" })
	public String showRegisterUserPage() {
		return "authUserViews/authRegisterUser";
	}

	@PostMapping("/register/user")
	public ModelAndView registerUserPage(UserRequest userRequest) {

		boolean userData = authService.registerUser(userRequest);

		if (userData) {
			return new ModelAndView("redirect:/auth/login");
		} else {
			ModelAndView mv = new ModelAndView("authUser/authRegisterUser");
			mv.addObject("message", "User with email already exits");
			return mv;
		}
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
//		Optional<Recruiter> recruiterData = authService.recruiterLogin(email, password);

		if (userData.isPresent() && userData.get().getPassword().equals(password)) {

//			session.setAttribute("userEmail", email);

			if (userData.get().getRole() == Role.SUPER_ADMIN) {
				return new ModelAndView("redirect:/dashboard/superAdmin");
			}

			else if (userData.get().getRole() == Role.CANDIDATE) {
				Optional<User> candidateData = authService.findByEmail(userData.get().getEmail());
				session.setAttribute("candidateId", candidateData.get().getUserId());
				session.setAttribute("candidateEmail", candidateData.get().getEmail());
				session.setAttribute("candidateProfile", candidateData.get().getImageUrl());
				return new ModelAndView("redirect:/dashboard/candidate");
			}

			else if (userData.get().getRole() == Role.COMPANY_ADMIN) {
				Optional<Company> companyAdminData = companyService.findByEmail(userData.get().getEmail());
				session.setAttribute("companyName", companyAdminData.get().getCname());
				session.setAttribute("companyId", companyAdminData.get().getCompanyId());
				session.setAttribute("companyLogo", companyAdminData.get().getImageLink());
				return new ModelAndView("redirect:/dashboard/company");
			}

			else if (userData.get().getRole() == Role.RECRUITER) {
				Optional<Recruiter> recruiterLoginData = recruiterService.findByEmail(userData.get().getEmail());
				session.setAttribute("recruiterName", recruiterLoginData.get().getRecruiterFullname());
				session.setAttribute("recruiterEmail", recruiterLoginData.get().getRecruiterEmail());
				return new ModelAndView("redirect:/dashboard/recruiter");
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