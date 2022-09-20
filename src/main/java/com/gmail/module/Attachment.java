package com.gmail.module;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public  class  Attachment {

	@Id
	private int id;
	
	@Embedded
	private Video video;
	
	@Embedded
	private Audio audio;
	
	@Embedded
	private PDF pdf;
}
