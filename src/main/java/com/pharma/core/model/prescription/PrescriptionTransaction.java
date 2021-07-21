package com.pharma.core.model.prescription;

import static javax.persistence.GenerationType.IDENTITY;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * This class is a JPA entity class
 */

@Entity
@Table(name="prescription_transaction")
public class PrescriptionTransaction {

	private int id;
	private int prescriptionId;
	private String rxNumber;
	private String rxStatus;
	private Date writtenDate;
	private Date expireDate;
	private String type;
	private String origin;
	private String itemid;
	private String itemname;
	private int medicationid;
	private String medicationdescription;
	private int quantity;
	private String unitName;
	private int refills;
	private String dwa;
	private String auto;
	private String prn;
	private String icd10;
	private String sigCodes;
	private int refillsRemaining;
	private int refillsFilled;
	private Date lastFilledDate;
	private String trackingNumber;
	private String delFlag;
	private int daysSupply;
	
	private String futureFill;
	private String priortyType;
	private String scriptType;
	private String previousRxNumber;
	private String prescriberOrderNumber;
	private String controlSubstance;
	
	private String dispensedItemId;
	private String dispensedItemName;
	
	private int prescriptionNo;
	private String cre8PrescriptionNo; 
	private String comments;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "prescription_tran_id", unique = true, nullable = false)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@Column(name = "prescription_id", nullable = false)
	public int getPrescriptionId() {
		return prescriptionId;
	}
	public void setPrescriptionId(int prescriptionId) {
		this.prescriptionId = prescriptionId;
	}
	
	@Column(name = "written_date", nullable = false)
	public Date getWrittenDate() {
		return writtenDate;
	}
	public void setWrittenDate(Date writtenDate) {
		this.writtenDate = writtenDate;
	}
	
	@Column(name = "expire_date", nullable = false)
	public Date getExpireDate() {
		return expireDate;
	}
	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	
	
	
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	@Column(name = "unit_name", nullable = false)
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	
	
	public int getRefills() {
		return refills;
	}
	public void setRefills(int refills) {
		this.refills = refills;
	}
	
	@Column(name = "refills_remaining", nullable = false)
	public int getRefillsRemaining() {
		return refillsRemaining;
	}
	public void setRefillsRemaining(int refillsRemaining) {
		this.refillsRemaining = refillsRemaining;
	}
	
	@Column(name = "refills_filled", nullable = false)
	public int getRefillsFilled() {
		return refillsFilled;
	}
	public void setRefillsFilled(int refillsFilled) {
		this.refillsFilled = refillsFilled;
	}
	
	@Column(name = "last_filled_date", nullable = true)
	public Date getLastFilledDate() {
		return lastFilledDate;
	}
	public void setLastFilledDate(Date lastFilledDate) {
		this.lastFilledDate = lastFilledDate;
	}
	
	
	public String getDwa() {
		return dwa;
	}
	public void setDwa(String dwa) {
		this.dwa = dwa;
	}
	
	
	public String getAuto() {
		return auto;
	}
	public void setAuto(String auto) {
		this.auto = auto;
	}
	
	
	public String getPrn() {
		return prn;
	}
	public void setPrn(String prn) {
		this.prn = prn;
	}
	
	
	public String getIcd10() {
		return icd10;
	}
	public void setIcd10(String icd10) {
		this.icd10 = icd10;
	}
	
	@Column(name = "sig_codes", nullable = true)
	public String getSigCodes() {
		return sigCodes;
	}
	public void setSigCodes(String sigCodes) {
		this.sigCodes = sigCodes;
	}
	
	@Column(name = "tracking_number", nullable = true)
	public String getTrackingNumber() {
		return trackingNumber;
	}
	public void setTrackingNumber(String trackingNumber) {
		this.trackingNumber = trackingNumber;
	}
	
	@Column(name = "rx_number", nullable = true)
	public String getRxNumber() {
		return rxNumber;
	}
	public void setRxNumber(String rxNumber) {
		this.rxNumber = rxNumber;
	}
	
	@Column(name = "rx_status", nullable = true)
	public String getRxStatus() {
		return rxStatus;
	}
	public void setRxStatus(String rxStatus) {
		this.rxStatus = rxStatus;
	}
	
	@Column(name = "delFlag", nullable = false)
	public String getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
	
	@Column(name = "days_supply", nullable = false)
	public int getDaysSupply() {
		return daysSupply;
	}
	public void setDaysSupply(int daysSupply) {
		this.daysSupply = daysSupply;
	}
	
	
	@Column(name = "future_fill", nullable = true)
	public String getFutureFill() {
		return futureFill;
	}
	public void setFutureFill(String futureFill) {
		this.futureFill = futureFill;
	}
	
	@Column(name = "priorty_type", nullable = true)
	public String getPriortyType() {
		return priortyType;
	}
	public void setPriortyType(String priortyType) {
		this.priortyType = priortyType;
	}
	
	@Column(name = "script_type", nullable = true)
	public String getScriptType() {
		return scriptType;
	}
	public void setScriptType(String scriptType) {
		this.scriptType = scriptType;
	}
	
	@Column(name = "previous_rx_number", nullable = true)
	public String getPreviousRxNumber() {
		return previousRxNumber;
	}
	public void setPreviousRxNumber(String previousRxNumber) {
		this.previousRxNumber = previousRxNumber;
	}
	
	@Column(name = "prescriber_order_number", nullable = true)
	public String getPrescriberOrderNumber() {
		return prescriberOrderNumber;
	}
	public void setPrescriberOrderNumber(String prescriberOrderNumber) {
		this.prescriberOrderNumber = prescriberOrderNumber;
	}
	
	@Column(name = "control_substance", nullable = true, length=1)
	public String getControlSubstance() {
		return controlSubstance;
	}
	public void setControlSubstance(String controlSubstance) {
		this.controlSubstance = controlSubstance;
	}
	
	@Column(name = "dispensed_itemid", nullable = true)
	
	public String getDispensedItemId() {
		return dispensedItemId;
	}
	public void setDispensedItemId(String dispensedItemId) {
		this.dispensedItemId = dispensedItemId;
	}
	
	@Column(name = "dispensed_itemname", nullable = true)
	
	public String getDispensedItemName() {
		return dispensedItemName;
	}
	public void setDispensedItemName(String dispensedItemName) {
		this.dispensedItemName = dispensedItemName;
	}
	
	@Column(name = "prescription_no", unique = true, nullable = false)
	
	public int getPrescriptionNo() {
		return prescriptionNo;
	}
	public void setPrescriptionNo(int prescriptionNo) {
		this.prescriptionNo = prescriptionNo;
	}
	@Column(name = "cre8_prescription_no", nullable = false)
	public String getCre8PrescriptionNo() {
		return cre8PrescriptionNo;
	}
	public void setCre8PrescriptionNo(String cre8PrescriptionNo) {
		this.cre8PrescriptionNo = cre8PrescriptionNo;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getItemid() {
		return itemid;
	}
	public void setItemid(String itemid) {
		this.itemid = itemid;
	}
	public String getItemname() {
		return itemname;
	}
	public void setItemname(String itemname) {
		this.itemname = itemname;
	}
	public int getMedicationid() {
		return medicationid;
	}
	public void setMedicationid(int medicationid) {
		this.medicationid = medicationid;
	}
	public String getMedicationdescription() {
		return medicationdescription;
	}
	public void setMedicationdescription(String medicationdescription) {
		this.medicationdescription = medicationdescription;
	}
}
