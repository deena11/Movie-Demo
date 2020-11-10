package com.example.userservice.service.serviceImpl;

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
import com.example.userservice.exception.InValidUserException;
import com.example.userservice.exception.NoSuchUserException;
import com.example.userservice.exception.ServiceException;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	TokenStore tokenStore;

	private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Override
	public User createUser(User user) throws InValidUserException, ServiceException {

		try {
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			if (!validUser(user.getEmail())) {
				
				return userRepository.save(user);
			} else {
				throw new InValidUserException("Email Already Exist");
			}
		} catch (DataAccessException ex) {

			logger.error("Exception message - " + ex.getMessage());
			throw new ServiceException("Failed to Add due to internal server err");
		} 
	} // versioning

	@Override
	public User updateUser(User user) throws InValidUserException, ServiceException {

		try {
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			if (fetchById(user.getId()) != null) {
				userRepository.save(user);
				return userRepository.save(user);
			}
			else {
				throw new InValidUserException("User Id does not exist cannot update");
			}
		} catch (NoSuchUserException ex) {
			throw new InValidUserException("User Id does not exist cannot update");

		} catch (DataAccessException ex) {
			logger.error("Exception message -" + ex.getMessage());
			throw new ServiceException("Failed to Update due to internal server err");
		}

	}

	@Override
	public void deleteUser(int userId) throws InValidUserException, ServiceException {

		try {
			if (fetchById(userId) != null) {
				userRepository.deleteById(userId);
			}
			else {
				throw new InValidUserException("User Id does not exist cannot delete");
			}
		} catch (NoSuchUserException ex) {
			throw new InValidUserException("User Id does not exist cannot delete");
		}

		catch (DataAccessException ex) {
			logger.error("Exception occured while Deleted user -" + userId);
			throw new ServiceException("Failed to delete", ex.getCause());
		}
	}

	@Override
	public User fetchById(int userId) throws ServiceException, NoSuchUserException {

		try {
			Optional<User> user = userRepository.findById(userId);
			if (user.isPresent()) {
				return user.get();
			} else {
				throw new NoSuchUserException("user id does not exist");
			}

		} catch (DataAccessException ex) {

			logger.error("Exception message -" + ex.getMessage());
			throw new ServiceException("Failed to Fetch due to internal server err");
		}
	}

	@Override
	public List<User> fetchAll() throws EmptyListException, ServiceException {

		try {
			List<User> user = userRepository.findAll();
			if (user.size() > 0) {
				return user;
			} else {
				throw new EmptyListException("No Data Available");
			}

		} catch (DataAccessException ex) {

			logger.error("Exception message -" + ex.getMessage());
			throw new ServiceException("Failed to fetch all due to internal server err");
		}
	}

	@Override
	public void logout(HttpServletRequest request) throws AccessTokenRevokeException {
		try {
			String authHeader = request.getHeader("Authorization");
			if (authHeader != null) {
				String tokenValue = authHeader.replace("Bearer", "").trim();
				OAuth2AccessToken accessToken = tokenStore.readAccessToken(tokenValue);
				if (accessToken == null) {
					logger.warn("Invalid Access Token");
					throw new InvalidTokenException("Invalid Access Token");
				}
				System.out.println(accessToken.toString());
				tokenStore.removeAccessToken(accessToken);
				logger.info("User Logout Process is done Successfully");

			}
		}

		catch (Exception ex) {

			logger.warn("Exception occured while Logging out for user -" + request.getUserPrincipal().getName());

			throw new AccessTokenRevokeException("Failed to Logout", ex.getCause());
		}
	}

	@Override
	public boolean validUser(String email) throws ServiceException {
		try {
			Optional<User> user = userRepository.findByEmail(email);

			if (user.isPresent()) {
				return true;
			}
			return false;
		} catch (DataAccessException ex) {
			throw new ServiceException("Unable to Access database");
		}
	}

}
