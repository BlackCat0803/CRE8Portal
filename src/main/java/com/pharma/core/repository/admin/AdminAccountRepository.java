package com.pharma.core.repository.admin;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pharma.core.model.admin.AdminAccount;
/**
 * This class is a Java interface that defines a data access contract
 *
 */
@Repository("adminAccountRepository")
public interface AdminAccountRepository extends JpaRepository<AdminAccount, Integer> {
	
	@Query("select COALESCE(count(c),0) from AdminAccount c where c.name like %:name% and status=:status")
	int findByNameAndStatus(@Param("name") String name, @Param("status") String status);
	
	@Query("Select c from AdminAccount c where c.name like %:name% and status=:status ")
	Page<AdminAccount> findByNameAndStatus(@Param("name") String name, @Param("status") String status, Pageable page);
	
	AdminAccount findByEmailAndPassword(String email, String password);
	
	List<AdminAccount> findByEmailAndIdNot(String email, int id);
	
	List<AdminAccount> findByEmail(String email);
	
	AdminAccount findById(int id);
	
	@Query("select COALESCE(count(c),0) from AdminAccount c where status=:status")
	int findByStatus(@Param("status") String status);
	
	@Query("select c from AdminAccount c where status=:status order by c.id desc")
	Page<AdminAccount> findByStatus(@Param("status") String status, Pageable page);
	
	@Query("select COALESCE(count(c),0) from AdminAccount c  order by case when c.status='Active' then 1 when c.status='Inactive' then 2 end,c.id desc")
	int findByAllStatus();
	
	@Query("select c from AdminAccount c  order by case when c.status='Active' then 1 when c.status='Inactive' then 2 end,c.id desc")
	Page<AdminAccount> findByAllStatus(Pageable page);
	
	@Query("select COALESCE(count(a),0) from AdminAccount a, Pharmacy p where a.pharmacyId=p.id and a.name like %:admin% and  p.pharmacyName like %:pharmacy% and a.status=:status")
	int findByStatusWidthSearch(@Param("admin") String admin, @Param("pharmacy") String pharmacy, @Param("status") String status);

	@Query("select a from AdminAccount a, Pharmacy p where a.pharmacyId=p.id and a.name like %:admin% and  p.pharmacyName like %:pharmacy% and a.status=:status order by a.id desc")
	Page<AdminAccount> findByStatusWidthSearch(@Param("admin") String admin, @Param("pharmacy") String pharmacy, @Param("status") String status, Pageable page);
	
	@Query("select COALESCE(count(a),0) from AdminAccount a, Pharmacy p where a.pharmacyId=p.id and a.name like %:admin% and  p.pharmacyName like %:pharmacy%  order by case when a.status='Active' then 1 when a.status='Inactive' then 2 end,a.id desc")
	int findByStatusWidthSearch(@Param("admin") String admin, @Param("pharmacy") String pharmacy);

	@Query("select a from AdminAccount a, Pharmacy p where a.pharmacyId=p.id and a.name like %:admin% and  p.pharmacyName like %:pharmacy%  order by case when a.status='Active' then 1 when a.status='Inactive' then 2 end,a.id desc")
	Page<AdminAccount> findByStatusWidthSearch(@Param("admin") String admin, @Param("pharmacy") String pharmacy, Pageable page);
	
	AdminAccount findByEmailAndType(String email, String type);
	
	@Query("select COALESCE(count(a),0) from AdminAccount a, Pharmacy p where a.pharmacyId=p.id and a.name like %:admin% and  p.pharmacyName like %:pharmacy% and a.type=:userType and a.status=:status")
	int findWidthUserTypeAndSearch(@Param("admin") String admin, @Param("pharmacy") String pharmacy, @Param("userType") String userType, @Param("status") String status);
	
	@Query("select a from AdminAccount a, Pharmacy p where a.pharmacyId=p.id and a.name like %:admin% and  p.pharmacyName like %:pharmacy% and a.type=:userType and a.status=:status order by a.id desc")
	Page<AdminAccount> findWidthUserTypeAndSearch(@Param("admin") String admin, @Param("pharmacy") String pharmacy, @Param("userType") String userType, @Param("status") String status, Pageable page);
	
	@Query("select COALESCE(count(a),0) from AdminAccount a, Pharmacy p where a.pharmacyId=p.id and a.name like %:admin% and  p.pharmacyName like %:pharmacy% and a.type=:userType  order by case when a.status='Active' then 1 when a.status='Inactive' then 2 end,a.id desc")
	int findWidthUserTypeAndSearch(@Param("admin") String admin, @Param("pharmacy") String pharmacy, @Param("userType") String userType);
	
	@Query("select a from AdminAccount a, Pharmacy p where a.pharmacyId=p.id and a.name like %:admin% and  p.pharmacyName like %:pharmacy% and a.type=:userType  order by case when a.status='Active' then 1 when a.status='Inactive' then 2 end,a.id desc")
	Page<AdminAccount> findWidthUserTypeAndSearch(@Param("admin") String admin, @Param("pharmacy") String pharmacy, @Param("userType") String userType, Pageable page);

}
