package com.jdc.spring.javaconfig.service.entity;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Customer {
	
	@Id
	private String email;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private String password;
	
	private String phone;
	private boolean activated;
	private boolean locked;
	private Date validpass;
	private Date retired;

}
