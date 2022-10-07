package com.gmail.controller;

import com.gmail.exception.NoMailFound;
import com.gmail.module.Mail;
import com.gmail.module.MailDto;
import com.gmail.module.User;
import com.gmail.repository.MailDao;
import com.gmail.repository.UserDao;
import com.gmail.service.MailService;
import com.gmail.service.UserService;

import net.bytebuddy.implementation.bytecode.Throw;

import java.util.List;
import java.util.Optional;

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
	
	@Autowired
	private MailDao mailDao;

    @PostMapping("/user")
    public ResponseEntity<String> addUser(@RequestBody User user){
    	
    	boolean response = userService.addUser(user);
        
        return new ResponseEntity<>("user added", HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/user")
    public ResponseEntity<String> deleteUser(){
		boolean response = userService.deleteUser();
		return new ResponseEntity<>("user deleted",HttpStatus.ACCEPTED);
    }
    
    @PostMapping(value = "/send")
    public ResponseEntity<String> sendMail(@RequestBody MailDto mailDto){
		
    	userService.sentMail(mailDto);
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
    @PostMapping(value = "/trash/{mailId}")
	public ResponseEntity<String> deleteMail(@PathVariable("mailId") int mailId){
    	Optional<Mail> mail=mailDao.findById(mailId);
    	
    	if(mail.isPresent()) {
    			System.out.println("Mail found");
    			userService.deleteMail(mail.get());
    			return new ResponseEntity<String>("Mail Deleted Successfully",HttpStatus.OK);
    		
    	}
    	else {
    	       throw new NoMailFound("No Mail Found");
    	}
    	

	}
	//Shivam
    @PostMapping(value = "/restore/{mailId}")
	public ResponseEntity<String> restoreMail(@PathVariable("mailId") int mailId){
    	Optional<Mail> mail=mailDao.findById(mailId);
    	
    	if(mail.isPresent()) {
    		
    		userService.restoreMail(mail.get());
    		return new ResponseEntity<String>("Mail Restored Successfully",HttpStatus.OK);
    		
    	}
    	else {
    		throw new NoMailFound("No Mail Found");
    	}
	}

	//Raj
	//Searching based on sender mail Id,Subject & body
    @GetMapping(value = "/search/{token}")
	public ResponseEntity<List<Mail>> searchMail(@PathVariable("token") String email){
		List<Mail> mailList = mailService.searchMail(email);

		return new ResponseEntity<>(mailList,HttpStatus.ACCEPTED);
	}
	
	
	
	
	
	
}
