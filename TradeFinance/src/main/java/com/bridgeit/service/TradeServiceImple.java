package com.bridgeit.service;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.hyperledger.fabric.sdk.Enrollment;
import org.hyperledger.fabric_ca.sdk.HFCAClient;
import org.hyperledger.fabric_ca.sdk.exception.EnrollmentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgeit.model.TradeUser;

import jdk.internal.jline.internal.Log;

@Service
public class TradeServiceImple implements TradeService {

	@Autowired
	HFCAClient caClient;

	@Override
	public TradeUser getAdmin(HFCAClient caClient) {
		TradeUser admin = tryDeserialize("admin");
		if (admin == null) {
			Log.info("----------------"+admin);
			Enrollment adminEnrollment = null;
			try {
				try {
					adminEnrollment = caClient.enroll("admin", "adminpw");
				} catch (org.hyperledger.fabric_ca.sdk.exception.InvalidArgumentException e) {
					e.printStackTrace();
				}
			} catch (EnrollmentException e) {
				e.printStackTrace();
			}
			admin = new TradeUser("admin", "importer", "ImporterMSP", adminEnrollment);
			serialize(admin);
		}
		return admin;
	}

	@Override
	public TradeUser tryDeserialize(String name) {
		if (Files.exists(Paths.get(name + ".jso"))) {
			return deSerialize(name);
		}
		return null;
	}

	@Override
	public TradeUser deSerialize(String name) {
		try (ObjectInputStream decoder = new ObjectInputStream(Files.newInputStream(Paths.get(name + ".jso")))) {
			return (TradeUser) decoder.readObject();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void serialize(TradeUser tradeUser) {
		try (ObjectOutputStream oos = new ObjectOutputStream(
				Files.newOutputStream(Paths.get(tradeUser.getName() + ".jso")))) {
			oos.writeObject(tradeUser);
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

}
