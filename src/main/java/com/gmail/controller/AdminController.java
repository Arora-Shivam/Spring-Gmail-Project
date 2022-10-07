package com.gmail.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gmail.module.User;
import com.gmail.repository.UserDao;
import com.gmail.service.AdminService;

@RestController
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	AdminService adminService;
	@GetMapping("/user")
	public ResponseEntity<List<User>> getAllUsers(){
		
		List<User> users=adminService.getAllUsers();
		
		return new ResponseEntity<>(users,HttpStatus.OK);
		
	}
	
	@DeleteMapping("/user/delete/{id}")
	public ResponseEntity<String> deleteUser(@PathVariable String id){
		
		adminService.deleteUser(id);
		return new ResponseEntity<String>("User Deleted Successfully",HttpStatus.OK);
		
	}
	 
	@GetMapping("/user/{token}")
	public ResponseEntity<List<User>> searchUsers(@PathVariable("token") String token){
		
		List<User> users=adminService.serchUser(token);
		return new ResponseEntity<List<User>>(users,HttpStatus.FOUND);
	}
	
//	 @PostMapping("/admin")
//	    public ResponseEntity<String> addUser(@RequestBody User user){
//	    	user.setRole("ROLE_ADMIN");
//	    	boolean response = userService.addUser(user);
//	        
//	        return new ResponseEntity<>("user added", HttpStatus.ACCEPTED);
//	    }
//


	
}
