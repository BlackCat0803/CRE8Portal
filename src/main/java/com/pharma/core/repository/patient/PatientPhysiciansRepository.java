package com.pharma.core.repository.patient;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pharma.core.model.patientaccount.PatientPhysicians;
/**
 * This class is a Java interface that defines a data access contract
 *
 */
public interface PatientPhysiciansRepository extends JpaRepository<PatientPhysicians, Integer> {

	List<PatientPhysicians> findByPhysicianId(int physicianId);
	
	List<PatientPhysicians> findByPatientId(int patientId);
	
	List<PatientPhysicians> findByPatientIdAndDelFlag(int patientId, String delFlag);
	
	List<PatientPhysicians> findByPhysicianIdAndDelFlag(int physicianId, String delFlag);
	
	PatientPhysicians findByPatientIdAndPhysicianId(int patientId, int physicianId);
	
	@Modifying
	@Transactional
	void deleteByPatientId(int id);
	
	@Modifying
	@Transactional
	void deleteByPhysicianId(int id);
	
	
	@Query("select c from PatientPhysicians c where patientId =:patientId and delFlag='No'")
	List<PatientPhysicians> getAllPatientWisePhysicianList( @Param("patientId") int patientId);
	
}
