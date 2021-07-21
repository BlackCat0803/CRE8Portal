package com.pharma.core.model.pioneer;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * This class is a JPA entity class
 */
@Entity
@Table(name="[pioneer.item.deaoverride]")
public class PrescriptionItemDEAOverride {

	private String deaoverrideid;
	private String gcn;
	private String statecode;
	private String locationid;
	private int deaschedule;
	private int drugofconcern;
	private String changedby;
	private String changedat;
	private String itemid;
	private Date changedonutc;
	private Date centralchangedonutc;
	private int applytoexpirationdate;
	private int applytorefills;
	private int applytopartialfills;
	private int applytotransfers;
	

	@Id
	@GeneratedValue(strategy = IDENTITY)
	public String getDeaoverrideid() {
		return deaoverrideid;
	}
	public void setDeaoverrideid(String deaoverrideid) {
		this.deaoverrideid = deaoverrideid;
	}
	public String getGcn() {
		return gcn;
	}
	public void setGcn(String gcn) {
		this.gcn = gcn;
	}
	public String getStatecode() {
		return statecode;
	}
	public void setStatecode(String statecode) {
		this.statecode = statecode;
	}
	public String getLocationid() {
		return locationid;
	}
	public void setLocationid(String locationid) {
		this.locationid = locationid;
	}
	public int getDeaschedule() {
		return deaschedule;
	}
	public void setDeaschedule(int deaschedule) {
		this.deaschedule = deaschedule;
	}
	public int getDrugofconcern() {
		return drugofconcern;
	}
	public void setDrugofconcern(int drugofconcern) {
		this.drugofconcern = drugofconcern;
	}
	public String getChangedby() {
		return changedby;
	}
	public void setChangedby(String changedby) {
		this.changedby = changedby;
	}
	public String getChangedat() {
		return changedat;
	}
	public void setChangedat(String changedat) {
		this.changedat = changedat;
	}
	public String getItemid() {
		return itemid;
	}
	public void setItemid(String itemid) {
		this.itemid = itemid;
	}
	public Date getChangedonutc() {
		return changedonutc;
	}
	public void setChangedonutc(Date changedonutc) {
		this.changedonutc = changedonutc;
	}
	public Date getCentralchangedonutc() {
		return centralchangedonutc;
	}
	public void setCentralchangedonutc(Date centralchangedonutc) {
		this.centralchangedonutc = centralchangedonutc;
	}
	public int getApplytoexpirationdate() {
		return applytoexpirationdate;
	}
	public void setApplytoexpirationdate(int applytoexpirationdate) {
		this.applytoexpirationdate = applytoexpirationdate;
	}
	public int getApplytorefills() {
		return applytorefills;
	}
	public void setApplytorefills(int applytorefills) {
		this.applytorefills = applytorefills;
	}
	public int getApplytopartialfills() {
		return applytopartialfills;
	}
	public void setApplytopartialfills(int applytopartialfills) {
		this.applytopartialfills = applytopartialfills;
	}
	public int getApplytotransfers() {
		return applytotransfers;
	}
	public void setApplytotransfers(int applytotransfers) {
		this.applytotransfers = applytotransfers;
	}

	

}
