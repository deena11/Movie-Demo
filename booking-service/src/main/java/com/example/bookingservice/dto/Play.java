package com.example.bookingservice.dto;
import java.io.Serializable;
import java.sql.Date;

public class Play implements Serializable{
	private int id;
    private Date startTiming;
    private  Date endTiming;
    private Movie movie;
    private Screen screen;
    private String date;
    private int seatsAvailable;
    
	public Play() {
		super();
	}

	public Play(int id, Date startTiming, Date endTiming, Movie movie, Screen screen, String date, int seatsAvailable) {
		super();
		this.id = id;
		this.startTiming = startTiming;
		this.endTiming = endTiming;
		this.movie = movie;
		this.screen = screen;
		this.date = date;
		this.seatsAvailable = seatsAvailable;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getStartTiming() {
		return startTiming;
	}

	public void setStartTiming(Date startTiming) {
		this.startTiming = startTiming;
	}

	public Date getEndTiming() {
		return endTiming;
	}

	public void setEndTiming(Date endTiming) {
		this.endTiming = endTiming;
	}

	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}

	public Screen getScreen() {
		return screen;
	}

	public void setScreen(Screen screen) {
		this.screen = screen;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getSeatsAvailable() {
		return seatsAvailable;
	}

	public void setSeatsAvailable(int seatsAvailable) {
		this.seatsAvailable = seatsAvailable;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Play [id=");
		builder.append(id);
		builder.append(", startTiming=");
		builder.append(startTiming);
		builder.append(", endTiming=");
		builder.append(endTiming);
		builder.append(", movie=");
		builder.append(movie);
		builder.append(", screen=");
		builder.append(screen);
		builder.append(", date=");
		builder.append(date);
		builder.append(", seatsAvailable=");
		builder.append(seatsAvailable);
		builder.append("]");
		return builder.toString();
	}

	
	
	
}
