package com.pharma.core.pharmaservices.pioneer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pharma.core.model.pioneer.PatientSyncStatusType;
import com.pharma.core.repository.pioneer.PatientSyncStatusTypeRepository;

/**
 * This is an implementation class that loads the pioneer related data
 *
 */
@Service("patientSyncStatusTypeService")
public class PatientSyncStatusTypeServiceImpl implements PatientSyncStatusTypeService {

	@Autowired
	PatientSyncStatusTypeRepository patientSyncStatusTypeRepo;
	
	public List<PatientSyncStatusType> getAllPatientSyncStatusType() {
		try {
			return patientSyncStatusTypeRepo.findAll();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return null;
	}

	
		
}
