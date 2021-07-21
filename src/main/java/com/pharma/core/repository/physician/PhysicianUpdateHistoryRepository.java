package com.pharma.core.repository.physician;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pharma.core.model.physician.PhysicianUpdateHistory;
/**
 * This class is a Java interface that defines a data access contract
 *
 */
@Repository("PhysicianUpdateHistoryRepository")
public interface PhysicianUpdateHistoryRepository extends JpaRepository<PhysicianUpdateHistory, Integer> {
	
	List<PhysicianUpdateHistory> findByPhysicianId(int id);

}
