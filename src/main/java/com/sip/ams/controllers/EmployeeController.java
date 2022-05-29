package com.sip.ams.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeController {
	@GetMapping({ "/greeting" })
	public String welcomePage() {
		return "Welcome!";
	}
}
