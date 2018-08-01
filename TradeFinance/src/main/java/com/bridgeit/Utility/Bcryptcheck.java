package com.bridgeit.Utility;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class Bcryptcheck {
public static void main(String[] args) {
	
	String name="asdf";
	String pas=BCrypt.hashpw(name,  BCrypt.gensalt());
	System.out.println(pas);
System.out.println(	BCrypt.checkpw(name, pas));
	
}
}
