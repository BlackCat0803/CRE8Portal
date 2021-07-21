package com.pharma.core.model.invoice;

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
@Table(name="invoice_transaction")
public class InvoiceTransaction {
	
	private int id;
	private int invoiceId;
	private String item;
	private int quantity;
	private double total;
	private String lotNumber;
	private Date expirationDate;
	private int daysSupply;
	private int prescriptionTranId;
	private String rxNumber;
	private int numberOfRefillsFilled;
	private String paymentstatus;
	private String paymenttype;
	private Date paymentdate;
	private String scriptType;
	private int refillNumber;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "invoice_tran_id", unique = true, nullable = false)	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@Column(name = "invoice_id", nullable = false)
	public int getInvoiceId() {
		return invoiceId;
	}
	public void setInvoiceId(int invoiceId) {
		this.invoiceId = invoiceId;
	}
	
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
	
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	
	@Column(name = "lot_number", nullable = true, length = 50)
	public String getLotNumber() {
		return lotNumber;
	}
	public void setLotNumber(String lotNumber) {
		this.lotNumber = lotNumber;
	}
	
	@Column(name = "expiration_date", nullable = true)
	public Date getExpirationDate() {
		return expirationDate;
	}
	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}
	
	@Column(name = "days_supply", nullable = true)
	public int getDaysSupply() {
		return daysSupply;
	}
	public void setDaysSupply(int daysSupply) {
		this.daysSupply = daysSupply;
	}
	@Column(name = "prescription_tran_id", nullable = false)
	public int getPrescriptionTranId() {
		return prescriptionTranId;
	}
	public void setPrescriptionTranId(int prescriptionTranId) {
		this.prescriptionTranId = prescriptionTranId;
	}
	
	@Column(name = "rx_number", nullable = false, length=50)
	public String getRxNumber() {
		return rxNumber;
	}
	public void setRxNumber(String rxNumber) {
		this.rxNumber = rxNumber;
	}
	
	@Column(name = "numberOfRefillsFilled", nullable = false)
	public int getNumberOfRefillsFilled() {
		return numberOfRefillsFilled;
	}
	public void setNumberOfRefillsFilled(int numberOfRefillsFilled) {
		this.numberOfRefillsFilled = numberOfRefillsFilled;
	}
	public String getPaymentstatus() {
		return paymentstatus;
	}
	public void setPaymentstatus(String paymentstatus) {
		this.paymentstatus = paymentstatus;
	}
	public String getPaymenttype() {
		return paymenttype;
	}
	public void setPaymenttype(String paymenttype) {
		this.paymenttype = paymenttype;
	}
	public Date getPaymentdate() {
		return paymentdate;
	}
	public void setPaymentdate(Date paymentdate) {
		this.paymentdate = paymentdate;
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
