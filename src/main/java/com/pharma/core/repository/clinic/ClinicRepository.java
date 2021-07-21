package com.pharma.core.repository.clinic;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pharma.core.model.clinic.Clinic;
/**
 * This class is a Java interface that defines a data access contract
 *
 */
@Repository("clinicRepository")
public interface ClinicRepository extends JpaRepository<Clinic, Integer> {

	List<Clinic> findByStatus(String status);
	
	@Query("select p from Clinic p order by case when p.status='Info Completed' then 1 when p.status='New' then 2 when p.status='Active' then 3 when p.status='Inactive' then 4 end, p.id desc")
	List<Clinic> findByNotStatus();

	@Query("select a from Clinic a where status=:status or id=:id")
	List<Clinic> findByStatusAndId(@Param("status") String status, @Param("id") int id);
	
	/*@Query("select COALESCE(count(c),0) from Clinic c where ( clinicName like %:clinicName%  and email like %:email% and state=:state ) and status=:status and ((:groupId>0  and COALESCE(groupId,0)>:groupId) or (:groupId=0 and COALESCE(groupId,0)=0))")
	int getSearchTotal(@Param("clinicName") String clinicName, @Param("email") String email, @Param("state") String state,  @Param("status") String status,  @Param("groupId") int groupId);

	@Query("select COALESCE(count(c),0) from Clinic c where clinicName like %:clinicName% and email like %:email%  and status=:status and ((:groupId>0  and COALESCE(groupId,0)>:groupId) or (:groupId=0 and COALESCE(groupId,0)=0))")
	int getSearchTotal(@Param("clinicName") String clinicName, @Param("email") String email, @Param("status") String status,  @Param("groupId") int groupId);
*/
	
	@Query("select COALESCE(count(c),0) from Clinic c where ( clinicName like %:clinicName%  and email like %:email% and state=:state ) and status=:status and groupId=:groupId ")
	int getSearchTotal(@Param("clinicName") String clinicName, @Param("email") String email, @Param("state") String state,  @Param("status") String status,  @Param("groupId") int groupId);
	
	@Query("select COALESCE(count(c),0) from Clinic c where ( clinicName like %:clinicName%  and email like %:email% and state=:state ) and groupId=:groupId  order by case when c.status='Info Completed' then 1 when c.status='New' then 2 when c.status='Active' then 3 when c.status='Inactive' then 4 end, c.id desc")
	int getAllSearchTotal(@Param("clinicName") String clinicName, @Param("email") String email, @Param("state") String state,    @Param("groupId") int groupId);

	@Query("select COALESCE(count(c),0) from Clinic c where clinicName like %:clinicName% and email like %:email%  and status=:status and groupId=:groupId ")
	int getSearchTotal(@Param("clinicName") String clinicName, @Param("email") String email, @Param("status") String status,  @Param("groupId") int groupId);
	
	@Query("select COALESCE(count(c),0) from Clinic c where clinicName like %:clinicName% and email like %:email%  and groupId=:groupId  order by case when c.status='Info Completed' then 1 when c.status='New' then 2 when c.status='Active' then 3 when c.status='Inactive' then 4 end, c.id desc")
	int getAllSearchTotal(@Param("clinicName") String clinicName, @Param("email") String email,  @Param("groupId") int groupId);

	@Query("select COALESCE(count(c),0) from Clinic c where ( clinicName like %:clinicName%  and email like %:email% and state=:state ) and status=:status ")
	int getSearchTotal(@Param("clinicName") String clinicName, @Param("email") String email, @Param("state") String state,  @Param("status") String status);
	
	@Query("select COALESCE(count(c),0) from Clinic c where ( clinicName like %:clinicName%  and email like %:email% and state=:state )  order by case when c.status='Info Completed' then 1 when c.status='New' then 2 when c.status='Active' then 3 when c.status='Inactive' then 4 end, c.id desc")
	int getAllSearchTotal(@Param("clinicName") String clinicName, @Param("email") String email, @Param("state") String state);


	@Query("select COALESCE(count(c),0) from Clinic c where clinicName like %:clinicName% and email like %:email%  and status=:status")
	int getSearchTotal(@Param("clinicName") String clinicName, @Param("email") String email, @Param("status") String status);

	@Query("select COALESCE(count(c),0) from Clinic c where clinicName like %:clinicName% and email like %:email%   order by case when c.status='Info Completed' then 1 when c.status='New' then 2 when c.status='Active' then 3 when c.status='Inactive' then 4 end, c.id desc")
	int getAllSearchTotal(@Param("clinicName") String clinicName, @Param("email") String email);
	
	@Query("select c from Clinic c where status=:status order by id desc")
	Page<Clinic> findByStatus(  @Param("status") String status, Pageable page);
	
	
	@Query("select p from Clinic p order by case when p.status='Info Completed' then 1 when p.status='New' then 2 when p.status='Active' then 3 when p.status='Inactive' then 4 end, p.id desc")
	Page<Clinic> findByNotStatus( Pageable page);
	
	
/*	@Query("select c from Clinic c where ( clinicName like %:clinicName%  and email like %:email% and state=:state ) and status=:status  and ((:groupId>0  and COALESCE(groupId,0)>:groupId) or (:groupId=0 and COALESCE(groupId,0)=0)) order by id desc")
	Page<Clinic> getSearchRecords( @Param("clinicName") String clinicName, @Param("email") String email, @Param("state") String state,  @Param("status") String status,  @Param("groupId") int groupId, Pageable page);

	@Query("select c from Clinic c where clinicName like %:clinicName%  and email like %:email% and status=:status  and ((:groupId>0  and COALESCE(groupId,0)>:groupId) or (:groupId=0 and COALESCE(groupId,0)=0)) order by id desc")
	Page<Clinic> getSearchRecords( @Param("clinicName") String clinicName, @Param("email") String email,  @Param("status") String status,  @Param("groupId") int groupId, Pageable page);
*/
	
