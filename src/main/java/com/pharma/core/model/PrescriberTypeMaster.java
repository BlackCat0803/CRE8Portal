package com.pharma.core.model;

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
@Table(name="prescriber_type_master")
public class PrescriberTypeMaster {

	private int typeId;
	private String prescriberType;
	private String pioneerPrescriberTypeId;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "type_id", unique = true, nullable = false)
	public int getTypeId() {
		return typeId;
	}
	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}
	
	@Column(name = "prescriber_type", nullable = false, length = 10)
	public String getPrescriberType() {
		return prescriberType;
	}
	public void setPrescriberType(String prescriberType) {
		this.prescriberType = prescriberType;
	}
	
	@Column(name = "pioneer_prescriber_type_id", nullable = false, length = 100)
	public String getPioneerPrescriberTypeId() {
		return pioneerPrescriberTypeId;
	}
	public void setPioneerPrescriberTypeId(String pioneerPrescriberTypeId) {
		this.pioneerPrescriberTypeId = pioneerPrescriberTypeId;
	}
	
}
