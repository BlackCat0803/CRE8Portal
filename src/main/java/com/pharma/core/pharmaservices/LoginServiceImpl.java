package com.pharma.core.pharmaservices;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pharma.core.model.admin.AdminAccount;
import com.pharma.core.model.patientaccount.PatientAccount;
import com.pharma.core.model.pharmacygroup.GroupDirector;
import com.pharma.core.model.physician.PhysicianAccount;
import com.pharma.core.model.physician.PhysicianAssistantAccount;
import com.pharma.core.repository.admin.AdminAccountRepository;
import com.pharma.core.repository.patient.PatientAccountRespository;
import com.pharma.core.repository.pharmacygroup.GroupDirectorAccountRespository;
import com.pharma.core.repository.physician.PhysicianAccountRespository;
import com.pharma.core.repository.physician.PhysicianAssistantAccountRespository;
import com.pharma.core.util.PharmacyUtil;
/**
 * This class is an implementation for the application Log In / Log out activities
 *
 */
@Service("loginService")
public class LoginServiceImpl implements LoginService {

	@Autowired
	PhysicianAccountRespository physicianRepo;
	
	@Autowired
	PhysicianAssistantAccountRespository assistantRepo;
	
	@Autowired
	AdminAccountRepository adminRepo;
	
	@Autowired
	PatientAccountRespository patientRepo;
	
	@Autowired
	GroupDirectorAccountRespository groupDirRepo;
	

