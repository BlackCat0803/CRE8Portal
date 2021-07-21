package com.pharma.core.pharmaservices.physicianservices;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pharma.core.formBean.LoginForm;
import com.pharma.core.formBean.physician.PhysicianAssistantJSonObj;
import com.pharma.core.formBean.physician.PhysicianAssistantRegistration;
import com.pharma.core.model.physician.PhysicianAccount;
import com.pharma.core.model.physician.PhysicianAssistantAccount;
import com.pharma.core.model.physician.PhysicianAssistantPhysician;
import com.pharma.core.model.physician.PhysicianClinic;
import com.pharma.core.model.physician.PhysicianGroup;
import com.pharma.core.pharmaservices.mail.MailSendConfig;
import com.pharma.core.pharmaservices.pharmacygroupservices.GroupMasterService;
import com.pharma.core.repository.clinic.ClinicRepository;
import com.pharma.core.repository.physician.PhysicianAccountRespository;
import com.pharma.core.repository.physician.PhysicianAssistantAccountRespository;
import com.pharma.core.repository.physician.PhysicianAssistantPhysicianRepository;
import com.pharma.core.repository.physician.PhysicianGroupRepository;
import com.pharma.core.util.PharmacyUtil;
/**
 * This is an implementation class that loads and stores the physician assistant account data
 */
@Service("PhysicianAssistantAccountService")
@PropertySource("classpath:webService.properties")
public class PhysicianAssistantAccountServiceImpl implements PhysicianAssistantAccountService {

	@Autowired
	PhysicianAssistantAccountRespository phyAssistantAccountRepo;

	@Autowired
	PhysicianAccountRespository physicianRepo;

	@Autowired
	ClinicRepository clinicRepo;

	@Autowired
	PhysicianAssistantFileUploadService fileUploadService;

	@Autowired
	GroupMasterService groupService;

	@Autowired
	PhysicianGroupService phyGroupService;

	@Autowired
	PhysicianGroupRepository phyGroupRepo;


	@Autowired
	PhysicianAssistantPhysicianRepository phyAssistantPhyRepo;

	@Autowired
	PhysicianAccountService phyAcctService;

	@Autowired
	private Environment env;

