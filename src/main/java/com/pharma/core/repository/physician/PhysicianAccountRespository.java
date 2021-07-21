package com.pharma.core.repository.physician;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pharma.core.model.physician.PhysicianAccount;
/**
 * This class is a Java interface that defines a data access contract
 *
 */
@Repository("physicianAccount")
public interface PhysicianAccountRespository extends JpaRepository<PhysicianAccount, Integer> {

	PhysicianAccount findByPhysicianName(String name);
	
	@Query("select p from PhysicianAccount p where p.status in ('Approved', 'New Modifications', 'Profile Completed') ")
	List<PhysicianAccount> getPhysicianListForGroupChange();
	
	@Query("select p from PhysicianAccount p left join PhysicianGroup g on p.id=g.physicianId where g.groupId=:groupId and "
			+ "p.status in ('Approved', 'New Modifications', 'Profile Completed') ")
	List<PhysicianAccount> getPhysicianListForGroupChange(@Param("groupId") int groupId);
	
	PhysicianAccount findById(int id);
	
	@Query("select p from PhysicianAccount p left join PhysicianGroup g on p.id=g.physicianId "
			+ "where g.groupId=:groupId and g.status='Active' and p.physicianName like %:physicianName%  group by p.id ")
	List<PhysicianAccount> getPhysicianListByActiveGroupId(@Param("groupId") int groupId, @Param("physicianName") String physicianName);
	
	@Query("select p from PhysicianAccount p left join PhysicianGroup g on p.id=g.physicianId "
			+ "where g.groupId=:groupId and g.status='Active' and p.physicianName like %:physicianName%  group by p.id ")
	Page<PhysicianAccount> getPhysicianListByActiveGroupId(@Param("groupId") int groupId, @Param("physicianName") String physicianName, Pageable page);
	


	
	@Query("select p from PhysicianAccount p left join PhysicianGroup g on p.id=g.physicianId "
			+ "where g.groupId=:groupId  and p.physicianName like %:physicianName%  group by p.id ")
	List<PhysicianAccount> getPhysicianListByGroupId(@Param("groupId") int groupId, @Param("physicianName") String physicianName);
	
	@Query("select p from PhysicianAccount p left join PhysicianGroup g on p.id=g.physicianId "
			+ "where g.groupId=:groupId and p.physicianName like %:physicianName%  group by p.id ")
	Page<PhysicianAccount> getPhysicianListByGroupId(@Param("groupId") int groupId, @Param("physicianName") String physicianName, Pageable page);
	

	
	
	
	
	
	@Query("select p from PhysicianAccount p left join PhysicianGroup g on p.id=g.physicianId "
			+ "where g.groupId=:groupId and g.status='Active' and p.physicianName like %:physicianName% and p.status=:status group by p.id ")
	List<PhysicianAccount> getPhysicianListByActiveGroupId(@Param("groupId") int groupId, @Param("physicianName") String physicianName, 
			@Param("status") String status);
	
	@Query("select p from PhysicianAccount p left join PhysicianGroup g on p.id=g.physicianId "
			+ "where g.groupId=:groupId and g.status='Active' and p.physicianName like %:physicianName% and p.status=:status group by p.id ")
	Page<PhysicianAccount> getPhysicianListByActiveGroupId(@Param("groupId") int groupId, @Param("physicianName") String physicianName, 
			@Param("status") String status, Pageable page);

	
	
	
	@Query("select p from PhysicianAccount p left join PhysicianGroup g on p.id=g.physicianId "
			+ "where g.groupId=:groupId and p.physicianName like %:physicianName% and p.status=:status group by p.id ")
	List<PhysicianAccount> getPhysicianListByGroupId(@Param("groupId") int groupId, @Param("physicianName") String physicianName, 
			@Param("status") String status);
	
	@Query("select p from PhysicianAccount p left join PhysicianGroup g on p.id=g.physicianId "
			+ "where g.groupId=:groupId and p.physicianName like %:physicianName% and p.status=:status group by p.id ")
	Page<PhysicianAccount> getPhysicianListByGroupId(@Param("groupId") int groupId, @Param("physicianName") String physicianName, 
			@Param("status") String status, Pageable page);
	
	
	
	@Query("select p from PhysicianAccount p left join PhysicianGroup g on p.id=g.physicianId  where g.groupId=:groupId")
	List<PhysicianAccount> getPhysicianByGroup(@Param("groupId") int groupId);
	
	@Query("select p from PhysicianAccount p left join PhysicianClinic c on p.id=c.physicianId where c.clinicId=:clinicId and c.delFlag='N' ")
	List<PhysicianAccount> getPhysicianListByClinicId(@Param("clinicId") int clinicId);

	@Query("select p from PhysicianAccount p left join PhysicianClinic c on p.id=c.physicianId where c.clinicId=:clinicId and c.delFlag='N' ")
	Page<PhysicianAccount> getPhysicianListByClinicId(@Param("clinicId") int clinicId, Pageable page);
	
	@Query("select a from PhysicianAccount a where (a.email like :username) and a.password = :password")
	PhysicianAccount findByUsernameAndPassword(@Param("username") String name,@Param("password") String password);

	@Query("select a from PhysicianAccount a where (a.email like :username)")
	PhysicianAccount findByUsername(@Param("username") String name);

	List<PhysicianAccount> findByEmail(String email);
	
	List<PhysicianAccount> findByEmailAndIdNot(String email, int id);
	
	@Query("select  COALESCE(count(c),0)  from PhysicianAccount c where c.physicianName like %:name% and status=:status")
	int findByPhysicianNameAndStatus(@Param("name") String name, @Param("status") String status);

	@Query("Select c from PhysicianAccount c where c.physicianName like %:name% and status=:status "
			+ "order by case when c.status='Profile Completed' then 1 when c.status='New' then 2 when c.status='New Modifications' then 3 when c.status='Approved' then 4 when c.status='Denied' then 5 end ")
	Page<PhysicianAccount> findByPhysicianNameAndStatus(@Param("name") String name, @Param("status") String status, Pageable page);

	@Query("select  COALESCE(count(c),0)  from PhysicianAccount c where status=:status")
	int findByStatus(@Param("status") String status);


	
	@Query("select  COALESCE(count(p),0)  from PhysicianAccount p left join PhysicianProfileInfo c on c.physicianId=p.id "
			+ "where p.physicianName like %:physicianName% and c.dea like %:dea% and p.status=:status")
	int countByClinicAndStatus(@Param("physicianName") String physicianName, @Param("dea") String dea, @Param("status") String status);

	@Query("select  COALESCE(count(p),0)  from PhysicianAccount p where p.physicianName like %:physicianName% and p.status=:status")
	int countByStatus(@Param("physicianName") String physicianName, @Param("status") String status);
	
	@Query("select  COALESCE(count(p),0)  from PhysicianAccount p left join PhysicianProfileInfo c on c.physicianId=p.id "
			+ "where p.physicianName like %:physicianName% and (c.dea like %:dea%) ")
	int countByClinic(@Param("physicianName") String physicianName, @Param("dea") String dea);
	
	@Query("select  COALESCE(count(p),0)  from PhysicianAccount p where p.physicianName like %:physicianName% ")
	int countBySearch(@Param("physicianName") String physicianName);

	
	@Query("select  p  from PhysicianAccount p left join PhysicianProfileInfo c on c.physicianId=p.id where p.physicianName like %:physicianName% and "
			+ "c.dea like %:dea% and p.status=:status "
			+ "order by case when p.status='Profile Completed' then 1 when p.status='New' then 2 when p.status='New Modifications' then 3 when p.status='Approved' then 4 when p.status='Denied' then 5 end, p.id desc ")
	Page<PhysicianAccount> findByClinicAndStatus(@Param("physicianName") String physicianName, @Param("dea") String dea, @Param("status") String status, Pageable page);

	@Query("select  p  from PhysicianAccount p where p.physicianName like %:physicianName% and p.status=:status "
			+ "order by case when p.status='Profile Completed' then 1 when p.status='New' then 2 when p.status='New Modifications' then 3 when p.status='Approved' then 4 when p.status='Denied' then 5 end, p.id desc ")
	Page<PhysicianAccount> findByStatus(@Param("physicianName") String physicianName, @Param("status") String status, Pageable page);
	
	@Query("select  p  from PhysicianAccount p left join PhysicianProfileInfo c on c.physicianId=p.id "
			+ "where p.physicianName like %:physicianName% and (c.dea like %:dea%)  "
			+ "order by case when p.status='Profile Completed' then 1 when p.status='New' then 2 when p.status='New Modifications' then 3 when p.status='Approved' then 4 when p.status='Denied' then 5 end, c.id desc  ")
	Page<PhysicianAccount> findByClinic(@Param("physicianName") String physicianName, @Param("dea") String dea, Pageable page);
	
	@Query("select  p  from PhysicianAccount p where p.physicianName like %:physicianName% "
			+ "order by case when p.status='Profile Completed' then 1 when p.status='New' then 2 when p.status='New Modifications' then 3 when p.status='Approved' then 4 when p.status='Denied' then 5 end, p.id desc ")
	Page<PhysicianAccount>  findBySearch(@Param("physicianName") String physicianName, Pageable page);
	
	
	
	
	
	
	
	
	@Query("select  COALESCE(count(p),0)  from PhysicianAccount p left join Clinic c on c.id=p.clinicId where p.physicianName like %:physicianName% and "
			+ "(c.clinicName like %:clinicName% or c.clinicName is null) and p.status=:status")
	int countByStatusWithSearch(@Param("physicianName") String physicianName, @Param("clinicName") String clinicName, @Param("status") String status);

