package com.pharma.core.model.physician;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * This class is a JPA entity class
 */
@Entity
@Table(name = "phy_profile")
public class PhysicianProfileInfo {

	private int physicianId;
	private String website;
	private String marketer;
	private String dea;
	private String npi;
	private String upin;
	private String stateLicense;
	private String medicaid;
	private String dps;
	private String commEmail;
	private String commPhone;
	private String commFax;
	private String comments;
	private int reqBeforeDays;
	private String sendMailPermission;

	private String commTrackingNo;
	private String commShipped;
	private String commDelivered;
	private String commDeliveryExceptions;

	private String commentsUpdateInPioneer;
	
	
	// Constructors
	/** default constructor */

	public PhysicianProfileInfo() {
	}

	// Property accessors

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "physician_id", unique = true, nullable = false)
	public int getPhysicianId() {
		return physicianId;
	}

	public void setPhysicianId(int physicianId) {
		this.physicianId = physicianId;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getMarketer() {
		return marketer;
	}

	public void setMarketer(String marketer) {
		this.marketer = marketer;
	}

	public String getDea() {
		return dea;
	}

	public void setDea(String dea) {
		this.dea = dea;
	}

	public String getNpi() {
		return npi;
	}

	public void setNpi(String npi) {
		this.npi = npi;
	}

	public String getUpin() {
		return upin;
	}

	public void setUpin(String upin) {
		this.upin = upin;
	}

	public String getMedicaid() {
		return medicaid;
	}

	public void setMedicaid(String medicaid) {
		this.medicaid = medicaid;
	}

	public String getDps() {
		return dps;
	}

	public void setDps(String dps) {
		this.dps = dps;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	@Column(name = "renewal_req_days_bf_supply_ends", nullable = true)
	public int getReqBeforeDays() {
		return reqBeforeDays;
	}

	public void setReqBeforeDays(int reqBeforeDays) {
		this.reqBeforeDays = reqBeforeDays;
	}

	@Column(name = "state_license", nullable = true)
	public String getStateLicense() {
		return stateLicense;
	}

	public void setStateLicense(String stateLicense) {
		this.stateLicense = stateLicense;
	}

	@Column(name = "comm_email", nullable = true)
	public String getCommEmail() {
		return commEmail;
	}

	public void setCommEmail(String commEmail) {
		this.commEmail = commEmail;
	}

	@Column(name = "comm_phone", nullable = true)
	public String getCommPhone() {
		return commPhone;
	}

	public void setCommPhone(String commPhone) {
		this.commPhone = commPhone;
	}

	@Column(name = "comm_fax", nullable = true)
	public String getCommFax() {
		return commFax;
	}

	public void setCommFax(String commFax) {
		this.commFax = commFax;
	}

	@Column(name = "send_mail_permission", nullable = true)
	public String getSendMailPermission() {
		return sendMailPermission;
	}

	public void setSendMailPermission(String sendMailPermission) {
		this.sendMailPermission = sendMailPermission;
	}

	public String getCommTrackingNo() {
		return commTrackingNo;
	}

	public void setCommTrackingNo(String commTrackingNo) {
		this.commTrackingNo = commTrackingNo;
	}

	public String getCommShipped() {
		return commShipped;
	}

	public void setCommShipped(String commShipped) {
		this.commShipped = commShipped;
	}

	public String getCommDelivered() {
		return commDelivered;
	}

	public void setCommDelivered(String commDelivered) {
		this.commDelivered = commDelivered;
	}

	public String getCommDeliveryExceptions() {
		return commDeliveryExceptions;
	}

	public void setCommDeliveryExceptions(String commDeliveryExceptions) {
		this.commDeliveryExceptions = commDeliveryExceptions;
	}

	public String getCommentsUpdateInPioneer() {
		return commentsUpdateInPioneer;
	}

	public void setCommentsUpdateInPioneer(String commentsUpdateInPioneer) {
		this.commentsUpdateInPioneer = commentsUpdateInPioneer;
	}
}
