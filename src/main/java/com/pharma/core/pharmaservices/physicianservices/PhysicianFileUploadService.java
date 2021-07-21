package com.pharma.core.pharmaservices.physicianservices;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.pharma.core.formBean.LoginForm;
import com.pharma.core.model.physician.PhysicianFileUpload;

@Service
public interface PhysicianFileUploadService {

	boolean photoFileSave(int physicianId, CommonsMultipartFile file, String savedPhotoFile, LoginForm loggedInUser);
	
	String getPhysicianPhoto(int physicianId);
	
	List<PhysicianFileUpload> getUploadedDocuments(int physicianId);
	
	boolean documentFileSave(int physicianId, CommonsMultipartFile file, String savedPhotoFile, LoginForm loggedInUser,
			String docPurpose, String expiryDate, String fileName);
	
	int getLastDocId();

	boolean deleteFile(int fileId);
	
	String downloadFileName(int fileId, boolean isDownload);
	
	String photoNameByPhysicianId(int id, String filepath);
	
	Page<PhysicianFileUpload> getUploadedDocuments(int physicianId, Pageable page);
	
	String logoNameByPhysicianId(int id, String filepath);
	
	boolean logoFileSave(int physicianId, CommonsMultipartFile file, String savedLogoFile, LoginForm loggedInUser);
	
	String getPhysicianLogo(int physicianId);
	
}
