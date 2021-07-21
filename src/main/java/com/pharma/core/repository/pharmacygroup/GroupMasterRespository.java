package com.pharma.core.repository.pharmacygroup;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pharma.core.model.pharmacygroup.GroupMaster;
/**
 * This class is a Java interface that defines a data access contract
 *
 */
@Repository("groupMaster")
public interface GroupMasterRespository extends JpaRepository<GroupMaster, Integer> {

	@Query("select  COALESCE(count(c),0)  from GroupMaster c where status=:status order by case when c.status='Active' then 1 when c.status='Inactive' then 2 end,c.id desc")
	int findByStatus(@Param("status") String status);
	
	@Query("select c from GroupMaster c where status=:status order by case when c.status='Active' then 1 when c.status='Inactive' then 2 end,c.id desc")
	Page<GroupMaster> findByStatus(@Param("status") String status, Pageable page);
	
	@Query("select  COALESCE(count(c),0)  from GroupMaster c order by case when c.status='Active' then 1 when c.status='Inactive' then 2 end,c.id desc")
	int findByAllStatus();
	
	@Query("select c from GroupMaster c order by case when c.status='Active' then 1 when c.status='Inactive' then 2 end,c.id desc")
	Page<GroupMaster> findByAllStatus(Pageable page);
	
	
	@Query("select c from GroupMaster c where status=:status")
	List<GroupMaster> findByGroupStatus(@Param("status") String status);
	
	@Query("select c from GroupMaster c where status=:status or id=:groupId")
	List<GroupMaster> findByGroupStatus(@Param("status") String status,@Param("groupId") int groupId);
	
	@Query("select c from GroupMaster c where c.status=:status and c.id<>:groupId and newgroup<>'Y'")
	List<GroupMaster> findByOtherGroupStatus(@Param("status") String status,@Param("groupId") int groupId);
	
	@Query("select  COALESCE(count(c),0)  from GroupMaster c where groupName like %:groupName% order by case when c.status='Active' then 1 when c.status='Inactive' then 2 end,c.id desc" )
	int findByPharmacyGroupName(@Param("groupName") String groupName);
	
	@Query("select c from GroupMaster c where groupName like %:groupName% order by case when c.status='Active' then 1 when c.status='Inactive' then 2 end,c.id desc")
	Page<GroupMaster> findByPharmacyGroupName(@Param("groupName") String groupName, Pageable page);
	
	@Query("select c from GroupMaster c order by case when c.status='Active' then 1 when c.status='Inactive' then 2 end,c.id desc ")
	List<GroupMaster> findByAll();

	@Query("select  COALESCE(count(c),0)  from GroupMaster c where groupName like %:groupName% and status=:status")
	int findByGroupNameAndStatus(@Param("groupName") String groupName,@Param("status") String status);
	
	@Query("select c from GroupMaster c where groupName like %:groupName% and status=:status order by c.id desc")
	Page<GroupMaster> findByGroupNameAndStatus(@Param("groupName") String groupName,@Param("status") String status, Pageable page);
	
	@Query("select c from GroupMaster c where defaultgroup='Y'")
	List<GroupMaster> findByDefaultGroup();
	
	@Query("select c from GroupMaster c where newgroup='Y'")
	List<GroupMaster> findByNewGroup();
	
	@Query("select c from GroupMaster c where status=:status and newgroup<>'Y'")
	List<GroupMaster> getAllGroupList(@Param("status") String status);
	
	GroupMaster findByEmailAndIdNot(String email, int id);
	
	GroupMaster findByEmail(String email);
	
	GroupMaster findByGroupNameAndIdNot(String groupName, int id);
	
	GroupMaster findByGroupName(String groupName);
	
	@Query("select  COALESCE(count(c),0)  from GroupMaster c")
	int getAllRecords();

	// order by case when status='Active' then 1 when status='Inactive' then 2 end
	@Query("select c from GroupMaster c order by case when status='Active' then 1 when status='Inactive' then 2 end, c.id desc")
	Page<GroupMaster> getAllRecords(Pageable page);
	
	@Query("SELECT g FROM GroupMaster g WHERE  g.status='Active' and g.id NOT IN (SELECT pa.groupId FROM PatientGroup pa where  pa.patientId=:patientId and pa.delFlag='N')")
	List<GroupMaster> getAllPatientGroupWiseListNotSelected(@Param("patientId") int patientId);
	
