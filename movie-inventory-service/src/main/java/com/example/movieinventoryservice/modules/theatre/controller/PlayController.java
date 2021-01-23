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
import com.example.movieinventoryservice.exception.BusinessException;
import com.example.movieinventoryservice.exception.RecordNotDeletedException;
import com.example.movieinventoryservice.exception.RecordNotFoundException;
import com.example.movieinventoryservice.exception.RecordNotUpdatedException;
import com.example.movieinventoryservice.exception.ServiceException;
import com.example.movieinventoryservice.modules.theatre.service.PlayService;
import com.example.movieinventoryservice.restApiConfig.ApiSuccessResponse;

/**
 * @author M1053559
 * @version v1
 * @Description RestApi for Play Service
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

	/**
	 * @param playId
	 * @return
	 * @throws RecordNotFoundException
	 * @throws ServiceException
	 */
	@GetMapping("/{playId}")
	public ResponseEntity<ApiSuccessResponse> getPlay(@PathVariable("playId") int playId)
			throws BusinessException, ServiceException {

		String message = "";
		logger.info("Fetching Play of id - {} Request is Processing",playId);
		message = "Successfully Fetched Play Data ";

		return responseBuilder(HttpStatus.OK, message, playService.getPlayById(playId));

	}

	/**
	 * @return
	 * @throws BusinessException
	 * @throws ServiceException
	 */
	@GetMapping("/all")
	public ResponseEntity<ApiSuccessResponse> getAllPlay() throws BusinessException, ServiceException {

		String message = "";
		logger.info("Fetching All Play Data Request is Processing");
		message = "Successfully Fetched All Plays ";

		return responseBuilder(HttpStatus.OK, message, playService.getAllPlay());

	}

	/**
	 * @param play
	 * @return
	 * @throws BusinessException
	 * @throws ServiceException
	 */
	@PostMapping("/")
	public ResponseEntity<ApiSuccessResponse> addPlay(@RequestBody Play play) throws BusinessException, ServiceException {

		String message = "";
		logger.info("Adding Play of id - {} Request is Processing", play.getId());
		message = "Successfully Added Play";

		String kafkamessage = restTemplate.exchange(kafkaUrl + "play/" + "play data Added @" + new Date().toString(),
				HttpMethod.GET, null, String.class).getBody();
		logger.info(kafkamessage);

		return responseBuilder(HttpStatus.CREATED, message, playService.addPlay(play));

	}

	/**
	 * @param play
	 * @return
	 * @throws RecordNotUpdatedException
	 * @throws ServiceException
	 */
	@PutMapping("/")
	public ResponseEntity<ApiSuccessResponse> updatePlay(@RequestBody Play play) throws BusinessException, ServiceException {

		String message = "";
		logger.info("Updating Play of id - {} Request is Processing", play.getId() );
		message = "Successfully Updated Play ";

		return responseBuilder(HttpStatus.OK, message, playService.updatePlay(play));

	}

	/**
	 * @param playId
	 * @return
	 * @throws RecordNotDeletedException
	 * @throws ServiceException
	 */
	@DeleteMapping("/{playId}")
	public ResponseEntity<ApiSuccessResponse> deletePlay(@PathVariable("playId") int playId) throws BusinessException, ServiceException {

		String message = "";
		logger.info("Delete Play of id - {} Request is Processing",playId );
		message = "Successfully Deleted play id " + playId;
		playService.deletePlay(playId);

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