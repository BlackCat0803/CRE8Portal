package com.pharma.core.model;

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
@Table(name="security_question")
public class SecurityQuestion {

	private int rowId;
	private String securityquestion;
	private String status;
	
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "row_id", unique = true, nullable = false)
	public int getRowId() {
		return rowId;
	}
	public void setRowId(int rowId) {
		this.rowId = rowId;
	}
	
	@Column(name = "securityquestion", nullable = false)
	public String getSecurityquestion() {
		return securityquestion;
	}
	public void setSecurityquestion(String securityquestion) {
		this.securityquestion = securityquestion;
	}
	
	@Column(name = "status", nullable = false,length=10)
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	
}
