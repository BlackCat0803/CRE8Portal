package com.pharma.core.pharmaservices.pharmacygroupservices;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.pharma.core.formBean.LoginForm;
import com.pharma.core.model.pharmacygroup.GroupDirectorFileUpload;
import com.pharma.core.repository.pharmacygroup.GroupDirectorFileUploadRepository;
/**
 * This is an implementation class that loads and stores the group director file upload data
 */
@Service("groupDirectorFileUploadService")
public class GroupDirectorFileUploadServiceImpl implements GroupDirectorFileUploadService {
	
	@Autowired
	GroupDirectorFileUploadRepository groupDirectorFileRepo;
	
	public boolean photoFileSave(int groupDirectorId, CommonsMultipartFile file, String savedPhotoFile, LoginForm loggedInUser) {
		boolean isSaved = false;
		try {
			GroupDirectorFileUpload fileRec = null;
			fileRec = groupDirectorFileRepo.findByGroupDirectorIdAndFileType(groupDirectorId, "Photo");
			if (fileRec == null) {
				fileRec = new GroupDirectorFileUpload();
			}
			String oname=file.getOriginalFilename();
			
			fileRec.setFileDescription("");
			fileRec.setFileType("Photo");
			fileRec.setOriginalFileName(oname);
			fileRec.setGroupDirectorId(groupDirectorId);
			fileRec.setStoredFielName(savedPhotoFile);
			fileRec.setUploadedBy(loggedInUser.getUserid());
			fileRec.setUploadedDate(new java.sql.Date(new Date().getTime()));
			fileRec.setUserType(loggedInUser.getType());
			
			groupDirectorFileRepo.save(fileRec);
			isSaved=true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isSaved;
	}

	
	public String photoNameByGroupDirectorId(int fileId, String filepath) {
		String filename="";
		try {
			if (filepath != null && !"".equalsIgnoreCase(filepath)) {
				GroupDirectorFileUpload rec = groupDirectorFileRepo.findByGroupDirectorIdAndFileType(fileId, "Photo");
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
