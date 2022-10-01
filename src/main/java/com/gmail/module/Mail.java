package com.gmail.module;

import java.time.ZonedDateTime;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Entity
@Data
public class Mail {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int mailId;
	
	@JsonIgnore
	private ZonedDateTime timeStamp;
	
	
	@ManyToOne
	@JsonIgnoreProperties(value = {
			"firstName",
			"lastName",
			"mobileNumber",
			"dateOfBirth",
			"password",
			"role"
	}
			)
	private User sender;
	

	@ManyToMany
	@JsonIgnoreProperties(value = {
			"firstName",
			"lastName",
			"mobileNumber",
			"dateOfBirth",
			"password",
			"role"
	}
			)
	private List<User> recievers;
	
	
	private String body;
	
	
}
