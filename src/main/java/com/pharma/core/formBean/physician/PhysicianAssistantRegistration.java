package com.pharma.core.formBean.physician;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * This class is a mcv form bean
 *
 */
public class PhysicianAssistantRegistration {

	private int assistantId;
	private int physicianId;
	// private int clinicId;  // removed from Db on 2nd Nov. 2017
	private String status;
	private String dateRegistrated;
	private String firstName;
	private String lastName;
	private String middleName;
	private String assistantName;
	private String email;
	private String password;
	private String mobile;
	private String phone; 
	
	
	// for Summary page list ids
	private String DT_RowId;
	private String clinicName;
	private String physicianName;
	
	private String photoFile;
		
	private int groupId;
	private String groupName;
	
	
	private int createdBy;
	private String createdUser;
	private Timestamp createdDate;
	private int lastUpdatedBy;
	private String lastUpdatedUser;
	private Timestamp lastUpdatedDate;

	//Multiple select list box
	private String physicianList;
	private String selectedPhysicianId;
	private String physicianSelectedList;
	
	
	private int dropDownGroupId;
	
	public int getAssistantId() {
		return assistantId;
	}
	public void setAssistantId(int assistantId) {
		this.assistantId = assistantId;
	}
	public int getPhysicianId() {
		return physicianId;
	}
	public void setPhysicianId(int physicianId) {
		this.physicianId = physicianId;
	}
	/*public int getClinicId() {
		return clinicId;
	}
	public void setClinicId(int clinicId) {
		this.clinicId = clinicId;
	}*/
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDateRegistrated() {
		return dateRegistrated;
	}
	public void setDateRegistrated(String dateRegistrated) {
		this.dateRegistrated = dateRegistrated;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	public String getAssistantName() {
		return assistantName;
	}
	public void setAssistantName(String assistantName) {
		this.assistantName = assistantName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getDT_RowId() {
		return DT_RowId;
	}
	public void setDT_RowId(String dT_RowId) {
		DT_RowId = dT_RowId;
	}
	public String getClinicName() {
		return clinicName;
	}
	public void setClinicName(String clinicName) {
		this.clinicName = clinicName;
	}
	public String getPhysicianName() {
		return physicianName;
	}
	public void setPhysicianName(String physicianName) {
		this.physicianName = physicianName;
	}
	public String getPhotoFile() {
		return photoFile;
	}
	public void setPhotoFile(String photoFile) {
		this.photoFile = photoFile;
	}
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
	public int getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}
	public String getCreatedUser() {
		return createdUser;
	}
	public void setCreatedUser(String createdUser) {
		this.createdUser = createdUser;
	}
	public Timestamp getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}
	public int getLastUpdatedBy() {
		return lastUpdatedBy;
	}
	public void setLastUpdatedBy(int lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
	public String getLastUpdatedUser() {
		return lastUpdatedUser;
	}
	public void setLastUpdatedUser(String lastUpdatedUser) {
		this.lastUpdatedUser = lastUpdatedUser;
	}
	public Timestamp getLastUpdatedDate() {
		return lastUpdatedDate;
	}
	public void setLastUpdatedDate(Timestamp lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}
	public String getPhysicianList() {
		return physicianList;
	}
	public void setPhysicianList(String physicianList) {
		this.physicianList = physicianList;
	}
	public String getSelectedPhysicianId() {
		return selectedPhysicianId;
	}
	public void setSelectedPhysicianId(String selectedPhysicianId) {
		this.selectedPhysicianId = selectedPhysicianId;
	}
	public String getPhysicianSelectedList() {
		return physicianSelectedList;
	}
	public void setPhysicianSelectedList(String physicianSelectedList) {
		this.physicianSelectedList = physicianSelectedList;
	}
	public int getDropDownGroupId() {
		return dropDownGroupId;
	}
	public void setDropDownGroupId(int dropDownGroupId) {
		this.dropDownGroupId = dropDownGroupId;
	}
	
}
