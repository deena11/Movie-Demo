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
import com.example.movieinventoryservice.exception.EmptyListException;
import com.example.movieinventoryservice.exception.RecordNotAddedException;
import com.example.movieinventoryservice.exception.RecordNotDeletedException;
import com.example.movieinventoryservice.exception.RecordNotFoundException;
import com.example.movieinventoryservice.exception.RecordNotUpdatedException;
import com.example.movieinventoryservice.exception.ServiceException;
import com.example.movieinventoryservice.modules.movies.service.MovieService;
import com.example.movieinventoryservice.modules.theatre.repository.PlayRepository;
import com.example.movieinventoryservice.modules.theatre.service.PlayService;
import com.example.movieinventoryservice.modules.theatre.service.ScreenService;

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
	
	private String message="";

	@Override
	public Play addPlay(Play play) throws RecordNotAddedException,ServiceException {
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
			throw new RecordNotAddedException("Failed To Add Play", ex.getCause());
		}
	}

	@Override
	public void deletePlay(int playId) throws RecordNotDeletedException,ServiceException {
		try {
			if(getPlayById(playId)!=null) {
			playRepository.deleteById(playId);
			}
			}catch (RecordNotFoundException e) {
				throw new RecordNotDeletedException("playd id - "+playId+" not found");
			}
		catch (DataAccessException ex) {
			throw new ServiceException("Failed to Delete id-"+playId, ex.getCause());
		}
	}

	@Override
	public Play updatePlay(Play play) throws RecordNotUpdatedException,ServiceException {
		try {
			if(getPlayById(play.getId())!=null) {
			return playRepository.save(play);
			}
		}catch(RecordNotFoundException ex) {
			throw new RecordNotUpdatedException("playd id - "+play.getId()+" not found");
		}

		catch (DataAccessException ex) {
			throw new ServiceException("Failed to Update", ex.getCause());
		}
		catch(Exception ex) {
			throw new RecordNotUpdatedException("Failed to Update", ex.getCause());
		}
		return play;
	}

	@Override
	public Play getPlayById(int playId) throws RecordNotFoundException ,ServiceException{
		try {
			logger.info("Entered into Play Service - getByid "+playId);
			logger.info(playRepository.findAll().toString());
			Optional<Play> play = playRepository.findById(playId);
			if (play.isPresent()) {
				logger.info(play.get().toString());
				return play.get();
			} else {
				throw new RecordNotFoundException("No Record to Fetch");
			}
		} catch (DataAccessException ex) {
			throw new ServiceException("Failed to Fetch", ex.getCause());
		}
	}

	@Override
	public List<Play> getAllPlay() throws EmptyListException,ServiceException {
		try {
			List<Play> plays = playRepository.findAll();
			if (plays.size()>0) {
				return plays;
			} else {
				throw new EmptyListException("No Record to Fetch");
			}
		} catch (DataAccessException ex) {
			throw new ServiceException("Failed to Fetch", ex.getCause());
		}
	}

	
}
