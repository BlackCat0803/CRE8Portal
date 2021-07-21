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
@Table(name="[pioneer.item.dispensingunit]")
public class PrescriptionItemDispensingUnit {

	private int dispensingunitid;
	private String dispensingunittext;
	private int ncpdpdispensingunitid;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	public int getDispensingunitid() {
		return dispensingunitid;
	}
	public void setDispensingunitid(int dispensingunitid) {
		this.dispensingunitid = dispensingunitid;
	}
	public String getDispensingunittext() {
		return dispensingunittext;
	}
	public void setDispensingunittext(String dispensingunittext) {
		this.dispensingunittext = dispensingunittext;
	}
	public int getNcpdpdispensingunitid() {
		return ncpdpdispensingunitid;
	}
	public void setNcpdpdispensingunitid(int ncpdpdispensingunitid) {
		this.ncpdpdispensingunitid = ncpdpdispensingunitid;
	}

}
