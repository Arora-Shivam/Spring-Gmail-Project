package com.gmail.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gmail.exception.UserNotFoundException;
import com.gmail.module.User;
import com.gmail.repository.UserDao;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	UserDao userDao;

	@Override
	public List<User> getAllUsers() {
		// TODO Auto-generated method stub

		List<User> users = userDao.findAll();
		if (users.size() != 0)
			return users;
		else {
			throw new UserNotFoundException("No User Exist in your System");
		}
	}

	@Override
	public List<User> serchUser(String token) {
		// TODO Auto-generated method stub
		List<User> users = userDao.searchUsers(token);
		if (users.size() != 0)
			return users;
		else {
			throw new UserNotFoundException("No Such  User Exist in your System");
		}
	}

	@Override
	public boolean deleteUser(String email) {
		// TODO Auto-generated method stub
		Optional<User> optUser = userDao.findByEmail(email);
		if (optUser.isPresent()) {

			userDao.delete(optUser.get());
			return true;
		} else {
			throw new UserNotFoundException("User with this email does not exist");
		}

	}

}
