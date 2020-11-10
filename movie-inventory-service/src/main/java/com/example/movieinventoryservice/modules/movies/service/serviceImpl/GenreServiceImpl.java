package com.example.movieinventoryservice.modules.movies.service.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.movieinventoryservice.entity.Genre;
import com.example.movieinventoryservice.exception.EmptyListException;
import com.example.movieinventoryservice.exception.RecordNotAddedException;
import com.example.movieinventoryservice.exception.RecordNotDeletedException;
import com.example.movieinventoryservice.exception.RecordNotFoundException;
import com.example.movieinventoryservice.exception.RecordNotUpdatedException;
import com.example.movieinventoryservice.exception.ServiceException;
import com.example.movieinventoryservice.modules.movies.repository.GenreRepository;
import com.example.movieinventoryservice.modules.movies.service.GenreService;

@Service
@Transactional
public class GenreServiceImpl implements GenreService {
	
	@Autowired
	private GenreRepository genreRepository;
	
	private Logger logger = LoggerFactory.getLogger(GenreServiceImpl.class);

	

	@Override
	public void deleteGenre(int genreId) throws RecordNotDeletedException,ServiceException {
		try {
			if(getGenreById(genreId)!=null) {
			genreRepository.deleteById(genreId);
			}
			}catch (RecordNotFoundException e) {
				throw new RecordNotDeletedException("genred id - "+genreId+" not found");
			}
		catch (DataAccessException ex) {
			throw new ServiceException("Failed to Delete id-"+genreId, ex.getCause());
		}
	}

	@Override
	public Genre updateGenre(Genre genre) throws RecordNotUpdatedException,ServiceException {
		try {
			if(getGenreById(genre.getId())!=null) {
			return genreRepository.save(genre);
			}
		}catch(RecordNotFoundException ex) {
			throw new RecordNotUpdatedException("genred id - "+genre.getId()+" not found");
		}

		catch (DataAccessException ex) {
			throw new ServiceException("Failed to Update", ex.getCause());
		}
		catch(Exception ex) {
			throw new RecordNotUpdatedException("Failed to Update", ex.getCause());
		}
		return genre;
	}

	@Override
	public Genre getGenreById(int genreId) throws RecordNotFoundException ,ServiceException{
		try {
			logger.info("Entered into Genre Service - getByid "+genreId);
			logger.info(genreRepository.findAll().toString());
			Optional<Genre> genre = genreRepository.findById(genreId);
			if (genre.isPresent()) {
				logger.info(genre.get().toString());
				return genre.get();
			} else {
				throw new RecordNotFoundException("Genre id -"+genreId+"is not present");
			}
		} catch (DataAccessException ex) {
			throw new ServiceException("Failed to Fetch", ex.getCause());
		}
	}

	@Override
	public List<Genre> getAllGenres() throws EmptyListException,ServiceException {
		try {
			List<Genre> genres = genreRepository.findAll();
			if (genres.size()>0) {
				return genres;
			} else {
				throw new EmptyListException("No Record to Fetch");
			}
		} catch (DataAccessException ex) {
			throw new ServiceException("Failed to Fetch", ex.getCause());
		}
	}	

	@Override
	public Genre addGenre(Genre genre) throws RecordNotAddedException, ServiceException {
		try {
			return genreRepository.save(genre);
		} catch (DataAccessException ex) {
			throw new ServiceException("Failed To Add Genre", ex.getCause());
		}catch(Exception ex) {
			throw new RecordNotAddedException("Invalid Data");
		}
	}

}


