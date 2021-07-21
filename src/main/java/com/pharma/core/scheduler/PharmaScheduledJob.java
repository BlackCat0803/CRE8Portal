package com.pharma.core.scheduler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pharma.core.formBean.DocumentFileList;
import com.pharma.core.formBean.physician.PhysicianProfile;
import com.pharma.core.model.Pharmacy;
import com.pharma.core.model.patientaccount.PatientAccount;
import com.pharma.core.model.physician.PhysicianAccount;
import com.pharma.core.model.physician.PhysicianFileUpload;
import com.pharma.core.pharmaservices.mail.Mail;
import com.pharma.core.pharmaservices.mail.MailSendConfig;
import com.pharma.core.repository.patient.PatientAccountRespository;
import com.pharma.core.repository.physician.PhysicianAccountRespository;
import com.pharma.core.repository.physician.PhysicianFileUploadRepository;
import com.pharma.core.util.PharmacyUtil;
import com.pharma.core.webservices.client.PharmacyClient;
/**
 * 
 *The <<PharmaScheduledJob>> schedules the Job for updating the Patient/Physician details in the Pioneer System
 * if the pioneer result is -1 at the particular time every day
 *
 */
@PropertySource("classpath:physicianAccount.properties")
@PropertySource("classpath:webService.properties")
public class PharmaScheduledJob extends SpringBeanAutowiringSupport {
 
	@Autowired
	PhysicianAccountRespository physicianRepo;
	
	@Autowired
	PatientAccountRespository patientRepo;
	
	@Autowired
	private Environment env;
	   
    @Autowired
	PhysicianAccountRespository phyAccount;
    
	@Autowired
	PhysicianFileUploadRepository phyFileRepo;
	
	private AtomicInteger counter = new AtomicInteger(0);
	
    //@Scheduled(fixedRate=60*60*1000, initialDelay=10*60*1000)
	/**
	 * Cron expression is represented by six fields:
		second, minute, hour, day of month, month, day(s) of week
	 *  0- is for seconds
		0/30- 30 minute
		6- hour of the day.
	 */
	//@Scheduled(cron="0 0/30 6 * * MON-FRI")
	@Async @Scheduled(cron="${schedularTime}")
	public void schedulePioneerUpdationInfo(){
    	int jobId = counter.incrementAndGet();
    	//System.out.println("Job @ fixed delay " + new Date() + ", jobId: " + jobId);
    	String PioneerPatientInfo=env.getProperty("PioneerPatientInfoFlag")+"";
    	String PioneerPhysicianInfo=env.getProperty("PioneerPhysicianInfoFlag")+"";
    	String PioneerPharmacyTables=env.getProperty("PioneerPharmacyTablesFlag")+"";
    	
    	String PioneerChangedInfo=env.getProperty("PioneerChangedInfoFlag")+"";
    	String PioneerRX=env.getProperty("PioneerRXFlag")+"";
    	String PointOfSale=env.getProperty("PointOfSaleFlag")+"";
    	
    	//System.out.println("PioneerPatientInfo =="+PioneerPatientInfo+"==PioneerPhysicianInfo=="+PioneerPhysicianInfo+"==PioneerPharmacyTables=="+PioneerPharmacyTables+"==PioneerChangedInfo=="+PioneerChangedInfo+"==PioneerRX=="+PioneerRX+"==PointOfSale=="+PointOfSale);
    	
    	
    	if(PioneerPatientInfo.equalsIgnoreCase("true"))
    		updatePioneerPatientInfo();
    	if(PioneerPhysicianInfo.equalsIgnoreCase("true"))
    		updatePioneerPhysicianInfo();
    	
    	if(PioneerPharmacyTables.equalsIgnoreCase("true"))
    		SchedulePioneerPharmacyTables();
    	
    	if(PioneerChangedInfo.equalsIgnoreCase("true"))
    		SchedulePioneerChangedInfo();
    	
    	if(PioneerRX.equalsIgnoreCase("true"))
    		SchedulePioneerRX();
    	
    	if(PointOfSale.equalsIgnoreCase("true"))
    		SchedulePointOfSale();
    	
    	//System.out.println("Job " + jobId + " done");
    }
	
	
	// 0 15 9-17 * * MON-FRI
	@Async @Scheduled(cron="${expMailSchedularTime}")
    public void testSchedulePioneerUpdationInfo(){
    	int jobId = counter.incrementAndGet();
    	//System.out.println("Job @ fixed delay " + new Date() + ", jobId: " + jobId);
    	DocumentLicenseExpiryMail();
    	//System.out.println("Job " + jobId + " done");
    }
	
