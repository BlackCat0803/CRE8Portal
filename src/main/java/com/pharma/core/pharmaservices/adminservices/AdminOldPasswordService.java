package com.pharma.core.pharmaservices.adminservices;

import org.springframework.stereotype.Service;

import com.pharma.core.model.admin.AdminOldPassword;

@Service
public interface AdminOldPasswordService {
	
	AdminOldPassword getAdminOldPasswordRecord(int id) ;
	
	
}
