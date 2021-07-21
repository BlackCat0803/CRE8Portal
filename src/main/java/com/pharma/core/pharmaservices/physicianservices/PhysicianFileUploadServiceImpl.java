package com.pharma.core.pharmaservices.physicianservices;

import java.io.File;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.pharma.core.formBean.LoginForm;
import com.pharma.core.model.physician.PhysicianFileUpload;
import com.pharma.core.repository.physician.PhysicianFileUploadRepository;
import com.pharma.core.util.PharmacyUtil;
/**
 * This is an implementation class that loads and stores the physician file upload data
 */
@Service("physicianFileUploadService")
public class PhysicianFileUploadServiceImpl implements PhysicianFileUploadService {

	@Autowired
	PhysicianFileUploadRepository physicianFileRepo;
	
	public boolean photoFileSave(int physicianId, CommonsMultipartFile file, String savedPhotoFile, LoginForm loggedInUser) {
		boolean isSaved = false;
		try {
			PhysicianFileUpload fileRec = null;
			fileRec = physicianFileRepo.findByPhysicianIdAndFileType(physicianId, "Photo");
			if (fileRec == null) {
				fileRec = new PhysicianFileUpload();
			}
			String oname=file.getOriginalFilename();
//			String extension = oname.substring(oname.lastIndexOf('.') + 1, oname.length());
			
			fileRec.setFileDescription("");
			fileRec.setFileType("Photo");
			fileRec.setOriginalFileName(oname);
			fileRec.setPhysicianId(physicianId);
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

	public String getPhysicianPhoto(int physicianId) {
		String fileName="";
		PhysicianFileUpload fileRec = null;
		fileRec = physicianFileRepo.findByPhysicianIdAndFileType(physicianId, "Photo");
		if (fileRec != null) {
			fileName = fileRec.getStoredFielName();
		}
		return fileName;
	}

	public List<PhysicianFileUpload> getUploadedDocuments(int physicianId) {
		return physicianFileRepo.findByPhysicianIdAndUploadFiles(physicianId, "Doc");
	}
	
	public boolean documentFileSave(int physicianId, CommonsMultipartFile file, String savedPhotoFile, LoginForm loggedInUser,
			String docPurpose, String expiryDate, String fileName) {
		boolean isSaved = false;
		try {
			PhysicianFileUpload fileRec = null;
			if(!docPurpose.equalsIgnoreCase("Other"))
				fileRec = physicianFileRepo.findByPhysicianIdAndFilePurpose(physicianId, docPurpose);
			if (fileRec == null)
				fileRec = new PhysicianFileUpload();
			
			String oname=file.getOriginalFilename();
			
			fileRec.setFileDescription("");
			fileRec.setFileType("Doc");
			fileRec.setOriginalFileName(oname);
			fileRec.setPhysicianId(physicianId);
			fileRec.setStoredFielName(savedPhotoFile);
			fileRec.setUploadedBy(loggedInUser.getUserid());
			fileRec.setUploadedDate(new java.sql.Date(new Date().getTime()));
			fileRec.setUserType(loggedInUser.getType());
			
			fileRec.setFilePurpose(docPurpose);
			fileRec.setOtherDocFileName(fileName);
			if (expiryDate != null && !"".equalsIgnoreCase(expiryDate))
				fileRec.setDocExpiryDate( PharmacyUtil.getSqlDateFromString(expiryDate) );
			
			physicianFileRepo.save(fileRec);
			isSaved=true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isSaved;
	}
	
	public int getLastDocId() {
		int no=0;
		try {
			no=physicianFileRepo.getMaxFileId();
		} catch(Exception e){ }
		return no;
	}
	
	public boolean deleteFile(int fileId) {
		PhysicianFileUpload fileRec = physicianFileRepo.findOne(fileId);
		if (fileRec != null) {
			File f = new File(fileRec.getStoredFielName());
			if (f.exists()) 
				f.delete();
			
			physicianFileRepo.delete(fileId);	
		}
		return true;
	}
	
	public String downloadFileName(int fileId, boolean isDownload) {
		String fileName = "";
		if (isDownload)
			fileName = physicianFileRepo.findOne(fileId).getOriginalFileName();
		else
			fileName = physicianFileRepo.findOne(fileId).getStoredFielName();
		return fileName;
	}
	
	public String photoNameByPhysicianId(int fileId, String filepath) {
		String filename="";
		try {
			if (filepath != null && !"".equalsIgnoreCase(filepath)) {
				PhysicianFileUpload rec = physicianFileRepo.findByPhysicianIdAndFileType(fileId, "Photo");
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
	
	
	public Page<PhysicianFileUpload> getUploadedDocuments(int physicianId, Pageable page) {
		return physicianFileRepo.findByPhysicianIdAndUploadFiles(physicianId, "Doc", page);
	}
	
	public String logoNameByPhysicianId(int fileId, String filepath) {
		String filename="";
		try {
			if (filepath != null && !"".equalsIgnoreCase(filepath)) {
				PhysicianFileUpload rec = physicianFileRepo.findByPhysicianIdAndFileType(fileId, "Logo");
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
	public boolean logoFileSave(int physicianId, CommonsMultipartFile file, String savedLogoFile, LoginForm loggedInUser) {
		boolean isSaved = false;
		try {
			PhysicianFileUpload fileRec = null;
			fileRec = physicianFileRepo.findByPhysicianIdAndFileType(physicianId, "Logo");
			if (fileRec == null) {
				fileRec = new PhysicianFileUpload();
			}
			String oname=file.getOriginalFilename();
//			String extension = oname.substring(oname.lastIndexOf('.') + 1, oname.length());
			
			fileRec.setFileDescription("");
			fileRec.setFileType("Logo");
			fileRec.setOriginalFileName(oname);
			fileRec.setPhysicianId(physicianId);
			fileRec.setStoredFielName(savedLogoFile);
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

	public String getPhysicianLogo(int physicianId) {
		String fileName="";
		PhysicianFileUpload fileRec = null;
		fileRec = physicianFileRepo.findByPhysicianIdAndFileType(physicianId, "Logo");
		if (fileRec != null) {
			fileName = fileRec.getStoredFielName();
		}
		return fileName;
	}

}
