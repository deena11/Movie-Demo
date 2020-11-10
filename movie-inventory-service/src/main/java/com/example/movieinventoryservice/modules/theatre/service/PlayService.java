package com.example.movieinventoryservice.modules.theatre.service;

import java.util.List;

import com.example.movieinventoryservice.entity.Play;
import com.example.movieinventoryservice.exception.EmptyListException;
import com.example.movieinventoryservice.exception.RecordNotAddedException;
import com.example.movieinventoryservice.exception.RecordNotDeletedException;
import com.example.movieinventoryservice.exception.RecordNotFoundException;
import com.example.movieinventoryservice.exception.RecordNotUpdatedException;
import com.example.movieinventoryservice.exception.ServiceException;

public interface PlayService {

	public Play addPlay(Play play) throws RecordNotAddedException,ServiceException;

	public void deletePlay(int playId) throws RecordNotDeletedException,ServiceException;

	public Play updatePlay(Play play) throws RecordNotUpdatedException,ServiceException;

	public Play getPlayById(int playId) throws RecordNotFoundException,ServiceException;

	public List<Play> getAllPlay() throws EmptyListException,ServiceException;

}
