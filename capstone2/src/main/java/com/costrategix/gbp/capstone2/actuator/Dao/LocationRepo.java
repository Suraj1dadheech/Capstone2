package com.costrategix.gbp.capstone2.actuator.Dao;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.costrategix.gbp.capstone2.actuator.DTO.CustomLocationDto;
import com.costrategix.gbp.capstone2.actuator.model.Location;

public interface LocationRepo extends JpaRepository<Location, Integer> {

	
	@Query(value="SELECT distinct location.location_type as locationType FROM location",nativeQuery = true)
	List<CustomLocationDto> getLocationTypes();
	

	@Query(value="select * from location where location_id= ?1", nativeQuery = true)
	Location findById(int id);
	
	@Query(value="select * from location where location_id= ?1", nativeQuery = true)
	Optional<Location> findByLocationId(int id);

}
