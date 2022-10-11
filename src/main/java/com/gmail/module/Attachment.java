package com.gmail.module;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Data
@Entity
@NoArgsConstructor
public  class  Attachment {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private Integer id;


	private String FileName;
	private String FileType;

	@JsonIgnore
	@Lob
	private byte[] data;

	@ManyToOne
	private Content content;




}
