package com.example.movieinventoryservice.modules.theatre.service.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.movieinventoryservice.entity.Theatre;
import com.example.movieinventoryservice.exception.BusinessException;
import com.example.movieinventoryservice.exception.ServiceException;
import com.example.movieinventoryservice.modules.theatre.repository.AddressRepository;
import com.example.movieinventoryservice.modules.theatre.repository.LocationRepository;
import com.example.movieinventoryservice.modules.theatre.repository.TheatreRepository;
import com.example.movieinventoryservice.modules.theatre.service.TheatreService;

/**
 * @author M1053559
 * @description business logic for Theatre service
 */
@Service
@Transactional
public class TheatreServiceImpl implements TheatreService {

	@Autowired
	private TheatreRepository theatreRepository;
	
	@Autowired
	private AddressRepository addressRepository;
	
	@Autowired
	private LocationRepository locationRepository;
	

	private Logger logger = LoggerFactory.getLogger(TheatreServiceImpl.class);
	

	/**
	 * @author M1053559
	 *
	 * @param theatreId
	 * @throws BusinessException
	 * @throws ServiceException
	 */
	@Override
	public void deleteTheatre(int theatreId) throws BusinessException,ServiceException {
		try {
			if(getTheatreById(theatreId)!=null) {
			theatreRepository.deleteById(theatreId);
			}
			}catch (BusinessException e) {
				throw new BusinessException("theatred id - "+theatreId+" not found");
			}
		catch (DataAccessException ex) {
			throw new ServiceException("Failed to Delete id-"+theatreId, ex.getCause());
		}
	}

	/**
	 * @author M1053559
	 *
	 * @param theatre
	 * @return
	 * @throws BusinessException
	 * @throws ServiceException
	 */
	@Override
	public Theatre updateTheatre(Theatre theatre) throws BusinessException,ServiceException {
		try {
			if(getTheatreById(theatre.getId())!=null) {
			return theatreRepository.save(theatre);
			}
		}catch(BusinessException ex) {
			throw new BusinessException("theatred id - "+theatre.getId()+" not found");
		}

		catch (DataAccessException ex) {
			throw new ServiceException("Failed to Update", ex.getCause());
		}
		catch(Exception ex) {
			throw new BusinessException("Failed to Update", ex.getCause());
		}
		return theatre;
	}

	/**
	 * @author M1053559
	 *
	 * @param theatreId
	 * @return
	 * @throws BusinessException
	 * @throws ServiceException
	 */
	@Override
	public Theatre getTheatreById(int theatreId) throws BusinessException ,ServiceException{
		try {
			logger.info("Entered into Theatre Service - getByid {}",theatreId);
			Optional<Theatre> theatre = theatreRepository.findById(theatreId);
			if (theatre.isPresent()) {
				logger.info("Theatre of id -{} is found",theatreId);
				return theatre.get();
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
	public List<Theatre> getAllTheatre() throws BusinessException,ServiceException {
		try {
			List<Theatre> theatres = theatreRepository.findAll();
			if (!theatres.isEmpty()) {
				return theatres;
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
	 * @param theatre
	 * @return
	 * @throws BusinessException
	 * @throws ServiceException
	 */
	@Override
	public Theatre addTheatre(Theatre theatre) throws BusinessException, ServiceException {
		try {
			theatre.setAddress(addressRepository.save(theatre.getAddress()));
			theatre.setLocation(locationRepository.save(theatre.getLocation()));
			return theatreRepository.save(theatre);
		} catch (DataAccessException ex) {
			throw new ServiceException("Failed To Add Theatre", ex.getCause());
		}
	     catch(Exception ex) {
	    	 throw new BusinessException(ex.getMessage(),ex.getCause());
	     }
	}
}
