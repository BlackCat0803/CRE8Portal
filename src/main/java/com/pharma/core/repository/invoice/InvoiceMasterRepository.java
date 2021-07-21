package com.pharma.core.repository.invoice;

import java.sql.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pharma.core.model.invoice.InvoiceMaster;
/**
 * This class is a Java interface that defines a data access contract
 *
 */
@Repository("invoiceRepository")
public interface InvoiceMasterRepository extends JpaRepository<InvoiceMaster, Integer> {

	@Query("Select COALESCE(count(m),0) from InvoiceMaster m,InvoiceTransaction t, PatientAccount p, PhysicianAccount ph "
			+ "where m.id=t.invoiceId  and m.patientId=p.id and m.physicianId=ph.id and m.invoiceNumber like %:invoiceNo% "
			+ "and p.patientName like %:patientName% and ph.physicianName like %:phyName% and t.rxNumber like %:rxNo% group by m.id ")
	int countAllWithoutDate(@Param("invoiceNo") String invoiceNo, @Param("patientName") String patientName, @Param("phyName") String phyName, 
			@Param("rxNo") String rxNo);
	
	@Query("Select COALESCE(count(m),0) from InvoiceMaster m,InvoiceTransaction t, PatientAccount p, PhysicianAccount ph "
			+ "where m.id=t.invoiceId  and m.patientId=p.id and m.physicianId=ph.id and m.invoiceNumber like %:invoiceNo% and "
			+ "p.patientName like %:patientName% and ph.physicianName like %:phyName% and m.invoiceDate >=:fromDate and t.rxNumber like %:rxNo% group by m.id ")
	int countAllWithoutFromDate(@Param("invoiceNo") String invoiceNo, @Param("patientName") String patientName, @Param("phyName") String phyName, 
			@Param("fromDate") Date fromDate, @Param("rxNo") String rxNo);

	@Query("Select COALESCE(count(m),0) from InvoiceMaster m,InvoiceTransaction t, PatientAccount p, PhysicianAccount ph "
			+ "where m.id=t.invoiceId  and m.patientId=p.id and m.physicianId=ph.id and m.invoiceNumber like %:invoiceNo% and "
			+ "p.patientName like %:patientName% and ph.physicianName like %:phyName% and m.invoiceDate <=:toDate and t.rxNumber like %:rxNo% group by m.id ")
	int countAllWithoutToDate(@Param("invoiceNo") String invoiceNo, @Param("patientName") String patientName, @Param("phyName") String phyName, 
			@Param("toDate") Date toDate, @Param("rxNo") String rxNo);

	@Query("Select COALESCE(count(m),0) from InvoiceMaster m,InvoiceTransaction t, PatientAccount p, PhysicianAccount ph "
			+ "where m.id=t.invoiceId  and m.patientId=p.id and m.physicianId=ph.id and m.invoiceNumber like %:invoiceNo% and "
			+ "p.patientName like %:patientName% and ph.physicianName like %:phyName% and m.invoiceDate >=:fromDate and m.invoiceDate <=:toDate "
			+ "and t.rxNumber like %:rxNo% group by m.id ")
	int countAllWithDate(@Param("invoiceNo") String invoiceNo, @Param("patientName") String patientName, @Param("phyName") String phyName,
			@Param("fromDate") Date fromDate, @Param("toDate") Date toDate, @Param("rxNo") String rxNo);

	

	@Query("Select m from InvoiceMaster m order by m.id desc ")
	Page<InvoiceMaster> getAllRecords(Pageable page);

	@Query("Select m from InvoiceMaster m,InvoiceTransaction t, PatientAccount p, PhysicianAccount ph "
			+ "where m.id=t.invoiceId  and m.patientId=p.id and m.physicianId=ph.id and m.invoiceNumber like %:invoiceNo% and "
			+ "p.patientName like %:patientName% and ph.physicianName like %:phyName%  and t.rxNumber like %:rxNo%  group by m.id  order by m.id desc ")
	Page<InvoiceMaster> findAllWithoutDate(@Param("invoiceNo") String invoiceNo, @Param("patientName") String patientName, 
			@Param("phyName") String phyName, @Param("rxNo") String rxNo, Pageable page);

	@Query("Select m from InvoiceMaster m,InvoiceTransaction t, PatientAccount p, PhysicianAccount ph "
			+ "where m.id=t.invoiceId  and m.patientId=p.id and m.physicianId=ph.id and m.invoiceNumber like %:invoiceNo% and "
			+ "p.patientName like %:patientName% and ph.physicianName like %:phyName% and m.invoiceDate >=:fromDate  and t.rxNumber like %:rxNo% "
			+ "group by m.id  order by m.id desc ")
	Page<InvoiceMaster> findAllWithoutFromDate(@Param("invoiceNo") String invoiceNo, @Param("patientName") String patientName, 
			@Param("phyName") String phyName, @Param("fromDate") Date fromDate, @Param("rxNo") String rxNo, Pageable page);
	
