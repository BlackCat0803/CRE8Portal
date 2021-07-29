package com.pharma.core.formBean.patient;

import com.pharma.core.util.PharmacyUtil;

import java.sql.Date;
import java.sql.Timestamp;
/**
 * This class is a mcv form bean
 *
 */
public class PatientAccountForm {
	
	private int patientId;
	private int physicianId;
	private String patientCode;
	private String status = PharmacyUtil.statusProfileCompleted;
	private String firstName;
	private String lastName;
	private String middleName;
	private String patientName;
	private String email;
	private String password;
	private String mobile;
	private String phone;
	private Timestamp passwordResetDate;
	private int loginAttempts;
	private Timestamp loginLockedDate;
	private int securityQuestionNumber;
	private int securityQuestionNumber2;
	private String securityAnswer;
	private String securityAnswer2;
	private int forgotPasswordAttempts;
	private Timestamp forgotPwdLockedDate;
	
	private String notifyNo;
	private String rxNotify;
	private String refillRenewal;
	private String dateOfBirth;
	private String gender;
	private String allergies;
	private String otherMedications;
	private String medicalConditions;
	private String criticalComments;
	private String driversLicense;
	
	private String licenseExpDate;
	private String ssn;
	private String dateRegistered;
	private int updatedBy;
	private String updatedByType;
	private Date updatedDate;
	
	private String address;
	private String city;
	private String state;
	private String zipCode;
	private String country;
	private String driversLicenseState;
	private String DT_RowId;
	private String pregnancyprecaution;
	
	// Summary page purpose.
	private String physicianName;
	
	private String photoFile;
	
	private int groupId;
	private String groupName;
	private int rxNotifyProviderTypeID;
	private int syncStatus;
	private String syncStatusChangedDate;
	
	private String cardType;
	private String cardNumber;
	private String cardCvcNumber;
	private String cardHolderName;
	private String expMonth;
	private String expYear;	
	
	//private String clinicName; //Commented on jan 22,2018
	
	// for return to Prescription Page purpose (prescription page patient view button click)
	private String targetObject;
	private String prescriptionId;
	
	private int createdBy;
	private String createdUser;
	private Timestamp createdDate;
	private int lastUpdatedBy;
	private String lastUpdatedUser;
	private Timestamp lastUpdatedDate;
	private String addressInfo;	
	
	private String sendMailPermission;
	
	private String userLoginId;
	
	
	//Multiple select list box
	private String physicianList;
	private String selectedPhysicianId;
	private String physicianSelectedList;
	private String updatedHistory;
	
	private String billingZipCode;
	
	private String commEmail;
	private String commPhone;
	private String commTrackingNo;
	private String commShipped;
	private String commDelivered;
	private String commDeliveryExceptions;
	
	private String alternateId;
	private String alternateIdTypeId;
	private String alternateIdTypeText;
	
	
	private String dobDate;
	private String dobMonth;
	private String dobYear;
	
	private int groupPhysicianId;

	
	//Multiple select list box
	private String groupList;
	private String selectedGroupId;
	private String groupSelectedList;
	
	
	private String commentsUpdateInPioneer;
		
