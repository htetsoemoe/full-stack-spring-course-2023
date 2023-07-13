package com.jdc.spring.javaconfig.service.repo;

import java.util.stream.Stream;

import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

import com.jdc.spring.javaconfig.service.entity.Address;

public interface AddressRepo extends JpaRepositoryImplementation<Address, Integer>{
	Stream<Address> streamByCustomerEmail(String email);
}