	@Query("select  COALESCE(count(p),0)  from PhysicianAccount p left join Clinic c on c.id=p.clinicId where p.physicianName like %:physicianName% and "
			+ "(c.clinicName like %:clinicName% or c.clinicName is null) ")
	int countByNotDeniedWithSearch(@Param("physicianName") String physicianName, @Param("clinicName") String clinicName);
	
	
	
	@Query("select  COALESCE(count(p),0)  from PhysicianGroup g, PhysicianAccount p left join PhysicianProfileInfo c on c.physicianId=p.id "
			+ "where p.id=g.physicianId and g.groupId=:groupId and (c.dea like %:dea% or c.dea is null) and p.physicianName like %:physicianName%  "
			+ "and p.status=:status")
	int countByGroupStatusWithSearch(@Param("groupId") int groupId, @Param("dea") String dea, @Param("physicianName") String physicianName, @Param("status") String status);

	@Query("select  COALESCE(count(p),0)  from PhysicianGroup g, PhysicianAccount p left join PhysicianProfileInfo c on c.physicianId=p.id "
			+ "where p.id=g.physicianId and g.groupId=:groupId and (c.dea like %:dea% or c.dea is null) and p.physicianName like %:physicianName% ")
	int countByGroupNotDeniedWithSearch(@Param("groupId") int groupId, @Param("dea") String dea, @Param("physicianName") String physicianName);
	
	
	@Query("select p from PhysicianAccount p left join Clinic c on c.id=p.clinicId where p.physicianName like %:physicianName%  and "
			+ "(c.clinicName like %:clinicName% or c.clinicName is null) and p.status=:status "
			+ "order by case when p.status='Profile Completed' then 1 when p.status='New' then 2 when p.status='New Modifications' then 3 when p.status='Approved' then 4 when p.status='Denied' then 5 end, p.id desc ")
	Page<PhysicianAccount> findByStatusWithSearch(@Param("physicianName") String physicianName, @Param("clinicName") String clinicName, @Param("status") String status, Pageable page);
	
	@Query("select p from PhysicianAccount p left join Clinic c on c.id=p.clinicId where p.physicianName like %:physicianName%  and "
			+ "(c.clinicName like %:clinicName% or c.clinicName is null) "
			+ "order by case when p.status='Profile Completed' then 1 when p.status='New' then 2 when p.status='New Modifications' then 3 when p.status='Approved' then 4 when p.status='Denied' then 5 end, p.id desc ")	
	Page<PhysicianAccount> findByNotDeniedWithSearch(@Param("physicianName") String physicianName, @Param("clinicName") String clinicName, Pageable page);

	@Query("select p from PhysicianGroup g, PhysicianAccount p left join PhysicianProfileInfo c on c.physicianId=p.id where p.id=g.physicianId and "
			+ "g.groupId=:groupId and (c.dea like %:dea% or c.dea is null) and p.physicianName like %:physicianName%  and p.status=:status "
			+ "order by case when p.status='Profile Completed' then 1 when p.status='New' then 2 when p.status='New Modifications' then 3 when p.status='Approved' then 4 when p.status='Denied' then 5 end, p.id desc")
	Page<PhysicianAccount> findByGroupStatusWithSearch(@Param("groupId") int groupId, @Param("dea") String dea, @Param("physicianName") String physicianName, @Param("status") String status, Pageable page);

	@Query("select p from PhysicianGroup g, PhysicianAccount p left join PhysicianProfileInfo c on c.physicianId=p.id where p.id=g.physicianId and "
			+ "(c.dea like %:dea% or c.dea is null) and g.groupId=:groupId and p.physicianName like %:physicianName%  "
			+ "order by case when p.status='Profile Completed' then 1 when p.status='New' then 2 when p.status='New Modifications' then 3 when p.status='Approved' then 4 when p.status='Denied' then 5 end, p.id desc")
	Page<PhysicianAccount> findByGroupNotDeniedWithSearch(@Param("groupId") int groupId, @Param("dea") String dea, @Param("physicianName") String physicianName, Pageable page);

	
	
	
	
	
	
	

	@Query("select  COALESCE(count(p),0)  from PhysicianAccount p where p.physicianName like %:physicianName%  and p.status=:status")
	int findByStatusWithSearch(@Param("physicianName") String physicianName, @Param("status") String status);	
	
	@Query("select  COALESCE(count(p),0)  from PhysicianAccount p where p.physicianName like %:physicianName% and p.clinicId=:clinicId ")
	int findByNotDeniedWithSearch(@Param("physicianName") String physicianName, @Param("clinicId") int clinicId);

	@Query("select  COALESCE(count(p),0)  from PhysicianAccount p where p.physicianName like %:physicianName% ")
	int findByNotDeniedWithSearch(@Param("physicianName") String physicianName);

	@Query("select p from PhysicianAccount p where p.physicianName like %:physicianName% and p.clinicId=:clinicId "
			+ "order by case when p.status='Profile Completed' then 1 when p.status='New' then 2 when p.status='New Modifications' then 3 when p.status='Approved' then 4 when p.status='Denied' then 5 end, p.id desc")
	Page<PhysicianAccount> findByNotDeniedWithSearch(@Param("physicianName") String physicianName,  @Param("clinicId") int clinicId, Pageable page);
	
	@Query("select p from PhysicianAccount p where p.physicianName like %:physicianName% "
			+ "order by case when p.status='Profile Completed' then 1 when p.status='New' then 2 when p.status='New Modifications' then 3 when p.status='Approved' then 4 when p.status='Denied' then 5 end, p.id desc")
	Page<PhysicianAccount> findByNotDeniedWithSearch(@Param("physicianName") String physicianName, Pageable page);
	
	@Query("select p from PhysicianAccount p where status=:status "
			+ "order by case when p.status='Profile Completed' then 1 when p.status='New' then 2 when p.status='New Modifications' then 3 when p.status='Approved' then 4 when p.status='Denied' then 5 end")
	Page<PhysicianAccount> findByStatus(@Param("status") String status, Pageable page);
	
	//@Query("select p from PhysicianAccount p, Clinic c where c.id=p.clinicId and p.physicianName like %:physicianName% and c.clinicName like %:clinicName% and p.status=:status")
	//Page<PhysicianAccount> findByStatusWithSearch(@Param("physicianName") String physicianName, @Param("clinicName") String clinicName, @Param("status") String status, Pageable page);

	@Query("select p from PhysicianAccount p where p.physicianName like %:physicianName% and p.clinicId=:clinicId and p.status=:status "
			+ "order by case when p.status='Profile Completed' then 1 when p.status='New' then 2 when p.status='New Modifications' then 3 when p.status='Approved' then 4 when p.status='Denied' then 5 end, p.id desc")
	Page<PhysicianAccount> findByStatusWithSearch(@Param("physicianName") String physicianName, @Param("clinicId") int clinicId, @Param("status") String status, Pageable page);
	
	@Query("select p from PhysicianAccount p where p.physicianName like %:physicianName% and p.status=:status "
			+ "order by case when p.status='Profile Completed' then 1 when p.status='New' then 2 when p.status='New Modifications' then 3 when p.status='Approved' then 4 when p.status='Denied' then 5 end, p.id desc")
	Page<PhysicianAccount> findByStatusWithSearch(@Param("physicianName") String physicianName, @Param("status") String status, Pageable page);

	@Query("select  COALESCE(count(c),0)  from PhysicianAccount c where status!='Denied' ")
	int getAllActiveAndNewRecords();

	@Query("select  COALESCE(count(c),0)  from PhysicianAccount c  ")
	int getAllRecords();
	
	// order by case when status='New' then 1 when status='Approved' then 2 when status='Denied' then 3 end
	@Query("select p from PhysicianAccount p where status!='Denied' order by case when p.status='Profile Completed' then 1 when p.status='New' then 2 when p.status='New Modifications' then 3 when p.status='Approved' then 4 when p.status='Denied' then 5 end ")
	Page<PhysicianAccount> getAllActiveAndNewRecords(Pageable page);
	
	@Query("select p from PhysicianAccount p order by case when p.status='Profile Completed' then 1 when p.status='New' then 2 when p.status='New Modifications' then 3 when p.status='Approved' then 4 when p.status='Denied' then 5 end, p.id desc ")
	Page<PhysicianAccount> getAllRecords(Pageable page);
	
	// Autocomplete to get physician names
	@Query("select p from PhysicianAccount p where p.physicianName like :term% and p.status!=:status order by case when p.status='Profile Completed' then 1 when p.status='New' then 2 when p.status='New Modifications' then 3 when p.status='Approved' then 4 when p.status='Denied' then 5 end")
	List<PhysicianAccount> findByAutoPhysicianNameAndStatus(@Param("term") String term, @Param("status") String status);

	@Query("select p from PhysicianAccount p where status='Approved' order by case when p.status='Profile Completed' then 1 when p.status='New' then 2 when p.status='New Modifications' then 3 when p.status='Approved' then 4 when p.status='Denied' then 5 end")
	List<PhysicianAccount> getAllApprovedPhysicians();
	
	@Query("select p from PhysicianAccount p where status='Approved' or id=:id order by case when p.status='Profile Completed' then 1 when p.status='New' then 2 when p.status='New Modifications' then 3 when p.status='Approved' then 4 when p.status='Denied' then 5 end")
	List<PhysicianAccount> getAllApprovedPhysiciansAndId(@Param("id") int id);
	
	
	@Query("select c from PhysicianAccount c, PhysicianGroup g where c.id=g.physicianId and c.status='Approved' and (g.groupId=:groupId or c.id=:id) "
			+ "order by case when c.status='Profile Completed' then 1 when c.status='New' then 2 when c.status='New Modifications' then 3 when c.status='Approved' then 4 when c.status='Denied' then 5 end")
	List<PhysicianAccount> getAllApprovedPhysiciansByGroupIdAndId(@Param("groupId") int groupId, @Param("id") int id);

