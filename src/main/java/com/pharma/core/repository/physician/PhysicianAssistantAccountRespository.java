package com.pharma.core.repository.physician;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pharma.core.model.physician.PhysicianAssistantAccount;
/**
 * This class is a Java interface that defines a data access contract
 *
 */
@Repository("physicianAssistantAccount")
public interface PhysicianAssistantAccountRespository extends JpaRepository<PhysicianAssistantAccount, Integer> {

	/* select * from physician_assistant a left join physician_assistant_physician ap on a.physician_assistant_id = ap.physician_assistant_id 
			left join phy_info p on ap.physician_id = p.physician_id */ 
	
	@Query("select a from PhysicianAssistantAccount a left join PhysicianAssistantPhysician ap on ap.physicianAssistantId=a.id "
			+ "left join PhysicianAccount p on ap.physicianId=p.id "
			+ "where a.groupId=:groupId and ap.delFlag='N' and a.assistantName like %:assistant% group by a.id ")
	List<PhysicianAssistantAccount> getAssistantByGroupId(@Param("groupId") int groupId, @Param("assistant") String assistant);
	
	@Query("select a.id, a.assistantName, p.physicianName, a.email, a.phone, a.status from PhysicianAssistantAccount a left join "
			+ "PhysicianAssistantPhysician ap on ap.physicianAssistantId=a.id left join PhysicianAccount p on ap.physicianId=p.id "
			+ "where a.groupId=:groupId and ap.delFlag='N' and a.assistantName like %:assistant% group by a.id ")
	Page<Object[]> getAssistantByGroupId(@Param("groupId") int groupId, @Param("assistant") String assistant, Pageable page);

	PhysicianAssistantAccount findById(int id);
	
	@Query("select a from PhysicianAssistantAccount a left join PhysicianAssistantPhysician ap on ap.physicianAssistantId=a.id "
			+ "left join PhysicianAccount p on ap.physicianId=p.id "
			+ "where a.groupId=:groupId and ap.delFlag='N' and a.assistantName like %:assistant% and a.status=:status group by a.id ")
	List<PhysicianAssistantAccount> getAssistantByGroupId(@Param("groupId") int groupId, @Param("assistant") String assistant, 
			@Param("status") String status);
	
	@Query("select a.id, a.assistantName, p.physicianName, a.email, a.phone, a.status from PhysicianAssistantAccount a left join "
			+ "PhysicianAssistantPhysician ap on ap.physicianAssistantId=a.id left join PhysicianAccount p on ap.physicianId=p.id "
			+ "where a.groupId=:groupId and ap.delFlag='N' and a.assistantName like %:assistant% and a.status=:status group by a.id ")
	Page<Object[]> getAssistantByGroupId(@Param("groupId") int groupId, @Param("assistant") String assistant, 
			@Param("status") String status, Pageable page);
	
	
	PhysicianAssistantAccount findByIdAndGroupId(int id, int groupId);
	
	/*
	select * from phy_clinic c left join phy_info p on c.clinic_id = p.clinic_id left join physician_assistant_physician a 
			on a.physician_id = c.physician_id left join physician_assistant pa on pa.physician_assistant_id = a.physician_assistant_id
			where c.clinic_id =55 and c.delFlag='N';
	*/
	@Query("select pa.id, pa.assistantName, p.physicianName, pa.email, pa.phone from PhysicianClinic c left join "
			+ "PhysicianAccount p on c.physicianId=p.id join PhysicianAssistantPhysician a "
			+ "on a.physicianId=p.id join PhysicianAssistantAccount pa on pa.id=a.physicianAssistantId "
			+ "where c.clinicId=:clinicId and c.delFlag='N' and a.delFlag='N' ")
	Page<Object[]> getAssistantByClinicId(@Param("clinicId") int clinicId, Pageable page);
	
	
	@Query("select pa from PhysicianClinic c left join PhysicianAccount p on c.physicianId=p.id join PhysicianAssistantPhysician a "
			+ "on a.physicianId=p.id join PhysicianAssistantAccount pa on pa.id=a.physicianAssistantId "
			+ "where c.clinicId=:clinicId and c.delFlag='N' and a.delFlag='N' ")
	List<PhysicianAssistantAccount> getAssistantByClinicId(@Param("clinicId") int clinicId);
	
	
	List<PhysicianAssistantAccount> findByEmail(String email);
	
