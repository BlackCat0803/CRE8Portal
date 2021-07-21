package com.pharma.core.model.pioneer;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * This class is a JPA entity class
 */
@Entity
@Table(name="[pioneer.person.patientrxnotifyprovidertype]")
public class PatientRxNotifyProviderType {

	private int rxProviderNotifyTypeID;
	private String rxProviderNotifyTypeText;
	

	@Id
	@Column(name = "rxProviderNotifyTypeID", unique = true, nullable = false)
	public int getRxProviderNotifyTypeID() {
		return rxProviderNotifyTypeID;
	}
	public void setRxProviderNotifyTypeID(int rxProviderNotifyTypeID) {
		this.rxProviderNotifyTypeID = rxProviderNotifyTypeID;
	}
	@Column(name = "rxProviderNotifyTypeText", nullable = false, length = 50)
	public String getRxProviderNotifyTypeText() {
		return rxProviderNotifyTypeText;
	}
	public void setRxProviderNotifyTypeText(String rxProviderNotifyTypeText) {
		this.rxProviderNotifyTypeText = rxProviderNotifyTypeText;
	}

}
