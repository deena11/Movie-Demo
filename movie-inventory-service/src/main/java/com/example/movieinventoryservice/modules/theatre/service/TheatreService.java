package com.example.movieinventoryservice.modules.theatre.service;

import java.util.List;

import com.example.movieinventoryservice.entity.Theatre;
import com.example.movieinventoryservice.exception.BusinessException;
import com.example.movieinventoryservice.exception.ServiceException;

public interface TheatreService {

	public Theatre addTheatre(Theatre theatre) throws BusinessException, ServiceException;

	public void deleteTheatre(int theatreId) throws BusinessException, ServiceException;

	public Theatre updateTheatre(Theatre theatre) throws BusinessException, ServiceException;

	public Theatre getTheatreById(int theatreId) throws BusinessException, ServiceException;

	public List<Theatre> getAllTheatre() throws BusinessException, ServiceException;

}
