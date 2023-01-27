package com.costrategix.gbp.capstone2.actuator.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomConfig {
	
	@Bean
	public List<String> typesOfLocations(){
		List<String> locationTypes=new ArrayList<String>(Arrays.asList("Stadium","Arena","Open Air","Storage"));
		return locationTypes;
	}
	
	@Bean
	public List<String> typesOfColor(){
		List<String> listColor=new ArrayList<String>(Arrays.asList("Pink",
				"Red","Orange","Yellow","Green","Teal","Blue","Purple","Brown"));
		return listColor;
	}
	
}
