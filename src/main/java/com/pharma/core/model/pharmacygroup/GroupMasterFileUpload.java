package com.pharma.core.model.pharmacygroup;

import static javax.persistence.GenerationType.IDENTITY;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * This class is a JPA entity class
 */
@Entity
@Table(name="group_fileupload")
public class GroupMasterFileUpload {

	private int fileId;
	private int groupId;
	private String originalFileName;
	private String storedFileName;
	private String fileType;
	private String fileDescription;
	private Date uploadedDate;
	private int uploadedBy;
	private String userType;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "file_id", unique = true, nullable = false)
	public int getFileId() {
		return fileId;
	}
	public void setFileId(int fileId) {
		this.fileId = fileId;
	}
	
	@Column(name="group_id", nullable = false)
	public int getGroupId() {
		return groupId;
	}
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	
	@Column(name = "original_file_name", nullable = false, length = 200)
	public String getOriginalFileName() {
		return originalFileName;
	}
	public void setOriginalFileName(String originalFileName) {
		this.originalFileName = originalFileName;
	}
	
	@Column(name = "stored_file_name", nullable = false, length = 200)
	public String getStoredFileName() {
		return storedFileName;
	}
	public void setStoredFileName(String storedFileName) {
		this.storedFileName = storedFileName;
	}
	
	@Column(name = "file_type", nullable = false, length = 5)
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	
	@Column(name = "description", nullable = true)
	public String getFileDescription() {
		return fileDescription;
	}
	public void setFileDescription(String fileDescription) {
		this.fileDescription = fileDescription;
	}
	
	@Column(name="uploaded_date", nullable = true)
	public Date getUploadedDate() {
		return uploadedDate;
	}
	public void setUploadedDate(Date uploadedDate) {
		this.uploadedDate = uploadedDate;
	}
	
	@Column(name = "uploaded_by", nullable = true)
	public int getUploadedBy() {
		return uploadedBy;
	}
	public void setUploadedBy(int uploadedBy) {
		this.uploadedBy = uploadedBy;
	}
	
	@Column(name = "user_type", nullable = false, length = 10)
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	
}
