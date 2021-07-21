package com.pharma.core.formBean.dashboard;

import java.util.List;

/**
 * This class is a mcv form bean
 *
 */
public class DashboardForm {
	
	private int patientId;
	private int physicianId;
	private int groupId;
	private int prescriptionId;
	private int orderId;
	private int invoiceId;
	private int assistantId;
	
	private String prescriptionstatus;
	private String orderstatus;
	private String invoicestatus;
	private List<String> selectedCheckBox;
	
	
	public int getPatientId() {
		return patientId;
	}
	public void setPatientId(int patientId) {
		this.patientId = patientId;
	}
	public int getPhysicianId() {
		return physicianId;
	}
	public void setPhysicianId(int physicianId) {
		this.physicianId = physicianId;
	}
	public int getGroupId() {
		return groupId;
	}
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	public int getPrescriptionId() {
		return prescriptionId;
	}
	public void setPrescriptionId(int prescriptionId) {
		this.prescriptionId = prescriptionId;
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
	public String getPrescriptionstatus() {
		return prescriptionstatus;
	}
	public void setPrescriptionstatus(String prescriptionstatus) {
		this.prescriptionstatus = prescriptionstatus;
	}
	public String getOrderstatus() {
		return orderstatus;
	}
	public void setOrderstatus(String orderstatus) {
		this.orderstatus = orderstatus;
	}
	public String getInvoicestatus() {
		return invoicestatus;
	}
	public void setInvoicestatus(String invoicestatus) {
		this.invoicestatus = invoicestatus;
	}
	public List<String> getSelectedCheckBox() {
		return selectedCheckBox;
	}
	public void setSelectedCheckBox(List<String> selectedCheckBox) {
		this.selectedCheckBox = selectedCheckBox;
	}
	public int getAssistantId() {
		return assistantId;
	}
	public void setAssistantId(int assistantId) {
		this.assistantId = assistantId;
	}
	
	
}
