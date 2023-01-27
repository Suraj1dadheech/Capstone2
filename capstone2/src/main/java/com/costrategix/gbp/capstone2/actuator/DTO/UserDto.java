package com.costrategix.gbp.capstone2.actuator.DTO;

import java.util.List;

public class UserDto {
	
	private int id;

	private String firstname;
	
	private String lastname;
	
	private String email;
	
	private List<RoleDto> role;
	
	private List<PhoneDto> phone;
	
	private int exist;
	
	private String temppassword;
	
	private String password;
	
	private int count;

	
	
	
public String getTemppassword() {
		return temppassword;
	}
	public void setTemppassword(String temppassword) {
		this.temppassword = temppassword;
	}
	
public int getExist() {
		return exist;
	}
	public void setExist(int exist) {
		this.exist = exist;
	}
	//	private String[] phone;
//	
//	private String type;
//	
//	private boolean sms = true;
//	
	
	

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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}

	
	
}
