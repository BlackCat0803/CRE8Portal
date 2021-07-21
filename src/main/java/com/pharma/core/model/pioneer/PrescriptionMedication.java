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
@Table(name="[pioneer.fdb.medmedication]")
public class PrescriptionMedication {

	private int medicationid;
	private int routeddosageformmedicationid;
	private String strength;
	private String strengthunitofmeasure;
	private String medicationdescription;
	private int clinicalformulationid;
	private String clinicalformulationidassignmentcode;
	private String medicationnamesourcecode;
	private String referencefederallegendindicator;
	private String referencefederaldeaclasscode;
	private String referencemultisourcecode;
	private String referencegenericmedicationnamecode;
	private String referencegenericcomparativepricecode;
	private String referencegenericpricespreadcode;
	private String referenceinnovatorindicator;
	private String referencegenerictherapeuticequivalencecode;
	private String referencedesiindicator;
	private String referencedesi2indicator;
	private String medicationstatuscode;
	private int genericmedicationid;
	private int medtype;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	public int getMedicationid() {
		return medicationid;
	}
	public void setMedicationid(int medicationid) {
		this.medicationid = medicationid;
	}
	public int getRouteddosageformmedicationid() {
		return routeddosageformmedicationid;
	}
	public void setRouteddosageformmedicationid(int routeddosageformmedicationid) {
		this.routeddosageformmedicationid = routeddosageformmedicationid;
	}
	public String getStrength() {
		return strength;
	}
	public void setStrength(String strength) {
		this.strength = strength;
	}
	public String getStrengthunitofmeasure() {
		return strengthunitofmeasure;
	}
	public void setStrengthunitofmeasure(String strengthunitofmeasure) {
		this.strengthunitofmeasure = strengthunitofmeasure;
	}
	public String getMedicationdescription() {
		return medicationdescription;
	}
	public void setMedicationdescription(String medicationdescription) {
		this.medicationdescription = medicationdescription;
	}
	public int getClinicalformulationid() {
		return clinicalformulationid;
	}
	public void setClinicalformulationid(int clinicalformulationid) {
		this.clinicalformulationid = clinicalformulationid;
	}
	public String getClinicalformulationidassignmentcode() {
		return clinicalformulationidassignmentcode;
	}
	public void setClinicalformulationidassignmentcode(
			String clinicalformulationidassignmentcode) {
		this.clinicalformulationidassignmentcode = clinicalformulationidassignmentcode;
	}
	public String getMedicationnamesourcecode() {
		return medicationnamesourcecode;
	}
	public void setMedicationnamesourcecode(String medicationnamesourcecode) {
		this.medicationnamesourcecode = medicationnamesourcecode;
	}
	public String getReferencefederallegendindicator() {
		return referencefederallegendindicator;
	}
	public void setReferencefederallegendindicator(
			String referencefederallegendindicator) {
		this.referencefederallegendindicator = referencefederallegendindicator;
	}
	public String getReferencefederaldeaclasscode() {
		return referencefederaldeaclasscode;
	}
	public void setReferencefederaldeaclasscode(String referencefederaldeaclasscode) {
		this.referencefederaldeaclasscode = referencefederaldeaclasscode;
	}
	public String getReferencemultisourcecode() {
		return referencemultisourcecode;
	}
	public void setReferencemultisourcecode(String referencemultisourcecode) {
		this.referencemultisourcecode = referencemultisourcecode;
	}
	public String getReferencegenericmedicationnamecode() {
		return referencegenericmedicationnamecode;
	}
	public void setReferencegenericmedicationnamecode(
			String referencegenericmedicationnamecode) {
		this.referencegenericmedicationnamecode = referencegenericmedicationnamecode;
	}
	public String getReferencegenericcomparativepricecode() {
		return referencegenericcomparativepricecode;
	}
	public void setReferencegenericcomparativepricecode(
			String referencegenericcomparativepricecode) {
		this.referencegenericcomparativepricecode = referencegenericcomparativepricecode;
	}
	public String getReferencegenericpricespreadcode() {
		return referencegenericpricespreadcode;
	}
	public void setReferencegenericpricespreadcode(
			String referencegenericpricespreadcode) {
		this.referencegenericpricespreadcode = referencegenericpricespreadcode;
	}
	public String getReferenceinnovatorindicator() {
		return referenceinnovatorindicator;
	}
	public void setReferenceinnovatorindicator(String referenceinnovatorindicator) {
		this.referenceinnovatorindicator = referenceinnovatorindicator;
	}
	public String getReferencegenerictherapeuticequivalencecode() {
		return referencegenerictherapeuticequivalencecode;
	}
	public void setReferencegenerictherapeuticequivalencecode(
			String referencegenerictherapeuticequivalencecode) {
		this.referencegenerictherapeuticequivalencecode = referencegenerictherapeuticequivalencecode;
	}
	public String getReferencedesiindicator() {
		return referencedesiindicator;
	}
	public void setReferencedesiindicator(String referencedesiindicator) {
		this.referencedesiindicator = referencedesiindicator;
	}
	public String getReferencedesi2indicator() {
		return referencedesi2indicator;
	}
	public void setReferencedesi2indicator(String referencedesi2indicator) {
		this.referencedesi2indicator = referencedesi2indicator;
	}
	public String getMedicationstatuscode() {
		return medicationstatuscode;
	}
	public void setMedicationstatuscode(String medicationstatuscode) {
		this.medicationstatuscode = medicationstatuscode;
	}
	public int getGenericmedicationid() {
		return genericmedicationid;
	}
	public void setGenericmedicationid(int genericmedicationid) {
		this.genericmedicationid = genericmedicationid;
	}
	public int getMedtype() {
		return medtype;
	}
	public void setMedtype(int medtype) {
		this.medtype = medtype;
	}

}
