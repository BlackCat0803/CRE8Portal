package com.pharma.core.pharmaservices.pharmacygroupservices;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.pharma.core.formBean.LoginForm;
import com.pharma.core.model.pharmacygroup.GroupMasterFileUpload;
import com.pharma.core.repository.pharmacygroup.GroupMasterFileUploadRepository;
/**
 * This is an implementation class that loads and stores the group master file upload data
 *
 */
@Service("groupMasterFileUploadService")
public class GroupMasterFileUploadServiceImpl implements GroupMasterFileUploadService {
	
	@Autowired
	GroupMasterFileUploadRepository groupMasterFileRepo;
	
	public boolean logoFileSave(int groupId, CommonsMultipartFile file, String savedLogoFile, LoginForm loggedInUser) {
		boolean isSaved = false;
		try {
			GroupMasterFileUpload fileRec = null;
			fileRec = groupMasterFileRepo.findByGroupIdAndFileType( groupId, "Logo");
			if (fileRec == null) {
				fileRec = new GroupMasterFileUpload();
			}
			String oname=file.getOriginalFilename();
			
			fileRec.setFileDescription("");
			fileRec.setFileType("Logo");
			fileRec.setOriginalFileName(oname);
			fileRec.setGroupId(groupId);
			fileRec.setStoredFileName(savedLogoFile);
			fileRec.setUploadedBy(loggedInUser.getUserid());
			fileRec.setUploadedDate(new java.sql.Date(new Date().getTime()));
			fileRec.setUserType(loggedInUser.getType());
			
			groupMasterFileRepo.save(fileRec);
			isSaved=true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isSaved;
	}

	
	public String logoNameByGroupId(int fileId, String filepath) {
		String filename="";
		try {
			if (filepath != null && !"".equalsIgnoreCase(filepath)) {
				GroupMasterFileUpload rec = groupMasterFileRepo.findByGroupIdAndFileType(fileId, "Logo");
				if (rec != null && rec.getStoredFileName() != null && !"".equalsIgnoreCase(rec.getStoredFileName())) {
					filename = rec.getStoredFileName().substring(rec.getStoredFileName().indexOf(filepath));
					filename = filename.replace("\\", "/");
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return filename; 
	}

}
