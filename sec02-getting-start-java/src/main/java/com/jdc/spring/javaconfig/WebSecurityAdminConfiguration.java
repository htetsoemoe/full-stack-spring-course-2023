package com.jdc.spring.javaconfig;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.DigestAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.DigestAuthenticationFilter;

import com.jdc.spring.javaconfig.service.security.CustomerUserDetailsService;

@Configuration
@EnableWebSecurity
public class WebSecurityAdminConfiguration {
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	SecurityFilterChain homeFilter(HttpSecurity http) throws Exception {
		http.securityMatcher("/").authorizeHttpRequests(request -> {
			request.anyRequest().permitAll();
		});

		return http.build();
	}

	@Bean
	SecurityFilterChain resourcesFilter(HttpSecurity http) throws Exception {
		http.securityMatcher("/resources/**").authorizeHttpRequests(request -> {
			request.anyRequest().permitAll();
		});

		return http.build();
	}
	
	/**
	 * HTTP Basic for Admin
	 * @throws Exception 
	 */
	@Bean
	SecurityFilterChain adminResource(HttpSecurity http, PasswordEncoder passwordEncoder) throws Exception {
		http.securityMatcher("/admin/**")
			.authorizeHttpRequests(request -> {
				request.anyRequest().hasAuthority("Admin");
			});
		
		http.httpBasic(Customizer.withDefaults());
		http.logout(Customizer.withDefaults());
		
		var authenticationManager = new ProviderManager(getAdminProvider(passwordEncoder));
		http.authenticationManager(authenticationManager);
		
		return http.build();
	}

	
	private AuthenticationProvider getAdminProvider(PasswordEncoder passwordEncoder) {
		// create UserDetailsService
		var userDetailsService = new InMemoryUserDetailsManager(List.of(User.withUsername("admin@gmail.com")
				.password("$2a$10$ricn4HuEM.hQoTASy7/fU.PWLqbV4dzmo3X2Orw0raO1.OcGoQbTe")
				.authorities("Admin")
				.build()));

		// set UserDetailsService and PasswordEncoder to AuthenticationProvider
		var provider = new DaoAuthenticationProvider(passwordEncoder);
		provider.setUserDetailsService(userDetailsService);
		
		return provider;
	}
	
}
