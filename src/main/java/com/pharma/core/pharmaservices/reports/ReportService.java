package com.pharma.core.pharmaservices.reports;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.pharma.core.formBean.reports.PatientsList;

@Service
public interface ReportService {

	String getPatientListData(int draw, int start, int length, String searchTerm, int orderColumn, String orderDir, String physician, 
			String group, String status, String fromDate, String toDate, HttpSession session);
	
	boolean getPatientListExcelFile(PatientsList form, String fileName, HttpSession session, HttpServletRequest request, 
			HttpServletResponse response, boolean isPdfFile);
	
	String getPhysicianListData(int draw, int start, int length, String searchTerm, int orderColumn, String orderDir, String physician, 
			String clinic, String group, String status, String dea, String fromDate, String toDate, HttpSession session);

	boolean getPhysicianListExcelFile(PatientsList form, String fileName, HttpSession session, HttpServletRequest request, 
			HttpServletResponse response, boolean isPdfFile);
	
	String getPrescriptionListData(int draw, int start, int length, String searchTerm, int orderColumn, String orderDir, String physician, 
			String patient, String fromDate, String toDate, String status, String group, String rxNo,String prescriptionNo, String rxPrescriptionNo, HttpSession session);
	
	boolean getPrescriptionListExcelFile(PatientsList form, String fileName, HttpSession session, HttpServletRequest request, 
			HttpServletResponse response, boolean isPdfFile);
	

	String getOrderListData(int draw, int start, int length, String searchTerm, int orderColumn, String orderDir, String physician, String patient, 
			String fromDate, String toDate, String status, String group, String orderNo, String rxNumber, HttpSession session);

	boolean getOrderListExcelFile(PatientsList form, String fileName, HttpSession session, HttpServletRequest request, 
			HttpServletResponse response, boolean isPdfFile);
	

	String getInvoiceListData(int draw, int start, int length, String searchTerm, int orderColumn, String orderDir, String physician, String patient, 
			String fromDate, String toDate, String group, String invoiceNo, String rxNumber, HttpSession session);

	boolean getInvoiceListExcelFile(PatientsList form, String fileName, HttpSession session, HttpServletRequest request, 
			HttpServletResponse response, boolean isPdfFile);
	
	
}
