package com.pharma.core.events.physician;

import org.springframework.context.ApplicationEvent;

import com.pharma.core.formBean.physician.PhysicianProfile;
/**
 * This class is a asynchronous and transactional patient event published on physician status change
 *
 */
public class PhysicianEvent extends ApplicationEvent
{
    private static final long serialVersionUID = 1L;
     
    private String eventType;
    private PhysicianProfile physicianProfileForm;
    private String restURL;
    private String previousStatus;
     
    //Constructor's first parameter must be source
    public PhysicianEvent(Object source, String eventType, PhysicianProfile physicianProfileForm,String RESTfulURL,String previousStatus)
    {
        //Calling this super class constructor is necessary
        super(source);
        this.eventType = eventType;
        this.physicianProfileForm = physicianProfileForm;
        this.restURL=RESTfulURL;
        this.previousStatus=previousStatus;
    }
 
    public String getEventType() {
        return eventType;
    }
 
    public PhysicianProfile getPhysicianProfileForm() {
        return physicianProfileForm;
    }
    public String getRestURL() {
        return restURL;
    }

	public String getPreviousStatus() {
		return previousStatus;
	}

	public void setPreviousStatus(String previousStatus) {
		this.previousStatus = previousStatus;
	}
}