	@Query("select c from Clinic c where ( clinicName like %:clinicName%  and email like %:email% and state=:state ) and status=:status and groupId=:groupId order by id desc")
	Page<Clinic> getSearchRecords( @Param("clinicName") String clinicName, @Param("email") String email, @Param("state") String state,  @Param("status") String status,  @Param("groupId") int groupId, Pageable page);

	@Query("select c from Clinic c where ( clinicName like %:clinicName%  and email like %:email% and state=:state ) and groupId=:groupId order by case when c.status='Info Completed' then 1 when c.status='New' then 2 when c.status='Active' then 3 when c.status='Inactive' then 4 end, c.id desc")
	Page<Clinic> getAllSearchRecords( @Param("clinicName") String clinicName, @Param("email") String email, @Param("state") String state, @Param("groupId") int groupId, Pageable page);

	
	@Query("select c from Clinic c where clinicName like %:clinicName%  and email like %:email% and status=:status and groupId=:groupId order by id desc")
	Page<Clinic> getSearchRecords( @Param("clinicName") String clinicName, @Param("email") String email,  @Param("status") String status,  @Param("groupId") int groupId, Pageable page);

	@Query("select c from Clinic c where clinicName like %:clinicName%  and email like %:email% and groupId=:groupId  order by case when c.status='Info Completed' then 1 when c.status='New' then 2 when c.status='Active' then 3 when c.status='Inactive' then 4 end, c.id desc")
	Page<Clinic> getAllSearchRecords( @Param("clinicName") String clinicName, @Param("email") String email,  @Param("groupId") int groupId, Pageable page);
	
	@Query("select c from Clinic c where ( clinicName like %:clinicName%  and email like %:email% and state=:state ) and status=:status order by id desc")
	Page<Clinic> getSearchRecords( @Param("clinicName") String clinicName, @Param("email") String email, @Param("state") String state,  @Param("status") String status,  Pageable page);
	
	@Query("select c from Clinic c where ( clinicName like %:clinicName%  and email like %:email% and state=:state ) order by case when c.status='Info Completed' then 1 when c.status='New' then 2 when c.status='Active' then 3 when c.status='Inactive' then 4 end, c.id desc")
	Page<Clinic> getAllSearchRecords( @Param("clinicName") String clinicName, @Param("email") String email, @Param("state") String state,  Pageable page);


	@Query("select c from Clinic c where clinicName like %:clinicName%  and email like %:email% and status=:status order by id desc")
	Page<Clinic> getSearchRecords( @Param("clinicName") String clinicName, @Param("email") String email,  @Param("status") String status,  Pageable page);

	
	@Query("select c from Clinic c where clinicName like %:clinicName%  and email like %:email%   order by case when c.status='Info Completed' then 1 when c.status='New' then 2 when c.status='Active' then 3 when c.status='Inactive' then 4 end, c.id desc")
	Page<Clinic> getAllSearchRecords( @Param("clinicName") String clinicName, @Param("email") String email,  Pageable page);
	
	
	List<Clinic> findByClinicNameAndIdNot(String clinicName, int id);
	
	List<Clinic> findByClinicName(String clinicName);
	
	List<Clinic> findByClinicNameAndGroupId(String clinicName,int groupId);
	
	// Autocomplete to get list
	@Query("select c from Clinic c where clinicName like %:clinicName% and (status=:status or id=:id)")
	List<Clinic> getAutoCompleteClinicList( @Param("clinicName") String clinicName, @Param("status") String status,@Param("id") int id, Pageable pageable);

	// Autocomplete to get list
	@Query("select c from Clinic c where clinicName like %:clinicName%")
	List<Clinic> getAllAutoCompleteClinicList( @Param("clinicName") String clinicName, Pageable pageable);
	
	List<Clinic> findByEmailAndIdNot(String email, int id);
	
	List<Clinic> findByEmail(String email);
	
	@Query("select c from Clinic c where groupId =:groupId and (status='Active' or id=:clinicId)")
	List<Clinic> getAllGroupWiseClinicList( @Param("groupId") int groupId, @Param("clinicId") int clinicId);
	
	@Query("select c from Clinic c where groupId =:groupId")
	List<Clinic> getGroupWiseAllClinicList( @Param("groupId") int groupId);
	
	/*******************  Group-wise records fetch Queries ***********************/
	
	int findByGroupIdAndStatus(int groupId, String status);

	@Query("select c from Clinic c left join PhysicianClinic pc on c.id=pc.clinicId where (pc.physicianId=:physicianId and pc.delFlag='N' and c.status=:status) or c.id=:clinicId group by c.id")
	List<Clinic> getClinicListByPhysicianIdAndClinicId(@Param("physicianId") int physicianId, @Param("clinicId") int clinicId, @Param("status") String status);
	
	
	@Query("SELECT c FROM Clinic c WHERE c.groupId=:groupId and c.status='Active' and c.id NOT IN (SELECT p.clinicId FROM PhysicianClinic p where  p.physicianId=:physicianId and p.delFlag='N')")
	List<Clinic> getAllPhysicianGroupWiseClinicListNotSelected( @Param("groupId") int groupId, @Param("physicianId") int physicianId);
	
	@Query("select c from PhysicianClinic p, Clinic c where c.groupId=:groupId and p.clinicId = c.id and p.delFlag='N' and p.physicianId=:physicianId and c.status<>'Inactive'")
	List<Clinic> getAllPhysicianGroupWiseClinicListSelected( @Param("groupId") int groupId, @Param("physicianId") int physicianId);

}
