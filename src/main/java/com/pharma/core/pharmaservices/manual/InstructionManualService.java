package com.pharma.core.pharmaservices.manual;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.pharma.core.formBean.LoginForm;
import com.pharma.core.formBean.manual.InstructionManualForm;
import com.pharma.core.model.manual.InstructionManual;

@Service("instructionManual")
public interface InstructionManualService {

	int getLatestDisplayOrder();
	
	boolean saveDocumentFiles(CommonsMultipartFile uploadDocFile, CommonsMultipartFile uploadImageFile, InstructionManualForm form, String rootFilePath, LoginForm loggedInUser);
	
	String getManualDocumentsData(int draw, int start, int length, String searchTerm, int orderColumn, String orderDir, String docName, String doctype);
	
	String getDocFileName(int fileId, boolean isImageFile);
	
	String getDownloadDocFileName(int fileId, boolean isImageFile);
	
	InstructionManualForm editInstructionManualDocFiles(int id);
	
	boolean deleteInstructionManualDocFiles(int id);
	
	List<InstructionManualForm> getAllDocumentsList(String filePath);
	
	String getDocumentFileNameById(String id, String filepath);

	List<InstructionManualForm> getLatestManual(Pageable latest, String filepath);
}
