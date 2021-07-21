package com.pharma.core.formBean.notifications;

import java.sql.Timestamp;
/**
 * This class is a mcv form bean
 *
 */
public class NotificationForm {

	private int notifyId;
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
	
	// for Summary page list ids
	private String DT_RowId;

	public int getNotifyId() {
		return notifyId;
	}

	public void setNotifyId(int notifyId) {
		this.notifyId = notifyId;
	}

	public int getNotifyTypeId() {
		return notifyTypeId;
	}

	public void setNotifyTypeId(int notifyTypeId) {
		this.notifyTypeId = notifyTypeId;
	}

	public int getNotifyRecordId() {
		return notifyRecordId;
	}

	public void setNotifyRecordId(int notifyRecordId) {
		this.notifyRecordId = notifyRecordId;
	}

	public String getNotifyRecordType() {
		return notifyRecordType;
	}

	public void setNotifyRecordType(String notifyRecordType) {
		this.notifyRecordType = notifyRecordType;
	}

	public String getNotificationMessage() {
		return notificationMessage;
	}

	public void setNotificationMessage(String notificationMessage) {
		this.notificationMessage = notificationMessage;
	}

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	public Timestamp getNotifiedDate() {
		return notifiedDate;
	}

	public void setNotifiedDate(Timestamp notifiedDate) {
		this.notifiedDate = notifiedDate;
	}

	public int getNotifyBy() {
		return notifyBy;
	}

	public void setNotifyBy(int notifyBy) {
		this.notifyBy = notifyBy;
	}

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

	public String getDT_RowId() {
		return DT_RowId;
	}

	public void setDT_RowId(String dT_RowId) {
		DT_RowId = dT_RowId;
	}

}
