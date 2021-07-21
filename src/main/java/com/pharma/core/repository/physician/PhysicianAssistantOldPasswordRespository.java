package com.pharma.core.repository.physician;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pharma.core.model.physician.PhysicianAssistantOldPassword;



/**
 * This class is a Java interface that defines a data access contract
 *
 */
@Repository("PhysicianAssistantOldPassword")
public interface PhysicianAssistantOldPasswordRespository extends JpaRepository<PhysicianAssistantOldPassword, Integer> {
	
	List<PhysicianAssistantOldPassword> findByPasswordAndPhysicianAssistantId(String password,int id);

	
	List<PhysicianAssistantOldPassword> findByPhysicianAssistantIdOrderByRowIdAsc(int physicianAssistantId);
}
