package com.costrategix.gbp.capstone2.actuator.model;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;


@Entity
@Table(name="User")
public class User {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="userId")
	private int userId;
	
	@NotNull
	@NotBlank(message="firstName field is Required!!!")
	@Size(min=2,max=20,message="min is 2 and max is 20 characters are allowed!!!")
	@Column(name="firstName")
	private String firstName;
	
	@NotNull
	@NotBlank(message="lastName field is Required!!!")
	@Size(min=2,max=20,message="min is 2 and max is 20 characters are allowed!!!")
	@Column(name="lastName")
	private String lastName;
	
	@NotNull
	@Column(name="email",unique = true)
	private String email;
	
	@NotNull
	@Column(name="status")
	private String status;
	
	
	
	@DateTimeFormat(iso = ISO.DATE_TIME)
	@Column(name="lastseen")
	private ZonedDateTime lastseen;
	
	
	
	
	@Column(name="password",length = 500)
	private String password;
	
	
	@Column(name="exist")
	private int exist;
	
	@Column(name="count")
	private int count;
	
	

	
	@OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
	
//			,fetch = FetchType.EAGER, targetEntity = UserRole.class,cascade = CascadeType.ALL,orphanRemoval = true)
	private List<UserRole> userRoles =new ArrayList<UserRole>();
	
	@OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
//	,cascade = CascadeType.PERSIST ,targetEntity = Phone.class,orphanRemoval = true)
	private List<Phone> phones =new ArrayList<Phone>();
	
	@OneToOne(mappedBy = "user",targetEntity = AuthToken.class,cascade = CascadeType.ALL,orphanRemoval = true)
	private AuthToken authToken;
	
	
	@OneToOne(mappedBy = "user",targetEntity = LoginAuth.class,cascade = CascadeType.ALL,orphanRemoval = true)
	private LoginAuth loginAuth;
	

	public User() {	
	}




	public User(int userId,
			@NotNull @NotBlank(message = "firstName field is Required!!!") @Size(min = 2, max = 20, message = "min is 2 and max is 20 characters are allowed!!!") String firstName,
			@NotNull @NotBlank(message = "lastName field is Required!!!") @Size(min = 2, max = 20, message = "min is 2 and max is 20 characters are allowed!!!") String lastName,
			@NotNull String email, @NotNull String status, ZonedDateTime lastseen, String password,
			List<UserRole> userRoles, List<Phone> phones, AuthToken authToken) {
		super();
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.status = status;
		this.lastseen = lastseen;
		this.password = password;
		this.userRoles = userRoles;
		this.phones = phones;
		this.authToken = authToken;
	}





	
	

	public LoginAuth getLoginAuth() {
		return loginAuth;
	}




	public void setLoginAuth(LoginAuth loginAuth) {
		this.loginAuth = loginAuth;
	}




	public int getCount() {
		return count;
	}




	public void setCount(int count) {
		this.count = count;
	}




	public int getExist() {
		return exist;
	}




	public void setExist(int exist) {
		this.exist = exist;
	}






	public String getPassword() {
		return password;
	}






	public void setPassword(String password) {
		this.password = password;
	}






	public ZonedDateTime getLastseen() {
		return lastseen;
	}



	public void setLastseen(ZonedDateTime lastseen) {
		this.lastseen = lastseen;
	}



	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	

	public List<UserRole> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(List<UserRole> userRoles) {
		this.userRoles = userRoles;
	}

	public List<Phone> getPhones() {
		return phones;
	}

	public void setPhones(List<Phone> phones) {
		this.phones = phones;
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





	public String getEmail() {
		return email;
	}





	public void setEmail(String email) {
		this.email = email;
	}




	public String getStatus() {
		return status;
	}




	public void setStatus(String status) {
		this.status = status;
	}


	




	public AuthToken getAuthToken() {
		return authToken;
	}





	public void setAuthToken(AuthToken authToken) {
		this.authToken = authToken;
	}




	
	
	
}

