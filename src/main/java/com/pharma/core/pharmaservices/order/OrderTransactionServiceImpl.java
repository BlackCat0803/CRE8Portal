package com.pharma.core.pharmaservices.order;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pharma.core.formBean.order.OrderTransactionForm;
import com.pharma.core.formBean.order.OrderTransactionJSonObj;
import com.pharma.core.model.order.OrderTransaction;
import com.pharma.core.repository.invoice.InvoiceMasterRepository;
import com.pharma.core.repository.invoice.InvoiceTransactionRepository;
import com.pharma.core.repository.order.OrderTransactionRepository;
import com.pharma.core.repository.prescription.PrescriptionTranRepository;
/**
 * This is an implementation class that loads and stores the order transaction data
 */
@Service("orderTransactionService")
public class OrderTransactionServiceImpl implements OrderTransactionService {
	

	@Autowired
	OrderTransactionRepository orderTranRepo;
	
	@Autowired
	PrescriptionTranRepository presTranRepo;
	
	
	@Autowired
	InvoiceMasterRepository invoiceMasterRepo;
	
	@Autowired
	InvoiceTransactionRepository invoiceTranRepo;
	
	public String getOrderTransactionDataList(int draw, int start, int length, String searchTerm, int orderColumn, String orderDir, int orderId) {
		
		
		int total = 0;
		int pageNumber = 0;
		Pageable page = null;
		Page<OrderTransaction> orderTranList = null;
		
		Direction dir = Direction.ASC;
		if (orderDir.equalsIgnoreCase("desc"))
			dir = Direction.DESC;

		try {
			
			total=orderTranRepo.getFilterRecordCount(orderId);
		
		} catch(Exception e) {
			e.printStackTrace();
		}
		pageNumber = start / length;
		
		switch (orderColumn) {
			case 1: {
				page = new PageRequest(pageNumber, length, dir, "permission");
				break;
			}
			case 2: {
				page = new PageRequest(pageNumber, length, dir, "isActive");
				break;
			}
			default: {
				page = new PageRequest(pageNumber, length, dir, "id");
				break;
			}
		}
		
		try {
			
			orderTranList=orderTranRepo.getFilterRecords(orderId,page);
			
		
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	
		SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy");
		List<OrderTransactionForm> orderObjList = new ArrayList<OrderTransactionForm>();
		String medicationDispensed="";
		String medicationPrescribed="";
		if (orderTranList != null && orderTranList.getSize() > 0 ) {
			for(OrderTransaction t: orderTranList) {
				OrderTransactionForm formTran = new OrderTransactionForm();
				
				formTran.setDaysSupply(t.getDaysSupply());
				formTran.setDispensedDrug(t.getDispensedDrug());
				formTran.setDispensedName(t.getDispensedName());
				formTran.setDispensedQuantity(t.getDispensedQuantity());
				formTran.setDispensedUnit(t.getDispensedUnit());
				
				if(t.getDispensedName()!=null && t.getDispensedName().length()>0)
					medicationDispensed=t.getDispensedName();
				if(t.getDispensedDrug()!=null && t.getDispensedDrug().length()>0)
					medicationDispensed+="-"+t.getDispensedDrug();
				if(t.getDispensedQuantity()>0)
					medicationDispensed+="-"+t.getDispensedQuantity();
				if(t.getDispensedUnit()!=null && t.getDispensedUnit().length()>0)
					medicationDispensed+="-"+t.getDispensedUnit();
				
				formTran.setMedicationDispensed(medicationDispensed);
				if (t.getLastFilledDate() != null)
					formTran.setLastFilledDate(dt.format(t.getLastFilledDate()));
				else
					formTran.setLastFilledDate("");
				formTran.setOrderId(t.getOrderId()+"");
				formTran.setOrderTranId(t.getId());
				formTran.setPrescribedDrug(t.getPrescribedDrug());
				formTran.setPrescribedName(t.getPrescribedName());
				formTran.setPrescribedQuantity(t.getPrescribedQuantity());
				formTran.setPrescribedUnit(t.getPrescribedUnit());
				if(t.getPrescribedName()!=null && t.getPrescribedName().length()>0)
					medicationPrescribed=t.getPrescribedName();
				if(t.getPrescribedDrug()!=null && t.getPrescribedDrug().length()>0)
					medicationPrescribed+="-"+t.getPrescribedDrug();
				if(t.getPrescribedQuantity()>0)
					medicationPrescribed+="-"+t.getPrescribedQuantity();
				if(t.getPrescribedUnit()!=null && t.getPrescribedUnit().length()>0)
					medicationPrescribed+="-"+t.getPrescribedUnit();
				formTran.setMedicationPrescribed(medicationPrescribed);
				
				
				formTran.setQuantityRemaining(t.getQuantityRemaining());
				formTran.setRefillsAllowed(t.getRefillsAllowed());
				formTran.setRefillsFilled(t.getRefillsFilled());
				formTran.setRefillsRemaining(t.getRefillsRemaining());
				formTran.setRxNumber(t.getRxNumber());
				formTran.setRxStatus(t.getRxStatus());
				formTran.setTrackingNumber(t.getTrackingNumber());
				
				if (t.getPrescriptionTranId() > 0)
					formTran.setPrescriberOrderNumber(presTranRepo.findOne(t.getPrescriptionTranId()).getPrescriberOrderNumber());
				else
					formTran.setPrescriberOrderNumber("");
				
				if (t.getPrescriptionTranId() > 0)
					formTran.setInvoiceId(invoiceTranRepo.fetchInvoiceDetailsByRxRefill(t.getPrescriptionTranId(),t.getScriptType(),t.getRefillNumber()).get(0).getInvoiceId());
				else
					formTran.setInvoiceId(0);
				
				if (formTran.getInvoiceId() > 0)
					formTran.setInvoiceNumber(invoiceMasterRepo.findOne(formTran.getInvoiceId()).getInvoiceNumber()+"");
				else
					formTran.setInvoiceNumber("");
				
				formTran.setPrescriptionTranId(t.getPrescriptionTranId());
				formTran.setPriorityType(t.getPriorityType());
				formTran.setLotNumber(t.getLotNumber());
				if (t.getLotExpirationDate() != null)
					formTran.setLotExpirationDate(dt.format(t.getLotExpirationDate()));
				else
					formTran.setLotExpirationDate("");
				formTran.setRxComments(t.getRxComments());
				if (t.getCompletedDate() != null)
					formTran.setCompletedDate(dt.format(t.getCompletedDate()));
				else
					formTran.setCompletedDate("");
				
				formTran.setDT_RowId("row_" + presTranRepo.findOne(t.getPrescriptionTranId()).getPrescriptionId());
				
					
				orderObjList.add(formTran);
			}
		}

		OrderTransactionJSonObj data = new OrderTransactionJSonObj();
		data.setData(orderObjList);
		data.setDraw(draw);
		data.setRecordsFiltered(total);
		data.setRecordsFiltered(total);
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json2 = gson.toJson(data);
		
		return json2;		
	}

}
