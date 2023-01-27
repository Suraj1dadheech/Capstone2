package com.costrategix.gbp.capstone2.actuator.DTO;

import java.util.List;

public class NewUserDto {
	
	  int UserId;
	  String FirstName;
	  String LastName;
	  String Email;
	  String Status;
	  List<CustomPhoneDto> Phone;
	  
	public int getUserId() {
		return UserId;
	}
	public void setUserId(int userId) {
		UserId = userId;
	}
	public String getFirstName() {
		return FirstName;
	}
	public void setFirstName(String firstName) {
		FirstName = firstName;
	}
	public String getLastName() {
		return LastName;
	}
	public void setLastName(String lastName) {
		LastName = lastName;
	}
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
	public List<CustomPhoneDto> getPhone() {
		return Phone;
	}
	public void setPhone(List<CustomPhoneDto> phone) {
		Phone = phone;
	}
	@Override
	public String toString() {
		return "NewUserDto [UserId=" + UserId + ", FirstName=" + FirstName + ", LastName=" + LastName + ", Email="
				+ Email + ", Status=" + Status + ", Phone=" + Phone + "]";
	}
	  
	  
	
	  
	  
}
