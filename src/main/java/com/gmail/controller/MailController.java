package com.gmail.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gmail.module.Mail;
import com.gmail.service.MailService;

@RestController
@RequestMapping("/mail")
public class MailController {

	@Autowired
	private MailService mailService;

	// Handle		 --> /mail/inbox}
	// What is does? --> Get all mails recieved from inbox
	// Request Type? --> Get request
	// Input 		 --> No Input required
	@GetMapping(value = "/inbox")
	public ResponseEntity<List<Mail>> inbox() {

		List<Mail> inboxList = mailService.inbox();
		return new ResponseEntity<>(inboxList, HttpStatus.ACCEPTED);

	}


	// Handle		 --> /mail/sent}
	// What is does? --> Get all sent mails by the user
	// Request Type? --> Get request
	// Input 		 --> No Input required
	@GetMapping(value = "/sent")
	public ResponseEntity<List<Mail>> sent() {

		List<Mail> sentList = mailService.sentBox();
		return new ResponseEntity<>(sentList, HttpStatus.ACCEPTED);

	}

	// Handle		 --> /mail/starred}
	// What is does? --> Get all starred mails by the user
	// Request Type? --> Get request
	// Input 		 --> No Input required
	@GetMapping(value = "/starred")
	public ResponseEntity<List<Mail>> starred() {

		List<Mail> starredMails = mailService.getStarredMails();
		return new ResponseEntity<List<Mail>>(starredMails, HttpStatus.OK);

	}


	// Handle		 --> /mail/draft}
	// What is does? --> Get all drafted mails by the user
	// Request Type? --> Get request
	// Input 		 --> No Input required
	@GetMapping(value = "/draft")
	public ResponseEntity<List<Mail>> draft() {

		List<Mail> draftList = mailService.draftedMail();
		return new ResponseEntity<>(draftList, HttpStatus.ACCEPTED);

	}


	// Handle		 --> /mail/trash}
	// What is does? --> Get all deleted mails by the user
	// Request Type? --> Get request
	// Input 		 --> No Input required
	@GetMapping(value = "/trash")
	public ResponseEntity<List<Mail>> trash() {

		List<Mail> trashMails = mailService.getDeletedMails();
		return new ResponseEntity<List<Mail>>(trashMails, HttpStatus.OK);

	}

	// Handle		 --> /mail/allMail}
	// What is does? --> Get all mails present
	// Request Type? --> Get request
	// Input 		 --> No Input required
	@GetMapping(value = "/allMail")
	public ResponseEntity<List<Mail>> allMail() {

		List<Mail> allMailList = mailService.getAllMail();
		return new ResponseEntity<>(allMailList, HttpStatus.ACCEPTED);

	}

}
