package com.pharma.core.repository.pharmacygroup;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pharma.core.model.pharmacygroup.GroupDirectorFileUpload;
/**
 * This class is a Java interface that defines a data access contract
 *
 */
@Repository("groupDirectorFileUpload")
public interface GroupDirectorFileUploadRepository extends JpaRepository<GroupDirectorFileUpload, Integer>{

	GroupDirectorFileUpload findByGroupDirectorIdAndFileType(int id, String fileType);
	
	List<GroupDirectorFileUpload> findByGroupDirectorIdAndFileTypeNot(int id, String fileType);
}
