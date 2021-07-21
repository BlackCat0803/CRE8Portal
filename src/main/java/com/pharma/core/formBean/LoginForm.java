package com.pharma.core.formBean;
/**
 * This class is a mcv form bean
 *
 */
public class LoginForm {

	private String userName;
	private String password;
	private String type;
	
	// for session access purpose 
	private int userid;
	private String displayName;
	private String status;
	private String photoUrl;
	private int groupid;
	private String groupName;
	private String logoUrl;
	
	// for physician name with group name display 
	private String physicianGroupName="";
	private int physicianAssistantPhysicianId=0;
	private int physicianAssistantId=0;
	
	private String adminAccCreationPermission;
	private String groupCreationPermission;
	private String groupDirectorCreationPermission;
	private String clinicCreationPermission;
	private String physicianCreationPermission;
	private String assistantCreationPermission;
	private String physicianApprovalPermission;
	private String patientCreationPermission;
	private String patientApprovalPermission;
	private String prescriptionCreationPermission;
	private String controlSubstanceDocUploadPermission;
	private String remoteFileUploadPermission;

	private String tmpUserName;
	private String tmpType;
	
	private String rememberMe;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String username) {
		this.userName = username;
	}

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	
	
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
	public String getPhotoUrl() {
		return photoUrl;
	}
	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}
	public int getGroupid() {
		return groupid;
	}
	public void setGroupid(int groupid) {
		this.groupid = groupid;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getLogoUrl() {
		return logoUrl;
	}
	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}
	public String getPhysicianGroupName() {
		return physicianGroupName;
	}
	public void setPhysicianGroupName(String physicianGroupName) {
		this.physicianGroupName = physicianGroupName;
	}
	public int getPhysicianAssistantPhysicianId() {
		return physicianAssistantPhysicianId;
	}
	public void setPhysicianAssistantPhysicianId(int physicianAssistantPhysicianId) {
		this.physicianAssistantPhysicianId = physicianAssistantPhysicianId;
	}
	public int getPhysicianAssistantId() {
		return physicianAssistantId;
	}
	public void setPhysicianAssistantId(int physicianAssistantId) {
		this.physicianAssistantId = physicianAssistantId;
	}
	public String getAdminAccCreationPermission() {
		return adminAccCreationPermission;
	}
	public void setAdminAccCreationPermission(String adminAccCreationPermission) {
		this.adminAccCreationPermission = adminAccCreationPermission;
	}
	public String getGroupCreationPermission() {
		return groupCreationPermission;
	}
	public void setGroupCreationPermission(String groupCreationPermission) {
		this.groupCreationPermission = groupCreationPermission;
	}
	public String getGroupDirectorCreationPermission() {
		return groupDirectorCreationPermission;
	}
	public void setGroupDirectorCreationPermission(
			String groupDirectorCreationPermission) {
		this.groupDirectorCreationPermission = groupDirectorCreationPermission;
	}
	public String getClinicCreationPermission() {
		return clinicCreationPermission;
	}
	public void setClinicCreationPermission(String clinicCreationPermission) {
		this.clinicCreationPermission = clinicCreationPermission;
	}
	public String getPhysicianCreationPermission() {
		return physicianCreationPermission;
	}
	public void setPhysicianCreationPermission(String physicianCreationPermission) {
		this.physicianCreationPermission = physicianCreationPermission;
	}
	public String getAssistantCreationPermission() {
		return assistantCreationPermission;
	}
	public void setAssistantCreationPermission(String assistantCreationPermission) {
		this.assistantCreationPermission = assistantCreationPermission;
	}
	public String getPhysicianApprovalPermission() {
		return physicianApprovalPermission;
	}
	public void setPhysicianApprovalPermission(String physicianApprovalPermission) {
		this.physicianApprovalPermission = physicianApprovalPermission;
	}
	public String getPatientCreationPermission() {
		return patientCreationPermission;
	}
	public void setPatientCreationPermission(String patientCreationPermission) {
		this.patientCreationPermission = patientCreationPermission;
	}
	public String getPatientApprovalPermission() {
		return patientApprovalPermission;
	}
	public void setPatientApprovalPermission(String patientApprovalPermission) {
		this.patientApprovalPermission = patientApprovalPermission;
	}
	public String getPrescriptionCreationPermission() {
		return prescriptionCreationPermission;
	}
	public void setPrescriptionCreationPermission(
			String prescriptionCreationPermission) {
		this.prescriptionCreationPermission = prescriptionCreationPermission;
	}
	public String getControlSubstanceDocUploadPermission() {
		return controlSubstanceDocUploadPermission;
	}
	public void setControlSubstanceDocUploadPermission(
			String controlSubstanceDocUploadPermission) {
		this.controlSubstanceDocUploadPermission = controlSubstanceDocUploadPermission;
	}
	public String getRemoteFileUploadPermission() {
		return remoteFileUploadPermission;
	}
	public void setRemoteFileUploadPermission(String remoteFileUploadPermission) {
		this.remoteFileUploadPermission = remoteFileUploadPermission;
	}
	public String getTmpUserName() {
		return tmpUserName;
	}
	public void setTmpUserName(String tmpUserName) {
		this.tmpUserName = tmpUserName;
	}
	public String getTmpType() {
		return tmpType;
	}
	public void setTmpType(String tmpType) {
		this.tmpType = tmpType;
	}
	public String getRememberMe() {
		return rememberMe;
	}
	public void setRememberMe(String rememberMe) {
		this.rememberMe = rememberMe;
	}
	
}
