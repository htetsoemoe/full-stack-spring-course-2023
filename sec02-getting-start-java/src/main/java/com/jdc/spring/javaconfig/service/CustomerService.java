package com.jdc.spring.javaconfig.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jdc.spring.javaconfig.service.entity.Customer;
import com.jdc.spring.javaconfig.service.repo.CustomerRepo;

@Service
@Transactional(readOnly = true)
public class CustomerService {
	
	@Autowired
	private CustomerRepo repo;
	
	public Optional<Customer> findByEmail(String email) {
		return repo.findById(email);
	}

}