	@Query("select c from PhysicianAccount c, PhysicianGroup g where c.id=g.physicianId and g.groupId=:groupId and c.status='Approved' "
			+ "order by case when c.status='Profile Completed' then 1 when c.status='New' then 2 when c.status='New Modifications' then 3 when c.status='Approved' then 4 when c.status='Denied' then 5 end")
	List<PhysicianAccount> getAllApprovedPhysiciansByGroupId(@Param("groupId") int groupId);
	
	// Summary Search With Group queries
	
	@Query("select  COALESCE(count(c),0)  from PhysicianAccount c, PhysicianGroup g where c.id=g.physicianId and g.groupId=:groupId  ")
	int getAllGroupRecords(@Param("groupId") int groupId);

	@Query("select  COALESCE(count(p),0)  from PhysicianAccount p, PhysicianGroup g where p.id=g.physicianId and g.groupId=:groupId and p.clinicId=:clinicId and p.physicianName like %:physicianName%  and p.status=:status")
	int findByGroupStatusWithSearch(@Param("groupId") int groupId, @Param("clinicId") int clinicId, @Param("physicianName") String physicianName, @Param("status") String status);

	@Query("select  COALESCE(count(p),0)  from PhysicianAccount p, PhysicianGroup g where p.id=g.physicianId and g.groupId=:groupId and p.physicianName like %:physicianName%  and p.status=:status")
	int findByGroupStatusWithSearch(@Param("groupId") int groupId, @Param("physicianName") String physicianName, @Param("status") String status);
	
	
	@Query("select  COALESCE(count(p),0)  from PhysicianAccount p, PhysicianGroup g where p.id=g.physicianId and g.groupId=:groupId and p.clinicId=:clinicId and p.physicianName like %:physicianName%  ")
	int findByGroupNotDeniedWithSearch(@Param("groupId") int groupId, @Param("clinicId") int clinicId, @Param("physicianName") String physicianName);
	
	@Query("select  COALESCE(count(p),0)  from PhysicianAccount p, PhysicianGroup g where p.id=g.physicianId and g.groupId=:groupId and p.physicianName like %:physicianName%  ")
	int findByGroupNotDeniedWithSearch(@Param("groupId") int groupId, @Param("physicianName") String physicianName);

	
	
	@Query("select c from PhysicianAccount c, PhysicianGroup g where c.id=g.physicianId and g.groupId=:groupId "
			+ "order by case when c.status='Profile Completed' then 1 when c.status='New' then 2 when c.status='New Modifications' then 3 when c.status='Approved' then 4 when c.status='Denied' then 5 end, c.id desc")
	Page<PhysicianAccount> getAllGroupRecords(@Param("groupId") int groupId, Pageable page);

	
	
	
	@Query("select p from PhysicianAccount p, PhysicianGroup g where p.id=g.physicianId and g.groupId=:groupId and p.clinicId=:clinicId and p.physicianName like %:physicianName%  "
			+ "order by case when p.status='Profile Completed' then 1 when p.status='New' then 2 when p.status='New Modifications' then 3 when p.status='Approved' then 4 when p.status='Denied' then 5 end, p.id desc")
	Page<PhysicianAccount> findByGroupNotDeniedWithSearch(@Param("groupId") int groupId, @Param("clinicId") int clinicId, @Param("physicianName") String physicianName, Pageable page);

//	@Query("select p from PhysicianAccount p, PhysicianGroup g where p.id=g.physicianId and g.groupId=:groupId and p.physicianName like %:physicianName%  order by case when p.status='New' then 1 when p.status='Approved' then 2 when p.status='Denied' then 3 end, p.id desc")
//	Page<PhysicianAccount> findByGroupNotDeniedWithSearch(@Param("groupId") int groupId, @Param("physicianName") String physicianName, Pageable page);
	
	
	
	@Query("select p from PhysicianAccount p where pioneerResponse=-1 and status='Approved' "
			+ "order by case when p.status='Profile Completed' then 1 when p.status='New' then 2 when p.status='New Modifications' then 3 when p.status='Approved' then 4 when p.status='Denied' then 5 end")
	List<PhysicianAccount> fetchAllNotUpdatedPioneerPhysician();
	
	// Autocomplete to get list
	@Query("select p from PhysicianAccount p where physicianName like %:physicianName% and (status=:status or id=:id) and  p.status!='Denied' "
			+ "order by case when p.status='Profile Completed' then 1 when p.status='New' then 2 when p.status='New Modifications' then 3 when p.status='Approved' then 4 when p.status='Denied' then 5 end")
	List<PhysicianAccount> fetchPhysicianNameByAutoComplete( @Param("physicianName") String physicianName, @Param("status") String status,@Param("id") int id, Pageable pageable);

	@Query("select p from PhysicianAccount p where physicianName like %:physicianName% and  status!='Denied' "
			+ "order by case when p.status='Profile Completed' then 1 when p.status='New' then 2 when p.status='New Modifications' then 3 when p.status='Approved' then 4 when p.status='Denied' then 5 end")
	List<PhysicianAccount> fetchNotNewPhysicianNameByAutoComplete( @Param("physicianName") String physicianName, Pageable pageable);

	@Query("select p from PhysicianAccount p where (physicianName like %:physicianName% and  status!='Denied') or p.id=:id  "
			+ "order by case when p.status='Profile Completed' then 1 when p.status='New' then 2 when p.status='New Modifications' then 3 when p.status='Approved' then 4 when p.status='Denied' then 5 end")
	List<PhysicianAccount> fetchNotNewPhysicianNameByAutoComplete( @Param("physicianName") String physicianName, @Param("id") int id, Pageable pageable);


	@Query("select p from PhysicianAccount p where physicianName like %:physicianName% "
			+ "order by case when p.status='Profile Completed' then 1 when p.status='New' then 2 when p.status='New Modifications' then 3 when p.status='Approved' then 4 when p.status='Denied' then 5 end")
	List<PhysicianAccount> fetchAllPhysicianNameByAutoComplete( @Param("physicianName") String physicianName, Pageable pageable);

	@Query("select p from PhysicianAccount p where physicianName like %:physicianName%  or p.id=:id "
			+ "order by case when p.status='Profile Completed' then 1 when p.status='New' then 2 when p.status='New Modifications' then 3 when p.status='Approved' then 4 when p.status='Denied' then 5 end")
	List<PhysicianAccount> fetchAllPhysicianNameByAutoComplete( @Param("physicianName") String physicianName, @Param("id") int id, Pageable pageable);
	

	
	@Query("select p from PhysicianAccount p, PhysicianGroup g where p.id=g.physicianId and g.groupId=:groupId and p.physicianName like %:physicianName%  or p.id=:id "
			+ "order by case when p.status='Profile Completed' then 1 when p.status='New' then 2 when p.status='New Modifications' then 3 when p.status='Approved' then 4 when p.status='Denied' then 5 end")
	List<PhysicianAccount> fetchGroupPhysicianNameByAutoComplete( @Param("physicianName") String physicianName, @Param("id") int id, 
			@Param("groupId") int groupId, Pageable pageable);

	
	@Query("select p from PhysicianAccount p, PhysicianGroup g where p.id=g.physicianId and g.groupId=:groupId and p.physicianName like %:physicianName% "
			+ "order by case when p.status='Profile Completed' then 1 when p.status='New' then 2 when p.status='New Modifications' then 3 when p.status='Approved' then 4 when p.status='Denied' then 5 end")
	List<PhysicianAccount> fetchGroupPhysicianNameByAutoComplete( @Param("physicianName") String physicianName,  
			@Param("groupId") int groupId, Pageable pageable);
	
	
	// Autocomplete to get list
	@Query("select p from PhysicianAccount p where physicianName like %:physicianName% "
			+ "order by case when p.status='Profile Completed' then 1 when p.status='New' then 2 when p.status='New Modifications' then 3 when p.status='Approved' then 4 when p.status='Denied' then 5 end")
	List<PhysicianAccount> fetchPhysicianNameByAutoComplete( @Param("physicianName") String physicianName, Pageable pageable);

	// Autocomplete to get list
	/*@Query("select p from PhysicianAccount p, PhysicianGroup g where p.physicianName like %:physicianName%  and p.id=g.physicianId and g.groupId=:groupId and ( p.status=:status or  p.id=:id) order by case when  p.status='New' then 1 when  p.status='Approved' then 2 when  p.status='Denied' then 3 end")
	List<PhysicianAccount> fetchPhysicianNameByAutoComplete( @Param("physicianName") String physicianName,@Param("groupId") int groupId,  @Param("status") String status,@Param("id") int id, Pageable pageable);*/
	
	// Autocomplete to get list
	@Query("select p from PhysicianAccount p, PhysicianGroup g where (p.physicianName like %:physicianName%  and p.id=g.physicianId and g.groupId=:groupId and  p.status!='Denied') or p.id=:id "
			+ "order by case when p.status='Profile Completed' then 1 when p.status='New' then 2 when p.status='New Modifications' then 3 when p.status='Approved' then 4 when p.status='Denied' then 5 end")
	List<PhysicianAccount> fetchPhysicianNameByAutoComplete( @Param("physicianName") String physicianName,@Param("groupId") int groupId, @Param("id") int id, Pageable pageable);

	// Autocomplete to get list
	@Query("select p from PhysicianAccount p, PhysicianGroup g where  p.physicianName like %:physicianName%  and p.id=g.physicianId and g.groupId=:groupId "
			+ "order by case when p.status='Profile Completed' then 1 when p.status='New' then 2 when p.status='New Modifications' then 3 when p.status='Approved' then 4 when p.status='Denied' then 5 end")
	List<PhysicianAccount> fetchPhysicianNameByAutoComplete( @Param("physicianName") String physicianName,@Param("groupId") int groupId , Pageable pageable);