	@Query("Select c from PhysicianAssistantAccount c where c.assistantName like %:name% and status=:status  group by c.id")
	Page<PhysicianAssistantAccount> findByAssistantNameAndStatus(@Param("name") String name, @Param("status") String status, Pageable page);
	

	
	@Query("Select c from PhysicianAssistantAccount c where c.assistantName like %:name%  group by c.id "
			+ "order by case when c.status='Active' then 1 when c.status='Inactive' then 2 end, c.id desc")
	Page<PhysicianAssistantAccount> findByAssistantNameAndStatus(@Param("name") String name, Pageable page);
	
	
	@Query("select  COALESCE(count(c),0)  from PhysicianAssistantAccount c where c.assistantName like %:name% and status=:status  group by c.id")
	int findByAssistantNameAndStatus(@Param("name") String name, @Param("status") String status);

	@Query("select  COALESCE(count(c),0)  from PhysicianAssistantAccount c where status=:status order by c.id desc")
	int findByStatus(@Param("status") String status);

	
	@Query("select  COALESCE(count(c),0)  from PhysicianAssistantAccount c order by c.id desc")
	int findByStatus();

	
	@Query("select c from PhysicianAssistantAccount c where status=:status order by c.id desc") 
	Page<PhysicianAssistantAccount> findByStatus(@Param("status") String status, Pageable page);
	
	
	
	@Query("select c from PhysicianAssistantAccount c order by case when c.status='Active' then 1 when c.status='Inactive' then 2 end, c.id desc") 
	Page<PhysicianAssistantAccount> findByStatus( Pageable page);
	
	
	
	List<PhysicianAssistantAccount> findByEmailAndIdNot(String email, int id);
	
	PhysicianAssistantAccount findByEmailAndPassword(String email, String password);
	
	@Query("select  COALESCE(count(c),0)  from PhysicianAssistantAccount c,PhysicianAssistantPhysician pa "
			+ "where c.id=pa.physicianAssistantId and pa.physicianId=:id and c.status=:status group by c.id order by c.id desc")
	int findByPhysicianIdAndStatus(@Param("id") int id, @Param("status") String status);
	
	 
	
	@Query("select  COALESCE(count(c),0)  from PhysicianAssistantAccount c,PhysicianAssistantPhysician pa "
			+ "where c.id=pa.physicianAssistantId and pa.physicianId=:id group by c.id  order by case when c.status='Active' then 1 "
			+ "when c.status='Inactive' then 2 end, c.id desc")
	int findByPhysicianIdAndStatus(@Param("id") int id);

	
	
	
	@Query("select  COALESCE(count(a),0)  from PhysicianAssistantAccount a, PhysicianAccount p,PhysicianAssistantPhysician pa "
			+ "where pa.physicianId=p.id and a.id=pa.physicianAssistantId and pa.physicianId=:id and a.assistantName like %:assistant% "
			+ "and a.status=:status   group by a.id order by a.id desc")
	int findByAssistantNameAndStatus(@Param("id") int id, @Param("assistant") String assistant,  @Param("status") String status);
	

	@Query("select  COALESCE(count(a),0)  from PhysicianAssistantAccount a, PhysicianAccount p,PhysicianAssistantPhysician pa "
			+ "where pa.physicianId=p.id and a.id=pa.physicianAssistantId and pa.physicianId=:id and a.assistantName like %:assistant% "
			+ "group by a.id order by case when a.status='Active' then 1 when a.status='Inactive' then 2 end, a.id  desc")
	int findByAssistantNameAndStatus(@Param("id") int id, @Param("assistant") String assistant);
	
	
	
