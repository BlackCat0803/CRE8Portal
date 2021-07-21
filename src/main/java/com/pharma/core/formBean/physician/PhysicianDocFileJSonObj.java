package com.pharma.core.formBean.physician;

import java.util.List;

import com.pharma.core.formBean.DocumentFileList;
/**
 * This class is a list of mcv form bean
 *
 */
public class PhysicianDocFileJSonObj {

	private List<DocumentFileList> data;
	private int draw;
	private int recordsTotal;
	private int recordsFiltered;
	
	
	public List<DocumentFileList> getData() {
		return data;
	}
	public void setData(List<DocumentFileList> data) {
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
