package com.pharma.core.formBean.order;
/**
 * This class is a mcv form bean
 *
 */
public class OrderTransactionForm {

	private int orderTranId;
	private String orderId;
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
	private String lastFilledDate;
	private String trackingNumber;

	private int prescriptionTranId;
	private String priorityType;
	private String lotNumber;
	private String lotExpirationDate;
	private String rxComments;
	private String completedDate;
	private String prescriberOrderNumber;
	
	private String medicationPrescribed;
	private String medicationDispensed;
	
	private String shipmentstatus;
	private String shippingcompany;
	private String trackingurl;
	
	private String scriptType;
	private int refillNumber;
	private int prescriptionId;
	private int invoiceId;
	private String invoiceNumber;
	private String prescriptionNo;
	
	// for Summary page list ids
	private String DT_RowId;
	
	public int getOrderTranId() {
		return orderTranId;
	}
	public void setOrderTranId(int orderTranId) {
		this.orderTranId = orderTranId;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getRxNumber() {
		return rxNumber;
	}
	public void setRxNumber(String rxNumber) {
		this.rxNumber = rxNumber;
	}
	public String getRxStatus() {
		return rxStatus;
	}
	public void setRxStatus(String rxStatus) {
		this.rxStatus = rxStatus;
	}
	public String getPrescribedName() {
		return prescribedName;
	}
	public void setPrescribedName(String prescribedName) {
		this.prescribedName = prescribedName;
	}
	public String getPrescribedDrug() {
		return prescribedDrug;
	}
	public void setPrescribedDrug(String prescribedDrug) {
		this.prescribedDrug = prescribedDrug;
	}
	public int getPrescribedQuantity() {
		return prescribedQuantity;
	}
	public void setPrescribedQuantity(int prescribedQuantity) {
		this.prescribedQuantity = prescribedQuantity;
	}
	public String getPrescribedUnit() {
		return prescribedUnit;
	}
	public void setPrescribedUnit(String prescribedUnit) {
		this.prescribedUnit = prescribedUnit;
	}
	public String getDispensedName() {
		return dispensedName;
	}
	public void setDispensedName(String dispensedName) {
		this.dispensedName = dispensedName;
	}
	public String getDispensedDrug() {
		return dispensedDrug;
	}
	public void setDispensedDrug(String dispensedDrug) {
		this.dispensedDrug = dispensedDrug;
	}
	public int getDispensedQuantity() {
		return dispensedQuantity;
	}
	public void setDispensedQuantity(int dispensedQuantity) {
		this.dispensedQuantity = dispensedQuantity;
	}
	public String getDispensedUnit() {
		return dispensedUnit;
	}
	public void setDispensedUnit(String dispensedUnit) {
		this.dispensedUnit = dispensedUnit;
	}
	public int getDaysSupply() {
		return daysSupply;
	}
	public void setDaysSupply(int daysSupply) {
		this.daysSupply = daysSupply;
	}
	public int getQuantityRemaining() {
		return quantityRemaining;
	}
	public void setQuantityRemaining(int quantityRemaining) {
		this.quantityRemaining = quantityRemaining;
	}
	public int getRefillsAllowed() {
		return refillsAllowed;
	}
	public void setRefillsAllowed(int refillsAllowed) {
		this.refillsAllowed = refillsAllowed;
	}
	public int getRefillsFilled() {
		return refillsFilled;
	}
	public void setRefillsFilled(int refillsFilled) {
		this.refillsFilled = refillsFilled;
	}
	public int getRefillsRemaining() {
		return refillsRemaining;
	}
	public void setRefillsRemaining(int refillsRemaining) {
		this.refillsRemaining = refillsRemaining;
	}
	public String getLastFilledDate() {
		return lastFilledDate;
	}
	public void setLastFilledDate(String lastFilledDate) {
		this.lastFilledDate = lastFilledDate;
	}
	public String getTrackingNumber() {
		return trackingNumber;
	}
	public void setTrackingNumber(String trackingNumber) {
		this.trackingNumber = trackingNumber;
	}
	public int getPrescriptionTranId() {
		return prescriptionTranId;
	}
	public void setPrescriptionTranId(int prescriptionTranId) {
		this.prescriptionTranId = prescriptionTranId;
	}
	public String getPriorityType() {
		return priorityType;
	}
	public void setPriorityType(String priorityType) {
		this.priorityType = priorityType;
	}
	public String getLotNumber() {
		return lotNumber;
	}
	public void setLotNumber(String lotNumber) {
		this.lotNumber = lotNumber;
	}
	public String getLotExpirationDate() {
		return lotExpirationDate;
	}
	public void setLotExpirationDate(String lotExpirationDate) {
		this.lotExpirationDate = lotExpirationDate;
	}
	public String getRxComments() {
		return rxComments;
	}
	public void setRxComments(String rxComments) {
		this.rxComments = rxComments;
	}
	public String getCompletedDate() {
		return completedDate;
	}
	public void setCompletedDate(String completedDate) {
		this.completedDate = completedDate;
	}
	public String getDT_RowId() {
		return DT_RowId;
	}
	public void setDT_RowId(String dT_RowId) {
		DT_RowId = dT_RowId;
	}
	public String getPrescriberOrderNumber() {
		return prescriberOrderNumber;
	}
	public void setPrescriberOrderNumber(String prescriberOrderNumber) {
		this.prescriberOrderNumber = prescriberOrderNumber;
	}
	public String getMedicationPrescribed() {
		return medicationPrescribed;
	}
	public void setMedicationPrescribed(String medicationPrescribed) {
		this.medicationPrescribed = medicationPrescribed;
	}
	public String getMedicationDispensed() {
		return medicationDispensed;
	}
	public void setMedicationDispensed(String medicationDispensed) {
		this.medicationDispensed = medicationDispensed;
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
	public String getTrackingurl() {
		return trackingurl;
	}
	public void setTrackingurl(String trackingurl) {
		this.trackingurl = trackingurl;
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
	public int getPrescriptionId() {
		return prescriptionId;
	}
	public void setPrescriptionId(int prescriptionId) {
		this.prescriptionId = prescriptionId;
	}
	public int getInvoiceId() {
		return invoiceId;
	}
	public void setInvoiceId(int invoiceId) {
		this.invoiceId = invoiceId;
	}
	public String getInvoiceNumber() {
		return invoiceNumber;
	}
	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}
	public String getPrescriptionNo() {
		return prescriptionNo;
	}
	public void setPrescriptionNo(String prescriptionNo) {
		this.prescriptionNo = prescriptionNo;
	}

}
