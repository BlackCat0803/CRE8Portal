package com.pharma.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pharma.core.model.PrescriberTypeMaster;
/**
 * This class is a Java interface that defines a data access contract
 *
 */
@Repository("prescriberTypeMaster")
public interface PrescriberTypeMasterRepository extends JpaRepository<PrescriberTypeMaster, Integer> {

}
