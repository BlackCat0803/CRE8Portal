package com.pharma.core.pharmaservices.prescription;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.pharma.core.formBean.LoginForm;
import com.pharma.core.formBean.prescription.PrescriptionForm;
import com.pharma.core.model.patientaccount.PatientAccount;
import com.pharma.core.model.physician.PhysicianAccount;
import com.pharma.core.model.prescription.PrescriptionMaster;

@Service
public interface PrescriptionService {

	String getPatientById(int id,int physicianId);
	
	String getPhysicianById(int physicianId, int prescriptionId);
	
	PrescriptionForm getPhysicianDataById(int id, int prescriptionId, PrescriptionForm form);
	
	PrescriptionMaster savePrescription(PrescriptionForm form, String rootFilePath, HttpSession session, boolean isPdfGenerate, LoginForm loggedInUser);
	
	PrescriptionForm getPrescriptionData(int id, HttpSession session);
	
	String getPrescriptionDataList(int draw, int start, int len, String searchTerm, int orderColumn, String orderDir, String physicianName, 
			String patientName, String presriptionDate, String presriptionToDate, String userId, String userType, int groupId,
			String prescriptionNo,String prescriptionstatus, String rxNo,String rxPrescriptionNo, HttpServletRequest request, HttpServletResponse response, HttpSession session);

	boolean pdfFtpTransfer(String serverName, String userName, String password, String targetFolder, String sourceFile, String targetFileName,int prescriptionId,int serverPort);
	
	boolean updatePatientData(int prescriptionId, PatientAccount acc, LoginForm loggedInUser);
	
	String getPatientAddress(int patientId, int addressOpt, boolean isBilling);
	
	String getPhysicianAddress(int physicianId, int addressOpt, boolean isBilling);

	String getClinicAddress(int physicianId,int clinicId, boolean isBilling);
	
	String getTemporaryAddress();
	
	boolean updatePhysicianData(int prescriptionId, PhysicianAccount acc, LoginForm loggedInUser);
	
	String pineerItemControlSubstanceCheck(String itemId);
	
	String savePhysicianPrescriptionSignature(String base64ImgString,int prescriptionid,int physicianid ,HttpSession session ,PrescriptionForm form);
	
	boolean getClass2ControlSubtance(int prescriptionId);
	
	boolean getFileFtpAllowed(int prescriptionId);
	
	boolean saveSubtanceDocFiles(CommonsMultipartFile docFile, int prescriptionid, String rootFilePath, LoginForm loggedInUser,
			String scan, String fax, String isHardCopy, String description);
	
	String[] duplicateRXAlreadyExists(String rxItemFormId,int patientFormId,int writtenByFormId);

	boolean prescriptionPdfFTPUpload(String prescriptionId, HttpSession session);

	String pineerSubstancePrescriptionItemheck(String itemId);
}
