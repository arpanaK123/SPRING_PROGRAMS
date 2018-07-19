package com.bridgeit.awareBean;

import java.util.Arrays;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class AwareBean implements ApplicationContextAware, BeanFactoryAware, BeanNameAware {

	public void setBeanName(String name) {
		System.out.println("set  bean name of AwareBean:");
		System.out.println("set name of bean=" + name);
	}

	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {

		System.out.println("setBeanFactory methos of awarebean");
		System.out.println("Aware singleton object:" + beanFactory.isSingleton("AwareBean"));
	}

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

		System.out.println("setApplicationcontext of awarebean");
		System.out.println("bean definition name:" + Arrays.toString(applicationContext.getBeanDefinitionNames()));
	}

}
