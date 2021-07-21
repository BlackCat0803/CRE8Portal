package com.pharma.core.pharmaservices.physicianservices;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.pharma.core.formBean.LoginForm;
import com.pharma.core.model.physician.PhysicianAssistantFileUpload;
import com.pharma.core.repository.physician.PhysicianAssistantFileUploadRepository;
/**
 * This is an implementation class that loads and stores the physician assistant file upload data
 */
@Service("physicianAssistantFileUploadService")
public class PhysicianAssistantFileUploadServiceImpl implements PhysicianAssistantFileUploadService {
	
	@Autowired
	PhysicianAssistantFileUploadRepository physicianFileRepo;
	
	public boolean photoFileSave(int assistantId, CommonsMultipartFile file, String savedPhotoFile, LoginForm loggedInUser) {
		boolean isSaved = false;
		try {
			PhysicianAssistantFileUpload fileRec = null;
			fileRec = physicianFileRepo.findByPhysicianAssistantIdAndFileType(assistantId, "Photo");
			if (fileRec == null) {
				fileRec = new PhysicianAssistantFileUpload();
			}
			String oname=file.getOriginalFilename();
			
			fileRec.setFileDescription("");
			fileRec.setFileType("Photo");
			fileRec.setOriginalFileName(oname);
			fileRec.setPhysicianAssistantId(assistantId);
			fileRec.setStoredFielName(savedPhotoFile);
			fileRec.setUploadedBy(loggedInUser.getUserid());
			fileRec.setUploadedDate(new java.sql.Date(new Date().getTime()));
			fileRec.setUserType(loggedInUser.getType());
			
			physicianFileRepo.save(fileRec);
			isSaved=true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isSaved;
	}

	
	public String photoNameByAssistantId(int fileId, String filepath) {
		String filename="";
		try {
			if(filepath != null && !"".equalsIgnoreCase(filepath)) {
				PhysicianAssistantFileUpload rec = physicianFileRepo.findByPhysicianAssistantIdAndFileType(fileId, "Photo");
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
	
}
