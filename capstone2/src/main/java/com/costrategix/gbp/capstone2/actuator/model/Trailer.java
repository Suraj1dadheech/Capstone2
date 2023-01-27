package com.costrategix.gbp.capstone2.actuator.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name="trailer")
public class Trailer {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="trailerId")
	public int trailerId;
	
	@Column(name="groupColor")
	public String groupColor;
	
	@Column(name="trailerNo",nullable = false)
	public String trailerNo;
	
	@Column(name="trailerName",nullable = false)
	public String trailerName;
	
	@Column(name="trailerWeight",nullable = false)
	public int trailerWeight;
	
	@Column(name="tractorWeight",nullable = false)
	public int tractorWeight;
	
	@Column(name="skyBitzNo")
	public String skyBitzNo;
	
	@Column(name="image")
	public String image;
	
	@Column(name="comment")
	public String comment;
	
	@Column(name="trailertype")
	public String trailertype;
	
	@JsonIgnore
	@Column(name="softdelete")
	public int softdelete;
	
//	@JsonIgnore
	@Column(name="inventoryid")
	public int inventoryId;
	
	
	public int getTrailerId() {
		return trailerId;
	}

	public void setTrailerId(int trailerId) {
		this.trailerId = trailerId;
	}

	public String getGroupColor() {
		return groupColor;
	}

	public void setGroupColor(String groupColor) {
		this.groupColor = groupColor;
	}

	public String getTrailerNo() {
		return trailerNo;
	}

	public void setTrailerNo(String trailerNo) {
		this.trailerNo = trailerNo;
	}

	public String getTrailerName() {
		return trailerName;
	}

	public void setTrailerName(String trailerName) {
		this.trailerName = trailerName;
	}

	public int getTrailerWeight() {
		return trailerWeight;
	}

	public void setTrailerWeight(int trailerWeight) {
		this.trailerWeight = trailerWeight;
	}

	public int getTractorWeight() {
		return tractorWeight;
	}

	public void setTractorWeight(int tractorWeight) {
		this.tractorWeight = tractorWeight;
	}

	public String getSkyBitzNo() {
		return skyBitzNo;
	}

	public void setSkyBitzNo(String skyBitzNo) {
		this.skyBitzNo = skyBitzNo;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

//	@JsonProperty("trailerType")
	public String getTrailertype() {
		return trailertype;
	}

	public void setTrailertype(String trailertype) {
		this.trailertype = trailertype;
	}

	public int getSoftdelete() {
		return softdelete;
	}

	public void setSoftdelete(int softdelete) {
		this.softdelete = softdelete;
	}

	public int getInventoryId() {
		return inventoryId;
	}

	public void setInventoryId(int inventoryId) {
		this.inventoryId = inventoryId;
	}
	
	


	
	
	
}