package com.pharma.core.repository.physician;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pharma.core.model.clinic.Clinic;
import com.pharma.core.model.physician.PhysicianClinic;
/**
 * This class is a Java interface that defines a data access contract
 *
 */
public interface PhysicianClinicRepository extends JpaRepository<PhysicianClinic, Integer> {

	List<PhysicianClinic> findByClinicId(int clinicId);
	
	List<PhysicianClinic> findByPhysicianId(int physicianId);
	
	List<PhysicianClinic> findByPhysicianIdAndDelFlagOrderById(int physicianId, String delFlag);
	
	List<PhysicianClinic> findByClinicIdAndDelFlag(int clinicId, String delFlag);
	
	PhysicianClinic findByPhysicianIdAndClinicId(int physicianId, int clinicId);
	
	@Modifying
	@Transactional
	void deleteByPhysicianId(int id);
	
	@Modifying
	@Transactional
	void deleteByClinicId(int id);
	
	
	@Query("select c from PhysicianClinic c where physicianId =:physicianId and delFlag='N'")
	List<PhysicianClinic> getAllPhysicianWiseClinicList( @Param("physicianId") int physicianId);
	
}
