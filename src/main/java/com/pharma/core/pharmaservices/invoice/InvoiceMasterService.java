package com.pharma.core.pharmaservices.invoice;

import javax.servlet.http.HttpSession;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.pharma.core.formBean.invoice.InvoiceForm;

@Service
public interface InvoiceMasterService {

	String getInvoiceSummaryData(int draw, int start, int length, String searchTerm, int orderColumn, String orderDir, String patientName, String phyName,
			 String invoiceNo, String fromDate, String toDate,  int physicianId, int patientId, int groupId, String rxNo);
	
	InvoiceForm getInvoiceData(int id);
	
	boolean generateInvoicePdf(InvoiceForm form, String rootFilePath, HttpSession session, Environment env);
}
