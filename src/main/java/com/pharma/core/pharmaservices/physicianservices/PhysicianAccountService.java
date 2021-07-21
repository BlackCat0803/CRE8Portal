package com.pharma.core.pharmaservices.physicianservices;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.pharma.core.formBean.DocumentFileList;
import com.pharma.core.formBean.LoginForm;
import com.pharma.core.formBean.physician.PhysicianProfile;
import com.pharma.core.formBean.physician.PhysicianRegistration;
import com.pharma.core.model.States;
import com.pharma.core.model.patientaccount.PatientAccount;
import com.pharma.core.model.physician.PhysicianAccount;


@Service
public interface PhysicianAccountService {
	
	PhysicianAccount register(PhysicianRegistration phyAcct, HttpSession session, HttpServletRequest request);

	PhysicianAccount savePhysicianAccountProfile(PhysicianProfile phyAcct, CommonsMultipartFile photoFile1, CommonsMultipartFile[] docFiles, 
			String rootFilePath1, LoginForm loggedInUser, HttpSession session, HttpServletRequest request, CommonsMultipartFile photoFile2,String rootFilePath2);
	
	PhysicianProfile getPhysicianData(int id, String filepath1,String filepath2);

	String getPhysicianDataList(int draw, int start, int len, String searchTerm, int orderColumn, String orderDir, String physicianName, String dea, String status, int groupId);

	List<PhysicianAccount> getAllPhysician();
	
	List<PhysicianAccount> getApprovedPhysician();
	
	//Auto complete method
	List<String> getPhysicianNameList(String term);
	
	
	PhysicianAccount getPhysicianAccountDetails(int id);
	
	List<DocumentFileList> getUploadedDocuments(int id);
	
	boolean savePhysicianDocFiles(CommonsMultipartFile docFile, int physicianId, String rootFilePath, LoginForm loggedInUser,
			String docPurpose, String expiryDate, String fileName);
	
	boolean deletePhysicianDocFiles(int fileId);
	
	String getPhysicianDocFileName(int fileId);
	
	String getPhysicianDownloadDocFileName(int fileId);
	
	String getPhysicianPhotoFileName(int id, String filepath);
	
	String getPhysicianDocumentDataList(int draw, int start, int len, String searchTerm, int orderColumn, String orderDir, String physicianId);
	
	List<PhysicianAccount> getApprovedPhysicianAndId(int id);
	
	List<PhysicianAccount> getApprovedPhysicianByGroupIdAndId(int groupId, int phyid);
	
	List<PhysicianAccount>  getTop10Physicians();
	
	//Auto complete method
	List<PhysicianAccount> getAutoCompletePhysicianList(String term,String status,int id);
	
	List<PhysicianAccount> getAutoCompletePhysicianList(String term, int id);
	
	List<PhysicianAccount> getAutoCompleteGroupPhysicianList(String term, int id, int groupId);
	

	//Auto complete method
	List<PhysicianAccount> getAllAutoCompletePhysicianList(String term);
	
	//Auto complete method
	List<PhysicianAccount> getAutoCompletePhysicianList(String term,String status,int id,int groupid);

	//Auto complete method
	List<PhysicianAccount> getAllAutoCompletePhysicianList(String term,int groupid);
	
	List<PhysicianAccount> getAutoCompletePhysicianListByPhysician(String term,String status,int id,int physicianId);

	List<PhysicianAccount> getAllAutoCompletePhysicianListByPhysician(String term,int id,int physicianId);
	
	//Auto complete method
	List<PhysicianAccount> getAutoCompleteApprovedPhysicianList(String term,String status,int id,int groupid);
	
	List<PhysicianAccount> getAutoCompleteApprovedPhysicianListByPhysician(String term,String status,int id,int physicianId);
	
	List<PhysicianAccount> getAutoCompleteApprovedPhysicianList(String term,String status,int id);
	
	String getPhysicianLogoFileName(int id, String filepath);
	
	boolean saveNewPhysicianClinic(int physicianId,LoginForm loggedInUser,String physicianProfile,int groupId);
	
	boolean checkPhysicianClinicAlreadyExists(int physicianId,LoginForm loggedInUser,String physicianProfile,int groupId);
	
	String generatePdfFilePath(int physicianId);
	
	String getPhysicianPdfGenerated(int physicianId, String outputFileName, HttpSession session, String base64ImgString);

	void approvalPhysicianNotification(int physicianId, LoginForm loggedInUser);
	
	void deniedPhysicianNotification(int physicianId, LoginForm loggedInUser);
	
	void newClinicAddedByPhysician(int physicianId, LoginForm loggedInUser, int clinicId);
	
	void criticalCommentsChangedByPhysician(int physicianId, LoginForm loggedInUser);

	boolean sendPhysicianAccountMail(PhysicianProfile form,LoginForm loggedInUser, HttpSession session,HttpServletRequest request, Environment env);
	
}
