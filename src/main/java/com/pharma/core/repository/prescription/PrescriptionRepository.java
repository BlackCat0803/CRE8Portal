package com.pharma.core.repository.prescription;

import java.sql.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pharma.core.model.prescription.PrescriptionMaster;
/**
 * This class is a Java interface that defines a data access contract
 *
 */
@Repository("prescriptionRepository")
public interface PrescriptionRepository extends JpaRepository<PrescriptionMaster, Integer> {

	
	/*************  Admin and Super Admin Users Log-in Queries ***************/
	@Query("select  COALESCE(count(pm),0)  from PrescriptionMaster pm, PrescriptionTransaction pt, PhysicianAccount pa, PatientAccount p "
			+ "where pm.physicianId=pa.id and pm.patientId=p.id and pm.id=pt.prescriptionId and "
			+ "(pa.physicianName like %:physicianName% and p.patientName like %:patientName%) and pm.cre8PrescriptionNo like %:prescriptionNo% and "
			+ "pt.rxStatus like %:prescriptionstatus% and pt.rxNumber like %:rxNo% and pt.cre8PrescriptionNo like %:rxPrescriptionNo%")
	int countByFilter(@Param("physicianName") String physicianName, @Param("patientName") String patientName,@Param("prescriptionNo") String prescriptionNo,
			@Param("prescriptionstatus") String prescriptionstatus, @Param("rxNo") String rxNo, @Param("rxPrescriptionNo") String rxPrescriptionNo);
	
	@Query("select  COALESCE(count(pm),0)  from PrescriptionMaster pm, PrescriptionTransaction pt, PhysicianAccount pa, PatientAccount p "
			+ "where pm.physicianId=pa.id and pm.patientId=p.id and pm.id=pt.prescriptionId and "
			+ "(pa.physicianName like %:physicianName% and p.patientName like %:patientName%) and pm.cre8PrescriptionNo like %:prescriptionNo% and "
			+ "pt.rxStatus like %:prescriptionstatus% and pt.rxNumber like %:rxNo% and pt.cre8PrescriptionNo like %:rxPrescriptionNo% "
			+ "and pm.groupId=:groupId ")
	int countByFilter(@Param("physicianName") String physicianName, @Param("patientName") String patientName,@Param("prescriptionNo") String prescriptionNo,
			@Param("prescriptionstatus") String prescriptionstatus, @Param("rxNo") String rxNo, @Param("rxPrescriptionNo") String rxPrescriptionNo,
			@Param("groupId") int groupId);

	
	
	@Query("select  COALESCE(count(pm),0)  from PrescriptionMaster pm, PrescriptionTransaction pt, PhysicianAccount pa, PatientAccount p where pm.physicianId=pa.id "
			+ "and pm.patientId=p.id and pm.id=pt.prescriptionId and (pa.physicianName like %:physicianName% and p.patientName like %:patientName%) and "
			+ "pm.prescriptionDate >=:presriptionDate and pm.cre8PrescriptionNo like %:prescriptionNo% and "
			+ "pt.rxStatus like %:prescriptionstatus% and pt.rxNumber like %:rxNo%  and pt.cre8PrescriptionNo like %:rxPrescriptionNo%")
	int countByFilterFromDate(@Param("physicianName") String physicianName, @Param("patientName") String patientName, 
			@Param("presriptionDate") Date presriptionDate, @Param("prescriptionNo") String prescriptionNo,
			@Param("prescriptionstatus") String prescriptionstatus, @Param("rxNo") String rxNo, @Param("rxPrescriptionNo") String rxPrescriptionNo);
	
	
	
	@Query("select  COALESCE(count(pm),0)  from PrescriptionMaster pm, PrescriptionTransaction pt, PhysicianAccount pa, PatientAccount p where pm.physicianId=pa.id "
			+ "and pm.patientId=p.id and pm.id=pt.prescriptionId and (pa.physicianName like %:physicianName% and p.patientName like %:patientName%) and "
			+ "pm.prescriptionDate >=:presriptionDate and pm.cre8PrescriptionNo like %:prescriptionNo% and "
			+ "pt.rxStatus like %:prescriptionstatus% and pt.rxNumber like %:rxNo%  and pt.cre8PrescriptionNo like %:rxPrescriptionNo% "
			+ "and pm.groupId=:groupId ")
	int countByFilterFromDate(@Param("physicianName") String physicianName, @Param("patientName") String patientName, 
			@Param("presriptionDate") Date presriptionDate, @Param("prescriptionNo") String prescriptionNo,
			@Param("prescriptionstatus") String prescriptionstatus, @Param("rxNo") String rxNo, @Param("rxPrescriptionNo") String rxPrescriptionNo,
			@Param("groupId") int groupId);
	
	
	@Query("select  COALESCE(count(pm),0)  from PrescriptionMaster pm, PrescriptionTransaction pt, PhysicianAccount pa, PatientAccount p "
			+ "where pm.physicianId=pa.id and pm.patientId=p.id and pm.id=pt.prescriptionId and (pa.physicianName like %:physicianName% and p.patientName like %:patientName%) "
			+ "and pm.prescriptionDate <=:presriptionDate and pm.cre8PrescriptionNo like %:prescriptionNo% and "
			+ "pt.rxStatus like %:prescriptionstatus% and pt.rxNumber like %:rxNo%  and pt.cre8PrescriptionNo like %:rxPrescriptionNo%")
	int countByFilterToDate(@Param("physicianName") String physicianName, @Param("patientName") String patientName, @Param("presriptionDate") Date presriptionDate, 
			 @Param("prescriptionNo") String prescriptionNo, @Param("prescriptionstatus") String prescriptionstatus, @Param("rxNo") String rxNo, @Param("rxPrescriptionNo") String rxPrescriptionNo);

	@Query("select  COALESCE(count(pm),0)  from PrescriptionMaster pm, PrescriptionTransaction pt, PhysicianAccount pa, PatientAccount p "
			+ "where pm.physicianId=pa.id and pm.patientId=p.id and pm.id=pt.prescriptionId and (pa.physicianName like %:physicianName% and p.patientName like %:patientName%) "
			+ "and pm.prescriptionDate <=:presriptionDate and pm.cre8PrescriptionNo like %:prescriptionNo% and "
			+ "pt.rxStatus like %:prescriptionstatus% and pt.rxNumber like %:rxNo%  and pt.cre8PrescriptionNo like %:rxPrescriptionNo% and pm.groupId=:groupId ")
	int countByFilterToDate(@Param("physicianName") String physicianName, @Param("patientName") String patientName, @Param("presriptionDate") Date presriptionDate, 
			 @Param("prescriptionNo") String prescriptionNo, @Param("prescriptionstatus") String prescriptionstatus, @Param("rxNo") String rxNo, 
			 @Param("rxPrescriptionNo") String rxPrescriptionNo, @Param("groupId") int groupId);

	
	@Query("select  COALESCE(count(pm),0)  from PrescriptionMaster pm, PrescriptionTransaction pt, PhysicianAccount pa, PatientAccount p "
			+ "where pm.physicianId=pa.id and pm.patientId=p.id and pm.id=pt.prescriptionId and (pa.physicianName like %:physicianName% and p.patientName like %:patientName%) "
			+ "and pm.prescriptionDate >=:presriptionDate and pm.prescriptionDate <=:presriptionToDate and pm.cre8PrescriptionNo like %:prescriptionNo% and "
			+ "pt.rxStatus like %:prescriptionstatus% and pt.rxNumber like %:rxNo%  and pt.cre8PrescriptionNo like %:rxPrescriptionNo%")
	int countByFilter(@Param("physicianName") String physicianName, @Param("patientName") String patientName, @Param("presriptionDate") Date presriptionDate,
			@Param("presriptionToDate") Date presriptionToDate, @Param("prescriptionNo") String prescriptionNo, 
			@Param("prescriptionstatus") String prescriptionstatus, @Param("rxNo") String rxNo, @Param("rxPrescriptionNo") String rxPrescriptionNo);

	@Query("select  COALESCE(count(pm),0)  from PrescriptionMaster pm, PrescriptionTransaction pt, PhysicianAccount pa, PatientAccount p "
			+ "where pm.physicianId=pa.id and pm.patientId=p.id and pm.id=pt.prescriptionId and (pa.physicianName like %:physicianName% and p.patientName like %:patientName%) "
			+ "and pm.prescriptionDate >=:presriptionDate and pm.prescriptionDate <=:presriptionToDate and pm.cre8PrescriptionNo like %:prescriptionNo% and "
			+ "pt.rxStatus like %:prescriptionstatus% and pt.rxNumber like %:rxNo%  and pt.cre8PrescriptionNo like %:rxPrescriptionNo% and pm.groupId=:groupId ")
	int countByFilter(@Param("physicianName") String physicianName, @Param("patientName") String patientName, @Param("presriptionDate") Date presriptionDate,
			@Param("presriptionToDate") Date presriptionToDate, @Param("prescriptionNo") String prescriptionNo, 
			@Param("prescriptionstatus") String prescriptionstatus, @Param("rxNo") String rxNo, @Param("rxPrescriptionNo") String rxPrescriptionNo, 
			@Param("groupId") int groupId);

	
	
	
	@Query("select pm  from PrescriptionMaster pm, PrescriptionTransaction pt, PhysicianAccount pa, PatientAccount p "
			+ "where pm.physicianId=pa.id and pm.patientId=p.id and pm.id=pt.prescriptionId and "
			+ "(pa.physicianName like %:physicianName% and p.patientName like %:patientName%) and pm.cre8PrescriptionNo like %:prescriptionNo% and "
			+ "pt.rxStatus like %:prescriptionstatus% and pt.rxNumber like %:rxNo%  and pt.cre8PrescriptionNo like %:rxPrescriptionNo% GROUP BY pm.id")
	List<PrescriptionMaster> countByFilterGroup(@Param("physicianName") String physicianName, @Param("patientName") String patientName,@Param("prescriptionNo") String prescriptionNo,
			@Param("prescriptionstatus") String prescriptionstatus, @Param("rxNo") String rxNo, @Param("rxPrescriptionNo") String rxPrescriptionNo);

	@Query("select pm  from PrescriptionMaster pm, PrescriptionTransaction pt, PhysicianAccount pa, PatientAccount p "
			+ "where pm.physicianId=pa.id and pm.patientId=p.id and pm.id=pt.prescriptionId and "
			+ "(pa.physicianName like %:physicianName% and p.patientName like %:patientName%) and pm.cre8PrescriptionNo like %:prescriptionNo% and "
			+ "pt.rxStatus like %:prescriptionstatus% and pt.rxNumber like %:rxNo%  and pt.cre8PrescriptionNo like %:rxPrescriptionNo% "
			+ "and pm.groupId=:groupId GROUP BY pm.id")
	List<PrescriptionMaster> countByFilterGroup(@Param("physicianName") String physicianName, @Param("patientName") String patientName,@Param("prescriptionNo") String prescriptionNo,
			@Param("prescriptionstatus") String prescriptionstatus, @Param("rxNo") String rxNo, @Param("rxPrescriptionNo") String rxPrescriptionNo,
			@Param("groupId") int groupId);
	
	
	
	@Query("select pm  from PrescriptionMaster pm, PrescriptionTransaction pt, PhysicianAccount pa, PatientAccount p where pm.physicianId=pa.id "
			+ "and pm.patientId=p.id and pm.id=pt.prescriptionId and (pa.physicianName like %:physicianName% and p.patientName like %:patientName%) and "
			+ "pm.prescriptionDate >=:presriptionDate and pm.cre8PrescriptionNo like %:prescriptionNo% and "
			+ "pt.rxStatus like %:prescriptionstatus% and pt.rxNumber like %:rxNo%  and pt.cre8PrescriptionNo like %:rxPrescriptionNo% GROUP BY pm.id ")
	List<PrescriptionMaster> countByFilterFromDateGroup(@Param("physicianName") String physicianName, @Param("patientName") String patientName, 
			@Param("presriptionDate") Date presriptionDate, @Param("prescriptionNo") String prescriptionNo,
			@Param("prescriptionstatus") String prescriptionstatus, @Param("rxNo") String rxNo, @Param("rxPrescriptionNo") String rxPrescriptionNo);
	

	@Query("select pm  from PrescriptionMaster pm, PrescriptionTransaction pt, PhysicianAccount pa, PatientAccount p where pm.physicianId=pa.id "
			+ "and pm.patientId=p.id and pm.id=pt.prescriptionId and (pa.physicianName like %:physicianName% and p.patientName like %:patientName%) and "
			+ "pm.prescriptionDate >=:presriptionDate and pm.cre8PrescriptionNo like %:prescriptionNo% and "
			+ "pt.rxStatus like %:prescriptionstatus% and pt.rxNumber like %:rxNo%  and pt.cre8PrescriptionNo like %:rxPrescriptionNo% "
			+ "and pm.groupId=:groupId GROUP BY pm.id ")
	List<PrescriptionMaster> countByFilterFromDateGroup(@Param("physicianName") String physicianName, @Param("patientName") String patientName, 
			@Param("presriptionDate") Date presriptionDate, @Param("prescriptionNo") String prescriptionNo,
			@Param("prescriptionstatus") String prescriptionstatus, @Param("rxNo") String rxNo, @Param("rxPrescriptionNo") String rxPrescriptionNo,
			@Param("groupId") int groupId);

	
	
	
	@Query("select pm from PrescriptionMaster pm, PrescriptionTransaction pt, PhysicianAccount pa, PatientAccount p "
			+ "where pm.physicianId=pa.id and pm.patientId=p.id and pm.id=pt.prescriptionId and (pa.physicianName like %:physicianName% and p.patientName like %:patientName%) "
			+ "and pm.prescriptionDate <=:presriptionDate and pm.cre8PrescriptionNo like %:prescriptionNo% and "
			+ "pt.rxStatus like %:prescriptionstatus% and pt.rxNumber like %:rxNo%  and pt.cre8PrescriptionNo like %:rxPrescriptionNo% GROUP BY pm.id ")
	List<PrescriptionMaster> countByFilterToDateGroup(@Param("physicianName") String physicianName, @Param("patientName") String patientName, @Param("presriptionDate") Date presriptionDate, 
			 @Param("prescriptionNo") String prescriptionNo, @Param("prescriptionstatus") String prescriptionstatus, @Param("rxNo") String rxNo, @Param("rxPrescriptionNo") String rxPrescriptionNo);

	@Query("select pm from PrescriptionMaster pm, PrescriptionTransaction pt, PhysicianAccount pa, PatientAccount p "
			+ "where pm.physicianId=pa.id and pm.patientId=p.id and pm.id=pt.prescriptionId and (pa.physicianName like %:physicianName% and p.patientName like %:patientName%) "
			+ "and pm.prescriptionDate <=:presriptionDate and pm.cre8PrescriptionNo like %:prescriptionNo% and "
			+ "pt.rxStatus like %:prescriptionstatus% and pt.rxNumber like %:rxNo%  and pt.cre8PrescriptionNo like %:rxPrescriptionNo% "
			+ "and pm.groupId=:groupId GROUP BY pm.id ")
	List<PrescriptionMaster> countByFilterToDateGroup(@Param("physicianName") String physicianName, @Param("patientName") String patientName, @Param("presriptionDate") Date presriptionDate, 
			 @Param("prescriptionNo") String prescriptionNo, @Param("prescriptionstatus") String prescriptionstatus, @Param("rxNo") String rxNo, 
			 @Param("rxPrescriptionNo") String rxPrescriptionNo, @Param("groupId") int groupId);

	
	@Query("select pm from PrescriptionMaster pm, PrescriptionTransaction pt, PhysicianAccount pa, PatientAccount p "
			+ "where pm.physicianId=pa.id and pm.patientId=p.id and pm.id=pt.prescriptionId and (pa.physicianName like %:physicianName% and p.patientName like %:patientName%) "
			+ "and pm.prescriptionDate >=:presriptionDate and pm.prescriptionDate <=:presriptionToDate and pm.cre8PrescriptionNo like %:prescriptionNo% and "
			+ "pt.rxStatus like %:prescriptionstatus% and pt.rxNumber like %:rxNo%  and pt.cre8PrescriptionNo like %:rxPrescriptionNo% GROUP BY pm.id ")
	List<PrescriptionMaster> countByFilterGroup(@Param("physicianName") String physicianName, @Param("patientName") String patientName, @Param("presriptionDate") Date presriptionDate,
			@Param("presriptionToDate") Date presriptionToDate, @Param("prescriptionNo") String prescriptionNo, 
			@Param("prescriptionstatus") String prescriptionstatus, @Param("rxNo") String rxNo, @Param("rxPrescriptionNo") String rxPrescriptionNo);
	
	
	@Query("select pm from PrescriptionMaster pm, PrescriptionTransaction pt, PhysicianAccount pa, PatientAccount p "
			+ "where pm.physicianId=pa.id and pm.patientId=p.id and pm.id=pt.prescriptionId and (pa.physicianName like %:physicianName% and p.patientName like %:patientName%) "
			+ "and pm.prescriptionDate >=:presriptionDate and pm.prescriptionDate <=:presriptionToDate and pm.cre8PrescriptionNo like %:prescriptionNo% and "
			+ "pt.rxStatus like %:prescriptionstatus% and pt.rxNumber like %:rxNo%  and pt.cre8PrescriptionNo like %:rxPrescriptionNo% "
			+ "and pm.groupId=:groupId GROUP BY pm.id ")
	List<PrescriptionMaster> countByFilterGroup(@Param("physicianName") String physicianName, @Param("patientName") String patientName, @Param("presriptionDate") Date presriptionDate,
			@Param("presriptionToDate") Date presriptionToDate, @Param("prescriptionNo") String prescriptionNo, 
			@Param("prescriptionstatus") String prescriptionstatus, @Param("rxNo") String rxNo, @Param("rxPrescriptionNo") String rxPrescriptionNo,
			@Param("groupId") int groupId);

	
	
	
	@Query("select  COALESCE(count(pm),0)  from PrescriptionMaster pm, PrescriptionTransaction pt, PhysicianAccount pa, PatientAccount p "
			+ "where pm.physicianId=pa.id and pm.patientId=p.id and pm.id=pt.prescriptionId and "
			+ "(pa.physicianName like %:physicianName% and p.patientName like %:patientName%) and pt.rxStatus like %:prescriptionstatus% "
			+ "and pt.rxNumber like %:rxNo%  and pt.cre8PrescriptionNo like %:rxPrescriptionNo%")
	int countByFilter(@Param("physicianName") String physicianName, @Param("patientName") String patientName, 
			@Param("prescriptionstatus") String prescriptionstatus, @Param("rxNo") String rxNo, @Param("rxPrescriptionNo") String rxPrescriptionNo);
	
	
	@Query("select  COALESCE(count(pm),0)  from PrescriptionMaster pm, PrescriptionTransaction pt, PhysicianAccount pa, PatientAccount p where pm.physicianId=pa.id "
			+ "and pm.patientId=p.id and pm.id=pt.prescriptionId and (pa.physicianName like %:physicianName% and p.patientName like %:patientName%) and "
			+ "pm.prescriptionDate >=:presriptionDate and pt.rxStatus like %:prescriptionstatus% and pt.rxNumber like %:rxNo%  and pt.cre8PrescriptionNo like %:rxPrescriptionNo%")
	int countByFilterFromDate(@Param("physicianName") String physicianName, @Param("patientName") String patientName, 
			@Param("presriptionDate") Date presriptionDate, @Param("prescriptionstatus") String prescriptionstatus, @Param("rxNo") String rxNo, @Param("rxPrescriptionNo") String rxPrescriptionNo);
	
	@Query("select  COALESCE(count(pm),0)  from PrescriptionMaster pm, PrescriptionTransaction pt, PhysicianAccount pa, PatientAccount p "
			+ "where pm.physicianId=pa.id and pm.patientId=p.id and pm.id=pt.prescriptionId and (pa.physicianName like %:physicianName% and p.patientName like %:patientName%) "
			+ "and pm.prescriptionDate <=:presriptionDate and pt.rxStatus like %:prescriptionstatus% and pt.rxNumber like %:rxNo%  and pt.cre8PrescriptionNo like %:rxPrescriptionNo%")
	int countByFilterToDate(@Param("physicianName") String physicianName, @Param("patientName") String patientName, @Param("presriptionDate") Date presriptionDate, 
			 @Param("prescriptionstatus") String prescriptionstatus, @Param("rxNo") String rxNo, @Param("rxPrescriptionNo") String rxPrescriptionNo);

