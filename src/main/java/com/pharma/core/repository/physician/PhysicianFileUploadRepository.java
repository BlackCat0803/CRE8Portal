package com.pharma.core.repository.physician;

import java.sql.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pharma.core.model.physician.PhysicianFileUpload;
/**
 * This class is a Java interface that defines a data access contract
 *
 */
@Repository("physicianFileUpload")
public interface PhysicianFileUploadRepository extends JpaRepository<PhysicianFileUpload, Integer> {

	PhysicianFileUpload findByPhysicianIdAndFileType(int id, String fileType);
	

	
	@Query("select  COALESCE(max(id),0)  from PhysicianFileUpload ")
	int getMaxFileId();
	
	
	@Query("select p from PhysicianFileUpload p where p.fileType<>:fileType1 and p.fileType<>:fileType2 and p.physicianId<>:physicianId")
	List<PhysicianFileUpload> findByPhysicianIdAndFileTypeNot( @Param("physicianId") int physicianId, @Param("fileType1") String fileType1, @Param("fileType2") String fileType2);
	
	@Query("select p from PhysicianFileUpload p where p.fileType<>:fileType1 and p.fileType<>:fileType2 and p.physicianId<>:physicianId")
	Page<PhysicianFileUpload> findByPhysicianIdAndFileTypeNot( @Param("physicianId") int physicianId, @Param("fileType1") String fileType1, @Param("fileType2") String fileType2, Pageable page);

	@Query("select p from PhysicianFileUpload p where p.fileType=:fileType and p.physicianId=:physicianId")
	List<PhysicianFileUpload> findByPhysicianIdAndUploadFiles( @Param("physicianId") int physicianId, @Param("fileType") String fileType);
	
	@Query("select p from PhysicianFileUpload p where p.fileType=:fileType and p.physicianId=:physicianId")
	Page<PhysicianFileUpload> findByPhysicianIdAndUploadFiles( @Param("physicianId") int physicianId, @Param("fileType") String fileType, Pageable page);


	PhysicianFileUpload findByPhysicianIdAndFilePurpose(int physicianId, String purpose);

	@Query("Select p from PhysicianFileUpload p where p.physicianId=:physicianId and p.docExpiryDate=:expDate ")
	List<PhysicianFileUpload> getLicenseExpiredDocListByPhysician(@Param("physicianId") int physicianId, @Param("expDate") Date expDate);

}


