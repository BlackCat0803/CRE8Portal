package com.pharma.core.formBean.prescription;

import java.sql.Date;
/**
 * This class is a mcv form bean
 *
 */
public class SubtanceItemDocForm {

	private int id;
	private int prescriptionId;
	private String originalFileName;
	private String storedFielName;
	private String fileType;
	private String fileDescription;
	private String scan;
	private String fax;
	private String isHardCopy;
	private String uploadedDate;
	private int uploadedBy;
	private String userType;
	private int verifyBy;
	private String verifyDate;
	private String verifyUserType;
	
	private String verifyByName;
	private String DT_RowId;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPrescriptionId() {
		return prescriptionId;
	}
	public void setPrescriptionId(int prescriptionId) {
		this.prescriptionId = prescriptionId;
	}
	public String getOriginalFileName() {
		return originalFileName;
	}
	public void setOriginalFileName(String originalFileName) {
		this.originalFileName = originalFileName;
	}
	public String getStoredFielName() {
		return storedFielName;
	}
	public void setStoredFielName(String storedFielName) {
		this.storedFielName = storedFielName;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public String getFileDescription() {
		return fileDescription;
	}
	public void setFileDescription(String fileDescription) {
		this.fileDescription = fileDescription;
	}
	public String getScan() {
		return scan;
	}
	public void setScan(String scan) {
		this.scan = scan;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getIsHardCopy() {
		return isHardCopy;
	}
	public void setIsHardCopy(String isHardCopy) {
		this.isHardCopy = isHardCopy;
	}
	public String getUploadedDate() {
		return uploadedDate;
	}
	public void setUploadedDate(String uploadedDate) {
		this.uploadedDate = uploadedDate;
	}
	public int getUploadedBy() {
		return uploadedBy;
	}
	public void setUploadedBy(int uploadedBy) {
		this.uploadedBy = uploadedBy;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public int getVerifyBy() {
		return verifyBy;
	}
	public void setVerifyBy(int verifyBy) {
		this.verifyBy = verifyBy;
	}
	public String getVerifyDate() {
		return verifyDate;
	}
	public void setVerifyDate(String verifyDate) {
		this.verifyDate = verifyDate;
	}
	public String getVerifyUserType() {
		return verifyUserType;
	}
	public void setVerifyUserType(String verifyUserType) {
		this.verifyUserType = verifyUserType;
	}
	public String getVerifyByName() {
		return verifyByName;
	}
	public void setVerifyByName(String verifyByName) {
		this.verifyByName = verifyByName;
	}
	public String getDT_RowId() {
		return DT_RowId;
	}
	public void setDT_RowId(String dT_RowId) {
		DT_RowId = dT_RowId;
	}
	
}
