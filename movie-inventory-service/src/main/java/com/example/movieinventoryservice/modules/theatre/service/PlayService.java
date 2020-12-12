package com.example.movieinventoryservice.modules.theatre.service;

import java.util.List;

import com.example.movieinventoryservice.entity.Play;
import com.example.movieinventoryservice.exception.BusinessException;
import com.example.movieinventoryservice.exception.ServiceException;

public interface PlayService {

	public Play addPlay(Play play) throws BusinessException,ServiceException;

	public void deletePlay(int playId) throws BusinessException,ServiceException;

	public Play updatePlay(Play play) throws BusinessException,ServiceException;

	public Play getPlayById(int playId) throws BusinessException,ServiceException;

	public List<Play> getAllPlay() throws BusinessException,ServiceException;

}
