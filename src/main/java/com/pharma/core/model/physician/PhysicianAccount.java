package com.pharma.core.model.physician;

import static javax.persistence.GenerationType.IDENTITY;

import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * This class is a JPA entity class
 */
@Entity
@Table(name= "phy_info")
public class PhysicianAccount {
	
	private int id;
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
	private Date dateRegistrated;
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
	
	
	
	
	private String secondaryAddress1;
	private String secondaryCity;
	private String secondaryState;
	private String secondaryZipCode;
	private String secondaryCountry;
	
	private int clinicId;
	
	private String pioneerUid;
	private int pioneerResponse=-1;
	
	private int createdBy;
	private String createdUser;
	private Timestamp createdDate;
	private int lastUpdatedBy;
	private String lastUpdatedUser;
	private Timestamp lastUpdatedDate;
	private String useGroupLogo;
	private String physicianNameWithGroupName;
	private String defaultphysician;
	private int profilestep=0;
	
	/** default constructor */
	
	public PhysicianAccount(){
	}
	
	//Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "physician_id", unique = true, nullable = false)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	
	@Column(name = "physician_name", nullable = false, length = 110)
	public String getPhysicianName() {
		return physicianName;
	}
	public void setPhysicianName(String physicianName) {
		this.physicianName = physicianName;
	}
	
	@Column(name = "email", nullable = false, length = 255)
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	// @Type(type = "encryptedStr")
	@Column(name = "password", nullable = false, length = 100)
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Column(name = "mobile", nullable = false, length = 15)
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	@Column(name = "phone", nullable = true, length = 15)
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@Column(name = "city", nullable = false, length = 25)
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
	@Column(name = "state", nullable = false, length = 3)
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	@Column(name = "zip_code", nullable = true, length = 12)
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	
	@Column(name = "previous_signon", nullable = true)
	public Timestamp getPreviousSignon() {
		return previousSignon;
	}
	public void setPreviousSignon(Timestamp previousSignon) {
		this.previousSignon = previousSignon;
	}
	
	@Column(name = "status", nullable = false, length = 15)
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	
	@Column(name="prescriber_type", nullable = true)	
	public int getPrescriberType() {
		return prescriberType;
	}
	public void setPrescriberType(int prescriberType) {
		this.prescriberType = prescriberType;
	}
	
	@Column(name="prescriber_group", nullable = true)
	public String getPrescriberGroup() {
		return prescriberGroup;
	}
	public void setPrescriberGroup(String prescriberGroup) {
		this.prescriberGroup = prescriberGroup;
	}
	
	@Column(name="fax", nullable = true)
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	
	@Column(name="phone2", nullable = true)
	public String getPhone2() {
		return phone2;
	}
	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}
	
	@Column(name="phone3", nullable = true)
	public String getPhone3() {
		return phone3;
	}
	public void setPhone3(String phone3) {
		this.phone3 = phone3;
	}
	
	@Column(name="address1", nullable = true)
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	
	@Column(name="country", nullable = true)
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}

	@Column(name="physician_code", nullable = true)
	public String getPhysicianCode() {
		return physicianCode;
	}

	public void setPhysicianCode(String physicianCode) {
		this.physicianCode = physicianCode;
	}

	@Column(name="middle_name", nullable = true, length=50)
	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
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

	@Column(name="date_of_registration", nullable = true)
	public Date getDateRegistrated() {
		return dateRegistrated;
	}

	public void setDateRegistrated(Date dateRegistrated) {
		this.dateRegistrated = dateRegistrated;
	}

	@Column(name = "secondary_address1", nullable = true)
	public String getSecondaryAddress1() {
		return secondaryAddress1;
	}

	public void setSecondaryAddress1(String secondaryAddress1) {
		this.secondaryAddress1 = secondaryAddress1;
	}

	@Column(name = "secondary_city", nullable = true, length = 25)
	public String getSecondaryCity() {
		return secondaryCity;
	}

	public void setSecondaryCity(String secondaryCity) {
		this.secondaryCity = secondaryCity;
	}

	@Column(name = "secondary_state", nullable = true, length = 3)
	public String getSecondaryState() {
		return secondaryState;
	}

	public void setSecondaryState(String secondaryState) {
		this.secondaryState = secondaryState;
	}

	@Column(name = "secondary_zip_code", nullable = true, length = 12)
	public String getSecondaryZipCode() {
		return secondaryZipCode;
	}

	public void setSecondaryZipCode(String secondaryZipCode) {
		this.secondaryZipCode = secondaryZipCode;
	}

	@Column(name = "secondary_country", nullable = true, length = 50)
	public String getSecondaryCountry() {
		return secondaryCountry;
	}

	public void setSecondaryCountry(String secondaryCountry) {
		this.secondaryCountry = secondaryCountry;
	}

	/*@Column(name="group_id", nullable = false)
	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}*/

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

	@Column(name="clinic_id", nullable = false)
	public int getClinicId() {
		return clinicId;
	}

	public void setClinicId(int clinicId) {
		this.clinicId = clinicId;
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

	public String getUseGroupLogo() {
		return useGroupLogo;
	}

	public void setUseGroupLogo(String useGroupLogo) {
		this.useGroupLogo = useGroupLogo;
	}

	@Column(name="name_with_group_name", nullable = false)
	public String getPhysicianNameWithGroupName() {
		return physicianNameWithGroupName;
	}

	public void setPhysicianNameWithGroupName(String physicianNameWithGroupName) {
		this.physicianNameWithGroupName = physicianNameWithGroupName;
	}

	public String getDefaultphysician() {
		return defaultphysician;
	}

	public void setDefaultphysician(String defaultphysician) {
		this.defaultphysician = defaultphysician;
	}

	public int getProfilestep() {
		return profilestep;
	}

	public void setProfilestep(int profilestep) {
		this.profilestep = profilestep;
	}
	
}
