package com.jdc.demo.service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class Address {
	
	private int id;
	private String email;
	
	@NotBlank(message = "Please enter name.")
	private String name;
	
	@NotBlank(message = "Please enter building name.")
	private String building;
	
	@NotBlank(message = "Please enter street name.")
	private String street;
	
	@NotBlank(message = "Please enter township name.")
	private String township;
	
	@NotBlank(message = "Please enter division name.")
	private String division;

}
