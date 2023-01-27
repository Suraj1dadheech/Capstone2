package com.costrategix.gbp.capstone2.actuator.Dao;


import java.sql.Timestamp;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.costrategix.gbp.capstone2.actuator.model.AuthToken;

public interface AuthTokenRepo extends JpaRepository<AuthToken, Integer> {

	
	/*@Query(value = "SELECT auth_token.expirationtime from auth_token where auth_token.user_user_id=?1 ",nativeQuery = true)
	Timestamp getExpirationDateDto(int id);*/
	
	@Query(value = "SELECT auth_token.expirationtime FROM auth_token,user where auth_token.user_user_id=user.user_id AND user.email=?1",nativeQuery = true)
	Timestamp getExpirationDateDto(String email);


	@Query(value = "select * from auth_token where user_user_id=?1", nativeQuery = true)
	AuthToken getTokenByUserId(int id);

	@Query(value = "select auth_token.authid from user, auth_token where user.user_id = auth_token.user_user_id and user.email = ?1", nativeQuery = true)
	int findAuthIdbyUserEmail(String email);

	@Modifying
    @Transactional
	@Query(value = "delete from auth_token where authid = ?1", nativeQuery = true)
	void deleteAuthTokenById(int authId);


	

	
	


}