	@Query("select a from PhysicianAssistantAccount a, PhysicianAccount p,PhysicianAssistantPhysician pa "
			+ "where pa.physicianId=p.id and a.id=pa.physicianAssistantId and pa.physicianId=:id and a.assistantName like %:assistant% "
			+ "and a.status=:status   group by a.id order by a.id desc")
	Page<PhysicianAssistantAccount> findByAssistantNameAndStatus(@Param("id") int id, @Param("assistant") String assistant, @Param("status") String status, Pageable page);
	
	@Query("select a from PhysicianAssistantAccount a, PhysicianAccount p,PhysicianAssistantPhysician pa "
			+ "where pa.physicianId=p.id and a.id=pa.physicianAssistantId and pa.physicianId=:id and a.assistantName like %:assistant% "
			+ "group by a.id order by case when a.status='Active' then 1 when a.status='Inactive' then 2 end, a.id desc")
	Page<PhysicianAssistantAccount> findByAssistantNameAndStatus(@Param("id") int id, @Param("assistant") String assistant, Pageable page);

	
	
	
	
	@Query("select c from PhysicianAssistantAccount c,PhysicianAssistantPhysician pa where c.id=pa.physicianAssistantId and pa.physicianId=:id "
			+ "and status=:status   group by c.id order by c.id desc")
	Page<PhysicianAssistantAccount> findByPhysicianIdAndStatus(@Param("id") int id, @Param("status") String status, Pageable page);

	
	
	@Query("select c from PhysicianAssistantAccount c,PhysicianAssistantPhysician pa where c.id=pa.physicianAssistantId and pa.physicianId=:id "
			+ "and status=:status   group by c.id order by case when c.status='Active' then 1 when c.status='Inactive' then 2 end, c.id desc")
	Page<PhysicianAssistantAccount> findByPhysicianIdAndStatus(@Param("id") int id,  Pageable page);

	
	
	

	@Query("select  COALESCE(count(a),0)  from PhysicianAssistantAccount a  "
			+ "where (a.assistantName like %:assistant% or a.assistantName is null) and a.status=:status ")
	int findByStatusAndSearch(@Param("assistant") String assistant, @Param("status") String status);

	
	
	@Query("select  COALESCE(count(a),0)  from PhysicianAssistantAccount a where (a.assistantName like %:assistant% or a.assistantName is null)  ")
	int findBySearchAssistant(@Param("assistant") String assistant );


	
	
	
	@Query("select  COALESCE(count(a),0)  from PhysicianAssistantAccount a left join PhysicianAssistantPhysician pa on a.id=pa.physicianAssistantId "
			+ "left join PhysicianAccount p on pa.physicianId=p.id "
			+ "where (a.assistantName like %:assistant% or a.assistantName is null) and (pa.delFlag='N' or pa.delFlag is null)and p.physicianName like %:phyName% "
			+ "and a.status=:status ")
	int findByStatusAndSearch(@Param("assistant") String assistant, @Param("phyName") String phyName,  @Param("status") String status);
	
	
	@Query("select  COALESCE(count(a),0)  from PhysicianAssistantAccount a left join PhysicianAssistantPhysician pa on a.id=pa.physicianAssistantId "
			+ "left join PhysicianAccount p on pa.physicianId=p.id "
			+ "where (a.assistantName like %:assistant% or a.assistantName is null) and (pa.delFlag='N' or pa.delFlag is null)and p.physicianName like %:phyName%  ")
	int findBySearchAssistant(@Param("assistant") String assistant, @Param("phyName") String phyName);

	
	
	
	
	
	/*@Query("select a from PhysicianAssistantAccount a, PhysicianAccount p,PhysicianAssistantPhysician pa where pa.physicianId=p.id and a.id=pa.physicianAssistantId and a.assistantName like %:assistant% and p.physicianName like %:phyName% and a.status=:status   group by a.id order by a.id desc")
	Page<PhysicianAssistantAccount> findByStatusAndSearch(@Param("assistant") String assistant, @Param("phyName") String phyName,  @Param("status") String status, Pageable page);*/
	

