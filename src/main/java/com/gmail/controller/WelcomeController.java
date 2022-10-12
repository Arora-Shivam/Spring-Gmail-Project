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
			URL url=new URL("https://lucid.app/lucidchart/3230b4ca-3702-4a8b-85d6-4b80bdced604/edit?viewport_loc=231%2C-7%2C2746%2C1351%2C0_0&invitationId=inv_043119d5-8305-4c81-be25-b324f02b84ca#");
			return new ResponseEntity<>(url,HttpStatus.ACCEPTED);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			throw new UserAlreadyExistException("Application Server Problem");
		}
	}
}
