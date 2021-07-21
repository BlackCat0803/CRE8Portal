package com.pharma.core.pharmaservices.notifications;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pharma.core.formBean.LoginForm;
import com.pharma.core.formBean.notifications.NotificationForm;
import com.pharma.core.formBean.notifications.NotificationJSonObj;
import com.pharma.core.model.notifications.Notification;
import com.pharma.core.repository.notifications.NotificationRepository;
import com.pharma.core.util.PharmacyUtil;
/**
 * This is an implementation class that triggers and stores the nofication data
 */
@Service("notificationService")
public class NotificationServiceImpl implements NotificationService {

	@Autowired
	NotificationRepository notificationRepo;
	
	public Notification saveNotification(Notification notification) {
		Notification notify = null;
		try {
			notify = notificationRepo.save(notification);
		} catch(Exception e){
			e.printStackTrace();
		}
		return notify;
	}

	public Notification getNofification(int id) {
		return notificationRepo.findOne(id);
	}

	public Notification getPhysicianId(int physicianId) {
		return notificationRepo.findByNotifyRecordIdAndNotifyRecordTypeAndDelFlag(physicianId, PharmacyUtil.pagePhysician, PharmacyUtil.deleteFlagNo);
	}

	public Notification getPhysicianReapprove(int physicianId) {
		return notificationRepo.findByNotifyTypeIdAndNotifyRecordIdAndNotifyRecordTypeAndDelFlag(1, physicianId, PharmacyUtil.pagePhysician, PharmacyUtil.deleteFlagNo);
	}

	public Notification getPhysicianDeactivated(int physicianId) {
		return notificationRepo.findByNotifyTypeIdAndNotifyRecordIdAndNotifyRecordTypeAndDelFlag(2, physicianId, PharmacyUtil.pagePhysician, PharmacyUtil.deleteFlagNo);
	}
	
	public Notification getPhysicianCreateNewClinic(int physicianId) {
		return notificationRepo.findByNotifyTypeIdAndNotifyRecordIdAndNotifyRecordTypeAndDelFlag(3, physicianId, PharmacyUtil.pagePhysician, PharmacyUtil.deleteFlagNo);		
	}
	
	public Notification getPhysicianCriticalCommentsChanges(int physicianId) {
		return notificationRepo.findByNotifyTypeIdAndNotifyRecordIdAndNotifyRecordTypeAndDelFlag(4, physicianId, PharmacyUtil.pagePhysician, PharmacyUtil.deleteFlagNo);
	}

	
	public Notification getPatientReapprove(int patientId) {
		return notificationRepo.findByNotifyTypeIdAndNotifyRecordIdAndNotifyRecordTypeAndDelFlag(16, patientId, PharmacyUtil.pagePatient, PharmacyUtil.deleteFlagNo);	
	}

	public Notification getPatientDeactivated(int patientId) {
		return notificationRepo.findByNotifyTypeIdAndNotifyRecordIdAndNotifyRecordTypeAndDelFlag(8, patientId, PharmacyUtil.pagePatient, PharmacyUtil.deleteFlagNo);
	}

	public Notification getPatientNewPhysicianAdded(int patientId) {
		return notificationRepo.findByNotifyTypeIdAndNotifyRecordIdAndNotifyRecordTypeAndDelFlag(9, patientId, PharmacyUtil.pagePatient, PharmacyUtil.deleteFlagNo);
	}

	public Notification getPatientCriticalCommentsChanges(int patientId) {
		return notificationRepo.findByNotifyTypeIdAndNotifyRecordIdAndNotifyRecordTypeAndDelFlag(10, patientId, PharmacyUtil.pagePatient, PharmacyUtil.deleteFlagNo);
	}
	
	
	

	public Notification getPrescriptionControlledSubstance(int prescriptionId) {
		return notificationRepo.findByNotifyTypeIdAndNotifyRecordIdAndNotifyRecordTypeAndDelFlag(5, prescriptionId, PharmacyUtil.pagePrescription, PharmacyUtil.deleteFlagNo);
	}

	public Notification getPrescriptionMedicationChanged(int prescriptionId) {
		return notificationRepo.findByNotifyTypeIdAndNotifyRecordIdAndNotifyRecordTypeAndDelFlag(6, prescriptionId, PharmacyUtil.pagePrescription, PharmacyUtil.deleteFlagNo);
	}

	public Notification getPrescriptionProcessStarted(int prescriptionId) {
		return notificationRepo.findByNotifyTypeIdAndNotifyRecordIdAndNotifyRecordTypeAndDelFlag(7, prescriptionId, PharmacyUtil.pagePrescription, PharmacyUtil.deleteFlagNo);
	}
	
	
	
