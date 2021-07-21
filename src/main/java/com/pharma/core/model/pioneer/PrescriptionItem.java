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
@Table(name="[pioneer.item.item]")
public class PrescriptionItem {

	
	private String itemid	;
	private String locationid;
	private int itemtypeid;
	private String itemname;
	private String printname;
	private String itemgroup;
	private String upc		;
	private String ndc;
	private String manufacturer;
	private String departmentid;
	private int labeltypeid;
	private int drugclassid;
	private int deaschedule;
	private String cpt		;
	private String hcpc	;
	private String brandequivalentndc;
	private String gcn		;
	private String hicl	;
	private int itemadministrationid;
	private int dosageformid		;
	private int dispensingunitid	;
	private int unitsperlabel		;
	private double stocksize;
	private int packagetypeid		;
	private String strength;
	private int defaultdayssupply	;
	private String defaultdirections;
	private int issoldotc;
	private int refrigerationtypeid	;
	private double compoundrecipetotalquantity;
	private int compoundquantitytypeid;
	private String compoundmixingdirections;	
	private double compoundcost;
	private Date compoundcostchangeddate;
	private double awp		;
	private Date awpchangeddate		;
	private double mac		;
	private Date macchangeddate		;
	private double msrp	;
	private Date msrpchangeddate		;
	private int allowsubstitutions	;	
	private String usermonograph	;
	private String usermonographspanish	;
	private String criticalcomment	;
	private String informationalcomment	;
	private String 	hiddencomment	;
	private int needsreview;
	private String audithistory	;
	private String statustypeid;
	private Date statuschangedon		;
	private Date dateadded			;
	private Date changedon			;
	private String changedby;
	private String changedat;
	private int manuallyentered		;
	private String legacynumber;
	private double lastcostpaid;
	private double compoundcalculatedcost;
	private double compoundcalculatedawp;
	private double compoundcalculatedmac;
	private String compoundflavorid;
	private double compoundminutestomake;
	private double compoundbeyondusedatevalue;
	private int compoundbeyondusedatetypeid;
	private int inventorymanagedbylot;
	private String alternateid;
	private double compoundadditionalcost;
	private String conversionlog	;
	private int batchmethodtypeid	;	
	private int alwaysenterprice	;
	private double compoundawpoverride	;
	private double compoundmacoverride	;
	private double compoundcostoverride;
	private int isflex	;
	private String legacynumberretail;
	private int isdeleted;
	private String receiveinventorytogrouptypeid;	
	private String itemidmergedto;
	private int blisterpacklabelfactor;
	private String subforoverride;
	private double dispensingunitsperlabel;
	private double commondispensedsize		;
	private int ingredienttransmittypeid;
	private int alwaystaxasnonrx	;
	private int lookalikesoundaliketypeid;
	private String upc12withcheckdigit;
	private String createdat;
	private String createdby;
	private String approvedby;
	private int sharedstatustypeid	;
	private int isiv	;
	private int packindividually	;
	private String labeloverridereportid	;
	private int compoundtypeid		;
	private String snomedtypeid;
	private String compoundinventorygrouptypeid;
	private int centralneedsreview	;
	private String centralreviewby;
	private Date centralreviewon		;
	private int centralautoaddtolocations;	
	private Date changedonutc		;
	private int agerestrictedyearsold;
	private Date centraladdedonutc	;
	private int pharmacistsaleonly	;
	private Date centralchangedonutc	;
	private int blackboxwarningtypeid;
	private int itemcentralkey		;
	private Date centralstoresyncedonutc;
	private int iscentralstatus		;
	private String upn		;
	private String marwarningtext	;
	private String createdfromcatalogitemid;
	private int creationsourcetypeid;
	private int specialhandling		;
	private String specialhandlingtext;	
	private int isunitdose;
	private String primarycategoryid;
	private int immunizationreporttypeid;
	private String salecomment		;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	public String getItemid() {
		return itemid;
	}
	public void setItemid(String itemid) {
		this.itemid = itemid;
	}
	public String getLocationid() {
		return locationid;
	}
	public void setLocationid(String locationid) {
		this.locationid = locationid;
	}
	public int getItemtypeid() {
		return itemtypeid;
	}
	public void setItemtypeid(int itemtypeid) {
		this.itemtypeid = itemtypeid;
	}
	public String getItemname() {
		return itemname;
	}
	public void setItemname(String itemname) {
		this.itemname = itemname;
	}
	public String getPrintname() {
		return printname;
	}
	public void setPrintname(String printname) {
		this.printname = printname;
	}
	public String getItemgroup() {
		return itemgroup;
	}
	public void setItemgroup(String itemgroup) {
		this.itemgroup = itemgroup;
	}
	public String getUpc() {
		return upc;
	}
	public void setUpc(String upc) {
		this.upc = upc;
	}
	public String getNdc() {
		return ndc;
	}
	public void setNdc(String ndc) {
		this.ndc = ndc;
	}
	public String getManufacturer() {
		return manufacturer;
	}
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	public String getDepartmentid() {
		return departmentid;
	}
	public void setDepartmentid(String departmentid) {
		this.departmentid = departmentid;
	}
	public int getLabeltypeid() {
		return labeltypeid;
	}
	public void setLabeltypeid(int labeltypeid) {
		this.labeltypeid = labeltypeid;
	}
	public int getDrugclassid() {
		return drugclassid;
	}
	public void setDrugclassid(int drugclassid) {
		this.drugclassid = drugclassid;
	}
	public int getDeaschedule() {
		return deaschedule;
	}
	public void setDeaschedule(int deaschedule) {
		this.deaschedule = deaschedule;
	}
	public String getCpt() {
		return cpt;
	}
	public void setCpt(String cpt) {
		this.cpt = cpt;
	}
	public String getHcpc() {
		return hcpc;
	}
	public void setHcpc(String hcpc) {
		this.hcpc = hcpc;
	}
	public String getBrandequivalentndc() {
		return brandequivalentndc;
	}
	public void setBrandequivalentndc(String brandequivalentndc) {
		this.brandequivalentndc = brandequivalentndc;
	}
	public String getGcn() {
		return gcn;
	}
	public void setGcn(String gcn) {
		this.gcn = gcn;
	}
	public String getHicl() {
		return hicl;
	}
	public void setHicl(String hicl) {
		this.hicl = hicl;
	}
	public int getItemadministrationid() {
		return itemadministrationid;
	}
	public void setItemadministrationid(int itemadministrationid) {
		this.itemadministrationid = itemadministrationid;
	}
	public int getDosageformid() {
		return dosageformid;
	}
	public void setDosageformid(int dosageformid) {
		this.dosageformid = dosageformid;
	}
	public int getDispensingunitid() {
		return dispensingunitid;
	}
	public void setDispensingunitid(int dispensingunitid) {
		this.dispensingunitid = dispensingunitid;
	}
	public int getUnitsperlabel() {
		return unitsperlabel;
	}
	public void setUnitsperlabel(int unitsperlabel) {
		this.unitsperlabel = unitsperlabel;
	}
	public double getStocksize() {
		return stocksize;
	}
	public void setStocksize(double stocksize) {
		this.stocksize = stocksize;
	}
	public int getPackagetypeid() {
		return packagetypeid;
	}
	public void setPackagetypeid(int packagetypeid) {
		this.packagetypeid = packagetypeid;
	}
	public String getStrength() {
		return strength;
	}
	public void setStrength(String strength) {
		this.strength = strength;
	}
	public int getDefaultdayssupply() {
		return defaultdayssupply;
	}
	public void setDefaultdayssupply(int defaultdayssupply) {
		this.defaultdayssupply = defaultdayssupply;
	}
	public String getDefaultdirections() {
		return defaultdirections;
	}
	public void setDefaultdirections(String defaultdirections) {
		this.defaultdirections = defaultdirections;
	}
	public int getIssoldotc() {
		return issoldotc;
	}
	public void setIssoldotc(int issoldotc) {
		this.issoldotc = issoldotc;
	}
	public int getRefrigerationtypeid() {
		return refrigerationtypeid;
	}
	public void setRefrigerationtypeid(int refrigerationtypeid) {
		this.refrigerationtypeid = refrigerationtypeid;
	}
	public double getCompoundrecipetotalquantity() {
		return compoundrecipetotalquantity;
	}
	public void setCompoundrecipetotalquantity(double compoundrecipetotalquantity) {
		this.compoundrecipetotalquantity = compoundrecipetotalquantity;
	}
	public int getCompoundquantitytypeid() {
		return compoundquantitytypeid;
	}
	public void setCompoundquantitytypeid(int compoundquantitytypeid) {
		this.compoundquantitytypeid = compoundquantitytypeid;
	}
	public String getCompoundmixingdirections() {
		return compoundmixingdirections;
	}
	public void setCompoundmixingdirections(String compoundmixingdirections) {
		this.compoundmixingdirections = compoundmixingdirections;
	}
	public double getCompoundcost() {
		return compoundcost;
	}
	public void setCompoundcost(double compoundcost) {
		this.compoundcost = compoundcost;
	}
	public Date getCompoundcostchangeddate() {
		return compoundcostchangeddate;
	}
	public void setCompoundcostchangeddate(Date compoundcostchangeddate) {
		this.compoundcostchangeddate = compoundcostchangeddate;
	}
	public double getAwp() {
		return awp;
	}
	public void setAwp(double awp) {
		this.awp = awp;
	}
	public Date getAwpchangeddate() {
		return awpchangeddate;
	}
	public void setAwpchangeddate(Date awpchangeddate) {
		this.awpchangeddate = awpchangeddate;
	}
	public double getMac() {
		return mac;
	}
	public void setMac(double mac) {
		this.mac = mac;
	}
	public Date getMacchangeddate() {
		return macchangeddate;
	}
	public void setMacchangeddate(Date macchangeddate) {
		this.macchangeddate = macchangeddate;
	}
	public double getMsrp() {
		return msrp;
	}
	public void setMsrp(double msrp) {
		this.msrp = msrp;
	}
	public Date getMsrpchangeddate() {
		return msrpchangeddate;
	}
	public void setMsrpchangeddate(Date msrpchangeddate) {
		this.msrpchangeddate = msrpchangeddate;
	}
	public int getAllowsubstitutions() {
		return allowsubstitutions;
	}
	public void setAllowsubstitutions(int allowsubstitutions) {
		this.allowsubstitutions = allowsubstitutions;
	}
	public String getUsermonograph() {
		return usermonograph;
	}
	public void setUsermonograph(String usermonograph) {
		this.usermonograph = usermonograph;
	}
	public String getUsermonographspanish() {
		return usermonographspanish;
	}
	public void setUsermonographspanish(String usermonographspanish) {
		this.usermonographspanish = usermonographspanish;
	}
	public String getCriticalcomment() {
		return criticalcomment;
	}
	public void setCriticalcomment(String criticalcomment) {
		this.criticalcomment = criticalcomment;
	}
	public String getInformationalcomment() {
		return informationalcomment;
	}
	public void setInformationalcomment(String informationalcomment) {
		this.informationalcomment = informationalcomment;
	}
	public String getHiddencomment() {
		return hiddencomment;
	}
	public void setHiddencomment(String hiddencomment) {
		this.hiddencomment = hiddencomment;
	}
	public int getNeedsreview() {
		return needsreview;
	}
	public void setNeedsreview(int needsreview) {
		this.needsreview = needsreview;
	}
	public String getAudithistory() {
		return audithistory;
	}
	public void setAudithistory(String audithistory) {
		this.audithistory = audithistory;
	}
	public String getStatustypeid() {
		return statustypeid;
	}
	public void setStatustypeid(String statustypeid) {
		this.statustypeid = statustypeid;
	}
	public Date getStatuschangedon() {
		return statuschangedon;
	}
	public void setStatuschangedon(Date statuschangedon) {
		this.statuschangedon = statuschangedon;
	}
	public Date getDateadded() {
		return dateadded;
	}
	public void setDateadded(Date dateadded) {
		this.dateadded = dateadded;
	}
	public Date getChangedon() {
		return changedon;
	}
	public void setChangedon(Date changedon) {
		this.changedon = changedon;
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
	public int getManuallyentered() {
		return manuallyentered;
	}
	public void setManuallyentered(int manuallyentered) {
		this.manuallyentered = manuallyentered;
	}
	public String getLegacynumber() {
		return legacynumber;
	}
	public void setLegacynumber(String legacynumber) {
		this.legacynumber = legacynumber;
	}
	public double getLastcostpaid() {
		return lastcostpaid;
	}
	public void setLastcostpaid(double lastcostpaid) {
		this.lastcostpaid = lastcostpaid;
	}
	public double getCompoundcalculatedcost() {
		return compoundcalculatedcost;
	}
	public void setCompoundcalculatedcost(double compoundcalculatedcost) {
		this.compoundcalculatedcost = compoundcalculatedcost;
	}
	public double getCompoundcalculatedawp() {
		return compoundcalculatedawp;
	}
	public void setCompoundcalculatedawp(double compoundcalculatedawp) {
		this.compoundcalculatedawp = compoundcalculatedawp;
	}
	public double getCompoundcalculatedmac() {
		return compoundcalculatedmac;
	}
	public void setCompoundcalculatedmac(double compoundcalculatedmac) {
		this.compoundcalculatedmac = compoundcalculatedmac;
	}
	public String getCompoundflavorid() {
		return compoundflavorid;
	}
	public void setCompoundflavorid(String compoundflavorid) {
		this.compoundflavorid = compoundflavorid;
	}
	public double getCompoundminutestomake() {
		return compoundminutestomake;
	}
	public void setCompoundminutestomake(double compoundminutestomake) {
		this.compoundminutestomake = compoundminutestomake;
	}
	public double getCompoundbeyondusedatevalue() {
		return compoundbeyondusedatevalue;
	}
	public void setCompoundbeyondusedatevalue(double compoundbeyondusedatevalue) {
		this.compoundbeyondusedatevalue = compoundbeyondusedatevalue;
	}
	public int getCompoundbeyondusedatetypeid() {
		return compoundbeyondusedatetypeid;
	}
	public void setCompoundbeyondusedatetypeid(int compoundbeyondusedatetypeid) {
		this.compoundbeyondusedatetypeid = compoundbeyondusedatetypeid;
	}
	public int getInventorymanagedbylot() {
		return inventorymanagedbylot;
	}
	public void setInventorymanagedbylot(int inventorymanagedbylot) {
		this.inventorymanagedbylot = inventorymanagedbylot;
	}
	public String getAlternateid() {
		return alternateid;
	}
	public void setAlternateid(String alternateid) {
		this.alternateid = alternateid;
	}
	public double getCompoundadditionalcost() {
		return compoundadditionalcost;
	}
	public void setCompoundadditionalcost(double compoundadditionalcost) {
		this.compoundadditionalcost = compoundadditionalcost;
	}
	public String getConversionlog() {
		return conversionlog;
	}
	public void setConversionlog(String conversionlog) {
		this.conversionlog = conversionlog;
	}
	public int getBatchmethodtypeid() {
		return batchmethodtypeid;
	}
	public void setBatchmethodtypeid(int batchmethodtypeid) {
		this.batchmethodtypeid = batchmethodtypeid;
	}
	public int getAlwaysenterprice() {
		return alwaysenterprice;
	}
	public void setAlwaysenterprice(int alwaysenterprice) {
		this.alwaysenterprice = alwaysenterprice;
	}
	public double getCompoundawpoverride() {
		return compoundawpoverride;
	}
	public void setCompoundawpoverride(double compoundawpoverride) {
		this.compoundawpoverride = compoundawpoverride;
	}
	public double getCompoundmacoverride() {
		return compoundmacoverride;
	}
	public void setCompoundmacoverride(double compoundmacoverride) {
		this.compoundmacoverride = compoundmacoverride;
	}
	public double getCompoundcostoverride() {
		return compoundcostoverride;
	}
	public void setCompoundcostoverride(double compoundcostoverride) {
		this.compoundcostoverride = compoundcostoverride;
	}
	public int getIsflex() {
		return isflex;
	}
	public void setIsflex(int isflex) {
		this.isflex = isflex;
	}
	public String getLegacynumberretail() {
		return legacynumberretail;
	}
	public void setLegacynumberretail(String legacynumberretail) {
		this.legacynumberretail = legacynumberretail;
	}
	public int getIsdeleted() {
		return isdeleted;
	}
	public void setIsdeleted(int isdeleted) {
		this.isdeleted = isdeleted;
	}
	public String getReceiveinventorytogrouptypeid() {
		return receiveinventorytogrouptypeid;
	}
	public void setReceiveinventorytogrouptypeid(
			String receiveinventorytogrouptypeid) {
		this.receiveinventorytogrouptypeid = receiveinventorytogrouptypeid;
	}
	public String getItemidmergedto() {
		return itemidmergedto;
	}
	public void setItemidmergedto(String itemidmergedto) {
		this.itemidmergedto = itemidmergedto;
	}
	public int getBlisterpacklabelfactor() {
		return blisterpacklabelfactor;
	}
	public void setBlisterpacklabelfactor(int blisterpacklabelfactor) {
		this.blisterpacklabelfactor = blisterpacklabelfactor;
	}
	public String getSubforoverride() {
		return subforoverride;
	}
	public void setSubforoverride(String subforoverride) {
		this.subforoverride = subforoverride;
	}
	public double getDispensingunitsperlabel() {
		return dispensingunitsperlabel;
	}
	public void setDispensingunitsperlabel(double dispensingunitsperlabel) {
		this.dispensingunitsperlabel = dispensingunitsperlabel;
	}
	public double getCommondispensedsize() {
		return commondispensedsize;
	}
	public void setCommondispensedsize(double commondispensedsize) {
		this.commondispensedsize = commondispensedsize;
	}
	public int getIngredienttransmittypeid() {
		return ingredienttransmittypeid;
	}
	public void setIngredienttransmittypeid(int ingredienttransmittypeid) {
		this.ingredienttransmittypeid = ingredienttransmittypeid;
	}
	public int getAlwaystaxasnonrx() {
		return alwaystaxasnonrx;
	}
	public void setAlwaystaxasnonrx(int alwaystaxasnonrx) {
		this.alwaystaxasnonrx = alwaystaxasnonrx;
	}
	public int getLookalikesoundaliketypeid() {
		return lookalikesoundaliketypeid;
	}
	public void setLookalikesoundaliketypeid(int lookalikesoundaliketypeid) {
		this.lookalikesoundaliketypeid = lookalikesoundaliketypeid;
	}
	public String getUpc12withcheckdigit() {
		return upc12withcheckdigit;
	}
	public void setUpc12withcheckdigit(String upc12withcheckdigit) {
		this.upc12withcheckdigit = upc12withcheckdigit;
	}
	public String getCreatedat() {
		return createdat;
	}
	public void setCreatedat(String createdat) {
		this.createdat = createdat;
	}
	public String getCreatedby() {
		return createdby;
	}
	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}
	public String getApprovedby() {
		return approvedby;
	}
	public void setApprovedby(String approvedby) {
		this.approvedby = approvedby;
	}
	public int getSharedstatustypeid() {
		return sharedstatustypeid;
	}
	public void setSharedstatustypeid(int sharedstatustypeid) {
		this.sharedstatustypeid = sharedstatustypeid;
	}
	public int getIsiv() {
		return isiv;
	}
	public void setIsiv(int isiv) {
		this.isiv = isiv;
	}
	public int getPackindividually() {
		return packindividually;
	}
	public void setPackindividually(int packindividually) {
		this.packindividually = packindividually;
	}
	public String getLabeloverridereportid() {
		return labeloverridereportid;
	}
	public void setLabeloverridereportid(String labeloverridereportid) {
		this.labeloverridereportid = labeloverridereportid;
	}
	public int getCompoundtypeid() {
		return compoundtypeid;
	}
	public void setCompoundtypeid(int compoundtypeid) {
		this.compoundtypeid = compoundtypeid;
	}
	public String getSnomedtypeid() {
		return snomedtypeid;
	}
	public void setSnomedtypeid(String snomedtypeid) {
		this.snomedtypeid = snomedtypeid;
	}
	public String getCompoundinventorygrouptypeid() {
		return compoundinventorygrouptypeid;
	}
	public void setCompoundinventorygrouptypeid(String compoundinventorygrouptypeid) {
		this.compoundinventorygrouptypeid = compoundinventorygrouptypeid;
	}
	public int getCentralneedsreview() {
		return centralneedsreview;
	}
	public void setCentralneedsreview(int centralneedsreview) {
		this.centralneedsreview = centralneedsreview;
	}
	public String getCentralreviewby() {
		return centralreviewby;
	}
	public void setCentralreviewby(String centralreviewby) {
		this.centralreviewby = centralreviewby;
	}
	public Date getCentralreviewon() {
		return centralreviewon;
	}
	public void setCentralreviewon(Date centralreviewon) {
		this.centralreviewon = centralreviewon;
	}
	public int getCentralautoaddtolocations() {
		return centralautoaddtolocations;
	}
	public void setCentralautoaddtolocations(int centralautoaddtolocations) {
		this.centralautoaddtolocations = centralautoaddtolocations;
	}
	public Date getChangedonutc() {
		return changedonutc;
	}
	public void setChangedonutc(Date changedonutc) {
		this.changedonutc = changedonutc;
	}
	public int getAgerestrictedyearsold() {
		return agerestrictedyearsold;
	}
	public void setAgerestrictedyearsold(int agerestrictedyearsold) {
		this.agerestrictedyearsold = agerestrictedyearsold;
	}
	public Date getCentraladdedonutc() {
		return centraladdedonutc;
	}
	public void setCentraladdedonutc(Date centraladdedonutc) {
		this.centraladdedonutc = centraladdedonutc;
	}
	public int getPharmacistsaleonly() {
		return pharmacistsaleonly;
	}
	public void setPharmacistsaleonly(int pharmacistsaleonly) {
		this.pharmacistsaleonly = pharmacistsaleonly;
	}
	public Date getCentralchangedonutc() {
		return centralchangedonutc;
	}
	public void setCentralchangedonutc(Date centralchangedonutc) {
		this.centralchangedonutc = centralchangedonutc;
	}
	public int getBlackboxwarningtypeid() {
		return blackboxwarningtypeid;
	}
	public void setBlackboxwarningtypeid(int blackboxwarningtypeid) {
		this.blackboxwarningtypeid = blackboxwarningtypeid;
	}
	public int getItemcentralkey() {
		return itemcentralkey;
	}
	public void setItemcentralkey(int itemcentralkey) {
		this.itemcentralkey = itemcentralkey;
	}
	public Date getCentralstoresyncedonutc() {
		return centralstoresyncedonutc;
	}
	public void setCentralstoresyncedonutc(Date centralstoresyncedonutc) {
		this.centralstoresyncedonutc = centralstoresyncedonutc;
	}
	public int getIscentralstatus() {
		return iscentralstatus;
	}
	public void setIscentralstatus(int iscentralstatus) {
		this.iscentralstatus = iscentralstatus;
	}
	public String getUpn() {
		return upn;
	}
	public void setUpn(String upn) {
		this.upn = upn;
	}
	public String getMarwarningtext() {
		return marwarningtext;
	}
	public void setMarwarningtext(String marwarningtext) {
		this.marwarningtext = marwarningtext;
	}
	public String getCreatedfromcatalogitemid() {
		return createdfromcatalogitemid;
	}
	public void setCreatedfromcatalogitemid(String createdfromcatalogitemid) {
		this.createdfromcatalogitemid = createdfromcatalogitemid;
	}
	public int getCreationsourcetypeid() {
		return creationsourcetypeid;
	}
	public void setCreationsourcetypeid(int creationsourcetypeid) {
		this.creationsourcetypeid = creationsourcetypeid;
	}
	public int getSpecialhandling() {
		return specialhandling;
	}
	public void setSpecialhandling(int specialhandling) {
		this.specialhandling = specialhandling;
	}
	public String getSpecialhandlingtext() {
		return specialhandlingtext;
	}
	public void setSpecialhandlingtext(String specialhandlingtext) {
		this.specialhandlingtext = specialhandlingtext;
	}
	public int getIsunitdose() {
		return isunitdose;
	}
	public void setIsunitdose(int isunitdose) {
		this.isunitdose = isunitdose;
	}
	public String getPrimarycategoryid() {
		return primarycategoryid;
	}
	public void setPrimarycategoryid(String primarycategoryid) {
		this.primarycategoryid = primarycategoryid;
	}
	public int getImmunizationreporttypeid() {
		return immunizationreporttypeid;
	}
	public void setImmunizationreporttypeid(int immunizationreporttypeid) {
		this.immunizationreporttypeid = immunizationreporttypeid;
	}
	public String getSalecomment() {
		return salecomment;
	}
	public void setSalecomment(String salecomment) {
		this.salecomment = salecomment;
	}

}
