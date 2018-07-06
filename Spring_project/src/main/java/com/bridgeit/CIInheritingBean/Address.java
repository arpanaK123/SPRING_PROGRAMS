package com.bridgeit.CIInheritingBean;

public class Address {
	private String addressLine, city, state, country;

	public Address(String addressLine, String city, String state, String country) {
		super();
		this.addressLine = addressLine;
		this.city = city;
		this.state = state;
		this.country = country;
	}

	public String toString() {
		return addressLine + ", " + city + ", " + state + ", " + country;

	}

}
