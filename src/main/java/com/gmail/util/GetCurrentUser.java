package com.gmail.util;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import com.gmail.module.User;
import com.gmail.repository.UserDao;

import lombok.Data;

@Repository
@Data
public class GetCurrentUser {

	private Object principal;

	@Autowired
	private UserDao userDao;

	public User getCurrentUser() {
		principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		
		String username = (String)principal;
		
		
		Optional<User> currentUser = userDao.findByEmail(username);

		return currentUser.get();
	}

	public void logout() {
		SecurityContextHolder.clearContext();
	}
}
