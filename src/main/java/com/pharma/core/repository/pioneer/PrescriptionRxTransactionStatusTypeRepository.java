package com.pharma.core.repository.pioneer;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pharma.core.model.pioneer.PrescriptionRxTransactionStatusType;
/**
 * This class is a Java interface that defines a data access contract
 *
 */
@Repository("prescriptionRxTransactionStatusTypeRepository")
public interface PrescriptionRxTransactionStatusTypeRepository extends JpaRepository<PrescriptionRxTransactionStatusType, String> {
	
	List<PrescriptionRxTransactionStatusType> findByIsactive(int statusId);
}
