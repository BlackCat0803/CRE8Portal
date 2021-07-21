package com.pharma.core.pharmaservices.adminservices;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.pharma.core.formBean.admin.AdminForm;
import com.pharma.core.formBean.admin.AdminFormJSonObj;
import com.pharma.core.model.admin.AdminAccount;
import com.pharma.core.model.admin.AdminPermissions;
import com.pharma.core.pharmaservices.mail.MailSendConfig;
import com.pharma.core.repository.PharmacyRepository;
import com.pharma.core.repository.admin.AdminAccountRepository;
import com.pharma.core.repository.admin.AdminPermissionsRepository;
import com.pharma.core.util.PharmacyUtil;
/**
 * This is an implementation class that loads and stores the admin account data
 *
 */

@Service("adminAccountService")
public class AdminAccountServiceImpl implements AdminAccountService {

	@Autowired
	AdminAccountRepository adminRepo;

	@Autowired
	PharmacyRepository pharmacyRepo;

	@Autowired
	AdminPermissionsRepository adminPermissionRepo;


	@Autowired
	AdminFileUploadService fileUploadService;

	public String getAdminDataList(int draw, int start, int length, String searchTerm, int orderColumn, String orderDir, String admin,
			String pharmacy, String userType, String status) {

		try {
			if (admin == null) admin="";
			if (pharmacy == null) pharmacy="";
			if (userType == null) userType = "";
			if (status == null) status = "";
			//if (status == null || "".equalsIgnoreCase(status.trim())) status = PharmacyUtil.statusActive;

			int total = 0;
			int pageNumber = 0;
			Pageable page = null;
			Page<AdminAccount> adminList = null;

			Direction dir = Direction.ASC;
			if (orderDir.equalsIgnoreCase("desc"))
				dir = Direction.DESC;

			try {
				if ("".equalsIgnoreCase(admin) && "".equalsIgnoreCase(pharmacy) && "".equalsIgnoreCase(userType) && "".equalsIgnoreCase(status)) {
					total = (int) adminRepo.findByAllStatus();
				}else{
					if ("".equalsIgnoreCase(admin) && "".equalsIgnoreCase(pharmacy) && "".equalsIgnoreCase(userType) && status.length()>0) {
						total = (int) adminRepo.findByStatus(status);
					} else {
						if ("".equalsIgnoreCase(userType) && status.length()>0)
							total = adminRepo.findByStatusWidthSearch(admin, pharmacy, status);
						else if ("".equalsIgnoreCase(userType) && status.length()==0)
							total = adminRepo.findByStatusWidthSearch(admin, pharmacy);

						else if (!"".equalsIgnoreCase(userType) && status.length()>0)
							total = adminRepo.findWidthUserTypeAndSearch(admin, pharmacy, userType, status);
						else if (!"".equalsIgnoreCase(userType) && status.length()==0)
							total = adminRepo.findWidthUserTypeAndSearch(admin, pharmacy, userType);
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
				if ("".equalsIgnoreCase(admin) && "".equalsIgnoreCase(pharmacy) && "".equalsIgnoreCase(userType) && "".equalsIgnoreCase(status)) {

					adminList= adminRepo.findByAllStatus(page);
				}else{
					if ("".equalsIgnoreCase(admin) && "".equalsIgnoreCase(pharmacy) && "".equalsIgnoreCase(userType)) {
						adminList = adminRepo.findByStatus(status, page);
					} else {
						if ("".equalsIgnoreCase(userType)  && status.length()>0)
							adminList = adminRepo.findByStatusWidthSearch(admin, pharmacy, status, page);
						else if ("".equalsIgnoreCase(userType) && status.length()==0)
							adminList = adminRepo.findByStatusWidthSearch(admin, pharmacy, page);
						else if (!"".equalsIgnoreCase(userType)   && status.length()>0)
							adminList = adminRepo.findWidthUserTypeAndSearch(admin, pharmacy, userType, status, page);
						else if (!"".equalsIgnoreCase(userType) && status.length()==0)
							adminList = adminRepo.findWidthUserTypeAndSearch(admin, pharmacy, userType, page);
					}
				}
			} catch(Exception e) {
				e.printStackTrace();
			}

			List<AdminForm> adminObjList = new ArrayList<AdminForm>();
			SimpleDateFormat dt = new SimpleDateFormat("MM/dd/yyyy");

			if (adminList != null && adminList.getSize() > 0 ) {
				for(AdminAccount a: adminList) {
					AdminForm obj = new AdminForm();
					obj.setAdminId(a.getId());
					obj.setAdminName(a.getName());
					obj.setEmail(a.getEmail());
					obj.setPharmacyName( pharmacyRepo.findOne(a.getPharmacyId()).getPharmacyName() );
					obj.setType(a.getType());
					obj.setMobile(a.getMobile());
					obj.setStatus(a.getStatus());
					obj.setDateRegistrated(dt.format(a.getDateRegistrated()));

					obj.setDT_RowId("row_" + a.getId());

					adminObjList.add(obj);
				}
			}

			AdminFormJSonObj data = new AdminFormJSonObj();
			data.setData(adminObjList);
			data.setDraw(draw);
			data.setRecordsFiltered(total);
			data.setRecordsFiltered(total);

			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			String json2 = gson.toJson(data);

			return json2;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public AdminAccount findAccountByUsernamePassword(String userName, String password) {
		return adminRepo.findByEmailAndPassword(userName, password);
	}

	public List<AdminAccount> getAdminAccountByEmailAndNotId(String email, int adminId) {
		return adminRepo.findByEmailAndIdNot(email, adminId);
	}

	public List<AdminAccount> getAdminAccountByEmail(String email) {
		return adminRepo.findByEmail(email);
	}

	public AdminAccount saveAdminAccount(AdminForm form, CommonsMultipartFile photoFile, CommonsMultipartFile[] docFiles, String rootFilePath,
			LoginForm loggedInUser, HttpSession session, HttpServletRequest request, Environment env) {
		AdminAccount acc = null;
		if (form.getAdminId() >0) {
			acc = adminRepo.findOne(form.getAdminId());
		} else {
			acc = new AdminAccount();
			acc.setPassword(form.getPassword());
			acc.setCreatedBy(loggedInUser.getUserid());
			acc.setCreatedUser(loggedInUser.getType());
			acc.setCreatedDate( PharmacyUtil.getCurrentTimeStamp() );
		}

		acc.setLastUpdatedBy(loggedInUser.getUserid());
		acc.setLastUpdatedUser(loggedInUser.getType());
		acc.setLastUpdatedDate( PharmacyUtil.getCurrentTimeStamp() );

		acc.setPharmacyId(form.getPharmacyId());
		acc.setStatus(form.getStatus());
		acc.setType(form.getType());
		if (form.getDateRegistrated() != null && !"".equalsIgnoreCase(form.getDateRegistrated()))
			acc.setDateRegistrated( PharmacyUtil.getSqlDateFromString(form.getDateRegistrated()));
		acc.setFirstName(form.getFirstName().trim());
		acc.setMiddleName(form.getMiddleName().trim());
		acc.setLastName(form.getLastName().trim());

		if(form.getMiddleName()!=null && form.getMiddleName().trim().length()>0)
			acc.setName(form.getFirstName().trim() + " " + form.getMiddleName().trim() + " " + form.getLastName().trim());
		else
			acc.setName(form.getFirstName().trim() + " " + form.getLastName().trim());

		acc.setEmail(form.getEmail());
		acc.setMobile(form.getMobile());
		acc.setPhone(form.getPhone());
		AdminAccount rec = adminRepo.save(acc);

		// Admin Permissions data Insert / update
		// In admin_permissions table
		if (rec.getId() > 0) {
			AdminPermissions adminPerm = null;
			adminPerm = adminPermissionRepo.findById(rec.getId());
			if (adminPerm == null) {
				adminPerm = new AdminPermissions();
				adminPerm.setId(rec.getId());
			}

			adminPerm.setAdminAccCreation((form.getAdminAccCreation()==null?"No":form.getAdminAccCreation()));
			adminPerm.setGroupCreation((form.getGroupCreation()==null?"No":form.getGroupCreation()));
			adminPerm.setGroupDirectorCreation((form.getGroupDirectorCreation()==null?"No":form.getGroupDirectorCreation()));
			adminPerm.setClinicCreation((form.getClinicCreation()==null?"No":form.getClinicCreation()));
			adminPerm.setPhysicianCreation((form.getPhysicianCreation()==null?"No":form.getPhysicianCreation()));
			adminPerm.setPhysicianApproval((form.getPhysicianApproval()==null?"No":form.getPhysicianApproval()));
			adminPerm.setAssistantCreation((form.getAssistantCreation()==null?"No":form.getAssistantCreation()));
			adminPerm.setPatientCreation((form.getPatientCreation()==null?"No":form.getPatientCreation()));
			adminPerm.setPatientApproval((form.getPatientApproval()==null?"No":form.getPatientApproval()));
			adminPerm.setPrescriptionCreation((form.getPrescriptionCreation()==null?"No":form.getPrescriptionCreation()));
			adminPerm.setControlSubstanceDocUpload((form.getControlSubstanceDocUpload()==null?"No":form.getControlSubstanceDocUpload()));
			adminPerm.setRemoteFileUpload((form.getRemoteFileUpload()==null?"No":form.getRemoteFileUpload()));

			adminPermissionRepo.save(adminPerm);
		}



		/*
		 * Uploaded Files saving process
		 */
		if (photoFile.getSize() > 0) {
			String folderName="admin";
			String photoFileName="photo_"+rec.getId();
			boolean isUploaded=PharmacyUtil.userPhotoUpload(photoFile, folderName, photoFileName, rootFilePath, rec.getId(), "");
			if (isUploaded){
				String oname=photoFile.getOriginalFilename().toLowerCase();
				String extension = oname.substring(oname.lastIndexOf('.') + 1, oname.length());

				StringBuffer uploadedFile = new StringBuffer(PharmacyUtil.getFileUploadPath(rootFilePath, folderName, rec.getId(), ""));
				uploadedFile.append( File.separator + photoFileName + "." + extension);

				fileUploadService.photoFileSave(rec.getId(), photoFile, uploadedFile.toString(), loggedInUser);

				//System.out.println("Admin Photo uploaded ok");
			} else {
				//System.out.println("Admin Photo not uploaded");
			}
		}
		//Automatic mail triggered is commented out and called from Send Credential Mail button on 10/02/2021
		/*try {
			String loginUrl = PharmacyUtil.getCurrentSystemUrl(request)+"/login?s=2";  // 2 means User Type is Admin

			// Only New Admin at the time of registration will get Mail about new physician registration
			if (form.getAdminId() == 0 && "true".equalsIgnoreCase(env.getProperty("isMailSend"))) {
				String fileName = null;
				String attachmentFileName = null;
				if ("true".equalsIgnoreCase(env.getProperty("includeAttachment"))) {
					// Include attachments here...
					
					 * Attachment including example
					 *
					String attachPath = PharmacyUtil.getRootFolderForPrescriptionPDF(session, env);
					fileName = attachPath + File.separator + "8"  + File.separatorChar + "Prescription_8_esigned.pdf";
					attachmentFileName = "Prescription.pdf"; // attachment filename  
				}

				MailSendConfig mail = new MailSendConfig();
				mail.MailSending(rec, env, "AdminSignup", loginUrl, fileName, attachmentFileName );
			 }
		} catch (Exception e) {
			e.printStackTrace();
		}*/



		return rec;
	}

	public AdminForm getAdminAccountById(int id, String filepath) {
		AdminForm form = new AdminForm();
		AdminAccount acc = adminRepo.findOne(id);

		form.setAdminId(acc.getId());
		form.setPharmacyId(acc.getPharmacyId());
		form.setStatus(acc.getStatus());
		form.setType(acc.getType());
		form.setDateRegistrated( PharmacyUtil.getStringDateFromSqlDate(acc.getDateRegistrated()) );
		form.setFirstName(acc.getFirstName());
		form.setMiddleName(acc.getMiddleName());
		form.setLastName(acc.getLastName());
		form.setAdminName(acc.getName());
		form.setEmail(acc.getEmail());
		form.setPassword(acc.getPassword());
		form.setMobile(acc.getMobile());
		form.setPhone(acc.getPhone());

		form.setCreatedBy(acc.getCreatedBy());
		form.setCreatedUser(acc.getCreatedUser());
		form.setCreatedDate(acc.getCreatedDate());

		form.setLastUpdatedBy(acc.getLastUpdatedBy());
		form.setLastUpdatedUser(acc.getLastUpdatedUser());
		form.setLastUpdatedDate(acc.getLastUpdatedDate());

		form.setPhotoUrl(fileUploadService.photoNameByAdminId(id, filepath)  ) ;

		// Admin Permissions data fetch from admin_permissions table
		AdminPermissions adminPerm = adminPermissionRepo.findById(id);
		if (adminPerm != null) {
			form.setAdminAccCreation(adminPerm.getAdminAccCreation());
			form.setGroupCreation(adminPerm.getGroupCreation());
			form.setGroupDirectorCreation(adminPerm.getGroupDirectorCreation());
			form.setClinicCreation(adminPerm.getClinicCreation());
			form.setPhysicianCreation(adminPerm.getPhysicianCreation());
			form.setPhysicianApproval(adminPerm.getPhysicianApproval());
			form.setAssistantCreation(adminPerm.getAssistantCreation());
			form.setPatientCreation(adminPerm.getPatientCreation());
			form.setPatientApproval(adminPerm.getPatientApproval());
			form.setPrescriptionCreation(adminPerm.getPrescriptionCreation());
			form.setControlSubstanceDocUpload(adminPerm.getControlSubstanceDocUpload());
			form.setRemoteFileUpload(adminPerm.getRemoteFileUpload());
		}

		return form;
	}


	public String getAdminPhotoFileName(int id, String filepath) {
		String file = "";
		if (id > 0) {
			file = fileUploadService.photoNameByAdminId(id, filepath);
		}
		return file;
	}
	public boolean sendPharmacyAdminAccountMail(AdminForm form,LoginForm loggedInUser,HttpSession session, HttpServletRequest request,Environment env) {
		boolean isSend = false;
		try {
		String loginUrl = PharmacyUtil.getCurrentSystemUrl(request)+"/login?s=2";  // 2 means User Type is Admin
	    AdminAccount rec = adminRepo.findOne(form.getAdminId());
	    
		if (rec!=null && form.getAdminId() > 0 && "true".equalsIgnoreCase(env.getProperty("isMailSend"))) {
			String fileName = null;
			String attachmentFileName = null;
			if ("true".equalsIgnoreCase(env.getProperty("includeAttachment"))) {}

			MailSendConfig mail = new MailSendConfig();
			mail.MailSending(rec, env, "AdminSignup", loginUrl, fileName, attachmentFileName );
			isSend=true;
		 }
		
	} catch (Exception e) {
		e.printStackTrace();
		isSend=false;
	}
		return isSend;
		}

}
