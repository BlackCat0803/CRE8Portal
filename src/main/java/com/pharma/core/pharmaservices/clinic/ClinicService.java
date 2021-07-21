package com.pharma.core.pharmaservices.clinic;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pharma.core.formBean.LoginForm;
import com.pharma.core.formBean.clinic.ClinicForm;
import com.pharma.core.formBean.physician.PhysicianAssistantRegistration;
import com.pharma.core.model.clinic.Clinic;

@Service
public interface ClinicService {

	List<Clinic> getClinicList(boolean isActive);
	
	List<Clinic> getClinicList(int id);
	
	String getClinicDataList(int draw, int start, int length, String searchTerm, int orderColumn, String orderDir, 
			String clinicName, String state, String status, String email, int groupName);
	
	ClinicForm getClinicAccountById(int id);
	
	Clinic saveClinic(ClinicForm form, LoginForm loggedInUser);
	
	List<Clinic> getClinicByNameAndNotId(String clinicName, int id);
	
	List<Clinic> getClinicByName(String clinicName);
	
	//Auto complete method
	List<Clinic> getAutoCompleteClinicList(String term,String status,int id);

	//Auto complete method
	List<Clinic> getAllAutoCompleteClinicList(String term);
	
	List<Clinic> getClinicByEmailAndNotId(String email, int id);
	
	List<Clinic> getClinicByEmail(String email);
	
	boolean deleteClinicAccountById(int id,  LoginForm loggedInUser);
	
	String getClinicAddressById(String clinicId);

	List<PhysicianAssistantRegistration> getPhysicianAssistantByClinicId(int id);

	String getPhysicianDataByClinicId(int draw, int start, int length, String searchTerm, int orderColumn, String orderDir, int clinicId,String physicianName);
	
	String getPhysicianAssistantDataByClinicId(int draw, int start, int length, String searchTerm, int orderColumn, String orderDir, int clinicId,String assistantName);
	
}
