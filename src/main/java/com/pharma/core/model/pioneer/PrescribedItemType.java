package com.pharma.core.model.pioneer;

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
@Table(name="[pioneer.prescription.prescribeditemtype]")
public class PrescribedItemType {

	private int prescribedItemTypeID;
	private String prescribedItemTypeText;
	

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "prescribedItemTypeID", unique = true, nullable = false)
	public int getPrescribedItemTypeID() {
		return prescribedItemTypeID;
	}
	public void setPrescribedItemTypeID(int prescribedItemTypeID) {
		this.prescribedItemTypeID = prescribedItemTypeID;
	}
	@Column(name = "prescribedItemTypeText", nullable = false, length = 100)
	public String getPrescribedItemTypeText() {
		return prescribedItemTypeText;
	}
	public void setPrescribedItemTypeText(String prescribedItemTypeText) {
		this.prescribedItemTypeText = prescribedItemTypeText;
	}

}
