package com.pharma.core.model.pharmacygroup;

import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import static javax.persistence.GenerationType.IDENTITY;
/**
 * This class is a JPA entity class
 */
@Entity
@Table(name= "group_director") 
public class GroupDirector {
	
	private int id;
	private int groupId;
	private String status;
	private Date dateRegistrated;
	private String firstName;
	private String lastName;
	private String middleName;
	private String groupDirectorName;
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
	
	
	private int createdBy;
	private String createdUser;
	private Timestamp createdDate;
	private int lastUpdatedBy;
	private String lastUpdatedUser;
	private Timestamp lastUpdatedDate;
	
		
	/** default constructor */
	
	public GroupDirector(){
	}
	
	//Property accessors
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "group_director_id", unique = true, nullable = false)
	public int getId() {
		return id;
	}
	public void setId(int groupDirectorId) {
		this.id = groupDirectorId;
	}
	
	
	@Column(name="group_id", nullable = false)
	public int getGroupId() {
		return groupId;
	}
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	@Column(name="date_of_registration", nullable = false)
	public Date getDateRegistrated() {
		return dateRegistrated;
	}
	public void setDateRegistrated(Date dateRegistrated) {
		this.dateRegistrated = dateRegistrated;
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
	
	@Column(name="middle_name", nullable = true, length=10)
	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	@Column(name = "name", nullable = false, length = 110)
	public String getGroupDirectorName() {
		return groupDirectorName;
	}
	public void setGroupDirectorName(String groupDirectorName) {
		this.groupDirectorName = groupDirectorName;
	}
	
	@Column(name = "email", nullable = false, length = 255)
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
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
	
	
}
