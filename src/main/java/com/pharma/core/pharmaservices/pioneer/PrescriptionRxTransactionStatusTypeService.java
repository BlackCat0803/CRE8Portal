package com.pharma.core.pharmaservices.pioneer;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pharma.core.model.pioneer.PrescriptionRxTransactionStatusType;

@Service
public interface PrescriptionRxTransactionStatusTypeService {
	
	List<PrescriptionRxTransactionStatusType> getAllPrescriptionRxTransactionStatusType();
	
	List<PrescriptionRxTransactionStatusType> getAllActiveStatus();
}
