package com.pharma.core.repository.pharmacygroup;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pharma.core.model.pharmacygroup.GroupMasterFileUpload;
/**
 * This class is a Java interface that defines a data access contract
 *
 */
@Repository("groupMasterFileUpload")
public interface GroupMasterFileUploadRepository extends JpaRepository<GroupMasterFileUpload, Integer>{

	GroupMasterFileUpload findByGroupIdAndFileType(int id, String fileType);
	
	List<GroupMasterFileUpload> findByGroupIdAndFileTypeNot(int id, String fileType);
}
