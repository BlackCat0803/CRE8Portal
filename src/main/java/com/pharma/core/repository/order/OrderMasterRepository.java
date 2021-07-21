package com.pharma.core.repository.order;

import java.sql.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pharma.core.model.order.OrderMaster;
/**
 * This class is a Java interface that defines a data access contract
 *
 */
@Repository("orderMasterRepository")
public interface OrderMasterRepository extends JpaRepository<OrderMaster, Integer>  {
	
	// Admin and super admin login

	// select * from order_master m, order_transaction t, phy_info p, patient_profile pat, prescription_transaction pre
	// where m.order_id=t.order_id and m.patient_id=pat.patient_id and m.physician_id=p.physician_id
	// and t.prescription_tran_id=pre.prescription_tran_id group by m.order_id;
	
	@Query(value = "Select   COALESCE(count(m),0) from OrderMaster m, OrderTransaction t, PhysicianAccount p, PatientAccount pat "
			+ "where m.id=t.orderId and m.patientId=pat.id and m.physicianId=p.id and "
			+ "m.orderNumber like %:orderNo% and pat.patientName like %:patientName% and p.physicianName like %:phyname% and "
			+ "t.rxStatus like %:status% and t.rxNumber like %:rxNo% GROUP BY m.id ")
	int countAllWithoutDate(@Param("orderNo") String orderNo, @Param("patientName") String patientName, @Param("phyname") String phyname, 
			@Param("status") String status, @Param("rxNo") String rxNo);
	
	@Query(value = "Select  COALESCE(count(m),0)  from OrderMaster m, OrderTransaction t, PhysicianAccount p, PatientAccount pat "
			+ "where m.id=t.orderId and m.patientId=pat.id and m.physicianId=p.id and "
			+ "m.orderNumber like %:orderNo% and pat.patientName like %:patientName% and p.physicianName like %:phyname% and "
			+ "t.rxStatus like %:status% and m.orderDate >=:fromDate and t.rxNumber like %:rxNo% GROUP BY m.id ")
	int countAllWithoutFromDate(@Param("orderNo") String orderNo, @Param("patientName") String patientName, @Param("phyname") String phyname, 
			@Param("status") String status, @Param("fromDate") Date fromDate, @Param("rxNo") String rxNo);

	@Query(value = "Select  COALESCE(count(m),0)  from OrderMaster m, OrderTransaction t, PhysicianAccount p, PatientAccount pat "
			+ "where m.id=t.orderId and m.patientId=pat.id and m.physicianId=p.id and "
			+ "m.orderNumber like %:orderNo% and pat.patientName like %:patientName% and p.physicianName like %:phyname% and "
			+ "t.rxStatus like %:status% and m.orderDate <=:toDate and t.rxNumber like %:rxNo% GROUP BY m.id ")
	int countAllWithoutToDate(@Param("orderNo") String orderNo, @Param("patientName") String patientName, @Param("phyname") String phyname, 
			@Param("status") String status, @Param("toDate") Date toDate, @Param("rxNo") String rxNo);
	
	@Query(value = "Select  COALESCE(count(m),0)  from OrderMaster m, OrderTransaction t, PhysicianAccount p, PatientAccount pat "
			+ "where m.id=t.orderId and m.patientId=pat.id and m.physicianId=p.id and "
			+ "m.orderNumber like %:orderNo% and pat.patientName like %:patientName% and p.physicianName like %:phyname% and "
			+ "t.rxStatus like %:status% and m.orderDate >=:fromDate and m.orderDate <=:toDate and t.rxNumber like %:rxNo% GROUP BY m.id ")
	int countAllWithDate(@Param("orderNo") String orderNo, @Param("patientName") String patientName, @Param("phyname") String phyname, 
			@Param("status") String status, @Param("fromDate") Date fromDate, @Param("toDate") Date toDate, @Param("rxNo") String rxNo);


	@Query(value = "Select m from OrderMaster m, OrderTransaction t, PhysicianAccount p, PatientAccount pat "
			+ "where m.id=t.orderId and m.patientId=pat.id and m.physicianId=p.id and "
			+ "m.orderNumber like %:orderNo% and pat.patientName like %:patientName% and p.physicianName like %:phyname% and "
			+ "t.rxStatus like %:status% and t.rxNumber like %:rxNo% GROUP BY m.id  order by m.id desc ")
	Page<OrderMaster> findAllWithoutDate(@Param("orderNo") String orderNo, @Param("patientName") String patientName, 
			@Param("phyname") String phyname, @Param("status") String status, @Param("rxNo") String rxNo, Pageable page);

	
	@Query(value = "Select m from OrderMaster m, OrderTransaction t, PhysicianAccount p, PatientAccount pat "
			+ "where m.id=t.orderId and m.patientId=pat.id and m.physicianId=p.id and "
			+ "m.orderNumber like %:orderNo% and pat.patientName like %:patientName% and p.physicianName like %:phyname% and "
			+ "t.rxStatus like %:status% and m.orderDate >=:fromDate and t.rxNumber like %:rxNo% GROUP BY m.id  order by m.id desc ")
	Page<OrderMaster> findAllWithoutFromDate(@Param("orderNo") String orderNo, @Param("patientName") String patientName, @Param("phyname") String phyname, 
			@Param("status") String status, @Param("fromDate") Date fromDate, @Param("rxNo") String rxNo, Pageable page);

	@Query(value = "Select m from OrderMaster m, OrderTransaction t, PhysicianAccount p, PatientAccount pat "
			+ "where m.id=t.orderId and m.patientId=pat.id and m.physicianId=p.id and "
			+ "m.orderNumber like %:orderNo% and pat.patientName like %:patientName% and p.physicianName like %:phyname% and "
			+ "t.rxStatus like %:status% and m.orderDate <=:toDate and t.rxNumber like %:rxNo% GROUP BY m.id  order by m.id desc ")
	Page<OrderMaster> findAllWithoutToDate(@Param("orderNo") String orderNo, @Param("patientName") String patientName, @Param("phyname") String phyname, 
			@Param("status") String status, @Param("toDate") Date toDate, @Param("rxNo") String rxNo, Pageable page);
	
	@Query(value = "Select m from OrderMaster m, OrderTransaction t, PhysicianAccount p, PatientAccount pat "
			+ "where m.id=t.orderId and m.patientId=pat.id and m.physicianId=p.id and "
			+ "m.orderNumber like %:orderNo% and pat.patientName like %:patientName% and p.physicianName like %:phyname% and "
			+ "t.rxStatus like %:status% and m.orderDate >=:fromDate and m.orderDate <=:toDate and t.rxNumber like %:rxNo% GROUP BY m.id  order by m.id desc ")
	Page<OrderMaster> findAllWithDate(@Param("orderNo") String orderNo, @Param("patientName") String patientName, @Param("phyname") String phyname, 
			@Param("status") String status, @Param("fromDate") Date fromDate, @Param("toDate") Date toDate, @Param("rxNo") String rxNo, Pageable page);
	
	
	
	// Patient Login 
	@Query("Select  COALESCE(count(m),0)  from OrderMaster m where m.patientId=:patientId ")
	int findAllByPatientId(@Param("patientId") int patientId);
	
	@Query(value = "Select  COALESCE(count(m),0)  from OrderMaster m, OrderTransaction t, PhysicianAccount p, PatientAccount pat "
			+ "where m.id=t.orderId and m.patientId=pat.id and m.physicianId=p.id and m.patientId=:patientId and "
			+ "m.orderNumber like %:orderNo% and pat.patientName like %:patientName% and p.physicianName like %:phyname% and "
			+ "t.rxStatus like %:status% and t.rxNumber like %:rxNo% GROUP BY m.id ")
	int countPatientWithoutDate(@Param("patientId") int patientId, @Param("orderNo") String orderNo, @Param("patientName") String patientName, 
			@Param("phyname") String phyname, @Param("status") String status, @Param("rxNo") String rxNo);
	
	@Query(value = "Select  COALESCE(count(m),0)  from OrderMaster m, OrderTransaction t, PhysicianAccount p, PatientAccount pat "
			+ "where m.id=t.orderId and m.patientId=pat.id and m.physicianId=p.id and m.patientId=:patientId and "
			+ "m.orderNumber like %:orderNo% and pat.patientName like %:patientName% and p.physicianName like %:phyname% and "
			+ "t.rxStatus like %:status% and m.orderDate >=:fromDate and t.rxNumber like %:rxNo% GROUP BY m.id ")
	int countPatientWithoutFromDate(@Param("patientId") int patientId, @Param("orderNo") String orderNo, @Param("patientName") String patientName, 
			@Param("phyname") String phyname, @Param("status") String status, @Param("fromDate") Date fromDate, @Param("rxNo") String rxNo);

	@Query(value = "Select  COALESCE(count(m),0)  from OrderMaster m, OrderTransaction t, PhysicianAccount p, PatientAccount pat "
			+ "where m.id=t.orderId and m.patientId=pat.id and m.physicianId=p.id and m.patientId=:patientId and "
			+ "m.orderNumber like %:orderNo% and pat.patientName like %:patientName% and p.physicianName like %:phyname% and "
			+ "t.rxStatus like %:status% and m.orderDate <=:toDate and t.rxNumber like %:rxNo% GROUP BY m.id ")
	int countPatientWithoutToDate(@Param("patientId") int patientId, @Param("orderNo") String orderNo, @Param("patientName") String patientName, 
			@Param("phyname") String phyname, @Param("status") String status, @Param("toDate") Date toDate, @Param("rxNo") String rxNo);
	
