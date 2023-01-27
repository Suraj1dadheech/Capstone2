package com.costrategix.gbp.capstone2.actuator.Dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.costrategix.gbp.capstone2.actuator.model.Inventory;

public interface InventoryRepo extends JpaRepository<Inventory, Integer>{
	
	@Modifying
    @Transactional
	@Query(value="UPDATE inventory SET trailer_count =trailer_count+1 WHERE inventory.group_color=?1",nativeQuery=true)
	void increaseTracktorCount(String color);
	

	
	@Query(value="SELECT group_color FROM inventory WHERE exist=1",nativeQuery = true)
	List<String> findAllColorsinInv();
	
	@Query(value = "SELECT inventory_id,exist,group_name,group_color,lead_driver,trailer_count,comment FROM inventory WHERE exist=1;",nativeQuery = true)
	List<Inventory> findAllInventory();
	
	@Query(value="SELECT group_color FROM inventory where inventory_id=?1",nativeQuery = true)
	String findColorById(int id);
	
	@Modifying
    @Transactional
	@Query(value="UPDATE inventory SET group_name=?1,lead_driver=?2 WHERE inventory_id=?3",nativeQuery = true)
	void updateGrpNameLeadDriver(String ipName, String ipLeadDriver, int ipid);
	
	@Query(value="SELECT inventory_id FROM inventory where group_color=?1 AND exist=1",nativeQuery = true)
	int findIdOfIpColor(String ipColor);
	

	@Query(value="SELECT trailer_count FROM inventory where group_color=?1",nativeQuery = true)
	int findNoOfTrailersByColor(String exColor);
	
	@Modifying
    @Transactional
	@Query(value="UPDATE inventory SET trailer_count =trailer_count+?1 WHERE inventory.inventory_id=?2",nativeQuery = true)
	void increaseTracktorCount(int noOftrailer, int idIpCol);
	
	@Modifying
    @Transactional
	@Query(value="UPDATE inventory set trailer_count=0 WHERE inventory.inventory_id=?1",nativeQuery=true)
	void makeTrailerCountZero(int id);
	
	@Modifying
    @Transactional
	@Query(value="UPDATE inventory SET group_name=?1,group_color=?2,lead_driver=?3 WHERE inventory_id=?4",nativeQuery = true)	
	void updateGrpClrGrpNameLeadDriver(String ipName, String ipColor, String ipLeadDriver, int ipid);
	
	@Modifying
    @Transactional
	@Query(value="UPDATE inventory SET exist=0 WHERE inventory_id=?1",nativeQuery = true)
	void softdeleteById(int id);
	
	
	@Modifying
    @Transactional
	@Query(value="UPDATE inventory SET exist=1,group_name=?1,lead_driver=?2,trailer_count=0 WHERE inventory_id=?3 AND exist=0;",nativeQuery = true)
	void updateExInv(String grpName,String leadDriver, int exid);

	

	
	@Modifying
    @Transactional
	@Query(value="UPDATE inventory SET trailer_count =trailer_count-1 WHERE inventory.group_color=?1 AND exist=1",nativeQuery=true)
	void decreaseTractorCount(String color);


	@Query(value = "SELECT inventory_id FROM inventory where group_color=?1",nativeQuery = true)
	int findbyColor(String groupcolor);	
	

	
//	@Modifying
//  @Transactional
//	@Query(value="UPDATE inventory SET tractor_count =tractor_count-1 WHERE inventory.group_color=?1 AND exist=1",nativeQuery=true)
//	void decreaseTractorCount(String color);
	
}
