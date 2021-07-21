package com.pharma.core.model.physician;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.pharma.core.converters.JPACryptoConverter;

/**
 * This class is a JPA entity class
 */

@Entity
@Table(name= "phy_creditcard") 
public class PhysicianCreditCardDetails {
	

	
	private int physicianId;
	private String cardType;
	private String cardNumber;
	private String cardCvcNumber;
	private String cardHolderName;
	private String expMonth;
	private String expYear;
	private String billingZipCode;

	
	// Constructors

	/** default constructor */

	public PhysicianCreditCardDetails(){
	}

	//Property accessors
	@Id
	@Column(name = "physician_id", unique = true, nullable = false)
	public int getPhysicianId() {
		return physicianId;
	}
	public void setPhysicianId(int physicianId) {
		this.physicianId = physicianId;
	}
	@Column(columnDefinition= "LONGBLOB", name = "card_type")
	@Convert(converter = JPACryptoConverter.class)
	//@Column(name = "card_type", nullable = true)
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	@Column(columnDefinition= "LONGBLOB", name = "card_number")
	@Convert(converter = JPACryptoConverter.class)
	//@Column(name = "card_number", nullable = false, length=25)
	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	@Column(columnDefinition= "LONGBLOB", name = "card_cvc_number")
	@Convert(converter = JPACryptoConverter.class)
	//@Column(name = "card_cvc_number", nullable = true, length=4)
	public String getCardCvcNumber() {
		return cardCvcNumber;
	}

	public void setCardCvcNumber(String cardCvcNumber) {
		this.cardCvcNumber = cardCvcNumber;
	}
	@Column(columnDefinition= "LONGBLOB", name = "card_holder_name")
	@Convert(converter = JPACryptoConverter.class)
	//@Column(name = "card_holder_name", nullable = true, length=120)
	public String getCardHolderName() {
		return cardHolderName;
	}

	public void setCardHolderName(String cardHolderName) {
		this.cardHolderName = cardHolderName;
	}
	@Column(columnDefinition= "LONGBLOB", name = "card_expiry_month")
	@Convert(converter = JPACryptoConverter.class)
	//@Column(name = "card_expiry_month", nullable = true)
	public String getExpMonth() {
		return expMonth;
	}
	public void setExpMonth(String expMonth) {
		this.expMonth = expMonth;
	}
	@Column(columnDefinition= "LONGBLOB", name = "card_expiry_year")
	@Convert(converter = JPACryptoConverter.class)
	//@Column(name = "card_expiry_year", nullable = true)
	public String getExpYear() {
		return expYear;
	}
	public void setExpYear(String expYear) {
		this.expYear = expYear;
	}

	public String getBillingZipCode() {
		return billingZipCode;
	}

	public void setBillingZipCode(String billingZipCode) {
		this.billingZipCode = billingZipCode;
	}

	
	

}
