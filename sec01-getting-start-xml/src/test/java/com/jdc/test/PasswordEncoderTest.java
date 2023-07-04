package com.jdc.test;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoderTest {
	
	public static void main(String[] args) {
		var encoder = new BCryptPasswordEncoder();
		
		System.out.println(encoder.encode("Admin"));
		System.out.println(encoder.encode("Admin"));
		System.out.println(encoder.encode("Admin"));
		
		System.out.println(encoder.matches("Admin", "$2a$10$qI/gxK7WIFhmmTADAIHPIeXetk6vKUAGVAX83IXnbRFK/RZ5xDhru"));
	}

}
