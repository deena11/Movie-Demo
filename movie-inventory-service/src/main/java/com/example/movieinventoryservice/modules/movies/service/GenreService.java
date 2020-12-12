package com.example.movieinventoryservice.modules.movies.service;

import java.util.List;

import com.example.movieinventoryservice.entity.Genre;
import com.example.movieinventoryservice.exception.BusinessException;
import com.example.movieinventoryservice.exception.ServiceException;

public interface GenreService {
	
	public Genre addGenre(Genre genre) throws BusinessException,ServiceException;

	public void deleteGenre(int genreId) throws BusinessException,ServiceException;

	public Genre updateGenre(Genre genre) throws BusinessException,ServiceException;

	public Genre getGenreById(int genreId) throws BusinessException,ServiceException;

	public List<Genre> getAllGenres() throws BusinessException,ServiceException;

}
