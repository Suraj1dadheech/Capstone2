package com.costrategix.gbp.capstone2.actuator.Dao;


import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.costrategix.gbp.capstone2.actuator.DTO.CustomPhoneDto;
import com.costrategix.gbp.capstone2.actuator.model.Phone;



public interface PhoneRepo extends JpaRepository<Phone, Integer>{
	
	@Query(value = "select * from phone where phone.user_user_id = ?1", nativeQuery = true)
	Phone findPhoneByUserID(int id);
	
	@Transactional
	@Modifying
	@Query(value = "delete from phone where user_user_id = ?1", nativeQuery = true)
	void deletePhonesByUserId(int id);

	@Query(value = "select phone_no as phoneNo,sms_allowed as smsAllowed,phonetype.phonetype as phoneType\r\n"
			+ "from phone,phonetype\r\n"
			+ "where phone.phone_type_phonetypeid=phonetype.phonetypeid\r\n"
			+ "and phone.user_user_id=?1",nativeQuery = true)
	List<CustomPhoneDto> findAllphoneNos(int id);
}