	// Autocomplete to get list
	@Query("select p from PhysicianAccount p where  p.physicianName like %:physicianName% and p.id=:physicianId and p.status != 'Denied' "
			+ "order by case when p.status='Profile Completed' then 1 when p.status='New' then 2 when p.status='New Modifications' then 3 when p.status='Approved' then 4 when p.status='Denied' then 5 end")
	List<PhysicianAccount> getPhysicianListByPhysicianId( @Param("physicianName") String physicianName, @Param("physicianId") int physicianId , Pageable pageable );

	// Autocomplete to get list
	@Query("select p from PhysicianAccount p where  p.physicianName like %:physicianName% and p.id=:physicianId "
			+ "order by case when p.status='Profile Completed' then 1 when p.status='New' then 2 when p.status='New Modifications' then 3 when p.status='Approved' then 4 when p.status='Denied' then 5 end")
	List<PhysicianAccount> getAllPhysicianListByPhysicianId( @Param("physicianName") String physicianName, @Param("physicianId") int physicianId , Pageable pageable );

		
	// Autocomplete to get list
	@Query("select p from PhysicianAccount p where  p.physicianName like %:physicianName% and p.status != 'Denied' "
			+ "order by case when p.status='Profile Completed' then 1 when p.status='New' then 2 when p.status='New Modifications' then 3 when p.status='Approved' then 4 when p.status='Denied' then 5 end")
	List<PhysicianAccount> getPhysicianListByPhysicianId( @Param("physicianName") String physicianName,  Pageable pageable );

	// Autocomplete to get list
	@Query("select p from PhysicianAccount p where  p.physicianName like %:physicianName% "
			+ "order by case when p.status='Profile Completed' then 1 when p.status='New' then 2 when p.status='New Modifications' then 3 when p.status='Approved' then 4 when p.status='Denied' then 5 end")
	List<PhysicianAccount> getAllPhysicianListByPhysicianId( @Param("physicianName") String physicianName,  Pageable pageable );
	
	// Autocomplete to get list
	@Query("select p from PhysicianAccount p, PhysicianGroup g where (p.physicianName like %:physicianName%  and p.id=g.physicianId and g.groupId=:groupId and  p.status='Approved') or p.id=:id ")
	List<PhysicianAccount> fetchApprovedPhysicianNameByAutoComplete( @Param("physicianName") String physicianName,@Param("groupId") int groupId, @Param("id") int id, Pageable pageable);

	// Autocomplete to get list
	@Query("select p from PhysicianAccount p, PhysicianGroup g where  p.physicianName like %:physicianName%  and p.id=g.physicianId and  p.status='Approved' and g.groupId=:groupId ")
	List<PhysicianAccount> fetchApprovedPhysicianNameByAutoComplete( @Param("physicianName") String physicianName,@Param("groupId") int groupId , Pageable pageable);

	// Autocomplete to get list
	@Query("select p from PhysicianAccount p where  p.physicianName like %:physicianName% and p.id=:physicianId and p.status = 'Approved' ")
	List<PhysicianAccount> getApprovedPhysicianListByPhysicianId( @Param("physicianName") String physicianName, @Param("physicianId") int physicianId , Pageable pageable );

	// Autocomplete to get list
	@Query("select p from PhysicianAccount p where  p.physicianName like %:physicianName% and p.status = 'Approved' ")
	List<PhysicianAccount> getApprovedPhysicianListByPhysicianId( @Param("physicianName") String physicianName,  Pageable pageable );


	@Query("select c from PhysicianAccount c where physicianName like %:physicianName% and status in ('Approved', 'New Modifications', 'Profile Completed') ")
	List<PhysicianAccount> fetchNotNewApprovedPhysicianNameByAutoComplete( @Param("physicianName") String physicianName, Pageable pageable);

	@Query("select c from PhysicianAccount c where (physicianName like %:physicianName% and status in ('Approved', 'New Modifications', 'Profile Completed')) or c.id=:id  ")
	List<PhysicianAccount> fetchNotNewApprovedPhysicianNameByAutoComplete( @Param("physicianName") String physicianName, @Param("id") int id, Pageable pageable);


	
	// pick Pioneer ID from another record for same DEA query
	@Query("select p from PhysicianProfileInfo pro left join PhysicianAccount p on pro.id=p.id where pro.dea=:dea and p.pioneerUid is not null and p.pioneerUid != '' ")
	List<PhysicianAccount> getPioneerIdByDea(@Param("dea") String dea);
	
	@Query("select p from  PhysicianAccount p,PhysicianGroup g where p.id=g.physicianId and g.groupId in (:groupId) and p.status not in ('New','Denied') and (g.status='Active' or p.id=:physicianId)")
	List<PhysicianAccount> getAllGroupWisePhysicianList( @Param("groupId") List<Integer> groupId, @Param("physicianId") int physicianId);
	
	@Query("SELECT p FROM PhysicianAccount p,PhysicianGroup g WHERE p.id=g.physicianId and g.groupId=:groupId and p.status not in ('New','Denied') and p.id NOT IN (SELECT pa.physicianId FROM PhysicianAssistantPhysician pa where  pa.physicianAssistantId=:physicianAssistantId and pa.delFlag='N')")
	List<PhysicianAccount> getAllPhysicianAssistantGroupWisePhysicianListNotSelected( @Param("groupId") int groupId, @Param("physicianAssistantId") int physicianAssistantId);
	
	@Query("select p from PhysicianAccount p,PhysicianGroup g, PhysicianAssistantPhysician pa where p.id=g.physicianId and g.groupId=:groupId and pa.physicianId = p.id and pa.delFlag='N' and pa.physicianAssistantId=:physicianAssistantId and p.status not in ('New','Denied') ")
	List<PhysicianAccount> getAllPhysicianAssistantGroupWisePhysicianListSelected( @Param("groupId") int groupId, @Param("physicianAssistantId") int physicianAssistantId);

	@Query("select p from PhysicianAccount p,PhysicianAssistantAccount g, PhysicianAssistantPhysician pa where pa.physicianId = p.id  and pa.physicianAssistantId=g.id and pa.delFlag='N' and pa.physicianAssistantId=:physicianAssistantId and p.status not in ('New','Denied') ")
	List<PhysicianAccount> getAllPhysicianAssistantPhysicianListSelected( @Param("physicianAssistantId") int physicianAssistantId);

	@Query("SELECT p FROM PhysicianAccount p,PhysicianGroup g WHERE p.id=g.physicianId and g.groupId in (:groupId) and p.status='Approved' and p.id NOT IN (SELECT pa.physicianId FROM PatientPhysicians pa where  pa.patientId=:patientId and pa.delFlag='N')")
	List<PhysicianAccount> getAllPatientGroupWisePhysicianListNotSelected( @Param("groupId") List<Integer> groupId, @Param("patientId") int patientId);
	
	@Query("select p from PhysicianAccount p,PhysicianGroup g, PatientPhysicians pa where p.id=g.physicianId and g.groupId in (:groupId) and pa.physicianId = p.id and pa.delFlag='N' and pa.patientId=:patientId and p.status='Approved'")
	List<PhysicianAccount> getAllPatientGroupWisePhysicianListSelected( @Param("groupId") List<Integer> groupId, @Param("patientId") int patientId);
	
	@Query("SELECT p FROM PhysicianAccount p,PhysicianGroup g WHERE p.id=g.physicianId and g.groupId in (:groupId) and p.status not in ('New','Denied') and p.id NOT IN (SELECT pa.physicianId FROM PatientPhysicians pa where  pa.patientId=:patientId and pa.delFlag='N')")
	List<PhysicianAccount> getAllPatientPhysicianGroupWisePhysicianListNotSelected( @Param("groupId") List<Integer> groupId, @Param("patientId") int patientId);
	
	@Query("select p from PhysicianAccount p,PhysicianGroup g, PatientPhysicians pa where p.id=g.physicianId and g.groupId in (:groupId) and pa.physicianId = p.id and pa.delFlag='N' and pa.patientId=:patientId and p.status not in ('New','Denied')")
	List<PhysicianAccount> getAllPatientPhysicianGroupWisePhysicianListSelected( @Param("groupId") List<Integer> groupId, @Param("patientId") int patientId);


	@Query("select p from PhysicianAccount p,PhysicianGroup g, PatientPhysicians pa where p.id=g.physicianId and g.groupId in (:groupId) and pa.physicianId = p.id and pa.delFlag='N' and pa.patientId=:patientId ")
	List<PhysicianAccount> getPatientGroupWisePhysicianListSelected( @Param("groupId") List<Integer> groupId, @Param("patientId") int patientId);
	

	@Query("select p from PhysicianAccount p,PhysicianGroup g, PhysicianAssistantPhysician pa where p.id=g.physicianId and g.groupId in (:groupId) and pa.physicianId = p.id and pa.delFlag='N' and pa.physicianAssistantId=:assistantId and p.status not in ('New','Denied')")
	List<PhysicianAccount> getAllAssistantGroupWisePhysicianListSelected( @Param("groupId") List<Integer> groupId, @Param("assistantId") int assistantId);

	
	
	

	// **************************** Physician List Report Queries  *************************/ 
	