	@Query(value = "Select  COALESCE(count(m),0)  from OrderMaster m, OrderTransaction t, PhysicianAccount p, PatientAccount pat "
			+ "where m.id=t.orderId and m.patientId=pat.id and m.physicianId=p.id and m.patientId=:patientId and "
			+ "m.orderNumber like %:orderNo% and pat.patientName like %:patientName% and p.physicianName like %:phyname% and "
			+ "t.rxStatus like %:status% and m.orderDate >=:fromDate and m.orderDate <=:toDate and t.rxNumber like %:rxNo% GROUP BY m.id ")
	int countPatientWithDate(@Param("patientId") int patientId, @Param("orderNo") String orderNo, @Param("patientName") String patientName, 
			@Param("phyname") String phyname, @Param("status") String status, @Param("fromDate") Date fromDate, @Param("toDate") Date toDate, @Param("rxNo") String rxNo);

	
	
	@Query(value = "Select m from OrderMaster m, OrderTransaction t, PhysicianAccount p, PatientAccount pat "
			+ "where m.id=t.orderId and m.patientId=pat.id and m.physicianId=p.id and m.patientId=:patientId and "
			+ "m.orderNumber like %:orderNo% and pat.patientName like %:patientName% and p.physicianName like %:phyname% and "
			+ "t.rxStatus like %:status% and t.rxNumber like %:rxNo% GROUP BY m.id  order by m.id desc ")
	Page<OrderMaster> findPatientWithoutDate(@Param("patientId") int patientId, @Param("orderNo") String orderNo, @Param("patientName") String patientName, 
			@Param("phyname") String phyname, @Param("status") String status, @Param("rxNo") String rxNo, Pageable page);
	
	@Query(value = "Select m from OrderMaster m, OrderTransaction t, PhysicianAccount p, PatientAccount pat "
			+ "where m.id=t.orderId and m.patientId=pat.id and m.physicianId=p.id and m.patientId=:patientId and "
			+ "m.orderNumber like %:orderNo% and pat.patientName like %:patientName% and p.physicianName like %:phyname% and "
			+ "t.rxStatus like %:status% and m.orderDate >=:fromDate and t.rxNumber like %:rxNo% GROUP BY m.id  order by m.id desc ")
	Page<OrderMaster> findPatientWithoutFromDate(@Param("patientId") int patientId, @Param("orderNo") String orderNo, @Param("patientName") String patientName, 
			@Param("phyname") String phyname, @Param("status") String status, @Param("fromDate") Date fromDate, @Param("rxNo") String rxNo, Pageable page);

	@Query(value = "Select m from OrderMaster m, OrderTransaction t, PhysicianAccount p, PatientAccount pat "
			+ "where m.id=t.orderId and m.patientId=pat.id and m.physicianId=p.id and m.patientId=:patientId and "
			+ "m.orderNumber like %:orderNo% and pat.patientName like %:patientName% and p.physicianName like %:phyname% and "
			+ "t.rxStatus like %:status% and m.orderDate <=:toDate and t.rxNumber like %:rxNo% GROUP BY m.id  order by m.id desc ")
	Page<OrderMaster> findPatientWithoutToDate(@Param("patientId") int patientId, @Param("orderNo") String orderNo, @Param("patientName") String patientName, 
			@Param("phyname") String phyname, @Param("status") String status, @Param("toDate") Date toDate, @Param("rxNo") String rxNo, Pageable page);
	
	@Query(value = "Select m from OrderMaster m, OrderTransaction t, PhysicianAccount p, PatientAccount pat "
			+ "where m.id=t.orderId and m.patientId=pat.id and m.physicianId=p.id and m.patientId=:patientId and "
			+ "m.orderNumber like %:orderNo% and pat.patientName like %:patientName% and p.physicianName like %:phyname% and "
			+ "t.rxStatus like %:status% and m.orderDate >=:fromDate and m.orderDate <=:toDate and t.rxNumber like %:rxNo% GROUP BY m.id  order by m.id desc ")
	Page<OrderMaster> findPatientWithDate(@Param("patientId") int patientId, @Param("orderNo") String orderNo, @Param("patientName") String patientName, 
			@Param("phyname") String phyname, @Param("status") String status, @Param("fromDate") Date fromDate, @Param("toDate") Date toDate, @Param("rxNo") String rxNo, Pageable page);

	
	
	@Query("Select  COALESCE(count(m),0)  from OrderMaster m where m.physicianId=:physicianId ")
	int getFilterRecordCountByPhysicianId(@Param("physicianId") int physicianId);

	@Query("Select m from OrderMaster m order by m.id desc")
	Page<OrderMaster> getAllRecords(Pageable page);

	@Query("Select m from OrderMaster m where m.patientId=:patientId  order by m.id desc")
	Page<OrderMaster> getFilterRecordsByPatientId(@Param("patientId") int patientId, Pageable page);

	@Query("Select m from OrderMaster m where m.physicianId=:physicianId  order by m.id desc")
	Page<OrderMaster> getFilterRecordsByPhysicianId(@Param("physicianId") int physicianId, Pageable page);

	
	
	// Physician login
	@Query(value = "Select  COALESCE(count(m),0)  from OrderMaster m, OrderTransaction t, PhysicianAccount p, PatientAccount pat "
			+ "where m.id=t.orderId and m.patientId=pat.id and m.physicianId=p.id and m.physicianId=:physicianId and "
			+ "m.orderNumber like %:orderNo% and pat.patientName like %:patientName% and p.physicianName like %:phyname% and "
			+ "t.rxStatus like %:status% and t.rxNumber like %:rxNo% GROUP BY m.id ")
	int countPhysicianWithoutDate(@Param("physicianId") int physicianId, @Param("orderNo") String orderNo, @Param("patientName") String patientName, 
			@Param("phyname") String phyname, @Param("status") String status, @Param("rxNo") String rxNo);
	
	@Query(value = "Select  COALESCE(count(m),0)  from OrderMaster m, OrderTransaction t, PhysicianAccount p, PatientAccount pat "
			+ "where m.id=t.orderId and m.patientId=pat.id and m.physicianId=p.id and m.physicianId=:physicianId and "
			+ "m.orderNumber like %:orderNo% and pat.patientName like %:patientName% and p.physicianName like %:phyname% and "
			+ "t.rxStatus like %:status% and m.orderDate >=:fromDate and t.rxNumber like %:rxNo% GROUP BY m.id ")
	int countPhysicianWithoutFromDate(@Param("physicianId") int physicianId, @Param("orderNo") String orderNo, @Param("patientName") String patientName, 
			@Param("phyname") String phyname, @Param("status") String status, @Param("fromDate") Date fromDate, @Param("rxNo") String rxNo);

	@Query(value = "Select  COALESCE(count(m),0)  from OrderMaster m, OrderTransaction t, PhysicianAccount p, PatientAccount pat "
			+ "where m.id=t.orderId and m.patientId=pat.id and m.physicianId=p.id and m.physicianId=:physicianId and "
			+ "m.orderNumber like %:orderNo% and pat.patientName like %:patientName% and p.physicianName like %:phyname% and "
			+ "t.rxStatus like %:status% and m.orderDate <=:toDate and t.rxNumber like %:rxNo% GROUP BY m.id ")
	int countPhysicianWithoutToDate(@Param("physicianId") int physicianId, @Param("orderNo") String orderNo, @Param("patientName") String patientName, 
			@Param("phyname") String phyname, @Param("status") String status, @Param("toDate") Date toDate, @Param("rxNo") String rxNo);
	
	@Query(value = "Select  COALESCE(count(m),0)  from OrderMaster m, OrderTransaction t, PhysicianAccount p, PatientAccount pat "
			+ "where m.id=t.orderId and m.patientId=pat.id and m.physicianId=p.id and m.physicianId=:physicianId and "
			+ "m.orderNumber like %:orderNo% and pat.patientName like %:patientName% and p.physicianName like %:phyname% and "
			+ "t.rxStatus like %:status% and m.orderDate >=:fromDate and m.orderDate <=:toDate and t.rxNumber like %:rxNo% GROUP BY m.id ")
	int countPhysicianWithDate(@Param("physicianId") int physicianId, @Param("orderNo") String orderNo, @Param("patientName") String patientName, 
			@Param("phyname") String phyname, @Param("status") String status, @Param("fromDate") Date fromDate, @Param("toDate") Date toDate, @Param("rxNo") String rxNo);

	
	
	@Query(value = "Select m from OrderMaster m, OrderTransaction t, PhysicianAccount p, PatientAccount pat "
			+ "where m.id=t.orderId and m.patientId=pat.id and m.physicianId=p.id and m.physicianId=:physicianId and "
			+ "m.orderNumber like %:orderNo% and pat.patientName like %:patientName% and p.physicianName like %:phyname% and "
			+ "t.rxStatus like %:status% and t.rxNumber like %:rxNo% GROUP BY m.id  order by m.id desc ")
	Page<OrderMaster> findPhysicianWithoutDate(@Param("physicianId") int physicianId, @Param("orderNo") String orderNo, @Param("patientName") String patientName, 
			@Param("phyname") String phyname, @Param("status") String status, @Param("rxNo") String rxNo, Pageable page);
	
	@Query(value = "Select m from OrderMaster m, OrderTransaction t, PhysicianAccount p, PatientAccount pat "
			+ "where m.id=t.orderId and m.patientId=pat.id and m.physicianId=p.id and m.physicianId=:physicianId and "
			+ "m.orderNumber like %:orderNo% and pat.patientName like %:patientName% and p.physicianName like %:phyname% and "
			+ "t.rxStatus like %:status% and m.orderDate >=:fromDate and t.rxNumber like %:rxNo% GROUP BY m.id  order by m.id desc ")
	Page<OrderMaster> findPhysicianWithoutFromDate(@Param("physicianId") int physicianId, @Param("orderNo") String orderNo, @Param("patientName") String patientName, 
			@Param("phyname") String phyname, @Param("status") String status, @Param("fromDate") Date fromDate, @Param("rxNo") String rxNo, Pageable page);

