package com.gmail.module;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Content {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int id;



	@OneToMany(mappedBy = "content",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Attachment> attachments = new ArrayList<>();
	
}
