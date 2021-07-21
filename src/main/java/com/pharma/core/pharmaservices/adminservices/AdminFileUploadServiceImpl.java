package com.pharma.core.pharmaservices.adminservices;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.pharma.core.formBean.LoginForm;
import com.pharma.core.model.admin.AdminFileUpload;
import com.pharma.core.repository.admin.AdminFileUploadRepository;
/**
 * This is an implementation class that loads and stores the admin account file upload data
 *
 */
@Service("adminFileUploadService")
public class AdminFileUploadServiceImpl implements AdminFileUploadService {

	@Autowired
	AdminFileUploadRepository adminFileRepo;
	
	public boolean photoFileSave(int adminId, CommonsMultipartFile file, String savedPhotoFile, LoginForm loggedInUser) {
		boolean isSaved = false;
		try {
			AdminFileUpload fileRec = null;
			fileRec = adminFileRepo.findByAdminIdAndFileType(adminId, "Photo");
			if (fileRec == null) {
				fileRec = new AdminFileUpload();
			}
			String oname=file.getOriginalFilename();
//			String extension = oname.substring(oname.lastIndexOf('.') + 1, oname.length());
			
			fileRec.setFileDescription("");
			fileRec.setFileType("Photo");
			fileRec.setOriginalFileName(oname);
			fileRec.setAdminId(adminId);
			fileRec.setStoredFielName(savedPhotoFile);
			fileRec.setUploadedBy(loggedInUser.getUserid());
			fileRec.setUploadedDate(new java.sql.Date(new Date().getTime()));
			fileRec.setUserType(loggedInUser.getType());
			
			adminFileRepo.save(fileRec);
			isSaved=true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isSaved;
	}

	public String getAdminPhoto(int adminId) {
		String fileName="";
		AdminFileUpload fileRec = null;
		fileRec = adminFileRepo.findByAdminIdAndFileType(adminId, "Photo");
		if (fileRec != null) {
			fileName = fileRec.getStoredFielName();
		}
		return fileName;
	}
	
	public String photoNameByAdminId(int fileId, String filepath) {
		String filename="";
		try {
			if (filepath != null && !"".equalsIgnoreCase(filepath)) {
				AdminFileUpload rec = adminFileRepo.findByAdminIdAndFileType(fileId, "Photo");
				if (rec != null && rec.getStoredFielName() != null && !"".equalsIgnoreCase(rec.getStoredFielName()) && filepath!=null) {
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
