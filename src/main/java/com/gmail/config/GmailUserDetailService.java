package com.gmail.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.gmail.exception.UserNotFoundException;
import com.gmail.module.User;
import com.gmail.repository.UserDao;

@Service
public class GmailUserDetailService implements UserDetailsService {
	@Autowired
	private UserDao userDao;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> admin = userDao.findById(username);

		if (admin.isPresent()) {
			return new SecurityUser(admin.get());
		}

		throw new UserNotFoundException("User Not Found");
	}

}
