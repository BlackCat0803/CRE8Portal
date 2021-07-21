package com.pharma.core.pharmaservices.pharmacygroupservices;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

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
import com.pharma.core.formBean.pharmacygroup.GroupDirectorForm;
import com.pharma.core.formBean.pharmacygroup.GroupDirectorJSonObj;
import com.pharma.core.model.pharmacygroup.GroupDirector;
import com.pharma.core.pharmaservices.mail.MailSendConfig;
import com.pharma.core.repository.pharmacygroup.GroupDirectorAccountRespository;
import com.pharma.core.repository.pharmacygroup.GroupMasterRespository;
import com.pharma.core.util.PharmacyUtil;
/**
 * This is an implementation class that loads and stores the group director data
 */
@Service("GroupDirectorService")
public class GroupDirectorServiceImpl implements GroupDirectorService {

	@Autowired
	GroupDirectorAccountRespository groupDirectorRepo;

	@Autowired
	GroupMasterRespository groupMasterRepo;

	@Autowired
	GroupDirectorFileUploadService fileUploadService;

	public GroupDirector saveGroupDirectorAccount(GroupDirectorForm form, CommonsMultipartFile photoFile, CommonsMultipartFile[] docFiles,
			String rootFilePath, LoginForm loggedInUser, HttpSession session, HttpServletRequest request, Environment env) {
		try {
			GroupDirector acc = null;
			if (form.getGroupDirectorId() >0) {
				acc = groupDirectorRepo.findOne(form.getGroupDirectorId());
			}
			else {
				acc = new GroupDirector();
				acc.setPassword(form.getPassword());

				acc.setCreatedBy(loggedInUser.getUserid());
				acc.setCreatedUser(loggedInUser.getType());
				acc.setCreatedDate( PharmacyUtil.getCurrentTimeStamp() );
			}

			acc.setLastUpdatedBy(loggedInUser.getUserid());
			acc.setLastUpdatedUser(loggedInUser.getType());
			acc.setLastUpdatedDate( PharmacyUtil.getCurrentTimeStamp() );

			acc.setGroupId(form.getGroupId());
			acc.setStatus(form.getStatus());
			if (form.getDateRegistrated() != null && !"".equalsIgnoreCase(form.getDateRegistrated()))
				acc.setDateRegistrated( PharmacyUtil.getSqlDateFromString(form.getDateRegistrated()));
			acc.setFirstName(form.getFirstName().trim());
			acc.setMiddleName(form.getMiddleName().trim());
			acc.setLastName(form.getLastName().trim());

			if(form.getMiddleName()!=null && form.getMiddleName().trim().length()>0)
				acc.setGroupDirectorName(form.getFirstName().trim() + " " + form.getMiddleName().trim() + " " + form.getLastName().trim());
			else
				acc.setGroupDirectorName(form.getFirstName().trim() + " " + form.getLastName().trim());

			acc.setEmail(form.getEmail());
			acc.setMobile(form.getMobile());
			acc.setPhone(form.getPhone());
			GroupDirector rec = groupDirectorRepo.save(acc);

			/*
			 * Uploaded Files saving process
			 */
			if (photoFile.getSize() > 0) {
				String folderName="group_director";
				String photoFileName="photo_"+rec.getId();
				boolean isUploaded=PharmacyUtil.userPhotoUpload(photoFile, folderName, photoFileName, rootFilePath, rec.getId(), "");
				if (isUploaded){
					String oname=photoFile.getOriginalFilename().toLowerCase();
					String extension = oname.substring(oname.lastIndexOf('.') + 1, oname.length()).toLowerCase();

					StringBuffer uploadedFile = new StringBuffer(PharmacyUtil.getFileUploadPath(rootFilePath, folderName, rec.getId(), ""));
					uploadedFile.append( File.separator + photoFileName + "." + extension);

					fileUploadService.photoFileSave(rec.getId(), photoFile, uploadedFile.toString(), loggedInUser);

					//System.out.println("Group Director Photo uploaded ok");
				} else {
					//System.out.println("Group Director Photo not uploaded");
				}
			}
			//Automatic mail triggered is commented out and called from Send Credential Mail button on 10/02/2021 by durgadevi

			/*try {
				String loginUrl = PharmacyUtil.getCurrentSystemUrl(request)+"/login?s=4";  // 4 means User Type is Group Director

				// Only New Admin at the time of registration will get Mail about new physician registration
				if (form.getGroupDirectorId() == 0 && "true".equalsIgnoreCase(env.getProperty("isMailSend"))) {
					String fileName = null;
					String attachmentFileName = null;
					if ("true".equalsIgnoreCase(env.getProperty("includeAttachment"))) {
						// Include attachments here...

					}

					MailSendConfig mail = new MailSendConfig();
					mail.MailSending(rec, env, "DirectorSignup", loginUrl, fileName, attachmentFileName );
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

	public List<GroupDirector> getGroupDirectorByEmail(String email) {
		return groupDirectorRepo.findByEmail(email);
	}



	public String getGroupDirectorDataList(int draw, int start, int length, String searchTerm, int orderColumn, String orderDir,
			String groupDirectorName, int groupName, String status) {

		try {

			if (groupDirectorName == null)  groupDirectorName = "";
			if (status == null) status = "";

			int total = 0;
			int pageNumber = 0;
			Pageable page = null;
			Page<GroupDirector> groupDirectorList = null;

			Direction dir = Direction.ASC;
			if (orderDir.equalsIgnoreCase("desc"))
				dir = Direction.DESC;

			try {
				if ("".equalsIgnoreCase(groupDirectorName) && groupName==0 && "".equalsIgnoreCase(status)) {
						total = (int) groupDirectorRepo.findByAllStatus();
				} else {
					if (groupDirectorName.length()>0 && groupName>0 && status.length()>0) {
						total = (int) groupDirectorRepo.findByGroupIdGroupDirectorNameAndStatus(groupName, groupDirectorName, status);
					}else
					{

						if (groupDirectorName.length()>0 && groupName>0) {
							total = (int) groupDirectorRepo.findByGroupIdGroupDirectorName(groupName, groupDirectorName);
						}else if (groupName>0 && status.length()>0) {
							total = (int) groupDirectorRepo.findByGroupIdAndStatus(groupName,  status);
						}else if (groupDirectorName.length()>0 && status.length()>0) {
							total = (int) groupDirectorRepo.findByGroupDirectorNameAndStatus(groupDirectorName, status);
						}else if (groupDirectorName.length()>0) {
							total = (int) groupDirectorRepo.findByGroupDirectorName(groupDirectorName);
						}else if (groupName>0) {
							total = (int) groupDirectorRepo.findByGroupId(groupName);
						}else if (status.length()>0) {
							total = (int) groupDirectorRepo.findByStatus(status);
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

				if ("".equalsIgnoreCase(groupDirectorName) && groupName==0 && "".equalsIgnoreCase(status)) {
					groupDirectorList =  groupDirectorRepo.findByAllStatus(page);
				} else {
					if (groupDirectorName.length()>0 && groupName>0 && status.length()>0) {
						groupDirectorList =  groupDirectorRepo.findByGroupIdGroupDirectorNameAndStatus(groupName, groupDirectorName, status, page);
					}else
					{

						if (groupDirectorName.length()>0 && groupName>0) {
							groupDirectorList =  groupDirectorRepo.findByGroupIdGroupDirectorName(groupName, groupDirectorName, page);
						}else if (groupName>0 && status.length()>0) {
							groupDirectorList =  groupDirectorRepo.findByGroupIdAndStatus(groupName,  status, page);
						}else if (groupDirectorName.length()>0 && status.length()>0) {
							groupDirectorList =  groupDirectorRepo.findByGroupDirectorNameAndStatus(groupDirectorName, status, page);
						}else if (groupDirectorName.length()>0) {
							groupDirectorList =  groupDirectorRepo.findByGroupDirectorName(groupDirectorName, page);
						}else if (groupName>0) {
							groupDirectorList =  groupDirectorRepo.findByGroupId(groupName, page);
						}else if (status.length()>0) {
							groupDirectorList =  groupDirectorRepo.findByStatus(status, page);
						}

					}

				}

			} catch(Exception e) {
				e.printStackTrace();
			}
			List<GroupDirectorForm> phyAssObjList = new ArrayList<GroupDirectorForm>();
			SimpleDateFormat dt = new SimpleDateFormat("MM/dd/yyyy");

			if (groupDirectorList != null && groupDirectorList.getSize() > 0 ) {
				for(GroupDirector a: groupDirectorList) {
					GroupDirectorForm obj = new GroupDirectorForm();
					obj.setGroupDirectorId(a.getId());
					obj.setGroupDirectorName(a.getGroupDirectorName());
					obj.setEmail(a.getEmail());
					obj.setGroupName(groupMasterRepo.findOne(a.getGroupId()).getGroupName());
					obj.setStatus(a.getStatus());
					obj.setDateRegistrated(dt.format(a.getDateRegistrated()));
					obj.setDT_RowId("row_" + a.getId());

					phyAssObjList.add(obj);
				}
			}

			GroupDirectorJSonObj data = new GroupDirectorJSonObj();
			data.setData(phyAssObjList);
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


	public GroupDirectorForm getGroupDirectorById(int id, String filepath) {

		GroupDirectorForm form = new GroupDirectorForm();

		try {
			GroupDirector acc = groupDirectorRepo.findOne(id);

			form.setGroupDirectorId(acc.getId());
			form.setGroupId(acc.getGroupId());
			form.setGroupName(groupMasterRepo.findOne(acc.getGroupId()).getGroupName());
			form.setStatus(acc.getStatus());
			form.setDateRegistrated( PharmacyUtil.getStringDateFromSqlDate(acc.getDateRegistrated()) );
			form.setFirstName(acc.getFirstName());
			form.setMiddleName(acc.getMiddleName());
			form.setLastName(acc.getLastName());
			form.setGroupDirectorName(acc.getGroupDirectorName());
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

			if (!"".equalsIgnoreCase(filepath)) {
				form.setPhotoFile(fileUploadService.photoNameByGroupDirectorId(id, filepath) );
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return form;
	}

	public List<GroupDirector> getGroupDirectorByEmailAndNotId(String email, int id) {
		return groupDirectorRepo.findByEmailAndIdNot(email, id);
	}

	public GroupDirector getGroupDirectorDetails(int id) {
		return groupDirectorRepo.findOne(id);
	}


	public String getGroupDirectorPhotoFileName(int id, String filepath) {
		String file = "";
		if (id > 0) {
			file = fileUploadService.photoNameByGroupDirectorId(id, filepath);
		}
		return file;
	}

	public boolean sendGroupDirectorMail(GroupDirectorForm form,LoginForm loggedInUser, HttpSession session,HttpServletRequest request, Environment env) {
		boolean isSend = false;
		try {
		String loginUrl = PharmacyUtil.getCurrentSystemUrl(request)+"/login?s=2";  // 2 means User Type is Admin
	    GroupDirector rec = groupDirectorRepo.findOne(form.getGroupDirectorId());
	    
		if (rec!=null && form.getGroupDirectorId() > 0 && "true".equalsIgnoreCase(env.getProperty("isMailSend"))) {
			String fileName = null;
			String attachmentFileName = null;
			if ("true".equalsIgnoreCase(env.getProperty("includeAttachment"))) {
				//attachment
			}

			MailSendConfig mail = new MailSendConfig();
			mail.MailSending(rec, env, "DirectorSignup", loginUrl, fileName, attachmentFileName );
			isSend=true;

		 }
	} catch (Exception e) {
		e.printStackTrace();
		isSend=false;
	}
		return isSend;
	}

}
