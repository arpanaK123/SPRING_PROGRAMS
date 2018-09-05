package com.bridgeit.hyperledger_fabric_sdk;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.stream.Collectors;

import static java.nio.charset.StandardCharsets.UTF_8;

import org.apache.log4j.Logger;
import org.hyperledger.fabric.sdk.BlockEvent;
import org.hyperledger.fabric.sdk.BlockEvent.TransactionEvent;
import org.hyperledger.fabric.sdk.ChaincodeID;
import org.hyperledger.fabric.sdk.ChaincodeResponse.Status;
import org.hyperledger.fabric.sdk.Channel;
import org.hyperledger.fabric.sdk.Enrollment;
import org.hyperledger.fabric.sdk.EventHub;
import org.hyperledger.fabric.sdk.HFClient;
import org.hyperledger.fabric.sdk.Orderer;
import org.hyperledger.fabric.sdk.Peer;
import org.hyperledger.fabric.sdk.ProposalResponse;
import org.hyperledger.fabric.sdk.QueryByChaincodeRequest;
import org.hyperledger.fabric.sdk.TransactionProposalRequest;
import org.hyperledger.fabric.sdk.exception.CryptoException;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.hyperledger.fabric.sdk.exception.TransactionException;
import org.hyperledger.fabric.sdk.security.CryptoSuite;
import org.hyperledger.fabric_ca.sdk.HFCAClient;
import org.hyperledger.fabric_ca.sdk.RegistrationRequest;

public class FabricSDK {
	private static final Logger log = Logger.getLogger(FabricSDK.class);

	public static void main(String[] args) throws Exception {
		// create fabric-ca client
		HFCAClient caClient = getHfCaClient("http://localhost:7054", null);
		// enroll or load admin
		AppUser admin = getAdmin(caClient);
		System.out.println(admin);
		// register and enroll new user
		AppUser appUser = getUser(caClient, admin, "hfuser");
		log.info(appUser);
		System.out.println(appUser);
		// get HFC client instance
		HFClient client = getHfClient();
		// set user context
		client.setUserContext(admin);

		// get HFC channel using the client
		Channel channel = getChannel(client);
		log.info("Channel: " + channel.getName());

		// call query blockchain example
		queryBlockChain(client);
		// invokeChainCode(client);
	}

