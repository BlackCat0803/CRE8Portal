package com.pharma.core.repository.pioneer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pharma.core.model.pioneer.PrescriptionItemDispensingUnit;
/**
 * This class is a Java interface that defines a data access contract
 *
 */
@Repository("prescriptionItemDispensingUnitRepository")
public interface PrescriptionItemDispensingUnitRepository extends JpaRepository<PrescriptionItemDispensingUnit, Integer> {
	
	
	PrescriptionItemDispensingUnit findByDispensingunitid(int id);
	
	
}
