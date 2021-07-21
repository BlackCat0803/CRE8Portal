package com.pharma.core.pharmaservices.patientservices;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.pharma.core.formBean.DocumentFileList;
import com.pharma.core.formBean.LoginForm;
import com.pharma.core.formBean.patient.PatientAccountForm;
import com.pharma.core.model.clinic.Clinic;
import com.pharma.core.model.patientaccount.PatientAccount;
import com.pharma.core.util.PharmacyUtil;
@Service
public interface PatientAccountService {
	
	PatientAccount save(PatientAccountForm patientAcct, CommonsMultipartFile patientPhoto, String rootFilePath, LoginForm loggedInUser, 
			HttpSession session, HttpServletRequest request, Environment env);

	List<PatientAccount> getPatientAccountEmailAndNotId(String email, int id);
	
	List<PatientAccount> getPatientAccountUserIdAndNotId(String userId, int id);
	
	List<PatientAccount> getPatientAccountSsnAndNotId(String ssn, int groupId, int id);
	
	List<PatientAccount> getPatientAccountByEmail(String email);
	
	List<PatientAccount> getPatientAccountByUserId(String userId);
	
	List<PatientAccount> getPatientAccountBySSN(String ssn, int groupId);
	

	PatientAccountForm getPatientData(int id, String filepath);
	
	List<PatientAccount>  getAllPatient();
	
	String getPatientAccountDataList(int id,  int draw, int start, int len, String searchTerm, int orderColumn, String orderDir, 
			String patientName, String phyname, String status, int groupId, HttpSession session);

	List<DocumentFileList> getUploadedDocuments(int id);

	boolean savePatientDocFiles(CommonsMultipartFile docFile, int patientId, String rootFilePath, LoginForm loggedInUser);
	
	boolean deletePatientDocFiles(int fileId);
	
	String getPatientDocFileName(int fileId);
	
	String getPatientDownloadDocFileName(int fileId);
	
	String getPatientPhotoFileName(int id, String filepath);

	String getPatientDocumentDataList(int draw, int start, int len, String searchTerm, int orderColumn, String orderDir, String patientId);
	
	List<PatientAccount>  getTop10Patients();

	PatientAccount getPatientData(int id);
	
	//Auto complete method
	List<PatientAccount> getAutoCompletePatientList(String term,String status,int id);

	//Auto complete method
	List<PatientAccount> getAllAutoCompletePatientList(String term);
	
	//Auto complete method
	List<PatientAccount> getAutoCompletePatientListByPhysicianId(String term,String status,int id, int physicianId);
	
	List<PatientAccount> getAutoCompletePrescriptionPatientListByPhysicianId(String term,int id, int physicianId);
	
	List<PatientAccount> getAutoCompletePatientListByGroupId(String term,String status,int groupId);
	
	List<PatientAccount> getAutoCompletePatientListByGroupId(String term,String status,int id, int groupId);
	
	List<PatientAccount> getAutoCompleteAllPatientListByPhysicianId(String term, int id, int physicianId);

	
	List<PatientAccount> getAutoCompletePatientListByGroupId(String term, int id, int groupId);
	
	void saveNewGroupAlreadyExistingPatient(PatientAccountForm form,int alreadyExistingPatientId, LoginForm loggedInUser);
	
	void saveNewPhysicianAlreadyExistingPatient(PatientAccountForm form,int alreadyExistingPatientId, LoginForm loggedInUser);
	
	
	void approvalPatientNotification(int patientId, LoginForm loggedInUser);
	
	void deniedPhysicianNotification(int patientId, LoginForm loggedInUser);
	
	void newPhysicianAddedByPatient(int patientId, LoginForm loggedInUser, String physicianNames);
	
	void criticalCommentsChangedByPatient(int patientId, LoginForm loggedInUser);
	
	void cancelCriticalCommentsNotificationByPatient(int patientId);

	boolean sendPatientAccountMail(PatientAccountForm form,LoginForm loggedInUser, HttpSession session,HttpServletRequest request, Environment env);
	
}
