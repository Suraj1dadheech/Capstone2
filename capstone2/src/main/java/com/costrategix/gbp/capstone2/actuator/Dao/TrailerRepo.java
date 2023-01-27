	package com.costrategix.gbp.capstone2.actuator.Dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.costrategix.gbp.capstone2.actuator.DTO.TrailerDto;
import com.costrategix.gbp.capstone2.actuator.model.Trailer;

public interface TrailerRepo extends JpaRepository<Trailer, Integer>{
	
	@Query(value = "SELECT trailer_no FROM trailer WHERE softdelete=0",nativeQuery = true)
	List<String> getAllTrailerNo();
	
	@Query(value = "SELECT sky_bitz_no FROM trailer WHERE softdelete=0",nativeQuery = true)
	List<String> getAllSkyBtzNo();

	@Query(value = "SELECT * FROM trailer WHERE trailer_id = ?1", nativeQuery = true)
	Trailer findTrailerById(int id);
	
	@Query(value="SELECT trailer_id as trailerId,group_color as groupColor,trailertype as trailertype,comment as comment,image as image,sky_bitz_no as skyBitzNo,tractor_weight as tractorWeight,trailer_name as trailerName,trailer_no as trailerNo,trailer_weight as trailerWeight, inventoryId as inventoryId from trailer WHERE trailer.softdelete=0",nativeQuery = true)
	List<TrailerDto> findAllTrailers();
	
	@Modifying
    @Transactional
	@Query(value = "update trailer set comment=?1 where trailer_id=?2",nativeQuery = true)
	void addComment(String comment,int id);
	
	@Modifying
    @Transactional
	@Query(value = "UPDATE trailer set image=?1 where trailer_id=?2",nativeQuery = true)	
	void saveImagePath(String filePath, int id);
	
	@Modifying
    @Transactional
	@Query(value="UPDATE trailer SET group_color=?1 where group_color=?2 AND trailer_id=?3 AND softdelete=0",nativeQuery = true)
	void updateTrailersColor(String ipColor, String exColor,int id);
	
	@Query(value="SELECT trailer_id FROM trailer WHERE group_color=?1 AND softdelete=0",nativeQuery = true)
	List<Integer> findIdsWithExColor(String exColor);
	
	@Query(value="SELECT * FROM trailer where group_color=?1 AND softdelete=0",nativeQuery = true)
	List<Trailer> findTrailersByColor(String color);
	
	@Modifying
    @Transactional
	@Query(value="UPDATE trailer SET softdelete=1 WHERE trailer_id=?1",nativeQuery = true)
	void softdeleteById(Integer ids);

	@Query(value="SELECT * from trailer WHERE inventoryid=?1 and softdelete=0",nativeQuery = true)
	List<Trailer> findTrailerByInventoryId(int id);

	
	@Modifying
    @Transactional
	@Query(value="UPDATE trailer SET inventoryid=0, group_color=null WHERE inventoryid=?1",nativeQuery = true)
	void deleteinventoryIdInTrailer(int id);

	
	@Query(value = "SELECT * FROM trailer WHERE trailer_id = ?1 and softdelete=0", nativeQuery = true)
	Trailer findTrailerByIds(int id);
	
	
	
}
