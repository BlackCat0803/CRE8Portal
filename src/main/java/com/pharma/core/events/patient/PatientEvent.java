package com.pharma.core.events.patient;

import org.springframework.context.ApplicationEvent;

import com.pharma.core.model.patientaccount.PatientAccount;
/**
 * This class is a asynchronous and transactional patient event published on patient status change
 *
 */
public class PatientEvent extends ApplicationEvent
{
    private static final long serialVersionUID = 1L;
     
    private String eventType;
    private PatientAccount patientForm;
    private String restURL;
    private String previousStatus;
     
    //Constructor's first parameter must be source
    public PatientEvent(Object source, String eventType, PatientAccount patientForm,String RESTfulURL,String previousStatus)
    {
        //Calling this super class constructor is necessary
        super(source);
        this.eventType = eventType;
        this.patientForm = patientForm;
        this.restURL=RESTfulURL;
        this.previousStatus=previousStatus;
    }
 
    public String getEventType() {
        return eventType;
    }
 
    public PatientAccount getPatientForm() {
        return patientForm;
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