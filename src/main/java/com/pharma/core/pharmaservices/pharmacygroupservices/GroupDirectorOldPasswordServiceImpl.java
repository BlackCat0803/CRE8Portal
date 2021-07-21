package com.pharma.core.pharmaservices.pharmacygroupservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pharma.core.model.pharmacygroup.GroupDirectorOldPassword;
import com.pharma.core.repository.pharmacygroup.GroupDirectorOldPasswordRespository;


/**
 * This is an implementation class that loads and stores the group director old password data
 */
@Service("groupDirectorOldPasswordService")
public class GroupDirectorOldPasswordServiceImpl implements GroupDirectorOldPasswordService {

	@Autowired
	GroupDirectorOldPasswordRespository groupDirectorRepo;
	
	public GroupDirectorOldPassword getGroupDirectorOldPasswordRecord(int id) {
	
		return groupDirectorRepo.findOne(id);
		
	}
}
