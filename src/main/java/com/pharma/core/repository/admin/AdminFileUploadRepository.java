package com.pharma.core.repository.admin;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pharma.core.model.admin.AdminFileUpload;
/**
 * This class is a Java interface that defines a data access contract
 *
 */
@Repository("adminFileUploadRepository")
public interface AdminFileUploadRepository extends JpaRepository<AdminFileUpload, Integer> {

	AdminFileUpload findByAdminIdAndFileType(int id, String fileType);
	
	List<AdminFileUpload> findByAdminIdAndFileTypeNot(int id, String fileType);	
	
}
