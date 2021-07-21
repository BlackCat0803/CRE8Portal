package com.pharma.core.pharmaservices.mail;

import org.springframework.stereotype.Service;

@Service
public interface MailService {
	 public void sendEmail(Mail mail, String mailType, String fileName, String attachmentFileName);
}
