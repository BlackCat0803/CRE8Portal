package com.pharma.core.pharmaservices.pioneer;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pharma.core.model.pioneer.PrescriptionMedication;

@Service
public interface PrescriptionMedicationService {
	
	List<PrescriptionMedication> getAutoCompletePrescriptionMedicationList(String term,int id,int typeId);
	
	List<PrescriptionMedication> getAllAutoCompletePrescriptionMedicationList(String term);

	List<PrescriptionMedication> getAutoCompletePrescriptionMedicationsList(String term,int id);

	List<PrescriptionMedication> getAllAutoCompletePrescriptionMedicationsList();

	List saveMedication(int medTypeId, String medicationDesc);
}
