package com.costrategix.gbp.capstone2.actuator.Dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.costrategix.gbp.capstone2.actuator.DTO.AllUsersDto;
import com.costrategix.gbp.capstone2.actuator.DTO.CustomRolesDto;
import com.costrategix.gbp.capstone2.actuator.DTO.CustomUserDto;
import com.costrategix.gbp.capstone2.actuator.DTO.NewUserDto;
//import com.costrategix.gbp.capstone2.actuator.DTO.GetUserIdsDto;
import com.costrategix.gbp.capstone2.actuator.model.User;



public interface UserRepo extends JpaRepository<User, Integer>{
	
	@Query(value = "SELECT * FROM user WHERE email = ?1", nativeQuery = true)
	User findByEmail(String email);
	
	@Query(value = "SELECT * FROM user WHERE user_id = ?1", nativeQuery = true)
	User findById(int id);
	
	
	@Query(value = "select roles.role_name \r\n"
			+ "from user\r\n"
			+ "Join user_role\r\n"
			+ "on user.user_id = user_role.user_user_id\r\n"
			+ "Join roles\r\n"
			+ "on user_role.role_role_id = roles.role_id\r\n"
			+ "where user.user_id = ?1", nativeQuery = true)
	String findRoleById(int id);
	
	
	@Query(value = "select phone.phone_no from phone, user where phone.user_user_id = user.user_id and phone.user_user_id = ?1", nativeQuery = true)
	String findPhoneById(int id);
	
	
	@Query(value = "select phone.sms_allowed from phone, user where phone.user_user_id = user.user_id and phone.user_user_id = ?1", nativeQuery = true)
	boolean findSMSById(int id);
	
	@Query(value = "select phonetype.phonetype from user\r\n"
			+ "join phone on\r\n"
			+ "user.user_id = phone.user_user_id\r\n"
			+ "join phonetype on\r\n"
			+ "phone.phone_type_phonetypeid = phonetype.phonetypeid \r\n"
			+ "where user.user_id = ?1", nativeQuery = true)
	String findPhoneTypeById(int id);


	
	@Query(value = "SELECT user.user_id as userId,user.lastseen as lastseen,user.status as status,user.email as email,phone.sms_allowed as smsAllowed,roles.role_name as roleName,user.first_name as firstName,user.last_name as lastName,phone.phone_no as phoneNo\r\n"
			+ "FROM user,phone,roles,user_role\r\n"
			+ "WHERE user_role.role_role_id=roles.role_id\r\n"
			+ "AND phone.user_user_id=user.user_id\r\n"
			+ "AND user.user_id=user_role.user_user_id;",nativeQuery = true)
	List<AllUsersDto> findByUserDto();
	
	@Query(value = "SELECT user.user_id as userId,user.lastseen as lastseen,user.status as status,user.email as email,phone.sms_allowed as smsAllowed,roles.role_name as roleName,user.first_name as firstName,user.last_name as lastName,phone.phone_no as phoneNo\r\n"
			+ "FROM user,phone,roles,user_role\r\n"
			+ "WHERE user_role.role_role_id=roles.role_id\r\n"
			+ "AND phone.user_user_id=user.user_id\r\n"
			+ "AND user.user_id=user_role.user_user_id\r\n"
			+ "AND roles.role_name=?1",nativeQuery = true)
	List<AllUsersDto> filterByUserDtoRole(String role);
	
	@Query(value = "SELECT user.user_id as userId,user.lastseen as lastseen,user.status as status,user.email as email,phone.sms_allowed as smsAllowed,roles.role_name as roleName,user.first_name as firstName,user.last_name as lastName,phone.phone_no as phoneNo\r\n"
			+ "FROM user,phone,roles,user_role\r\n"
			+ "WHERE user_role.role_role_id=roles.role_id\r\n"
			+ "AND phone.user_user_id=user.user_id\r\n"
			+ "AND user.user_id=user_role.user_user_id\r\n"
			+ "AND user.status=?1",nativeQuery = true)
	List<AllUsersDto> filterByUserDtoStatus(String status);
	
	@Query(value = "SELECT user.user_id as userId,user.lastseen as lastseen,user.status as status,user.email as email,phone.sms_allowed as smsAllowed,roles.role_name as roleName,user.first_name as firstName,user.last_name as lastName,phone.phone_no as phoneNo\r\n"
			+ "FROM user,phone,roles,user_role\r\n"
			+ "WHERE user_role.role_role_id=roles.role_id\r\n"
			+ "AND phone.user_user_id=user.user_id\r\n"
			+ "AND user.user_id=user_role.user_user_id\r\n"
			+ "AND roles.role_name=?1\r\n"
			+ "AND user.status=?2",nativeQuery = true)
	List<AllUsersDto> filterByUserDtoRoleStatus(String role,String status);
	
	@Query(value = "SELECT * FROM user where email=?1",nativeQuery = true)
	AllUsersDto findByEmailDto(String email);
	
	@Query(value = "select user.user_id as userId ,user.first_name as firstName,user.last_name as lastName,user.status as status,user.lastseen as lastSeen,user.email as email\r\n"
			+ "from user WHERE user.user_id=?1",nativeQuery = true)
	List<CustomUserDto> findAllTheUsersDto(int id);
	
	@Query(value = "SELECT roles.role_id as roleId, roles.role_name as roleName\r\n"
			+ "FROM user,roles,user_role\r\n"
			+ "where user.user_id=user_role.user_user_id\r\n"
			+ "and roles.role_id=user_role.role_role_id\r\n"
			+ "and user.user_id=?1;", nativeQuery = true)
	List<CustomRolesDto> findRoleIdaa(int id);
	
	/*@Query(value = "select user_id as userId,email as Email,first_name as firstName,last_name as lastName,lastseen as Lastseen,status as Status\r\n"
			+ "from user",nativeQuery = true)
	List<CustomUserDto> findUserByCustomUserDto();*/
	
	@Query(value = "select user_id as userId,email as Email,first_name as firstName,last_name as lastName,lastseen as Lastseen,status as Status\r\n"
			+ "from user where exist = 1",nativeQuery = true)
	List<CustomUserDto> findUserByCustomUserDto();
	
	
	@Modifying
    @Transactional
	@Query(value = "delete from user where email=?1",nativeQuery=true)
	void deleteById(String email);

	
	@Query(value = "select * from user where temppassword = ?1",nativeQuery=true)
	User findByTempPassword(String temppassword);



	
//	User findByUserName(String username);
	

}
