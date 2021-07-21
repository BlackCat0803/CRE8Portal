package com.pharma.core.pharmaservices.mail;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.core.env.Environment;

import com.pharma.core.formBean.DocumentFileList;
import com.pharma.core.formBean.physician.PhysicianProfile;
import com.pharma.core.model.admin.AdminAccount;
import com.pharma.core.model.patientaccount.PatientAccount;
import com.pharma.core.model.pharmacygroup.GroupDirector;
import com.pharma.core.model.physician.PhysicianAccount;
import com.pharma.core.model.physician.PhysicianAssistantAccount;
/**
 * This is an mail config class for sending mails
 */
public class MailSendConfig {
	
	public void MailSending(Object obj, Environment env, String targetMail, String currentUrl, String fileName, String attachmentFileName) {
		
        AbstractApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        MailService mailService = (MailService) context.getBean("mailServices");

        Mail mail = new Mail();

        if ("toPhysicianMail".equalsIgnoreCase(targetMail)) {
			mail = setPhysicianSignUpMail(mail, obj, env, currentUrl );
			mailService.sendEmail(mail, targetMail, fileName, attachmentFileName);
		} else if ("PhysicianSignuptoAdmin".equalsIgnoreCase(targetMail)) {
			mail = setPhysicianSignUpToAdminMail(mail, obj, env, currentUrl );
			mailService.sendEmail(mail, targetMail, fileName, attachmentFileName);
		} else if ("AdminForPhysicianSignup".equalsIgnoreCase(targetMail)) {
			mail = setAdminSignedForPhysicianMail(mail, obj, env, currentUrl );
			mailService.sendEmail(mail, targetMail, fileName, attachmentFileName);
		} else if ("AdminSignup".equalsIgnoreCase(targetMail)) {
			mail = setAdminSignUpMail(mail, obj, env, currentUrl );
			mailService.sendEmail(mail, targetMail, fileName, attachmentFileName);
		} else if ("AssistantSignup".equalsIgnoreCase(targetMail)) {
			mail = setAssistantSignUpMail(mail, obj, env, currentUrl );
			mailService.sendEmail(mail, targetMail, fileName, attachmentFileName);
		} else if ("DirectorSignup".equalsIgnoreCase(targetMail)) {
			mail = setDirectorSignUpMail(mail, obj, env, currentUrl );
			mailService.sendEmail(mail, targetMail, fileName, attachmentFileName);
		} else if ("PatientSignup".equalsIgnoreCase(targetMail)) {
			mail = setPatientSignUpMail(mail, obj, env, currentUrl );
			mailService.sendEmail(mail, targetMail, fileName, attachmentFileName);
		} else if ("PhysicianApproval".equalsIgnoreCase(targetMail)) {
			mail = setPhysicianApprovalMail(mail, obj, env, currentUrl );
			mailService.sendEmail(mail, targetMail, fileName, attachmentFileName);
		} else if ("PatientApproval".equalsIgnoreCase(targetMail)) {
			mail = setPatientApprovalMail(mail, obj, env, currentUrl );
			mailService.sendEmail(mail, targetMail, fileName, attachmentFileName);
		}

        context.close();
	}
	
	
	public void physicianDocExpiryMail(Object obj, Environment env, String targetMail, List<DocumentFileList> phyAssObjList ) {
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        MailService mailService = (MailService) context.getBean("mailServices");

        Mail mail = new Mail();
        mail = setPhysicianLicenseDocExpiryMail(obj, env, mail, phyAssObjList);
		mailService.sendEmail(mail, targetMail, null, null);
		
		context.close();
	}
	
	
	
