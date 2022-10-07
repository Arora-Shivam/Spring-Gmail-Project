package com.gmail.module;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Content {
	
	
	@Id
	private int id;
	
	private String subject;
	
	private String body;
	
	@OneToMany
	private List <Attachment> attachment= new ArrayList <> (  );
	
}
