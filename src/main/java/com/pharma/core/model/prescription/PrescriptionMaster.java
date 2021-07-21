package com.pharma.core.model.prescription;

import static javax.persistence.GenerationType.IDENTITY;

import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * This class is a JPA entity class
 */
@Entity
@Table(name="prescription_master")
public class PrescriptionMaster {

	private int id;
	private int physicianId;
	private int patientId;
	private int groupId;
	private int clinicId;
	private String clinicName;
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
	private String patientName;
	private String patientAddress;
	private String patientCity;
	private String patientState;
	private String patientZipCode;
	private String patientCountry;
	private Date patientDateOfBirth;
	private String patientPhone;
	private String patientMobile;
	private String patientSsn;
	private String allergies;
	private String diagnosis;
	private String patientBillToId;
	private String clinicPo;
	private String paymentType;
	private String paymentTerms;
	private String nameOnCard;
	private String creditCardType;
	private String creditCardNumber;
	private String cvcCode;
	private String billingAddressId;
	private String billingName;
	private String billingAddress;
	private String billingCity;
	private String billingState;
	private String billingZipCode;
	private String billingCountry;
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
	
	private int createdBy;
	private String createdUser;
	private Timestamp createdDate;
	private int lastUpdatedBy;
	private String lastUpdatedUser;
	private Timestamp lastUpdatedDate;
	
