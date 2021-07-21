package com.pharma.core.repository.physician;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pharma.core.model.physician.PhysicianOldPassword;
/**
 * This class is a Java interface that defines a data access contract
 *
 */
@Repository("PhysicianOldPassword")
public interface PhysicianOldPasswordRespository extends JpaRepository<PhysicianOldPassword, Integer> {
	
	List<PhysicianOldPassword> findByPasswordAndPhysicianId(String password,int id);
	
	List<PhysicianOldPassword> findByPhysicianIdOrderByRowIdAsc(int physicianId);

}
