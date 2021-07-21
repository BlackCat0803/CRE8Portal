package com.pharma.core.repository.prescription;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.pharma.core.model.prescription.PrescriptionStatus;
/**
 * This class is a Java interface that defines a data access contract
 *
 */
@Repository("prescriptionStatusRepository")
public interface PrescriptionStatusRepository extends JpaRepository<PrescriptionStatus, Integer> {
	
	
	@Query("select p from PrescriptionStatus p where p.delflag='N' order by p.displayorder asc")
	List<PrescriptionStatus> getAllPrescriptionStatusList();
	
}
