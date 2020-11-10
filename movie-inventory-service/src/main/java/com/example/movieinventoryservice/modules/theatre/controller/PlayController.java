package com.example.movieinventoryservice.modules.theatre.controller;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
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
import org.springframework.web.client.RestTemplate;

import com.example.movieinventoryservice.entity.Play;
import com.example.movieinventoryservice.exception.EmptyListException;
import com.example.movieinventoryservice.exception.RecordNotAddedException;
import com.example.movieinventoryservice.exception.RecordNotDeletedException;
import com.example.movieinventoryservice.exception.RecordNotFoundException;
import com.example.movieinventoryservice.exception.RecordNotUpdatedException;
import com.example.movieinventoryservice.exception.ServiceException;
import com.example.movieinventoryservice.modules.theatre.service.PlayService;
import com.example.movieinventoryservice.restApiConfig.ApiSuccessResponse;

/**
 * @author M1053559
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/plays/v1")
public class PlayController {

	@Autowired
	private RestTemplate restTemplate;

	@Value("${kafka.url}")
	private String kafkaUrl;

	private Logger logger = LoggerFactory.getLogger(PlayController.class);

	@Autowired
	private PlayService playService;

	private String message = "";

	/**
	 * @param playId
	 * @return
	 * @throws RecordNotFoundException
	 * @throws ServiceException
	 */
	@GetMapping("/{playId}")
	public ResponseEntity<ApiSuccessResponse> getPlay(@PathVariable("playId") int playId)
			throws RecordNotFoundException, ServiceException {

		logger.info("Fetching Play of id - " + playId + " Request is Processing");
		message = "Successfully Fetched Play Data ";

		return ResponseEntity.ok(responseBuilder(HttpStatus.OK, message, playService.getPlayById(playId)));

	}

	/**
	 * @return
	 * @throws EmptyListException
	 * @throws ServiceException
	 */
	@GetMapping("/all")
	public ResponseEntity<?> getAllPlay() throws EmptyListException, ServiceException {

		logger.info("Fetching All Play Data Request is Processing");
		message = "Successfully Fetched All Plays ";

		return ResponseEntity.ok(responseBuilder(HttpStatus.OK, message, playService.getAllPlay()));

	}

	/**
	 * @param play
	 * @return
	 * @throws RecordNotAddedException
	 * @throws ServiceException
	 */
	@PostMapping("/")
	public ResponseEntity<?> addPlay(@RequestBody Play play) throws RecordNotAddedException, ServiceException {

		logger.info("Adding Play of id - " + play.getId() + " Request is Processing");
		message = "Successfully Added Play";

		String kafkamessage =  restTemplate.exchange(kafkaUrl+"play/"+"play data Added @"+new Date().toString(), HttpMethod.GET,null,String.class).getBody();
		logger.info(kafkamessage);
		 
		 
		return ResponseEntity.ok(responseBuilder(HttpStatus.CREATED, message, playService.addPlay(play)));

	}

	/**
	 * @param play
	 * @return
	 * @throws RecordNotUpdatedException
	 * @throws ServiceException
	 */
	@PutMapping("/")
	public ResponseEntity<?> updatePlay(@RequestBody Play play) throws RecordNotUpdatedException, ServiceException {

		logger.info("Updating Play of id - " + play.getId() + " Request is Processing");
		message = "Successfully Updated Play ";

		return ResponseEntity.ok(responseBuilder(HttpStatus.OK, message, playService.updatePlay(play)));

	}

	/**
	 * @param playId
	 * @return
	 * @throws RecordNotDeletedException
	 * @throws ServiceException
	 */
	@DeleteMapping("/{playId}")
	public ResponseEntity<?> deletePlay(@PathVariable("playId") int playId) throws RecordNotDeletedException, ServiceException {

		logger.info("Delete Play of id - " + playId + " Request is Processing");
		message = "Successfully Deleted play id " + playId;
		playService.deletePlay(playId);

		return ResponseEntity.ok(responseBuilder(HttpStatus.NO_CONTENT, message, null));

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
		response.setHttpStatus(status.toString());
		response.setSuccess(true);
		response.setBody(body);

		logger.info("Request is Processed Successfully");

		return response;
	}

}