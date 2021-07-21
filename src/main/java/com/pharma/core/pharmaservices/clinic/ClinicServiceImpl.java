package com.pharma.core.pharmaservices.clinic;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pharma.core.formBean.LoginForm;
import com.pharma.core.formBean.clinic.ClinicForm;
import com.pharma.core.formBean.clinic.ClinicFormJSonObj;
import com.pharma.core.formBean.physician.PhysicianAssistantJSonObj;
import com.pharma.core.formBean.physician.PhysicianAssistantRegistration;
import com.pharma.core.formBean.physician.PhysicianJSonObj;
import com.pharma.core.formBean.physician.PhysicianProfile;
import com.pharma.core.model.clinic.Clinic;
import com.pharma.core.model.pharmacygroup.GroupMaster;
import com.pharma.core.model.physician.PhysicianAccount;
import com.pharma.core.model.physician.PhysicianAssistantPhysician;
import com.pharma.core.pharmaservices.notifications.NotificationService;
import com.pharma.core.repository.clinic.ClinicRepository;
import com.pharma.core.repository.pharmacygroup.GroupDirectorAccountRespository;
import com.pharma.core.repository.pharmacygroup.GroupMasterRespository;
import com.pharma.core.repository.physician.PhysicianAccountRespository;
import com.pharma.core.repository.physician.PhysicianAssistantAccountRespository;
import com.pharma.core.repository.physician.PhysicianAssistantPhysicianRepository;
import com.pharma.core.util.PharmacyUtil;
/**
 * This is an implementation class that loads and stores the clinic account data
 *
 */
@Service("pharmaClinicService")
@PropertySource("classpath:webService.properties")
public class ClinicServiceImpl implements ClinicService {

	@Autowired
	ClinicRepository clinicRepo;
	
	@Autowired
	private Environment env;
	
	@Autowired
	GroupMasterRespository groupMasterRepo;
	
	@Autowired
	GroupDirectorAccountRespository groupDirRepo;

	@Autowired
	PhysicianAssistantAccountRespository assistantRepo; 
	
	@Autowired
	PhysicianAssistantPhysicianRepository assistantPhysicianRepo;

	@Autowired
	PhysicianAccountRespository physicianRepo;

	@Autowired
	PhysicianAccountRespository phyAccRepo;
	
	@Autowired
	NotificationService notificationService;
	
	public List<Clinic> getClinicList(boolean isActive) {
		List<Clinic> clinicList = null;
		
		if (isActive)
			clinicList = clinicRepo.findByStatus(PharmacyUtil.statusActive);
		else
			clinicList = clinicRepo.findAll();
		
		return clinicList;
	}

	public List<Clinic> getClinicList(int id) {
		List<Clinic> clinicList = null;
		if (id > 0)
			clinicList = clinicRepo.findByStatusAndId(PharmacyUtil.statusActive, id);
		else
			clinicList = clinicRepo.findByStatus(PharmacyUtil.statusActive);
		return clinicList;
	}

