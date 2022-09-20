package com.gmail.module;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.Data;

@Entity
@Data
public class Content {
	
	
	@Id
	private int id;
	
	private String subject;
	
	private String body;
	
	@OneToOne
	private Attachment attachment;
	
}
