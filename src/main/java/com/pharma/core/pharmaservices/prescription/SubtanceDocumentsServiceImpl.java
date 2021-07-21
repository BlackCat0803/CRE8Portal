package com.pharma.core.pharmaservices.prescription;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pharma.core.formBean.LoginForm;
import com.pharma.core.formBean.prescription.SubtanceItemDocForm;
import com.pharma.core.formBean.prescription.SubtanceItemsDocJSonObj;
import com.pharma.core.model.physician.PhysicianFileUpload;
import com.pharma.core.model.prescription.SubtanceItemDocuments;
import com.pharma.core.repository.admin.AdminAccountRepository;
import com.pharma.core.repository.prescription.SubtanceItemsDocumentsRepository;
/**
 * This is an implementation class that loads and stores the Controlled Substance document data
 *
 */
@Service("subtanceDocService")
public class SubtanceDocumentsServiceImpl implements SubtanceDocumentsService {

	@Autowired
	SubtanceItemsDocumentsRepository subtanceRepo;
	
	@Autowired
	AdminAccountRepository adminRepo;
	
	public boolean documentFileSave(int prescriptionId, CommonsMultipartFile file, String savedPhotoFile, LoginForm loggedInUser,
			String scan, String fax, String isHardCopy, String description) {
		boolean isSaved = false;
		try {
			SubtanceItemDocuments fileRec = null;
			fileRec = new SubtanceItemDocuments();
			
			String oname=file.getOriginalFilename();	
			
			fileRec.setPrescriptionId(prescriptionId);
			fileRec.setOriginalFileName(oname);
			fileRec.setStoredFielName(savedPhotoFile);
			fileRec.setFileType("Doc");
			fileRec.setFileDescription(description);
			
			//fileRec.setFax(fax);
			fileRec.setIsHardCopy(isHardCopy);
			//fileRec.setScan(scan);

			fileRec.setUploadedBy(loggedInUser.getUserid());
			fileRec.setUploadedDate(new java.sql.Date(new Date().getTime()));
			fileRec.setUserType(loggedInUser.getType());
			
			subtanceRepo.save(fileRec);
			isSaved=true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isSaved;
	}


	public String getSubtanceDocumentDataList(int draw, int start, int length, int orderColumn, String orderDir, String searchTerm, String prescriptionId) {
		
		int total = 0;
		int pageNumber = 0;
		try {
			Pageable page = null;
			Page<SubtanceItemDocuments> documentList = null;
			
			Direction dir = Direction.ASC;
			if (orderDir.equalsIgnoreCase("desc"))
				dir = Direction.DESC;

			List<SubtanceItemDocuments> phyDocList = subtanceRepo.findAllByPrescriptionId(Integer.parseInt(prescriptionId));
			total = phyDocList.size();
			pageNumber = start / length;
			
			switch (orderColumn) {
				case 1: {
					page = new PageRequest(pageNumber, length, dir, "permission");
					break;
				}
				case 2: {
					page = new PageRequest(pageNumber, length, dir, "isActive");
					break;
				}
				default: {
					page = new PageRequest(pageNumber, length, dir, "id");
					break;
				}
			}
			
			documentList = subtanceRepo.findByPrescriptionId(Integer.parseInt(prescriptionId), page);
			List<SubtanceItemDocForm> phyAssObjList = new ArrayList<SubtanceItemDocForm>();
			SimpleDateFormat sm = new SimpleDateFormat("MM/dd/yyyy");
			
			if (documentList != null && documentList.getSize() > 0 ) {
				for(SubtanceItemDocuments a: documentList) {
					SubtanceItemDocForm d = new SubtanceItemDocForm();
					d.setDT_RowId(a.getId()+"");
					
					d.setId(a.getId());
					d.setFileType(a.getFileType());
					d.setOriginalFileName(a.getOriginalFileName());
					d.setStoredFielName(a.getStoredFielName());
					d.setUploadedDate( sm.format(a.getUploadedDate()) );
					d.setUserType(a.getUserType());
					
				/*	if ("Y".equalsIgnoreCase(a.getScan()))
						d.setScan("Yes");
					else
						d.setScan("No");
					if ("Y".equalsIgnoreCase(a.getFax()))
						d.setFax("Yes");
					else
						d.setFax("No");*/
					if ("Y".equalsIgnoreCase(a.getIsHardCopy()))
						d.setIsHardCopy("Hard Copy");
					else
						d.setIsHardCopy("Others");
					
					d.setVerifyByName("");
					d.setVerifyDate("");
					d.setVerifyUserType("");
					if (a.getVerifyBy() > 0) {
						d.setVerifyByName(adminRepo.findOne( a.getVerifyBy()).getName());
						d.setVerifyDate(sm.format(a.getVerifyDate()));
						d.setVerifyUserType(a.getVerifyUserType());
					}
					phyAssObjList.add(d);
				}
			}
			
			SubtanceItemsDocJSonObj data = new SubtanceItemsDocJSonObj();
			data.setData(phyAssObjList);
			data.setDraw(draw);
			data.setRecordsFiltered(total);
			data.setRecordsFiltered(total);
			
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			String json2 = gson.toJson(data);	
			
			return json2;
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return null;
	}

	
	public boolean deletePrescriptionDocFiles(int fileId) {
		boolean isDeleted = false;
		if (fileId > 0) {
			SubtanceItemDocuments fileRec = subtanceRepo.findOne(fileId);
			if (fileRec != null) {
				File f = new File(fileRec.getStoredFielName());
				if (f.exists()) 
					f.delete();
				
				subtanceRepo.delete(fileId);	
			}
			isDeleted=true;
		}
		return isDeleted;
	}
	
	
	public SubtanceItemDocuments getSubtanceFile(int fileId) {
		return subtanceRepo.findOne(fileId);
	}
	
	
	
}
