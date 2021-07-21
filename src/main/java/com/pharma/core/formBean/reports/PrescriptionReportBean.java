package com.pharma.core.formBean.reports;
/**
 * This class is a mcv form bean
 *
 */
public class PrescriptionReportBean {

	
	private String prescriberNumber;
	private String writtenOn;
    private String expireOn;
    private String rxNumber;
	private String physicianName;
	private String patientName;
	private String item;
	private String origin;
	private String quantity;
    private String daysSupply;
    private String refills;
    private String refillsRemaining;
    private String refillsFilled;
    private String status;

    private int prescriptionId;
    private String dispensedItems; 
    private String cre8PrescriptionNo;
    
    public String getPrescriberNumber() {
		return prescriberNumber;
	}
	public void setPrescriberNumber(String prescriberNumber) {
		this.prescriberNumber = prescriberNumber;
	}
	public String getWrittenOn() {
		return writtenOn;
	}
	public void setWrittenOn(String writtenOn) {
		this.writtenOn = writtenOn;
	}
	public String getExpireOn() {
		return expireOn;
	}
	public void setExpireOn(String expireOn) {
		this.expireOn = expireOn;
	}
	public String getRxNumber() {
		return rxNumber;
	}
	public void setRxNumber(String rxNumber) {
		this.rxNumber = rxNumber;
	}
	public String getPhysicianName() {
		return physicianName;
	}
	public void setPhysicianName(String physicianName) {
		this.physicianName = physicianName;
	}
	public String getPatientName() {
		return patientName;
	}
	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getDaysSupply() {
		return daysSupply;
	}
	public void setDaysSupply(String daysSupply) {
		this.daysSupply = daysSupply;
	}
	public String getRefills() {
		return refills;
	}
	public void setRefills(String refills) {
		this.refills = refills;
	}
	public String getRefillsRemaining() {
		return refillsRemaining;
	}
	public void setRefillsRemaining(String refillsRemaining) {
		this.refillsRemaining = refillsRemaining;
	}
	public String getRefillsFilled() {
		return refillsFilled;
	}
	public void setRefillsFilled(String refillsFilled) {
		this.refillsFilled = refillsFilled;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getPrescriptionId() {
		return prescriptionId;
	}
	public void setPrescriptionId(int prescriptionId) {
		this.prescriptionId = prescriptionId;
	}
	public String getDispensedItems() {
		return dispensedItems;
	}
	public void setDispensedItems(String dispensedItems) {
		this.dispensedItems = dispensedItems;
	}
	public String getCre8PrescriptionNo() {
		return cre8PrescriptionNo;
	}
	public void setCre8PrescriptionNo(String cre8PrescriptionNo) {
		this.cre8PrescriptionNo = cre8PrescriptionNo;
	}

}
