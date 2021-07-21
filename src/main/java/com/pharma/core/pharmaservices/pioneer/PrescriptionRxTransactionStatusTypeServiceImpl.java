package com.pharma.core.pharmaservices.pioneer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pharma.core.model.pioneer.PrescriptionRxTransactionStatusType;
import com.pharma.core.repository.pioneer.PrescriptionRxTransactionStatusTypeRepository;

/**
 * This is an implementation class that loads the pioneer related data
 */
@Service("prescriptionRxTransactionStatusTypeService")
public class PrescriptionRxTransactionStatusTypeServiceImpl implements PrescriptionRxTransactionStatusTypeService {

	@Autowired
	PrescriptionRxTransactionStatusTypeRepository  prescriptionRxTransactionStatusTypeRepo;
	
	public List<PrescriptionRxTransactionStatusType> getAllPrescriptionRxTransactionStatusType() {
		try {
			return prescriptionRxTransactionStatusTypeRepo.findAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		 return null;
	}


	public List<PrescriptionRxTransactionStatusType> getAllActiveStatus() {
		try {
			return prescriptionRxTransactionStatusTypeRepo.findByIsactive(1);  // 0 -> Inactive, 1 -> Active
		} catch (Exception e) {
			e.printStackTrace();
		}
		 return null;
	}
		
}
