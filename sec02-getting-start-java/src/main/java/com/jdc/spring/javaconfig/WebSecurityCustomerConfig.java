package com.jdc.spring.javaconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.InMemoryTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;

import com.jdc.spring.javaconfig.service.security.CustomerUserDetailsService;

@Configuration
public class WebSecurityCustomerConfig {

	@Bean
	SecurityFilterChain httpFilter(HttpSecurity http, PersistentTokenBasedRememberMeServices rememberMeServices) throws Exception {

		http.authorizeHttpRequests(request -> {
				request.requestMatchers("/authentication", "/signup").permitAll();
				request.requestMatchers("/customer/**").hasAuthority("Customer");
				request.anyRequest().denyAll();
			});

		// Form Login with Authentication
		http.formLogin(form -> {
			form.loginPage("/authentication"); // loginPage => the login page to redirect to if authentication is required (i.e."/login")
			form.defaultSuccessUrl("/customer", true);
		});
		
		// Remember Me with specific UserDetailsService
		/*
		 http.rememberMe(rememberMe -> {
			rememberMe.userDetailsService(customerUserDetailsService);
		 });
		 */
		
		// Remember Me with PersistentTokenBasedRememberMeServices (InMemory)
		http.rememberMe(rememberMe -> {
			rememberMe.rememberMeServices(rememberMeServices);
		});
		

		// Logout with Default
		//http.logout(Customizer.withDefaults());
		
		// logout with logout success-url
		http.logout(logout -> logout.logoutSuccessUrl("/"));

		return http.build();
	}
	
	@Bean
	PersistentTokenBasedRememberMeServices rememberMeServices(CustomerUserDetailsService customerUserDetailsService) {
		return new PersistentTokenBasedRememberMeServices("SGVsbG8gSmF2YQ==", customerUserDetailsService, new InMemoryTokenRepositoryImpl());
	}
	
	@Bean
	SecurityContextRepository securityContextRepository() {
		return new HttpSessionSecurityContextRepository();
	}

	@Bean
	AuthenticationManager configure(HttpSecurity http, PasswordEncoder passwordEncoder, CustomerUserDetailsService customerUserDetailsService) throws Exception {

		var builder = http.getSharedObject(AuthenticationManagerBuilder.class);

		// Add Authentication Provider with custom user details service
		builder.authenticationProvider(getCustomerProvider(passwordEncoder, customerUserDetailsService));

		return builder.build();
	}


	private AuthenticationProvider getCustomerProvider(PasswordEncoder passwordEncoder, CustomerUserDetailsService customerUserDetailsService) {

		var provider = new DaoAuthenticationProvider();
		provider.setPasswordEncoder(passwordEncoder);
		provider.setUserDetailsService(customerUserDetailsService);

		return provider;
	}

}