	@Query("Select m from InvoiceMaster m,InvoiceTransaction t, PatientAccount p, PhysicianAccount ph "
			+ "where m.id=t.invoiceId  and m.patientId=p.id and m.physicianId=ph.id and m.invoiceNumber like %:invoiceNo% and "
			+ "p.patientName like %:patientName% and ph.physicianName like %:phyName% and m.invoiceDate <=:toDate  and t.rxNumber like %:rxNo% "
			+ "group by m.id order by m.id desc ")
	Page<InvoiceMaster> findAllWithoutToDate(@Param("invoiceNo") String invoiceNo, @Param("patientName") String patientName, 
			@Param("phyName") String phyName, @Param("toDate") Date toDate, @Param("rxNo") String rxNo, Pageable page);
	
	@Query("Select m from InvoiceMaster m,InvoiceTransaction t, PatientAccount p, PhysicianAccount ph "
			+ "where m.id=t.invoiceId  and m.patientId=p.id and m.physicianId=ph.id and m.invoiceNumber like %:invoiceNo% and "
			+ "p.patientName like %:patientName% and ph.physicianName like %:phyName% and m.invoiceDate >=:fromDate and m.invoiceDate <=:toDate "
			+ " and t.rxNumber like %:rxNo% group by m.id order by m.id desc ")
	Page<InvoiceMaster> findAllWithDate(@Param("invoiceNo") String invoiceNo, @Param("patientName") String patientName, 
			@Param("phyName") String phyName, @Param("fromDate") Date fromDate, @Param("toDate") Date toDate, @Param("rxNo") String rxNo, Pageable page);
	
	// for Group Dashboard
	@Query("Select COALESCE(count(m),0) from InvoiceMaster m, PhysicianAccount ph, PhysicianGroup pg where m.physicianId=ph.id and "
			+ "m.physicianId=pg.physicianId and pg.groupId=:groupId ")
	int countAllByGroupId(@Param("groupId") int groupId);
	
	@Query("Select COALESCE(count(m),0) from InvoiceMaster m,InvoiceTransaction t, PatientAccount p, PhysicianAccount ph, PhysicianGroup pg "
			+ "where m.id=t.invoiceId  and m.patientId=p.id and m.physicianId=ph.id and m.physicianId=pg.physicianId and pg.groupId=:groupId "
			+ "and m.invoiceNumber like %:invoiceNo% and p.patientName like %:patientName% and ph.physicianName like %:phyName%  and t.rxNumber like %:rxNo% ")
	int countAllGroupWithoutDate(@Param("invoiceNo") String invoiceNo, @Param("patientName") String patientName, @Param("phyName") String phyName, 
			@Param("groupId") int groupId, @Param("rxNo") String rxNo);
	
	@Query("Select COALESCE(count(m),0) from InvoiceMaster m, InvoiceTransaction t,PatientAccount p, PhysicianAccount ph, PhysicianGroup pg "
			+ "where m.id=t.invoiceId  and m.patientId=p.id and m.physicianId=ph.id and m.physicianId=pg.physicianId and pg.groupId=:groupId "
			+ "and m.invoiceNumber like %:invoiceNo% and p.patientName like %:patientName% and ph.physicianName like %:phyName% and "
			+ "m.invoiceDate >=:fromDate  and t.rxNumber like %:rxNo% ")
	int countAllGroupWithoutFromDate(@Param("invoiceNo") String invoiceNo, @Param("patientName") String patientName, @Param("phyName") String phyName, 
			@Param("fromDate") Date fromDate, @Param("groupId") int groupId, @Param("rxNo") String rxNo);
	
	@Query("Select COALESCE(count(m),0) from InvoiceMaster m,InvoiceTransaction t, PatientAccount p, PhysicianAccount ph, PhysicianGroup pg "
			+ "where m.id=t.invoiceId  and m.patientId=p.id and m.physicianId=ph.id and m.physicianId=pg.physicianId and pg.groupId=:groupId "
			+ "and m.invoiceNumber like %:invoiceNo% and p.patientName like %:patientName% and ph.physicianName like %:phyName% and "
			+ "m.invoiceDate <=:toDate and t.rxNumber like %:rxNo% ")
	int countAllGroupWithoutToDate(@Param("invoiceNo") String invoiceNo, @Param("patientName") String patientName, @Param("phyName") String phyName, 
			@Param("toDate") Date toDate, @Param("groupId") int groupId, @Param("rxNo") String rxNo);
	
	@Query("Select COALESCE(count(m),0) from InvoiceMaster m,InvoiceTransaction t, PatientAccount p, PhysicianAccount ph, PhysicianGroup pg "
			+ "where m.id=t.invoiceId  and m.patientId=p.id and m.physicianId=ph.id and m.physicianId=pg.physicianId and pg.groupId=:groupId "
			+ "and m.invoiceNumber like %:invoiceNo% and p.patientName like %:patientName% and ph.physicianName like %:phyName% and "
			+ "m.invoiceDate >=:fromDate and m.invoiceDate <=:toDate  and t.rxNumber like %:rxNo% ")
	int countAllGroupWithDate(@Param("invoiceNo") String invoiceNo, @Param("patientName") String patientName, @Param("phyName") String phyName,
			@Param("fromDate") Date fromDate, @Param("toDate") Date toDate, @Param("groupId") int groupId, @Param("rxNo") String rxNo);
	

	
	
