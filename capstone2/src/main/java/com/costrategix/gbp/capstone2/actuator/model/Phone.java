package com.costrategix.gbp.capstone2.actuator.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="Phone")
public class Phone {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="phoneid")
	private int phoneid;
	
	@NotNull
	@Column(name="phone_no")
	private String phone_no;
	
	
	
	@Column(name="smsAllowed")
	private int smsAllowed;
	
	@ManyToOne
	private User user;
	
	@OneToOne
	private PhoneType phoneType;

	public Phone() {
	}

	public Phone(int phoneid, @NotNull String phone_no, int smsAllowed, User user, PhoneType phoneType) {
		super();
		this.phoneid = phoneid;
		this.phone_no = phone_no;
		this.smsAllowed = smsAllowed;
		this.user = user;
		this.phoneType = phoneType;
	}

	public int getPhoneid() {
		return phoneid;
	}

	public void setPhoneid(int phoneid) {
		this.phoneid = phoneid;
	}

	public String getPhone_no() {
		return phone_no;
	}

	public void setPhone_no(String phone_no) {
		this.phone_no = phone_no;
	}

	public int getSmsAllowed() {
		return smsAllowed;
	}

	public void setSmsAllowed(int smsAllowed) {
		this.smsAllowed = smsAllowed;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public PhoneType getPhoneType() {
		return phoneType;
	}

	public void setPhoneType(PhoneType phoneType) {
		this.phoneType = phoneType;
	}
	
}

