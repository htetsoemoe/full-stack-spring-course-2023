package com.jdc.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration {
	
	@Bean
	UserDetailsService userDetailsService() {
		return new InMemoryUserDetailsManager(
				User.builder().username("kodemo").password("{noop}kodemo").authorities("Member").build()) ;
	}

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(request -> {
			request.requestMatchers("/", "/home").permitAll();
			request.requestMatchers("/member/**").hasAuthority("Member");
			request.anyRequest().fullyAuthenticated();
		});
		
		http.formLogin(config -> {});
		
		return http.build();
	}
}