	@Query("select  COALESCE(count(pm),0)  from PrescriptionMaster pm, PrescriptionTransaction pt, PhysicianAccount pa, PatientAccount p "
			+ "where pm.physicianId=pa.id and pm.patientId=p.id and pm.id=pt.prescriptionId and (pa.physicianName like %:physicianName% and p.patientName like %:patientName%) "
			+ "and pm.prescriptionDate >=:presriptionDate and pm.prescriptionDate <=:presriptionToDate and "
			+ "pt.rxStatus like %:prescriptionstatus% and pt.rxNumber like %:rxNo%  and pt.cre8PrescriptionNo like %:rxPrescriptionNo%")
	int countByFilter(@Param("physicianName") String physicianName, @Param("patientName") String patientName, @Param("presriptionDate") Date presriptionDate,
			@Param("presriptionToDate") Date presriptionToDate, @Param("prescriptionstatus") String prescriptionstatus, @Param("rxNo") String rxNo, @Param("rxPrescriptionNo") String rxPrescriptionNo);

	
	
	@Query("select c from PrescriptionMaster c order by c.id desc")
	Page<PrescriptionMaster> getAllRecords(Pageable page);
	
	@Query("select pm from PrescriptionMaster pm, PrescriptionTransaction pt, PhysicianAccount pa, PatientAccount p "
			+ "where pm.physicianId=pa.id and pm.patientId=p.id and pm.id=pt.prescriptionId and "
			+ "(pa.physicianName like %:physicianName% and p.patientName like %:patientName%) and pm.cre8PrescriptionNo like %:prescriptionNo% "
			+ "and pt.rxStatus like %:prescriptionstatus% and pt.rxNumber like %:rxNo%  and pt.cre8PrescriptionNo like %:rxPrescriptionNo% GROUP BY pm.id order by pm.id desc")
	Page<PrescriptionMaster> getByFilter(@Param("physicianName") String physicianName, @Param("patientName") String patientName,
			@Param("prescriptionNo") String prescriptionNo, @Param("prescriptionstatus") String prescriptionstatus, @Param("rxNo") String rxNo, @Param("rxPrescriptionNo") String rxPrescriptionNo, Pageable page);
	
	@Query("select pm from PrescriptionMaster pm, PrescriptionTransaction pt, PhysicianAccount pa, PatientAccount p "
			+ "where pm.physicianId=pa.id and pm.patientId=p.id and pm.id=pt.prescriptionId and "
			+ "(pa.physicianName like %:physicianName% and p.patientName like %:patientName%) and pm.cre8PrescriptionNo like %:prescriptionNo% "
			+ "and pt.rxStatus like %:prescriptionstatus% and pt.rxNumber like %:rxNo%  and pt.cre8PrescriptionNo like %:rxPrescriptionNo% "
			+ "and pm.groupId=:groupId GROUP BY pm.id order by pm.id desc")
	Page<PrescriptionMaster> getByFilter(@Param("physicianName") String physicianName, @Param("patientName") String patientName,
			@Param("prescriptionNo") String prescriptionNo, @Param("prescriptionstatus") String prescriptionstatus, @Param("rxNo") String rxNo, 
			@Param("rxPrescriptionNo") String rxPrescriptionNo, @Param("groupId") int groupId, Pageable page);

	
	
	@Query("select pm from PrescriptionMaster pm, PrescriptionTransaction pt, PhysicianAccount pa, PatientAccount p "
			+ "where pm.physicianId=pa.id and pm.patientId=p.id and pm.id=pt.prescriptionId and "
			+ "(pa.physicianName like %:physicianName% and p.patientName like %:patientName%) and pm.prescriptionDate >=:presriptionDate and "
			+ "pm.cre8PrescriptionNo like %:prescriptionNo% and pt.rxStatus like %:prescriptionstatus% and pt.rxNumber like %:rxNo%   and pt.cre8PrescriptionNo like %:rxPrescriptionNo% "
			+ "GROUP BY pm.id  order by pm.id desc")
	Page<PrescriptionMaster> getByFilterFromDate(@Param("physicianName") String physicianName, @Param("patientName") String patientName, 
			@Param("presriptionDate") Date presriptionDate,@Param("prescriptionNo") String prescriptionNo,
			@Param("prescriptionstatus") String prescriptionstatus, @Param("rxNo") String rxNo, @Param("rxPrescriptionNo") String rxPrescriptionNo, Pageable page);
	
	@Query("select pm from PrescriptionMaster pm, PrescriptionTransaction pt, PhysicianAccount pa, PatientAccount p "
			+ "where pm.physicianId=pa.id and pm.patientId=p.id and pm.id=pt.prescriptionId and "
			+ "(pa.physicianName like %:physicianName% and p.patientName like %:patientName%) and pm.prescriptionDate >=:presriptionDate and "
			+ "pm.cre8PrescriptionNo like %:prescriptionNo% and pt.rxStatus like %:prescriptionstatus% and pt.rxNumber like %:rxNo% "
			+ "and pt.cre8PrescriptionNo like %:rxPrescriptionNo% and pm.groupId=:groupId GROUP BY pm.id  order by pm.id desc")
	Page<PrescriptionMaster> getByFilterFromDate(@Param("physicianName") String physicianName, @Param("patientName") String patientName, 
			@Param("presriptionDate") Date presriptionDate,@Param("prescriptionNo") String prescriptionNo,
			@Param("prescriptionstatus") String prescriptionstatus, @Param("rxNo") String rxNo, @Param("rxPrescriptionNo") String rxPrescriptionNo, 
			@Param("groupId") int groupId, Pageable page);

	
	
	
	@Query("select pm from PrescriptionMaster pm, PrescriptionTransaction pt, PhysicianAccount pa, PatientAccount p "
			+ "where pm.physicianId=pa.id and pm.patientId=p.id and pm.id=pt.prescriptionId and "
			+ "(pa.physicianName like %:physicianName% and p.patientName like %:patientName%) and pm.prescriptionDate <=:presriptionDate "
			+ "and pm.cre8PrescriptionNo like %:prescriptionNo% and pt.rxStatus like %:prescriptionstatus% and pt.rxNumber like %:rxNo%  and pt.cre8PrescriptionNo like %:rxPrescriptionNo% "
			+ "GROUP BY pm.id order by pm.id desc")
	Page<PrescriptionMaster> getByFilterToDate(@Param("physicianName") String physicianName, @Param("patientName") String patientName, 
			@Param("presriptionDate") Date presriptionDate,@Param("prescriptionNo") String prescriptionNo,
			@Param("prescriptionstatus") String prescriptionstatus, @Param("rxNo") String rxNo, @Param("rxPrescriptionNo") String rxPrescriptionNo, Pageable page);

	
	@Query("select pm from PrescriptionMaster pm, PrescriptionTransaction pt, PhysicianAccount pa, PatientAccount p "
			+ "where pm.physicianId=pa.id and pm.patientId=p.id and pm.id=pt.prescriptionId and "
			+ "(pa.physicianName like %:physicianName% and p.patientName like %:patientName%) and pm.prescriptionDate <=:presriptionDate "
			+ "and pm.cre8PrescriptionNo like %:prescriptionNo% and pt.rxStatus like %:prescriptionstatus% and pt.rxNumber like %:rxNo%  "
			+ "and pt.cre8PrescriptionNo like %:rxPrescriptionNo% and pm.groupId=:groupId GROUP BY pm.id order by pm.id desc")
	Page<PrescriptionMaster> getByFilterToDate(@Param("physicianName") String physicianName, @Param("patientName") String patientName, 
			@Param("presriptionDate") Date presriptionDate,@Param("prescriptionNo") String prescriptionNo,
			@Param("prescriptionstatus") String prescriptionstatus, @Param("rxNo") String rxNo, @Param("rxPrescriptionNo") String rxPrescriptionNo, 
			@Param("groupId") int groupId, Pageable page);
	
	
	
	
	
	@Query("select pm from PrescriptionMaster pm, PrescriptionTransaction pt, PhysicianAccount pa, PatientAccount p "
			+ "where pm.physicianId=pa.id and pm.patientId=p.id and pm.id=pt.prescriptionId and (pa.physicianName like %:physicianName% and "
			+ "p.patientName like %:patientName%) and pm.prescriptionDate >=:presriptionDate and pm.prescriptionDate <=:presriptionToDate and "
			+ "pm.cre8PrescriptionNo like %:prescriptionNo% and pt.rxStatus like %:prescriptionstatus% and pt.rxNumber like %:rxNo%  and pt.cre8PrescriptionNo like %:rxPrescriptionNo% "
			+ "GROUP BY pm.id order by pm.id desc")
	Page<PrescriptionMaster> getByFilter(@Param("physicianName") String physicianName, @Param("patientName") String patientName, 
			@Param("presriptionDate") Date presriptionDate, @Param("presriptionToDate") Date presriptionToDate,
			@Param("prescriptionNo") String prescriptionNo, @Param("prescriptionstatus") String prescriptionstatus, @Param("rxNo") String rxNo, 
			@Param("rxPrescriptionNo") String rxPrescriptionNo, Pageable page);

	@Query("select pm from PrescriptionMaster pm, PrescriptionTransaction pt, PhysicianAccount pa, PatientAccount p "
			+ "where pm.physicianId=pa.id and pm.patientId=p.id and pm.id=pt.prescriptionId and (pa.physicianName like %:physicianName% and "
			+ "p.patientName like %:patientName%) and pm.prescriptionDate >=:presriptionDate and pm.prescriptionDate <=:presriptionToDate and "
			+ "pm.cre8PrescriptionNo like %:prescriptionNo% and pt.rxStatus like %:prescriptionstatus% and pt.rxNumber like %:rxNo%  "
			+ "and pt.cre8PrescriptionNo like %:rxPrescriptionNo% and pm.groupId=:groupId GROUP BY pm.id order by pm.id desc")
	Page<PrescriptionMaster> getByFilter(@Param("physicianName") String physicianName, @Param("patientName") String patientName, 
			@Param("presriptionDate") Date presriptionDate, @Param("presriptionToDate") Date presriptionToDate,
			@Param("prescriptionNo") String prescriptionNo, @Param("prescriptionstatus") String prescriptionstatus, @Param("rxNo") String rxNo, 
			@Param("rxPrescriptionNo") String rxPrescriptionNo, @Param("groupId") int groupId, Pageable page);

	
	

	
	@Query("select pm from PrescriptionMaster pm, PrescriptionTransaction pt, PhysicianAccount pa, PatientAccount p "
			+ "where pm.physicianId=pa.id and pm.patientId=p.id and pm.id=pt.prescriptionId and "
			+ "(pa.physicianName like %:physicianName% and p.patientName like %:patientName%) "
			+ "and pt.rxStatus like %:prescriptionstatus% and pt.rxNumber like %:rxNo%  and pt.cre8PrescriptionNo like %:rxPrescriptionNo% GROUP BY pm.id order by pm.id desc")
	Page<PrescriptionMaster> getByFilter(@Param("physicianName") String physicianName, @Param("patientName") String patientName,
			 @Param("prescriptionstatus") String prescriptionstatus, @Param("rxNo") String rxNo, @Param("rxPrescriptionNo") String rxPrescriptionNo, Pageable page);
	
	@Query("select pm from PrescriptionMaster pm, PrescriptionTransaction pt, PhysicianAccount pa, PatientAccount p "
			+ "where pm.physicianId=pa.id and pm.patientId=p.id and pm.id=pt.prescriptionId and "
			+ "(pa.physicianName like %:physicianName% and p.patientName like %:patientName%) and pm.prescriptionDate >=:presriptionDate and "
			+ "pt.rxStatus like %:prescriptionstatus% and pt.rxNumber like %:rxNo%  and pt.cre8PrescriptionNo like %:rxPrescriptionNo% GROUP BY pm.id  order by pm.id desc")
	Page<PrescriptionMaster> getByFilterFromDate(@Param("physicianName") String physicianName, @Param("patientName") String patientName, 
			@Param("presriptionDate") Date presriptionDate, @Param("prescriptionstatus") String prescriptionstatus, @Param("rxNo") String rxNo, @Param("rxPrescriptionNo") String rxPrescriptionNo, Pageable page);
	
	@Query("select pm from PrescriptionMaster pm, PrescriptionTransaction pt, PhysicianAccount pa, PatientAccount p "
			+ "where pm.physicianId=pa.id and pm.patientId=p.id and pm.id=pt.prescriptionId and "
			+ "(pa.physicianName like %:physicianName% and p.patientName like %:patientName%) and pm.prescriptionDate <=:presriptionDate "
			+ "and pt.rxStatus like %:prescriptionstatus% and pt.rxNumber like %:rxNo%  and pt.cre8PrescriptionNo like %:rxPrescriptionNo% GROUP BY pm.id order by pm.id desc")
	Page<PrescriptionMaster> getByFilterToDate(@Param("physicianName") String physicianName, @Param("patientName") String patientName, 
			@Param("presriptionDate") Date presriptionDate, @Param("prescriptionstatus") String prescriptionstatus, @Param("rxNo") String rxNo, @Param("rxPrescriptionNo") String rxPrescriptionNo, Pageable page);

	@Query("select pm from PrescriptionMaster pm, PrescriptionTransaction pt, PhysicianAccount pa, PatientAccount p "
			+ "where pm.physicianId=pa.id and pm.patientId=p.id and pm.id=pt.prescriptionId and (pa.physicianName like %:physicianName% and p.patientName like %:patientName%) "
			+ "and pm.prescriptionDate >=:presriptionDate and pm.prescriptionDate <=:presriptionToDate and "
			+ "pt.rxStatus like %:prescriptionstatus% and pt.rxNumber like %:rxNo%  and pt.cre8PrescriptionNo like %:rxPrescriptionNo% GROUP BY pm.id order by pm.id desc")
	Page<PrescriptionMaster> getByFilter(@Param("physicianName") String physicianName, @Param("patientName") String patientName, 
			@Param("presriptionDate") Date presriptionDate, @Param("presriptionToDate") Date presriptionToDate,
			 @Param("prescriptionstatus") String prescriptionstatus, @Param("rxNo") String rxNo, @Param("rxPrescriptionNo") String rxPrescriptionNo, Pageable page);

	
	
	/************************* Physician Log-in Queries ****************/
	@Query("select  COALESCE(count(c),0)  from PrescriptionMaster c where c.physicianId=:id")	
	int findByPhysicianId(@Param("id") int id);
	
	@Query("select  COALESCE(count(pm),0)  from PrescriptionMaster pm, PrescriptionTransaction pt, PhysicianAccount pa, PatientAccount p "
			+ "where pm.physicianId=pa.id and pm.patientId=p.id and pm.id=pt.prescriptionId and "
			+ "pm.physicianId=:id and (pa.physicianName like %:physicianName% and p.patientName like %:patientName%) "
			+ "and pm.cre8PrescriptionNo like %:prescriptionNo% and pt.rxStatus like %:prescriptionstatus% and pt.rxNumber like %:rxNo%  and pt.cre8PrescriptionNo like %:rxPrescriptionNo%")
	int countByPhysicianFilter(@Param("id") int id, @Param("physicianName") String physicianName, @Param("patientName") String patientName,
			@Param("prescriptionNo") String prescriptionNo, @Param("prescriptionstatus") String prescriptionstatus, @Param("rxNo") String rxNo, @Param("rxPrescriptionNo") String rxPrescriptionNo);

	@Query("select  COALESCE(count(pm),0)  from PrescriptionMaster pm, PrescriptionTransaction pt, PhysicianAccount pa, PatientAccount p "
			+ "where pm.physicianId=pa.id and pm.patientId=p.id and pm.id=pt.prescriptionId and "
			+ "pm.physicianId=:id and (pa.physicianName like %:physicianName% and p.patientName like %:patientName%) and "
			+ "pm.prescriptionDate >=:presriptionDate and pm.cre8PrescriptionNo like %:prescriptionNo% and pt.rxStatus like %:prescriptionstatus% "
			+ "and pt.rxNumber like %:rxNo%  and pt.cre8PrescriptionNo like %:rxPrescriptionNo%")
	int countByPhysicianFilterFromDate(@Param("id") int id, @Param("physicianName") String physicianName, @Param("patientName") String patientName, 
			@Param("presriptionDate") Date presriptionDate, @Param("prescriptionNo") String prescriptionNo, 
			@Param("prescriptionstatus") String prescriptionstatus, @Param("rxNo") String rxNo, @Param("rxPrescriptionNo") String rxPrescriptionNo);
	
	@Query("select  COALESCE(count(pm),0)  from PrescriptionMaster pm, PrescriptionTransaction pt, PhysicianAccount pa, PatientAccount p "
			+ "where pm.physicianId=pa.id and pm.patientId=p.id and pm.id=pt.prescriptionId and "
			+ "pm.physicianId=:id and (pa.physicianName like %:physicianName% and p.patientName like %:patientName%) and "
			+ "pm.prescriptionDate <=:presriptionToDate and pm.cre8PrescriptionNo like %:prescriptionNo% and pt.rxStatus like %:prescriptionstatus% "
			+ "and pt.rxNumber like %:rxNo%  and pt.cre8PrescriptionNo like %:rxPrescriptionNo%")
	int countByPhysicianFilterToDate(@Param("id") int id, @Param("physicianName") String physicianName, @Param("patientName") String patientName, 
			@Param("presriptionToDate") Date presriptionToDate, @Param("prescriptionNo") String prescriptionNo, 
			@Param("prescriptionstatus") String prescriptionstatus, @Param("rxNo") String rxNo, @Param("rxPrescriptionNo") String rxPrescriptionNo);
	
	@Query("select  COALESCE(count(pm),0)  from PrescriptionMaster pm, PrescriptionTransaction pt, PhysicianAccount pa, PatientAccount p "
			+ "where pm.physicianId=pa.id and pm.patientId=p.id and pm.id=pt.prescriptionId and "
			+ "pm.physicianId=:id and (pa.physicianName like %:physicianName% and p.patientName like %:patientName%) and "
			+ "pm.prescriptionDate >=:presriptionDate and pm.prescriptionDate <=:presriptionToDate and pm.cre8PrescriptionNo like %:prescriptionNo% "
			+ "and pt.rxStatus like %:prescriptionstatus% and pt.rxNumber like %:rxNo%  and pt.cre8PrescriptionNo like %:rxPrescriptionNo%")
	int countByPhysicianFilter(@Param("id") int id, @Param("physicianName") String physicianName, @Param("patientName") String patientName, 
			@Param("presriptionDate") Date presriptionDate, @Param("presriptionToDate") Date presriptionToDate,
			@Param("prescriptionNo") String prescriptionNo,@Param("prescriptionstatus") String prescriptionstatus, @Param("rxNo") String rxNo, @Param("rxPrescriptionNo") String rxPrescriptionNo);


	
	
	@Query("select  COALESCE(count(pm),0)  from PrescriptionMaster pm, PrescriptionTransaction pt, PhysicianAccount pa, PatientAccount p "
			+ "where pm.physicianId=pa.id and pm.patientId=p.id and pm.id=pt.prescriptionId and "
			+ "pm.physicianId=:id and (pa.physicianName like %:physicianName% and p.patientName like %:patientName%) "
			+ "and pm.cre8PrescriptionNo like %:prescriptionNo% and pt.rxStatus like %:prescriptionstatus% and pt.rxNumber like %:rxNo%  and pt.cre8PrescriptionNo like %:rxPrescriptionNo% group by pm.id ")
	List<PrescriptionMaster> countByPhysicianFilterGroup(@Param("id") int id, @Param("physicianName") String physicianName, @Param("patientName") String patientName,
			@Param("prescriptionNo") String prescriptionNo, @Param("prescriptionstatus") String prescriptionstatus, @Param("rxNo") String rxNo, @Param("rxPrescriptionNo") String rxPrescriptionNo);

