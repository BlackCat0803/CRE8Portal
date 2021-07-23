package com.pharma.core.util;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.core.env.Environment;
import org.springframework.ui.Model;

import com.pharma.core.formBean.ForgotPasswordForm;
import com.pharma.core.formBean.LoginForm;
import com.pharma.core.model.patientaccount.PatientAccount;
import com.pharma.core.model.admin.AdminAccount;
import com.pharma.core.model.pharmacygroup.GroupDirector;
import com.pharma.core.model.physician.PhysicianAccount;
import com.pharma.core.model.physician.PhysicianAssistantAccount;
import com.pharma.core.pharmaservices.LoginService;

/**
 * The class <<HipaaPasswordValidator>> is used to validate the password by HIPAA Validation rules
 */
public class HipaaPasswordValidator {
	
	/**
	 * 
	 *    Maximum age: All passwords must be changed at least every sixty (60) days.
    	  History: Set at six (6), meaning the password needs to be set six times before it can be reused.
          Account Lockout Threshold: After five (5) unsuccessful attempts to enter a password, the involved user-ID will be temporarily disabled for five (5) minutes after which the account will be automatically unlocked.

		1) To check the password over 60 days, we have to store the password changed date
		
		   All passwords must be changed at least every sixty (60) days.
		   
		   While loggingin check for (currentdate-passwordchangeddate)>60
		   then force them to change the password and store the new password and update passwordchangeddate column
		   
		2) Store only the previous 6 passwords in the comma separated format
		
		   Set at six (6), meaning the password needs to be set six times before it can be reused
		
		3) Store the loginattempts count, lockedup datetime for 5 mins if the count of the attempt exceeds the count 5
		
		   After five (5) unsuccessful attempts to enter a password, 
		   the involved user-ID will be temporarily disabled for five (5) minutes after which the account will be automatically unlocked.
		   
		   If on successful, reset loginattempts count,lockedup datetime
   
	 * @param user
	 * @param sessionmap
	 * @return
	 */
	public int HIPAAPasswordRuleforLoginValidation(int checkingType,int pwdChangedDays,long lockedupTime,Environment env)
	{
		int flagType=0;
		long currentTime=0;
		int maximumPasswordExpiryDays=Integer.valueOf(env.getProperty("maximumPasswordExpiryDays"));
		int accountLockupTime=Integer.valueOf(env.getProperty("accountLockupTime"));
		try
		{
			if(checkingType==1)//Check for Account Lockout Threshold
			{
				currentTime=System.currentTimeMillis();
	    		////System.out.println("currentTime =============="+currentTime);
	    		////System.out.println("lockedupTime =============="+lockedupTime);
	    		if(currentTime<lockedupTime)
	    			flagType=7;
			}else if(checkingType==2)//Check the password over 60 days
			{
				currentTime=System.currentTimeMillis();
	    			 
	    		//System.out.println("pwdChangedDays ["+pwdChangedDays+"]=========="+"maximumPasswordExpiryDays ["+maximumPasswordExpiryDays+"]");
	    		if(pwdChangedDays<maximumPasswordExpiryDays && (maximumPasswordExpiryDays-pwdChangedDays)<=5)
	    		{
	    			flagType=8;
	    		}
	    		else if(pwdChangedDays==maximumPasswordExpiryDays)
	    		{
	    			flagType=9;
	    		}else if(pwdChangedDays>maximumPasswordExpiryDays)
	    		{
		    		/*
		    		 *  While loggingin check for (currentdate-passwordchangeddate)>60  then force them to change the password
		    		*/
	    			flagType=6;
	    		}
			}
		}
	    catch(Exception e)
	    {
	    	e.printStackTrace();
	    }
		return flagType;
	}
	
