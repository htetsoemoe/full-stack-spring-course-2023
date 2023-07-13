package com.jdc.spring.javaconfig.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.jdc.spring.javaconfig.service.dto.AddressDto;
import com.jdc.spring.javaconfig.service.repo.AddressRepo;
import com.jdc.spring.javaconfig.service.repo.CustomerRepo;

@Service
@Transactional(readOnly = true)
public class AddressService {
	
	@Autowired
	private AddressRepo addressRepo;
	@Autowired
	private CustomerRepo customerRepo;
	
	public List<AddressDto> findOwnAddress() {
		// get current login user-name from securityContext
		var username = SecurityContextHolder.getContext().getAuthentication().getName();
		
		if (StringUtils.hasLength(username)) {
			return addressRepo.streamByCustomerEmail(username)
					.map(AddressDto::new).toList();
		}
		
		return List.of();
	}
	
	// for address edit view (if addressDto with id)
	public AddressDto findById(int id) {
		return addressRepo.findById(id).map(AddressDto::new).orElseThrow();
	}
	
	@Transactional
	public void save(AddressDto form ) {
		// if AddressDto form is new
		if (form.getId() == null) {
			var username = SecurityContextHolder.getContext().getAuthentication().getName();
			// create new Address
			var entity = form.entity();
			
			// set Customer to Address entity
			entity.setCustomer(customerRepo.findById(username).orElseThrow());
			
			addressRepo.save(entity);
		} else {
			// if Address in managed state its setter methods are update functions
			addressRepo.findById(form.getId()).ifPresent(entity -> {
				entity.setName(form.getName());
				entity.setBuilding(form.getBuilding());
				entity.setStreet(form.getStreet());
				entity.setTownship(form.getTownship());
				entity.setDivision(form.getDivision());
			});
		}
	}

}