	@Query(value = "Select m from OrderMaster m, OrderTransaction t, PhysicianAccount p, PatientAccount pat "
			+ "where m.id=t.orderId and m.patientId=pat.id and m.physicianId=p.id and m.physicianId=:physicianId and "
			+ "m.orderNumber like %:orderNo% and pat.patientName like %:patientName% and p.physicianName like %:phyname% and "
			+ "t.rxStatus like %:status% and m.orderDate <=:toDate and t.rxNumber like %:rxNo% GROUP BY m.id  order by m.id desc ")
	Page<OrderMaster> findPhysicianWithoutToDate(@Param("physicianId") int physicianId, @Param("orderNo") String orderNo, @Param("patientName") String patientName, 
			@Param("phyname") String phyname, @Param("status") String status, @Param("toDate") Date toDate, @Param("rxNo") String rxNo, Pageable page);
	
	@Query(value = "Select m from OrderMaster m, OrderTransaction t, PhysicianAccount p, PatientAccount pat "
			+ "where m.id=t.orderId and m.patientId=pat.id and m.physicianId=p.id and m.physicianId=:physicianId and "
			+ "m.orderNumber like %:orderNo% and pat.patientName like %:patientName% and p.physicianName like %:phyname% and "
			+ "t.rxStatus like %:status% and m.orderDate >=:fromDate and m.orderDate <=:toDate and t.rxNumber like %:rxNo% GROUP BY m.id  order by m.id desc ")
	Page<OrderMaster> findPhysicianWithDate(@Param("physicianId") int physicianId, @Param("orderNo") String orderNo, @Param("patientName") String patientName, 
			@Param("phyname") String phyname, @Param("status") String status, @Param("fromDate") Date fromDate, @Param("toDate") Date toDate, @Param("rxNo") String rxNo, Pageable page);

	
	
	// In Group Director Dashboard
	@Query(value = "Select  COALESCE(count(m),0)  from OrderMaster m where m.groupId=:groupId ")
	int countByGroupId(@Param("groupId") int groupId);
	
	@Query(value = "Select  COALESCE(count(m),0)  from OrderMaster m, PatientAccount p , OrderTransaction t "
			+ "where m.id=t.orderId and m.patientId=p.id and p.patientName like %:patientName% and m.groupId=:groupId and t.rxNumber like %:rxNo% ")
	int countByGroupId(@Param("patientName") String patientName, @Param("groupId") int groupId,  @Param("rxNo") String rxNo);

	@Query(value = "Select  COALESCE(sum(t.refillsFilled),0)  from OrderMaster m, OrderTransaction t "
			+ "where m.id=t.orderId and m.physicianId=:physicianId and m.lastUpdatedDate>:fromDate and m.lastUpdatedDate<=:toDate ")
	int fetchTotalRefillsCountByOrderDate( @Param("physicianId") int physicianId, @Param("fromDate") java.util.Date fromDate, 
			@Param("toDate") java.util.Date toDate);
	
	@Query(value = "Select  COALESCE(sum(t.refillsFilled),0)  from OrderMaster m, OrderTransaction t "
			+ "where m.id=t.orderId and m.physicianId=:physicianId and m.lastUpdatedDate>:fromDate and m.lastUpdatedDate<=:toDate ")
	int fetchTotalRefillsWeekCountByOrderDate( @Param("physicianId") int physicianId, @Param("fromDate") java.util.Date fromDate, 
			@Param("toDate") java.util.Date toDate);
	
	@Query(value = "Select  COALESCE(sum(t.refillsFilled),0)  from OrderMaster m, OrderTransaction t "
			+ "where m.id=t.orderId and m.physicianId=:physicianId and m.lastUpdatedDate>:fromDate and m.lastUpdatedDate<=:toDate ")
	int fetchTotalRefillsMonthCountByOrderDate( @Param("physicianId") int physicianId, @Param("fromDate") java.util.Date fromDate, 
			@Param("toDate") java.util.Date toDate);

	@Query(value = "Select  COALESCE(sum(t.refillsFilled),0)  from OrderMaster m, OrderTransaction t "
			+ "where m.id=t.orderId and m.groupId=:groupId and m.lastUpdatedDate>:fromDate and m.lastUpdatedDate<=:toDate ")
	int fetchGroupTotalRefillsCountByOrderDate( @Param("groupId") int groupId, @Param("fromDate") java.util.Date fromDate, 
			@Param("toDate") java.util.Date toDate);
	
	@Query(value = "Select  COALESCE(sum(t.refillsFilled),0)  from OrderMaster m, OrderTransaction t "
			+ "where m.id=t.orderId and m.groupId=:groupId and m.lastUpdatedDate>:fromDate and m.lastUpdatedDate<=:toDate ")
	int fetchGroupTotalRefillsWeekCountByOrderDate( @Param("groupId") int groupId, @Param("fromDate") java.util.Date fromDate, 
			@Param("toDate") java.util.Date toDate);
	
	@Query(value = "Select  COALESCE(sum(t.refillsFilled),0)  from OrderMaster m, OrderTransaction t "
			+ "where m.id=t.orderId and m.groupId=:groupId and m.lastUpdatedDate>:fromDate and m.lastUpdatedDate<=:toDate ")
	int fetchGroupTotalRefillsMonthCountByOrderDate( @Param("groupId") int groupId, @Param("fromDate") java.util.Date fromDate, 
			@Param("toDate") java.util.Date toDate);

	
	
	
	/****************  Group Director login **********************/
	@Query(value = "Select COALESCE(count(m),0) from OrderMaster m, OrderTransaction t, PhysicianAccount p, PatientAccount pat, PhysicianGroup gph, GroupMaster g  "
			+ "where m.id=t.orderId and m.patientId=pat.id and m.physicianId=p.id and gph.physicianId=p.id and g.id=gph.groupId and "
			+ "g.id=:groupId and m.orderNumber like %:orderNo% and pat.patientName like %:patientName% and p.physicianName like %:phyname% and "
			+ "t.rxStatus like %:status% and t.rxNumber like %:rxNo% GROUP BY m.id ")
	int countGroupWithoutDate(@Param("groupId") int groupId, @Param("orderNo") String orderNo, @Param("patientName") String patientName, 
			@Param("phyname") String phyname, @Param("status") String status, @Param("rxNo") String rxNo);

	@Query(value = "Select COALESCE(count(m),0) from OrderMaster m, OrderTransaction t, PhysicianAccount p, PatientAccount pat, PhysicianGroup gph, GroupMaster g  "
			+ "where m.id=t.orderId and m.patientId=pat.id and m.physicianId=p.id and gph.physicianId=p.id and g.id=gph.groupId and "
			+ "g.id=:groupId and m.orderNumber like %:orderNo% and pat.patientName like %:patientName% and p.physicianName like %:phyname% and "
			+ "t.rxStatus like %:status% and t.rxNumber like %:rxNo% and m.orderDate >=:fromDate GROUP BY m.id ")
	int countGroupWithoutFromDate(@Param("groupId") int groupId, @Param("orderNo") String orderNo, @Param("patientName") String patientName, 
			@Param("phyname") String phyname, @Param("status") String status, @Param("fromDate") java.util.Date fromDate, @Param("rxNo") String rxNo);
	
	
	@Query(value = "Select COALESCE(count(m),0) from OrderMaster m, OrderTransaction t, PhysicianAccount p, PatientAccount pat, PhysicianGroup gph, GroupMaster g  "
			+ "where m.id=t.orderId and m.patientId=pat.id and m.physicianId=p.id and gph.physicianId=p.id and g.id=gph.groupId and "
			+ "g.id=:groupId and m.orderNumber like %:orderNo% and pat.patientName like %:patientName% and p.physicianName like %:phyname% and "
			+ "t.rxStatus like %:status% and t.rxNumber like %:rxNo% and m.orderDate <=:toDate GROUP BY m.id ")
	int countGroupWithoutToDate(@Param("groupId") int groupId, @Param("orderNo") String orderNo, @Param("patientName") String patientName, 
			@Param("phyname") String phyname, @Param("status") String status, @Param("toDate") java.util.Date toDate, @Param("rxNo") String rxNo);
	
	
	@Query(value = "Select COALESCE(count(m),0) from OrderMaster m, OrderTransaction t, PhysicianAccount p, PatientAccount pat, PhysicianGroup gph, GroupMaster g  "
			+ "where m.id=t.orderId and m.patientId=pat.id and m.physicianId=p.id and gph.physicianId=p.id and g.id=gph.groupId and "
			+ "g.id=:groupId and m.orderNumber like %:orderNo% and pat.patientName like %:patientName% and p.physicianName like %:phyname% and "
			+ "t.rxStatus like %:status% and t.rxNumber like %:rxNo% and m.orderDate >=:fromDate and m.orderDate <=:toDate GROUP BY m.id ")
	int countGroupWithDate(@Param("groupId") int groupId, @Param("orderNo") String orderNo, @Param("patientName") String patientName, 
			@Param("phyname") String phyname, @Param("status") String status, @Param("fromDate") java.util.Date fromDate,
			@Param("toDate") java.util.Date toDate, @Param("rxNo") String rxNo);

	
	@Query(value = "Select m from OrderMaster m where m.groupId=:groupId order by m.id desc ")
	Page<OrderMaster> findByGroupId(@Param("groupId") int groupId, Pageable page);

