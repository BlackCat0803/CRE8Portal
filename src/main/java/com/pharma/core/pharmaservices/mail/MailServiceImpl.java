package com.pharma.core.pharmaservices.mail;

import java.io.File;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import freemarker.template.Configuration;
/**
 * This is an implementation class that configures the mail content and send the mails
 */
@Service("mailServices")
public class MailServiceImpl implements MailService {

	@Autowired
    JavaMailSender mailSender;
 
    @Autowired
    Configuration fmConfiguration;
    
    public void sendEmail(Mail mail, String mailType, String fileName, String attachmentFileName) {
        
        try {
        	MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
 
            mimeMessageHelper.setSubject(mail.getMailSubject());
            mimeMessageHelper.setFrom(mail.getMailFrom());
            
            mimeMessageHelper.setTo(InternetAddress.parse(mail.getMailTo()));
            mimeMessageHelper.setCc(InternetAddress.parse(mail.getMailCc()));
            mimeMessageHelper.setBcc(InternetAddress.parse(mail.getMailBcc()));

            mail.setMailContent(geContentFromTemplate(mail.getModel(), mailType));
           	mimeMessageHelper.setText(mail.getMailContent(), true);
           	
           	if (fileName != null && !"".equalsIgnoreCase(fileName.trim())) {
               	FileSystemResource file = new FileSystemResource(new File(fileName));
               	mimeMessageHelper.addAttachment(attachmentFileName, file);
           	}

            mailSender.send(mimeMessageHelper.getMimeMessage());
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
 
    public String geContentFromTemplate(Map < String, Object > model, String mailType) {
        StringBuffer content = new StringBuffer();
 
        try {
        	if ("toPhysicianMail".equalsIgnoreCase(mailType) )
        		content.append(FreeMarkerTemplateUtils.processTemplateIntoString(fmConfiguration.getTemplate("physicianSignup.txt"), model));
        	else if ("PhysicianSignuptoAdmin".equalsIgnoreCase(mailType))
        		content.append(FreeMarkerTemplateUtils.processTemplateIntoString(fmConfiguration.getTemplate("physicianSignupToAdmin.txt"), model));
        	else if ("AdminForPhysicianSignup".equalsIgnoreCase(mailType))
        		content.append(FreeMarkerTemplateUtils.processTemplateIntoString(fmConfiguration.getTemplate("adminPhysicianSignup.txt"), model));
        	else if ("AdminSignup".equalsIgnoreCase(mailType))
        		content.append(FreeMarkerTemplateUtils.processTemplateIntoString(fmConfiguration.getTemplate("adminRegistration.txt"), model));
        	else if ("AssistantSignup".equalsIgnoreCase(mailType))
        		content.append(FreeMarkerTemplateUtils.processTemplateIntoString(fmConfiguration.getTemplate("assistantRegistration.txt"), model));
        	else if ("DirectorSignup".equalsIgnoreCase(mailType))
        		content.append(FreeMarkerTemplateUtils.processTemplateIntoString(fmConfiguration.getTemplate("directorRegistration.txt"), model));
        	else if ("PatientSignup".equalsIgnoreCase(mailType))
        		content.append(FreeMarkerTemplateUtils.processTemplateIntoString(fmConfiguration.getTemplate("patientRegistration.txt"), model));
        	else if ("PhysicianApproval".equalsIgnoreCase(mailType))
        		content.append(FreeMarkerTemplateUtils.processTemplateIntoString(fmConfiguration.getTemplate("physicianApprovalMail.txt"), model));
        	else if ("PatientApproval".equalsIgnoreCase(mailType))
        		content.append(FreeMarkerTemplateUtils.processTemplateIntoString(fmConfiguration.getTemplate("patientApprovalMail.txt"), model));
        	else if ("toPhysicianDocExpiryMail".equalsIgnoreCase(mailType))
        		content.append(FreeMarkerTemplateUtils.processTemplateIntoString(fmConfiguration.getTemplate("physicianDocExpiryMail.txt"), model));
        	else
        		content.append(FreeMarkerTemplateUtils.processTemplateIntoString(fmConfiguration.getTemplate("email-template.txt"), model));
        	
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content.toString();
    }
    
}