	@Query("select  COALESCE(count(pm),0)  from PrescriptionMaster pm, PrescriptionTransaction pt, PhysicianAccount pa, PatientAccount p "
			+ "where pm.physicianId=pa.id and pm.patientId=p.id and pm.id=pt.prescriptionId and "
			+ "pm.physicianId=:id and (pa.physicianName like %:physicianName% and p.patientName like %:patientName%) and "
			+ "pm.prescriptionDate >=:presriptionDate and pm.cre8PrescriptionNo like %:prescriptionNo% and pt.rxStatus like %:prescriptionstatus% "
			+ "and pt.rxNumber like %:rxNo%  and pt.cre8PrescriptionNo like %:rxPrescriptionNo% group by pm.id ")
	List<PrescriptionMaster> countByPhysicianFilterFromDateGroup(@Param("id") int id, @Param("physicianName") String physicianName, @Param("patientName") String patientName, 
			@Param("presriptionDate") Date presriptionDate, @Param("prescriptionNo") String prescriptionNo, 
			@Param("prescriptionstatus") String prescriptionstatus, @Param("rxNo") String rxNo, @Param("rxPrescriptionNo") String rxPrescriptionNo);
	
	@Query("select  COALESCE(count(pm),0)  from PrescriptionMaster pm, PrescriptionTransaction pt, PhysicianAccount pa, PatientAccount p "
			+ "where pm.physicianId=pa.id and pm.patientId=p.id and pm.id=pt.prescriptionId and "
			+ "pm.physicianId=:id and (pa.physicianName like %:physicianName% and p.patientName like %:patientName%) and "
			+ "pm.prescriptionDate <=:presriptionToDate and pm.cre8PrescriptionNo like %:prescriptionNo% and pt.rxStatus like %:prescriptionstatus% "
			+ "and pt.rxNumber like %:rxNo%  and pt.cre8PrescriptionNo like %:rxPrescriptionNo% group by pm.id ")
	List<PrescriptionMaster> countByPhysicianFilterToDateGroup(@Param("id") int id, @Param("physicianName") String physicianName, @Param("patientName") String patientName, 
			@Param("presriptionToDate") Date presriptionToDate, @Param("prescriptionNo") String prescriptionNo, 
			@Param("prescriptionstatus") String prescriptionstatus, @Param("rxNo") String rxNo, @Param("rxPrescriptionNo") String rxPrescriptionNo);
	
	@Query("select  COALESCE(count(pm),0)  from PrescriptionMaster pm, PrescriptionTransaction pt, PhysicianAccount pa, PatientAccount p "
			+ "where pm.physicianId=pa.id and pm.patientId=p.id and pm.id=pt.prescriptionId and "
			+ "pm.physicianId=:id and (pa.physicianName like %:physicianName% and p.patientName like %:patientName%) and "
			+ "pm.prescriptionDate >=:presriptionDate and pm.prescriptionDate <=:presriptionToDate and pm.cre8PrescriptionNo like %:prescriptionNo% "
			+ "and pt.rxStatus like %:prescriptionstatus% and pt.rxNumber like %:rxNo%  and pt.cre8PrescriptionNo like %:rxPrescriptionNo% group by pm.id ")
	List<PrescriptionMaster> countByPhysicianFilterGroup(@Param("id") int id, @Param("physicianName") String physicianName, @Param("patientName") String patientName, 
			@Param("presriptionDate") Date presriptionDate, @Param("presriptionToDate") Date presriptionToDate,
			@Param("prescriptionNo") String prescriptionNo,@Param("prescriptionstatus") String prescriptionstatus, @Param("rxNo") String rxNo, @Param("rxPrescriptionNo") String rxPrescriptionNo);

	
	
	
	
	
	
	
	@Query("select  COALESCE(count(pm),0)  from PrescriptionMaster pm, PrescriptionTransaction pt, PhysicianAccount pa, PatientAccount p "
			+ "where pm.physicianId=pa.id and pm.patientId=p.id and pm.id=pt.prescriptionId and "
			+ "pm.physicianId=:id and (pa.physicianName like %:physicianName% and p.patientName like %:patientName%) "
			+ "and pt.rxStatus like %:prescriptionstatus% and pt.rxNumber like %:rxNo%  and pt.cre8PrescriptionNo like %:rxPrescriptionNo%")
	int countByPhysicianFilter(@Param("id") int id, @Param("physicianName") String physicianName, @Param("patientName") String patientName,
			@Param("prescriptionstatus") String prescriptionstatus, @Param("rxNo") String rxNo, @Param("rxPrescriptionNo") String rxPrescriptionNo);

	@Query("select  COALESCE(count(pm),0)  from PrescriptionMaster pm, PrescriptionTransaction pt, PhysicianAccount pa, PatientAccount p "
			+ "where pm.physicianId=pa.id and pm.patientId=p.id and pm.id=pt.prescriptionId and "
			+ "pm.physicianId=:id and (pa.physicianName like %:physicianName% and p.patientName like %:patientName%) and "
			+ "pm.prescriptionDate >=:presriptionDate and pt.rxStatus like %:prescriptionstatus% and pt.rxNumber like %:rxNo%  and pt.cre8PrescriptionNo like %:rxPrescriptionNo%")
	int countByPhysicianFilterFromDate(@Param("id") int id, @Param("physicianName") String physicianName, @Param("patientName") String patientName, 
			@Param("presriptionDate") Date presriptionDate, @Param("prescriptionstatus") String prescriptionstatus, @Param("rxNo") String rxNo, @Param("rxPrescriptionNo") String rxPrescriptionNo);
	
	@Query("select  COALESCE(count(pm),0)  from PrescriptionMaster pm, PrescriptionTransaction pt, PhysicianAccount pa, PatientAccount p "
			+ "where pm.physicianId=pa.id and pm.patientId=p.id and pm.id=pt.prescriptionId and "
			+ "pm.physicianId=:id and (pa.physicianName like %:physicianName% and p.patientName like %:patientName%) and "
			+ "pm.prescriptionDate <=:presriptionToDate and pt.rxStatus like %:prescriptionstatus% and pt.rxNumber like %:rxNo%  and pt.cre8PrescriptionNo like %:rxPrescriptionNo%")
	int countByPhysicianFilterToDate(@Param("id") int id, @Param("physicianName") String physicianName, @Param("patientName") String patientName, 
			@Param("presriptionToDate") Date presriptionToDate, @Param("prescriptionstatus") String prescriptionstatus, @Param("rxNo") String rxNo, @Param("rxPrescriptionNo") String rxPrescriptionNo);
	
	@Query("select  COALESCE(count(pm),0)  from PrescriptionMaster pm, PrescriptionTransaction pt, PhysicianAccount pa, PatientAccount p "
			+ "where pm.physicianId=pa.id and pm.patientId=p.id and pm.id=pt.prescriptionId and "
			+ "pm.physicianId=:id and (pa.physicianName like %:physicianName% and p.patientName like %:patientName%) and "
			+ "pm.prescriptionDate >=:presriptionDate and pm.prescriptionDate <=:presriptionToDate "
			+ "and pt.rxStatus like %:prescriptionstatus% and pt.rxNumber like %:rxNo%  and pt.cre8PrescriptionNo like %:rxPrescriptionNo%")
	int countByPhysicianFilter(@Param("id") int id, @Param("physicianName") String physicianName, @Param("patientName") String patientName, 
			@Param("presriptionDate") Date presriptionDate, @Param("presriptionToDate") Date presriptionToDate,
			@Param("prescriptionstatus") String prescriptionstatus, @Param("rxNo") String rxNo, @Param("rxPrescriptionNo") String rxPrescriptionNo);
	
	
	
	
	@Query("select c from PrescriptionMaster c where c.physicianId=:id order by c.id desc")	
	Page<PrescriptionMaster> findByPhysicianId(@Param("id") int id, Pageable page);

	@Query("select pm from PrescriptionMaster pm, PrescriptionTransaction pt, PhysicianAccount pa, PatientAccount p "
			+ "where pm.physicianId=pa.id and pm.patientId=p.id and pm.id=pt.prescriptionId and "
			+ "pm.physicianId=:id and (pa.physicianName like %:physicianName% and p.patientName like %:patientName%) "
			+ "and pm.cre8PrescriptionNo like %:prescriptionNo% and pt.rxStatus like %:prescriptionstatus% and pt.rxNumber like %:rxNo%  and pt.cre8PrescriptionNo like %:rxPrescriptionNo% "
			+ "GROUP BY pm.id order by pm.id desc ")
	Page<PrescriptionMaster> findByPhysicianFilter(@Param("id") int id, @Param("physicianName") String physicianName, @Param("patientName") String patientName, 
			 @Param("prescriptionNo") String prescriptionNo, @Param("prescriptionstatus") String prescriptionstatus, @Param("rxNo") String rxNo, @Param("rxPrescriptionNo") String rxPrescriptionNo, Pageable page);

	@Query("select pm from PrescriptionMaster pm, PrescriptionTransaction pt, PhysicianAccount pa, PatientAccount p "
			+ "where pm.physicianId=pa.id and pm.patientId=p.id and pm.id=pt.prescriptionId and pm.physicianId=:id and "
			+ "(pa.physicianName like %:physicianName% and p.patientName like %:patientName%) and pm.prescriptionDate >=:presriptionDate and "
			+ "pm.cre8PrescriptionNo like %:prescriptionNo% and pt.rxStatus like %:prescriptionstatus% and pt.rxNumber like %:rxNo%  and pt.cre8PrescriptionNo like %:rxPrescriptionNo% "
			+ "GROUP BY pm.id order by pm.id desc ")
	Page<PrescriptionMaster> findByPhysicianFilterFromDate(@Param("id") int id, @Param("physicianName") String physicianName, 
			@Param("patientName") String patientName, @Param("presriptionDate") Date presriptionDate, @Param("prescriptionNo") String prescriptionNo, 
			@Param("prescriptionstatus") String prescriptionstatus, @Param("rxNo") String rxNo, @Param("rxPrescriptionNo") String rxPrescriptionNo, Pageable page);	
	
	@Query("select pm from PrescriptionMaster pm, PrescriptionTransaction pt, PhysicianAccount pa, PatientAccount p "
			+ "where pm.physicianId=pa.id and pm.patientId=p.id and pm.id=pt.prescriptionId and pm.physicianId=:id and "
			+ "(pa.physicianName like %:physicianName% and p.patientName like %:patientName%) and pm.prescriptionDate <=:presriptionToDate and "
			+ "pm.cre8PrescriptionNo like %:prescriptionNo% and pt.rxStatus like %:prescriptionstatus% and pt.rxNumber like %:rxNo%  and pt.cre8PrescriptionNo like %:rxPrescriptionNo% "
			+ "GROUP BY pm.id order by pm.id desc ")
	Page<PrescriptionMaster> findByPhysicianFilterToDate(@Param("id") int id, @Param("physicianName") String physicianName, 
			@Param("patientName") String patientName, @Param("presriptionToDate") Date presriptionToDate,@Param("prescriptionNo") String prescriptionNo,
			@Param("prescriptionstatus") String prescriptionstatus, @Param("rxNo") String rxNo, @Param("rxPrescriptionNo") String rxPrescriptionNo, Pageable page);
	
	
	@Query("select pm from PrescriptionMaster pm, PrescriptionTransaction pt, PhysicianAccount pa, PatientAccount p "
			+ "where pm.physicianId=pa.id and pm.patientId=p.id and pm.id=pt.prescriptionId and "
			+ "pm.physicianId=:id and (pa.physicianName like %:physicianName% and p.patientName like %:patientName%) and "
			+ "pm.prescriptionDate >=:presriptionDate and pm.prescriptionDate <=:presriptionToDate and pm.cre8PrescriptionNo like %:prescriptionNo% and "
			+ "pt.rxStatus like %:prescriptionstatus% and pt.rxNumber like %:rxNo%  and pt.cre8PrescriptionNo like %:rxPrescriptionNo% GROUP BY pm.id order by pm.id desc")
	Page<PrescriptionMaster> findByPhysicianFilter(@Param("id") int id, @Param("physicianName") String physicianName, 
			@Param("patientName") String patientName, @Param("presriptionDate") Date presriptionDate, @Param("presriptionToDate") Date presriptionToDate,
			@Param("prescriptionNo") String prescriptionNo,@Param("prescriptionstatus") String prescriptionstatus, @Param("rxNo") String rxNo, @Param("rxPrescriptionNo") String rxPrescriptionNo, Pageable page);
	
	
	@Query("select pm from PrescriptionMaster pm, PrescriptionTransaction pt, PhysicianAccount pa, PatientAccount p "
			+ "where pm.physicianId=pa.id and pm.patientId=p.id and pm.id=pt.prescriptionId and "
			+ "pm.physicianId=:id and (pa.physicianName like %:physicianName% and p.patientName like %:patientName%) "
			+ "and pt.rxStatus like %:prescriptionstatus% and pt.rxNumber like %:rxNo%  and pt.cre8PrescriptionNo like %:rxPrescriptionNo% GROUP BY pm.id order by pm.id desc ")
	Page<PrescriptionMaster> findByPhysicianFilter(@Param("id") int id, @Param("physicianName") String physicianName, @Param("patientName") String patientName, 
			 @Param("prescriptionstatus") String prescriptionstatus, @Param("rxNo") String rxNo, @Param("rxPrescriptionNo") String rxPrescriptionNo, Pageable page);

	@Query("select pm from PrescriptionMaster pm, PrescriptionTransaction pt, PhysicianAccount pa, PatientAccount p "
			+ "where pm.physicianId=pa.id and pm.patientId=p.id and pm.id=pt.prescriptionId and pm.physicianId=:id and "
			+ "(pa.physicianName like %:physicianName% and p.patientName like %:patientName%) and pm.prescriptionDate >=:presriptionDate and "
			+ "pt.rxStatus like %:prescriptionstatus% and pt.rxNumber like %:rxNo%   and pt.cre8PrescriptionNo like %:rxPrescriptionNo% GROUP BY pm.id order by pm.id desc ")
	Page<PrescriptionMaster> findByPhysicianFilterFromDate(@Param("id") int id, @Param("physicianName") String physicianName, 
			@Param("patientName") String patientName, @Param("presriptionDate") Date presriptionDate,  
			@Param("prescriptionstatus") String prescriptionstatus, @Param("rxNo") String rxNo, @Param("rxPrescriptionNo") String rxPrescriptionNo, Pageable page);	
	
	@Query("select pm from PrescriptionMaster pm, PrescriptionTransaction pt, PhysicianAccount pa, PatientAccount p "
			+ "where pm.physicianId=pa.id and pm.patientId=p.id and pm.id=pt.prescriptionId and pm.physicianId=:id and "
			+ "(pa.physicianName like %:physicianName% and p.patientName like %:patientName%) and pm.prescriptionDate <=:presriptionToDate and "
			+ "pt.rxStatus like %:prescriptionstatus% and pt.rxNumber like %:rxNo%   and pt.cre8PrescriptionNo like %:rxPrescriptionNo% GROUP BY pm.id order by pm.id desc ")
	Page<PrescriptionMaster> findByPhysicianFilterToDate(@Param("id") int id, @Param("physicianName") String physicianName, 
			@Param("patientName") String patientName, @Param("presriptionToDate") Date presriptionToDate,
			@Param("prescriptionstatus") String prescriptionstatus, @Param("rxNo") String rxNo, @Param("rxPrescriptionNo") String rxPrescriptionNo, Pageable page);
	
	
	@Query("select pm from PrescriptionMaster pm, PrescriptionTransaction pt, PhysicianAccount pa, PatientAccount p "
			+ "where pm.physicianId=pa.id and pm.patientId=p.id and pm.id=pt.prescriptionId and "
			+ "pm.physicianId=:id and (pa.physicianName like %:physicianName% and p.patientName like %:patientName%) and "
			+ "pm.prescriptionDate >=:presriptionDate and pm.prescriptionDate <=:presriptionToDate and "
			+ "pt.rxStatus like %:prescriptionstatus% and pt.rxNumber like %:rxNo%   and pt.cre8PrescriptionNo like %:rxPrescriptionNo% GROUP BY pm.id order by pm.id desc")
	Page<PrescriptionMaster> findByPhysicianFilter(@Param("id") int id, @Param("physicianName") String physicianName, 
			@Param("patientName") String patientName, @Param("presriptionDate") Date presriptionDate, @Param("presriptionToDate") Date presriptionToDate,
			@Param("prescriptionstatus") String prescriptionstatus, @Param("rxNo") String rxNo, @Param("rxPrescriptionNo") String rxPrescriptionNo, Pageable page);
		
	
	
	
	/********************  Group Director Log-in Queries ********************/
	@Query("select  COALESCE(count(c),0)  from PrescriptionMaster c where c.groupId=:id")	
	int findByGroupId(@Param("id") int id);
	
	@Query("select  COALESCE(count(pm),0)  from PrescriptionMaster pm, PrescriptionTransaction pt, PhysicianAccount pa, PatientAccount p "
			+ "where pm.physicianId=pa.id and pm.patientId=p.id and pm.id=pt.prescriptionId and "
			+ "pm.groupId=:id and (pa.physicianName like %:physicianName% and p.patientName like %:patientName%) and pm.cre8PrescriptionNo like %:prescriptionNo% "
			+ "and pt.rxStatus like %:prescriptionstatus% and pt.rxNumber like %:rxNo%   and pt.cre8PrescriptionNo like %:rxPrescriptionNo% ")
	int countByGroupFilter(@Param("id") int id, @Param("physicianName") String physicianName, @Param("patientName") String patientName,
			@Param("prescriptionNo") String prescriptionNo, @Param("prescriptionstatus") String prescriptionstatus, @Param("rxNo") String rxNo, @Param("rxPrescriptionNo") String rxPrescriptionNo);
	
	@Query("select  COALESCE(count(pm),0)  from PrescriptionMaster pm, PrescriptionTransaction pt, PhysicianAccount pa, PatientAccount p "
			+ "where pm.physicianId=pa.id and pm.patientId=p.id and pm.id=pt.prescriptionId and "
			+ "pm.groupId=:id and (pa.physicianName like %:physicianName% and p.patientName like %:patientName%) and pm.prescriptionDate >=:presriptionDate "
			+ "and pm.cre8PrescriptionNo like %:prescriptionNo% and pt.rxStatus like %:prescriptionstatus% and pt.rxNumber like %:rxNo%   and pt.cre8PrescriptionNo like %:rxPrescriptionNo% ")
	int countByGroupFilterFromDate(@Param("id") int id, @Param("physicianName") String physicianName, @Param("patientName") String patientName, 
			@Param("presriptionDate") Date presriptionDate, @Param("prescriptionNo") String prescriptionNo,
			@Param("prescriptionstatus") String prescriptionstatus, @Param("rxNo") String rxNo, @Param("rxPrescriptionNo") String rxPrescriptionNo);

	@Query("select  COALESCE(count(pm),0)  from PrescriptionMaster pm, PrescriptionTransaction pt, PhysicianAccount pa, PatientAccount p "
			+ "where pm.physicianId=pa.id and pm.patientId=p.id and pm.id=pt.prescriptionId and "
			+ "pm.groupId=:id and (pa.physicianName like %:physicianName% and p.patientName like %:patientName%) and pm.prescriptionDate <=:presriptionDate "
			+ "and pm.cre8PrescriptionNo like %:prescriptionNo% and pt.rxStatus like %:prescriptionstatus% and pt.rxNumber like %:rxNo%   and pt.cre8PrescriptionNo like %:rxPrescriptionNo% ")
	int countByGroupFilterToDate(@Param("id") int id, @Param("physicianName") String physicianName, @Param("patientName") String patientName, 
			@Param("presriptionDate") Date presriptionDate, @Param("prescriptionNo") String prescriptionNo, 
			@Param("prescriptionstatus") String prescriptionstatus, @Param("rxNo") String rxNo, @Param("rxPrescriptionNo") String rxPrescriptionNo);

