package com.costrategix.gbp.capstone2.actuator.DTO;

import java.util.List;

public class GetUserDto {
	
	private int id;

	private String firstname;
	
	private String lastname;
	
	private String email;
	
	private List<RoleDto> role;
	
	private List<PhoneDto> phone;
	
	private String status;
	
	private String lastseen;
	
	private int smsAllowed;

	

	public GetUserDto(int id, String firstname, String lastname, String email, List<RoleDto> role, List<PhoneDto> phone,
			String status, String lastseen, int smsAllowed) {
		super();
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.role = role;
		this.phone = phone;
		this.status = status;
		this.lastseen = lastseen;
		this.smsAllowed = smsAllowed;
	}



	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public String getFirstname() {
		return firstname;
	}



	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}



	public String getLastname() {
		return lastname;
	}



	public void setLastname(String lastname) {
		this.lastname = lastname;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public List<RoleDto> getRole() {
		return role;
	}



	public void setRole(List<RoleDto> role) {
		this.role = role;
	}



	public List<PhoneDto> getPhone() {
		return phone;
	}



	public void setPhone(List<PhoneDto> phone) {
		this.phone = phone;
	}



	public String getStatus() {
		return status;
	}



	public void setStatus(String status) {
		this.status = status;
	}



	public String getLastseen() {
		return lastseen;
	}



	public void setLastseen(String lastseen) {
		this.lastseen = lastseen;
	}



	public int getSmsAllowed() {
		return smsAllowed;
	}



	public void setSmsAllowed(int smsAllowed) {
		this.smsAllowed = smsAllowed;
	}

	
	
	

}
