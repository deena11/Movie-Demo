package com.example.movieinventoryservice.modules.movies.service.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.movieinventoryservice.entity.Cast;
import com.example.movieinventoryservice.exception.EmptyListException;
import com.example.movieinventoryservice.exception.RecordNotAddedException;
import com.example.movieinventoryservice.exception.RecordNotDeletedException;
import com.example.movieinventoryservice.exception.RecordNotFoundException;
import com.example.movieinventoryservice.exception.RecordNotUpdatedException;
import com.example.movieinventoryservice.exception.ServiceException;
import com.example.movieinventoryservice.modules.movies.repository.CastRepository;
import com.example.movieinventoryservice.modules.movies.service.CastService;

/**
 * @author M1053559
 *
 */
@Service
@Transactional
public class CastServiceImpl implements CastService{
	
	@Autowired
	private CastRepository castRepository;
	
private Logger logger = LoggerFactory.getLogger(CastServiceImpl.class);

	

	/**
	 * @author M1053559
	 *
	 * @param castId
	 * @throws RecordNotDeletedException
	 * @throws ServiceException
	 */
	@Override
	public void deleteCast(int castId) throws RecordNotDeletedException,ServiceException {
		try {
			if(getCastById(castId)!=null) {
			castRepository.deleteById(castId);
			}
			}catch (RecordNotFoundException e) {
				throw new RecordNotDeletedException("castd id - "+castId+" not found");
			}
		catch (DataAccessException ex) {
			throw new ServiceException("Failed to Delete id-"+castId, ex.getCause());
		}
	}

	/**
	 * @author M1053559
	 *
	 * @param cast
	 * @return
	 * @throws RecordNotUpdatedException
	 * @throws ServiceException
	 */
	@Override
	public Cast updateCast(Cast cast) throws RecordNotUpdatedException,ServiceException {
		try {
			if(getCastById(cast.getId())!=null) {
			return castRepository.save(cast);
			}
		}catch(RecordNotFoundException ex) {
			throw new RecordNotUpdatedException("castd id - "+cast.getId()+" not found");
		}

		catch (DataAccessException ex) {
			throw new ServiceException("Failed to Update", ex.getCause());
		}
		catch(Exception ex) {
			throw new RecordNotUpdatedException("Failed to Update", ex.getCause());
		}
		return cast;
	}

	/**
	 * @author M1053559
	 *
	 * @param castId
	 * @return
	 * @throws RecordNotFoundException
	 * @throws ServiceException
	 */
	@Override
	public Cast getCastById(int castId) throws RecordNotFoundException ,ServiceException{
		try {
			logger.info("Entered into Cast Service - getByid "+castId);
			logger.info(castRepository.findAll().toString());
			Optional<Cast> cast = castRepository.findById(castId);
			if (cast.isPresent()) {
				logger.info(cast.get().toString());
				return cast.get();
			} else {
				throw new RecordNotFoundException("Cast id -"+castId+"is not present");
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
	public List<Cast> getAllCasts() throws EmptyListException,ServiceException {
		try {
			List<Cast> casts = castRepository.findAll();
			if (casts.size()>0) {
				return casts;
			} else {
				throw new EmptyListException("No Record to Fetch");
			}
		} catch (DataAccessException ex) {
			throw new ServiceException("Failed to Fetch", ex.getCause());
		}
	}	

	/**
	 * @author M1053559
	 *
	 * @param cast
	 * @return
	 * @throws RecordNotAddedException
	 * @throws ServiceException
	 */
	@Override
	public Cast addCast(Cast cast) throws RecordNotAddedException, ServiceException {
		try {
			return castRepository.save(cast);
		} catch (DataAccessException ex) {
			throw new ServiceException("Failed To Add Cast", ex.getCause());
		}catch(Exception ex) {
			throw new RecordNotAddedException("Invalid Data");
		}
	}

}


