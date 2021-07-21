package com.pharma.core.repository.physician;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pharma.core.model.physician.PhysicianGroup;
/**
 * This class is a Java interface that defines a data access contract
 *
 */
@Repository("physicianGroupRepository")
public interface PhysicianGroupRepository extends JpaRepository<PhysicianGroup, Integer> {

	List<PhysicianGroup> findByGroupId(int id);
	
	List<PhysicianGroup> findByPhysicianId(int id);
	
	PhysicianGroup findRecordByPhysicianId(int id);
	
	PhysicianGroup findRecordByPhysicianIdAndGroupId(int physicianId, int groupId);
	
	List<PhysicianGroup> findByPhysicianIdAndStatus(int id, String status);
	
	@Modifying
	@Transactional
	void deleteByPhysicianId(int id);
	
	@Modifying
	@Transactional
	void deleteByGroupId(int id);
	
	/*@Query("UPDATE PhysicianGroup c SET c.groupId = :otherGroupId WHERE c.groupId = :groupId")
	@Modifying(clearAutomatically = true)
	@org.springframework.transaction.annotation.Transactional
    int reAssignPhysicianGroup(@Param("otherGroupId") int otherGroupId, @Param("groupId") int groupId);*/
}