	@Query("Select m from InvoiceMaster m, PhysicianAccount ph, PhysicianGroup pg where m.physicianId=ph.id and m.physicianId=pg.physicianId "
			+ "and pg.groupId=:groupId order by m.id desc ")
	Page<InvoiceMaster> getAllGroupRecords(@Param("groupId") int groupId, Pageable page);

	@Query("Select m from InvoiceMaster m,InvoiceTransaction t, PatientAccount p, PhysicianAccount ph, PhysicianGroup pg "
			+ "where m.id=t.invoiceId  and m.patientId=p.id and m.physicianId=ph.id and m.physicianId=pg.physicianId and pg.groupId=:groupId "
			+ "and m.invoiceNumber like %:invoiceNo% and p.patientName like %:patientName% and ph.physicianName like %:phyName% "
			+ " and t.rxNumber like %:rxNo%  order by m.id desc ")
	Page<InvoiceMaster> findAllGroupWithoutDate(@Param("invoiceNo") String invoiceNo, @Param("patientName") String patientName, 
			@Param("phyName") String phyName, @Param("groupId") int groupId, @Param("rxNo") String rxNo, Pageable page);

	@Query("Select m from InvoiceMaster m,InvoiceTransaction t, PatientAccount p, PhysicianAccount ph, PhysicianGroup pg "
			+ "where m.patientId=p.id and m.physicianId=ph.id and m.physicianId=pg.physicianId and pg.groupId=:groupId and "
			+ "m.invoiceNumber like %:invoiceNo% and p.patientName like %:patientName% and ph.physicianName like %:phyName% and "
			+ "m.invoiceDate >=:fromDate  and t.rxNumber like %:rxNo% order by m.id desc ")
	Page<InvoiceMaster> findAllGroupWithFromDate(@Param("invoiceNo") String invoiceNo, @Param("patientName") String patientName, 
			@Param("phyName") String phyName, @Param("fromDate") Date fromDate, @Param("groupId") int groupId, @Param("rxNo") String rxNo, Pageable page);
	
	@Query("Select m from InvoiceMaster m,InvoiceTransaction t, PatientAccount p, PhysicianAccount ph, PhysicianGroup pg "
			+ "where m.id=t.invoiceId  and m.patientId=p.id and m.physicianId=ph.id and m.physicianId=pg.physicianId and pg.groupId=:groupId "
			+ "and m.invoiceNumber like %:invoiceNo% and p.patientName like %:patientName% and ph.physicianName like %:phyName% and "
			+ "m.invoiceDate <=:toDate  and t.rxNumber like %:rxNo% order by m.id desc ")
	Page<InvoiceMaster> findAllGroupWithToDate(@Param("invoiceNo") String invoiceNo, @Param("patientName") String patientName, 
			@Param("phyName") String phyName, @Param("toDate") Date toDate, @Param("groupId") int groupId, @Param("rxNo") String rxNo, Pageable page);
	
	@Query("Select m from InvoiceMaster m, InvoiceTransaction t,PatientAccount p, PhysicianAccount ph, PhysicianGroup pg "
			+ "where m.id=t.invoiceId  and m.patientId=p.id and m.physicianId=ph.id and m.physicianId=pg.physicianId and pg.groupId=:groupId "
			+ "and m.invoiceNumber like %:invoiceNo% and p.patientName like %:patientName% and ph.physicianName like %:phyName%  and "
			+ "m.invoiceDate >=:fromDate and m.invoiceDate <=:toDate  and t.rxNumber like %:rxNo% order by m.id desc ")
	Page<InvoiceMaster> findAllGroupWithDate(@Param("invoiceNo") String invoiceNo, @Param("patientName") String patientName, 
			@Param("phyName") String phyName, @Param("fromDate") Date fromDate, @Param("toDate") Date toDate, @Param("groupId") int groupId, 
			@Param("rxNo") String rxNo, Pageable page);
	
	
	// Dashboard Queries
	@Query(value = "Select  COALESCE(sum(m.total),0) from InvoiceMaster m, InvoiceTransaction t "
			+ "where m.id=t.invoiceId  and m.physicianId=:physicianId and t.paymentstatus='Paid' and t.paymentdate>:fromDate and t.paymentdate<=:toDate ")
	double fetchTotalInvoicePaidByInvoiceDate( @Param("physicianId") int physicianId, @Param("fromDate") java.util.Date fromDate, @Param("toDate") java.util.Date toDate);
	
