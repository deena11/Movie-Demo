package com.example.movieinventoryservice.modules.theatre.service.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.movieinventoryservice.entity.Play;
import com.example.movieinventoryservice.entity.Screen;
import com.example.movieinventoryservice.exception.BusinessException;
import com.example.movieinventoryservice.exception.ServiceException;
import com.example.movieinventoryservice.modules.movies.service.MovieService;
import com.example.movieinventoryservice.modules.theatre.repository.PlayRepository;
import com.example.movieinventoryservice.modules.theatre.service.PlayService;
import com.example.movieinventoryservice.modules.theatre.service.ScreenService;

/**
 * @author M1053559
 * @description business logic for play service
 */
@Service
@Transactional
public class PlayServiceImpl implements PlayService{
	
	
	@Autowired
	private PlayRepository playRepository;
	
	@Autowired
	private ScreenService screenService;
	
	@Autowired
	private MovieService movieService;
	
	private Logger logger = LoggerFactory.getLogger(PlayService.class);
	

	/**
	 * @author M1053559
	 *
	 * @param play
	 * @return
	 * @throws BusinessException
	 * @throws ServiceException
	 */
	@Override
	public Play addPlay(Play play) throws BusinessException,ServiceException {
		try {
			Screen screen = screenService.getScreenById(play.getScreen().getId());
			play.setScreen(screen);
			play.setMovie(movieService.getMovieById(play.getMovie().getId()));
			logger.info(screen.toString());
			play.setSeatsAvailable(screen.getTotalSeatsPerScreen());
			logger.info(play.toString());
			return playRepository.save(play);
		} catch (DataAccessException ex) {
			throw new ServiceException("Failed To Add Play Internal error", ex.getCause());
		}
		catch(Exception ex) {
			throw new BusinessException("Failed To Add Play", ex.getCause());
		}
	}

	/**
	 * @author M1053559
	 *
	 * @param playId
	 * @throws BusinessException
	 * @throws ServiceException
	 */
	@Override
	public void deletePlay(int playId) throws BusinessException,ServiceException {
		try {
			if(getPlayById(playId)!=null) {
			playRepository.deleteById(playId);
			}
			}catch (BusinessException e) {
				throw new BusinessException("playd id - "+playId+" not found");
			}
		catch (DataAccessException ex) {
			throw new ServiceException("Failed to Delete id-"+playId, ex.getCause());
		}
	}

	/**
	 * @author M1053559
	 *
	 * @param play
	 * @return
	 * @throws BusinessException
	 * @throws ServiceException
	 */
	@Override
	public Play updatePlay(Play play) throws BusinessException,ServiceException {
		try {
			if(getPlayById(play.getId())!=null) {
			return playRepository.save(play);
			}
		}catch(BusinessException ex) {
			throw new BusinessException("playd id - "+play.getId()+" not found");
		}

		catch (DataAccessException ex) {
			throw new ServiceException("Failed to Update", ex.getCause());
		}
		catch(Exception ex) {
			throw new BusinessException("Failed to Update", ex.getCause());
		}
		return play;
	}

	/**
	 * @author M1053559
	 *
	 * @param playId
	 * @return
	 * @throws BusinessException
	 * @throws ServiceException
	 */
	@Override
	public Play getPlayById(int playId) throws BusinessException ,ServiceException{
		try {
			logger.info("Entered into Play Service - getByid "+playId);
			logger.info(playRepository.findAll().toString());
			Optional<Play> play = playRepository.findById(playId);
			if (play.isPresent()) {
				logger.info(play.get().toString());
				return play.get();
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
	public List<Play> getAllPlay() throws BusinessException,ServiceException {
		try {
			List<Play> plays = playRepository.findAll();
			if (plays.size()>0) {
				return plays;
			} else {
				throw new BusinessException("No Record to Fetch");
			}
		} catch (DataAccessException ex) {
			throw new ServiceException("Failed to Fetch", ex.getCause());
		}
	}

	
}
