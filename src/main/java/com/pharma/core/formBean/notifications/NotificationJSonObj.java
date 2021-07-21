package com.pharma.core.formBean.notifications;

import java.util.List;
/**
 * This class is a list of mcv form bean
 *
 */
public class NotificationJSonObj {

	private List<NotificationForm> data;
	private int draw;
	private int recordsTotal;
	private int recordsFiltered;
	
	
	public List<NotificationForm> getData() {
		return data;
	}
	public void setData(List<NotificationForm> data) {
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
