package com.pharma.core.pharmaservices.pharmacygroupservices;

import org.springframework.stereotype.Service;

import com.pharma.core.model.pharmacygroup.GroupDirectorOldPassword;

@Service
public interface GroupDirectorOldPasswordService {
	
	GroupDirectorOldPassword getGroupDirectorOldPasswordRecord(int id) ;
}
