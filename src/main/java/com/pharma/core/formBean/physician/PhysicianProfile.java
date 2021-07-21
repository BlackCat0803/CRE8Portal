package com.pharma.core.formBean.physician;

import java.sql.Timestamp;
import java.util.List;

import com.pharma.core.model.physician.PhysicianClinic;
/**
 * This class is a mcv form bean
 *
 */
public class PhysicianProfile {

	private int physicianId;
	private String firstName;
	private String lastName;
	private String middleName;
	private String physicianName;
	private String email;
	private String password;
	private String mobile;
	private String phone;
	private String city;
	private String state;
	private String zipCode;
	private Timestamp previousSignon;
	private String dateRegistrated;
	private String status;
	
	private Timestamp passwordResetDate;
	private int loginAttempts;
	private Timestamp loginLockedDate;
	private int securityQuestionNumber;
	private int securityQuestionNumber2;
	private String securityAnswer;
	private String securityAnswer2;
	private int forgotPasswordAttempts;
	private Timestamp forgotPwdLockedDate;
	
	private int prescriberType;
	private String prescriberGroup;
	private String fax;
	private String phone2;
	private String phone3;
	private String address1;
	private String country;
	private String physicianCode;
	private int approvedBy;
	private String approvedUser;
	private Timestamp approvedDate;
	private int deniedBy;
	private String deniedUser;
	private Timestamp deniedDate;
	
	private String address2;
	private String city2;
	private String state2;
	private String zipCode2;
	private String country2;
	
	private String website;
	private String marketer;
	private String dea;
	private String npi;
	private String upin;
	private String stateLicense;
	private String medicaid;
	private String dps;
	private String commEmail;
	private String commPhone;
	private String commFax;
	private String comments;
	private int reqBeforeDays;

	private String cardType;
	private String cardNumber;
	private String cardCvcNumber;
	private String cardHolderName;
	private String expMonth;
	private String expYear;
	private String billingZipCode;
	
	private String photoFile;

	//private ArrayList<Integer> groupId;
	private int groupId;
	private String groupName;
	
	// for Summary page list ids
	private String DT_RowId;
	private String clinicName;
	private String displayName;
	
	private String pioneerUid;
	private int pioneerResponse=-1;
	private String pioneerPrescriberTypeId;

	private int clinicId;
	
	// for multiple clinic selection.... (with comma separated)
	private String clinicIdString;
	private String clinicNameList;
	
	// for return to Prescription Page purpose (prescription page physician view button click)
	private String targetObject;
	private String prescriptionId;
	
	private int createdBy;
	private String createdUser;
	private Timestamp createdDate;
	private int lastUpdatedBy;
	private String lastUpdatedUser;
	private Timestamp lastUpdatedDate;

	private String logoFile;
	private String useGroupLogo;
	private String physicianNameWithGroupName;
	private String sendMailPermission;
	
	//Multiple select list box
	private String clinicList;
	private String selectedClinicId;
	private String clinicSelectedList;
	private String clinicSelectedStatusList;
	private String updatedHistory;
	
	List<PhysicianClinic> physicianClinicList;
	private String addressInfo;
	private String groupLogoFile;
	
	private String commTrackingNo;
	private String commShipped;
	private String commDelivered;
	private String commDeliveryExceptions;
	
	private String uploadedDocFilePurpose;  //  DEA License / State License / Other  types
	private String uploadedOtherDocFileName;
	private String uploadedDocExpiryDate;

	private int dropDownGroupId;
	
	/*********************  Physician PDF Purpose additional fields ****************/
	private String clinicAddress;
	private String clinicCity;
	private String clinicState;
	private String clinicZipcode;
	private String clinicCountry;
	private String clinicPhone;
	private String clinicFax;
	private String clinicEmail1;
	private String clinicEmail2;
	
	private String billingSameAsClinicAdd;
	private String billingAddress;
	private String billingCity;
	private String billingState;
	private String billingZipcode;
	private String billingCountry;
	private String preferredShippingMethod;
	
	private String clinicAuthorizedName1;
	private String clinicAuthorizedTitle1;
	private String clinicAuthorizedCell1;
	private String clinicAuthorizedName2;
	private String clinicAuthorizedTitle2;
	private String clinicAuthorizedCell2;
	
	private String paymentBillingName;
	private String paymentBillingAddress;
	private String paymentBillingCity;
	private String paymentBillingState;
	private String paymentBillingZipcode;
	private String paymentBillingCountry;
	
	private String referenceBillClinic;
	private String referenceBillPatient;
	private String referenceBillMayVary;
	