	 /**
	  * checkUserLoginHipaaValidation
	  * @param model
	  * @param bindingResult
	  * @param loginBean
	  * @param env
	  * @param loginService
	  * @return
	  */
	  public int checkUserLoginHipaaValidation(Model model,Object bindingResult,LoginForm loginBean,Environment env,LoginService loginService)
		{
		  	boolean flag=false;
			int flagType=0,loginattempts=0,pwdChangedDays=0;
			Date lockedupDate = null;
			long lockedupTime=0,passwordresetTime=0;
			String passwordresetDate="",memPassword="",password="",emailid="",usertype="",status="",userTable="";
			String username="";
			int userid=0;
			Date passwordResetDate=null;
			String formattedTime="";
			//System.out.println("11111111111111111====="+env.getProperty("maximumPasswordExpiryDays"));
			int maximumPasswordExpiryDays=Integer.valueOf(env.getProperty("maximumPasswordExpiryDays"));
			int accountLockupTime=Integer.valueOf(env.getProperty("accountLockupTime"));
			PhysicianAccount phyAcct=null;
			AdminAccount adminAcct=null;
			PhysicianAssistantAccount phyAssisAcct=null;
			PatientAccount patAcct=null;
			GroupDirector groupDirAcc=null;
			
			try{
				emailid=loginBean.getUserName();
				password=loginBean.getPassword();
				usertype=loginBean.getType();
				
				if(usertype!=null && usertype.length()>0)
				{
					if(usertype.equalsIgnoreCase(PharmacyUtil.userAdministrator) || usertype.equalsIgnoreCase(PharmacyUtil.userSuperAdmin) || usertype.equalsIgnoreCase(PharmacyUtil.userAdmin)){
						adminAcct=(AdminAccount)bindingResult;
						
						if(adminAcct!=null) {
							if(adminAcct.getLoginLockedDate()!=null)
			 				{
								lockedupTime=adminAcct.getLoginLockedDate().getTime();
			 					lockedupDate=adminAcct.getLoginLockedDate();
			 				}
			 				
		    			 	memPassword=adminAcct.getPassword();
		    			 	status=adminAcct.getStatus();
		    			 	userid=adminAcct.getId();
		    			 	username=adminAcct.getName();
		    			 	passwordResetDate=adminAcct.getPasswordResetDate();
		    			 	loginattempts=adminAcct.getLoginAttempts();
		    			}
					}else if(usertype.equalsIgnoreCase(PharmacyUtil.userPhysician)){
						phyAcct=(PhysicianAccount)bindingResult;
						
						if(phyAcct!=null) {
							if(phyAcct.getLoginLockedDate()!=null)
			 				{
								lockedupTime=phyAcct.getLoginLockedDate().getTime();
			 					lockedupDate=phyAcct.getLoginLockedDate();
			 				}
			 				
		    			 	memPassword=phyAcct.getPassword();
		    			 	status=phyAcct.getStatus();
		    			 	userid=phyAcct.getId();
		    			 	username=phyAcct.getPhysicianName();
		    			 	passwordResetDate=phyAcct.getPasswordResetDate();
		    			 	loginattempts=phyAcct.getLoginAttempts();
		    			}
					}else if(usertype.equalsIgnoreCase(PharmacyUtil.userPhysicianAssistant)){
						phyAssisAcct=(PhysicianAssistantAccount)bindingResult;
						
						if(phyAssisAcct!=null) {
							if(phyAssisAcct.getLoginLockedDate()!=null)
			 				{
								lockedupTime=phyAssisAcct.getLoginLockedDate().getTime();
			 					lockedupDate=phyAssisAcct.getLoginLockedDate();
			 				}
			 				
		    			 	memPassword=phyAssisAcct.getPassword();
		    			 	status=phyAssisAcct.getStatus();
		    			 	userid=phyAssisAcct.getId();
		    			 	username=phyAssisAcct.getAssistantName();
		    			 	passwordResetDate=phyAssisAcct.getPasswordResetDate();
		    			 	loginattempts=phyAssisAcct.getLoginAttempts();
		    			}
					}else if(usertype.equalsIgnoreCase(PharmacyUtil.userPatient)){
						patAcct=(PatientAccount)bindingResult;
						
						if(patAcct!=null) {
							if(patAcct.getLoginLockedDate()!=null)
			 				{
								lockedupTime=patAcct.getLoginLockedDate().getTime();
			 					lockedupDate=patAcct.getLoginLockedDate();
			 				}
			 				
		    			 	memPassword=patAcct.getPassword();
		    			 	status=patAcct.getStatus();
		    			 	userid=patAcct.getId();
		    			 	username=patAcct.getPatientName();
		    			 	passwordResetDate=patAcct.getPasswordResetDate();
		    			 	loginattempts=patAcct.getLoginAttempts();
		    			}
					} else if (usertype.equalsIgnoreCase(PharmacyUtil.userGroupDirector)) {
						groupDirAcc=(GroupDirector)bindingResult;
						
						if (groupDirAcc != null) {
							if(groupDirAcc.getLoginLockedDate()!=null) {
								lockedupTime=groupDirAcc.getLoginLockedDate().getTime();
			 					lockedupDate=groupDirAcc.getLoginLockedDate();
			 				}
			 				
		    			 	memPassword=groupDirAcc.getPassword();
		    			 	status=groupDirAcc.getStatus();
		    			 	userid=groupDirAcc.getId();
		    			 	username=groupDirAcc.getGroupDirectorName();
		    			 	passwordResetDate=groupDirAcc.getPasswordResetDate();
		    			 	loginattempts=groupDirAcc.getLoginAttempts();
						}
					}
				}
				
				
				 //Check for Account Lockout Threshold
				flagType=HIPAAPasswordRuleforLoginValidation(1,pwdChangedDays,lockedupTime,env);
				if(flagType==7)
				{
					formattedTime=PharmacyUtil.getElapsedTimeHoursMinutesFromMilliseconds(System.currentTimeMillis(),lockedupDate.getTime());
					model.addAttribute("elapsingtime",formattedTime);
				}
				//System.out.println("Account Lockout Threshold flagType ==============="+flagType);
				//Account Lockout Threshold validation fails, then go for password match
				if(flagType==0)
				{
					//System.out.println("memPassword ============"+memPassword);
		    		//System.out.println("password ============"+password);
							    		  	
		    		//If password matches with stored password
					if(password.equals(memPassword))
		    		{
						//flagType=1==>Not Approved Account
		    		  	if(status!=null && status.length()>0 && !(status.equalsIgnoreCase("New") || status.equalsIgnoreCase("Approved") 
		    		  			|| status.equalsIgnoreCase("Active") || status.equalsIgnoreCase("New Modifications")  || status.equalsIgnoreCase("Profile Completed")))
		    		  	{
		    		  		//flagType=1==>Not Approved Account
		    		  		flagType = 1;
		    		  	} else if(!checkHipaaValidation(password,username,emailid,env))
		  	 			{
		  	 				//flagType=4==>if the credentials created by the admin/physician/physician assistant-Temporary Password is set that does n't meet hipaa pwd criteria
		  	 				flagType=4;
		  	 				model.addAttribute("ResetPassword", "nothippavalidatedpassword");
		  	 			}
	 	    			else 
		  	 			{
	 	    				// durations
							Instant pwdResetInstant =null; // 2011-01-11 01:11
							if(passwordResetDate!=null){
								pwdResetInstant = Instant.ofEpochMilli(passwordResetDate.getTime());// 2011-01-11 01:11
								Duration between = Duration.between(pwdResetInstant, Instant.now());
								pwdChangedDays = Integer.valueOf(between.toDays()+"");
							}
							//System.out.println("pwdChangedDays ============"+pwdChangedDays);
			  	 			// Maximum age: All passwords must be changed at least every sixty (60) days.
			  	 			flagType=HIPAAPasswordRuleforLoginValidation(2,pwdChangedDays,lockedupTime,env);
			  	 			if(passwordResetDate!=null)
			  	 			{
			  	 				//passwordresetDate=MedCareUtil.getFormattedDate1(userList1.get(0).getPasswordresetdate()+"");
		  	 					 /* Period period = Period.between(LocalDate.ofEpochDay(passwordResetDate.getTime()),
		  	 							 LocalDate.ofEpochDay(passwordResetDate.getTime()));*/

		  	 					 Instant instant = Instant.ofEpochMilli(passwordResetDate.getTime());
		  	 					 Instant nowPlysDays=instant.plus(Duration.ofDays(maximumPasswordExpiryDays));
			  	 					 
		  	 					 LocalDateTime expiryDate = LocalDateTime.ofInstant(nowPlysDays, ZoneId.systemDefault());
								 passwordresetDate = expiryDate.format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
		  	 					 //System.out.println("passwordresetDate ========="+passwordresetDate+"======="+expiryDate.format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
		  	 				}
		  	 				//System.out.println("flagType ============="+flagType);
			  	 				
		  	 				 if(flagType==8)
		  	 				 {
		  	 					 	model.addAttribute("ResetPassword", "passwordgoingtoexpire");
		  	 					 	model.addAttribute("passwordresetDate", passwordresetDate);
		  	 				 }else if(flagType==9)
		  	 				 {
			  	 					model.addAttribute("ResetPassword", "passwordexpiring");
			  	 			 }else if(flagType==6)
		  	 				 {
			  	 					model.addAttribute("ResetPassword", "passwordexpired");
			  	 			 }
			  	 				
	  		 				 //model.addAttribute("userid", userid+"");
		  	 				 //model.addAttribute("username", username+"");
		  	 			 }
		  	    		 
		  	    		 if(flagType==0)
	  	 				 {
		  	    			 //flagType=3==>successful
		  	    			 flagType=3;
	  	 				 }
		  	 			 
		  	 			 //flagType=3==>successful==> If on successful,set the session variables
		  		 		 if(flagType==3){
		  		 			 //setUserSessionVariables
			  		    	  
		  					 //If Login on successful, reset loginattempts count,lockedup datetime
		  		 			 if(usertype.equalsIgnoreCase(PharmacyUtil.userAdministrator))
	  		    		     {
		  		 				loginService.resetLoginAttempts(userid,adminAcct.getType());
	  		    		     }else
	  		    		     {
	  		    		    	loginService.resetLoginAttempts(userid,usertype);
	  		    		     }
		  		 			 
		  		 		 }//if(flagType==3)
		  	    	  } else 
		  	    	  { 
		  	    			//If password does'nt matches, go for Account Lockout Threshold Validations
		  	    		    //flagType=0==>Invalid Email ID 
					    	flagType=0;
		  	    		 
		  	    		 /**
		  	    		  * Account Lockout Threshold: After five (5) unsuccessful attempts to enter a password, the involved user-ID will be temporarily disabled for five (5) minutes 
		  	    		  * after which the account will be automatically unlocked.
		  	    		  */
		  		    		 loginattempts++;
		  		    		 
		  		    		 if(loginattempts==6)
		  		    		 {
		  		    			  //set the lockeduptime for 5 minutes on the 6 attempt and reset the loginattempts count
		  		    			  //lockedupDate=new java.sql.Timestamp(MedCareUtil.getIntervalTimeforMinutes(accountLockupTime).getTime());
		  		    			 lockedupDate=new Date();//now
		  		    		     lockedupDate = DateUtils.addMinutes(lockedupDate, accountLockupTime);
		  		    		     loginattempts=0;
		  		    		     
		  		    			  //set the lockeduptime for 5 minutes on the 6 attempt and reset the loginattempts count
		  		    		     if(usertype.equalsIgnoreCase(PharmacyUtil.userAdministrator))
		  		    		     {
		  		    		    	 loginService.setLoginLockuptime(userid,adminAcct.getType(),lockedupDate);
		  		    		     }else
		  		    		     {
		  		    		    	 
		  		    		    	loginService.setLoginLockuptime(userid,usertype,lockedupDate);
		  		    		     }
		  		    		   
		  				     	  //Check for Account Lockout Threshold
		  						  flagType=HIPAAPasswordRuleforLoginValidation(1,pwdChangedDays,lockedupDate.getTime(),env);
		  						  
		  						
								  formattedTime=PharmacyUtil.getElapsedTimeHoursMinutesFromMilliseconds(System.currentTimeMillis(),lockedupDate.getTime());
		  						  model.addAttribute("elapsingtime",formattedTime);
		  		    		 }else{
		  		    			  //increment and update the loginattempts count on every unsuccessful password attempts 
		  		    			 if(usertype.equalsIgnoreCase(PharmacyUtil.userAdministrator))
		  		    		     {
		  		    		    	 loginService.setLoginAttemptCount(userid,adminAcct.getType(),loginattempts);
		  		    		     }else
		  		    		     {
		  		    		    	 loginService.setLoginAttemptCount(userid,usertype,loginattempts);
		  		    		     }
		  		    		 }
		  	    	  }
				 }

			}catch(Exception e)
			{
				e.printStackTrace();
			}
			return flagType;
		}
	  
	  
	  /**
	   * validateUserSecurityQuesAnswer
	   * @param model
	   * @param bindingResult
	   * @param forgotPasswordBean
	   * @param env
	   * @param loginService
	   * @return
	   */
	public int validateUserSecurityQuesAnswer(Model model, Object bindingResult, ForgotPasswordForm forgotPasswordBean, 
			Environment env, LoginService loginService) {
		boolean flag = false;
		int flagType = 0;
		int securityQuesid = 0, securityQuesid2 = 0;
		String securityAnswer = "", securityAnswer2 = "", forgotStatusFlag = "";
		String admintype = "", emailid = "", usertype = "";
		int admintypeid = 0, loginattempts = 0;

		int quesid = 0, quesid2 = 0;
		String answer = "", answer2 = "", formattedTime = "";

		Date lockedupDate = null;
		long currentTime = 0;
		long lockedupTime = 0;

		PhysicianAccount phyAcct = null;
		AdminAccount adminAcct = null;
		PhysicianAssistantAccount phyAssisAcct = null;
		PatientAccount patAcct = null;
		GroupDirector groupDirAcc = null;

		int userid = 0;
		int accountLockupTime = Integer.valueOf(env.getProperty("forgotaccountLockupTime"));

		try {
			emailid = forgotPasswordBean.getEmail();
			usertype = forgotPasswordBean.getType();
			quesid = forgotPasswordBean.getSecurityquestion();
			quesid2 = forgotPasswordBean.getSecurityquestion2();
			answer = forgotPasswordBean.getSecurityanswer();
			answer2 = forgotPasswordBean.getSecurityanswer2();

			if (usertype != null && usertype.length() > 0) {
				if (usertype.equalsIgnoreCase(PharmacyUtil.userAdministrator) || usertype.equalsIgnoreCase(PharmacyUtil.userSuperAdmin) || usertype.equalsIgnoreCase(PharmacyUtil.userAdmin)) {
					adminAcct = (AdminAccount) bindingResult;

					if (adminAcct != null) {
						userid = adminAcct.getId();
						// fetching the security question 1 and 2 from the member details
						securityQuesid = adminAcct.getSecurityQuestionNumber();
						securityAnswer = adminAcct.getSecurityAnswer();

						securityQuesid2 = adminAcct.getSecurityQuestionNumber2();
						securityAnswer2 = adminAcct.getSecurityAnswer2();

						// if forgot pwd wrong answer was given we save the time and retreive it -> locked up time
						if (adminAcct.getForgotPwdLockedDate() != null) {
							lockedupTime = adminAcct.getForgotPwdLockedDate().getTime();
						}
						loginattempts = adminAcct.getForgotPasswordAttempts();
					}
				} else if (usertype.equalsIgnoreCase(PharmacyUtil.userPhysician)) {
					phyAcct = (PhysicianAccount) bindingResult;

					if (phyAcct != null) {
						userid = phyAcct.getId();
						// fetching the security question 1 and 2 from the member details
						securityQuesid = phyAcct.getSecurityQuestionNumber();
						securityAnswer = phyAcct.getSecurityAnswer();

						securityQuesid2 = phyAcct.getSecurityQuestionNumber2();
						securityAnswer2 = phyAcct.getSecurityAnswer2();

						// if forgot pwd wrong answer was given we save the time and retreive it -> locked up time
						if (phyAcct.getForgotPwdLockedDate() != null) {
							lockedupTime = phyAcct.getForgotPwdLockedDate().getTime();
						}
						loginattempts = phyAcct.getForgotPasswordAttempts();
					}
				} else if (usertype.equalsIgnoreCase(PharmacyUtil.userPhysicianAssistant)) {
					phyAssisAcct = (PhysicianAssistantAccount) bindingResult;

					if (phyAssisAcct != null) {
						userid = phyAssisAcct.getId();
						// fetching the security question 1 and 2 from the member details
						securityQuesid = phyAssisAcct.getSecurityQuestionNumber();
						securityAnswer = phyAssisAcct.getSecurityAnswer();

						securityQuesid2 = phyAssisAcct.getSecurityQuestionNumber2();
						securityAnswer2 = phyAssisAcct.getSecurityAnswer2();

						// if forgot pwd wrong answer was given we save the time and retreive it -> locked up time
						if (phyAssisAcct.getForgotPwdLockedDate() != null) {
							lockedupTime = phyAssisAcct.getForgotPwdLockedDate().getTime();
						}
						loginattempts = phyAssisAcct.getForgotPasswordAttempts();
					}
				} else if (usertype.equalsIgnoreCase(PharmacyUtil.userPatient)) {
					patAcct = (PatientAccount) bindingResult;

					if (patAcct != null) {
						userid = patAcct.getId();
						// fetching the security question 1 and 2 from the member details
						securityQuesid = patAcct.getSecurityQuestionNumber();
						securityAnswer = patAcct.getSecurityAnswer();

						securityQuesid2 = patAcct.getSecurityQuestionNumber2();
						securityAnswer2 = patAcct.getSecurityAnswer2();

						// if forgot pwd wrong answer was given we save the time and retreive it -> locked up time
						if (patAcct.getForgotPwdLockedDate() != null) {
							lockedupTime = patAcct.getForgotPwdLockedDate().getTime();
						}
						loginattempts = patAcct.getForgotPasswordAttempts();
					}
				} else if (usertype.equalsIgnoreCase(PharmacyUtil.userGroupDirector)) {
					groupDirAcc = (GroupDirector) bindingResult;

					if (groupDirAcc != null) {
						userid = groupDirAcc.getId();

						// fetching the security question 1 and 2 from the member details
						securityQuesid = groupDirAcc.getSecurityQuestionNumber();
						securityAnswer = groupDirAcc.getSecurityAnswer();

						securityQuesid2 = groupDirAcc.getSecurityQuestionNumber2();
						securityAnswer2 = groupDirAcc.getSecurityAnswer2();

						// if forgot pwd wrong answer was given we save the time and retreive it -> locked up time
						if (groupDirAcc.getForgotPwdLockedDate() != null) {
							lockedupTime = groupDirAcc.getForgotPwdLockedDate().getTime();
						}
						loginattempts = groupDirAcc.getForgotPasswordAttempts();
					}
				}

				if (phyAcct != null || phyAssisAcct != null || adminAcct != null || patAcct != null || groupDirAcc != null) {
					// Check for Existing Account Lockout Threshold
					currentTime = System.currentTimeMillis();
					if (lockedupTime > 0 && currentTime < lockedupTime) {
						flagType = 4;	// lock out
						forgotStatusFlag = "forgotlockuptime";

						formattedTime = PharmacyUtil.getElapsedTimeHoursMinutesFromMilliseconds(System.currentTimeMillis(), lockedupTime);
						model.addAttribute("elapsingtime", formattedTime);
					} else {
						if (securityQuesid == quesid && securityAnswer.equalsIgnoreCase(answer) && securityQuesid2 == quesid2 
								&& securityAnswer2.equalsIgnoreCase(answer2)) {
							// success
							flagType = 1;
							forgotStatusFlag = "validdetails";
						} else {
							// If Security Ques/Answer does'nt matches, go for Account Lockout Threshold Validations
							if (securityQuesid != quesid) {
								forgotStatusFlag = ",invalidque1";  // Invalid security Question1
								model.addAttribute("invalidque1", "invalidque1");
							}
							if (securityQuesid2 != quesid2) {
								forgotStatusFlag += ",invalidque2";// Invalid security Question2
								model.addAttribute("invalidque2", "invalidque2");
							}
							if (!securityAnswer.equalsIgnoreCase(answer)) {
								forgotStatusFlag += ",invalidans1";// Invalid security Answer1
								model.addAttribute("invalidans1", "invalidans1");
							}
							if (!securityAnswer2.equalsIgnoreCase(answer2)) {
								forgotStatusFlag += ",invalidans2";// Invalid security Answer2
								model.addAttribute("invalidans2", "invalidans2");
							}

							if (forgotStatusFlag != null && forgotStatusFlag.length() > 0) {
								flagType = 3;
								// loginattempts=phyAcct.getForgotPasswordAttempts();
								loginattempts++;
							}
							// //System.out.println("insideeeeeeeeeeeeeeeeeeeeeeeeeee Memberdetails elseeeeeeeeeeeeee"+forgotStatusFlag);

							/**
							 * Account Lockout Threshold: After five (5)
							 * unsuccessful attempts to enter a password, the
							 * involved user-ID will be temporarily disabled for
							 * five (5) minutes after which the account will be
							 * automatically unlocked.
							 */

							// //System.out.println("loginattempts ==========="+loginattempts);
							if (loginattempts == 3) {
								lockedupDate = new Date();// now
								lockedupDate = DateUtils.addMinutes(lockedupDate, accountLockupTime); // add minute
								loginattempts = 0;

								// set the lockeduptime for 5 minutes on the 3 attempt and reset the loginattempts count
								
								 if(usertype.equalsIgnoreCase(PharmacyUtil.userAdministrator))
		  		    		     {
			  		 				loginService.setForgotPasswordLockuptime(userid, adminAcct.getType(), lockedupDate);
		  		    		     }else
		  		    		     {
		  		    		    	loginService.setForgotPasswordLockuptime(userid, usertype, lockedupDate);
		  		    		     }
								 

								// Check for Account Lockout Threshold
								currentTime = System.currentTimeMillis();
								if (currentTime < lockedupDate.getTime()) {
									forgotStatusFlag = "forgotlockuptime";
									flagType = 4;
								}

								formattedTime = PharmacyUtil.getElapsedTimeHoursMinutesFromMilliseconds(System.currentTimeMillis(), lockedupDate.getTime());
								model.addAttribute("elapsingtime", formattedTime);
								// Send Mail
							} else {
								// increment and update the loginattempts count on every unsuccessful forgot password attempts
								 if(usertype.equalsIgnoreCase(PharmacyUtil.userAdministrator))
		  		    		     {
			  		 				loginService.setForgotPasswordAttemptCount(userid, adminAcct.getType(), loginattempts);
		  		    		     }else
		  		    		     {
		  		    		    	 loginService.setForgotPasswordAttemptCount(userid, usertype, loginattempts);
		  		    		     }
								 
								 
							
							}
						}
					}
				} else
					flagType = 2;  // invalidemailid
			}
		} catch (Exception e) {
			//e.printStackTrace();
			flagType = 2;
		}
		return flagType;
	}

