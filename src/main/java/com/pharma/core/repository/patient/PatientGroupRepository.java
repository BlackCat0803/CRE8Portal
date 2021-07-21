package com.pharma.core.repository.patient;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pharma.core.model.patientaccount.PatientGroup;
/**
 * This class is a Java interface that defines a data access contract
 *
 */
public interface PatientGroupRepository extends JpaRepository<PatientGroup, Integer> {

	List<PatientGroup> findByGroupId(int groupId);
	
	List<PatientGroup> findByPatientId(int patientId);
	
	List<PatientGroup> findByPatientIdAndDelFlag(int patientId, String delFlag);
	
	List<PatientGroup> findByGroupIdAndDelFlag(int groupId, String delFlag);
	
	PatientGroup findByPatientIdAndGroupId(int patientId, int groupId);
	
	@Modifying
	@Transactional
	void deleteByPatientId(int id);
	
	@Modifying
	@Transactional
	void deleteByGroupId(int id);
	
	
	@Query("select c from PatientGroup c where patientId =:patientId and delFlag='No'")
	List<PatientGroup> getAllPatientWiseGroupList( @Param("patientId") int patientId);
	
}
