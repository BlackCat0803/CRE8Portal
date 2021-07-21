package com.pharma.core.repository.prescription;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.pharma.core.model.invoice.InvoiceTransaction;
import com.pharma.core.model.prescription.PrescriptionTransaction;

/**
 * This class is a Java interface that defines a data access contract
 *
 */
@Repository("prescriptionTranRepository")
public interface PrescriptionTranRepository extends JpaRepository<PrescriptionTransaction, Integer>{

	List<PrescriptionTransaction> findByPrescriptionId(int id);
	
	List<PrescriptionTransaction> findByPrescriptionIdAndDelFlag(int id, String delFlag);
	

}

