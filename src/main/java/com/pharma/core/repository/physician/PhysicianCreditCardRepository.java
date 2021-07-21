package com.pharma.core.repository.physician;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pharma.core.model.physician.PhysicianCreditCardDetails;
/**
 * This class is a Java interface that defines a data access contract
 *
 */
@Repository("creditCardRepository")
public interface PhysicianCreditCardRepository extends JpaRepository<PhysicianCreditCardDetails, Integer> {

	
}