	/*
	 * select p.physician_name,g.group_name,c.clinic_name,pro.dea,p.phone,p.mobile,p.email,p.city,p.state,p.date_of_registration,p.status 
		from phy_profile pro left join phy_info p on p.physician_id = pro.physician_id left join clinic c on p.physician_id = c.clinic_id 
		left join group_master g on p.physician_id = g.group_id ;  
	 */
	/*
	 select p.physician_id, p.physician_name,g.group_name,c.clinic_name,pro.dea,p.phone,p.mobile,p.email,p.city,p.state,p.date_of_registration,p.status 
				from phy_profile pro left join phy_info p on p.physician_id = pro.physician_id left join 
			phy_clinic pc on pc.physician_id = p.physician_id left join clinic c on pc.clinic_id = c.clinic_id left join group_master g 
			on p.physician_id = g.group_id group by p.physician_id order by p.physician_id;
	 */
	
	@Query("select p.physicianName from PhysicianProfileInfo pro left join PhysicianAccount p on pro.id=p.id left join  PhysicianClinic pc on p.id=pc.physicianId left join Clinic c on c.id=pc.clinicId "
			+ " left join PhysicianGroup gph on gph.physicianId=p.id left join GroupMaster g on g.id=gph.groupId where "
			+ "COALESCE(p.physicianName, '') like %:physicianName%  and "
			+ "COALESCE(g.groupName, '') like %:group%  and COALESCE(pro.dea, '') like %:dea% and p.status =:status group by p.id ")
	List<PhysicianAccount> reportCountByFilterWithStatus(@Param("physicianName") String physician, @Param("group") String group, 
			@Param("dea") String dea, @Param("status") String status);

	@Query("select p.physicianName from PhysicianProfileInfo pro left join PhysicianAccount p on pro.id=p.id left join  PhysicianClinic pc on p.id=pc.physicianId left join Clinic c on c.id=pc.clinicId "
			+ " left join PhysicianGroup gph on gph.physicianId=p.id left join GroupMaster g on g.id=gph.groupId where "
			+ "COALESCE(p.physicianName, '') like %:physicianName%  and "
			+ "COALESCE(g.groupName, '') like %:group%  and COALESCE(pro.dea, '') like %:dea% and p.dateRegistrated>=:fromDate and p.status =:status group by p.id ")
	List<PhysicianAccount> reportCountByFilterWithStatusAndFromDate(@Param("physicianName") String physician, @Param("group") String group, 
			@Param("fromDate") Date fromDate, @Param("dea") String dea, @Param("status") String status);
	
	@Query("select p.physicianName from PhysicianProfileInfo pro left join PhysicianAccount p on pro.id=p.id left join  PhysicianClinic pc on p.id=pc.physicianId left join Clinic c on c.id=pc.clinicId "
			+ " left join PhysicianGroup gph on gph.physicianId=p.id left join GroupMaster g on g.id=gph.groupId where "
			+ "COALESCE(p.physicianName, '') like %:physicianName%  and "
			+ "COALESCE(g.groupName, '') like %:group%  and COALESCE(pro.dea, '') like %:dea% and p.dateRegistrated<=:toDate and p.status =:status group by p.id ")
	List<PhysicianAccount> reportCountByFilterWithStatusAndToDate(@Param("physicianName") String physician, @Param("group") String group, 
			@Param("toDate") Date toDate, @Param("dea") String dea, @Param("status") String status);
	
	@Query("select p.physicianName from PhysicianProfileInfo pro left join PhysicianAccount p on pro.id=p.id left join  PhysicianClinic pc on p.id=pc.physicianId left join Clinic c on c.id=pc.clinicId "
			+ " left join PhysicianGroup gph on gph.physicianId=p.id left join GroupMaster g on g.id=gph.groupId where "
			+ "COALESCE(p.physicianName, '') like %:physicianName%  and "
			+ "COALESCE(g.groupName, '') like %:group%  and COALESCE(pro.dea, '') like %:dea% and p.dateRegistrated>=:fromDate and p.dateRegistrated<=:toDate and p.status =:status group by p.id ")
	List<PhysicianAccount> reportCountByFilterWithStatusAndDate(@Param("physicianName") String physician, @Param("group") String group, 
			@Param("fromDate") Date fromDate, @Param("toDate") Date toDate, @Param("dea") String dea, @Param("status") String status);
	
	
	
	
	
	@Query("select p.physicianName from PhysicianProfileInfo pro left join PhysicianAccount p on pro.id=p.id left join  PhysicianClinic pc on p.id=pc.physicianId left join Clinic c on c.id=pc.clinicId "
			+ " left join PhysicianGroup gph on gph.physicianId=p.id left join GroupMaster g on g.id=gph.groupId where pc.delFlag='N' and "
			+ "COALESCE(p.physicianName, '') like %:physicianName%  and COALESCE(c.clinicName, '') like %:clinic%  and "
			+ "COALESCE(g.groupName, '') like %:group%  and COALESCE(pro.dea, '') like %:dea% and p.status =:status group by p.id ")
	List<PhysicianAccount> reportCountByFilterWithStatus(@Param("physicianName") String physician, @Param("clinic") String clinic, @Param("group") String group, 
			@Param("dea") String dea, @Param("status") String status);
	
	@Query("select p.physicianName from PhysicianProfileInfo pro left join PhysicianAccount p on pro.id=p.id left join  PhysicianClinic pc on p.id=pc.physicianId left join Clinic c on c.id=pc.clinicId "
			+ " left join PhysicianGroup gph on gph.physicianId=p.id left join GroupMaster g on g.id=gph.groupId where pc.delFlag='N' and "
			+ "COALESCE(p.physicianName, '') like %:physicianName%  and COALESCE(c.clinicName, '') like %:clinic%  and "
			+ "COALESCE(g.groupName, '') like %:group%  and COALESCE(pro.dea, '') like %:dea% and p.dateRegistrated>=:fromDate and p.status =:status group by p.id ")
	List<PhysicianAccount> reportCountByFilterWithStatusAndFromDate(@Param("physicianName") String physician, @Param("clinic") String clinic, @Param("group") String group, 
			@Param("fromDate") Date fromDate, @Param("dea") String dea, @Param("status") String status);
	
	@Query("select p.physicianName from PhysicianProfileInfo pro left join PhysicianAccount p on pro.id=p.id left join  PhysicianClinic pc on p.id=pc.physicianId left join Clinic c on c.id=pc.clinicId "
			+ " left join PhysicianGroup gph on gph.physicianId=p.id left join GroupMaster g on g.id=gph.groupId where pc.delFlag='N' and "
			+ "COALESCE(p.physicianName, '') like %:physicianName%  and COALESCE(c.clinicName, '') like %:clinic%  and "
			+ "COALESCE(g.groupName, '') like %:group%  and COALESCE(pro.dea, '') like %:dea% and p.dateRegistrated<=:toDate and p.status =:status group by p.id ")
	List<PhysicianAccount> reportCountByFilterWithStatusAndToDate(@Param("physicianName") String physician, @Param("clinic") String clinic, @Param("group") String group, 
			@Param("toDate") Date toDate, @Param("dea") String dea, @Param("status") String status);
	
	@Query("select p.physicianName from PhysicianProfileInfo pro left join PhysicianAccount p on pro.id=p.id left join  PhysicianClinic pc on p.id=pc.physicianId left join Clinic c on c.id=pc.clinicId "
			+ " left join PhysicianGroup gph on gph.physicianId=p.id left join GroupMaster g on g.id=gph.groupId where pc.delFlag='N' and "
			+ "COALESCE(p.physicianName, '') like %:physicianName%  and COALESCE(c.clinicName, '') like %:clinic%  and "
			+ "COALESCE(g.groupName, '') like %:group%  and COALESCE(pro.dea, '') like %:dea% and p.dateRegistrated>=:fromDate and p.dateRegistrated<=:toDate and p.status =:status group by p.id ")
	List<PhysicianAccount> reportCountByFilterWithDate(@Param("physicianName") String physician, @Param("clinic") String clinic, @Param("group") String group, 
			@Param("fromDate") Date fromDate, @Param("toDate") Date toDate, @Param("dea") String dea, @Param("status") String status);
	
	
	
	
	
	@Query("select p.physicianName from PhysicianProfileInfo pro left join PhysicianAccount p on pro.id=p.id left join  PhysicianClinic pc on p.id=pc.physicianId left join Clinic c on c.id=pc.clinicId "
			+ " left join PhysicianGroup gph on gph.physicianId=p.id left join GroupMaster g on g.id=gph.groupId where "
			+ "COALESCE(p.physicianName, '') like %:physicianName%  and "
			+ "COALESCE(g.groupName, '') like %:group%  and COALESCE(pro.dea, '') like %:dea% group by p.id ")
	List<PhysicianAccount> reportCountByFilter(@Param("physicianName") String physician, @Param("group") String group, 
			@Param("dea") String dea);

	@Query("select p.physicianName from PhysicianProfileInfo pro left join PhysicianAccount p on pro.id=p.id left join  PhysicianClinic pc on p.id=pc.physicianId left join Clinic c on c.id=pc.clinicId "
			+ " left join PhysicianGroup gph on gph.physicianId=p.id left join GroupMaster g on g.id=gph.groupId where "
			+ "COALESCE(p.physicianName, '') like %:physicianName%  and "
			+ "COALESCE(g.groupName, '') like %:group%  and COALESCE(pro.dea, '') like %:dea% and p.dateRegistrated>=:fromDate group by p.id ")
	List<PhysicianAccount> reportCountByFilterWithFromDate(@Param("physicianName") String physician, @Param("group") String group, 
			@Param("fromDate") Date fromDate, @Param("dea") String dea);
	
	@Query("select p.physicianName from PhysicianProfileInfo pro left join PhysicianAccount p on pro.id=p.id left join  PhysicianClinic pc on p.id=pc.physicianId left join Clinic c on c.id=pc.clinicId "
			+ " left join PhysicianGroup gph on gph.physicianId=p.id left join GroupMaster g on g.id=gph.groupId where "
			+ "COALESCE(p.physicianName, '') like %:physicianName%  and "
			+ "COALESCE(g.groupName, '') like %:group%  and COALESCE(pro.dea, '') like %:dea% and p.dateRegistrated<=:toDate group by p.id ")
	List<PhysicianAccount> reportCountByFilterWithToDate(@Param("physicianName") String physician, @Param("group") String group, 
			@Param("toDate") Date toDate, @Param("dea") String dea);
	
