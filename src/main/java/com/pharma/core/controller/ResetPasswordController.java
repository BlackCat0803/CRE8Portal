package com.pharma.core.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.JsonObject;
import com.pharma.core.formBean.LoginForm;
import com.pharma.core.formBean.ResetPasswordForm;
import com.pharma.core.model.SecurityQuestion;
import com.pharma.core.model.admin.AdminAccount;
import com.pharma.core.model.admin.AdminOldPassword;
import com.pharma.core.model.patientaccount.PatientAccount;
import com.pharma.core.model.patientaccount.PatientOldPassword;
import com.pharma.core.model.pharmacygroup.GroupDirector;
import com.pharma.core.model.pharmacygroup.GroupDirectorOldPassword;
import com.pharma.core.model.physician.PhysicianAccount;
import com.pharma.core.model.physician.PhysicianAssistantAccount;
import com.pharma.core.model.physician.PhysicianAssistantOldPassword;
import com.pharma.core.model.physician.PhysicianOldPassword;
import com.pharma.core.pharmaservices.ResetPasswordService;
import com.pharma.core.pharmaservices.SecurityQuestionService;
import com.pharma.core.repository.admin.AdminAccountRepository;
import com.pharma.core.repository.admin.AdminOldPasswordRespository;
import com.pharma.core.repository.patient.PatientAccountRespository;
import com.pharma.core.repository.patient.PatientOldPasswordRespository;
import com.pharma.core.repository.pharmacygroup.GroupDirectorAccountRespository;
import com.pharma.core.repository.pharmacygroup.GroupDirectorOldPasswordRespository;
import com.pharma.core.repository.physician.PhysicianAccountRespository;
import com.pharma.core.repository.physician.PhysicianAssistantAccountRespository;
import com.pharma.core.repository.physician.PhysicianAssistantOldPasswordRespository;
import com.pharma.core.repository.physician.PhysicianOldPasswordRespository;
import com.pharma.core.util.HipaaPasswordValidator;
import com.pharma.core.util.PharmacyUtil;
/**
 * This class is a controller to reset the password details of admin,patient,physician,physician assistant and group director
 */
@Controller
@SessionAttributes("loginDetail")
@PropertySource("classpath:hipaa.properties")
@PropertySource("classpath:resetPassword.properties")
public class ResetPasswordController { 
	
	@Autowired
	private Environment env;
	
	@Autowired
	PhysicianOldPasswordRespository phyOldPwdRepo;
	
	@Autowired
	PhysicianAssistantOldPasswordRespository phyAsstOldPwdRepo;
	
	@Autowired
	AdminOldPasswordRespository adminOldPwdRepo;
	
	@Autowired
	PatientOldPasswordRespository patientOldPwdRepo;
	
	
	@Autowired
	PhysicianAccountRespository phyAcctRepo;

	@Autowired
	PhysicianAssistantAccountRespository phyAsstAcctRepo;
	
	@Autowired
	AdminAccountRepository adminRepo;
	
	@Autowired
	PatientAccountRespository patRepo;
	
	@Autowired
	GroupDirectorOldPasswordRespository groupDirOldPassRepo;
	
	@Autowired
	GroupDirectorAccountRespository groupDirRepo;
	
	@Autowired
	SecurityQuestionService securityQuestionService;
	
	@Autowired
	ResetPasswordService resetPasswordService;
	
	HipaaPasswordValidator hipaaValidator;