	private String creditCardSignDate;
	
	private String agreeDate;
	private String agreePrintName;

	private String base64ImgString;
	private int profilestep=0;
	private String screenflag="";
	private int newGroupId=0;
	private int otherPhysicianId;
	private String deniedStatusFlg="";
	private boolean eignedPdf=false;
	private String newClinicFlag="";
	
	private String commentsUpdateInPioneer;
	
	
	
	public int getPhysicianId() {
		return physicianId;
	}
	public void setPhysicianId(int physicianId) {
		this.physicianId = physicianId;
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
	public String getPhysicianName() {
		return physicianName;
	}
	public void setPhysicianName(String physicianName) {
		this.physicianName = physicianName;
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
	public Timestamp getPreviousSignon() {
		return previousSignon;
	}
	public void setPreviousSignon(Timestamp previousSignon) {
		this.previousSignon = previousSignon;
	}
	public String getDateRegistrated() {
		return dateRegistrated;
	}
	public void setDateRegistrated(String dateRegistrated) {
		this.dateRegistrated = dateRegistrated;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	public int getPrescriberType() {
		return prescriberType;
	}
	public void setPrescriberType(int prescriberType) {
		this.prescriberType = prescriberType;
	}
	public String getPrescriberGroup() {
		return prescriberGroup;
	}
	public void setPrescriberGroup(String prescriberGroup) {
		this.prescriberGroup = prescriberGroup;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getPhone2() {
		return phone2;
	}
	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}
	public String getPhone3() {
		return phone3;
	}
	public void setPhone3(String phone3) {
		this.phone3 = phone3;
	}
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getPhysicianCode() {
		return physicianCode;
	}
	public void setPhysicianCode(String physicianCode) {
		this.physicianCode = physicianCode;
	}
	public int getApprovedBy() {
		return approvedBy;
	}
	public void setApprovedBy(int approvedBy) {
		this.approvedBy = approvedBy;
	}
	public Timestamp getApprovedDate() {
		return approvedDate;
	}
	public void setApprovedDate(Timestamp approvedDate) {
		this.approvedDate = approvedDate;
	}
	public int getDeniedBy() {
		return deniedBy;
	}
	public void setDeniedBy(int deniedBy) {
		this.deniedBy = deniedBy;
	}
	public Timestamp getDeniedDate() {
		return deniedDate;
	}
	public void setDeniedDate(Timestamp deniedDate) {
		this.deniedDate = deniedDate;
	}
	public String getState2() {
		return state2;
	}
	public void setState2(String state2) {
		this.state2 = state2;
	}
	public String getClinicName() {
		return clinicName;
	}
	public void setClinicName(String clinicName) {
		this.clinicName = clinicName;
	}
	public String getDT_RowId() {
		return DT_RowId;
	}
	public void setDT_RowId(String dT_RowId) {
		DT_RowId = dT_RowId;
	}
	public String getAddress2() {
		return address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	public String getCity2() {
		return city2;
	}
	public void setCity2(String city2) {
		this.city2 = city2;
	}
	public String getZipCode2() {
		return zipCode2;
	}
	public void setZipCode2(String zipCode2) {
		this.zipCode2 = zipCode2;
	}
	public String getCountry2() {
		return country2;
	}
	public void setCountry2(String country2) {
		this.country2 = country2;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	public String getMarketer() {
		return marketer;
	}
	public void setMarketer(String marketer) {
		this.marketer = marketer;
	}
	public String getDea() {
		return dea;
	}
	public void setDea(String dea) {
		this.dea = dea;
	}
	public String getNpi() {
		return npi;
	}
	public void setNpi(String npi) {
		this.npi = npi;
	}
	public String getUpin() {
		return upin;
	}
	public void setUpin(String upin) {
		this.upin = upin;
	}
	public String getStateLicense() {
		return stateLicense;
	}
	public void setStateLicense(String stateLicense) {
		this.stateLicense = stateLicense;
	}
	public String getMedicaid() {
		return medicaid;
	}
	public void setMedicaid(String medicaid) {
		this.medicaid = medicaid;
	}
	public String getDps() {
		return dps;
	}
	public void setDps(String dps) {
		this.dps = dps;
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
	public String getCommFax() {
		return commFax;
	}
	public void setCommFax(String commFax) {
		this.commFax = commFax;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public int getReqBeforeDays() {
		return reqBeforeDays;
	}
	public void setReqBeforeDays(int reqBeforeDays) {
		this.reqBeforeDays = reqBeforeDays;
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
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getPhotoFile() {
		return photoFile;
	}
	public void setPhotoFile(String photoFile) {
		this.photoFile = photoFile;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	/*public ArrayList<Integer> getGroupId() {
		return groupId;
	}
	public void setGroupId(ArrayList<Integer> groupId) {
		this.groupId = groupId;
	}*/
	public int getGroupId() {
		return groupId;
	}
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	public String getPioneerUid() {
		return pioneerUid;
	}
	public void setPioneerUid(String pioneerUid) {
		this.pioneerUid = pioneerUid;
	}
	public int getPioneerResponse() {
		return pioneerResponse;
	}
	public void setPioneerResponse(int pioneerResponse) {
		this.pioneerResponse = pioneerResponse;
	}
	public String getPioneerPrescriberTypeId() {
		return pioneerPrescriberTypeId;
	}
	public void setPioneerPrescriberTypeId(String pioneerPrescriberTypeId) {
		this.pioneerPrescriberTypeId = pioneerPrescriberTypeId;
	}
	public int getClinicId() {
		return clinicId;
	}
	public void setClinicId(int clinicId) {
		this.clinicId = clinicId;
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
	public String getLogoFile() {
		return logoFile;
	}
	public void setLogoFile(String logoFile) {
		this.logoFile = logoFile;
	}
	public String getUseGroupLogo() {
		return useGroupLogo;
	}
	public void setUseGroupLogo(String useGroupLogo) {
		this.useGroupLogo = useGroupLogo;
	}
	public String getClinicIdString() {
		return clinicIdString;
	}
	public void setClinicIdString(String clinicIdString) {
		this.clinicIdString = clinicIdString;
	}
	public String getClinicNameList() {
		return clinicNameList;
	}
	public void setClinicNameList(String clinicNameList) {
		this.clinicNameList = clinicNameList;
	}
	public String getPhysicianNameWithGroupName() {
		return physicianNameWithGroupName;
	}
	public void setPhysicianNameWithGroupName(String physicianNameWithGroupName) {
		this.physicianNameWithGroupName = physicianNameWithGroupName;
	}
	public String getSendMailPermission() {
		return sendMailPermission;
	}
	public void setSendMailPermission(String sendMailPermission) {
		this.sendMailPermission = sendMailPermission;
	}
	public List<PhysicianClinic> getPhysicianClinicList() {
		return physicianClinicList;
	}
	public void setPhysicianClinicList(List<PhysicianClinic> physicianClinicList) {
		this.physicianClinicList = physicianClinicList;
	}
	public String getClinicList() {
		return clinicList;
	}
	public void setClinicList(String clinicList) {
		this.clinicList = clinicList;
	}
	public String getSelectedClinicId() {
		return selectedClinicId;
	}
	public void setSelectedClinicId(String selectedClinicId) {
		this.selectedClinicId = selectedClinicId;
	}
	public String getClinicSelectedList() {
		return clinicSelectedList;
	}
	public void setClinicSelectedList(String clinicSelectedList) {
		this.clinicSelectedList = clinicSelectedList;
	}
	public String getDeniedUser() {
		return deniedUser;
	}
	public void setDeniedUser(String deniedUser) {
		this.deniedUser = deniedUser;
	}
	public String getApprovedUser() {
		return approvedUser;
	}
	public void setApprovedUser(String approvedUser) {
		this.approvedUser = approvedUser;
	}
	public String getUpdatedHistory() {
		return updatedHistory;
	}
	public void setUpdatedHistory(String updatedHistory) {
		this.updatedHistory = updatedHistory;
	}
	public String getAddressInfo() {
		return addressInfo;
	}
	public void setAddressInfo(String addressInfo) {
		this.addressInfo = addressInfo;
	}
	public String getGroupLogoFile() {
		return groupLogoFile;
	}
	public void setGroupLogoFile(String groupLogoFile) {
		this.groupLogoFile = groupLogoFile;
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
	public String getBillingZipCode() {
		return billingZipCode;
	}
	public void setBillingZipCode(String billingZipCode) {
		this.billingZipCode = billingZipCode;
	}
	public String getUploadedDocFilePurpose() {
		return uploadedDocFilePurpose;
	}
	public void setUploadedDocFilePurpose(String uploadedDocFilePurpose) {
		this.uploadedDocFilePurpose = uploadedDocFilePurpose;
	}
	public String getUploadedOtherDocFileName() {
		return uploadedOtherDocFileName;
	}
	public void setUploadedOtherDocFileName(String uploadedOtherDocFileName) {
		this.uploadedOtherDocFileName = uploadedOtherDocFileName;
	}
	public String getUploadedDocExpiryDate() {
		return uploadedDocExpiryDate;
	}
	public void setUploadedDocExpiryDate(String uploadedDocExpiryDate) {
		this.uploadedDocExpiryDate = uploadedDocExpiryDate;
	}
	public String getClinicAddress() {
		return clinicAddress;
	}
	public void setClinicAddress(String clinicAddress) {
		this.clinicAddress = clinicAddress;
	}
	public String getClinicCity() {
		return clinicCity;
	}
	public void setClinicCity(String clinicCity) {
		this.clinicCity = clinicCity;
	}
	public String getClinicState() {
		return clinicState;
	}
	public void setClinicState(String clinicState) {
		this.clinicState = clinicState;
	}
	public String getClinicZipcode() {
		return clinicZipcode;
	}
	public void setClinicZipcode(String clinicZipcode) {
		this.clinicZipcode = clinicZipcode;
	}
	public String getClinicCountry() {
		return clinicCountry;
	}
	public void setClinicCountry(String clinicCountry) {
		this.clinicCountry = clinicCountry;
	}
	public String getClinicPhone() {
		return clinicPhone;
	}
	public void setClinicPhone(String clinicPhone) {
		this.clinicPhone = clinicPhone;
	}
	public String getClinicFax() {
		return clinicFax;
	}
	public void setClinicFax(String clinicFax) {
		this.clinicFax = clinicFax;
	}
	public String getClinicEmail1() {
		return clinicEmail1;
	}
	public void setClinicEmail1(String clinicEmail1) {
		this.clinicEmail1 = clinicEmail1;
	}
	public String getClinicEmail2() {
		return clinicEmail2;
	}
	public void setClinicEmail2(String clinicEmail2) {
		this.clinicEmail2 = clinicEmail2;
	}
	public String getBillingSameAsClinicAdd() {
		return billingSameAsClinicAdd;
	}
	public void setBillingSameAsClinicAdd(String billingSameAsClinicAdd) {
		this.billingSameAsClinicAdd = billingSameAsClinicAdd;
	}
	public String getBillingAddress() {
		return billingAddress;
	}
	public void setBillingAddress(String billingAddress) {
		this.billingAddress = billingAddress;
	}
	public String getBillingCity() {
		return billingCity;
	}
	public void setBillingCity(String billingCity) {
		this.billingCity = billingCity;
	}
	public String getBillingState() {
		return billingState;
	}
	public void setBillingState(String billingState) {
		this.billingState = billingState;
	}
	public String getBillingZipcode() {
		return billingZipcode;
	}
	public void setBillingZipcode(String billingZipcode) {
		this.billingZipcode = billingZipcode;
	}
	public String getBillingCountry() {
		return billingCountry;
	}
	public void setBillingCountry(String billingCountry) {
		this.billingCountry = billingCountry;
	}
	public String getPreferredShippingMethod() {
		return preferredShippingMethod;
	}
	public void setPreferredShippingMethod(String preferredShippingMethod) {
		this.preferredShippingMethod = preferredShippingMethod;
	}
	public String getClinicAuthorizedName1() {
		return clinicAuthorizedName1;
	}
	public void setClinicAuthorizedName1(String clinicAuthorizedName1) {
		this.clinicAuthorizedName1 = clinicAuthorizedName1;
	}
	public String getClinicAuthorizedTitle1() {
		return clinicAuthorizedTitle1;
	}
	public void setClinicAuthorizedTitle1(String clinicAuthorizedTitle1) {
		this.clinicAuthorizedTitle1 = clinicAuthorizedTitle1;
	}
	public String getClinicAuthorizedCell1() {
		return clinicAuthorizedCell1;
	}
	public void setClinicAuthorizedCell1(String clinicAuthorizedCell1) {
		this.clinicAuthorizedCell1 = clinicAuthorizedCell1;
	}
	public String getClinicAuthorizedName2() {
		return clinicAuthorizedName2;
	}
	public void setClinicAuthorizedName2(String clinicAuthorizedName2) {
		this.clinicAuthorizedName2 = clinicAuthorizedName2;
	}
	public String getClinicAuthorizedTitle2() {
		return clinicAuthorizedTitle2;
	}
	public void setClinicAuthorizedTitle2(String clinicAuthorizedTitle2) {
		this.clinicAuthorizedTitle2 = clinicAuthorizedTitle2;
	}
	public String getClinicAuthorizedCell2() {
		return clinicAuthorizedCell2;
	}
	public void setClinicAuthorizedCell2(String clinicAuthorizedCell2) {
		this.clinicAuthorizedCell2 = clinicAuthorizedCell2;
	}
	public String getPaymentBillingName() {
		return paymentBillingName;
	}
	public void setPaymentBillingName(String paymentBillingName) {
		this.paymentBillingName = paymentBillingName;
	}
	public String getPaymentBillingAddress() {
		return paymentBillingAddress;
	}
	public void setPaymentBillingAddress(String paymentBillingAddress) {
		this.paymentBillingAddress = paymentBillingAddress;
	}
	public String getPaymentBillingCity() {
		return paymentBillingCity;
	}
	public void setPaymentBillingCity(String paymentBillingCity) {
		this.paymentBillingCity = paymentBillingCity;
	}
	public String getPaymentBillingState() {
		return paymentBillingState;
	}
	public void setPaymentBillingState(String paymentBillingState) {
		this.paymentBillingState = paymentBillingState;
	}
	public String getPaymentBillingZipcode() {
		return paymentBillingZipcode;
	}
	public void setPaymentBillingZipcode(String paymentBillingZipcode) {
		this.paymentBillingZipcode = paymentBillingZipcode;
	}
	public String getPaymentBillingCountry() {
		return paymentBillingCountry;
	}
	public void setPaymentBillingCountry(String paymentBillingCountry) {
		this.paymentBillingCountry = paymentBillingCountry;
	}
	public String getReferenceBillClinic() {
		return referenceBillClinic;
	}
	public void setReferenceBillClinic(String referenceBillClinic) {
		this.referenceBillClinic = referenceBillClinic;
	}
	public String getReferenceBillPatient() {
		return referenceBillPatient;
	}
	public void setReferenceBillPatient(String referenceBillPatient) {
		this.referenceBillPatient = referenceBillPatient;
	}
	public String getReferenceBillMayVary() {
		return referenceBillMayVary;
	}
	public void setReferenceBillMayVary(String referenceBillMayVary) {
		this.referenceBillMayVary = referenceBillMayVary;
	}
	public String getCreditCardSignDate() {
		return creditCardSignDate;
	}
	public void setCreditCardSignDate(String creditCardSignDate) {
		this.creditCardSignDate = creditCardSignDate;
	}
	public String getAgreeDate() {
		return agreeDate;
	}
	public void setAgreeDate(String agreeDate) {
		this.agreeDate = agreeDate;
	}
	public String getAgreePrintName() {
		return agreePrintName;
	}
	public void setAgreePrintName(String agreePrintName) {
		this.agreePrintName = agreePrintName;
	}
	public String getBase64ImgString() {
		return base64ImgString;
	}
	public void setBase64ImgString(String base64ImgString) {
		this.base64ImgString = base64ImgString;
	}
	public String getClinicSelectedStatusList() {
		return clinicSelectedStatusList;
	}
	public void setClinicSelectedStatusList(String clinicSelectedStatusList) {
		this.clinicSelectedStatusList = clinicSelectedStatusList;
	}
	public int getProfilestep() {
		return profilestep;
	}
	public void setProfilestep(int profilestep) {
		this.profilestep = profilestep;
	}
	public String getScreenflag() {
		return screenflag;
	}
	public void setScreenflag(String screenflag) {
		this.screenflag = screenflag;
	}
	public int getNewGroupId() {
		return newGroupId;
	}
	public void setNewGroupId(int newGroupId) {
		this.newGroupId = newGroupId;
	}
	public int getOtherPhysicianId() {
		return otherPhysicianId;
	}
	public void setOtherPhysicianId(int otherPhysicianId) {
		this.otherPhysicianId = otherPhysicianId;
	}
	public int getDropDownGroupId() {
		return dropDownGroupId;
	}
	public void setDropDownGroupId(int dropDownGroupId) {
		this.dropDownGroupId = dropDownGroupId;
	}
	public String getDeniedStatusFlg() {
		return deniedStatusFlg;
	}
	public void setDeniedStatusFlg(String deniedStatusFlg) {
		this.deniedStatusFlg = deniedStatusFlg;
	}
	public boolean isEignedPdf() {
		return eignedPdf;
	}
	public void setEignedPdf(boolean eignedPdf) {
		this.eignedPdf = eignedPdf;
	}
	public String getNewClinicFlag() {
		return newClinicFlag;
	}
	public void setNewClinicFlag(String newClinicFlag) {
		this.newClinicFlag = newClinicFlag;
	}
	public String getCommentsUpdateInPioneer() {
		return commentsUpdateInPioneer;
	}
	public void setCommentsUpdateInPioneer(String commentsUpdateInPioneer) {
		this.commentsUpdateInPioneer = commentsUpdateInPioneer;
	}
	
	
}
