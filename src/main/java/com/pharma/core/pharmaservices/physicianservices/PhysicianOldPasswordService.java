package com.pharma.core.pharmaservices.physicianservices;

import org.springframework.stereotype.Service;

import com.pharma.core.model.physician.PhysicianOldPassword;

@Service
public interface PhysicianOldPasswordService {
	
	PhysicianOldPassword getPhysicianOldPasswordRecord(int id) ;
	
	
}
