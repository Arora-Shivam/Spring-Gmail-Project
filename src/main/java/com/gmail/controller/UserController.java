package com.gmail.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

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

import com.gmail.exception.NoMailFound;
import com.gmail.module.Mail;
import com.gmail.module.MailDto;
import com.gmail.module.User;
import com.gmail.repository.MailDao;
import com.gmail.service.MailService;
import com.gmail.service.UserService;

@RestController
@RequestMapping("/mail")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private MailService mailService;

	@Autowired
	private MailDao mailDao;

	
	// Handle		 --> /mail/user
	// What is does? --> registers the User with the system
	// Request Type? --> Post request
	// Input 		 --> User as the Request Body
    @PostMapping("/register")
    public ResponseEntity<String> addUser(@Valid @RequestBody User user){

		boolean response = userService.addUser(user);

		return new ResponseEntity<>("user added", HttpStatus.ACCEPTED);
	}


	// Handle		 --> /mail/user
	// What is does? --> deletes the User with the system
	// Request Type? --> Delete request
	// Input 		 --> None
    @DeleteMapping("/user")
    public ResponseEntity<String> deleteUser(){
		boolean response = userService.deleteUser();
		return new ResponseEntity<>("user deleted",HttpStatus.ACCEPTED);
    }
    
	// Handle		 --> /mail/send
	// What is does? --> compose mail
	// Request Type? --> Post request
	// Input 		 --> MailDto as the Request Body
    @PostMapping(value = "/send")
    public ResponseEntity<String> sendMail(@Valid @RequestBody MailDto mailDto){
    
    	userService.sentMail(mailDto);
    	
    	return new ResponseEntity<String>("Mail Sent",HttpStatus.OK);
	}
	
	// Handle		 --> /mail/starred/{mailId}
	// What is does? --> Starring/Unstaring the received/sent mails
	// Request Type? --> Post request
	// Input 		 --> mail Id as Path Variable
    @PostMapping(value = "/starred/{mailId}")
	public ResponseEntity<String> starredMail(@PathVariable("mailId") int mailId){
			
				if(userService.starredMail(mailId))
					return new ResponseEntity<>("Starred successfully", HttpStatus.ACCEPTED);
				else 
					return new ResponseEntity<>("Mail Unstarred successfully", HttpStatus.ACCEPTED);	
				
	}
    
	// Handle		 --> /mail/draft
	// What is does? --> draft mail
	// Request Type? --> Post request
	// Input 		 --> MailDto as the Request Body
    @PostMapping(value = "/draft")
   	public ResponseEntity<String> draftMail(@RequestBody MailDto mail){


		userService.draftMail(mail);
		return new ResponseEntity<>("Mail saved to draft", HttpStatus.ACCEPTED);
	}

    
	// Handle		 --> /mail/trash/{mailId}
	// What is does? --> Adding mail to trash
	// Request Type? --> Post request
	// Input 		 --> MailId as Path Variable
	@PostMapping(value = "/trash/{mailId}")
	public ResponseEntity<String> deleteMail(@PathVariable("mailId") int mailId) {
	
		userService.deleteMail(mailId);
		return new ResponseEntity<String>("Mail Deleted Successfully", HttpStatus.OK);

	}

	
	// Handle		 --> /mail/restore/{mailId}
	// What is does? --> restoring a trash mail
	// Request Type? --> Post request
	// Input 		 --> MailId as Path Variable
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

	// Handle		 --> /mail/search/{searchKeyword}
	// What is does? --> Searching based on sender mail Id,Subject & body
	// Request Type? --> Get request
	// Input 		 --> Search Keyword as Path Variable
    @GetMapping(value = "/search/{token}")
	public ResponseEntity<List<Mail>> searchMail(@PathVariable("token") String email){

		List<Mail> mailList = mailService.searchMail(email);

		return new ResponseEntity<>(mailList, HttpStatus.ACCEPTED);
	}

	
	// Handle		 --> /mail/emptyTrash
	// What is does? --> Deleting all the Trash mails
	// Request Type? --> Delete request
	// Input 		 --> None
	@DeleteMapping(value = "/emptyTrash")
	public ResponseEntity<String> emptyTrash() {
		userService.emptyTrash();
		return new ResponseEntity<>("Trash cleared", HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/login")
	public ResponseEntity<String> login(){
		
		
		return new ResponseEntity<String>("Loggen In",HttpStatus.ACCEPTED);
	}
}