	//if the credentials created by the admin/physician/physician assistant-Temporary Password is set that does n't meet hipaa pwd criteria, the following hipaa password validation follows
  /**
   * isAsciiPrintable
   * @param str
   * @return {Boolean}
   */
	//For each char occurance, check for the regex validation
	public boolean isAsciiPrintable(String str) {
		try {
			if (str == null) {
				return false;
			}
			int sz = str.length();
			for (int i = 0; i < sz; i++) {
				// //System.out.println(str.charAt(i)+"==========="+isAsciiPrintable2(str.charAt(i)))
				if (!checkIsAsciiPrintable(str.charAt(i) + "")) {
					return false;
				}
			}
		} catch (Exception e) {
			//System.out.println(e);
			return true;
		}
		return true;
	}
	 
	 
   /**
    * checkIsAsciiPrintable
    * @param ch
    * @return {Boolean}
    */
	//Regex Validation for password, check for Upper case/Lower case/Number/Non-alphanumeric (~!@#$%^*&;?.+_)
	public boolean checkIsAsciiPrintable(String ch) {
		try {
			Pattern numPattern = Pattern.compile(".*[0-9].*");
			Pattern lowerAlphaPattern = Pattern.compile(".*[a-z].*");
			Pattern upperAlphaPattern = Pattern.compile(".*[A-Z].*");
			Pattern symbolsAlphaPattern = Pattern.compile(".*[~!@#$%^*&;?.+_].*");

			Matcher numPatternMatch = numPattern.matcher(ch);
			Matcher lowerAlphaPatternMatch = lowerAlphaPattern.matcher(ch);
			Matcher upperAlphaPatternMatch = upperAlphaPattern.matcher(ch);
			Matcher symbolsAlphaPatternMatch = symbolsAlphaPattern.matcher(ch);

			if (numPatternMatch.matches() || lowerAlphaPatternMatch.matches() || upperAlphaPatternMatch.matches() || symbolsAlphaPatternMatch.matches())
				return true;
			else
				return false;
		} catch (Exception e) {
			//System.out.println(e);
			return true;
		}
	}

	
   /**
    * checkSequence
    * @param oriString
    * @param strTobeMatched
    * @param seqLimit
    * @return {Boolean}
    */
	public boolean checkSequence(String oriString, String strTobeMatched, int seqLimit) {
		int countRet = -1;
		int strLen = 0;
		String strtoBeSearched = "";
		boolean alreadyExists = false;

		try {
			// //System.out.println("oriString ==="+oriString);
			/*
			 * //System.out.println("strTobeMatched ==="+strTobeMatched);
			 * //System.out.println("seqLimit ==="+seqLimit);
			 */
			strLen = strTobeMatched.length();
			for (int i = 0; i < strLen; i++) {
				if (i < strLen - (seqLimit)) {
					strtoBeSearched = strTobeMatched.substring(i, i + seqLimit + 1);
					// //System.out.println("strtoBeSearched ========"+strtoBeSearched);
					// countRet = StringUtils.countMatches(oriString, strtoBeSearched);
					countRet = oriString.indexOf(strtoBeSearched);
					// //System.out.println("countRet ======"+countRet);
					if (countRet >= 0) {
						alreadyExists = true;
						break;
					}
				}
			}
		} catch (Exception e) {
			//System.out.println(e);
		}
		return alreadyExists;
	}
	   
	
  /**
   * getPasswordCategoryCount
   * @param password
   * @return {int}
   */
	public int getPasswordCategoryCount(String password) {
		int count = 0;

		try {
			Pattern numPattern = Pattern.compile(".*[0-9].*");
			Pattern lowerAlphaPattern = Pattern.compile(".*[a-z].*");
			Pattern upperAlphaPattern = Pattern.compile(".*[A-Z].*");
			Pattern symbolsAlphaPattern = Pattern.compile(".*[~!@#$%^*&;?.+_].*");

			Matcher numPatternMatch = numPattern.matcher(password);
			Matcher lowerAlphaPatternMatch = lowerAlphaPattern.matcher(password);
			Matcher upperAlphaPatternMatch = upperAlphaPattern.matcher(password);
			Matcher symbolsAlphaPatternMatch = symbolsAlphaPattern.matcher(password);

			/*
			 * //System.out.println(numPatternMatch.matches());
			 * //System.out.println(lowerAlphaPatternMatch.matches());
			 * //System.out.println(upperAlphaPatternMatch.matches());
			 * //System.out.println(symbolsAlphaPatternMatch.matches());
			 */

			if (numPatternMatch.matches()) {
				// "Password must contain at least one number (0-9)! <br>";
				count++;
			}

			if (lowerAlphaPatternMatch.matches()) {
				// "Password must contain at least one lowercase letter (a-z)! <br>";
				count++;
			}
			if (upperAlphaPatternMatch.matches()) {
				// "Password must contain at least one uppercase letter (A-Z)! <br>";
				count++;
			}

			if (symbolsAlphaPatternMatch.matches()) {
				// "Password must contain at least Non-alphanumeric (~!@#$%^*&;?.+_)! <br>";
				count++;
			}
		} catch (Exception e) {
			//System.out.println(e);
		}
		return count;
	}

	
  /**
   * checkHipaaValidation
   * @param password
   * @param fullname
   * @param accountname
   * @param env
   * @return {Boolean}
   */
	public boolean checkHipaaValidation(String password, String fullname, String accountname, Environment env) {
		boolean hipaaValidateLogin = true;
		int passwordminlength = Integer.valueOf(env.getProperty("password.minlength"));
		int passwordmaxlength = Integer.valueOf(env.getProperty("password.maxlength"));
		/*int passwordminlength = 8;
		int passwordmaxlength = 25;*/
		try {
			/*if (!isAsciiPrintable(password)) {
				hipaaValidateLogin = false;
			} else if (password.toLowerCase().indexOf("temp")==0) {
				hipaaValidateLogin = false;
			}else if (password.length() < passwordminlength || password.length() > passwordmaxlength) {
				hipaaValidateLogin = false;
			} else if (getPasswordCategoryCount(password) < 3) {
				hipaaValidateLogin = false;
			} else {
				String newpassword = password;
				newpassword = newpassword.toLowerCase();
				if (checkSequence(fullname, newpassword, 2) || checkSequence(accountname, newpassword, 2)) {
					hipaaValidateLogin = false;
				}
			}*/
			String passwordPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{6,}$";
			Pattern pattern = Pattern.compile(passwordPattern);
			Matcher matcher = pattern.matcher(password);
			hipaaValidateLogin =  matcher.matches();

		} catch (Exception e) {
			//System.out.println(e);
		}
		return hipaaValidateLogin;
	}  
	
	/**
	//Test Code Snippet
	public static void main(String[] a)
	{
		boolean flg=new HipaaPasswordValidator().checkHipaaValidation("Welcome123456","Test1 Test2", "superadmin@gmail.com", null);
		
		//System.out.println("flg ==========="+flg);
	}
	*/
}
