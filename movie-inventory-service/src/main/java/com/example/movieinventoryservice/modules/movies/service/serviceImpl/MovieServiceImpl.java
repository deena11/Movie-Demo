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
import com.example.movieinventoryservice.entity.Movie;
import com.example.movieinventoryservice.exception.EmptyListException;
import com.example.movieinventoryservice.exception.RecordNotAddedException;
import com.example.movieinventoryservice.exception.RecordNotDeletedException;
import com.example.movieinventoryservice.exception.RecordNotFoundException;
import com.example.movieinventoryservice.exception.RecordNotUpdatedException;
import com.example.movieinventoryservice.exception.ServiceException;
import com.example.movieinventoryservice.modules.movies.repository.CastRepository;
import com.example.movieinventoryservice.modules.movies.repository.GenreRepository;
import com.example.movieinventoryservice.modules.movies.repository.MovieRepository;
import com.example.movieinventoryservice.modules.movies.service.MovieService;

/**
 * @author M1053559
 *
 */
@Service
@Transactional
public class MovieServiceImpl implements MovieService {

	private Logger logger = LoggerFactory.getLogger(MovieServiceImpl.class);

	@Autowired
	private MovieRepository movieRepository;

	@Autowired
	private CastRepository castRepository;

	@Autowired
	private GenreRepository genreRepository;

	/**
	 * @author M1053559
	 *
	 * @param movie
	 * @return
	 * @throws RecordNotAddedException
	 * @throws ServiceException
	 */
	@Override
	@Transactional
	public Movie addMovie(Movie movie) throws RecordNotAddedException, ServiceException {

		try {

			movie.getGenre().forEach(val -> {
				Optional<Integer> genreId = genreRepository.getIdByName(val.getName());
				if (genreId.isPresent()) {
					val.setId(genreId.get());
				}
			});
			movie.getGenre().forEach(v -> genreRepository.save(v));

			logger.info(movie.getGenre().toString());

			List<Cast> cast = movie.getCast();
			cast.forEach(v -> v.setId(castRepository.save(v).getId()));

			logger.info(movie.getCast().toString());

			return movieRepository.save(movie);
		} catch (DataAccessException ex) {
			System.out.println(ex.getLocalizedMessage());
			throw new ServiceException("Failed To Add Movie", ex.getCause());
		} catch (Exception ex) {
			throw new RecordNotAddedException("Invalid Data");
		}

	}

	/**
	 * @author M1053559
	 *
	 * @param movieId
	 * @throws RecordNotDeletedException
	 * @throws ServiceException
	 */
	@Override
	public void deleteMovie(int movieId) throws RecordNotDeletedException, ServiceException {
		try {
			if (getMovieById(movieId) != null) {
				movieRepository.deleteById(movieId);
			}
		} catch (RecordNotFoundException e) {
			throw new RecordNotDeletedException("movied id - " + movieId + " not found");
		} catch (DataAccessException ex) {
			throw new ServiceException("Failed to Delete id-" + movieId, ex.getCause());
		}

	}

	/**
	 * @author M1053559
	 *
	 * @param movie
	 * @return
	 * @throws RecordNotUpdatedException
	 * @throws ServiceException
	 */
	@Override
	public Movie updateMovie(Movie movie) throws RecordNotUpdatedException, ServiceException {
		try {
			if (getMovieById(movie.getId()) != null) {
				movie.getGenre().forEach(val -> {
					Optional<Integer> genreId = genreRepository.getIdByName(val.getName());
					if (genreId.isPresent()) {
						val.setId(genreId.get());
					}
				});
				movie.getGenre().forEach(v -> genreRepository.save(v));

				List<Cast> cast = movie.getCast();
				cast.forEach(v -> castRepository.save(v));

				return movieRepository.save(movie);
			} else {
				throw new RecordNotUpdatedException("invalid movie id. cannot update");
			}
		} catch (RecordNotFoundException ex) {
			throw new RecordNotUpdatedException("invalid movie id. cannot update");
		}

		catch (DataAccessException ex) {
			throw new ServiceException("Failed to Update", ex.getCause());
		}

	}

	/**
	 * @author M1053559
	 *
	 * @param movieId
	 * @return
	 * @throws RecordNotFoundException
	 * @throws ServiceException
	 */
	@Override
	public Movie getMovieById(int movieId) throws RecordNotFoundException, ServiceException {

		try {
			Optional<Movie> movie = movieRepository.findById(movieId);
			if (movie.isPresent()) {
				return movie.get();
			} else {
				throw new RecordNotFoundException("Movie of id - " + movieId + "is not present");
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
	public List<Movie> getAllMovies() throws EmptyListException, ServiceException {
		try {
			List<Movie> movies = movieRepository.findAll();
			if (movies.size() > 0) {
				return movies;
			} else {
				throw new EmptyListException("No Record to Fetch");
			}
		} catch (DataAccessException ex) {
			throw new ServiceException("Failed to Fetch", ex.getCause());
		}
	}

}
