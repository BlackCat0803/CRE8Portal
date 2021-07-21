package com.pharma.core.pharmaservices.pioneer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pharma.core.model.pioneer.PatientRxNotifyProviderType;
import com.pharma.core.repository.pioneer.PatientRxNotifyProviderTypeRepository;

/**
 * This is an implementation class that loads the pioneer related data
 */
@Service("patientRxNotifyProviderTypeService")
public class PatientRxNotifyProviderTypeServiceImpl implements PatientRxNotifyProviderTypeService {

	@Autowired
	PatientRxNotifyProviderTypeRepository patientRxNotifyProviderTypeRepo;
	
	public List<PatientRxNotifyProviderType> getAllPatientRxNotifyProviderType() {
		try {
			return patientRxNotifyProviderTypeRepo.findAll();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return null;
	}

	
		
}
