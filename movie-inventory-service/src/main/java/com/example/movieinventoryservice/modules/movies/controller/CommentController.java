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
import com.example.movieinventoryservice.exception.BusinessException;
import com.example.movieinventoryservice.exception.EmptyListException;
import com.example.movieinventoryservice.exception.RecordNotDeletedException;
import com.example.movieinventoryservice.exception.RecordNotFoundException;
import com.example.movieinventoryservice.exception.RecordNotUpdatedException;
import com.example.movieinventoryservice.exception.ServiceException;
import com.example.movieinventoryservice.modules.movies.service.CommentService;
import com.example.movieinventoryservice.restApiConfig.ApiSuccessResponse;

/**
 * @author M1053559
 * @version v1
 * @description restApi for comments controller
 */
@RestController
@CrossOrigin
@RequestMapping("/comments/v1")
public class CommentController {

	private Logger logger = LoggerFactory.getLogger(CommentController.class);

	@Autowired
	private CommentService commentService;

	/**
	 * @param commentId
	 * @return
	 * @throws RecordNotFoundException
	 * @throws ServiceException
	 */
	@GetMapping("/{commentId}")
	public ResponseEntity<ApiSuccessResponse> getComment(@PathVariable("commentId") int commentId)
			throws BusinessException, ServiceException {
		String message = "";

		logger.info("Fetching Comment of id - {} Request is Processing",commentId);
		message = "Successfully Fetched Comment Data ";

		return responseBuilder(HttpStatus.OK, message, commentService.getCommentById(commentId));

	}

	/**
	 * @return
	 * @throws EmptyListException
	 * @throws ServiceException
	 */
	@GetMapping("/")
	public ResponseEntity<ApiSuccessResponse> getAllComment() throws BusinessException, ServiceException {

		String message = "";
		logger.info("Fetching All Comment Data Request is Processing");
		message = "Successfully Fetched All Comments ";

		return responseBuilder(HttpStatus.OK, message, commentService.getAllComments());

	}

	/**
	 * @param comment
	 * @return
	 * @throws BusinessException
	 * @throws ServiceException
	 */
	@PostMapping("/")
	public ResponseEntity<ApiSuccessResponse> addComment(@RequestBody Comment comment) throws BusinessException, ServiceException {
		String message = "";

		logger.info("Adding Comment of id - {} Request is Processing",comment.getId() );
		message = "Successfully Added Comment for movie";

		return responseBuilder(HttpStatus.CREATED, message, commentService.addComment(comment));

	}

	/**
	 * @param comment
	 * @return
	 * @throws RecordNotUpdatedException
	 * @throws ServiceException
	 */
	@PutMapping("/")
	public ResponseEntity<ApiSuccessResponse> updateComment(@RequestBody Comment comment) throws BusinessException, ServiceException {

		String message = "";
		logger.info("Updating Comment of id - {} Request is Processing",comment.getId() );
		message = "Successfully Updated Comment ";

		return responseBuilder(HttpStatus.OK, message, commentService.updateComment(comment));

	}

	/**
	 * @param commentId
	 * @return
	 * @throws RecordNotDeletedException
	 * @throws ServiceException
	 */
	@DeleteMapping("/{commentId}")
	public ResponseEntity<ApiSuccessResponse> deleteComment(@PathVariable("commentId") int commentId)
			throws BusinessException, ServiceException {

		String message = "";
		logger.info("Delete Comment of id - {} Request is Processing",commentId );
		message = "Successfully Deleted comment id " + commentId;
		commentService.deleteComment(commentId);

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
		response.setHttpStatusCode(200);
		response.setHttpStatus(status.toString());
		response.setSuccess(true);
		response.setBody(body);


		return ResponseEntity.ok(response);
	}

}
