package com.bridgeit.DIConstructorUsingAnnotation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages="com.bridgeit.DIConstructorUsingAnnotation")
public class AppConfig {
@Bean
	public Employee getEmployee()
	{
	System.out.println("appconfig");
		return new EmployeeImplementation();
		
	}
	
}
