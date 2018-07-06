package com.bridgeit.set;

import java.util.HashSet;
import java.util.Set;

public class MainSet {

	public static void main(String[] args) {

//		 Set<Integer> set = new HashSet();
//		 set.add(5);
//		 set.add(6);
//		 set.add(7);
//		set.add(7);
//	 System.out.println(set);
//		 System.out.println("size: "+set.size());
		
		Set<User> set = new HashSet<>();
		
		User user1 = new User(1, "arsi2");
		
		User user2 = new User(1, "arsi");
		
		User user3 = new User(4, "arsi");
		
		User user4 = new User(4, "arsi");


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

	

