package com.pharma.core.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pharma.core.formBean.ForgotPasswordForm;
import com.pharma.core.formBean.ResetPasswordForm;
import com.pharma.core.model.SecurityQuestion;
import com.pharma.core.model.admin.AdminAccount;
import com.pharma.core.model.patientaccount.PatientAccount;
import com.pharma.core.model.pharmacygroup.GroupDirector;
import com.pharma.core.model.physician.PhysicianAccount;
import com.pharma.core.model.physician.PhysicianAssistantAccount;
import com.pharma.core.pharmaservices.LoginService;
import com.pharma.core.pharmaservices.ResetPasswordService;
import com.pharma.core.pharmaservices.SecurityQuestionService;
import com.pharma.core.repository.admin.AdminAccountRepository;
import com.pharma.core.repository.patient.PatientAccountRespository;
import com.pharma.core.repository.pharmacygroup.GroupDirectorAccountRespository;
import com.pharma.core.repository.physician.PhysicianAccountRespository;
import com.pharma.core.repository.physician.PhysicianAssistantAccountRespository;
import com.pharma.core.util.HipaaPasswordValidator;
import com.pharma.core.util.PharmacyUtil;
/**
 * This class is a controller to retrieves the forgotten password for the existing emailid
 *
 */
@Controller
@SessionAttributes("loginDetail")
@PropertySource("classpath:hipaa.properties")
@PropertySource("classpath:forgotPassword.properties")
public class ForgotPasswordController { 
	
	@Autowired
	private Environment env;
	
	@Autowired
	private LoginService loginService;
		
	@Autowired
	PhysicianAccountRespository phyAcctRepo;
	
	@Autowired
	PhysicianAssistantAccountRespository phyAsstAcctPwdRepo;
	
	@Autowired
	AdminAccountRepository adminRepo;
	
	@Autowired
	PatientAccountRespository patRepo;
	
	@Autowired
	SecurityQuestionService securityQuestionService;
	
	@Autowired
	GroupDirectorAccountRespository groupDirRepo;
	
	@Autowired
	ResetPasswordService resetPasswordService;
	
    HipaaPasswordValidator hipaaValidator;

	/**
	 * This method loads the forgot Password page
	 * @param model
	 * @param forgotPasswordForm
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/forgotPassword", method = RequestMethod.GET)
	public ModelAndView loadForgotPasswordForm(Model model,@ModelAttribute("forgotPasswordForm")  ForgotPasswordForm forgotPasswordForm, HttpSession session, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		try {
			
			List<SecurityQuestion> securityQuestionList = securityQuestionService.getAllSecurityQuestions();
			/*//System.out.println("message **********   "+forgotPasswordForm.getUserId());
			//System.out.println("message **********   "+forgotPasswordForm.getUserName());*/
			
