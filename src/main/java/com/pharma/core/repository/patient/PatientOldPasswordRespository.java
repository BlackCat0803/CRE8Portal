package com.pharma.core.repository.patient;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pharma.core.model.patientaccount.PatientOldPassword;

/**
 * This class is a Java interface that defines a data access contract
 *
 */
@Repository("PatientOldPassword")
public interface PatientOldPasswordRespository extends JpaRepository<PatientOldPassword, Integer> {
	
	List<PatientOldPassword> findByPasswordAndPatientId(String password,int id);
	
	List<PatientOldPassword> findByPatientIdOrderByRowIdAsc(int patientId);

}
