package com.example.userservice.exception.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.example.userservice.exception.EmptyListException;
import com.example.userservice.exception.BusinessException;
import com.example.userservice.exception.NoSuchUserException;
import com.example.userservice.exception.ServiceException;
import com.example.userservice.restApiConfig.ApiErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	private ResponseEntity<Object> buildResponseEntity(ApiErrorResponse response, HttpStatus httpStatus) {

		return ResponseEntity.status(HttpStatus.OK).header("status", String.valueOf(httpStatus)).body(response);
	}

	private static final String TYPE_STRING = "should be of type ";

	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		ApiErrorResponse response = new ApiErrorResponse();

		BindingResult binding = ex.getBindingResult();
		FieldError error = binding.getFieldError();
		String message = error.getDefaultMessage();
		response.setCause(ex.getLocalizedMessage());
		response.setMessage(message);
		response.setHttpStatus(HttpStatus.BAD_REQUEST);
		response.setError(true);

		return buildResponseEntity(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ResponseBody
	protected ResponseEntity<Object> handleMethodArgumentTypeMismatchExceptions(
			final MethodArgumentTypeMismatchException ex, WebRequest request) {
		ApiErrorResponse response = new ApiErrorResponse();

		String message = ex.getName() + TYPE_STRING + ex.getRequiredType().getName();

		response.setCause(ex.getLocalizedMessage());
		response.setMessage(message);
		response.setHttpStatus(HttpStatus.BAD_REQUEST);
		response.setError(true);

		return buildResponseEntity(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(IllegalStateException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ResponseBody
	protected ResponseEntity<Object> handleIllegalStateException(final MethodArgumentTypeMismatchException ex,
			WebRequest request) {
		ApiErrorResponse response = new ApiErrorResponse();

		String message = ex.getName() + TYPE_STRING+ ex.getRequiredType().getName();

		response.setCause(ex.getLocalizedMessage());
		response.setMessage(message);
		response.setHttpStatus(HttpStatus.BAD_REQUEST);
		response.setError(true);

		return buildResponseEntity(response, HttpStatus.BAD_REQUEST);
	}

	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ResponseBody
	protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		ApiErrorResponse response = new ApiErrorResponse();
		String message = ex.getParameterName() + TYPE_STRING + ex.getParameterType();

		response.setCause(ex.getLocalizedMessage());
		response.setMessage(message);
		response.setHttpStatus(HttpStatus.BAD_REQUEST);
		response.setError(true);

		return buildResponseEntity(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
		ApiErrorResponse response = new ApiErrorResponse();

		response.setCause(ex.getLocalizedMessage());
		response.setMessage(ex.getMessage());
		logger.error("exception occured - {}",ex.getLocalizedMessage());
		response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		response.setError(true);

		return buildResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);

	}

	@ExceptionHandler(EmptyListException.class)
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public final ResponseEntity<Object> handleEmptyListFoundExceptions(EmptyListException ex, WebRequest request) {
		ApiErrorResponse response = new ApiErrorResponse();

		if (ex.getCause() != null) {
			response.setCause(ex.getCause().getMessage());
		} else {
			response.setCause(ex.getLocalizedMessage());
		}
		response.setMessage("Something went wrong !!. Record not found.");
		response.setExceptionMessage(ex.getMessage());
		response.setHttpStatus(HttpStatus.NO_CONTENT);
		response.setError(true);

		return buildResponseEntity(response, HttpStatus.NO_CONTENT);

	}

	@ExceptionHandler(NoSuchUserException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public final ResponseEntity<Object> handleNoSuchUserExceptions(NoSuchUserException ex, WebRequest request) {
		ApiErrorResponse response = new ApiErrorResponse();

		if (ex.getCause() != null) {
			response.setCause(ex.getCause().getMessage());
		} else {
			response.setCause(ex.getLocalizedMessage());
		}
		response.setMessage("Something went wrong !!. Record not found.");
		response.setExceptionMessage(ex.getMessage());
		response.setHttpStatus(HttpStatus.NOT_FOUND);
		response.setError(true);

		return buildResponseEntity(response, HttpStatus.NOT_FOUND);

	}

	@ExceptionHandler(BusinessException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public final ResponseEntity<Object> handleRecordNotCreatedExceptions(BusinessException ex, WebRequest request) {
		ApiErrorResponse response = new ApiErrorResponse();

		if (ex.getCause() != null) {
			response.setCause(ex.getCause().getMessage());
		} else {
			response.setCause(ex.getLocalizedMessage());
		}
		response.setMessage("Bad input Request");
		response.setExceptionMessage(ex.getMessage());
		response.setHttpStatus(HttpStatus.BAD_REQUEST);
		response.setError(true);

		return buildResponseEntity(response, HttpStatus.BAD_REQUEST);

	}

	@ExceptionHandler(ServiceException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public final ResponseEntity<Object> handleRecordNotDeletedExceptions(ServiceException ex, WebRequest request) {
		ApiErrorResponse response = new ApiErrorResponse();

		if (ex.getCause() != null) {
			response.setCause(ex.getCause().getMessage());
		} else {
			response.setCause(ex.getLocalizedMessage());
		}
		response.setMessage("Something went wrong.");
		response.setExceptionMessage(ex.getMessage());
		response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		response.setError(true);

		return buildResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);

	}

	@ExceptionHandler(IllegalArgumentException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public final ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex,
			WebRequest request) {
		ApiErrorResponse response = new ApiErrorResponse();

		if (ex.getCause() != null) {
			response.setCause(ex.getCause().getMessage());
		} else {
			response.setCause(ex.getLocalizedMessage());
		}
		response.setMessage("IllegalArgumentException - Something went wrong");
		response.setExceptionMessage(ex.getMessage());
		response.setHttpStatus(HttpStatus.BAD_REQUEST);
		response.setError(true);

		return buildResponseEntity(response, HttpStatus.BAD_REQUEST);

	}

	@ExceptionHandler(NullPointerException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public final ResponseEntity<Object> handleNullPointerException(NullPointerException ex, WebRequest request) {
		ApiErrorResponse response = new ApiErrorResponse();

		if (ex.getCause() != null) {
			response.setCause(ex.getCause().getMessage());
		} else {
			response.setCause(ex.getLocalizedMessage());
		}
		logger.error("exception occured - Nullpointer- ");
		response.setMessage("Something went wrong. Object is empty or null.");
		response.setExceptionMessage(ex.getMessage());
		response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		response.setError(true);

		return buildResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);

	}

}
