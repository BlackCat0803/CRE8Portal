package com.pharma.core.pharmaservices;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.pharma.core.formBean.LoginForm;
import com.pharma.core.formBean.ResetPasswordForm;
import com.pharma.core.model.admin.AdminAccount;
import com.pharma.core.model.admin.AdminOldPassword;
import com.pharma.core.model.admin.AdminPermissions;
import com.pharma.core.model.patientaccount.PatientAccount;
import com.pharma.core.model.patientaccount.PatientOldPassword;
import com.pharma.core.model.pharmacygroup.GroupDirector;
import com.pharma.core.model.pharmacygroup.GroupDirectorOldPassword;
import com.pharma.core.model.physician.PhysicianAccount;
import com.pharma.core.model.physician.PhysicianAssistantAccount;
import com.pharma.core.model.physician.PhysicianAssistantOldPassword;
import com.pharma.core.model.physician.PhysicianOldPassword;
import com.pharma.core.pharmaservices.adminservices.AdminAccountService;
import com.pharma.core.pharmaservices.patientservices.PatientAccountService;
import com.pharma.core.pharmaservices.pharmacygroupservices.GroupDirectorService;
import com.pharma.core.pharmaservices.physicianservices.PhysicianAccountService;
import com.pharma.core.pharmaservices.physicianservices.PhysicianAssistantAccountService;
import com.pharma.core.repository.admin.AdminAccountRepository;
import com.pharma.core.repository.admin.AdminOldPasswordRespository;
import com.pharma.core.repository.admin.AdminPermissionsRepository;
import com.pharma.core.repository.patient.PatientAccountRespository;
import com.pharma.core.repository.patient.PatientOldPasswordRespository;
import com.pharma.core.repository.pharmacygroup.GroupDirectorAccountRespository;
import com.pharma.core.repository.pharmacygroup.GroupDirectorOldPasswordRespository;
import com.pharma.core.repository.pharmacygroup.GroupMasterRespository;
import com.pharma.core.repository.physician.PhysicianAccountRespository;
import com.pharma.core.repository.physician.PhysicianAssistantAccountRespository;
import com.pharma.core.repository.physician.PhysicianAssistantOldPasswordRespository;
import com.pharma.core.repository.physician.PhysicianOldPasswordRespository;
import com.pharma.core.util.PharmacyUtil;
/**
 * This is an implementation class that resets and stores the new password of admin, patient ,physician,,physician assistant and group director
 *
 */
@Service("resetPasswordService")
@PropertySource("classpath:physicianAccount.properties")
public class ResetPasswordServiceImpl implements ResetPasswordService {

	@Autowired
	PhysicianAccountRespository phyRepo;
	
	@Autowired
	PhysicianOldPasswordRespository phyOldRepo;
	
	@Autowired
	PhysicianAssistantAccountRespository phyAsstRepo;
	
	@Autowired
	PhysicianAssistantOldPasswordRespository phyAsstOldRepo;
	
	@Autowired
	AdminAccountRepository adminRepo;
	
	@Autowired
	AdminOldPasswordRespository adminOldRepo;
	
	@Autowired
	PatientAccountRespository patRepo;
	
	@Autowired
	PatientOldPasswordRespository patOldRepo;
	
	@Autowired
	PhysicianAssistantAccountService phyAssistService;
	
	@Autowired
	AdminAccountService adminService;
	
	@Autowired
	PhysicianAccountService phyService;
	
	@Autowired
	PatientAccountService patientService;
	
	@Autowired
	GroupDirectorAccountRespository groupDirRepo;
	
	@Autowired
	GroupDirectorOldPasswordRespository groupDirOldRepo;
	
	@Autowired
	GroupDirectorService grpDirectorService;
	
	@Autowired
	GroupMasterRespository groupMasterRepo;
	
	@Autowired
	private Environment env;
	
	@Autowired
	AdminPermissionsRepository adminPermissionRepo;
	
