package com.pharma.core.repository.pioneer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pharma.core.model.pioneer.PatientRxNotifyType;
/**
 * This class is a Java interface that defines a data access contract
 *
 */
@Repository("patientRxNotifyTypeRepository")
public interface PatientRxNotifyTypeRepository extends JpaRepository<PatientRxNotifyType, Integer> {
	
}
