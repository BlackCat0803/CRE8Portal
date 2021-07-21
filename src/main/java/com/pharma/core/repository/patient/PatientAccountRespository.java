package com.pharma.core.repository.patient;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pharma.core.model.patientaccount.PatientAccount;
/**
 * This class is a Java interface that defines a data access contract
 *
 */
@Repository("patientAccountRepository")
public interface PatientAccountRespository extends JpaRepository<PatientAccount, Integer> {

	/* select * from patient_profile p left join patient_physicians pp on p.patient_id = pp.patient_id 
			left join phy_info phy on pp.physician_id = phy.physician_id where p.group_id=2 and pp.delFlag='N'; */
	@Query("select p from PatientAccount p,PatientGroup pg where p.id=pg.patientId and pg.groupId=:groupId and p.patientName like %:name%  ")
	List<PatientAccount> getPatientByGroupId(@Param("groupId") int groupId, @Param("name") String name);
	
	@Query("select p.id, p.patientName,p.status,p.email,p.phone from PatientAccount p,PatientGroup pg "
			+ "where p.id=pg.patientId and pg.groupId=:groupId and p.patientName like %:name%  ")
	Page<Object[]> getPatientByGroupId(@Param("groupId") int groupId, @Param("name") String name, Pageable page);
	
	
	@Query("select p from PatientAccount p,PatientGroup pg where p.id=pg.patientId and pg.groupId=:groupId and p.patientName like %:name% "
			+ "and status=:status ")
	List<PatientAccount> getPatientByGroupId(@Param("groupId") int groupId, @Param("name") String name, @Param("status") String status);
	
	@Query("select p.id, p.patientName,p.status,p.email,p.phone from PatientAccount p,PatientGroup pg where p.id=pg.patientId and "
			+ "pg.groupId=:groupId and p.patientName like %:name% and status=:status ")
	Page<Object[]> getPatientByGroupId(@Param("groupId") int groupId, @Param("name") String name, @Param("status") String status, Pageable page);

	@Query("Select c from PatientAccount c where c.patientName=:patientName and c.dateOfBirth=:dob ")
	List<PatientAccount> findByPatientNameAndDOB(@Param("patientName") String patientName, @Param("dob") Date dob);
	
	List<PatientAccount> findByEmail(String email);
	
	List<PatientAccount> findByUserLoginId(String userLoginId);
	
	PatientAccount findById(int id);
	
	@Query("select p from PatientAccount p,PatientGroup pg where p.id=pg.patientId and pg.groupId=:groupId and p.ssn=:ssn  ")
	List<PatientAccount> findBySsnAndGroupId(String ssn, int groupId);
	
	@Query("Select c from PatientAccount c where c.patientName like %:name% and status=:status ")
	Page<PatientAccount> findByPatientNameAndStatus(@Param("name") String name, @Param("status") String status, Pageable page);
	
	@Query("select  COALESCE(count(c),0)  from PatientAccount c where c.patientName like %:name% and status=:status")
	int findByPatientNameAndStatus(@Param("name") String name, @Param("status") String status);

	@Query("select  COALESCE(count(c),0)  from PatientAccount c where status=:status")
	int findByStatus(@Param("status") String status);
	
	@Query("select  COALESCE(count(c),0)  from PatientAccount c ")
	int findByAll();
	
	@Query("select c from PatientAccount c where status=:status")
	Page<PatientAccount> findByStatus(@Param("status") String status, Pageable page);
	
	List<PatientAccount> findByEmailAndIdNot(String email, int id);
	
	List<PatientAccount> findByUserLoginIdAndIdNot(String userLoginId, int id);
	
	@Query("select p from PatientAccount p,PatientGroup pg where p.id=pg.patientId and pg.groupId=:groupId and p.ssn=:ssn and p.id<>:id ")
	List<PatientAccount> findBySsnAndGroupIdAndIdNot(String ssn, int groupId, int id);
	
	PatientAccount findByEmailAndPassword(String email, String password);
	
	@Query("select  COALESCE(count(c),0)  from PatientAccount c where c.id=:id and status=:status")
	int findByPatientIdAndStatus(@Param("id") int id, @Param("status") String status);
	
	@Query("select c from PatientAccount c ")
	Page<PatientAccount> findByPatientIdAndStatus( Pageable page);

	/****  Summary page queries start  ***********/
	// order by case when status='New' then 1 when status='Approved' then 2 when status='Denied' then 3 end, c.id desc
	
	@Query("select  COALESCE(count(c),0)  from PatientAccount c,PatientPhysicians pp where pp.physicianId=:id and c.id=pp.patientId and "
			+ "status=:status group by c.id order by case when c.status='Profile Completed' then 1 when c.status='New' then 2 when "
			+ "c.status='New Modifications' then 3 when c.status='Approved' then 4 when c.status='Denied' then 5 end, c.id desc ")
	int findByAllByPhysician(@Param("id") int id, @Param("status") String status);

	@Query("select  COALESCE(count(c),0)  from PatientAccount c left join PatientPhysicians pp on pp.patientId=c.id left join PhysicianAccount p on "
			+ "pp.physicianId=p.id where pp.physicianId=:id and (c.patientName like %:patientName% or c.patientName is null) and "
			+ "(p.physicianName like %:physicianName% or p.physicianName is null) and c.status=:status group by c.id "
			+ "order by case when c.status='Profile Completed' then 1 when c.status='New' then 2 when c.status='New Modifications' then 3 "
			+ "when c.status='Approved' then 4 when c.status='Denied' then 5 end, c.id desc ")
	int getSearchTotal(@Param("id") int id, @Param("patientName") String patientName, @Param("physicianName") String physicianName,  @Param("status") String status);


	
	@Query("select  COALESCE(count(c),0)  from PatientAccount c left join PatientPhysicians pp on pp.patientId=c.id left join PhysicianAccount p on "
			+ "pp.physicianId=p.id where pp.physicianId=:id and (c.patientName like %:patientName% or c.patientName is null) and "
			+ "p.physicianName like %:physicianName%  and c.status=:status group by c.id "
			+ "order by case when c.status='Profile Completed' then 1 when c.status='New' then 2 when c.status='New Modifications' then 3 "
			+ "when c.status='Approved' then 4 when c.status='Denied' then 5 end, c.id desc ")
	int getSearchAndPhysicianFilterTotal(@Param("id") int id, @Param("patientName") String patientName, @Param("physicianName") String physicianName,  @Param("status") String status);
	
	
	
	
	@Query("select  COALESCE(count(c),0)  from PatientAccount c where status=:status order by c.id ")
	int findByAllByStatus(@Param("status") String status);
	
	@Query("select COALESCE(count(c),0)  from PatientAccount c left join PatientPhysicians pp on pp.patientId=c.id left join PhysicianAccount p on "
			+ "pp.physicianId=p.id where (c.patientName like %:patientName% or c.patientName is null) and (p.physicianName like %:physicianName% or "
			+ "p.physicianName is null) and c.status=:status group by c.id "
			+ "order by case when c.status='Profile Completed' then 1 when c.status='New' then 2 when c.status='New Modifications' then 3 "
			+ "when c.status='Approved' then 4 when c.status='Denied' then 5 end, c.id desc ")	
	List<PatientAccount> getSearchTotal(@Param("patientName") String patientName, @Param("physicianName") String physicianName,  @Param("status") String status);
	
	
	@Query("select  COALESCE(count(c),0)  from PatientAccount c left join PatientPhysicians pp on pp.patientId=c.id left join PhysicianAccount p on "
			+ "pp.physicianId=p.id where (c.patientName like %:patientName% or c.patientName is null) and p.physicianName like %:physicianName%  "
			+ " and c.status=:status group by c.id "
			+ "order by case when c.status='Profile Completed' then 1 when c.status='New' then 2 when c.status='New Modifications' then 3 "
			+ "when c.status='Approved' then 4 when c.status='Denied' then 5 end, c.id desc ")	
	int getSearchAndPhysicianFilterTotal(@Param("patientName") String patientName, @Param("physicianName") String physicianName,  @Param("status") String status);
	

	
	