	public String getNotificationDataList(int draw, int start, int length,
			String searchTerm, int orderColumn, String orderDir,
			String notifyStartDate, String notifyEndDate, String notifyRxNo,
			String notifyReferenceNo, String notifyPatientName,
			String notifyPhysicianName, String notificationtype,HttpServletRequest request,
			HttpSession session) {
		

		int total = 0;
		int pageNumber = 0;
		Pageable page = null;
		Page<Notification> notificationList = null;
		if (notifyStartDate == null) notifyStartDate = "";
		if (notifyEndDate == null) notifyEndDate = "";
		if (notifyRxNo == null) notifyRxNo = "";
		if (notifyReferenceNo == null) notifyReferenceNo = "";
		if (notifyPatientName == null) notifyPatientName = "";
		if (notifyPhysicianName == null) notifyPhysicianName = "";
		
		LoginForm loggedInUser = (LoginForm) session.getAttribute("loginDetail");
		String userType=loggedInUser.getType();
		
		
		Direction dir = Direction.ASC;
		if (orderDir.equalsIgnoreCase("desc"))
			dir = Direction.DESC;
		
		try {
			if(userType.equalsIgnoreCase(PharmacyUtil.userSuperAdmin) || userType.equalsIgnoreCase(PharmacyUtil.userAdmin) || userType.equalsIgnoreCase(PharmacyUtil.userAdministrator))
			{
				if(notifyStartDate.length()==0 && notifyEndDate.length()==0)
					total = notificationRepo.getAdminNotificationMessage(null,null,
						notifyRxNo,notifyReferenceNo,notifyPatientName,notifyPhysicianName,notificationtype).size();
				else
					total = notificationRepo.getAdminNotificationMessage(PharmacyUtil.getSqlDateFromString(notifyStartDate),
							PharmacyUtil.getSqlDateFromString(notifyEndDate),
							notifyRxNo,notifyReferenceNo,notifyPatientName,notifyPhysicianName,notificationtype).size();
			}
			else if(userType.equalsIgnoreCase(PharmacyUtil.userPhysician))
			{
				if(notifyStartDate.length()==0 && notifyEndDate.length()==0)
					total = notificationRepo.getPhysicianNotificationMessage(null,null,
						notifyRxNo,notifyReferenceNo,notifyPatientName,notifyPhysicianName,notificationtype).size();
				else
					total = notificationRepo.getPhysicianNotificationMessage(PharmacyUtil.getSqlDateFromString(notifyStartDate),
							PharmacyUtil.getSqlDateFromString(notifyEndDate),
							notifyRxNo,notifyReferenceNo,notifyPatientName,notifyPhysicianName,notificationtype).size();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		pageNumber = start / length;
		
		switch (orderColumn) {
			default: {
				page = new PageRequest(pageNumber, length, dir, "id");
				break;
			}
		}
		
		try {
			
			if(userType.equalsIgnoreCase(PharmacyUtil.userSuperAdmin) || userType.equalsIgnoreCase(PharmacyUtil.userAdmin) || userType.equalsIgnoreCase(PharmacyUtil.userAdministrator))
			{
				if(notifyStartDate.length()==0 && notifyEndDate.length()==0)
					notificationList = notificationRepo.getAdminNotificationMessage(null,null,
							notifyRxNo,notifyReferenceNo,notifyPatientName,notifyPhysicianName,notificationtype,page);
				else
					notificationList = notificationRepo.getAdminNotificationMessage(PharmacyUtil.getSqlDateFromString(notifyStartDate),
							PharmacyUtil.getSqlDateFromString(notifyEndDate),
							notifyRxNo,notifyReferenceNo,notifyPatientName,notifyPhysicianName,notificationtype,page);
			}
			else if(userType.equalsIgnoreCase(PharmacyUtil.userPhysician))
			{
				if(notifyStartDate.length()==0 && notifyEndDate.length()==0)
					notificationList = notificationRepo.getPhysicianNotificationMessage(null,null,
						notifyRxNo,notifyReferenceNo,notifyPatientName,notifyPhysicianName,notificationtype,page);
				else
					notificationList = notificationRepo.getPhysicianNotificationMessage(PharmacyUtil.getSqlDateFromString(notifyStartDate),
							PharmacyUtil.getSqlDateFromString(notifyEndDate),
							notifyRxNo,notifyReferenceNo,notifyPatientName,notifyPhysicianName,notificationtype,page);
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		List<NotificationForm> notifyObjList = new ArrayList<NotificationForm>();
		if (notificationList != null && notificationList.getSize() > 0 ) {
			for(Notification a: notificationList) {
				NotificationForm obj = new NotificationForm();
				
				obj.setNotifyId(a.getId());
				obj.setNotifyRecordId(a.getNotifyRecordId());
				obj.setNotifyRecordType(a.getNotifyRecordType());
				obj.setNotificationMessage(a.getNotificationMessage());
				
				obj.setDT_RowId("row_" + a.getNotifyRecordId()+"_"+a.getNotifyRecordType());
				
				notifyObjList.add(obj);
			}
		}

		NotificationJSonObj data = new NotificationJSonObj();
		data.setData(notifyObjList);
		data.setDraw(draw);
		data.setRecordsFiltered(total);
		data.setRecordsFiltered(total);
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json2 = gson.toJson(data);
		
		return json2;		
	
	}

	public void approveNotifyClinic(int clinicId) {
		if (clinicId > 0) {
			Notification notify = notificationRepo.findByClinicId(clinicId);
			if (notify != null)  {
				notify.setDelFlag(PharmacyUtil.deleteFlagYes);
				notify.setExpiryDate(PharmacyUtil.getCurrentTimeStamp());

				saveNotification(notify);
			}
		}
	}
	
	public void physicianSetDeleteYes(int physicianId) {
		notificationRepo.recordsSetDeleteFlag(physicianId, PharmacyUtil.deleteFlagYes, PharmacyUtil.pagePhysician);
	}
	
	public void patientSetDeleteYes(int patientId) {
		notificationRepo.recordsSetDeleteFlag(patientId, PharmacyUtil.deleteFlagYes, PharmacyUtil.pagePatient);
	}
	

	public Notification getPrescriptionItemChanged(int prescriptionId) {
		return notificationRepo.findByNotifyTypeIdAndNotifyRecordIdAndNotifyRecordTypeAndDelFlag(6, prescriptionId, PharmacyUtil.pagePrescription, PharmacyUtil.deleteFlagNo);
	}
}
