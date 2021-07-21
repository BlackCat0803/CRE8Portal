package com.pharma.core.pharmaservices.prescription;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pharma.core.model.prescription.PrescriptionTransaction;

@Service
public interface PrescriptionTranService {

	List<PrescriptionTransaction> getTransactionByPrescription(int id);
	
	
	
}
