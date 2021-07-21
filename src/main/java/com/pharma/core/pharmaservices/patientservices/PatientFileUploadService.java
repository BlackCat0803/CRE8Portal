package com.pharma.core.pharmaservices.patientservices;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.pharma.core.formBean.LoginForm;
import com.pharma.core.model.patientaccount.PatientFileUpload;

@Service
public interface PatientFileUploadService {

	boolean photoFileSave(int patientId, CommonsMultipartFile file, String savedPhotoFile, LoginForm loggedInUser);
	
	String getPatientPhoto(int patientId);
	
	String photoNameByPatientId(int id, String filepath);
	
	List<PatientFileUpload> getUploadedDocuments(int patientId);

	boolean documentFileSave(int patientId, CommonsMultipartFile file, String savedPhotoFile, LoginForm loggedInUser);
	
	int getLastDocId();
	
	boolean deleteFile(int fileId);
	
	String downloadFileName(int fileId, boolean isDownload);
	
	String photoNameByPhysicianId(int id, String filepath);
	
	Page<PatientFileUpload> getUploadedDocuments(int physicianId, Pageable page);

}
