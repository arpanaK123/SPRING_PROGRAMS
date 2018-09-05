package com.bridgeit.model;

import java.io.Serializable;
import java.util.Set;

import org.hyperledger.fabric.sdk.Enrollment;
import org.hyperledger.fabric.sdk.User;

public class TradeUser implements User, Serializable {
	private static final long serialVersionUID = 1L;
	private String name;
	private Set<String> roles;
	private String account;
	private String affiliation;
	private Enrollment enrollment;
	private String mspId;

	public TradeUser() {
	}

	public TradeUser(String name, String affiliation, String mspId, Enrollment enrollment) {
		super();
		this.name = name;
		this.affiliation = affiliation;
		this.mspId = mspId;
		this.enrollment = enrollment;

	}

	public String getName() {

		return name;
	}

	public Set<String> getRoles() {

		return roles;
	}

	public String getAccount() {

		return account;
	}

	public String getAffiliation() {

		return affiliation;
	}

	public Enrollment getEnrollment() {

		return enrollment;
	}

	public String getMspId() {

		return mspId;
	}

	@Override
	public String toString() {
		return "AppUser{" + "name='" + name + '\'' + "\n, roles=" + roles + "\n, account='" + account + '\''
				+ "\n, affiliation='" + affiliation + '\'' + "\n, enrollment=" + enrollment + "\n, mspId='" + mspId
				+ '\'' + '}';
	}

}
