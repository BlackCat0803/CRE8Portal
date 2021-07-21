package com.pharma.core.pharmaservices.adminservices;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.pharma.core.formBean.LoginForm;

@Service
public interface AdminFileUploadService {

	boolean photoFileSave(int adminId, CommonsMultipartFile file, String savedPhotoFile, LoginForm loggedInUser);
	
	String getAdminPhoto(int adminId);
	
	String photoNameByAdminId(int id, String path);
	
}
