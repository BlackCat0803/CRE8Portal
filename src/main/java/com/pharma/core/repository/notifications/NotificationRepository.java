package com.pharma.core.repository.notifications;

import java.sql.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pharma.core.model.notifications.Notification;
/**
 * This class is a Java interface that defines a data access contract
 *
 */
@Repository("notificationRepository")
public interface NotificationRepository extends JpaRepository<Notification, Integer> {

	Notification findByNotifyRecordIdAndNotifyRecordTypeAndDelFlag(int physicianId, String condition, String delFlag);
	
	Notification findByNotifyTypeIdAndNotifyRecordIdAndNotifyRecordTypeAndDelFlag(int typeId, int physicianId, String condition, String delFlag);
	
	Notification findByNotifyTypeIdAndNotifyRecordIdAndNotifyRecordType(int typeId, int physicianId, String condition);
	
	Notification findByClinicId(int clinicId);
	
	
	@Query("select n from Notification n where (notifiedDate>=COALESCE(:notifyStartDate, 0) and notifiedDate<=COALESCE(:notifyEndDate, CURRENT_TIMESTAMP)) and "
			+"  COALESCE(n.rxNo,'') like %:notifyRxNo%  and COALESCE(n.referenceNo,'') like %:notifyReferenceNo%  and COALESCE(n.patientName,'') "
			+ "like %:notifyPatientName%   and COALESCE(n.physicianName,'') like %:notifyPhysicianName%  and COALESCE(n.notifyRecordType,'') "
			+ "like %:notificationtype%  and n.notifyTypeId in (select id from NotificationType where adminNotification='Yes' and delFlag='N') "
			+ " order by n.notifiedDate desc")
	List<Notification> getAdminNotificationMessage(@Param("notifyStartDate") Date notifyStartDate, @Param("notifyEndDate") Date notifyEndDate,
			@Param("notifyRxNo") String notifyRxNo,@Param("notifyReferenceNo") String notifyReferenceNo,@Param("notifyPatientName") String notifyPatientName,
			@Param("notifyPhysicianName") String  notifyPhysicianName,@Param("notificationtype") String  notificationtype);
	
	
	@Query("select n from Notification n where (notifiedDate>=COALESCE(:notifyStartDate, 0) and notifiedDate<=COALESCE(:notifyEndDate, CURRENT_TIMESTAMP)) and "
			+"  COALESCE(n.rxNo,'') like %:notifyRxNo%  and COALESCE(n.referenceNo,'') like %:notifyReferenceNo%  and COALESCE(n.patientName,'') "
			+ "like %:notifyPatientName%   and COALESCE(n.physicianName,'') like %:notifyPhysicianName%  and COALESCE(n.notifyRecordType,'') "
			+ "like %:notificationtype%  and n.notifyTypeId in (select id from NotificationType where adminNotification='Yes' and delFlag='N') "
			+ " order by n.notifiedDate desc")
	Page<Notification> getAdminNotificationMessage(@Param("notifyStartDate") Date notifyStartDate, @Param("notifyEndDate") Date notifyEndDate,
			@Param("notifyRxNo") String notifyRxNo,@Param("notifyReferenceNo") String notifyReferenceNo,@Param("notifyPatientName") String notifyPatientName,
			@Param("notifyPhysicianName") String  notifyPhysicianName,@Param("notificationtype") String  notificationtype,Pageable page);
	
	
	
	@Query("select n from Notification n where (notifiedDate>=COALESCE(:notifyStartDate, 0) and notifiedDate<=COALESCE(:notifyEndDate, CURRENT_TIMESTAMP)) and "
			+"  COALESCE(n.rxNo,'') like %:notifyRxNo%  and COALESCE(n.referenceNo,'') like %:notifyReferenceNo%  and COALESCE(n.patientName,'') "
			+ "like %:notifyPatientName%   and COALESCE(n.physicianName,'') like %:notifyPhysicianName%  and COALESCE(n.notifyRecordType,'') "
			+ "like %:notificationtype%  and n.notifyTypeId in (select id from NotificationType where physicianNotification='Yes' and delFlag='N') "
			+ " order by n.notifiedDate desc")
	List<Notification> getPhysicianNotificationMessage(@Param("notifyStartDate") Date notifyStartDate, @Param("notifyEndDate") Date notifyEndDate,
			@Param("notifyRxNo") String notifyRxNo,@Param("notifyReferenceNo") String notifyReferenceNo,@Param("notifyPatientName") String notifyPatientName,
			@Param("notifyPhysicianName") String  notifyPhysicianName,@Param("notificationtype") String  notificationtype);
	
	
	
	@Query("select n from Notification n where (notifiedDate>=COALESCE(:notifyStartDate, 0) and notifiedDate<=COALESCE(:notifyEndDate, CURRENT_TIMESTAMP)) and "
			+"  COALESCE(n.rxNo,'') like %:notifyRxNo%  and COALESCE(n.referenceNo,'') like %:notifyReferenceNo%  and COALESCE(n.patientName,'') "
			+ "like %:notifyPatientName%   and COALESCE(n.physicianName,'') like %:notifyPhysicianName%  and COALESCE(n.notifyRecordType,'') "
			+ "like %:notificationtype%  and n.notifyTypeId in (select id from NotificationType where physicianNotification='Yes' and delFlag='N') "
			+ " order by n.notifiedDate desc")
	Page<Notification>  getPhysicianNotificationMessage(@Param("notifyStartDate") Date notifyStartDate, @Param("notifyEndDate") Date notifyEndDate,
			@Param("notifyRxNo") String notifyRxNo,@Param("notifyReferenceNo") String notifyReferenceNo,@Param("notifyPatientName") String notifyPatientName,
			@Param("notifyPhysicianName") String  notifyPhysicianName,@Param("notificationtype") String  notificationtype,Pageable page);


	
	
	@Modifying
	@Transactional(readOnly=false)
    @Query("UPDATE Notification c SET c.delFlag = ?2 WHERE c.notifyRecordId = ?1 and c.notifyRecordType=?3")
    int recordsSetDeleteFlag(int physicianId, String delFlag, String recordType);
	

}