	@Query("select p.physicianName from PhysicianProfileInfo pro left join PhysicianAccount p on pro.id=p.id left join  PhysicianClinic pc on p.id=pc.physicianId left join Clinic c on c.id=pc.clinicId "
			+ " left join PhysicianGroup gph on gph.physicianId=p.id left join GroupMaster g on g.id=gph.groupId where "
			+ "COALESCE(p.physicianName, '') like %:physicianName%  and "
			+ "COALESCE(g.groupName, '') like %:group%  and COALESCE(pro.dea, '') like %:dea% and p.dateRegistrated>=:fromDate and p.dateRegistrated<=:toDate group by p.id ")
	List<PhysicianAccount> reportCountByFilterWithDate(@Param("physicianName") String physician, @Param("group") String group, 
			@Param("fromDate") Date fromDate, @Param("toDate") Date toDate, @Param("dea") String dea);
	
	
	
	
	
	@Query("select p.physicianName from PhysicianProfileInfo pro left join PhysicianAccount p on pro.id=p.id left join  PhysicianClinic pc on p.id=pc.physicianId left join Clinic c on c.id=pc.clinicId "
			+ " left join PhysicianGroup gph on gph.physicianId=p.id left join GroupMaster g on g.id=gph.groupId where pc.delFlag='N' and "
			+ "COALESCE(p.physicianName, '') like %:physicianName%  and COALESCE(c.clinicName, '') like %:clinic%  and "
			+ "COALESCE(g.groupName, '') like %:group%  and COALESCE(pro.dea, '') like %:dea% group by p.id ")
	List<PhysicianAccount> reportCountByFilter(@Param("physicianName") String physician, @Param("clinic") String clinic, @Param("group") String group, 
			@Param("dea") String dea);
	
	@Query("select p.physicianName from PhysicianProfileInfo pro left join PhysicianAccount p on pro.id=p.id left join  PhysicianClinic pc on p.id=pc.physicianId left join Clinic c on c.id=pc.clinicId "
			+ " left join PhysicianGroup gph on gph.physicianId=p.id left join GroupMaster g on g.id=gph.groupId where pc.delFlag='N' and "
			+ "COALESCE(p.physicianName, '') like %:physicianName%  and COALESCE(c.clinicName, '') like %:clinic%  and "
			+ "COALESCE(g.groupName, '') like %:group%  and COALESCE(pro.dea, '') like %:dea% and p.dateRegistrated>=:fromDate group by p.id ")
	List<PhysicianAccount> reportCountByFilterWithFromDate(@Param("physicianName") String physician, @Param("clinic") String clinic, @Param("group") String group, 
			@Param("fromDate") Date fromDate, @Param("dea") String dea);
	
	@Query("select p.physicianName from PhysicianProfileInfo pro left join PhysicianAccount p on pro.id=p.id left join  PhysicianClinic pc on p.id=pc.physicianId left join Clinic c on c.id=pc.clinicId "
			+ " left join PhysicianGroup gph on gph.physicianId=p.id left join GroupMaster g on g.id=gph.groupId where pc.delFlag='N' and "
			+ "COALESCE(p.physicianName, '') like %:physicianName%  and COALESCE(c.clinicName, '') like %:clinic%  and "
			+ "COALESCE(g.groupName, '') like %:group%  and COALESCE(pro.dea, '') like %:dea% and p.dateRegistrated<=:toDate group by p.id ")
	List<PhysicianAccount> reportCountByFilterWithToDate(@Param("physicianName") String physician, @Param("clinic") String clinic, @Param("group") String group, 
			@Param("toDate") Date toDate, @Param("dea") String dea);
	
	@Query("select p.physicianName from PhysicianProfileInfo pro left join PhysicianAccount p on pro.id=p.id left join  PhysicianClinic pc on p.id=pc.physicianId left join Clinic c on c.id=pc.clinicId "
			+ " left join PhysicianGroup gph on gph.physicianId=p.id left join GroupMaster g on g.id=gph.groupId where pc.delFlag='N' and "
			+ "COALESCE(p.physicianName, '') like %:physicianName%  and COALESCE(c.clinicName, '') like %:clinic%  and "
			+ "COALESCE(g.groupName, '') like %:group%  and COALESCE(pro.dea, '') like %:dea% and p.dateRegistrated>=:fromDate and p.dateRegistrated<=:toDate  group by p.id ")
	List<PhysicianAccount> reportCountByFilterWithDate(@Param("physicianName") String physician, @Param("clinic") String clinic, @Param("group") String group, 
			@Param("fromDate") Date fromDate, @Param("toDate") Date toDate, @Param("dea") String dea);

	
	
	
	
	@Query("select p.physicianName,g.groupName,c.clinicName,pro.dea,p.phone,p.mobile,p.email,p.city,p.state,p.dateRegistrated,p.status, p.id "
			+ " from PhysicianProfileInfo pro left join PhysicianAccount p on pro.id=p.id left join  PhysicianClinic pc on p.id=pc.physicianId left join Clinic c on c.id=pc.clinicId "
			+ " left join PhysicianGroup gph on gph.physicianId=p.id left join GroupMaster g on g.id=gph.groupId where "
			+ "COALESCE(p.physicianName, '') like %:physicianName% "
			+ "and COALESCE(g.groupName, '') like %:group% "
			+ "and COALESCE(pro.dea, '') like %:dea%  group by p.id "
			+ "order by case when p.status='Profile Completed' then 1 when p.status='New' then 2 when p.status='New Modifications' then 3 when p.status='Approved' then 4 when p.status='Denied' then 5 end, p.id desc ")
	Page<Object[]> reportByFilter(@Param("physicianName") String physician, @Param("group") String group, 
			@Param("dea") String dea, Pageable page);
	
	@Query("select p.physicianName,g.groupName,c.clinicName,pro.dea,p.phone,p.mobile,p.email,p.city,p.state,p.dateRegistrated,p.status, p.id "
			+ " from PhysicianProfileInfo pro left join PhysicianAccount p on pro.id=p.id left join  PhysicianClinic pc on p.id=pc.physicianId left join Clinic c on c.id=pc.clinicId "
			+ " left join PhysicianGroup gph on gph.physicianId=p.id left join GroupMaster g on g.id=gph.groupId where "
			+ "COALESCE(p.physicianName, '') like %:physicianName% "
			+ "and COALESCE(g.groupName, '') like %:group% "
			+ "and COALESCE(pro.dea, '') like %:dea%  and p.dateRegistrated>=:fromDate group by p.id "
			+ "order by case when p.status='Profile Completed' then 1 when p.status='New' then 2 when p.status='New Modifications' then 3 when p.status='Approved' then 4 when p.status='Denied' then 5 end, p.id desc ")
	Page<Object[]> reportByFilterWithFromDate(@Param("physicianName") String physician, @Param("group") String group, 
			@Param("fromDate") Date fromDate, @Param("dea") String dea, Pageable page);

	@Query("select p.physicianName,g.groupName,c.clinicName,pro.dea,p.phone,p.mobile,p.email,p.city,p.state,p.dateRegistrated,p.status, p.id "
			+ " from PhysicianProfileInfo pro left join PhysicianAccount p on pro.id=p.id left join  PhysicianClinic pc on p.id=pc.physicianId left join Clinic c on c.id=pc.clinicId "
			+ " left join PhysicianGroup gph on gph.physicianId=p.id left join GroupMaster g on g.id=gph.groupId where "
			+ "COALESCE(p.physicianName, '') like %:physicianName% "
			+ "and COALESCE(g.groupName, '') like %:group% "
			+ "and COALESCE(pro.dea, '') like %:dea%  and p.dateRegistrated<=:toDate group by p.id "
			+ "order by case when p.status='Profile Completed' then 1 when p.status='New' then 2 when p.status='New Modifications' then 3 when p.status='Approved' then 4 when p.status='Denied' then 5 end, p.id desc ")
	Page<Object[]> reportByFilterWithToDate(@Param("physicianName") String physician, @Param("group") String group, 
			@Param("toDate") Date toDate, @Param("dea") String dea, Pageable page);
	
	@Query("select p.physicianName,g.groupName,c.clinicName,pro.dea,p.phone,p.mobile,p.email,p.city,p.state,p.dateRegistrated,p.status, p.id "
			+ " from PhysicianProfileInfo pro left join PhysicianAccount p on pro.id=p.id left join  PhysicianClinic pc on p.id=pc.physicianId left join Clinic c on c.id=pc.clinicId "
			+ " left join PhysicianGroup gph on gph.physicianId=p.id left join GroupMaster g on g.id=gph.groupId where "
			+ "COALESCE(p.physicianName, '') like %:physicianName% "
			+ "and COALESCE(g.groupName, '') like %:group% "
			+ "and COALESCE(pro.dea, '') like %:dea%  and p.dateRegistrated>=:fromDate and p.dateRegistrated<=:toDate group by p.id "
			+ "order by case when p.status='Profile Completed' then 1 when p.status='New' then 2 when p.status='New Modifications' then 3 when p.status='Approved' then 4 when p.status='Denied' then 5 end, p.id desc ")
	Page<Object[]> reportByFilterWithDate(@Param("physicianName") String physician, @Param("group") String group, 
			@Param("fromDate") Date fromDate, @Param("toDate") Date toDate, @Param("dea") String dea, Pageable page);
	
	
	