	@Query("select  COALESCE(count(pm),0)  from PrescriptionMaster pm, PrescriptionTransaction pt, PhysicianAccount pa, PatientAccount p "
			+ "where pm.physicianId=pa.id and pm.patientId=p.id and pm.id=pt.prescriptionId and "
			+ "pm.groupId=:id and (pa.physicianName like %:physicianName% and p.patientName like %:patientName%) and pm.prescriptionDate >=:presriptionDate "
			+ "and pm.prescriptionDate <=:presriptionToDate and pm.cre8PrescriptionNo like %:prescriptionNo% and pt.rxStatus like %:prescriptionstatus% "
			+ "and pt.rxNumber like %:rxNo%   and pt.cre8PrescriptionNo like %:rxPrescriptionNo% ")
	int countByGroupFilter(@Param("id") int id, @Param("physicianName") String physicianName, @Param("patientName") String patientName, 
			@Param("presriptionDate") Date presriptionDate, @Param("presriptionToDate") Date presriptionToDate, @Param("prescriptionNo") String prescriptionNo,
			@Param("prescriptionstatus") String prescriptionstatus, @Param("rxNo") String rxNo, @Param("rxPrescriptionNo") String rxPrescriptionNo);

	
	
	
	@Query("select pm from PrescriptionMaster pm, PrescriptionTransaction pt, PhysicianAccount pa, PatientAccount p "
			+ "where pm.physicianId=pa.id and pm.patientId=p.id and pm.id=pt.prescriptionId and "
			+ "pm.groupId=:id and (pa.physicianName like %:physicianName% and p.patientName like %:patientName%) and pm.cre8PrescriptionNo like %:prescriptionNo% "
			+ "and pt.rxStatus like %:prescriptionstatus% and pt.rxNumber like %:rxNo%   and pt.cre8PrescriptionNo like %:rxPrescriptionNo% group by pm.id ")
	List<PrescriptionMaster> countByGroupFilterGroup(@Param("id") int id, @Param("physicianName") String physicianName, @Param("patientName") String patientName,
			@Param("prescriptionNo") String prescriptionNo, @Param("prescriptionstatus") String prescriptionstatus, @Param("rxNo") String rxNo, @Param("rxPrescriptionNo") String rxPrescriptionNo);
	
	@Query("select pm from PrescriptionMaster pm, PrescriptionTransaction pt, PhysicianAccount pa, PatientAccount p "
			+ "where pm.physicianId=pa.id and pm.patientId=p.id and pm.id=pt.prescriptionId and "
			+ "pm.groupId=:id and (pa.physicianName like %:physicianName% and p.patientName like %:patientName%) and pm.prescriptionDate >=:presriptionDate "
			+ "and pm.cre8PrescriptionNo like %:prescriptionNo% and pt.rxStatus like %:prescriptionstatus% and pt.rxNumber like %:rxNo%   and pt.cre8PrescriptionNo like %:rxPrescriptionNo% group by pm.id ")
	List<PrescriptionMaster> countByGroupFilterFromDateGroup(@Param("id") int id, @Param("physicianName") String physicianName, @Param("patientName") String patientName, 
			@Param("presriptionDate") Date presriptionDate, @Param("prescriptionNo") String prescriptionNo,
			@Param("prescriptionstatus") String prescriptionstatus, @Param("rxNo") String rxNo, @Param("rxPrescriptionNo") String rxPrescriptionNo);

	@Query("select pm from PrescriptionMaster pm, PrescriptionTransaction pt, PhysicianAccount pa, PatientAccount p "
			+ "where pm.physicianId=pa.id and pm.patientId=p.id and pm.id=pt.prescriptionId and "
			+ "pm.groupId=:id and (pa.physicianName like %:physicianName% and p.patientName like %:patientName%) and pm.prescriptionDate <=:presriptionDate "
			+ "and pm.cre8PrescriptionNo like %:prescriptionNo% and pt.rxStatus like %:prescriptionstatus% and pt.rxNumber like %:rxNo%   and pt.cre8PrescriptionNo like %:rxPrescriptionNo% group by pm.id ")
	List<PrescriptionMaster> countByGroupFilterToDateGroup(@Param("id") int id, @Param("physicianName") String physicianName, @Param("patientName") String patientName, 
			@Param("presriptionDate") Date presriptionDate, @Param("prescriptionNo") String prescriptionNo, 
			@Param("prescriptionstatus") String prescriptionstatus, @Param("rxNo") String rxNo, @Param("rxPrescriptionNo") String rxPrescriptionNo);

	@Query("select pm from PrescriptionMaster pm, PrescriptionTransaction pt, PhysicianAccount pa, PatientAccount p "
			+ "where pm.physicianId=pa.id and pm.patientId=p.id and pm.id=pt.prescriptionId and "
			+ "pm.groupId=:id and (pa.physicianName like %:physicianName% and p.patientName like %:patientName%) and pm.prescriptionDate >=:presriptionDate "
			+ "and pm.prescriptionDate <=:presriptionToDate and pm.cre8PrescriptionNo like %:prescriptionNo% and pt.rxStatus like %:prescriptionstatus% "
			+ "and pt.rxNumber like %:rxNo%   and pt.cre8PrescriptionNo like %:rxPrescriptionNo% group by pm.id ")
	List<PrescriptionMaster> countByGroupFilterGroup(@Param("id") int id, @Param("physicianName") String physicianName, @Param("patientName") String patientName, 
			@Param("presriptionDate") Date presriptionDate, @Param("presriptionToDate") Date presriptionToDate, @Param("prescriptionNo") String prescriptionNo,
			@Param("prescriptionstatus") String prescriptionstatus, @Param("rxNo") String rxNo, @Param("rxPrescriptionNo") String rxPrescriptionNo);

	

	
	
	
	@Query("select  COALESCE(count(pm),0)  from PrescriptionMaster pm, PrescriptionTransaction pt, PhysicianAccount pa, PatientAccount p "
			+ "where pm.physicianId=pa.id and pm.patientId=p.id and pm.id=pt.prescriptionId and "
			+ "pm.groupId=:id and (pa.physicianName like %:physicianName% and p.patientName like %:patientName%) "
			+ "and pt.rxStatus like %:prescriptionstatus% and pt.rxNumber like %:rxNo%   and pt.cre8PrescriptionNo like %:rxPrescriptionNo% ")
	int countByGroupFilter(@Param("id") int id, @Param("physicianName") String physicianName, @Param("patientName") String patientName,
			@Param("prescriptionstatus") String prescriptionstatus, @Param("rxNo") String rxNo, @Param("rxPrescriptionNo") String rxPrescriptionNo);
	
	@Query("select  COALESCE(count(pm),0)  from PrescriptionMaster pm, PrescriptionTransaction pt, PhysicianAccount pa, PatientAccount p "
			+ "where pm.physicianId=pa.id and pm.patientId=p.id and pm.id=pt.prescriptionId and "
			+ "pm.groupId=:id and (pa.physicianName like %:physicianName% and p.patientName like %:patientName%) and pm.prescriptionDate >=:presriptionDate "
			+ "and pm.prescriptionNumber=:prescriptionNo and pt.rxStatus like %:prescriptionstatus% and pt.rxNumber like %:rxNo%   and pt.cre8PrescriptionNo like %:rxPrescriptionNo% ")
	int countByGroupFilterFromDate(@Param("id") int id, @Param("physicianName") String physicianName, @Param("patientName") String patientName, 
			@Param("presriptionDate") Date presriptionDate, @Param("prescriptionstatus") String prescriptionstatus, @Param("rxNo") String rxNo, @Param("rxPrescriptionNo") String rxPrescriptionNo);

	@Query("select  COALESCE(count(pm),0)  from PrescriptionMaster pm, PrescriptionTransaction pt, PhysicianAccount pa, PatientAccount p "
			+ "where pm.physicianId=pa.id and pm.patientId=p.id and pm.id=pt.prescriptionId and "
			+ "pm.groupId=:id and (pa.physicianName like %:physicianName% and p.patientName like %:patientName%) and pm.prescriptionDate <=:presriptionDate "
			+ "and pm.prescriptionNumber=:prescriptionNo and pt.rxStatus like %:prescriptionstatus% and pt.rxNumber like %:rxNo%   and pt.cre8PrescriptionNo like %:rxPrescriptionNo% ")
	int countByGroupFilterToDate(@Param("id") int id, @Param("physicianName") String physicianName, @Param("patientName") String patientName, 
			@Param("presriptionDate") Date presriptionDate, @Param("prescriptionstatus") String prescriptionstatus, @Param("rxNo") String rxNo, @Param("rxPrescriptionNo") String rxPrescriptionNo);
	

	@Query("select  COALESCE(count(pm),0)  from PrescriptionMaster pm, PrescriptionTransaction pt, PhysicianAccount pa, PatientAccount p "
			+ "where pm.physicianId=pa.id and pm.patientId=p.id and pm.id=pt.prescriptionId and "
			+ "pm.groupId=:id and (pa.physicianName like %:physicianName% and p.patientName like %:patientName%) and pm.prescriptionDate >=:presriptionDate "
			+ "and pm.prescriptionDate <=:presriptionToDate and pt.rxStatus like %:prescriptionstatus% and pt.rxNumber like %:rxNo%   and pt.cre8PrescriptionNo like %:rxPrescriptionNo% ")
	int countByGroupFilter(@Param("id") int id, @Param("physicianName") String physicianName, @Param("patientName") String patientName, 
			@Param("presriptionDate") Date presriptionDate, @Param("presriptionToDate") Date presriptionToDate, 
			@Param("prescriptionstatus") String prescriptionstatus, @Param("rxNo") String rxNo, @Param("rxPrescriptionNo") String rxPrescriptionNo);

	
	@Query("select c from PrescriptionMaster c where c.groupId=:id order by c.id desc")	
	Page<PrescriptionMaster> findByGroupId(@Param("id") int id, Pageable page);

	
	@Query("select pm from PrescriptionMaster pm, PrescriptionTransaction pt, PhysicianAccount pa, PatientAccount p "
			+ "where pm.physicianId=pa.id and pm.patientId=p.id and pm.id=pt.prescriptionId and "
			+ "pm.groupId=:id and (pa.physicianName like %:physicianName% and p.patientName like %:patientName%) "
			+ "and pm.cre8PrescriptionNo like %:prescriptionNo% and pt.rxStatus like %:prescriptionstatus% and pt.rxNumber like %:rxNo%   and pt.cre8PrescriptionNo like %:rxPrescriptionNo% GROUP BY pm.id order by pm.id desc ")
	Page<PrescriptionMaster> findByGroupFilter(@Param("id") int id, @Param("physicianName") String physicianName, 
			@Param("patientName") String patientName, @Param("prescriptionNo") String prescriptionNo, @Param("prescriptionstatus") String prescriptionstatus, 
			@Param("rxNo") String rxNo, @Param("rxPrescriptionNo") String rxPrescriptionNo, Pageable page);

	@Query("select pm from PrescriptionMaster pm, PrescriptionTransaction pt, PhysicianAccount pa, PatientAccount p "
			+ "where pm.physicianId=pa.id and pm.patientId=p.id and pm.id=pt.prescriptionId and "
			+ "pm.groupId=:id and (pa.physicianName like %:physicianName% and p.patientName like %:patientName%) and "
			+ "pm.prescriptionDate >=:presriptionDate and pm.cre8PrescriptionNo like %:prescriptionNo% and pt.rxStatus like %:prescriptionstatus% "
			+ "and pt.rxNumber like %:rxNo%   and pt.cre8PrescriptionNo like %:rxPrescriptionNo% GROUP BY pm.id order by pm.id desc ")
	Page<PrescriptionMaster> findByGroupFilterFromDate(@Param("id") int id, @Param("physicianName") String physicianName, 
			@Param("patientName") String patientName, @Param("presriptionDate") Date presriptionDate,@Param("prescriptionNo") String prescriptionNo,
			@Param("prescriptionstatus") String prescriptionstatus, @Param("rxNo") String rxNo, @Param("rxPrescriptionNo") String rxPrescriptionNo, Pageable page);

	@Query("select pm from PrescriptionMaster pm, PrescriptionTransaction pt, PhysicianAccount pa, PatientAccount p "
			+ "where pm.physicianId=pa.id and pm.patientId=p.id and pm.id=pt.prescriptionId and "
			+ "pm.groupId=:id and (pa.physicianName like %:physicianName% and p.patientName like %:patientName%) and "
			+ "pm.prescriptionDate <=:presriptionDate and pm.cre8PrescriptionNo like %:prescriptionNo% and pt.rxStatus like %:prescriptionstatus% "
			+ "and pt.rxNumber like %:rxNo%   and pt.cre8PrescriptionNo like %:rxPrescriptionNo% GROUP BY pm.id order by pm.id desc ")
	Page<PrescriptionMaster> findByGroupFilterToDate(@Param("id") int id, @Param("physicianName") String physicianName, 
			@Param("patientName") String patientName, @Param("presriptionDate") Date presriptionDate,@Param("prescriptionNo") String prescriptionNo, 
			@Param("prescriptionstatus") String prescriptionstatus, @Param("rxNo") String rxNo, @Param("rxPrescriptionNo") String rxPrescriptionNo, Pageable page);

	@Query("select pm from PrescriptionMaster pm, PrescriptionTransaction pt,PhysicianAccount pa, PatientAccount p "
			+ "where pm.physicianId=pa.id and pm.patientId=p.id and "
			+ "pm.groupId=:id and (pa.physicianName like %:physicianName% and p.patientName like %:patientName%) and "
			+ "pm.prescriptionDate >=:presriptionDate and pm.prescriptionDate <=:presriptionToDate and pm.cre8PrescriptionNo like %:prescriptionNo% "
			+ "and pt.rxStatus like %:prescriptionstatus% and pt.rxNumber like %:rxNo%   and pt.cre8PrescriptionNo like %:rxPrescriptionNo% GROUP BY pm.id  order by pm.id desc ")
	Page<PrescriptionMaster> findByGroupFilter(@Param("id") int id, @Param("physicianName") String physicianName, @Param("patientName") String patientName, 
			@Param("presriptionDate") Date presriptionDate, @Param("presriptionToDate") Date presriptionToDate, @Param("prescriptionNo") String prescriptionNo,
			@Param("prescriptionstatus") String prescriptionstatus, @Param("rxNo") String rxNo, @Param("rxPrescriptionNo") String rxPrescriptionNo, Pageable page);

	
	
	@Query("select pm from PrescriptionMaster pm, PrescriptionTransaction pt, PhysicianAccount pa, PatientAccount p "
			+ "where pm.physicianId=pa.id and pm.patientId=p.id and pm.id=pt.prescriptionId and "
			+ "pm.groupId=:id and (pa.physicianName like %:physicianName% and p.patientName like %:patientName%) "
			+ "and pt.rxStatus like %:prescriptionstatus% and pt.rxNumber like %:rxNo%   and pt.cre8PrescriptionNo like %:rxPrescriptionNo% GROUP BY pm.id order by pm.id desc ")
	Page<PrescriptionMaster> findByGroupFilter(@Param("id") int id, @Param("physicianName") String physicianName, 
			@Param("patientName") String patientName, @Param("prescriptionstatus") String prescriptionstatus, @Param("rxNo") String rxNo, @Param("rxPrescriptionNo") String rxPrescriptionNo, 
			Pageable page);

	@Query("select pm from PrescriptionMaster pm, PrescriptionTransaction pt, PhysicianAccount pa, PatientAccount p "
			+ "where pm.physicianId=pa.id and pm.patientId=p.id and pm.id=pt.prescriptionId and "
			+ "pm.groupId=:id and (pa.physicianName like %:physicianName% and p.patientName like %:patientName%) and "
			+ "pm.prescriptionDate >=:presriptionDate and pt.rxStatus like %:prescriptionstatus% and pt.rxNumber like %:rxNo%   and pt.cre8PrescriptionNo like %:rxPrescriptionNo% "
			+ "GROUP BY pm.id order by pm.id desc ")
	Page<PrescriptionMaster> findByGroupFilterFromDate(@Param("id") int id, @Param("physicianName") String physicianName, 
			@Param("patientName") String patientName, @Param("presriptionDate") Date presriptionDate, 
			@Param("prescriptionstatus") String prescriptionstatus, @Param("rxNo") String rxNo, @Param("rxPrescriptionNo") String rxPrescriptionNo, Pageable page);

	@Query("select pm from PrescriptionMaster pm, PrescriptionTransaction pt, PhysicianAccount pa, PatientAccount p "
			+ "where pm.physicianId=pa.id and pm.patientId=p.id and pm.id=pt.prescriptionId and "
			+ "pm.groupId=:id and (pa.physicianName like %:physicianName% and p.patientName like %:patientName%) and "
			+ "pm.prescriptionDate <=:presriptionDate and pt.rxStatus like %:prescriptionstatus% and pt.rxNumber like %:rxNo%   and pt.cre8PrescriptionNo like %:rxPrescriptionNo% "
			+ "GROUP BY pm.id order by pm.id desc ")
	Page<PrescriptionMaster> findByGroupFilterToDate(@Param("id") int id, @Param("physicianName") String physicianName, 
			@Param("patientName") String patientName, @Param("presriptionDate") Date presriptionDate, 
			@Param("prescriptionstatus") String prescriptionstatus, @Param("rxNo") String rxNo, @Param("rxPrescriptionNo") String rxPrescriptionNo, Pageable page);

	@Query("select pm from PrescriptionMaster pm, PrescriptionTransaction pt, PhysicianAccount pa, PatientAccount p "
			+ "where pm.physicianId=pa.id and pm.patientId=p.id and pm.id=pt.prescriptionId and "
			+ "pm.groupId=:id and (pa.physicianName like %:physicianName% and p.patientName like %:patientName%) and "
			+ "pm.prescriptionDate >=:presriptionDate and pm.prescriptionDate <=:presriptionToDate and pt.rxStatus like %:prescriptionstatus% "
			+ "and pt.rxNumber like %:rxNo%   and pt.cre8PrescriptionNo like %:rxPrescriptionNo% GROUP BY pm.id order by pm.id desc ")
	Page<PrescriptionMaster> findByGroupFilter(@Param("id") int id, @Param("physicianName") String physicianName, @Param("patientName") String patientName, 
			@Param("presriptionDate") Date presriptionDate, @Param("presriptionToDate") Date presriptionToDate,
			@Param("prescriptionstatus") String prescriptionstatus, @Param("rxNo") String rxNo, @Param("rxPrescriptionNo") String rxPrescriptionNo, Pageable page);

	
	/******************************* Patient Log-in Queries **************/
	@Query("select  COALESCE(count(c),0)  from PrescriptionMaster c where c.patientId=:id")	
	int findByPatientId(@Param("id") int id);

	@Query("select  COALESCE(count(pm),0)  from PrescriptionMaster pm, PrescriptionTransaction pt, PhysicianAccount pa, PatientAccount p "
			+ "where pm.physicianId=pa.id and pm.patientId=p.id  and pm.id=pt.prescriptionId and "
			+ "pm.patientId=:patientId and pm.cre8PrescriptionNo like %:prescriptionNo% "
			+ "and pt.rxStatus like %:prescriptionstatus% and pt.rxNumber like %:rxNo%   and pt.cre8PrescriptionNo like %:rxPrescriptionNo%  ")
	int countByPatientFilterWithoutDate(@Param("patientId") int patientId, @Param("prescriptionNo") String prescriptionNo, 
			@Param("prescriptionstatus") String prescriptionstatus, @Param("rxNo") String rxNo, @Param("rxPrescriptionNo") String rxPrescriptionNo);
	
