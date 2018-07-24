package com.bridgeit.main;

import java.util.List;
import java.util.Scanner;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.bridgeit.DAO.UserDAO;
import com.bridgeit.DAO.UserDaoTemplateImple;
import com.bridgeit.model.UserModel;

public class SpringMain {

	public static void main(String[] args) {

		ApplicationContext context = new ClassPathXmlApplicationContext("SpringJdbcBean.xml");
		UserDAO userdao = context.getBean("userdao", UserDAO.class);
		UserModel user = new UserModel();
		UserDaoTemplateImple userTemplate = new UserDaoTemplateImple();
		System.out.println("Welcome to Database");
		System.out.println(
				"1. Insert data\n2. Update Data\n3. Data getBy ID\n4. Delete Data by id\n5. All Data\n \t Enter your choice");
		Scanner scan = new Scanner(System.in);
		int choice = scan.nextInt();
		switch (choice) {
		case 1:

			user.setId(1);
			user.setName("Siya");
			user.setEmail("siya@gmail.com");
			user.setPassword("siya");
			user.setMobile_num("909099030");
			user.setAddress("mumbai");
			userdao.saveUserData(user);
			break;
		case 2:
			user.setName("Rakhi");
			user.setEmail("rakhi@gmail.com");
			user.setPassword("rakhi");
			user.setAddress("Mumbai");
			user.setMobile_num("9090900");
			userdao.updateUserData(user);
			break;

		case 3:
			UserModel user1 = userdao.userGetById(3);
			System.out.println("User Data found :" + user1);
			break;

		case 4:
			userdao.deleteUserById(3);
			break;

		case 5:
			List<UserModel> userlist = userdao.getAll();
			System.out.println("All data: " + userlist);
			break;
		default:
			System.out.println("Enter correct choice");
			break;

		}

	}

}
