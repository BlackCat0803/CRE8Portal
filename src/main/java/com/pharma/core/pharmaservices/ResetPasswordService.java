package com.pharma.core.pharmaservices;

import org.springframework.stereotype.Service;

import com.pharma.core.formBean.LoginForm;
import com.pharma.core.formBean.ResetPasswordForm;



@Service
public interface ResetPasswordService {
		
	LoginForm saveResetFormDetails(ResetPasswordForm resetPasswordForm);
	
	ResetPasswordForm saveNewPasswordDetails(ResetPasswordForm resetPasswordForm);
	
	ResetPasswordForm saveSecurityQuestionDetails(ResetPasswordForm resetPasswordForm);
}