	private int prescriptionNumber;
	private String cre8PrescriptionNo; 
	private String useGroupLogo;
	private String scan;
	private String fax;
	private int statusId;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "prescription_id", unique = true, nullable = false, length = 11)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "physician_id", nullable = false)
	public int getPhysicianId() {
		return physicianId;
	}
	public void setPhysicianId(int physicianId) {
		this.physicianId = physicianId;
	}
	
	@Column(name = "patient_id", nullable = false)
	public int getPatientId() {
		return patientId;
	}
	public void setPatientId(int patientId) {
		this.patientId = patientId;
	}
	
	@Column(name = "group_id", nullable = false)
	public int getGroupId() {
		return groupId;
	}
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	
	@Column(name = "clinic_id", nullable = false)
	public int getClinicId() {
		return clinicId;
	}
	public void setClinicId(int clinicId) {
		this.clinicId = clinicId;
	}
	
	@Column(name = "clinic_name", nullable = true, length = 100)
	public String getClinicName() {
		return clinicName;
	}
	public void setClinicName(String clinicName) {
		this.clinicName = clinicName;
	}
	
	@Column(name = "written_by", nullable = false)
	public int getWrittenBy() {
		return writtenBy;
	}
	public void setWrittenBy(int writtenBy) {
		this.writtenBy = writtenBy;
	}
	
	@Column(name = "written_by_name", nullable = true, length = 200)
	public String getWrittenByName() {
		return writtenByName;
	}
	public void setWrittenByName(String writtenByName) {
		this.writtenByName = writtenByName;
	}
	
	@Column(name="prescription_date", nullable = true)
	public Date getPrescriptionDate() {
		return prescriptionDate;
	}
	public void setPrescriptionDate(Date prescriptionDate) {
		this.prescriptionDate = prescriptionDate;
	}
	
	@Column(name="phy_address", nullable = true)
	public String getPhyAddress() {
		return phyAddress;
	}
	public void setPhyAddress(String phyAddress) {
		this.phyAddress = phyAddress;
	}
	
	@Column(name="phy_city", nullable = true)
	public String getPhyCity() {
		return phyCity;
	}
	public void setPhyCity(String phyCity) {
		this.phyCity = phyCity;
	}
	
	@Column(name="phy_state", nullable = true)
	public String getPhyState() {
		return phyState;
	}
	public void setPhyState(String phyState) {
		this.phyState = phyState;
	}
	
	@Column(name="phy_zip_code", nullable = true)
	public String getPhyZipCode() {
		return phyZipCode;
	}
	public void setPhyZipCode(String phyZipCode) {
		this.phyZipCode = phyZipCode;
	}
	
	@Column(name="phy_country", nullable = true)
	public String getPhyCountry() {
		return phyCountry;
	}
	public void setPhyCountry(String phyCountry) {
		this.phyCountry = phyCountry;
	}
	
	@Column(name="phy_phone", nullable = true)
	public String getPhyPhone() {
		return phyPhone;
	}
	public void setPhyPhone(String phyPhone) {
		this.phyPhone = phyPhone;
	}
	
	@Column(name="phy_dea", nullable = true)
	public String getPhyDea() {
		return phyDea;
	}
	public void setPhyDea(String phyDea) {
		this.phyDea = phyDea;
	}
	
	
	@Column(name="phy_npi", nullable = true)
	public String getPhyNpi() {
		return phyNpi;
	}
	public void setPhyNpi(String phyNpi) {
		this.phyNpi = phyNpi;
	}
	
	@Column(name="phy_upin", nullable = true)
	public String getPhyUpin() {
		return phyUpin;
	}
	public void setPhyUpin(String phyUpin) {
		this.phyUpin = phyUpin;
	}
	
	@Column(name="phy_state_license", nullable = true)
	public String getPhyStateLicense() {
		return phyStateLicense;
	}
	public void setPhyStateLicense(String phyStateLicense) {
		this.phyStateLicense = phyStateLicense;
	}
	
	@Column(name="phy_medicaid", nullable = true)
	public String getPhyMedicaid() {
		return phyMedicaid;
	}
	public void setPhyMedicaid(String phyMedicaid) {
		this.phyMedicaid = phyMedicaid;
	}
	
	@Column(name="patient_name", nullable = true)
	public String getPatientName() {
		return patientName;
	}
	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}
	
	@Column(name="patient_address", nullable = true)
	public String getPatientAddress() {
		return patientAddress;
	}
	public void setPatientAddress(String patientAddress) {
		this.patientAddress = patientAddress;
	}
	
	@Column(name="patient_city", nullable = true)
	public String getPatientCity() {
		return patientCity;
	}
	public void setPatientCity(String patientCity) {
		this.patientCity = patientCity;
	}
	
	@Column(name="patient_state", nullable = true)
	public String getPatientState() {
		return patientState;
	}
	public void setPatientState(String patientState) {
		this.patientState = patientState;
	}
	
	@Column(name="patient_zip_code", nullable = true)
	public String getPatientZipCode() {
		return patientZipCode;
	}
	public void setPatientZipCode(String patientZipCode) {
		this.patientZipCode = patientZipCode;
	}
	
	@Column(name="patient_country", nullable = true)
	public String getPatientCountry() {
		return patientCountry;
	}
	public void setPatientCountry(String patientCountry) {
		this.patientCountry = patientCountry;
	}
	
	@Column(name="patient_date_of_birth", nullable = true)
	public Date getPatientDateOfBirth() {
		return patientDateOfBirth;
	}
	public void setPatientDateOfBirth(Date patientDateOfBirth) {
		this.patientDateOfBirth = patientDateOfBirth;
	}
	@Column(name="patient_phone", nullable = true)
	public String getPatientPhone() {
		return patientPhone;
	}
	public void setPatientPhone(String patientPhone) {
		this.patientPhone = patientPhone;
	}
	@Column(name="patient_mobile", nullable = true)
	public String getPatientMobile() {
		return patientMobile;
	}
	public void setPatientMobile(String patientMobile) {
		this.patientMobile = patientMobile;
	}
	
	@Column(name="patient_ssn", nullable = true)
	public String getPatientSsn() {
		return patientSsn;
	}
	public void setPatientSsn(String patientSsn) {
		this.patientSsn = patientSsn;
	}
	
	@Column(name="allergies", nullable = true)
	public String getAllergies() {
		return allergies;
	}
	public void setAllergies(String allergies) {
		this.allergies = allergies;
	}
	
	@Column(name="diagnosis", nullable = true)
	public String getDiagnosis() {
		return diagnosis;
	}
	public void setDiagnosis(String diagnosis) {
		this.diagnosis = diagnosis;
	}
	
	
	@Column(name="patient_bill_to_id", nullable = false)
	public String getPatientBillToId() {
		return patientBillToId;
	}
	public void setPatientBillToId(String patientBillToId) {
		this.patientBillToId = patientBillToId;
	}
	
	@Column(name="clinic_po", nullable = true)
	public String getClinicPo() {
		return clinicPo;
	}
	public void setClinicPo(String clinicPo) {
		this.clinicPo = clinicPo;
	}
	
	@Column(name="payment_type", nullable = true)
	public String getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	
	@Column(name="payment_terms", nullable = true)
	public String getPaymentTerms() {
		return paymentTerms;
	}
	public void setPaymentTerms(String paymentTerms) {
		this.paymentTerms = paymentTerms;
	}
	
	@Column(name="name_on_card", nullable = true)
	public String getNameOnCard() {
		return nameOnCard;
	}
	public void setNameOnCard(String nameOnCard) {
		this.nameOnCard = nameOnCard;
	}
	
	@Column(name="credit_card_type", nullable = true)
	public String getCreditCardType() {
		return creditCardType;
	}
	public void setCreditCardType(String creditCardType) {
		this.creditCardType = creditCardType;
	}
	
	@Column(name="credit_card_number", nullable = true)
	public String getCreditCardNumber() {
		return creditCardNumber;
	}
	public void setCreditCardNumber(String creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}
	
	@Column(name="cvc_code", nullable = true)
	public String getCvcCode() {
		return cvcCode;
	}
	public void setCvcCode(String cvcCode) {
		this.cvcCode = cvcCode;
	}
	
	@Column(name="billing_address_id", nullable = true)
	public String getBillingAddressId() {
		return billingAddressId;
	}
	public void setBillingAddressId(String billingAddressId) {
		this.billingAddressId = billingAddressId;
	}
	
	@Column(name="billing_name", nullable = true)
	public String getBillingName() {
		return billingName;
	}
	public void setBillingName(String billingName) {
		this.billingName = billingName;
	}
	
	@Column(name="billing_address", nullable = true)
	public String getBillingAddress() {
		return billingAddress;
	}
	public void setBillingAddress(String billingAddress) {
		this.billingAddress = billingAddress;
	}
	
	@Column(name="billing_city", nullable = true)
	public String getBillingCity() {
		return billingCity;
	}
	public void setBillingCity(String billingCity) {
		this.billingCity = billingCity;
	}
	
	@Column(name="billing_state", nullable = true)
	public String getBillingState() {
		return billingState;
	}
	public void setBillingState(String billingState) {
		this.billingState = billingState;
	}
	
	@Column(name="billing_zip_code", nullable = true)
	public String getBillingZipCode() {
		return billingZipCode;
	}
	public void setBillingZipCode(String billingZipCode) {
		this.billingZipCode = billingZipCode;
	}
	
	@Column(name="billing_country", nullable = true)
	public String getBillingCountry() {
		return billingCountry;
	}
	public void setBillingCountry(String billingCountry) {
		this.billingCountry = billingCountry;
	}
	
	@Column(name="patient_ship_to_id", nullable = true)
	public String getPatientShipToId() {
		return patientShipToId;
	}
	public void setPatientShipToId(String patientShipToId) {
		this.patientShipToId = patientShipToId;
	}
	
	@Column(name="shipping_method", nullable = true)
	public String getShippingMethod() {
		return shippingMethod;
	}
	public void setShippingMethod(String shippingMethod) {
		this.shippingMethod = shippingMethod;
	}
	
	@Column(name="other_shipping_method", nullable = true)
	public String getOtherShippingMethod() {
		return otherShippingMethod;
	}
	public void setOtherShippingMethod(String otherShippingMethod) {
		this.otherShippingMethod = otherShippingMethod;
	}
	
	@Column(name="shipping_company", nullable = true)
	public String getShippingCompany() {
		return shippingCompany;
	}
	public void setShippingCompany(String shippingCompany) {
		this.shippingCompany = shippingCompany;
	}
	
	@Column(name="other_shipping_company", nullable = true)
	public String getOtherShippingCompany() {
		return otherShippingCompany;
	}
	public void setOtherShippingCompany(String otherShippingCompany) {
		this.otherShippingCompany = otherShippingCompany;
	}
	
	@Column(name="shipping_account_no", nullable = true)
	public String getShippingAccountNo() {
		return shippingAccountNo;
	}
	public void setShippingAccountNo(String shippingAccountNo) {
		this.shippingAccountNo = shippingAccountNo;
	}
	
	@Column(name="remove_signature", nullable = true)
	public String getRemoveSignature() {
		return removeSignature;
	}
	public void setRemoveSignature(String removeSignature) {
		this.removeSignature = removeSignature;
	}
	
	@Column(name="shipping_address_id", nullable = true)
	public String getShippingAddressId() {
		return shippingAddressId;
	}
	public void setShippingAddressId(String shippingAddressId) {
		this.shippingAddressId = shippingAddressId;
	}
	
	@Column(name="shipping_name", nullable = true)
	public String getShippingName() {
		return shippingName;
	}
	public void setShippingName(String shippingName) {
		this.shippingName = shippingName;
	}
	
	@Column(name="shipping_address", nullable = true)
	public String getShippingAddress() {
		return shippingAddress;
	}
	public void setShippingAddress(String shippingAddress) {
		this.shippingAddress = shippingAddress;
	}
	
	@Column(name="shipping_city", nullable = true)
	public String getShippingCity() {
		return shippingCity;
	}
	public void setShippingCity(String shippingCity) {
		this.shippingCity = shippingCity;
	}
	
	@Column(name="shipping_state", nullable = true)
	public String getShippingState() {
		return shippingState;
	}
	public void setShippingState(String shippingState) {
		this.shippingState = shippingState;
	}
	
	@Column(name="shipping_zip_code", nullable = true)
	public String getShippingZipCode() {
		return shippingZipCode;
	}
	public void setShippingZipCode(String shippingZipCode) {
		this.shippingZipCode = shippingZipCode;
	}
	
	@Column(name="shipping_country", nullable = true)
	public String getShippingCountry() {
		return shippingCountry;
	}
	public void setShippingCountry(String shippingCountry) {
		this.shippingCountry = shippingCountry;
	}

	@Column(name="created_by", nullable = true)
	public int getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name="created_user", nullable = true)
	public String getCreatedUser() {
		return createdUser;
	}
	public void setCreatedUser(String createdUser) {
		this.createdUser = createdUser;
	}

	@Column(name="created_date", nullable = true)
	public Timestamp getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	@Column(name="last_updated_by", nullable = true)
	public int getLastUpdatedBy() {
		return lastUpdatedBy;
	}
	public void setLastUpdatedBy(int lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	@Column(name="last_updated_user", nullable = true)
	public String getLastUpdatedUser() {
		return lastUpdatedUser;
	}
	public void setLastUpdatedUser(String lastUpdatedUser) {
		this.lastUpdatedUser = lastUpdatedUser;
	}

	@Column(name="last_updated_date", nullable = true)
	public Timestamp getLastUpdatedDate() {
		return lastUpdatedDate;
	}
	public void setLastUpdatedDate(Timestamp lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}
	
	@Column(name = "prescription_number", nullable = true)
	public int getPrescriptionNumber() {
		return prescriptionNumber;
	}
	public void setPrescriptionNumber(int prescriptionNumber) {
		this.prescriptionNumber = prescriptionNumber;
	}
	
	@Column(name = "cre8_prescription_no", nullable = false)
	public String getCre8PrescriptionNo() {
		return cre8PrescriptionNo;
	}
	public void setCre8PrescriptionNo(String cre8PrescriptionNo) {
		this.cre8PrescriptionNo = cre8PrescriptionNo;
	}
	public String getUseGroupLogo() {
		return useGroupLogo;
	}
	public void setUseGroupLogo(String useGroupLogo) {
		this.useGroupLogo = useGroupLogo;
	}
	
	
	@Column(name = "scan", nullable = false)
	public String getScan() {
		return scan;
	}
	public void setScan(String scan) {
		this.scan = scan;
	}
	
	@Column(name = "fax", nullable = false)
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	
	
	@Column(name = "status_id", nullable = false)
	public int getStatusId() {
		return statusId;
	}
	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}
	
	
}