	@Query("select  COALESCE(count(c),0)  from PatientAccount c left join PatientPhysicians pp on c.id=pp.patientId where pp.physicianId=:id "
			+ "order by case when c.status='Profile Completed' then 1 when c.status='New' then 2 when c.status='New Modifications' then 3 "
			+ "when c.status='Approved' then 4 when c.status='Denied' then 5 end, c.id desc ")
	int findByAllByPhysician(@Param("id") int id);
	
	@Query("select  COALESCE(count(c),0)  from PatientAccount c left join PatientPhysicians pp on pp.patientId=c.id left join "
			+ "PhysicianAccount p on pp.physicianId=p.id where pp.physicianId=:id and (c.patientName like %:patientName% or c.patientName is null) "
			+ "and (p.physicianName like %:physicianName% or p.physicianName is null)  group by c.id "
			+ "order by case when c.status='Profile Completed' then 1 when c.status='New' then 2 when c.status='New Modifications' then 3 "
			+ "when c.status='Approved' then 4 when c.status='Denied' then 5 end, c.id desc ")
	int getSearchTotal(@Param("id") int id, @Param("patientName") String patientName, @Param("physicianName") String physicianName);
	
	
	@Query("select  COALESCE(count(c),0)  from PatientAccount c left join PatientPhysicians pp on pp.patientId=c.id left join "
			+ "PhysicianAccount p on pp.physicianId=p.id where pp.physicianId=:id and (c.patientName like %:patientName% or c.patientName is null) "
			+ "and p.physicianName like %:physicianName%  group by c.id "
			+ "order by case when c.status='Profile Completed' then 1 when c.status='New' then 2 when c.status='New Modifications' then 3 "
			+ "when c.status='Approved' then 4 when c.status='Denied' then 5 end, c.id desc ")
	int getSearchAndPhysicianFilterTotal(@Param("id") int id, @Param("patientName") String patientName, @Param("physicianName") String physicianName);
	

	
	
	@Query("select  COALESCE(count(c),0)  from PatientAccount c order by case when c.status='Profile Completed' then 1 when c.status='New' then 2 "
			+ "when c.status='New Modifications' then 3 when c.status='Approved' then 4 when c.status='Denied' then 5 end, c.id desc ")
	int findByAllByStatus();

	/*@Query("select  COALESCE(count(c),0)  from PatientAccount c, PhysicianAccount p,PatientPhysicians pp where pp.physicianId=p.id and pp.patientId=c.id "
			+ "and c.patientName like %:patientName%  and p.physicianName like %:physicianName%  group by c.id  "
			+ "order by case when c.status='Profile Completed' then 1 when c.status='New' then 2 when c.status='New Modifications' then 3 "
			+ "when c.status='Approved' then 4 when c.status='Denied' then 5 end, c.id desc ")	
	int getSearchTotal(@Param("patientName") String patientName, @Param("physicianName") String physicianName);*/

	@Query("select  COALESCE(count(c),0)  from PatientAccount c left join PatientPhysicians pp on pp.patientId=c.id left join PhysicianAccount p on "
			+ "pp.physicianId=p.id where (c.patientName like %:patientName% or c.patientName is null) and (p.physicianName like %:physicianName% "
			+ "or p.physicianName is null) group by c.id  order by case when c.status='Profile Completed' then 1 when c.status='New' then 2 "
			+ "when c.status='New Modifications' then 3 when c.status='Approved' then 4 when c.status='Denied' then 5 end, c.id desc ")	
	int getSearchTotal(@Param("patientName") String patientName, @Param("physicianName") String physicianName);
	
	
	@Query("select  COALESCE(count(c),0)  from PatientAccount c left join PatientPhysicians pp on pp.patientId=c.id left join PhysicianAccount p on "
			+ "pp.physicianId=p.id where (c.patientName like %:patientName% or c.patientName is null) and p.physicianName like %:physicianName% "
			+ " group by c.id  order by case when c.status='Profile Completed' then 1 when c.status='New' then 2 "
			+ "when c.status='New Modifications' then 3 when c.status='Approved' then 4 when c.status='Denied' then 5 end, c.id desc ")	
	int getSearchAndPhysicianFilterTotal(@Param("patientName") String patientName, @Param("physicianName") String physicianName);

	
	@Query("select c from PatientAccount c,PatientPhysicians pp where pp.patientId=c.id and pp.physicianId=:id and c.status=:status  "
			+ "group by c.id order by c.id desc")
	Page<PatientAccount> findByAllByPhysician(@Param("id") int id, @Param("status") String status, Pageable page);
	
	@Query("select c from PatientAccount c left join PatientPhysicians pp on pp.patientId=c.id left join PhysicianAccount p on pp.physicianId=p.id "
			+ "where pp.physicianId=:id and (c.patientName like %:patientName% or c.patientName is null) and (p.physicianName like %:physicianName% "
			+ "or p.physicianName is null) and c.status=:status group by c.id "
			+ "order by case when c.status='Profile Completed' then 1 when c.status='New' then 2 when c.status='New Modifications' then 3 "
			+ "when c.status='Approved' then 4 when c.status='Denied' then 5 end, c.id desc")
	Page<PatientAccount> getSearchRecords(@Param("id") int id, @Param("patientName") String patientName, @Param("physicianName") String physicianName,  
			@Param("status") String status, Pageable page);

	
	@Query("select c from PatientAccount c left join PatientPhysicians pp on pp.patientId=c.id left join PhysicianAccount p on pp.physicianId=p.id "
			+ "where pp.physicianId=:id and (c.patientName like %:patientName% or c.patientName is null) and p.physicianName like %:physicianName% "
			+ "and c.status=:status and pp.delFlag='N' group by c.id "
			+ "order by case when c.status='Profile Completed' then 1 when c.status='New' then 2 when c.status='New Modifications' then 3 "
			+ "when c.status='Approved' then 4 when c.status='Denied' then 5 end, c.id desc")
	Page<PatientAccount> getSearchAndPhysicianFilterRecords(@Param("id") int id, @Param("patientName") String patientName, @Param("physicianName") String physicianName,  
			@Param("status") String status, Pageable page);
	
	
	
	@Query("select c from PatientAccount c where status=:status order by c.id desc")
	Page<PatientAccount> findByAllByStatus(@Param("status") String status, Pageable page);
	
	@Query("select c from PatientAccount c left join PatientPhysicians pp on pp.patientId=c.id left join PhysicianAccount p on pp.physicianId=p.id where "
			+ "(c.patientName like %:patientName% or c.patientName is null) and (p.physicianName like %:physicianName% or p.physicianName is null) and "
			+ "c.status=:status  group by c.id order by case when c.status='Profile Completed' then 1 when c.status='New' then 2 "
			+ "when c.status='New Modifications' then 3 when c.status='Approved' then 4 when c.status='Denied' then 5 end, c.id desc")	
	Page<PatientAccount> getSearchRecords(@Param("patientName") String patientName, @Param("physicianName") String physicianName,  
			@Param("status") String status, Pageable page);
	
	
	@Query("select c from PatientAccount c left join PatientPhysicians pp on pp.patientId=c.id left join PhysicianAccount p on pp.physicianId=p.id where "
			+ "(c.patientName like %:patientName% or c.patientName is null) and p.physicianName like %:physicianName% and "
			+ "c.status=:status and pp.delFlag='N'  group by c.id order by case when c.status='Profile Completed' then 1 when c.status='New' then 2 "
			+ "when c.status='New Modifications' then 3 when c.status='Approved' then 4 when c.status='Denied' then 5 end, c.id desc")	
	Page<PatientAccount> getSearchAndPhysicianFilterRecords(@Param("patientName") String patientName, @Param("physicianName") String physicianName,  
			@Param("status") String status, Pageable page);
	
	
	
