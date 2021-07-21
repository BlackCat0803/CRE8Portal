package com.pharma.core.pharmaservices.notifications;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.pharma.core.model.notifications.Notification;

@Service
public interface NotificationService {

	public Notification saveNotification(Notification notification);
	
	public Notification getNofification(int id);
	
	public Notification getPhysicianId(int physicianId);
	
	public Notification getPhysicianReapprove(int physicianId);
	
	public Notification getPhysicianDeactivated(int physicianId);
	
	public Notification getPhysicianCreateNewClinic(int physicianId);
	
	public Notification getPhysicianCriticalCommentsChanges(int physicianId);
	
	
	public Notification getPatientReapprove(int patientId);
	
	public Notification getPatientDeactivated(int patientId);
	
	public Notification getPatientNewPhysicianAdded(int patientId);
	
	public Notification getPatientCriticalCommentsChanges(int patientId);

	
	public Notification getPrescriptionControlledSubstance(int prescriptionId);
	
	public Notification getPrescriptionMedicationChanged(int prescriptionId);
	
	public Notification getPrescriptionProcessStarted(int prescriptionId);
	
	String getNotificationDataList(int draw, int start, int len, String searchTerm, int orderColumn, String orderDir,
			String notifyStartDate, String notifyEndDate, String notifyRxNo, String notifyReferenceNo, 
			String notifyPatientName, String notifyPhysicianName,String notificationtype, HttpServletRequest request, HttpSession session);
	
	
	public void approveNotifyClinic(int clinicId);
	
	
	public void physicianSetDeleteYes(int physicianId);
	
	public void patientSetDeleteYes(int patientId);
	
	public Notification getPrescriptionItemChanged(int prescriptionId);
	
}
