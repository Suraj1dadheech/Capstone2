package com.costrategix.gbp.capstone2.actuator.Dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.costrategix.gbp.capstone2.actuator.DTO.CustomRolesDto;
import com.costrategix.gbp.capstone2.actuator.model.Role;




@Repository
public interface RoleRepo extends JpaRepository<Role, Integer> {
	

	
	@Query(value = "SELECT * FROM roles WHERE role_name = ?1", nativeQuery = true)
	Role findByName(String roleName);
	

	@Query(value = "select role_name as roleName\r\n"
			+ "from roles,user_role\r\n"
			+ "where user_role.role_role_id=roles.role_id\r\n"
			+ "AND user_user_id= ?1", nativeQuery = true)
	List<CustomRolesDto> findAllRolesByUserId(int id);

	
	
//	@Query(value = "select roles.role_id from roles where roles.role_name = ?1", nativeQuery = true)
//	int findRoleId(String roleName);


}