	// Send to Physician at the time of Signup (Physician Signup)
	private Mail setPhysicianSignUpMail(Mail mail, Object obj, Environment env, String currentUrl) {
		PhysicianProfile profile = (PhysicianProfile) obj;
		
		mail.setMailSubject(env.getProperty("phy_phy_subject"));
		mail.setMailFrom(env.getProperty("test_phy_phy_from"));
		
		if ("true".equalsIgnoreCase(env.getProperty("isDevelopmentEnv"))) {
			mail.setMailTo(env.getProperty("development_to_address"));
		} else if ("true".equalsIgnoreCase(env.getProperty("isTestEnv"))) {
			mail.setMailTo(env.getProperty("test_phy_phy_to"));
			mail.setMailCc(env.getProperty("test_phy_phy_cc"));
			mail.setMailBcc(env.getProperty("test_phy_phy_bcc"));
		} else {
			mail.setMailFrom(env.getProperty("live_phy_phy_from"));
			mail.setMailTo(profile.getEmail()+", "+env.getProperty("live_phy_phy_to"));
			mail.setMailCc(env.getProperty("live_phy_phy_cc"));
			mail.setMailBcc(env.getProperty("live_phy_phy_bcc"));
		}

        Map < String, Object > model = new HashMap < String, Object > ();
        model.put("physicianName", profile.getPhysicianName());
        model.put("email", profile.getEmail());
        model.put("password", profile.getPassword());
        model.put("registerUrl", currentUrl);
        mail.setModel(model);
        
		return mail;
	}
	
	// Sending mail to admin about physician signup (physician own signup)
	private Mail setPhysicianSignUpToAdminMail(Mail mail, Object obj, Environment env, String currentUrl) {
		PhysicianProfile profile = (PhysicianProfile) obj;

		mail.setMailSubject(env.getProperty("phy_admin_subject"));
		mail.setMailFrom(env.getProperty("test_phy_admin_from"));
		
		if ("true".equalsIgnoreCase(env.getProperty("isDevelopmentEnv"))) {
			mail.setMailTo(env.getProperty("development_to_address"));
		} else if ("true".equalsIgnoreCase(env.getProperty("isTestEnv"))) {
			mail.setMailTo(env.getProperty("test_phy_admin_to"));
			mail.setMailCc(env.getProperty("test_phy_admin_cc"));
			mail.setMailBcc(env.getProperty("test_phy_admin_bcc"));
		} else {
			mail.setMailFrom(env.getProperty("live_phy_admin_from"));
			mail.setMailTo(profile.getEmail()+", "+env.getProperty("live_phy_admin_to"));
			mail.setMailCc(env.getProperty("live_phy_admin_cc"));
			mail.setMailBcc(env.getProperty("live_phy_admin_bcc"));
		}
		
        Map < String, Object > model = new HashMap < String, Object > ();
        model.put("physicianName", profile.getPhysicianName());
        model.put("email", profile.getEmail());
        model.put("phone", profile.getPhone());
        model.put("mobile", profile.getMobile());
        model.put("city", profile.getCity());
        model.put("state", profile.getState());
        model.put("registerUrl", currentUrl);
        model.put("adminName", env.getProperty("to_admin_name"));
        
        mail.setModel(model);
        
		return mail;
	}
	
