package com.gmail.controller;

import com.gmail.module.Mail;
import com.gmail.module.User;
import com.gmail.repository.UserDao;
import com.gmail.service.MailService;
import com.gmail.service.UserService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController

public class UserController {

    @Autowired
    private UserService userService;

	@Autowired
	private MailService mailService;

    @PostMapping("/register")
    public ResponseEntity<String> addUser(@RequestBody User user){
        boolean response = userService.addUser(user);
        return new ResponseEntity<>("user added", HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/deleteUser")
    public ResponseEntity<String> deleteUser(){
        return new ResponseEntity<>("user deleted",HttpStatus.ACCEPTED);
    }
    
    @PostMapping(value = "/send")
    public ResponseEntity<String> sendMail(@RequestBody Mail mail){
		
    	userService.sentMail(mail);
    	return new ResponseEntity<String>("Mail Sent",HttpStatus.OK);
	}
	
    @PostMapping(value = "/starred/{mailId}")
	public ResponseEntity<String> starredMail(@PathVariable("mailId") int mailId){
		userService.starredMail(mailId);
		return new ResponseEntity<>("Starred successfully", HttpStatus.ACCEPTED);
	}
    
    //Raj
    @PostMapping(value = "/draft")
   	public ResponseEntity<String> draftMail(@RequestBody Mail mail){

		userService.draftMail(mail);
		return new ResponseEntity<>("Mail saved to draft",HttpStatus.ACCEPTED);
   	}


	//Shivam
    @PostMapping(value = "/delete/{mailId}")
	public ResponseEntity<String> deleteMail(@PathVariable("mailId") int mailId){
		return null;
	}
	//Shivam
    @PostMapping(value = "/restore/{mailId}")
	public ResponseEntity<String> restoreMail(@PathVariable("mailId") int mailId){
		return null;
	}

	//Raj
	//Searching based on sender mail Id,Subject & body
    @GetMapping(value = "/search/{token}")
	public ResponseEntity<List<Mail>> searchMail(@PathVariable("token") String email){
		List<Mail> mailList = mailService.searchMail(email);

		return new ResponseEntity<>(mailList,HttpStatus.ACCEPTED);
	}
	
	
	
	
	
	
}
