package com.costrategix.gbp.capstone2.actuator.DTO;

import java.util.List;

public class CustomUsersResponseDto {
	
	private int userId;
	private String firstName;
	private String lastName;
	private String lastSeen;
	private String status;
	private String email;
	List<CustomPhoneDto> phoneDetails;
	List<CustomRolesDto> roleDetails;
	
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getLastSeen() {
		return lastSeen;
	}
	public void setLastSeen(String lastSeen) {
		this.lastSeen = lastSeen;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public List<CustomPhoneDto> getPhoneDetails() {
		return phoneDetails;
	}
	public void setPhoneDetails(List<CustomPhoneDto> phoneDetails) {
		this.phoneDetails = phoneDetails;
	}
	public List<CustomRolesDto> getRoleDetails() {
		return roleDetails;
	}
	public void setRoleDetails(List<CustomRolesDto> roleDetails) {
		this.roleDetails = roleDetails;
	}
	
	@Override
	public String toString() {
		return "CustomUsersResponseDto [userId=" + userId + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", lastSeen=" + lastSeen + ", status=" + status + ", email=" + email + ", phoneDetails="
				+ phoneDetails + ", roleDetails=" + roleDetails + "]";
	}
	
	
	
}
