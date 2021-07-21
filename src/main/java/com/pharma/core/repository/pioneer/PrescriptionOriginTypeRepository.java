package com.pharma.core.repository.pioneer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pharma.core.model.pioneer.PrescriptionOriginType;
/**
 * This class is a Java interface that defines a data access contract
 *
 */
@Repository("prescriptionOriginTypeRepository")
public interface PrescriptionOriginTypeRepository extends JpaRepository<PrescriptionOriginType, Integer> {
	
	@Query("select c from PrescriptionOriginType c where origintypeid=:id ")
	PrescriptionOriginType findByOriginId(@Param("id") int id);
	
}