	public PhysicianAssistantAccount saveAssistantAccount(PhysicianAssistantRegistration form, CommonsMultipartFile photoFile,
			CommonsMultipartFile[] docFiles, String rootFilePath, LoginForm loggedInUser, HttpSession session,
			HttpServletRequest request, Environment env) {
		try {
			PhysicianAssistantAccount acc = null;
			if (form.getAssistantId() >0)
				acc = phyAssistantAccountRepo.findOne(form.getAssistantId());
			else {
				acc = new PhysicianAssistantAccount();
				acc.setPassword(form.getPassword());

				acc.setCreatedBy(loggedInUser.getUserid());
				acc.setCreatedUser(loggedInUser.getType());
				acc.setCreatedDate( PharmacyUtil.getCurrentTimeStamp() );
			}

			acc.setLastUpdatedBy(loggedInUser.getUserid());
			acc.setLastUpdatedUser(loggedInUser.getType());
			acc.setLastUpdatedDate( PharmacyUtil.getCurrentTimeStamp() );

			acc.setGroupId(form.getGroupId());
			//Temp commented on jan 19,2018
			//acc.setPhysicianId(form.getPhysicianId());
			// acc.setClinicId(form.getClinicId());
			acc.setStatus(form.getStatus());
			if (form.getDateRegistrated() != null && !"".equalsIgnoreCase(form.getDateRegistrated()))
				acc.setDateRegistrated( PharmacyUtil.getSqlDateFromString(form.getDateRegistrated()));
			acc.setFirstName(form.getFirstName().trim());
			acc.setMiddleName(form.getMiddleName().trim());
			acc.setLastName(form.getLastName().trim());

			if(form.getMiddleName()!=null && form.getMiddleName().trim().length()>0)
				acc.setAssistantName(form.getFirstName().trim() + " " + form.getMiddleName().trim() + " " + form.getLastName().trim());
			else
				acc.setAssistantName(form.getFirstName().trim() + " " + form.getLastName().trim());

			acc.setEmail(form.getEmail());
			acc.setMobile(form.getMobile());
			acc.setPhone(form.getPhone());
			PhysicianAssistantAccount rec = phyAssistantAccountRepo.save(acc);



			/*
			 * Saving records in Physician Assistant Physician table
			 */
			try {
				// Deleted existing records
				List<PhysicianAssistantPhysician> phyAssistantList =  phyAssistantPhyRepo.findByPhysicianAssistantId(rec.getId());
				if (phyAssistantList.size() > 0) {
					for (PhysicianAssistantPhysician c: phyAssistantList) {
						c.setDelFlag(PharmacyUtil.deleteFlagYes);
						c.setUpdatedBy(loggedInUser.getUserid());
						c.setUpdatedDate(PharmacyUtil.getCurrentTimeStamp());
						c.setUpdatedUserType(loggedInUser.getType());
						phyAssistantPhyRepo.save(c);
					}
				}

				if (!"".equalsIgnoreCase( form.getSelectedPhysicianId())) {
					String[] idList = form.getSelectedPhysicianId().split(",");
					if (idList.length > 0) {
						for (int i=0; i < idList.length; i++) {
							PhysicianAssistantPhysician obj = null;
							obj = phyAssistantPhyRepo.findByPhysicianAssistantIdAndPhysicianId(rec.getId(), Integer.parseInt(idList[i]));
							if (obj == null) {
								obj = new PhysicianAssistantPhysician();
								obj.setPhysicianAssistantId(rec.getId());
								obj.setCreatedBy(loggedInUser.getUserid());
								obj.setCreatedDate(PharmacyUtil.getCurrentTimeStamp());
								obj.setCreatedUserType(loggedInUser.getType());
							}
							obj.setPhysicianId(Integer.parseInt(idList[i]));
							obj.setDelFlag(PharmacyUtil.deleteFlagNo);

							obj.setUpdatedBy(loggedInUser.getUserid());
							obj.setUpdatedDate(PharmacyUtil.getCurrentTimeStamp());
							obj.setUpdatedUserType(loggedInUser.getType());

							phyAssistantPhyRepo.save(obj);
						}
					}
				}
			} catch(Exception e) {
				e.printStackTrace();
			}


			/*
			 * Uploaded Files saving process
			 */
			if (photoFile.getSize() > 0) {
				String folderName="physician_assistant";
				String photoFileName="photo_"+rec.getId();
				boolean isUploaded=PharmacyUtil.userPhotoUpload(photoFile, folderName, photoFileName, rootFilePath, rec.getId(), "");
				if (isUploaded){
					String oname=photoFile.getOriginalFilename().toLowerCase();
					String extension = oname.substring(oname.lastIndexOf('.') + 1, oname.length()).toLowerCase();

					StringBuffer uploadedFile = new StringBuffer(PharmacyUtil.getFileUploadPath(rootFilePath, folderName, rec.getId(), ""));
					uploadedFile.append( File.separator + photoFileName + "." + extension);

					fileUploadService.photoFileSave(rec.getId(), photoFile, uploadedFile.toString(), loggedInUser);

					//System.out.println("Physician Assistant Photo uploaded ok");
				} else {
					//System.out.println("Physician Assistant Photo not uploaded");
				}
			}
			//Automatic mail triggered is commented out and called from Send Credential Mail button on 10/02/2021 by durgadevi
			/*try {
				String loginUrl = PharmacyUtil.getCurrentSystemUrl(request)+"/login?s=3";  // 3 means User Type is Physician Assistant

				// Only New Physician Assistant at the time of registration will get Mail about new physician Assistant registration
				if (form.getAssistantId() == 0 && "true".equalsIgnoreCase(env.getProperty("isMailSend"))) {
					String fileName = null;
					String attachmentFileName = null;
					if ("true".equalsIgnoreCase(env.getProperty("includeAttachment"))) {
						// Include attachments here...

					}

					MailSendConfig mail = new MailSendConfig();
					mail.MailSending(rec, env, "AssistantSignup", loginUrl, fileName, attachmentFileName );
				}
			} catch (Exception e) {
				e.printStackTrace();
			}*/

			return rec;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<PhysicianAssistantAccount> getPhysicianAssistantByEmail(String email) {
		return phyAssistantAccountRepo.findByEmail(email);
	}

	public String getPhysicianAssistantDataList(int id, int draw, int start, int length, String searchTerm, int orderColumn, String orderDir,
			String assistant, String clinic, String status, int groupId) {

		if (assistant == null)  assistant = "";
		if (clinic == null)  clinic = "";
		if (status == null) status = "";

		int total = 0;
		int pageNumber = 0;
		Pageable page = null;
		Page<PhysicianAssistantAccount> assistantList = null;

		Direction dir = Direction.ASC;
		if (orderDir.equalsIgnoreCase("desc"))
			dir = Direction.DESC;

		try {
			if (groupId == 0) {
				if ("".equalsIgnoreCase(assistant)  && "".equalsIgnoreCase(status)) {
					total = (int) phyAssistantAccountRepo.findByPhysicianIdAndStatus(id);
				} else {
					if ("".equalsIgnoreCase(status))
							total = phyAssistantAccountRepo.findByAssistantNameAndStatus(id, assistant);
						else
							total = phyAssistantAccountRepo.findByAssistantNameAndStatus(id, assistant, status);

				}
			} else {
				if ("".equalsIgnoreCase(assistant)  && "".equalsIgnoreCase(status)) {
					total = (int) phyAssistantAccountRepo.findByPhysicianAndGroupAndStatus(id, groupId);
				} else {

						if ("".equalsIgnoreCase(status))
							total = phyAssistantAccountRepo.findByAssistantNameAndGroupAndStatus(id, groupId, assistant);
						else
							total = phyAssistantAccountRepo.findByAssistantNameAndGroupAndStatus(id, groupId, assistant, status);

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
			if (groupId == 0) {
				if ("".equalsIgnoreCase(assistant)  && "".equalsIgnoreCase(status)) {
					assistantList = phyAssistantAccountRepo.findByPhysicianIdAndStatus(id, page);
				} else {

						if ("".equalsIgnoreCase(status))
							assistantList = phyAssistantAccountRepo.findByAssistantNameAndStatus(id, assistant, page);
						else
							assistantList = phyAssistantAccountRepo.findByAssistantNameAndStatus(id, assistant, status, page);

				}
			} else {
				if ("".equalsIgnoreCase(assistant)  && "".equalsIgnoreCase(status)) {
					assistantList = phyAssistantAccountRepo.findByPhysicianAndGroupAndStatus(id, groupId, page);
				} else {

					if ("".equalsIgnoreCase(status))
						assistantList = phyAssistantAccountRepo.findByAssistantNameGroupAndAndStatus(id, groupId, assistant,  page);
					else
						assistantList = phyAssistantAccountRepo.findByAssistantNameGroupAndAndStatus(id, groupId, assistant, status, page);

				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		List<PhysicianAssistantRegistration> phyAssObjList = new ArrayList<PhysicianAssistantRegistration>();
		SimpleDateFormat dt = new SimpleDateFormat("MM/dd/yyyy");

		if (assistantList != null && assistantList.getSize() > 0 ) {
			for(PhysicianAssistantAccount a: assistantList) {
				PhysicianAssistantRegistration obj = new PhysicianAssistantRegistration();
				obj.setAssistantId(a.getId());
				obj.setAssistantName(a.getAssistantName());
				obj.setEmail(a.getEmail());
				//temp commented on jan 19,2018
				/*if (physicianRepo.findOne(a.getPhysicianId()).getClinicId() > 0)
					obj.setClinicName(clinicRepo.findOne(physicianRepo.findOne(a.getPhysicianId()).getClinicId()).getClinicName());
				else
					obj.setClinicName("");*/
				obj.setClinicName("");
				obj.setStatus(a.getStatus());
				obj.setMobile(a.getMobile());
				obj.setDateRegistrated(dt.format(a.getDateRegistrated()));
				obj.setDT_RowId("row_" + a.getId());

//				StringBuilder groupName = new StringBuilder();
//				ArrayList<Integer> groupList = new ArrayList<Integer>();
				obj.setGroupId(a.getGroupId());
				if(a.getGroupId()>0)
					obj.setGroupName(groupService.getGroupMasterDetails(a.getGroupId()).getGroupName());
				else
					obj.setGroupName("");


				StringBuilder physicianName = new StringBuilder();
				ArrayList<Integer> physicianList = new ArrayList<Integer>();
				List<PhysicianAccount> groupTableList = physicianRepo.getAllPhysicianAssistantGroupWisePhysicianListSelected(a.getGroupId(),a.getId());
				if (groupTableList.size() > 0) {
					for (PhysicianAccount g: groupTableList) {
						physicianList.add(a.getGroupId());
						if (a.getGroupId() > 0)
							physicianName.append( phyAcctService.getPhysicianAccountDetails(g.getId()).getPhysicianName()).append(",");
						else
							physicianName.append(",");
					}
				}
				if (physicianName.length() > 0)
					obj.setPhysicianName( physicianName.toString().substring(0, physicianName.length()-1) );
				else
					obj.setPhysicianName("");

				phyAssObjList.add(obj);
			}
		}

		PhysicianAssistantJSonObj data = new PhysicianAssistantJSonObj();
		data.setData(phyAssObjList);
		data.setDraw(draw);
		data.setRecordsFiltered(total);
		data.setRecordsFiltered(total);

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json2 = gson.toJson(data);

		return json2;
	}

	public String getAdminPhysicianAssistantDataList(int draw, int start, int length, String searchTerm, int orderColumn, String orderDir,
			String assistant, String clinic, String phyName, String status, int groupId) {

		if (assistant == null)  assistant = "";
		if (clinic == null)  clinic = "";
		if (phyName == null) phyName="";
		if (status == null) status = "";

		int total = 0;
		int pageNumber = 0;
		Pageable page = null;
		Page<PhysicianAssistantAccount> assistantList = null;

		Direction dir = Direction.ASC;
		if (orderDir.equalsIgnoreCase("desc"))
			dir = Direction.DESC;

		try {
			if (groupId == 0) {
				if ("".equalsIgnoreCase(assistant) &&  "".equalsIgnoreCase(phyName) && "".equalsIgnoreCase(status)) {
					total = (int) phyAssistantAccountRepo.findByStatus();
				} else {
					if ("".equalsIgnoreCase(status)) {
						if ("".equalsIgnoreCase(phyName))
							total = phyAssistantAccountRepo.findBySearchAssistant(assistant);
						else
							total = phyAssistantAccountRepo.findBySearchAssistant(assistant, phyName);
					} else {
						if ("".equalsIgnoreCase(phyName))
							total = phyAssistantAccountRepo.findByStatusAndSearch(assistant, status);
						else
							total = phyAssistantAccountRepo.findByStatusAndSearch(assistant, phyName, status);
					}
				}
			} else {
				if ("".equalsIgnoreCase(assistant) && "".equalsIgnoreCase(phyName) && "".equalsIgnoreCase(status)) {
					total = (int) phyAssistantAccountRepo.findByGroupAndStatus(groupId);
				} else {
					if ("".equalsIgnoreCase(status)) {
						if ("".equalsIgnoreCase(phyName))
							total = phyAssistantAccountRepo.findBySearchAssistant(assistant, groupId);
						else
							total = phyAssistantAccountRepo.findBySearchAssistant(assistant, phyName, groupId);
					} else {
						if ("".equalsIgnoreCase(phyName))
							total = phyAssistantAccountRepo.findByStatusAndSearch(assistant, groupId, status).size();
						else
							total = phyAssistantAccountRepo.findByStatusAndSearch(assistant, phyName, groupId, status);
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
			if (groupId == 0) {
				if ("".equalsIgnoreCase(assistant)  && "".equalsIgnoreCase(phyName) && "".equalsIgnoreCase(status)) {
					assistantList = phyAssistantAccountRepo.findByStatus(page);
				} else {
					if ("".equalsIgnoreCase(status)) {
						if ("".equalsIgnoreCase(phyName))
							assistantList = phyAssistantAccountRepo.findByAssistantSearch(assistant, page);
						else
							assistantList = phyAssistantAccountRepo.findByAssistantSearch(assistant, phyName, page);
					} else {
						if ("".equalsIgnoreCase(phyName))
							assistantList = phyAssistantAccountRepo.findByStatusAndSearch(assistant, status, page);
						else
							assistantList = phyAssistantAccountRepo.findByStatusAndSearch(assistant, phyName, status, page);
					}
				}
			} else {
				if ("".equalsIgnoreCase(assistant) && "".equalsIgnoreCase(phyName) && "".equalsIgnoreCase(status)) {
					assistantList = phyAssistantAccountRepo.findByGroupAndStatus(groupId, page);
				} else {
					if ("".equalsIgnoreCase(status)) {
						if ("".equalsIgnoreCase(phyName))
							assistantList = phyAssistantAccountRepo.findByAssistantSearch(assistant, groupId, page);
						else
							assistantList = phyAssistantAccountRepo.findByAssistantSearch(assistant, phyName, groupId, page);
					} else {
						if ("".equalsIgnoreCase(phyName))
							assistantList = phyAssistantAccountRepo.findByStatusAndSearch(assistant, groupId, status, page);
						else
							assistantList = phyAssistantAccountRepo.findByStatusAndSearch(assistant, phyName, groupId, status, page);
					}
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		List<PhysicianAssistantRegistration> phyAssObjList = new ArrayList<PhysicianAssistantRegistration>();
		SimpleDateFormat dt = new SimpleDateFormat("MM/dd/yyyy");

		if (assistantList != null && assistantList.getSize() > 0 ) {
			for(PhysicianAssistantAccount a: assistantList) {
				PhysicianAssistantRegistration obj = new PhysicianAssistantRegistration();
				obj.setAssistantId(a.getId());
				obj.setAssistantName(a.getAssistantName());
				obj.setEmail(a.getEmail());
				//Temp commented on jan 19,2018
				/*if (physicianRepo.findOne(a.getPhysicianId()).getClinicId() > 0)
					obj.setClinicName(clinicRepo.findOne(physicianRepo.findOne(a.getPhysicianId()).getClinicId()).getClinicName());
				else
					obj.setClinicName("");*/
				obj.setClinicName("");
				obj.setStatus(a.getStatus());
				obj.setMobile(a.getMobile());
				obj.setDateRegistrated(dt.format(a.getDateRegistrated()));
				obj.setDT_RowId("row_" + a.getId());
				obj.setGroupId(a.getGroupId());
				if(a.getGroupId()>0)
					obj.setGroupName(groupService.getGroupMasterDetails(a.getGroupId()).getGroupName());
				else
					obj.setGroupName("");


				StringBuilder physicianName = new StringBuilder();
				ArrayList<Integer> physicianList = new ArrayList<Integer>();
				List<PhysicianAccount> groupTableList = physicianRepo.getAllPhysicianAssistantGroupWisePhysicianListSelected(a.getGroupId(),a.getId());
				if (groupTableList.size() > 0) {
					for (PhysicianAccount g: groupTableList) {
						physicianList.add(a.getGroupId());
						if (a.getGroupId() > 0)
							physicianName.append( phyAcctService.getPhysicianAccountDetails(g.getId()).getPhysicianName()).append(",");
						else
							physicianName.append(",");
					}
				}
				if (physicianName.length() > 0)
					obj.setPhysicianName( physicianName.toString().substring(0, physicianName.length()-1) );
				else
					obj.setPhysicianName("");

				phyAssObjList.add(obj);
			}
		}

		PhysicianAssistantJSonObj data = new PhysicianAssistantJSonObj();
		data.setData(phyAssObjList);
		data.setDraw(draw);
		data.setRecordsFiltered(total);
		data.setRecordsFiltered(total);

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json2 = gson.toJson(data);

		return json2;
	}


	public PhysicianAssistantRegistration getPhysicianAssistantById(int id, String filepath) {


		try {
			PhysicianAssistantRegistration form = new PhysicianAssistantRegistration();
			PhysicianAssistantAccount acc = phyAssistantAccountRepo.findOne(id);
			//Temp commented on jan 19,2018
			//PhysicianAccount phyAcc = physicianRepo.findOne(acc.getPhysicianId());
			//PhysicianGroup phyGroup = phyGroupRepo.findRecordByPhysicianId(phyAcc.getId());

			form.setAssistantId(acc.getId());
			form.setGroupId(acc.getGroupId());
			if (acc.getGroupId() > 0)
				form.setGroupName(groupService.getGroupMasterDetails(acc.getGroupId()).getGroupName());

			form.setStatus(acc.getStatus());
			form.setDateRegistrated( PharmacyUtil.getStringDateFromSqlDate(acc.getDateRegistrated()) );
			form.setFirstName(acc.getFirstName());
			form.setMiddleName(acc.getMiddleName());
			form.setLastName(acc.getLastName());
			form.setAssistantName(acc.getAssistantName());
			form.setEmail(acc.getEmail());
			form.setPassword(acc.getPassword()!=null?acc.getPassword():"");
			form.setMobile(acc.getMobile());
			form.setPhone(acc.getPhone());

			form.setCreatedBy(acc.getCreatedBy());
			form.setCreatedUser(acc.getCreatedUser());
			form.setCreatedDate(acc.getCreatedDate());

			form.setLastUpdatedBy(acc.getLastUpdatedBy());
			form.setLastUpdatedUser(acc.getLastUpdatedUser());
			form.setLastUpdatedDate(acc.getLastUpdatedDate());

			form.setClinicName("");
			//Temp commented on jan 19,2018
			/*if (phyAcc.getClinicId() > 0)
				form.setClinicName(clinicRepo.findOne(phyAcc.getClinicId()).getClinicName());
			if (phyGroup != null) {
				form.setGroupId(phyGroup.getGroupId());
				form.setGroupName( groupService.getGroupMasterDetails(phyGroup.getGroupId()).getGroupName() );
			}*/

			if (!"".equalsIgnoreCase(filepath)) {
				form.setPhotoFile(fileUploadService.photoNameByAssistantId(id, filepath) );
			}

			StringBuilder selectedPhysicianId = new StringBuilder();


			List<Integer> patGroupList = new ArrayList<Integer>();
			String patselectedGroupId=form.getGroupId()+"";
			if(patselectedGroupId!=null && patselectedGroupId.length()>0)
			{

				String[] patselectedGroupIdArr=patselectedGroupId.split(",");


				for (String i : patselectedGroupIdArr) {
					patGroupList.add(Integer.valueOf(i));
				}

			}

			List<PhysicianAccount> phyList = physicianRepo.getAllAssistantGroupWisePhysicianListSelected(patGroupList,form.getAssistantId());
			if (phyList.size() > 0) {
				for (PhysicianAccount g: phyList) {
					if (g.getId() > 0)
						selectedPhysicianId.append(g.getId()+"").append(",");
					else
						selectedPhysicianId.append(",");
				}
			}

			if (selectedPhysicianId.length() > 0)
				form.setSelectedPhysicianId(selectedPhysicianId.toString().substring(0, selectedPhysicianId.length()-1) );
			else
				form.setSelectedPhysicianId("");

			return form;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<PhysicianAssistantAccount> getPhysicianAssistantByEmailAndNotId(String email, int id) {
		return phyAssistantAccountRepo.findByEmailAndIdNot(email, id);
	}
	//Temp commented on jan 19,2018
	/*public String getPhysicianNameById(int id) {
		int phyId = getPhysicianAssistantAccountDetails(id).getPhysicianId();
		return physicianRepo.findOne(phyId).getPhysicianName();
	}*/

	public PhysicianAssistantAccount getPhysicianAssistantAccountDetails(int id) {
		return phyAssistantAccountRepo.findOne(id);
	}
	//temp commented on jan 19,2018
	/*public boolean setInactiveByPhysicianId(int id, LoginForm loginForm) {
		boolean isSetted = false;
		try {
			List<PhysicianAssistantAccount> list = phyAssistantAccountRepo.findByPhysicianId(id);
			if (list.size() > 0) {
				for (PhysicianAssistantAccount a: list) {
					a.setDeniedBy(loginForm.getUserid());
					a.setDeniedDate(new java.sql.Date(new Date().getTime()));
					a.setStatus(PharmacyUtil.statusInactive);

					phyAssistantAccountRepo.save(a);
				}
			}
			isSetted = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isSetted;
	}*/

	public String getAssistantPhotoFileName(int id, String filepath) {
		String file = "";
		if (id > 0) {
			file = fileUploadService.photoNameByAssistantId(id, filepath);
		}
		return file;
	}

	public List<PhysicianAssistantAccount> getAutoCompletePhysicianAssistantList(
			String term, String status, int id) {

		int MaxResults=Integer.parseInt(env.getProperty("MaxResults"));
		List<PhysicianAssistantAccount> physicianAcctObj = phyAssistantAccountRepo.getAutoCompletePhysicianAssistantList(term,status,id, new PageRequest(0, MaxResults));
		return physicianAcctObj;
	}

	public List<PhysicianAssistantAccount> getAllAutoCompletePhysicianAssistantList(
			String term) {
		int MaxResults=Integer.parseInt(env.getProperty("MaxResults"));
		List<PhysicianAssistantAccount> physicianAcctObj = phyAssistantAccountRepo.getAllAutoCompletePhysicianAssistantList(term, new PageRequest(0, MaxResults));
		return physicianAcctObj;
	}


	public List<PhysicianAssistantAccount> getAutoCompleteGroupWisePhysicianAssistantList(
			String term, String status, int id,int groupid ){

		int MaxResults=Integer.parseInt(env.getProperty("MaxResults"));
		List<PhysicianAssistantAccount> physicianAcctObj = phyAssistantAccountRepo.getAutoCompleteGroupWisePhysicianAssistantList(term,status,id,groupid, new PageRequest(0, MaxResults));
		return physicianAcctObj;
	}


	public List<PhysicianAssistantAccount> getAutoCompletePhysicianWisePhysicianAssistantList(
			String term, String status, int id,int physicianid) {

		int MaxResults=Integer.parseInt(env.getProperty("MaxResults"));
		List<PhysicianAssistantAccount> physicianAcctObj = phyAssistantAccountRepo.getAutoCompletePhysicianWisePhysicianAssistantList(term,status,id,physicianid, new PageRequest(0, MaxResults));
		return physicianAcctObj;
	}

	public List<PhysicianAssistantAccount> getAllAutoCompleteGroupWisePhysicianAssistantList(
			String term,int groupId) {
		int MaxResults=Integer.parseInt(env.getProperty("MaxResults"));
		List<PhysicianAssistantAccount> physicianAcctObj = phyAssistantAccountRepo.getAllAutoCompleteGroupWisePhysicianAssistantList(term,groupId, new PageRequest(0, MaxResults));
		return physicianAcctObj;
	}

	public List<PhysicianAssistantAccount> getAllAutoCompletePhysicianWisePhysicianAssistantList(
			String term,int physicianId) {
		int MaxResults=Integer.parseInt(env.getProperty("MaxResults"));
		List<PhysicianAssistantAccount> physicianAcctObj = phyAssistantAccountRepo.getAllAutoCompletePhysicianWisePhysicianAssistantList(term,physicianId, new PageRequest(0, MaxResults));
		return physicianAcctObj;
	}
	
	public boolean sendPhysicianAsstAccountMail(PhysicianAssistantRegistration form,LoginForm loggedInUser,HttpSession session, HttpServletRequest request,Environment env) {
		boolean isSend = false;
		try {
			String loginUrl = PharmacyUtil.getCurrentSystemUrl(request)+"/login?s=3";  // 3 means User Type is Physician Assistant
				PhysicianAssistantAccount rec = phyAssistantAccountRepo.findOne(form.getAssistantId());
			if (form.getAssistantId() > 0 && "true".equalsIgnoreCase(env.getProperty("isMailSend"))) {
				String fileName = null;
				String attachmentFileName = null;
				if ("true".equalsIgnoreCase(env.getProperty("includeAttachment"))) {
					// Include attachments here...

				}

				MailSendConfig mail = new MailSendConfig();
				mail.MailSending(rec, env, "AssistantSignup", loginUrl, fileName, attachmentFileName );
				isSend=true;

			}
		} catch (Exception e) {
		e.printStackTrace();
		isSend=false;
	}
		return isSend;
		}
}
