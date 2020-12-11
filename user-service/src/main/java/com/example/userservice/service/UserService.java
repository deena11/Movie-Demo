package com.example.userservice.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.example.userservice.entity.User;
import com.example.userservice.exception.AccessTokenRevokeException;
import com.example.userservice.exception.EmptyListException;
import com.example.userservice.exception.BusinessException;
import com.example.userservice.exception.NoSuchUserException;
import com.example.userservice.exception.ServiceException;

public interface UserService {
	

	public User createUser(User user) throws BusinessException, ServiceException;

	public User updateUser(User user) throws BusinessException, ServiceException;

	public void deleteUser(int userId) throws BusinessException, ServiceException;

	public User fetchById(int userId) throws ServiceException, BusinessException;
	
	public List<User> fetchAll() throws BusinessException, ServiceException;

	public void logout(HttpServletRequest request) throws BusinessException;
	
	public boolean validUser(String email) throws  ServiceException; 
		
}


