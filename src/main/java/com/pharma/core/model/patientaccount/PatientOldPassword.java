package com.pharma.core.model.patientaccount;


import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * This class is a JPA entity class
 */
@Entity
@Table(name= "patient_old_password")
public class PatientOldPassword{

	private int rowId;
    private int patientId;
    private String password;
    private Date dateMoved;
    
 // Constructors

 	/** default constructor */
 	
 	public PatientOldPassword(){
 	}

	//Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "row_id", unique = true, nullable = false)
	public int getRowId() {
		return rowId;
	}

	public void setRowId(int rowId) {
		this.rowId = rowId;
	}

	@Column(name="patient_id", nullable = false)
	public int getPatientId() {
		return patientId;
	}

	public void setPatientId(int patientId) {
		this.patientId = patientId;
	}

	@Column(name = "password", nullable = false, length = 100)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "date_moved", nullable = false)
	public Date getDateMoved() {
		return dateMoved;
	}

	public void setDateMoved(Date dateMoved) {
		this.dateMoved = dateMoved;
	}
    
 
}
