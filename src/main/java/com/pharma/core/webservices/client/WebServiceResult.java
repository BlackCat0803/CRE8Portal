package com.pharma.core.webservices.client;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.pharma.core.model.patientaccount.PatientAccount;
import com.pharma.core.model.physician.PhysicianAccount;
import com.pharma.core.repository.patient.PatientAccountRespository;
import com.pharma.core.repository.physician.PhysicianAccountRespository;
import com.pharma.core.util.PharmacyUtil;

/**
 * 
 * The class WebServiceResult handles the result returned from the Pioneer Web Service
 *
 */
public class WebServiceResult extends SpringBeanAutowiringSupport{

	
	
	
	@Autowired
	PhysicianAccountRespository physicianRepo;
	
	@Autowired
	PatientAccountRespository patientRepo;
	
	/**
	 * updateWebServiceResult-Updates the value returned from the Pioneer Web Service in the Pioneer Web Portal
	 * @param jsonObject
	 */
	public void updateWebServiceResult(JSONObject jsonObject)
	{
		
		String tableType="";
		int physicianId=0;
		try {
			if(jsonObject!=null){
				tableType=jsonObject.get("webServiceTableName").toString();
				if (tableType.equalsIgnoreCase(PharmacyUtil.userPhysician)) {
					
					if(jsonObject.has("physicianId"))
						physicianId=Integer.parseInt(jsonObject.getString("physicianId"));
					else if(jsonObject.has("id"))
						physicianId=Integer.parseInt(jsonObject.getString("id"));
					//System.out.println("jsonObject.getString physicianId ====="+physicianId);
					PhysicianAccount entityObj =physicianRepo.findOne(physicianId);
					entityObj.setPioneerUid(jsonObject.getString("webServiceNewID"));
					entityObj.setPioneerResponse(jsonObject.getInt("webServiceCallResult"));
					physicianRepo.save(entityObj);
					
					
				}else if (tableType.equalsIgnoreCase(PharmacyUtil.userPatient)) {
					//System.out.println("jsonObject.getString iddd ====="+jsonObject.getString("id"));
					PatientAccount entityObj =patientRepo.findOne(Integer.parseInt(jsonObject.getString("id")));
					entityObj.setPioneerUid(jsonObject.getString("webServiceNewID"));
					entityObj.setPioneerResponse(jsonObject.getInt("webServiceCallResult"));
					patientRepo.save(entityObj);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
}
