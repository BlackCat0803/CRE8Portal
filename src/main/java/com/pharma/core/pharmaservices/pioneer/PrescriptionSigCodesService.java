package com.pharma.core.pharmaservices.pioneer;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pharma.core.model.pioneer.PrescriptionSigCodes;

@Service
public interface PrescriptionSigCodesService {
	
	List<PrescriptionSigCodes> getAutoCompletePrescriptionSigList(String term,String sigid);
	
	List<PrescriptionSigCodes> getAllAutoCompletePrescriptionSigList(String term);
}
