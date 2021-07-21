package com.pharma.core.model.order;

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
@Table(name="order_transaction")
public class OrderTransaction {
	
	private int id;
	private int orderId;
	private String rxNumber;
	private String rxStatus;
	private String prescribedName;
	private String prescribedDrug;
	private int prescribedQuantity;
	private String prescribedUnit;
	private String dispensedName;
	private String dispensedDrug;
	private int dispensedQuantity;
	private String dispensedUnit;
	private int daysSupply;
	private int quantityRemaining;
	private int refillsAllowed;
	private int refillsFilled;
	private int refillsRemaining;
	private Date lastFilledDate;
	private String trackingNumber;
	
	private int prescriptionTranId;
	private String priorityType;
	private String lotNumber;
	private Date lotExpirationDate;
	private String rxComments;
	private Date completedDate;
	private String shipmentstatus;
	private String shippingcompany;
	private String scriptType;
	private int refillNumber;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "transaction_id", unique = true, nullable = false)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@Column(name = "order_id", nullable = true)
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	
	@Column(name = "rx_number", nullable = false, length = 50)
	public String getRxNumber() {
		return rxNumber;
	}
	public void setRxNumber(String rxNumber) {
		this.rxNumber = rxNumber;
	}
	
	@Column(name = "rx_status", nullable = false, length = 50)
	public String getRxStatus() {
		return rxStatus;
	}
	public void setRxStatus(String rxStatus) {
		this.rxStatus = rxStatus;
	}
	
	@Column(name = "prescribed_name", nullable = false, length = 100)
	public String getPrescribedName() {
		return prescribedName;
	}
	public void setPrescribedName(String prescribedName) {
		this.prescribedName = prescribedName;
	}
	
	@Column(name = "prescribed_drug", nullable = false, length = 50)
	public String getPrescribedDrug() {
		return prescribedDrug;
	}
	public void setPrescribedDrug(String prescribedDrug) {
		this.prescribedDrug = prescribedDrug;
	}
	
	@Column(name = "prescribed_quantity", nullable = true)
	public int getPrescribedQuantity() {
		return prescribedQuantity;
	}
	public void setPrescribedQuantity(int prescribedQuantity) {
		this.prescribedQuantity = prescribedQuantity;
	}
	
	@Column(name = "prescribed_unit", nullable = false, length = 20)
	public String getPrescribedUnit() {
		return prescribedUnit;
	}
	public void setPrescribedUnit(String prescribedUnit) {
		this.prescribedUnit = prescribedUnit;
	}
	
	@Column(name = "dispensed_name", nullable = false, length = 100)
	public String getDispensedName() {
		return dispensedName;
	}
	public void setDispensedName(String dispensedName) {
		this.dispensedName = dispensedName;
	}
	
	@Column(name = "dispensed_drug", nullable = false, length = 50)
	public String getDispensedDrug() {
		return dispensedDrug;
	}
	public void setDispensedDrug(String dispensedDrug) {
		this.dispensedDrug = dispensedDrug;
	}
	
	@Column(name = "dispensed_quantity", nullable = true)
	public int getDispensedQuantity() {
		return dispensedQuantity;
	}
	public void setDispensedQuantity(int dispensedQuantity) {
		this.dispensedQuantity = dispensedQuantity;
	}
	
	@Column(name = "dispensed_unit", nullable = false, length = 20)
	public String getDispensedUnit() {
		return dispensedUnit;
	}
	public void setDispensedUnit(String dispensedUnit) {
		this.dispensedUnit = dispensedUnit;
	}
	
	@Column(name = "days_supply", nullable = true)
	public int getDaysSupply() {
		return daysSupply;
	}
	public void setDaysSupply(int daysSupply) {
		this.daysSupply = daysSupply;
	}
	
	@Column(name = "quantity_remaining", nullable = true)
	public int getQuantityRemaining() {
		return quantityRemaining;
	}
	public void setQuantityRemaining(int quantityRemaining) {
		this.quantityRemaining = quantityRemaining;
	}
	
	@Column(name = "refills_allowed", nullable = true)
	public int getRefillsAllowed() {
		return refillsAllowed;
	}
	public void setRefillsAllowed(int refillsAllowed) {
		this.refillsAllowed = refillsAllowed;
	}
	
	@Column(name = "refills_filled", nullable = true)
	public int getRefillsFilled() {
		return refillsFilled;
	}
	public void setRefillsFilled(int refillsFilled) {
		this.refillsFilled = refillsFilled;
	}
	
	@Column(name = "refills_remaining", nullable = true)
	public int getRefillsRemaining() {
		return refillsRemaining;
	}
	public void setRefillsRemaining(int refillsRemaining) {
		this.refillsRemaining = refillsRemaining;
	}
	
	@Column(name="last_filled_date", nullable = true)
	public Date getLastFilledDate() {
		return lastFilledDate;
	}
	public void setLastFilledDate(Date lastFilledDate) {
		this.lastFilledDate = lastFilledDate;
	}
	
	@Column(name = "tracking_number", nullable = false, length = 50)
	public String getTrackingNumber() {
		return trackingNumber;
	}
	public void setTrackingNumber(String trackingNumber) {
		this.trackingNumber = trackingNumber;
	}
	
	
	@Column(name = "prescription_tran_id", nullable = false)
	public int getPrescriptionTranId() {
		return prescriptionTranId;
	}
	public void setPrescriptionTranId(int prescriptionTranId) {
		this.prescriptionTranId = prescriptionTranId;
	}
	
	@Column(name = "priority_type", nullable = true)
	public String getPriorityType() {
		return priorityType;
	}
	public void setPriorityType(String priorityType) {
		this.priorityType = priorityType;
	}
	
	@Column(name = "lot_expiration_date", nullable = true)
	public Date getLotExpirationDate() {
		return lotExpirationDate;
	}
	public void setLotExpirationDate(Date lotExpirationDate) {
		this.lotExpirationDate = lotExpirationDate;
	}
	
	@Column(name = "lot_number", nullable = true)
	public String getLotNumber() {
		return lotNumber;
	}
	public void setLotNumber(String lotNumber) {
		this.lotNumber = lotNumber;
	}
	
	@Column(name = "rx_comments", nullable = true)
	public String getRxComments() {
		return rxComments;
	}
	public void setRxComments(String rxComments) {
		this.rxComments = rxComments;
	}
	
	@Column(name = "completed_date", nullable = true)
	public Date getCompletedDate() {
		return completedDate;
	}
	public void setCompletedDate(Date completedDate) {
		this.completedDate = completedDate;
	}
	public String getShipmentstatus() {
		return shipmentstatus;
	}
	public void setShipmentstatus(String shipmentstatus) {
		this.shipmentstatus = shipmentstatus;
	}
	public String getShippingcompany() {
		return shippingcompany;
	}
	public void setShippingcompany(String shippingcompany) {
		this.shippingcompany = shippingcompany;
	}
	public String getScriptType() {
		return scriptType;
	}
	public void setScriptType(String scriptType) {
		this.scriptType = scriptType;
	}
	public int getRefillNumber() {
		return refillNumber;
	}
	public void setRefillNumber(int refillNumber) {
		this.refillNumber = refillNumber;
	}
	
}
