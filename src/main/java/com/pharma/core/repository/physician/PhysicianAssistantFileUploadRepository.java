package com.pharma.core.repository.physician;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pharma.core.model.physician.PhysicianAssistantFileUpload;
/**
 * This class is a Java interface that defines a data access contract
 *
 */
@Repository("physicianAssistantFileUpload")
public interface PhysicianAssistantFileUploadRepository extends JpaRepository<PhysicianAssistantFileUpload, Integer>{

	PhysicianAssistantFileUpload findByPhysicianAssistantIdAndFileType(int id, String fileType);
	
	List<PhysicianAssistantFileUpload> findByPhysicianAssistantIdAndFileTypeNot(int id, String fileType);
}
