package com.example.userservice.service.serviceImpl;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.userservice.entity.User;
import com.example.userservice.repository.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserDetailServiceImpl.class)
public class UserDetailServiceImplTest {

	@MockBean
	private UserRepository userReposiory;

	@Autowired
	private UserDetailServiceImpl userDetailService;

	@Test(expected = UsernameNotFoundException.class)
	public void loadUserDetailEmpty() {

		Mockito.when(userReposiory.findByUsername(Mockito.anyString())).thenReturn(Optional.ofNullable(null));

		userDetailService.loadUserByUsername("sample");
	}

	@Test
	public void loadUserDetail() {

		Mockito.when(userReposiory.findByUsername(Mockito.anyString())).thenReturn(Optional.of(getUser()));

		userDetailService.loadUserByUsername("sample");

	}

	private User getUser() {
		User user = new User(1, "test", "secret", "test@test.com", "1234567890", "test");
		return user;
	}
}
