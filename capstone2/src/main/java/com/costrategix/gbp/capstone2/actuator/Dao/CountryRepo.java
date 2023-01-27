package com.costrategix.gbp.capstone2.actuator.Dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.costrategix.gbp.capstone2.actuator.model.CountryInfo;

public interface CountryRepo extends JpaRepository<CountryInfo, Integer>{
	
	@Query(value="SELECT * FROM countryinfo where zipcode=?1",nativeQuery = true)
	CountryInfo findCountryInfoByZip(String zip);
	
	/*@Query(value="SELECT * FROM countryinfo where country=?1",nativeQuery = true)
	List<CountryInfo> findCountryInfo(String country);*/
	
	@Query(value="SELECT distinct state FROM countryinfo WHERE country=?1",nativeQuery = true)
	List<String> findCountryInfo(String country);
		
}
