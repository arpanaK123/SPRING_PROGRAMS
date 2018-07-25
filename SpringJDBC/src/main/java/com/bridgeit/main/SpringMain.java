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
			System.out.println("enter id");
			int id = scan.nextInt();
			user.setId(id);
			System.out.println("enter name");
			String name = scan.next();
			user.setName(name);
			System.out.println("enter email");
			String email = scan.next();
			user.setEmail(email);
			System.out.println("enter password");
			String password = scan.next();
			user.setPassword(password);
			System.out.println("enter mobile num.");
			String mobnum = scan.next();
			user.setMobile_num(mobnum);
			System.out.println("enter Address");
			String address = scan.next();
			user.setAddress(address);
			// user.setId(1);
			// user.setName("Siya");
			// user.setEmail("siya@gmail.com");
			// user.setPassword("siya");
			// user.setMobile_num("909099030");
			// user.setAddress("mumbai");
			userdao.saveUserData(user);
			break;
		case 2:
			
//			System.out.println("Enter new name");
//		String name1=scan.next();
//		user.setName(name1);
//		System.out.println("enter new email");
//		String email1=scan.next();
//		user.setEmail(email1);
//		System.out.println("enter new password");
//		String password1=scan.next();
//		user.setPassword(password1);
//		System.out.println("enetr new mobile num");
//		String mobnum1=scan.next();
//		user.setMobile_num(mobnum1);
//		System.out.println("enter new address");
//		String address1=scan.next();
//		user.setAddress(address1);
//		System.out.println("enetr id to update data");
//		int idupdate=scan.nextInt();
//		userdao.updateUserData(user);
				
			user.setName("Rakhi");
			user.setEmail("rakhi@gmail.com");
			user.setPassword("rakhi");
			user.setAddress("Mumbai");
			user.setMobile_num("9090900");
			userdao.updateUserData(user);
			break;

		case 3:
			System.out.println("enter id to get data");
			int id1 = scan.nextInt();
			UserModel user1 = userdao.userGetById(id1);
			System.out.println(user1);
			// UserModel user1 = userdao.userGetById(3);
			// System.out.println("User Data found :" + user1);
			break;

		case 4:
			System.out.println("Enter id to delete data");
			int id2 = scan.nextInt();

			userdao.deleteUserById(id2);
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
