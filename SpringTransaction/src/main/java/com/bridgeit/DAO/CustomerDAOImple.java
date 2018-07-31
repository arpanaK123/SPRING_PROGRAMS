package com.bridgeit.DAO;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.bridgeit.Model.Customer;

public class CustomerDAOImple implements CustomerDAO {

	private DataSource datasource;

	public void setDatasource(DataSource datasource) {
		this.datasource = datasource;
	}

	@Override
	public void create(Customer customer) {

		String customerQuery = "insert into Cuustomer (id ,name) values(?,?)";

		String addressQuery = "insert into Address (id,address,country) values(?,?,?)";
		JdbcTemplate template = new JdbcTemplate(datasource);

		template.update(customerQuery, new Object[] { customer.getId(), customer.getName() });
		System.out.println("inserted into customer Data successfully");

		template.update(addressQuery, new Object[] { customer.getId(), customer.getAddress().getAddress(),
				customer.getAddress().getCountry() });

		System.out.println("inserted into address Data successfully");
		

	}

}
