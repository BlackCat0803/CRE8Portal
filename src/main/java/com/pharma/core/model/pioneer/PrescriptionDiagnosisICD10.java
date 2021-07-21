package com.pharma.core.model.pioneer;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * This class is a JPA entity class
 */
@Entity
@Table(name="[pioneer.diagnosis.icd10]")
public class PrescriptionDiagnosisICD10 {

	private String icd10code;
	private int isheader;
	private String shortdescription;
	private String longdescription;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	public String getIcd10code() {
		return icd10code;
	}
	public void setIcd10code(String icd10code) {
		this.icd10code = icd10code;
	}
	public int getIsheader() {
		return isheader;
	}
	public void setIsheader(int isheader) {
		this.isheader = isheader;
	}
	public String getShortdescription() {
		return shortdescription;
	}
	public void setShortdescription(String shortdescription) {
		this.shortdescription = shortdescription;
	}
	public String getLongdescription() {
		return longdescription;
	}
	public void setLongdescription(String longdescription) {
		this.longdescription = longdescription;
	}

}
