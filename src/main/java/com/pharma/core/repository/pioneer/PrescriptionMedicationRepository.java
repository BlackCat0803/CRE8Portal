package com.pharma.core.repository.pioneer;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pharma.core.model.pioneer.PrescriptionMedication;
/**
 * This class is a Java interface that defines a data access contract
 *
 */
@Repository("prescriptionMedicationRepository")
public interface PrescriptionMedicationRepository extends JpaRepository<PrescriptionMedication, Integer> {
	
	// Autocomplete to get list
	@Query("select c from PrescriptionMedication c where medtype=:typeId  and (medicationdescription like %:term% or medicationid=:medicationid) order by medicationid asc")
	List<PrescriptionMedication> getAutoCompletePrescriptionMedicationList( @Param("term") String term, @Param("medicationid") int medicationid,@Param("typeId") int typeId, Pageable pageable);

	// Autocomplete to get list
	@Query("select c from PrescriptionMedication c where medicationdescription like %:term%  order by medicationid asc")
	List<PrescriptionMedication> getAllAutoCompletePrescriptionMedicationList( @Param("term") String term, Pageable pageable);

	// Autocomplete to get list
	@Query("select c from PrescriptionMedication c where (medicationdescription like %:term% or medicationid=:medicationid) order by medicationid asc")
	List<PrescriptionMedication> getAutoCompletePrescriptionMedicationsList( @Param("term") String term, @Param("medicationid") int medicationid, Pageable pageable);

	@Query("select c from PrescriptionMedication c  order by c.medicationid asc")
	List<PrescriptionMedication> getAllAutoCompletePrescriptionMedicationsList();

	
}
