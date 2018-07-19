package com.bridgeit.DIbyConstructor;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class Main {

	public static void main(String[] args) {
		Resource resource = new ClassPathResource("DIConstructor.xml");
		System.out.println(resource);
		BeanFactory factory = new XmlBeanFactory(resource);
		System.out.println(factory);
		Student student = (Student) factory.getBean("student");
		student.show();
	}

}
