package com.pharma.core.model.patientaccount;

import static javax.persistence.GenerationType.IDENTITY;

import java.sql.Date;




import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.pharma.core.converters.JPACryptoConverter;

/**
 * This class is a JPA entity class
 */
@Entity
@Table(name="patient_profile")
public class PatientAccount {
	
	private int id;
	//private int physicianId; //Commented on jan 22,2018
	private String patientCode;
	private String status;
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
	private Date dateOfBirth;
	private String gender;
	private String allergies;
	private String otherMedications;
	private String medicalConditions;
	private String criticalComments;
	private String driversLicense;
	
	private Date licenseExpDate;
	private String ssn;
	private Date dateRegistered;
	private int updatedBy;
	private String updatedByType;
	private Date updatedDate;
	
	private String address;
	private String city;
	private String state;
	private String zipCode;
	private String country;
	private String driversLicenseState;
	private String pregnancyprecaution;
	
	private String pioneerUid;
	private int pioneerResponse=-1;
	private int rxNotifyProviderTypeID;
	private int syncStatus;
	private Date syncStatusChangedDate;
	
	
	private String cardType;
	private String cardNumber;
	private String cardCvcNumber;
	private String cardHolderName;
	private String expMonth;
	private String expYear;
	
	private int createdBy;
	private String createdUser;
	private Timestamp createdDate;
	private int lastUpdatedBy;
	private String lastUpdatedUser;
	private Timestamp lastUpdatedDate;

	private String sendMailPermission;
	//private int groupId;//commented on Feb 7,2018
	
	private int approvedBy;
	private String approvedUser;
	private Timestamp approvedDate;
	private int deniedBy;
	private String deniedUser;
	private Timestamp deniedDate;
	
	private String userLoginId;
	
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
	
	
	private String commentsUpdateInPioneer;
	
		//Property accessors
		@Id
		@GeneratedValue(strategy = IDENTITY)
		@Column(name = "patient_id", unique = true, nullable = false)
		
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		
		
		//Commented on jan 22,2018
		/*@Column(name = "physician_id", nullable = false)
		public int getPhysicianId() {
			return physicianId;
		}
		public void setPhysicianId(int physicianId) {
			this.physicianId = physicianId;
		}*/
		
		
		@Column(name = "patient_code", nullable = true)
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
		
		
		
