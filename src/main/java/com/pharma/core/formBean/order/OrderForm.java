package com.pharma.core.formBean.order;

import java.sql.Timestamp;
import java.util.List;
/**
 * This class is a mcv form bean
 *
 */
public class OrderForm {

	private int orderId;
	private String orderNumber;
	private String orderDate;
	private int groupId;
	private String groupName;
	private int physicianId;
	private String physicianName;
	private int patientId;
	private String patientName;
	
	private String shippingAddress;
	private String shippingCity;
	private String shippingState;
	private String shippingZipCode;
	private String shippingName;
	
	private List<OrderTransactionForm> transactions;

	private int createdBy;
	private String createdUser;
	private Timestamp createdDate;
	private int lastUpdatedBy;
	private String lastUpdatedUser;
	private Timestamp lastUpdatedDate;

	
	// for Summary Data purpose
	private String rxNumbers;
	private String orderNumbers;
	private String rxStatuses;
	private String status;
	
	private String medicationDispensed;
	private String refillsFilled;
	private String daysSupply;
	private String priorityType;
	
	private String scriptType;
	private int refillNumber;
	private int prescriptionId;
	private int invoiceId;
	private String refillRemaining; 
	
	// for Summary page list ids
	private String DT_RowId;

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public int getPhysicianId() {
		return physicianId;
	}

	public void setPhysicianId(int physicianId) {
		this.physicianId = physicianId;
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

	public List<OrderTransactionForm> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<OrderTransactionForm> transactions) {
		this.transactions = transactions;
	}

	public String getDT_RowId() {
		return DT_RowId;
	}

	public void setDT_RowId(String dT_RowId) {
		DT_RowId = dT_RowId;
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

	public String getShippingName() {
		return shippingName;
	}

	public void setShippingName(String shippingName) {
		this.shippingName = shippingName;
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

	public String getRxNumbers() {
		return rxNumbers;
	}

	public void setRxNumbers(String rxNumbers) {
		this.rxNumbers = rxNumbers;
	}

	public String getOrderNumbers() {
		return orderNumbers;
	}

	public void setOrderNumbers(String orderNumbers) {
		this.orderNumbers = orderNumbers;
	}

	public String getRxStatuses() {
		return rxStatuses;
	}

	public void setRxStatuses(String rxStatuses) {
		this.rxStatuses = rxStatuses;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMedicationDispensed() {
		return medicationDispensed;
	}

	public void setMedicationDispensed(String medicationDispensed) {
		this.medicationDispensed = medicationDispensed;
	}

	public String getRefillsFilled() {
		return refillsFilled;
	}

	public void setRefillsFilled(String refillsFilled) {
		this.refillsFilled = refillsFilled;
	}

	public String getDaysSupply() {
		return daysSupply;
	}

	public void setDaysSupply(String daysSupply) {
		this.daysSupply = daysSupply;
	}

	public String getPriorityType() {
		return priorityType;
	}

	public void setPriorityType(String priorityType) {
		this.priorityType = priorityType;
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

	public String getRefillRemaining() {
		return refillRemaining;
	}

	public void setRefillRemaining(String refillRemaining) {
		this.refillRemaining = refillRemaining;
	}

}