	@Query("select a  from PhysicianAssistantAccount a  "
			+ "where (a.assistantName like %:assistant% or a.assistantName is null) and a.status=:status order by a.id desc ")
	Page<PhysicianAssistantAccount> findByStatusAndSearch(@Param("assistant") String assistant,  @Param("status") String status, Pageable page);

	@Query("select a  from PhysicianAssistantAccount a where (a.assistantName like %:assistant% or a.assistantName is null) "
			+ "order by  case when a.status='Active' then 1 when a.status='Inactive' then 2 end,  a.id desc ")
	Page<PhysicianAssistantAccount> findByAssistantSearch(@Param("assistant") String assistant, Pageable page);

	
	
	
	
	@Query("select a  from PhysicianAssistantAccount a left join PhysicianAssistantPhysician pa on a.id=pa.physicianAssistantId "
			+ "left join PhysicianAccount p on pa.physicianId=p.id "
			+ "where (a.assistantName like %:assistant% or a.assistantName is null) and p.physicianName like %:phyName% and (pa.delFlag='N' or pa.delFlag is null)"
			+ "and a.status=:status order by a.id desc ")
	Page<PhysicianAssistantAccount> findByStatusAndSearch(@Param("assistant") String assistant, @Param("phyName") String phyName,  @Param("status") String status, Pageable page);
	
	
	
	@Query("select a  from PhysicianAssistantAccount a left join PhysicianAssistantPhysician pa on a.id=pa.physicianAssistantId "
			+ "left join PhysicianAccount p on pa.physicianId=p.id "
			+ "where (a.assistantName like %:assistant% or a.assistantName is null) and p.physicianName like %:phyName% and (pa.delFlag='N' or pa.delFlag is null)"
			+ "order by  case when a.status='Active' then 1 when a.status='Inactive' then 2 end,  a.id desc ")
	Page<PhysicianAssistantAccount> findByAssistantSearch(@Param("assistant") String assistant, @Param("phyName") String phyName, Pageable page);
	
	

	
	
	//List<PhysicianAssistantAccount> findByPhysicianId(int id);
	
	
	// Summary search with physician group id
	@Query("select  COALESCE(count(a),0)  from PhysicianAssistantAccount a, PhysicianAccount p, PhysicianGroup g,PhysicianAssistantPhysician pa "
			+ "where pa.physicianId=p.id and a.id=pa.physicianAssistantId and p.id=g.physicianId and a.groupId=:groupId and pa.physicianId=:id "
			+ "and a.status=:status   group by a.id order by a.id desc")
	int findByPhysicianAndGroupAndStatus(@Param("id") int id, @Param("groupId") int groupId, @Param("status") String status);
	
	@Query("select  COALESCE(count(a),0)  from PhysicianAssistantAccount a, PhysicianAccount p, PhysicianGroup g,PhysicianAssistantPhysician pa "
			+ "where pa.physicianId=p.id and a.id=pa.physicianAssistantId and p.id=g.physicianId and a.groupId=:groupId and pa.physicianId=:id "
			+ "group by a.id order by  case when a.status='Active' then 1 when a.status='Inactive' then 2 end,  a.id desc")
	int findByPhysicianAndGroupAndStatus(@Param("id") int id, @Param("groupId") int groupId);
	
	@Query("select  COALESCE(count(a),0)  from PhysicianAssistantAccount a, PhysicianAccount p, PhysicianGroup g,PhysicianAssistantPhysician pa "
			+ "where pa.physicianId=p.id and a.id=pa.physicianAssistantId and p.id=g.physicianId and a.groupId=:groupId and pa.physicianId=:id "
			+ "and a.assistantName like %:assistant% and a.status=:status   group by a.id order by a.id desc")
	int findByAssistantNameAndGroupAndStatus(@Param("id") int id, @Param("groupId") int groupId, @Param("assistant") String assistant, @Param("status") String status);


	@Query("select  COALESCE(count(a),0)  from PhysicianAssistantAccount a, PhysicianAccount p, PhysicianGroup g,PhysicianAssistantPhysician pa "
			+ "where pa.physicianId=p.id and a.id=pa.physicianAssistantId and p.id=g.physicianId and a.groupId=:groupId and pa.physicianId=:id "
			+ "and a.assistantName like %:assistant%  group by a.id order by  case when a.status='Active' then 1 when a.status='Inactive' then 2 end, a.id desc")
	int findByAssistantNameAndGroupAndStatus(@Param("id") int id, @Param("groupId") int groupId, @Param("assistant") String assistant);

	
	
