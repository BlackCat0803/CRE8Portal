package com.pharma.core.listeners;

import org.json.JSONObject;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.PropertySource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pharma.core.events.physician.PhysicianEvent;
import com.pharma.core.webservices.client.PharmacyClient;

/**
 * 
 * The class <<PhysicianEventListener>>  invokes the Wrapper class to sends the JSON Object to the Pioneer Web Service for Pushing the data from the Portal to the Pioneer System
 * Status==>Approved/Changed from New Modifications to Approved for Re-Approval
 */
@PropertySource("classpath:physicianAccount.properties")
@PropertySource("classpath:webService.properties")
public class PhysicianEventListener implements ApplicationListener<PhysicianEvent>  {
	
    public void onApplicationEvent(PhysicianEvent event)
    {
    	PhysicianEvent physicianEvent = (PhysicianEvent) event;
 
        //System.out.println("Physician " + physicianEvent.getEventType() + " with details : " + physicianEvent.getPhysicianProfileForm().getPhysicianName()+"==="+ physicianEvent.getPhysicianProfileForm().getStatus());
 
        // Do more processing as per application logic
        
    	 if((physicianEvent.getPhysicianProfileForm().getStatus().equalsIgnoreCase("Approved") 
				 && (physicianEvent.getPhysicianProfileForm().getPioneerUid()==null || physicianEvent.getPhysicianProfileForm().getPioneerUid().equalsIgnoreCase("")))
			|| (event.getPreviousStatus().equalsIgnoreCase("New Modifications") && physicianEvent.getPhysicianProfileForm().getStatus().equalsIgnoreCase("Approved"))) 
			{
    			try {

					String RESTfulURL=physicianEvent.getRestURL();
					//System.out.println("RESTfulURL ======"+RESTfulURL);
					Gson gson = new GsonBuilder().setPrettyPrinting().create();
					String json2 = gson.toJson(physicianEvent.getPhysicianProfileForm());
					//System.out.println("acc ======="+physicianEvent.getPhysicianProfileForm());
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