	public String getClinicDataList(int draw, int start, int length, String searchTerm, int orderColumn, String orderDir,
			String clinicName, String state, String status, String email, int groupName) {
		
		try {
			if (clinicName == null)  clinicName = "";
			if (state == null)  state = "";
			if (status == null)  status = "";
			if (email == null)  email = "";
			
			int total = 0;
			int pageNumber = 0;
			Pageable page = null;
			Page<Clinic> clinicList = null;
			
			Direction dir = Direction.ASC;
			if (orderDir.equalsIgnoreCase("desc"))
				dir = Direction.DESC;
			
			try {
				if ("".equalsIgnoreCase(clinicName) && "".equalsIgnoreCase(email) && "".equalsIgnoreCase(state) && "".equalsIgnoreCase(status) && groupName==0 ) {
					total = clinicRepo.findByNotStatus().size();
				} else {
					if (groupName > 0) {
						if ("".equalsIgnoreCase(state)) {
							if ("".equalsIgnoreCase(status))
								total = clinicRepo.getAllSearchTotal(clinicName, email, groupName);
							else
								total = clinicRepo.getSearchTotal(clinicName, email, status,groupName);
						} else {
							if ("".equalsIgnoreCase(status))
								total = clinicRepo.getAllSearchTotal(clinicName, email, state, groupName);
							else
								total = clinicRepo.getSearchTotal(clinicName, email, state, status,groupName);
						}
					} else {
						if ("".equalsIgnoreCase(state)) {
							if ("".equalsIgnoreCase(status))
								total = clinicRepo.getAllSearchTotal(clinicName, email);
							else
								total = clinicRepo.getSearchTotal(clinicName, email, status);
						} else {
							if ("".equalsIgnoreCase(status))
								total = clinicRepo.getAllSearchTotal(clinicName, email, state);
							else
								total = clinicRepo.getSearchTotal(clinicName, email, state, status);
						}

					}
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
				if ("".equalsIgnoreCase(clinicName) && "".equalsIgnoreCase(email) && "".equalsIgnoreCase(state) && "".equalsIgnoreCase(status) && groupName==0) {
					clinicList = clinicRepo.findByNotStatus(page);
				} else {
					if (groupName > 0) {
						if ("".equalsIgnoreCase(state)) {
							if ("".equalsIgnoreCase(status))
								clinicList = clinicRepo.getAllSearchRecords(clinicName, email,groupName, page);
							else
								clinicList = clinicRepo.getSearchRecords(clinicName, email, status,groupName, page);
						} else {
							if ("".equalsIgnoreCase(status))
								clinicList = clinicRepo.getAllSearchRecords(clinicName, email, state, groupName, page);
							else
								clinicList = clinicRepo.getSearchRecords(clinicName, email, state, status,groupName, page);
						}	
					} else {
						if ("".equalsIgnoreCase(state)) {
							if ("".equalsIgnoreCase(status))
								clinicList = clinicRepo.getAllSearchRecords(clinicName, email,  page);
							else
								clinicList = clinicRepo.getSearchRecords(clinicName, email, status, page);
						} else {
							if ("".equalsIgnoreCase(status))
								clinicList = clinicRepo.getAllSearchRecords(clinicName, email, state, page);
							else
								clinicList = clinicRepo.getSearchRecords(clinicName, email, state, status, page);
						}
					}
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
			
			StringBuilder fullAddress = new StringBuilder();
			List<ClinicForm> clinicObjList = new ArrayList<ClinicForm>();
			if (clinicList != null && clinicList.getSize() > 0 ) {
				for(Clinic a: clinicList) {
					ClinicForm obj = new ClinicForm();
					obj.setClinicId(a.getId());
					obj.setClinicName(a.getClinicName());
					obj.setStatus(a.getStatus());
					obj.setPhone(a.getPhone());
					obj.setEmail(a.getEmail());
					obj.setFax(a.getFax());
					
					obj.setAddress(a.getAddress());
					obj.setCity(a.getCity());
					if (!"0".equalsIgnoreCase(a.getState()))
						obj.setState(a.getState());
					else
						obj.setState("");
					
					obj.setZipCode(a.getZipCode());
					obj.setGroupId(a.getGroupId());
					
					if (a.getGroupId() > 0)
						obj.setGroupName(  groupMasterRepo.findOne(obj.getGroupId()).getGroupName() );
					else 
						obj.setGroupName("");
					
					obj.setLocation(a.getLocation());
					obj.setContactName(a.getContactName());
					
					fullAddress = new StringBuilder();
					if (a.getAddress() != null && !"".equalsIgnoreCase(a.getAddress()) )
						fullAddress.append(a.getAddress()).append(", ");
					if (a.getCity() != null && !"".equalsIgnoreCase(a.getCity()))
						fullAddress.append(a.getCity()).append(", ");
					if (a.getState() != null && !"".equalsIgnoreCase(a.getState()))
						fullAddress.append(a.getState()).append(", ");
					if (a.getZipCode() != null && !"".equalsIgnoreCase(a.getZipCode()))
						fullAddress.append(a.getZipCode()).append(", ");
					if(fullAddress!=null && fullAddress.length()>0)
						obj.setFullAddress( fullAddress.toString().trim().substring(0, fullAddress.toString().trim().length()-1) );
					else
						obj.setFullAddress( "");
					obj.setDT_RowId("row_" + a.getId());
					
					clinicObjList.add(obj);
				}
			}

			ClinicFormJSonObj data = new ClinicFormJSonObj();
			data.setData(clinicObjList);
			data.setDraw(draw);
			data.setRecordsFiltered(total);
			data.setRecordsFiltered(total);
			
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			String json2 = gson.toJson(data);
			
			return json2;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public ClinicForm getClinicAccountById(int id) {
		ClinicForm form = new ClinicForm();
		if (id > 0) {
			Clinic acc = clinicRepo.findOne(id);
			if (acc != null) {
				form.setAddress(acc.getAddress());
				form.setCity(acc.getCity());
				form.setClinicId(acc.getId());
				form.setClinicName(acc.getClinicName());
				form.setPhone(acc.getPhone());
				form.setState(acc.getState());
				form.setStatus(acc.getStatus());
				form.setZipCode(acc.getZipCode());
				form.setEmail(acc.getEmail());
				form.setLocation(acc.getLocation());
				form.setContactName(acc.getContactName());
				
				form.setCreatedBy(acc.getCreatedBy());
				form.setCreatedUser(acc.getCreatedUser());
				form.setCreatedDate(acc.getCreatedDate());
				
				form.setLastUpdatedBy(acc.getLastUpdatedBy());
				form.setLastUpdatedUser(acc.getLastUpdatedUser());
				form.setLastUpdatedDate(acc.getLastUpdatedDate());
				form.setGroupId(acc.getGroupId());
				form.setFax(acc.getFax());
			}
		}
		return form;
	}

	
	public Clinic saveClinic(ClinicForm form,LoginForm loggedInUser) {
		Clinic rec = null;
		try {
			String previousStatus="";
			int previousGroupId=0;
			Clinic acc = null;
			if (form.getClinicId() >0) {
				acc = clinicRepo.findOne(form.getClinicId());
				previousStatus=acc.getStatus();
				previousGroupId=acc.getGroupId();
			} else {
				acc = new Clinic();
				acc.setCreatedBy(loggedInUser.getUserid());
				acc.setCreatedUser(loggedInUser.getType());
				acc.setCreatedDate( PharmacyUtil.getCurrentTimeStamp() );
			}
			
			acc.setLastUpdatedBy(loggedInUser.getUserid());
			acc.setLastUpdatedUser(loggedInUser.getType());
			acc.setLastUpdatedDate( PharmacyUtil.getCurrentTimeStamp() );

			acc.setAddress(form.getAddress());
			acc.setCity(form.getCity());
			acc.setClinicName(form.getClinicName());
			acc.setPhone(form.getPhone());
			acc.setState(form.getState());
			acc.setStatus(form.getStatus());
			acc.setZipCode(form.getZipCode());
			acc.setEmail(form.getEmail());
			acc.setGroupId(form.getGroupId());
			acc.setLocation(form.getLocation());
			acc.setContactName(form.getContactName());
			acc.setFax(form.getFax());
			rec = clinicRepo.save(acc);
			
			
			int newGroupId=0;
			List<GroupMaster> groupMastList= groupMasterRepo.findByNewGroup();
			if (groupMastList.size() > 0) {
				for (GroupMaster g: groupMastList) {
					newGroupId=g.getId();
				}
			}
			
			if(form.getStatus().equalsIgnoreCase(PharmacyUtil.statusActive)  && (previousStatus.equalsIgnoreCase(PharmacyUtil.statusNew) 
					|| previousStatus.equalsIgnoreCase(PharmacyUtil.statusInfoCompleted)))
			{
				if(previousGroupId==newGroupId)
					physicianRepo.reAssignPhysicianGroupOnChangeofDefaultGroupinClinic(form.getGroupId(),rec.getId());
				
			}
			
			// Remove notification from Notification Table
			if (form.getStatus().equalsIgnoreCase(PharmacyUtil.statusActive)) {
				notificationService.approveNotifyClinic(acc.getId());
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return rec;
	}
	
	public boolean deleteClinicAccountById(int id, LoginForm loggedInUser) {
		boolean isDeleted = false;
		if (id > 0) {
			Clinic acc = null;
			acc = clinicRepo.findOne(id);
			
			acc.setStatus(PharmacyUtil.statusInactive);
			acc.setLastUpdatedBy(loggedInUser.getUserid());
			acc.setLastUpdatedUser(loggedInUser.getType());
			acc.setLastUpdatedDate( PharmacyUtil.getCurrentTimeStamp() );
			
			clinicRepo.save(acc);
			
			isDeleted = true;
		}
		
		return isDeleted;
	}
	

	public List<Clinic> getClinicByNameAndNotId(String clinicName, int id) {
		return clinicRepo.findByClinicNameAndIdNot(clinicName, id);
	}

	public List<Clinic> getClinicByName(String clinicName) {
		return clinicRepo.findByClinicName(clinicName);
	}
	
	public List<Clinic> getAutoCompleteClinicList(String term, String status, int id) {
		int MaxResults=Integer.parseInt(env.getProperty("MaxResults"));
		List<Clinic> physicianAcctObj = clinicRepo.getAutoCompleteClinicList(term,status,id, new PageRequest(0, MaxResults));
		return physicianAcctObj;
	}

	public List<Clinic> getAllAutoCompleteClinicList(String term) {
		int MaxResults=Integer.parseInt(env.getProperty("MaxResults"));
		List<Clinic> physicianAcctObj = clinicRepo.getAllAutoCompleteClinicList(term, new PageRequest(0, MaxResults));
		return physicianAcctObj;
	}
	
	public List<Clinic> getClinicByEmailAndNotId(String email, int id) {
		return clinicRepo.findByEmailAndIdNot(email, id);
	}

	public List<Clinic> getClinicByEmail(String email) {
		return clinicRepo.findByEmail(email);
	}

	public String getClinicAddressById(String clinicId) {
		String json2 = "";
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		if (!"".equalsIgnoreCase(clinicId)) {
			ClinicForm clinicObj = getClinicAccountById(  Integer.parseInt(clinicId) );
			json2 = gson.toJson(clinicObj);	
		}

		return json2;
	}
	
	
	public List<PhysicianAssistantRegistration> getPhysicianAssistantByClinicId(int id) {
		List<PhysicianAssistantRegistration> assistantList = new ArrayList<PhysicianAssistantRegistration>();
		
		Pageable page = new PageRequest(0, Integer.MAX_VALUE);
		Page<Object[]> physicianAssistantList = assistantRepo.getAssistantByClinicId(id, page);
		
		List<Object[]> outputList = physicianAssistantList.getContent();
		
		// pa.id, pa.assistantName, p.physicianName, pa.email, pa.phone
		if (physicianAssistantList != null && physicianAssistantList.getSize() > 0 ) {
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
				
				/*if (outputList.get(i)[2] != null)
					assForm.setPhysicianName((String) outputList.get(i)[2]);
				else
					assForm.setPhysicianName("");*/
				
				StringBuilder selectedPhysicianId = new StringBuilder();
				
				
				List<PhysicianAssistantPhysician> phyList = assistantPhysicianRepo.findByPhysicianAssistantId(assForm.getAssistantId());
				if (phyList.size() > 0) {
					for (PhysicianAssistantPhysician g: phyList) {
						if (g.getPhysicianId() > 0) {
							selectedPhysicianId.append( physicianRepo.findOne(g.getPhysicianId()).getPhysicianName() ).append(", ");
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
				
				assistantList.add(assForm);
			}
		}
		
		return assistantList;
	}

	public String getPhysicianDataByClinicId(int draw, int start, int length, String searchTerm, int orderColumn, String orderDir, int clinicId,String physicianName) {
		int total = 0;
		int pageNumber = 0;
		Pageable page = null;
		Page<PhysicianAccount> physicianList = null;
		if (physicianName == null) physicianName = "";
		Direction dir = Direction.ASC;
		if (orderDir.equalsIgnoreCase("desc"))
			dir = Direction.DESC;
		
		try {
			total = phyAccRepo.getPhysicianListByClinicId(clinicId,physicianName).size();
		} catch(Exception e) {
			e.printStackTrace();
		}
		pageNumber = start / length;
		
		switch (orderColumn) {
			case 1: {
				page = new PageRequest(pageNumber, length, dir, "physicianName");
				break;
			}
			
			default: {
				page = new PageRequest(pageNumber, length, dir, "id");
				break;
			}
		}
		
		try {
			physicianList = phyAccRepo.getPhysicianListByClinicId(clinicId,physicianName, page);
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
	
	
	public String getPhysicianAssistantDataByClinicId(int draw, int start, int length, String searchTerm, int orderColumn, String orderDir, int clinicId,String assistantName) {
		int total = 0;
		int pageNumber = 0;
		Pageable page = null;
		Page<Object[]> physicianAssistantList = null;
		
		if (assistantName == null) assistantName = "";
		Direction dir = Direction.ASC;
		if (orderDir.equalsIgnoreCase("desc"))
			dir = Direction.DESC;
		
		try {
			total =  assistantRepo.getAssistantByClinicId(clinicId,assistantName).size();
		} catch(Exception e) {
			e.printStackTrace();
		}
		pageNumber = start / length;
		
		switch (orderColumn) {
			case 1: {
				page = new PageRequest(pageNumber, length, dir, "pa.assistantName");
				break;
			}
			
			default: {
				page = new PageRequest(pageNumber, length, dir, "pa.id");
				break;
			}
		}
		
		try {
			physicianAssistantList = assistantRepo.getAssistantByClinicId(clinicId,assistantName, page);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		List<PhysicianAssistantRegistration> assistantList = new ArrayList<PhysicianAssistantRegistration>();
		List<Object[]> outputList = physicianAssistantList.getContent();
		StringBuilder selectedPhysicianId = new StringBuilder();
		
		// pa.id, pa.assistantName, p.physicianName, pa.email, pa.phone
		if (physicianAssistantList != null && physicianAssistantList.getSize() > 0 ) {
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
							selectedPhysicianId.append( physicianRepo.findOne(g.getPhysicianId()).getPhysicianName() ).append(", ");
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
	
	
}
