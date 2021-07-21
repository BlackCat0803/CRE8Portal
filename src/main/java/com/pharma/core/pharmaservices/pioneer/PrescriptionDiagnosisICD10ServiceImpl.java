package com.pharma.core.pharmaservices.pioneer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.pharma.core.model.pioneer.PrescriptionDiagnosisICD10;
import com.pharma.core.repository.pioneer.PrescriptionDiagnosisICD10Repository;
/**
 * This is an implementation class that loads the pioneer related data
 *
 */

@Service("prescriptionDiagnosisICD10Service")
@PropertySource("classpath:webService.properties")
public class PrescriptionDiagnosisICD10ServiceImpl implements PrescriptionDiagnosisICD10Service {

	@Autowired
	PrescriptionDiagnosisICD10Repository prescriptionDiagnosisICD10Repo;
	
	@Autowired
	private Environment env;
	
	public List<PrescriptionDiagnosisICD10> getAutoCompleteDiagnosisICD10List(String term, String icd10code) {
		
		int MaxResults=Integer.parseInt(env.getProperty("MaxResults"));
		List<PrescriptionDiagnosisICD10> icd10Obj = prescriptionDiagnosisICD10Repo.getAutoCompleteDiagnosisICD10List(term,icd10code, new PageRequest(0, MaxResults));
		return icd10Obj;
		
	
	}

	public List<PrescriptionDiagnosisICD10> getAllAutoCompleteDiagnosisICD10List(String term) {

		int MaxResults=Integer.parseInt(env.getProperty("MaxResults"));
		List<PrescriptionDiagnosisICD10> icd10Obj = prescriptionDiagnosisICD10Repo.getAllAutoCompleteDiagnosisICD10List(term, new PageRequest(0, MaxResults));
		return icd10Obj;
	}

	
		
}
