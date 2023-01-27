package com.costrategix.gbp.capstone2.actuator.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.costrategix.gbp.capstone2.actuator.model.PhoneType;




@Repository
public interface PhoneTypeRepo extends JpaRepository<PhoneType, Integer>{

	@Query(value = "SELECT * from phonetype WHERE phonetype = ?1", nativeQuery = true)
	PhoneType findByPhoneType(String phone);

		
	@Query(value = "select * from phonetype where phonetype.phonetype = ?1", nativeQuery = true)
	PhoneType findPhoneTypeId(String type);
	

	@Query(value = "select * from phonetype where phonetype.phonetypeid = ?1", nativeQuery = true)
	PhoneType findDefaultPhoneType(int id);
}
