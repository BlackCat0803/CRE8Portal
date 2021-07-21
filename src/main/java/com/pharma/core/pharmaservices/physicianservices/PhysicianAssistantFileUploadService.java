package com.pharma.core.pharmaservices.physicianservices;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.pharma.core.formBean.LoginForm;

@Service
public interface PhysicianAssistantFileUploadService {
	
	boolean photoFileSave(int physicianId, CommonsMultipartFile file, String savedPhotoFile, LoginForm loggedInUser);
	
	String photoNameByAssistantId(int id, String filepath);
	
}
