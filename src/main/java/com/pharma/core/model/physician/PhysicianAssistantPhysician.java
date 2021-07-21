package com.pharma.core.model.physician;

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
@Table(name="physician_assistant_physician")
public class PhysicianAssistantPhysician {

	private int id;
	private int physicianAssistantId;
	private int physicianId;
	private String delFlag;
	private int createdBy;
	private Timestamp createdDate;
	private String createdUserType;
	private int updatedBy;
	private Timestamp updatedDate;
	private String updatedUserType;

	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	

	@Column(name = "physician_assistant_id", nullable = false)
	public int getPhysicianAssistantId() {
		return physicianAssistantId;
	}
	public void setPhysicianAssistantId(int physicianAssistantId) {
		this.physicianAssistantId = physicianAssistantId;
	}
	
	
	@Column(name = "physician_id", nullable = false)
	public int getPhysicianId() {
		return physicianId;
	}
	public void setPhysicianId(int physicianId) {
		this.physicianId = physicianId;
	}
	
	@Column(name = "delFlag", nullable = false, length = 1)
	public String getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
	
	@Column(name = "created_by", nullable = true)
	public int getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}
	
	@Column(name="created_date", nullable = true)
	public Timestamp getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}
	
	@Column(name = "created_user_type", nullable = false, length = 10)
	public String getCreatedUserType() {
		return createdUserType;
	}
	public void setCreatedUserType(String createdUserType) {
		this.createdUserType = createdUserType;
	}
	
	@Column(name = "updated_by", nullable = true)
	public int getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(int updatedBy) {
		this.updatedBy = updatedBy;
	}
	
	@Column(name="updated_date", nullable = true)
	public Timestamp getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}
	
	@Column(name = "updated_user_type", nullable = false, length = 10)
	public String getUpdatedUserType() {
		return updatedUserType;
	}
	public void setUpdatedUserType(String updatedUserType) {
		this.updatedUserType = updatedUserType;
	}
	
}
