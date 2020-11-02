package com.example.userservice.service;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.userservice.entity.User;
import com.example.userservice.exception.AccessTokenRevokeException;
import com.example.userservice.exception.EmptyListException;
import com.example.userservice.exception.RecordNotAddedException;
import com.example.userservice.exception.RecordNotDeletedException;
import com.example.userservice.exception.RecordNotFoundException;
import com.example.userservice.exception.RecordNotUpdatedException;
import com.example.userservice.repository.UserRepository;

@Service
@Transactional
public class UserServiceImpl{

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	TokenStore tokenStore;

	private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	public String createUser(User user) throws RecordNotAddedException {

		try {
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			userRepository.save(user);
			return "SuccessFully Saved USer Data";
		} catch (DataAccessException ex) {
			
			logger.error("Exception Occured while Creatig a User Record");
			throw new RecordNotAddedException("Failed to Add ");
		}
	}

	public String updateUser(User user) throws RecordNotUpdatedException {

		try {
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			userRepository.save(user);
			return "SuccessFully Updated User Data";
		} catch (DataAccessException ex) {
			logger.error("Exception occured while Updated user Record of id -"+user.getId());
			throw new RecordNotUpdatedException("Failed to Update");
		}
	}

	public String deleteUser(int userId) throws RecordNotDeletedException {

		try {
			userRepository.deleteById(userId);
			return "SuccessFully Deleted User Data";
		} catch (DataAccessException ex) {
			logger.error("Exception occured while Deleted user -"+userId);
			throw new RecordNotDeletedException("Failed to delete", ex.getCause());
		}
	}

	public User fetchById(int userId) throws RecordNotFoundException {

		try {
			Optional<User> user = userRepository.findById(userId);
			if (user.isPresent()) {
				return user.get();
			} else {
				throw new RecordNotFoundException("Failed to fetch");
			}

		} catch (DataAccessException ex) {
			
			logger.error("Exception occured while Fetching user details of user id -"+userId);
			throw new RecordNotFoundException("something went wrong");
		}
	}

	public List<User> fetchAll() throws EmptyListException, RecordNotFoundException {

		try {
			List<User> user = userRepository.findAll();
			if (user.size() > 0) {
				return user;
			} else {
				throw new EmptyListException("Failed to fetch All Records");
			}

		} catch (DataAccessException ex) {
			
			logger.error("Exception Occured while Fetching all user Details");
			throw new RecordNotFoundException("something went wrong");
		}
	}
	
	
	public String logout(HttpServletRequest request) throws AccessTokenRevokeException {
		try {
		String authHeader = request.getHeader("Authorization");
		if (authHeader != null) {
			String tokenValue = authHeader.replace("Bearer", "").trim();
			OAuth2AccessToken accessToken = tokenStore.readAccessToken(tokenValue);
			if(accessToken==null) {
				logger.warn("Invalid Access Token");
				throw new InvalidTokenException("Invalid Access Token");
			}
//			logger.info(tokenValue);
			System.out.println(accessToken.toString());
//			logger.info(tokenStore.findTokensByClientIdAndUserName("", "dummy").toString());
			tokenStore.removeAccessToken(accessToken);
			logger.info("User Logout Process is done Successfully");
			
		}
		return "Successfully logged out";
		}
		
		catch(Exception ex) {
			
			logger.warn("Exception occured while Logging out for user -"+request.getUserPrincipal().getName());
			
			throw new AccessTokenRevokeException("Failed to Logout",ex.getCause());
		}
	}
}
