package com.example.movieinventoryservice.modules.movies.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.movieinventoryservice.entity.Movie;
import com.example.movieinventoryservice.exception.EmptyListException;
import com.example.movieinventoryservice.exception.RecordNotAddedException;
import com.example.movieinventoryservice.exception.RecordNotDeletedException;
import com.example.movieinventoryservice.exception.RecordNotFoundException;
import com.example.movieinventoryservice.exception.RecordNotUpdatedException;
import com.example.movieinventoryservice.exception.ServiceException;
import com.example.movieinventoryservice.modules.movies.service.MovieService;
import com.example.movieinventoryservice.restApiConfig.ApiSuccessResponse;

/**
 * @author M1053559
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/movies/v1")
public class MovieController {

	private Logger logger = LoggerFactory.getLogger(MovieController.class);

	private String message = "";

	@Autowired
	private MovieService movieService;

	/**
	 * @param movieId
	 * @return
	 * @throws RecordNotFoundException
	 * @throws ServiceException
	 */
	@GetMapping("/{movieId}")
	public ResponseEntity<?> getMovie(@PathVariable("movieId") int movieId) throws RecordNotFoundException, ServiceException {

		logger.info("Fetching Movie of id - " + movieId + " Request is Processing");
		message = "Successfully Fetched Movie Data ";

		return ResponseEntity.ok(responseBuilder(HttpStatus.OK,message, movieService.getMovieById(movieId)));

	}

	/**
	 * @return
	 * @throws EmptyListException
	 * @throws ServiceException
	 */
	@GetMapping("/")
	public ResponseEntity<?> getAllMovie() throws EmptyListException, ServiceException {

		logger.info("Fetching All Movie Data Request is Processing");
		message = "Successfully Fetched All Movies ";

		return ResponseEntity.ok(responseBuilder(HttpStatus.OK,message, movieService.getAllMovies()));

	}

	/**
	 * @param movie
	 * @return
	 * @throws RecordNotAddedException
	 * @throws ServiceException
	 */
	@PostMapping("/")
	public ResponseEntity<?> addMovie(@RequestBody Movie movie) throws RecordNotAddedException, ServiceException {

		logger.info("Adding Movie of id - " + movie.getId() + " Request is Processing");
		message = "Successfully Added Movie - " + movie.getName();

		return ResponseEntity.ok(responseBuilder(HttpStatus.CREATED,message, movieService.addMovie(movie)));

	}

	/**
	 * @param movie
	 * @return
	 * @throws RecordNotUpdatedException
	 * @throws ServiceException
	 */
	@PutMapping("/")
	public ResponseEntity<?> updateMovie(@RequestBody Movie movie) throws RecordNotUpdatedException, ServiceException {

		logger.info("Updating Movie of id - " + movie.getId() + " Request is Processing");
		message = "Successfully Updated Movie - " + movie.getName();

		return ResponseEntity.ok(responseBuilder(HttpStatus.OK,message, movieService.updateMovie(movie)));

	}

	/**
	 * @param movieId
	 * @return
	 * @throws RecordNotDeletedException
	 * @throws ServiceException
	 */
	@DeleteMapping("/{movieId}")
	public ResponseEntity<?> deleteMovie(@PathVariable("movieId") int movieId) throws RecordNotDeletedException, ServiceException {

		logger.info("Delete Movie of id - " + movieId + " Request is Processing");
		message = "Successfully Deleted movie id " + movieId;
		movieService.deleteMovie(movieId);

		return ResponseEntity.ok(responseBuilder(HttpStatus.NO_CONTENT,message,null ));
	}

	/**
	 * @param status
	 * @param message
	 * @param body
	 * @return
	 */
	public ApiSuccessResponse responseBuilder(HttpStatus status, String message, Object body) {
		
		ApiSuccessResponse response = new ApiSuccessResponse();
		response.setError(false);
		response.setMessage(message);
		response.setHttpStatusCode(200);
		response.setHttpStatus(status.toString());
		response.setSuccess(true);
		response.setBody(body);

		logger.info("Request is Processed Successfully");

		return response;
	}

}
