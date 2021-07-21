package com.pharma.core.repository.prescription;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.pharma.core.model.prescription.SubtanceItemDocuments;
/**
 * This class is a Java interface that defines a data access contract
 *
 */
@Repository("subtanceDocumentsRepository")
public interface SubtanceItemsDocumentsRepository extends JpaRepository<SubtanceItemDocuments, Integer> {

	@Query("select  COALESCE(max(id),0)  from SubtanceItemDocuments ")
	int getLastDocId();
	
	SubtanceItemDocuments findByPrescriptionIdAndIsHardCopy(int id, String hardCopy);
	
	List<SubtanceItemDocuments> findAllByPrescriptionId(int id);
	
	Page<SubtanceItemDocuments> findByPrescriptionId(int id, Pageable page);
	
}