	// Re-Approval for Physician mail to Administrator 
	private Mail setPhysicianApprovalMail(Mail mail, Object obj, Environment env, String currentUrl) {
		PhysicianProfile profile = (PhysicianProfile) obj;

		mail.setMailSubject(env.getProperty("phy_approval_subject"));
		mail.setMailFrom(env.getProperty("test_phy_approval_from"));
		
		if ("true".equalsIgnoreCase(env.getProperty("isDevelopmentEnv"))) {
			mail.setMailTo(env.getProperty("development_to_address"));
		} else if ("true".equalsIgnoreCase(env.getProperty("isTestEnv"))) {
			mail.setMailTo(env.getProperty("test_phy_approval_to"));
			mail.setMailCc(env.getProperty("test_phy_approval_cc"));
			mail.setMailBcc(env.getProperty("test_phy_approval_bcc"));
		} else {
			mail.setMailFrom(env.getProperty("live_phy_approval_from"));
			mail.setMailTo(profile.getEmail()+", "+env.getProperty("live_phy_approval_to"));
			mail.setMailCc(env.getProperty("live_phy_approval_cc"));
			mail.setMailBcc(env.getProperty("live_phy_approval_bcc"));
		}
		
        Map < String, Object > model = new HashMap < String, Object > ();
        model.put("physicianName", profile.getPhysicianName());
        model.put("email", profile.getEmail());
        model.put("phone", profile.getPhone());
        model.put("mobile", profile.getMobile());
        model.put("city", profile.getCity());
        model.put("state", profile.getState());
        model.put("registerUrl", currentUrl);
        model.put("Dea", profile.getDea());
        model.put("adminName", env.getProperty("to_admin_name"));
        
        mail.setModel(model);
        
		return mail;
	}
	
	
	// Send to Physician (admin / super admin / group director create account for physician)
	private Mail setAdminSignedForPhysicianMail(Mail mail, Object obj, Environment env, String currentUrl) {
		PhysicianProfile profile = (PhysicianProfile) obj;

		mail.setMailSubject(env.getProperty("admin_phy_subject"));
		mail.setMailFrom(env.getProperty("test_admin_phy_from"));
		
		String tmpString="";
		
		if ("true".equalsIgnoreCase(env.getProperty("isDevelopmentEnv"))) {
			mail.setMailTo(env.getProperty("development_to_address"));
		} else if ("true".equalsIgnoreCase(env.getProperty("isTestEnv"))) {
			tmpString = env.getProperty("test_admin_phy_to");
			mail.setMailTo(tmpString);
			mail.setMailCc(env.getProperty("test_admin_phy_cc"));
			mail.setMailBcc(env.getProperty("test_admin_phy_bcc"));
		} else {
			tmpString = profile.getEmail()+", "+env.getProperty("live_admin_phy_to");
			mail.setMailFrom(env.getProperty("live_admin_phy_from"));
			mail.setMailTo(tmpString);
			mail.setMailCc(env.getProperty("live_admin_phy_cc"));
			mail.setMailBcc(env.getProperty("live_admin_phy_bcc"));
		}

		Map < String, Object > model = new HashMap < String, Object > ();
        model.put("physicianName", profile.getPhysicianName());
        model.put("email", profile.getEmail());
        model.put("password", profile.getPassword());
        model.put("registerUrl", currentUrl);
        mail.setModel(model);
        
		return mail;
	}
	
	
	// Creating new Admin User 
	private Mail setAdminSignUpMail(Mail mail, Object obj, Environment env, String currentUrl) {
		AdminAccount form = (AdminAccount) obj;
		
		mail.setMailSubject(env.getProperty("admin_subject"));
		mail.setMailFrom(env.getProperty("test_admin_from"));
		
		String tmpString="";
		
		if ("true".equalsIgnoreCase(env.getProperty("isDevelopmentEnv"))) {
			mail.setMailTo(env.getProperty("development_to_address"));
		} else if ("true".equalsIgnoreCase(env.getProperty("isTestEnv"))) {
			tmpString = env.getProperty("test_admin_to");
			mail.setMailTo(tmpString);
			mail.setMailCc(env.getProperty("test_admin_cc"));
			mail.setMailBcc(env.getProperty("test_admin_bcc"));
		} else {
			tmpString = form.getEmail()+", "+env.getProperty("live_admin_to");
			mail.setMailFrom(env.getProperty("live_admin_from"));
			mail.setMailTo(tmpString);
			mail.setMailCc(env.getProperty("live_admin_cc"));
			mail.setMailBcc(env.getProperty("live_admin_bcc"));
		}

		Map < String, Object > model = new HashMap < String, Object > ();
        model.put("adminName", form.getName());
        model.put("email", form.getEmail());
        model.put("password", form.getPassword());
        model.put("registerUrl", currentUrl);
        mail.setModel(model);
        
		return mail;
	}
	
