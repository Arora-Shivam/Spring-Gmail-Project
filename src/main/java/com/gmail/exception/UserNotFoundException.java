package com.gmail.exception;

public class UserNotFoundException extends RuntimeException {
	public UserNotFoundException(String msg) {
		super(msg);
	}

	public UserNotFoundException() {
	}
}
