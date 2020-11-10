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

import com.example.movieinventoryservice.entity.Comment;
import com.example.movieinventoryservice.exception.EmptyListException;
import com.example.movieinventoryservice.exception.RecordNotAddedException;
import com.example.movieinventoryservice.exception.RecordNotDeletedException;
import com.example.movieinventoryservice.exception.RecordNotFoundException;
import com.example.movieinventoryservice.exception.RecordNotUpdatedException;
import com.example.movieinventoryservice.exception.ServiceException;
import com.example.movieinventoryservice.modules.movies.service.CommentService;
import com.example.movieinventoryservice.restApiConfig.ApiSuccessResponse;

@RestController
@CrossOrigin
@RequestMapping("/comments/v1")
public class CommentController {

	private Logger logger = LoggerFactory.getLogger(CommentController.class);
	private String message = "";

	@Autowired
	private CommentService commentService;

	@GetMapping("/{commentId}")
	public ResponseEntity<?> getComment(@PathVariable("commentId") int commentId) throws RecordNotFoundException, ServiceException {

		logger.info("Fetching Comment of id - " + commentId + " Request is Processing");
		message = "Successfully Fetched Comment Data ";

		return ResponseEntity.ok(responseBuilder(HttpStatus.OK,message, commentService.getCommentById(commentId)));

	}

	@GetMapping("/")
	public ResponseEntity<?> getAllComment() throws EmptyListException, ServiceException {

		logger.info("Fetching All Comment Data Request is Processing");
		message = "Successfully Fetched All Comments ";

		return ResponseEntity.ok(responseBuilder(HttpStatus.OK,message, commentService.getAllComments()));

	}

	@PostMapping("/")
	public ResponseEntity<?> addComment(@RequestBody Comment comment) throws RecordNotAddedException, ServiceException {

		logger.info("Adding Comment of id - " + comment.getId() + " Request is Processing");
		message = "Successfully Added Comment for movie" ;

		return ResponseEntity.ok(responseBuilder(HttpStatus.CREATED,message, commentService.addComment(comment)));

	}

	@PutMapping("/")
	public ResponseEntity<?> updateComment(@RequestBody Comment comment) throws RecordNotUpdatedException, ServiceException {

		logger.info("Updating Comment of id - " + comment.getId() + " Request is Processing");
		message = "Successfully Updated Comment ";

		return ResponseEntity.ok(responseBuilder(HttpStatus.OK,message, commentService.updateComment(comment)));

	}

	@DeleteMapping("/{commentId}")
	public ResponseEntity<?> deleteComment(@PathVariable("commentId") int commentId) throws RecordNotDeletedException, ServiceException {

		logger.info("Delete Comment of id - " + commentId + " Request is Processing");
		message = "Successfully Deleted comment id " + commentId;
		commentService.deleteComment(commentId);

		return ResponseEntity.ok(responseBuilder(HttpStatus.NO_CONTENT,message,null ));
	}

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
