package com.pharma.core.pharmaservices.pharmacygroupservices;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pharma.core.formBean.LoginForm;
import com.pharma.core.formBean.patient.PatientAccountForm;
import com.pharma.core.formBean.patient.PatientAccountJSonObj;
import com.pharma.core.formBean.pharmacygroup.GroupMasterForm;
import com.pharma.core.formBean.pharmacygroup.GroupMasterJSonObj;
import com.pharma.core.formBean.physician.PhysicianAssistantJSonObj;
import com.pharma.core.formBean.physician.PhysicianAssistantRegistration;
import com.pharma.core.formBean.physician.PhysicianJSonObj;
import com.pharma.core.formBean.physician.PhysicianProfile;
import com.pharma.core.model.patientaccount.PatientAccount;
import com.pharma.core.model.patientaccount.PatientPhysicians;
import com.pharma.core.model.patientaccount.PatientUpdateHistory;
import com.pharma.core.model.pharmacygroup.GroupDirector;
import com.pharma.core.model.pharmacygroup.GroupMaster;
import com.pharma.core.model.physician.PhysicianAccount;
import com.pharma.core.model.physician.PhysicianAssistantAccount;
import com.pharma.core.model.physician.PhysicianAssistantPhysician;
import com.pharma.core.model.physician.PhysicianGroup;
import com.pharma.core.model.physician.PhysicianUpdateHistory;
import com.pharma.core.pharmaservices.patientservices.PatientAccountService;
import com.pharma.core.pharmaservices.physicianservices.PhysicianAccountService;
import com.pharma.core.pharmaservices.physicianservices.PhysicianGroupService;
import com.pharma.core.repository.patient.PatientAccountRespository;
import com.pharma.core.repository.patient.PatientPhysiciansRepository;
import com.pharma.core.repository.patient.PatientUpdateHistoryRepository;
import com.pharma.core.repository.pharmacygroup.GroupDirectorAccountRespository;
import com.pharma.core.repository.pharmacygroup.GroupMasterRespository;
import com.pharma.core.repository.physician.PhysicianAccountRespository;
import com.pharma.core.repository.physician.PhysicianAssistantAccountRespository;
import com.pharma.core.repository.physician.PhysicianAssistantPhysicianRepository;
import com.pharma.core.repository.physician.PhysicianGroupRepository;
import com.pharma.core.repository.physician.PhysicianUpdateHistoryRepository;
import com.pharma.core.util.PharmacyUtil;
/**
 * This is an implementation class that loads and stores the group master data
 *
 */
@Service("groupMasterService")
@Transactional
public class GroupMasterServiceImpl implements GroupMasterService  {
	
	@Autowired
	GroupMasterRespository groupMasterAccount;
	
	@Autowired
	GroupDirectorAccountRespository groupDirectorRepo;
	
	@Autowired
	PhysicianGroupService phyGroupService;
	
	@Autowired
	PhysicianGroupRepository phyGroupRepo;
	
	@Autowired
	PhysicianAccountRespository phyAccRepo;
	
	@Autowired
	PhysicianAssistantAccountRespository assistantRepo; 
	
	@Autowired
	PhysicianAssistantPhysicianRepository assistantPhysicianRepo;
	
	@Autowired
	GroupMasterFileUploadService fileUploadService;
	
	@Autowired
	PatientAccountRespository patientRepo;
	
	@Autowired
	PatientPhysiciansRepository patientPhysicianRepo;

	@Autowired
	PhysicianUpdateHistoryRepository updatedHistoryRepo;
	
	@Autowired
	PhysicianAccountService physicianService;
	
	@Autowired
	PatientUpdateHistoryRepository updatedPatientHistoryRepo;
	