	/**
	 * Initialize and get HF channel
	 *
	 * @param client
	 *            The HFC client
	 * @return Initialized channel
	 * @throws InvalidArgumentException
	 * @throws TransactionException
	 */
	static Channel getChannel(HFClient client) throws InvalidArgumentException, TransactionException {
		// initialize channel
		// peer name and endpoint in fabcar network
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

	/**
	 * Invoke blockchain query
	 *
	 * @param client
	 *            The HF Client
	 * @throws ProposalException
	 * @throws InvalidArgumentException
	 */
	static void queryBlockChain(HFClient client) throws ProposalException, InvalidArgumentException {
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

	/**
	 * Create new HLF client
	 *
	 * @return new HLF client instance. Never null.
	 * @throws CryptoException
	 * @throws InvalidArgumentException
	 */
	static HFClient getHfClient() throws Exception {
		// initialize default cryptosuite
		CryptoSuite cryptoSuite = CryptoSuite.Factory.getCryptoSuite();
		// setup the client
		HFClient client = HFClient.createNewInstance();
		System.out.println("gethfclient " + client);
		client.setCryptoSuite(cryptoSuite);
		return client;
	}

	/**
	 * Enroll admin into fabric-ca using {@code admin/adminpw} credentials. If
	 * AppUser object already exist serialized on fs it will be loaded and new
	 * enrollment will not be executed.
	 *
	 * @param caClient
	 *            The fabric-ca client
	 * @return AppUser instance with userid, affiliation, mspId and enrollment set
	 * @throws Exception
	 */
	static AppUser getAdmin(HFCAClient caClient) throws Exception {
		// TODO Auto-generated method stub
		AppUser admin = tryDeserialize("admin");
		if (admin == null) {
			Enrollment adminEnrollment = caClient.enroll("admin", "adminpw");
			admin = new AppUser("admin", "importer", "ImporterMSP", adminEnrollment);
			serialize(admin);

		}
		return admin;
	}

	/**
	 * Register and enroll user with userId. If AppUser object with the name already
	 * exist on fs it will be loaded and registration and enrollment will be
	 * skipped.
	 *
	 * @param caClient
	 *            The fabric-ca client.
	 * @param registrar
	 *            The registrar to be used.
	 * @param userId
	 *            The user id.
	 * @return AppUser instance with userId, affiliation,mspId and enrollment set.
	 * @throws Exception
	 */
	static AppUser getUser(HFCAClient caClient, AppUser registrar, String userId) throws Exception {
		AppUser appUser = tryDeserialize(userId);
		if (appUser == null) {
			RegistrationRequest rr = new RegistrationRequest(userId, "org1.department1");
			String enrollmentSecret = caClient.register(rr, registrar);
			Enrollment enrollment = caClient.enroll(userId, enrollmentSecret);

		//	appUser = new AppUser(userId, "exporter", "ExporterMSP", enrollment);
			appUser = new AppUser(userId, "exporter", "ExporterMSP", enrollment);

			serialize(appUser);
		}
		return appUser;
	}

	/**
	 * Get new fabic-ca client
	 *
	 * @param caUrl
	 *            The fabric-ca-server endpoint url
	 * @param caClientProperties
	 *            The fabri-ca client properties. Can be null.
	 * @return new client instance. never null.
	 * @throws Exception
	 */
	static HFCAClient getHfCaClient(String caUrl, Properties caClientProperties) throws Exception {
		// TODO Auto-generated method stub
		CryptoSuite cryptoSuite = CryptoSuite.Factory.getCryptoSuite();
		HFCAClient caClient = HFCAClient.createNewInstance(caUrl, caClientProperties);
		caClient.setCryptoSuite(cryptoSuite);
		return caClient;
	}

	private static final byte[] EXPECTED_EVENT_DATA = "!".getBytes(UTF_8);
	private static final String EXPECTED_EVENT_NAME = "event";

	static CompletableFuture<TransactionEvent> invokeChainCode(HFClient client)
			throws ProposalException, InvalidArgumentException {
		Channel channel = client.getChannel("mychannel");

		// create chaincode request
		QueryByChaincodeRequest qpr = client.newQueryProposalRequest();
		TransactionProposalRequest transactionRequest = client.newTransactionProposalRequest();
		ChaincodeID chaincodeID = ChaincodeID.newBuilder().setName("tradefinancecc").build();

		// ChaincodeID chaincodeID =
		// ChaincodeID.newBuilder().setName("tradefinancecc").build();
		transactionRequest.setChaincodeID(chaincodeID);
		transactionRequest.setFcn("create_Account");
		transactionRequest.setArgs(new String[] { "100", "Importer", "20000", "SBI" });
		Map<String, byte[]> tm2 = new HashMap<>();
		tm2.put("HyperLedgerFabric", "TransactionProposalRequest:JavaSDK".getBytes(UTF_8));
		tm2.put("result", ":)".getBytes(UTF_8));
		tm2.put(EXPECTED_EVENT_NAME, EXPECTED_EVENT_DATA);
		transactionRequest.setTransientMap(tm2);

		Collection<ProposalResponse> responses = channel.sendTransactionProposal(transactionRequest);
		List<ProposalResponse> invalid = responses.stream().filter(r -> r.isInvalid()).collect(Collectors.toList());
		if (!invalid.isEmpty()) {
			invalid.forEach(response -> {
				System.out.println(response.getMessage());
				log.error(response.getMessage());
			});

			throw new RuntimeException("invalid response(s) found");

		}
		//return channel.sendTransaction(responses);

		BlockEvent.TransactionEvent event =channel.sendTransaction(client, channel).get(60, TimeUnit.SECONDS);
		if (event.isValid()) {
			System.out.println("Transacion tx: " + event.getTransactionID() + " is completed.");
			log.info("Transacion tx: " + event.getTransactionID() + " is completed.");

		} else {
			System.out.println("Transaction tx : " + event.getTransactionID() + "is invalid");
			log.error("Transaction tx: " + event.getTransactionID() + " is invalid.");
		}
		
		return channel.sendTransaction(responses);

	}

	// for (ProposalResponse res: responses) {
	// Status status = res.getStatus();
	// Logger.getLogger(FabricSDK.class.getName()).log(Level.INFO,"Invoked
	// create_Account on "+Config.tradefinancecc + ". Status - " + status);
	// }
	// List<ProposalResponse> invalid = responses.stream().filter(r ->
	// r.isInvalid()).collect(Collectors.toList());
	//
	// if (!invalid.isEmpty()) {
	// invalid.forEach(response -> {
	// log.error(response.getMessage());
	// });
	// throw new RuntimeException("invalid response(s) found");
	// }
	// return channel.sendTransaction(responses);
	// display response
	// for (ProposalResponse pres : responses) {
	// String stringResponse = new String(pres.getChaincodeActionResponsePayload());
	// // log.info(stringResponse);
	// System.out.println(stringResponse);
	// }

	/**
	 * Serialize AppUser object to file
	 *  
	 * @param appUser
	 *            The object to be serialized
	 * @throws IOException
	 */
	static void serialize(AppUser appUser) throws IOException {
		try (ObjectOutputStream oos = new ObjectOutputStream(
				Files.newOutputStream(Paths.get(appUser.getName() + ".jso")))) {
			oos.writeObject(appUser);
		}
	}

	/**
	 * Deserialize AppUser object from file
	 *
	 * @param name
	 *            The name of the user. Used to build file name ${name}.jso
	 * @return
	 * @throws Exception
	 */
	static AppUser tryDeserialize(String name) throws Exception {
		if (Files.exists(Paths.get(name + ".jso"))) {
			return deserialize(name);
		}
		return null;
	}

	static AppUser deserialize(String name) throws Exception {
		try (ObjectInputStream decoder = new ObjectInputStream(Files.newInputStream(Paths.get(name + ".jso")))) {
			return (AppUser) decoder.readObject();
		}
	}

}