	@Query("select c from PatientAccount c left join PatientPhysicians pp on pp.patientId=c.id where pp.physicianId=:id  group by c.id "
			+ "order by case when c.status='Profile Completed' then 1 when c.status='New' then 2 when c.status='New Modifications' then 3 "
			+ "when c.status='Approved' then 4 when c.status='Denied' then 5 end, c.id desc ")
	Page<PatientAccount> findByAllByPhysician(@Param("id") int id, Pageable page);
	
	@Query("select c from PatientAccount c left join PatientPhysicians pp on pp.patientId=c.id left join PhysicianAccount p on pp.physicianId=p.id "
			+ "where pp.physicianId=:id and (c.patientName like %:patientName% or c.patientName is null) and (p.physicianName like %:physicianName% or "
			+ "p.physicianName is null)  group by c.id order by case when c.status='Profile Completed' then 1 when c.status='New' then 2 "
			+ "when c.status='New Modifications' then 3 when c.status='Approved' then 4 when c.status='Denied' then 5 end, c.id desc ")
	Page<PatientAccount> getSearchRecords(@Param("id") int id, @Param("patientName") String patientName, @Param("physicianName") String physicianName,  
			Pageable page);
	
	
	@Query("select c from PatientAccount c left join PatientPhysicians pp on pp.patientId=c.id left join PhysicianAccount p on pp.physicianId=p.id "
			+ "where pp.physicianId=:id and (c.patientName like %:patientName% or c.patientName is null) and p.physicianName like %:physicianName% "
			+ "and pp.delFlag='N' group by c.id order by case when c.status='Profile Completed' then 1 when c.status='New' then 2 "
			+ "when c.status='New Modifications' then 3 when c.status='Approved' then 4 when c.status='Denied' then 5 end, c.id desc ")
	Page<PatientAccount> getSearchAndPhysicianFilterRecords(@Param("id") int id, @Param("patientName") String patientName, @Param("physicianName") String physicianName,  
			Pageable page);
	
	
	
	@Query("select c from PatientAccount c order by case when c.status='Profile Completed' then 1 when c.status='New' then 2 "
			+ "when c.status='New Modifications' then 3 when c.status='Approved' then 4 when c.status='Denied' then 5 end, c.id desc ")
	Page<PatientAccount> findByAllByStatus(Pageable page);
	
	/*@Query("select c from PatientAccount c, PhysicianAccount p,PatientPhysicians pp where pp.physicianId=p.id and pp.patientId=c.id and "
			+ "c.patientName like %:patientName%  and p.physicianName like %:physicianName%  group by c.id "
			+ "order by case when c.status='Profile Completed' then 1 when c.status='New' then 2 when c.status='New Modifications' then 3 "
			+ "when c.status='Approved' then 4 when c.status='Denied' then 5 end, c.id desc ")	
	Page<PatientAccount> getSearchRecords(@Param("patientName") String patientName, @Param("physicianName") String physicianName, Pageable page);*/

	@Query("select c from PatientAccount c left join PatientPhysicians pp on pp.patientId=c.id left join PhysicianAccount p on pp.physicianId=p.id "
			+ "where  (c.patientName like %:patientName%  or c.patientName is null) and (p.physicianName like %:physicianName% or p.physicianName is null) "
			+ " group by c.id order by case when c.status='Profile Completed' then 1 when c.status='New' then 2 when c.status='New Modifications' then 3 "
			+ "when c.status='Approved' then 4 when c.status='Denied' then 5 end, c.id desc ")	
	Page<PatientAccount> getSearchRecords(@Param("patientName") String patientName, @Param("physicianName") String physicianName, Pageable page);

	
	@Query("select c from PatientAccount c left join PatientPhysicians pp on pp.patientId=c.id left join PhysicianAccount p on pp.physicianId=p.id "
			+ "where  (c.patientName like %:patientName%  or c.patientName is null) and p.physicianName like %:physicianName% and pp.delFlag='N' "
			+ "group by c.id order by case when c.status='Profile Completed' then 1 when c.status='New' then 2 when c.status='New Modifications' then 3 "
			+ "when c.status='Approved' then 4 when c.status='Denied' then 5 end, c.id desc ")	
	Page<PatientAccount> getSearchAndPhysicianFilterRecords(@Param("patientName") String patientName, @Param("physicianName") String physicianName, Pageable page);

	
	
	
	/****  Summary page queries using group end  ***********/
	@Query("select  COALESCE(count(c),0)  from PatientAccount c, PhysicianAccount p, PatientGroup g,PatientPhysicians pp where pp.physicianId=p.id "
			+ "and pp.patientId=c.id and c.id=g.patientId and g.groupId=:groupId and pp.physicianId=:id and c.status=:status  group by c.id order by c.id ")
	int findByAllByPhysicianAndGroup(@Param("id") int id, @Param("groupId") int groupId, @Param("status") String status);
	
	@Query("select  COALESCE(count(c),0)  from PatientAccount c left join PatientPhysicians pp on pp.patientId=c.id left join PhysicianAccount p "
			+ "on pp.physicianId=p.id left join PatientGroup g on c.id=g.patientId where "
			+ "g.groupId=:groupId and pp.physicianId=:id and (c.patientName like %:patientName% or c.patientName is null) "
			+ "and (p.physicianName like %:physicianName% or p.physicianName is null) and c.status=:status  and g.delFlag='N'  group by c.id order by c.id ")
	int getSearchTotalByGroup(@Param("id") int id, @Param("groupId") int groupId, @Param("patientName") String patientName, 
			@Param("physicianName") String physicianName,  @Param("status") String status);
	
	

	@Query("select  COALESCE(count(c),0)  from PatientAccount c left join PatientPhysicians pp on pp.patientId=c.id left join PhysicianAccount p "
			+ "on pp.physicianId=p.id left join PatientGroup g on c.id=g.patientId where "
			+ "g.groupId=:groupId and pp.physicianId=:id and (c.patientName like %:patientName% or c.patientName is null) "
			+ "and p.physicianName like %:physicianName%  and c.status=:status  and g.delFlag='N'  group by c.id order by c.id ")
	int getSearchAndPhysicianFilterTotalByGroup(@Param("id") int id, @Param("groupId") int groupId, @Param("patientName") String patientName, 
			@Param("physicianName") String physicianName,  @Param("status") String status);

	
		
	@Query("select  COALESCE(count(c),0)  from PatientAccount c, PatientGroup g where  "
			+ "c.id=g.patientId and g.groupId=:groupId  and c.status=:status  group by c.id order by c.id ")
	int findByAllByStatusAndGroup(@Param("groupId") int groupId, @Param("status") String status);
	
	@Query("select  COALESCE(count(c),0)  from PatientAccount c left join PatientPhysicians pp on pp.patientId=c.id left join PhysicianAccount p "
			+ "on pp.physicianId=p.id left join PatientGroup g on c.id=g.patientId where g.groupId=:groupId and (c.patientName like %:patientName% or "
			+ "c.patientName is null) and (p.physicianName like %:physicianName% or p.physicianName is null) and c.status=:status  and g.delFlag='N' group by c.id "
			+ "order by case when c.status='New' then 1 when c.status='Profile Completed' then 2 when c.status='New Modifications' then 3  "
			+ "when c.status='Approved' then 4 when c.status='Denied' then 5 end, c.id desc ")	
	int getSearchTotalByGroup(@Param("groupId") int groupId, @Param("patientName") String patientName, @Param("physicianName") String physicianName,  
			@Param("status") String status);
	
	
	