	@Query("select a from PhysicianAssistantAccount a, PhysicianAccount p, PhysicianGroup g,PhysicianAssistantPhysician pa "
			+ "where pa.physicianId=p.id and a.id=pa.physicianAssistantId and p.id=g.physicianId and a.groupId=:groupId and pa.physicianId=:id "
			+ "and a.status=:status   group by a.id order by a.id desc")
	Page<PhysicianAssistantAccount> findByPhysicianAndGroupAndStatus(@Param("id") int id, @Param("groupId") int groupId, @Param("status") String status, Pageable page);
	
	
	
	@Query("select a from PhysicianAssistantAccount a, PhysicianAccount p, PhysicianGroup g,PhysicianAssistantPhysician pa "
			+ "where pa.physicianId=p.id and a.id=pa.physicianAssistantId and p.id=g.physicianId and a.groupId=:groupId and pa.physicianId=:id "
			+ "group by a.id order by  case when a.status='Active' then 1 when a.status='Inactive' then 2 end, a.id desc")
	Page<PhysicianAssistantAccount> findByPhysicianAndGroupAndStatus(@Param("id") int id, @Param("groupId") int groupId, Pageable page);

	
	
	
	@Query("select a from PhysicianAssistantAccount a, PhysicianAccount p, PhysicianGroup g,PhysicianAssistantPhysician pa "
			+ "where pa.physicianId=p.id and a.id=pa.physicianAssistantId and p.id=g.physicianId and a.groupId=:groupId and pa.physicianId=:id "
			+ "and a.assistantName like %:assistant% and a.status=:status   group by a.id order by a.id desc")
	Page<PhysicianAssistantAccount> findByAssistantNameGroupAndAndStatus(@Param("id") int id, @Param("groupId") int groupId, @Param("assistant") String assistant, @Param("status") String status, Pageable page);
	
	
	@Query("select a from PhysicianAssistantAccount a, PhysicianAccount p, PhysicianGroup g,PhysicianAssistantPhysician pa "
			+ "where pa.physicianId=p.id and a.id=pa.physicianAssistantId and p.id=g.physicianId and a.groupId=:groupId and pa.physicianId=:id "
			+ "and a.assistantName like %:assistant%  group by a.id order by  case when a.status='Active' then 1 when a.status='Inactive' then 2 end, a.id desc")
	Page<PhysicianAssistantAccount> findByAssistantNameGroupAndAndStatus(@Param("id") int id, @Param("groupId") int groupId, 
			@Param("assistant") String assistant, Pageable page);

	
	
	
	@Query("select  COALESCE(count(a),0)  from PhysicianAssistantAccount a, PhysicianAccount p, PhysicianGroup g,PhysicianAssistantPhysician pa "
			+ "where p.id=g.physicianId and a.groupId=:groupId and pa.physicianId=p.id and (pa.delFlag='N' or pa.delFlag is null)and a.status=:status group by a.id ")
	int findByGroupAndStatus(@Param("groupId") int groupId, @Param("status") String status);

	
	@Query("select  COALESCE(count(a),0)  from PhysicianAssistantAccount a where a.groupId=:groupId ")
	int findByGroupAndStatus(@Param("groupId") int groupId);
	
	
	@Query("select  COALESCE(count(a),0)  from PhysicianAssistantAccount a left join PhysicianAssistantPhysician pa on a.id=pa.physicianAssistantId "
			+ "left join PhysicianAccount p on pa.physicianId=p.id left join PhysicianGroup g on  p.id=g.physicianId "
			+ "where (a.assistantName like %:assistant% or a.assistantName is null) and p.physicianName like %:phyName% "
			+ "and a.status=:status and (pa.delFlag='N' or pa.delFlag is null)and a.groupId=:groupId  group by a.id ")
	int findByStatusAndSearch(@Param("assistant") String assistant, @Param("phyName") String phyName, @Param("groupId") int groupId, @Param("status") String status);
	
