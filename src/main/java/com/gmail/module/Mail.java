package com.gmail.module;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.Data;

@Entity
@Data
public class Mail {

	@Id
	private int id;
	
	private LocalDateTime timeStamp;
	
	
	//private User user;
	
	@OneToOne
	
	private Content content;
	
	@OneToMany
	private List<User> reciever;
	
	

}
