package com.costrategix.gbp.capstone2.actuator.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="countryinfo")
public class CountryInfo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(name="country")
	private String country;
	
	@Column(name="city",unique = true)
	private String city;
	
	@Column(name="state")
	private String state;
	
	@Column(name="zipcode",unique = true)
	private String zipCode;
	
	

	public CountryInfo(int id, String country, String city, String state, String zipCode) {
		super();
		this.id = id;
		this.country = country;
		this.city = city;
		this.state = state;
		this.zipCode = zipCode;
	}
	
	

	public CountryInfo() {
		
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	
		

}
