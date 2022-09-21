package com.gmail.module;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {

	@Id
	private String email;
	
	private String firstName;
	
	private String lastName;
	
	private String mobileNumber;
	
	private LocalDate dateOfBirth;

	private String password;

	private String role;
	
	
//	private List<Mail> allMails;

//	@OneToMany
//	@JsonIgnore
//	@JoinColumn(name = "Inbox")
//	private List<Mail> inbox;

//	@OneToMany
//	@JsonIgnore
//	@JoinColumn(name = "Sent")
//	private List<Mail> sent;

//	@OneToMany
//	@JsonIgnore
//	@JoinColumn(name = "Draft")
//	private List<Mail> draft;
//
//
//	@OneToMany
//	@JsonIgnore
//	@JoinColumn(name = "Starred")
//	private List<Mail> starred;



}