	public LoginForm saveResetFormDetails(ResetPasswordForm resetPasswordForm) {
		LoginForm loginForm=new LoginForm();
		try {
			String userType=resetPasswordForm.getUserType();
			loginForm.setPhysicianGroupName("");
			
			if (userType.equalsIgnoreCase(PharmacyUtil.userPhysician)) {
				
				//Update the details of the newpassword,passwordreset date,security question and answers
				PhysicianAccount entityObj =phyRepo.findOne(resetPasswordForm.getUserId());
				entityObj.setPassword(resetPasswordForm.getNewPassword());
				entityObj.setPasswordResetDate(new java.sql.Timestamp(new Date().getTime()));
				entityObj.setSecurityQuestionNumber(resetPasswordForm.getSecurityquestion());
				entityObj.setSecurityAnswer(resetPasswordForm.getSecurityanswer());
				entityObj.setSecurityQuestionNumber2(resetPasswordForm.getSecurityquestion2());
				entityObj.setSecurityAnswer2(resetPasswordForm.getSecurityanswer2());
				entityObj.setLoginAttempts(0);
				entityObj.setLoginLockedDate(null);
				entityObj.setForgotPasswordAttempts(0);
				entityObj.setForgotPwdLockedDate(null);
				phyRepo.save(entityObj);
				
				//FIFO deletion-You may not reuse one of your past 6 passwords.Already 6 pwds are there, delete the first one and insert the new one
				List<PhysicianOldPassword> phyList=  phyOldRepo.findByPhysicianIdOrderByRowIdAsc(resetPasswordForm.getUserId());
				if(phyList!=null && phyList.size()>0 && phyList.size()>=6){
					int rowId=phyList.get(0).getRowId();
					phyOldRepo.delete(rowId);
				}
				
				PhysicianOldPassword phyOldPwd=new PhysicianOldPassword();
				phyOldPwd.setPhysicianId(resetPasswordForm.getUserId());
				phyOldPwd.setPassword(resetPasswordForm.getOldPassword());
				phyOldPwd.setDateMoved(PharmacyUtil.getCurrentDateAndTime());;
				phyOldRepo.save(phyOldPwd);
				
				loginForm.setDisplayName(entityObj.getPhysicianName());
		    	loginForm.setPhotoUrl(phyService.getPhysicianPhotoFileName(resetPasswordForm.getUserId(), env.getProperty("file.photo_path")));
		    	loginForm.setLogoUrl(phyService.getPhysicianLogoFileName(resetPasswordForm.getUserId(), env.getProperty("file.logo_path")));
		    	loginForm.setUserid(entityObj.getId());
		    	loginForm.setType(PharmacyUtil.userPhysician);
		    	loginForm.setPhysicianGroupName(entityObj.getPhysicianNameWithGroupName());
		    	
		    	/*if (PharmacyUtil.statusApproved.equalsIgnoreCase(entityObj.getStatus()))
		    		loginForm.setStatus(PharmacyUtil.statusApproved);
		    	else if (PharmacyUtil.statusNew.equalsIgnoreCase(entityObj.getStatus()))
		    		loginForm.setStatus(PharmacyUtil.statusNew);
		    	else if (PharmacyUtil.statusDenied.equalsIgnoreCase(entityObj.getStatus()))
		    		loginForm.setStatus(PharmacyUtil.statusDenied);
		    	else if (PharmacyUtil.statusNewModified.equalsIgnoreCase(entityObj.getStatus()))
		    		loginForm.setStatus(PharmacyUtil.statusNewModified);*/
		    	loginForm.setStatus(entityObj.getStatus());
		    	
		    	loginForm.setUserName(entityObj.getEmail());
				
				
			}else if (userType.equalsIgnoreCase(PharmacyUtil.userPhysicianAssistant)) {
				//Update the details of the newpassword,passwordreset date,security question and answers
				PhysicianAssistantAccount entityObj =phyAsstRepo.findOne(resetPasswordForm.getUserId());
				entityObj.setPassword(resetPasswordForm.getNewPassword());
				entityObj.setPasswordResetDate(new java.sql.Timestamp(new Date().getTime()));
				entityObj.setSecurityQuestionNumber(resetPasswordForm.getSecurityquestion());
				entityObj.setSecurityAnswer(resetPasswordForm.getSecurityanswer());
				entityObj.setSecurityQuestionNumber2(resetPasswordForm.getSecurityquestion2());
				entityObj.setSecurityAnswer2(resetPasswordForm.getSecurityanswer2());
				entityObj.setLoginAttempts(0);
				entityObj.setLoginLockedDate(null);
				entityObj.setForgotPasswordAttempts(0);
				entityObj.setForgotPwdLockedDate(null);
				phyAsstRepo.save(entityObj);
				
				//FIFO deletion-You may not reuse one of your past 6 passwords.Already 6 pwds are there, delete the first one and insert the new one
				List<PhysicianAssistantOldPassword> phyList=  phyAsstOldRepo.findByPhysicianAssistantIdOrderByRowIdAsc(resetPasswordForm.getUserId());
				if(phyList!=null && phyList.size()>0 && phyList.size()>=6){
					int rowId=phyList.get(0).getRowId();
					phyAsstOldRepo.delete(rowId);
				}
				
				PhysicianAssistantOldPassword phyOldPwd=new PhysicianAssistantOldPassword();
				phyOldPwd.setPhysicianAssistantId(resetPasswordForm.getUserId());
				phyOldPwd.setPassword(resetPasswordForm.getOldPassword());
				phyOldPwd.setDateMoved(PharmacyUtil.getCurrentDateAndTime());;
				phyAsstOldRepo.save(phyOldPwd);
				
				
				loginForm.setDisplayName(entityObj.getAssistantName());
		    	loginForm.setPhotoUrl(phyAssistService.getAssistantPhotoFileName(resetPasswordForm.getUserId(), env.getProperty("file.photo_path")));
		    	loginForm.setUserid(entityObj.getId());
		    	loginForm.setType(PharmacyUtil.userPhysicianAssistant);
		    	
		    	if (PharmacyUtil.statusActive.equalsIgnoreCase(entityObj.getStatus()))
		    		loginForm.setStatus(PharmacyUtil.statusActive);
		    	else
		    		loginForm.setStatus(PharmacyUtil.statusInactive);
		    	
		    	loginForm.setUserName(entityObj.getEmail());
				
			}else if (userType.equalsIgnoreCase(PharmacyUtil.userAdministrator) || userType.equalsIgnoreCase(PharmacyUtil.userSuperAdmin) || userType.equalsIgnoreCase(PharmacyUtil.userAdmin) ) {

				//Update the details of the newpassword,passwordreset date,security question and answers
				AdminAccount entityObj =adminRepo.findOne(resetPasswordForm.getUserId());
				entityObj.setPassword(resetPasswordForm.getNewPassword());
				entityObj.setPasswordResetDate(new java.sql.Timestamp(new Date().getTime()));
				entityObj.setSecurityQuestionNumber(resetPasswordForm.getSecurityquestion());
				entityObj.setSecurityAnswer(resetPasswordForm.getSecurityanswer());
				entityObj.setSecurityQuestionNumber2(resetPasswordForm.getSecurityquestion2());
				entityObj.setSecurityAnswer2(resetPasswordForm.getSecurityanswer2());
				entityObj.setLoginAttempts(0);
				entityObj.setLoginLockedDate(null);
				entityObj.setForgotPasswordAttempts(0);
				entityObj.setForgotPwdLockedDate(null);
				adminRepo.save(entityObj);
				
				//FIFO deletion-You may not reuse one of your past 6 passwords.Already 6 pwds are there, delete the first one and insert the new one
				List<AdminOldPassword> adminList=  adminOldRepo.findByAdminIdOrderByRowIdAsc(resetPasswordForm.getUserId());
				if(adminList!=null && adminList.size()>0 && adminList.size()>=6){
					int rowId=adminList.get(0).getRowId();
					adminOldRepo.delete(rowId);
				}
				
				AdminOldPassword adminOldPwd=new AdminOldPassword();
				adminOldPwd.setAdminId(resetPasswordForm.getUserId());
				adminOldPwd.setPassword(resetPasswordForm.getOldPassword());
				adminOldPwd.setDateMoved(PharmacyUtil.getCurrentDateAndTime());;
				adminOldRepo.save(adminOldPwd);
				
				
				loginForm.setDisplayName(entityObj.getName());
		    	loginForm.setPhotoUrl(adminService.getAdminPhotoFileName(resetPasswordForm.getUserId(), env.getProperty("file.photo_path")));
		    	loginForm.setUserid(entityObj.getId());
		    	loginForm.setType(entityObj.getType());
		    	if (PharmacyUtil.statusActive.equalsIgnoreCase(entityObj.getStatus()))
		    		loginForm.setStatus(PharmacyUtil.statusActive);
		    	else
		    		loginForm.setStatus(PharmacyUtil.statusInactive);
		    	loginForm.setUserName(entityObj.getEmail());
		    	
		    	//Load Admin Persmission List
		    	AdminPermissions adminPerm = null;
				adminPerm = adminPermissionRepo.findById(entityObj.getId());
				if (adminPerm != null) {
				
					loginForm.setAdminAccCreationPermission(adminPerm.getAdminAccCreation());
					loginForm.setGroupCreationPermission(adminPerm.getGroupCreation());
					loginForm.setGroupDirectorCreationPermission(adminPerm.getGroupDirectorCreation());
					loginForm.setClinicCreationPermission(adminPerm.getClinicCreation());
					loginForm.setPhysicianCreationPermission(adminPerm.getPhysicianCreation());
					loginForm.setPhysicianApprovalPermission(adminPerm.getPhysicianApproval());
					loginForm.setAssistantCreationPermission(adminPerm.getAssistantCreation());
					loginForm.setPatientCreationPermission(adminPerm.getPatientCreation());
					loginForm.setPatientApprovalPermission(adminPerm.getPatientApproval());
					loginForm.setPrescriptionCreationPermission(adminPerm.getPrescriptionCreation());
					loginForm.setControlSubstanceDocUploadPermission(adminPerm.getControlSubstanceDocUpload());
					loginForm.setRemoteFileUploadPermission(adminPerm.getRemoteFileUpload());
					
				}

		    	
			}else if (userType.equalsIgnoreCase(PharmacyUtil.userPatient)) {
				
				//Update the details of the newpassword,passwordreset date,security question and answers
				PatientAccount entityObj =patRepo.findOne(resetPasswordForm.getUserId());
				entityObj.setPassword(resetPasswordForm.getNewPassword());
				entityObj.setPasswordResetDate(new java.sql.Timestamp(new Date().getTime()));
				entityObj.setSecurityQuestionNumber(resetPasswordForm.getSecurityquestion());
				entityObj.setSecurityAnswer(resetPasswordForm.getSecurityanswer());
				entityObj.setSecurityQuestionNumber2(resetPasswordForm.getSecurityquestion2());
				entityObj.setSecurityAnswer2(resetPasswordForm.getSecurityanswer2());
				entityObj.setLoginAttempts(0);
				entityObj.setLoginLockedDate(null);
				entityObj.setForgotPasswordAttempts(0);
				entityObj.setForgotPwdLockedDate(null);
				patRepo.save(entityObj);
				
				//FIFO deletion-You may not reuse one of your past 6 passwords.Already 6 pwds are there, delete the first one and insert the new one
				List<PatientOldPassword> phyList=  patOldRepo.findByPatientIdOrderByRowIdAsc(resetPasswordForm.getUserId());
				if(phyList!=null && phyList.size()>0 && phyList.size()>=6){
					int rowId=phyList.get(0).getRowId();
					patOldRepo.delete(rowId);
				}

				PatientOldPassword patOldPwd=new PatientOldPassword();
				patOldPwd.setPatientId(resetPasswordForm.getUserId());
				patOldPwd.setPassword(resetPasswordForm.getOldPassword());
				patOldPwd.setDateMoved(PharmacyUtil.getCurrentDateAndTime());;
				patOldRepo.save(patOldPwd);

				loginForm.setDisplayName(entityObj.getPatientName());
		    	loginForm.setPhotoUrl( patientService.getPatientPhotoFileName(resetPasswordForm.getUserId(), env.getProperty("file.photo_path")));
		    	loginForm.setUserid(entityObj.getId());
		    	loginForm.setType(PharmacyUtil.userPatient);
		    	
		    	loginForm.setStatus(entityObj.getStatus());
		    	/*if (PharmacyUtil.statusApproved.equalsIgnoreCase(entityObj.getStatus()))
		    		loginForm.setStatus(PharmacyUtil.statusApproved);
		    	else if (PharmacyUtil.statusNew.equalsIgnoreCase(entityObj.getStatus()))
		    		loginForm.setStatus(PharmacyUtil.statusNew);
		    	else if (PharmacyUtil.statusDenied.equalsIgnoreCase(entityObj.getStatus()))
		    		loginForm.setStatus(PharmacyUtil.statusDenied);
		    	else if (PharmacyUtil.statusNewModified.equalsIgnoreCase(entityObj.getStatus()))
		    		loginForm.setStatus(PharmacyUtil.statusNewModified);*/

		    	loginForm.setUserName(entityObj.getEmail());
			} else  if (userType.equalsIgnoreCase(PharmacyUtil.userGroupDirector)) {
				//Update the details of the newpassword,passwordreset date,security question and answers
				GroupDirector entityObj =groupDirRepo.findOne(resetPasswordForm.getUserId());
				entityObj.setPassword(resetPasswordForm.getNewPassword());
				entityObj.setPasswordResetDate(new java.sql.Timestamp(new Date().getTime()));
				entityObj.setSecurityQuestionNumber(resetPasswordForm.getSecurityquestion());
				entityObj.setSecurityAnswer(resetPasswordForm.getSecurityanswer());
				entityObj.setSecurityQuestionNumber2(resetPasswordForm.getSecurityquestion2());
				entityObj.setSecurityAnswer2(resetPasswordForm.getSecurityanswer2());
				entityObj.setLoginAttempts(0);
				entityObj.setLoginLockedDate(null);
				entityObj.setForgotPasswordAttempts(0);
				entityObj.setForgotPwdLockedDate(null);
				groupDirRepo.save(entityObj);
				
				//FIFO deletion-You may not reuse one of your past 6 passwords.Already 6 pwds are there, delete the first one and insert the new one
				List<GroupDirectorOldPassword> phyList=  groupDirOldRepo.findByGroupDirectorIdOrderByRowIdAsc(resetPasswordForm.getUserId());
				if(phyList!=null && phyList.size()>0 && phyList.size()>=6){
					int rowId=phyList.get(0).getRowId();
					groupDirOldRepo.delete(rowId);
				}
				
				GroupDirectorOldPassword patOldPwd=new GroupDirectorOldPassword();
				patOldPwd.setGroupDirectorId(resetPasswordForm.getUserId());
				patOldPwd.setPassword(resetPasswordForm.getOldPassword());
				patOldPwd.setDateMoved(PharmacyUtil.getCurrentDateAndTime());;
				groupDirOldRepo.save(patOldPwd);
				
				
				loginForm.setDisplayName(entityObj.getGroupDirectorName());
				loginForm.setPhotoUrl(grpDirectorService.getGroupDirectorPhotoFileName(resetPasswordForm.getUserId(), env.getProperty("file.photo_path")));
		    	loginForm.setUserid(entityObj.getId());
		    	loginForm.setGroupid(entityObj.getGroupId());
		    	loginForm.setGroupName(  groupMasterRepo.findOne(entityObj.getGroupId()).getGroupName() );
		    	loginForm.setType(PharmacyUtil.userGroupDirector);
		    	
		    	if (PharmacyUtil.statusActive.equalsIgnoreCase(entityObj.getStatus()))
		    		loginForm.setStatus(PharmacyUtil.statusActive);
		    	else
		    		loginForm.setStatus(PharmacyUtil.statusInactive);
		   
		    	loginForm.setUserName(entityObj.getEmail());
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return loginForm;
	}
	
	
	public ResetPasswordForm saveNewPasswordDetails(ResetPasswordForm resetPasswordForm) {
		
		try {
			String userType=resetPasswordForm.getUserType();
			
			if (userType.equalsIgnoreCase(PharmacyUtil.userPhysician)) {
				//Update the details of the newpassword,passwordreset date,security question and answers
				PhysicianAccount entityObj =phyRepo.findOne(resetPasswordForm.getUserId());
				entityObj.setPassword(resetPasswordForm.getNewPassword());
				entityObj.setPasswordResetDate(new java.sql.Timestamp(new Date().getTime()));
				entityObj.setLoginAttempts(0);
				entityObj.setLoginLockedDate(null);
				entityObj.setForgotPasswordAttempts(0);
				entityObj.setForgotPwdLockedDate(null);
				phyRepo.save(entityObj);
				
				//FIFO deletion-You may not reuse one of your past 6 passwords.Already 6 pwds are there, delete the first one and insert the new one
				List<PhysicianOldPassword> phyList=  phyOldRepo.findByPhysicianIdOrderByRowIdAsc(resetPasswordForm.getUserId());
				if(phyList!=null && phyList.size()>0 && phyList.size()>=6){
					int rowId=phyList.get(0).getRowId();
					phyOldRepo.delete(rowId);
				}
				
				PhysicianOldPassword phyOldPwd=new PhysicianOldPassword();
				phyOldPwd.setPhysicianId(resetPasswordForm.getUserId());
				phyOldPwd.setPassword(resetPasswordForm.getOldPassword());
				phyOldPwd.setDateMoved(PharmacyUtil.getCurrentDateAndTime());;
				phyOldRepo.save(phyOldPwd);
				
				resetPasswordForm.setOldPassword(resetPasswordForm.getNewPassword());
				
			}else if (userType.equalsIgnoreCase(PharmacyUtil.userPhysicianAssistant)) {
				//Update the details of the newpassword,passwordreset date,security question and answers
				PhysicianAssistantAccount entityObj =phyAsstRepo.findOne(resetPasswordForm.getUserId());
				entityObj.setPassword(resetPasswordForm.getNewPassword());
				entityObj.setPasswordResetDate(new java.sql.Timestamp(new Date().getTime()));
				entityObj.setLoginAttempts(0);
				entityObj.setLoginLockedDate(null);
				entityObj.setForgotPasswordAttempts(0);
				entityObj.setForgotPwdLockedDate(null);
				phyAsstRepo.save(entityObj);
				
				//FIFO deletion-You may not reuse one of your past 6 passwords.Already 6 pwds are there, delete the first one and insert the new one
				List<PhysicianAssistantOldPassword> phyList=  phyAsstOldRepo.findByPhysicianAssistantIdOrderByRowIdAsc(resetPasswordForm.getUserId());
				if(phyList!=null && phyList.size()>0 && phyList.size()>=6){
					int rowId=phyList.get(0).getRowId();
					phyAsstOldRepo.delete(rowId);
				}
				
				PhysicianAssistantOldPassword phyOldPwd=new PhysicianAssistantOldPassword();
				phyOldPwd.setPhysicianAssistantId(resetPasswordForm.getUserId());
				phyOldPwd.setPassword(resetPasswordForm.getOldPassword());
				phyOldPwd.setDateMoved(PharmacyUtil.getCurrentDateAndTime());;
				phyAsstOldRepo.save(phyOldPwd);
				
				resetPasswordForm.setOldPassword(resetPasswordForm.getNewPassword());
				
				
			}else if (userType.equalsIgnoreCase(PharmacyUtil.userSuperAdmin) || userType.equalsIgnoreCase(PharmacyUtil.userAdmin) ) {

				//Update the details of the newpassword,passwordreset date,security question and answers
				AdminAccount entityObj =adminRepo.findOne(resetPasswordForm.getUserId());
				entityObj.setPassword(resetPasswordForm.getNewPassword());
				entityObj.setPasswordResetDate(new java.sql.Timestamp(new Date().getTime()));
				entityObj.setLoginAttempts(0);
				entityObj.setLoginLockedDate(null);
				entityObj.setForgotPasswordAttempts(0);
				entityObj.setForgotPwdLockedDate(null);
				adminRepo.save(entityObj);
				
				//FIFO deletion-You may not reuse one of your past 6 passwords.Already 6 pwds are there, delete the first one and insert the new one
				List<AdminOldPassword> adminList=  adminOldRepo.findByAdminIdOrderByRowIdAsc(resetPasswordForm.getUserId());
				if(adminList!=null && adminList.size()>0 && adminList.size()>=6){
					int rowId=adminList.get(0).getRowId();
					adminOldRepo.delete(rowId);
				}
				
				AdminOldPassword adminOldPwd=new AdminOldPassword();
				adminOldPwd.setAdminId(resetPasswordForm.getUserId());
				adminOldPwd.setPassword(resetPasswordForm.getOldPassword());
				adminOldPwd.setDateMoved(PharmacyUtil.getCurrentDateAndTime());;
				adminOldRepo.save(adminOldPwd);
				
				resetPasswordForm.setOldPassword(resetPasswordForm.getNewPassword());
				
			}else if (userType.equalsIgnoreCase(PharmacyUtil.userPatient)) {
				
				//Update the details of the newpassword,passwordreset date,security question and answers
				PatientAccount entityObj =patRepo.findOne(resetPasswordForm.getUserId());
				entityObj.setPassword(resetPasswordForm.getNewPassword());
				entityObj.setPasswordResetDate(new java.sql.Timestamp(new Date().getTime()));
				entityObj.setLoginAttempts(0);
				entityObj.setLoginLockedDate(null);
				entityObj.setForgotPasswordAttempts(0);
				entityObj.setForgotPwdLockedDate(null);
				patRepo.save(entityObj);
				
				//FIFO deletion-You may not reuse one of your past 6 passwords.Already 6 pwds are there, delete the first one and insert the new one
				List<PatientOldPassword> phyList=  patOldRepo.findByPatientIdOrderByRowIdAsc(resetPasswordForm.getUserId());
				if(phyList!=null && phyList.size()>0 && phyList.size()>=6){
					int rowId=phyList.get(0).getRowId();
					patOldRepo.delete(rowId);
				}
				
				PatientOldPassword patOldPwd=new PatientOldPassword();
				patOldPwd.setPatientId(resetPasswordForm.getUserId());
				patOldPwd.setPassword(resetPasswordForm.getOldPassword());
				patOldPwd.setDateMoved(PharmacyUtil.getCurrentDateAndTime());;
				patOldRepo.save(patOldPwd);
				
				resetPasswordForm.setOldPassword(resetPasswordForm.getNewPassword());
				
			} else if (userType.equalsIgnoreCase(PharmacyUtil.userGroupDirector)) {
				GroupDirector entityObj =groupDirRepo.findOne(resetPasswordForm.getUserId());
				entityObj.setPassword(resetPasswordForm.getNewPassword());
				entityObj.setPasswordResetDate(new java.sql.Timestamp(new Date().getTime()));
				entityObj.setLoginAttempts(0);
				entityObj.setLoginLockedDate(null);
				entityObj.setForgotPasswordAttempts(0);
				entityObj.setForgotPwdLockedDate(null);
				groupDirRepo.save(entityObj);
				
				//FIFO deletion-You may not reuse one of your past 6 passwords.Already 6 pwds are there, delete the first one and insert the new one
				List<GroupDirectorOldPassword> phyList= groupDirOldRepo.findByGroupDirectorIdOrderByRowIdAsc(resetPasswordForm.getUserId());
				if(phyList!=null && phyList.size()>0 && phyList.size()>=6){
					int rowId=phyList.get(0).getRowId();
					groupDirOldRepo.delete(rowId);
				}
				
				GroupDirectorOldPassword patOldPwd=new GroupDirectorOldPassword();
				patOldPwd.setGroupDirectorId(resetPasswordForm.getUserId());
				patOldPwd.setPassword(resetPasswordForm.getOldPassword());
				patOldPwd.setDateMoved(PharmacyUtil.getCurrentDateAndTime());;
				groupDirOldRepo.save(patOldPwd);
				
				resetPasswordForm.setOldPassword(resetPasswordForm.getNewPassword());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return resetPasswordForm;
	}
	
	
	
	public ResetPasswordForm saveSecurityQuestionDetails(ResetPasswordForm resetPasswordForm) {
		try {
			String userType=resetPasswordForm.getUserType();
			
			if (userType.equalsIgnoreCase(PharmacyUtil.userPhysician)) {
				//Update the details of the newpassword,passwordreset date,security question and answers
				PhysicianAccount entityObj =phyRepo.findOne(resetPasswordForm.getUserId());
				entityObj.setSecurityQuestionNumber(resetPasswordForm.getSecurityquestion());
				entityObj.setSecurityAnswer(resetPasswordForm.getSecurityanswer());
				entityObj.setSecurityQuestionNumber2(resetPasswordForm.getSecurityquestion2());
				entityObj.setSecurityAnswer2(resetPasswordForm.getSecurityanswer2());
				entityObj.setLoginAttempts(0);
				entityObj.setLoginLockedDate(null);
				entityObj.setForgotPasswordAttempts(0);
				entityObj.setForgotPwdLockedDate(null);
				phyRepo.save(entityObj);
				
			}else if (userType.equalsIgnoreCase(PharmacyUtil.userPhysicianAssistant)) {
				//Update the details of the newpassword,passwordreset date,security question and answers
				PhysicianAssistantAccount entityObj =phyAsstRepo.findOne(resetPasswordForm.getUserId());
				entityObj.setSecurityQuestionNumber(resetPasswordForm.getSecurityquestion());
				entityObj.setSecurityAnswer(resetPasswordForm.getSecurityanswer());
				entityObj.setSecurityQuestionNumber2(resetPasswordForm.getSecurityquestion2());
				entityObj.setSecurityAnswer2(resetPasswordForm.getSecurityanswer2());
				entityObj.setLoginAttempts(0);
				entityObj.setLoginLockedDate(null);
				entityObj.setForgotPasswordAttempts(0);
				entityObj.setForgotPwdLockedDate(null);
				phyAsstRepo.save(entityObj);
			}else if (userType.equalsIgnoreCase(PharmacyUtil.userSuperAdmin) || userType.equalsIgnoreCase(PharmacyUtil.userAdmin) ) {
				//Update the details of the newpassword,passwordreset date,security question and answers
				AdminAccount entityObj =adminRepo.findOne(resetPasswordForm.getUserId());
				entityObj.setSecurityQuestionNumber(resetPasswordForm.getSecurityquestion());
				entityObj.setSecurityAnswer(resetPasswordForm.getSecurityanswer());
				entityObj.setSecurityQuestionNumber2(resetPasswordForm.getSecurityquestion2());
				entityObj.setSecurityAnswer2(resetPasswordForm.getSecurityanswer2());
				entityObj.setLoginAttempts(0);
				entityObj.setLoginLockedDate(null);
				entityObj.setForgotPasswordAttempts(0);
				entityObj.setForgotPwdLockedDate(null);
				adminRepo.save(entityObj);
			}else if (userType.equalsIgnoreCase(PharmacyUtil.userPatient)) {
				//Update the details of the newpassword,passwordreset date,security question and answers
				PatientAccount entityObj =patRepo.findOne(resetPasswordForm.getUserId());
				entityObj.setSecurityQuestionNumber(resetPasswordForm.getSecurityquestion());
				entityObj.setSecurityAnswer(resetPasswordForm.getSecurityanswer());
				entityObj.setSecurityQuestionNumber2(resetPasswordForm.getSecurityquestion2());
				entityObj.setSecurityAnswer2(resetPasswordForm.getSecurityanswer2());
				entityObj.setLoginAttempts(0);
				entityObj.setLoginLockedDate(null);
				entityObj.setForgotPasswordAttempts(0);
				entityObj.setForgotPwdLockedDate(null);
				patRepo.save(entityObj);
			} else if (userType.equalsIgnoreCase(PharmacyUtil.userGroupDirector)) {
				//Update the details of the newpassword,passwordreset date,security question and answers
				GroupDirector entityObj =groupDirRepo.findOne(resetPasswordForm.getUserId());
				entityObj.setSecurityQuestionNumber(resetPasswordForm.getSecurityquestion());
				entityObj.setSecurityAnswer(resetPasswordForm.getSecurityanswer());
				entityObj.setSecurityQuestionNumber2(resetPasswordForm.getSecurityquestion2());
				entityObj.setSecurityAnswer2(resetPasswordForm.getSecurityanswer2());
				entityObj.setLoginAttempts(0);
				entityObj.setLoginLockedDate(null);
				entityObj.setForgotPasswordAttempts(0);
				entityObj.setForgotPwdLockedDate(null);
				groupDirRepo.save(entityObj);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return resetPasswordForm;
	}
	
}
