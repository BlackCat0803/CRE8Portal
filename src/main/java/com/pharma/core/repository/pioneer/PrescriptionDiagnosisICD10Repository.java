package com.pharma.core.repository.pioneer;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pharma.core.model.pioneer.PrescriptionDiagnosisICD10;
/**
 * This class is a Java interface that defines a data access contract
 *
 */
@Repository("prescriptionDiagnosisICD10Repository")
public interface PrescriptionDiagnosisICD10Repository extends JpaRepository<PrescriptionDiagnosisICD10, String> {
	
	// Autocomplete to get list
	@Query("select c from PrescriptionDiagnosisICD10 c where shortdescription like %:term% or icd10code=:icd10code order by icd10code asc")
	List<PrescriptionDiagnosisICD10> getAutoCompleteDiagnosisICD10List( @Param("term") String term, @Param("icd10code") String icd10code, Pageable pageable);

	// Autocomplete to get list
	@Query("select c from PrescriptionDiagnosisICD10 c where shortdescription like %:term% order by icd10code asc")
	List<PrescriptionDiagnosisICD10> getAllAutoCompleteDiagnosisICD10List( @Param("term") String term, Pageable pageable);
	

	
}
