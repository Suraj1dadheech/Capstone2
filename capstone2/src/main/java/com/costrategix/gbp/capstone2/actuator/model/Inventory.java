package com.costrategix.gbp.capstone2.actuator.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name="inventory")
//@JsonIgnoreProperties
public class Inventory {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="inventoryId")
	private int inventoryId;
	
	@Column(name="groupName",nullable = false,unique = true)
	private String  groupName;
	
	
	@Column(name="groupColor",nullable = false)
	private String groupcolor;
	
	//,columnDefinition="tinyint(1) default 0"
	@Column(name="trailerCount")
	private int tractorCount;
	
	@Column(name="leadDriver",nullable = false)
	private String leadDriver;
	
	@Column(name="comment")
	private String comment;
	
	
	//private List<Trailer> trailers;
	
	//@Column(name="comment")
	//private String comment;
	
	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@JsonIgnore
	@Column(name="exist")
	private int exist;
	

	public int getInventoryId() {
		return inventoryId;
	}

	public void setInventoryId(int inventoryId) {
		this.inventoryId = inventoryId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getGroupcolor() {
		return groupcolor;
	}

	public void setGroupcolor(String groupcolor) {
		this.groupcolor = groupcolor;
	}

	@JsonProperty("trailercount")
	public int getTractorCount() {
		return tractorCount;
	}

	public void setTractorCount(int tractorCount) {
		this.tractorCount = tractorCount;
	}

	public String getLeadDriver() {
		return leadDriver;
	}

	public void setLeadDriver(String leadDriver) {
		this.leadDriver = leadDriver;
	}

	/*public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}*/

	public int getExist() {
		return exist;
	}

	public void setExist(int exist) {
		this.exist = exist;
	}
	
	
	
}
