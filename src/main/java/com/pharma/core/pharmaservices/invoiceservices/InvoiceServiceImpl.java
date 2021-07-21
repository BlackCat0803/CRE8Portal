package com.pharma.core.pharmaservices.invoiceservices;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pharma.core.formBean.order.OrderForm;
import com.pharma.core.formBean.order.OrderJSonObj;
/**
 *This is an implementation class that loads and stores the invoice data
 */
@Service("invoiceService")
public class InvoiceServiceImpl implements InvoiceService  {
	

	public String getInvoiceDataList(int draw, int start, int length, String searchTerm, int orderColumn, String orderDir, String groupName, String status) {

		try {
			if (groupName == null) groupName = "";
			if (status == null || status == "") status = "";
			
			int total = 0;
			int pageNumber = 0;
			Pageable page = null;
			Page<OrderForm> orderFormList = null;
			
			Direction dir = Direction.ASC;
			if (orderDir.equalsIgnoreCase("desc"))
				dir = Direction.DESC;

			try {
				//logic goes here
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
				//logic goes here
			} catch(Exception e) {
				e.printStackTrace();
			}
			List<OrderForm> orderFormObjList = new ArrayList<OrderForm>();
			if (orderFormList != null && orderFormList.getSize()>0 ) {
				for(OrderForm a: orderFormList) {
					OrderForm obj = new OrderForm();
											
					obj.setDT_RowId("row_" + a.getOrderId());
					
					orderFormObjList.add(obj);
				}
			}
			
			OrderJSonObj data = new OrderJSonObj();
			data.setData(orderFormObjList);
			data.setDraw(draw);
			data.setRecordsFiltered(total);
			data.setRecordsFiltered(total);
			
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			String json2 = gson.toJson(data);	
			
			return json2;
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return "";
	}


}

