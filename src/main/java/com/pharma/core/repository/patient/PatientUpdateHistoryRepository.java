package com.pharma.core.repository.patient;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pharma.core.model.patientaccount.PatientUpdateHistory;
/**
 * This class is a Java interface that defines a data access contract
 *
 */
@Repository("patientUpdateHistoryRepository")
public interface PatientUpdateHistoryRepository extends JpaRepository<PatientUpdateHistory, Integer> {

	List<PatientUpdateHistory> findByPatientId(int id);
	
}
