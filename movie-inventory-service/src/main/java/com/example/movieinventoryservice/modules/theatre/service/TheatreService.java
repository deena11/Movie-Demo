package com.example.movieinventoryservice.modules.theatre.service;

import java.util.List;

import com.example.movieinventoryservice.entity.Theatre;
import com.example.movieinventoryservice.exception.EmptyListException;
import com.example.movieinventoryservice.exception.RecordNotAddedException;
import com.example.movieinventoryservice.exception.RecordNotDeletedException;
import com.example.movieinventoryservice.exception.RecordNotFoundException;
import com.example.movieinventoryservice.exception.RecordNotUpdatedException;
import com.example.movieinventoryservice.exception.ServiceException;

public interface TheatreService {
	
	public Theatre addTheatre(Theatre theatre) throws RecordNotAddedException,ServiceException;
	
	public void deleteTheatre(int theatreId) throws RecordNotDeletedException,ServiceException;
	
	public Theatre updateTheatre(Theatre theatre) throws RecordNotUpdatedException,ServiceException;
	
	public Theatre getTheatreById(int theatreId) throws RecordNotFoundException,ServiceException;
	
	public List<Theatre> getAllTheatre() throws EmptyListException,ServiceException;


}
