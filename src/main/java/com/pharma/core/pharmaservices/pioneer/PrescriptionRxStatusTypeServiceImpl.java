package com.pharma.core.pharmaservices.pioneer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pharma.core.model.pioneer.PatientSyncStatusType;
import com.pharma.core.model.pioneer.PrescriptionRxStatusType;
import com.pharma.core.repository.pioneer.PatientSyncStatusTypeRepository;
import com.pharma.core.repository.pioneer.PrescriptionRxStatusTypeRepository;

/**
 * This is an implementation class that loads the pioneer related data
 */
@Service("prescriptionRxStatusTypeService")
public class PrescriptionRxStatusTypeServiceImpl implements PrescriptionRxStatusTypeService {

	@Autowired
	PrescriptionRxStatusTypeRepository  prescriptionRxStatusTypeRepo;
	
	public List<PrescriptionRxStatusType> getAllPrescriptionRxStatusType() {
		try {
			return prescriptionRxStatusTypeRepo.findAll();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return null;
	}

	
		
}
