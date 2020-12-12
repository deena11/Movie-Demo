package com.example.movieinventoryservice.modules.theatre.service;

import java.util.List;

import com.example.movieinventoryservice.entity.Screen;
import com.example.movieinventoryservice.exception.BusinessException;
import com.example.movieinventoryservice.exception.ServiceException;

public interface ScreenService {
	public Screen addScreen(Screen screen) throws BusinessException, ServiceException;

	public void deleteScreen(int screenId) throws BusinessException, ServiceException;

	public Screen updateScreen(Screen screen) throws BusinessException, ServiceException;

	public Screen getScreenById(int screenId) throws BusinessException, ServiceException;

	public List<Screen> getAllScreen() throws BusinessException, ServiceException;

}
