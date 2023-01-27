package com.costrategix.gbp.capstone2.actuator.model;

import java.time.ZonedDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Entity
@Table(name="Auth_Token")
public class AuthToken {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="authid")
	private int authid;
	
	
	@Column(name="tokenvalue",length = 500)
	private String tokenvalue;
	

	@DateTimeFormat(iso = ISO.DATE_TIME)
	@Column(name="createddatetime")
	private ZonedDateTime createddatetime;
	
	@DateTimeFormat(iso = ISO.DATE_TIME)
	@Column(name="expirationtime")
	private ZonedDateTime expirationtime;
	
	@DateTimeFormat(iso = ISO.DATE_TIME)
	@Column(name="effectivetime")
	private ZonedDateTime effectivetime;
	
	
	@Column(name="tokenvalidity")
	private int tokenvalidity;
	
	@OneToOne(fetch = FetchType.EAGER)
	private User user;
	


	

	public AuthToken(int authid, String tokenvalue, ZonedDateTime createddatetime, ZonedDateTime expirationtime,
			ZonedDateTime effectivetime, int tokenvalidity, User user) {
		this.authid = authid;
		this.tokenvalue = tokenvalue;
		this.createddatetime = createddatetime;
		this.expirationtime = expirationtime;
		this.effectivetime = effectivetime;
		this.tokenvalidity = tokenvalidity;
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getTokenvalidity() {
		return tokenvalidity;
	}

	public void setTokenvalidity(int tokenvalidity) {
		this.tokenvalidity = tokenvalidity;
	}

	public AuthToken() {
		
	}

	public int getAuthid() {
		return authid;
	}

	public void setAuthid(int authid) {
		this.authid = authid;
	}

	public String gettokenvalue() {
		return tokenvalue;
	}

	public void settokenvalue(String tokenvalue) {
		this.tokenvalue = tokenvalue;
	}

	public ZonedDateTime getcreateddatetime() {
		return createddatetime;
	}

	public void setcreateddatetime(ZonedDateTime createddatetime) {
		this.createddatetime = createddatetime;
	}

	public String getTokenvalue() {
		return tokenvalue;
	}

	public void setTokenvalue(String tokenvalue) {
		this.tokenvalue = tokenvalue;
	}

	public ZonedDateTime getCreateddatetime() {
		return createddatetime;
	}

	public void setCreateddatetime(ZonedDateTime createddatetime) {
		this.createddatetime = createddatetime;
	}

	public ZonedDateTime getExpirationtime() {
		return expirationtime;
	}

	public void setExpirationtime(ZonedDateTime expirationtime) {
		this.expirationtime = expirationtime;
	}

	public ZonedDateTime getEffectivetime() {
		return effectivetime;
	}

	public void setEffectivetime(ZonedDateTime effectivetime) {
		this.effectivetime = effectivetime;
	}
	
	

	

	
}
