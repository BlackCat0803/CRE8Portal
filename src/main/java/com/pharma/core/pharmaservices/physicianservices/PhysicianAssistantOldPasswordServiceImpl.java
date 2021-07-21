package com.pharma.core.pharmaservices.physicianservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pharma.core.model.physician.PhysicianAssistantOldPassword;
import com.pharma.core.repository.physician.PhysicianAssistantOldPasswordRespository;

/**
 * This is an implementation class that loads and stores the physician assistant account old password data
 *
 */
@Service("physicianAssistantOldPasswordService")
public class PhysicianAssistantOldPasswordServiceImpl implements PhysicianAssistantOldPasswordService {

	@Autowired
	PhysicianAssistantOldPasswordRespository phyRepo;
	
	public PhysicianAssistantOldPassword getPhysicianAssistantOldPasswordRecord(int id) {
	
		return phyRepo.findOne(id);
		
	}
}
