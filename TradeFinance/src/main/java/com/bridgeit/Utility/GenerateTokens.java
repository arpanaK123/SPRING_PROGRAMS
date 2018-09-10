package com.bridgeit.Utility;

import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class GenerateTokens {

	public static String createVerificationToken(String id, String subject, long ttlMillis) {
		// The JWT signature algorithm we will be using to sign the token
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
		System.out.println("sign algo: " + signatureAlgorithm);
		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);

		// Let's set the JWT Claims
		JwtBuilder builder = Jwts.builder().setId(id).setIssuedAt(now).setSubject(subject).signWith(signatureAlgorithm,
				"signingKey");

		// if it has been specified, let's add the expiration
		if (ttlMillis >= 0) {
			long expMillis = nowMillis + ttlMillis;
			Date exp = new Date(expMillis);
			builder.setExpiration(exp);
		}
		// Builds the JWT and serializes it to a compact, URL-safe string
		return builder.compact();

	}

	public Claims parseJwt(String jwt) {

		Claims claims = Jwts.parser().setSigningKey("signingKey").parseClaimsJws(jwt).getBody();

		return claims;
	}

	public String getJwtBYEmail(String jwt) {

		Claims claims = Jwts.parser().setSigningKey("signingKey").parseClaimsJws(jwt).getBody();
		System.out.println("claims: " + claims);
		System.out.println(claims.getId());
		return claims.getSubject();

	}

	public String getJwtSubject(String jwt) {

		Claims claims = Jwts.parser().setSigningKey("signingKey").parseClaimsJws(jwt).getBody();

		return claims.getSubject();

	}

}