			mv.addObject("securityQuestions", securityQuestionList);
			model.addAttribute("showusertype", "showusertype");
			mv.addObject("forgotPasswordForm", forgotPasswordForm);
			mv.setViewName("forgotPasswordForm");
		} catch(Exception e) {
			e.printStackTrace();
			mv.setViewName("forgotPasswordForm");
		}
		return mv;
	}
	/**
	 * This method loads the patient forgot Password page
	 * @param model
	 * @param forgotPasswordForm
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/patientforgotPassword", method = RequestMethod.GET)
	public ModelAndView loadPAtientForgotPasswordForm(Model model,@ModelAttribute("forgotPasswordForm")  ForgotPasswordForm forgotPasswordForm, HttpSession session, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		try {
			
			List<SecurityQuestion> securityQuestionList = securityQuestionService.getAllSecurityQuestions();
			/*//System.out.println("message **********   "+forgotPasswordForm.getUserId());
			//System.out.println("message **********   "+forgotPasswordForm.getUserName());*/
			
			mv.addObject("securityQuestions", securityQuestionList);
			model.addAttribute("notshowusertype", "notshowusertype");
			mv.addObject("forgotPasswordForm", forgotPasswordForm);
			mv.setViewName("forgotPasswordForm");
		} catch(Exception e) {
			e.printStackTrace();
			mv.setViewName("forgotPasswordForm");
		}
		return mv;
	}
	/**
	 * This method loads the security questions and answers page for the given emailid to retrieve the forgotten password
	 * @param model
	 * @param forgotPasswordForm
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping( value = "/checkForgotPasswordEmailidExists", method = RequestMethod.POST)
	public ModelAndView checkForgotPasswordEmailidExists(Model model,@ModelAttribute("forgotPasswordForm")  ForgotPasswordForm forgotPasswordForm,
			HttpSession session, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
			try {
		String errMsg="",userType="";
		userType=forgotPasswordForm.getType();
		List<SecurityQuestion> securityQuestionList = securityQuestionService.getAllSecurityQuestions();
		mv.addObject("securityQuestions", securityQuestionList);
		if (userType.equalsIgnoreCase(PharmacyUtil.userPhysician)) {
		
			List<PhysicianAccount> phyAcct=phyAcctRepo.findByEmail(forgotPasswordForm.getEmail());
			
			if(phyAcct.size() > 0 && phyAcct.get(0).getId()>0 )
			{
				forgotPasswordForm.setSecurityquestion(phyAcct.get(0).getSecurityQuestionNumber());
				forgotPasswordForm.setSecurityquestion2(phyAcct.get(0).getSecurityQuestionNumber2());
				/*forgotPasswordForm.setSecurityanswer(phyAcct.getSecurityAnswer());
				forgotPasswordForm.setSecurityanswer2(phyAcct.getSecurityAnswer2());*/
				model.addAttribute("showusertype", "showusertype");
				model.addAttribute("showsecurityques", "showsecurityques");
			 	mv.setViewName("forgotPasswordForm");
			}else
			{
				errMsg=env.getProperty("error.invalidemailid");
				model.addAttribute("showusertype", "showusertype");
    			model.addAttribute("error", errMsg);
			 	mv.setViewName("forgotPasswordForm");
			}
			
			}else if (userType.equalsIgnoreCase(PharmacyUtil.userPhysicianAssistant)) {
				
				List<PhysicianAssistantAccount> phyAsstAcct=phyAsstAcctPwdRepo.findByEmail(forgotPasswordForm.getEmail());
				
				if(phyAsstAcct.size() > 0 && phyAsstAcct.get(0).getId()>0)
				{
					forgotPasswordForm.setSecurityquestion(phyAsstAcct.get(0).getSecurityQuestionNumber());
					forgotPasswordForm.setSecurityquestion2(phyAsstAcct.get(0).getSecurityQuestionNumber2());
					/*forgotPasswordForm.setSecurityanswer(phyAsstAcct.getSecurityAnswer());
					forgotPasswordForm.setSecurityanswer2(phyAsstAcct.getSecurityAnswer2());*/
					model.addAttribute("showusertype", "showusertype");
					model.addAttribute("showsecurityques", "showsecurityques");
					mv.setViewName("forgotPasswordForm");
				}else
				{
					errMsg=env.getProperty("error.invalidemailid");
					model.addAttribute("showusertype", "showusertype");
	    			model.addAttribute("error", errMsg);
				 	mv.setViewName("forgotPasswordForm");
				}
				
			}else if (userType.equalsIgnoreCase(PharmacyUtil.userAdministrator) || userType.equalsIgnoreCase(PharmacyUtil.userSuperAdmin) || userType.equalsIgnoreCase(PharmacyUtil.userAdmin)) {
				
				List<AdminAccount> obj = adminRepo.findByEmail(forgotPasswordForm.getEmail());
				AdminAccount adminAcct=null;
				if (obj.size() > 0)
					adminAcct= obj.get(0);
				
				
				if(adminAcct!=null && adminAcct.getId()>0)
				{
					forgotPasswordForm.setSecurityquestion(adminAcct.getSecurityQuestionNumber());
					forgotPasswordForm.setSecurityquestion2(adminAcct.getSecurityQuestionNumber2());
					/*forgotPasswordForm.setSecurityanswer(adminAcct.getSecurityAnswer());
					forgotPasswordForm.setSecurityanswer2(adminAcct.getSecurityAnswer2());*/
					model.addAttribute("showsecurityques", "showsecurityques");
					model.addAttribute("showusertype", "showusertype");
					mv.setViewName("forgotPasswordForm");
				}else
				{
					errMsg=env.getProperty("error.invalidemailid");
					model.addAttribute("showusertype", "showusertype");
	    			model.addAttribute("error", errMsg);
				 	mv.setViewName("forgotPasswordForm");
				}
				
			}else if (userType.equalsIgnoreCase(PharmacyUtil.userPatient)) {
				
				List<PatientAccount> patAcctList=patRepo.findByUserLoginId(forgotPasswordForm.getUsername());
				if(patAcctList!=null && patAcctList.size()>0){
				PatientAccount patAcct = patAcctList.get(0);
				
					if(patAcct!=null && patAcct.getId()>0)
					{
						forgotPasswordForm.setSecurityquestion(patAcct.getSecurityQuestionNumber());
						forgotPasswordForm.setSecurityquestion2(patAcct.getSecurityQuestionNumber2());
						/*forgotPasswordForm.setSecurityanswer(patAcct.getSecurityAnswer());
						forgotPasswordForm.setSecurityanswer2(patAcct.getSecurityAnswer2());*/
						model.addAttribute("showsecurityques", "showsecurityques");
						model.addAttribute("notshowusertype", "notshowusertype");
						mv.setViewName("forgotPasswordForm");
					}else
					{
						errMsg=env.getProperty("error.invalidusername");
						model.addAttribute("notshowusertype", "notshowusertype");
		    			model.addAttribute("error", errMsg);
					 	mv.setViewName("forgotPasswordForm");
					}
				}else
				{
					errMsg=env.getProperty("error.invalidusername");
					model.addAttribute("notshowusertype", "notshowusertype");
	    			model.addAttribute("error", errMsg);
				 	mv.setViewName("forgotPasswordForm");
				}
				
			} else if (userType.equalsIgnoreCase(PharmacyUtil.userGroupDirector)) {
				
				List<GroupDirector> groupDirAcctList=groupDirRepo.findByEmail(forgotPasswordForm.getEmail());
				GroupDirector groupDirAcct = groupDirAcctList.get(0);
				
				if(groupDirAcct!=null && groupDirAcct.getId()>0)
				{
					forgotPasswordForm.setSecurityquestion(groupDirAcct.getSecurityQuestionNumber());
					forgotPasswordForm.setSecurityquestion2(groupDirAcct.getSecurityQuestionNumber2());
					model.addAttribute("showsecurityques", "showsecurityques");
					model.addAttribute("showusertype", "showusertype");
					mv.setViewName("forgotPasswordForm");
				}else
				{
					errMsg=env.getProperty("error.invalidemailid");
					model.addAttribute("showusertype", "showusertype");
	    			model.addAttribute("error", errMsg);
				 	mv.setViewName("forgotPasswordForm");
				}
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return mv;	
	}
	
	/**
	 * This method validates the security answers for the loaded security questions to retrieve the forgotten password
	 * @param model
	 * @param forgotPasswordForm
	 * @param session
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "/validateUserSecurityQuestionsandAnswers",method = RequestMethod.POST)
	public ModelAndView validateUserSecurityQuestionsandAnswers(Model model,@ModelAttribute("forgotPasswordForm")  ForgotPasswordForm forgotPasswordForm,
			HttpSession session,final RedirectAttributes redirectAttributes) {
	
		int flagType=0;
		ModelAndView mv = new ModelAndView();
		HipaaPasswordValidator hipaaValidator=null;
		List<PhysicianAccount> phyAcct=null;
		List<PhysicianAssistantAccount> phyAsstAcct=null;
		AdminAccount adminAcct=null;
		PatientAccount patientAcct=null;
		List<GroupDirector> groupDirAcct=null;
		String errMsg="";
	    try {
	    	//System.out.println("username ===="+forgotPasswordForm.getEmail());
	    	//System.out.println("user type ===="+forgotPasswordForm.getType());
	    	
			
			if (forgotPasswordForm != null && (forgotPasswordForm.getEmail() != null ||  forgotPasswordForm.getUsername() != null) && forgotPasswordForm.getType() != null) {
				
						hipaaValidator=new HipaaPasswordValidator();
						
						if (forgotPasswordForm.getType().equalsIgnoreCase(PharmacyUtil.userPhysician)) {
							
							phyAcct=phyAcctRepo.findByEmail(forgotPasswordForm.getEmail());
							if(phyAcct != null && phyAcct.size()>0) {
								flagType=hipaaValidator.validateUserSecurityQuesAnswer( model, phyAcct.get(0), forgotPasswordForm,env,loginService);
							}
						}else if (forgotPasswordForm.getType().equalsIgnoreCase(PharmacyUtil.userPhysicianAssistant)) {
							
							phyAsstAcct=phyAsstAcctPwdRepo.findByEmail(forgotPasswordForm.getEmail());
							if(phyAsstAcct != null && phyAsstAcct.size()>0) {
								flagType=hipaaValidator.validateUserSecurityQuesAnswer( model, phyAsstAcct.get(0), forgotPasswordForm,env,loginService);
							}
						}else if (forgotPasswordForm.getType().equalsIgnoreCase(PharmacyUtil.userAdministrator) || forgotPasswordForm.getType().equalsIgnoreCase(PharmacyUtil.userSuperAdmin) || forgotPasswordForm.getType().equalsIgnoreCase(PharmacyUtil.userAdmin)) {
							
							List<AdminAccount> obj = adminRepo.findByEmail(forgotPasswordForm.getEmail());
							if (obj.size() > 0)
								adminAcct= obj.get(0);
							
							if(adminAcct != null) {
								flagType=hipaaValidator.validateUserSecurityQuesAnswer( model, adminAcct, forgotPasswordForm,env,loginService);
							}
							
						}else if (forgotPasswordForm.getType().equalsIgnoreCase(PharmacyUtil.userPatient)) {
							
							List<PatientAccount> patAcctList=patRepo.findByUserLoginId(forgotPasswordForm.getUsername());
							if(patAcctList!=null && patAcctList.size()>0){
								patientAcct = patAcctList.get(0);							
								if(patientAcct != null) {
									flagType=hipaaValidator.validateUserSecurityQuesAnswer( model, patientAcct, forgotPasswordForm,env,loginService);
								}
							}
						} else if (forgotPasswordForm.getType().equalsIgnoreCase(PharmacyUtil.userGroupDirector)) {
							groupDirAcct=groupDirRepo.findByEmail(forgotPasswordForm.getEmail());
							if(groupDirAcct!=null && groupDirAcct.size() > 0) {
								flagType=hipaaValidator.validateUserSecurityQuesAnswer( model, groupDirAcct.get(0), forgotPasswordForm, env,loginService);
							}
						}
						
						//System.out.println("flagType ============"+flagType);
						
						if(flagType==1)//validdetails
			    		{
							if (phyAcct!=null && phyAcct.size() > 0 && forgotPasswordForm.getType().equalsIgnoreCase(PharmacyUtil.userPhysician)){
			    			 	 ResetPasswordForm resetPasswordForm=new ResetPasswordForm();
			    				 resetPasswordForm.setUserId(phyAcct.get(0).getId());
				    			 resetPasswordForm.setUserName(phyAcct.get(0).getPhysicianName());
				    			 resetPasswordForm.setUserType(forgotPasswordForm.getType());
				    			 resetPasswordForm.setUserEmailId(forgotPasswordForm.getEmail());
				    			 resetPasswordForm.setOldPassword(phyAcct.get(0).getPassword());
				    			 resetPasswordForm.setSecurityquestion(phyAcct.get(0).getSecurityQuestionNumber());
				    			 resetPasswordForm.setSecurityquestion2(phyAcct.get(0).getSecurityQuestionNumber2());
				    			 resetPasswordForm.setSecurityanswer(phyAcct.get(0).getSecurityAnswer());
				    			 resetPasswordForm.setSecurityanswer2(phyAcct.get(0).getSecurityAnswer2());
				    			 redirectAttributes.addFlashAttribute("resetPasswordForm",resetPasswordForm);
				    			 errMsg=env.getProperty("error.resetPassword");
				    			 model.addAttribute("error", errMsg);
				    			 mv.setViewName("redirect:loadResetPasswordForm");
							}else if (phyAsstAcct!=null && forgotPasswordForm.getType().equalsIgnoreCase(PharmacyUtil.userPhysicianAssistant)){
			    			 	 ResetPasswordForm resetPasswordForm=new ResetPasswordForm();
			    				 resetPasswordForm.setUserId(phyAsstAcct.get(0).getId());
				    			 resetPasswordForm.setUserName(phyAsstAcct.get(0).getAssistantName());
				    			 resetPasswordForm.setUserType(forgotPasswordForm.getType());
				    			 resetPasswordForm.setUserEmailId(forgotPasswordForm.getEmail());
				    			 resetPasswordForm.setOldPassword(phyAsstAcct.get(0).getPassword());
				    			 resetPasswordForm.setSecurityquestion(phyAsstAcct.get(0).getSecurityQuestionNumber());
				    			 resetPasswordForm.setSecurityquestion2(phyAsstAcct.get(0).getSecurityQuestionNumber2());
				    			 resetPasswordForm.setSecurityanswer(phyAsstAcct.get(0).getSecurityAnswer());
				    			 resetPasswordForm.setSecurityanswer2(phyAsstAcct.get(0).getSecurityAnswer2());
				    			 redirectAttributes.addFlashAttribute("resetPasswordForm",resetPasswordForm);
				    			 errMsg=env.getProperty("error.resetPassword");
				    			 model.addAttribute("error", errMsg);
				    			 mv.setViewName("redirect:/loadResetPasswordForm");
							}else if (adminAcct!=null && 
									(forgotPasswordForm.getType().equalsIgnoreCase(PharmacyUtil.userAdministrator) || forgotPasswordForm.getType().equalsIgnoreCase(PharmacyUtil.userSuperAdmin) || forgotPasswordForm.getType().equalsIgnoreCase(PharmacyUtil.userAdmin))){
			    			 	 ResetPasswordForm resetPasswordForm=new ResetPasswordForm();
			    				 resetPasswordForm.setUserId(adminAcct.getId());
				    			 resetPasswordForm.setUserName(adminAcct.getName());
				    			 resetPasswordForm.setUserType(forgotPasswordForm.getType());
				    			 resetPasswordForm.setUserEmailId(forgotPasswordForm.getEmail());
				    			 resetPasswordForm.setOldPassword(adminAcct.getPassword());
				    			 resetPasswordForm.setSecurityquestion(adminAcct.getSecurityQuestionNumber());
				    			 resetPasswordForm.setSecurityquestion2(adminAcct.getSecurityQuestionNumber2());
				    			 resetPasswordForm.setSecurityanswer(adminAcct.getSecurityAnswer());
				    			 resetPasswordForm.setSecurityanswer2(adminAcct.getSecurityAnswer2());
				    			 redirectAttributes.addFlashAttribute("resetPasswordForm",resetPasswordForm);
				    			 errMsg=env.getProperty("error.resetPassword");
				    			 model.addAttribute("error", errMsg);
				    			 mv.setViewName("redirect:/loadResetPasswordForm");
							}else if (patientAcct!=null && (forgotPasswordForm.getType().equalsIgnoreCase(PharmacyUtil.userPatient))){
			    			 	 ResetPasswordForm resetPasswordForm=new ResetPasswordForm();
			    				 resetPasswordForm.setUserId(patientAcct.getId());
				    			 resetPasswordForm.setUserName(patientAcct.getPatientName());
				    			 resetPasswordForm.setUserType(forgotPasswordForm.getType());
				    			 resetPasswordForm.setUserEmailId(forgotPasswordForm.getEmail());
				    			 resetPasswordForm.setOldPassword(patientAcct.getPassword());
				    			 resetPasswordForm.setSecurityquestion(patientAcct.getSecurityQuestionNumber());
				    			 resetPasswordForm.setSecurityquestion2(patientAcct.getSecurityQuestionNumber2());
				    			 resetPasswordForm.setSecurityanswer(patientAcct.getSecurityAnswer());
				    			 resetPasswordForm.setSecurityanswer2(patientAcct.getSecurityAnswer2());
				    			 redirectAttributes.addFlashAttribute("resetPasswordForm",resetPasswordForm);
				    			 errMsg=env.getProperty("error.resetPassword");
				    			 model.addAttribute("error", errMsg);
				    			 mv.setViewName("redirect:/loadResetPasswordForm");
							}else if (groupDirAcct!=null && (forgotPasswordForm.getType().equalsIgnoreCase(PharmacyUtil.userGroupDirector))){
			    			 	 ResetPasswordForm resetPasswordForm=new ResetPasswordForm();
			    				 resetPasswordForm.setUserId(groupDirAcct.get(0).getId());
				    			 resetPasswordForm.setUserName(groupDirAcct.get(0).getGroupDirectorName());
				    			 resetPasswordForm.setUserType(forgotPasswordForm.getType());
				    			 resetPasswordForm.setUserEmailId(forgotPasswordForm.getEmail());
				    			 resetPasswordForm.setOldPassword(groupDirAcct.get(0).getPassword());
				    			 resetPasswordForm.setSecurityquestion(groupDirAcct.get(0).getSecurityQuestionNumber());
				    			 resetPasswordForm.setSecurityquestion2(groupDirAcct.get(0).getSecurityQuestionNumber2());
				    			 resetPasswordForm.setSecurityanswer(groupDirAcct.get(0).getSecurityAnswer());
				    			 resetPasswordForm.setSecurityanswer2(groupDirAcct.get(0).getSecurityAnswer2());
				    			 redirectAttributes.addFlashAttribute("resetPasswordForm",resetPasswordForm);
				    			 errMsg=env.getProperty("error.resetPassword");
				    			 model.addAttribute("error", errMsg);
				    			 mv.setViewName("redirect:/loadResetPasswordForm");
							}
							
			    		}else if(flagType==2)//invalidemailid
			    		{
			    			List<SecurityQuestion> securityQuestionList = securityQuestionService.getAllSecurityQuestions();
			    			mv.addObject("securityQuestions", securityQuestionList);
			    			errMsg=env.getProperty("error.invalidemailid");
			    			model.addAttribute("forgotPasswordForm", forgotPasswordForm);
			    			
			    			if (forgotPasswordForm.getType().equalsIgnoreCase(PharmacyUtil.userPatient))
			    					model.addAttribute("notshowusertype", "notshowusertype");
			    			else
			    					model.addAttribute("showusertype", "showusertype");
			    			
			    			
					        model.addAttribute("error", errMsg);
					        mv.setViewName("forgotPasswordForm");
			    			
			    		}else if(flagType==3)//invalidsecurityquestionanswer
			    		{
			    			List<SecurityQuestion> securityQuestionList = securityQuestionService.getAllSecurityQuestions();
			    			mv.addObject("securityQuestions", securityQuestionList);
			    			errMsg="";
			    			model.addAttribute("forgotPasswordForm", forgotPasswordForm);
			    			if(model.asMap().get("invalidque1")!=null && model.asMap().get("invalidque1").toString().length()>0)
			    			{
			    				errMsg=env.getProperty("error.invalidSecurityQuestion1");
			    			}
			    			if(model.asMap().get("invalidque2")!=null && model.asMap().get("invalidque2").toString().length()>0)
			    			{
			    				errMsg+=env.getProperty("error.invalidSecurityQuestion2");
			    			}
			    			if(model.asMap().get("invalidans1")!=null && model.asMap().get("invalidans1").toString().length()>0)
			    			{
			    				errMsg+=env.getProperty("error.invalidSecurityAnswer1");
			    			}
			    			if(model.asMap().get("invalidans2")!=null && model.asMap().get("invalidans2").toString().length()>0)
			    			{
			    				errMsg+=env.getProperty("error.invalidSecurityAnswer2");
			    			}
					        model.addAttribute("error", errMsg);
					        model.addAttribute("showsecurityques", "showsecurityques");
					        if (forgotPasswordForm.getType().equalsIgnoreCase(PharmacyUtil.userPatient))
		    					model.addAttribute("notshowusertype", "notshowusertype");
					        else
		    					model.addAttribute("showusertype", "showusertype");
					        mv.setViewName("forgotPasswordForm");
			    			
			    		}else if(flagType==4)//forgotlockuptime
			    		{
			    			List<SecurityQuestion> securityQuestionList = securityQuestionService.getAllSecurityQuestions();
			    			mv.addObject("securityQuestions", securityQuestionList);
			    			errMsg=env.getProperty("error.forgotlockuptime");
			    			model.addAttribute("forgotPasswordForm", forgotPasswordForm);
			    			model.addAttribute("error", errMsg+model.asMap().get("elapsingtime"));
			    			if (forgotPasswordForm.getType().equalsIgnoreCase(PharmacyUtil.userPatient))
		    					model.addAttribute("notshowusertype", "notshowusertype");
			    			else
		    					model.addAttribute("showusertype", "showusertype");
			    			model.addAttribute("showsecurityques", "showsecurityques");
					        mv.setViewName("forgotPasswordForm");
			    		}
					
					}else {
						List<SecurityQuestion> securityQuestionList = securityQuestionService.getAllSecurityQuestions();
						mv.addObject("securityQuestions", securityQuestionList);
						errMsg=env.getProperty("error.invalidemailid");
		    			model.addAttribute("forgotPasswordForm", forgotPasswordForm);
		    			if (forgotPasswordForm.getType().equalsIgnoreCase(PharmacyUtil.userPatient))
	    					model.addAttribute("notshowusertype", "notshowusertype");
		    			else
	    					model.addAttribute("showusertype", "showusertype");
				        model.addAttribute("error", errMsg);
				        mv.setViewName("forgotPasswordForm");
					}
		
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("message", env.getProperty("error.internalError") );
			mv.setViewName("error500");
		}
	    return mv;
	}
}
