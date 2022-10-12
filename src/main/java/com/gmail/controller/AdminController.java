package com.gmail.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.gmail.module.User;
import com.gmail.service.AdminService;

@RestController
@RequestMapping("/mail/admin")
public class AdminController {

	@Autowired
	AdminService adminService;

	
	
	// Handle		 --> / mail/admin/user
	// What is does? --> Gives all the registered users 
	// Request Type? --> Get request
	// Input 		 --> None
	
	@GetMapping("/user")
	public ResponseEntity<List<User>> getAllUsers() {

		List<User> users = adminService.getAllUsers();
		return new ResponseEntity<>(users, HttpStatus.OK);

	}
	
	
	// Handle		 --> / mail/admin/user/{email}
	// What is does? --> search the users according to the email id 
	// Request Type? --> Get request
	// Input 		 --> Email  as path variable

	@GetMapping("/user/{token}")
	public ResponseEntity<List<User>> searchUsers(@PathVariable("token") String token) {

		List<User> users = adminService.serchUser(token);
		return new ResponseEntity<List<User>>(users, HttpStatus.FOUND);
	}

	


}