	@Query("select  COALESCE(count(pm),0)  from PrescriptionMaster pm, PrescriptionTransaction pt, PhysicianAccount pa, PatientAccount p "
			+ "where pm.physicianId=pa.id and pm.patientId=p.id  and pm.id=pt.prescriptionId and "
			+ "pm.patientId=:patientId and pt.rxStatus like %:prescriptionstatus% and pt.rxNumber like %:rxNo%  and pt.cre8PrescriptionNo like %:rxPrescriptionNo%  ")
	int countByPatientFilterWithoutDate(@Param("patientId") int patientId, @Param("prescriptionstatus") String prescriptionstatus, @Param("rxNo") String rxNo, @Param("rxPrescriptionNo") String rxPrescriptionNo);
	
	
	@Query("select  COALESCE(count(pm),0)  from PrescriptionMaster pm, PrescriptionTransaction pt, PhysicianAccount pa, PatientAccount p "
			+ "where pm.physicianId=pa.id and pm.patientId=p.id  and pm.id=pt.prescriptionId and "
			+ "pm.patientId=:patientId and pm.prescriptionDate >=:presriptionDate and pm.cre8PrescriptionNo like %:prescriptionNo% "
			+ "and pt.rxStatus like %:prescriptionstatus% and pt.rxNumber like %:rxNo%   and pt.cre8PrescriptionNo like %:rxPrescriptionNo% ")
	int countByPatientFilterFromDate(@Param("patientId") int patientId, @Param("presriptionDate") Date presriptionDate,
			@Param("prescriptionNo") String prescriptionNo, @Param("prescriptionstatus") String prescriptionstatus, @Param("rxNo") String rxNo, @Param("rxPrescriptionNo") String rxPrescriptionNo);
	
	@Query("select  COALESCE(count(pm),0)  from PrescriptionMaster pm, PrescriptionTransaction pt, PhysicianAccount pa, PatientAccount p "
			+ "where pm.physicianId=pa.id and pm.patientId=p.id  and pm.id=pt.prescriptionId and "
			+ "pm.patientId=:patientId and pm.prescriptionDate <=:presriptionDate and pm.cre8PrescriptionNo like %:prescriptionNo% "
			+ "and pt.rxStatus like %:prescriptionstatus% and pt.rxNumber like %:rxNo%   and pt.cre8PrescriptionNo like %:rxPrescriptionNo% ")
	int countByPatientFilterToDate(@Param("patientId") int patientId, @Param("presriptionDate") Date presriptionDate,
			@Param("prescriptionNo") String prescriptionNo, @Param("prescriptionstatus") String prescriptionstatus, @Param("rxNo") String rxNo, @Param("rxPrescriptionNo") String rxPrescriptionNo);


	@Query("select  COALESCE(count(pm),0)  from PrescriptionMaster pm, PrescriptionTransaction pt, PhysicianAccount pa, PatientAccount p "
			+ "where pm.physicianId=pa.id and pm.patientId=p.id  and pm.id=pt.prescriptionId and "
			+ "pm.patientId=:patientId and pm.prescriptionDate >=:presriptionDate and pm.prescriptionDate <=:presriptionToDate "
			+ "and pm.cre8PrescriptionNo like %:prescriptionNo% and pt.rxStatus like %:prescriptionstatus% and pt.rxNumber like %:rxNo%   and pt.cre8PrescriptionNo like %:rxPrescriptionNo% ")
	int countByPatientFilter(@Param("patientId") int patientId, @Param("presriptionDate") Date presriptionDate, 
			@Param("presriptionToDate") Date presriptionToDate, @Param("prescriptionNo") String prescriptionNo,
			@Param("prescriptionstatus") String prescriptionstatus, @Param("rxNo") String rxNo, @Param("rxPrescriptionNo") String rxPrescriptionNo);
	
	
	/****************** **/
	@Query("select pm from PrescriptionMaster pm, PrescriptionTransaction pt, PhysicianAccount pa, PatientAccount p "
			+ "where pm.physicianId=pa.id and pm.patientId=p.id  and pm.id=pt.prescriptionId and "
			+ "pm.patientId=:patientId and pm.cre8PrescriptionNo like %:prescriptionNo% "
			+ "and pt.rxStatus like %:prescriptionstatus% and pt.rxNumber like %:rxNo%   and pt.cre8PrescriptionNo like %:rxPrescriptionNo% group by pm.id ")
	List<PrescriptionMaster> countByPatientFilterWithoutDateGroup(@Param("patientId") int patientId, @Param("prescriptionNo") String prescriptionNo, 
			@Param("prescriptionstatus") String prescriptionstatus, @Param("rxNo") String rxNo, @Param("rxPrescriptionNo") String rxPrescriptionNo);
	
	@Query("select pm from PrescriptionMaster pm, PrescriptionTransaction pt, PhysicianAccount pa, PatientAccount p "
			+ "where pm.physicianId=pa.id and pm.patientId=p.id  and pm.id=pt.prescriptionId and "
			+ "pm.patientId=:patientId and pt.rxStatus like %:prescriptionstatus% and pt.rxNumber like %:rxNo%   and pt.cre8PrescriptionNo like %:rxPrescriptionNo% group by pm.id ")
	List<PrescriptionMaster> countByPatientFilterWithoutDateGroup(@Param("patientId") int patientId, @Param("prescriptionstatus") String prescriptionstatus, @Param("rxNo") String rxNo, @Param("rxPrescriptionNo") String rxPrescriptionNo);
	
	
	@Query("select pm from PrescriptionMaster pm, PrescriptionTransaction pt, PhysicianAccount pa, PatientAccount p "
			+ "where pm.physicianId=pa.id and pm.patientId=p.id  and pm.id=pt.prescriptionId and "
			+ "pm.patientId=:patientId and pm.prescriptionDate >=:presriptionDate and pm.cre8PrescriptionNo like %:prescriptionNo% "
			+ "and pt.rxStatus like %:prescriptionstatus% and pt.rxNumber like %:rxNo%   and pt.cre8PrescriptionNo like %:rxPrescriptionNo% group by pm.id ")
	List<PrescriptionMaster> countByPatientFilterFromDateGroup(@Param("patientId") int patientId, @Param("presriptionDate") Date presriptionDate,
			@Param("prescriptionNo") String prescriptionNo, @Param("prescriptionstatus") String prescriptionstatus, @Param("rxNo") String rxNo, @Param("rxPrescriptionNo") String rxPrescriptionNo);
	
	@Query("select pm from PrescriptionMaster pm, PrescriptionTransaction pt, PhysicianAccount pa, PatientAccount p "
			+ "where pm.physicianId=pa.id and pm.patientId=p.id  and pm.id=pt.prescriptionId and "
			+ "pm.patientId=:patientId and pm.prescriptionDate <=:presriptionDate and pm.cre8PrescriptionNo like %:prescriptionNo% "
			+ "and pt.rxStatus like %:prescriptionstatus% and pt.rxNumber like %:rxNo%   and pt.cre8PrescriptionNo like %:rxPrescriptionNo% group by pm.id ")
	List<PrescriptionMaster> countByPatientFilterToDateGroup(@Param("patientId") int patientId, @Param("presriptionDate") Date presriptionDate,
			@Param("prescriptionNo") String prescriptionNo, @Param("prescriptionstatus") String prescriptionstatus, @Param("rxNo") String rxNo, @Param("rxPrescriptionNo") String rxPrescriptionNo);

	
	@Query("select pm  from PrescriptionMaster pm, PrescriptionTransaction pt, PhysicianAccount pa, PatientAccount p "
			+ "where pm.physicianId=pa.id and pm.patientId=p.id  and pm.id=pt.prescriptionId and "
			+ "pm.patientId=:patientId and pm.prescriptionDate >=:presriptionDate and pm.prescriptionDate <=:presriptionToDate "
			+ "and pm.cre8PrescriptionNo like %:prescriptionNo% and pt.rxStatus like %:prescriptionstatus% and pt.rxNumber like %:rxNo%   and pt.cre8PrescriptionNo like %:rxPrescriptionNo% group by pm.id ")
	List<PrescriptionMaster> countByPatientFilterGroup(@Param("patientId") int patientId, @Param("presriptionDate") Date presriptionDate, 
			@Param("presriptionToDate") Date presriptionToDate, @Param("prescriptionNo") String prescriptionNo,
			@Param("prescriptionstatus") String prescriptionstatus, @Param("rxNo") String rxNo, @Param("rxPrescriptionNo") String rxPrescriptionNo);	
	
	
	
	

	
	@Query("select  COALESCE(count(pm),0)  from PrescriptionMaster pm, PrescriptionTransaction pt, PhysicianAccount pa, PatientAccount p "
			+ "where pm.physicianId=pa.id and pm.patientId=p.id  and pm.id=pt.prescriptionId and "
			+ "pm.patientId=:patientId and pm.prescriptionDate >=:presriptionDate and pt.rxStatus like %:prescriptionstatus% "
			+ "and pt.rxNumber like %:rxNo%   and pt.cre8PrescriptionNo like %:rxPrescriptionNo% ")
	int countByPatientFilterFromDate(@Param("patientId") int patientId, @Param("presriptionDate") Date presriptionDate,
			@Param("prescriptionstatus") String prescriptionstatus, @Param("rxNo") String rxNo, @Param("rxPrescriptionNo") String rxPrescriptionNo);
	
	@Query("select  COALESCE(count(pm),0)  from PrescriptionMaster pm, PrescriptionTransaction pt, PhysicianAccount pa, PatientAccount p "
			+ "where pm.physicianId=pa.id and pm.patientId=p.id  and pm.id=pt.prescriptionId and "
			+ "pm.patientId=:patientId and pm.prescriptionDate <=:presriptionDate and pt.rxStatus like %:prescriptionstatus% "
			+ "and pt.rxNumber like %:rxNo%   and pt.cre8PrescriptionNo like %:rxPrescriptionNo% ")
	int countByPatientFilterToDate(@Param("patientId") int patientId, @Param("presriptionDate") Date presriptionDate,
			@Param("prescriptionstatus") String prescriptionstatus, @Param("rxNo") String rxNo, @Param("rxPrescriptionNo") String rxPrescriptionNo);

	@Query("select  COALESCE(count(pm),0)  from PrescriptionMaster pm, PrescriptionTransaction pt, PhysicianAccount pa, PatientAccount p "
			+ "where pm.physicianId=pa.id and pm.patientId=p.id  and pm.id=pt.prescriptionId and "
			+ "pm.patientId=:patientId and pm.prescriptionDate >=:presriptionDate and pm.prescriptionDate <=:presriptionToDate "
			+ "and pt.rxStatus like %:prescriptionstatus% and pt.rxNumber like %:rxNo%   and pt.cre8PrescriptionNo like %:rxPrescriptionNo% ")
	int countByPatientFilter(@Param("patientId") int patientId, @Param("presriptionDate") Date presriptionDate, 
			@Param("presriptionToDate") Date presriptionToDate, @Param("prescriptionstatus") String prescriptionstatus, @Param("rxNo") String rxNo, @Param("rxPrescriptionNo") String rxPrescriptionNo);	
	
	
	@Query("select c from PrescriptionMaster c where c.patientId=:id order by c.id desc")	
	Page<PrescriptionMaster> findByPatientId(@Param("id") int id, Pageable page);

	@Query("select pm from PrescriptionMaster pm, PrescriptionTransaction pt, PhysicianAccount pa, PatientAccount p "
			+ "where pm.physicianId=pa.id and pm.patientId=p.id and pm.patientId=:id and pm.id=pt.prescriptionId and "
			+ "pm.cre8PrescriptionNo like %:prescriptionNo% and "
			+ "pt.rxStatus like %:prescriptionstatus% and pt.rxNumber like %:rxNo%   and pt.cre8PrescriptionNo like %:rxPrescriptionNo% GROUP BY pm.id  order by pm.id desc ")
	Page<PrescriptionMaster> findByPatientIdWithoutDate(@Param("id") int id, @Param("prescriptionNo") String prescriptionNo,   
			@Param("prescriptionstatus") String prescriptionstatus, @Param("rxNo") String rxNo, @Param("rxPrescriptionNo") String rxPrescriptionNo, Pageable page);
	
	@Query("select pm from PrescriptionMaster pm, PrescriptionTransaction pt, PhysicianAccount pa, PatientAccount p "
			+ "where pm.physicianId=pa.id and pm.patientId=p.id and pm.patientId=:id and pm.id=pt.prescriptionId and "
			+ "pt.rxStatus like %:prescriptionstatus% and pt.rxNumber like %:rxNo%   and pt.cre8PrescriptionNo like %:rxPrescriptionNo% GROUP BY pm.id  order by pm.id desc ")
	Page<PrescriptionMaster> findByPatientIdWithoutDate(@Param("id") int id, @Param("prescriptionstatus") String prescriptionstatus, 
			@Param("rxNo") String rxNo, @Param("rxPrescriptionNo") String rxPrescriptionNo, Pageable page);
	
	
	
	@Query("select pm from PrescriptionMaster pm, PrescriptionTransaction pt, PhysicianAccount pa, PatientAccount p "
			+ "where pm.physicianId=pa.id and pm.patientId=p.id and pm.patientId=:id and pm.id=pt.prescriptionId and "
			+ "pm.prescriptionDate >=:presriptionDate and pm.cre8PrescriptionNo like %:prescriptionNo% and "
			+ "pt.rxStatus like %:prescriptionstatus% and pt.rxNumber like %:rxNo%   and pt.cre8PrescriptionNo like %:rxPrescriptionNo% GROUP BY pm.id  order by pm.id desc ")
	Page<PrescriptionMaster> findByPatientIdFromDate(@Param("id") int id, @Param("presriptionDate") Date presriptionDate, 
			@Param("prescriptionNo") String prescriptionNo,@Param("prescriptionstatus") String prescriptionstatus, @Param("rxNo") String rxNo, @Param("rxPrescriptionNo") String rxPrescriptionNo, Pageable page);

	@Query("select pm from PrescriptionMaster pm, PrescriptionTransaction pt, PhysicianAccount pa, PatientAccount p "
			+ "where pm.physicianId=pa.id and pm.patientId=p.id and pm.patientId=:id and pm.id=pt.prescriptionId and "
			+ "pm.prescriptionDate <=:presriptionDate and pm.cre8PrescriptionNo like %:prescriptionNo% and "
			+ "pt.rxStatus like %:prescriptionstatus% and pt.rxNumber like %:rxNo%   and pt.cre8PrescriptionNo like %:rxPrescriptionNo% GROUP BY pm.id order by pm.id desc ")
	Page<PrescriptionMaster> findByPatientIdToDate(@Param("id") int id, @Param("presriptionDate") Date presriptionDate,
			@Param("prescriptionNo") String prescriptionNo,@Param("prescriptionstatus") String prescriptionstatus, @Param("rxNo") String rxNo, @Param("rxPrescriptionNo") String rxPrescriptionNo, Pageable page);

	@Query("select pm from PrescriptionMaster pm, PrescriptionTransaction pt, PhysicianAccount pa, PatientAccount p "
			+ "where pm.physicianId=pa.id and pm.patientId=p.id and pm.id=pt.prescriptionId and "
			+ "pm.patientId=:id and pm.prescriptionDate >=:presriptionDate and pm.prescriptionDate <=:presriptionToDate and "
			+ "pm.cre8PrescriptionNo like %:prescriptionNo% and pt.rxStatus like %:prescriptionstatus% and pt.rxNumber like %:rxNo%   and pt.cre8PrescriptionNo like %:rxPrescriptionNo% "
			+ "GROUP BY pm.id  order by pm.id desc ")
	Page<PrescriptionMaster> findByPatientId(@Param("id") int id, @Param("presriptionDate") Date presriptionDate, 
			@Param("presriptionToDate") Date presriptionToDate, @Param("prescriptionNo") String prescriptionNo, 
			@Param("prescriptionstatus") String prescriptionstatus, @Param("rxNo") String rxNo, @Param("rxPrescriptionNo") String rxPrescriptionNo, Pageable page);
	
	@Query("select pm from PrescriptionMaster pm, PrescriptionTransaction pt, PhysicianAccount pa, PatientAccount p "
			+ "where pm.physicianId=pa.id and pm.patientId=p.id and pm.patientId=:id and pm.id=pt.prescriptionId and "
			+ "pm.prescriptionDate >=:presriptionDate and pt.rxStatus like %:prescriptionstatus% and pt.rxNumber like %:rxNo%   and pt.cre8PrescriptionNo like %:rxPrescriptionNo% "
			+ "GROUP BY pm.id  order by pm.id desc ")
	Page<PrescriptionMaster> findByPatientIdFromDate(@Param("id") int id, @Param("presriptionDate") Date presriptionDate, 
			@Param("prescriptionstatus") String prescriptionstatus, @Param("rxNo") String rxNo, @Param("rxPrescriptionNo") String rxPrescriptionNo, Pageable page);

	@Query("select pm from PrescriptionMaster pm, PrescriptionTransaction pt, PhysicianAccount pa, PatientAccount p "
			+ "where pm.physicianId=pa.id and pm.patientId=p.id and pm.patientId=:id and pm.id=pt.prescriptionId and "
			+ "pm.prescriptionDate <=:presriptionDate and pt.rxStatus like %:prescriptionstatus% and pt.rxNumber like %:rxNo%   and pt.cre8PrescriptionNo like %:rxPrescriptionNo% "
			+ "GROUP BY pm.id order by pm.id desc ")
	Page<PrescriptionMaster> findByPatientIdToDate(@Param("id") int id, @Param("presriptionDate") Date presriptionDate,
			@Param("prescriptionstatus") String prescriptionstatus, @Param("rxNo") String rxNo, @Param("rxPrescriptionNo") String rxPrescriptionNo, Pageable page);

	@Query("select pm from PrescriptionMaster pm, PrescriptionTransaction pt, PhysicianAccount pa, PatientAccount p "
			+ "where pm.physicianId=pa.id and pm.patientId=p.id and pm.id=pt.prescriptionId and "
			+ "pm.patientId=:id and pm.prescriptionDate >=:presriptionDate and pm.prescriptionDate <=:presriptionToDate and "
			+ "pt.rxStatus like %:prescriptionstatus% and pt.rxNumber like %:rxNo%   and pt.cre8PrescriptionNo like %:rxPrescriptionNo% GROUP BY pm.id  order by pm.id desc ")
	Page<PrescriptionMaster> findByPatientId(@Param("id") int id, @Param("presriptionDate") Date presriptionDate, 
			@Param("presriptionToDate") Date presriptionToDate, @Param("prescriptionstatus") String prescriptionstatus, @Param("rxNo") String rxNo, @Param("rxPrescriptionNo") String rxPrescriptionNo, 
			Pageable page);
	
	/*
	---------------- Not used --------------- 
	@Query("select pm from PrescriptionMaster pm, PhysicianAccount pa, PatientAccount p where pm.physicianId=pa.id and pm.patientId=p.id and "
			+ "pm.patientId=:id and (pa.physicianName like %:physicianName% and p.patientName like %:patientName%)  order by pm.id desc ")
	Page<PrescriptionMaster> findByPatientFilter(@Param("id") int id, @Param("physicianName") String physicianName, @Param("patientName") String patientName, 
			Pageable page);
	
	@Query("select  COALESCE(count(pm),0)  from PrescriptionMaster pm, PhysicianAccount pa, PatientAccount p where pm.physicianId=pa.id and pm.patientId=p.id and "
			+ "pm.patientId=:id and (pa.physicianName like %:physicianName% and p.patientName like %:patientName%)   ")
	int countByPatientFilter(@Param("id") int id, @Param("physicianName") String physicianName, @Param("patientName") String patientName,@Param("prescriptionNo") String prescriptionNo,@Param("prescriptionstatus") String prescriptionstatus);
	
	@Query("select  COALESCE(count(pm),0)  from PrescriptionMaster pm, PhysicianAccount pa, PatientAccount p where pm.physicianId=pa.id and pm.patientId=p.id and "
			+ "pm.patientId=:id and (pa.physicianName like %:physicianName% and p.patientName like %:patientName%) and pm.prescriptionDate =:presriptionDate   ")
	int countByPatientFilter(@Param("id") int id, @Param("physicianName") String physicianName, @Param("patientName") String patientName, 
			@Param("presriptionDate") Date presriptionDate,@Param("prescriptionNo") String prescriptionNo,@Param("prescriptionstatus") String prescriptionstatus);
	*/
	
	@Query("SELECT COALESCE(MAX(p.prescriptionNumber),0) from PrescriptionMaster p")
	int getMaxPrescriptionNumber();
	