	@Autowired
	PatientAccountService patientService;
	
	
	public GroupMaster saveGroupMaster(GroupMasterForm form, LoginForm loggedInUser,CommonsMultipartFile photoFile,String rootFilePath) {
		GroupMaster acc=null;
		String previousGroupName = "";
		if(form.getGroupId() > 0) {
			acc = groupMasterAccount.findOne(form.getGroupId());
			previousGroupName = acc.getGroupName();
		}else {
			acc= new GroupMaster();
			acc.setCreatedUser(loggedInUser.getType());
			acc.setCreatedBy(loggedInUser.getUserid());
			acc.setCreatedDate(PharmacyUtil.getCurrentDateAndTime());
			acc.setDefaultgroup("N");
			acc.setNewgroup("N");
		}
		
		acc.setLastUpdatedUser(loggedInUser.getType());
		acc.setGroupName(form.getGroupName());
		acc.setContactName(form.getContactName());
		acc.setEmail(form.getEmail());
		acc.setMobile(form.getMobile());
		acc.setStatus(form.getStatus());
		
		acc.setLastUpdatedBy(loggedInUser.getUserid());
		acc.setLastUpdatedDate(PharmacyUtil.getCurrentDateAndTime());
		
		
		GroupMaster grp = groupMasterAccount.save(acc);
		
		if (grp.getStatus().equalsIgnoreCase(PharmacyUtil.statusInactive)) {
			List<PhysicianGroup> gpList = phyGroupService.getByGroup(grp.getId());
			if (gpList.size() > 0) {
				for (PhysicianGroup g: gpList) {
					g.setStatus(PharmacyUtil.statusInactive);
					g.setUpdatedBy(loggedInUser.getUserid());
					g.setUpdatedDate(PharmacyUtil.getCurrentTimeStamp());
					g.setUpdatedUserType(loggedInUser.getType());
					
					phyGroupRepo.save(g);
				}
			}
		} else {
			List<PhysicianGroup> gpList = phyGroupService.getByGroup(grp.getId());
			if (gpList.size() > 0) {
				for (PhysicianGroup g: gpList) {
					g.setStatus(PharmacyUtil.statusActive);
					g.setUpdatedBy(loggedInUser.getUserid());
					g.setUpdatedDate(PharmacyUtil.getCurrentTimeStamp());
					g.setUpdatedUserType(loggedInUser.getType());
					
					phyGroupRepo.save(g);
				}
			}
		}
		
		// Group name in physician field updated if group name changed
		if (!previousGroupName.equalsIgnoreCase(form.getGroupName())) {
			List<PhysicianAccount> phyList = phyAccRepo.getPhysicianByGroup(grp.getId());
			String gpName = "";
			
			for (PhysicianAccount p: phyList) {
				gpName = p.getPhysicianNameWithGroupName().replace(" - " +previousGroupName, "");
				gpName = gpName + " - " + form.getGroupName();
				p.setPhysicianNameWithGroupName(gpName);
				
				phyAccRepo.save(p);
			}
		}

		try {
			/*
			 * Uploaded Files saving process
			 */
			if (photoFile.getSize() > 0) {
				String folderName="group";
				String photoFileName="logo_"+grp.getId();
				boolean isUploaded=PharmacyUtil.userPhotoUpload(photoFile, folderName, photoFileName, rootFilePath, grp.getId(), "");
				if (isUploaded){
					String oname=photoFile.getOriginalFilename().toLowerCase();
					String extension = oname.substring(oname.lastIndexOf('.') + 1, oname.length()).toLowerCase();

					StringBuffer uploadedFile = new StringBuffer(PharmacyUtil.getFileUploadPath(rootFilePath, folderName, grp.getId(), ""));
					uploadedFile.append( File.separator + photoFileName + "." + extension);
					
					fileUploadService.logoFileSave(grp.getId(), photoFile, uploadedFile.toString(), loggedInUser);				
					
					//System.out.println("Group Logo uploaded ok");
				} else {
					//System.out.println("Group Logo not uploaded");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return grp;
	}
	
	
	
	
	public GroupMasterForm getGroupMasterData(int id, String filepath) {
		
		GroupMaster acc = groupMasterAccount.findOne(id);
		
		GroupMasterForm pro = new GroupMasterForm();
		List<GroupDirector> gdObj=(groupDirectorRepo.findNameByGroupId(acc.getId()));
		
		pro.setGroupId(acc.getId());
		pro.setGroupName(acc.getGroupName());
		pro.setContactName(acc.getContactName()); 
		pro.setEmail(acc.getEmail());
		pro.setMobile(acc.getMobile()); 
		pro.setStatus(acc.getStatus());

		
		pro.setCreatedBy(acc.getCreatedBy());
		pro.setCreatedUser(acc.getCreatedUser());
		pro.setCreatedDate(acc.getCreatedDate());
		
		pro.setLastUpdatedBy(acc.getLastUpdatedBy());
		pro.setLastUpdatedUser(acc.getLastUpdatedUser());
		pro.setLastUpdatedDate(acc.getLastUpdatedDate());
		
		if(gdObj.size()==0)
			pro.setGroupDirectorName("");
		else {
			StringBuilder sb = new StringBuilder();
			for (GroupDirector g: gdObj) {
				sb.append(g.getGroupDirectorName()).append(", ");
			}
			pro.setGroupDirectorName(sb.toString().substring(0, sb.toString().length()-2) );
		}

		if (!"".equalsIgnoreCase(filepath)) {
			pro.setLogoFile(fileUploadService.logoNameByGroupId(id, filepath) );
		}
		
		return pro;
	}

	public String getGroupMasterDataList(int draw, int start, int length, String searchTerm, int orderColumn, String orderDir, String groupName, String status) {

		try {
			if (groupName == null) groupName = "";
			if (status == null || status == "") status = "";
			
			int total = 0;
			int pageNumber = 0;
			Pageable page = null;
			Page<GroupMaster> groupMasterList = null;
			
			Direction dir = Direction.ASC;
			if (orderDir.equalsIgnoreCase("desc"))
				dir = Direction.DESC;

			try {
				if ((groupName.equals("")  ||  groupName.length()==0) && (status.equals("") || status.length()==0))
				{
					total = groupMasterAccount.findByAllStatus();
				}else if (!groupName.equals("")  && groupName.length()>0 && !status.equals("") && status.length()>0) {
					total = (int) groupMasterAccount.findByGroupNameAndStatus(groupName,status);
				} else {
					if (!groupName.equals("")  && groupName.length()>0)
						total = groupMasterAccount.findByPharmacyGroupName(groupName);
					else if (!status.equals("") && status.length()>0)
						total = groupMasterAccount.findByStatus(status);
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
			pageNumber = start / length;
			
			switch (orderColumn) {
				case 1: {
					page = new PageRequest(pageNumber, length, dir, "permission");
					break;
				}
				case 2: {
					page = new PageRequest(pageNumber, length, dir, "isActive");
					break;
				}
				default: {
					page = new PageRequest(pageNumber, length, dir, "id");
					break;
				}
			}
			
			try {
				if ((groupName.equals("")  ||  groupName.length()==0) && (status.equals("") || status.length()==0))
				{
					groupMasterList = groupMasterAccount.findByAllStatus(page);
				}else if (!groupName.equals("")  && groupName.length()>0 && !status.equals("") && status.length()>0) {
					groupMasterList = groupMasterAccount.findByGroupNameAndStatus(groupName,status,page);
				} else {
					if (!groupName.equals("")  && groupName.length()>0)
						groupMasterList = groupMasterAccount.findByPharmacyGroupName(groupName,page);
					else if (!status.equals("") && status.length()>0)
						groupMasterList = groupMasterAccount.findByStatus(status,page);
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
			List<GroupMasterForm> groupMastObjList = new ArrayList<GroupMasterForm>();
			if (groupMasterList != null && groupMasterList.getSize()>0 ) {
				for(GroupMaster a: groupMasterList) {
					GroupMasterForm obj = new GroupMasterForm();
					List<GroupDirector> gdObj = groupDirectorRepo.findNameByGroupId(a.getId());
					
					obj.setGroupId(a.getId());
					obj.setGroupName(a.getGroupName());
					obj.setContactName(a.getContactName());
					obj.setEmail(a.getEmail());
					obj.setMobile(a.getMobile());
					obj.setStatus(a.getStatus());

					if(gdObj.size()==0)
						obj.setGroupDirectorName("");
					else {
						StringBuilder sb = new StringBuilder();
						for (GroupDirector g: gdObj) {
							sb.append(g.getGroupDirectorName()).append(", ");
						}
						obj.setGroupDirectorName(sb.toString().substring(0, sb.toString().length()-2) );
					}				
					obj.setDT_RowId("row_" + a.getId());
					
					groupMastObjList.add(obj);
				}
			}
			
			GroupMasterJSonObj data = new GroupMasterJSonObj();
			data.setData(groupMastObjList);
			data.setDraw(draw);
			data.setRecordsFiltered(total);
			data.setRecordsFiltered(total);
			
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			String json2 = gson.toJson(data);	
			
			return json2;
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return "";
	}

	public boolean deleteClinicAccountById(int id , LoginForm loggedInUser) {
		boolean isDeleted = false;
		
		if (id > 0) {
			GroupMaster acc = null;
			acc = groupMasterAccount.findOne(id);
			
			acc.setStatus(PharmacyUtil.statusInactive);
			acc.setLastUpdatedBy(loggedInUser.getUserid());
			acc.setLastUpdatedUser(loggedInUser.getType());
			acc.setLastUpdatedDate( PharmacyUtil.getCurrentTimeStamp() );
			
			groupMasterAccount.save(acc);
			
			isDeleted = true;
		}
		
		return isDeleted;
	}
	
	
	public List<GroupMaster> getAllGroupMaster() {
		return groupMasterAccount.findByAll();
	}
	public List<GroupMaster> getAllGroupMaster(String status) {
		return groupMasterAccount.findByGroupStatus(status);
	}
	public List<GroupMaster> getAllGroupMaster(String status,int groupId) {
		return groupMasterAccount.findByGroupStatus(status,groupId);
	}

	public List<GroupMaster> getAllOtherGroupMaster(String status,int groupId) {
		return groupMasterAccount.findByOtherGroupStatus(status,groupId);
	}
	
    public GroupMaster getGroupMasterDetails(int id) {
		return groupMasterAccount.findOne(id);
	}

    public GroupMaster getGroupMasterByEmailAndNotId(String email, int groupId) {
		return groupMasterAccount.findByEmailAndIdNot(email, groupId);
	}

	public GroupMaster getGroupMasterByEmail(String email) {
		return groupMasterAccount.findByEmail(email);
	}
	
	public GroupMaster getGroupMasterByGroupNameAndNotId(String groupName, int groupId) {
		return groupMasterAccount.findByGroupNameAndIdNot(groupName, groupId);
	}

	public GroupMaster getGroupMasterByGroupName(String groupName) {
		return groupMasterAccount.findByGroupName(groupName);
	}

	public List<GroupMaster> getByGroupMaster(int id) {
		return groupMasterAccount.findByGroupStatus(PharmacyUtil.statusActive, id);
	}




	public String getPhysicianDataByGroupId(int draw, int start, int length, String searchTerm, int orderColumn, String orderDir, 
			int groupId, String physicianName, String status) {
		
		if (physicianName == null) physicianName = "";
		if (status == null) status = "";
		
		int total = 0;
		int pageNumber = 0;
		Pageable page = null;
		Page<PhysicianAccount> physicianList = null;
		
		Direction dir = Direction.ASC;
		if (orderDir.equalsIgnoreCase("desc"))
			dir = Direction.DESC;
		
		try {
			if (groupId > 0) {
				if ("".equalsIgnoreCase(status))
					total = phyAccRepo.getPhysicianListByGroupId(groupId, physicianName).size();
				else
					total = phyAccRepo.getPhysicianListByGroupId(groupId, physicianName, status).size();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		pageNumber = start / length;
		
		switch (orderColumn) {
			case 1: {
				page = new PageRequest(pageNumber, length, dir, "physicianName");
				break;
			}
			case 4: {
				page = new PageRequest(pageNumber, length, dir, "status");
				break;
			}
			default: {
				page = new PageRequest(pageNumber, length, dir, "id");
				break;
			}
		}
		
		try {
			if (groupId > 0) {
				if ("".equalsIgnoreCase(status))
					physicianList = phyAccRepo.getPhysicianListByGroupId(groupId, physicianName, page);
				else
					physicianList = phyAccRepo.getPhysicianListByGroupId(groupId, physicianName, status, page);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		List<PhysicianProfile> phyObjList = new ArrayList<PhysicianProfile>();
		if (physicianList != null && physicianList.getSize() > 0 ) {
			for(PhysicianAccount a: physicianList) {
				PhysicianProfile obj = new PhysicianProfile();
				
				obj.setPhysicianId(a.getId());
				obj.setPhysicianName(a.getPhysicianName());
				obj.setEmail(a.getEmail());
				obj.setPhone(a.getPhone());
				obj.setStatus(a.getStatus());
				
				obj.setDT_RowId("row_" + a.getId());
				
				phyObjList.add(obj);
			}
		}

		PhysicianJSonObj data = new PhysicianJSonObj();
		data.setData(phyObjList);
		data.setDraw(draw);
		data.setRecordsFiltered(total);
		data.setRecordsFiltered(total);
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json2 = gson.toJson(data);
		
		return json2;
		
	}




	public String getPhysicianAssistantDataByGroupId(int draw, int start, int length, String searchTerm, int orderColumn, String orderDir, 
			int groupId, String assistantName, String status) {
		
		if (assistantName == null) assistantName = "";
		if (status == null) status = "";
		
		int total = 0;
		int pageNumber = 0;
		Pageable page = null;
		Page<Object[]> physicianAssistantList = null;
		
		Direction dir = Direction.ASC;
		if (orderDir.equalsIgnoreCase("desc"))
			dir = Direction.DESC;
		
		try {
			if (groupId > 0) {
				if ("".equalsIgnoreCase(status))
					total =  assistantRepo.getAssistantByGroupId(groupId, assistantName).size();
				else
					total =  assistantRepo.getAssistantByGroupId(groupId, assistantName, status).size();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		pageNumber = start / length;
		
		switch (orderColumn) {
			case 1: {
				page = new PageRequest(pageNumber, length, dir, "assistantName");
				break;
			}
			case 2: {
				page = new PageRequest(pageNumber, length, dir, "physicianName");
				break;
			}
			case 5: {
				page = new PageRequest(pageNumber, length, dir, "status");
				break;
			}
			default: {
				page = new PageRequest(pageNumber, length, dir, "id");
				break;
			}
		}
		
		try {
			if (groupId > 0) {
				if ("".equalsIgnoreCase(status))
					physicianAssistantList = assistantRepo.getAssistantByGroupId(groupId, assistantName, page);
				else
					physicianAssistantList = assistantRepo.getAssistantByGroupId(groupId, assistantName, status, page);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		List<PhysicianAssistantRegistration> assistantList = new ArrayList<PhysicianAssistantRegistration>();
		if (physicianAssistantList != null && physicianAssistantList.getSize() > 0 ) {
			List<Object[]> outputList = physicianAssistantList.getContent();
			StringBuilder selectedPhysicianId = new StringBuilder();
			
			// a.id, a.assistantName, p.physicianName, a.email, a.phone, a.status
			for (int i=0; i<outputList.size(); i++) {
				PhysicianAssistantRegistration assForm = new PhysicianAssistantRegistration();
				
				if (outputList.get(i)[0] != null)
					assForm.setAssistantId((Integer) outputList.get(i)[0]);
				else
					assForm.setAssistantId(0);
				
				if (outputList.get(i)[1] != null)
					assForm.setAssistantName((String) outputList.get(i)[1]);
				else
					assForm.setAssistantName("");
				
				selectedPhysicianId = new StringBuilder();
				
				List<PhysicianAssistantPhysician> phyList = assistantPhysicianRepo.findByphysicianAssistantIdAndDelFlag(assForm.getAssistantId(), PharmacyUtil.deleteFlagNo);
				if (phyList.size() > 0) {
					for (PhysicianAssistantPhysician g: phyList) {
						if (g.getPhysicianId() > 0) {
							selectedPhysicianId.append( phyAccRepo.findOne(g.getPhysicianId()).getPhysicianName() ).append(", ");
						}
					}
				}
				
				if (selectedPhysicianId.length() > 0)
					assForm.setPhysicianName(selectedPhysicianId.toString().substring(0, selectedPhysicianId.length()-2) );
				else
					assForm.setPhysicianName("");
				
				if (outputList.get(i)[3] != null)
					assForm.setEmail((String) outputList.get(i)[3]);
				else
					assForm.setEmail("");
				
				if (outputList.get(i)[4] != null)
					assForm.setPhone((String) outputList.get(i)[4]);
				else
					assForm.setPhone("");
				
				if (outputList.get(i)[5] != null)
					assForm.setStatus((String) outputList.get(i)[5]);
				else
					assForm.setStatus("");
				
				assForm.setDT_RowId("row_" + assForm.getAssistantId());
				
				assistantList.add(assForm);
			}
		}

		PhysicianAssistantJSonObj data = new PhysicianAssistantJSonObj();
		data.setData(assistantList);
		data.setDraw(draw);
		data.setRecordsFiltered(total);
		data.setRecordsFiltered(total);
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json2 = gson.toJson(data);
		
		return json2;
		
	}




	public String getPatientDataByGroupId(int draw, int start, int length, String searchTerm, int orderColumn, String orderDir, 
			int groupId, String patientName, String status) {
		
		if (patientName == null) patientName = "";
		if (status == null) status = "";
		
		int total = 0;
		int pageNumber = 0;
		Pageable page = null;
		Page<Object[]> patientList = null;
		
		Direction dir = Direction.ASC;
		if (orderDir.equalsIgnoreCase("desc"))
			dir = Direction.DESC;
		
		try {
			if (groupId > 0) {
				if ("".equalsIgnoreCase(status))
					total =  patientRepo.getPatientByGroupId(groupId, patientName).size();
				else
					total =  patientRepo.getPatientByGroupId(groupId, patientName, status).size();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		pageNumber = start / length;
		
		switch (orderColumn) {
			case 1: {
				page = new PageRequest(pageNumber, length, dir, "patientName");
				break;
			}
			case 2: {
				page = new PageRequest(pageNumber, length, dir, "physicianName");
				break;
			}
			case 5: {
				page = new PageRequest(pageNumber, length, dir, "status");
				break;
			}
			default: {
				page = new PageRequest(pageNumber, length, dir, "id");
				break;
			}
		}
		
		try {
			if (groupId > 0) {
				if ("".equalsIgnoreCase(status))
					patientList = patientRepo.getPatientByGroupId(groupId, patientName, page);
				else
					patientList = patientRepo.getPatientByGroupId(groupId, patientName, status, page);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		List<PatientAccountForm> patientFormList = new ArrayList<PatientAccountForm>();
		if (patientList != null && patientList.getSize() > 0 ) {
			List<Object[]> outputList = patientList.getContent();
			StringBuilder selectedPhysicianId = new StringBuilder();
			
			//  p.id, p.patientName,p.status,p.email,p.phone, phy.physicianName
		
			for (int i=0; i<outputList.size(); i++) {
				PatientAccountForm assForm = new PatientAccountForm();
				
				if (outputList.get(i)[0] != null)
					assForm.setPatientId((Integer) outputList.get(i)[0]);
				else
					assForm.setPatientId(0);
				
				if (outputList.get(i)[1] != null)
					assForm.setPatientName((String) outputList.get(i)[1]);
				else
					assForm.setPatientName("");
				
				if (outputList.get(i)[2] != null)
					assForm.setStatus((String) outputList.get(i)[2]);
				else
					assForm.setStatus("");
				
				if (outputList.get(i)[3] != null)
					assForm.setEmail((String) outputList.get(i)[3]);
				else
					assForm.setEmail("");
				
				if (outputList.get(i)[4] != null)
					assForm.setPhone((String) outputList.get(i)[4]);
				else
					assForm.setPhone("");
				
				selectedPhysicianId = new StringBuilder();
	
				List<PatientPhysicians> phyList = patientPhysicianRepo.findByPatientIdAndDelFlag(assForm.getPatientId(), PharmacyUtil.deleteFlagNo);
				if (phyList.size() > 0) {
					for (PatientPhysicians g: phyList) {
						if (g.getPhysicianId() > 0) {
							selectedPhysicianId.append( phyAccRepo.findOne(g.getPhysicianId()).getPhysicianName() ).append(", ");
						}
					}
				}
				
				if (selectedPhysicianId.length() > 0)
					assForm.setPhysicianName(selectedPhysicianId.toString().substring(0, selectedPhysicianId.length()-2) );
				else
					assForm.setPhysicianName("");
				
				assForm.setDT_RowId("row_" + assForm.getPatientId());
				
				patientFormList.add(assForm);
			}
		}

		PatientAccountJSonObj data = new PatientAccountJSonObj();
		data.setData(patientFormList);
		data.setDraw(draw);
		data.setRecordsFiltered(total);
		data.setRecordsFiltered(total);
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json2 = gson.toJson(data);
		
		return json2;
	}

	
	public boolean changePhysicianGroup(int physicianId, int newGroupId, int groupId, LoginForm loggedInUser) {
		boolean isSaved = false;

		try {
			if (physicianId > 0) {
				PhysicianGroup phyGrp = phyGroupRepo.findRecordByPhysicianIdAndGroupId(physicianId, groupId);
				
				if (phyGrp != null) {
					phyGrp.setGroupId(newGroupId);
					
					phyGrp.setUpdatedBy(loggedInUser.getUserid());
					phyGrp.setUpdatedDate(PharmacyUtil.getCurrentTimeStamp());
					phyGrp.setUpdatedUserType(loggedInUser.getType());
					
					phyGroupRepo.save(phyGrp);

					// update Group name with physician name
					PhysicianAccount phy = phyAccRepo.findOne(physicianId);
					if (phy != null) {
						phy.setPhysicianNameWithGroupName( phy.getPhysicianName() + " - " + groupMasterAccount.findOne(newGroupId).getGroupName() );
						
						phy.setLastUpdatedBy(loggedInUser.getUserid());
						phy.setLastUpdatedDate(PharmacyUtil.getCurrentTimeStamp());
						phy.setLastUpdatedUser(loggedInUser.getType());

						phyAccRepo.save(phy);
						
						physicianService.approvalPhysicianNotification(physicianId, loggedInUser);
					}
					isSaved = true;
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return isSaved;
	}
	
	
	public boolean changeAssistantGroup(int assistantId, int newGroupId, int groupId, LoginForm loggedInUser) {
		boolean isSaved = false;
		
		try {
			if (assistantId > 0) {
				PhysicianAssistantAccount assObj = assistantRepo.findByIdAndGroupId(assistantId, groupId);
				
				if (assObj != null) {
					assObj.setGroupId(newGroupId);
					
					assObj.setLastUpdatedBy(loggedInUser.getUserid());
					assObj.setLastUpdatedDate(PharmacyUtil.getCurrentTimeStamp());
					assObj.setLastUpdatedUser(loggedInUser.getType());
					
					assistantRepo.save(assObj);

					isSaved = true;
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return isSaved;
	}
	
	public List<PhysicianAccount> getPhysicianListForGroupChange() {
		return phyAccRepo.getPhysicianListForGroupChange();
	}
	
	public List<PhysicianAccount> getPhysicianListForGroupChange(int groupId) {
		return phyAccRepo.getPhysicianListForGroupChange(groupId);
	}
	
	
	public boolean changePatientPhysicianInGroupMaster(int patientId, int newPhysicianId, int groupId, LoginForm loggedInUser) {
		boolean isSaved = false;
		
		try {
			if (patientId > 0) {
				// Deleted existing records
				List<PatientPhysicians> patientPhyList =  patientPhysicianRepo.findByPatientId(patientId);
				if (patientPhyList.size() > 0) {
					for (PatientPhysicians c: patientPhyList) {
						c.setDelFlag(PharmacyUtil.deleteFlagYes);
						c.setUpdatedBy(loggedInUser.getUserid());
						c.setUpdatedDate(PharmacyUtil.getCurrentTimeStamp());
						c.setUpdatedUserType(loggedInUser.getType());
						patientPhysicianRepo.save(c);
					}
				}
			
				PatientPhysicians obj = null;
				obj = patientPhysicianRepo.findByPatientIdAndPhysicianId(patientId, newPhysicianId);
				if (obj == null) {
					obj = new PatientPhysicians();
					obj.setPatientId(patientId);
					obj.setCreatedBy(loggedInUser.getUserid());
					obj.setCreatedDate(PharmacyUtil.getCurrentTimeStamp());
					obj.setCreatedUserType(loggedInUser.getType());
				} 
				obj.setPhysicianId(newPhysicianId);
				obj.setDelFlag(PharmacyUtil.deleteFlagNo);
				
				obj.setUpdatedBy(loggedInUser.getUserid());
				obj.setUpdatedDate(PharmacyUtil.getCurrentTimeStamp());
				obj.setUpdatedUserType(loggedInUser.getType());
				
				patientPhysicianRepo.save(obj);
				
				patientService.approvalPatientNotification(patientId, loggedInUser);
				
				isSaved = true;
			}
		} catch(Exception e) {
			e.printStackTrace();
		}

		return isSaved;
	}
	
	
	public boolean changePhysicianStatusFromGroupMaster(int physicianId, String status, LoginForm loggedInUser, HttpServletRequest request) {
		boolean isSaved = false;
		
		try {
			if (physicianId > 0) {
				PhysicianAccount acc = phyAccRepo.findOne(physicianId);
				if (acc != null) {
					if ("Denied".equalsIgnoreCase(status)) {
						acc.setStatus( PharmacyUtil.statusDenied );
						
						acc.setDeniedBy(loggedInUser.getUserid());
						acc.setDeniedUser(loggedInUser.getType());
						acc.setDeniedDate(PharmacyUtil.getCurrentTimeStamp());
					} else {
						acc.setStatus( PharmacyUtil.statusApproved);
						
						acc.setApprovedBy(loggedInUser.getUserid());
						acc.setApprovedUser(loggedInUser.getType());
						acc.setApprovedDate(PharmacyUtil.getCurrentTimeStamp());
					}
					acc.setLastUpdatedBy(loggedInUser.getUserid());
					acc.setLastUpdatedDate(PharmacyUtil.getCurrentTimeStamp());
					acc.setLastUpdatedUser(loggedInUser.getType());
	
					phyAccRepo.save(acc);
				

				
					// Updating Physician Update History table
					PhysicianUpdateHistory history = new PhysicianUpdateHistory();
					
					history.setPhysicianId(physicianId);
					history.setUpdatedDate(PharmacyUtil.getCurrentTimeStamp());
					history.setUpdatedBy(loggedInUser.getUserid());
					history.setUpdatedUser(loggedInUser.getType());
					history.setDescription("Physician status changes from group master.");
					
					if (PharmacyUtil.statusApproved.equalsIgnoreCase(acc.getStatus())) {
						history.setApprovedBy(loggedInUser.getUserid());
						history.setApprovedDate(PharmacyUtil.getCurrentTimeStamp());
						history.setApprovedUser(loggedInUser.getType());
					}
					updatedHistoryRepo.save(history);
					
					
					physicianService.approvalPhysicianNotification(physicianId, loggedInUser);
				}
			}
			isSaved = true;
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return isSaved;
	}
	

	
	public boolean changeAssistantStatusFromGroupMaster(int assistantId, String status, LoginForm loggedInUser, HttpServletRequest request) {
		boolean isSaved = false;
		
		try {
			if (assistantId > 0) {
				PhysicianAssistantAccount acc = assistantRepo.findOne(assistantId);
				if (acc != null) {
					if ("Inactive".equalsIgnoreCase(status)) {
						acc.setStatus( PharmacyUtil.statusInactive );
					} else {
						acc.setStatus( PharmacyUtil.statusActive);
					}
					acc.setLastUpdatedBy(loggedInUser.getUserid());
					acc.setLastUpdatedDate(PharmacyUtil.getCurrentTimeStamp());
					acc.setLastUpdatedUser(loggedInUser.getType());
	
					assistantRepo.save(acc);
				}
			}
			isSaved = true;
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return isSaved;
	}
	
	
	public boolean changePatientStatusFromGroupMaster(int patientId, String status, LoginForm loggedInUser, HttpServletRequest request) {
		boolean isSaved = false;
		
		try {
			if (patientId > 0) {
				PatientAccount acc = patientRepo.findOne(patientId);
				if (acc != null) {
					if ("Denied".equalsIgnoreCase(status)) {
						acc.setStatus( PharmacyUtil.statusDenied );
						
						acc.setDeniedBy(loggedInUser.getUserid());
						acc.setDeniedUser(loggedInUser.getType());
						acc.setDeniedDate(PharmacyUtil.getCurrentTimeStamp());
					} else {
						acc.setStatus( PharmacyUtil.statusApproved);
						
						acc.setApprovedBy(loggedInUser.getUserid());
						acc.setApprovedUser(loggedInUser.getType());
						acc.setApprovedDate(PharmacyUtil.getCurrentTimeStamp());
					}
					acc.setLastUpdatedBy(loggedInUser.getUserid());
					acc.setLastUpdatedDate(PharmacyUtil.getCurrentTimeStamp());
					acc.setLastUpdatedUser(loggedInUser.getType());
	
					patientRepo.save(acc);
				

				
					// Updating Patient Update History table
					PatientUpdateHistory history = new PatientUpdateHistory();
					
					history.setPatientId(patientId);
					history.setUpdatedDate(PharmacyUtil.getCurrentTimeStamp());
					history.setUpdatedBy(loggedInUser.getUserid());
					history.setUpdatedUser(loggedInUser.getType());
					history.setDescription("Patient status changes from group master.");
					
					if (PharmacyUtil.statusApproved.equalsIgnoreCase(acc.getStatus())) {
						history.setApprovedBy(loggedInUser.getUserid());
						history.setApprovedDate(PharmacyUtil.getCurrentTimeStamp());
						history.setApprovedUser(loggedInUser.getType());
					}
					updatedPatientHistoryRepo.save(history);
					
					patientService.approvalPatientNotification(patientId, loggedInUser);
				}
			}
			isSaved = true;
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return isSaved;
	}
	

    /**
     * Do you wish to re-assign the Group for all Physicians, Assistants, Patients of this Group to a different Group? 
     * and deactivates the current group in normal save
     * @param otherGroupId
     * @param groupId
     * @param loggedInUser
     */
    public void reAssignGroup( Integer otherGroupId, Integer groupId,LoginForm loggedInUser)
    {
        try
        {
        	
        		//System.out.println("reAssignGroup :: otherGroupId ==="+otherGroupId+" :: Current Group Id :: "+groupId);
        		
				int reassignedRow= groupMasterAccount.reAssignPhysicianGroup(otherGroupId,loggedInUser.getUserid(),PharmacyUtil.getCurrentTimeStamp(),loggedInUser.getType(), groupId);
				//System.out.println("Physician reassignedRow ======="+reassignedRow);
				
				reassignedRow= groupMasterAccount.reAssignPhysicianAssistantGroup(otherGroupId,loggedInUser.getUserid(),PharmacyUtil.getCurrentTimeStamp(),loggedInUser.getType(), groupId);
				//System.out.println("PhysicianAssistant reassignedRow ======="+reassignedRow);
				
				reassignedRow= groupMasterAccount.reAssignPatientGroup(otherGroupId,loggedInUser.getUserid(),PharmacyUtil.getCurrentTimeStamp(),loggedInUser.getType(), groupId);
				//System.out.println("Patient reassignedRow ======="+reassignedRow);
	    	
        }
        catch (Exception e)
        {
        	e.printStackTrace();
          
        }
    }
    
   /**
    * All Group Directors, Physicians and Assistants of this Group will be DeActivated(Denied). 
    * If you wish to re-assign them to different Groups go to lists shown an re-assign
    * @param groupId
    * @param loggedInUser
    */
    public void deActivateGroup(Integer groupId,LoginForm loggedInUser)
    {
        try
        {
        	
        		//System.out.println("deActivateGroup :: Current Group Id :: "+groupId);
        		
				int deactivatedRow= groupMasterAccount.deActivateGroupDirector(PharmacyUtil.statusInactive,loggedInUser.getUserid(),loggedInUser.getType(),PharmacyUtil.getCurrentTimeStamp(), groupId);
				//System.out.println("GroupDirector deactivatedRow ======="+deactivatedRow);
				
				deactivatedRow= groupMasterAccount.deActivatePhysician(PharmacyUtil.statusDenied,loggedInUser.getUserid(),loggedInUser.getType(),PharmacyUtil.getCurrentTimeStamp(),loggedInUser.getUserid(),loggedInUser.getType(),PharmacyUtil.getCurrentTimeStamp(), groupId);
				//System.out.println("Physician deactivatedRow ======="+deactivatedRow);
				
				deactivatedRow= groupMasterAccount.deActivatePhysicianAssistant(PharmacyUtil.statusInactive,loggedInUser.getUserid(),loggedInUser.getType(),PharmacyUtil.getCurrentTimeStamp(),loggedInUser.getUserid(),PharmacyUtil.getCurrentTimeStamp(), groupId);
				//System.out.println("PhysicianAssistant deactivatedRow ======="+deactivatedRow);
				
				deactivatedRow= groupMasterAccount.deActivatePatientGroup(PharmacyUtil.deleteFlagYes,loggedInUser.getUserid(),PharmacyUtil.getCurrentTimeStamp(),loggedInUser.getType(),groupId);
				//System.out.println("Patient deactivatedRow ======="+deactivatedRow);
				
		
        }
        catch (Exception e)
        {
        	e.printStackTrace();
          
        }
    }
}

