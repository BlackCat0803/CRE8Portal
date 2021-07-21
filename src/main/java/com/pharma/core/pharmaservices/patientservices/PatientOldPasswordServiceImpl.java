package com.pharma.core.pharmaservices.patientservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pharma.core.model.patientaccount.PatientOldPassword;
import com.pharma.core.repository.patient.PatientOldPasswordRespository;

/**
 * This is an implementation class that loads and stores the patient account old password data
 */
@Service("patientOldPasswordService")
public class PatientOldPasswordServiceImpl implements PatientOldPasswordService {

	@Autowired
	PatientOldPasswordRespository patRepo;
	
	public PatientOldPassword getPatientOldPasswordRecord(int id) {
	
		return patRepo.findOne(id);
		
	}

	
}
