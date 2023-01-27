package com.costrategix.gbp.capstone2.actuator.DTO;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.costrategix.gbp.capstone2.actuator.model.Trailer;

public class InvtreDto {
	
	
	private String  groupName;
	
	private String groupcolor;
	
	private int tractorCount;
	
	private String leadDriver;
	
	private String comment;
	
	private List<Trailer> trailers;

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

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public List<Trailer> getTrailers() {
		return trailers;
	}

	public void setTrailers(List<Trailer> trailers) {
		this.trailers = trailers;
	}
	
	
}

