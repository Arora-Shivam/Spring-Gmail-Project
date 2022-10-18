package com.gmail.exceptionhandler;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.gmail.exception.ErrorDetails;
import com.gmail.exception.NoMailFound;
import com.gmail.exception.PasswordMisMatchException;
import com.gmail.exception.UserAlreadyExistException;
import com.gmail.exception.UserNotFoundException;

@ControllerAdvice
public class ExceptionalHandler {

	@ExceptionHandler(NoMailFound.class)
	public ResponseEntity<ErrorDetails> noMailFound(NoMailFound noMailFound, WebRequest request) {

		ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), "Bad Request",
				noMailFound.getMessage());

		return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(NoHandlerFoundException.class)
	public ResponseEntity<ErrorDetails> noHandler(NoHandlerFoundException noHandler, WebRequest request) {
		ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(),
				"Please enter valid URL", request.getDescription(false));

		return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<ErrorDetails> httpRequestMethodException(HttpRequestMethodNotSupportedException exception,
			WebRequest request) {

		ErrorDetails errorDetail = new ErrorDetails(LocalDateTime.now(), HttpStatus.METHOD_NOT_ALLOWED.value(),
				exception.getMessage(), request.getDescription(false));

		return new ResponseEntity<ErrorDetails>(errorDetail, HttpStatus.METHOD_NOT_ALLOWED);
	}

	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ResponseEntity<ErrorDetails> missingServletRequestParameterException(
			MissingServletRequestParameterException exception, WebRequest request) {

		ErrorDetails errorDetail = new ErrorDetails(LocalDateTime.now(), HttpStatus.METHOD_NOT_ALLOWED.value(),
				exception.getMessage(), request.getDescription(false));

		return new ResponseEntity<ErrorDetails>(errorDetail, HttpStatus.METHOD_NOT_ALLOWED);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorDetails> myMANVExceptionHandler(
			MethodArgumentNotValidException methodArgumentNotValidException) {

		ErrorDetails err = new ErrorDetails(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), "Validation Error",
				methodArgumentNotValidException.getMessage());

		return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);

	}

	@ExceptionHandler(UsernameNotFoundException.class)
	public ResponseEntity<ErrorDetails> userNotFound(UsernameNotFoundException userNameNotFoundException,
			WebRequest webRequest) {

		ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), "Not Found",
				userNameNotFoundException.getMessage());

		return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.NOT_FOUND);

	}

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ErrorDetails> userNotFound(UserNotFoundException userNotFoundException,
			WebRequest webRequest) {

		ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), "Not Found",
				userNotFoundException.getMessage());

		return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.NOT_FOUND);

	}

	@ExceptionHandler(UserAlreadyExistException.class)
	public ResponseEntity<ErrorDetails> userAlreadyExistException(UserAlreadyExistException userAlreadyExistException,
			WebRequest webRequest) {

		ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), HttpStatus.CONFLICT.value(),
				"User Already Exist", userAlreadyExistException.getMessage());
		return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.CONFLICT);
	}

	@ExceptionHandler(PasswordMisMatchException.class)
	public ResponseEntity<ErrorDetails> passwordMismatchException(PasswordMisMatchException exception,
			WebRequest webRequest) {

		ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), HttpStatus.CONFLICT.value(),
				"BAD Request", exception.getMessage());
		return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.CONFLICT);
	}
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorDetails> generalException(Exception exception, WebRequest request) {

		ErrorDetails errorDetail = new ErrorDetails(LocalDateTime.now(), HttpStatus.METHOD_NOT_ALLOWED.value(),
				"BAD REQUEST", request.getDescription(false));

		return new ResponseEntity<ErrorDetails>(errorDetail, HttpStatus.METHOD_NOT_ALLOWED);
	}
}
