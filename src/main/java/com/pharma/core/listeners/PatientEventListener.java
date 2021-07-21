package com.pharma.core.listeners;

import org.json.JSONObject;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.PropertySource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pharma.core.events.patient.PatientEvent;
import com.pharma.core.webservices.client.PharmacyClient;

/**
 * 
 * The class <<PatientEventListener>>  invokes the Wrapper class to sends the JSON Object to the Pioneer Web Service for Pushing the data from the Portal to the Pioneer System
 * Status==>Approved/Changed from New Modifications to Approved for Re-Approval
 */
@PropertySource("classpath:patientAccount.properties")
@PropertySource("classpath:webService.properties")
public class PatientEventListener implements ApplicationListener<PatientEvent>  {
	
    public void onApplicationEvent(PatientEvent event)
    {
    	PatientEvent patientEvent = (PatientEvent) event;
 
        //System.out.println("Patient " + patientEvent.getEventType() + " with details : " + patientEvent.getPatientForm().getPatientName()+"==="+ patientEvent.getPatientForm().getStatus());
 
        // Do more processing as per application logic
        
    	 if((patientEvent.getPatientForm().getStatus().equalsIgnoreCase("Approved") 
    			 && (patientEvent.getPatientForm().getPioneerUid()==null || patientEvent.getPatientForm().getPioneerUid().equalsIgnoreCase("")))
    		|| (event.getPreviousStatus().equalsIgnoreCase("New Modifications") && patientEvent.getPatientForm().getStatus().equalsIgnoreCase("Approved"))) 
    		{
				try {

					String RESTfulURL=patientEvent.getRestURL();
					//System.out.println("RESTfulURL ======"+RESTfulURL);
					Gson gson = new GsonBuilder().setPrettyPrinting().create();
					String json2 = gson.toJson(patientEvent.getPatientForm());
					//System.out.println("acc ======="+patientEvent.getPatientForm());
					//System.out.println("json2 ======="+json2);
					
					JSONObject jsonObject = null;
					jsonObject = PharmacyClient.webServiceConnectionWrapper(RESTfulURL, "application/json", "POST", json2);
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
		 
    }
  
}