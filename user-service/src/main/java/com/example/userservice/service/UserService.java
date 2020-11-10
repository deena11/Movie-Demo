package com.example.userservice.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.example.userservice.entity.User;
import com.example.userservice.exception.AccessTokenRevokeException;
import com.example.userservice.exception.EmptyListException;
import com.example.userservice.exception.InValidUserException;
import com.example.userservice.exception.NoSuchUserException;
import com.example.userservice.exception.ServiceException;

public interface UserService {
	

	public User createUser(User user) throws InValidUserException, ServiceException;

	public User updateUser(User user) throws InValidUserException, ServiceException;

	public void deleteUser(int userId) throws InValidUserException, ServiceException;

	public User fetchById(int userId) throws ServiceException, NoSuchUserException;
	
	public List<User> fetchAll() throws EmptyListException, ServiceException;

	public void logout(HttpServletRequest request) throws AccessTokenRevokeException;
	
	public boolean validUser(String email) throws  ServiceException; 
		
}


