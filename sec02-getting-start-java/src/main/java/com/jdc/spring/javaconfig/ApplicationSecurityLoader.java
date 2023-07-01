package com.jdc.spring.javaconfig;

import java.util.EnumSet;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

import jakarta.servlet.DispatcherType;

// Registers the DelegatingFilterProxy to use the springSecurityFilterChain before any other registered Filter
public class ApplicationSecurityLoader extends AbstractSecurityWebApplicationInitializer{

	@Override
	protected EnumSet<DispatcherType> getSecurityDispatcherTypes() {
		return EnumSet.of(DispatcherType.ASYNC, DispatcherType.REQUEST);
	}
}
