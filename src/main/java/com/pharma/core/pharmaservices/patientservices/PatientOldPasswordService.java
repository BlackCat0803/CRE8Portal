package com.pharma.core.pharmaservices.patientservices;

import org.springframework.stereotype.Service;

import com.pharma.core.model.patientaccount.PatientOldPassword;

@Service
public interface PatientOldPasswordService {
	
	PatientOldPassword getPatientOldPasswordRecord(int id) ;
	
	
}
