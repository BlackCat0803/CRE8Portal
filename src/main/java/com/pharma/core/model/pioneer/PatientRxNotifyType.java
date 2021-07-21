package com.pharma.core.model.pioneer;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * This class is a JPA entity class
 */
@Entity
@Table(name="[pioneer.person.patientrxnotifytype]")
public class PatientRxNotifyType {

	private int rxNotifyTypeID;
	private String rxNotifyTypeText;
	

	@Id
	@Column(name = "rxNotifyTypeID", unique = true, nullable = false)
	public int getRxNotifyTypeID() {
		return rxNotifyTypeID;
	}
	public void setRxNotifyTypeID(int rxNotifyTypeID) {
		this.rxNotifyTypeID = rxNotifyTypeID;
	}
	@Column(name = "rxNotifyTypeText", nullable = false, length = 50)
	public String getRxNotifyTypeText() {
		return rxNotifyTypeText;
	}
	public void setRxNotifyTypeText(String rxNotifyTypeText) {
		this.rxNotifyTypeText = rxNotifyTypeText;
	}

}
