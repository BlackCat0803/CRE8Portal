package com.pharma.core.pharmaservices.physicianservices;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.pharma.core.formBean.LoginForm;
import com.pharma.core.formBean.physician.PhysicianAssistantRegistration;
import com.pharma.core.model.physician.PhysicianAssistantAccount;

@Service
public interface PhysicianAssistantAccountService {
	
	//Physician Assistant Account setup from physician login
	PhysicianAssistantAccount saveAssistantAccount(PhysicianAssistantRegistration phyAssistantAcct, 
			CommonsMultipartFile photoFile, CommonsMultipartFile[] docFiles, String rootFilePath, 
			LoginForm loggedInUser, HttpSession session, HttpServletRequest request, Environment env);

	// Find email address is exist or not to avoid duplicate email addresses
	List<PhysicianAssistantAccount> getPhysicianAssistantByEmail(String email);
	
	PhysicianAssistantRegistration getPhysicianAssistantById(int id, String filepath);
	
	List<PhysicianAssistantAccount> getPhysicianAssistantByEmailAndNotId(String email, int id);
	
	//String getPhysicianNameById(int id);
	
	String getPhysicianAssistantDataList(int id,  int draw, int start, int len, String searchTerm, int orderColumn, String orderDir, String assistant, 
			String clinic, String status, int groupId);
	 
	String getAdminPhysicianAssistantDataList(int draw, int start, int len, String searchTerm, int orderColumn, String orderDir, String assistant, 
			String clinic, String phyName, String status, int groupId);
		
	PhysicianAssistantAccount getPhysicianAssistantAccountDetails(int id);
	
	//boolean setInactiveByPhysicianId(int id, LoginForm loginForm);

	String getAssistantPhotoFileName(int id, String filepath);
	
	//Auto complete method
	List<PhysicianAssistantAccount> getAutoCompletePhysicianAssistantList(String term,String status,int id);

	//Auto complete method
	List<PhysicianAssistantAccount> getAllAutoCompletePhysicianAssistantList(String term);
	
	
	//Auto complete method
	List<PhysicianAssistantAccount> getAutoCompleteGroupWisePhysicianAssistantList(String term,String status,int id,int groupid);
	
	
	//Auto complete method
	List<PhysicianAssistantAccount> getAutoCompletePhysicianWisePhysicianAssistantList(String term,String status,int id,int physicianid);
	
	
	//Auto complete method
	List<PhysicianAssistantAccount> getAllAutoCompleteGroupWisePhysicianAssistantList(String term,int groupid);
	
	//Auto complete method
	List<PhysicianAssistantAccount> getAllAutoCompletePhysicianWisePhysicianAssistantList(String term,int physicianid);

	boolean sendPhysicianAsstAccountMail(PhysicianAssistantRegistration form,LoginForm loggedInUser, HttpSession session,HttpServletRequest request, Environment env);
		

}