	//@Query("select count(pt) from PrescriptionTransaction pt where (pt.rxNumber is null or pt.rxNumber='') and  writtenDate>CURDATE()-2 and writtenDate<CURDATE() GROUP BY pt.prescriptionId ")
	@Query("select  COALESCE(count(pm),0)  from PrescriptionMaster pm, PrescriptionTransaction pt "
			+ "where pm.id=pt.prescriptionId and pm.physicianId=:physicianId and (pt.rxNumber is null or pt.rxNumber='') and  pm.prescriptionDate>:fromDate and pm.prescriptionDate<=:toDate")
	int fetchNewPrescriptionCountByPrescriptionDate( @Param("physicianId") int physicianId, @Param("fromDate") java.util.Date fromDate, @Param("toDate") java.util.Date toDate);
	
	@Query("select  COALESCE(count(pm),0)  from PrescriptionMaster pm, PrescriptionTransaction pt "
			+ "where pm.id=pt.prescriptionId and pm.physicianId=:physicianId and (pt.rxNumber is null or pt.rxNumber='') and  pm.prescriptionDate>:fromDate and pm.prescriptionDate<=:toDate")
	int fetchNewPrescriptionWeekCountByPrescriptionDate( @Param("physicianId") int physicianId, @Param("fromDate") java.util.Date fromDate, @Param("toDate") java.util.Date toDate);
	
	@Query("select  COALESCE(count(pm),0)  from PrescriptionMaster pm, PrescriptionTransaction pt "
			+ "where pm.id=pt.prescriptionId and pm.physicianId=:physicianId and (pt.rxNumber is null or pt.rxNumber='') and  pm.prescriptionDate>:fromDate and pm.prescriptionDate<=:toDate")
	int fetchNewPrescriptionMonthCountByPrescriptionDate( @Param("physicianId") int physicianId, @Param("fromDate") java.util.Date fromDate, @Param("toDate") java.util.Date toDate);
	
	@Query("select  COALESCE(count(pm),0)  from PrescriptionMaster pm, PrescriptionTransaction pt "
			+ "where pm.id=pt.prescriptionId and pm.groupId=:groupId and (pt.rxNumber is null or pt.rxNumber='')  and  pm.prescriptionDate>:fromDate and pm.prescriptionDate<=:toDate")
	int fetchGroupNewPrescriptionCountByPrescriptionDate( @Param("groupId") int groupId, @Param("fromDate") java.util.Date fromDate, @Param("toDate") java.util.Date toDate);
	
	@Query("select  COALESCE(count(pm),0)  from PrescriptionMaster pm, PrescriptionTransaction pt "
			+ "where pm.id=pt.prescriptionId and pm.groupId=:groupId and (pt.rxNumber is null or pt.rxNumber='')  and  pm.prescriptionDate>:fromDate and pm.prescriptionDate<=:toDate")
	int fetchGroupNewPrescriptionWeekCountByPrescriptionDate( @Param("groupId") int groupId, @Param("fromDate") java.util.Date fromDate, @Param("toDate") java.util.Date toDate);
	
	@Query("select  COALESCE(count(pm),0)  from PrescriptionMaster pm, PrescriptionTransaction pt "
			+ "where pm.id=pt.prescriptionId and pm.groupId=:groupId and (pt.rxNumber is null or pt.rxNumber='')  and  pm.prescriptionDate>:fromDate and pm.prescriptionDate<=:toDate")
	int fetchGroupNewPrescriptionMonthCountByPrescriptionDate( @Param("groupId") int groupId, @Param("fromDate") java.util.Date fromDate, @Param("toDate") java.util.Date toDate);
	
	
	
	
	
	/**********************  Prescription List Report Quries *************************/
	/*
	select m.prescription_number, t.written_date, t.expire_date, t.rx_number,p.physician_name,pat.patient_name,
	t.item_name, t.origin, t.quantity, t.days_supply, t.refills, t.refills_remaining, t.refills_filled, t.rx_status
	from prescription_master m left join prescription_transaction t on m.prescription_id = t.prescription_id left join phy_info p 
	on m.physician_id = p.physician_id left join patient_profile pat on m.patient_id = pat.patient_id order by m.prescription_number
	*/
	
	@Query("select m.cre8PrescriptionNo from PrescriptionMaster m, PrescriptionTransaction t, PhysicianAccount p, PatientAccount pat, PhysicianGroup gph, GroupMaster g "
			+ "where m.physicianId=p.id and m.patientId=pat.id  and m.id=t.prescriptionId and gph.physicianId=p.id and g.id=gph.groupId and "
			+ "p.physicianName like %:physician% and pat.patientName like %:patient% and t.rxStatus like %:status% and COALESCE(g.groupName, '') like %:group% "
			+ "and COALESCE(t.rxNumber, '') like %:rxNo%   and t.cre8PrescriptionNo like %:rxPrescriptionNo% and m.cre8PrescriptionNo like %:prescriptionNo%  ")
	List<PrescriptionMaster> reportCountByFilter(@Param("physician") String physician, @Param("patient") String patient, @Param("status") String status, 
			@Param("group") String group, @Param("rxNo") String rxNo, @Param("rxPrescriptionNo") String rxPrescriptionNo, @Param("prescriptionNo") String prescriptionNo);

	@Query("select m.cre8PrescriptionNo  from PrescriptionMaster m, PrescriptionTransaction t, PhysicianAccount p, PatientAccount pat, PhysicianGroup gph, GroupMaster g "
			+ "where m.physicianId=p.id and m.patientId=pat.id  and m.id=t.prescriptionId and gph.physicianId=p.id and g.id=gph.groupId and "
			+ "p.physicianName like %:physician% and pat.patientName like %:patient% and t.rxStatus like %:status% and COALESCE(g.groupName, '') like %:group% "
			+ "and COALESCE(t.rxNumber, '') like %:rxNo%   and t.cre8PrescriptionNo like %:rxPrescriptionNo% "
			+ "and m.cre8PrescriptionNo like %:prescriptionNo% and t.writtenDate>=:presriptionFromDate")
	List<PrescriptionMaster> reportCountByFilterWithFromDate(@Param("physician") String physician, @Param("patient") String patient, @Param("status") String status,
			@Param("group") String group, @Param("rxNo") String rxNo, @Param("rxPrescriptionNo") String rxPrescriptionNo, @Param("prescriptionNo") String prescriptionNo, @Param("presriptionFromDate") Date presriptionFromDate);
	
	@Query("select m.cre8PrescriptionNo from PrescriptionMaster m, PrescriptionTransaction t, PhysicianAccount p, PatientAccount pat, PhysicianGroup gph, GroupMaster g "
			+ "where m.physicianId=p.id and m.patientId=pat.id  and m.id=t.prescriptionId and gph.physicianId=p.id and g.id=gph.groupId and "
			+ "p.physicianName like %:physician% and pat.patientName like %:patient% and t.rxStatus like %:status% and COALESCE(g.groupName, '') like %:group% "
			+ "and COALESCE(t.rxNumber, '') like %:rxNo%   and t.cre8PrescriptionNo like %:rxPrescriptionNo% and "
			+ "m.cre8PrescriptionNo like %:prescriptionNo% and t.writtenDate<=:presriptionToDate")
	List<PrescriptionMaster> reportCountByFilterWithToDate(@Param("physician") String physician, @Param("patient") String patient, @Param("status") String status,
			@Param("group") String group, @Param("rxNo") String rxNo, @Param("rxPrescriptionNo") String rxPrescriptionNo, @Param("prescriptionNo") String prescriptionNo, @Param("presriptionToDate") Date presriptionToDate);

	@Query("select m.cre8PrescriptionNo from PrescriptionMaster m, PrescriptionTransaction t, PhysicianAccount p, PatientAccount pat, PhysicianGroup gph, GroupMaster g "
			+ "where m.physicianId=p.id and m.patientId=pat.id  and m.id=t.prescriptionId and gph.physicianId=p.id and g.id=gph.groupId and "
			+ "p.physicianName like %:physician% and pat.patientName like %:patient% and t.rxStatus like %:status% and COALESCE(g.groupName, '') like %:group% "
			+ "and COALESCE(t.rxNumber, '') like %:rxNo%   and t.cre8PrescriptionNo like %:rxPrescriptionNo% and "
			+ "m.cre8PrescriptionNo like %:prescriptionNo% and t.writtenDate>=:presriptionFromDate and t.writtenDate<=:presriptionToDate ")
	List<PrescriptionMaster> reportCountByFilterWithDate(@Param("physician") String physician, @Param("patient") String patient, @Param("status") String status,
			@Param("group") String group, @Param("rxNo") String rxNo, @Param("rxPrescriptionNo") String rxPrescriptionNo, @Param("prescriptionNo") String prescriptionNo, @Param("presriptionFromDate") Date presriptionFromDate, @Param("presriptionToDate") Date presriptionToDate);
	

	
	
	
	@Query("select  COALESCE(count(m),0)  from PrescriptionMaster m, PrescriptionTransaction t, PhysicianAccount p, PatientAccount pat, PhysicianGroup gph, GroupMaster g "
			+ "where m.physicianId=p.id and m.patientId=pat.id  and m.id=t.prescriptionId and gph.physicianId=p.id and g.id=gph.groupId and "
			+ "p.physicianName like %:physician% and pat.patientName like %:patient% and t.rxStatus like %:status% and COALESCE(g.groupName, '') like %:group% "
			+ "and COALESCE(t.rxNumber, '') like %:rxNo%   and t.cre8PrescriptionNo like %:rxPrescriptionNo% ")
	int reportCountByFilter(@Param("physician") String physician, @Param("patient") String patient, @Param("status") String status, 
			@Param("group") String group, @Param("rxNo") String rxNo, @Param("rxPrescriptionNo") String rxPrescriptionNo);

	@Query("select  COALESCE(count(m),0)  from PrescriptionMaster m, PrescriptionTransaction t, PhysicianAccount p, PatientAccount pat, PhysicianGroup gph, GroupMaster g "
			+ "where m.physicianId=p.id and m.patientId=pat.id  and m.id=t.prescriptionId and gph.physicianId=p.id and g.id=gph.groupId and "
			+ "p.physicianName like %:physician% and pat.patientName like %:patient% and t.rxStatus like %:status% and COALESCE(g.groupName, '') like %:group% "
			+ "and COALESCE(t.rxNumber, '') like %:rxNo%   and t.cre8PrescriptionNo like %:rxPrescriptionNo% and t.writtenDate>=:presriptionFromDate")
	int reportCountByFilterWithFromDate(@Param("physician") String physician, @Param("patient") String patient, @Param("status") String status,
			@Param("group") String group, @Param("rxNo") String rxNo, @Param("rxPrescriptionNo") String rxPrescriptionNo, @Param("presriptionFromDate") Date presriptionFromDate);
	
	@Query("select  COALESCE(count(m),0)  from PrescriptionMaster m, PrescriptionTransaction t, PhysicianAccount p, PatientAccount pat, PhysicianGroup gph, GroupMaster g "
			+ "where m.physicianId=p.id and m.patientId=pat.id  and m.id=t.prescriptionId and gph.physicianId=p.id and g.id=gph.groupId and "
			+ "p.physicianName like %:physician% and pat.patientName like %:patient% and t.rxStatus like %:status% and COALESCE(g.groupName, '') like %:group% "
			+ "and COALESCE(t.rxNumber, '') like %:rxNo%   and t.cre8PrescriptionNo like %:rxPrescriptionNo% and t.writtenDate<=:presriptionToDate")
	int reportCountByFilterWithToDate(@Param("physician") String physician, @Param("patient") String patient, @Param("status") String status,
			@Param("group") String group, @Param("rxNo") String rxNo, @Param("rxPrescriptionNo") String rxPrescriptionNo, @Param("presriptionToDate") Date presriptionToDate);

	@Query("select  COALESCE(count(m),0)  from PrescriptionMaster m, PrescriptionTransaction t, PhysicianAccount p, PatientAccount pat, PhysicianGroup gph, GroupMaster g "
			+ "where m.physicianId=p.id and m.patientId=pat.id  and m.id=t.prescriptionId and gph.physicianId=p.id and g.id=gph.groupId and "
			+ "p.physicianName like %:physician% and pat.patientName like %:patient% and t.rxStatus like %:status% and COALESCE(g.groupName, '') like %:group% "
			+ "and COALESCE(t.rxNumber, '') like %:rxNo%   and t.cre8PrescriptionNo like %:rxPrescriptionNo% and t.writtenDate>=:presriptionFromDate and t.writtenDate<=:presriptionToDate")
	int reportCountByFilterWithDate(@Param("physician") String physician, @Param("patient") String patient, @Param("status") String status,
			@Param("group") String group, @Param("rxNo") String rxNo, @Param("rxPrescriptionNo") String rxPrescriptionNo, @Param("presriptionFromDate") Date presriptionFromDate, @Param("presriptionToDate") Date presriptionToDate);
	
	
	@Query("select m.cre8PrescriptionNo, t.writtenDate, t.expireDate, t.rxNumber, p.physicianName, pat.patientName, t.itemname, o.origintypetext, "
			+ "t.quantity, t.daysSupply, t.refills, t.refillsRemaining, t.refillsFilled, t.rxStatus, m.id, t.cre8PrescriptionNo, t.dispensedItemName "
			+ "from PrescriptionMaster m, PrescriptionTransaction t, PhysicianAccount p, PatientAccount pat, PrescriptionOriginType o, PhysicianGroup gph, GroupMaster g "
			+ "where m.physicianId=p.id and m.patientId=pat.id  and m.id=t.prescriptionId and t.origin=o.origintypeid and gph.physicianId=p.id and g.id=gph.groupId and "
			+ "p.physicianName like %:physician% and pat.patientName like %:patient% and t.rxStatus like %:status% and COALESCE(g.groupName, '') like %:group% "
			+ "and COALESCE(t.rxNumber, '') like %:rxNo%   and t.cre8PrescriptionNo like %:rxPrescriptionNo% and m.cre8PrescriptionNo like %:prescriptionNo% "
			+ "ORDER BY m.prescriptionNumber DESC ")
	Page<Object[]> reportByFilter(@Param("physician") String physician, @Param("patient") String patient, @Param("status") String status,
			@Param("group") String group, @Param("rxNo") String rxNo, @Param("rxPrescriptionNo") String rxPrescriptionNo, @Param("prescriptionNo") String prescriptionNo, Pageable page);
	
	@Query("select m.cre8PrescriptionNo, t.writtenDate, t.expireDate, t.rxNumber, p.physicianName, pat.patientName, t.itemname, o.origintypetext, "
			+ "t.quantity, t.daysSupply, t.refills, t.refillsRemaining, t.refillsFilled, t.rxStatus, m.id, t.cre8PrescriptionNo, t.dispensedItemName "
			+ "from PrescriptionMaster m, PrescriptionTransaction t, PhysicianAccount p, PatientAccount pat, PrescriptionOriginType o, PhysicianGroup gph, GroupMaster g "
			+ "where m.physicianId=p.id and m.patientId=pat.id  and m.id=t.prescriptionId and t.origin=o.origintypeid and gph.physicianId=p.id and g.id=gph.groupId and "
			+ "p.physicianName like %:physician% and pat.patientName like %:patient% and t.rxStatus like %:status% and COALESCE(g.groupName, '') like %:group% "
			+ "and COALESCE(t.rxNumber, '') like %:rxNo%   and t.cre8PrescriptionNo like %:rxPrescriptionNo% and m.cre8PrescriptionNo like %:prescriptionNo% and "
			+ "t.writtenDate>=:presriptionFromDate ORDER BY m.prescriptionNumber DESC ")
	Page<Object[]> reportByFilterWithFromDate(@Param("physician") String physician, @Param("patient") String patient, @Param("status") String status, 
			@Param("group") String group, @Param("rxNo") String rxNo, @Param("rxPrescriptionNo") String rxPrescriptionNo, @Param("prescriptionNo") String prescriptionNo, @Param("presriptionFromDate") Date presriptionFromDate, Pageable page);
	
	
	@Query("select m.cre8PrescriptionNo, t.writtenDate, t.expireDate, t.rxNumber, p.physicianName, pat.patientName, t.itemname, o.origintypetext, "
			+ "t.quantity, t.daysSupply, t.refills, t.refillsRemaining, t.refillsFilled, t.rxStatus, m.id, t.cre8PrescriptionNo, t.dispensedItemName "
			+ "from PrescriptionMaster m, PrescriptionTransaction t, PhysicianAccount p, PatientAccount pat, PrescriptionOriginType o, PhysicianGroup gph, GroupMaster g "
			+ "where m.physicianId=p.id and m.patientId=pat.id  and m.id=t.prescriptionId and t.origin=o.origintypeid and gph.physicianId=p.id and g.id=gph.groupId and "
			+ "p.physicianName like %:physician% and pat.patientName like %:patient% and t.rxStatus like %:status% and COALESCE(g.groupName, '') like %:group% "
			+ "and COALESCE(t.rxNumber, '') like %:rxNo%   and t.cre8PrescriptionNo like %:rxPrescriptionNo% and m.cre8PrescriptionNo like %:prescriptionNo% and "
			+ "t.writtenDate<=:presriptionToDate ORDER BY m.prescriptionNumber DESC ")
	Page<Object[]> reportByFilterWithToDate(@Param("physician") String physician, @Param("patient") String patient, @Param("status") String status, 
			@Param("group") String group, @Param("rxNo") String rxNo, @Param("rxPrescriptionNo") String rxPrescriptionNo, @Param("prescriptionNo") String prescriptionNo, @Param("presriptionToDate") Date presriptionToDate, Pageable page);
	
	
	@Query("select m.cre8PrescriptionNo, t.writtenDate, t.expireDate, t.rxNumber, p.physicianName, pat.patientName, t.itemname, o.origintypetext, "
			+ "t.quantity, t.daysSupply, t.refills, t.refillsRemaining, t.refillsFilled, t.rxStatus, m.id, t.cre8PrescriptionNo, t.dispensedItemName "
			+ "from PrescriptionMaster m, PrescriptionTransaction t, PhysicianAccount p, PatientAccount pat, PrescriptionOriginType o, PhysicianGroup gph, GroupMaster g "
			+ "where m.physicianId=p.id and m.patientId=pat.id  and m.id=t.prescriptionId and t.origin=o.origintypeid and gph.physicianId=p.id and g.id=gph.groupId and "
			+ "p.physicianName like %:physician% and pat.patientName like %:patient% and t.rxStatus like %:status% and COALESCE(g.groupName, '') like %:group% "
			+ "and COALESCE(t.rxNumber, '') like %:rxNo%   and t.cre8PrescriptionNo like %:rxPrescriptionNo% and "
			+ "m.cre8PrescriptionNo like %:prescriptionNo% and t.writtenDate>=:presriptionFromDate and t.writtenDate<=:presriptionToDate "
			+ "ORDER BY m.prescriptionNumber DESC ")
	Page<Object[]> reportByFilterWithDate(@Param("physician") String physician, @Param("patient") String patient, @Param("status") String status, 
			@Param("group") String group, @Param("rxNo") String rxNo, @Param("rxPrescriptionNo") String rxPrescriptionNo, @Param("prescriptionNo") String prescriptionNo, @Param("presriptionFromDate") Date presriptionFromDate, 
			@Param("presriptionToDate") Date presriptionToDate, Pageable page);
	
	
	
	
	@Query("select m.cre8PrescriptionNo, t.writtenDate, t.expireDate, t.rxNumber, p.physicianName, pat.patientName, t.itemname, o.origintypetext, "
			+ "t.quantity, t.daysSupply, t.refills, t.refillsRemaining, t.refillsFilled, t.rxStatus, m.id "
			+ "from PrescriptionMaster m, PrescriptionTransaction t, PhysicianAccount p, PatientAccount pat, PrescriptionOriginType o, PhysicianGroup gph, GroupMaster g "
			+ "where m.physicianId=p.id and m.patientId=pat.id  and m.id=t.prescriptionId and t.origin=o.origintypeid and gph.physicianId=p.id and g.id=gph.groupId and "
			+ "p.physicianName like %:physician% and pat.patientName like %:patient% and t.rxStatus like %:status% and COALESCE(g.groupName, '') like %:group% "
			+ "and COALESCE(t.rxNumber, '') like %:rxNo%   and t.cre8PrescriptionNo like %:rxPrescriptionNo% ORDER BY m.prescriptionNumber DESC ")
	Page<Object[]> reportByFilter(@Param("physician") String physician, @Param("patient") String patient, @Param("status") String status,
			@Param("group") String group, @Param("rxNo") String rxNo, @Param("rxPrescriptionNo") String rxPrescriptionNo, Pageable page);
	
