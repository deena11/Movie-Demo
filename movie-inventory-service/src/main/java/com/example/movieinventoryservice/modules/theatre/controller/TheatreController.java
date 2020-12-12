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
import com.example.movieinventoryservice.exception.BusinessException;
import com.example.movieinventoryservice.exception.EmptyListException;
import com.example.movieinventoryservice.exception.RecordNotDeletedException;
import com.example.movieinventoryservice.exception.RecordNotFoundException;
import com.example.movieinventoryservice.exception.RecordNotUpdatedException;
import com.example.movieinventoryservice.exception.ServiceException;
import com.example.movieinventoryservice.modules.theatre.service.TheatreService;
import com.example.movieinventoryservice.restApiConfig.ApiSuccessResponse;

/**
 * @author M1053559
 * @version v1
 * @description RestApi for Theatre Service
 */
@RestController
@CrossOrigin
@RequestMapping("/theatres/v1")
public class TheatreController {

	private Logger logger = LoggerFactory.getLogger(TheatreController.class);

	@Autowired
	private TheatreService theatreService;

	/**
	 * @param theatreId
	 * @return
	 * @throws RecordNotFoundException
	 * @throws ServiceException
	 */
	@GetMapping("/{theatreId}")
	public ResponseEntity<ApiSuccessResponse> getTheatre(@PathVariable("theatreId") int theatreId)
			throws BusinessException, ServiceException {

		String message = "";
		logger.info("Fetching Theatre of id - " + theatreId + " Request is Processing");
		message = "Successfully Fetched Theatre Data ";

		return responseBuilder(HttpStatus.OK, message, theatreService.getTheatreById(theatreId));

	}

	/**
	 * @return
	 * @throws EmptyListException
	 * @throws ServiceException
	 */
	@GetMapping("/")
	public ResponseEntity<?> getAllTheatre() throws BusinessException, ServiceException {

		String message = "";
		logger.info("Fetching All Theatre Data Request is Processing");
		message = "Successfully Fetched All Theatres ";

		return responseBuilder(HttpStatus.OK, message, theatreService.getAllTheatre());

	}

	/**
	 * @param theatre
	 * @return
	 * @throws BusinessException
	 * @throws ServiceException
	 */
	@PostMapping("/")
	public ResponseEntity<?> addTheatre(@RequestBody Theatre theatre) throws BusinessException, ServiceException {

		String message = "";
		logger.info("Adding Theatre of id - " + theatre.getId() + " Request is Processing");
		message = "Successfully Added Theatre - " + theatre.getName();

		return responseBuilder(HttpStatus.CREATED, message, theatreService.addTheatre(theatre));

	}

	/**
	 * @param theatre
	 * @return
	 * @throws RecordNotUpdatedException
	 * @throws ServiceException
	 */
	@PutMapping("/")
	public ResponseEntity<?> updateTheatre(@RequestBody Theatre theatre) throws BusinessException, ServiceException {

		String message = "";
		logger.info("Updating Theatre of id - " + theatre.getId() + " Request is Processing");
		message = "Successfully Updated Theatre - " + theatre.getName();

		return responseBuilder(HttpStatus.OK, message, theatreService.updateTheatre(theatre));

	}

	/**
	 * @param theatreId
	 * @return
	 * @throws RecordNotDeletedException
	 * @throws ServiceException
	 */
	@DeleteMapping("/{theatreId}")
	public ResponseEntity<?> deleteTheatre(@PathVariable("theatreId") int theatreId)
			throws BusinessException, ServiceException {

		String message = "";
		logger.info("Delete Theatre of id - " + theatreId + " Request is Processing");
		message = "Successfully Deleted theatre id " + theatreId;

		theatreService.deleteTheatre(theatreId);
		return responseBuilder(HttpStatus.NO_CONTENT, message, null);

	}

	/**
	 * @param status
	 * @param message
	 * @param body
	 * @return
	 */
	private ResponseEntity<ApiSuccessResponse> responseBuilder(HttpStatus status, String message, Object body) {

		ApiSuccessResponse response = new ApiSuccessResponse();
		response.setError(false);
		response.setMessage(message);
		response.setHttpStatus(status.toString());
		response.setSuccess(true);
		response.setBody(body);

		logger.info("Request is Processed Successfully");

		return ResponseEntity.ok(response);
	}

}