	@Query(value = "Select m from OrderMaster m, OrderTransaction t, PhysicianAccount p, PatientAccount pat, PhysicianGroup gph, GroupMaster g  "
			+ "where m.id=t.orderId and m.patientId=pat.id and m.physicianId=p.id and gph.physicianId=p.id and g.id=gph.groupId and "
			+ "g.id=:groupId and m.orderNumber like %:orderNo% and pat.patientName like %:patientName% and p.physicianName like %:phyname% and "
			+ "t.rxStatus like %:status% and t.rxNumber like %:rxNo% and m.orderDate >=:fromDate GROUP BY m.id  order by m.id desc ")
	Page<OrderMaster> findGroupWithoutDate(@Param("groupId") int groupId, @Param("orderNo") String orderNo, @Param("patientName") String patientName, 
			@Param("phyname") String phyname, @Param("status") String status,  @Param("rxNo") String rxNo, Pageable page);
	
	@Query(value = "Select m from OrderMaster m, OrderTransaction t, PhysicianAccount p, PatientAccount pat, PhysicianGroup gph, GroupMaster g  "
			+ "where m.id=t.orderId and m.patientId=pat.id and m.physicianId=p.id and gph.physicianId=p.id and g.id=gph.groupId and "
			+ "g.id=:groupId and m.orderNumber like %:orderNo% and pat.patientName like %:patientName% and p.physicianName like %:phyname% and "
			+ "t.rxStatus like %:status% and t.rxNumber like %:rxNo% and m.orderDate <=:toDate GROUP BY m.id  order by m.id desc ")
	Page<OrderMaster> findGroupWithFromDate(@Param("groupId") int groupId, @Param("orderNo") String orderNo, @Param("patientName") String patientName, 
			@Param("phyname") String phyname, @Param("status") String status, @Param("toDate") java.util.Date toDate, @Param("rxNo") String rxNo, Pageable page);
	
	@Query(value = "Select m from OrderMaster m, OrderTransaction t, PhysicianAccount p, PatientAccount pat, PhysicianGroup gph, GroupMaster g  "
			+ "where m.id=t.orderId and m.patientId=pat.id and m.physicianId=p.id and gph.physicianId=p.id and g.id=gph.groupId and "
			+ "g.id=:groupId and m.orderNumber like %:orderNo% and pat.patientName like %:patientName% and p.physicianName like %:phyname% and "
			+ "t.rxStatus like %:status% and t.rxNumber like %:rxNo% and m.orderDate <=:toDate GROUP BY m.id  order by m.id desc ")
	Page<OrderMaster> findGroupWithToDate(@Param("groupId") int groupId, @Param("orderNo") String orderNo, @Param("patientName") String patientName, 
			@Param("phyname") String phyname, @Param("status") String status, @Param("toDate") java.util.Date toDate, @Param("rxNo") String rxNo, Pageable page);
	
	
	@Query(value = "Select m from OrderMaster m, OrderTransaction t, PhysicianAccount p, PatientAccount pat, PhysicianGroup gph, GroupMaster g  "
			+ "where m.id=t.orderId and m.patientId=pat.id and m.physicianId=p.id and gph.physicianId=p.id and g.id=gph.groupId and "
			+ "g.id=:groupId and m.orderNumber like %:orderNo% and pat.patientName like %:patientName% and p.physicianName like %:phyname% and "
			+ "t.rxStatus like %:status% and t.rxNumber like %:rxNo% and m.orderDate >=:fromDate and m.orderDate <=:toDate GROUP BY m.id  order by m.id desc ")
	Page<OrderMaster> findGroupWithDate(@Param("groupId") int groupId, @Param("orderNo") String orderNo, @Param("patientName") String patientName, 
			@Param("phyname") String phyname, @Param("status") String status, @Param("fromDate") java.util.Date fromDate,
			@Param("toDate") java.util.Date toDate, @Param("rxNo") String rxNo, Pageable page);

		
	
	
	
	
	
	/*******************  Order List Report Queries **********************/
	//  and m.orderDate >=:fromDate and m.orderDate <=:toDate  
	// @Param("fromDate") Date fromDate, @Param("toDate") Date toDate); 
	@Query(value = "Select m.orderNumber, m.orderDate, t.rxNumber, p.physicianName, pat.patientName, concat(t.dispensedName, ' ', t.dispensedDrug, ' ',"
			+ "t.dispensedQuantity, ' ', t.dispensedUnit) as medicationDispensed, t.refillsFilled, t.daysSupply, t.refillsRemaining, t.rxStatus, m.id  "
			+ "from OrderMaster m, OrderTransaction t, PhysicianAccount p, PatientAccount pat, PhysicianGroup gph, GroupMaster g "
			+ "where m.id=t.orderId and m.patientId=pat.id and m.physicianId=p.id and gph.physicianId=p.id and g.id=gph.groupId and "
			+ "p.physicianName like %:physician% and pat.patientName like %:patient% and t.rxStatus like %:status% and m.orderNumber like %:orderNo% and "
			+ "t.rxNumber like %:rxNo% and COALESCE(g.groupName, '') like %:group% GROUP BY m.id ")
	List<OrderMaster> reportCountByFilter(@Param("physician") String physician, @Param("patient") String patient, @Param("status") String status, 
			@Param("group") String group,  @Param("orderNo") String orderNo,  @Param("rxNo") String rxNumber);

	@Query(value = "Select m.orderNumber, m.orderDate, t.rxNumber, p.physicianName, pat.patientName, concat(t.dispensedName, ' ', t.dispensedDrug, ' ',"
			+ "t.dispensedQuantity, ' ', t.dispensedUnit) as medicationDispensed, t.refillsFilled, t.daysSupply, t.refillsRemaining, t.rxStatus, m.id  "
			+ "from OrderMaster m, OrderTransaction t, PhysicianAccount p, PatientAccount pat, PhysicianGroup gph, GroupMaster g "
			+ "where m.id=t.orderId and m.patientId=pat.id and m.physicianId=p.id and gph.physicianId=p.id and g.id=gph.groupId and "
			+ "p.physicianName like %:physician% and pat.patientName like %:patient% and t.rxStatus like %:status% and m.orderNumber like %:orderNo% and "
			+ "t.rxNumber like %:rxNo% and COALESCE(g.groupName, '') like %:group% and m.orderDate>=:fromDate GROUP BY m.id ")
	List<OrderMaster> reportCountByFilterWithFromDate(@Param("physician") String physician, @Param("patient") String patient, @Param("status") String status, 
			@Param("group") String group,  @Param("orderNo") String orderNo,  @Param("rxNo") String rxNumber, @Param("fromDate") Date fromDate);
	
	@Query(value = "Select m.orderNumber, m.orderDate, t.rxNumber, p.physicianName, pat.patientName, concat(t.dispensedName, ' ', t.dispensedDrug, ' ',"
			+ "t.dispensedQuantity, ' ', t.dispensedUnit) as medicationDispensed, t.refillsFilled, t.daysSupply, t.refillsRemaining, t.rxStatus, m.id  "
			+ "from OrderMaster m, OrderTransaction t, PhysicianAccount p, PatientAccount pat, PhysicianGroup gph, GroupMaster g "
			+ "where m.id=t.orderId and m.patientId=pat.id and m.physicianId=p.id and gph.physicianId=p.id and g.id=gph.groupId and "
			+ "p.physicianName like %:physician% and pat.patientName like %:patient% and t.rxStatus like %:status% and m.orderNumber like %:orderNo% and "
			+ "t.rxNumber like %:rxNo% and COALESCE(g.groupName, '') like %:group% and m.orderDate<=:toDate GROUP BY m.id ")
	List<OrderMaster> reportCountByFilterWithToDate(@Param("physician") String physician, @Param("patient") String patient, @Param("status") String status, 
			@Param("group") String group,  @Param("orderNo") String orderNo,  @Param("rxNo") String rxNumber, @Param("toDate") Date toDate);
	
	@Query(value = "Select m.orderNumber, m.orderDate, t.rxNumber, p.physicianName, pat.patientName, concat(t.dispensedName, ' ', t.dispensedDrug, ' ',"
			+ "t.dispensedQuantity, ' ', t.dispensedUnit) as medicationDispensed, t.refillsFilled, t.daysSupply, t.refillsRemaining, t.rxStatus, m.id  "
			+ "from OrderMaster m, OrderTransaction t, PhysicianAccount p, PatientAccount pat, PhysicianGroup gph, GroupMaster g "
			+ "where m.id=t.orderId and m.patientId=pat.id and m.physicianId=p.id and gph.physicianId=p.id and g.id=gph.groupId and "
			+ "p.physicianName like %:physician% and pat.patientName like %:patient% and t.rxStatus like %:status% and m.orderNumber like %:orderNo% and "
			+ "t.rxNumber like %:rxNo% and COALESCE(g.groupName, '') like %:group% and m.orderDate >=:fromDate and m.orderDate <=:toDate GROUP BY m.id ")
	List<OrderMaster> reportCountByFilterWithDate(@Param("physician") String physician, @Param("patient") String patient, @Param("status") String status, 
			@Param("group") String group,  @Param("orderNo") String orderNo,  @Param("rxNo") String rxNumber, 
			@Param("fromDate") Date fromDate, @Param("toDate") Date toDate);
	
	
	/*m.order_number, m.order_date, t.rx_number, p.physician_name, pat.patient_name,
	concat(t.dispensed_name, " ", t.dispensed_drug, " ", t.dispensed_quantity, " ", t.dispensed_unit) as medicationDispensed,
	t.refills_filled, t.days_supply, t.refills_remaining, t.rx_status */
	
