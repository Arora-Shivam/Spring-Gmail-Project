package com.gmail.module;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
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

	private String role; // Admin , User
	
	
//	@OneToMany
//	@JsonIgnore
//	@JoinColumn(name="recieved_mail")
//	private List<Mail> Inbox=new ArrayList<>();


	@OneToMany
	@JsonIgnore
	//@JoinColumn(name = "sent_mail")
	private List<Mail> sent=new ArrayList<>();

	@OneToMany
	@JsonIgnore
	//@JoinColumn(name = "starred_mail")
	private List<Mail> starred = new ArrayList<>();

	@OneToMany
	@JsonIgnore
	private List<Mail> draft = new ArrayList<>();;






}

