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
import com.example.movieinventoryservice.exception.BusinessException;
import com.example.movieinventoryservice.exception.ServiceException;
import com.example.movieinventoryservice.modules.movies.repository.CastRepository;
import com.example.movieinventoryservice.modules.movies.service.CastService;

/**
 * @author M1053559
 *
 */
@Service
@Transactional
public class CastServiceImpl implements CastService {

	@Autowired
	private CastRepository castRepository;

	private Logger logger = LoggerFactory.getLogger(CastServiceImpl.class);

	/**
	 * @author M1053559
	 *
	 * @param castId
	 * @throws BusinessException
	 * @throws ServiceException
	 */
	@Override
	public void deleteCast(int castId) throws BusinessException, ServiceException {
		try {
			if (getCastById(castId) != null) {
				castRepository.deleteById(castId);
			}
		} catch (BusinessException e) {
			throw new BusinessException("castd id - " + castId + " not found");
		} catch (DataAccessException ex) {
			throw new ServiceException("Failed to Delete id-" + castId, ex.getCause());
		}
	}

	/**
	 * @author M1053559
	 *
	 * @param cast
	 * @return
	 * @throws BusinessException
	 * @throws ServiceException
	 */
	@Override
	public Cast updateCast(Cast cast) throws BusinessException, ServiceException {
		try {
			if (getCastById(cast.getId()) != null) {
				return castRepository.save(cast);
			}
		} catch (BusinessException ex) {
			throw new BusinessException("castd id - " + cast.getId() + " not found");
		}

		catch (DataAccessException ex) {
			throw new ServiceException("Failed to Update", ex.getCause());
		} catch (Exception ex) {
			throw new BusinessException("Failed to Update", ex.getCause());
		}
		return cast;
	}

	/**
	 * @author M1053559
	 *
	 * @param castId
	 * @return
	 * @throws BusinessException
	 * @throws ServiceException
	 */
	@Override
	public Cast getCastById(int castId) throws BusinessException, ServiceException {
		try {
			logger.info("Entered into Cast Service - getByid " + castId);
			logger.info(castRepository.findAll().toString());
			Optional<Cast> cast = castRepository.findById(castId);
			if (cast.isPresent()) {
				logger.info(cast.get().toString());
				return cast.get();
			} else {
				throw new BusinessException("Cast id -" + castId + "is not present");
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
	public List<Cast> getAllCasts() throws BusinessException, ServiceException {
		try {
			List<Cast> casts = castRepository.findAll();
			if (!casts.isEmpty()) {
				return casts;
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
	 * @param cast
	 * @return
	 * @throws BusinessException
	 * @throws ServiceException
	 */
	@Override
	public Cast addCast(Cast cast) throws BusinessException, ServiceException {
		try {
			return castRepository.save(cast);
		} catch (DataAccessException ex) {
			throw new ServiceException("Failed To Add Cast", ex.getCause());
		} catch (Exception ex) {
			throw new BusinessException("Invalid Data");
		}
	}

}
