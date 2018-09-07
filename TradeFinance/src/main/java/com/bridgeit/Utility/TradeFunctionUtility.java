package com.bridgeit.Utility;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

import org.hyperledger.fabric.sdk.BlockEvent;
import org.hyperledger.fabric.sdk.ChaincodeID;
import org.hyperledger.fabric.sdk.Channel;
import org.hyperledger.fabric.sdk.Enrollment;
import org.hyperledger.fabric.sdk.EventHub;
import org.hyperledger.fabric.sdk.HFClient;
import org.hyperledger.fabric.sdk.Orderer;
import org.hyperledger.fabric.sdk.Peer;
import org.hyperledger.fabric.sdk.ProposalResponse;
import org.hyperledger.fabric.sdk.QueryByChaincodeRequest;
import org.hyperledger.fabric.sdk.TransactionProposalRequest;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.hyperledger.fabric.sdk.exception.TransactionException;
import org.hyperledger.fabric_ca.sdk.HFCAClient;
import org.hyperledger.fabric_ca.sdk.RegistrationRequest;
import org.springframework.stereotype.Component;

import com.bridgeit.model.TradeContractModel;
import com.bridgeit.model.TradeUser;
import com.bridgeit.model.UserModel;

@Component
public class TradeFunctionUtility {

	public TradeUser getAdmin(HFCAClient caClient) throws Exception {

		TradeUser admin = tryDeserialize("admin");
		if (admin == null) {
			Enrollment adminEnrollment = caClient.enroll("admin", "adminpw");
			admin = new TradeUser("admin", "importer", "ImporterMSP", adminEnrollment);
			serialize(admin);

		}
		return admin;

	}

	public static TradeUser tryDeserialize(String name) throws Exception {
		if (Files.exists(Paths.get("//home//bridgeit//SPRING//TradeFinance//" + name + ".jso"))) {
			return deserialize(name);
		}
		return null;
	}

	public static TradeUser deserialize(String name) throws Exception {
		try (ObjectInputStream decoder = new ObjectInputStream(
				Files.newInputStream(Paths.get("//home//bridgeit//SPRING//TradeFinance//" + name + ".jso")))) {
			return (TradeUser) decoder.readObject();
		}
	}

	public static void serialize(TradeUser appUser) throws IOException {
		try (ObjectOutputStream oos = new ObjectOutputStream(Files
				.newOutputStream(Paths.get("//home//bridgeit//SPRING//TradeFinance//" + appUser.getName() + ".jso")))) {
			oos.writeObject(appUser);
		}
	}

