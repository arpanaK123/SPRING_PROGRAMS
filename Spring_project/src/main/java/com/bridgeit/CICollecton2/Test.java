package com.bridgeit.CICollecton2;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class Test {

	public static void main(String[] args) {

		Resource resource = new ClassPathResource("collection2.xml");
		BeanFactory factory = new XmlBeanFactory(resource);
		Question question = (Question) factory.getBean("q");
		question.displayInformation();
	}

}
