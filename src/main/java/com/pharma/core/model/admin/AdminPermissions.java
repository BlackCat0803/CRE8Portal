package com.pharma.core.model.admin;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * This class is a JPA entity class
 */
@Entity
@Table(name="admin_permissions")
public class AdminPermissions {

	private int id;
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

	@Id
	@Column(name = "admin_id", unique = true, nullable = false, length = 11)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@Column(name = "admin_acc_creation", nullable = true, length = 3)
	public String getAdminAccCreation() {
		return adminAccCreation;
	}
	public void setAdminAccCreation(String adminAccCreation) {
		this.adminAccCreation = adminAccCreation;
	}
	
	@Column(name = "group_creation", nullable = true, length = 3)
	public String getGroupCreation() {
		return groupCreation;
	}
	public void setGroupCreation(String groupCreation) {
		this.groupCreation = groupCreation;
	}
	
	@Column(name = "group_director_creation", nullable = true, length = 3)
	public String getGroupDirectorCreation() {
		return groupDirectorCreation;
	}
	public void setGroupDirectorCreation(String groupDirectorCreation) {
		this.groupDirectorCreation = groupDirectorCreation;
	}
	
	@Column(name = "clinic_creation", nullable = true, length = 3)
	public String getClinicCreation() {
		return clinicCreation;
	}
	public void setClinicCreation(String clinicCreation) {
		this.clinicCreation = clinicCreation;
	}
	
	@Column(name = "physician_creation", nullable = true, length = 3)
	public String getPhysicianCreation() {
		return physicianCreation;
	}
	public void setPhysicianCreation(String physicianCreation) {
		this.physicianCreation = physicianCreation;
	}
	
	@Column(name = "assistant_creation", nullable = true, length = 3)
	public String getAssistantCreation() {
		return assistantCreation;
	}
	public void setAssistantCreation(String assistantCreation) {
		this.assistantCreation = assistantCreation;
	}
	
	@Column(name = "physician_approval", nullable = true, length = 3)
	public String getPhysicianApproval() {
		return physicianApproval;
	}
	public void setPhysicianApproval(String physicianApproval) {
		this.physicianApproval = physicianApproval;
	}
	
	@Column(name = "patient_creation", nullable = true, length = 3)
	public String getPatientCreation() {
		return patientCreation;
	}
	public void setPatientCreation(String patientCreation) {
		this.patientCreation = patientCreation;
	}
	
	@Column(name = "patient_approval", nullable = true, length = 3)
	public String getPatientApproval() {
		return patientApproval;
	}
	public void setPatientApproval(String patientApproval) {
		this.patientApproval = patientApproval;
	}
	
	@Column(name = "prescription_creation", nullable = true, length = 3)
	public String getPrescriptionCreation() {
		return prescriptionCreation;
	}
	public void setPrescriptionCreation(String prescriptionCreation) {
		this.prescriptionCreation = prescriptionCreation;
	}
	
	@Column(name = "control_substance_doc_upload", nullable = true, length = 3)
	public String getControlSubstanceDocUpload() {
		return controlSubstanceDocUpload;
	}
	public void setControlSubstanceDocUpload(String controlSubstanceDocUpload) {
		this.controlSubstanceDocUpload = controlSubstanceDocUpload;
	}
	
	@Column(name = "remote_file_upload", nullable = true, length = 3)
	public String getRemoteFileUpload() {
		return remoteFileUpload;
	}
	public void setRemoteFileUpload(String remoteFileUpload) {
		this.remoteFileUpload = remoteFileUpload;
	}
	
}
