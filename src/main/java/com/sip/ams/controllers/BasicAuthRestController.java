package com.sip.ams.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
//import com.sip.ams.entities.Auth;
import com.sip.ams.entities.User;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.validation.Valid;
import com.sip.ams.entities.User;
import com.sip.ams.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@CrossOrigin(origins = "*")
@RestController
public class BasicAuthRestController {
	@Autowired
	private UserService userService;

	@GetMapping(path = "/basicauth")

	public User basicauth() {
		// return "User Amine : Role Admin";
		// return new User("ma@gmail.com","1234");
		// return "Success";
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		return user;
	}

	/*
	 * public Authentication basicauth() { return new Authentication("Spring");
	 * //return "Success"; }
	 */
}



/*package com.sip.ams.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sip.ams.entities.AuthenticationBean;

@CrossOrigin(origins = "*")
@RestController

public class BasicAuthRestController {

	@GetMapping(path = "/basicauth")
	public AuthenticationBean basicauth() {
		return new AuthenticationBean("You are authenticated");
	}
}*/
