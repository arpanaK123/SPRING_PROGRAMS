package com.bridgeit.Controller;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.bridgeit.Model.Address;
import com.bridgeit.Model.Customer;
import com.bridgeit.Service.CustomerManager;
import com.bridgeit.Service.CustomerManagerImple;

public class TransactionController {
	public static void main(String[] args) {

		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");

		CustomerManager customermanager = ctx.getBean("customerManager", CustomerManagerImple.class);

		Customer customer = createDummyCustomer();
		customermanager.createCustomer(customer);

	}

	private static Customer createDummyCustomer() {
		Customer customer = new Customer();
		customer.setId(1);
		customer.setName("Aniket");

		Address address = new Address();
		address.setId(1);
		address.setCountry("India");
		customer.setAddress(address);
		return null;
	}
}