	@Query("select a from PhysicianAssistantAccount a left join PhysicianAssistantPhysician pa on a.id=pa.physicianAssistantId "
			+ "left join PhysicianAccount p on pa.physicianId=p.id left join PhysicianGroup g on  p.id=g.physicianId "
			+ "where (a.assistantName like %:assistant% or a.assistantName is null) and (pa.delFlag='N' or pa.delFlag is null) "
			+ "and a.status=:status and a.groupId=:groupId  group by a.id ")
	List<PhysicianAssistantAccount> findByStatusAndSearch(@Param("assistant") String assistant,  @Param("groupId") int groupId, @Param("status") String status);
	
	
	
	
	
	@Query("select  COALESCE(count(a),0)  from PhysicianAssistantAccount a left join PhysicianAssistantPhysician pa on a.id=pa.physicianAssistantId "
			+ "left join PhysicianAccount p on pa.physicianId=p.id left join PhysicianGroup g on  p.id=g.physicianId "
			+ "where (a.assistantName like %:assistant% or a.assistantName is null) and (pa.delFlag='N' or pa.delFlag is null) "
			+ " and a.groupId=:groupId  group by a.id ")
	int findBySearchAssistant(@Param("assistant") String assistant,  @Param("groupId") int groupId);
	

	@Query("select  COALESCE(count(a),0)  from PhysicianAssistantAccount a left join PhysicianAssistantPhysician pa on a.id=pa.physicianAssistantId "
			+ "left join PhysicianAccount p on pa.physicianId=p.id left join PhysicianGroup g on  p.id=g.physicianId "
			+ "where (a.assistantName like %:assistant% or a.assistantName is null) and p.physicianName like %:phyName% "
			+ "and (pa.delFlag='N' or pa.delFlag is null)and a.groupId=:groupId  group by a.id ")
	int findBySearchAssistant(@Param("assistant") String assistant, @Param("phyName") String phyName, @Param("groupId") int groupId);
	

	
	
	
	/*@Query("select  COALESCE(count(a),0)  from PhysicianAssistantAccount a, PhysicianAccount p, PhysicianGroup g,PhysicianAssistantPhysician pa "
			+ "where p.id=g.physicianId and a.groupId=:groupId and pa.physicianId=p.id and a.assistantName like %:assistant% and "
			+ "p.physicianName like %:phyName% and a.status=:status   group by a.id")
	int findByStatusAndGroupAndSearch(@Param("assistant") String assistant, @Param("phyName") String phyName, @Param("groupId") int groupId,  @Param("status") String status);*/
			
	@Query("select  COALESCE(count(a),0)  from PhysicianAssistantAccount a left join PhysicianAssistantPhysician pa on a.id=pa.physicianAssistantId "
			+ "left join PhysicianAccount p on pa.physicianId=p.id left join PhysicianGroup g on g.physicianId=p.id "
			+ "where a.groupId=:groupId and a.assistantName like %:assistant% and p.physicianName like %:phyName% "
			+ "and a.status=:status group by a.id")
	int findByStatusAndGroupAndSearch(@Param("assistant") String assistant, @Param("phyName") String phyName, @Param("groupId") int groupId, 
			@Param("status") String status);
	
	
	

	
	@Query("select a from PhysicianAssistantAccount a, PhysicianAccount p, PhysicianGroup g,PhysicianAssistantPhysician pa "
			+ "where p.id=g.physicianId and a.groupId=:groupId and (pa.delFlag='N' or pa.delFlag is null)and pa.physicianId=p.id and a.status=:status  "
			+ "group by a.id order by a.id desc")
	Page<PhysicianAssistantAccount> findByGroupAndStatus(@Param("groupId") int groupId, @Param("status") String status, Pageable page);

	
	@Query("select a from PhysicianAssistantAccount a where a.groupId=:groupId "
			+ "order by  case when a.status='Active' then 1 when a.status='Inactive' then 2 end, a.id desc ")
	Page<PhysicianAssistantAccount> findByGroupAndStatus(@Param("groupId") int groupId,  Pageable page);


	
	
