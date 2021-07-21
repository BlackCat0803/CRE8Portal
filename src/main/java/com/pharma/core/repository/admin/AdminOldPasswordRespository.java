package com.pharma.core.repository.admin;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pharma.core.model.admin.AdminOldPassword;
/**
 * This class is a Java interface that defines a data access contract
 *
 */
@Repository("AdminOldPassword")
public interface AdminOldPasswordRespository extends JpaRepository<AdminOldPassword, Integer> {
	
	List<AdminOldPassword> findByPasswordAndAdminId(String password,int id);
	
	List<AdminOldPassword> findByAdminIdOrderByRowIdAsc(int physicianId);

}
