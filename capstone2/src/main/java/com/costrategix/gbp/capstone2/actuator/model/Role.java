package com.costrategix.gbp.capstone2.actuator.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="Roles")
public class Role {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="RoleId")
	private int roleId;
	
	@Column(name="RoleName")
	private String roleName;


	
	@OneToMany(mappedBy = "role")
	private List<UserRole> userRoles = new ArrayList<UserRole>();
	
	
	public Role() {
	}


	public Role(int roleId, String roleName, List<UserRole> userRoles) {
		
		this.roleId = roleId;
		this.roleName = roleName;
		this.userRoles = userRoles;
	}


	public int getRoleId() {
		return roleId;
	}


	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}


	public String getRoleName() {
		return roleName;
	}


	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}


	public List<UserRole> getUserRoles() {
		return userRoles;
	}


	public void setUserRoles(List<UserRole> userRoles) {
		this.userRoles = userRoles;
	}

	
	
}


