package com.pharma.core.pharmaservices.pioneer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.pharma.core.model.pioneer.PrescriptionMedication;
import com.pharma.core.repository.pioneer.PrescriptionMedicationRepository;

/**
 * This is an implementation class that loads the pioneer related data
 *
 */
@Service("prescriptionMedicationService")
@PropertySource("classpath:webService.properties")
public class PrescriptionMedicationServiceImpl implements PrescriptionMedicationService {

	@Autowired
	PrescriptionMedicationRepository prescriptionMedicationRepo;
	
	@Autowired
	private Environment env;
	
	public List<PrescriptionMedication> getAutoCompletePrescriptionMedicationList(String term,int id,int typeId) {
		int MaxResults=Integer.parseInt(env.getProperty("MaxResults"));
		List<PrescriptionMedication> medicationObj = prescriptionMedicationRepo.getAutoCompletePrescriptionMedicationList(term,id,typeId, new PageRequest(0, MaxResults));
		return medicationObj;
		
	
	}

	
	
	public List<PrescriptionMedication> getAllAutoCompletePrescriptionMedicationList(String term) {
		int MaxResults=Integer.parseInt(env.getProperty("MaxResults"));
		List<PrescriptionMedication> medicationObj = prescriptionMedicationRepo.getAllAutoCompletePrescriptionMedicationList(term, new PageRequest(0, MaxResults));
		return medicationObj;
	}
	
	public List<PrescriptionMedication> getAutoCompletePrescriptionMedicationsList(String term,int id) {
		int MaxResults=Integer.parseInt(env.getProperty("MaxResults"));
		List<PrescriptionMedication> medicationObj = prescriptionMedicationRepo.getAutoCompletePrescriptionMedicationsList(term,id, new PageRequest(0, MaxResults));
		return medicationObj;
		
	
	}

	public List<PrescriptionMedication> getAllAutoCompletePrescriptionMedicationsList() {
		int MaxResults=Integer.parseInt(env.getProperty("MaxResults"));
		List<PrescriptionMedication> medicationObj = prescriptionMedicationRepo.getAllAutoCompletePrescriptionMedicationsList();
		return medicationObj;
	}

		
}
