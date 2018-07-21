package com.bridgeit.DISettersUsingAnnotation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.bridgeit.DISettersUsingAnnotation")
public class AppConfig {
	@Bean
	public Department department() {
		System.out.println("config");
		return new DepartmentImple();
	}

}
