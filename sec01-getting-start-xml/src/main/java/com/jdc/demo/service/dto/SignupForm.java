package com.jdc.demo.service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SignupForm {
	
	@NotBlank(message = "Please enter customer name.")
	private String name;
	
	@Email(message = "Please enter valid email.")
	@NotBlank(message = "Please enter login email.")
	private String email;
	
	@NotBlank(message = "Please enter password.")
	private String password;

}
