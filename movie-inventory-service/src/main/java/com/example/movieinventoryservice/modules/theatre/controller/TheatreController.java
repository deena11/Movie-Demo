package com.example.movieinventoryservice.modules.theatre.controller;

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

import com.example.movieinventoryservice.entity.Theatre;
import com.example.movieinventoryservice.exception.EmptyListException;
import com.example.movieinventoryservice.exception.RecordNotAddedException;
import com.example.movieinventoryservice.exception.RecordNotDeletedException;
import com.example.movieinventoryservice.exception.RecordNotFoundException;
import com.example.movieinventoryservice.exception.RecordNotUpdatedException;
import com.example.movieinventoryservice.modules.theatre.service.TheatreService;
import com.example.movieinventoryservice.restApiConfig.ApiSuccessResponse;

@RestController
@CrossOrigin
@RequestMapping("/theatre")
public class TheatreController {

	private Logger logger = LoggerFactory.getLogger(TheatreController.class);

	@Autowired
	private TheatreService theatreService;

	private String message = "";

	@GetMapping("/{theatreId}")
	public ResponseEntity<ApiSuccessResponse> getTheatre(@PathVariable("theatreId") int theatreId)
			throws RecordNotFoundException {


		logger.info("Fetching Theatre of id - " + theatreId + " Request is Processing");
		message = "Successfully Fetched Theatre Data ";

		return ResponseEntity.ok(responseBuilder(HttpStatus.OK,message, theatreService.getTheatreById(theatreId)));

	}

	@GetMapping("/")
	public ResponseEntity<?> getAllTheatre() throws EmptyListException {

		logger.info("Fetching All Theatre Data Request is Processing");
		message = "Successfully Fetched All Theatres ";

		return ResponseEntity.ok(responseBuilder(HttpStatus.OK,message, theatreService.getAllTheatres()));

	}

	@PostMapping("/")
	public ResponseEntity<?> addTheatre(@RequestBody Theatre theatre) throws RecordNotAddedException {

		logger.info("Adding Theatre of id - " + theatre.getId() + " Request is Processing");
		message = "Successfully Added Theatre - " + theatre.getName();

		return ResponseEntity.ok(responseBuilder(HttpStatus.CREATED,message, theatreService.addTheatre(theatre)));

	}

	@PutMapping("/")
	public ResponseEntity<?> updateTheatre(@RequestBody Theatre theatre) throws RecordNotUpdatedException {

		logger.info("Updating Theatre of id - " + theatre.getId() + " Request is Processing");
		message = "Successfully Updated Theatre - " + theatre.getName();

		return ResponseEntity.ok(responseBuilder(HttpStatus.ACCEPTED,message, theatreService.updateTheatre(theatre)));

	}

	@DeleteMapping("/{theatreId}")
	public ResponseEntity<?> deleteTheatre(@PathVariable("theatreId") int theatreId) throws RecordNotDeletedException {

		logger.info("Delete Theatre of id - " + theatreId + " Request is Processing");
		message = "Successfully Deleted theatre id " + theatreId;

		return ResponseEntity.ok(responseBuilder(HttpStatus.NO_CONTENT,message, theatreService.deleteTheatre(theatreId)));
	
	}

	public ApiSuccessResponse responseBuilder(HttpStatus status, String message, Object body) {

		ApiSuccessResponse response = new ApiSuccessResponse();
		response.setError(false);
		response.setMessage(message);
		response.setHttpStatusCode(status.value());
		response.setHttpStatus(status.toString());
		response.setSuccess(true);
		response.setBody(body);

		logger.info("Request is Processed Successfully");

		return response;
	}

}
