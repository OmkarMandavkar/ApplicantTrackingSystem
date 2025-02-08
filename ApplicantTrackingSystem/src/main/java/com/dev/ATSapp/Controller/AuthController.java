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
import com.dev.ATSapp.Entity.User;
import com.dev.ATSapp.Enums.Role;
import com.dev.ATSapp.Enums.Status;
import com.dev.ATSapp.Repository.CompanyRepository;
import com.dev.ATSapp.Service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/auth")
public class AuthController {

	private UserService userService;
	private CompanyRepository companyRepository;

	public AuthController(UserService userService, CompanyRepository companyRepository) {
		this.userService = userService;
		this.companyRepository = companyRepository;
	}

	@GetMapping({ "/", "/register/user" })
	public String showRegisterUserPage() {
		return "authRegisterUser";
	}

	@PostMapping("/register/user")
	public ModelAndView registerUserPage(UserRequest userRequest) {

		boolean userData = userService.registerUser(userRequest);

		if (userData) {
			return new ModelAndView("redirect:/auth/login");
		} else {
			ModelAndView mv = new ModelAndView("authRegisterUser");
			mv.addObject("message", "User with email already exits");
			return mv;
		}
	}

	@GetMapping("/register/company")
	public String showRegisterCompanyPage() {
		return "authRegisterCompany";
	}

	@PostMapping("/register/company")
	public ModelAndView RegisterCompanyPage(CompanyRequest companyRequest) {

//		System.out.println("stage 1 cross");
		boolean companyData = userService.registerCompany(companyRequest);
//		System.out.println("Company Request: " + companyRequest);

		if (companyData) {
			return new ModelAndView("redirect:/auth/login");
		} else {
			ModelAndView mv = new ModelAndView("authRegisterCompany");
			mv.addObject("message", "Company already exits");
			return mv;
		}
	}

	@GetMapping("/login")
	public String showLoginPage() {
		return "authLogin";
	}

	@PostMapping("/login")
	public ModelAndView loginPage(@RequestParam String email, @RequestParam String password, HttpSession session) {

		Optional<User> userData = userService.login(email, password);

		if (userData.isPresent() && userData.get().getPassword().equals(password)) {

			session.setAttribute("userEmail", email);

			if (userData.get().getRole() == Role.SUPER_ADMIN) {
				return new ModelAndView("redirect:/dashboard/superAdmin");
			} else if (userData.get().getRole() == Role.CANDIDATE) {
				return new ModelAndView("redirect:/dashboard/candidate");
			} else if (userData.get().getRole() == Role.COMPANY_ADMIN) {
				return new ModelAndView("redirect:/dashboard/company");
			} else if (userData.get().getRole() == Role.RECRUITER) {
				return new ModelAndView("redirect:/dashboard/recruiter");
			}

			return null;

		} else {
			ModelAndView mv = new ModelAndView("authLogin");
			mv.addObject("message", "Invalid Credentials");
			return mv;
		}
	}
}