	public byte[] getUserAccountToByteArray(TradeUser tradeUser) {

		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)) {

			objectOutputStream.writeObject(tradeUser);
		} catch (IOException e) {
			e.printStackTrace();
		}
		byte[] serializeData = byteArrayOutputStream.toByteArray();
		return serializeData;

	}

	public Channel getChannel(HFClient client, TradeUser admin) throws InvalidArgumentException, TransactionException {

		client.setUserContext(admin);
		Peer peer1 = client.newPeer("peer0.importer.bridgelabz.com", "grpc://localhost:7051");
		// eventhub name and endpoint in fabcar network
		EventHub eventHub = client.newEventHub("eventhub01", "grpc://localhost:7053");
		// orderer name and endpoint in fabcar network
		Orderer orderer = client.newOrderer("orderer.bridgelabz.com", "grpc://localhost:7050");
		// channel name in fabcar network
		Channel channel = client.newChannel("mychannel");
		channel.addPeer(peer1);
		channel.addEventHub(eventHub);
		channel.addOrderer(orderer);
		channel.initialize();
		return channel;
	}

	public TradeUser getUserTradeContract(HFCAClient caClient, TradeUser registrar, String userId) {
//		TradeUser tradeUser = tryDeserialize(userId);
//		if (tradeUser == null) {
//			RegistrationRequest registrationRequest = new RegistrationRequest(userId, "org1.department2");
//			String enrollmentSecret = caClient.register(registrationRequest, registrar);
//			Enrollment enrollment = caClient.enroll(userId, enrollmentSecret);
//			serialize(tradeUser);
//		
	return registrar;

		
	}

	public TradeUser getTradeUserByteArrayToObject(byte[] bytearray) throws IOException, ClassNotFoundException {

		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytearray);
		ObjectInput objectInput = new ObjectInputStream(byteArrayInputStream);

		return (TradeUser) objectInput.readObject();

	}

	public TradeUser getUserTradeAccount(HFCAClient caClient, TradeUser registrar, String userId, String userRole)
			throws Exception {

		TradeUser tradeUser = tryDeserialize(userId);
		if (tradeUser == null) {
			RegistrationRequest registrationRequest = new RegistrationRequest(userId, "org1.department2");
			String enrollmentSecret = caClient.register(registrationRequest, registrar);
			Enrollment enrollment = caClient.enroll(userId, enrollmentSecret);

			switch (userRole) {
			case "exporter":
				tradeUser = new TradeUser(userId, "exporter", "ExporterMSP", enrollment);
				break;

			case "importer":
				tradeUser = new TradeUser(userId, "importer", "ImporterMSP", enrollment);
				break;

			case "importerBank":
				tradeUser = new TradeUser(userId, "importerBank", "ImporterBankMSP", enrollment);
				break;
			case "insurance":
				tradeUser = new TradeUser(userId, "insurance", "InsuranceMSP", enrollment);
				break;
			case "custom":
				tradeUser = new TradeUser(userId, "custom", "CustomMSP", enrollment);
				break;
			default:
				System.out.println("invalid role");
			}

			serialize(tradeUser);

		}
		return tradeUser;

	}

	public void ivokeBlockChain(HFClient client, UserModel user, String chaincodefunction)
			throws ExecutionException, TimeoutException {
		Channel channel = client.getChannel("mychannel");
		TransactionProposalRequest transactionProposalRequest = client.newTransactionProposalRequest();
		ChaincodeID tradeFinanceCCId = ChaincodeID.newBuilder().setName("tradefinancecc").build();
		transactionProposalRequest.setChaincodeID(tradeFinanceCCId);
		transactionProposalRequest.setFcn("create_Account");
		transactionProposalRequest.setArgs(new String[] { "102", "exporter", "20000", "SBI" });
		Collection<ProposalResponse> responses = null;
		try {
			responses = channel.sendTransactionProposal(transactionProposalRequest);
			List<ProposalResponse> invalid = responses.stream().filter(res -> res.isInvalid())
					.collect(Collectors.toList());
			if (!invalid.isEmpty()) {

				invalid.forEach(response -> {
					System.out.println(response.getMessage());
				});

			}
		} catch (ProposalException | InvalidArgumentException e) {
			e.printStackTrace();
		}
		try {
			BlockEvent.TransactionEvent event = channel.sendTransaction(responses).get(60, TimeUnit.SECONDS);
			if (event.isValid()) {
				System.out.println(event.getTransactionID() + " transaction is valid ");
			} else {
				System.out.println(event.getTransactionID() + " transaction is invalid");
			}
		} catch (InterruptedException | ExecutionException | TimeoutException e) {
			e.printStackTrace();
		}
	}

	public List<String> queryBlockChain(HFClient client, String userFunction, String arrayargs[])
			throws InvalidArgumentException, ProposalException {
		// get channel instance from client
		Channel channel = client.getChannel("mychannel");
		// create chaincode request
		QueryByChaincodeRequest qpr = client.newQueryProposalRequest();
		// build cc id providing the chaincode name. Version is omitted here.
		ChaincodeID tradefinanceccId = ChaincodeID.newBuilder().setName("tradefinancecc").build();
		qpr.setChaincodeID(tradefinanceccId);
		// CC function to be called
		qpr.setFcn(userFunction);
		qpr.setArgs(arrayargs);

		Collection<ProposalResponse> res = channel.queryByChaincode(qpr);
		// display response
		for (ProposalResponse pres : res) {
			String stringResponse = new String(pres.getChaincodeActionResponsePayload());
			// log.info(stringResponse);
			System.out.println(stringResponse);
		}
		return null;
	}

	// public List<String> queryBlockChain(HFClient client,String function,String []
	// args) throws ProposalException, InvalidArgumentException {
	// // get channel instance from client
	// Channel channel = client.getChannel("mychannel");
	// // create chaincode request
	// QueryByChaincodeRequest qpr = client.newQueryProposalRequest();
	// ChaincodeID tradeFinanceCCId =
	// ChaincodeID.newBuilder().setName("tradefinancecc").build();
	// qpr.setChaincodeID(tradeFinanceCCId);
	// // CC function to be called
	// qpr.setFcn(function);
	// qpr.setArgs(args);
	// List<String> list = new ArrayList<String>();
	// Collection<ProposalResponse> res = null;
	// try {
	// res = channel.queryByChaincode(qpr);
	// } catch (org.hyperledger.fabric.sdk.exception.InvalidArgumentException e) {
	// e.printStackTrace();
	// }
	//
	// // display response
	// for (ProposalResponse pres : res) {
	// String stringResponse = null;
	// try {
	// stringResponse = new String(pres.getChaincodeActionResponsePayload());
	// } catch (org.hyperledger.fabric.sdk.exception.InvalidArgumentException e) {
	// e.printStackTrace();
	// }
	// // log.info(stringResponse);
	// System.out.println(stringResponse);
	// list.add(stringResponse);
	// }
	// return list;
	// }

}
