package com.pharma.core.repository.manual;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pharma.core.model.manual.InstructionManual;
/**
 * This class is a Java interface that defines a data access contract
 *
 */
@Repository("instructionManualRepository")
public interface InstructionManualRepository extends JpaRepository<InstructionManual, Integer> {

	@Query("Select c from InstructionManual c order by id desc")
	List<InstructionManual> getAllManual();
	
	@Query("Select  COALESCE(max(c.displayOrder),0)  from InstructionManual c ")
	int getMaxDisplayOrder();
	
	@Query("select c from InstructionManual c where displayOrder >= :order order by displayOrder asc, id desc")
	List<InstructionManual> getOrderRecords(@Param("order") int order);

	@Query("select  COALESCE(count(c),0) from InstructionManual c where fileTitle like %:docName%  ")
	int countWidthSearch(@Param("docName") String docName);

	@Query("Select c from InstructionManual c where fileTitle like %:docName%  order by id desc ")
	Page<InstructionManual> findWidthSearch(@Param("docName") String docName,  Pageable page);
	
	@Query("Select c from InstructionManual c order by id desc")
	List<InstructionManual> getLatestManual(Pageable pageable);
	
	@Query("Select c from InstructionManual c order by id desc")
	Page<InstructionManual> getAllManual(Pageable pageable);
}
