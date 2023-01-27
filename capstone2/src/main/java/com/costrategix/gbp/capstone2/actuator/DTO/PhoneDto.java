package com.costrategix.gbp.capstone2.actuator.DTO;

public class PhoneDto {


	private String phone_no;
	
	
	private int smsAllowed;
	

	private String phoneType;

	public PhoneDto() {
	}
//	Role role = roleRepo.findByID(id);

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

	public String getPhoneType() {
		return phoneType;
	}

	public void setPhoneType(String phoneType) {
		this.phoneType = phoneType;
	}
	
	

}