	@Query(value = "Select m.orderNumber, m.orderDate, t.rxNumber, p.physicianName, pat.patientName, concat(t.dispensedName, ' ', t.dispensedDrug, ' ',"
			+ "t.dispensedQuantity, ' ', t.dispensedUnit) as medicationDispensed, t.refillsFilled, t.daysSupply, t.refillsRemaining, t.rxStatus, m.id "
			+ "from OrderMaster m, OrderTransaction t, PhysicianAccount p, PatientAccount pat, PhysicianGroup gph, GroupMaster g  "
			+ "where m.id=t.orderId and m.patientId=pat.id and m.physicianId=p.id and gph.physicianId=p.id and g.id=gph.groupId and "
			+ "p.physicianName like %:physician% and pat.patientName like %:patient% and t.rxStatus like %:status% and m.orderNumber like %:orderNo% and "
			+ "t.rxNumber like %:rxNo% and COALESCE(g.groupName, '') like %:group% GROUP BY m.id ")
	Page<Object[]> reportByFilter(@Param("physician") String physician, @Param("patient") String patient, @Param("status") String status, 
			@Param("group") String group,  @Param("orderNo") String orderNo,  @Param("rxNo") String rxNumber, Pageable page);

	
	@Query(value = "Select m.orderNumber, m.orderDate, t.rxNumber, p.physicianName, pat.patientName, concat(t.dispensedName, ' ', t.dispensedDrug, ' ',"
			+ "t.dispensedQuantity, ' ', t.dispensedUnit) as medicationDispensed, t.refillsFilled, t.daysSupply, t.refillsRemaining, t.rxStatus, m.id  "
			+ "from OrderMaster m, OrderTransaction t, PhysicianAccount p, PatientAccount pat, PhysicianGroup gph, GroupMaster g "
			+ "where m.id=t.orderId and m.patientId=pat.id and m.physicianId=p.id and gph.physicianId=p.id and g.id=gph.groupId and "
			+ "p.physicianName like %:physician% and pat.patientName like %:patient% and t.rxStatus like %:status% and m.orderNumber like %:orderNo% and "
			+ "t.rxNumber like %:rxNo% and COALESCE(g.groupName, '') like %:group% and m.orderDate>=:fromDate GROUP BY m.id ")
	Page<Object[]> reportByFilterWithFromDate(@Param("physician") String physician, @Param("patient") String patient, @Param("status") String status, 
			@Param("group") String group,  @Param("orderNo") String orderNo,  @Param("rxNo") String rxNumber, @Param("fromDate") Date fromDate, Pageable page);
	
	@Query(value = "Select m.orderNumber, m.orderDate, t.rxNumber, p.physicianName, pat.patientName, concat(t.dispensedName, ' ', t.dispensedDrug, ' ',"
			+ "t.dispensedQuantity, ' ', t.dispensedUnit) as medicationDispensed, t.refillsFilled, t.daysSupply, t.refillsRemaining, t.rxStatus, m.id  "
			+ "from OrderMaster m, OrderTransaction t, PhysicianAccount p, PatientAccount pat, PhysicianGroup gph, GroupMaster g "
			+ "where m.id=t.orderId and m.patientId=pat.id and m.physicianId=p.id and gph.physicianId=p.id and g.id=gph.groupId and "
			+ "p.physicianName like %:physician% and pat.patientName like %:patient% and t.rxStatus like %:status% and m.orderNumber like %:orderNo% and "
			+ "t.rxNumber like %:rxNo% and COALESCE(g.groupName, '') like %:group% and m.orderDate<=:toDate GROUP BY m.id ")
	Page<Object[]> reportByFilterWithToDate(@Param("physician") String physician, @Param("patient") String patient, @Param("status") String status, 
			@Param("group") String group,  @Param("orderNo") String orderNo,  @Param("rxNo") String rxNumber, @Param("toDate") Date toDate, Pageable page);
	
	@Query(value = "Select m.orderNumber, m.orderDate, t.rxNumber, p.physicianName, pat.patientName, concat(t.dispensedName, ' ', t.dispensedDrug, ' ',"
			+ "t.dispensedQuantity, ' ', t.dispensedUnit) as medicationDispensed, t.refillsFilled, t.daysSupply, t.refillsRemaining, t.rxStatus, m.id  "
			+ "from OrderMaster m, OrderTransaction t, PhysicianAccount p, PatientAccount pat, PhysicianGroup gph, GroupMaster g "
			+ "where m.id=t.orderId and m.patientId=pat.id and m.physicianId=p.id and gph.physicianId=p.id and g.id=gph.groupId and "
			+ "p.physicianName like %:physician% and pat.patientName like %:patient% and t.rxStatus like %:status% and m.orderNumber like %:orderNo% and "
			+ "t.rxNumber like %:rxNo% and COALESCE(g.groupName, '') like %:group% and m.orderDate >=:fromDate and m.orderDate <=:toDate GROUP BY m.id ")
	Page<Object[]> reportByFilterWithDate(@Param("physician") String physician, @Param("patient") String patient, @Param("status") String status, 
			@Param("group") String group,  @Param("orderNo") String orderNo,  @Param("rxNo") String rxNumber, 
			@Param("fromDate") Date fromDate, @Param("toDate") Date toDate, Pageable page);

	
	
	
	
	
	
	/********************* Physician Login Report  *********************************/
	@Query(value = "Select m.orderNumber, m.orderDate, t.rxNumber, p.physicianName, pat.patientName, concat(t.dispensedName, ' ', t.dispensedDrug, ' ',"
			+ "t.dispensedQuantity, ' ', t.dispensedUnit) as medicationDispensed, t.refillsFilled, t.daysSupply, t.refillsRemaining, t.rxStatus, m.id  "
			+ "from OrderMaster m, OrderTransaction t, PhysicianAccount p, PatientAccount pat, PhysicianGroup gph, GroupMaster g "
			+ "where m.id=t.orderId and m.patientId=pat.id and m.physicianId=p.id and gph.physicianId=p.id and g.id=gph.groupId and "
			+ "m.physicianId=:physicianId and p.physicianName like %:physician% and pat.patientName like %:patient% and t.rxStatus like %:status% and m.orderNumber like %:orderNo% and "
			+ "t.rxNumber like %:rxNo% and COALESCE(g.groupName, '') like %:group% GROUP BY m.id ")
	List<OrderMaster> physicianReportCountByFilter(@Param("physicianId") int physicianId, @Param("physician") String physician, @Param("patient") String patient, @Param("status") String status, 
			@Param("group") String group,  @Param("orderNo") String orderNo,  @Param("rxNo") String rxNumber);

	@Query(value = "Select m.orderNumber, m.orderDate, t.rxNumber, p.physicianName, pat.patientName, concat(t.dispensedName, ' ', t.dispensedDrug, ' ',"
			+ "t.dispensedQuantity, ' ', t.dispensedUnit) as medicationDispensed, t.refillsFilled, t.daysSupply, t.refillsRemaining, t.rxStatus, m.id  "
			+ "from OrderMaster m, OrderTransaction t, PhysicianAccount p, PatientAccount pat, PhysicianGroup gph, GroupMaster g "
			+ "where m.id=t.orderId and m.patientId=pat.id and m.physicianId=p.id and gph.physicianId=p.id and g.id=gph.groupId and "
			+ "m.physicianId=:physicianId and p.physicianName like %:physician% and pat.patientName like %:patient% and t.rxStatus like %:status% and m.orderNumber like %:orderNo% and "
			+ "t.rxNumber like %:rxNo% and COALESCE(g.groupName, '') like %:group% and m.orderDate>=:fromDate GROUP BY m.id ")
	List<OrderMaster> physicianReportCountByFilterWithFromDate(@Param("physicianId") int physicianId, @Param("physician") String physician, @Param("patient") String patient, @Param("status") String status, 
			@Param("group") String group,  @Param("orderNo") String orderNo,  @Param("rxNo") String rxNumber, @Param("fromDate") Date fromDate);
	
	@Query(value = "Select m.orderNumber, m.orderDate, t.rxNumber, p.physicianName, pat.patientName, concat(t.dispensedName, ' ', t.dispensedDrug, ' ',"
			+ "t.dispensedQuantity, ' ', t.dispensedUnit) as medicationDispensed, t.refillsFilled, t.daysSupply, t.refillsRemaining, t.rxStatus, m.id  "
			+ "from OrderMaster m, OrderTransaction t, PhysicianAccount p, PatientAccount pat, PhysicianGroup gph, GroupMaster g "
			+ "where m.id=t.orderId and m.patientId=pat.id and m.physicianId=p.id and gph.physicianId=p.id and g.id=gph.groupId and "
			+ "m.physicianId=:physicianId and p.physicianName like %:physician% and pat.patientName like %:patient% and t.rxStatus like %:status% and m.orderNumber like %:orderNo% and "
			+ "t.rxNumber like %:rxNo% and COALESCE(g.groupName, '') like %:group% and m.orderDate<=:toDate GROUP BY m.id ")
	List<OrderMaster> physicianReportCountByFilterWithToDate(@Param("physicianId") int physicianId, @Param("physician") String physician, @Param("patient") String patient, @Param("status") String status, 
			@Param("group") String group,  @Param("orderNo") String orderNo,  @Param("rxNo") String rxNumber, @Param("toDate") Date toDate);
	
