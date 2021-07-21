package com.pharma.core.pharmaservices.adminservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pharma.core.model.admin.AdminOldPassword;
import com.pharma.core.repository.admin.AdminOldPasswordRespository;

/**
 * This is an implementation class that loads and stores the admin account old password data
 *
 */
@Service("adminOldPasswordService")
public class AdminOldPasswordServiceImpl implements AdminOldPasswordService {

	@Autowired
	AdminOldPasswordRespository adminRepo;
	
	public AdminOldPassword getAdminOldPasswordRecord(int id) {
	
		return adminRepo.findOne(id);
		
	}

	
}
