package com.costrategix.gbp.capstone2.actuator.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MyControllerAdvice {
	
	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<ResponseModel> handleException(Exception exc) {
		
		
		ResponseModel error=new ResponseModel();
		error.setStatus(HttpStatus.NOT_FOUND.value());
		error.setMessage(exc.getMessage());
		error.setTimeStamp(System.currentTimeMillis());
		
				
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ResponseModel> handleExceptions(Exception exc) {
		
		
		ResponseModel error=new ResponseModel();
		error.setStatus(HttpStatus.BAD_REQUEST.value());
		error.setMessage(exc.getMessage());
		error.setTimeStamp(System.currentTimeMillis());
		
				
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(ForbiddenException.class)
	public ResponseEntity<ResponseModel> ForbiddenException(Exception exc) {
		
		
		ResponseModel error=new ResponseModel();
		error.setStatus(HttpStatus.FORBIDDEN.value());
		error.setMessage(exc.getMessage());
		error.setTimeStamp(System.currentTimeMillis());
		
				
		return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
	}
	
	@ExceptionHandler(UnauthorizedException.class)
	public ResponseEntity<ResponseModel> UnauthorizedException(Exception exc) {
		
		
		ResponseModel error=new ResponseModel();
		error.setStatus(HttpStatus.UNAUTHORIZED.value());
		error.setMessage(exc.getMessage());
		error.setTimeStamp(System.currentTimeMillis());
		
				
		return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(ConflictException.class)
	public ResponseEntity<ResponseModel> ConflictException(Exception exc) {
		
		
		ResponseModel error=new ResponseModel();
		error.setStatus(HttpStatus.CONFLICT.value());
		error.setMessage(exc.getMessage());
		error.setTimeStamp(System.currentTimeMillis());
		
				
		return new ResponseEntity<>(error, HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(UnprocessableException.class)
	public ResponseEntity<ResponseModel> UnprocessableException(Exception exc) {
		
		
		ResponseModel error=new ResponseModel();
		error.setStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());
		error.setMessage(exc.getMessage());
		error.setTimeStamp(System.currentTimeMillis());
		
				
		return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
	}
}
