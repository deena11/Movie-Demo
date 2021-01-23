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
import com.example.movieinventoryservice.exception.BusinessException;
import com.example.movieinventoryservice.exception.ServiceException;
import com.example.movieinventoryservice.modules.theatre.repository.ScreenRepository;
import com.example.movieinventoryservice.modules.theatre.service.ScreenService;
import com.example.movieinventoryservice.modules.theatre.service.TheatreService;

/**
 * @author M1053559
 * @description business logic for screen service
 */
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
	 * @throws BusinessException
	 * @throws ServiceException
	 */
	@Override
	public Screen addScreen(Screen screen) throws BusinessException, ServiceException {
		try {
			logger.info(screen.toString());
			screen.setTheatre(theatreService.getTheatreById(screen.getTheatre().getId()));
			return screenRepository.save(screen);
		} catch (DataAccessException ex) {
			logger.error(ex.getLocalizedMessage());
			throw new ServiceException("Record Not Added due to internal server");
		}
	}

	/**
	 * @author M1053559
	 *
	 * @param screenId
	 * @throws BusinessException
	 * @throws ServiceException
	 */
	@Override
	public void deleteScreen(int screenId) throws BusinessException, ServiceException {
		try {
			if (getScreenById(screenId) != null) {
				screenRepository.deleteById(screenId);
			}
		} catch (BusinessException e) {
			throw new BusinessException("screend id - " + screenId + " not found");
		} catch (DataAccessException ex) {
			throw new ServiceException("Failed to Delete id-" + screenId, ex.getCause());
		}
	}

	/**
	 * @author M1053559
	 *
	 * @param screen
	 * @return
	 * @throws BusinessException
	 * @throws ServiceException
	 */
	@Override
	public Screen updateScreen(Screen screen) throws BusinessException, ServiceException {
		try {
			if (getScreenById(screen.getId()) != null) {
				return screenRepository.save(screen);
			}
		} catch (BusinessException ex) {
			throw new BusinessException("screend id - " + screen.getId() + " not found");
		}

		catch (DataAccessException ex) {
			throw new ServiceException("Failed to Update", ex.getCause());
		} catch (Exception ex) {
			throw new BusinessException("Failed to Update", ex.getCause());
		}
		return screen;
	}

	/**
	 * @author M1053559
	 *
	 * @param screenId
	 * @return
	 * @throws BusinessException
	 * @throws ServiceException
	 */
	@Override
	public Screen getScreenById(int screenId) throws BusinessException, ServiceException {
		try {
			logger.info("Entered into Screen Service - getByid  {}" , screenId);
			Optional<Screen> screen = screenRepository.findById(screenId);
			if (screen.isPresent()) {
				logger.info("Screen of id {} is found",screenId);
				return screen.get();
			} else {
				throw new BusinessException("No Record to Fetch");
			}
		} catch (DataAccessException ex) {
			throw new ServiceException("Failed to Fetch", ex.getCause());
		}
	}

	/**
	 * @author M1053559
	 *
	 * @return
	 * @throws BusinessException
	 * @throws ServiceException
	 */
	@Override
	public List<Screen> getAllScreen() throws BusinessException, ServiceException {
		try {
			List<Screen> screens = screenRepository.findAll();
			if (!screens.isEmpty()) {
				return screens;
			} else {
				throw new BusinessException("No Record to Fetch");
			}
		} catch (DataAccessException ex) {
			throw new ServiceException("Failed to Fetch", ex.getCause());
		}
	}

}
