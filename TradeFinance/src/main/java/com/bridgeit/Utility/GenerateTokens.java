package com.bridgeit.Utility;

import java.util.Date;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class GenerateTokens {
	public static void main(String[] args) {
		String generate=GenerateTokens.createVerificationToken("1", "asdf", "email", 10000);
		System.out.println(generate);
		
	}

	public static String createVerificationToken(String id, String issuer, String subject, long ttlMillis) {
		// The JWT signature algorithm we will be using to sign the token
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
		System.out.println("sign algo: "+signatureAlgorithm);
		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);
		// We will sign our JWT with our ApiKey secret
		// byte[] apiKeySecretBytes =
		// DatatypeConverter.parseBase64Binary(apiKey.getSecret());
		// Key signingKey = new SecretKeySpec(apiKeySecretBytes,
		// signatureAlgorithm.getJcaName());

		// Let's set the JWT Claims
		JwtBuilder builder = Jwts.builder().setId(id).setIssuedAt(now).setSubject(subject).setIssuer(issuer)
				.signWith(signatureAlgorithm, "signingKey");

		// if it has been specified, let's add the expiration
		if (ttlMillis >= 0) {
			long expMillis = nowMillis + ttlMillis;
			Date exp = new Date(expMillis);
			builder.setExpiration(exp);
		}
		// Builds the JWT and serializes it to a compact, URL-safe string
		return builder.compact();

	}

//	private static void verifyToken(String token) {
//		Claims claim = (Claims) Jwts.parser().setSigningKey("apiKey").parseClaimsJws(token).getBody();
//		System.out.println("id: " + claim.getId());
//		System.out.println("issued: " + claim.getIssuer());
//		System.out.println("subject: " + claim.getIssuer());
//		System.out.println("expiration: " + claim.getExpiration());
//
//	}

}
