package com.jdc.demo.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jdc.demo.service.CustomerService;
import com.jdc.demo.service.dto.SignupForm;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("signup")
public class CustomerSignupController {
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private SecurityContextRepository securityContextRepository;
	
	// Used in 'Redirect Save Request'
	private RequestCache requestCache = new HttpSessionRequestCache();
	
	@GetMapping
	String index() {
		return "signup";
	}
	
	@PostMapping
	String signup(@Validated @ModelAttribute("form") SignupForm form, 
			BindingResult result,
			HttpServletRequest request, HttpServletResponse response) {
		if (result.hasErrors()) {
			return "signup";
		}
		
		try {
			// Create Customer
			customerService.save(form);
			
			// Authenticate
			var authentication = authenticationManager.authenticate(form.authentication());
			
			// Set Authentication Result to Security Context
			var securityContext = SecurityContextHolder.getContext();
			securityContext.setAuthentication(authentication);
			
			// Save Security Context to Security Context Repository
			securityContextRepository.saveContext(securityContext, request, response);
			
			// Redirect to Save Request
			var redirectUrl = getSavedRequest(request, response).map(SavedRequest::getRedirectUrl)
					.orElse("/customer");
			
			return "redirect:%s".formatted(redirectUrl);
			
			
		} catch (Exception e) {
			result.rejectValue("email", "email", e.getMessage());
			return "signup";
		}

	}
	
	@ModelAttribute("form")
	SignupForm form() {
		return new SignupForm();
	}
	
	private Optional<SavedRequest> getSavedRequest(HttpServletRequest request, HttpServletResponse response) {
		var savedRequest = requestCache.getRequest(request, response);
		return Optional.ofNullable(savedRequest);
	}
}
