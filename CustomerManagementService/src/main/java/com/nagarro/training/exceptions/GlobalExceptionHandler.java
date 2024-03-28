package com.nagarro.training.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(NoCustomerException.class)
	public ResponseEntity<?> handleNoCustomerException(NoCustomerException ex) {
		System.out.println("NoCustomerException");

		return ResponseEntity.badRequest().body(ex.getMessage());
	}

	@ExceptionHandler(AccountDetailsNotFoundException.class)
	public ResponseEntity<?> handleAccountException(AccountDetailsNotFoundException ex) {
		System.out.println("AccountException");

		return ResponseEntity.badRequest().body(ex.getMessage());
	}

	@ExceptionHandler(UpdateNotFoundException.class)
	public ResponseEntity<?> handleUpdateNotFoundException(UpdateNotFoundException ex) {
		System.out.println("UpdateNotFoundException");

		return ResponseEntity.badRequest().body(ex.getMessage());
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleGenericException(Exception ex) {
		System.out.println("handleGenericException");

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body("An error occurred processing your request. Please try again later.");
	}
}
