package com.costrategix.gbp.capstone2.actuator.DTO;

import java.time.ZonedDateTime;

public class ValidatorDto {
	ZonedDateTime Expirationtime;
	String response;
		
	public ValidatorDto() {
		//super();
	}
	public ValidatorDto(ZonedDateTime expirationtime, String response) {
		//super();
		Expirationtime = expirationtime;
		this.response = response;
	}
	public ZonedDateTime getExpirationtime() {
		return Expirationtime;
	}
	public void setExpirationtime(ZonedDateTime expirationtime) {
		Expirationtime = expirationtime;
	}
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	
}

