package com.pharma.core.model.prescription;

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
@Table(name="prescription_status")
public class PrescriptionStatus {

	private int id;
	private String status;
	private int displayorder;
	private String delflag;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "status_id", unique = true, nullable = false)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@Column(name = "status", nullable = false)
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
	@Column(name = "displayorder", nullable = false)
	public int getDisplayorder() {
		return displayorder;
	}
	public void setDisplayorder(int displayorder) {
		this.displayorder = displayorder;
	}
	
	@Column(name = "delflag", nullable = false)
	public String getDelflag() {
		return delflag;
	}
	public void setDelflag(String delflag) {
		this.delflag = delflag;
	}
	
}
