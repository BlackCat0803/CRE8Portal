package com.pharma.core.repository.pioneer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pharma.core.model.pioneer.PatientSyncStatusType;
/**
 * This class is a Java interface that defines a data access contract
 *
 */
@Repository("patientSyncStatusTypeRepository")
public interface PatientSyncStatusTypeRepository extends JpaRepository<PatientSyncStatusType, Integer> {
	
}
