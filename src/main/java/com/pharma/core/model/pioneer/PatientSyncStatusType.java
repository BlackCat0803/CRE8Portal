package com.pharma.core.model.pioneer;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * This class is a JPA entity class
 */
@Entity
@Table(name="[pioneer.person.patientsyncstatustype]")
public class PatientSyncStatusType {

	private int syncStatusTypeID;
	private String syncStatusTypeText;
	

	@Id
	@Column(name = "syncStatusTypeID", unique = true, nullable = false)
	public int getSyncStatusTypeID() {
		return syncStatusTypeID;
	}
	public void setSyncStatusTypeID(int syncStatusTypeID) {
		this.syncStatusTypeID = syncStatusTypeID;
	}
	@Column(name = "syncStatusTypeText", nullable = false, length = 50)
	public String getSyncStatusTypeText() {
		return syncStatusTypeText;
	}
	public void setSyncStatusTypeText(String syncStatusTypeText) {
		this.syncStatusTypeText = syncStatusTypeText;
	}

}