	private Mail setAssistantSignUpMail(Mail mail, Object obj, Environment env, String currentUrl) {
		PhysicianAssistantAccount form = (PhysicianAssistantAccount) obj;
		
		mail.setMailSubject(env.getProperty("assistant_subject"));
		mail.setMailFrom(env.getProperty("test_assistant_from"));
		
		String tmpString="";
		
		if ("true".equalsIgnoreCase(env.getProperty("isDevelopmentEnv"))) {
			mail.setMailTo(env.getProperty("development_to_address"));
		} else if ("true".equalsIgnoreCase(env.getProperty("isTestEnv"))) {
			tmpString = env.getProperty("test_assistant_to");
			mail.setMailTo(tmpString);
			mail.setMailCc(env.getProperty("test_assistant_cc"));
			mail.setMailBcc(env.getProperty("test_assistant_bcc"));
		} else {
			tmpString = form.getEmail()+", "+env.getProperty("live_assistant_to");
			mail.setMailFrom(env.getProperty("live_assistant_from"));
			mail.setMailTo(tmpString);
			mail.setMailCc(env.getProperty("live_assistant_cc"));
			mail.setMailBcc(env.getProperty("live_assistant_bcc"));
		}

		Map < String, Object > model = new HashMap < String, Object > ();
        model.put("assistantName", form.getAssistantName());
        model.put("email", form.getEmail());
        model.put("password", form.getPassword());
        model.put("registerUrl", currentUrl);
        mail.setModel(model);
        
		return mail;
	}
	
	
	private Mail setDirectorSignUpMail(Mail mail, Object obj, Environment env, String currentUrl) {
		GroupDirector form = (GroupDirector) obj;
		
		mail.setMailSubject(env.getProperty("director_subject"));
		mail.setMailFrom(env.getProperty("test_director_from"));
		
		String tmpString="";
		
		if ("true".equalsIgnoreCase(env.getProperty("isDevelopmentEnv"))) {
			mail.setMailTo(env.getProperty("development_to_address"));
		} else if ("true".equalsIgnoreCase(env.getProperty("isTestEnv"))) {
			tmpString = env.getProperty("test_director_to");
			mail.setMailTo(tmpString);
			mail.setMailCc(env.getProperty("test_director_cc"));
			mail.setMailBcc(env.getProperty("test_director_bcc"));
		} else {
			tmpString = form.getEmail()+", "+env.getProperty("live_director_to");
			mail.setMailFrom(env.getProperty("live_director_from"));
			mail.setMailTo(tmpString);
			mail.setMailCc(env.getProperty("live_director_cc"));
			mail.setMailBcc(env.getProperty("live_director_bcc"));
		}

		Map < String, Object > model = new HashMap < String, Object > ();
        model.put("userName", form.getGroupDirectorName());
        model.put("email", form.getEmail());
        model.put("password", form.getPassword());
        model.put("registerUrl", currentUrl);
        mail.setModel(model);
        
		return mail;
	}

	
	private Mail setPatientSignUpMail(Mail mail, Object obj, Environment env, String currentUrl) {
		PatientAccount form = (PatientAccount) obj;
		
		mail.setMailSubject(env.getProperty("patient_subject"));
		mail.setMailFrom(env.getProperty("test_patient_from"));
		
		String tmpString="";
		
		if ("true".equalsIgnoreCase(env.getProperty("isDevelopmentEnv"))) {
			mail.setMailTo(env.getProperty("development_to_address"));
		} else if ("true".equalsIgnoreCase(env.getProperty("isTestEnv"))) {
			tmpString = env.getProperty("test_patient_to");
			mail.setMailTo(tmpString);
			mail.setMailCc(env.getProperty("test_patient_cc"));
			mail.setMailBcc(env.getProperty("test_patient_bcc"));
		} else {
			tmpString = form.getEmail()+", "+env.getProperty("live_patient_to");
			mail.setMailFrom(env.getProperty("live_patient_from"));
			mail.setMailTo(tmpString);
			mail.setMailCc(env.getProperty("live_patient_cc"));
			mail.setMailBcc(env.getProperty("live_patient_bcc"));
		}

		Map < String, Object > model = new HashMap < String, Object > ();
        model.put("userName", form.getPatientName());
        model.put("email", form.getEmail());
        model.put("password", form.getPassword());
        model.put("registerUrl", currentUrl);
        mail.setModel(model);
        
		return mail;
	}
	
