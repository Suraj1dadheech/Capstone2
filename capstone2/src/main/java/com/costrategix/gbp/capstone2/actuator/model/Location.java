package com.costrategix.gbp.capstone2.actuator.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="location")
//@JsonIgnoreType
public class Location {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="locationId")
	private int locationId;
	
	
	@Column(name="locationType")
	private String locationType;
	
	@Column(name="locationname")
	private String locationname;
	
	
	@Column(name="address")
	private String address;
	
	@Column(name="address2")
	private String address2;
	
	@Column(name="city")
	private String city;
	
	@Column(name="state")
	private String state;
	
	@Column(name="country")
	private String country;
	
	@Column(name="zip")
	private int zip;
	
	@Column(name="vCoordinates")
	private String vCoordinates;
	
	@Column(name="hCoordinates")
	private String hCoordinates;
	
	@Column(name="comments",length=500)
	private String comments;
	
	@Column(name="photo")
	private String photo;
	
	

	public int getLocationId() {
		return locationId;
	}

	public void setLocationId(int locationId) {
		this.locationId = locationId;
	}

	public String getLocationType() {
		return locationType;
	}

	public void setLocationType(String locationType) {
		this.locationType = locationType;
	}

	

	public String getLocationname() {
		return locationname;
	}

	public void setLocationname(String locationname) {
		this.locationname = locationname;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
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

	

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public int getZip() {
		return zip;
	}

	public void setZip(int zip) {
		this.zip = zip;
	}

	public String getvCoordinates() {
		return vCoordinates;
	}

	public void setvCoordinates(String vCoordinates) {
		this.vCoordinates = vCoordinates;
	}

	public String gethCoordinates() {
		return hCoordinates;
	}

	public void sethCoordinates(String hCoordinates) {
		this.hCoordinates = hCoordinates;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}
	
	
	
}