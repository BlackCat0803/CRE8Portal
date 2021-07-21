package com.pharma.core.model.manual;

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
@Table(name="instruction_manual")
public class InstructionManual {
	
	private int id;
	private String fileTitle;
	
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
	private Date uploadedDate;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "file_id", unique = true, nullable = false)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@Column(name = "file_title", nullable = false, length = 200)
	public String getFileTitle() {
		return fileTitle;
	}
	public void setFileTitle(String fileTitle) {
		this.fileTitle = fileTitle;
	}
	
	@Column(name = "original_thumb_file_name", nullable = false, length = 200)
	public String getOriginalThumbFileName() {
		return originalThumbFileName;
	}
	public void setOriginalThumbFileName(String originalThumbFileName) {
		this.originalThumbFileName = originalThumbFileName;
	}
	
	@Column(name = "stored_thumb_file_name", nullable = false, length = 200)
	public String getStoredThumbFielName() {
		return storedThumbFielName;
	}
	public void setStoredThumbFielName(String storedThumbFielName) {
		this.storedThumbFielName = storedThumbFielName;
	}
	
	@Column(name = "thumb_file_extension", nullable = false, length = 200)
	public String getThumbFileExtension() {
		return thumbFileExtension;
	}
	public void setThumbFileExtension(String thumbFileExtension) {
		this.thumbFileExtension = thumbFileExtension;
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
	
	@Column(name = "file_extension", nullable = false, length = 5)
	public String getFileExtension() {
		return fileExtension;
	}
	public void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}

	@Column(name = "description", nullable = true)
	public String getFileDescription() {
		return fileDescription;
	}
	public void setFileDescription(String fileDescription) {
		this.fileDescription = fileDescription;
	}
	
	@Column(name = "display_order", nullable = true)
	public int getDisplayOrder() {
		return displayOrder;
	}
	public void setDisplayOrder(int displayOrder) {
		this.displayOrder = displayOrder;
	}
	
	@Column(name = "admin_id", nullable = true)
	public int getAdminId() {
		return adminId;
	}
	public void setAdminId(int adminId) {
		this.adminId = adminId;
	}
	
	@Column(name="uploaded_date", nullable = true)
	public Date getUploadedDate() {
		return uploadedDate;
	}
	public void setUploadedDate(Date uploadedDate) {
		this.uploadedDate = uploadedDate;
	}
	
}
