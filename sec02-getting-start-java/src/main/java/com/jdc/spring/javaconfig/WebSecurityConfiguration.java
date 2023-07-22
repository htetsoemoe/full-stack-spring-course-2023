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
public class WebSecurityConfiguration {

	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private CustomerUserDetailsService customerUserDetailsService;

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
	
//	@Bean
//	SecurityFilterChain httpFilter(HttpSecurity http) throws Exception {
//		http.authorizeHttpRequests(request -> {
////			request.requestMatchers("/admin/**").hasAuthority("Admin");
////			request.requestMatchers("/member/**").hasAuthority("Member");
////			request.requestMatchers("/customer/**").hasAuthority("Customer");
//			request.anyRequest().denyAll();
//		});
//
//		http.formLogin(Customizer.withDefaults());
//		http.logout(Customizer.withDefaults());
//
//		return http.build();
//	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
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

	
	// Member Login with Digest Login
	@Bean
	SecurityFilterChain memberFilter(HttpSecurity http, 
			DigestAuthenticationEntryPoint digestAuthenticationEntryPoint,
			DigestAuthenticationFilter digestAuthenticationFilter) throws Exception {
		
		http.securityMatcher("/member/**")
			.authorizeHttpRequests(request -> request.anyRequest().hasAuthority("Member"));
		
		http.exceptionHandling(exception -> {
			exception.authenticationEntryPoint(digestAuthenticationEntryPoint);
		});
		
		http.addFilterAt(digestAuthenticationFilter, DigestAuthenticationFilter.class);
		
		return http.build();
	}
	
	@Bean
	DigestAuthenticationEntryPoint digestAuthenticationEntryPoint() {
		var bean = new DigestAuthenticationEntryPoint();
		bean.setKey("MY_SECRET_KEY");
		bean.setRealmName("DIGEST_REALM");
		return bean;
	}
	
	@Bean
	DigestAuthenticationFilter digestAuthenticationFilter(
			DigestAuthenticationEntryPoint digestAuthenticationEntryPoint, 
			JdbcUserDetailsManager memberUserDetailsService) {
		
		var bean = new DigestAuthenticationFilter();
		bean.setAuthenticationEntryPoint(digestAuthenticationEntryPoint);
		bean.setUserDetailsService(memberUserDetailsService);
		bean.setCreateAuthenticatedToken(true);
		return bean;
	}
	
	@Bean
	JdbcUserDetailsManager memberUserDetailsService(DataSource dataSource) {
		
		var userDetailsService = new JdbcUserDetailsManager(dataSource);
		userDetailsService.setUsersByUsernameQuery("select email username, password, true from member where email = ?");
		userDetailsService.setAuthoritiesByUsernameQuery("select email username, role from member where email = ?");
		
		return userDetailsService;
	}	

//	@Bean
//	AuthenticationManager configure(HttpSecurity http, PasswordEncoder passwordEncoder) throws Exception {
//		// get AuthenticationManagerBuilder from HttpSecurity
//		var builder = http.getSharedObject(AuthenticationManagerBuilder.class);
//
//		// set InMemoryUserDetailsManager with AuthenticationProvider to AuthenticationManagerBuilder
////		builder.authenticationProvider(getAdminProvider(passwordEncoder));
//		
//		// set JdbcUserDetailsManager with AuthenticationProvider to AuthenticationManagerBuilder
////		builder.authenticationProvider(getMemberProvider(passwordEncoder));
//		
//		// set JpaUserDetailsManager with AuthenticationProvider to AuthenticationManagerBuilder
////		builder.authenticationProvider(getCustomerProvider(passwordEncoder));
//
//		return builder.build();
//	}

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
	
//	private AuthenticationProvider getCustomerProvider(PasswordEncoder passwordEncoder) {
//		var provider = new DaoAuthenticationProvider();
//		provider.setPasswordEncoder(passwordEncoder);
//		provider.setUserDetailsService(customerUserDetailsService);
//		
//		return provider;
//	}

//	@Bean
//	InMemoryUserDetailsManager userDetailsManager() {
//		return new InMemoryUserDetailsManager(List.of(
//				User.withUsername("admin@gmail.com").password("{noop}admin").authorities("Admin").build(),
//				User.withUsername("member@gmail.com").password("{noop}member").authorities("Member").build()
//		));
//	}
}
