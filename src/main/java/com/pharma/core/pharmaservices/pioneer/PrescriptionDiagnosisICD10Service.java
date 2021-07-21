package com.pharma.core.pharmaservices.pioneer;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pharma.core.model.pioneer.PrescriptionDiagnosisICD10;

@Service
public interface PrescriptionDiagnosisICD10Service {
	
	
	List<PrescriptionDiagnosisICD10> getAutoCompleteDiagnosisICD10List(String term,String icd10code);
	
	List<PrescriptionDiagnosisICD10> getAllAutoCompleteDiagnosisICD10List(String term);
}