	@Query("select a  from PhysicianAssistantAccount a left join PhysicianAssistantPhysician pa on a.id=pa.physicianAssistantId "
			+ "left join PhysicianAccount p on pa.physicianId=p.id left join PhysicianGroup g on p.id=g.physicianId "
			+ "where (a.assistantName like %:assistant% or a.assistantName is null) and p.physicianName like %:phyName% and (pa.delFlag='N' or pa.delFlag is null)"
			+ "and a.status=:status and a.groupId=:groupId  group by a.id order by a.id desc ")
	Page<PhysicianAssistantAccount> findByStatusAndSearch(@Param("assistant") String assistant, @Param("phyName") String phyName,  
			@Param("groupId") int groupId, @Param("status") String status, Pageable page);
	
	
	
	@Query("select a  from PhysicianAssistantAccount a left join PhysicianAssistantPhysician pa on a.id=pa.physicianAssistantId "
			+ "left join PhysicianAccount p on pa.physicianId=p.id left join PhysicianGroup g on p.id=g.physicianId "
			+ "where (a.assistantName like %:assistant% or a.assistantName is null) and p.physicianName like %:phyName% and (pa.delFlag='N' or pa.delFlag is null)"
			+ "and a.groupId=:groupId  group by a.id order by  case when a.status='Active' then 1 when a.status='Inactive' then 2 end, a.id desc ")
	Page<PhysicianAssistantAccount> findByAssistantSearch(@Param("assistant") String assistant, @Param("phyName") String phyName,  
			@Param("groupId") int groupId, Pageable page);
	
	

	
	
	
	@Query("select a  from PhysicianAssistantAccount a left join PhysicianAssistantPhysician pa on a.id=pa.physicianAssistantId "
			+ "left join PhysicianAccount p on pa.physicianId=p.id left join PhysicianGroup g on p.id=g.physicianId "
			+ "where (a.assistantName like %:assistant% or a.assistantName is null) and (pa.delFlag='N' or pa.delFlag is null)and a.status=:status "
			+ "and a.groupId=:groupId  group by a.id order by a.id desc ")
	Page<PhysicianAssistantAccount> findByStatusAndSearch(@Param("assistant") String assistant,  
			@Param("groupId") int groupId, @Param("status") String status, Pageable page);
	
	
	
	
	@Query("select a  from PhysicianAssistantAccount a left join PhysicianAssistantPhysician pa on a.id=pa.physicianAssistantId "
			+ "left join PhysicianAccount p on pa.physicianId=p.id left join PhysicianGroup g on p.id=g.physicianId "
			+ "where (a.assistantName like %:assistant% or a.assistantName is null) and (pa.delFlag='N' or pa.delFlag is null)"
			+ "and a.groupId=:groupId  group by a.id order by  case when a.status='Active' then 1 when a.status='Inactive' then 2 end, a.id desc ")
	Page<PhysicianAssistantAccount> findByAssistantSearch(@Param("assistant") String assistant,  
			@Param("groupId") int groupId, Pageable page);

	
	
	/*@Query("select a from PhysicianAssistantAccount a, PhysicianAccount p, PhysicianGroup g,PhysicianAssistantPhysician pa where p.id=g.physicianId and a.groupId=:groupId and pa.physicianId=p.id and a.assistantName like %:assistant% and p.physicianName like %:phyName% and a.status=:status   group by a.id order by a.id desc")
	Page<PhysicianAssistantAccount> findByStatusAndGroupAndSearch(@Param("assistant") String assistant, @Param("phyName") String phyName, @Param("groupId") int groupId,  @Param("status") String status, Pageable page); */
	
	
	@Query("select a from PhysicianAssistantAccount a left join PhysicianAssistantPhysician pa on a.id=pa.physicianAssistantId "
			+ "left join PhysicianAccount p on pa.physicianId=p.id left join PhysicianGroup g on g.physicianId=p.id "
			+ "where a.groupId=:groupId and a.assistantName like %:assistant% and p.physicianName like %:phyName% "
			+ "and a.status=:status group by a.id order by a.id desc")
	Page<PhysicianAssistantAccount> findByStatusAndGroupAndSearch(@Param("assistant") String assistant, @Param("phyName") String phyName, 
			@Param("groupId") int groupId,  @Param("status") String status, Pageable page);
	
	
	
	
	

	
	// Autocomplete to get list
	@Query("select c from PhysicianAssistantAccount c where assistantName like %:assistantName% and (status=:status or id=:id)")
	List<PhysicianAssistantAccount> getAutoCompletePhysicianAssistantList( @Param("assistantName") String assistantName, @Param("status") String status,@Param("id") int id, Pageable pageable);