    /**
     * Scheduler for Non-Updated Prescriber - Daily Scheduler created at the CRE8 Portal-Schedules the Job which Updates the 
     * Physician details in the Pioneer System if the pioneer result is -1 for non-updated Approved Physicians
     */
    public void updatePioneerPatientInfo()
    {
    	
    	List<PatientAccount> allPatients=patientRepo.fetchAllNotUpdatedPioneerPatient();
    	
    	if(allPatients!=null && allPatients.size()>0){
	    	for (Iterator iterator = allPatients.iterator(); iterator.hasNext();) {
				PatientAccount patientAccount = (PatientAccount) iterator.next();
				try {
	
					String RESTfulURL=env.getProperty("webservice_url")+env.getProperty("PatientSave");
					
					Gson gson = new GsonBuilder().setPrettyPrinting().create();
					String json2 = gson.toJson(patientAccount);
					/*//System.out.println("acc ======="+patientAccount);
					//System.out.println("json2 ======="+json2);*/
					
					PharmacyClient.webServiceConnectionWrapper(RESTfulURL, "application/json", "POST", json2);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
    	}
    	
    }
    
    /**
     * Scheduler for Non-Updated Patient - Daily Scheduler created at the CRE8 Portal-Schedules the Job which Updates the 
     * Patient details in the Pioneer System if the pioneer result is -1 for non-updated Active Patients
     */
    public void updatePioneerPhysicianInfo()
    {
    	
    	List<PhysicianAccount> allPhysicians=physicianRepo.fetchAllNotUpdatedPioneerPhysician();
    	
    	if(allPhysicians!=null && allPhysicians.size()>0){
	    	for (Iterator iterator = allPhysicians.iterator(); iterator.hasNext();) {
	    		PhysicianAccount physicianAccount = (PhysicianAccount) iterator.next();
				try {
					
					String RESTfulURL=env.getProperty("webservice_url")+env.getProperty("PrescriberSave");
					
					Gson gson = new GsonBuilder().setPrettyPrinting().create();
					String json2 = gson.toJson(physicianAccount);
					/*//System.out.println("acc ======="+physicianAccount);
					//System.out.println("json2 ======="+json2);*/
					
					PharmacyClient.webServiceConnectionWrapper(RESTfulURL, "application/json", "POST", json2);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
    	}
    	
    }

    /**
     * Schedules for pushing PioneerPharmacyTables
     * Scheduler for mapping the Pioneer Tables into CRE8Portal for putting Prescriptions
     */
    public void SchedulePioneerPharmacyTables()
    {
    
				try {
					
					String RESTfulURL=env.getProperty("webservice_url")+env.getProperty("SchedulePioneerPharmacyTables");
					
					Gson gson = new GsonBuilder().setPrettyPrinting().create();
					String json2 = gson.toJson(null);
					//System.out.println("json2 ======="+json2);
					
					PharmacyClient.webServiceConnectionWrapper(RESTfulURL, "application/json", "POST", json2);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			
    }
    
    /**
     * SchedulePioneerChangedInfo
     * Scheduler for Tracking Patient/Prescriber Info if it is changed in Pioneer
     */
    public void SchedulePioneerChangedInfo()
    {
    
				try {
					
					String RESTfulURL=env.getProperty("webservice_url")+env.getProperty("SchedulePioneerChangedInfo");
					
					Gson gson = new GsonBuilder().setPrettyPrinting().create();
					String json2 = gson.toJson(null);
					//System.out.println("json2 ======="+json2);
					
					PharmacyClient.webServiceConnectionWrapper(RESTfulURL, "application/json", "POST", json2);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			
    }
    /**
     * Schedule the PioneerRX
     * Scheduler for Tracking RX Info from Pioneer. 
     */
    public void SchedulePioneerRX()
    {
    
				try {
					
					String RESTfulURL=env.getProperty("webservice_url")+env.getProperty("SchedulePioneerRX");
					
					Gson gson = new GsonBuilder().setPrettyPrinting().create();
					String json2 = gson.toJson(null);
					//System.out.println("json2 ======="+json2);
					
					PharmacyClient.webServiceConnectionWrapper(RESTfulURL, "application/json", "POST", json2);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			
    }
    /**
     * Schedule the PointOfSale
     * Scheduler for Tracking latest Shipment information of an rx/rf from Pioneer. 
     */
    public void SchedulePointOfSale()
    {
    
				try {
					
					String RESTfulURL=env.getProperty("webservice_url")+env.getProperty("SchedulePointOfSale");
					
					Gson gson = new GsonBuilder().setPrettyPrinting().create();
					String json2 = gson.toJson(null);
					//System.out.println("json2 ======="+json2);
					
					PharmacyClient.webServiceConnectionWrapper(RESTfulURL, "application/json", "POST", json2);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			
    }
	
    public void DocumentLicenseExpiryMail() {
    	if ("true".equalsIgnoreCase(env.getProperty("isMailSend"))) {
	    	try {
	    		MailSendConfig mail = new MailSendConfig();
	    		
	    		SimpleDateFormat sm = new SimpleDateFormat("MM/dd/yyyy");
	    		Calendar cal = Calendar.getInstance(); 
	    		cal.add(Calendar.MONTH, 1);
	    		String dateToPick = sm.format(cal.getTime());
	    		
	    		//System.out.println("Targeted expiry date for physician documents "+ dateToPick);
		    		
	    		List<PhysicianAccount> phyList = phyAccount.getAllEmailCommunicatePhysicians();
	    		List<DocumentFileList> phyAssObjList = new ArrayList<DocumentFileList>();
	    		if (phyList.size() > 0) {
	    			for (PhysicianAccount p: phyList) {
	    				phyAssObjList = new ArrayList<DocumentFileList>();
	    				List<PhysicianFileUpload> uploadedFiles = phyFileRepo.getLicenseExpiredDocListByPhysician(p.getId(), PharmacyUtil.getSqlDateFromString(dateToPick));
	    				if (uploadedFiles.size() > 0) {
	    					for (PhysicianFileUpload a: uploadedFiles) {
	    						DocumentFileList d = new DocumentFileList();
		        					
	        					d.setOriginalFileName(a.getOriginalFileName());
	        					d.setStoredFielName(a.getStoredFielName());
	        					if (a.getDocExpiryDate() != null)
	        						d.setDocExpiryDate( PharmacyUtil.getStringDateFromSqlDate(a.getDocExpiryDate()) );
	        					else
	        						d.setDocExpiryDate("");
	        					d.setFilePurpose(a.getFilePurpose());
	        					phyAssObjList.add(d);
	    					}
	    				}
	    				
						// Physician will get Welcome mail
	    				mail.physicianDocExpiryMail(p, env, "toPhysicianDocExpiryMail", phyAssObjList );
	    			}
	    		}
	    		
	    	} catch(Exception e) {
	    		e.printStackTrace();
	    	}
    	}
    }
    
  
}