	/**
	 * This method loads the reset password form page
	 * @param resetPasswordForm
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/loadResetPasswordForm", method = RequestMethod.GET)
	public ModelAndView loadResetPasswordForm(@ModelAttribute("resetPasswordForm")  ResetPasswordForm resetPasswordForm, HttpSession session, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		try {
			
			List<SecurityQuestion> securityQuestionList = securityQuestionService.getAllSecurityQuestions();
			//System.out.println("message **********   "+resetPasswordForm.getUserId());
			//System.out.println("message **********   "+resetPasswordForm.getUserName());
			
			mv.addObject("securityQuestions", securityQuestionList);
			
			mv.addObject("resetPasswordForm", resetPasswordForm);
			mv.setViewName("resetPasswordForm");
		} catch(Exception e) {
			e.printStackTrace();
			mv.setViewName("resetPasswordForm");
		}
		return mv;
	}
	/**
	 * This method validates whether the user password already exists
	 * @param newPassword
	 * @param userId
	 * @param userType
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping( value = "/alreadyUserPasswordExist", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody String alreadyUserPasswordExist(@RequestParam("newPassword") String newPassword,
    		@RequestParam("userId") int userId,@RequestParam("userType") String userType,  HttpSession session, HttpServletRequest request) {
	
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("returnStatus", Boolean.FALSE); 
	
		try {
		
			if (userType.equalsIgnoreCase(PharmacyUtil.userPhysician)) {
			
				List<PhysicianOldPassword> phyAcct=phyOldPwdRepo.findByPasswordAndPhysicianId(newPassword,userId);
				
				PhysicianAccount acct=phyAcctRepo.findById(userId);
				String oldPassword=acct.getPassword();
				
				if(phyAcct!=null && phyAcct.size()>0 && phyAcct.get(0).getRowId()>0 || newPassword.equals(oldPassword))
				{
					
					jsonObject.addProperty("returnStatus", Boolean.TRUE); 
					if(newPassword.equals(oldPassword))
						jsonObject.addProperty("errMsg",env.getProperty("error.newPasswordNotSameasOldPassword"));
					else if(phyAcct!=null && phyAcct.size()>0 && phyAcct.get(0).getRowId()>0)
						jsonObject.addProperty("errMsg",env.getProperty("error.oldpasswordreused"));
				}
				
			}else if (userType.equalsIgnoreCase(PharmacyUtil.userPhysicianAssistant)) {
				
				List<PhysicianAssistantOldPassword> phyAsstAcct=phyAsstOldPwdRepo.findByPasswordAndPhysicianAssistantId(newPassword,userId);
				
				PhysicianAssistantAccount acct=phyAsstAcctRepo.findById(userId);
				String oldPassword=acct.getPassword();
				
				if(phyAsstAcct!=null && phyAsstAcct.size()>0 && phyAsstAcct.get(0).getRowId()>0  || newPassword.equals(oldPassword))
				{
					jsonObject.addProperty("returnStatus",  Boolean.TRUE); 
					if(newPassword.equals(oldPassword))
						jsonObject.addProperty("errMsg",env.getProperty("error.newPasswordNotSameasOldPassword"));
					else if(phyAsstAcct!=null && phyAsstAcct.size()>0 && phyAsstAcct.get(0).getRowId()>0)
						jsonObject.addProperty("errMsg",env.getProperty("error.oldpasswordreused"));
				}
				
			}else if (userType.equalsIgnoreCase(PharmacyUtil.userAdministrator) || userType.equalsIgnoreCase(PharmacyUtil.userSuperAdmin) || userType.equalsIgnoreCase(PharmacyUtil.userAdmin)) {
				
				List<AdminOldPassword> adminAcct=adminOldPwdRepo.findByPasswordAndAdminId(newPassword,userId);
				
				AdminAccount acct=adminRepo.findById(userId);
				String oldPassword=acct.getPassword();
				
				if(adminAcct!=null && adminAcct.size()>0 && adminAcct.get(0).getRowId()>0  || newPassword.equals(oldPassword))
				{
					jsonObject.addProperty("returnStatus",  Boolean.TRUE); 
					if(newPassword.equals(oldPassword))
						jsonObject.addProperty("errMsg",env.getProperty("error.newPasswordNotSameasOldPassword"));
					else if(adminAcct!=null && adminAcct.size()>0 && adminAcct.get(0).getRowId()>0)
						jsonObject.addProperty("errMsg",env.getProperty("error.oldpasswordreused"));
				}
				
			}else if (userType.equalsIgnoreCase(PharmacyUtil.userPatient)) {
				
				List<PatientOldPassword> patAcct=patientOldPwdRepo.findByPasswordAndPatientId(newPassword,userId);
				
				PatientAccount acct=patRepo.findById(userId);
				String oldPassword=acct.getPassword();
				
				if(patAcct!=null && patAcct.size()>0 && patAcct.get(0).getRowId()>0  || newPassword.equals(oldPassword))
				{
					jsonObject.addProperty("returnStatus",  Boolean.TRUE); 
					if(newPassword.equals(oldPassword))
						jsonObject.addProperty("errMsg",env.getProperty("error.newPasswordNotSameasOldPassword"));
					else if(patAcct!=null && patAcct.size()>0 && patAcct.get(0).getRowId()>0)
						jsonObject.addProperty("errMsg",env.getProperty("error.oldpasswordreused"));
				}
			} else if (userType.equalsIgnoreCase(PharmacyUtil.userGroupDirector)) {
				List<GroupDirectorOldPassword> groupDirAcct=groupDirOldPassRepo.findByPasswordAndGroupDirectorId(newPassword,userId);
				
				GroupDirector acct=groupDirRepo.findById(userId);
				String oldPassword=acct.getPassword();
				
				
				if(groupDirAcct!=null && groupDirAcct.size()>0 && groupDirAcct.get(0).getRowId()>0  || newPassword.equals(oldPassword))
				{
					jsonObject.addProperty("returnStatus",  Boolean.TRUE); 
					if(newPassword.equals(oldPassword))
						jsonObject.addProperty("errMsg",env.getProperty("error.newPasswordNotSameasOldPassword"));
					else if(groupDirAcct!=null && groupDirAcct.size()>0 && groupDirAcct.get(0).getRowId()>0)
						jsonObject.addProperty("errMsg",env.getProperty("error.oldpasswordreused"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			// returnStatus = Util.failure;
			jsonObject.addProperty("returnStatus", Boolean.FALSE); 
		}
		
		return jsonObject.toString();
	
	}
	/**
	 * This method resets and saves the user password data
	 * @param resetPasswordForm
	 * @param model
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/saveResetPasswordDetails", method = RequestMethod.POST)
	public ModelAndView saveResetPasswordDetails(@ModelAttribute("resetPasswordForm")  ResetPasswordForm resetPasswordForm, Model model,
			HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		
		ModelAndView mv = new ModelAndView();
		
		try {
			
			String userType="";
			userType=resetPasswordForm.getUserType();
			LoginForm loginForm = new LoginForm();
			String selectedUserType = "";
			
			if (userType.equalsIgnoreCase(PharmacyUtil.userPhysician)) {
				
				PhysicianAccount acct=phyAcctRepo.findById(resetPasswordForm.getUserId());
				String oldPassword=acct.getPassword();
				resetPasswordForm.setOldPassword(oldPassword);
				
				loginForm=resetPasswordService.saveResetFormDetails(resetPasswordForm);
				session = request.getSession();
				session.setAttribute("loginDetail", loginForm);
				mv.addObject("loginForm", loginForm);
				selectedUserType = PharmacyUtil.userPhysician; 
						
				PhysicianAccount pAcc = phyAcctRepo.findOne(  loginForm.getUserid() );
				if ("New".equalsIgnoreCase(pAcc.getStatus()))
			    	mv.setViewName("redirect:physician/PhysicianProfile");
			    else
			    	mv.setViewName("redirect:dashboard/physiciandashboard");
				
			}else if (userType.equalsIgnoreCase(PharmacyUtil.userPhysicianAssistant)) {
				selectedUserType = PharmacyUtil.userPhysicianAssistant;
				
				PhysicianAssistantAccount acct=phyAsstAcctRepo.findById(resetPasswordForm.getUserId());
				String oldPassword=acct.getPassword();
				resetPasswordForm.setOldPassword(oldPassword);
				
				loginForm=resetPasswordService.saveResetFormDetails(resetPasswordForm);
				session = request.getSession();
				session.setAttribute("loginDetail", loginForm);
				mv.addObject("loginForm", loginForm);
				// mv.setViewName("redirect:physician/editPhysicianassistantaccount?id="+loginForm.getUserid());
				//mv.setViewName("redirect:dashboard/physiciandashboard");
				
				//Modified on Jan 20,2018 - redirected to physician assistant 's Physicians list screen
			    mv.setViewName("redirect:dashboard/physicianassistanthome");
				
			}else if (userType.equalsIgnoreCase(PharmacyUtil.userAdministrator))
			{
				selectedUserType = PharmacyUtil.userAdministrator;
				
				AdminAccount acct=adminRepo.findById(resetPasswordForm.getUserId());
				String oldPassword=acct.getPassword();
				resetPasswordForm.setOldPassword(oldPassword);
				
				loginForm=resetPasswordService.saveResetFormDetails(resetPasswordForm);
				
				if (loginForm.getType().equalsIgnoreCase(PharmacyUtil.userAdmin)) {
					
					
					session = request.getSession();
					session.setAttribute("loginDetail", loginForm);
					mv.addObject("loginForm", loginForm);
					// mv.setViewName("redirect:admin/editAdminAccount?id="+loginForm.getUserid());
					// mv.setViewName("redirect:admin/adminProfile");
					mv.setViewName("redirect:dashboard/adminDashboard");
					
				}else if (loginForm.getType().equalsIgnoreCase(PharmacyUtil.userSuperAdmin)) {
					
					session = request.getSession();
					session.setAttribute("loginDetail", loginForm);
					mv.addObject("loginForm", loginForm);
					// mv.setViewName("redirect:admin/adminAccountSummary");
					mv.setViewName("redirect:dashboard/adminDashboard");
					
				}
				
				
			}else if (userType.equalsIgnoreCase(PharmacyUtil.userPatient)) {
				
				selectedUserType = PharmacyUtil.userPatient;
				
				PatientAccount acct=patRepo.findById(resetPasswordForm.getUserId());
				String oldPassword=acct.getPassword();
				resetPasswordForm.setOldPassword(oldPassword);
				
				loginForm=resetPasswordService.saveResetFormDetails(resetPasswordForm);
				session = request.getSession();
				session.setAttribute("loginDetail", loginForm);
				mv.addObject("loginForm", loginForm);
				//mv.setViewName("redirect:patient/editPatientAccount?id="+loginForm.getUserid());
				// mv.setViewName("redirect:dashboard/patientdashboard");
				
				// redirected to profile page
				if ( "New".equalsIgnoreCase(loginForm.getStatus()) || PharmacyUtil.statusProfileCompleted.equalsIgnoreCase(loginForm.getStatus()) )
					mv.setViewName("redirect:patient/patientProfile");
				else
					mv.setViewName("redirect:dashboard/patientdashboard");
			} else if (userType.equalsIgnoreCase(PharmacyUtil.userGroupDirector)) {
				
				selectedUserType = PharmacyUtil.userGroupDirector;
				
				GroupDirector acct=groupDirRepo.findById(resetPasswordForm.getUserId());
				String oldPassword=acct.getPassword();
				resetPasswordForm.setOldPassword(oldPassword);
				
				loginForm=resetPasswordService.saveResetFormDetails(resetPasswordForm);
				session = request.getSession();
				session.setAttribute("loginDetail", loginForm);
				mv.addObject("loginForm", loginForm);
				mv.setViewName("redirect:dashboard/groupdirectordashboard");
			}
			
			if ("Yes".equalsIgnoreCase(resetPasswordForm.getRememberMe())) {
				LoginController.setLoginCookies(loginForm.getUserName(), selectedUserType, request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			mv.setViewName("error500");
		}
		
		return mv;
	}
	/**
	 * This method loads the change password form page
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/changePassword", method = RequestMethod.GET)
	public ModelAndView loadChangePassword(HttpSession session, HttpServletRequest request) {
		
		ModelAndView mv = new ModelAndView();
		PhysicianAccount phyAcct=null;
		PhysicianAssistantAccount phyAsstAcct=null;
		AdminAccount adminAcct=null;
		PatientAccount patientAcct=null;
		GroupDirector groupDirAcc = null;
		String userType="";
		try {
			ResetPasswordForm resetPasswordForm = new ResetPasswordForm();
			
			LoginForm loggedInUser = (LoginForm) session.getAttribute("loginDetail");
			
			if (loggedInUser != null) {
				
				userType=loggedInUser.getType();
				
				if (loggedInUser.getType().equalsIgnoreCase(PharmacyUtil.userPhysician)) {
					phyAcct=phyAcctRepo.findOne(loggedInUser.getUserid());
				}else if (loggedInUser.getType().equalsIgnoreCase(PharmacyUtil.userPhysicianAssistant)) {
					phyAsstAcct=phyAsstAcctRepo.findOne(loggedInUser.getUserid());
				}else if (loggedInUser.getType().equalsIgnoreCase(PharmacyUtil.userSuperAdmin) || loggedInUser.getType().equalsIgnoreCase(PharmacyUtil.userAdmin)) {
					adminAcct=adminRepo.findOne(loggedInUser.getUserid());
				}else if (loggedInUser.getType().equalsIgnoreCase(PharmacyUtil.userPatient)) {
					patientAcct=patRepo.findOne(loggedInUser.getUserid());
				} else if (loggedInUser.getType().equalsIgnoreCase(PharmacyUtil.userGroupDirector)) {
					groupDirAcc=groupDirRepo.findOne(loggedInUser.getUserid());
				}
				
				if (userType.equalsIgnoreCase(PharmacyUtil.userPhysician)) {
					 resetPasswordForm.setUserId(loggedInUser.getUserid());
	    			 resetPasswordForm.setUserName(phyAcct.getPhysicianName());
	    			 resetPasswordForm.setUserType(loggedInUser.getType());
	    			 resetPasswordForm.setUserEmailId(phyAcct.getEmail());
	    			 resetPasswordForm.setOldPassword(phyAcct.getPassword());
	    			 mv.addObject("changePasswordForm", resetPasswordForm);
	    			 mv.setViewName("changePasswordForm");
				}else if (userType.equalsIgnoreCase(PharmacyUtil.userPhysicianAssistant)) {
					 resetPasswordForm.setUserId(loggedInUser.getUserid());
		   			 resetPasswordForm.setUserName(phyAsstAcct.getAssistantName());
		   			 resetPasswordForm.setUserType(loggedInUser.getType());
		   			 resetPasswordForm.setUserEmailId(phyAsstAcct.getEmail());
		   			 resetPasswordForm.setOldPassword(phyAsstAcct.getPassword());
		   			 mv.addObject("changePasswordForm", resetPasswordForm);
		   			 mv.setViewName("changePasswordForm");
				}else if (userType.equalsIgnoreCase(PharmacyUtil.userAdmin)) { 
					 resetPasswordForm.setUserId(loggedInUser.getUserid());
		  			 resetPasswordForm.setUserName(adminAcct.getName());
		  			 resetPasswordForm.setUserType(loggedInUser.getType());
		  			 resetPasswordForm.setUserEmailId(adminAcct.getEmail());
		  			 resetPasswordForm.setOldPassword(adminAcct.getPassword());
		  			 mv.addObject("changePasswordForm", resetPasswordForm);
		  			 mv.setViewName("changePasswordForm");
				}else if (userType.equalsIgnoreCase(PharmacyUtil.userSuperAdmin)) {
					 resetPasswordForm.setUserId(loggedInUser.getUserid());
		  			 resetPasswordForm.setUserName(adminAcct.getName());
		  			 resetPasswordForm.setUserType(loggedInUser.getType());
		  			 resetPasswordForm.setUserEmailId(adminAcct.getEmail());
		  			 resetPasswordForm.setOldPassword(adminAcct.getPassword());
		  			 mv.addObject("changePasswordForm", resetPasswordForm);
		  			 mv.setViewName("changePasswordForm");
				}else if (userType.equalsIgnoreCase(PharmacyUtil.userPatient)) {
					 resetPasswordForm.setUserId(loggedInUser.getUserid());
		  			 resetPasswordForm.setUserName(patientAcct.getPatientName());
		  			 resetPasswordForm.setUserType(loggedInUser.getType());
		  			 resetPasswordForm.setUserEmailId(patientAcct.getEmail());
		  			 resetPasswordForm.setOldPassword(patientAcct.getPassword());
		  			 mv.addObject("changePasswordForm", resetPasswordForm);
		  			 mv.setViewName("changePasswordForm");
				} else if (userType.equalsIgnoreCase(PharmacyUtil.userGroupDirector)) {
					 resetPasswordForm.setUserId(loggedInUser.getUserid());
		  			 resetPasswordForm.setUserName(groupDirAcc.getGroupDirectorName());
		  			 resetPasswordForm.setUserType(loggedInUser.getType());
		  			 resetPasswordForm.setUserEmailId(groupDirAcc.getEmail());
		  			 resetPasswordForm.setOldPassword(groupDirAcc.getPassword());
		  			 mv.addObject("changePasswordForm", resetPasswordForm);
		  			 mv.setViewName("changePasswordForm");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			mv.setViewName("error500");
		}
		
		return mv;
	}
	/**
	 * This method saves the new password changed
	 * @param resetPasswordForm
	 * @param model
	 * @param request
	 * @param response
	 * @param session
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "/saveNewPasswordDetails", method = RequestMethod.POST)
	public ModelAndView saveNewPasswordDetails(@ModelAttribute("resetPasswordForm")  ResetPasswordForm resetPasswordForm, Model model,
			HttpServletRequest request, HttpServletResponse response, HttpSession session, RedirectAttributes redirectAttributes) {
		
		ModelAndView mv = new ModelAndView();
		try {
			String userType=resetPasswordForm.getUserType();
			if (userType.equalsIgnoreCase(PharmacyUtil.userPhysician)) {
			
				PhysicianAccount acct=phyAcctRepo.findById(resetPasswordForm.getUserId());
				String oldPassword=acct.getPassword();
				resetPasswordForm.setOldPassword(oldPassword);
				
				if (acct != null) {
					List<PhysicianOldPassword> oldPwdAcct=phyOldPwdRepo.findByPasswordAndPhysicianId(resetPasswordForm.getNewPassword(), resetPasswordForm.getUserId());
					if(oldPwdAcct!=null && oldPwdAcct.size()>0 && oldPwdAcct.get(0).getRowId()>0  || resetPasswordForm.getNewPassword().equals(oldPassword))
					{
						if(resetPasswordForm.getNewPassword().equals(oldPassword)) {
							redirectAttributes.addFlashAttribute("message", env.getProperty("error.newPasswordNotSameasOldPassword"));
							redirectAttributes.addFlashAttribute("saveStatus", "false");
							mv.setViewName("redirect:changePassword");
						}
						else if(oldPwdAcct!=null && oldPwdAcct.size()>0 && oldPwdAcct.get(0).getRowId()>0) {
							redirectAttributes.addFlashAttribute("message", env.getProperty("error.oldpasswordreused"));
							redirectAttributes.addFlashAttribute("saveStatus", "false");
							mv.setViewName("redirect:changePassword");
						}
					} else {
						ResetPasswordForm resetForm=resetPasswordService.saveNewPasswordDetails(resetPasswordForm);
						redirectAttributes.addFlashAttribute("message", "Password updated successfully.");
						redirectAttributes.addFlashAttribute("saveStatus", "true");
						mv.setViewName("redirect:changePassword");
					} 
				} 
				
				
			}else if (userType.equalsIgnoreCase(PharmacyUtil.userPhysicianAssistant)) {
				

				
				PhysicianAssistantAccount acct=phyAsstAcctRepo.findById(resetPasswordForm.getUserId());
				String oldPassword=acct.getPassword();
				resetPasswordForm.setOldPassword(oldPassword);
				
				if (acct != null) {
					List<PhysicianAssistantOldPassword> oldPwdAcct=phyAsstOldPwdRepo.findByPasswordAndPhysicianAssistantId(resetPasswordForm.getNewPassword(), resetPasswordForm.getUserId());
					if(oldPwdAcct!=null && oldPwdAcct.size()>0 && oldPwdAcct.get(0).getRowId()>0  || resetPasswordForm.getNewPassword().equals(oldPassword))
					{
						if(resetPasswordForm.getNewPassword().equals(oldPassword)) {
							redirectAttributes.addFlashAttribute("message", env.getProperty("error.newPasswordNotSameasOldPassword"));
							redirectAttributes.addFlashAttribute("saveStatus", "false");
							mv.setViewName("redirect:changePassword");
						}
						else if(oldPwdAcct!=null && oldPwdAcct.size()>0 && oldPwdAcct.get(0).getRowId()>0) {
							redirectAttributes.addFlashAttribute("message", env.getProperty("error.oldpasswordreused"));
							redirectAttributes.addFlashAttribute("saveStatus", "false");
							mv.setViewName("redirect:changePassword");
						}
					} else {
						ResetPasswordForm resetForm=resetPasswordService.saveNewPasswordDetails(resetPasswordForm);
						redirectAttributes.addFlashAttribute("message", "Password updated successfully.");
						redirectAttributes.addFlashAttribute("saveStatus", "true");
						mv.setViewName("redirect:changePassword");
					} 
				} 
				
				
			
				
				
			}else if (userType.equalsIgnoreCase(PharmacyUtil.userAdmin) || userType.equalsIgnoreCase(PharmacyUtil.userSuperAdmin) || userType.equalsIgnoreCase(PharmacyUtil.userAdministrator)) { 
				
				
				AdminAccount acct=adminRepo.findById(resetPasswordForm.getUserId());
				String oldPassword=acct.getPassword();
				resetPasswordForm.setOldPassword(oldPassword);
				if (acct != null) {
					List<AdminOldPassword> oldPwdAcct=adminOldPwdRepo.findByPasswordAndAdminId(resetPasswordForm.getNewPassword(), resetPasswordForm.getUserId());
					if(oldPwdAcct!=null && oldPwdAcct.size()>0 && oldPwdAcct.get(0).getRowId()>0  || resetPasswordForm.getNewPassword().equals(oldPassword))
					{
						if(resetPasswordForm.getNewPassword().equals(oldPassword)) {
							redirectAttributes.addFlashAttribute("message", env.getProperty("error.newPasswordNotSameasOldPassword"));
							redirectAttributes.addFlashAttribute("saveStatus", "false");
							mv.setViewName("redirect:changePassword");
						}
						else if(oldPwdAcct!=null && oldPwdAcct.size()>0 && oldPwdAcct.get(0).getRowId()>0) {
							redirectAttributes.addFlashAttribute("message", env.getProperty("error.oldpasswordreused"));
							redirectAttributes.addFlashAttribute("saveStatus", "false");
							mv.setViewName("redirect:changePassword");
						}
					} else {
						ResetPasswordForm resetForm=resetPasswordService.saveNewPasswordDetails(resetPasswordForm);
						redirectAttributes.addFlashAttribute("message", "Password updated successfully.");
						redirectAttributes.addFlashAttribute("saveStatus", "true");
						mv.setViewName("redirect:changePassword");
					} 
				} 
				
				
				
			}else if (userType.equalsIgnoreCase(PharmacyUtil.userPatient)) {
				
				
				PatientAccount acct=patRepo.findById(resetPasswordForm.getUserId());
				String oldPassword=acct.getPassword();
				resetPasswordForm.setOldPassword(oldPassword);
				
				if (acct != null) {
					List<PatientOldPassword> oldPwdAcct=patientOldPwdRepo.findByPasswordAndPatientId(resetPasswordForm.getNewPassword(), resetPasswordForm.getUserId());
					if(oldPwdAcct!=null && oldPwdAcct.size()>0 && oldPwdAcct.get(0).getRowId()>0  || resetPasswordForm.getNewPassword().equals(oldPassword))
					{
						if(resetPasswordForm.getNewPassword().equals(oldPassword)) {
							redirectAttributes.addFlashAttribute("message", env.getProperty("error.newPasswordNotSameasOldPassword"));
							redirectAttributes.addFlashAttribute("saveStatus", "false");
							mv.setViewName("redirect:changePassword");
						}
						else if(oldPwdAcct!=null && oldPwdAcct.size()>0 && oldPwdAcct.get(0).getRowId()>0) {
							redirectAttributes.addFlashAttribute("message", env.getProperty("error.oldpasswordreused"));
							redirectAttributes.addFlashAttribute("saveStatus", "false");
							mv.setViewName("redirect:changePassword");
						}
					} else {
						ResetPasswordForm resetForm=resetPasswordService.saveNewPasswordDetails(resetPasswordForm);
						redirectAttributes.addFlashAttribute("message", "Password updated successfully.");
						redirectAttributes.addFlashAttribute("saveStatus", "true");
						mv.setViewName("redirect:changePassword");
					} 
				} 
				
				
				
			} else if (userType.equalsIgnoreCase(PharmacyUtil.userGroupDirector)) {
				
				
				GroupDirector acct=groupDirRepo.findById(resetPasswordForm.getUserId());
				String oldPassword=acct.getPassword();
				resetPasswordForm.setOldPassword(oldPassword);
				
				if (acct != null) {
					List<GroupDirectorOldPassword> oldPwdAcct=groupDirOldPassRepo.findByPasswordAndGroupDirectorId(resetPasswordForm.getNewPassword(), resetPasswordForm.getUserId());
					if(oldPwdAcct!=null && oldPwdAcct.size()>0 && oldPwdAcct.get(0).getRowId()>0  || resetPasswordForm.getNewPassword().equals(oldPassword))
					{
						if(resetPasswordForm.getNewPassword().equals(oldPassword)) {
							redirectAttributes.addFlashAttribute("message", env.getProperty("error.newPasswordNotSameasOldPassword"));
							redirectAttributes.addFlashAttribute("saveStatus", "false");
							mv.setViewName("redirect:changePassword");
						}
						else if(oldPwdAcct!=null && oldPwdAcct.size()>0 && oldPwdAcct.get(0).getRowId()>0) {
							redirectAttributes.addFlashAttribute("message", env.getProperty("error.oldpasswordreused"));
							redirectAttributes.addFlashAttribute("saveStatus", "false");
							mv.setViewName("redirect:changePassword");
						}
					} else {
						ResetPasswordForm resetForm=resetPasswordService.saveNewPasswordDetails(resetPasswordForm);
						redirectAttributes.addFlashAttribute("message", "Password updated successfully.");
						redirectAttributes.addFlashAttribute("saveStatus", "true");
						mv.setViewName("redirect:changePassword");
					} 
				} 
				
				
				
			}
			
			
			
			
			
			
			
			
			/*resetPasswordForm=resetPasswordService.saveNewPasswordDetails(resetPasswordForm);
			mv.addObject("changePasswordForm", resetPasswordForm);
			mv.setViewName("changePasswordForm");
			model.addAttribute("message", "Password updated successfully.");*/
		} catch (Exception e) {
			e.printStackTrace();
			mv.setViewName("error500");
		}
		
		return mv;
	}	
	/**
	 * This method loads the change security questions form page
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/changeSecurityQuestions", method = RequestMethod.GET)
	public ModelAndView loadChangeSecurityQuestions(HttpSession session, HttpServletRequest request) {
		
		ModelAndView mv = new ModelAndView();
		PhysicianAccount phyAcct=null;
		PhysicianAssistantAccount phyAsstAcct=null;
		AdminAccount adminAcct=null;
		PatientAccount patientAcct=null;
		GroupDirector groupDirAcct=null;
		String userType="";
		try {
			ResetPasswordForm resetPasswordForm = new ResetPasswordForm();
			
			List<SecurityQuestion> securityQuestionList = securityQuestionService.getAllSecurityQuestions();
			
			mv.addObject("securityQuestions", securityQuestionList);
			
			LoginForm loggedInUser = (LoginForm) session.getAttribute("loginDetail");
			
			if (loggedInUser != null) {
				
				userType=loggedInUser.getType();
				
				if (loggedInUser.getType().equalsIgnoreCase(PharmacyUtil.userPhysician)) {
					phyAcct=phyAcctRepo.findOne(loggedInUser.getUserid());
				}else if (loggedInUser.getType().equalsIgnoreCase(PharmacyUtil.userPhysicianAssistant)) {
					phyAsstAcct=phyAsstAcctRepo.findOne(loggedInUser.getUserid());
				}else if (loggedInUser.getType().equalsIgnoreCase(PharmacyUtil.userSuperAdmin) || loggedInUser.getType().equalsIgnoreCase(PharmacyUtil.userAdmin)) {
					adminAcct=adminRepo.findOne(loggedInUser.getUserid());
				}else if (loggedInUser.getType().equalsIgnoreCase(PharmacyUtil.userPatient)) {
					patientAcct=patRepo.findOne(loggedInUser.getUserid());
				} else if (loggedInUser.getType().equalsIgnoreCase(PharmacyUtil.userGroupDirector)) {
					groupDirAcct=groupDirRepo.findOne(loggedInUser.getUserid());
				}
				
				if (userType.equalsIgnoreCase(PharmacyUtil.userPhysician)) {
					
					
					 resetPasswordForm.setUserId(loggedInUser.getUserid());
	    			 resetPasswordForm.setUserName(phyAcct.getPhysicianName());
	    			 resetPasswordForm.setUserType(loggedInUser.getType());
	    			 resetPasswordForm.setUserEmailId(phyAcct.getEmail());
	    			 resetPasswordForm.setOldPassword(phyAcct.getPassword());
	    			 
	    			 resetPasswordForm.setSecurityquestion(phyAcct.getSecurityQuestionNumber());
	    			 resetPasswordForm.setSecurityanswer(phyAcct.getSecurityAnswer());
	    			 resetPasswordForm.setSecurityquestion2(phyAcct.getSecurityQuestionNumber2());
	    			 resetPasswordForm.setSecurityanswer2(phyAcct.getSecurityAnswer2());
	    			 
	    			 mv.addObject("changeSecurityQuestionForm", resetPasswordForm);
	    			 mv.setViewName("changeSecurityQuestionForm");
	    			 
				
					
				}else if (userType.equalsIgnoreCase(PharmacyUtil.userPhysicianAssistant)) {
					
					
					 resetPasswordForm.setUserId(loggedInUser.getUserid());
		   			 resetPasswordForm.setUserName(phyAsstAcct.getAssistantName());
		   			 resetPasswordForm.setUserType(loggedInUser.getType());
		   			 resetPasswordForm.setUserEmailId(phyAsstAcct.getEmail());
		   			 resetPasswordForm.setOldPassword(phyAsstAcct.getPassword());
		   			 
		   			 resetPasswordForm.setSecurityquestion(phyAsstAcct.getSecurityQuestionNumber());
	    			 resetPasswordForm.setSecurityanswer(phyAsstAcct.getSecurityAnswer());
	    			 resetPasswordForm.setSecurityquestion2(phyAsstAcct.getSecurityQuestionNumber2());
	    			 resetPasswordForm.setSecurityanswer2(phyAsstAcct.getSecurityAnswer2());
	    			 
		   			 mv.addObject("changeSecurityQuestionForm", resetPasswordForm);
		   			 mv.setViewName("changeSecurityQuestionForm");
	   			 
				
					
				}else if (userType.equalsIgnoreCase(PharmacyUtil.userAdmin)) { 
					
					 resetPasswordForm.setUserId(loggedInUser.getUserid());
		  			 resetPasswordForm.setUserName(adminAcct.getName());
		  			 resetPasswordForm.setUserType(loggedInUser.getType());
		  			 resetPasswordForm.setUserEmailId(adminAcct.getEmail());
		  			 resetPasswordForm.setOldPassword(adminAcct.getPassword());
		  			 
		  			 resetPasswordForm.setSecurityquestion(adminAcct.getSecurityQuestionNumber());
	    			 resetPasswordForm.setSecurityanswer(adminAcct.getSecurityAnswer());
	    			 resetPasswordForm.setSecurityquestion2(adminAcct.getSecurityQuestionNumber2());
	    			 resetPasswordForm.setSecurityanswer2(adminAcct.getSecurityAnswer2());
	    			 
	    			 
		  			 mv.addObject("changeSecurityQuestionForm", resetPasswordForm);
		  			 mv.setViewName("changeSecurityQuestionForm");
	  			 
				}else if (userType.equalsIgnoreCase(PharmacyUtil.userSuperAdmin)) {
					
					 resetPasswordForm.setUserId(loggedInUser.getUserid());
		  			 resetPasswordForm.setUserName(adminAcct.getName());
		  			 resetPasswordForm.setUserType(loggedInUser.getType());
		  			 resetPasswordForm.setUserEmailId(adminAcct.getEmail());
		  			 resetPasswordForm.setOldPassword(adminAcct.getPassword());
		  			 
		  			 resetPasswordForm.setSecurityquestion(adminAcct.getSecurityQuestionNumber());
	    			 resetPasswordForm.setSecurityanswer(adminAcct.getSecurityAnswer());
	    			 resetPasswordForm.setSecurityquestion2(adminAcct.getSecurityQuestionNumber2());
	    			 resetPasswordForm.setSecurityanswer2(adminAcct.getSecurityAnswer2());
	    			 
		  			 mv.addObject("changeSecurityQuestionForm", resetPasswordForm);
		  			 mv.setViewName("changeSecurityQuestionForm");
					
				}else if (userType.equalsIgnoreCase(PharmacyUtil.userPatient)) {
					
					
					 resetPasswordForm.setUserId(loggedInUser.getUserid());
		  			 resetPasswordForm.setUserName(patientAcct.getPatientName());
		  			 resetPasswordForm.setUserType(loggedInUser.getType());
		  			 resetPasswordForm.setUserEmailId(patientAcct.getEmail());
		  			 resetPasswordForm.setOldPassword(patientAcct.getPassword());
		  			 
		  			 resetPasswordForm.setSecurityquestion(patientAcct.getSecurityQuestionNumber());
	    			 resetPasswordForm.setSecurityanswer(patientAcct.getSecurityAnswer());
	    			 resetPasswordForm.setSecurityquestion2(patientAcct.getSecurityQuestionNumber2());
	    			 resetPasswordForm.setSecurityanswer2(patientAcct.getSecurityAnswer2());
	    			 
	    			 mv.addObject("changeSecurityQuestionForm", resetPasswordForm);
		  			 mv.setViewName("changeSecurityQuestionForm");
					
				} else if (userType.equalsIgnoreCase(PharmacyUtil.userGroupDirector)) {
					 resetPasswordForm.setUserId(loggedInUser.getUserid());
		  			 resetPasswordForm.setUserName(groupDirAcct.getGroupDirectorName());
		  			 resetPasswordForm.setUserType(loggedInUser.getType());
		  			 resetPasswordForm.setUserEmailId(groupDirAcct.getEmail());
		  			 resetPasswordForm.setOldPassword(groupDirAcct.getPassword());
		  			 
		  			 resetPasswordForm.setSecurityquestion(groupDirAcct.getSecurityQuestionNumber());
	    			 resetPasswordForm.setSecurityanswer(groupDirAcct.getSecurityAnswer());
	    			 resetPasswordForm.setSecurityquestion2(groupDirAcct.getSecurityQuestionNumber2());
	    			 resetPasswordForm.setSecurityanswer2(groupDirAcct.getSecurityAnswer2());
	    			 
	    			 mv.addObject("changeSecurityQuestionForm", resetPasswordForm);
		  			 mv.setViewName("changeSecurityQuestionForm");
					
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			mv.setViewName("error500");
		}
		
		return mv;
	}
	/**
	 * This method saves the change password details
	 * @param resetPasswordForm
	 * @param model
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/saveSecurityQuestionsDetails", method = RequestMethod.POST)
	public ModelAndView saveSecurityQuestionsDetails(@ModelAttribute("resetPasswordForm")  ResetPasswordForm resetPasswordForm, Model model,
			HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		
		ModelAndView mv = new ModelAndView();
		
		try {
			
			List<SecurityQuestion> securityQuestionList = securityQuestionService.getAllSecurityQuestions();
			
			mv.addObject("securityQuestions", securityQuestionList);
			resetPasswordForm=resetPasswordService.saveSecurityQuestionDetails(resetPasswordForm);
			mv.addObject("changeSecurityQuestionForm", resetPasswordForm);
			mv.setViewName("changeSecurityQuestionForm");
			model.addAttribute("message", "Security Questions updated successfully.");
	
		} catch (Exception e) {
			e.printStackTrace();
			mv.setViewName("error500");
		}
		
		return mv;
	}	
	
}
