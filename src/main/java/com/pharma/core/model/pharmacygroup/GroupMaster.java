package com.pharma.core.model.pharmacygroup;

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
@Table(name= "group_master") 
public class GroupMaster {
	
	private int id;
	private String groupName;
	private String contactName;
	private String email;
	private String mobile; 
	private String status;
	
	private int lastUpdatedBy;
	private Timestamp lastUpdatedDate;
	private int createdBy;
	private Timestamp createdDate;
	
	private String createdUser;
	private String lastUpdatedUser;
	private String defaultgroup;
	private String newgroup;
	
	
	/** default constructor */
	
	public GroupMaster(){
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "group_id", unique = true, nullable = false)
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "group_name", nullable = false, length = 80)
	public String getGroupName() {
		return groupName;
	}


	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	@Column(name = "contact_name", nullable = false, length = 110)
	public String getContactName() {
		return contactName;
	}


	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	@Column(name = "email", nullable = false, length = 255)
	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "mobile", nullable = true, length = 15)
	public String getMobile() {
		return mobile;
	}


	public void setMobile(String mobile) {
		this.mobile = mobile;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	@Column(name = "last_updated_by", nullable = true)
	public int getLastUpdatedBy() {
		return lastUpdatedBy;
	}


	public void setLastUpdatedBy(int lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	@Column(name = "last_updated_date", nullable = true)
	public Timestamp getLastUpdatedDate() {
		return lastUpdatedDate;
	}


	public void setLastUpdatedDate(Timestamp lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	@Column(name = "created_by", nullable = true)
	public int getCreatedBy() {
		return createdBy;
	}


	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "created_date", nullable = true)
	public Timestamp getCreatedDate() {
		return createdDate;
	}


	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}


	@Column(name="created_user", nullable = true)
	public String getCreatedUser() {
		return createdUser;
	}
	public void setCreatedUser(String createdUser) {
		this.createdUser = createdUser;
	}
	
	@Column(name="last_updated_user", nullable = true)
	public String getLastUpdatedUser() {
		return lastUpdatedUser;
	}
	public void setLastUpdatedUser(String lastUpdatedUser) {
		this.lastUpdatedUser = lastUpdatedUser;
	}

	public String getDefaultgroup() {
		return defaultgroup;
	}

	public void setDefaultgroup(String defaultgroup) {
		this.defaultgroup = defaultgroup;
	}

	public String getNewgroup() {
		return newgroup;
	}

	public void setNewgroup(String newgroup) {
		this.newgroup = newgroup;
	}


	
}
