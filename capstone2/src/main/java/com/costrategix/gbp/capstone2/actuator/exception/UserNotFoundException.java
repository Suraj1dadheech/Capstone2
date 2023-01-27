package com.costrategix.gbp.capstone2.actuator.exception;

import org.springframework.stereotype.Component;

//@Component
public class UserNotFoundException extends RuntimeException{
	
	
	public UserNotFoundException(String string) {
		super(string);
	}
	
	
	
	/**
	 * 
	 */
	/*private static final long serialVersionUID = 1L;
	private String errorCode;
	private String errorMessage;
	
	
	
	public UserNotFoundException() {
		//super();
	}

	public UserNotFoundException(String errorCode, String errorMessage) {
		//super();
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}
	
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}*/
	
}
