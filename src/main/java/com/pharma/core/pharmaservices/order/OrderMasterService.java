package com.pharma.core.pharmaservices.order;

import javax.servlet.http.HttpSession;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.pharma.core.formBean.order.OrderForm;

@Service
public interface OrderMasterService {
	
	String getOrderDataList(int draw, int start, int length, String searchTerm, int orderColumn, String orderDir, String orderNo, String phyname,
			 String patientName, String fromDate, String toDate, String status, int physicianId, int patientId, int groupId, String rxNo);

	OrderForm getOrderDetails(int id);

	boolean generateOrderPdf(OrderForm form, String rootFilePath, HttpSession session, Environment env);
	
}
