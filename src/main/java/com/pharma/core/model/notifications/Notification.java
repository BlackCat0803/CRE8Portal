package com.pharma.core.model.notifications;

import static javax.persistence.GenerationType.IDENTITY;

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
@Table(name="notification")
public class Notification {

	private int id;
	private int notifyTypeId;
	private int notifyRecordId;
	private String notifyRecordType;
	private String notificationMessage;
	private String delFlag;
	private Timestamp notifiedDate;
	private int notifyBy;
	private String notifyUserType;
	
	private String patientName;
	private String physicianName;
	private String rxNo;
	private String referenceNo;
	
	private Timestamp expiryDate;
	private int clinicId;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "notificationId", unique = true, nullable = false)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@Column(name = "notify_type_id", nullable = false)
	public int getNotifyTypeId() {
		return notifyTypeId;
	}
	public void setNotifyTypeId(int notifyTypeId) {
		this.notifyTypeId = notifyTypeId;
	}
	
	
	@Column(name = "notify_record_id", nullable = false)
	public int getNotifyRecordId() {
		return notifyRecordId;
	}
	public void setNotifyRecordId(int notifyRecordId) {
		this.notifyRecordId = notifyRecordId;
	}
	
	@Column(name = "notify_record_type", nullable = false)
	public String getNotifyRecordType() {
		return notifyRecordType;
	}
	public void setNotifyRecordType(String notifyRecordType) {
		this.notifyRecordType = notifyRecordType;
	}
	
	@Column(name = "notificationMessage", nullable = true)
	public String getNotificationMessage() {
		return notificationMessage;
	}
	public void setNotificationMessage(String notificationMessage) {
		this.notificationMessage = notificationMessage;
	}
	
	@Column(name = "delFlag", nullable = false, length=1)
	public String getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
	
	
	@Column(name="notified_date", nullable = true)
	public Timestamp getNotifiedDate() {
		return notifiedDate;
	}
	public void setNotifiedDate(Timestamp notifiedDate) {
		this.notifiedDate = notifiedDate;
	}
	
	@Column(name="notified_by", nullable = true)
	public int getNotifyBy() {
		return notifyBy;
	}
	public void setNotifyBy(int notifyBy) {
		this.notifyBy = notifyBy;
	}
	
	@Column(name="notified_user_type", nullable = true)
	public String getNotifyUserType() {
		return notifyUserType;
	}
	public void setNotifyUserType(String notifyUserType) {
		this.notifyUserType = notifyUserType;
	}
	public String getPatientName() {
		return patientName;
	}
	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}
	public String getPhysicianName() {
		return physicianName;
	}
	public void setPhysicianName(String physicianName) {
		this.physicianName = physicianName;
	}
	public String getRxNo() {
		return rxNo;
	}
	public void setRxNo(String rxNo) {
		this.rxNo = rxNo;
	}
	public String getReferenceNo() {
		return referenceNo;
	}
	public void setReferenceNo(String referenceNo) {
		this.referenceNo = referenceNo;
	}
	
	@Column(name="expiry_date", nullable = true)
	public Timestamp getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(Timestamp expiryDate) {
		this.expiryDate = expiryDate;
	}
	public int getClinicId() {
		return clinicId;
	}
	public void setClinicId(int clinicId) {
		this.clinicId = clinicId;
	}
	
}
