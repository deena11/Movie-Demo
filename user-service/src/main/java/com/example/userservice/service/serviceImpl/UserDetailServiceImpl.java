package com.example.userservice.service.serviceImpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.userservice.entity.AuthUserDetails;
import com.example.userservice.entity.User;
import com.example.userservice.repository.UserRepository;

/**
 * @author M1053559
 *
 */
@Service("userDetailsService")
public class UserDetailServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	/*
	 * @author M1053559
	 *
	 * @param name
	 * 
	 * @return
	 * 
	 * @throws UsernameNotFoundException
	 */
	@Override
	public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {

		Optional<User> optionalUser = userRepository.findByUsername(name);

		if (optionalUser.isPresent()) {
//			System.out.println(optionalUser.get().toString());
			UserDetails userDetails = new AuthUserDetails(optionalUser.get());
			new AccountStatusUserDetailsChecker().check(userDetails);
			return userDetails;
		} else {
			throw new UsernameNotFoundException("Username or password wrong");
		}

	}

}