	@Query("SELECT g FROM GroupMaster g WHERE g.id IN (SELECT pa.groupId FROM PatientGroup pa where  pa.patientId=:patientId and pa.delFlag='N')")
	List<GroupMaster> getAllPatientGroupWiseListSelected(@Param("patientId") int patientId);

	
	@Query("SELECT g FROM GroupMaster g WHERE g.id IN (SELECT pa.groupId FROM PatientGroup pa where  pa.patientId=:patientId and pa.groupId=:groupId and pa.delFlag='N')")
	List<GroupMaster> getAllPatientGroupWiseListSelected(@Param("patientId") int patientId, @Param("groupId") int groupId);
	
	
	@Modifying
	@Transactional(readOnly=false)
    @Query("UPDATE PhysicianGroup c SET c.groupId = ?1,c.updatedBy=?2, c.updatedDate=?3,c.updatedUserType=?4 WHERE c.groupId = ?5")
    int reAssignPhysicianGroup(int otherGroupId,int updatedBy,Timestamp updatedDate,String updatedUser, int groupId);
	
	@Modifying
	@Transactional(readOnly=false)
    @Query("UPDATE PatientGroup c SET c.groupId = ?1,c.updatedBy=?2, c.updatedDate=?3,c.updatedUserType=?4 WHERE c.groupId = ?5")
    int reAssignPatientGroup(int otherGroupId,int updatedBy,Timestamp updatedDate,String updatedUser, int groupId);
	
	@Modifying
	@Transactional(readOnly=false)
    @Query("UPDATE PhysicianAssistantAccount c SET c.groupId = ?1,c.lastUpdatedBy=?2, c.lastUpdatedDate=?3,c.lastUpdatedUser=?4 WHERE c.groupId = ?5")
    int reAssignPhysicianAssistantGroup(int otherGroupId,int updatedBy,Timestamp updatedDate,String updatedUser, int groupId);
	
	@Modifying
	@Transactional(readOnly=false)
    @Query("UPDATE GroupDirector c SET c.status = ?1, c.lastUpdatedBy=?2,c.lastUpdatedUser=?3,  c.lastUpdatedDate=?4 WHERE c.groupId = ?5")
    int deActivateGroupDirector(String status,int updatedBy,String updatedUser,Timestamp updatedDate,int groupId);
	
	@Modifying
	@Transactional(readOnly=false)
    @Query("UPDATE PhysicianAccount c SET c.status = ?1, c.lastUpdatedBy=?2, c.lastUpdatedUser=?3, c.lastUpdatedDate=?4, c.deniedBy=?5, c.deniedUser=?6, c.deniedDate=?7 WHERE c.id in (select p.physicianId from PhysicianGroup p where p.groupId = ?8)")
    int deActivatePhysician(String status,int updatedBy,String updatedUser,Timestamp updatedDate,int deniedBy,String deniedUser,Timestamp deniedDate,int groupId);
	
	@Modifying
	@Transactional(readOnly=false)
    @Query("UPDATE PhysicianAssistantAccount c SET c.status = ?1, c.lastUpdatedBy=?2, c.lastUpdatedUser=?3, c.lastUpdatedDate=?4, c.deniedBy=?5, c.deniedDate=?6 WHERE c.groupId = ?7")
    int deActivatePhysicianAssistant(String status,int updatedBy,String updatedUser,Timestamp updatedDate,int deniedBy,Timestamp deniedDate,int groupId);
	
	
	@Modifying
	@Transactional(readOnly=false)
    @Query("UPDATE PatientGroup c SET c.delFlag = ?1,c.updatedBy=?2, c.updatedDate=?3,c.updatedUserType=?4 WHERE c.groupId = ?5")
    int deActivatePatientGroup(String status,int updatedBy,Timestamp updatedDate,String updatedUser, int groupId);

	
	
	@Query("SELECT g FROM GroupMaster g join PatientGroup pa on g.id=pa.groupId where pa.delFlag='N' and g.status='Active' and pa.patientId=:patientId")
	List<GroupMaster> getGroupsByPatientid(@Param("patientId") int patientId);
	
	
}
