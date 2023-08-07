package com.jdc.spring.javaconfig.service.dto;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SignupForm {

	@NotBlank(message = "Please enter customer name.")
	private String name;
	
	@NotBlank(message = "Please enter valid email.")
	private String email;
	
	@NotBlank(message = "Please enter password.")
	private String password;
	
	
	public Authentication authentication() {
		return UsernamePasswordAuthenticationToken.unauthenticated(email, password);
	}
}
