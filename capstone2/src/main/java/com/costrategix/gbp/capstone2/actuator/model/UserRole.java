package com.costrategix.gbp.capstone2.actuator.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="User_Role")
public class UserRole {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="User_RoleId")
	private int User_RoleId;
	
	
	@ManyToOne(fetch = FetchType.EAGER)
	private User user;
	
	
	@ManyToOne
	private Role role;
	
	


	









	public UserRole() {
		super();
	}









	public Role getRole() {
		return role;
	}









	public void setRole(Role role) {
		this.role = role;
	}









	public UserRole(int user_RoleId, User user, Role role) {
		User_RoleId = user_RoleId;
		this.user = user;
		this.role = role;
	}









	public int getUser_RoleId() {
		return User_RoleId;
	}




	public void setUser_RoleId(int user_RoleId) {
		User_RoleId = user_RoleId;
	}




	public User getUser() {
		return user;
	}




	public void setUser(User user) {
		this.user = user;
	}
	
	
	
	
	
}
