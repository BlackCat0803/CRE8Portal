package com.pharma.core.pharmaservices;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.pharma.core.model.admin.AdminAccount;
import com.pharma.core.model.patientaccount.PatientAccount;
import com.pharma.core.model.pharmacygroup.GroupDirector;
import com.pharma.core.model.physician.PhysicianAccount;
import com.pharma.core.model.physician.PhysicianAssistantAccount;


@Service
public interface LoginService {
		
	PhysicianAccount findPhysicianByUsernamePassword(String username,String password);
	
	List<PhysicianAccount> findPhysicianByUsername(String username);

	PhysicianAssistantAccount findAssistantByUsernamePassword(String username,String password);
	
	PhysicianAccount getPhysicianById(int id);
	
	PhysicianAssistantAccount findByPhysicianAssistantUsername(String username);
	
	AdminAccount findByAdminUsername(String username);
	
	PatientAccount findByPatientUsername(String username);
	
	PatientAccount findByPatientUserLoginId(String username);
	
	void resetLoginAttempts(int userid,String userType);
	
	void setLoginLockuptime(int userid,String userType,Date lockedupDate);
	
	void setLoginAttemptCount(int userid,String userType,int loginattempts);
	
	void setForgotPasswordLockuptime(int userid,String userType,Date lockedupDate);
	
	void setForgotPasswordAttemptCount(int userid,String userType,int loginattempts);
	
	GroupDirector findGroupDirectorByUsernamePassword(String username,String password);
	
	List<GroupDirector> findGroupDirectorByUsername(String username);
	
	
}