	@Query(value = "Select COALESCE(sum(m.total),0) from InvoiceMaster m, InvoiceTransaction t "
			+ "where m.id=t.invoiceId  and m.physicianId=:physicianId and t.paymentstatus='Paid' and t.paymentdate>:fromDate and t.paymentdate<=:toDate ")
	double fetchWeekTotalInvoicePaidByInvoiceDate( @Param("physicianId") int physicianId, @Param("fromDate") java.util.Date fromDate, @Param("toDate") java.util.Date toDate);
	
	@Query(value = "Select COALESCE(sum(m.total),0) from InvoiceMaster m, InvoiceTransaction t "
			+ "where m.id=t.invoiceId  and m.physicianId=:physicianId and t.paymentstatus='Paid' and t.paymentdate>:fromDate and t.paymentdate<=:toDate ")
	double fetchMonthTotalInvoicePaidByInvoiceDate( @Param("physicianId") int physicianId, @Param("fromDate") java.util.Date fromDate, @Param("toDate") java.util.Date toDate);

	@Query(value = "Select COALESCE(sum(m.total),0) from InvoiceMaster m, InvoiceTransaction t,PhysicianGroup p "
			+ "where m.id=t.invoiceId   and m.physicianId=p.physicianId and p.id=:groupId  and t.paymentstatus='Paid' and t.paymentdate>:fromDate and t.paymentdate<=:toDate")
	double fetchGroupTotalInvoicePaidByInvoiceDate( @Param("groupId") int groupId, @Param("fromDate") java.util.Date fromDate, @Param("toDate") java.util.Date toDate);
	
	@Query(value = "Select COALESCE(sum(m.total),0) from InvoiceMaster m, InvoiceTransaction t,PhysicianGroup p "
			+ "where m.id=t.invoiceId   and m.physicianId=p.physicianId and p.id=:groupId  and t.paymentstatus='Paid' and t.paymentdate>:fromDate and t.paymentdate<=:toDate")
	double fetchGroupWeekTotalInvoicePaidByInvoiceDate( @Param("groupId") int groupId, @Param("fromDate") java.util.Date fromDate, @Param("toDate") java.util.Date toDate);
	
	@Query(value = "Select COALESCE(sum(m.total),0) from InvoiceMaster m, InvoiceTransaction t,PhysicianGroup p "
			+ "where m.id=t.invoiceId  and m.physicianId=p.physicianId and p.id=:groupId  and t.paymentstatus='Paid' and t.paymentdate>:fromDate and t.paymentdate<=:toDate")
	double fetchGroupMonthTotalInvoicePaidByInvoiceDate( @Param("groupId") int groupId, @Param("fromDate") java.util.Date fromDate, @Param("toDate") java.util.Date toDate);

	
	//patient login
	@Query("Select  COALESCE(count(m),0)  from InvoiceMaster m where m.patientId=:patientId ")
	int findAllByPatientId(@Param("patientId") int patientId);
	
	@Query("Select COALESCE(count(m),0) from InvoiceMaster m,InvoiceTransaction t, PatientAccount p, PhysicianAccount ph "
			+ "where m.id=t.invoiceId  and m.patientId=p.id and m.physicianId=ph.id and m.patientId=:patientId and m.invoiceNumber like %:invoiceNo% and "
			+ "p.patientName like %:patientName% and ph.physicianName like %:phyName%  and t.rxNumber like %:rxNo% ")
	int countPatientWithoutDate(@Param("patientId") int patientId,@Param("invoiceNo") String invoiceNo, @Param("patientName") String patientName, 
			@Param("phyName") String phyName, @Param("rxNo") String rxNo);
	
	@Query("Select COALESCE(count(m),0) from InvoiceMaster m,InvoiceTransaction t, PatientAccount p, PhysicianAccount ph "
			+ "where m.id=t.invoiceId  and m.patientId=p.id and m.physicianId=ph.id and m.patientId=:patientId and m.invoiceNumber like %:invoiceNo% "
			+ "and p.patientName like %:patientName% and ph.physicianName like %:phyName% and m.invoiceDate >=:fromDate  and t.rxNumber like %:rxNo% ")
	int countPatientWithoutFromDate(@Param("patientId") int patientId,@Param("invoiceNo") String invoiceNo, @Param("patientName") String patientName, 
			@Param("phyName") String phyName, @Param("fromDate") Date fromDate, @Param("rxNo") String rxNo);
	
	@Query("Select COALESCE(count(m),0) from InvoiceMaster m,InvoiceTransaction t, PatientAccount p, PhysicianAccount ph "
			+ "where m.id=t.invoiceId  and m.patientId=p.id and m.physicianId=ph.id and m.patientId=:patientId and m.invoiceNumber like %:invoiceNo% "
			+ "and p.patientName like %:patientName% and ph.physicianName like %:phyName% and m.invoiceDate <=:toDate and t.rxNumber like %:rxNo% ")
	int countPatientWithoutToDate(@Param("patientId") int patientId,@Param("invoiceNo") String invoiceNo, @Param("patientName") String patientName, 
			@Param("phyName") String phyName, @Param("toDate") Date toDate, @Param("rxNo") String rxNo);
	