	@Query(value = "Select m.orderNumber, m.orderDate, t.rxNumber, p.physicianName, pat.patientName, concat(t.dispensedName, ' ', t.dispensedDrug, ' ',"
			+ "t.dispensedQuantity, ' ', t.dispensedUnit) as medicationDispensed, t.refillsFilled, t.daysSupply, t.refillsRemaining, t.rxStatus, m.id  "
			+ "from OrderMaster m, OrderTransaction t, PhysicianAccount p, PatientAccount pat, PhysicianGroup gph, GroupMaster g "
			+ "where m.id=t.orderId and m.patientId=pat.id and m.physicianId=p.id and gph.physicianId=p.id and g.id=gph.groupId and "
			+ "m.physicianId=:physicianId and p.physicianName like %:physician% and pat.patientName like %:patient% and t.rxStatus like %:status% and m.orderNumber like %:orderNo% and "
			+ "t.rxNumber like %:rxNo% and COALESCE(g.groupName, '') like %:group% and m.orderDate >=:fromDate and m.orderDate <=:toDate GROUP BY m.id ")
	List<OrderMaster> physicianReportCountByFilterWithDate(@Param("physicianId") int physicianId, @Param("physician") String physician, @Param("patient") String patient, @Param("status") String status, 
			@Param("group") String group,  @Param("orderNo") String orderNo,  @Param("rxNo") String rxNumber, 
			@Param("fromDate") Date fromDate, @Param("toDate") Date toDate);

	
	
	
	@Query(value = "Select m.orderNumber, m.orderDate, t.rxNumber, p.physicianName, pat.patientName, concat(t.dispensedName, ' ', t.dispensedDrug, ' ',"
			+ "t.dispensedQuantity, ' ', t.dispensedUnit) as medicationDispensed, t.refillsFilled, t.daysSupply, t.refillsRemaining, t.rxStatus, m.id  "
			+ "from OrderMaster m, OrderTransaction t, PhysicianAccount p, PatientAccount pat, PhysicianGroup gph, GroupMaster g "
			+ "where m.id=t.orderId and m.patientId=pat.id and m.physicianId=p.id and gph.physicianId=p.id and g.id=gph.groupId and "
			+ "m.physicianId=:physicianId and p.physicianName like %:physician% and pat.patientName like %:patient% and t.rxStatus like %:status% and m.orderNumber like %:orderNo% and "
			+ "t.rxNumber like %:rxNo% and COALESCE(g.groupName, '') like %:group% GROUP BY m.id ")
	Page<Object[]>  physicianReportCountByFilter(@Param("physicianId") int physicianId, @Param("physician") String physician, @Param("patient") String patient, @Param("status") String status, 
			@Param("group") String group,  @Param("orderNo") String orderNo,  @Param("rxNo") String rxNumber, Pageable page);

	@Query(value = "Select  m.orderNumber, m.orderDate, t.rxNumber, p.physicianName, pat.patientName, concat(t.dispensedName, ' ', t.dispensedDrug, ' ',"
			+ "t.dispensedQuantity, ' ', t.dispensedUnit) as medicationDispensed, t.refillsFilled, t.daysSupply, t.refillsRemaining, t.rxStatus, m.id  "
			+ "from OrderMaster m, OrderTransaction t, PhysicianAccount p, PatientAccount pat, PhysicianGroup gph, GroupMaster g "
			+ "where m.id=t.orderId and m.patientId=pat.id and m.physicianId=p.id and gph.physicianId=p.id and g.id=gph.groupId and "
			+ "m.physicianId=:physicianId and p.physicianName like %:physician% and pat.patientName like %:patient% and t.rxStatus like %:status% and m.orderNumber like %:orderNo% and "
			+ "t.rxNumber like %:rxNo% and COALESCE(g.groupName, '') like %:group% and m.orderDate>=:fromDate GROUP BY m.id ")
	Page<Object[]>  physicianReportCountByFilterWithFromDate(@Param("physicianId") int physicianId, @Param("physician") String physician, @Param("patient") String patient, @Param("status") String status, 
			@Param("group") String group,  @Param("orderNo") String orderNo,  @Param("rxNo") String rxNumber, @Param("fromDate") Date fromDate, Pageable page);
	
	@Query(value = "Select  m.orderNumber, m.orderDate, t.rxNumber, p.physicianName, pat.patientName, concat(t.dispensedName, ' ', t.dispensedDrug, ' ',"
			+ "t.dispensedQuantity, ' ', t.dispensedUnit) as medicationDispensed, t.refillsFilled, t.daysSupply, t.refillsRemaining, t.rxStatus, m.id  "
			+ "from OrderMaster m, OrderTransaction t, PhysicianAccount p, PatientAccount pat, PhysicianGroup gph, GroupMaster g "
			+ "where m.id=t.orderId and m.patientId=pat.id and m.physicianId=p.id and gph.physicianId=p.id and g.id=gph.groupId and "
			+ "m.physicianId=:physicianId and p.physicianName like %:physician% and pat.patientName like %:patient% and t.rxStatus like %:status% and m.orderNumber like %:orderNo% and "
			+ "t.rxNumber like %:rxNo% and COALESCE(g.groupName, '') like %:group% and m.orderDate<=:toDate GROUP BY m.id ")
	Page<Object[]>  physicianReportCountByFilterWithToDate(@Param("physicianId") int physicianId, @Param("physician") String physician, @Param("patient") String patient, @Param("status") String status, 
			@Param("group") String group,  @Param("orderNo") String orderNo,  @Param("rxNo") String rxNumber, @Param("toDate") Date toDate, Pageable page);
	
	@Query(value = "Select m.orderNumber, m.orderDate, t.rxNumber, p.physicianName, pat.patientName, concat(t.dispensedName, ' ', t.dispensedDrug, ' ',"
			+ "t.dispensedQuantity, ' ', t.dispensedUnit) as medicationDispensed, t.refillsFilled, t.daysSupply, t.refillsRemaining, t.rxStatus, m.id  "
			+ "from OrderMaster m, OrderTransaction t, PhysicianAccount p, PatientAccount pat, PhysicianGroup gph, GroupMaster g "
			+ "where m.id=t.orderId and m.patientId=pat.id and m.physicianId=p.id and gph.physicianId=p.id and g.id=gph.groupId and "
			+ "m.physicianId=:physicianId and p.physicianName like %:physician% and pat.patientName like %:patient% and t.rxStatus like %:status% and m.orderNumber like %:orderNo% and "
			+ "t.rxNumber like %:rxNo% and COALESCE(g.groupName, '') like %:group% and m.orderDate >=:fromDate and m.orderDate <=:toDate GROUP BY m.id ")
	Page<Object[]>  physicianReportCountByFilterWithDate(@Param("physicianId") int physicianId, @Param("physician") String physician, @Param("patient") String patient, @Param("status") String status, 
			@Param("group") String group,  @Param("orderNo") String orderNo,  @Param("rxNo") String rxNumber, 
			@Param("fromDate") Date fromDate, @Param("toDate") Date toDate, Pageable page);
	
	
	
	/********************* Patient Login Report  *********************************/
	@Query(value = "Select m.orderNumber, m.orderDate, t.rxNumber, p.physicianName, pat.patientName, concat(t.dispensedName, ' ', t.dispensedDrug, ' ',"
			+ "t.dispensedQuantity, ' ', t.dispensedUnit) as medicationDispensed, t.refillsFilled, t.daysSupply, t.refillsRemaining, t.rxStatus, m.id  "
			+ "from OrderMaster m, OrderTransaction t, PhysicianAccount p, PatientAccount pat, PhysicianGroup gph, GroupMaster g "
			+ "where m.id=t.orderId and m.patientId=pat.id and m.physicianId=p.id and gph.physicianId=p.id and g.id=gph.groupId and "
			+ "m.patientId=:patientId and p.physicianName like %:physician% and pat.patientName like %:patient% and t.rxStatus like %:status% and m.orderNumber like %:orderNo% and "
			+ "t.rxNumber like %:rxNo% and COALESCE(g.groupName, '') like %:group% GROUP BY m.id ")
	List<OrderMaster> patientReportCountByFilter(@Param("patientId") int patientId, @Param("physician") String physician, @Param("patient") String patient, @Param("status") String status, 
			@Param("group") String group,  @Param("orderNo") String orderNo,  @Param("rxNo") String rxNumber);

	@Query(value = "Select m.orderNumber, m.orderDate, t.rxNumber, p.physicianName, pat.patientName, concat(t.dispensedName, ' ', t.dispensedDrug, ' ',"
			+ "t.dispensedQuantity, ' ', t.dispensedUnit) as medicationDispensed, t.refillsFilled, t.daysSupply, t.refillsRemaining, t.rxStatus, m.id  "
			+ "from OrderMaster m, OrderTransaction t, PhysicianAccount p, PatientAccount pat, PhysicianGroup gph, GroupMaster g "
			+ "where m.id=t.orderId and m.patientId=pat.id and m.physicianId=p.id and gph.physicianId=p.id and g.id=gph.groupId and "
			+ "m.patientId=:patientId and p.physicianName like %:physician% and pat.patientName like %:patient% and t.rxStatus like %:status% and m.orderNumber like %:orderNo% and "
			+ "t.rxNumber like %:rxNo% and COALESCE(g.groupName, '') like %:group% and m.orderDate>=:fromDate GROUP BY m.id ")
	List<OrderMaster> patientReportCountByFilterWithFromDate(@Param("patientId") int patientId, @Param("physician") String physician, @Param("patient") String patient, @Param("status") String status, 
			@Param("group") String group,  @Param("orderNo") String orderNo,  @Param("rxNo") String rxNumber, @Param("fromDate") Date fromDate);
	
