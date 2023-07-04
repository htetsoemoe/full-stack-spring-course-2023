package com.jdc.spring.javaconfig;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration {
	
	@Bean
	SecurityFilterChain homeFilter(HttpSecurity http) throws Exception {
		http.securityMatcher("/")
			.authorizeHttpRequests(request -> {
				request.anyRequest().permitAll();
			});
		
		return http.build();
	}
	
	@Bean
	SecurityFilterChain resourcesFilter(HttpSecurity http) throws Exception {
		http.securityMatcher("/resources/**")
			.authorizeHttpRequests(request -> {
				request.anyRequest().permitAll();
			});
		
		return http.build();
	}
	
	@Bean
	SecurityFilterChain httpFilter(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(request -> {
				request.requestMatchers("/admin/**").hasAuthority("Admin");
				request.requestMatchers("/member/**").hasAnyAuthority("Admin", "Member");
				request.anyRequest().denyAll();
			});
		
		http.formLogin(Customizer.withDefaults());
		http.logout(Customizer.withDefaults());
		
		return http.build();
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	AuthenticationManager configure(HttpSecurity http, PasswordEncoder passwordEncoder) throws Exception {
		// get AuthenticationManagerBuilder from HttpSecurity
		var builder = http.getSharedObject(AuthenticationManagerBuilder.class);
		
		// create UserDetailsService
		var userDetailsService = new InMemoryUserDetailsManager(List.of(
				User.withUsername("admin@gmail.com")
				.password("$2a$10$ricn4HuEM.hQoTASy7/fU.PWLqbV4dzmo3X2Orw0raO1.OcGoQbTe")
				.authorities("Admin").build()));
		
		//set UserDetailsService and PasswordEncoder to AuthenticationProvider
		var provider = new DaoAuthenticationProvider(passwordEncoder);
		provider.setUserDetailsService(userDetailsService);
		
		// set AuthenticationProvider to AuthenticationManagerBuilder
		builder.authenticationProvider(provider);
		
		return builder.build();
	}
	
//	@Bean
//	InMemoryUserDetailsManager userDetailsManager() {
//		return new InMemoryUserDetailsManager(List.of(
//				User.withUsername("admin@gmail.com").password("{noop}admin").authorities("Admin").build(),
//				User.withUsername("member@gmail.com").password("{noop}member").authorities("Member").build()
//		));
//	}
}
