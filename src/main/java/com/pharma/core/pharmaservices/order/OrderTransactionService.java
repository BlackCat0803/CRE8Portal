package com.pharma.core.pharmaservices.order;

import org.springframework.stereotype.Service;

@Service
public interface OrderTransactionService {

	
	String getOrderTransactionDataList(int draw, int start, int length, String searchTerm, int orderColumn, String orderDir, int orderId);
	
}
