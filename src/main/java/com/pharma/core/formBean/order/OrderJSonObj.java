package com.pharma.core.formBean.order;

import java.util.List;
/**
 * This class is a list of mcv form bean
 *
 */
public class OrderJSonObj {

	private List<OrderForm> data;
	private int draw;
	private int recordsTotal;
	private int recordsFiltered;
	
	
	public List<OrderForm> getData() {
		return data;
	}
	public void setData(List<OrderForm> data) {
		this.data = data;
	}
	public int getDraw() {
		return draw;
	}
	public void setDraw(int draw) {
		this.draw = draw;
	}
	public int getRecordsTotal() {
		return recordsTotal;
	}
	public void setRecordsTotal(int recordsTotal) {
		this.recordsTotal = recordsTotal;
	}
	public int getRecordsFiltered() {
		return recordsFiltered;
	}
	public void setRecordsFiltered(int recordsFiltered) {
		this.recordsFiltered = recordsFiltered;
	}
	
}
