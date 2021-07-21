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
@Table(name="[pioneer.prescription.rxstatustype]")
public class PrescriptionRxStatusType {

	private String rxstatustypeid;
	private String rxstatustypetext;
	
	private int isactivestatus;
	private int statustypeenum;
	private int keepinqueue;
	private int showrxinqueue;
	private int isreadytobesold;
	private int includeonmar;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	public String getRxstatustypeid() {
		return rxstatustypeid;
	}
	public void setRxstatustypeid(String rxstatustypeid) {
		this.rxstatustypeid = rxstatustypeid;
	}
	public String getRxstatustypetext() {
		return rxstatustypetext;
	}
	public void setRxstatustypetext(String rxstatustypetext) {
		this.rxstatustypetext = rxstatustypetext;
	}
	public int getIsactivestatus() {
		return isactivestatus;
	}
	public void setIsactivestatus(int isactivestatus) {
		this.isactivestatus = isactivestatus;
	}
	public int getStatustypeenum() {
		return statustypeenum;
	}
	public void setStatustypeenum(int statustypeenum) {
		this.statustypeenum = statustypeenum;
	}
	public int getKeepinqueue() {
		return keepinqueue;
	}
	public void setKeepinqueue(int keepinqueue) {
		this.keepinqueue = keepinqueue;
	}
	public int getShowrxinqueue() {
		return showrxinqueue;
	}
	public void setShowrxinqueue(int showrxinqueue) {
		this.showrxinqueue = showrxinqueue;
	}
	public int getIsreadytobesold() {
		return isreadytobesold;
	}
	public void setIsreadytobesold(int isreadytobesold) {
		this.isreadytobesold = isreadytobesold;
	}
	public int getIncludeonmar() {
		return includeonmar;
	}
	public void setIncludeonmar(int includeonmar) {
		this.includeonmar = includeonmar;
	}

}
