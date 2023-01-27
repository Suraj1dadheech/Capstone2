package com.costrategix.gbp.capstone2.actuator.Dao;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.costrategix.gbp.capstone2.actuator.model.UserRole;



public interface UserRoleRepo extends JpaRepository<UserRole, Integer> {

	@Query(value = "select * from user_role where user_user_id = ?1", nativeQuery = true)
	UserRole FindByUserId(int id);

	@Modifying
    @Transactional
	@Query(value = "delete from user_role where user_user_id = ?1", nativeQuery = true)
	void deleteRoleByUserId(int userId);

	



}
