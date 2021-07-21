package com.pharma.core.pharmaservices.pioneer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pharma.core.model.pioneer.PatientRxNotifyType;
import com.pharma.core.repository.pioneer.PatientRxNotifyTypeRepository;

/**
 * This is an implementation class that loads the pioneer related data
  *
 */
@Service("patientRxNotifyTypeService")
public class PatientRxNotifyTypeServiceImpl implements PatientRxNotifyTypeService {

	@Autowired
	PatientRxNotifyTypeRepository patientRxNotifyTypeRepo;
	
	public List<PatientRxNotifyType> getAllPatientRxNotifyType() {
		try {
			return patientRxNotifyTypeRepo.findAll();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return null;
	}

	
		
}
