package com.gmail.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gmail.module.Mail;

@RestController
public class MailController {

	
	@GetMapping(value = "/inbox")
	public ResponseEntity<List<Mail>> inbox(){
		
		return null;
	}
	
	
	@GetMapping(value = "/sent")
	public ResponseEntity<List<Mail>> sent(){
		
		return null;
	}
	
	
	@GetMapping(value = "/starred")
	public ResponseEntity<List<Mail>> starred(){
		
		return null;
	}
	
	@GetMapping(value = "/draft")
	public ResponseEntity<List<Mail>> draft(){
		
		return null;
	}
	
	
	@GetMapping(value = "/trash")
	public ResponseEntity<List<Mail>> trash(){
		
		return null;
	}
	
}
