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
@Table(name="[pioneer.prescription.origintype]")
public class PrescriptionOriginType {

	private int origintypeid;
	private String origintypetext;
	private int ncpdpcode;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	public int getOrigintypeid() {
		return origintypeid;
	}
	public void setOrigintypeid(int origintypeid) {
		this.origintypeid = origintypeid;
	}
	public String getOrigintypetext() {
		return origintypetext;
	}
	public void setOrigintypetext(String origintypetext) {
		this.origintypetext = origintypetext;
	}
	public int getNcpdpcode() {
		return ncpdpcode;
	}
	public void setNcpdpcode(int ncpdpcode) {
		this.ncpdpcode = ncpdpcode;
	}

}
