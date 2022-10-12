package com.gmail.exception;

public class PasswordMisMatchException extends RuntimeException {

	public PasswordMisMatchException() {

	}

	public PasswordMisMatchException(String msg) {
		super(msg);
	}
}
