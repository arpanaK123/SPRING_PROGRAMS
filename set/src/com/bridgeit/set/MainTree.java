package com.bridgeit.set;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

public class MainTree {

	public static void main(String[] args) {

		Set<User> set = new TreeSet<>();
		
		//OR
		Set<User> set2 = new TreeSet<>( new Comparator<User>() 
									{
										@Override
										public int compare(User arg0, User arg1) 
										{
											return 0;
										}
									});
		
		//OR
		Set<User> set3 = new TreeSet<>( new MyCom());



		
		User user1 = new User(1, "arsi2");

		User user2 = new User(4, "arsi");

		User user3 = new User(9, "arsi");

		User user4 = new User(7, "arsi");

		System.out.println("1------");
		set.add(user1);

		System.out.println("2------");
		set.add(user2);

		System.out.println("3------");
		set.add(user3);

		System.out.println("4------");
		set.add(user4);

		System.out.println("size: " + set.size());

	}

}
