package com.pharma.core.pharmaservices.pioneer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pharma.core.model.pioneer.PrescriptionItemDispensingUnit;
import com.pharma.core.repository.pioneer.PrescriptionItemDispensingUnitRepository;

/**
 * This is an implementation class that loads the pioneer related data
 *
 */
@Service("prescriptionItemDispensingUnitService")
public class PrescriptionItemDispensingUnitServiceImpl implements PrescriptionItemDispensingUnitService {

	@Autowired
	PrescriptionItemDispensingUnitRepository prescriptionItemDispensingUnitRepo;
	
	public List<PrescriptionItemDispensingUnit> getAllPrescriptionItemDispensingUnit() {
		try {
			return prescriptionItemDispensingUnitRepo.findAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		 return null;
	}

	
		
}
