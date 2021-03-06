package com.bridgeit.DIByConstructor;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

@SuppressWarnings("deprecation")
public class Test {

	public static void main(String[] args) {

		Resource resource = new ClassPathResource("cnstructor.xml");
		BeanFactory factory = new XmlBeanFactory(resource);

		Employee employee = (Employee) factory.getBean("e");
		employee.showInformation();
	}

}
