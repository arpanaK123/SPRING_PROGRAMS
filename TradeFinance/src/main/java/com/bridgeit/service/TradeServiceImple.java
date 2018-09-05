package com.bridgeit.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import org.hyperledger.fabric.sdk.ChaincodeID;
import org.hyperledger.fabric.sdk.Channel;
import org.hyperledger.fabric.sdk.Enrollment;
import org.hyperledger.fabric.sdk.HFClient;
import org.hyperledger.fabric.sdk.ProposalResponse;
import org.hyperledger.fabric.sdk.QueryByChaincodeRequest;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.hyperledger.fabric_ca.sdk.HFCAClient;
import org.hyperledger.fabric_ca.sdk.RegistrationRequest;
import org.springframework.stereotype.Service;

import com.bridgeit.model.TradeUser;

@Service
public class TradeServiceImple implements TradeService {
	
	public TradeUser getAdmin(HFCAClient caClient) throws Exception {
		// TODO Auto-generated method stub
		TradeUser admin = tryDeserialize("admin");
		if (admin == null) {
			Enrollment adminEnrollment = caClient.enroll("admin", "adminpw");
			admin = new TradeUser("admin", "importer", "ImporterMSP", adminEnrollment);
			serialize(admin);

		}
		return admin;
	}
	
	public TradeUser getUser(HFCAClient caClient, TradeUser registrar, String userId) throws Exception {
		TradeUser appUser = tryDeserialize(userId);
		if (appUser == null) {
			RegistrationRequest rr = new RegistrationRequest(userId, "org1.department1");
			String enrollmentSecret = caClient.register(rr, registrar);
			Enrollment enrollment = caClient.enroll(userId, enrollmentSecret);
			appUser = new TradeUser(userId, "exporter", "ExporterMSP", enrollment);
			serialize(appUser);
		}
		return appUser;
	}


	static void serialize(TradeUser appUser) throws IOException {
		try (ObjectOutputStream oos = new ObjectOutputStream(
				Files.newOutputStream(Paths.get(appUser.getName() + ".jso")))) {
			oos.writeObject(appUser);
		}
	}

	static TradeUser tryDeserialize(String name) throws Exception {
		if (Files.exists(Paths.get(name + ".jso"))) {
			return deserialize(name);
		}
		return null;
	}

	static TradeUser deserialize(String name) throws Exception {
		try (ObjectInputStream decoder = new ObjectInputStream(Files.newInputStream(Paths.get(name + ".jso")))) {
			return (TradeUser) decoder.readObject();
		}
	}

	public void queryBlockChain(HFClient client) throws InvalidArgumentException, ProposalException {
		// get channel instance from client
		Channel channel = client.getChannel("mychannel");
		// create chaincode request
		QueryByChaincodeRequest qpr = client.newQueryProposalRequest();
		// build cc id providing the chaincode name. Version is omitted here.
		ChaincodeID tradefinanceccId = ChaincodeID.newBuilder().setName("tradefinancecc").build();
		qpr.setChaincodeID(tradefinanceccId);
		// CC function to be called
		qpr.setFcn("get_Contract_By");
		qpr.setArgs(new String[] { "1" });

		Collection<ProposalResponse> res = channel.queryByChaincode(qpr);
		// display response
		for (ProposalResponse pres : res) {
			String stringResponse = new String(pres.getChaincodeActionResponsePayload());
			// log.info(stringResponse);
			System.out.println(stringResponse);
		}
	}

	@Override
	public TradeUser tradeUserByteToString(byte[] tradeUserData) throws ClassNotFoundException, IOException {
	    ByteArrayInputStream in = new ByteArrayInputStream(tradeUserData);
	    ObjectInputStream is = new ObjectInputStream(in);
	    return (TradeUser) is.readObject();
	}

	public TradeUser createTradeUserAccount(HFCAClient caClient, TradeUser registrar, String userId) throws Exception {
		TradeUser appUser = tryDeserialize(userId);
		if (appUser == null) {
			RegistrationRequest rr = new RegistrationRequest(userId, "org1.department1");
			String enrollmentSecret = caClient.register(rr, registrar);
			Enrollment enrollment = caClient.enroll(userId, enrollmentSecret);
			
			appUser = new TradeUser(userId, "exporter", "ExporterMSP", enrollment);
			serialize(appUser);
		}
		return appUser;
		
		
	}
	

	
}
