package com.pharma.core.model.prescription;

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
@Table(name="subtance_item_documents")
public class SubtanceItemDocuments {

	private int id;
	private int prescriptionId;
	private String originalFileName;
	private String storedFielName;
	private String fileType;
	private String fileDescription;
	private String isHardCopy;
	private Date uploadedDate;
	private int uploadedBy;
	private String userType;
	private int verifyBy;
	private Date verifyDate;
	private String verifyUserType;
	
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@Column(name="prescription_id", nullable = false)
	public int getPrescriptionId() {
		return prescriptionId;
	}
	public void setPrescriptionId(int prescriptionItemId) {
		this.prescriptionId = prescriptionItemId;
	}
	
	@Column(name = "original_file_name", nullable = false, length = 200)
	public String getOriginalFileName() {
		return originalFileName;
	}
	public void setOriginalFileName(String originalFileName) {
		this.originalFileName = originalFileName;
	}
	
	@Column(name = "stored_file_name", nullable = false, length = 200)
	public String getStoredFielName() {
		return storedFielName;
	}
	public void setStoredFielName(String storedFielName) {
		this.storedFielName = storedFielName;
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

	@Column(name = "is_hard_copy", nullable = false)
	public String getIsHardCopy() {
		return isHardCopy;
	}
	public void setIsHardCopy(String isHardCopy) {
		this.isHardCopy = isHardCopy;
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
	
	@Column(name = "user_type", nullable = false, length = 20)
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	
	@Column(name = "verify_by", nullable = true)
	public int getVerifyBy() {
		return verifyBy;
	}
	public void setVerifyBy(int verifyBy) {
		this.verifyBy = verifyBy;
	}
	
	@Column(name="verify_date", nullable = true)
	public Date getVerifyDate() {
		return verifyDate;
	}
	public void setVerifyDate(Date verifyDate) {
		this.verifyDate = verifyDate;
	}
	
	@Column(name = "verify_user_type", nullable = true)
	public String getVerifyUserType() {
		return verifyUserType;
	}
	public void setVerifyUserType(String verifyUserType) {
		this.verifyUserType = verifyUserType;
	}
	
}
