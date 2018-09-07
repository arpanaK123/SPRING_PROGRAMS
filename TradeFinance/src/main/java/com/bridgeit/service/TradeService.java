package com.bridgeit.service;

import org.hyperledger.fabric_ca.sdk.HFCAClient;
import org.springframework.stereotype.Service;

import com.bridgeit.model.TradeUser;

@Service
public interface TradeService {

	TradeUser getAdmin(HFCAClient caClient);

	TradeUser tryDeserialize(String name);

	TradeUser deSerialize(String name);

	void serialize(TradeUser user);
}
