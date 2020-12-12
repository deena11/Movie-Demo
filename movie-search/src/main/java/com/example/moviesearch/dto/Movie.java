package com.example.moviesearch.dto;

import java.io.Serializable;
import java.sql.Date;
import java.util.Arrays;
import java.util.List;

public class Movie implements Serializable{
	private int id;

	private String name;

	private List<Cast> cast;
	
	private int duration;
	
	private List<Genre> genre;
	
	private String synopsis;
	
	private String facts;
	
	private byte[] trailer;
	
	private String language;
	
	private Date releaseDate;
	
	private byte[] picture;
	
	private int frequentView;

	public Movie() {
		super();
	}

	public Movie(int id, String name, List<Cast> cast, int duration, List<Genre> genre, String synopsis, String facts,
			byte[] trailer, String language, Date releaseDate, byte[] picture, int frequentView) {
		super();
		this.id = id;
		this.name = name;
		this.cast = cast;
		this.duration = duration;
		this.genre = genre;
		this.synopsis = synopsis;
		this.facts = facts;
		this.trailer = trailer;
		this.language = language;
		this.releaseDate = releaseDate;
		this.picture = picture;
		this.frequentView = frequentView;
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

	public List<Cast> getCast() {
		return cast;
	}

	public void setCast(List<Cast> cast) {
		this.cast = cast;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public List<Genre> getGenre() {
		return genre;
	}

	public void setGenre(List<Genre> genre) {
		this.genre = genre;
	}

	public String getSynopsis() {
		return synopsis;
	}

	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
	}

	public String getFacts() {
		return facts;
	}

	public void setFacts(String facts) {
		this.facts = facts;
	}

	public byte[] getTrailer() {
		return trailer;
	}

	public void setTrailer(byte[] trailer) {
		this.trailer = trailer;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public byte[] getPicture() {
		return picture;
	}

	public void setPicture(byte[] picture) {
		this.picture = picture;
	}

	public int getFrequentView() {
		return frequentView;
	}

	public void setFrequentView(int frequentView) {
		this.frequentView = frequentView;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Movie [id=");
		builder.append(id);
		builder.append(", name=");
		builder.append(name);
		builder.append(", cast=");
		builder.append(cast);
		builder.append(", duration=");
		builder.append(duration);
		builder.append(", genre=");
		builder.append(genre);
		builder.append(", synopsis=");
		builder.append(synopsis);
		builder.append(", facts=");
		builder.append(facts);
		builder.append(", trailer=");
		builder.append(Arrays.toString(trailer));
		builder.append(", language=");
		builder.append(language);
		builder.append(", releaseDate=");
		builder.append(releaseDate);
		builder.append(", picture=");
		builder.append(Arrays.toString(picture));
		builder.append(", frequentView=");
		builder.append(frequentView);
		builder.append("]");
		return builder.toString();
	}

	

}
