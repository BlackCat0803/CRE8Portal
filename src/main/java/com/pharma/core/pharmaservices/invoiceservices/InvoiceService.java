package com.pharma.core.pharmaservices.invoiceservices;

import org.springframework.stereotype.Service;


@Service
public interface InvoiceService {
	

	String getInvoiceDataList(int draw, int start, int len, String searchTerm, int orderColumn, String orderDir, String groupName, String status);

	
}
