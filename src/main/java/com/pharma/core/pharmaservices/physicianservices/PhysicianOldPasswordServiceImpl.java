package com.pharma.core.pharmaservices.physicianservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pharma.core.model.physician.PhysicianOldPassword;
import com.pharma.core.repository.physician.PhysicianOldPasswordRespository;

/**
 * This is an implementation class that loads and stores the physician old password data
 *
 */
@Service("physicianOldPasswordService")
public class PhysicianOldPasswordServiceImpl implements PhysicianOldPasswordService {

	@Autowired
	PhysicianOldPasswordRespository phyRepo;
	
	public PhysicianOldPassword getPhysicianOldPasswordRecord(int id) {
	
		return phyRepo.findOne(id);
		
	}

	
}
