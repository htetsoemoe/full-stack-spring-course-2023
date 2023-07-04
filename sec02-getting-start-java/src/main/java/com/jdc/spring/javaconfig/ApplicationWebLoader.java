package com.jdc.spring.javaconfig;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * 
 * WebApplicationInitializer to register a DispatcherServlet and use Java-based Spring configuration. 

	Implementations are required to implement: 
		•getRootConfigClasses() -- for "root" application context (non-webinfrastructure) configuration. 
		•getServletConfigClasses() -- for DispatcherServletapplication context (Spring MVC infrastructure) configuration. 
 *
 */

public class ApplicationWebLoader extends AbstractAnnotationConfigDispatcherServletInitializer{

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class<?>[] {};
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class<?>[] {
			WebMvcConfiguration.class, DatabaseConfiguration.class ,WebSecurityConfiguration.class
		};
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] {"/"};
	}

}
