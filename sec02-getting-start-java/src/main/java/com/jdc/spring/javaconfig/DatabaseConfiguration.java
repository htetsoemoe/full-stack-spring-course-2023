package com.jdc.spring.javaconfig;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mysql.cj.jdbc.Driver;

@Configuration
public class DatabaseConfiguration {
	
	@Bean
	DataSource dataSource() {
		var ds = new BasicDataSource();
		ds.setUrl("jdbc:mysql://localhost:3306/security_db");
		ds.setUsername("securityusr");
		ds.setPassword("securitypwd");
		ds.setDriverClassName(Driver.class.getName());
		
		return ds;
	}

}
