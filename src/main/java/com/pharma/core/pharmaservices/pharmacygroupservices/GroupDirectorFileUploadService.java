package com.pharma.core.pharmaservices.pharmacygroupservices;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.pharma.core.formBean.LoginForm;

@Service
public interface GroupDirectorFileUploadService {
	
	boolean photoFileSave(int groupDirectorId, CommonsMultipartFile file, String savedPhotoFile, LoginForm loggedInUser);
	
	String photoNameByGroupDirectorId(int id, String filepath);
	
}
