package com.gmail.controller;

import java.net.MalformedURLException;
import java.net.URL;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gmail.exception.UserAlreadyExistException;

@Controller
public class WelcomeController {

	
	@RequestMapping(value="/")
	public ResponseEntity<URL> welcome() {
		try {
			URL url=new URL("https://www.javatpoint.com/URL-class");
			return new ResponseEntity<>(url,HttpStatus.ACCEPTED);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			throw new UserAlreadyExistException("Application Server Problem");
		}
		
		
	}
}