	// Autocomplete to get list
	@Query("select c from PhysicianAssistantAccount c where assistantName like %:assistantName%" )
	List<PhysicianAssistantAccount> getAllAutoCompletePhysicianAssistantList( @Param("assistantName") String assistantName, Pageable pageable);
	
	// Autocomplete to get list
	@Query("select c from PhysicianAssistantAccount c where assistantName like %:assistantName% and (status=:status or id=:id) and groupId=:groupId")
	List<PhysicianAssistantAccount> getAutoCompleteGroupWisePhysicianAssistantList( @Param("assistantName") String assistantName, @Param("status") String status,@Param("id") int id,@Param("groupId") int groupId, Pageable pageable);

	
	// Autocomplete to get list
	@Query("select c from PhysicianAssistantAccount c,PhysicianAssistantPhysician pa where c.id=pa.physicianAssistantId and pa.physicianId=:physicianId and c.assistantName like %:assistantName% and  (c.status=:status or c.id=:id)")
	List<PhysicianAssistantAccount> getAutoCompletePhysicianWisePhysicianAssistantList( @Param("assistantName") String assistantName, @Param("status") String status,@Param("id") int id,@Param("physicianId") int physicianId, Pageable pageable);
	
	
	// Autocomplete to get list
	@Query("select c from PhysicianAssistantAccount c where assistantName like %:assistantName% and groupId=:groupId" )
	List<PhysicianAssistantAccount> getAllAutoCompleteGroupWisePhysicianAssistantList( @Param("assistantName") String assistantName,@Param("groupId") int groupId, Pageable pageable);
	
	
	// Autocomplete to get list
	@Query("select c from PhysicianAssistantAccount c,PhysicianAssistantPhysician pa where c.id=pa.physicianAssistantId and pa.physicianId=:physicianId and c.assistantName like %:assistantName%" )
	List<PhysicianAssistantAccount> getAllAutoCompletePhysicianWisePhysicianAssistantList( @Param("assistantName") String assistantName,@Param("physicianId") int physicianId, Pageable pageable);

	@Query("select pa from PhysicianClinic c left join PhysicianAccount p on c.physicianId=p.id join PhysicianAssistantPhysician a "
			+ "on a.physicianId=p.id join PhysicianAssistantAccount pa on pa.id=a.physicianAssistantId "
			+ "where c.clinicId=:clinicId  and pa.assistantName like %:assistantName% and c.delFlag='N' and a.delFlag='N' ")
	List<PhysicianAssistantAccount> getAssistantByClinicId(@Param("clinicId") int clinicId,@Param("assistantName") String assistantName);
	
	
	@Query("select pa.id, pa.assistantName, p.physicianName, pa.email, pa.phone from PhysicianClinic c left join "
			+ "PhysicianAccount p on c.physicianId=p.id join PhysicianAssistantPhysician a "
			+ "on a.physicianId=p.id join PhysicianAssistantAccount pa on pa.id=a.physicianAssistantId "
			+ "where c.clinicId=:clinicId  and pa.assistantName like %:assistantName% and c.delFlag='N' and a.delFlag='N' ")
	Page<Object[]> getAssistantByClinicId(@Param("clinicId") int clinicId,@Param("assistantName") String assistantName, Pageable page);
	
	

	
}
