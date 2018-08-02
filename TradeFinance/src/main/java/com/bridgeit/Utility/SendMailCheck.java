package com.bridgeit.Utility;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

public class SendMailCheck {

	public static void main(String[] args) {

		final String fromEmail = "arpanabitp1317@gmail.com";
		final String fromPassword = "arshnand@%1317";
		final String toEmail = "abc@gmail.com";

		System.out.println("Start ");
		Properties properties = new Properties();
		properties.put("mail.smtp.host", "smtp.gmail.com"); // SMTP Host
		properties.put("mail.smtp.port", "587"); // TLS Port
		properties.put("mail.smtp.auth", "true"); // enable authentication
		properties.put("mail.smtp.starttls.enable", "true"); // enable STARTTLS

		Authenticator auth = new Authenticator() {
			// override the getPasswordAuthentication method
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(fromEmail, fromPassword);
			}
		};
		Session session = Session.getInstance(properties, auth);

		EmailUtil.sendEmail(session, toEmail, "TLSEmail Testing Subject", "TLSEmail Testing Body");

	}

}
