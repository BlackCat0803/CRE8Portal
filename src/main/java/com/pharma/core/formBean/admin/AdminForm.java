package com.pharma.core.formBean.admin;

import java.sql.Date;
import java.sql.Timestamp;
/**
 * This class is a mcv form bean
 *
 */
public class AdminForm {

	private int adminId;
	private int pharmacyId;
	private String status;
	private String type;
	private String dateRegistrated;
	private String firstName;
	private String lastName;
	private String middleName;
	private String adminName;
	private String email;
	private String password;
	private String mobile;
	private String phone;
	
	// for Summary page list ids
	private String DT_RowId;
	private String pharmacyName;
	private String photoUrl;
	
	private int createdBy;
	private String createdUser;
	private Timestamp createdDate;
	private int lastUpdatedBy;
	private String lastUpdatedUser;
	private Timestamp lastUpdatedDate;
	
	
	// Admin Permissions 
	private String adminAccCreation;
	private String groupCreation;
	private String groupDirectorCreation;
	private String clinicCreation;
	private String physicianCreation;
	private String assistantCreation;
	private String physicianApproval;
	private String patientCreation;
	private String patientApproval;
	private String prescriptionCreation;
	private String controlSubstanceDocUpload;
	private String remoteFileUpload;
	private String hideUserPermissionList;
	
	
	public int getAdminId() {
		return adminId;
	}
	public void setAdminId(int adminId) {
		this.adminId = adminId;
	}
	public int getPharmacyId() {
		return pharmacyId;
	}
	public void setPharmacyId(int pharmacyId) {
		this.pharmacyId = pharmacyId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
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
	
	public String getAdminName() {
		return adminName;
	}
	public void setAdminName(String adminName) {
		this.adminName = adminName;
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
	public String getPharmacyName() {
		return pharmacyName;
	}
	public void setPharmacyName(String pharmacyName) {
		this.pharmacyName = pharmacyName;
	}
	public String getPhotoUrl() {
		return photoUrl;
	}
	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
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
	
	
	
	
	public String getAdminAccCreation() {
		return adminAccCreation;
	}
	public void setAdminAccCreation(String adminAccCreation) {
		this.adminAccCreation = adminAccCreation;
	}
	public String getGroupCreation() {
		return groupCreation;
	}
	public void setGroupCreation(String groupCreation) {
		this.groupCreation = groupCreation;
	}
	public String getGroupDirectorCreation() {
		return groupDirectorCreation;
	}
	public void setGroupDirectorCreation(String groupDirectorCreation) {
		this.groupDirectorCreation = groupDirectorCreation;
	}
	public String getClinicCreation() {
		return clinicCreation;
	}
	public void setClinicCreation(String clinicCreation) {
		this.clinicCreation = clinicCreation;
	}
	public String getPhysicianCreation() {
		return physicianCreation;
	}
	public void setPhysicianCreation(String physicianCreation) {
		this.physicianCreation = physicianCreation;
	}
	public String getAssistantCreation() {
		return assistantCreation;
	}
	public void setAssistantCreation(String assistantCreation) {
		this.assistantCreation = assistantCreation;
	}
	public String getPhysicianApproval() {
		return physicianApproval;
	}
	public void setPhysicianApproval(String physicianApproval) {
		this.physicianApproval = physicianApproval;
	}
	public String getPatientCreation() {
		return patientCreation;
	}
	public void setPatientCreation(String patientCreation) {
		this.patientCreation = patientCreation;
	}
	public String getPatientApproval() {
		return patientApproval;
	}
	public void setPatientApproval(String patientApproval) {
		this.patientApproval = patientApproval;
	}
	public String getPrescriptionCreation() {
		return prescriptionCreation;
	}
	public void setPrescriptionCreation(String prescriptionCreation) {
		this.prescriptionCreation = prescriptionCreation;
	}
	public String getControlSubstanceDocUpload() {
		return controlSubstanceDocUpload;
	}
	public void setControlSubstanceDocUpload(String controlSubstanceDocUpload) {
		this.controlSubstanceDocUpload = controlSubstanceDocUpload;
	}
	public String getRemoteFileUpload() {
		return remoteFileUpload;
	}
	public void setRemoteFileUpload(String remoteFileUpload) {
		this.remoteFileUpload = remoteFileUpload;
	}
	public String getHideUserPermissionList() {
		return hideUserPermissionList;
	}
	public void setHideUserPermissionList(String hideUserPermissionList) {
		this.hideUserPermissionList = hideUserPermissionList;
	}

}
