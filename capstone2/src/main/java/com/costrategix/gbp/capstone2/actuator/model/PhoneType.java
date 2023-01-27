package com.costrategix.gbp.capstone2.actuator.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="phonetype")
public class PhoneType {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="phonetypeid")
	private int phonetypeid;
	

	@Column(name="phonetype")
	private String phonetypeDesc;
	
	@OneToOne(mappedBy = "user",fetch = FetchType.LAZY,targetEntity = Phone.class,cascade = CascadeType.ALL,orphanRemoval = true)
	private Phone phone;
	
	

	public PhoneType() {

	}



	public PhoneType(int phonetypeid, String phonetypeDesc, Phone phone) {
		this.phonetypeid = phonetypeid;
		this.phonetypeDesc = phonetypeDesc;
		this.phone = phone;
	}



	public int getPhonetypeid() {
		return phonetypeid;
	}



	public void setPhonetypeid(int phonetypeid) {
		this.phonetypeid = phonetypeid;
	}



	public String getPhonetypeDesc() {
		return phonetypeDesc;
	}



	public void setPhonetypeDesc(String phonetypeDesc) {
		this.phonetypeDesc = phonetypeDesc;
	}



	public Phone getPhone() {
		return phone;
	}



	public void setPhone(Phone phone) {
		this.phone = phone;
	}
	
	
	
	
	
	
}
