package com.pharma.core.repository.admin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pharma.core.model.admin.AdminPermissions;
/**
 * This class is a Java interface that defines a data access contract
 *
 */
@Repository("adminPermissionsRepository")
public interface AdminPermissionsRepository extends JpaRepository<AdminPermissions, Integer>  {

	
	AdminPermissions findById(int id);
	
}
