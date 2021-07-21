package com.pharma.core.model.invoice;

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
@Table(name="invoice_master")
public class InvoiceMaster {

	private int id;
	private String invoiceNumber;
	private Date invoiceDate;
	private String rxNumber;
	private Date writtenDate;
	private int patientId;
	private int physicianId;
	private String billingName;
	private String billingAddress;
	private String billingCity;
	private String billingState;
	private String billingZipcode;
	private String shippingName;
	private String shippingAddress;
	private String shippingCity;
	private String shippingState;
	private String shippingZipcode;
	private double subTotal;
	private double tax;
	private double total;
	
	private int createdBy;
	private String createdUser;
	private Timestamp createdDate;
	private int lastUpdatedBy;
	private String lastUpdatedUser;
	private Timestamp lastUpdatedDate;
	private String remarks;
	
	private double percentageSalesTaxAmountPaid;
	private double percentageSalesTaxAmountSubmitted;
	private double percentageSalesTaxRatePaid;
	private double percentageSalesTaxRateSubmitted;
	private double flatSalesTaxAmountPaid;
	private double flatSalesTaxAmountSubmitted;
	

	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "invoice_id", unique = true, nullable = false)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@Column(name = "invoice_number ", nullable = false, length = 50)
	public String getInvoiceNumber() {
		return invoiceNumber;
	}
	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}
	
	@Column(name = "invoice_date", nullable = false)
	public Date getInvoiceDate() {
		return invoiceDate;
	}
	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}
	
	@Column(name = "rx_number", nullable = false, length = 50)
	public String getRxNumber() {
		return rxNumber;
	}
	public void setRxNumber(String rxNumber) {
		this.rxNumber = rxNumber;
	}
	
	@Column(name = "written_date", nullable = false)
	public Date getWrittenDate() {
		return writtenDate;
	}
	public void setWrittenDate(Date writtenDate) {
		this.writtenDate = writtenDate;
	}
	
	@Column(name = "patient_id", nullable = false)
	public int getPatientId() {
		return patientId;
	}
	public void setPatientId(int patientId) {
		this.patientId = patientId;
	}
	
	@Column(name = "physician_id", nullable = false)
	public int getPhysicianId() {
		return physicianId;
	}
	public void setPhysicianId(int physicianId) {
		this.physicianId = physicianId;
	}
	
	@Column(name = "billing_name", nullable = false, length = 100)
	public String getBillingName() {
		return billingName;
	}
	public void setBillingName(String billingName) {
		this.billingName = billingName;
	}
	
	@Column(name = "billing_address", nullable = false, length = 100)
	public String getBillingAddress() {
		return billingAddress;
	}
	public void setBillingAddress(String billingAddress) {
		this.billingAddress = billingAddress;
	}
	
	@Column(name = "billing_city", nullable = false, length = 25)
	public String getBillingCity() {
		return billingCity;
	}
	public void setBillingCity(String billingCity) {
		this.billingCity = billingCity;
	}
	
	@Column(name = "billing_state", nullable = false, length = 3)
	public String getBillingState() {
		return billingState;
	}
	public void setBillingState(String billingState) {
		this.billingState = billingState;
	}
	
	@Column(name = "billing_zipcode", nullable = false, length = 12)
	public String getBillingZipcode() {
		return billingZipcode;
	}
	public void setBillingZipcode(String billingZipcode) {
		this.billingZipcode = billingZipcode;
	}
	
	@Column(name = "shipping_name", nullable = false, length = 100)
	public String getShippingName() {
		return shippingName;
	}
	public void setShippingName(String shippingName) {
		this.shippingName = shippingName;
	}
	
	@Column(name = "shipping_address", nullable = false, length = 100)
	public String getShippingAddress() {
		return shippingAddress;
	}
	public void setShippingAddress(String shippingAddress) {
		this.shippingAddress = shippingAddress;
	}
	
	@Column(name = "shipping_city", nullable = false, length = 25)
	public String getShippingCity() {
		return shippingCity;
	}
	public void setShippingCity(String shippingCity) {
		this.shippingCity = shippingCity;
	}
	
	@Column(name = "shipping_state", nullable = false, length = 3)
	public String getShippingState() {
		return shippingState;
	}
	public void setShippingState(String shippingState) {
		this.shippingState = shippingState;
	}
	
	@Column(name = "shipping_zipcode", nullable = false, length = 12)
	public String getShippingZipcode() {
		return shippingZipcode;
	}
	public void setShippingZipcode(String shippingZipcode) {
		this.shippingZipcode = shippingZipcode;
	}
	
	@Column(name = "subtotal", nullable = false, precision=2)
	public double getSubTotal() {
		return subTotal;
	}
	public void setSubTotal(double subTotal) {
		this.subTotal = subTotal;
	}
	
	@Column(name = "tax", nullable = false, precision=2)
	public double getTax() {
		return tax;
	}
	public void setTax(double tax) {
		this.tax = tax;
	}
	
	@Column(name = "total", nullable = false, precision=2)
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
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
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	public double getPercentageSalesTaxAmountPaid() {
		return percentageSalesTaxAmountPaid;
	}
	public void setPercentageSalesTaxAmountPaid(double percentageSalesTaxAmountPaid) {
		this.percentageSalesTaxAmountPaid = percentageSalesTaxAmountPaid;
	}
	public double getPercentageSalesTaxAmountSubmitted() {
		return percentageSalesTaxAmountSubmitted;
	}
	public void setPercentageSalesTaxAmountSubmitted(
			double percentageSalesTaxAmountSubmitted) {
		this.percentageSalesTaxAmountSubmitted = percentageSalesTaxAmountSubmitted;
	}
	public double getPercentageSalesTaxRatePaid() {
		return percentageSalesTaxRatePaid;
	}
	public void setPercentageSalesTaxRatePaid(double percentageSalesTaxRatePaid) {
		this.percentageSalesTaxRatePaid = percentageSalesTaxRatePaid;
	}
	public double getPercentageSalesTaxRateSubmitted() {
		return percentageSalesTaxRateSubmitted;
	}
	public void setPercentageSalesTaxRateSubmitted(
			double percentageSalesTaxRateSubmitted) {
		this.percentageSalesTaxRateSubmitted = percentageSalesTaxRateSubmitted;
	}
	public double getFlatSalesTaxAmountPaid() {
		return flatSalesTaxAmountPaid;
	}
	public void setFlatSalesTaxAmountPaid(double flatSalesTaxAmountPaid) {
		this.flatSalesTaxAmountPaid = flatSalesTaxAmountPaid;
	}
	public double getFlatSalesTaxAmountSubmitted() {
		return flatSalesTaxAmountSubmitted;
	}
	public void setFlatSalesTaxAmountSubmitted(double flatSalesTaxAmountSubmitted) {
		this.flatSalesTaxAmountSubmitted = flatSalesTaxAmountSubmitted;
	}
	
}