	@Query("Select COALESCE(count(m),0) from InvoiceMaster m,InvoiceTransaction t, PatientAccount p, PhysicianAccount ph "
			+ "where m.id=t.invoiceId  and m.patientId=p.id and m.physicianId=ph.id and m.patientId=:patientId and m.invoiceNumber like %:invoiceNo% "
			+ "and p.patientName like %:patientName% and ph.physicianName like %:phyName% and m.invoiceDate >=:fromDate and m.invoiceDate <=:toDate  and t.rxNumber like %:rxNo% ")
	int countPatientWithDate(@Param("patientId") int patientId,@Param("invoiceNo") String invoiceNo, @Param("patientName") String patientName, 
			@Param("phyName") String phyName, @Param("fromDate") Date fromDate, @Param("toDate") Date toDate, @Param("rxNo") String rxNo);

	
	@Query("Select m from InvoiceMaster m where m.patientId=:patientId order by id desc ")
	Page<InvoiceMaster> getPatientRecords(@Param("patientId") int patientId, Pageable page);

	@Query("Select m from InvoiceMaster m,InvoiceTransaction t, PatientAccount p, PhysicianAccount ph where m.id=t.invoiceId  and m.patientId=p.id "
			+ "and m.physicianId=ph.id and m.patientId=:patientId and m.invoiceNumber like %:invoiceNo% and p.patientName like %:patientName% and "
			+ "ph.physicianName like %:phyName%  and t.rxNumber like %:rxNo%  order by m.id desc ")
	Page<InvoiceMaster> findPatientWithoutDate(@Param("patientId") int patientId,@Param("invoiceNo") String invoiceNo, @Param("patientName") String patientName, 
			@Param("phyName") String phyName, @Param("rxNo") String rxNo, Pageable page);

	@Query("Select m from InvoiceMaster m,InvoiceTransaction t, PatientAccount p, PhysicianAccount ph where m.id=t.invoiceId  and m.patientId=p.id "
			+ "and m.physicianId=ph.id and m.patientId=:patientId and m.invoiceNumber like %:invoiceNo% and p.patientName like %:patientName% and "
			+ "ph.physicianName like %:phyName% and m.invoiceDate >=:fromDate  and t.rxNumber like %:rxNo% order by m.id desc ")
	Page<InvoiceMaster> findPatientWithoutFromDate(@Param("patientId") int patientId,@Param("invoiceNo") String invoiceNo, @Param("patientName") String patientName, 
			@Param("phyName") String phyName, @Param("fromDate") Date fromDate, @Param("rxNo") String rxNo, Pageable page);
	
	@Query("Select m from InvoiceMaster m,InvoiceTransaction t, PatientAccount p, PhysicianAccount ph where m.id=t.invoiceId  and m.patientId=p.id "
			+ "and m.physicianId=ph.id and m.patientId=:patientId and m.invoiceNumber like %:invoiceNo% and p.patientName like %:patientName% and "
			+ "ph.physicianName like %:phyName% and m.invoiceDate <=:toDate and t.rxNumber like %:rxNo%  order by m.id desc ")
	Page<InvoiceMaster> findPatientWithoutToDate(@Param("patientId") int patientId,@Param("invoiceNo") String invoiceNo, @Param("patientName") String patientName, 
			@Param("phyName") String phyName, @Param("toDate") Date toDate, @Param("rxNo") String rxNo, Pageable page);
	
	@Query("Select m from InvoiceMaster m,InvoiceTransaction t, PatientAccount p, PhysicianAccount ph where m.id=t.invoiceId  and m.patientId=p.id "
			+ "and m.physicianId=ph.id and m.patientId=:patientId and m.invoiceNumber like %:invoiceNo% and p.patientName like %:patientName% and "
			+ "ph.physicianName like %:phyName% and m.invoiceDate >=:fromDate and m.invoiceDate <=:toDate  and t.rxNumber like %:rxNo% order by m.id desc ")
	Page<InvoiceMaster> findPatientWithDate(@Param("patientId") int patientId,@Param("invoiceNo") String invoiceNo, @Param("patientName") String patientName, 
			@Param("phyName") String phyName, @Param("fromDate") Date fromDate, @Param("toDate") Date toDate, @Param("rxNo") String rxNo, Pageable page);
	
	//physician login
	@Query("Select  COALESCE(count(m),0)  from InvoiceMaster m where m.physicianId=:physicianId ")
	int findAllByPhysicianId(@Param("physicianId") int physicianId);
	