	@Query("select p.physicianName,g.groupName,c.clinicName,pro.dea,p.phone,p.mobile,p.email,p.city,p.state,p.dateRegistrated,p.status, p.id "
			+ " from PhysicianProfileInfo pro left join PhysicianAccount p on pro.id=p.id left join  PhysicianClinic pc on p.id=pc.physicianId left join Clinic c on c.id=pc.clinicId "
			+ " left join PhysicianGroup gph on gph.physicianId=p.id left join GroupMaster g on g.id=gph.groupId where pc.delFlag='N' and "
			+ "COALESCE(p.physicianName, '') like %:physicianName% "
			+ "and COALESCE(c.clinicName, '') like %:clinic%  and COALESCE(g.groupName, '') like %:group% "
			+ "and COALESCE(pro.dea, '') like %:dea%  group by p.id "
			+ "order by case when p.status='Profile Completed' then 1 when p.status='New' then 2 when p.status='New Modifications' then 3 when p.status='Approved' then 4 when p.status='Denied' then 5 end, p.id desc ")
	Page<Object[]> reportByFilter(@Param("physicianName") String physician, @Param("clinic") String clinic, @Param("group") String group, 
			@Param("dea") String dea, Pageable page);

	@Query("select p.physicianName,g.groupName,c.clinicName,pro.dea,p.phone,p.mobile,p.email,p.city,p.state,p.dateRegistrated,p.status, p.id "
			+ " from PhysicianProfileInfo pro left join PhysicianAccount p on pro.id=p.id left join  PhysicianClinic pc on p.id=pc.physicianId left join Clinic c on c.id=pc.clinicId "
			+ " left join PhysicianGroup gph on gph.physicianId=p.id left join GroupMaster g on g.id=gph.groupId where pc.delFlag='N' and "
			+ "COALESCE(p.physicianName, '') like %:physicianName% "
			+ "and COALESCE(c.clinicName, '') like %:clinic%  and COALESCE(g.groupName, '') like %:group% "
			+ "and COALESCE(pro.dea, '') like %:dea%  and p.dateRegistrated>=:fromDate group by p.id "
			+ "order by case when p.status='Profile Completed' then 1 when p.status='New' then 2 when p.status='New Modifications' then 3 when p.status='Approved' then 4 when p.status='Denied' then 5 end, p.id desc ")
	Page<Object[]> reportByFilterWithFromDate(@Param("physicianName") String physician, @Param("clinic") String clinic, @Param("group") String group, 
			@Param("fromDate") Date fromDate, @Param("dea") String dea, Pageable page);

	@Query("select p.physicianName,g.groupName,c.clinicName,pro.dea,p.phone,p.mobile,p.email,p.city,p.state,p.dateRegistrated,p.status, p.id "
			+ " from PhysicianProfileInfo pro left join PhysicianAccount p on pro.id=p.id left join  PhysicianClinic pc on p.id=pc.physicianId left join Clinic c on c.id=pc.clinicId "
			+ " left join PhysicianGroup gph on gph.physicianId=p.id left join GroupMaster g on g.id=gph.groupId where pc.delFlag='N' and "
			+ "COALESCE(p.physicianName, '') like %:physicianName% "
			+ "and COALESCE(c.clinicName, '') like %:clinic%  and COALESCE(g.groupName, '') like %:group% "
			+ "and COALESCE(pro.dea, '') like %:dea% and p.dateRegistrated<=:toDate  group by p.id "
			+ "order by case when p.status='Profile Completed' then 1 when p.status='New' then 2 when p.status='New Modifications' then 3 when p.status='Approved' then 4 when p.status='Denied' then 5 end, p.id desc ")
	Page<Object[]> reportByFilterWithToDate(@Param("physicianName") String physician, @Param("clinic") String clinic, @Param("group") String group, 
			@Param("toDate") Date toDate, @Param("dea") String dea, Pageable page);
	
	@Query("select p.physicianName,g.groupName,c.clinicName,pro.dea,p.phone,p.mobile,p.email,p.city,p.state,p.dateRegistrated,p.status, p.id "
			+ " from PhysicianProfileInfo pro left join PhysicianAccount p on pro.id=p.id left join  PhysicianClinic pc on p.id=pc.physicianId left join Clinic c on c.id=pc.clinicId "
			+ " left join PhysicianGroup gph on gph.physicianId=p.id left join GroupMaster g on g.id=gph.groupId where pc.delFlag='N' and "
			+ "COALESCE(p.physicianName, '') like %:physicianName% "
			+ "and COALESCE(c.clinicName, '') like %:clinic%  and COALESCE(g.groupName, '') like %:group% "
			+ "and COALESCE(pro.dea, '') like %:dea%  and p.dateRegistrated>=:fromDate and p.dateRegistrated<=:toDate  group by p.id "
			+ "order by case when p.status='Profile Completed' then 1 when p.status='New' then 2 when p.status='New Modifications' then 3 when p.status='Approved' then 4 when p.status='Denied' then 5 end, p.id desc ")
	Page<Object[]> reportByFilterWithDate(@Param("physicianName") String physician, @Param("clinic") String clinic, @Param("group") String group, 
			@Param("fromDate") Date fromDate, @Param("toDate") Date toDate, @Param("dea") String dea, Pageable page);
	
	
	
	
	
	
	
	@Query("select p.physicianName,g.groupName,c.clinicName,pro.dea,p.phone,p.mobile,p.email,p.city,p.state,p.dateRegistrated,p.status, p.id "
			+ " from PhysicianProfileInfo pro left join PhysicianAccount p on pro.id=p.id left join  PhysicianClinic pc on p.id=pc.physicianId left join Clinic c on c.id=pc.clinicId "
			+ " left join PhysicianGroup gph on gph.physicianId=p.id left join GroupMaster g on g.id=gph.groupId where  "
			+ "COALESCE(p.physicianName, '') like %:physicianName% "
			+ "and COALESCE(g.groupName, '') like %:group% "
			+ "and COALESCE(pro.dea, '') like %:dea% and p.status =:status   group by p.id "
			+ "order by case when p.status='Profile Completed' then 1 when p.status='New' then 2 when p.status='New Modifications' then 3 when p.status='Approved' then 4 when p.status='Denied' then 5 end, p.id desc ")
	Page<Object[]> reportByFilterWithStatus(@Param("physicianName") String physician, @Param("group") String group, 
			@Param("dea") String dea, @Param("status") String status, Pageable page);

	
	
	@Query("select p.physicianName,g.groupName,c.clinicName,pro.dea,p.phone,p.mobile,p.email,p.city,p.state,p.dateRegistrated,p.status, p.id "
			+ " from PhysicianProfileInfo pro left join PhysicianAccount p on pro.id=p.id left join  PhysicianClinic pc on p.id=pc.physicianId left join Clinic c on c.id=pc.clinicId "
			+ " left join PhysicianGroup gph on gph.physicianId=p.id left join GroupMaster g on g.id=gph.groupId where  "
			+ "COALESCE(p.physicianName, '') like %:physicianName% "
			+ "and COALESCE(g.groupName, '') like %:group% "
			+ "and COALESCE(pro.dea, '') like %:dea% and p.status =:status and p.dateRegistrated>=:fromDate group by p.id "
			+ "order by case when p.status='Profile Completed' then 1 when p.status='New' then 2 when p.status='New Modifications' then 3 when p.status='Approved' then 4 when p.status='Denied' then 5 end, p.id desc ")
	Page<Object[]> reportByFilterWithStatusAndFromDate(@Param("physicianName") String physician, @Param("group") String group, 
			@Param("fromDate") Date fromDate, @Param("dea") String dea, @Param("status") String status, Pageable page);

	
	@Query("select p.physicianName,g.groupName,c.clinicName,pro.dea,p.phone,p.mobile,p.email,p.city,p.state,p.dateRegistrated,p.status, p.id "
			+ " from PhysicianProfileInfo pro left join PhysicianAccount p on pro.id=p.id left join  PhysicianClinic pc on p.id=pc.physicianId left join Clinic c on c.id=pc.clinicId "
			+ " left join PhysicianGroup gph on gph.physicianId=p.id left join GroupMaster g on g.id=gph.groupId where  "
			+ "COALESCE(p.physicianName, '') like %:physicianName% "
			+ "and COALESCE(g.groupName, '') like %:group% "
			+ "and COALESCE(pro.dea, '') like %:dea% and p.status =:status and p.dateRegistrated<=:toDate   group by p.id "
			+ "order by case when p.status='Profile Completed' then 1 when p.status='New' then 2 when p.status='New Modifications' then 3 when p.status='Approved' then 4 when p.status='Denied' then 5 end, p.id desc ")
	Page<Object[]> reportByFilterWithStatusAndToDate(@Param("physicianName") String physician, @Param("group") String group, 
			@Param("toDate") Date toDate, @Param("dea") String dea, @Param("status") String status, Pageable page);
	
	@Query("select p.physicianName,g.groupName,c.clinicName,pro.dea,p.phone,p.mobile,p.email,p.city,p.state,p.dateRegistrated,p.status, p.id "
			+ " from PhysicianProfileInfo pro left join PhysicianAccount p on pro.id=p.id left join  PhysicianClinic pc on p.id=pc.physicianId left join Clinic c on c.id=pc.clinicId "
			+ " left join PhysicianGroup gph on gph.physicianId=p.id left join GroupMaster g on g.id=gph.groupId where  "
			+ "COALESCE(p.physicianName, '') like %:physicianName% "
			+ "and COALESCE(g.groupName, '') like %:group% "
			+ "and COALESCE(pro.dea, '') like %:dea% and p.status =:status and p.dateRegistrated>=:fromDate and p.dateRegistrated<=:toDate group by p.id "
			+ "order by case when p.status='Profile Completed' then 1 when p.status='New' then 2 when p.status='New Modifications' then 3 when p.status='Approved' then 4 when p.status='Denied' then 5 end, p.id desc ")
	Page<Object[]> reportByFilterWithStatusAndDate(@Param("physicianName") String physician, @Param("group") String group, 
			@Param("fromDate") Date fromDate, @Param("toDate") Date toDate, @Param("dea") String dea, @Param("status") String status, Pageable page);
	


	
	
