package com.nagarro.training.exceptions;

public class NoCustomerException extends Exception{
	
	private static final long serialVersionUID = 1L;

	public NoCustomerException(String msg ) {
		super(msg);
	}

}
