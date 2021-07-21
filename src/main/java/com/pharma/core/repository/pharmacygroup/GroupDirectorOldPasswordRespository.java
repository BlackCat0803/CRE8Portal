package com.pharma.core.repository.pharmacygroup;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pharma.core.model.pharmacygroup.GroupDirectorOldPassword;
/**
 * This class is a Java interface that defines a data access contract
 *
 */

@Repository("GroupDirectorOldPassword")
public interface GroupDirectorOldPasswordRespository extends JpaRepository<GroupDirectorOldPassword, Integer> {
	
	List<GroupDirectorOldPassword> findByPasswordAndGroupDirectorId(String password,int id);
	
	List<GroupDirectorOldPassword> findByGroupDirectorIdOrderByRowIdAsc(int groupDirectorId);
	
}
