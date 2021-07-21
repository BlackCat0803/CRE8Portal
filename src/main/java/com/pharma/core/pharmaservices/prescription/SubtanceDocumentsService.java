package com.pharma.core.pharmaservices.prescription;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.pharma.core.formBean.LoginForm;
import com.pharma.core.model.prescription.SubtanceItemDocuments;

@Service
public interface SubtanceDocumentsService {
	
	boolean documentFileSave(int prescriptionId, CommonsMultipartFile file, String savedPhotoFile, LoginForm loggedInUser,
			String scan, String fax, String isHardCopy, String description);
	
	String getSubtanceDocumentDataList(int draw, int start, int length, int orderColumn, String orderDir, String searchTerm, String prescriptionId);
	
	boolean deletePrescriptionDocFiles(int fileId);
	
	SubtanceItemDocuments getSubtanceFile(int fileId);
	
}