	@Query("select  COALESCE(count(c),0)  from PatientAccount c left join PatientPhysicians pp on pp.patientId=c.id left join PhysicianAccount p "
			+ "on pp.physicianId=p.id left join PatientGroup g on c.id=g.patientId where g.groupId=:groupId and (c.patientName like %:patientName% or "
			+ "c.patientName is null) and p.physicianName like %:physicianName% and c.status=:status  and g.delFlag='N' group by c.id "
			+ "order by case when c.status='New' then 1 when c.status='Profile Completed' then 2 when c.status='New Modifications' then 3  "
			+ "when c.status='Approved' then 4 when c.status='Denied' then 5 end, c.id desc ")	
	int getSearchAndPhysicianFilterTotalByGroup(@Param("groupId") int groupId, @Param("patientName") String patientName, @Param("physicianName") String physicianName,  
			@Param("status") String status);

	
	
	@Query("select c from PatientAccount c, PhysicianAccount p, PatientGroup g,PatientPhysicians pp where pp.physicianId=p.id and pp.patientId=c.id and "
			+ "c.id=g.patientId and g.groupId=:groupId and pp.physicianId=:id and c.status=:status  group by c.id "
			+ "order by case when c.status='Profile Completed' then 1 when c.status='New' then 2 when c.status='New Modifications' then 3 "
			+ "when c.status='Approved' then 4 when c.status='Denied' then 5 end, c.id desc")
	Page<PatientAccount> findByAllByPhysicianAndGroup(@Param("id") int id, @Param("groupId") int groupId, @Param("status") String status, Pageable page);

	@Query("select c from PatientAccount c left join PatientPhysicians pp on pp.patientId=c.id left join PhysicianAccount p on pp.physicianId=p.id "
			+ "left join PatientGroup g on c.id=g.patientId where g.groupId=:groupId and pp.physicianId=:id and (c.patientName like %:patientName% or "
			+ "c.patientName is null) and (p.physicianName like %:physicianName% or p.physicianName  is null) and c.status=:status  and g.delFlag='N' "
			+ "group by c.id order by case when c.status='Profile Completed' then 1 when c.status='New' then 2 when c.status='New Modifications' then 3 "
			+ "when c.status='Approved' then 4 when c.status='Denied' then 5 end, c.id desc")
	Page<PatientAccount> getSearchRecordAndGroup(@Param("id") int id, @Param("groupId") int groupId, @Param("patientName") String patientName, 
			@Param("physicianName") String physicianName,  @Param("status") String status, Pageable page);



	@Query("select c from PatientAccount c left join PatientPhysicians pp on pp.patientId=c.id left join PhysicianAccount p on pp.physicianId=p.id "
			+ "left join PatientGroup g on c.id=g.patientId where g.groupId=:groupId and pp.physicianId=:id and (c.patientName like %:patientName% or "
			+ "c.patientName is null) and p.physicianName like %:physicianName%  and c.status=:status and pp.delFlag='N'  and g.delFlag='N' group by c.id "
			+ "order by case when c.status='Profile Completed' then 1 when c.status='New' then 2 when c.status='New Modifications' then 3 "
			+ "when c.status='Approved' then 4 when c.status='Denied' then 5 end, c.id desc")
	Page<PatientAccount> getSearchAndPhysicianFilterRecordAndGroup(@Param("id") int id, @Param("groupId") int groupId, @Param("patientName") String patientName, 
			@Param("physicianName") String physicianName,  @Param("status") String status, Pageable page);
	
	
	
	@Query("select c from PatientAccount c, PatientGroup g where  "
			+ "c.id=g.patientId and g.groupId=:groupId and c.status=:status  group by c.id "
			+ "order by case when c.status='Profile Completed' then 1 when c.status='New' then 2 when c.status='New Modifications' then 3 "
			+ "when c.status='Approved' then 4 when c.status='Denied' then 5 end, c.id desc")
	Page<PatientAccount> findByAllByStatusAndGroup(@Param("groupId") int groupId, @Param("status") String status, Pageable page);
	
	
	@Query("select c from PatientAccount c left join PatientPhysicians pp on pp.patientId=c.id left join PhysicianAccount p on pp.physicianId=p.id "
			+ "left join PatientGroup g on c.id=g.patientId where g.groupId=:groupId and (c.patientName like %:patientName% or c.patientName is null) and "
			+ "(p.physicianName like %:physicianName% or p.physicianName is null) and c.status=:status  and g.delFlag='N' group by c.id "
			+ "order by case when c.status='New' then 1 when c.status='Profile Completed' then 2 "
			+ "when c.status='New Modifications' then 3  when c.status='Approved' then 4 when c.status='Denied' then 5 end, c.id desc ")	
	Page<PatientAccount> getSearchRecordAndGroup(@Param("groupId") int groupId, @Param("patientName") String patientName, 
			@Param("physicianName") String physicianName,  @Param("status") String status, Pageable page);
	

	@Query("select c from PatientAccount c left join PatientPhysicians pp on pp.patientId=c.id left join PhysicianAccount p on pp.physicianId=p.id "
			+ "left join PatientGroup g on c.id=g.patientId where g.groupId=:groupId and (c.patientName like %:patientName% or c.patientName is null) and "
			+ "p.physicianName like %:physicianName% and c.status=:status and pp.delFlag='N'  and g.delFlag='N' group by c.id "
			+ "order by case when c.status='New' then 1 when c.status='Profile Completed' then 2 "
			+ "when c.status='New Modifications' then 3  when c.status='Approved' then 4 when c.status='Denied' then 5 end, c.id desc ")	
	Page<PatientAccount> getSearchAndPhysicianFilterRecordAndGroup(@Param("groupId") int groupId, @Param("patientName") String patientName, 
			@Param("physicianName") String physicianName,  @Param("status") String status, Pageable page);
	
	
	@Query("select c from PatientAccount c left join PatientPhysicians pp on pp.patientId=c.id left join PhysicianAccount p on pp.physicianId=p.id "
			+ "left join PatientGroup g on c.id=g.patientId where g.groupId=:groupId and pp.physicianId=:id   and g.delFlag='N' group by c.id "
			+ "order by case when c.status='New' then 1 when c.status='Profile Completed' then 2 "
			+ "when c.status='New Modifications' then 3  when c.status='Approved' then 4 when c.status='Denied' then 5 end, c.id desc ")
	Page<PatientAccount> findByAllByPhysicianAndGroup(@Param("id") int id, @Param("groupId") int groupId, Pageable page);

	
	@Query("select c from PatientAccount c left join PatientPhysicians pp on pp.patientId=c.id left join PhysicianAccount p on pp.physicianId=p.id "
			+ "left join PatientGroup g on c.id=g.patientId where g.groupId=:groupId and pp.physicianId=:id and (c.patientName like %:patientName% or "
			+ "c.patientName is null) and (p.physicianName like %:physicianName% or p.physicianName is null)  and g.delFlag='N' group by c.id "
			+ "order by case when c.status='Profile Completed' then 1 when c.status='New' then 2 "
			+ "when c.status='New Modifications' then 3 when c.status='Approved' then 4 when c.status='Denied' then 5 end, c.id desc ")
	Page<PatientAccount> getSearchRecordAndGroup(@Param("id") int id, @Param("groupId") int groupId, @Param("patientName") String patientName, @Param("physicianName") String physicianName, Pageable page);
	

	@Query("select c from PatientAccount c left join PatientPhysicians pp on pp.patientId=c.id left join PhysicianAccount p on pp.physicianId=p.id "
			+ "left join PatientGroup g on c.id=g.patientId where g.groupId=:groupId and pp.physicianId=:id and (c.patientName like %:patientName% or "
			+ "c.patientName is null) and p.physicianName like %:physicianName% and pp.delFlag='N'  and g.delFlag='N' group by c.id "
			+ "order by case when c.status='Profile Completed' then 1 when c.status='New' then 2 "
			+ "when c.status='New Modifications' then 3 when c.status='Approved' then 4 when c.status='Denied' then 5 end, c.id desc ")
	Page<PatientAccount> getSearchAndPhysicianFilterRecordAndGroup(@Param("id") int id, @Param("groupId") int groupId, @Param("patientName") String patientName, @Param("physicianName") String physicianName, Pageable page);

	
	
