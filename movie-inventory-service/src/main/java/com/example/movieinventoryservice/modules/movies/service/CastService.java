package com.example.movieinventoryservice.modules.movies.service;

import java.util.List;

import com.example.movieinventoryservice.entity.Cast;
import com.example.movieinventoryservice.exception.EmptyListException;
import com.example.movieinventoryservice.exception.RecordNotAddedException;
import com.example.movieinventoryservice.exception.RecordNotDeletedException;
import com.example.movieinventoryservice.exception.RecordNotFoundException;
import com.example.movieinventoryservice.exception.RecordNotUpdatedException;
import com.example.movieinventoryservice.exception.ServiceException;

public interface CastService {
	public Cast addCast(Cast cast) throws RecordNotAddedException,ServiceException;

	public void deleteCast(int castId) throws RecordNotDeletedException,ServiceException;

	public Cast updateCast(Cast cast) throws RecordNotUpdatedException,ServiceException;

	public Cast getCastById(int castId) throws RecordNotFoundException,ServiceException;

	public List<Cast> getAllCasts() throws EmptyListException,ServiceException;

}