		@Column(name = "first_name", nullable = false, length = 50)
		public String getFirstName() {
			return firstName;
		}
		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}
		
		@Column(name = "last_name", nullable = false, length = 50)
		public String getLastName() {
			return lastName;
		}
		public void setLastName(String lastName) {
			this.lastName = lastName;
		}
		
		@Column(name="middle_name", nullable = true, length=50)
		public String getMiddleName() {
			return middleName;
		}
		public void setMiddleName(String middleName) {
			this.middleName = middleName;
		}
		@Column(name = "patient_name", nullable = false, length = 110)
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
		
		@Column(name = "mobile")
		public String getMobile() {
			return mobile;
		}
		public void setMobile(String mobile) {
			this.mobile = mobile;
		}
		
		@Column(name = "phone")
		public String getPhone() {
			return phone;
		}
		public void setPhone(String phone) {
			this.phone = phone;
		}
		
		
		@Column(name = "password_reset_date", nullable = true)
		public Timestamp getPasswordResetDate() {
			return passwordResetDate;
		}
		public void setPasswordResetDate(Timestamp passwordResetDate) {
			this.passwordResetDate = passwordResetDate;
		}
		
		@Column(name = "login_attempts", nullable = true)
		public int getLoginAttempts() {
			return loginAttempts;
		}
		public void setLoginAttempts(int loginAttempts) {
			this.loginAttempts = loginAttempts;
		}
		
		
		@Column(name = "login_locked_date", nullable = true)
		public Timestamp getLoginLockedDate() {
			return loginLockedDate;
		}
		public void setLoginLockedDate(Timestamp loginLockedDate) {
			this.loginLockedDate = loginLockedDate;
		}
		
		@Column(name = "security_question_number", nullable = true)
		public int getSecurityQuestionNumber() {
			return securityQuestionNumber;
		}
		public void setSecurityQuestionNumber(int securityQuestionNumber) {
			this.securityQuestionNumber = securityQuestionNumber;
		}
		
		@Column(name = "security_question_number2", nullable = true)
		public int getSecurityQuestionNumber2() {
			return securityQuestionNumber2;
		}
		public void setSecurityQuestionNumber2(int securityQuestionNumber2) {
			this.securityQuestionNumber2 = securityQuestionNumber2;
		}
		
		@Column(name = "security_answer", nullable = true)
		public String getSecurityAnswer() {
			return securityAnswer;
		}
		public void setSecurityAnswer(String securityAnswer) {
			this.securityAnswer = securityAnswer;
		}
		
		@Column(name ="security_answer2", nullable = true)
		public String getSecurityAnswer2() {
			return securityAnswer2;
		}
		public void setSecurityAnswer2(String securityAnswer2) {
			this.securityAnswer2 = securityAnswer2;
		}
		
		@Column(name="forgot_password_attempts", nullable = true)
		public int getForgotPasswordAttempts() {
			return forgotPasswordAttempts;
		}
		public void setForgotPasswordAttempts(int forgotPasswordAttempts) {
			this.forgotPasswordAttempts = forgotPasswordAttempts;
		}
		
		@Column(name="forgot_pwd_locked_date", nullable = true)	
		public Timestamp getForgotPwdLockedDate() {
			return forgotPwdLockedDate;
		}
		public void setForgotPwdLockedDate(Timestamp forgotPwdLockedDate) {
			this.forgotPwdLockedDate = forgotPwdLockedDate;
		}
	
	@Column(name="notify_no", nullable = true, length=50)
	public String getNotifyNo() {
		return notifyNo;
	}
	public void setNotifyNo(String notifyNo) {
		this.notifyNo = notifyNo;
	}
	
	@Column(name="rx_notify", nullable = true, length=50)
	public String getRxNotify() {
		return rxNotify;
	}
	public void setRxNotify(String rxNotify) {
		this.rxNotify = rxNotify;
	}
	
	@Column(name="refill_renewal", nullable = true, length=50)
	public String getRefillRenewal() {
		return refillRenewal;
	}
	public void setRefillRenewal(String refillRenewal) {
		this.refillRenewal = refillRenewal;
	}
	
	@Column(name = "date_of_birth", nullable = true)
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(Date dateOfBirth) {
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
	
	@Column(name="other_medications", nullable = true)
	public String getOtherMedications() {
		return otherMedications;
	}
	public void setOtherMedications(String otherMedications) {
		this.otherMedications = otherMedications;
	}
	
	@Column(name="medical_conditions", nullable = true)
	public String getMedicalConditions() {
		return medicalConditions;
	}
	public void setMedicalConditions(String medicalConditions) {
		this.medicalConditions = medicalConditions;
	}
	
	@Column(name="critical_comments", nullable = true)
	public String getCriticalComments() {
		return criticalComments;
	}
	public void setCriticalComments(String criticalComments) {
		this.criticalComments = criticalComments;
	}
	
	@Column(name="drivers_license", nullable = true, length=50)
	public String getDriversLicense() {
		return driversLicense;
	}
	public void setDriversLicense(String driversLicense) {
		this.driversLicense = driversLicense;
	}
	
	@Column(name = "license_expiration_date", nullable = true)
	public Date getLicenseExpDate() {
		return licenseExpDate;
	}
	public void setLicenseExpDate(Date licenseExpDate) {
		this.licenseExpDate = licenseExpDate;
	}
	public String getSsn() {
		return ssn;
	}
	public void setSsn(String ssn) {
		this.ssn = ssn;
	}
	
	@Column(name = "Date_registered", nullable = true)
	public Date getDateRegistered() {
		return dateRegistered;
	}
	public void setDateRegistered(Date dateRegistered) {
		this.dateRegistered = dateRegistered;
	}
	

	@Column(name = "updated_by", nullable = true)
	public int getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(int updatedBy) {
		this.updatedBy = updatedBy;
	}
	
	@Column(name="updated_by_type", nullable = true, length=50)
	public String getUpdatedByType() {
		return updatedByType;
	}
	public void setUpdatedByType(String updatedByType) {
		this.updatedByType = updatedByType;
	}
	
	
	@Column(name = "updated_date", nullable = true)
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
	@Column(name = "zip_code", nullable = true)
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
	@Column(name = "drivers_license_state", nullable = true)
	public String getDriversLicenseState() {
		return driversLicenseState;
	}
	public void setDriversLicenseState(String driversLicenseState) {
		this.driversLicenseState = driversLicenseState;
	}
	public String getPregnancyprecaution() {
		return pregnancyprecaution;
	}
	public void setPregnancyprecaution(String pregnancyprecaution) {
		this.pregnancyprecaution = pregnancyprecaution;
	}
	
	@Column(name = "pioneer_uid", nullable = true)
	public String getPioneerUid() {
		return pioneerUid;
	}
	public void setPioneerUid(String pioneerUid) {
		this.pioneerUid = pioneerUid;
	}
	
	@Column(name = "pioneer_response", nullable = true)
	public int getPioneerResponse() {
		return pioneerResponse;
	}
	public void setPioneerResponse(int pioneerResponse) {
		this.pioneerResponse = pioneerResponse;
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
	public Date getSyncStatusChangedDate() {
		return syncStatusChangedDate;
	}
	public void setSyncStatusChangedDate(Date syncStatusChangedDate) {
		this.syncStatusChangedDate = syncStatusChangedDate;
	}
	@Column(columnDefinition= "LONGBLOB", name = "card_type")
	@Convert(converter = JPACryptoConverter.class)
	//@Column(name = "card_type", nullable = true)
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	@Column(columnDefinition= "LONGBLOB", name = "card_number")
	@Convert(converter = JPACryptoConverter.class)
	//@Column(name = "card_number", nullable = true, length=25)
	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	@Column(columnDefinition= "LONGBLOB", name = "card_cvc_number")
	@Convert(converter = JPACryptoConverter.class)
	//@Column(name = "card_cvc_number", nullable = true, length=4)
	public String getCardCvcNumber() {
		return cardCvcNumber;
	}
	public void setCardCvcNumber(String cardCvcNumber) {
		this.cardCvcNumber = cardCvcNumber;
	}
	@Column(columnDefinition= "LONGBLOB", name = "card_holder_name")
	@Convert(converter = JPACryptoConverter.class)
	//@Column(name = "card_holder_name", nullable = true, length=120)
	public String getCardHolderName() {
		return cardHolderName;
	}
	public void setCardHolderName(String cardHolderName) {
		this.cardHolderName = cardHolderName;
	}
	@Column(columnDefinition= "LONGBLOB", name = "card_expiry_month")
	@Convert(converter = JPACryptoConverter.class)
	//@Column(name = "card_expiry_month", nullable = true, length=2)
	public String getExpMonth() {
		return expMonth;
	}
	public void setExpMonth(String expMonth) {
		this.expMonth = expMonth;
	}
	@Column(columnDefinition= "LONGBLOB", name = "card_expiry_year")
	@Convert(converter = JPACryptoConverter.class)
	//@Column(name = "card_expiry_year", nullable = true, length=4)
	public String getExpYear() {
		return expYear;
	}
	public void setExpYear(String expYear) {
		this.expYear = expYear;
	}

	@Column(name="created_by", nullable = true)
	public int getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name="created_user", nullable = true)
	public String getCreatedUser() {
		return createdUser;
	}
	public void setCreatedUser(String createdUser) {
		this.createdUser = createdUser;
	}

	@Column(name="created_date", nullable = true)
	public Timestamp getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	@Column(name="last_updated_by", nullable = true)
	public int getLastUpdatedBy() {
		return lastUpdatedBy;
	}
	public void setLastUpdatedBy(int lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	@Column(name="last_updated_user", nullable = true)
	public String getLastUpdatedUser() {
		return lastUpdatedUser;
	}
	public void setLastUpdatedUser(String lastUpdatedUser) {
		this.lastUpdatedUser = lastUpdatedUser;
	}

	@Column(name="last_updated_date", nullable = true)
	public Timestamp getLastUpdatedDate() {
		return lastUpdatedDate;
	}
	public void setLastUpdatedDate(Timestamp lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}
	
	@Column(name = "send_mail_permission", nullable = true)
	public String getSendMailPermission() {
		return sendMailPermission;
	}
	public void setSendMailPermission(String sendMailPermission) {
		this.sendMailPermission = sendMailPermission;
	}
	
	@Column(name="approved_by", nullable = true)
	public int getApprovedBy() {
		return approvedBy;
	}
	public void setApprovedBy(int approvedBy) {
		this.approvedBy = approvedBy;
	}
	
	@Column(name="approved_user", nullable = true)
	public String getApprovedUser() {
		return approvedUser;
	}
	public void setApprovedUser(String approvedUser) {
		this.approvedUser = approvedUser;
	}
	
	@Column(name="approved_date", nullable = true)
	public Timestamp getApprovedDate() {
		return approvedDate;
	}
	public void setApprovedDate(Timestamp approvedDate) {
		this.approvedDate = approvedDate;
	}
	
	@Column(name="denied_by", nullable = true)
	public int getDeniedBy() {
		return deniedBy;
	}
	public void setDeniedBy(int deniedBy) {
		this.deniedBy = deniedBy;
	}
	
	@Column(name="denied_user", nullable = true)
	public String getDeniedUser() {
		return deniedUser;
	}
	public void setDeniedUser(String deniedUser) {
		this.deniedUser = deniedUser;
	}
	
	@Column(name="denied_date", nullable = true)
	public Timestamp getDeniedDate() {
		return deniedDate;
	}
	public void setDeniedDate(Timestamp deniedDate) {
		this.deniedDate = deniedDate;
	}


	@Column(name="user_login_id", nullable = false)
	public String getUserLoginId() {
		return userLoginId;
	}
	public void setUserLoginId(String userLoginId) {
		this.userLoginId = userLoginId;
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
	
	
	@Column(name = "alternate_id", nullable = true)
	public String getAlternateId() {
		return alternateId;
	}
	public void setAlternateId(String alternateId) {
		this.alternateId = alternateId;
	}
	
	@Column(name = "alternate_id_type_id", nullable = true)
	public String getAlternateIdTypeId() {
		return alternateIdTypeId;
	}
	public void setAlternateIdTypeId(String alternateIdTypeId) {
		this.alternateIdTypeId = alternateIdTypeId;
	}
	
	@Column(name = "alternate_id_type_text", nullable = true)
	public String getAlternateIdTypeText() {
		return alternateIdTypeText;
	}
	public void setAlternateIdTypeText(String alternateIdTypeText) {
		this.alternateIdTypeText = alternateIdTypeText;
	}
	
	public String getCommentsUpdateInPioneer() {
		return commentsUpdateInPioneer;
	}
	public void setCommentsUpdateInPioneer(String commentsUpdateInPioneer) {
		this.commentsUpdateInPioneer = commentsUpdateInPioneer;
	}
	
}
