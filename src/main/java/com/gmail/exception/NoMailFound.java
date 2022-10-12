package com.gmail.exception;

public class NoMailFound extends RuntimeException {

	public NoMailFound() {

	}

	public NoMailFound(String msg) {
		super(msg);
	}
}