	@Query("select m.cre8PrescriptionNo, t.writtenDate, t.expireDate, t.rxNumber, p.physicianName, pat.patientName, t.itemname, o.origintypetext, "
			+ "t.quantity, t.daysSupply, t.refills, t.refillsRemaining, t.refillsFilled, t.rxStatus, m.id "
			+ "from PrescriptionMaster m, PrescriptionTransaction t, PhysicianAccount p, PatientAccount pat, PrescriptionOriginType o, PhysicianGroup gph, GroupMaster g "
			+ "where m.physicianId=p.id and m.patientId=pat.id  and m.id=t.prescriptionId and t.origin=o.origintypeid and gph.physicianId=p.id and g.id=gph.groupId and "
			+ "p.physicianName like %:physician% and pat.patientName like %:patient% and t.rxStatus like %:status% and COALESCE(g.groupName, '') like %:group% "
			+ "and COALESCE(t.rxNumber, '') like %:rxNo%   and t.cre8PrescriptionNo like %:rxPrescriptionNo% and t.writtenDate>=:presriptionFromDate ORDER BY m.prescriptionNumber DESC ")
	Page<Object[]> reportByFilterWithFromDate(@Param("physician") String physician, @Param("patient") String patient, @Param("status") String status, 
			@Param("group") String group, @Param("rxNo") String rxNo, @Param("rxPrescriptionNo") String rxPrescriptionNo, @Param("presriptionFromDate") Date presriptionFromDate, Pageable page);
	
	
	@Query("select m.cre8PrescriptionNo, t.writtenDate, t.expireDate, t.rxNumber, p.physicianName, pat.patientName, t.itemname, o.origintypetext, "
			+ "t.quantity, t.daysSupply, t.refills, t.refillsRemaining, t.refillsFilled, t.rxStatus, m.id "
			+ "from PrescriptionMaster m, PrescriptionTransaction t, PhysicianAccount p, PatientAccount pat, PrescriptionOriginType o, PhysicianGroup gph, GroupMaster g "
			+ "where m.physicianId=p.id and m.patientId=pat.id  and m.id=t.prescriptionId and t.origin=o.origintypeid and gph.physicianId=p.id and g.id=gph.groupId and "
			+ "p.physicianName like %:physician% and pat.patientName like %:patient% and t.rxStatus like %:status% and COALESCE(g.groupName, '') like %:group% "
			+ "and COALESCE(t.rxNumber, '') like %:rxNo%   and t.cre8PrescriptionNo like %:rxPrescriptionNo% and t.writtenDate<=:presriptionToDate ORDER BY m.prescriptionNumber DESC ")
	Page<Object[]> reportByFilterWithToDate(@Param("physician") String physician, @Param("patient") String patient, @Param("status") String status, 
			@Param("group") String group, @Param("rxNo") String rxNo, @Param("rxPrescriptionNo") String rxPrescriptionNo, @Param("presriptionToDate") Date presriptionToDate, Pageable page);
	
	
	@Query("select m.cre8PrescriptionNo, t.writtenDate, t.expireDate, t.rxNumber, p.physicianName, pat.patientName, t.itemname, o.origintypetext, "
			+ "t.quantity, t.daysSupply, t.refills, t.refillsRemaining, t.refillsFilled, t.rxStatus, m.id "
			+ "from PrescriptionMaster m, PrescriptionTransaction t, PhysicianAccount p, PatientAccount pat, PrescriptionOriginType o, PhysicianGroup gph, GroupMaster g "
			+ "where m.physicianId=p.id and m.patientId=pat.id  and m.id=t.prescriptionId and t.origin=o.origintypeid and gph.physicianId=p.id and g.id=gph.groupId and "
			+ "p.physicianName like %:physician% and pat.patientName like %:patient% and t.rxStatus like %:status% and COALESCE(g.groupName, '') like %:group% "
			+ "and COALESCE(t.rxNumber, '') like %:rxNo%   and t.cre8PrescriptionNo like %:rxPrescriptionNo% and t.writtenDate>=:presriptionFromDate and t.writtenDate<=:presriptionToDate ORDER BY m.prescriptionNumber DESC ")
	Page<Object[]> reportByFilterWithDate(@Param("physician") String physician, @Param("patient") String patient, @Param("status") String status, 
			@Param("group") String group, @Param("rxNo") String rxNo, @Param("rxPrescriptionNo") String rxPrescriptionNo, @Param("presriptionFromDate") Date presriptionFromDate, 
			@Param("presriptionToDate") Date presriptionToDate, Pageable page);
	

	@Query("SELECT COALESCE(MAX(p.prescriptionNo),0) from PrescriptionTransaction p where prescriptionId=:prescriptionId")
	int getMaxPrescriptionNumberCount( @Param("prescriptionId") int prescriptionId);

	
	/********************* Physician Login Report  *********************************/
	@Query("select m  from PrescriptionMaster m, PrescriptionTransaction t, PhysicianAccount p, PatientAccount pat, PhysicianGroup gph, GroupMaster g "
			+ "where m.physicianId=p.id and m.patientId=pat.id  and m.id=t.prescriptionId and gph.physicianId=p.id and g.id=gph.groupId and "
			+ "m.physicianId=:physicianId and p.physicianName like %:physician% and pat.patientName like %:patient% and t.rxStatus like %:status% and COALESCE(g.groupName, '') like %:group% "
			+ "and COALESCE(t.rxNumber, '') like %:rxNo%   and t.cre8PrescriptionNo like %:rxPrescriptionNo% and m.cre8PrescriptionNo like %:prescriptionNo% ")
	List<PrescriptionMaster>  physicianReportCountByFilter(@Param("physicianId") int physicianId, @Param("physician") String physician, @Param("patient") String patient, @Param("status") String status, 
			@Param("group") String group, @Param("rxNo") String rxNo, @Param("rxPrescriptionNo") String rxPrescriptionNo, @Param("prescriptionNo") String prescriptionNo);
	
	@Query("select m from PrescriptionMaster m, PrescriptionTransaction t, PhysicianAccount p, PatientAccount pat, PhysicianGroup gph, GroupMaster g "
			+ "where m.physicianId=p.id and m.patientId=pat.id  and m.id=t.prescriptionId and gph.physicianId=p.id and g.id=gph.groupId and "
			+ "m.physicianId=:physicianId and p.physicianName like %:physician% and pat.patientName like %:patient% and t.rxStatus like %:status% and COALESCE(g.groupName, '') like %:group% "
			+ "and COALESCE(t.rxNumber, '') like %:rxNo%   and t.cre8PrescriptionNo like %:rxPrescriptionNo% and "
			+ "m.cre8PrescriptionNo like %:prescriptionNo% and t.writtenDate>=:presriptionFromDate ")
	List<PrescriptionMaster>  physicianReportCountByFilterWithFromDate(@Param("physicianId") int physicianId, @Param("physician") String physician, @Param("patient") String patient, @Param("status") String status,
			@Param("group") String group, @Param("rxNo") String rxNo, @Param("rxPrescriptionNo") String rxPrescriptionNo, @Param("prescriptionNo") String prescriptionNo, @Param("presriptionFromDate") Date presriptionFromDate);
	
	@Query("select m from PrescriptionMaster m, PrescriptionTransaction t, PhysicianAccount p, PatientAccount pat, PhysicianGroup gph, GroupMaster g "
			+ "where m.physicianId=p.id and m.patientId=pat.id  and m.id=t.prescriptionId and gph.physicianId=p.id and g.id=gph.groupId and "
			+ "m.physicianId=:physicianId and p.physicianName like %:physician% and pat.patientName like %:patient% and t.rxStatus like %:status% and COALESCE(g.groupName, '') like %:group% "
			+ "and COALESCE(t.rxNumber, '') like %:rxNo%   and t.cre8PrescriptionNo like %:rxPrescriptionNo% and "
			+ "m.cre8PrescriptionNo like %:prescriptionNo% and t.writtenDate<=:presriptionToDate ")
	List<PrescriptionMaster>  physicianReportCountByFilterWithToDate(@Param("physicianId") int physicianId, @Param("physician") String physician, @Param("patient") String patient, @Param("status") String status,
			@Param("group") String group, @Param("rxNo") String rxNo, @Param("rxPrescriptionNo") String rxPrescriptionNo, @Param("prescriptionNo") String prescriptionNo, @Param("presriptionToDate") Date presriptionToDate);

	@Query("select m from PrescriptionMaster m, PrescriptionTransaction t, PhysicianAccount p, PatientAccount pat, PhysicianGroup gph, GroupMaster g "
			+ "where m.physicianId=p.id and m.patientId=pat.id  and m.id=t.prescriptionId and gph.physicianId=p.id and g.id=gph.groupId and "
			+ "m.physicianId=:physicianId and p.physicianName like %:physician% and pat.patientName like %:patient% and t.rxStatus like %:status% and COALESCE(g.groupName, '') like %:group% "
			+ "and COALESCE(t.rxNumber, '') like %:rxNo%   and t.cre8PrescriptionNo like %:rxPrescriptionNo% and "
			+ "m.cre8PrescriptionNo like %:prescriptionNo% and t.writtenDate>=:presriptionFromDate and t.writtenDate<=:presriptionToDate ")
	List<PrescriptionMaster>  physicianReportCountByFilterWithDate(@Param("physicianId") int physicianId, @Param("physician") String physician, @Param("patient") String patient, @Param("status") String status,
			@Param("group") String group, @Param("rxNo") String rxNo, @Param("rxPrescriptionNo") String rxPrescriptionNo, @Param("prescriptionNo") String prescriptionNo, @Param("presriptionFromDate") Date presriptionFromDate, @Param("presriptionToDate") Date presriptionToDate);
		
	
	
	
	
	@Query("select m.cre8PrescriptionNo, t.writtenDate, t.expireDate, t.rxNumber, p.physicianName, pat.patientName, t.itemname, o.origintypetext, "
			+ "t.quantity, t.daysSupply, t.refills, t.refillsRemaining, t.refillsFilled, t.rxStatus, m.id, t.cre8PrescriptionNo, t.dispensedItemName "
			+ "from PrescriptionMaster m, PrescriptionTransaction t, PhysicianAccount p, PatientAccount pat, PrescriptionOriginType o, PhysicianGroup gph, GroupMaster g "
			+ "where m.physicianId=p.id and m.patientId=pat.id  and m.id=t.prescriptionId and t.origin=o.origintypeid and gph.physicianId=p.id and g.id=gph.groupId and "
			+ "m.physicianId=:physicianId and p.physicianName like %:physician% and pat.patientName like %:patient% and t.rxStatus like %:status% and COALESCE(g.groupName, '') like %:group% "
			+ "and COALESCE(t.rxNumber, '') like %:rxNo%   and t.cre8PrescriptionNo like %:rxPrescriptionNo% and m.cre8PrescriptionNo like %:prescriptionNo% "
			+ "ORDER BY m.prescriptionNumber DESC ")
	Page<Object[]> physicianReportByFilter(@Param("physicianId") int physicianId, @Param("physician") String physician, @Param("patient") String patient, @Param("status") String status,
			@Param("group") String group, @Param("rxNo") String rxNo, @Param("rxPrescriptionNo") String rxPrescriptionNo, @Param("prescriptionNo") String prescriptionNo, Pageable page);
	
	@Query("select m.cre8PrescriptionNo, t.writtenDate, t.expireDate, t.rxNumber, p.physicianName, pat.patientName, t.itemname, o.origintypetext, "
			+ "t.quantity, t.daysSupply, t.refills, t.refillsRemaining, t.refillsFilled, t.rxStatus, m.id, t.cre8PrescriptionNo, t.dispensedItemName "
			+ "from PrescriptionMaster m, PrescriptionTransaction t, PhysicianAccount p, PatientAccount pat, PrescriptionOriginType o, PhysicianGroup gph, GroupMaster g "
			+ "where m.physicianId=p.id and m.patientId=pat.id  and m.id=t.prescriptionId and t.origin=o.origintypeid and gph.physicianId=p.id and g.id=gph.groupId and "
			+ "m.physicianId=:physicianId and p.physicianName like %:physician% and pat.patientName like %:patient% and t.rxStatus like %:status% and COALESCE(g.groupName, '') like %:group% "
			+ "and COALESCE(t.rxNumber, '') like %:rxNo%   and t.cre8PrescriptionNo like %:rxPrescriptionNo% and m.cre8PrescriptionNo like %:prescriptionNo% "
			+ "and t.writtenDate>=:presriptionFromDate ORDER BY m.prescriptionNumber DESC ")
	Page<Object[]> physicianReportByFilterWithFromDate(@Param("physicianId") int physicianId, @Param("physician") String physician, @Param("patient") String patient, @Param("status") String status, 
			@Param("group") String group, @Param("rxNo") String rxNo, @Param("rxPrescriptionNo") String rxPrescriptionNo, @Param("prescriptionNo") String prescriptionNo, @Param("presriptionFromDate") Date presriptionFromDate, Pageable page);
	
	
	@Query("select m.cre8PrescriptionNo, t.writtenDate, t.expireDate, t.rxNumber, p.physicianName, pat.patientName, t.itemname, o.origintypetext, "
			+ "t.quantity, t.daysSupply, t.refills, t.refillsRemaining, t.refillsFilled, t.rxStatus, m.id, t.cre8PrescriptionNo, t.dispensedItemName "
			+ "from PrescriptionMaster m, PrescriptionTransaction t, PhysicianAccount p, PatientAccount pat, PrescriptionOriginType o, PhysicianGroup gph, GroupMaster g "
			+ "where m.physicianId=p.id and m.patientId=pat.id  and m.id=t.prescriptionId and t.origin=o.origintypeid and gph.physicianId=p.id and g.id=gph.groupId and "
			+ "m.physicianId=:physicianId and p.physicianName like %:physician% and pat.patientName like %:patient% and t.rxStatus like %:status% and COALESCE(g.groupName, '') like %:group% "
			+ "and COALESCE(t.rxNumber, '') like %:rxNo%   and t.cre8PrescriptionNo like %:rxPrescriptionNo% and m.cre8PrescriptionNo like %:prescriptionNo% "
			+ "and t.writtenDate<=:presriptionToDate ORDER BY m.prescriptionNumber DESC ")
	Page<Object[]> physicianReportByFilterWithToDate(@Param("physicianId") int physicianId, @Param("physician") String physician, @Param("patient") String patient, @Param("status") String status, 
			@Param("group") String group, @Param("rxNo") String rxNo, @Param("rxPrescriptionNo") String rxPrescriptionNo, @Param("prescriptionNo") String prescriptionNo, @Param("presriptionToDate") Date presriptionToDate, Pageable page);
	
	
	@Query("select m.cre8PrescriptionNo, t.writtenDate, t.expireDate, t.rxNumber, p.physicianName, pat.patientName, t.itemname, o.origintypetext, "
			+ "t.quantity, t.daysSupply, t.refills, t.refillsRemaining, t.refillsFilled, t.rxStatus, m.id, t.cre8PrescriptionNo, t.dispensedItemName "
			+ "from PrescriptionMaster m, PrescriptionTransaction t, PhysicianAccount p, PatientAccount pat, PrescriptionOriginType o, PhysicianGroup gph, GroupMaster g "
			+ "where m.physicianId=p.id and m.patientId=pat.id  and m.id=t.prescriptionId and t.origin=o.origintypeid and gph.physicianId=p.id and g.id=gph.groupId and "
			+ "m.physicianId=:physicianId and p.physicianName like %:physician% and pat.patientName like %:patient% and t.rxStatus like %:status% and COALESCE(g.groupName, '') like %:group% "
			+ "and COALESCE(t.rxNumber, '') like %:rxNo%   and t.cre8PrescriptionNo like %:rxPrescriptionNo% and m.cre8PrescriptionNo like %:prescriptionNo% "
			+ "and t.writtenDate>=:presriptionFromDate and t.writtenDate<=:presriptionToDate ORDER BY m.prescriptionNumber DESC ")
	Page<Object[]> physicianReportByFilterWithDate(@Param("physicianId") int physicianId, @Param("physician") String physician, @Param("patient") String patient, @Param("status") String status, 
			@Param("group") String group, @Param("rxNo") String rxNo, @Param("rxPrescriptionNo") String rxPrescriptionNo, @Param("prescriptionNo") String prescriptionNo, @Param("presriptionFromDate") Date presriptionFromDate, 
			@Param("presriptionToDate") Date presriptionToDate, Pageable page);

	
	
	
	
	
	
	/********************* Patient Login Report  *********************************/
	@Query("select m from PrescriptionMaster m, PrescriptionTransaction t, PhysicianAccount p, PatientAccount pat, PhysicianGroup gph, GroupMaster g "
			+ "where m.physicianId=p.id and m.patientId=pat.id  and m.id=t.prescriptionId and gph.physicianId=p.id and g.id=gph.groupId and "
			+ "m.patientId=:patientId and p.physicianName like %:physician% and pat.patientName like %:patient% and t.rxStatus like %:status% and COALESCE(g.groupName, '') like %:group% "
			+ "and COALESCE(t.rxNumber, '') like %:rxNo%   and t.cre8PrescriptionNo like %:rxPrescriptionNo% and "
			+ "m.cre8PrescriptionNo like %:prescriptionNo% ")
	List<PrescriptionMaster>  patientReportCountByFilter(@Param("patientId") int patientId, @Param("physician") String physician, @Param("patient") String patient, @Param("status") String status, 
			@Param("group") String group, @Param("rxNo") String rxNo, @Param("rxPrescriptionNo") String rxPrescriptionNo, @Param("prescriptionNo") String prescriptionNo);
	
	@Query("select m from PrescriptionMaster m, PrescriptionTransaction t, PhysicianAccount p, PatientAccount pat, PhysicianGroup gph, GroupMaster g "
			+ "where m.physicianId=p.id and m.patientId=pat.id  and m.id=t.prescriptionId and gph.physicianId=p.id and g.id=gph.groupId and "
			+ "m.patientId=:patientId and p.physicianName like %:physician% and pat.patientName like %:patient% and t.rxStatus like %:status% and COALESCE(g.groupName, '') like %:group% "
			+ "and COALESCE(t.rxNumber, '') like %:rxNo%   and t.cre8PrescriptionNo like %:rxPrescriptionNo% and m.cre8PrescriptionNo like %:prescriptionNo% and "
			+ "t.writtenDate>=:presriptionFromDate ")
	List<PrescriptionMaster>  patientReportCountByFilterWithFromDate(@Param("patientId") int patientId, @Param("physician") String physician, @Param("patient") String patient, @Param("status") String status,
			@Param("group") String group, @Param("rxNo") String rxNo, @Param("rxPrescriptionNo") String rxPrescriptionNo, @Param("prescriptionNo") String prescriptionNo, @Param("presriptionFromDate") Date presriptionFromDate);
	
	@Query("select m from PrescriptionMaster m, PrescriptionTransaction t, PhysicianAccount p, PatientAccount pat, PhysicianGroup gph, GroupMaster g "
			+ "where m.physicianId=p.id and m.patientId=pat.id  and m.id=t.prescriptionId and gph.physicianId=p.id and g.id=gph.groupId and "
			+ "m.patientId=:patientId and p.physicianName like %:physician% and pat.patientName like %:patient% and t.rxStatus like %:status% and COALESCE(g.groupName, '') like %:group% "
			+ "and COALESCE(t.rxNumber, '') like %:rxNo%   and t.cre8PrescriptionNo like %:rxPrescriptionNo% and m.cre8PrescriptionNo like %:prescriptionNo% "
			+ "and t.writtenDate<=:presriptionToDate ")
	List<PrescriptionMaster>  patientReportCountByFilterWithToDate(@Param("patientId") int patientId, @Param("physician") String physician, @Param("patient") String patient, @Param("status") String status,
			@Param("group") String group, @Param("rxNo") String rxNo, @Param("rxPrescriptionNo") String rxPrescriptionNo, @Param("prescriptionNo") String prescriptionNo, @Param("presriptionToDate") Date presriptionToDate);

