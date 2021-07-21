package com.pharma.core.model.patientaccount;

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
@Table(name="patient_update_history")
public class PatientUpdateHistory {

	private int id;
	private int patientId;
	private int updatedBy;
	private String updatedUser;
	private Timestamp updatedDate; 
	private String description;
	private int approvedBy;
	private String approvedUser;
	private Timestamp approvedDate;
	
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@Column(name = "patient_id", nullable = false)
	public int getPatientId() {
		return patientId;
	}
	public void setPatientId(int patientId) {
		this.patientId = patientId;
	}

	@Column(name = "updated_by", nullable = true)
	public int getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(int updatedBy) {
		this.updatedBy = updatedBy;
	}
	
	@Column(name="updated_user_type", nullable = true, length=20)
	public String getUpdatedUser() {
		return updatedUser;
	}
	public void setUpdatedUser(String updatedUser) {
		this.updatedUser = updatedUser;
	}
	
	@Column(name = "updated_date", nullable = true)
	public Timestamp getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}
	
	@Column(name="description", nullable = true)
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Column(name = "approved_by", nullable = true)
	public int getApprovedBy() {
		return approvedBy;
	}
	public void setApprovedBy(int approvedBy) {
		this.approvedBy = approvedBy;
	}
	
	@Column(name="approved_user_type", nullable = true, length=20)
	public String getApprovedUser() {
		return approvedUser;
	}
	public void setApprovedUser(String approvedUser) {
		this.approvedUser = approvedUser;
	}
	
	@Column(name = "approved_date", nullable = true)
	public Timestamp getApprovedDate() {
		return approvedDate;
	}
	public void setApprovedDate(Timestamp approvedDate) {
		this.approvedDate = approvedDate;
	} 
	
}
