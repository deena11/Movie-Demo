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

import com.example.movieinventoryservice.entity.Screen;
import com.example.movieinventoryservice.exception.EmptyListException;
import com.example.movieinventoryservice.exception.RecordNotAddedException;
import com.example.movieinventoryservice.exception.RecordNotDeletedException;
import com.example.movieinventoryservice.exception.RecordNotFoundException;
import com.example.movieinventoryservice.exception.RecordNotUpdatedException;
import com.example.movieinventoryservice.exception.ServiceException;
import com.example.movieinventoryservice.modules.theatre.service.ScreenService;
import com.example.movieinventoryservice.restApiConfig.ApiSuccessResponse;

@RestController
@CrossOrigin
@RequestMapping("/screens/v1")
public class ScreenController {

	private Logger logger = LoggerFactory.getLogger(ScreenController.class);

	@Autowired
	private ScreenService screenService;

	private String message = "";

	@GetMapping("/{screenId}")
	public ResponseEntity<ApiSuccessResponse> getScreen(@PathVariable("screenId") int screenId)
			throws RecordNotFoundException, ServiceException {

		logger.info("Fetching Screen of id - " + screenId + " Request is Processing");
		message = "Successfully Fetched Screen Data ";

		return ResponseEntity.ok(responseBuilder(HttpStatus.OK, message, screenService.getScreenById(screenId)));

	}

	@GetMapping("/")
	public ResponseEntity<?> getAllScreen() throws EmptyListException, ServiceException {

		logger.info("Fetching All Screen Data Request is Processing");
		message = "Successfully Fetched All Screens ";

		return ResponseEntity.ok(responseBuilder(HttpStatus.OK, message, screenService.getAllScreen()));

	}

	@PostMapping("/")
	public ResponseEntity<?> addScreen(@RequestBody Screen screen) throws RecordNotAddedException, ServiceException {

		logger.info("Adding Screen of id - " + screen.getId() + " Request is Processing");
		message = "Successfully Added Screen - " + screen.getName();

		return ResponseEntity.ok(responseBuilder(HttpStatus.CREATED, message, screenService.addScreen(screen)));

	}

	@PutMapping("/")
	public ResponseEntity<?> updateScreen(@RequestBody Screen screen)
			throws RecordNotUpdatedException, ServiceException {

		logger.info("Updating Screen of id - " + screen.getId() + " Request is Processing");
		message = "Successfully Updated Screen - " + screen.getName();

		return ResponseEntity.ok(responseBuilder(HttpStatus.OK, message, screenService.updateScreen(screen)));

	}

	@DeleteMapping("/{screenId}")
	public ResponseEntity<?> deleteScreen(@PathVariable("screenId") int screenId) throws RecordNotDeletedException, ServiceException {

		logger.info("Delete Screen of id - " + screenId + " Request is Processing");
		message = "Successfully Deleted screen id " + screenId;
		screenService.deleteScreen(screenId);
		return ResponseEntity.ok(responseBuilder(HttpStatus.NO_CONTENT, message, null));

	}

	public ApiSuccessResponse responseBuilder(HttpStatus status, String message, Object body) {

		ApiSuccessResponse response = new ApiSuccessResponse();
		response.setError(false);
		response.setMessage(message);
		response.setHttpStatus(status.toString());
		response.setSuccess(true);
		response.setBody(body);

		logger.info("Request is Processed Successfully");

		return response;
	}

}