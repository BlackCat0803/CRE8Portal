package com.pharma.core.repository.pioneer;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pharma.core.model.pioneer.PrescriptionItem;
import com.pharma.core.model.pioneer.PrescriptionMedication;
/**
 * This class is a Java interface that defines a data access contract
 *
 */
@Repository("prescriptionItemRepository")
public interface PrescriptionItemRepository extends JpaRepository<PrescriptionItem, String> {
	//get list
	@Query("select c from PrescriptionItem c where itemid = :itemid")
	List<PrescriptionMedication> getPrescriptionDispensedItemList( @Param("itemid") String itemid);

	// Autocomplete to get list
	@Query("select c from PrescriptionItem c where (itemname like %:term% or itemid=:itemid) order by itemid asc")
	List<PrescriptionItem> getAutoCompletegetAutoCompletePrescriptionItemsList( @Param("term") String term, @Param("itemid") String itemid,Pageable pageable);

	@Query("select c from PrescriptionItem c  order by c.itemid asc")
	List<PrescriptionItem> getAllAutoCompletePrescriptionItemsList();

}


