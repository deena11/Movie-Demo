package com.example.movieinventoryservice.modules.movies.service;

import java.util.List;

import com.example.movieinventoryservice.entity.Cast;
import com.example.movieinventoryservice.exception.BusinessException;
import com.example.movieinventoryservice.exception.ServiceException;

public interface CastService {
	public Cast addCast(Cast cast) throws BusinessException,ServiceException;

	public void deleteCast(int castId) throws BusinessException,ServiceException;

	public Cast updateCast(Cast cast) throws BusinessException,ServiceException;

	public Cast getCastById(int castId) throws BusinessException,ServiceException;

	public List<Cast> getAllCasts() throws BusinessException,ServiceException;

}
