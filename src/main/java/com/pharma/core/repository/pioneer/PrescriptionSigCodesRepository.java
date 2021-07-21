package com.pharma.core.repository.pioneer;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pharma.core.model.pioneer.PrescriptionSigCodes;
/**
 * This class is a Java interface that defines a data access contract
 *
 */
@Repository("prescriptionSigCodesRepository")
public interface PrescriptionSigCodesRepository extends JpaRepository<PrescriptionSigCodes, String> {
	
	@Query("select c from PrescriptionSigCodes c where sigid=:code")
	PrescriptionSigCodes findBySigid(@Param("code") String code);
	
	// Autocomplete to get list
	@Query("select c from PrescriptionSigCodes c where englishtext like %:term% or sigid=:sigid order by sigid asc")
	List<PrescriptionSigCodes> getAutoCompletePrescriptionSigList( @Param("term") String term, @Param("sigid") String sigid, Pageable pageable);

	// Autocomplete to get list
	@Query("select c from PrescriptionSigCodes c where englishtext like %:term% order by sigid asc")
	List<PrescriptionSigCodes> getAllAutoCompletePrescriptionSigList( @Param("term") String term, Pageable pageable);
		

		
	
}