	@Query("select c from PatientAccount c left join PatientGroup g on c.id=g.patientId "
			+ "where g.groupId=:groupId  and g.delFlag='N' group by c.id order by case when c.status='Profile Completed' then 1 "
			+ "when c.status='New' then 2 when c.status='New Modifications' then 3 when c.status='Approved' then 4 when c.status='Denied' then 5 end, "
			+ "c.id desc ")
	Page<PatientAccount> findByAllByStatusAndGroup(@Param("groupId") int groupId, Pageable page);
	
	@Query("select  c  from PatientAccount c left join PatientPhysicians pp on pp.patientId=c.id left join PhysicianAccount p on pp.physicianId=p.id "
			+ "left join PatientGroup g on c.id=g.patientId where g.groupId=:groupId and (c.patientName like %:patientName% or c.patientName is null) and "
			+ "(p.physicianName like %:physicianName% or p.physicianName is null)  and g.delFlag='N' group by c.id "
			+ "order by case when c.status='Profile Completed' then 1 when c.status='New' then 2 "
			+ "when c.status='New Modifications' then 3 when c.status='Approved' then 4 when c.status='Denied' then 5 end, c.id desc ")	
	Page<PatientAccount> getSearchRecordAndGroup(@Param("groupId") int groupId, @Param("patientName") String patientName, 
			@Param("physicianName") String physicianName,  Pageable page);

	
	@Query("select  c  from PatientAccount c left join PatientPhysicians pp on pp.patientId=c.id left join PhysicianAccount p on pp.physicianId=p.id "
			+ "left join PatientGroup g on c.id=g.patientId where g.groupId=:groupId and (c.patientName like %:patientName% or c.patientName is null) and "
			+ "p.physicianName like %:physicianName% and pp.delFlag='N'  and g.delFlag='N' group by c.id "
			+ "order by case when c.status='Profile Completed' then 1 when c.status='New' then 2 "
			+ "when c.status='New Modifications' then 3 when c.status='Approved' then 4 when c.status='Denied' then 5 end, c.id desc ")	
	Page<PatientAccount> getSearchAndPhysicianFilterRecordAndGroup(@Param("groupId") int groupId, @Param("patientName") String patientName, 
			@Param("physicianName") String physicianName,  Pageable page);
	
	
	
	@Query("select  COALESCE(count(c),0)  from PatientAccount c left join PatientPhysicians pp on pp.patientId=c.id left join PhysicianAccount p "
			+ "on  pp.physicianId=p.id left join PatientGroup g on c.id=g.patientId where g.groupId=:groupId and pp.physicianId=:id  and g.delFlag='N' group by c.id "
			+ "order by case when c.status='Profile Completed' then 1 when c.status='New' then 2 "
			+ "when c.status='New Modifications' then 3 when c.status='Approved' then 4 when c.status='Denied' then 5 end, c.id desc ")
	int findByAllByPhysicianAndGroup(@Param("id") int id, @Param("groupId") int groupId);

	@Query("select  COALESCE(count(c),0)  from PatientAccount c left join PatientPhysicians pp on pp.patientId=c.id left join PhysicianAccount p "
			+ "on pp.physicianId=p.id left join PatientGroup g on c.id=g.patientId where g.groupId=:groupId and pp.physicianId=:id and "
			+ "(c.patientName like %:patientName% or c.patientName is null) and (p.physicianName like %:physicianName% or p.physicianName is null)  and g.delFlag='N' "
			+ "group by c.id order by case when c.status='New' then 1 when c.status='Profile Completed' then 2 "
			+ "when c.status='New Modifications' then 3  when c.status='Approved' then 4 when c.status='Denied' then 5 end, c.id desc ")
	int getSearchTotalByGroup(@Param("id") int id, @Param("groupId") int groupId, @Param("patientName") String patientName, 
			@Param("physicianName") String physicianName);

	
	@Query("select  COALESCE(count(c),0)  from PatientAccount c left join PatientPhysicians pp on pp.patientId=c.id left join PhysicianAccount p "
			+ "on pp.physicianId=p.id left join PatientGroup g on c.id=g.patientId where g.groupId=:groupId and pp.physicianId=:id and "
			+ "(c.patientName like %:patientName% or c.patientName is null) and p.physicianName like %:physicianName%  and g.delFlag='N' "
			+ "group by c.id order by case when c.status='New' then 1 when c.status='Profile Completed' then 2 "
			+ "when c.status='New Modifications' then 3  when c.status='Approved' then 4 when c.status='Denied' then 5 end, c.id desc ")
	int getSearchAndPhysicianFilterTotalByGroup(@Param("id") int id, @Param("groupId") int groupId, @Param("patientName") String patientName, 
			@Param("physicianName") String physicianName);
	
	
	@Query("select  COALESCE(count(c),0)  from PatientAccount c left join PatientGroup g on c.id=g.patientId "
			+ "where g.groupId=:groupId and g.delFlag='N' group by c.id order by case when c.status='New' then 1 when c.status='Profile Completed' "
			+ "then 2 when c.status='New Modifications' then 3  when c.status='Approved' then 4 when c.status='Denied' then 5 end, c.id desc ")
	int findByAllByStatusAndGroup(@Param("groupId") int groupId);

	@Query("select  COALESCE(count(c),0)  from PatientAccount c left join PatientPhysicians pp on pp.patientId=c.id left join PhysicianAccount p "
			+ "on pp.physicianId=p.id left join PatientGroup g on c.id=g.patientId where g.groupId=:groupId and (c.patientName like %:patientName% or "
			+ "c.patientName is null) and (p.physicianName like %:physicianName% or p.physicianName is null)  and g.delFlag='N' group by c.id "
			+ "order by case when c.status='New' then 1 when c.status='Profile Completed' then 2 "
			+ "when c.status='New Modifications' then 3  when c.status='Approved' then 4 when c.status='Denied' then 5 end, c.id desc ")	
	int getSearchTotalByGroup(@Param("groupId") int groupId, @Param("patientName") String patientName, @Param("physicianName") String physicianName);
	
	
	@Query("select  COALESCE(count(c),0)  from PatientAccount c left join PatientPhysicians pp on pp.patientId=c.id left join PhysicianAccount p "
			+ "on pp.physicianId=p.id left join PatientGroup g on c.id=g.patientId where g.groupId=:groupId and (c.patientName like %:patientName% or "
			+ "c.patientName is null) and p.physicianName like %:physicianName%  and g.delFlag='N' group by c.id "
			+ "order by case when c.status='New' then 1 when c.status='Profile Completed' then 2 "
			+ "when c.status='New Modifications' then 3  when c.status='Approved' then 4 when c.status='Denied' then 5 end, c.id desc ")	
	int getSearchAndPhysicianFilterTotalByGroup(@Param("groupId") int groupId, @Param("patientName") String patientName, @Param("physicianName") String physicianName);

	
	
	@Query("select c from PatientAccount c where pioneerResponse=-1 and status='Approved'")
	List<PatientAccount> fetchAllNotUpdatedPioneerPatient();

	/****  Summary page queries end  ***********/
	
	// Autocomplete to get list
	@Query("select c from PatientAccount c where patientName like %:patientName% and (status=:status or id=:id)")
	List<PatientAccount> getAutoCompletePatientList( @Param("patientName") String patientName, @Param("status") String status,@Param("id") int id, Pageable pageable);

	// Autocomplete to get list
	@Query("select c from PatientAccount c where patientName like %:patientName%")
	List<PatientAccount> getAllAutoCompletePatientList( @Param("patientName") String patientName, Pageable pageable);
	
	@Query("select c from PatientAccount c,PatientPhysicians pp where pp.physicianId=:physicianId and pp.patientId=c.id and "
			+ "c.patientName like %:patientName% and (status=:status or id=:id)  group by c.id ")
	List<PatientAccount> getAutoCompletePatientListByPhysicianId( @Param("physicianId") int physicianId, @Param("patientName") String patientName, 
			@Param("status") String status,@Param("id") int id, Pageable pageable);
	
