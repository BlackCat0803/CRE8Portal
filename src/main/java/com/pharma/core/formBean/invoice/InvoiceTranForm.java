package com.pharma.core.formBean.invoice;
/**
 * This class is a mcv form bean
 *
 */
public class InvoiceTranForm {

	private int id;
	private int invoiceId;
	private String rxItem;
    private int rxQuantity;
    private double rxTotal;
    private String rxLotNo;
    private String rxExpirationdate;
    private int daysSupply;
    private int noOfRefillsFilled;
    private String paymentstatus;
    private String paymenttype;
    private String rxNumber;
    private String prescriptionNo;
    
    
    private String scriptType;
	private int refillNumber;
	  
    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(int invoiceId) {
		this.invoiceId = invoiceId;
	}

	public String getRxItem() {
		return rxItem;
	}

	public void setRxItem(String rxItem) {
		this.rxItem = rxItem;
	}

	public int getRxQuantity() {
		return rxQuantity;
	}

	public void setRxQuantity(int rxQuantity) {
		this.rxQuantity = rxQuantity;
	}

	public double getRxTotal() {
		return rxTotal;
	}

	public void setRxTotal(double rxTotal) {
		this.rxTotal = rxTotal;
	}

	public String getRxLotNo() {
		return rxLotNo;
	}

	public void setRxLotNo(String rxLotNo) {
		this.rxLotNo = rxLotNo;
	}

	public String getRxExpirationdate() {
		return rxExpirationdate;
	}

	public void setRxExpirationdate(String rxExpirationdate) {
		this.rxExpirationdate = rxExpirationdate;
	}

	public int getDaysSupply() {
		return daysSupply;
	}

	public void setDaysSupply(int daysSupply) {
		this.daysSupply = daysSupply;
	}

	public int getNoOfRefillsFilled() {
		return noOfRefillsFilled;
	}

	public void setNoOfRefillsFilled(int noOfRefillsFilled) {
		this.noOfRefillsFilled = noOfRefillsFilled;
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

	public String getRxNumber() {
		return rxNumber;
	}

	public void setRxNumber(String rxNumber) {
		this.rxNumber = rxNumber;
	}

	public String getPrescriptionNo() {
		return prescriptionNo;
	}

	public void setPrescriptionNo(String prescriptionNo) {
		this.prescriptionNo = prescriptionNo;
	}
    
    
}
