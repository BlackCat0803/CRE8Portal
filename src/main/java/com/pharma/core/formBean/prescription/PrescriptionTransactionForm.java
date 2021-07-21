package com.pharma.core.formBean.prescription;

/**
 * This class is a mcv form bean
 *
 */
public class PrescriptionTransactionForm {

	private int id;
	private int prescriptionId;
	private String rxNumber;
	private String rxStatus;
	private String writtenDate;
	private String expireDate;
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
	private String lastFilledDate;
	private String trackingNumber;
	private int daysSupply;
	private String controlSubstance;
	
	private String futureFill;
	private String priortyType;
	private String scriptType;
	private String previousRxNumber;
	private String prescriberOrderNumber;
	
	// for Generating PDF purpose
	private String rxItemType;
	private String icd10Text;
	private String directionText;
	private String originText;
	private String unitText;
	private String rxItemName;
	
	private String dispensedItemId;
	private String dispensedItemName;
	
	private String cre8PrescriptionNo; 
	private String prescriptionNo;
	private String comments;
	private int slNo;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPrescriptionId() {
		return prescriptionId;
	}
	public void setPrescriptionId(int prescriptionId) {
		this.prescriptionId = prescriptionId;
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
	public String getWrittenDate() {
		return writtenDate;
	}
	public void setWrittenDate(String writtenDate) {
		this.writtenDate = writtenDate;
	}
	public String getExpireDate() {
		return expireDate;
	}
	public void setExpireDate(String expireDate) {
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
	public String getSigCodes() {
		return sigCodes;
	}
	public void setSigCodes(String sigCodes) {
		this.sigCodes = sigCodes;
	}
	public int getRefillsRemaining() {
		return refillsRemaining;
	}
	public void setRefillsRemaining(int refillsRemaining) {
		this.refillsRemaining = refillsRemaining;
	}
	public int getRefillsFilled() {
		return refillsFilled;
	}
	public void setRefillsFilled(int refillsFilled) {
		this.refillsFilled = refillsFilled;
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
	public String getRxItemType() {
		return rxItemType;
	}
	public void setRxItemType(String rxItemType) {
		this.rxItemType = rxItemType;
	}
	public String getIcd10Text() {
		return icd10Text;
	}
	public void setIcd10Text(String icd10Text) {
		this.icd10Text = icd10Text;
	}
	public String getDirectionText() {
		return directionText;
	}
	public void setDirectionText(String directionText) {
		this.directionText = directionText;
	}
	public String getOriginText() {
		return originText;
	}
	public void setOriginText(String originText) {
		this.originText = originText;
	}
	public String getUnitText() {
		return unitText;
	}
	public void setUnitText(String unitText) {
		this.unitText = unitText;
	}
	public String getRxItemName() {
		return rxItemName;
	}
	public void setRxItemName(String rxItemName) {
		this.rxItemName = rxItemName;
	}
	public int getDaysSupply() {
		return daysSupply;
	}
	public void setDaysSupply(int daysSupply) {
		this.daysSupply = daysSupply;
	}
	public String getFutureFill() {
		return futureFill;
	}
	public void setFutureFill(String futureFill) {
		this.futureFill = futureFill;
	}
	public String getPriortyType() {
		return priortyType;
	}
	public void setPriortyType(String priortyType) {
		this.priortyType = priortyType;
	}
	public String getScriptType() {
		return scriptType;
	}
	public void setScriptType(String scriptType) {
		this.scriptType = scriptType;
	}
	public String getPreviousRxNumber() {
		return previousRxNumber;
	}
	public void setPreviousRxNumber(String previousRxNumber) {
		this.previousRxNumber = previousRxNumber;
	}
	public String getPrescriberOrderNumber() {
		return prescriberOrderNumber;
	}
	public void setPrescriberOrderNumber(String prescriberOrderNumber) {
		this.prescriberOrderNumber = prescriberOrderNumber;
	}
	public String getControlSubstance() {
		return controlSubstance;
	}
	public void setControlSubstance(String controlSubstance) {
		this.controlSubstance = controlSubstance;
	}
	public String getDispensedItemId() {
		return dispensedItemId;
	}
	public void setDispensedItemId(String dispensedItemId) {
		this.dispensedItemId = dispensedItemId;
	}
	public String getDispensedItemName() {
		return dispensedItemName;
	}
	public void setDispensedItemName(String dispensedItemName) {
		this.dispensedItemName = dispensedItemName;
	}
	public String getPrescriptionNo() {
		return prescriptionNo;
	}
	public void setPrescriptionNo(String prescriptionNo) {
		this.prescriptionNo = prescriptionNo;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getCre8PrescriptionNo() {
		return cre8PrescriptionNo;
	}
	public void setCre8PrescriptionNo(String cre8PrescriptionNo) {
		this.cre8PrescriptionNo = cre8PrescriptionNo;
	}
	public int getSlNo() {
		return slNo;
	}
	public void setSlNo(int slNo) {
		this.slNo = slNo;
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
