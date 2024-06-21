package com.prodapt.capstoneproject.exceptions;

public class PaymentNotFoundException extends Exception{

	private static final long serialVersionUID = 1L;

	public PaymentNotFoundException() {
		super();
	}

	public PaymentNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public PaymentNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public PaymentNotFoundException(String message) {
		super(message);
	}

	public PaymentNotFoundException(Throwable cause) {
		super(cause);
	}
	
	

}
