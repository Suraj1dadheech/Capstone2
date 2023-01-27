package com.costrategix.gbp.capstone2.actuator.model;

public class JsonTokenObject {
	
	private String firstname;
	
	private String email;
	
	private String jti;
	
	private String iat;
	
	private String exp;

	public JsonTokenObject() {

	}

	public JsonTokenObject(String firstname, String email, String jti, String iat, String exp) {
		
		this.firstname = firstname;
		this.email = email;
		this.jti = jti;
		this.iat = iat;
		this.exp = exp;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getJti() {
		return jti;
	}

	public void setJti(String jti) {
		this.jti = jti;
	}

	public String getIat() {
		return iat;
	}

	public void setIat(String iat) {
		this.iat = iat;
	}

	public String getExp() {
		return exp;
	}

	public void setExp(String exp) {
		this.exp = exp;
	}
	
	
	
	
}
