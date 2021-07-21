package com.pharma.core.formBean.prescription;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import com.pharma.core.model.clinic.Clinic;
/**
 * This class is a mcv form bean
 *
 */
public class PrescriptionForm {

	private int prescriptionId;
	private int groupId;
	private int physicianId;
	private String groupName;
	private String physicianName;
	private String clinicName;
	
	private int patientId;
	private String patientName;
	private String patientAddress;
	private String patientCity;
	private String patientState;
	private String patientZipCode;
	
	private String diagnosis;
	private String allergies;
    
    private String billingAddressId;
	private String billingName;
	private String billingAddress;
	private String billingCity;
	private String billingState;
	private String billingZipCode;
	private String billingCountry;    
  
    private int rxTranId;
	private String rxNumber;
	private String rxWrittenOn;
    private String rxExpireOn;
    private String rxType;
    private String rxOrigin;
    private String rxItem;
    private String rxQuantity;
    private String rxItemUnit;
    private String rxRefills;
    private String rxDAW;
    private String rxAuto;
    private String rxPRN;
    private String rxICD10;
    private String rxSigCodes;
	private String rxStatus;
	private String rxDispensedItemName;
	private String trackingNumber;
	private String lastFilledDate;
	private String refillsFilled;
	private String refillRemaining;

	
    private String prescriberOrderNumber;
	private int clinicId;
	private int writtenBy;
	private String writtenByName;
	private Date prescriptionDate;
	private String phyAddress;
	private String phyCity;
	private String phyState;
	private String phyZipCode;
	private String phyCountry;
	private String phyPhone;
	private String phyDea;
	private String phyNpi;
	private String phyUpin;
	private String phyStateLicense;
	private String phyMedicaid;
	
	private String patientCountry;
	private String patientDateOfBirth;
	private String patientMobile;
	private String patientSsn;
	private String patientBillToId;
	private String clinicPo;
	private String paymentType;
	private String paymentTerms;
	private String nameOnCard;
	private String creditCardType;
	private String creditCardNumber;
	private String cvcCode;
	private String patientShipToId;
	private String shippingMethod;
	private String otherShippingMethod;
	private String shippingCompany;
	private String otherShippingCompany;
	private String shippingAccountNo;
	private String removeSignature;
	private String shippingAddressId;
	
	private String shippingName;
	private String shippingAddress;
	private String shippingCity;
	private String shippingState;
	private String shippingZipCode;
	private String shippingCountry;
	private String controlSubstance;
	
	// for summary
	private String DT_RowId;
	private String prescriptionDateStr;
	
	// transaction tables records
	private List<PrescriptionTransactionForm> medications;
	private List<SubtanceItemDocForm> subtanceDocuments;
	
	// for Prescription PDF generation
	private String patientFirstName;
	private String patientMiddleName;
	private String patientLastName;
	private String patientPhone;
	
	private String phyFirstName;
	private String phyMiddleName;
	private String phyLastName;
	
	private String rxItemType;
	private String icd10Text;
	private String directionText;
	private String originText;
	private String unitText;
	private String rxItemName;
	private int daysSupply;
	
	private String futureFill;
	private String priortyType;
	private String scriptType;
	private String previousRxNumber;
	
	private int createdBy;
	private String createdUser;
	private Timestamp createdDate;
	private int lastUpdatedBy;
	private String lastUpdatedUser;
	private Timestamp lastUpdatedDate;
	private int prescriptionNumber;
	private String prescriptionOrderNumber;
	
	
	private String tranItemName;
	private String tranDispensedItemName;
	private int tranQty;
	private int tranTotalRefills;
	private int tranRefillsFilled;
	private String tranRXStatus;
	private String tranRXNumber;
	private String tranPrescriptionNumber;

	private String tranQtyStr;
	private String tranTotalRefillsStr;
	private String tranRefillsFilledStr;
	private String tranDaySupply;
	private String base64ImgString;
	private boolean esignedPDF;
	private boolean csesignedPDF;
	
	// Control subtance Class 2 form data
	private boolean class2ControlSubtance;
	private String scan;
	private String fax;
	private String isHardCopy;
	private String description;
	private boolean fileFtpAllowed;
	private int orderId;
	private int invoiceId;
	private String prescriptionNo;
	private String cre8PrescriptionNo;
	private String comments;
	private String physicianPrintname;
	private String patientPrintname;
	private String physicianAddressInfo;
	private String patientAddressInfo;
	private String phyName;
	private String useGroupLogo;
	
