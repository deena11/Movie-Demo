package com.example.movieinventoryservice.modules.theatre.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import com.example.movieinventoryservice.modules.theatre.service.PlayService;
import com.example.movieinventoryservice.restApiConfig.ApiSuccessResponse;

@RestController
@CrossOrigin
@RequestMapping("/play")
public class PlayController {

	@Autowired
	private RestTemplate restTemplate;

	@Value("${kafka.url}")
	private String kafkaUrl;

	private Logger logger = LoggerFactory.getLogger(PlayController.class);

	@Autowired
	private PlayService playService;

	private String message = "";

	@GetMapping("/{playId}")
	public ResponseEntity<ApiSuccessResponse> getPlay(@PathVariable("playId") int playId)
			throws RecordNotFoundException {

		logger.info("Fetching Play of id - " + playId + " Request is Processing");
		message = "Successfully Fetched Play Data ";

		return ResponseEntity.ok(responseBuilder(HttpStatus.OK, message, playService.getPlayById(playId)));

	}

	@GetMapping("/")
	public ResponseEntity<?> getAllPlay() throws EmptyListException {

		logger.info("Fetching All Play Data Request is Processing");
		message = "Successfully Fetched All Plays ";

		return ResponseEntity.ok(responseBuilder(HttpStatus.OK, message, playService.getAllPlays()));

	}

	@PostMapping("/")
	public ResponseEntity<?> addPlay(@RequestBody Play play) throws RecordNotAddedException {

		logger.info("Adding Play of id - " + play.getId() + " Request is Processing");
		message = "Successfully Added Play";

		return ResponseEntity.ok(responseBuilder(HttpStatus.CREATED, message, playService.addPlay(play)));

	}

	@PutMapping("/")
	public ResponseEntity<?> updatePlay(@RequestBody Play play) throws RecordNotUpdatedException {

		logger.info("Updating Play of id - " + play.getId() + " Request is Processing");
		message = "Successfully Updated Play ";

		return ResponseEntity.ok(responseBuilder(HttpStatus.ACCEPTED, message, playService.updatePlay(play)));

	}

	@DeleteMapping("/{playId}")
	public ResponseEntity<?> deletePlay(@PathVariable("playId") int playId) throws RecordNotDeletedException {

		logger.info("Delete Play of id - " + playId + " Request is Processing");
		message = "Successfully Deleted play id " + playId;

		return ResponseEntity.ok(responseBuilder(HttpStatus.NO_CONTENT, message, playService.deletePlay(playId)));

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