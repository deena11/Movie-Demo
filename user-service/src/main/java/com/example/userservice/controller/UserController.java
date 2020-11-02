package com.example.userservice.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.userservice.entity.User;
import com.example.userservice.exception.AccessTokenRevokeException;
import com.example.userservice.exception.EmptyListException;
import com.example.userservice.exception.RecordNotAddedException;
import com.example.userservice.exception.RecordNotDeletedException;
import com.example.userservice.exception.RecordNotFoundException;
import com.example.userservice.exception.RecordNotUpdatedException;
import com.example.userservice.restApiConfig.ApiSuccessResponse;
import com.example.userservice.service.UserServiceImpl;

@RestController
@RequestMapping("user")
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class UserController {

	@Autowired
	private UserServiceImpl userServiceImpl;

	private static Logger LOGGER = LoggerFactory.getLogger(UserController.class);

	private String message = "";

	@DeleteMapping("/logout")
	public ResponseEntity<?> revokeToken(HttpServletRequest request) throws AccessTokenRevokeException {
		LOGGER.info("Log out Request is Called");

		message = "Logging out process";
		String result = userServiceImpl.logout(request);

		LOGGER.info("SuccessFully Logged out user - "+ request.getUserPrincipal().getName());

		return ResponseEntity.ok(responseBuilder(message, result));
	}

	@GetMapping("/{userId}")
	@PreAuthorize("hasRole('ROLE_user') or hasRole('ROLE_admin')")
	public ResponseEntity<?> getUser(@PathVariable("userId") int userId) throws RecordNotFoundException {
		LOGGER.info("fetching user details of id - " + userId);
		ApiSuccessResponse response = new ApiSuccessResponse();
		response.setError(false);
		message = "Successfully fetched user data of id- " + userId;
		User result = userServiceImpl.fetchById(userId);

		LOGGER.info("SuccessFully Fetched user Data of id -" + userId + " building Success Respone");

		return ResponseEntity.ok(responseBuilder(message, result));
	}

	@GetMapping("/")
	@PreAuthorize("hasRole('ROLE_admin')")
	public ResponseEntity<?> getAllUser() throws EmptyListException, RecordNotFoundException {
		LOGGER.info("fetching all user details");

		message = "Successfully fetched Data";
		List<User> result = userServiceImpl.fetchAll();

		LOGGER.info("SuccessFully Fetched All users. building Success Respone");

		return ResponseEntity.ok(responseBuilder(message, result));
	}

	@PostMapping("/add")
	public ResponseEntity<?> addUser(@RequestBody User user) throws RecordNotAddedException {

		LOGGER.info("Adding user Details " + user.toString());

		message = "Successfully Added Data";
		String result = userServiceImpl.createUser(user);

		LOGGER.info("SuccessFully Added user id -" + user.getId() + " building Success Respone");

		return ResponseEntity.ok(responseBuilder(message, result));
	}

	@PutMapping("/")
	@PreAuthorize("hasRole('ROLE_user') or hasRole('ROLE_admin')")
	public ResponseEntity<?> updateUser(@RequestBody User user) throws RecordNotUpdatedException {
		LOGGER.info("updating user Details for user id -" + user.getId());

		message = "Successfully Updated Data";
		String result = userServiceImpl.updateUser(user);

		LOGGER.info("SuccessFully Updated user id -" + user.getId() + " building Success Respone");

		return ResponseEntity.ok(responseBuilder(message, result));
	}

	@DeleteMapping("/{userId}")
	@PreAuthorize("hasRole('ROLE_admin')")
	public ResponseEntity<?> deleteUser(@PathVariable("userId") int userId) throws RecordNotDeletedException {
		LOGGER.info("Deleting user of id -" + userId);

		message = "Successfully Deleted Data";
		String result = userServiceImpl.deleteUser(userId);

		LOGGER.info("SuccessFully deleted user id -" + userId + " building Success Respone");

		return ResponseEntity.ok(responseBuilder(message, result));
	}

	public ApiSuccessResponse responseBuilder(String message, Object body) {

		LOGGER.info("Success Response is Building");

		ApiSuccessResponse response = new ApiSuccessResponse();
		response.setError(false);
		response.setMessage(message);
		response.setSuccess(true);
		response.setHttpStatus(HttpStatus.OK.toString());
		response.setBody(body);

		return response;

	}

}
