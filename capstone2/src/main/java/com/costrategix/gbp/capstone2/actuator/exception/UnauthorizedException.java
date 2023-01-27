package com.costrategix.gbp.capstone2.actuator.exception;

@SuppressWarnings("serial")
public class UnauthorizedException extends RuntimeException {

	public UnauthorizedException(String string) {
		super(string);
	}
	
}