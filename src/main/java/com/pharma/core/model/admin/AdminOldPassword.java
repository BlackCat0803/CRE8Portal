package com.pharma.core.model.admin;


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
@Table(name= "admin_old_password")
public class AdminOldPassword{

	private int rowId;
    private int adminId;
    private String password;
    private Date dateMoved;
    
 // Constructors

 	/** default constructor */
 	
 	public AdminOldPassword(){
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

	@Column(name="admin_id", nullable = false)
	public int getAdminId() {
		return adminId;
	}

	public void setAdminId(int adminId) {
		this.adminId = adminId;
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
