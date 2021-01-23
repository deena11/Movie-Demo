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
import com.example.movieinventoryservice.exception.BusinessException;
import com.example.movieinventoryservice.exception.EmptyListException;
import com.example.movieinventoryservice.exception.RecordNotDeletedException;
import com.example.movieinventoryservice.exception.RecordNotFoundException;
import com.example.movieinventoryservice.exception.RecordNotUpdatedException;
import com.example.movieinventoryservice.exception.ServiceException;
import com.example.movieinventoryservice.modules.theatre.service.ScreenService;
import com.example.movieinventoryservice.restApiConfig.ApiSuccessResponse;

/**
 * @author M1053559
 * @version v1
 * @description RestApi for Screen Service
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/screens/v1")
public class ScreenController {

	private Logger logger = LoggerFactory.getLogger(ScreenController.class);

	@Autowired
	private ScreenService screenService;

	/**
	 * @param screenId
	 * @return
	 * @throws RecordNotFoundException
	 * @throws ServiceException
	 */
	@GetMapping("/{screenId}")
	public ResponseEntity<ApiSuccessResponse> getScreen(@PathVariable("screenId") int screenId)
			throws BusinessException, ServiceException {

		String message = "";
		logger.info("Fetching Screen of id - {} ", screenId);
		message = "Successfully Fetched Screen Data ";

		return responseBuilder(HttpStatus.OK, message, screenService.getScreenById(screenId));

	}

	/**
	 * @return
	 * @throws EmptyListException
	 * @throws ServiceException
	 */
	@GetMapping("/")
	public ResponseEntity<ApiSuccessResponse> getAllScreen() throws BusinessException, ServiceException {

		String message = "";
		logger.info("Fetching All Screen Data Request is Processing");
		message = "Successfully Fetched All Screens ";

		return responseBuilder(HttpStatus.OK, message, screenService.getAllScreen());

	}

	/**
	 * @param screen
	 * @return
	 * @throws BusinessException
	 * @throws ServiceException
	 */
	@PostMapping("/")
	public ResponseEntity<ApiSuccessResponse> addScreen(@RequestBody Screen screen)
			throws BusinessException, ServiceException {

		String message = "";
		logger.info("Adding Screen of id - {}", screen.getId());
		message = "Successfully Added Screen - " + screen.getName();

		return responseBuilder(HttpStatus.CREATED, message, screenService.addScreen(screen));

	}

	/**
	 * @param screen
	 * @return
	 * @throws RecordNotUpdatedException
	 * @throws ServiceException
	 */
	@PutMapping("/")
	public ResponseEntity<ApiSuccessResponse> updateScreen(@RequestBody Screen screen)
			throws BusinessException, ServiceException {

		String message = "";
		logger.info("Updating Screen of id - {}", screen.getId());
		message = "Successfully Updated Screen - " + screen.getName();

		return responseBuilder(HttpStatus.OK, message, screenService.updateScreen(screen));

	}

	/**
	 * @param screenId
	 * @return
	 * @throws RecordNotDeletedException
	 * @throws ServiceException
	 */
	@DeleteMapping("/{screenId}")
	public ResponseEntity<ApiSuccessResponse> deleteScreen(@PathVariable("screenId") int screenId)
			throws BusinessException, ServiceException {

		String message = "";
		logger.info("Delete Screen of id - {}", screenId);
		message = "Successfully Deleted screen id " + screenId;
		screenService.deleteScreen(screenId);
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