	@Query("select c from PatientAccount c,PatientPhysicians pp where pp.physicianId=:physicianId and pp.patientId=c.id and "
			+ "c.patientName like %:patientName% or id=:id group by c.id ")
	List<PatientAccount> getAutoCompleteAllPatientListByPhysicianId( @Param("physicianId") int physicianId, @Param("patientName") String patientName, 
			@Param("id") int id, Pageable pageable);
	
	
	@Query("select c from PatientAccount c,PatientPhysicians pp where pp.physicianId=:physicianId and pp.patientId=c.id and "
			+ "c.patientName like %:patientName% group by c.id ")
	List<PatientAccount> getAutoCompleteAllPatientListByPhysicianId( @Param("physicianId") int physicianId, @Param("patientName") String patientName, 
			Pageable pageable);
	
	
	

	@Query("select c from PatientAccount c,PatientPhysicians pp where pp.physicianId=:physicianId and pp.patientId=c.id and "
			+ "c.patientName like %:patientName% and status=:status  group by c.id ")
	List<PatientAccount> getAutoCompletePatientListByPhysicianId( @Param("physicianId") int physicianId, @Param("patientName") String patientName, 
			@Param("status") String status, Pageable pageable);
	
	@Query("select c from PatientAccount c,PatientPhysicians pp where pp.physicianId=:physicianId and pp.patientId=c.id and "
			+ "c.patientName like %:patientName% and ((status<>'New' and status<>'Denied') or id=:id)  group by c.id ")
	List<PatientAccount> getAutoCompletePrescriptionPatientListByPhysicianId( @Param("physicianId") int physicianId, 
			@Param("patientName") String patientName, @Param("id") int id, Pageable pageable);

	
	@Query("select c from PatientAccount c,PatientPhysicians pp where pp.physicianId=:physicianId and pp.patientId=c.id and "
			+ "c.patientName like %:patientName% and (status<>'New' and status<>'Denied') group by c.id ")
	List<PatientAccount> getAutoCompletePrescriptionPatientListByPhysicianId( @Param("physicianId") int physicianId, @Param("patientName") String patientName,
			Pageable pageable);
	
	
	@Query("select  COALESCE(count(c),0)  from PatientAccount c,PatientPhysicians pp where c.status='Approved' and pp.physicianId=:physicianId and "
			+ "pp.patientId=c.id and c.dateRegistered>:fromDate and c.dateRegistered<=:toDate ")
	int fetchNewPatientCountByStatusandDateRegistered( @Param("physicianId") int physicianId, @Param("fromDate") Date fromDate, @Param("toDate") Date toDate);
	
	
	@Query("select  COALESCE(count(c),0)  from PatientAccount c,PatientPhysicians pp where c.status='Approved' and pp.physicianId=:physicianId and "
			+ "pp.patientId=c.id and c.dateRegistered>:fromDate and c.dateRegistered<=:toDate ")
	int fetchNewPatientWeekCountByStatusandDateRegistered( @Param("physicianId") int physicianId, @Param("fromDate") Date fromDate, 
			@Param("toDate") Date toDate);
	
	
	@Query("select  COALESCE(count(c),0)  from PatientAccount c,PatientPhysicians pp where c.status='Approved' and pp.physicianId=:physicianId and "
			+ "pp.patientId=c.id and c.dateRegistered>:fromDate and c.dateRegistered<=:toDate ")
	int fetchNewPatientMonthCountByStatusandDateRegistered( @Param("physicianId") int physicianId, @Param("fromDate") Date fromDate, 
			@Param("toDate") Date toDate);
	
	
	
	@Query("select  COALESCE(count(c),0)  from PatientAccount c,PatientGroup g where c.status='Approved' and "
			+ "c.id=g.patientId and g.groupId=:groupId  and c.dateRegistered>:fromDate and c.dateRegistered<=:toDate ")
	int fetchGroupNewPatientCountByStatusandDateRegistered( @Param("groupId") int groupId, @Param("fromDate") Date fromDate, @Param("toDate") Date toDate);
	
	@Query("select  COALESCE(count(c),0)  from PatientAccount c,PatientGroup g where c.status='Approved' and "
			+ "c.id=g.patientId and g.groupId=:groupId  and c.dateRegistered>:fromDate and c.dateRegistered<=:toDate) ")
	int fetchGroupNewPatientWeekCountByStatusandDateRegistered( @Param("groupId") int groupId, @Param("fromDate") Date fromDate, @Param("toDate") Date toDate);
	
	@Query("select  COALESCE(count(c),0)  from PatientAccount c,PatientGroup g where c.status='Approved' and "
			+ "c.id=g.patientId and g.groupId=:groupId  and c.dateRegistered>:fromDate and c.dateRegistered<=:toDate ")
	int fetchGroupNewPatientMonthCountByStatusandDateRegistered( @Param("groupId") int groupId, @Param("fromDate") Date fromDate, 
			@Param("toDate") Date toDate);


	@Query("select  c  from PatientAccount c,PatientGroup g where c.status='Approved' and "
			+ "c.id=g.patientId and g.groupId=:groupId and c.patientName like %:patientName% and (c.status=:status or c.id=:id) ")
	List<PatientAccount> getAutoCompletePatientListByGroupId( @Param("patientName") String patientName, @Param("status") String status,@Param("id") int id,
			@Param("groupId") int groupId, Pageable pageable);


	
	@Query("select  c  from PatientAccount c,PatientGroup g  where "
			+ "c.id=g.patientId and g.groupId=:groupId and c.patientName like %:patientName% or c.id=:id ")
	List<PatientAccount> getAutoCompletePatientListByGroupId( @Param("patientName") String patientName, @Param("id") int id,@Param("groupId") int groupId, 
			Pageable pageable);

	
	
	@Query("select  c  from PatientAccount c,PatientGroup g where c.status='Approved' and "
			+ "c.id=g.patientId and g.groupId=:groupId and c.patientName like %:patientName% and c.status=:status ")
	List<PatientAccount> getAutoCompletePatientListByGroupId( @Param("patientName") String patientName, @Param("status") String status, 
			@Param("groupId") int groupId, Pageable pageable);


	// pick Pioneer ID from another record for same SSN query
	@Query("select pat from PatientAccount pat where pat.ssn=:ssn and pat.pioneerUid is not null and pat.pioneerUid != '' ")
	List<PatientAccount> getPioneerIdBySSN(@Param("ssn") String ssn);
	
	/******************  Reports Queries *********************/
	/*
	select pa.patient_name, gm.group_name, c.clinic_name, phy.physician_name, pa.mobile, pa.email, pa.city, pa.state, pa.Date_registered, pa.status
	from patient_profile pa, phy_info phy, clinic c, phy_group phgp, group_master gm
	where pa.physician_id = phy.physician_id and phy.clinic_id = c.clinic_id and phy.physician_id = phgp.physician_id and phgp.group_id = gm.group_id; */
	
