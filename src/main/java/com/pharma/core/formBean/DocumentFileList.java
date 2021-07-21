package com.pharma.core.formBean;

import java.sql.Date;
/**
 * This class is a mcv form bean
 *
 */
public class DocumentFileList {

	private int fileId;
	private String originalFileName;
	private String storedFielName;
	private String fileType;
	private String uploadedDate;
	private String uploadedByName;
	private String userType;
	
	private String filePurpose;  //  DEA License / State License / Other  types
	private String otherDocFileName;
	private String docExpiryDate;

	
	private String DT_RowId;
	
	
	public int getFileId() {
		return fileId;
	}
	public void setFileId(int fileId) {
		this.fileId = fileId;
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
	public String getUploadedDate() {
		return uploadedDate;
	}
	public void setUploadedDate(String uploadedDate) {
		this.uploadedDate = uploadedDate;
	}
	public String getUploadedByName() {
		return uploadedByName;
	}
	public void setUploadedByName(String uploadedByName) {
		this.uploadedByName = uploadedByName;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getDT_RowId() {
		return DT_RowId;
	}
	public void setDT_RowId(String dT_RowId) {
		DT_RowId = dT_RowId;
	}
	public String getFilePurpose() {
		return filePurpose;
	}
	public void setFilePurpose(String filePurpose) {
		this.filePurpose = filePurpose;
	}
	public String getOtherDocFileName() {
		return otherDocFileName;
	}
	public void setOtherDocFileName(String otherDocFileName) {
		this.otherDocFileName = otherDocFileName;
	}
	public String getDocExpiryDate() {
		return docExpiryDate;
	}
	public void setDocExpiryDate(String docExpiryDate) {
		this.docExpiryDate = docExpiryDate;
	}
	
}
