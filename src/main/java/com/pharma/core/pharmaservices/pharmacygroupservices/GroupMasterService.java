package com.pharma.core.pharmaservices.pharmacygroupservices;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.pharma.core.formBean.LoginForm;
import com.pharma.core.formBean.pharmacygroup.GroupMasterForm;
import com.pharma.core.model.pharmacygroup.GroupMaster;
import com.pharma.core.model.physician.PhysicianAccount;


@Service
public interface GroupMasterService {
	
	GroupMaster saveGroupMaster(GroupMasterForm form, LoginForm loggedInUser,CommonsMultipartFile photoFile,String rootFilePath);
	
	GroupMasterForm getGroupMasterData(int id, String filepath);

	String getGroupMasterDataList(int draw, int start, int len, String searchTerm, int orderColumn, String orderDir, String groupName, String status);

	List<GroupMaster>  getAllGroupMaster();
	
	List<GroupMaster>  getAllGroupMaster(String status);
	
	List<GroupMaster>  getAllGroupMaster(String status,int groupId);
		
	GroupMaster getGroupMasterDetails(int id);
	
	List<GroupMaster> getByGroupMaster(int id);

	boolean deleteClinicAccountById(int id , LoginForm loggedInUser);
	
	String getPhysicianDataByGroupId(int draw, int start, int length, String searchTerm, int orderColumn, String orderDir, 
			int groupId, String physicianName, String status);
	
	String getPhysicianAssistantDataByGroupId(int draw, int start, int length, String searchTerm, int orderColumn, String orderDir, 
			int groupId, String assistantName, String status);

	String getPatientDataByGroupId(int draw, int start, int length, String searchTerm, int orderColumn, String orderDir, 
			int groupId, String patientName, String status);

	boolean changePhysicianGroup(int physicianId, int newGroupId, int groupId, LoginForm loggedInUser);

	boolean changeAssistantGroup(int assistantId, int newGroupId, int groupId, LoginForm loggedInUser);
	
	List<PhysicianAccount> getPhysicianListForGroupChange();
	
	List<PhysicianAccount> getPhysicianListForGroupChange(int groupId);
	
	boolean changePatientPhysicianInGroupMaster(int patientId, int newPhysicianId, int groupId, LoginForm loggedInUser);
	
	boolean changePhysicianStatusFromGroupMaster(int physicianId, String status, LoginForm loggedInUser, HttpServletRequest request);
	
	boolean changeAssistantStatusFromGroupMaster(int assistantId, String status, LoginForm loggedInUser, HttpServletRequest request);
	
	boolean changePatientStatusFromGroupMaster(int patientId, String status, LoginForm loggedInUser, HttpServletRequest request);
	
	
}