	// COALESCE(gm.groupName, '') like %:group% 
	/*
	select pa.patient_id, pa.patient_name, gm.group_name, phy.physician_name, pa.mobile, pa.email, 
	pa.city, pa.state, pa.Date_registered, pa.status , pa.SSN, pa.date_of_birth, pa.gender
	from patient_profile pa left join patient_physicians paph on pa.patient_id = paph.patient_id left join phy_info phy on paph.physician_id = phy.physician_id 
	left join phy_group phgp on phy.physician_id = phgp.physician_id left join group_master gm  on phgp.group_id = gm.group_id group by pa.patient_id;
	*/
	
	
	@Query("select pa.patientName from PatientAccount pa left join PatientPhysicians paph on paph.patientId=pa.id left join PhysicianAccount phy "
			+ "on paph.physicianId = phy.id left join PhysicianGroup phgp on phy.id = phgp.physicianId left join GroupMaster gm on phgp.groupId = gm.id "
			+ "where  COALESCE(phy.physicianName, '') like %:physician%  and COALESCE(gm.groupName, '') like %:group% and pa.status=:status  group by pa.id ")
	List<PatientAccount> reportCountByFilter(@Param("physician") String physician, @Param("group") String group, @Param("status") String status);

	
	@Query("select pa.patientName  from PatientAccount pa left join PatientPhysicians paph on paph.patientId=pa.id left join PhysicianAccount phy "
			+ "on paph.physicianId = phy.id left join PhysicianGroup phgp on phy.id = phgp.physicianId left join GroupMaster gm on phgp.groupId = gm.id "
			+ "where COALESCE(phy.physicianName, '') like %:physician%  and COALESCE(gm.groupName, '') like %:group% and pa.status=:status and "
			+ "pa.dateRegistered>=:fromDate  group by pa.id ")
	List<PatientAccount> reportCountByFilterWithFromDate(@Param("physician") String physician, @Param("group") String group, @Param("status") String status, 
			@Param("fromDate") Date fromDate);

	@Query("select pa.patientName from PatientAccount pa left join PatientPhysicians paph on paph.patientId=pa.id left join PhysicianAccount phy "
			+ "on paph.physicianId = phy.id left join PhysicianGroup phgp on phy.id = phgp.physicianId left join GroupMaster gm on phgp.groupId = gm.id "
			+ "where COALESCE(phy.physicianName, '') like %:physician%  and COALESCE(gm.groupName, '') like %:group% and pa.status=:status and "
			+ "pa.dateRegistered<=:toDate  group by pa.id ")
	List<PatientAccount> reportCountByFilterWithToDate(@Param("physician") String physician, @Param("group") String group, 
			@Param("status") String status, @Param("toDate") Date toDate);

	@Query("select pa.patientName  from PatientAccount pa left join PatientPhysicians paph on paph.patientId=pa.id left join PhysicianAccount phy "
			+ "on paph.physicianId = phy.id left join PhysicianGroup phgp on phy.id = phgp.physicianId left join GroupMaster gm on phgp.groupId = gm.id "
			+ "where COALESCE(phy.physicianName, '') like %:physician%  and COALESCE(gm.groupName, '') like %:group% and pa.status=:status and "
			+ "pa.dateRegistered>=:fromDate and pa.dateRegistered<=:toDate  group by pa.id ")
	List<PatientAccount> reportCountByFilterWithDate(@Param("physician") String physician, @Param("group") String group, 
			@Param("status") String status, @Param("fromDate") Date fromDate, @Param("toDate") Date toDate);
	
	
	
	@Query("select pa.patientName from PatientAccount pa left join PatientPhysicians paph on paph.patientId=pa.id left join PhysicianAccount phy "
			+ "on paph.physicianId = phy.id left join PhysicianGroup phgp on phy.id = phgp.physicianId left join GroupMaster gm on phgp.groupId = gm.id "
			+ "where COALESCE(phy.physicianName, '') like %:physician%  and COALESCE(gm.groupName, '') like %:group%  group by pa.id ")
	List<PatientAccount> reportCountByFilter(@Param("physician") String physician, @Param("group") String group);
	
	
	@Query("select pa.patientName from PatientAccount pa left join PatientPhysicians paph on paph.patientId=pa.id left join PhysicianAccount phy "
			+ "on paph.physicianId = phy.id left join PhysicianGroup phgp on phy.id = phgp.physicianId left join GroupMaster gm on phgp.groupId = gm.id "
			+ "where COALESCE(phy.physicianName, '') like %:physician%  and COALESCE(gm.groupName, '') like %:group% and "
			+ "pa.dateRegistered>=:fromDate  group by pa.id")
	List<PatientAccount> reportCountByFilterWithFromDate(@Param("physician") String physician, @Param("group") String group, 
			@Param("fromDate") Date fromDate);

	@Query("select pa.patientName from PatientAccount pa left join PatientPhysicians paph on paph.patientId=pa.id left join PhysicianAccount phy "
			+ "on paph.physicianId = phy.id left join PhysicianGroup phgp on phy.id = phgp.physicianId left join GroupMaster gm on phgp.groupId = gm.id "
			+ "where COALESCE(phy.physicianName, '') like %:physician%  and COALESCE(gm.groupName, '') like %:group% and "
			+ "pa.dateRegistered<=:toDate  group by pa.id")
	List<PatientAccount> reportCountByFilterWithToDate(@Param("physician") String physician, @Param("group") String group, 
			@Param("toDate") Date toDate);

	@Query("select pa.patientName from PatientAccount pa left join PatientPhysicians paph on paph.patientId=pa.id left join PhysicianAccount phy "
			+ "on paph.physicianId = phy.id left join PhysicianGroup phgp on phy.id = phgp.physicianId left join GroupMaster gm on phgp.groupId = gm.id "
			+ "where COALESCE(phy.physicianName, '') like %:physician%  and COALESCE(gm.groupName, '') like %:group% and "
			+ "pa.dateRegistered>=:fromDate and pa.dateRegistered<=:toDate  group by pa.id")
	List<PatientAccount> reportCountByFilterWithDate(@Param("physician") String physician, @Param("group") String group, 
			@Param("fromDate") Date fromDate, @Param("toDate") Date toDate);
	
	
	
	
	
	
	
	@Query("select pa.patientName,gm.groupName,phy.physicianName,pa.mobile,pa.email,pa.city,pa.state,pa.dateRegistered,pa.status,pa.ssn,pa.dateOfBirth,"
			+ "pa.gender,pa.id,gm.id,pa.address,pa.zipCode,pa.phone,pa.driversLicense,pa.alternateId,pa.alternateIdTypeText "
			+ "from PatientAccount pa left join PatientPhysicians paph on paph.patientId=pa.id left join PhysicianAccount phy "
			+ "on paph.physicianId = phy.id left join PhysicianGroup phgp on phy.id = phgp.physicianId left join GroupMaster gm on phgp.groupId = gm.id "
			+ "where  COALESCE(phy.physicianName, '') like %:physician%  and COALESCE(gm.groupName, '') like %:group% and pa.status=:status group by pa.id "
			+ "order by case when pa.status='Profile Completed' then 1 when pa.status='New' then 2 when pa.status='New Modifications' then 3 "
			+ "when pa.status='Approved' then 4 when pa.status='Denied' then 5 end, pa.id desc ")
	Page<Object[]> reportByFilter(@Param("physician") String physician, @Param("group") String group, @Param("status") String status, Pageable page);
	
	
	@Query("select pa.patientName,gm.groupName,phy.physicianName,pa.mobile,pa.email,pa.city,pa.state,pa.dateRegistered,pa.status,pa.ssn,pa.dateOfBirth,"
			+ "pa.gender,pa.id,gm.id,pa.address,pa.zipCode,pa.phone,pa.driversLicense,pa.alternateId,pa.alternateIdTypeText "
			+ "from PatientAccount pa left join PatientPhysicians paph on paph.patientId=pa.id left join PhysicianAccount phy "
			+ "on paph.physicianId = phy.id left join PhysicianGroup phgp on phy.id = phgp.physicianId left join GroupMaster gm on phgp.groupId = gm.id "
			+ "where COALESCE(phy.physicianName, '') like %:physician%  and COALESCE(gm.groupName, '') like %:group% and pa.status=:status and "
			+ "pa.dateRegistered>=:fromDate group by pa.id "
			+ "order by case when pa.status='Profile Completed' then 1 when pa.status='New' then 2 when pa.status='New Modifications' then 3 "
			+ "when pa.status='Approved' then 4 when pa.status='Denied' then 5 end, pa.id desc  ")
	Page<Object[]> reportByFilterWithFromDate(@Param("physician") String physician, @Param("group") String group, @Param("status") String status, 
			@Param("fromDate") Date fromDate, Pageable page);

