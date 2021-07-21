package com.pharma.core.repository.pioneer;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pharma.core.model.pioneer.PointofsaleShipperType;

/**
 * This class is a Java interface that defines a data access contract
 *
 */
@Repository("pointofsaleShipperTypeRepository")
public interface PointofsaleShipperTypeRepository extends JpaRepository<PointofsaleShipperType, Integer> {
	
	
	List<PointofsaleShipperType> findByIsdeleted(int statusId);
	
	
	List<PointofsaleShipperType> findByShippertypetext(String shippertypetext);
}
