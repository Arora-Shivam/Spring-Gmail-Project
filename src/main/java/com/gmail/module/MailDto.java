package com.gmail.module;


import lombok.Data;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Data
public class MailDto {
	
	//Should we keep List<String> ?
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

	private String subject;

    private String body;

	private int contentID;


}
