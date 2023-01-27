package com.costrategix.gbp.capstone2.actuator.exception;

import org.springframework.stereotype.Component;


public class BusinessException extends RuntimeException{

	public BusinessException(String string) {
		super(string);
	}
	
	
}

