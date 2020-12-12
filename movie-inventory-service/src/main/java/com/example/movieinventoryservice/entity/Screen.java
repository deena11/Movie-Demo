package com.example.movieinventoryservice.entity;
import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Screen implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String name;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "theatre_id")
	private Theatre theatre;
	
	private int totalSeatsPerScreen;

	public Screen() {
		super();
	}

	public Screen(int id, String name, Theatre theatre, int totalSeatsPerScreen) {
		super();
		this.id = id;
		this.name = name;
		this.theatre = theatre;
		this.totalSeatsPerScreen = totalSeatsPerScreen;
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

	public Theatre getTheatre() {
		return theatre;
	}

	public void setTheatre(Theatre theatre) {
		this.theatre = theatre;
	}

	public int getTotalSeatsPerScreen() {
		return totalSeatsPerScreen;
	}

	public void setTotalSeatsPerScreen(int totalSeatsPerScreen) {
		this.totalSeatsPerScreen = totalSeatsPerScreen;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Screen [id=");
		builder.append(id);
		builder.append(", name=");
		builder.append(name);
		builder.append(", theatre=");
		builder.append(theatre);
		builder.append(", totalSeatsPerScreen=");
		builder.append(totalSeatsPerScreen);
		builder.append("]");
		return builder.toString();
	}

	

	

	
}
