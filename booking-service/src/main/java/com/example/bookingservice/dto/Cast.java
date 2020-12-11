package com.example.bookingservice.dto;

import java.io.Serializable;
import java.util.Arrays;

public class Cast implements Serializable{
	private int id;
	private String name;
	private String role;
	private byte[] picture;

	public Cast() {
		super();
	}

	public Cast(int id, String name, String role, byte[] picture) {
		super();
		this.id = id;
		this.name = name;
		this.role = role;
		this.picture = picture;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public byte[] getPicture() {
		return picture;
	}

	public void setPicture(byte[] picture) {
		this.picture = picture;
	}

	@Override
	public String toString() {

		StringBuilder sb = new StringBuilder();
		sb.append("Cast [ id : " + id);
		sb.append(", name : " + name);
		sb.append(", role : " + role);
		sb.append(", picture : " + Arrays.toString(picture));
		sb.append(" ]");

		return sb.toString();
	}

}