	private List<Clinic> clinicList;
	private int statusId;
	
	
	public int getPrescriptionId() {
		return prescriptionId;
	}
	public void setPrescriptionId(int prescriptionId) {
		this.prescriptionId = prescriptionId;
	}
	public int getGroupId() {
		return groupId;
	}
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	public int getPhysicianId() {
		return physicianId;
	}
	public void setPhysicianId(int physicianId) {
		this.physicianId = physicianId;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getPhysicianName() {
		return physicianName;
	}
	public void setPhysicianName(String physicianName) {
		this.physicianName = physicianName;
	}
	public int getPatientId() {
		return patientId;
	}
	public void setPatientId(int patientId) {
		this.patientId = patientId;
	}
	public String getPatientName() {
		return patientName;
	}
	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}
	public String getPatientAddress() {
		return patientAddress;
	}
	public void setPatientAddress(String patientAddress) {
		this.patientAddress = patientAddress;
	}
	public String getPatientCity() {
		return patientCity;
	}
	public void setPatientCity(String patientCity) {
		this.patientCity = patientCity;
	}
	public String getPatientState() {
		return patientState;
	}
	public void setPatientState(String patientState) {
		this.patientState = patientState;
	}
	public String getPatientZipCode() {
		return patientZipCode;
	}
	public void setPatientZipCode(String patientZipcode) {
		this.patientZipCode = patientZipcode;
	}
	public String getDiagnosis() {
		return diagnosis;
	}
	public void setDiagnosis(String diagnosis) {
		this.diagnosis = diagnosis;
	}
	public String getAllergies() {
		return allergies;
	}
	public void setAllergies(String allergies) {
		this.allergies = allergies;
	}
	public String getBillingAddressId() {
		return billingAddressId;
	}
	public void setBillingAddressId(String billingAddressId) {
		this.billingAddressId = billingAddressId;
	}
	public String getBillingName() {
		return billingName;
	}
	public void setBillingName(String billingName) {
		this.billingName = billingName;
	}
	public String getBillingAddress() {
		return billingAddress;
	}
	public void setBillingAddress(String billingAddress) {
		this.billingAddress = billingAddress;
	}
	public String getBillingCity() {
		return billingCity;
	}
	public void setBillingCity(String billingCity) {
		this.billingCity = billingCity;
	}
	public String getBillingState() {
		return billingState;
	}
	public void setBillingState(String billingState) {
		this.billingState = billingState;
	}
	public String getBillingZipCode() {
		return billingZipCode;
	}
	public void setBillingZipCode(String billingZipCode) {
		this.billingZipCode = billingZipCode;
	}
	public String getBillingCountry() {
		return billingCountry;
	}
	public void setBillingCountry(String billingCountry) {
		this.billingCountry = billingCountry;
	}
	public String getRxNumber() {
		return rxNumber;
	}
	public void setRxNumber(String rxNumber) {
		this.rxNumber = rxNumber;
	}
	public String getRxWrittenOn() {
		return rxWrittenOn;
	}
	public void setRxWrittenOn(String rxWrittenOn) {
		this.rxWrittenOn = rxWrittenOn;
	}
	public String getRxExpireOn() {
		return rxExpireOn;
	}
	public void setRxExpireOn(String rxExpireOn) {
		this.rxExpireOn = rxExpireOn;
	}
	public String getRxType() {
		return rxType;
	}
	public void setRxType(String rxType) {
		this.rxType = rxType;
	}
	public String getRxOrigin() {
		return rxOrigin;
	}
	public void setRxOrigin(String rxOrigin) {
		this.rxOrigin = rxOrigin;
	}
	public String getRxItem() {
		return rxItem;
	}
	public void setRxItem(String rxItem) {
		this.rxItem = rxItem;
	}
	public String getRxQuantity() {
		return rxQuantity;
	}
	public void setRxQuantity(String rxQuantity) {
		this.rxQuantity = rxQuantity;
	}
	public String getRxItemUnit() {
		return rxItemUnit;
	}
	public void setRxItemUnit(String rxItemUnit) {
		this.rxItemUnit = rxItemUnit;
	}
	public String getRxRefills() {
		return rxRefills;
	}
	public void setRxRefills(String rxRefills) {
		this.rxRefills = rxRefills;
	}
	public String getRxDAW() {
		return rxDAW;
	}
	public void setRxDAW(String rxDAW) {
		this.rxDAW = rxDAW;
	}
	public String getRxAuto() {
		return rxAuto;
	}
	public void setRxAuto(String rxAuto) {
		this.rxAuto = rxAuto;
	}
	public String getRxPRN() {
		return rxPRN;
	}
	public void setRxPRN(String rxPRN) {
		this.rxPRN = rxPRN;
	}
	public String getRxICD10() {
		return rxICD10;
	}
	public void setRxICD10(String rxICD10) {
		this.rxICD10 = rxICD10;
	}
	public String getRxSigCodes() {
		return rxSigCodes;
	}
	public void setRxSigCodes(String rxSigCodes) {
		this.rxSigCodes = rxSigCodes;
	}
	public String getPrescriberOrderNumber() {
		return prescriberOrderNumber;
	}
	public void setPrescriberOrderNumber(String prescriberOrderNumber) {
		this.prescriberOrderNumber = prescriberOrderNumber;
	}
	public int getClinicId() {
		return clinicId;
	}
	public void setClinicId(int clinicId) {
		this.clinicId = clinicId;
	}
	public int getWrittenBy() {
		return writtenBy;
	}
	public void setWrittenBy(int writtenBy) {
		this.writtenBy = writtenBy;
	}
	public String getWrittenByName() {
		return writtenByName;
	}
	public void setWrittenByName(String writtenByName) {
		this.writtenByName = writtenByName;
	}
	public Date getPrescriptionDate() {
		return prescriptionDate;
	}
	public void setPrescriptionDate(Date prescriptionDate) {
		this.prescriptionDate = prescriptionDate;
	}
	public String getPhyAddress() {
		return phyAddress;
	}
	public void setPhyAddress(String phyAddress) {
		this.phyAddress = phyAddress;
	}
	public String getPhyCity() {
		return phyCity;
	}
	public void setPhyCity(String phyCity) {
		this.phyCity = phyCity;
	}
	public String getPhyState() {
		return phyState;
	}
	public void setPhyState(String phyState) {
		this.phyState = phyState;
	}
	public String getPhyZipCode() {
		return phyZipCode;
	}
	public void setPhyZipCode(String phyZipCode) {
		this.phyZipCode = phyZipCode;
	}
	public String getPhyCountry() {
		return phyCountry;
	}
	public void setPhyCountry(String phyCountry) {
		this.phyCountry = phyCountry;
	}
	public String getPhyPhone() {
		return phyPhone;
	}
	public void setPhyPhone(String phyPhone) {
		this.phyPhone = phyPhone;
	}
	public String getPhyDea() {
		return phyDea;
	}
	public void setPhyDea(String phyDea) {
		this.phyDea = phyDea;
	}
	public String getPhyNpi() {
		return phyNpi;
	}
	public void setPhyNpi(String phyNpi) {
		this.phyNpi = phyNpi;
	}
	public String getPhyUpin() {
		return phyUpin;
	}
	public void setPhyUpin(String phyUpin) {
		this.phyUpin = phyUpin;
	}
	public String getPhyStateLicense() {
		return phyStateLicense;
	}
	public void setPhyStateLicense(String phyStateLicense) {
		this.phyStateLicense = phyStateLicense;
	}
	public String getPhyMedicaid() {
		return phyMedicaid;
	}
	public void setPhyMedicaid(String phyMedicaid) {
		this.phyMedicaid = phyMedicaid;
	}
	public String getPatientCountry() {
		return patientCountry;
	}
	public void setPatientCountry(String patientCountry) {
		this.patientCountry = patientCountry;
	}
	public String getPatientDateOfBirth() {
		return patientDateOfBirth;
	}
	public void setPatientDateOfBirth(String patientDateOfBirth) {
		this.patientDateOfBirth = patientDateOfBirth;
	}
	public String getPatientMobile() {
		return patientMobile;
	}
	public void setPatientMobile(String patientMobile) {
		this.patientMobile = patientMobile;
	}
	public String getPatientSsn() {
		return patientSsn;
	}
	public void setPatientSsn(String patientSsn) {
		this.patientSsn = patientSsn;
	}
	public String getPatientBillToId() {
		return patientBillToId;
	}
	public void setPatientBillToId(String patientBillToId) {
		this.patientBillToId = patientBillToId;
	}
	public String getClinicPo() {
		return clinicPo;
	}
	public void setClinicPo(String clinicPo) {
		this.clinicPo = clinicPo;
	}
	public String getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	public String getPaymentTerms() {
		return paymentTerms;
	}
	public void setPaymentTerms(String paymentTerms) {
		this.paymentTerms = paymentTerms;
	}
	public String getNameOnCard() {
		return nameOnCard;
	}
	public void setNameOnCard(String nameOnCard) {
		this.nameOnCard = nameOnCard;
	}
	public String getCreditCardType() {
		return creditCardType;
	}
	public void setCreditCardType(String creditCardType) {
		this.creditCardType = creditCardType;
	}
	public String getCreditCardNumber() {
		return creditCardNumber;
	}
	public void setCreditCardNumber(String creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}
	public String getCvcCode() {
		return cvcCode;
	}
	public void setCvcCode(String cvcCode) {
		this.cvcCode = cvcCode;
	}
	public String getPatientShipToId() {
		return patientShipToId;
	}
	public void setPatientShipToId(String patientShipToId) {
		this.patientShipToId = patientShipToId;
	}
	public String getShippingMethod() {
		return shippingMethod;
	}
	public void setShippingMethod(String shippingMethod) {
		this.shippingMethod = shippingMethod;
	}
	public String getOtherShippingMethod() {
		return otherShippingMethod;
	}
	public void setOtherShippingMethod(String otherShippingMethod) {
		this.otherShippingMethod = otherShippingMethod;
	}
	public String getShippingCompany() {
		return shippingCompany;
	}
	public void setShippingCompany(String shippingCompany) {
		this.shippingCompany = shippingCompany;
	}
	public String getOtherShippingCompany() {
		return otherShippingCompany;
	}
	public void setOtherShippingCompany(String otherShippingCompany) {
		this.otherShippingCompany = otherShippingCompany;
	}
	public String getShippingAccountNo() {
		return shippingAccountNo;
	}
	public void setShippingAccountNo(String shippingAccountNo) {
		this.shippingAccountNo = shippingAccountNo;
	}
	public String getRemoveSignature() {
		return removeSignature;
	}
	public void setRemoveSignature(String removeSignature) {
		this.removeSignature = removeSignature;
	}
	public String getShippingAddressId() {
		return shippingAddressId;
	}
	public void setShippingAddressId(String shippingAddressId) {
		this.shippingAddressId = shippingAddressId;
	}
	public String getShippingName() {
		return shippingName;
	}
	public void setShippingName(String shippingName) {
		this.shippingName = shippingName;
	}
	public String getShippingAddress() {
		return shippingAddress;
	}
	public void setShippingAddress(String shippingAddress) {
		this.shippingAddress = shippingAddress;
	}
	public String getShippingCity() {
		return shippingCity;
	}
	public void setShippingCity(String shippingCity) {
		this.shippingCity = shippingCity;
	}
	public String getShippingState() {
		return shippingState;
	}
	public void setShippingState(String shippingState) {
		this.shippingState = shippingState;
	}
	public String getShippingZipCode() {
		return shippingZipCode;
	}
	public void setShippingZipCode(String shippingZipCode) {
		this.shippingZipCode = shippingZipCode;
	}
	public String getShippingCountry() {
		return shippingCountry;
	}
	public void setShippingCountry(String shippingCountry) {
		this.shippingCountry = shippingCountry;
	}
	public String getClinicName() {
		return clinicName;
	}
	public void setClinicName(String clinicName) {
		this.clinicName = clinicName;
	}
	public String getDT_RowId() {
		return DT_RowId;
	}
	public void setDT_RowId(String dT_RowId) {
		DT_RowId = dT_RowId;
	}
	public String getPrescriptionDateStr() {
		return prescriptionDateStr;
	}
	public void setPrescriptionDateStr(String prescriptionDateStr) {
		this.prescriptionDateStr = prescriptionDateStr;
	}
	public List<PrescriptionTransactionForm> getMedications() {
		return medications;
	}
	public void setMedications(List<PrescriptionTransactionForm> medications) {
		this.medications = medications;
	}
	public String getRxStatus() {
		return rxStatus;
	}
	public void setRxStatus(String rxStatus) {
		this.rxStatus = rxStatus;
	}
	public String getTrackingNumber() {
		return trackingNumber;
	}
	public void setTrackingNumber(String trackingNumber) {
		this.trackingNumber = trackingNumber;
	}
	public String getLastFilledDate() {
		return lastFilledDate;
	}
	public void setLastFilledDate(String lastFilledDate) {
		this.lastFilledDate = lastFilledDate;
	}
	public String getRefillsFilled() {
		return refillsFilled;
	}
	public void setRefillsFilled(String refillsFilled) {
		this.refillsFilled = refillsFilled;
	}
	public String getRefillRemaining() {
		return refillRemaining;
	}
	public void setRefillRemaining(String refillRemaining) {
		this.refillRemaining = refillRemaining;
	}
	public int getRxTranId() {
		return rxTranId;
	}
	public void setRxTranId(int rxTranId) {
		this.rxTranId = rxTranId;
	}
	public String getPatientFirstName() {
		return patientFirstName;
	}
	public void setPatientFirstName(String patientFirstName) {
		this.patientFirstName = patientFirstName;
	}
	public String getPatientMiddleName() {
		return patientMiddleName;
	}
	public void setPatientMiddleName(String patientMiddleName) {
		this.patientMiddleName = patientMiddleName;
	}
	public String getPatientLastName() {
		return patientLastName;
	}
	public void setPatientLastName(String patientLastName) {
		this.patientLastName = patientLastName;
	}
	public String getPatientPhone() {
		return patientPhone;
	}
	public void setPatientPhone(String patientPhone) {
		this.patientPhone = patientPhone;
	}
	public String getPhyFirstName() {
		return phyFirstName;
	}
	public void setPhyFirstName(String phyFirstName) {
		this.phyFirstName = phyFirstName;
	}
	public String getPhyMiddleName() {
		return phyMiddleName;
	}
	public void setPhyMiddleName(String phyMiddleName) {
		this.phyMiddleName = phyMiddleName;
	}
	public String getPhyLastName() {
		return phyLastName;
	}
	public void setPhyLastName(String phyLastName) {
		this.phyLastName = phyLastName;
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
	public int getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}
	public String getCreatedUser() {
		return createdUser;
	}
	public void setCreatedUser(String createdUser) {
		this.createdUser = createdUser;
	}
	public Timestamp getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}
	public int getLastUpdatedBy() {
		return lastUpdatedBy;
	}
	public void setLastUpdatedBy(int lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
	public String getLastUpdatedUser() {
		return lastUpdatedUser;
	}
	public void setLastUpdatedUser(String lastUpdatedUser) {
		this.lastUpdatedUser = lastUpdatedUser;
	}
	public Timestamp getLastUpdatedDate() {
		return lastUpdatedDate;
	}
	public void setLastUpdatedDate(Timestamp lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}
	public String getControlSubstance() {
		return controlSubstance;
	}
	public void setControlSubstance(String controlSubstance) {
		this.controlSubstance = controlSubstance;
	}
	public int getPrescriptionNumber() {
		return prescriptionNumber;
	}
	public void setPrescriptionNumber(int prescriptionNumber) {
		this.prescriptionNumber = prescriptionNumber;
	}
	public String getPrescriptionOrderNumber() {
		return prescriptionOrderNumber;
	}
	public void setPrescriptionOrderNumber(String prescriptionOrderNumber) {
		this.prescriptionOrderNumber = prescriptionOrderNumber;
	}
	public String getTranItemName() {
		return tranItemName;
	}
	public void setTranItemName(String tranItemName) {
		this.tranItemName = tranItemName;
	}
	public int getTranQty() {
		return tranQty;
	}
	public void setTranQty(int tranQty) {
		this.tranQty = tranQty;
	}
	public int getTranTotalRefills() {
		return tranTotalRefills;
	}
	public void setTranTotalRefills(int tranTotalRefills) {
		this.tranTotalRefills = tranTotalRefills;
	}
	public int getTranRefillsFilled() {
		return tranRefillsFilled;
	}
	public void setTranRefillsFilled(int tranRefillsFilled) {
		this.tranRefillsFilled = tranRefillsFilled;
	}
	public String getTranRXNumber() {
		return tranRXNumber;
	}
	public void setTranRXNumber(String tranRXNumber) {
		this.tranRXNumber = tranRXNumber;
	}
	public String getTranRXStatus() {
		return tranRXStatus;
	}
	public void setTranRXStatus(String tranRXStatus) {
		this.tranRXStatus = tranRXStatus;
	}
	public String getTranQtyStr() {
		return tranQtyStr;
	}
	public void setTranQtyStr(String tranQtyStr) {
		this.tranQtyStr = tranQtyStr;
	}
	public String getTranTotalRefillsStr() {
		return tranTotalRefillsStr;
	}
	public void setTranTotalRefillsStr(String tranTotalRefillsStr) {
		this.tranTotalRefillsStr = tranTotalRefillsStr;
	}
	public String getTranRefillsFilledStr() {
		return tranRefillsFilledStr;
	}
	public void setTranRefillsFilledStr(String tranRefillsFilledStr) {
		this.tranRefillsFilledStr = tranRefillsFilledStr;
	}
	public String getTranDaySupply() {
		return tranDaySupply;
	}
	public void setTranDaySupply(String tranDaySupply) {
		this.tranDaySupply = tranDaySupply;
	}
	public String getBase64ImgString() {
		return base64ImgString;
	}
	public void setBase64ImgString(String base64ImgString) {
		this.base64ImgString = base64ImgString;
	}
	public boolean isEsignedPDF() {
		return esignedPDF;
	}
	public void setEsignedPDF(boolean esignedPDF) {
		this.esignedPDF = esignedPDF;
	}
	public boolean isClass2ControlSubtance() {
		return class2ControlSubtance;
	}
	public void setClass2ControlSubtance(boolean class2ControlSubtance) {
		this.class2ControlSubtance = class2ControlSubtance;
	}
	public List<SubtanceItemDocForm> getSubtanceDocuments() {
		return subtanceDocuments;
	}
	public void setSubtanceDocuments(List<SubtanceItemDocForm> subtanceDocuments) {
		this.subtanceDocuments = subtanceDocuments;
	}
	public String getScan() {
		return scan;
	}
	public void setScan(String scan) {
		this.scan = scan;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getIsHardCopy() {
		return isHardCopy;
	}
	public void setIsHardCopy(String isHardCopy) {
		this.isHardCopy = isHardCopy;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public boolean isFileFtpAllowed() {
		return fileFtpAllowed;
	}
	public void setFileFtpAllowed(boolean fileFtpAllowed) {
		this.fileFtpAllowed = fileFtpAllowed;
	}
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public int getInvoiceId() {
		return invoiceId;
	}
	public void setInvoiceId(int invoiceId) {
		this.invoiceId = invoiceId;
	}
	public String getRxDispensedItemName() {
		return rxDispensedItemName;
	}
	public void setRxDispensedItemName(String rxDispensedItemName) {
		this.rxDispensedItemName = rxDispensedItemName;
	}
	public String getTranDispensedItemName() {
		return tranDispensedItemName;
	}
	public void setTranDispensedItemName(String tranDispensedItemName) {
		this.tranDispensedItemName = tranDispensedItemName;
	}
	public String getPrescriptionNo() {
		return prescriptionNo;
	}
	public void setPrescriptionNo(String prescriptionNo) {
		this.prescriptionNo = prescriptionNo;
	}
	public String getCre8PrescriptionNo() {
		return cre8PrescriptionNo;
	}
	public void setCre8PrescriptionNo(String cre8PrescriptionNo) {
		this.cre8PrescriptionNo = cre8PrescriptionNo;
	}
	public String getTranPrescriptionNumber() {
		return tranPrescriptionNumber;
	}
	public void setTranPrescriptionNumber(String tranPrescriptionNumber) {
		this.tranPrescriptionNumber = tranPrescriptionNumber;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getPhysicianPrintname() {
		return physicianPrintname;
	}
	public void setPhysicianPrintname(String physicianPrintname) {
		this.physicianPrintname = physicianPrintname;
	}
	public String getPatientPrintname() {
		return patientPrintname;
	}
	public void setPatientPrintname(String patientPrintname) {
		this.patientPrintname = patientPrintname;
	}
	public String getPhysicianAddressInfo() {
		return physicianAddressInfo;
	}
	public void setPhysicianAddressInfo(String physicianAddressInfo) {
		this.physicianAddressInfo = physicianAddressInfo;
	}
	public String getPatientAddressInfo() {
		return patientAddressInfo;
	}
	public void setPatientAddressInfo(String patientAddressInfo) {
		this.patientAddressInfo = patientAddressInfo;
	}
	public String getPhyName() {
		return phyName;
	}
	public void setPhyName(String phyName) {
		this.phyName = phyName;
	}
	public boolean isCsesignedPDF() {
		return csesignedPDF;
	}
	public void setCsesignedPDF(boolean csesignedPDF) {
		this.csesignedPDF = csesignedPDF;
	}
	public String getUseGroupLogo() {
		return useGroupLogo;
	}
	public void setUseGroupLogo(String useGroupLogo) {
		this.useGroupLogo = useGroupLogo;
	}
	public List<Clinic> getClinicList() {
		return clinicList;
	}
	public void setClinicList(List<Clinic> clinicList) {
		this.clinicList = clinicList;
	}
	public int getStatusId() {
		return statusId;
	}
	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}
	
}
