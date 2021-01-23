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
import com.example.userservice.exception.BusinessException;
import com.example.userservice.exception.ServiceException;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.service.UserService;

/**
 * @author M1053559
 *
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	TokenStore tokenStore;

	private static final String EXCEPTION_MESSAGE = "Exception message";

	private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	/*
	 * @author M1053559
	 *
	 * @param user
	 * 
	 * @return
	 * 
	 * @throws BusinessException
	 * 
	 * @throws ServiceException
	 */
	@Override
	public User createUser(User user) throws BusinessException, ServiceException {

		try {
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			if (!validUser(user.getEmail())) {
				logger.info("email id - {0} is valid", user.getEmail());

				return userRepository.save(user);
			} else {
				throw new BusinessException("Email Already Exist");
			}
		} catch (DataAccessException ex) {

			logger.error(" - {0}", ex.getMessage());
			throw new ServiceException("Failed to Add due to internal server err");
		}
	}

	/*
	 * @author M1053559
	 *
	 * @param user
	 * 
	 * @return
	 * 
	 * @throws BusinessException
	 * 
	 * @throws ServiceException
	 */
	@Override
	public User updateUser(User user) throws BusinessException, ServiceException {

		try {
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			if (fetchById(user.getId()) != null) {
				userRepository.save(user);
				return userRepository.save(user);
			} else {
				logger.error("user of id {0} does not exist in database", user.getId());
				throw new BusinessException("User Id does not exist cannot update");
			}
		} catch (BusinessException ex) {
			throw new BusinessException("User Id does not exist cannot update");

		} catch (DataAccessException ex) {
			logger.error(EXCEPTION_MESSAGE, ex.getMessage());
			throw new ServiceException("Failed to Update due to internal server err");
		}

	}

	/*
	 * @author M1053559
	 *
	 * @param userId
	 * 
	 * @throws BusinessException
	 * 
	 * @throws ServiceException
	 */
	@Override
	public void deleteUser(int userId) throws BusinessException, ServiceException {

		try {
			if (fetchById(userId) != null) {
				userRepository.deleteById(userId);
			} else {
				throw new BusinessException("User Id does not exist cannot delete");
			}
		} catch (BusinessException ex) {
			throw new BusinessException("User Id does not exist cannot delete");
		}

		catch (DataAccessException ex) {
			logger.error("Exception occured while Deleted user - {0}", userId);
			throw new ServiceException("Failed to delete", ex.getCause());
		}
	}

	/*
	 * @author M1053559
	 *
	 * @param userId
	 * 
	 * @return
	 * 
	 * @throws ServiceException
	 * 
	 * @throws BusinessException
	 */
	@Override
	public User fetchById(int userId) throws ServiceException, BusinessException {

		try {
			Optional<User> user = userRepository.findById(userId);
			if (user.isPresent()) {
				return user.get();
			} else {
				throw new BusinessException("user id does not exist");
			}

		} catch (DataAccessException ex) {

			logger.error(EXCEPTION_MESSAGE, ex.getMessage());
			throw new ServiceException("Failed to Fetch due to internal server err");
		}
	}

	/*
	 * @author M1053559
	 *
	 * @return
	 * 
	 * @throws BusinessException
	 * 
	 * @throws ServiceException
	 */
	@Override
	public List<User> fetchAll() throws BusinessException, ServiceException {

		try {
			List<User> user = userRepository.findAll();
			if (user.isEmpty()) {
				return user;
			} else {
				throw new BusinessException("No Data Available");
			}

		} catch (DataAccessException ex) {

			logger.error(EXCEPTION_MESSAGE, ex.getMessage());
			throw new ServiceException("Failed to fetch all due to internal server err");
		}
	}

	/*
	 * @author M1053559
	 *
	 * @param request
	 * 
	 * @throws BusinessException
	 */
	@Override
	public void logout(HttpServletRequest request) throws BusinessException {
		try {
			String authHeader = request.getHeader("Authorization");
			if (authHeader != null) {
				String tokenValue = authHeader.replace("Bearer", "").trim();
				OAuth2AccessToken accessToken = tokenStore.readAccessToken(tokenValue);
				if (accessToken == null) {
					logger.warn("Invalid Access Token");
					throw new InvalidTokenException("Invalid Access Token");
				}
				tokenStore.removeAccessToken(accessToken);
				logger.info("User Logout Process is done Successfully");

			}
		}

		catch (Exception ex) {

			logger.error("Exception occured while Logging out for user -{0}", request.getUserPrincipal().getName());

			throw new BusinessException("Failed to Logout", ex.getCause());
		}
	}

	/*
	 * @author M1053559
	 *
	 * @param email
	 * 
	 * @return
	 * 
	 * @throws ServiceException
	 */
	@Override
	public boolean validUser(String email) throws ServiceException {
		try {
			Optional<User> user = userRepository.findByEmail(email);

			return user.isPresent();
		} catch (DataAccessException ex) {
			logger.error("exception occured while trying to find user by email - {0}", email);
			throw new ServiceException("Unable to Access database");
		}
	}

}
