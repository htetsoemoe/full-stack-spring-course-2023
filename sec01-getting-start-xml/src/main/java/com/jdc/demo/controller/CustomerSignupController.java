package com.jdc.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jdc.demo.service.dto.SignupForm;

@Controller
@RequestMapping("signup")
public class CustomerSignupController {
	
	@GetMapping
	String index() {
		return "signup";
	}
	
	@PostMapping
	String signup(@Validated @ModelAttribute("form") SignupForm form, BindingResult result) {
		if (result.hasErrors()) {
			return "signup";
		}
		
		return null;
	}
	
	@ModelAttribute("form")
	SignupForm form() {
		return new SignupForm();
	}
	

}