	/******** with clinic *********/
	
	
	@Query("select p.physicianName,g.groupName,c.clinicName,pro.dea,p.phone,p.mobile,p.email,p.city,p.state,p.dateRegistrated,p.status, p.id "
			+ " from PhysicianProfileInfo pro left join PhysicianAccount p on pro.id=p.id left join  PhysicianClinic pc on p.id=pc.physicianId left join Clinic c on c.id=pc.clinicId "
			+ " left join PhysicianGroup gph on gph.physicianId=p.id left join GroupMaster g on g.id=gph.groupId where pc.delFlag='N' and "
			+ "COALESCE(p.physicianName, '') like %:physicianName% and COALESCE(c.clinicName, '') like %:clinic%  "
			+ "and COALESCE(g.groupName, '') like %:group% "
			+ "and COALESCE(pro.dea, '') like %:dea% and p.status =:status   group by p.id "
			+ "order by case when p.status='Profile Completed' then 1 when p.status='New' then 2 when p.status='New Modifications' then 3 when p.status='Approved' then 4 when p.status='Denied' then 5 end, p.id desc ")
	Page<Object[]> reportByFilterWithStatus(@Param("physicianName") String physician, @Param("clinic") String clinic, @Param("group") String group, 
			@Param("dea") String dea, @Param("status") String status, Pageable page);

	
	
	@Query("select p.physicianName,g.groupName,c.clinicName,pro.dea,p.phone,p.mobile,p.email,p.city,p.state,p.dateRegistrated,p.status, p.id "
			+ " from PhysicianProfileInfo pro left join PhysicianAccount p on pro.id=p.id left join  PhysicianClinic pc on p.id=pc.physicianId left join Clinic c on c.id=pc.clinicId "
			+ " left join PhysicianGroup gph on gph.physicianId=p.id left join GroupMaster g on g.id=gph.groupId where pc.delFlag='N' and "
			+ "COALESCE(p.physicianName, '') like %:physicianName% and COALESCE(c.clinicName, '') like %:clinic%  "
			+ "and COALESCE(g.groupName, '') like %:group% "
			+ "and COALESCE(pro.dea, '') like %:dea% and p.status =:status and p.dateRegistrated>=:fromDate group by p.id "
			+ "order by case when p.status='Profile Completed' then 1 when p.status='New' then 2 when p.status='New Modifications' then 3 when p.status='Approved' then 4 when p.status='Denied' then 5 end, p.id desc ")
	Page<Object[]> reportByFilterWithStatusAndFromDate(@Param("physicianName") String physician, @Param("clinic") String clinic, @Param("group") String group, 
			@Param("fromDate") Date fromDate, @Param("dea") String dea, @Param("status") String status, Pageable page);

	
	@Query("select p.physicianName,g.groupName,c.clinicName,pro.dea,p.phone,p.mobile,p.email,p.city,p.state,p.dateRegistrated,p.status, p.id "
			+ " from PhysicianProfileInfo pro left join PhysicianAccount p on pro.id=p.id left join  PhysicianClinic pc on p.id=pc.physicianId left join Clinic c on c.id=pc.clinicId "
			+ " left join PhysicianGroup gph on gph.physicianId=p.id left join GroupMaster g on g.id=gph.groupId where pc.delFlag='N' and "
			+ "COALESCE(p.physicianName, '') like %:physicianName% and COALESCE(c.clinicName, '') like %:clinic%  "
			+ "and COALESCE(g.groupName, '') like %:group% "
			+ "and COALESCE(pro.dea, '') like %:dea% and p.status =:status and p.dateRegistrated<=:toDate   group by p.id "
			+ "order by case when p.status='Profile Completed' then 1 when p.status='New' then 2 when p.status='New Modifications' then 3 when p.status='Approved' then 4 when p.status='Denied' then 5 end, p.id desc ")
	Page<Object[]> reportByFilterWithStatusAndToDate(@Param("physicianName") String physician, @Param("clinic") String clinic, @Param("group") String group, 
			@Param("toDate") Date toDate, @Param("dea") String dea, @Param("status") String status, Pageable page);
	
	@Query("select p.physicianName,g.groupName,c.clinicName,pro.dea,p.phone,p.mobile,p.email,p.city,p.state,p.dateRegistrated,p.status, p.id "
			+ " from PhysicianProfileInfo pro left join PhysicianAccount p on pro.id=p.id left join  PhysicianClinic pc on p.id=pc.physicianId left join Clinic c on c.id=pc.clinicId "
			+ " left join PhysicianGroup gph on gph.physicianId=p.id left join GroupMaster g on g.id=gph.groupId where pc.delFlag='N' and "
			+ "COALESCE(p.physicianName, '') like %:physicianName% and COALESCE(c.clinicName, '') like %:clinic% "
			+ "and COALESCE(g.groupName, '') like %:group% "
			+ "and COALESCE(pro.dea, '') like %:dea% and p.status =:status and p.dateRegistrated>=:fromDate and p.dateRegistrated<=:toDate group by p.id "
			+ "order by case when p.status='Profile Completed' then 1 when p.status='New' then 2 when p.status='New Modifications' then 3 when p.status='Approved' then 4 when p.status='Denied' then 5 end, p.id desc ")
	Page<Object[]> reportByFilterWithStatusAndDate(@Param("physicianName") String physician, @Param("clinic") String clinic, @Param("group") String group, 
			@Param("fromDate") Date fromDate, @Param("toDate") Date toDate, @Param("dea") String dea, @Param("status") String status, Pageable page);
	

	@Query("select p from PhysicianAccount p left join PhysicianClinic c on p.id=c.physicianId where c.clinicId=:clinicId and p.physicianName like %:physicianName% and c.delFlag='N' ")
	List<PhysicianAccount> getPhysicianListByClinicId(@Param("clinicId") int clinicId, @Param("physicianName") String physicianName);

	@Query("select p from PhysicianAccount p left join PhysicianClinic c on p.id=c.physicianId where c.clinicId=:clinicId and p.physicianName like %:physicianName% and c.delFlag='N' ")
	Page<PhysicianAccount> getPhysicianListByClinicId(@Param("clinicId") int clinicId, @Param("physicianName") String physicianName, Pageable page);
	
	
	@Modifying
	@Transactional(readOnly=false)
    @Query("UPDATE PhysicianAccount c SET c.status = ?1 WHERE c.id = ?2")
    int updatePhysicianStatus(String status, int physicianId);
	
	@Modifying
	@Transactional(readOnly=false)
    @Query("UPDATE PhysicianAccount c SET c.profilestep = ?1 WHERE c.id = ?2 and ?3>c.profilestep")
    int updatePhysicianProfileStep(int stepno, int physicianId,int checkStepno);
	
	@Query("select c from PhysicianAccount c where c.status=:status and c.id<>:physicianId")
	List<PhysicianAccount> getAllOtherPhysicians(@Param("status") String status,@Param("physicianId") int physicianId);

	
	@Modifying
	@Transactional(readOnly=false)
    @Query("UPDATE PatientPhysicians c SET c.delFlag = ?1,c.physicianId = ?2,c.updatedBy=?3, c.updatedDate=?4,c.updatedUserType=?5 WHERE c.physicianId = ?6")
    int reAssignPatientPhysician(String status,int otherPhysicianId,int updatedBy,Timestamp updatedDate,String updatedUser, int physicianId);
	
	@Modifying
	@Transactional(readOnly=false)
    @Query("UPDATE PatientPhysicians c SET c.delFlag = ?1,c.updatedBy=?2, c.updatedDate=?3,c.updatedUserType=?4 WHERE c.physicianId = ?5")
    int deActivatePatientPhysician(String status,int updatedBy,Timestamp updatedDate,String updatedUser, int physicianId);
	
	@Modifying
	@Transactional(readOnly=false)
    @Query("UPDATE PhysicianAccount c SET c.status = ?1, c.lastUpdatedBy=?2, c.lastUpdatedUser=?3, c.lastUpdatedDate=?4, c.deniedBy=?5, c.deniedUser=?6, c.deniedDate=?7 WHERE c.id=?8")
    int deActivatePhysician(String status,int updatedBy,String updatedUser,Timestamp updatedDate,int deniedBy,String deniedUser,Timestamp deniedDate,int physicianId);

	
	@Query("select p from PhysicianAccount p  join PhysicianGroup g on p.id=g.physicianId "
			+ "where g.groupId=:groupId and g.status='Active' and p.status='Approved' and p.id <>:physicianId  group by p.id ")
	List<PhysicianAccount> getOtherPhysicianListByActiveGroupId(@Param("groupId") int groupId,@Param("physicianId") int physicianId);
	
	
	@Modifying
	@Transactional(readOnly=false)
    @Query("UPDATE PhysicianGroup g SET g.groupId = ?1 WHERE g.physicianId in (select c.physicianId from PhysicianClinic c where c.clinicId=?2)")
    int reAssignPhysicianGroupOnChangeofDefaultGroupinClinic(int groupId,int clinicId);


	/*** Scheduled License expiry mail sending purpose  *************/
	@Query("select p from PhysicianAccount p join PhysicianProfileInfo b on p.id=b.physicianId and b.commEmail='Yes' and p.status!='Denied' ")
	List<PhysicianAccount> getAllEmailCommunicatePhysicians();

}