	@Query("select m from PrescriptionMaster m, PrescriptionTransaction t, PhysicianAccount p, PatientAccount pat, PhysicianGroup gph, GroupMaster g "
			+ "where m.physicianId=p.id and m.patientId=pat.id  and m.id=t.prescriptionId and gph.physicianId=p.id and g.id=gph.groupId and "
			+ "m.patientId=:patientId and p.physicianName like %:physician% and pat.patientName like %:patient% and t.rxStatus like %:status% and COALESCE(g.groupName, '') like %:group% "
			+ "and COALESCE(t.rxNumber, '') like %:rxNo%   and t.cre8PrescriptionNo like %:rxPrescriptionNo% and m.cre8PrescriptionNo like %:prescriptionNo% "
			+ "and t.writtenDate>=:presriptionFromDate and t.writtenDate<=:presriptionToDate ")
	List<PrescriptionMaster>  patientReportCountByFilterWithDate(@Param("patientId") int patientId, @Param("physician") String physician, @Param("patient") String patient, @Param("status") String status,
			@Param("group") String group, @Param("rxNo") String rxNo, @Param("rxPrescriptionNo") String rxPrescriptionNo, @Param("prescriptionNo") String prescriptionNo, @Param("presriptionFromDate") Date presriptionFromDate, @Param("presriptionToDate") Date presriptionToDate);

	
	

	@Query("select m.cre8PrescriptionNo, t.writtenDate, t.expireDate, t.rxNumber, p.physicianName, pat.patientName, t.itemname, o.origintypetext, "
			+ "t.quantity, t.daysSupply, t.refills, t.refillsRemaining, t.refillsFilled, t.rxStatus, m.id, t.cre8PrescriptionNo, t.dispensedItemName "
			+ "from PrescriptionMaster m, PrescriptionTransaction t, PhysicianAccount p, PatientAccount pat, PrescriptionOriginType o, PhysicianGroup gph, GroupMaster g "
			+ "where m.physicianId=p.id and m.patientId=pat.id  and m.id=t.prescriptionId and t.origin=o.origintypeid and gph.physicianId=p.id and g.id=gph.groupId and "
			+ "m.patientId=:patientId and p.physicianName like %:physician% and pat.patientName like %:patient% and t.rxStatus like %:status% and COALESCE(g.groupName, '') like %:group% "
			+ "and COALESCE(t.rxNumber, '') like %:rxNo%   and t.cre8PrescriptionNo like %:rxPrescriptionNo% and m.cre8PrescriptionNo like %:prescriptionNo%  "
			+ "ORDER BY m.prescriptionNumber DESC ")
	Page<Object[]> patientReportByFilter(@Param("patientId") int patientId, @Param("physician") String physician, @Param("patient") String patient, @Param("status") String status,
			@Param("group") String group, @Param("rxNo") String rxNo, @Param("rxPrescriptionNo") String rxPrescriptionNo, @Param("prescriptionNo") String prescriptionNo, Pageable page);
	
	@Query("select m.cre8PrescriptionNo, t.writtenDate, t.expireDate, t.rxNumber, p.physicianName, pat.patientName, t.itemname, o.origintypetext, "
			+ "t.quantity, t.daysSupply, t.refills, t.refillsRemaining, t.refillsFilled, t.rxStatus, m.id, t.cre8PrescriptionNo, t.dispensedItemName "
			+ "from PrescriptionMaster m, PrescriptionTransaction t, PhysicianAccount p, PatientAccount pat, PrescriptionOriginType o, PhysicianGroup gph, GroupMaster g "
			+ "where m.physicianId=p.id and m.patientId=pat.id  and m.id=t.prescriptionId and t.origin=o.origintypeid and gph.physicianId=p.id and g.id=gph.groupId and "
			+ "m.patientId=:patientId and p.physicianName like %:physician% and pat.patientName like %:patient% and t.rxStatus like %:status% and COALESCE(g.groupName, '') like %:group% "
			+ "and COALESCE(t.rxNumber, '') like %:rxNo%   and t.cre8PrescriptionNo like %:rxPrescriptionNo% and m.cre8PrescriptionNo like %:prescriptionNo% "
			+ "and t.writtenDate>=:presriptionFromDate ORDER BY m.prescriptionNumber DESC ")
	Page<Object[]> patientReportByFilterWithFromDate(@Param("patientId") int patientId, @Param("physician") String physician, @Param("patient") String patient, @Param("status") String status, 
			@Param("group") String group, @Param("rxNo") String rxNo, @Param("rxPrescriptionNo") String rxPrescriptionNo, @Param("prescriptionNo") String prescriptionNo, @Param("presriptionFromDate") Date presriptionFromDate, Pageable page);
	
	
	@Query("select m.cre8PrescriptionNo, t.writtenDate, t.expireDate, t.rxNumber, p.physicianName, pat.patientName, t.itemname, o.origintypetext, "
			+ "t.quantity, t.daysSupply, t.refills, t.refillsRemaining, t.refillsFilled, t.rxStatus, m.id, t.cre8PrescriptionNo, t.dispensedItemName "
			+ "from PrescriptionMaster m, PrescriptionTransaction t, PhysicianAccount p, PatientAccount pat, PrescriptionOriginType o, PhysicianGroup gph, GroupMaster g "
			+ "where m.physicianId=p.id and m.patientId=pat.id  and m.id=t.prescriptionId and t.origin=o.origintypeid and gph.physicianId=p.id and g.id=gph.groupId and "
			+ "m.patientId=:patientId and p.physicianName like %:physician% and pat.patientName like %:patient% and t.rxStatus like %:status% and COALESCE(g.groupName, '') like %:group% "
			+ "and COALESCE(t.rxNumber, '') like %:rxNo%   and t.cre8PrescriptionNo like %:rxPrescriptionNo% and m.cre8PrescriptionNo like %:prescriptionNo% "
			+ "and t.writtenDate<=:presriptionToDate ORDER BY m.prescriptionNumber DESC ")
	Page<Object[]> patientReportByFilterWithToDate(@Param("patientId") int patientId, @Param("physician") String physician, @Param("patient") String patient, @Param("status") String status, 
			@Param("group") String group, @Param("rxNo") String rxNo, @Param("rxPrescriptionNo") String rxPrescriptionNo, @Param("prescriptionNo") String prescriptionNo, @Param("presriptionToDate") Date presriptionToDate, Pageable page);
	
	
	@Query("select m.cre8PrescriptionNo, t.writtenDate, t.expireDate, t.rxNumber, p.physicianName, pat.patientName, t.itemname, o.origintypetext, "
			+ "t.quantity, t.daysSupply, t.refills, t.refillsRemaining, t.refillsFilled, t.rxStatus, m.id, t.cre8PrescriptionNo, t.dispensedItemName "
			+ "from PrescriptionMaster m, PrescriptionTransaction t, PhysicianAccount p, PatientAccount pat, PrescriptionOriginType o, PhysicianGroup gph, GroupMaster g "
			+ "where m.physicianId=p.id and m.patientId=pat.id  and m.id=t.prescriptionId and t.origin=o.origintypeid and gph.physicianId=p.id and g.id=gph.groupId and "
			+ "m.patientId=:patientId and p.physicianName like %:physician% and pat.patientName like %:patient% and t.rxStatus like %:status% and COALESCE(g.groupName, '') like %:group% "
			+ "and COALESCE(t.rxNumber, '') like %:rxNo%   and t.cre8PrescriptionNo like %:rxPrescriptionNo% and m.cre8PrescriptionNo like %:prescriptionNo% "
			+ "and t.writtenDate>=:presriptionFromDate and t.writtenDate<=:presriptionToDate ORDER BY m.prescriptionNumber DESC ")
	Page<Object[]> patientReportByFilterWithDate(@Param("patientId") int patientId, @Param("physician") String physician, @Param("patient") String patient, @Param("status") String status, 
			@Param("group") String group, @Param("rxNo") String rxNo, @Param("rxPrescriptionNo") String rxPrescriptionNo, @Param("prescriptionNo") String prescriptionNo, @Param("presriptionFromDate") Date presriptionFromDate, 
			@Param("presriptionToDate") Date presriptionToDate, Pageable page);

	
	
	
	/********************* Group director Login Report  *********************************/
	@Query("select m from PrescriptionMaster m, PrescriptionTransaction t, PhysicianAccount p, PatientAccount pat, PhysicianGroup gph, GroupMaster g "
			+ "where m.physicianId=p.id and m.patientId=pat.id  and m.id=t.prescriptionId and gph.physicianId=p.id and g.id=gph.groupId and "
			+ "g.id=:groupId and p.physicianName like %:physician% and pat.patientName like %:patient% and t.rxStatus like %:status% and COALESCE(g.groupName, '') like %:group% "
			+ "and COALESCE(t.rxNumber, '') like %:rxNo%   and t.cre8PrescriptionNo like %:rxPrescriptionNo% and "
			+ "m.cre8PrescriptionNo like %:prescriptionNo% ")
	List<PrescriptionMaster>  groupReportCountByFilter(@Param("groupId") int groupId, @Param("physician") String physician, @Param("patient") String patient, @Param("status") String status, 
			@Param("group") String group, @Param("rxNo") String rxNo, @Param("rxPrescriptionNo") String rxPrescriptionNo, @Param("prescriptionNo") String prescriptionNo);
	
	@Query("select m from PrescriptionMaster m, PrescriptionTransaction t, PhysicianAccount p, PatientAccount pat, PhysicianGroup gph, GroupMaster g "
			+ "where m.physicianId=p.id and m.patientId=pat.id  and m.id=t.prescriptionId and gph.physicianId=p.id and g.id=gph.groupId and "
			+ "g.id=:groupId and p.physicianName like %:physician% and pat.patientName like %:patient% and t.rxStatus like %:status% and COALESCE(g.groupName, '') like %:group% "
			+ "and COALESCE(t.rxNumber, '') like %:rxNo%   and t.cre8PrescriptionNo like %:rxPrescriptionNo% and "
			+ "m.cre8PrescriptionNo like %:prescriptionNo% and t.writtenDate>=:presriptionFromDate ")
	List<PrescriptionMaster>  groupReportCountByFilterWithFromDate(@Param("groupId") int groupId, @Param("physician") String physician, @Param("patient") String patient, @Param("status") String status,
			@Param("group") String group, @Param("rxNo") String rxNo, @Param("rxPrescriptionNo") String rxPrescriptionNo, @Param("prescriptionNo") String prescriptionNo, @Param("presriptionFromDate") Date presriptionFromDate);
	
	@Query("select m from PrescriptionMaster m, PrescriptionTransaction t, PhysicianAccount p, PatientAccount pat, PhysicianGroup gph, GroupMaster g "
			+ "where m.physicianId=p.id and m.patientId=pat.id  and m.id=t.prescriptionId and gph.physicianId=p.id and g.id=gph.groupId and "
			+ "g.id=:groupId and p.physicianName like %:physician% and pat.patientName like %:patient% and t.rxStatus like %:status% and COALESCE(g.groupName, '') like %:group% "
			+ "and COALESCE(t.rxNumber, '') like %:rxNo%   and t.cre8PrescriptionNo like %:rxPrescriptionNo% and "
			+ "m.cre8PrescriptionNo like %:prescriptionNo% and t.writtenDate<=:presriptionToDate ")
	List<PrescriptionMaster>  groupReportCountByFilterWithToDate(@Param("groupId") int groupId, @Param("physician") String physician, @Param("patient") String patient, @Param("status") String status,
			@Param("group") String group, @Param("rxNo") String rxNo, @Param("rxPrescriptionNo") String rxPrescriptionNo, @Param("prescriptionNo") String prescriptionNo, @Param("presriptionToDate") Date presriptionToDate);

	@Query("select m from PrescriptionMaster m, PrescriptionTransaction t, PhysicianAccount p, PatientAccount pat, PhysicianGroup gph, GroupMaster g "
			+ "where m.physicianId=p.id and m.patientId=pat.id  and m.id=t.prescriptionId and gph.physicianId=p.id and g.id=gph.groupId and "
			+ "g.id=:groupId and p.physicianName like %:physician% and pat.patientName like %:patient% and t.rxStatus like %:status% and COALESCE(g.groupName, '') like %:group% "
			+ "and COALESCE(t.rxNumber, '') like %:rxNo%   and t.cre8PrescriptionNo like %:rxPrescriptionNo% and "
			+ "m.cre8PrescriptionNo like %:prescriptionNo% and t.writtenDate>=:presriptionFromDate and t.writtenDate<=:presriptionToDate ")
	List<PrescriptionMaster>  groupReportCountByFilterWithDate(@Param("groupId") int groupId, @Param("physician") String physician, @Param("patient") String patient, @Param("status") String status,
			@Param("group") String group, @Param("rxNo") String rxNo, @Param("rxPrescriptionNo") String rxPrescriptionNo, @Param("prescriptionNo") String prescriptionNo, @Param("presriptionFromDate") Date presriptionFromDate, @Param("presriptionToDate") Date presriptionToDate);
	
	


	@Query("select m.cre8PrescriptionNo, t.writtenDate, t.expireDate, t.rxNumber, p.physicianName, pat.patientName, t.itemname, o.origintypetext, "
			+ "t.quantity, t.daysSupply, t.refills, t.refillsRemaining, t.refillsFilled, t.rxStatus, m.id, t.cre8PrescriptionNo, t.dispensedItemName "
			+ "from PrescriptionMaster m, PrescriptionTransaction t, PhysicianAccount p, PatientAccount pat, PrescriptionOriginType o, PhysicianGroup gph, GroupMaster g "
			+ "where m.physicianId=p.id and m.patientId=pat.id  and m.id=t.prescriptionId and t.origin=o.origintypeid and gph.physicianId=p.id and g.id=gph.groupId and "
			+ "g.id=:groupId and p.physicianName like %:physician% and pat.patientName like %:patient% and t.rxStatus like %:status% and COALESCE(g.groupName, '') like %:group% "
			+ "and COALESCE(t.rxNumber, '') like %:rxNo%   and t.cre8PrescriptionNo like %:rxPrescriptionNo% and m.cre8PrescriptionNo like %:prescriptionNo%  "
			+ "ORDER BY m.prescriptionNumber DESC ")
	Page<Object[]> groupReportByFilter(@Param("groupId") int groupId, @Param("physician") String physician, @Param("patient") String patient, @Param("status") String status,
			@Param("group") String group, @Param("rxNo") String rxNo, @Param("rxPrescriptionNo") String rxPrescriptionNo, @Param("prescriptionNo") String prescriptionNo, Pageable page);
	
	@Query("select m.cre8PrescriptionNo, t.writtenDate, t.expireDate, t.rxNumber, p.physicianName, pat.patientName, t.itemname, o.origintypetext, "
			+ "t.quantity, t.daysSupply, t.refills, t.refillsRemaining, t.refillsFilled, t.rxStatus, m.id, t.cre8PrescriptionNo, t.dispensedItemName "
			+ "from PrescriptionMaster m, PrescriptionTransaction t, PhysicianAccount p, PatientAccount pat, PrescriptionOriginType o, PhysicianGroup gph, GroupMaster g "
			+ "where m.physicianId=p.id and m.patientId=pat.id  and m.id=t.prescriptionId and t.origin=o.origintypeid and gph.physicianId=p.id and g.id=gph.groupId and "
			+ "g.id=:groupId and p.physicianName like %:physician% and pat.patientName like %:patient% and t.rxStatus like %:status% and COALESCE(g.groupName, '') like %:group% "
			+ "and COALESCE(t.rxNumber, '') like %:rxNo%   and t.cre8PrescriptionNo like %:rxPrescriptionNo% and m.cre8PrescriptionNo like %:prescriptionNo% "
			+ "and t.writtenDate>=:presriptionFromDate ORDER BY m.prescriptionNumber DESC ")
	Page<Object[]> groupReportByFilterWithFromDate(@Param("groupId") int groupId, @Param("physician") String physician, @Param("patient") String patient, @Param("status") String status, 
			@Param("group") String group, @Param("rxNo") String rxNo, @Param("rxPrescriptionNo") String rxPrescriptionNo, @Param("prescriptionNo") String prescriptionNo, @Param("presriptionFromDate") Date presriptionFromDate, Pageable page);
	
	@Query("select m.cre8PrescriptionNo, t.writtenDate, t.expireDate, t.rxNumber, p.physicianName, pat.patientName, t.itemname, o.origintypetext, "
			+ "t.quantity, t.daysSupply, t.refills, t.refillsRemaining, t.refillsFilled, t.rxStatus, m.id, t.cre8PrescriptionNo, t.dispensedItemName "
			+ "from PrescriptionMaster m, PrescriptionTransaction t, PhysicianAccount p, PatientAccount pat, PrescriptionOriginType o, PhysicianGroup gph, GroupMaster g "
			+ "where m.physicianId=p.id and m.patientId=pat.id  and m.id=t.prescriptionId and t.origin=o.origintypeid and gph.physicianId=p.id and g.id=gph.groupId and "
			+ "g.id=:groupId and p.physicianName like %:physician% and pat.patientName like %:patient% and t.rxStatus like %:status% and COALESCE(g.groupName, '') like %:group% "
			+ "and COALESCE(t.rxNumber, '') like %:rxNo%   and t.cre8PrescriptionNo like %:rxPrescriptionNo% and m.cre8PrescriptionNo like %:prescriptionNo% "
			+ "and t.writtenDate<=:presriptionToDate ORDER BY m.prescriptionNumber DESC ")
	Page<Object[]> groupReportByFilterWithToDate(@Param("groupId") int groupId, @Param("physician") String physician, @Param("patient") String patient, @Param("status") String status, 
			@Param("group") String group, @Param("rxNo") String rxNo, @Param("rxPrescriptionNo") String rxPrescriptionNo, @Param("prescriptionNo") String prescriptionNo, @Param("presriptionToDate") Date presriptionToDate, Pageable page);
	
	@Query("select m.cre8PrescriptionNo, t.writtenDate, t.expireDate, t.rxNumber, p.physicianName, pat.patientName, t.itemname, o.origintypetext, "
			+ "t.quantity, t.daysSupply, t.refills, t.refillsRemaining, t.refillsFilled, t.rxStatus, m.id, t.cre8PrescriptionNo, t.dispensedItemName "
			+ "from PrescriptionMaster m, PrescriptionTransaction t, PhysicianAccount p, PatientAccount pat, PrescriptionOriginType o, PhysicianGroup gph, GroupMaster g "
			+ "where m.physicianId=p.id and m.patientId=pat.id  and m.id=t.prescriptionId and t.origin=o.origintypeid and gph.physicianId=p.id and g.id=gph.groupId and "
			+ "g.id=:groupId and p.physicianName like %:physician% and pat.patientName like %:patient% and t.rxStatus like %:status% and COALESCE(g.groupName, '') like %:group% "
			+ "and COALESCE(t.rxNumber, '') like %:rxNo%   and t.cre8PrescriptionNo like %:rxPrescriptionNo% and m.cre8PrescriptionNo like %:prescriptionNo% "
			+ "and t.writtenDate>=:presriptionFromDate and t.writtenDate<=:presriptionToDate ORDER BY m.prescriptionNumber DESC ")
	Page<Object[]> groupReportByFilterWithDate(@Param("groupId") int groupId, @Param("physician") String physician, @Param("patient") String patient, @Param("status") String status, 
			@Param("group") String group, @Param("rxNo") String rxNo, @Param("rxPrescriptionNo") String rxPrescriptionNo, @Param("prescriptionNo") String prescriptionNo, @Param("presriptionFromDate") Date presriptionFromDate, 
			@Param("presriptionToDate") Date presriptionToDate, Pageable page);


}
