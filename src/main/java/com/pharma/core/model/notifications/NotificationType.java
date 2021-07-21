package com.pharma.core.model.notifications;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * This class is a JPA entity class
 */
@Entity
@Table(name="notificationType")
public class NotificationType {

	private int id;
	private String notificationName;
	private String screenName;
	private String adminNotification;
	private String physicianNotification;
	private String expiryCondition;
	private String delFlag;
	
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "notificationTypeId", unique = true, nullable = false)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@Column(name = "notificationName")
	public String getNotificationName() {
		return notificationName;
	}
	public void setNotificationName(String notificationName) {
		this.notificationName = notificationName;
	}
	
	@Column(name = "screenName", nullable = false, length=50)
	public String getScreenName() {
		return screenName;
	}
	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}
	
	
	@Column(name = "expiryCondition", nullable = false, length=150)
	public String getExpiryCondition() {
		return expiryCondition;
	}
	public void setExpiryCondition(String expiryCondition) {
		this.expiryCondition = expiryCondition;
	}
	
	@Column(name = "delFlag", nullable = false, length=1)
	public String getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
	public String getAdminNotification() {
		return adminNotification;
	}
	public void setAdminNotification(String adminNotification) {
		this.adminNotification = adminNotification;
	}
	public String getPhysicianNotification() {
		return physicianNotification;
	}
	public void setPhysicianNotification(String physicianNotification) {
		this.physicianNotification = physicianNotification;
	}

}
	