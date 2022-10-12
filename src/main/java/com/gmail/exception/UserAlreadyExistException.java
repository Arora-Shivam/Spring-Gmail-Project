package com.gmail.exception;

public class UserAlreadyExistException extends RuntimeException {
	public UserAlreadyExistException(String msg) {
		super(msg);
	}

	public UserAlreadyExistException() {
	}
}
