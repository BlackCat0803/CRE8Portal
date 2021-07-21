package com.pharma.core.pharmaservices.physicianservices;

import org.springframework.stereotype.Service;

import com.pharma.core.model.physician.PhysicianAssistantOldPassword;

@Service
public interface PhysicianAssistantOldPasswordService {
	
	PhysicianAssistantOldPassword getPhysicianAssistantOldPasswordRecord(int id) ;
}
