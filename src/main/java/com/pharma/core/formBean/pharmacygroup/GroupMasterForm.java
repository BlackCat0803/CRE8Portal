package com.pharma.core.formBean.pharmacygroup;

import java.sql.Timestamp;

/**
 * This class is a mcv form bean
 *
 */
public class GroupMasterForm {

	private int groupId;
	private String groupName;
	private String contactName;
	private String email;
	private String mobile; 
	private String status;
	
	private int lastUpdatedBy;
	private Timestamp lastUpdatedDate;
	private int createdBy;
	private Timestamp createdDate;
	private String groupDirectorName;
	
	
	private String createdUser;
	private String lastUpdatedUser;

	private String logoFile;
	
	// for Summary page list ids
	private String DT_RowId;
	private int otherGroupId;
	private int otherPhysicianId;
	
	
	private int dropDownGroupId;
	private int physicianId;
	private int assistantId;
	private int patientId;
	private int groupPhysicianId;
	
	
	

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	

	public int getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(int lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public Timestamp getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(Timestamp lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	public int getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public String getDT_RowId() {
		return DT_RowId;
	}

	public void setDT_RowId(String dT_RowId) {
		DT_RowId = dT_RowId;
	}

	public String getGroupDirectorName() {
		return groupDirectorName;
	}

	public void setGroupDirectorName(String groupDirectorName) {
		this.groupDirectorName = groupDirectorName;
	}

	public String getCreatedUser() {
		return createdUser;
	}

	public void setCreatedUser(String createdUser) {
		this.createdUser = createdUser;
	}

	public String getLastUpdatedUser() {
		return lastUpdatedUser;
	}

	public void setLastUpdatedUser(String lastUpdatedUser) {
		this.lastUpdatedUser = lastUpdatedUser;
	}

	public String getLogoFile() {
		return logoFile;
	}

	public void setLogoFile(String logoFile) {
		this.logoFile = logoFile;
	}

	public int getOtherGroupId() {
		return otherGroupId;
	}

	public void setOtherGroupId(int otherGroupId) {
		this.otherGroupId = otherGroupId;
	}

	public int getDropDownGroupId() {
		return dropDownGroupId;
	}

	public void setDropDownGroupId(int dropDownGroupId) {
		this.dropDownGroupId = dropDownGroupId;
	}

	public int getPhysicianId() {
		return physicianId;
	}

	public void setPhysicianId(int physicianId) {
		this.physicianId = physicianId;
	}

	public int getAssistantId() {
		return assistantId;
	}

	public void setAssistantId(int assistantId) {
		this.assistantId = assistantId;
	}

	public int getPatientId() {
		return patientId;
	}

	public void setPatientId(int patientId) {
		this.patientId = patientId;
	}

	public int getGroupPhysicianId() {
		return groupPhysicianId;
	}

	public void setGroupPhysicianId(int groupPhysicianId) {
		this.groupPhysicianId = groupPhysicianId;
	}

	public int getOtherPhysicianId() {
		return otherPhysicianId;
	}

	public void setOtherPhysicianId(int otherPhysicianId) {
		this.otherPhysicianId = otherPhysicianId;
	}

	
	
}
