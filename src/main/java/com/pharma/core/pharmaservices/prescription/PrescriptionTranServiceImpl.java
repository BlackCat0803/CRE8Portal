package com.pharma.core.pharmaservices.prescription;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pharma.core.model.prescription.PrescriptionTransaction;
import com.pharma.core.repository.prescription.PrescriptionTranRepository;

/**
 * This is an implementation class that loads and stores the prescription data
 *
 */
@Service("prescriptionTranService")
public class PrescriptionTranServiceImpl implements PrescriptionTranService {

	@Autowired
	PrescriptionTranRepository tranRepo;
	
	public List<PrescriptionTransaction> getTransactionByPrescription(int id) {
		return tranRepo.findByPrescriptionId(id);
	}

	
}
