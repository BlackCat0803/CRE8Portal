package com.pharma.core.repository.patient;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.pharma.core.model.patientaccount.PatientFileUpload;
/**
 * This class is a Java interface that defines a data access contract
 *
 */
@Repository("patientFileUploadRepository")
public interface PatientFileUploadRepository extends JpaRepository<PatientFileUpload, Integer> {

	PatientFileUpload findByPatientIdAndFileType(int id, String fileType);
	
	List<PatientFileUpload> findByPatientIdAndFileTypeNot(int id, String fileType);

	@Query("select  COALESCE(max(id),0)  from PatientFileUpload ")
	int getMaxFileId();
	
	Page<PatientFileUpload> findByPatientIdAndFileTypeNot(int id, String fileType, Pageable page);
	
}
