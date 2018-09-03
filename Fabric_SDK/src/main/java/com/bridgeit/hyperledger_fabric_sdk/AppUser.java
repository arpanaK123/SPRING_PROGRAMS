package com.bridgeit.hyperledger_fabric_sdk;

import java.io.Serializable;
import java.util.Set;

import org.hyperledger.fabric.sdk.Enrollment;
import org.hyperledger.fabric.sdk.User;

public class AppUser implements User, Serializable {

	private String name;
	private Set<String> roles;
	private String account;
	private String affiliation;
	private Enrollment enrollment;
	private String mspId;

	public AppUser() {
	}

	public AppUser(String name, String affiliation,String mspId, Enrollment enrollment ) {
		super();
		this.name = name;
		this.affiliation = affiliation;
		this.enrollment = enrollment;
		this.mspId = mspId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<String> getRoles() {
		return roles;
	}

	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getAffiliation() {
		return affiliation;
	}

	public void setAffiliation(String affiliation) {
		this.affiliation = affiliation;
	}

	public Enrollment getEnrollment() {
		return enrollment;
	}

	public void setEnrollment(Enrollment enrollment) {
		this.enrollment = enrollment;
	}

	public String getMspId() {
		return mspId;
	}

	public void setMspId(String mspId) {
		this.mspId = mspId;
	}

	public String toString() {
		return "AppUser{" + "name='" + name + '\'' + "\n, roles=" + roles + "\n, account='" + account + '\''
				+ "\n, affiliation='" + affiliation + '\'' + "\n, enrollment=" + enrollment + "\n, mspId='" + mspId
				+ '\'' + '}';
	}

}