	@Query("Select COALESCE(count(m),0) from InvoiceMaster m,InvoiceTransaction t, PatientAccount p, PhysicianAccount ph where m.id=t.invoiceId  and "
			+ "m.patientId=p.id and m.physicianId=ph.id and m.physicianId=:physicianId and m.invoiceNumber like %:invoiceNo% and "
			+ "p.patientName like %:patientName% and ph.physicianName like %:phyName%  and t.rxNumber like %:rxNo%  group by m.id ")
	int countPhysicianWithoutDate(@Param("physicianId") int physicianId,@Param("invoiceNo") String invoiceNo, @Param("patientName") String patientName, @Param("phyName") String phyName, @Param("rxNo") String rxNo);
	
	@Query("Select COALESCE(count(m),0) from InvoiceMaster m,InvoiceTransaction t, PatientAccount p, PhysicianAccount ph where m.id=t.invoiceId  and "
			+ "m.patientId=p.id and m.physicianId=ph.id and m.physicianId=:physicianId and m.invoiceNumber like %:invoiceNo% and "
			+ "p.patientName like %:patientName% and ph.physicianName like %:phyName% and m.invoiceDate >=:fromDate  and t.rxNumber like %:rxNo% group by m.id  ")
	int countPhysicianWithoutFromDate(@Param("physicianId") int physicianId,@Param("invoiceNo") String invoiceNo, @Param("patientName") String patientName, @Param("phyName") String phyName, 
		@Param("fromDate") Date fromDate, @Param("rxNo") String rxNo);
	
	@Query("Select COALESCE(count(m),0) from InvoiceMaster m,InvoiceTransaction t, PatientAccount p, PhysicianAccount ph where m.id=t.invoiceId  and "
			+ "m.patientId=p.id and m.physicianId=ph.id and m.physicianId=:physicianId and m.invoiceNumber like %:invoiceNo% and "
			+ "p.patientName like %:patientName% and ph.physicianName like %:phyName% and m.invoiceDate <=:toDate and t.rxNumber like %:rxNo% group by m.id ")
	int countPhysicianWithoutToDate(@Param("physicianId") int physicianId,@Param("invoiceNo") String invoiceNo, @Param("patientName") String patientName, @Param("phyName") String phyName, 
		@Param("toDate") Date toDate, @Param("rxNo") String rxNo);
	
	@Query("Select COALESCE(count(m),0) from InvoiceMaster m,InvoiceTransaction t, PatientAccount p, PhysicianAccount ph where m.id=t.invoiceId  and "
			+ "m.patientId=p.id and m.physicianId=ph.id and m.physicianId=:physicianId and m.invoiceNumber like %:invoiceNo% and "
			+ "p.patientName like %:patientName% and ph.physicianName like %:phyName% and m.invoiceDate >=:fromDate and m.invoiceDate <=:toDate  and t.rxNumber like %:rxNo% group by m.id  ")
	int countPhysicianWithDate(@Param("physicianId") int physicianId,@Param("invoiceNo") String invoiceNo, @Param("patientName") String patientName, @Param("phyName") String phyName,
		@Param("fromDate") Date fromDate, @Param("toDate") Date toDate, @Param("rxNo") String rxNo);

	
	@Query("Select m from InvoiceMaster m where m.physicianId=:physicianId order by m.id desc ")
	Page<InvoiceMaster> getPhysicianRecords(@Param("physicianId") int physicianId, Pageable page);

	@Query("Select m from InvoiceMaster m,InvoiceTransaction t, PatientAccount p, PhysicianAccount ph where m.id=t.invoiceId  and m.patientId=p.id "
			+ "and m.physicianId=ph.id and m.physicianId=:physicianId and m.invoiceNumber like %:invoiceNo% and p.patientName like %:patientName% and "
			+ "ph.physicianName like %:phyName%  and t.rxNumber like %:rxNo% group by m.id  order by m.id desc ")
	Page<InvoiceMaster> findPhysicianWithoutDate(@Param("physicianId") int physicianId,@Param("invoiceNo") String invoiceNo, 
			@Param("patientName") String patientName, @Param("phyName") String phyName, @Param("rxNo") String rxNo, Pageable page);

	@Query("Select m from InvoiceMaster m,InvoiceTransaction t, PatientAccount p, PhysicianAccount ph where m.id=t.invoiceId  and m.patientId=p.id and "
			+ "m.physicianId=ph.id and m.physicianId=:physicianId and m.invoiceNumber like %:invoiceNo% and p.patientName like %:patientName% and "
			+ "ph.physicianName like %:phyName% and m.invoiceDate >=:fromDate and t.rxNumber like %:rxNo% group by m.id  order by m.id desc ")
	Page<InvoiceMaster> findPhysicianWithoutFromDate(@Param("physicianId") int physicianId,@Param("invoiceNo") String invoiceNo, @Param("patientName") String patientName, 
			@Param("phyName") String phyName, @Param("fromDate") Date fromDate, @Param("rxNo") String rxNo, Pageable page);
	
