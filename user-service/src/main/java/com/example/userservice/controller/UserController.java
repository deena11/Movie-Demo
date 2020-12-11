package com.example.userservice.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.userservice.entity.User;
import com.example.userservice.exception.BusinessException;
import com.example.userservice.exception.ServiceException;
import com.example.userservice.restApiConfig.ApiSuccessResponse;
import com.example.userservice.service.serviceImpl.UserServiceImpl;

/**
 * @author M1053559
 * @version v1
 * @description user controller for Accessing user Records
 * 
 */
@RestController
@RequestMapping("users/v1")
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class UserController {

	@Autowired
	private UserServiceImpl userServiceImpl;

	private static Logger LOGGER = LoggerFactory.getLogger(UserController.class);

	/**
	 * @param request
	 * @return
	 * @throws BusinessException
	 */
	@DeleteMapping("/logout")
	public ResponseEntity<?> revokeToken(HttpServletRequest request) throws BusinessException {

		String message = "";
		LOGGER.info("Log out Request is Called");

		message = "Logging out process";
		userServiceImpl.logout(request);

		LOGGER.info("SuccessFully Logged out user - " + request.getUserPrincipal().getName());

		return responseBuilder(message, null, HttpStatus.OK);
	}

	/**
	 * @param userId
	 * @return
	 * @throws ServiceException
	 * @throws BusinessException
	 */
	@GetMapping("/{userId}")
	@PreAuthorize("hasRole('ROLE_user') or hasRole('ROLE_admin')")
	public ResponseEntity<?> getUser(@PathVariable("userId") int userId) throws ServiceException, BusinessException {
		String message = "";
		LOGGER.info("fetching user details of id - " + userId);
		ApiSuccessResponse response = new ApiSuccessResponse();
		response.setError(false);
		message = "Successfully fetched user data of id- " + userId;
		User result = userServiceImpl.fetchById(userId);

		LOGGER.info("SuccessFully Fetched user Data of id -" + userId + " building Success Respone");

		return responseBuilder(message, result, HttpStatus.OK);
	}

	/**
	 * @return
	 * @throws BusinessException
	 * @throws ServiceException
	 */
	@GetMapping("/") // ()
	@PreAuthorize("hasRole('ROLE_admin')")
	public ResponseEntity<?> getAllUser() throws BusinessException, ServiceException {

		String message = "";
		LOGGER.info("fetching all user details");

		message = "Successfully fetched Data";
		List<User> result = userServiceImpl.fetchAll();

		LOGGER.info("SuccessFully Fetched All users. building Success Respone");

		return responseBuilder(message, result, HttpStatus.OK);
	}

	/**
	 * @param user
	 * @param bindingResult
	 * @return
	 * @throws BusinessException
	 * @throws ServiceException
	 */
	@PostMapping("/add")
	public ResponseEntity<?> addUser(@Valid @RequestBody User user, BindingResult bindingResult)
			throws BusinessException, ServiceException {

		if (bindingResult.hasErrors()) {
			StringBuilder errorMessage = new StringBuilder();
			List<FieldError> errors = bindingResult.getFieldErrors();
			for (FieldError error : errors) {
				errorMessage.append(error.getDefaultMessage() + " ");
			}
			LOGGER.error("Error: {}", errorMessage);
			throw new BusinessException(errorMessage.toString());
		}
		String message = "";
		LOGGER.info("Adding user Details " + user.toString());

		message = "Successfully Added Data";
		User result = userServiceImpl.createUser(user);

		LOGGER.info("SuccessFully Added user id -" + user.getId() + " building Success Respone");

		return responseBuilder(message, result, HttpStatus.OK);
	}

	/**
	 * @param user
	 * @return
	 * @throws BusinessException
	 * @throws ServiceException
	 */
	@PutMapping("/")
	@PreAuthorize("hasRole('ROLE_user') or hasRole('ROLE_admin')")
	public ResponseEntity<?> updateUser(@RequestBody User user) throws BusinessException, ServiceException {
		String message = "";
		LOGGER.info("updating user Details for user id -" + user.getId());

		message = "Successfully Updated Data";
		User result = userServiceImpl.updateUser(user);

		LOGGER.info("SuccessFully Updated user id -" + user.getId() + " building Success Respone");

		return responseBuilder(message, result, HttpStatus.OK);
	}

	/**
	 * @param userId
	 * @return
	 * @throws BusinessException
	 * @throws ServiceException
	 */
	@DeleteMapping("/{userId}")
	@PreAuthorize("hasRole('ROLE_admin')")
	public ResponseEntity<?> deleteUser(@PathVariable("userId") int userId) throws BusinessException, ServiceException {
		String message = "";
		LOGGER.info("Deleting user of id -" + userId);

		message = "Successfully Deleted Data";
		userServiceImpl.deleteUser(userId);

		LOGGER.info("SuccessFully deleted user id -" + userId + " building Success Respone");

		return responseBuilder(message, null, HttpStatus.NO_CONTENT);
	}

	/**
	 * @param message
	 * @param body
	 * @param status
	 * @return
	 */
	public ResponseEntity<ApiSuccessResponse> responseBuilder(String message, Object body, HttpStatus status) {

		LOGGER.info("Success Response is Building");

		ApiSuccessResponse response = new ApiSuccessResponse();
		response.setError(false);
		response.setMessage(message);
		response.setSuccess(true);
		response.setHttpStatus(status.toString());
		response.setBody(body);

		return ResponseEntity.ok(response);

	}

}
