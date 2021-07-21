package com.pharma.core.formBean.manual;
/**
 * This class is a mcv form bean
 *
 */
public class InstructionManualForm {

	private int fileId;
	
	private String documentTitle;
	
	// Thumb image 
	private String originalThumbFileName;
	private String storedThumbFielName;
	private String thumbFileExtension;
	
	// Document file data
	private String originalFileName;
	private String storedFielName;
	private String fileExtension;
	
	private String fileDescription;
	private int displayOrder;
	private int adminId;
	private String uploadedDate;
	
	// for Summary page
	private String DT_RowId;
	private String documentType;
	private String creatorName;
	
	private String displayDocumentName;
	private String displayImageName;
	
	private int tempId;
	
	public int getFileId() {
		return fileId;
	}
	public void setFileId(int fileId) {
		this.fileId = fileId;
	}
	public String getDocumentTitle() {
		return documentTitle;
	}
	public void setDocumentTitle(String documentTitle) {
		this.documentTitle = documentTitle;
	}
	public String getOriginalThumbFileName() {
		return originalThumbFileName;
	}
	public void setOriginalThumbFileName(String originalThumbFileName) {
		this.originalThumbFileName = originalThumbFileName;
	}
	public String getStoredThumbFielName() {
		return storedThumbFielName;
	}
	public void setStoredThumbFielName(String storedThumbFielName) {
		this.storedThumbFielName = storedThumbFielName;
	}
	public String getThumbFileExtension() {
		return thumbFileExtension;
	}
	public void setThumbFileExtension(String thumbFileExtension) {
		this.thumbFileExtension = thumbFileExtension;
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
	public String getFileExtension() {
		return fileExtension;
	}
	public void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}
	
	public String getFileDescription() {
		return fileDescription;
	}
	public void setFileDescription(String fileDescription) {
		this.fileDescription = fileDescription;
	}
	public int getDisplayOrder() {
		return displayOrder;
	}
	public void setDisplayOrder(int displayOrder) {
		this.displayOrder = displayOrder;
	}
	public int getAdminId() {
		return adminId;
	}
	public void setAdminId(int adminId) {
		this.adminId = adminId;
	}
	public String getUploadedDate() {
		return uploadedDate;
	}
	public void setUploadedDate(String uploadedDate) {
		this.uploadedDate = uploadedDate;
	}
	public String getDT_RowId() {
		return DT_RowId;
	}
	public void setDT_RowId(String dT_RowId) {
		DT_RowId = dT_RowId;
	}
	public String getDocumentType() {
		return documentType;
	}
	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}
	public String getCreatorName() {
		return creatorName;
	}
	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}
	public String getDisplayDocumentName() {
		return displayDocumentName;
	}
	public void setDisplayDocumentName(String displayDocumentName) {
		this.displayDocumentName = displayDocumentName;
	}
	public String getDisplayImageName() {
		return displayImageName;
	}
	public void setDisplayImageName(String displayImageName) {
		this.displayImageName = displayImageName;
	}
	public int getTempId() {
		return tempId;
	}
	public void setTempId(int tempId) {
		this.tempId = tempId;
	}

}
