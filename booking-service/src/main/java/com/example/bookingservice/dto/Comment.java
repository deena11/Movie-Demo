package com.example.bookingservice.dto;

import java.io.Serializable;

public class Comment implements Serializable{
	private int id;
	private String comments;
	private double rating;
	private Movie movie;

	public Comment() {
		super();
	}

	public Comment(int id, String comments, double rating, Movie movie) {
		super();
		this.id = id;
		this.comments = comments;
		this.rating = rating;
		this.movie = movie;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Comment [id=");
		builder.append(id);
		builder.append(", comments=");
		builder.append(comments);
		builder.append(", rating=");
		builder.append(rating);
		builder.append(", movie=");
		builder.append(movie);
		builder.append("]");
		return builder.toString();
	}

	
	
	
}
