package com.gmail.module;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(indexes = {
		@Index(name = "idx_user_mobile",columnList = "mobile_number",unique = true)
})
public class User {

	@Id
	@Email
	@NotNull
	private String email;

	@NotNull
	@Pattern(regexp = "[a-zA-Z]{3,12}", message = "First Name must not contains numbers or special characters")
	private String firstName;

	@NotNull
	@Pattern(regexp = "[a-zA-Z]{3,12}", message = "Last Name must not contains numbers or special characters")
	private String lastName;
	

	@Column(name = "mobile_number", nullable = false)
	@Pattern(regexp = "[6-9]{1}[0-9]{9}", message = "Mobile number must have 10 digits")
	private String mobileNumber;

	@NotNull
	@Past
	private LocalDate dateOfBirth;

	@NotNull
	private String password;

	private String role;

	@OneToMany(mappedBy = "sender")
	@JsonIgnore
	private List<Mail> sent = new ArrayList<>();

	@ElementCollection
	@JsonIgnore
	private List<Mail> starred = new ArrayList<>();

	@ElementCollection
	@JsonIgnore
	private List<Mail> draft = new ArrayList<>();;

	@ElementCollection
	@JsonIgnore
	private List<Mail> trashMails = new ArrayList<>();

}
