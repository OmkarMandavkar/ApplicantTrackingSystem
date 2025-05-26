package com.dev.ATSapp.Controller;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.dev.ATSapp.Dto.UserRequest;
import com.dev.ATSapp.Entity.User;
import com.dev.ATSapp.Service.AuthService;
import com.dev.ATSapp.Service.CandidateService;
import com.dev.ATSapp.Service.CompanyService;
import com.dev.ATSapp.Service.JobDescriptionService;
import com.dev.ATSapp.Service.SuperAdminService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/superAdmin")
public class SuperAdminController {

	private final AuthService authService;
	
	@GetMapping("/viewDetails")
	public ModelAndView viewSuperAdminDetails(HttpSession session) {

		String superAdminEmail = (String) session.getAttribute("userEmail");

		Optional<User> superAdminData = authService.findByEmail(superAdminEmail);

		ModelAndView mv = new ModelAndView("superAdminViews/SuperAdminViewDetails");
		mv.addObject("superAdminData", superAdminData.get());
		return mv;
	}

	@GetMapping("/updateCompanyAdminData")
	public ModelAndView showEditSuperAdminPersonalDetails(HttpSession session) {

		String superAdminEmail = (String) session.getAttribute("userEmail");

		Optional<User> superAdminData = authService.findByEmail(superAdminEmail);

		ModelAndView mv = new ModelAndView("superAdminViews/SuperAdminEditPersonalDetails");
		mv.addObject("superAdminData", superAdminData.get());
		return mv;
	}

	@PostMapping("/updateCompanyAdminData")
	public ModelAndView editSuperAdminPersonalDetails(HttpSession session, UserRequest userRequest) {

		String superAdminEmail = (String) session.getAttribute("userEmail");

		Optional<User> userData = authService.findByEmail(superAdminEmail);

//		System.out.println(superAdminEmail);

		if (userData.isPresent()) {
			User user = userData.get();
			if (userRequest.getFirstName() != null) {
				user.setFirstName(userRequest.getFirstName());
			}
			if (userRequest.getLastName() != null) {
				user.setLastName(userRequest.getLastName());
			}
			if (userRequest.getDob() != null) {
				user.setDob(userRequest.getDob());
			}
			if (userRequest.getMobile() != null) {
				user.setMobile(userRequest.getMobile());
			}
			if (userRequest.getCountry() != null) {
				user.setCountry(userRequest.getCountry());
			}
			if (userRequest.getCity() != null) {
				user.setCity(userRequest.getCity());
			}
			if (userRequest.getPincode() != null) {
				user.setPincode(userRequest.getPincode());
			}

			authService.saveUser(user);

			ModelAndView mv = new ModelAndView("redirect:/superAdmin/viewDetails");
			mv.addObject("message", "Data updated successfully!");
			return mv;
		} else {
			ModelAndView mv = new ModelAndView("superAdminViews/dashboardSuperAdmin");
			mv.addObject("message", "Super-Admin not found!");
			return mv;
		}
	}

}