	/**
	 * If Login on successful, reset loginattempts count,lockedup datetime
	 */
	public void resetLoginAttempts(int userid,String userType) {
		
		try {
			if (userType.equalsIgnoreCase(PharmacyUtil.userPhysician)) {
				PhysicianAccount entityObj =physicianRepo.findOne(userid);
				entityObj.setLoginAttempts(0);
				entityObj.setLoginLockedDate(null);
				entityObj.setForgotPasswordAttempts(0);
				entityObj.setForgotPwdLockedDate(null);
				physicianRepo.save(entityObj);
			}else if (userType.equalsIgnoreCase(PharmacyUtil.userPhysicianAssistant)) {
				PhysicianAssistantAccount entityObj =assistantRepo.findOne(userid);
				entityObj.setLoginAttempts(0);
				entityObj.setLoginLockedDate(null);
				entityObj.setForgotPasswordAttempts(0);
				entityObj.setForgotPwdLockedDate(null);
				assistantRepo.save(entityObj);
			}else if (userType.equalsIgnoreCase(PharmacyUtil.userSuperAdmin) || userType.equalsIgnoreCase(PharmacyUtil.userAdmin)) {
				AdminAccount entityObj =adminRepo.findOne(userid);
				entityObj.setLoginAttempts(0);
				entityObj.setLoginLockedDate(null);
				entityObj.setForgotPasswordAttempts(0);
				entityObj.setForgotPwdLockedDate(null);
				adminRepo.save(entityObj);
			}else if (userType.equalsIgnoreCase(PharmacyUtil.userPatient)) {
				PatientAccount entityObj =patientRepo.findOne(userid);
				entityObj.setLoginAttempts(0);
				entityObj.setLoginLockedDate(null);
				entityObj.setForgotPasswordAttempts(0);
				entityObj.setForgotPwdLockedDate(null);
				patientRepo.save(entityObj);
			} else if (userType.equalsIgnoreCase(PharmacyUtil.userGroupDirector)) {
				GroupDirector entityObj = groupDirRepo.findOne(userid);
				entityObj.setLoginAttempts(0);
				entityObj.setLoginLockedDate(null);
				entityObj.setForgotPasswordAttempts(0);
				entityObj.setForgotPwdLockedDate(null);
				groupDirRepo.save(entityObj);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * set the lockeduptime for 5 minutes on the 6 attempt and reset the loginattempts count
	 */
	public void setLoginLockuptime(int userid,String userType,Date lockedupDate) {
		
		try {
			if (userType.equalsIgnoreCase(PharmacyUtil.userPhysician)) {
				PhysicianAccount entityObj =physicianRepo.findOne(userid);
				entityObj.setLoginAttempts(0);
				entityObj.setLoginLockedDate(new java.sql.Timestamp(lockedupDate.getTime()));
				physicianRepo.save(entityObj);
			}else if (userType.equalsIgnoreCase(PharmacyUtil.userPhysicianAssistant)) {
				PhysicianAssistantAccount entityObj =assistantRepo.findOne(userid);
				entityObj.setLoginAttempts(0);
				entityObj.setLoginLockedDate(new java.sql.Timestamp(lockedupDate.getTime()));
				assistantRepo.save(entityObj);
			}else if (userType.equalsIgnoreCase(PharmacyUtil.userSuperAdmin) || userType.equalsIgnoreCase(PharmacyUtil.userAdmin)) {
				AdminAccount entityObj =adminRepo.findOne(userid);
				entityObj.setLoginAttempts(0);
				entityObj.setLoginLockedDate(new java.sql.Timestamp(lockedupDate.getTime()));
				adminRepo.save(entityObj);
			}else if (userType.equalsIgnoreCase(PharmacyUtil.userPatient)) {
				PatientAccount entityObj =patientRepo.findOne(userid);
				entityObj.setLoginAttempts(0);
				entityObj.setLoginLockedDate(new java.sql.Timestamp(lockedupDate.getTime()));
				patientRepo.save(entityObj);
			} else if (userType.equalsIgnoreCase(PharmacyUtil.userGroupDirector)) {
				GroupDirector entityObj = groupDirRepo.findOne(userid);
				entityObj.setLoginAttempts(0);
				entityObj.setLoginLockedDate(new java.sql.Timestamp(lockedupDate.getTime()));
				groupDirRepo.save(entityObj);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * increment and update the loginattempts count on every unsuccessful password attempts 
	 */
	public void setLoginAttemptCount(int userid,String userType,int loginattempts) {
		
		try {
			if (userType.equalsIgnoreCase(PharmacyUtil.userPhysician)) {
				PhysicianAccount entityObj =physicianRepo.findOne(userid);
				entityObj.setLoginAttempts(loginattempts);
				physicianRepo.save(entityObj);
			}else if (userType.equalsIgnoreCase(PharmacyUtil.userPhysicianAssistant)) {
				PhysicianAssistantAccount entityObj =assistantRepo.findOne(userid);
				entityObj.setLoginAttempts(loginattempts);
				assistantRepo.save(entityObj);
			}else if (userType.equalsIgnoreCase(PharmacyUtil.userSuperAdmin) || userType.equalsIgnoreCase(PharmacyUtil.userAdmin)) {
				AdminAccount entityObj =adminRepo.findOne(userid);
				entityObj.setLoginAttempts(loginattempts);
				adminRepo.save(entityObj);
			}else if (userType.equalsIgnoreCase(PharmacyUtil.userPatient)) {
				PatientAccount entityObj =patientRepo.findOne(userid);
				entityObj.setLoginAttempts(loginattempts);
				patientRepo.save(entityObj);
			} else if (userType.equalsIgnoreCase(PharmacyUtil.userGroupDirector)) {
				GroupDirector entityObj =groupDirRepo.findOne(userid);
				entityObj.setLoginAttempts(loginattempts);
				groupDirRepo.save(entityObj);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Locks up the login account on every unsuccessful login attempts 
	 */
	public void setForgotPasswordLockuptime(int userid, String userType, Date lockedupDate) {
		
		try {
			if (userType.equalsIgnoreCase(PharmacyUtil.userPhysician)) {
				PhysicianAccount entityObj =physicianRepo.findOne(userid);
				entityObj.setForgotPasswordAttempts(0);
				entityObj.setForgotPwdLockedDate(new java.sql.Timestamp(lockedupDate.getTime()));
				physicianRepo.save(entityObj);
			}else if (userType.equalsIgnoreCase(PharmacyUtil.userPhysicianAssistant)) {
				PhysicianAssistantAccount entityObj =assistantRepo.findOne(userid);
				entityObj.setForgotPasswordAttempts(0);
				entityObj.setForgotPwdLockedDate(new java.sql.Timestamp(lockedupDate.getTime()));
				assistantRepo.save(entityObj);
			}else if (userType.equalsIgnoreCase(PharmacyUtil.userSuperAdmin) || userType.equalsIgnoreCase(PharmacyUtil.userAdmin)) {
				AdminAccount entityObj =adminRepo.findOne(userid);
				entityObj.setForgotPasswordAttempts(0);
				entityObj.setForgotPwdLockedDate(new java.sql.Timestamp(lockedupDate.getTime()));
				adminRepo.save(entityObj);
			}else if (userType.equalsIgnoreCase(PharmacyUtil.userPatient)) {
				PatientAccount entityObj =patientRepo.findOne(userid);
				entityObj.setForgotPasswordAttempts(0);
				entityObj.setForgotPwdLockedDate(new java.sql.Timestamp(lockedupDate.getTime()));
				patientRepo.save(entityObj);
			} else if (userType.equalsIgnoreCase(PharmacyUtil.userGroupDirector)) {
				GroupDirector entityObj =groupDirRepo.findOne(userid);
				entityObj.setForgotPasswordAttempts(0);
				entityObj.setForgotPwdLockedDate(new java.sql.Timestamp(lockedupDate.getTime()));
				groupDirRepo.save(entityObj);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * increment and update the forgotpasswordattempts count on every unsuccessful password attempts 
	 */
	public void setForgotPasswordAttemptCount(int userid, String userType, int loginattempts) {

		try {
			if (userType.equalsIgnoreCase(PharmacyUtil.userPhysician)) {
				PhysicianAccount entityObj =physicianRepo.findOne(userid);
				entityObj.setForgotPasswordAttempts(loginattempts);
				physicianRepo.save(entityObj);
			}else if (userType.equalsIgnoreCase(PharmacyUtil.userPhysicianAssistant)) {
				PhysicianAssistantAccount entityObj =assistantRepo.findOne(userid);
				entityObj.setForgotPasswordAttempts(loginattempts);
				assistantRepo.save(entityObj);
			}else if (userType.equalsIgnoreCase(PharmacyUtil.userSuperAdmin) || userType.equalsIgnoreCase(PharmacyUtil.userAdmin)) {
				AdminAccount entityObj =adminRepo.findOne(userid);
				entityObj.setForgotPasswordAttempts(loginattempts);
				adminRepo.save(entityObj);
			}else if (userType.equalsIgnoreCase(PharmacyUtil.userPatient)) {
				PatientAccount entityObj =patientRepo.findOne(userid);
				entityObj.setForgotPasswordAttempts(loginattempts);
				patientRepo.save(entityObj);
			} else if (userType.equalsIgnoreCase(PharmacyUtil.userGroupDirector)) {
				GroupDirector entityObj =groupDirRepo.findOne(userid);
				entityObj.setForgotPasswordAttempts(loginattempts);
				groupDirRepo.save(entityObj);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.pharma.core.pharmaservices.LoginService#findByPhysicianAssistantUsername(java.lang.String)
	 */
	public PhysicianAssistantAccount findByPhysicianAssistantUsername(String username) {
		List<PhysicianAssistantAccount> obj = assistantRepo.findByEmail(username);
		if (obj.size() > 0)
			return obj.get(0);
		else
			return null;
	}
	/*
	 * (non-Javadoc)
	 * @see com.pharma.core.pharmaservices.LoginService#findByAdminUsername(java.lang.String)
	 */
	public AdminAccount findByAdminUsername(String username) {
		List<AdminAccount> obj = adminRepo.findByEmail(username);
		if (obj.size() > 0)
			return obj.get(0);
		else
			return null;
	}
	/*
	 * (non-Javadoc)
	 * @see com.pharma.core.pharmaservices.LoginService#findByPatientUsername(java.lang.String)
	 */
	public PatientAccount findByPatientUsername(String username) {
		List<PatientAccount> objList = patientRepo.findByEmail(username);
		
		if (objList.size() > 0)
			return objList.get(0);
		else
			return null;
	
	}
	/*
	 * (non-Javadoc)
	 * @see com.pharma.core.pharmaservices.LoginService#findByPatientUserLoginId(java.lang.String)
	 */
	public PatientAccount findByPatientUserLoginId(String username) {
		List<PatientAccount> objList = patientRepo.findByUserLoginId(username);
		
		if (objList.size() > 0)
			return objList.get(0);
		else
			return null;
	
	}
	/*
	 * (non-Javadoc)
	 * @see com.pharma.core.pharmaservices.LoginService#findPhysicianByUsernamePassword(java.lang.String, java.lang.String)
	 */
	public PhysicianAccount findPhysicianByUsernamePassword(String username, String password) {
		return physicianRepo.findByUsernameAndPassword(username, password);
	}
	/*
	 * (non-Javadoc)
	 * @see com.pharma.core.pharmaservices.LoginService#findPhysicianByUsername(java.lang.String)
	 */
	public List<PhysicianAccount> findPhysicianByUsername(String username) {
		return physicianRepo.findByEmail(username);
	}
	/*
	 * (non-Javadoc)
	 * @see com.pharma.core.pharmaservices.LoginService#findAssistantByUsernamePassword(java.lang.String, java.lang.String)
	 */
	public PhysicianAssistantAccount findAssistantByUsernamePassword(String email, String password) {
		return assistantRepo.findByEmailAndPassword(email, password);
	}
	/*
	 * (non-Javadoc)
	 * @see com.pharma.core.pharmaservices.LoginService#findGroupDirectorByUsernamePassword(java.lang.String, java.lang.String)
	 */
	public GroupDirector findGroupDirectorByUsernamePassword(String username,String password) {
		return groupDirRepo.findByEmailAndPassword(username, password);
	}
	/*
	 * (non-Javadoc)
	 * @see com.pharma.core.pharmaservices.LoginService#findGroupDirectorByUsername(java.lang.String)
	 */
	public List<GroupDirector> findGroupDirectorByUsername(String username) {
		return groupDirRepo.findByEmail(username);
	}
	/*
	 * (non-Javadoc)
	 * @see com.pharma.core.pharmaservices.LoginService#getPhysicianById(int)
	 */
	public PhysicianAccount getPhysicianById(int id) {
		return physicianRepo.findOne(id);
	}
	
}
