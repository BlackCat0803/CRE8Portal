package com.pharma.core.model.pioneer;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * This class is a JPA entity class
 */
@Entity
@Table(name="[pioneer.prescription.sig]")
public class PrescriptionSigCodes {

	private String sigid;
	private String code;
	private String englishtext;
	private String spanishtext;
	private String legacynumber;
	private double quantity;
	private double frequency;
	private int totaldayssupply;
	private int calculatedayssupply;
	private String sigscheduledataid;
	private String othertext;
	private int sigtype;
	private String changedat;
	private Date changedonutc;
	private Date bulkinsertedonutc;
	private int syncdeleted;
	private String othertext2;
	private String othertext3;
	private String othertext4;
	private String othertext5;
	private String othertext6;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	public String getSigid() {
		return sigid;
	}
	public void setSigid(String sigid) {
		this.sigid = sigid;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getEnglishtext() {
		return englishtext;
	}
	public void setEnglishtext(String englishtext) {
		this.englishtext = englishtext;
	}
	public String getSpanishtext() {
		return spanishtext;
	}
	public void setSpanishtext(String spanishtext) {
		this.spanishtext = spanishtext;
	}
	public String getLegacynumber() {
		return legacynumber;
	}
	public void setLegacynumber(String legacynumber) {
		this.legacynumber = legacynumber;
	}
	public double getQuantity() {
		return quantity;
	}
	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}
	public double getFrequency() {
		return frequency;
	}
	public void setFrequency(double frequency) {
		this.frequency = frequency;
	}
	public int getTotaldayssupply() {
		return totaldayssupply;
	}
	public void setTotaldayssupply(int totaldayssupply) {
		this.totaldayssupply = totaldayssupply;
	}
	public int getCalculatedayssupply() {
		return calculatedayssupply;
	}
	public void setCalculatedayssupply(int calculatedayssupply) {
		this.calculatedayssupply = calculatedayssupply;
	}
	public String getSigscheduledataid() {
		return sigscheduledataid;
	}
	public void setSigscheduledataid(String sigscheduledataid) {
		this.sigscheduledataid = sigscheduledataid;
	}
	public String getOthertext() {
		return othertext;
	}
	public void setOthertext(String othertext) {
		this.othertext = othertext;
	}
	public int getSigtype() {
		return sigtype;
	}
	public void setSigtype(int sigtype) {
		this.sigtype = sigtype;
	}
	public String getChangedat() {
		return changedat;
	}
	public void setChangedat(String changedat) {
		this.changedat = changedat;
	}
	public Date getChangedonutc() {
		return changedonutc;
	}
	public void setChangedonutc(Date changedonutc) {
		this.changedonutc = changedonutc;
	}
	public Date getBulkinsertedonutc() {
		return bulkinsertedonutc;
	}
	public void setBulkinsertedonutc(Date bulkinsertedonutc) {
		this.bulkinsertedonutc = bulkinsertedonutc;
	}
	public int getSyncdeleted() {
		return syncdeleted;
	}
	public void setSyncdeleted(int syncdeleted) {
		this.syncdeleted = syncdeleted;
	}
	public String getOthertext2() {
		return othertext2;
	}
	public void setOthertext2(String othertext2) {
		this.othertext2 = othertext2;
	}
	public String getOthertext3() {
		return othertext3;
	}
	public void setOthertext3(String othertext3) {
		this.othertext3 = othertext3;
	}
	public String getOthertext4() {
		return othertext4;
	}
	public void setOthertext4(String othertext4) {
		this.othertext4 = othertext4;
	}
	public String getOthertext5() {
		return othertext5;
	}
	public void setOthertext5(String othertext5) {
		this.othertext5 = othertext5;
	}
	public String getOthertext6() {
		return othertext6;
	}
	public void setOthertext6(String othertext6) {
		this.othertext6 = othertext6;
	}

}
