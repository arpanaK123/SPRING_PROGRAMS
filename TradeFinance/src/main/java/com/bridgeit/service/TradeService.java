package com.bridgeit.service;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.hyperledger.fabric.sdk.ChaincodeID;
import org.hyperledger.fabric.sdk.Channel;
import org.hyperledger.fabric.sdk.Enrollment;
import org.hyperledger.fabric.sdk.EventHub;
import org.hyperledger.fabric.sdk.HFClient;
import org.hyperledger.fabric.sdk.Orderer;
import org.hyperledger.fabric.sdk.Peer;
import org.hyperledger.fabric.sdk.ProposalResponse;
import org.hyperledger.fabric.sdk.QueryByChaincodeRequest;
import org.hyperledger.fabric.sdk.exception.CryptoException;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.hyperledger.fabric.sdk.exception.TransactionException;
import org.hyperledger.fabric.sdk.security.CryptoSuite;
import org.hyperledger.fabric_ca.sdk.HFCAClient;
import org.hyperledger.fabric_ca.sdk.RegistrationRequest;
import org.springframework.stereotype.Service;

import com.bridgeit.model.TradeUser;

@Service
public interface TradeService {
//	HFCAClient caClient = getHfCaClient("http://localhost:7054", null);

	TradeUser getAdmin(HFCAClient caClient) throws Exception;
	TradeUser getUser(HFCAClient caClient, TradeUser registrar, String userId) throws Exception;
	void queryBlockChain(HFClient client) throws InvalidArgumentException, ProposalException;
	
	TradeUser tradeUserByteToString(byte[] obj) throws ClassNotFoundException, IOException;

}
