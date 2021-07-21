package com.pharma.core.pharmaservices.patientservices;

import java.io.File;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.pharma.core.formBean.LoginForm;
import com.pharma.core.model.patientaccount.PatientFileUpload;
import com.pharma.core.repository.admin.AdminAccountRepository;
import com.pharma.core.repository.patient.PatientFileUploadRepository;
import com.pharma.core.repository.physician.PhysicianAccountRespository;
import com.pharma.core.repository.physician.PhysicianAssistantAccountRespository;
import com.pharma.core.repository.physician.PhysicianProfileRespository;
/**
 * This is an implementation class that loads and stores the patient account file upload data
 */
@Service("patientFileUploadService")
public class PatientFileUploadServiceImpl implements PatientFileUploadService {
	
	@Autowired
	PatientFileUploadRepository patientFileRepo;
	
	@Autowired
	PhysicianAccountRespository phyAccount;
	
	@Autowired
	PhysicianProfileRespository phyProfile;
	
	@Autowired
	PhysicianAssistantAccountRespository assistantRepo;
	
	@Autowired
	AdminAccountRepository adminRepo;
	
	
	public boolean photoFileSave(int patientId, CommonsMultipartFile file, String savedPhotoFile, LoginForm loggedInUser) {
		boolean isSaved = false;
		try {
			PatientFileUpload fileRec = null;
			fileRec = patientFileRepo.findByPatientIdAndFileType(patientId, "Photo");
			if (fileRec == null) {
				fileRec = new PatientFileUpload();
			}
			String oname=file.getOriginalFilename();
			
			fileRec.setFileDescription("");
			fileRec.setFileType("Photo");
			fileRec.setOriginalFileName(oname);
			fileRec.setPatientId(patientId);
			fileRec.setStoredFielName(savedPhotoFile);
			fileRec.setUploadedBy(loggedInUser.getUserid());
			fileRec.setUploadedDate(new java.sql.Date(new Date().getTime()));
			fileRec.setUserType(loggedInUser.getType());
			
			patientFileRepo.save(fileRec);
			isSaved=true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isSaved;
	}

	public String getPatientPhoto(int patientId) {
		String fileName="";
		PatientFileUpload fileRec = null;
		fileRec = patientFileRepo.findByPatientIdAndFileType(patientId, "Photo");
		if (fileRec != null) {
			fileName = fileRec.getStoredFielName();
		}
		return fileName;
	}

	public String photoNameByPatientId(int id, String filepath) {
		String filename="";
		try {
			if (filepath != null && !"".equalsIgnoreCase(filepath)) {
				PatientFileUpload rec = patientFileRepo.findByPatientIdAndFileType(id, "Photo");
				if (rec != null && rec.getStoredFielName() != null && !"".equalsIgnoreCase(rec.getStoredFielName())) {
					filename = rec.getStoredFielName().substring(rec.getStoredFielName().indexOf(filepath));
					filename = filename.replace("\\", "/");
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return filename; 
	}

	public List<PatientFileUpload> getUploadedDocuments(int patientId) {
		return patientFileRepo.findByPatientIdAndFileTypeNot(patientId, "Photo");
	}

	public int getLastDocId() {
		int no=0;
		try {
			no=patientFileRepo.getMaxFileId();
		} catch(Exception e){ }
		return no;
	}
	
	public boolean deleteFile(int fileId) {
		PatientFileUpload fileRec = patientFileRepo.findOne(fileId);
		if (fileRec != null) {
			File f = new File(fileRec.getStoredFielName());
			if (f.exists()) 
				f.delete();
			
			patientFileRepo.delete(fileId);	
		}
		return true;
	}

	public String downloadFileName(int fileId, boolean isDownload) {
		String fileName = "";
		if (isDownload)
			fileName = patientFileRepo.findOne(fileId).getOriginalFileName();
		else
			fileName = patientFileRepo.findOne(fileId).getStoredFielName();
		return fileName;
	}

	public String photoNameByPhysicianId(int id, String filepath) {
		String filename="";
		try {
			if (filepath != null && !"".equalsIgnoreCase(filepath)) {
				PatientFileUpload rec = patientFileRepo.findByPatientIdAndFileType(id, "Photo");
				if (rec != null && rec.getStoredFielName() != null && !"".equalsIgnoreCase(rec.getStoredFielName())) {
					filename = rec.getStoredFielName().substring(rec.getStoredFielName().indexOf(filepath));
					filename = filename.replace("\\", "/");
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return filename; 
	}

	public boolean documentFileSave(int patientId, CommonsMultipartFile file, String savedPhotoFile, LoginForm loggedInUser) {
		boolean isSaved = false;
		try {
			PatientFileUpload fileRec = null;
			fileRec = new PatientFileUpload();
			
			String oname=file.getOriginalFilename();
			
			fileRec.setFileDescription("");
			fileRec.setFileType("Doc");
			fileRec.setOriginalFileName(oname);
			fileRec.setPatientId(patientId);
			fileRec.setStoredFielName(savedPhotoFile);
			fileRec.setUploadedBy(loggedInUser.getUserid());
			fileRec.setUploadedDate(new java.sql.Date(new Date().getTime()));
			fileRec.setUserType(loggedInUser.getType());
			
			patientFileRepo.save(fileRec);
			isSaved=true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isSaved;
	}

	public Page<PatientFileUpload> getUploadedDocuments(int patientId, Pageable page) {
		return patientFileRepo.findByPatientIdAndFileTypeNot(patientId, "Photo", page);
	}


}
