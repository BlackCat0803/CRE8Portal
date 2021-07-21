package com.pharma.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pharma.core.model.SecurityQuestion;
/**
 * This class is a Java interface that defines a data access contract
 *
 */
@Repository("securityQuestion")
public interface SecurityQuestionRepository extends JpaRepository<SecurityQuestion, String> {
	

}
