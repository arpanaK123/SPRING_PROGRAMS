package com.bridgeit.Utility;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bridgeit.model.TradeContractModel;
import com.bridgeit.model.TradeUser;
import com.bridgeit.model.UserModel;

@Component
public class TradeFunctionUtility {

	public byte[] getUserAccountToByteArray(TradeUser tradeUser) {

		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

		try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)) {

			objectOutputStream.writeObject(tradeUser);
		} catch (IOException e) {
			e.printStackTrace();
		}
		byte[] serializeData = byteArrayOutputStream.toByteArray();
		System.out.println(serializeData + "data");
		return serializeData;

	}

	public TradeUser getTradeUserByteArrayToObject(byte[] bytearray) throws IOException, ClassNotFoundException {

		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytearray);
		ObjectInput objectInput = new ObjectInputStream(byteArrayInputStream);

		return (TradeUser) objectInput.readObject();

	}

	@SuppressWarnings("unused")
	public TradeUser getUserTradeAccount(HFCAClient caClient, TradeUser tradeUserAdmin, String userId, String userRole)
			throws Exception {

		TradeUser tradeUser = null;
		System.out.println("tradeuser: " + tradeUser);
		if (tradeUser == null) {
			RegistrationRequest registrationRequest = new RegistrationRequest(userId, "org1.department2");
			String enrollmentSecret = caClient.register(registrationRequest, tradeUserAdmin);
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

			// serialize(tradeUser);

		}
		System.out.println(tradeUser + "33334");
		return tradeUser;

	}

	public boolean transactionInvokeBlockChain(HFClient client, String chaincodeFunction, String[] args,
			Channel channel) throws org.hyperledger.fabric.sdk.exception.InvalidArgumentException {

		TransactionProposalRequest tqr = client.newTransactionProposalRequest();
		ChaincodeID tradeFinanceCCId = ChaincodeID.newBuilder().setName("tradefinancecc").build();
		tqr.setChaincodeID(tradeFinanceCCId);
		tqr.setFcn(chaincodeFunction);
		tqr.setArgs(args);
		Collection<ProposalResponse> responses = null;
		try {
			responses = channel.sendTransactionProposal(tqr);
			List<ProposalResponse> invalid = responses.stream().filter(res -> res.isInvalid())
					.collect(Collectors.toList());
			if (!invalid.isEmpty()) {

				invalid.forEach(response -> {
					System.out.println(response.getMessage());
				});

			}
		} catch (ProposalException e) {
			e.printStackTrace();
			return false;
		}

		try {
			BlockEvent.TransactionEvent event = channel.sendTransaction(responses).get(60, TimeUnit.SECONDS);
			System.out.println("event______"+event);
			if (event.isValid()) {
				System.out.println(event.getTransactionID() + " transaction is valid ");
				return true;
			} else {
				System.out.println(event.getTransactionID() + " transaction is invalid");
				return false;
			}
		} catch (InterruptedException | ExecutionException | TimeoutException e) {
			e.printStackTrace();
			return false;
		}

	}

	public boolean invokeBlockChain(HFClient client, String function, String[] args, Channel channel)
			throws org.hyperledger.fabric.sdk.exception.InvalidArgumentException {
		System.out.println("channel: " + channel.getName());
		TransactionProposalRequest tqr = client.newTransactionProposalRequest();
		ChaincodeID tradeFinanceCCId = ChaincodeID.newBuilder().setName("tradefinancecc").build();
		System.out.println("---------" + tradeFinanceCCId);

		tqr.setChaincodeID(tradeFinanceCCId);
		tqr.setFcn(function);
		tqr.setArgs(args);
		Collection<ProposalResponse> responses = null;
		try {
			responses = channel.sendTransactionProposal(tqr);
			System.out.println("tqr" + tqr);
			System.out.println("response" + responses);
			List<ProposalResponse> invalid = responses.stream().filter(res -> res.isInvalid())
					.collect(Collectors.toList());
			if (!invalid.isEmpty()) {

				invalid.forEach(response -> {
					System.out.println(response.getMessage());
				});

			}
		} catch (ProposalException e) {
			e.printStackTrace();
			return false;
		}

		try {
			BlockEvent.TransactionEvent event = channel.sendTransaction(responses).get(60, TimeUnit.SECONDS);
			if (event.isValid()) {
				System.out.println(event.getTransactionID() + " transaction is valid ");
				return true;
			} else {
				System.out.println(event.getTransactionID() + " transaction is invalid");
				return false;
			}
		} catch (InterruptedException | ExecutionException | TimeoutException e) {
			e.printStackTrace();
			return false;
		}
	}

	public List<String> queryBlockChain(HFClient client, String function, String[] args, Channel channel)
			throws ProposalException, InvalidArgumentException {

		QueryByChaincodeRequest qpr = client.newQueryProposalRequest();
		ChaincodeID tradeFinanceCCId = ChaincodeID.newBuilder().setName("tradefinancecc").build();
		qpr.setChaincodeID(tradeFinanceCCId);
		qpr.setFcn(function);
		qpr.setArgs(args);
		List<String> list = new ArrayList<String>();
		Collection<ProposalResponse> res = null;
		try {
			res = channel.queryByChaincode(qpr);

		} catch (org.hyperledger.fabric.sdk.exception.InvalidArgumentException e) {
			e.printStackTrace();
		}

		// display response
		for (ProposalResponse pres : res) {
			String stringResponse = null;
			try {
				stringResponse = new String(pres.getChaincodeActionResponsePayload());
			} catch (org.hyperledger.fabric.sdk.exception.InvalidArgumentException e) {
				e.printStackTrace();
			}
			System.out.println(stringResponse);
			list.add(stringResponse);
		}
		return list;
	}

}
