package com.pharma.core.pharmaservices.pioneer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pharma.core.model.pioneer.PrescriptionOriginType;
import com.pharma.core.repository.pioneer.PrescriptionOriginTypeRepository;

/**
 * This is an implementation class that loads the pioneer related data
 *
 */
@Service("prescriptionOriginTypeService")
public class PrescriptionOriginTypeServiceImpl implements PrescriptionOriginTypeService {

	@Autowired
	PrescriptionOriginTypeRepository prescriptionOriginTypeRepo;
	
	public List<PrescriptionOriginType> getAllPrescriptionOriginType() {
		try {
			return prescriptionOriginTypeRepo.findAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		 return null;
	}

	
		
}
