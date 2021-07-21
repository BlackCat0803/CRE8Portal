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
@Table(name="[pioneer.prescription.rxtransactionstatustype]")
public class PrescriptionRxTransactionStatusType {

	private String rxtransactionstatustypeid;
	private String rxtransactionstatustypetext;
	
	private int statustypeenum;
	private String defaultnextstatustypeid;
	private int showrxtransactioninqueue;
	private int removefrominventory;
	private int readytobesold;
	private int isactive;
	private int workfloworder;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	public String getRxtransactionstatustypeid() {
		return rxtransactionstatustypeid;
	}
	public void setRxtransactionstatustypeid(String rxtransactionstatustypeid) {
		this.rxtransactionstatustypeid = rxtransactionstatustypeid;
	}
	public String getRxtransactionstatustypetext() {
		return rxtransactionstatustypetext;
	}
	public void setRxtransactionstatustypetext(String rxtransactionstatustypetext) {
		this.rxtransactionstatustypetext = rxtransactionstatustypetext;
	}
	public int getStatustypeenum() {
		return statustypeenum;
	}
	public void setStatustypeenum(int statustypeenum) {
		this.statustypeenum = statustypeenum;
	}
	public String getDefaultnextstatustypeid() {
		return defaultnextstatustypeid;
	}
	public void setDefaultnextstatustypeid(String defaultnextstatustypeid) {
		this.defaultnextstatustypeid = defaultnextstatustypeid;
	}
	public int getShowrxtransactioninqueue() {
		return showrxtransactioninqueue;
	}
	public void setShowrxtransactioninqueue(int showrxtransactioninqueue) {
		this.showrxtransactioninqueue = showrxtransactioninqueue;
	}
	public int getRemovefrominventory() {
		return removefrominventory;
	}
	public void setRemovefrominventory(int removefrominventory) {
		this.removefrominventory = removefrominventory;
	}
	public int getReadytobesold() {
		return readytobesold;
	}
	public void setReadytobesold(int readytobesold) {
		this.readytobesold = readytobesold;
	}
	public int getIsactive() {
		return isactive;
	}
	public void setIsactive(int isactive) {
		this.isactive = isactive;
	}
	public int getWorkfloworder() {
		return workfloworder;
	}
	public void setWorkfloworder(int workfloworder) {
		this.workfloworder = workfloworder;
	}

	
}