	@Query("Select m from InvoiceMaster m,InvoiceTransaction t, PatientAccount p, PhysicianAccount ph where m.id=t.invoiceId  and m.patientId=p.id and "
			+ "m.physicianId=ph.id and m.physicianId=:physicianId and m.invoiceNumber like %:invoiceNo% and p.patientName like %:patientName% and "
			+ "ph.physicianName like %:phyName% and m.invoiceDate <=:toDate and t.rxNumber like %:rxNo% group by m.id  order by m.id desc ")
	Page<InvoiceMaster> findPhysicianWithoutToDate(@Param("physicianId") int physicianId,@Param("invoiceNo") String invoiceNo, @Param("patientName") String patientName, 
			@Param("phyName") String phyName, @Param("toDate") Date toDate, @Param("rxNo") String rxNo, Pageable page);
	
	@Query("Select m from InvoiceMaster m,InvoiceTransaction t, PatientAccount p, PhysicianAccount ph where m.id=t.invoiceId  and m.patientId=p.id and "
			+ "m.physicianId=ph.id and m.physicianId=:physicianId and m.invoiceNumber like %:invoiceNo% and p.patientName like %:patientName% and "
			+ "ph.physicianName like %:phyName%  and m.invoiceDate >=:fromDate and m.invoiceDate <=:toDate  and t.rxNumber like %:rxNo% group by m.id  order by m.id desc ")
	Page<InvoiceMaster> findPhysicianWithDate(@Param("physicianId") int physicianId,@Param("invoiceNo") String invoiceNo, @Param("patientName") String patientName, 
			@Param("phyName") String phyName, @Param("fromDate") Date fromDate, @Param("toDate") Date toDate, @Param("rxNo") String rxNo, Pageable page);


	/***********************  Invoice List Report Queries **************************/
	
	@Query(value = "Select  COALESCE(count(m),0)  from InvoiceMaster m, InvoiceTransaction t, PhysicianAccount p, PatientAccount pat, PhysicianGroup gph, GroupMaster g "
			+ "where m.id=t.invoiceId and m.patientId=pat.id and m.physicianId=p.id and gph.physicianId=p.id and g.id=gph.groupId and "
			+ "p.physicianName like %:physician% and pat.patientName like %:patient% and m.invoiceNumber like %:invoiceNo% and "
			+ "t.rxNumber like %:rxNo% and COALESCE(g.groupName, '') like %:group% group by m.id   ")
	int reportCountByFilter(@Param("physician") String physician, @Param("patient") String patient, 
			@Param("group") String group,  @Param("invoiceNo") String invoiceNo,  @Param("rxNo") String rxNumber);

	@Query(value = "Select  COALESCE(count(m),0)  from InvoiceMaster m, InvoiceTransaction t, PhysicianAccount p, PatientAccount pat, PhysicianGroup gph, GroupMaster g "
			+ "where m.id=t.invoiceId and m.patientId=pat.id and m.physicianId=p.id and gph.physicianId=p.id and g.id=gph.groupId and "
			+ "p.physicianName like %:physician% and pat.patientName like %:patient% and m.invoiceNumber like %:invoiceNo% and "
			+ "t.rxNumber like %:rxNo% and COALESCE(g.groupName, '') like %:group% and m.invoiceDate >=:fromDate group by m.id   ")
	int reportCountByFilterWithFromDate(@Param("physician") String physician, @Param("patient") String patient, 
			@Param("group") String group,  @Param("invoiceNo") String invoiceNo,  @Param("rxNo") String rxNumber, @Param("fromDate") Date fromDate);
	
	@Query(value = "Select  COALESCE(count(m),0)  from InvoiceMaster m, InvoiceTransaction t, PhysicianAccount p, PatientAccount pat, PhysicianGroup gph, GroupMaster g "
			+ "where m.id=t.invoiceId and m.patientId=pat.id and m.physicianId=p.id and gph.physicianId=p.id and g.id=gph.groupId and "
			+ "p.physicianName like %:physician% and pat.patientName like %:patient% and m.invoiceNumber like %:invoiceNo% and "
			+ "t.rxNumber like %:rxNo% and COALESCE(g.groupName, '') like %:group% and m.invoiceDate <=:toDate  group by m.id   ")
	int reportCountByFilterWithToDate(@Param("physician") String physician, @Param("patient") String patient, 
			@Param("group") String group,  @Param("invoiceNo") String invoiceNo,  @Param("rxNo") String rxNumber, @Param("toDate") Date toDate);

