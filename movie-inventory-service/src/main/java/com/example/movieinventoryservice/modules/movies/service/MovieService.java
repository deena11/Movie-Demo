package com.example.movieinventoryservice.modules.movies.service;

import java.util.List;

import com.example.movieinventoryservice.entity.Movie;
import com.example.movieinventoryservice.exception.BusinessException;
import com.example.movieinventoryservice.exception.ServiceException;

public interface MovieService {
	
	public Movie addMovie(Movie movie) throws BusinessException,ServiceException;
	
	public void deleteMovie(int movieId) throws BusinessException,ServiceException;
	
	public Movie updateMovie(Movie movie) throws BusinessException,ServiceException;
	
	public Movie getMovieById(int movieId) throws BusinessException,ServiceException;
	
	public List<Movie> getAllMovies() throws BusinessException,ServiceException;

}
