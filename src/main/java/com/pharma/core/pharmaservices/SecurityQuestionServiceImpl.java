package com.pharma.core.pharmaservices;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pharma.core.model.SecurityQuestion;
import com.pharma.core.repository.SecurityQuestionRepository;

/**
 * This is an implementation class that loads the security questions
 *
 */
@Service("securityQuestionService")
public class SecurityQuestionServiceImpl implements SecurityQuestionService {

	@Autowired
	SecurityQuestionRepository securityQuestionRepo;
	
	public List<SecurityQuestion> getAllSecurityQuestions() {
		return securityQuestionRepo.findAll();
	}

	
		
}