	@Query(value = "Select  COALESCE(count(m),0)  from InvoiceMaster m, InvoiceTransaction t, PhysicianAccount p, PatientAccount pat, PhysicianGroup gph, GroupMaster g "
			+ "where m.id=t.invoiceId and m.patientId=pat.id and m.physicianId=p.id and gph.physicianId=p.id and g.id=gph.groupId and "
			+ "p.physicianName like %:physician% and pat.patientName like %:patient% and m.invoiceNumber like %:invoiceNo% and "
			+ "t.rxNumber like %:rxNo% and COALESCE(g.groupName, '') like %:group%  and m.invoiceDate >=:fromDate and m.invoiceDate <=:toDate group by m.id   ")
	int reportCountByFilterWithDate(@Param("physician") String physician, @Param("patient") String patient, 
			@Param("group") String group,  @Param("invoiceNo") String invoiceNo,  @Param("rxNo") String rxNumber, 
			@Param("fromDate") Date fromDate, @Param("toDate") Date toDate);
	
	
	@Query(value = "Select m.invoiceNumber, m.invoiceDate, t.rxNumber, p.physicianName, pat.patientName, t.item, t.quantity, m.subTotal, "
			+ "m.tax, m.total, m.id, t.paymentstatus, t.paymenttype "
			+ "from InvoiceMaster m, InvoiceTransaction t, PhysicianAccount p, PatientAccount pat, PhysicianGroup gph, GroupMaster g  "
			+ "where m.id=t.invoiceId and m.patientId=pat.id and m.physicianId=p.id and gph.physicianId=p.id and g.id=gph.groupId and "
			+ "p.physicianName like %:physician% and pat.patientName like %:patient% and m.invoiceNumber like %:orderNo% and "
			+ "t.rxNumber like %:rxNo% and COALESCE(g.groupName, '') like %:group% group by m.id  ")
	Page<Object[]> reportByFilter(@Param("physician") String physician, @Param("patient") String patient, 
			@Param("group") String group,  @Param("orderNo") String orderNo,  @Param("rxNo") String rxNumber, Pageable page);
	

	
	
	@Query(value = "Select m.invoiceNumber, m.invoiceDate, t.rxNumber, p.physicianName, pat.patientName, t.item, t.quantity, m.subTotal, "
			+ "m.tax, m.total, m.id, t.paymentstatus, t.paymenttype "
			+ "from InvoiceMaster m, InvoiceTransaction t, PhysicianAccount p, PatientAccount pat, PhysicianGroup gph, GroupMaster g  "
			+ "where m.id=t.invoiceId and m.patientId=pat.id and m.physicianId=p.id and gph.physicianId=p.id and g.id=gph.groupId and "
			+ "p.physicianName like %:physician% and pat.patientName like %:patient% and m.invoiceNumber like %:orderNo% and "
			+ "t.rxNumber like %:rxNo% and COALESCE(g.groupName, '') like %:group% and m.invoiceDate >=:fromDate   group by m.id  ")	
	Page<Object[]> reportByFilterWithFromDate(@Param("physician") String physician, @Param("patient") String patient, 
			@Param("group") String group,  @Param("orderNo") String orderNo,  @Param("rxNo") String rxNumber, @Param("fromDate") Date fromDate, Pageable page);
	
	
	@Query(value = "Select m.invoiceNumber, m.invoiceDate, t.rxNumber, p.physicianName, pat.patientName, t.item, t.quantity, m.subTotal, "
			+ "m.tax, m.total, m.id, t.paymentstatus, t.paymenttype "
			+ "from InvoiceMaster m, InvoiceTransaction t, PhysicianAccount p, PatientAccount pat, PhysicianGroup gph, GroupMaster g  "
			+ "where m.id=t.invoiceId and m.patientId=pat.id and m.physicianId=p.id and gph.physicianId=p.id and g.id=gph.groupId and "
			+ "p.physicianName like %:physician% and pat.patientName like %:patient% and m.invoiceNumber like %:orderNo% and "
			+ "t.rxNumber like %:rxNo% and COALESCE(g.groupName, '') like %:group% and m.invoiceDate <=:toDate group by m.id  ")
	Page<Object[]> reportByFilterWithToDate(@Param("physician") String physician, @Param("patient") String patient, 
			@Param("group") String group,  @Param("orderNo") String orderNo,  @Param("rxNo") String rxNumber, @Param("toDate") Date toDate, Pageable page);
	
	
	@Query(value = "Select m.invoiceNumber, m.invoiceDate, t.rxNumber, p.physicianName, pat.patientName, t.item, t.quantity, m.subTotal, "
			+ "m.tax, m.total, m.id, t.paymentstatus, t.paymenttype "
			+ "from InvoiceMaster m, InvoiceTransaction t, PhysicianAccount p, PatientAccount pat, PhysicianGroup gph, GroupMaster g  "
			+ "where m.id=t.invoiceId and m.patientId=pat.id and m.physicianId=p.id and gph.physicianId=p.id and g.id=gph.groupId and "
			+ "p.physicianName like %:physician% and pat.patientName like %:patient% and m.invoiceNumber like %:orderNo% and "
			+ "t.rxNumber like %:rxNo% and COALESCE(g.groupName, '') like %:group% and m.invoiceDate >=:fromDate and m.invoiceDate <=:toDate group by m.id  ")
	Page<Object[]> reportByFilterWithDate(@Param("physician") String physician, @Param("patient") String patient, 
			@Param("group") String group,  @Param("orderNo") String orderNo,  @Param("rxNo") String rxNumber, 
			@Param("fromDate") Date fromDate, @Param("toDate") Date toDate, Pageable page);

	
}
