package com.example.movieinventoryservice.modules.movies.service;

import java.util.List;

import com.example.movieinventoryservice.entity.Movie;
import com.example.movieinventoryservice.exception.EmptyListException;
import com.example.movieinventoryservice.exception.RecordNotAddedException;
import com.example.movieinventoryservice.exception.RecordNotDeletedException;
import com.example.movieinventoryservice.exception.RecordNotFoundException;
import com.example.movieinventoryservice.exception.RecordNotUpdatedException;
import com.example.movieinventoryservice.exception.ServiceException;

public interface MovieService {
	
	public Movie addMovie(Movie movie) throws RecordNotAddedException,ServiceException;
	
	public void deleteMovie(int movieId) throws RecordNotDeletedException,ServiceException;
	
	public Movie updateMovie(Movie movie) throws RecordNotUpdatedException,ServiceException;
	
	public Movie getMovieById(int movieId) throws RecordNotFoundException,ServiceException;
	
	public List<Movie> getAllMovies() throws EmptyListException,ServiceException;

}
