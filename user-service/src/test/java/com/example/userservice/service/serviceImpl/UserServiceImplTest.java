package com.example.userservice.service.serviceImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.exceptions.base.MockitoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.userservice.entity.User;
import com.example.userservice.exception.BusinessException;
import com.example.userservice.exception.ServiceException;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserServiceImpl.class)
public class UserServiceImplTest {

	@Autowired
	private UserService userService;

	@MockBean
	private UserRepository userRepository;
	
	@MockBean
	private PasswordEncoder passwordEncoder;
	
	@MockBean
	private TokenStore tokenStore;
	
	@MockBean
	private HttpServletRequest httpServletRequest;

	@Before
	public void setUp() throws Exception {
		List<User> users = new ArrayList<>();
		users.add(getUser());
		Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(getUser());
//		Mockito.verify(userRepository).deleteById(Mockito.anyInt());
		Mockito.when(userRepository.getOne(Mockito.anyInt())).thenReturn(getUser());
		Mockito.when(userRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(getUser()));
		Mockito.when(userRepository.findAll()).thenReturn(users);
	}

	@Test
	public void testGetUser() throws Exception {

		User user = userService.fetchById(1);
		assertTrue(user.getUsername().equalsIgnoreCase("test"));
	}

	@Test(expected=ServiceException.class)
	public void testGetUserException() throws Exception {
		Mockito.when(userRepository.findById(Mockito.anyInt())).thenThrow(Mockito.mock(DataAccessException.class));
		userService.fetchById(1);
	}

	@Test(expected=BusinessException.class)
	public void testGetUserException1() throws Exception {
		Mockito.when(userRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
		userService.fetchById(1);
	}

	@Test
	public void testGetAllUser() throws Exception {
		List<User> users = new ArrayList<>();
		users.add(getUser());
		Mockito.when(userRepository.findAll()).thenReturn(users);
		assertEquals(1,users.size());	}

	@Test(expected=ServiceException.class)
	public void testGetAllUserException() throws Exception {
		Mockito.when(userRepository.findAll()).thenThrow(Mockito.mock(DataAccessException.class));
		List<User> users=userService.fetchAll();
	}

	@Test(expected=BusinessException.class)
	public void testGetAllUserException1() throws Exception {
		Mockito.when(userRepository.findAll()).thenReturn(new ArrayList<User>());
		userService.fetchAll();
	}

	@Test
	public void testAddUser() throws Exception {
		Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.ofNullable(null));

		User user = userService.createUser(getUser());
		assertTrue(user.toString().contains("test"));

	}

	@Test(expected=BusinessException.class)
	public void testAddUserException() throws Exception {
		Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(getUser()));
		userService.createUser(getUser());
		

	}

	@Test
	public void testUpdateUser() throws Exception {

		User user = userService.updateUser(getUser());
		assertTrue(user.toString().contains("test"));

	}

	@Test(expected=BusinessException.class)
	public void testUpdateUserException1() throws Exception {

		Mockito.when(userRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
		userService.updateUser(getUser());
	}
	
	@Test(expected=ServiceException.class)
	public void testUpdateUserException() throws Exception {
		Mockito.when(userRepository.save(Mockito.any(User.class))).thenThrow(Mockito.mock(DataAccessException.class));
		userService.updateUser(getUser());
	}

	@Test
	public void testDeleteUser() throws Exception {
		userService.deleteUser(1);
		Mockito.verify(userRepository,Mockito.times(1)).deleteById(Mockito.any());
	}
	
	@Test(expected = BusinessException.class)
	public void testDeleteUserException() throws Exception {
		Mockito.when(userRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
		userService.deleteUser(1);
		Mockito.verify(userRepository,Mockito.times(1)).deleteById(Mockito.any());
	}
	
	@Test(expected = ServiceException.class)
	public void testDeleteServiceException() throws Exception {
		Mockito.when(userRepository.findById(Mockito.anyInt())).thenThrow(Mockito.mock(DataAccessException.class));
		userService.deleteUser(1);
		Mockito.verify(userRepository,Mockito.times(1)).deleteById(Mockito.any());
	}
	
	@Test
	public void validEmail() throws Exception{
		Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(getUser()));
		assertTrue(userService.validUser("test@gmail.com"));
	}
	
	@Test(expected = ServiceException.class)
	public void validEmailServiceException() throws Exception{
		Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenThrow(Mockito.mock(DataAccessException.class));
		assertTrue(userService.validUser("test@gmail.com"));
	}
	
	@Test(expected = NullPointerException.class)
	public void logoutTest() throws BusinessException {

		Mockito.when(httpServletRequest.getHeader(Mockito.anyString())).thenReturn("Bearer test1234");
		userService.logout(httpServletRequest);
	}
	
	@Test(expected = NullPointerException.class)
	public void logoutTestException() throws BusinessException {

		Mockito.when(httpServletRequest.getHeader(Mockito.anyString())).thenThrow(Mockito.mock(NullPointerException.class));
		userService.logout(httpServletRequest);
	}

	

	public User getUser() {

		com.example.userservice.entity.User user = new User();
		user.setId(1);
		user.setEmail("test@gmail.com");
		user.setUsername("test");
		user.setPassword("Test@123");

		return user;
	}
}