package com.gmail.service;

import java.util.List;

import com.gmail.module.User;

public interface AdminService {

	public List<User> getAllUsers();

	public List<User> serchUser(String token);

	public boolean deleteUser(String email);
}
