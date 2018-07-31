package com.bridgeit.Service;

import org.springframework.transaction.annotation.Transactional;

import com.bridgeit.DAO.CustomerDAO;
import com.bridgeit.Model.Customer;

public class CustomerManagerImple implements CustomerManager{

	private CustomerDAO customerDAO;
	
	public void setCustomerDAO(CustomerDAO customerDAO) {
		this.customerDAO = customerDAO;
	}


	@Override
	@Transactional
	public void createCustomer(Customer customer) {
		customerDAO.create(customer);
		
	}
	

}
