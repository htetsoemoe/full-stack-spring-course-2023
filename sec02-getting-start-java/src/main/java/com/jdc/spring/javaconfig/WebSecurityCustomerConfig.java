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

import com.jdc.spring.javaconfig.service.security.CustomerUserDetailsService;

@Configuration
public class WebSecurityCustomerConfig {

	@Bean
	SecurityFilterChain httpFilter(HttpSecurity http) throws Exception {

		http.authorizeHttpRequests(request -> {
				request.requestMatchers("/authentication").permitAll();
				request.requestMatchers("/customer/**").hasAuthority("Customer");
				request.anyRequest().denyAll();
			});

		http.formLogin(form -> {
			form.loginPage("/authentication"); // loginPage => the login page to redirect to if authentication is required (i.e."/login")
			form.defaultSuccessUrl("/customer", true);
		});

		http.logout(Customizer.withDefaults());

		return http.build();
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
