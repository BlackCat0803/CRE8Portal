package com.pharma.core.pharmaservices.pharmacygroupservices;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.pharma.core.formBean.LoginForm;

@Service
public interface GroupMasterFileUploadService {
	
	boolean logoFileSave(int groupId, CommonsMultipartFile file, String savedLogoFile, LoginForm loggedInUser);
	
	String logoNameByGroupId(int id, String filepath);
	
}
