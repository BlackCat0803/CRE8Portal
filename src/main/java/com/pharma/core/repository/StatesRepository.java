package com.pharma.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pharma.core.model.States;
/**
 * This class is a Java interface that defines a data access contract
 *
 */
@Repository("states")
public interface StatesRepository extends JpaRepository<States, String> {
	
	
	// Autocomplete to get state name
	List<States> findByStateNameIgnoreCaseContaining(String term);

}
