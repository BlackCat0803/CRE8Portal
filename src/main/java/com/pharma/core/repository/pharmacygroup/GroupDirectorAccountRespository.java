package com.pharma.core.repository.pharmacygroup;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pharma.core.model.pharmacygroup.GroupDirector;

/**
 * This class is a Java interface that defines a data access contract
 *
 */
@Repository("groupDirector")
public interface GroupDirectorAccountRespository extends JpaRepository<GroupDirector, Integer> {

	List<GroupDirector> findByEmail(String email);

	List<GroupDirector> findByEmailAndIdNot(String email, int id);
	
	GroupDirector findByEmailAndPassword(String email, String password);
	
	GroupDirector findById(int id);

	@Query("select  COALESCE(count(c),0)  from GroupDirector c where status=:status")
	int findByStatus(@Param("status") String status);
	
	@Query("select c from GroupDirector c where status=:status order by c.id desc")
	Page<GroupDirector> findByStatus(@Param("status") String status, Pageable page);
	
	
	@Query("select  COALESCE(count(c),0)  from GroupDirector c order by case when c.status='Active' then 1 when c.status='Inactive' then 2 end,c.id desc")
	int findByAllStatus();
	
	@Query("select c from GroupDirector c order by case when c.status='Active' then 1 when c.status='Inactive' then 2 end,c.id desc")
	Page<GroupDirector> findByAllStatus(Pageable page);
	
	
	@Query("select  COALESCE(count(p),0) from GroupDirector p, GroupMaster c where c.id=p.groupId and p.groupDirectorName like %:groupDirectorName%  order by case when p.status='Active' then 1 when p.status='Inactive' then 2 end,p.id desc")
	int findByGroupDirectorName( @Param("groupDirectorName") String groupDirectorName);
	
	@Query("select p from GroupDirector p, GroupMaster c where c.id=p.groupId and p.groupDirectorName like %:groupDirectorName%   order by case when p.status='Active' then 1 when p.status='Inactive' then 2 end,p.id desc")
	Page<GroupDirector> findByGroupDirectorName(@Param("groupDirectorName") String groupDirectorName, Pageable page);

	@Query("select  COALESCE(count(p),0) from GroupDirector p, GroupMaster c where c.id=p.groupId and c.id=:id  order by case when p.status='Active' then 1 when p.status='Inactive' then 2 end,p.id desc")
	int findByGroupId(@Param("id") int id);
	
	@Query("select p from GroupDirector p, GroupMaster c where c.id=p.groupId and c.id=:id  order by case when p.status='Active' then 1 when p.status='Inactive' then 2 end,p.id desc")
	Page<GroupDirector> findByGroupId(@Param("id") int id, Pageable page);
	
	@Query("select  COALESCE(count(p),0) from GroupDirector p, GroupMaster c where c.id=p.groupId and c.id=:id and p.groupDirectorName like %:groupDirectorName%  order by case when p.status='Active' then 1 when p.status='Inactive' then 2 end,p.id desc")
	int findByGroupIdGroupDirectorName(@Param("id") int id, @Param("groupDirectorName") String groupDirectorName);
	
	@Query("select p from GroupDirector p, GroupMaster c where c.id=p.groupId and c.id=:id and p.groupDirectorName like %:groupDirectorName%  order by case when p.status='Active' then 1 when p.status='Inactive' then 2 end,p.id desc")
	Page<GroupDirector> findByGroupIdGroupDirectorName(@Param("id") int id, @Param("groupDirectorName") String groupDirectorName, Pageable page);

	
	@Query("select  COALESCE(count(c),0)  from GroupDirector c where c.groupId=:id and status=:status")
	int findByGroupIdAndStatus(@Param("id") int id, @Param("status") String status);
	
	
	@Query("select c from GroupDirector c where c.groupId=:id and status=:status order by c.id desc")
	Page<GroupDirector> findByGroupIdAndStatus(@Param("id") int id, @Param("status") String status, Pageable page);
	
	
	@Query("select  COALESCE(count(c),0)  from GroupDirector c where c.groupDirectorName like %:name% and status=:status")
	int findByGroupDirectorNameAndStatus(@Param("name") String name, @Param("status") String status);

	@Query("Select c from GroupDirector c where c.groupDirectorName like %:name% and status=:status  order by c.id desc")
	Page<GroupDirector> findByGroupDirectorNameAndStatus(@Param("name") String name, @Param("status") String status, Pageable page);


	@Query("select  COALESCE(count(p),0) from GroupDirector p, GroupMaster c where c.id=p.groupId and c.id=:id and p.groupDirectorName like %:groupDirectorName% and p.status=:status")
	int findByGroupIdGroupDirectorNameAndStatus(@Param("id") int id, @Param("groupDirectorName") String groupDirectorName,  @Param("status") String status);
	
	@Query("select p from GroupDirector p, GroupMaster c where c.id=p.groupId and c.id=:id and p.groupDirectorName like %:groupDirectorName% and p.status=:status order by p.id desc")
	Page<GroupDirector> findByGroupIdGroupDirectorNameAndStatus(@Param("id") int id, @Param("groupDirectorName") String groupDirectorName, @Param("status") String status, Pageable page);
	
	List<GroupDirector> findNameByGroupId(int id);
	
}
