package com.bridgeit.Utility;

import org.mindrot.jbcrypt.BCrypt;

import com.bridgeit.model.UserModel;

public class BcryptHash {
	public static String generatedHashPassword(String originalPassword) {
		UserModel user = new UserModel();
		originalPassword = user.getPassword();
		String generatedSecuredPasswordHash = BCrypt.hashpw(originalPassword, BCrypt.gensalt(12));
		return generatedSecuredPasswordHash;
	}
}
