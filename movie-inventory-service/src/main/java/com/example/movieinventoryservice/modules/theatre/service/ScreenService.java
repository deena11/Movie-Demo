package com.example.movieinventoryservice.modules.theatre.service;

import java.util.List;

import com.example.movieinventoryservice.entity.Screen;
import com.example.movieinventoryservice.exception.EmptyListException;
import com.example.movieinventoryservice.exception.RecordNotAddedException;
import com.example.movieinventoryservice.exception.RecordNotDeletedException;
import com.example.movieinventoryservice.exception.RecordNotFoundException;
import com.example.movieinventoryservice.exception.RecordNotUpdatedException;
import com.example.movieinventoryservice.exception.ServiceException;

public interface ScreenService {
	public Screen addScreen(Screen screen) throws RecordNotAddedException,ServiceException;

	public void deleteScreen(int screenId) throws RecordNotDeletedException,ServiceException;

	public Screen updateScreen(Screen screen) throws RecordNotUpdatedException,ServiceException;

	public Screen getScreenById(int screenId) throws RecordNotFoundException,ServiceException;

	public List<Screen> getAllScreen() throws EmptyListException,ServiceException;

}
