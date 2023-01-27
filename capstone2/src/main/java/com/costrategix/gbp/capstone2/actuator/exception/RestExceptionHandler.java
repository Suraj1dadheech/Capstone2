package com.costrategix.gbp.capstone2.actuator.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {
	
	
	// add another exception handler ... to catch any exception (catch all)
	
	@ExceptionHandler
	public ResponseEntity<ResponseModel> handleException(Exception exc) {
		
		// create a StudentErrorResponse
		ResponseModel error = new ResponseModel();
		
		error.setStatus(HttpStatus.BAD_REQUEST.value());
		error.setMessage(exc.getMessage());
		error.setTimeStamp(System.currentTimeMillis());
		
		// return ResponseEntity		
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
	

	
	public ResponseEntity<ResponseModel> responseHandler(String message) {

		// create a StudentErrorResponse
		ResponseModel response = new ResponseModel();

		response.setStatus(HttpStatus.OK.value());
		response.setMessage(message);
		response.setTimeStamp(System.currentTimeMillis());

		// return ResponseEntity
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	public ResponseEntity<ResponseModel> responseHandler(String message,String token) {

		// create a StudentErrorResponse
		ResponseModel response = new ResponseModel();
		System.out.println(token);
		response.setStatus(HttpStatus.OK.value());
		response.setMessage(message);
		response.setTimeStamp(System.currentTimeMillis());
		response.setToken(token);

		// return ResponseEntity
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	public ResponseEntity<ResponseModel> responseHandlerAccepted(String message,String token) {

		// create a StudentErrorResponse
		ResponseModel response = new ResponseModel();
		System.out.println(token);
		response.setStatus(HttpStatus.ACCEPTED.value());
		response.setMessage(message);
		response.setTimeStamp(System.currentTimeMillis());
		response.setToken(token);

		// return ResponseEntity
		return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
	}


	public ResponseEntity<ResponseModel> CreatedResponseHandler(String message) {

		// create a StudentErrorResponse
		ResponseModel response = new ResponseModel();
		response.setStatus(HttpStatus.CREATED.value());
		response.setMessage(message);
		response.setTimeStamp(System.currentTimeMillis());

		// return ResponseEntity
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
}

