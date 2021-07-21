package com.pharma.core.repository.physician;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pharma.core.model.physician.PhysicianAssistantPhysician;
/**
 * This class is a Java interface that defines a data access contract
 *
 */
public interface PhysicianAssistantPhysicianRepository extends JpaRepository<PhysicianAssistantPhysician, Integer> {

	List<PhysicianAssistantPhysician> findByPhysicianId(int physicianId);
	
	List<PhysicianAssistantPhysician> findByPhysicianAssistantId(int physicianAssistantId);
	
	List<PhysicianAssistantPhysician> findByPhysicianAssistantIdAndDelFlag(int physicianAssistantId, String delFlag);
	
	List<PhysicianAssistantPhysician> findByPhysicianIdAndDelFlag(int physicianId, String delFlag);
	
	List<PhysicianAssistantPhysician> findByphysicianAssistantIdAndDelFlag(int assistantId, String delFlag);
	
	PhysicianAssistantPhysician findByPhysicianAssistantIdAndPhysicianId(int physicianAssistantId, int physicianId);
	
	
	
	
	@Modifying
	@Transactional
	void deleteByPhysicianAssistantId(int id);
	
	@Modifying
	@Transactional
	void deleteByPhysicianId(int id);
	
	
	@Query("select c from PhysicianAssistantPhysician c where physicianAssistantId =:physicianAssistantId and delFlag='No'")
	List<PhysicianAssistantPhysician> getAllPhysicianAssistantWisePhysicianList( @Param("physicianAssistantId") int physicianAssistantId);
	
}