	@Query(value = "Select m.orderNumber, m.orderDate, t.rxNumber, p.physicianName, pat.patientName, concat(t.dispensedName, ' ', t.dispensedDrug, ' ',"
			+ "t.dispensedQuantity, ' ', t.dispensedUnit) as medicationDispensed, t.refillsFilled, t.daysSupply, t.refillsRemaining, t.rxStatus, m.id  "
			+ "from OrderMaster m, OrderTransaction t, PhysicianAccount p, PatientAccount pat, PhysicianGroup gph, GroupMaster g "
			+ "where m.id=t.orderId and m.patientId=pat.id and m.physicianId=p.id and gph.physicianId=p.id and g.id=gph.groupId and "
			+ "m.patientId=:patientId and p.physicianName like %:physician% and pat.patientName like %:patient% and t.rxStatus like %:status% and m.orderNumber like %:orderNo% and "
			+ "t.rxNumber like %:rxNo% and COALESCE(g.groupName, '') like %:group% and m.orderDate<=:toDate GROUP BY m.id ")
	List<OrderMaster> patientReportCountByFilterWithToDate(@Param("patientId") int patientId, @Param("physician") String physician, @Param("patient") String patient, @Param("status") String status, 
			@Param("group") String group,  @Param("orderNo") String orderNo,  @Param("rxNo") String rxNumber, @Param("toDate") Date toDate);
	
	@Query(value = "Select m.orderNumber, m.orderDate, t.rxNumber, p.physicianName, pat.patientName, concat(t.dispensedName, ' ', t.dispensedDrug, ' ',"
			+ "t.dispensedQuantity, ' ', t.dispensedUnit) as medicationDispensed, t.refillsFilled, t.daysSupply, t.refillsRemaining, t.rxStatus, m.id  "
			+ "from OrderMaster m, OrderTransaction t, PhysicianAccount p, PatientAccount pat, PhysicianGroup gph, GroupMaster g "
			+ "where m.id=t.orderId and m.patientId=pat.id and m.physicianId=p.id and gph.physicianId=p.id and g.id=gph.groupId and "
			+ "m.patientId=:patientId and p.physicianName like %:physician% and pat.patientName like %:patient% and t.rxStatus like %:status% and m.orderNumber like %:orderNo% and "
			+ "t.rxNumber like %:rxNo% and COALESCE(g.groupName, '') like %:group% and m.orderDate >=:fromDate and m.orderDate <=:toDate GROUP BY m.id ")
	List<OrderMaster> patientReportCountByFilterWithDate(@Param("patientId") int patientId, @Param("physician") String physician, @Param("patient") String patient, @Param("status") String status, 
			@Param("group") String group,  @Param("orderNo") String orderNo,  @Param("rxNo") String rxNumber, 
			@Param("fromDate") Date fromDate, @Param("toDate") Date toDate);

	
	
	
	@Query(value = "Select m.orderNumber, m.orderDate, t.rxNumber, p.physicianName, pat.patientName, concat(t.dispensedName, ' ', t.dispensedDrug, ' ',"
			+ "t.dispensedQuantity, ' ', t.dispensedUnit) as medicationDispensed, t.refillsFilled, t.daysSupply, t.refillsRemaining, t.rxStatus, m.id  "
			+ "from OrderMaster m, OrderTransaction t, PhysicianAccount p, PatientAccount pat, PhysicianGroup gph, GroupMaster g "
			+ "where m.id=t.orderId and m.patientId=pat.id and m.physicianId=p.id and gph.physicianId=p.id and g.id=gph.groupId and "
			+ "m.patientId=:patientId and p.physicianName like %:physician% and pat.patientName like %:patient% and t.rxStatus like %:status% and m.orderNumber like %:orderNo% and "
			+ "t.rxNumber like %:rxNo% and COALESCE(g.groupName, '') like %:group% GROUP BY m.id ")
	Page<Object[]>  patientReportByFilter(@Param("patientId") int patientId, @Param("physician") String physician, @Param("patient") String patient, @Param("status") String status, 
			@Param("group") String group,  @Param("orderNo") String orderNo,  @Param("rxNo") String rxNumber, Pageable page);

	@Query(value = "Select m.orderNumber, m.orderDate, t.rxNumber, p.physicianName, pat.patientName, concat(t.dispensedName, ' ', t.dispensedDrug, ' ',"
			+ "t.dispensedQuantity, ' ', t.dispensedUnit) as medicationDispensed, t.refillsFilled, t.daysSupply, t.refillsRemaining, t.rxStatus, m.id  "
			+ "from OrderMaster m, OrderTransaction t, PhysicianAccount p, PatientAccount pat, PhysicianGroup gph, GroupMaster g "
			+ "where m.id=t.orderId and m.patientId=pat.id and m.physicianId=p.id and gph.physicianId=p.id and g.id=gph.groupId and "
			+ "m.patientId=:patientId and p.physicianName like %:physician% and pat.patientName like %:patient% and t.rxStatus like %:status% and m.orderNumber like %:orderNo% and "
			+ "t.rxNumber like %:rxNo% and COALESCE(g.groupName, '') like %:group% and m.orderDate>=:fromDate GROUP BY m.id ")
	Page<Object[]>  patientReportByFilterWithFromDate(@Param("patientId") int patientId, @Param("physician") String physician, @Param("patient") String patient, @Param("status") String status, 
			@Param("group") String group,  @Param("orderNo") String orderNo,  @Param("rxNo") String rxNumber, @Param("fromDate") Date fromDate, Pageable page);
	
	@Query(value = "Select m.orderNumber, m.orderDate, t.rxNumber, p.physicianName, pat.patientName, concat(t.dispensedName, ' ', t.dispensedDrug, ' ',"
			+ "t.dispensedQuantity, ' ', t.dispensedUnit) as medicationDispensed, t.refillsFilled, t.daysSupply, t.refillsRemaining, t.rxStatus, m.id  "
			+ "from OrderMaster m, OrderTransaction t, PhysicianAccount p, PatientAccount pat, PhysicianGroup gph, GroupMaster g "
			+ "where m.id=t.orderId and m.patientId=pat.id and m.physicianId=p.id and gph.physicianId=p.id and g.id=gph.groupId and "
			+ "m.patientId=:patientId and p.physicianName like %:physician% and pat.patientName like %:patient% and t.rxStatus like %:status% and m.orderNumber like %:orderNo% and "
			+ "t.rxNumber like %:rxNo% and COALESCE(g.groupName, '') like %:group% and m.orderDate<=:toDate GROUP BY m.id ")
	Page<Object[]>  patientReportByFilterWithToDate(@Param("patientId") int patientId, @Param("physician") String physician, @Param("patient") String patient, @Param("status") String status, 
			@Param("group") String group,  @Param("orderNo") String orderNo,  @Param("rxNo") String rxNumber, @Param("toDate") Date toDate, Pageable page);
	
	@Query(value = "Select m.orderNumber, m.orderDate, t.rxNumber, p.physicianName, pat.patientName, concat(t.dispensedName, ' ', t.dispensedDrug, ' ',"
			+ "t.dispensedQuantity, ' ', t.dispensedUnit) as medicationDispensed, t.refillsFilled, t.daysSupply, t.refillsRemaining, t.rxStatus, m.id  "
			+ "from OrderMaster m, OrderTransaction t, PhysicianAccount p, PatientAccount pat, PhysicianGroup gph, GroupMaster g "
			+ "where m.id=t.orderId and m.patientId=pat.id and m.physicianId=p.id and gph.physicianId=p.id and g.id=gph.groupId and "
			+ "m.patientId=:patientId and p.physicianName like %:physician% and pat.patientName like %:patient% and t.rxStatus like %:status% and m.orderNumber like %:orderNo% and "
			+ "t.rxNumber like %:rxNo% and COALESCE(g.groupName, '') like %:group% and m.orderDate >=:fromDate and m.orderDate <=:toDate GROUP BY m.id ")
	Page<Object[]>  patientReportByFilterWithDate(@Param("patientId") int patientId, @Param("physician") String physician, @Param("patient") String patient, @Param("status") String status, 
			@Param("group") String group,  @Param("orderNo") String orderNo,  @Param("rxNo") String rxNumber, 
			@Param("fromDate") Date fromDate, @Param("toDate") Date toDate, Pageable page);
	
	
	
	/********************* Group Login Report  *********************************/
	@Query(value = "Select m.orderNumber, m.orderDate, t.rxNumber, p.physicianName, pat.patientName, concat(t.dispensedName, ' ', t.dispensedDrug, ' ',"
			+ "t.dispensedQuantity, ' ', t.dispensedUnit) as medicationDispensed, t.refillsFilled, t.daysSupply, t.refillsRemaining, t.rxStatus, m.id  "
			+ "from OrderMaster m, OrderTransaction t, PhysicianAccount p, PatientAccount pat, PhysicianGroup gph, GroupMaster g "
			+ "where m.id=t.orderId and m.patientId=pat.id and m.physicianId=p.id and gph.physicianId=p.id and g.id=gph.groupId and "
			+ "g.id=:groupId and p.physicianName like %:physician% and pat.patientName like %:patient% and t.rxStatus like %:status% and m.orderNumber like %:orderNo% and "
			+ "t.rxNumber like %:rxNo% and COALESCE(g.groupName, '') like %:group% GROUP BY m.id ")
	List<OrderMaster> groupReportCountByFilter(@Param("groupId") int groupId, @Param("physician") String physician, @Param("patient") String patient, @Param("status") String status, 
			@Param("group") String group,  @Param("orderNo") String orderNo,  @Param("rxNo") String rxNumber);

