package com.bridgeit.Utility;



import org.springframework.security.crypto.bcrypt.BCrypt;

import com.bridgeit.model.UserModel;

public class BcryptHash {
	public static String generatedHashPassword(String originalPassword) {
		String generatedSecuredPasswordHash = BCrypt.hashpw(originalPassword, BCrypt.gensalt());
		return generatedSecuredPasswordHash;
	}
}
