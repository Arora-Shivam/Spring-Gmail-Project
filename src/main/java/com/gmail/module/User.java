package com.gmail.module;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {

	@Id
	@Email
	@NotNull
	private String email;

	@NotNull
	@Pattern(regexp="[a-zA-Z]{3,12}", message = "First Name must not contains numbers or special characters")
	private String firstName;

	@NotNull
	@Pattern(regexp="[a-zA-Z]{3,12}", message = "Last Name must not contains numbers or special characters")
	private String lastName;

	@NotNull
	@Pattern(regexp="[6-9]{1}[0-9]{9}", message = "Mobile number must have 10 digits")
	private String mobileNumber;

	@NotNull
	@Past
	private LocalDate dateOfBirth;

	@NotNull
	//@Pattern(regexp="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&-+=()])(?=\\S+$).{8,20}$",message="Password must contain between 6 to 12 characters. Must be alphanumeric with both Upper and lowercase characters.")
	private String password;


	@JsonIgnore
	private String role = "ROLE_USER"; // Admin , User



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

	@OneToMany
	@JsonIgnore
	private List<Mail> trashMails= new ArrayList<>();

	@Override
	public String toString() {
		return "User{" +
				"email='" + email + '\'' +
				", firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				", mobileNumber='" + mobileNumber + '\'' +
				", dateOfBirth=" + dateOfBirth +
				", password='" + password + '\'' +
				", role='" + role + '\'' +
				'}';
	}
}