	@Query(value = "Select m.orderNumber, m.orderDate, t.rxNumber, p.physicianName, pat.patientName, concat(t.dispensedName, ' ', t.dispensedDrug, ' ',"
			+ "t.dispensedQuantity, ' ', t.dispensedUnit) as medicationDispensed, t.refillsFilled, t.daysSupply, t.refillsRemaining, t.rxStatus, m.id  "
			+ "from OrderMaster m, OrderTransaction t, PhysicianAccount p, PatientAccount pat, PhysicianGroup gph, GroupMaster g "
			+ "where m.id=t.orderId and m.patientId=pat.id and m.physicianId=p.id and gph.physicianId=p.id and g.id=gph.groupId and "
			+ "g.id=:groupId and p.physicianName like %:physician% and pat.patientName like %:patient% and t.rxStatus like %:status% and m.orderNumber like %:orderNo% and "
			+ "t.rxNumber like %:rxNo% and COALESCE(g.groupName, '') like %:group% and m.orderDate>=:fromDate GROUP BY m.id ")
	List<OrderMaster> groupReportCountByFilterWithFromDate(@Param("groupId") int groupId, @Param("physician") String physician, @Param("patient") String patient, @Param("status") String status, 
			@Param("group") String group,  @Param("orderNo") String orderNo,  @Param("rxNo") String rxNumber, @Param("fromDate") Date fromDate);
	
	@Query(value = "Select m.orderNumber, m.orderDate, t.rxNumber, p.physicianName, pat.patientName, concat(t.dispensedName, ' ', t.dispensedDrug, ' ',"
			+ "t.dispensedQuantity, ' ', t.dispensedUnit) as medicationDispensed, t.refillsFilled, t.daysSupply, t.refillsRemaining, t.rxStatus, m.id  "
			+ "from OrderMaster m, OrderTransaction t, PhysicianAccount p, PatientAccount pat, PhysicianGroup gph, GroupMaster g "
			+ "where m.id=t.orderId and m.patientId=pat.id and m.physicianId=p.id and gph.physicianId=p.id and g.id=gph.groupId and "
			+ "g.id=:groupId and p.physicianName like %:physician% and pat.patientName like %:patient% and t.rxStatus like %:status% and m.orderNumber like %:orderNo% and "
			+ "t.rxNumber like %:rxNo% and COALESCE(g.groupName, '') like %:group% and m.orderDate<=:toDate GROUP BY m.id ")
	List<OrderMaster> groupReportCountByFilterWithToDate(@Param("groupId") int groupId, @Param("physician") String physician, @Param("patient") String patient, @Param("status") String status, 
			@Param("group") String group,  @Param("orderNo") String orderNo,  @Param("rxNo") String rxNumber, @Param("toDate") Date toDate);
	
	@Query(value = "Select m.orderNumber, m.orderDate, t.rxNumber, p.physicianName, pat.patientName, concat(t.dispensedName, ' ', t.dispensedDrug, ' ',"
			+ "t.dispensedQuantity, ' ', t.dispensedUnit) as medicationDispensed, t.refillsFilled, t.daysSupply, t.refillsRemaining, t.rxStatus, m.id  "
			+ "from OrderMaster m, OrderTransaction t, PhysicianAccount p, PatientAccount pat, PhysicianGroup gph, GroupMaster g "
			+ "where m.id=t.orderId and m.patientId=pat.id and m.physicianId=p.id and gph.physicianId=p.id and g.id=gph.groupId and "
			+ "g.id=:groupId and p.physicianName like %:physician% and pat.patientName like %:patient% and t.rxStatus like %:status% and m.orderNumber like %:orderNo% and "
			+ "t.rxNumber like %:rxNo% and COALESCE(g.groupName, '') like %:group% and m.orderDate >=:fromDate and m.orderDate <=:toDate GROUP BY m.id ")
	List<OrderMaster> groupReportCountByFilterWithDate(@Param("groupId") int groupId, @Param("physician") String physician, @Param("patient") String patient, @Param("status") String status, 
			@Param("group") String group,  @Param("orderNo") String orderNo,  @Param("rxNo") String rxNumber, 
			@Param("fromDate") Date fromDate, @Param("toDate") Date toDate);


	
	
	@Query(value = "Select m.orderNumber, m.orderDate, t.rxNumber, p.physicianName, pat.patientName, concat(t.dispensedName, ' ', t.dispensedDrug, ' ',"
			+ "t.dispensedQuantity, ' ', t.dispensedUnit) as medicationDispensed, t.refillsFilled, t.daysSupply, t.refillsRemaining, t.rxStatus, m.id  "
			+ "from OrderMaster m, OrderTransaction t, PhysicianAccount p, PatientAccount pat, PhysicianGroup gph, GroupMaster g "
			+ "where m.id=t.orderId and m.patientId=pat.id and m.physicianId=p.id and gph.physicianId=p.id and g.id=gph.groupId and "
			+ "g.id=:groupId and p.physicianName like %:physician% and pat.patientName like %:patient% and t.rxStatus like %:status% and m.orderNumber like %:orderNo% and "
			+ "t.rxNumber like %:rxNo% and COALESCE(g.groupName, '') like %:group% GROUP BY m.id ")
	Page<Object[]>  groupReportByFilter(@Param("groupId") int groupId, @Param("physician") String physician, @Param("patient") String patient, @Param("status") String status, 
			@Param("group") String group,  @Param("orderNo") String orderNo,  @Param("rxNo") String rxNumber, Pageable page);

	@Query(value = "Select m.orderNumber, m.orderDate, t.rxNumber, p.physicianName, pat.patientName, concat(t.dispensedName, ' ', t.dispensedDrug, ' ',"
			+ "t.dispensedQuantity, ' ', t.dispensedUnit) as medicationDispensed, t.refillsFilled, t.daysSupply, t.refillsRemaining, t.rxStatus, m.id  "
			+ "from OrderMaster m, OrderTransaction t, PhysicianAccount p, PatientAccount pat, PhysicianGroup gph, GroupMaster g "
			+ "where m.id=t.orderId and m.patientId=pat.id and m.physicianId=p.id and gph.physicianId=p.id and g.id=gph.groupId and "
			+ "g.id=:groupId and p.physicianName like %:physician% and pat.patientName like %:patient% and t.rxStatus like %:status% and m.orderNumber like %:orderNo% and "
			+ "t.rxNumber like %:rxNo% and COALESCE(g.groupName, '') like %:group% and m.orderDate>=:fromDate GROUP BY m.id ")
	Page<Object[]>  groupReportByFilterWithFromDate(@Param("groupId") int groupId, @Param("physician") String physician, @Param("patient") String patient, @Param("status") String status, 
			@Param("group") String group,  @Param("orderNo") String orderNo,  @Param("rxNo") String rxNumber, @Param("fromDate") Date fromDate, Pageable page);
	
	@Query(value = "Select m.orderNumber, m.orderDate, t.rxNumber, p.physicianName, pat.patientName, concat(t.dispensedName, ' ', t.dispensedDrug, ' ',"
			+ "t.dispensedQuantity, ' ', t.dispensedUnit) as medicationDispensed, t.refillsFilled, t.daysSupply, t.refillsRemaining, t.rxStatus, m.id  "
			+ "from OrderMaster m, OrderTransaction t, PhysicianAccount p, PatientAccount pat, PhysicianGroup gph, GroupMaster g "
			+ "where m.id=t.orderId and m.patientId=pat.id and m.physicianId=p.id and gph.physicianId=p.id and g.id=gph.groupId and "
			+ "g.id=:groupId and p.physicianName like %:physician% and pat.patientName like %:patient% and t.rxStatus like %:status% and m.orderNumber like %:orderNo% and "
			+ "t.rxNumber like %:rxNo% and COALESCE(g.groupName, '') like %:group% and m.orderDate<=:toDate GROUP BY m.id ")
	Page<Object[]>  groupReportByFilterWithToDate(@Param("groupId") int groupId, @Param("physician") String physician, @Param("patient") String patient, @Param("status") String status, 
			@Param("group") String group,  @Param("orderNo") String orderNo,  @Param("rxNo") String rxNumber, @Param("toDate") Date toDate, Pageable page);
	
	@Query(value = "Select m.orderNumber, m.orderDate, t.rxNumber, p.physicianName, pat.patientName, concat(t.dispensedName, ' ', t.dispensedDrug, ' ',"
			+ "t.dispensedQuantity, ' ', t.dispensedUnit) as medicationDispensed, t.refillsFilled, t.daysSupply, t.refillsRemaining, t.rxStatus, m.id  "
			+ "from OrderMaster m, OrderTransaction t, PhysicianAccount p, PatientAccount pat, PhysicianGroup gph, GroupMaster g "
			+ "where m.id=t.orderId and m.patientId=pat.id and m.physicianId=p.id and gph.physicianId=p.id and g.id=gph.groupId and "
			+ "g.id=:groupId and p.physicianName like %:physician% and pat.patientName like %:patient% and t.rxStatus like %:status% and m.orderNumber like %:orderNo% and "
			+ "t.rxNumber like %:rxNo% and COALESCE(g.groupName, '') like %:group% and m.orderDate >=:fromDate and m.orderDate <=:toDate GROUP BY m.id ")
	Page<Object[]>  groupReportByFilterWithDate(@Param("groupId") int groupId, @Param("physician") String physician, @Param("patient") String patient, @Param("status") String status, 
			@Param("group") String group,  @Param("orderNo") String orderNo,  @Param("rxNo") String rxNumber, 
			@Param("fromDate") Date fromDate, @Param("toDate") Date toDate, Pageable page);	
	
	@Query("Select t.rxNumber,pt.itemname,t.rxStatus,pt.rxStatus,pm.cre8PrescriptionNo  from OrderMaster o,OrderTransaction t,PrescriptionTransaction pt,PrescriptionMaster pm where pm.id=pt.prescriptionId and o.id=t.orderId and o.patientId=:patientId and o.physicianId=:physicianId and pt.id=t.prescriptionTranId and pt.itemid=:itemId and t.rxStatus<>'Completed' and t.rxStatus<>'Cancelled' order by o.id desc")
	List<Object[]> getFilterRecordsByPatientIdandPhysicianId(@Param("patientId") int patientId, @Param("physicianId") int physicianId, @Param("itemId") String itemId);

}