	public int getPatientId() {
		return patientId;
	}
	public void setPatientId(int patientId) {
		this.patientId = patientId;
	}
	public int getPhysicianId() {
		return physicianId;
	}
	public void setPhysicianId(int physicianId) {
		this.physicianId = physicianId;
	}
	public String getPatientCode() {
		return patientCode;
	}
	public void setPatientCode(String patientCode) {
		this.patientCode = patientCode;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	public String getPatientName() {
		return patientName;
	}
	public void setPatientName(String patientName) {
		this.patientName = patientName;
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
	public Timestamp getPasswordResetDate() {
		return passwordResetDate;
	}
	public void setPasswordResetDate(Timestamp passwordResetDate) {
		this.passwordResetDate = passwordResetDate;
	}
	public int getLoginAttempts() {
		return loginAttempts;
	}
	public void setLoginAttempts(int loginAttempts) {
		this.loginAttempts = loginAttempts;
	}
	public Timestamp getLoginLockedDate() {
		return loginLockedDate;
	}
	public void setLoginLockedDate(Timestamp loginLockedDate) {
		this.loginLockedDate = loginLockedDate;
	}
	public int getSecurityQuestionNumber() {
		return securityQuestionNumber;
	}
	public void setSecurityQuestionNumber(int securityQuestionNumber) {
		this.securityQuestionNumber = securityQuestionNumber;
	}
	public int getSecurityQuestionNumber2() {
		return securityQuestionNumber2;
	}
	public void setSecurityQuestionNumber2(int securityQuestionNumber2) {
		this.securityQuestionNumber2 = securityQuestionNumber2;
	}
	public String getSecurityAnswer() {
		return securityAnswer;
	}
	public void setSecurityAnswer(String securityAnswer) {
		this.securityAnswer = securityAnswer;
	}
	public String getSecurityAnswer2() {
		return securityAnswer2;
	}
	public void setSecurityAnswer2(String securityAnswer2) {
		this.securityAnswer2 = securityAnswer2;
	}
	public int getForgotPasswordAttempts() {
		return forgotPasswordAttempts;
	}
	public void setForgotPasswordAttempts(int forgotPasswordAttempts) {
		this.forgotPasswordAttempts = forgotPasswordAttempts;
	}
	public Timestamp getForgotPwdLockedDate() {
		return forgotPwdLockedDate;
	}
	public void setForgotPwdLockedDate(Timestamp forgotPwdLockedDate) {
		this.forgotPwdLockedDate = forgotPwdLockedDate;
	}
	public String getNotifyNo() {
		return notifyNo;
	}
	public void setNotifyNo(String notifyNo) {
		this.notifyNo = notifyNo;
	}
	public String getRxNotify() {
		return rxNotify;
	}
	public void setRxNotify(String rxNotify) {
		this.rxNotify = rxNotify;
	}
	public String getRefillRenewal() {
		return refillRenewal;
	}
	public void setRefillRenewal(String refillRenewal) {
		this.refillRenewal = refillRenewal;
	}
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getAllergies() {
		return allergies;
	}
	public void setAllergies(String allergies) {
		this.allergies = allergies;
	}
	public String getOtherMedications() {
		return otherMedications;
	}
	public void setOtherMedications(String otherMedications) {
		this.otherMedications = otherMedications;
	}
	public String getMedicalConditions() {
		return medicalConditions;
	}
	public void setMedicalConditions(String medicalConditions) {
		this.medicalConditions = medicalConditions;
	}
	public String getCriticalComments() {
		return criticalComments;
	}
	public void setCriticalComments(String criticalComments) {
		this.criticalComments = criticalComments;
	}
	public String getDriversLicense() {
		return driversLicense;
	}
	public void setDriversLicense(String driversLicense) {
		this.driversLicense = driversLicense;
	}
	public String getLicenseExpDate() {
		return licenseExpDate;
	}
	public void setLicenseExpDate(String licenseExpDate) {
		this.licenseExpDate = licenseExpDate;
	}
	public String getSsn() {
		return ssn;
	}
	public void setSsn(String ssn) {
		this.ssn = ssn;
	}
	public String getDateRegistered() {
		return dateRegistered;
	}
	public void setDateRegistered(String dateRegistered) {
		this.dateRegistered = dateRegistered;
	}
	public int getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(int updatedBy) {
		this.updatedBy = updatedBy;
	}
	public String getUpdatedByType() {
		return updatedByType;
	}
	public void setUpdatedByType(String updatedByType) {
		this.updatedByType = updatedByType;
	}
	public Date getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getDriversLicenseState() {
		return driversLicenseState;
	}
	public void setDriversLicenseState(String driversLicenseState) {
		this.driversLicenseState = driversLicenseState;
	}
	public String getDT_RowId() {
		return DT_RowId;
	}
	public void setDT_RowId(String dT_RowId) {
		DT_RowId = dT_RowId;
	}
	public String getPregnancyprecaution() {
		return pregnancyprecaution;
	}
	public void setPregnancyprecaution(String pregnancyprecaution) {
		this.pregnancyprecaution = pregnancyprecaution;
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
	public int getRxNotifyProviderTypeID() {
		return rxNotifyProviderTypeID;
	}
	public void setRxNotifyProviderTypeID(int rxNotifyProviderTypeID) {
		this.rxNotifyProviderTypeID = rxNotifyProviderTypeID;
	}
	public int getSyncStatus() {
		return syncStatus;
	}
	public void setSyncStatus(int syncStatus) {
		this.syncStatus = syncStatus;
	}
	public String getSyncStatusChangedDate() {
		return syncStatusChangedDate;
	}
	public void setSyncStatusChangedDate(String syncStatusChangedDate) {
		this.syncStatusChangedDate = syncStatusChangedDate;
	}
	public String getTargetObject() {
		return targetObject;
	}
	public void setTargetObject(String targetObject) {
		this.targetObject = targetObject;
	}
	public String getPrescriptionId() {
		return prescriptionId;
	}
	public void setPrescriptionId(String prescriptionId) {
		this.prescriptionId = prescriptionId;
	}
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	public String getCardCvcNumber() {
		return cardCvcNumber;
	}
	public void setCardCvcNumber(String cardCvcNumber) {
		this.cardCvcNumber = cardCvcNumber;
	}
	public String getCardHolderName() {
		return cardHolderName;
	}
	public void setCardHolderName(String cardHolderName) {
		this.cardHolderName = cardHolderName;
	}
	public String getExpMonth() {
		return expMonth;
	}
	public void setExpMonth(String expMonth) {
		this.expMonth = expMonth;
	}
	public String getExpYear() {
		return expYear;
	}
	public void setExpYear(String expYear) {
		this.expYear = expYear;
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
	public String getAddressInfo() {
		return addressInfo;
	}
	public void setAddressInfo(String addressInfo) {
		this.addressInfo = addressInfo;
	}
	//Commented on jan 22,2018
	/*public String getClinicName() {
		return clinicName;
	}
	public void setClinicName(String clinicName) {
		this.clinicName = clinicName;
	}*/
	public String getSendMailPermission() {
		return sendMailPermission;
	}
	public void setSendMailPermission(String sendMailPermission) {
		this.sendMailPermission = sendMailPermission;
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
	public String getUserLoginId() {
		return userLoginId;
	}
	public void setUserLoginId(String userLoginId) {
		this.userLoginId = userLoginId;
	}
	public String getUpdatedHistory() {
		return updatedHistory;
	}
	public void setUpdatedHistory(String updatedHistory) {
		this.updatedHistory = updatedHistory;
	}
	public String getBillingZipCode() {
		return billingZipCode;
	}
	public void setBillingZipCode(String billingZipCode) {
		this.billingZipCode = billingZipCode;
	}
	public String getCommEmail() {
		return commEmail;
	}
	public void setCommEmail(String commEmail) {
		this.commEmail = commEmail;
	}
	public String getCommPhone() {
		return commPhone;
	}
	public void setCommPhone(String commPhone) {
		this.commPhone = commPhone;
	}
	public String getCommTrackingNo() {
		return commTrackingNo;
	}
	public void setCommTrackingNo(String commTrackingNo) {
		this.commTrackingNo = commTrackingNo;
	}
	public String getCommShipped() {
		return commShipped;
	}
	public void setCommShipped(String commShipped) {
		this.commShipped = commShipped;
	}
	public String getCommDelivered() {
		return commDelivered;
	}
	public void setCommDelivered(String commDelivered) {
		this.commDelivered = commDelivered;
	}
	public String getCommDeliveryExceptions() {
		return commDeliveryExceptions;
	}
	public void setCommDeliveryExceptions(String commDeliveryExceptions) {
		this.commDeliveryExceptions = commDeliveryExceptions;
	}
	public String getAlternateId() {
		return alternateId;
	}
	public void setAlternateId(String alternateId) {
		this.alternateId = alternateId;
	}
	public String getAlternateIdTypeId() {
		return alternateIdTypeId;
	}
	public void setAlternateIdTypeId(String alternateIdTypeId) {
		this.alternateIdTypeId = alternateIdTypeId;
	}
	public String getAlternateIdTypeText() {
		return alternateIdTypeText;
	}
	public void setAlternateIdTypeText(String alternateIdTypeText) {
		this.alternateIdTypeText = alternateIdTypeText;
	}
	public String getDobDate() {
		return dobDate;
	}
	public void setDobDate(String dobDate) {
		this.dobDate = dobDate;
	}
	public String getDobMonth() {
		return dobMonth;
	}
	public void setDobMonth(String dobMonth) {
		this.dobMonth = dobMonth;
	}
	public String getDobYear() {
		return dobYear;
	}
	public void setDobYear(String dobYear) {
		this.dobYear = dobYear;
	}
	public String getGroupList() {
		return groupList;
	}
	public void setGroupList(String groupList) {
		this.groupList = groupList;
	}
	public String getSelectedGroupId() {
		return selectedGroupId;
	}
	public void setSelectedGroupId(String selectedGroupId) {
		this.selectedGroupId = selectedGroupId;
	}
	public String getGroupSelectedList() {
		return groupSelectedList;
	}
	public void setGroupSelectedList(String groupSelectedList) {
		this.groupSelectedList = groupSelectedList;
	}
	public int getGroupPhysicianId() {
		return groupPhysicianId;
	}
	public void setGroupPhysicianId(int groupPhysicianId) {
		this.groupPhysicianId = groupPhysicianId;
	}
	public String getCommentsUpdateInPioneer() {
		return commentsUpdateInPioneer;
	}
	public void setCommentsUpdateInPioneer(String commentsUpdateInPioneer) {
		this.commentsUpdateInPioneer = commentsUpdateInPioneer;
	}
}
