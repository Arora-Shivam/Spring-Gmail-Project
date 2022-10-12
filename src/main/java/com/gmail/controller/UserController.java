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
import org.springframework.web.bind.annotation.RestController;

import com.gmail.exception.NoMailFound;
import com.gmail.module.Mail;
import com.gmail.module.MailDto;
import com.gmail.module.User;
import com.gmail.repository.MailDao;
import com.gmail.service.MailService;
import com.gmail.service.UserService;

@RestController
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private MailService mailService;

	@Autowired
	private MailDao mailDao;

	@PostMapping("/user")
	public ResponseEntity<String> addUser(@Valid @RequestBody User user) {

		boolean response = userService.addUser(user);

		return new ResponseEntity<>("user added", HttpStatus.ACCEPTED);
	}

	@DeleteMapping("/user")
	public ResponseEntity<String> deleteUser() {
		boolean response = userService.deleteUser();
		return new ResponseEntity<>("user deleted", HttpStatus.ACCEPTED);
	}

	@PostMapping(value = "/send")
	public ResponseEntity<String> sendMail(@RequestBody MailDto mailDto) {

		userService.sentMail(mailDto);
		return new ResponseEntity<String>("Mail Sent", HttpStatus.OK);
	}

	@PostMapping(value = "/starred/{mailId}")
	public ResponseEntity<String> starredMail(@PathVariable("mailId") int mailId) {

		if (userService.starredMail(mailId))
			return new ResponseEntity<>("Starred successfully", HttpStatus.ACCEPTED);
		else
			return new ResponseEntity<>("Mail Unstarred successfully", HttpStatus.ACCEPTED);

	}

	@PostMapping(value = "/draft")
	public ResponseEntity<String> draftMail(@RequestBody MailDto mail) {

		userService.draftMail(mail);
		return new ResponseEntity<>("Mail saved to draft", HttpStatus.ACCEPTED);
	}

	@PostMapping(value = "/trash/{mailId}")
	public ResponseEntity<String> deleteMail(@PathVariable("mailId") int mailId) {

		userService.deleteMail(mailId);
		return new ResponseEntity<String>("Mail Deleted Successfully", HttpStatus.OK);

	}

	@PostMapping(value = "/restore/{mailId}")
	public ResponseEntity<String> restoreMail(@PathVariable("mailId") int mailId) {
		Optional<Mail> mail = mailDao.findById(mailId);

		if (mail.isPresent()) {

			userService.restoreMail(mail.get());
			return new ResponseEntity<String>("Mail Restored Successfully", HttpStatus.OK);

		} else {
			throw new NoMailFound("No Mail Found");
		}
	}

	@GetMapping(value = "/search/{token}")
	public ResponseEntity<List<Mail>> searchMail(@PathVariable("token") String email) {
		List<Mail> mailList = mailService.searchMail(email);

		return new ResponseEntity<>(mailList, HttpStatus.ACCEPTED);
	}

	@DeleteMapping(value = "/emptyTrash")
	public ResponseEntity<String> emptyTrash() {
		userService.emptyTrash();
		return new ResponseEntity<>("Trash cleared", HttpStatus.ACCEPTED);
	}

}
