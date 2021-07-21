package com.pharma.core.pharmaservices;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pharma.core.model.SecurityQuestion;

@Service
public interface SecurityQuestionService {
	
	List<SecurityQuestion> getAllSecurityQuestions();
}
