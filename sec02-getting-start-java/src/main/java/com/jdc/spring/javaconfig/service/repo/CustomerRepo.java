package com.jdc.spring.javaconfig.service.repo;

import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

import com.jdc.spring.javaconfig.service.entity.Customer;

public interface CustomerRepo extends JpaRepositoryImplementation<Customer, String>{

}
