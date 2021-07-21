package com.pharma.core.model.pioneer;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * This class is a JPA entity class
 */
@Entity
@Table(name="[pioneer.pointofsale.shippertype]")
public class PointofsaleShipperType {

	private int shippertypeid;
	private String shippertypetext;
	private int isdeleted;
	private String webserviceprod;
	private String webservicedev;
	private String urltrackingprod;
	private String urltrackingdev;
	private String regex;
	private String accountname;
	private String accountpassword;
	private String accountkey;
	private String accountnumber;
	private String accountmeternumber;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	public int getShippertypeid() {
		return shippertypeid;
	}
	public void setShippertypeid(int shippertypeid) {
		this.shippertypeid = shippertypeid;
	}
	public String getShippertypetext() {
		return shippertypetext;
	}
	public void setShippertypetext(String shippertypetext) {
		this.shippertypetext = shippertypetext;
	}
	public int getIsdeleted() {
		return isdeleted;
	}
	public void setIsdeleted(int isdeleted) {
		this.isdeleted = isdeleted;
	}
	public String getWebserviceprod() {
		return webserviceprod;
	}
	public void setWebserviceprod(String webserviceprod) {
		this.webserviceprod = webserviceprod;
	}
	public String getWebservicedev() {
		return webservicedev;
	}
	public void setWebservicedev(String webservicedev) {
		this.webservicedev = webservicedev;
	}
	public String getUrltrackingprod() {
		return urltrackingprod;
	}
	public void setUrltrackingprod(String urltrackingprod) {
		this.urltrackingprod = urltrackingprod;
	}
	public String getUrltrackingdev() {
		return urltrackingdev;
	}
	public void setUrltrackingdev(String urltrackingdev) {
		this.urltrackingdev = urltrackingdev;
	}
	public String getRegex() {
		return regex;
	}
	public void setRegex(String regex) {
		this.regex = regex;
	}
	public String getAccountname() {
		return accountname;
	}
	public void setAccountname(String accountname) {
		this.accountname = accountname;
	}
	public String getAccountpassword() {
		return accountpassword;
	}
	public void setAccountpassword(String accountpassword) {
		this.accountpassword = accountpassword;
	}
	public String getAccountkey() {
		return accountkey;
	}
	public void setAccountkey(String accountkey) {
		this.accountkey = accountkey;
	}
	public String getAccountnumber() {
		return accountnumber;
	}
	public void setAccountnumber(String accountnumber) {
		this.accountnumber = accountnumber;
	}
	public String getAccountmeternumber() {
		return accountmeternumber;
	}
	public void setAccountmeternumber(String accountmeternumber) {
		this.accountmeternumber = accountmeternumber;
	}

}
