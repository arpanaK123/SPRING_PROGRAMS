package com.bridgeit.annotation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
	@Bean
	public HelloWorld getHelloWorld() {
		return new HelloWorld();
	}

}
