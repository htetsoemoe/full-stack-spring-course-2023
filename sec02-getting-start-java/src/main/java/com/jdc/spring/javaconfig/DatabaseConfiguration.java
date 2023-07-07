package com.jdc.spring.javaconfig;

import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.mysql.cj.jdbc.Driver;

import jakarta.persistence.EntityManagerFactory;

@Configuration
@EnableJpaRepositories
@EnableTransactionManagement
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
	
	// using Spring JPA for CustomerUserDetailsService
	@Bean
	LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
		var factory = new LocalContainerEntityManagerFactoryBean();
		
		factory.setDataSource(dataSource);
		factory.setPackagesToScan("com.jdc.spring.javaconfig.service.entity");
		factory.setPersistenceProviderClass(HibernatePersistenceProvider.class);
		
		factory.setJpaPropertyMap(Map.of(
				"hibernate.show_sql", "true",
				"hibernate.format_sql", "true"
				));
		
		return factory;
	}
	
	@Bean
	JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory);
	}

}