	@Query("select pa.patientName,gm.groupName,phy.physicianName,pa.mobile,pa.email,pa.city,pa.state,pa.dateRegistered,pa.status,pa.ssn,pa.dateOfBirth,"
			+ "pa.gender,pa.id,gm.id,pa.address,pa.zipCode,pa.phone,pa.driversLicense,pa.alternateId,pa.alternateIdTypeText "
			+ "from PatientAccount pa left join PatientPhysicians paph on paph.patientId=pa.id left join PhysicianAccount phy "
			+ "on paph.physicianId = phy.id left join PhysicianGroup phgp on phy.id = phgp.physicianId left join GroupMaster gm on phgp.groupId = gm.id "
			+ "where COALESCE(phy.physicianName, '') like %:physician%  and COALESCE(gm.groupName, '') like %:group% and pa.status=:status and "
			+ "pa.dateRegistered<=:toDate  group by pa.id "
			+ "order by case when pa.status='Profile Completed' then 1 when pa.status='New' then 2 when pa.status='New Modifications' then 3 "
			+ "when pa.status='Approved' then 4 when pa.status='Denied' then 5 end, pa.id desc ")
	Page<Object[]> reportByFilterWithToDate(@Param("physician") String physician, @Param("group") String group, 
			@Param("status") String status, @Param("toDate") Date toDate, Pageable page);

	@Query("select pa.patientName,gm.groupName,phy.physicianName,pa.mobile,pa.email,pa.city,pa.state,pa.dateRegistered,pa.status,pa.ssn,pa.dateOfBirth,"
			+ "pa.gender,pa.id,gm.id,pa.address,pa.zipCode,pa.phone,pa.driversLicense,pa.alternateId,pa.alternateIdTypeText "
			+ "from PatientAccount pa left join PatientPhysicians paph on paph.patientId=pa.id left join PhysicianAccount phy "
			+ "on paph.physicianId = phy.id left join PhysicianGroup phgp on phy.id = phgp.physicianId left join GroupMaster gm on phgp.groupId = gm.id "
			+ "where COALESCE(phy.physicianName, '') like %:physician%  and COALESCE(gm.groupName, '') like %:group% and pa.status=:status and "
			+ "pa.dateRegistered>=:fromDate and pa.dateRegistered<=:toDate  group by pa.id "
			+ "order by case when pa.status='Profile Completed' then 1 when pa.status='New' then 2 when pa.status='New Modifications' then 3 "
			+ "when pa.status='Approved' then 4 when pa.status='Denied' then 5 end, pa.id desc ")
	Page<Object[]> reportByFilterWithDate(@Param("physician") String physician, @Param("group") String group, 
			@Param("status") String status, @Param("fromDate") Date fromDate, @Param("toDate") Date toDate, Pageable page);
	
	

	@Query("select pa.patientName,gm.groupName,phy.physicianName,pa.mobile,pa.email,pa.city,pa.state,pa.dateRegistered,pa.status,pa.ssn,pa.dateOfBirth,"
			+ "pa.gender,pa.id,gm.id,pa.address,pa.zipCode,pa.phone,pa.driversLicense,pa.alternateId,pa.alternateIdTypeText "
			+ "from PatientAccount pa left join PatientPhysicians paph on paph.patientId=pa.id left join PhysicianAccount phy "
			+ "on paph.physicianId = phy.id left join PhysicianGroup phgp on phy.id = phgp.physicianId left join GroupMaster gm on phgp.groupId = gm.id "
			+ "where  COALESCE(phy.physicianName, '') like %:physician%  and COALESCE(gm.groupName, '') like %:group% group by pa.id "
			+ "order by case when pa.status='Profile Completed' then 1 when pa.status='New' then 2 when pa.status='New Modifications' then 3 "
			+ "when pa.status='Approved' then 4 when pa.status='Denied' then 5 end, pa.id desc ")
	Page<Object[]> reportByFilter(@Param("physician") String physician, @Param("group") String group,  Pageable page);

	
	@Query("select  pa.patientName,gm.groupName,phy.physicianName,pa.mobile,pa.email,pa.city,pa.state,pa.dateRegistered,pa.status,pa.ssn,pa.dateOfBirth,"
			+ "pa.gender,pa.id,gm.id,pa.address,pa.zipCode,pa.phone,pa.driversLicense,pa.alternateId,pa.alternateIdTypeText "
			+ "from PatientAccount pa left join PatientPhysicians paph on paph.patientId=pa.id left join PhysicianAccount phy "
			+ "on paph.physicianId = phy.id left join PhysicianGroup phgp on phy.id = phgp.physicianId left join GroupMaster gm on phgp.groupId = gm.id "
			+ "where COALESCE(phy.physicianName, '') like %:physician%  and COALESCE(gm.groupName, '') like %:group% and "
			+ "pa.dateRegistered>=:fromDate group by pa.id "
			+ "order by case when pa.status='Profile Completed' then 1 when pa.status='New' then 2 when pa.status='New Modifications' then 3 "
			+ "when pa.status='Approved' then 4 when pa.status='Denied' then 5 end, pa.id desc")
	Page<Object[]> reportByFilterWithFromDate(@Param("physician") String physician, @Param("group") String group, 
			@Param("fromDate") Date fromDate,  Pageable page);

	@Query("select  pa.patientName,gm.groupName,phy.physicianName,pa.mobile,pa.email,pa.city,pa.state,pa.dateRegistered,pa.status,pa.ssn,pa.dateOfBirth,"
			+ "pa.gender,pa.id,gm.id,pa.address,pa.zipCode,pa.phone,pa.driversLicense,pa.alternateId,pa.alternateIdTypeText "
			+ "from PatientAccount pa left join PatientPhysicians paph on paph.patientId=pa.id left join PhysicianAccount phy "
			+ "on paph.physicianId = phy.id left join PhysicianGroup phgp on phy.id = phgp.physicianId left join GroupMaster gm on phgp.groupId = gm.id "
			+ "where COALESCE(phy.physicianName, '') like %:physician%  and COALESCE(gm.groupName, '') like %:group% and "
			+ "pa.dateRegistered<=:toDate group by pa.id "
			+ "order by case when pa.status='Profile Completed' then 1 when pa.status='New' then 2 when pa.status='New Modifications' then 3 "
			+ "when pa.status='Approved' then 4 when pa.status='Denied' then 5 end, pa.id desc")
	Page<Object[]> reportByFilterWithToDate(@Param("physician") String physician, @Param("group") String group, 
			@Param("toDate") Date toDate,  Pageable page);

	@Query("select  pa.patientName,gm.groupName,phy.physicianName,pa.mobile,pa.email,pa.city,pa.state,pa.dateRegistered,pa.status,pa.ssn,pa.dateOfBirth,"
			+ "pa.gender,pa.id,gm.id,pa.address,pa.zipCode,pa.phone,pa.driversLicense,pa.alternateId,pa.alternateIdTypeText "
			+ "from PatientAccount pa left join PatientPhysicians paph on paph.patientId=pa.id left join PhysicianAccount phy "
			+ "on paph.physicianId = phy.id left join PhysicianGroup phgp on phy.id = phgp.physicianId left join GroupMaster gm on phgp.groupId = gm.id "
			+ "where COALESCE(phy.physicianName, '') like %:physician%  and COALESCE(gm.groupName, '') like %:group% and "
			+ "pa.dateRegistered>=:fromDate and pa.dateRegistered<=:toDate group by pa.id "
			+ "order by case when pa.status='Profile Completed' then 1 when pa.status='New' then 2 when pa.status='New Modifications' then 3 "
			+ "when pa.status='Approved' then 4 when pa.status='Denied' then 5 end, pa.id desc")
	Page<Object[]> reportByFilterWithDate(@Param("physician") String physician, @Param("group") String group, 
			@Param("fromDate") Date fromDate, @Param("toDate") Date toDate,  Pageable page);
	
	
}
