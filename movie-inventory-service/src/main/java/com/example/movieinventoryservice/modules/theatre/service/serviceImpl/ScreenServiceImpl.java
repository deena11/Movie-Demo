package com.example.movieinventoryservice.modules.theatre.service.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.movieinventoryservice.entity.Screen;
import com.example.movieinventoryservice.exception.EmptyListException;
import com.example.movieinventoryservice.exception.RecordNotAddedException;
import com.example.movieinventoryservice.exception.RecordNotDeletedException;
import com.example.movieinventoryservice.exception.RecordNotFoundException;
import com.example.movieinventoryservice.exception.RecordNotUpdatedException;
import com.example.movieinventoryservice.exception.ServiceException;
import com.example.movieinventoryservice.modules.theatre.repository.ScreenRepository;
import com.example.movieinventoryservice.modules.theatre.service.ScreenService;
import com.example.movieinventoryservice.modules.theatre.service.TheatreService;

@Service
@Transactional
public class ScreenServiceImpl implements ScreenService {
	
	private Logger logger = LoggerFactory.getLogger(ScreenServiceImpl.class);
	
	
	@Autowired
	private ScreenRepository screenRepository;
	
	@Autowired
	private TheatreService theatreService;
	

	/**
	 * @author M1053559
	 *
	 * @param screen
	 * @return
	 * @throws RecordNotAddedException
	 * @throws ServiceException
	 */
	@Override
	public Screen addScreen(Screen screen) throws RecordNotAddedException, ServiceException {
		try {
			logger.info(screen.toString());
			screen.setTheatre(theatreService.getTheatreById(screen.getTheatre().getId()));
			return screenRepository.save(screen);
		} catch (RecordNotFoundException ex) {
			logger.info(ex.getLocalizedMessage());
			throw new RecordNotAddedException("Failed To Add Screen -invalid data", ex.getCause());
		}
		catch(DataAccessException ex) {
			throw new ServiceException("Record Not Added due to internal server");
		}
	}
	/**
	 * @author M1053559
	 *
	 * @param screenId
	 * @throws RecordNotDeletedException
	 * @throws ServiceException
	 */
	@Override
	public void deleteScreen(int screenId) throws RecordNotDeletedException,ServiceException {
		try {
			if(getScreenById(screenId)!=null) {
			screenRepository.deleteById(screenId);
			}
			}catch (RecordNotFoundException e) {
				throw new RecordNotDeletedException("screend id - "+screenId+" not found");
			}
		catch (DataAccessException ex) {
			throw new ServiceException("Failed to Delete id-"+screenId, ex.getCause());
		}
	}

	/**
	 * @author M1053559
	 *
	 * @param screen
	 * @return
	 * @throws RecordNotUpdatedException
	 * @throws ServiceException
	 */
	@Override
	public Screen updateScreen(Screen screen) throws RecordNotUpdatedException,ServiceException {
		try {
			if(getScreenById(screen.getId())!=null) {
			return screenRepository.save(screen);
			}
		}catch(RecordNotFoundException ex) {
			throw new RecordNotUpdatedException("screend id - "+screen.getId()+" not found");
		}

		catch (DataAccessException ex) {
			throw new ServiceException("Failed to Update", ex.getCause());
		}
		catch(Exception ex) {
			throw new RecordNotUpdatedException("Failed to Update", ex.getCause());
		}
		return screen;
	}

	/**
	 * @author M1053559
	 *
	 * @param screenId
	 * @return
	 * @throws RecordNotFoundException
	 * @throws ServiceException
	 */
	@Override
	public Screen getScreenById(int screenId) throws RecordNotFoundException ,ServiceException{
		try {
			logger.info("Entered into Screen Service - getByid "+screenId);
			logger.info(screenRepository.findAll().toString());
			Optional<Screen> screen = screenRepository.findById(screenId);
			if (screen.isPresent()) {
				logger.info(screen.get().toString());
				return screen.get();
			} else {
				throw new RecordNotFoundException("No Record to Fetch");
			}
		} catch (DataAccessException ex) {
			throw new ServiceException("Failed to Fetch", ex.getCause());
		}
	}

	/**
	 * @author M1053559
	 *
	 * @return
	 * @throws EmptyListException
	 * @throws ServiceException
	 */
	@Override
	public List<Screen> getAllScreen() throws EmptyListException,ServiceException {
		try {
			List<Screen> screens = screenRepository.findAll();
			if (screens.size()>0) {
				return screens;
			} else {
				throw new EmptyListException("No Record to Fetch");
			}
		} catch (DataAccessException ex) {
			throw new ServiceException("Failed to Fetch", ex.getCause());
		}
	}	

}
