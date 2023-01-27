package com.costrategix.gbp.capstone2.actuator.model;

import java.time.ZonedDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Entity
@Table(name="loginauth")
public class LoginAuth {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="loginauthId")
	private int loginauthId;
		
	
	@Column(name="logintoken")
	private String logintoken;
	

	@DateTimeFormat(iso = ISO.DATE_TIME)
	@Column(name="expirationtime")
	private ZonedDateTime expirationtime;
	
	
	@OneToOne(fetch = FetchType.EAGER)
	private User user;


	public int getLoginauthId() {
		return loginauthId;
	}


	public void setLoginauthId(int loginauthId) {
		this.loginauthId = loginauthId;
	}


	public String getLogintoken() {
		return logintoken;
	}


	public void setLogintoken(String logintoken) {
		this.logintoken = logintoken;
	}


	public ZonedDateTime getExpirationtime() {
		return expirationtime;
	}


	public void setExpirationtime(ZonedDateTime expirationtime) {
		this.expirationtime = expirationtime;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}
	
	
	
	
}