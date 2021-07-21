package com.pharma.core.repository.pioneer;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pharma.core.model.pioneer.PrescriptionItemDEAOverride;

/**
 * This class is a Java interface that defines a data access contract
 *
 */
@Repository("prescriptionItemDEAOverrideRepository")
public interface PrescriptionItemDEAOverrideRepository extends JpaRepository<PrescriptionItemDEAOverride, String> {
	
	
		@Query("select c from PrescriptionItemDEAOverride c where gcn like %:gcn% and statecode=:statecode order by deaoverrideid asc")
		List<PrescriptionItemDEAOverride> getPrescriptionItemDEAOverrideList( @Param("gcn") String gcn, @Param("statecode") String statecode);

		@Query("select c from PrescriptionItemDEAOverride c where gcn like %:gcn% order by deaoverrideid asc")
		List<PrescriptionItemDEAOverride> getPrescriptionItemDEAOverrideList( @Param("gcn") String gcn);
	
}
