package com.bridgeit.springLifeCycle;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class PersonBean implements InitializingBean, DisposableBean {

	private String name;

	public PersonBean() {
		System.out.println("construvtor of person bean");
	}

	
	//initializingBean
	public void afterPropertiesSet() throws Exception {
		int a=10;
		int b=20;
		int c=a+b;
		System.out.println("sum: "+c);
		System.out.println("afterPropertiesSet of person bean");

	}

	//disposelBean
	public void destroy() throws Exception {

		System.out.println("personbean is destroyed");
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
