package com.pharma.core.repository.pioneer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pharma.core.model.pioneer.PatientRxNotifyProviderType;
/**
 * This class is a Java interface that defines a data access contract
 *
 */
@Repository("patientRxNotifyProviderTypeRepository")
public interface PatientRxNotifyProviderTypeRepository extends JpaRepository<PatientRxNotifyProviderType, Integer> {
	
}