	// Re-Approval for Physician mail to Administrator 
		private Mail setPatientApprovalMail(Mail mail, Object obj, Environment env, String currentUrl) {
			PatientAccount profile = (PatientAccount) obj;

			mail.setMailSubject(env.getProperty("patient_approval_subject"));
			mail.setMailFrom(env.getProperty("test_patient_approval_from"));
			
			if ("true".equalsIgnoreCase(env.getProperty("isDevelopmentEnv"))) {
				mail.setMailTo(env.getProperty("development_to_address"));
			} else if ("true".equalsIgnoreCase(env.getProperty("isTestEnv"))) {
				mail.setMailTo(env.getProperty("test_patient_approval_to"));
				mail.setMailCc(env.getProperty("test_patient_approval_cc"));
				mail.setMailBcc(env.getProperty("test_patient_approval_bcc"));
			} else {
				mail.setMailFrom(env.getProperty("live_patient_approval_from"));
				mail.setMailTo(profile.getEmail()+", "+env.getProperty("live_patient_approval_to"));
				mail.setMailCc(env.getProperty("live_patient_approval_cc"));
				mail.setMailBcc(env.getProperty("live_patient_approval_bcc"));
			}
			
	        Map < String, Object > model = new HashMap < String, Object > ();
	        model.put("patientName", profile.getPatientName());
	        model.put("email", profile.getEmail());
	        model.put("SSN", profile.getSsn());
	        model.put("userId", profile.getUserLoginId());
	        model.put("mobile", profile.getMobile());
	        model.put("city", profile.getCity());
	        model.put("state", profile.getState());
	        model.put("registerUrl", currentUrl);
	        model.put("adminName", env.getProperty("to_admin_name"));
	        
	        mail.setModel(model);
	        
			return mail;
		}
		
		
		private Mail setPhysicianLicenseDocExpiryMail(Object obj, Environment env, Mail mail, List<DocumentFileList> phyAssObjList) {
			PhysicianAccount p = (PhysicianAccount) obj;
			
			mail.setMailSubject(env.getProperty("phy_exp_doc_subject"));
			mail.setMailFrom(env.getProperty("test_phy_exp_doc_from"));
			
			if ("true".equalsIgnoreCase(env.getProperty("isDevelopmentEnv"))) {
				mail.setMailTo(env.getProperty("development_to_address"));
			} else if ("true".equalsIgnoreCase(env.getProperty("isTestEnv"))) {
				mail.setMailTo(env.getProperty("test_phy_exp_doc_to"));
				mail.setMailCc(env.getProperty("test_phy_exp_doc_cc"));
				mail.setMailBcc(env.getProperty("test_phy_exp_doc_bcc"));
			} else {
				mail.setMailFrom(env.getProperty("live_phy_exp_doc_from"));
				mail.setMailTo(p.getEmail()+", "+env.getProperty("live_phy_exp_doc_to"));
				mail.setMailCc(env.getProperty("live_phy_exp_doc_cc"));
				mail.setMailBcc(env.getProperty("live_phy_exp_doc_bcc"));
			}
			
			
	        Map < String, Object > model = new HashMap < String, Object > ();
	        model.put("physicianName", p.getPhysicianName());
	        model.put("email", p.getEmail());
	        model.put("docList", phyAssObjList);

	        mail.setModel(model);
	        
			return mail;
		}		
		
}
