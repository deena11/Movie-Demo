package com.example.moviesearch.dto;

import java.io.Serializable;

public class Address implements Serializable{
	private int id;
	private String street;
	private String city;
	private String state;
	private String pincode;

	public Address() {
		super();
	}

	public Address(int id, String street, String city, String state, String pincode) {
		super();
		this.id = id;
		this.street = street;
		this.city = city;
		this.state = state;
		this.pincode = pincode;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Address [id=");
		builder.append(id);
		builder.append(", street=");
		builder.append(street);
		builder.append(", city=");
		builder.append(city);
		builder.append(", state=");
		builder.append(state);
		builder.append(", pincode=");
		builder.append(pincode);
		builder.append("]");
		return builder.toString();
	}

	
	
	
	

	
}
