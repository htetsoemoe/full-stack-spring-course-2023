package com.jdc.test;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoderTest {
	
	public static void main(String[] args) {
		var encoder = new BCryptPasswordEncoder();
		
		System.out.println(encoder.encode("Admin"));
		System.out.println(encoder.encode("Admin"));
		System.out.println(encoder.encode("Admin"));
		System.out.println("Customer Password : " + encoder.encode("Customer"));
		
		System.out.println(encoder.matches("Admin", "$2a$10$qI/gxK7WIFhmmTADAIHPIeXetk6vKUAGVAX83IXnbRFK/RZ5xDhru"));
		System.out.println(encoder.matches("Member", "$2a$10$4ZgBg/3np1Ff.YpDHNClMeGmYe4XEuqczC5MEgr3Mj1sPkt7bp/ly"));
	}

}
