package com.example.movieinventoryservice.modules.movies.service;

import java.util.List;

import com.example.movieinventoryservice.entity.Genre;
import com.example.movieinventoryservice.exception.EmptyListException;
import com.example.movieinventoryservice.exception.RecordNotAddedException;
import com.example.movieinventoryservice.exception.RecordNotDeletedException;
import com.example.movieinventoryservice.exception.RecordNotFoundException;
import com.example.movieinventoryservice.exception.RecordNotUpdatedException;
import com.example.movieinventoryservice.exception.ServiceException;

public interface GenreService {
	
	public Genre addGenre(Genre genre) throws RecordNotAddedException,ServiceException;

	public void deleteGenre(int genreId) throws RecordNotDeletedException,ServiceException;

	public Genre updateGenre(Genre genre) throws RecordNotUpdatedException,ServiceException;

	public Genre getGenreById(int genreId) throws RecordNotFoundException,ServiceException;

	public List<Genre> getAllGenres() throws EmptyListException,ServiceException;

}
