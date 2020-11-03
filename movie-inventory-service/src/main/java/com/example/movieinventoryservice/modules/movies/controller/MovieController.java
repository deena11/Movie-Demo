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
import com.example.movieinventoryservice.modules.movies.service.MovieService;
import com.example.movieinventoryservice.restApiConfig.ApiSuccessResponse;

@RestController
@CrossOrigin
@RequestMapping("/movie")
public class MovieController {

	private Logger logger = LoggerFactory.getLogger(MovieController.class);

	private String message = "";

	@Autowired
	private MovieService movieService;

	@GetMapping("/{movieId}")
	public ResponseEntity<?> getMovie(@PathVariable("movieId") int movieId) throws RecordNotFoundException {

		logger.info("Fetching Movie of id - " + movieId + " Request is Processing");
		message = "Successfully Fetched Movie Data ";

		return ResponseEntity.ok(responseBuilder(message, movieService.getMovieById(movieId)));

	}

	@GetMapping("/")
	public ResponseEntity<?> getAllMovie() throws EmptyListException {

		logger.info("Fetching All Movie Data Request is Processing");
		message = "Successfully Fetched All Movies ";

		return ResponseEntity.ok(responseBuilder(message, movieService.getAllMovies()));

	}

	@PostMapping("/")
	public ResponseEntity<?> addMovie(@RequestBody Movie movie) throws RecordNotAddedException {

		logger.info("Adding Movie of id - " + movie.getId() + " Request is Processing");
		message = "Successfully Added Movie - " + movie.getName();

		return ResponseEntity.ok(responseBuilder(message, movieService.addMovie(movie)));

	}

	@PutMapping("/")
	public ResponseEntity<?> updateMovie(@RequestBody Movie movie) throws RecordNotUpdatedException {

		logger.info("Updating Movie of id - " + movie.getId() + " Request is Processing");
		message = "Successfully Updated Movie - " + movie.getName();

		return ResponseEntity.ok(responseBuilder(message, movieService.updateMovie(movie)));

	}

	@DeleteMapping("/{movieId}")
	public ResponseEntity<?> deleteMovie(@PathVariable("movieId") int movieId) throws RecordNotDeletedException {

		logger.info("Delete Movie of id - " + movieId + " Request is Processing");
		message = "Successfully Deleted movie id " + movieId;

		return ResponseEntity.ok(responseBuilder(message, movieService.deleteMovie(movieId)));
	}

	public ApiSuccessResponse responseBuilder(String message, Object body) {

		ApiSuccessResponse response = new ApiSuccessResponse();
		response.setError(false);
		response.setMessage(message);
		response.setHttpStatusCode(200);
		response.setHttpStatus(HttpStatus.OK.toString());
		response.setSuccess(true);
		response.setBody(body);

		logger.info("Request is Processed Successfully");

		return response;
	}

}
