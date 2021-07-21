package com.pharma.core.repository.pioneer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pharma.core.model.pioneer.PrescriptionRxStatusType;
/**
 * This class is a Java interface that defines a data access contract
 *
 */
@Repository("prescriptionRxStatusTypeRepository")
public interface PrescriptionRxStatusTypeRepository extends JpaRepository<PrescriptionRxStatusType, String> {
	
}
