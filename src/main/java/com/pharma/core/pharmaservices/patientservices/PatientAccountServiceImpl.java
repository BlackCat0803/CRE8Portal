package com.pharma.core.pharmaservices.patientservices;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
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
import com.pharma.core.events.patient.PatientEvent;
import com.pharma.core.formBean.DocumentFileList;
import com.pharma.core.formBean.LoginForm;
import com.pharma.core.formBean.patient.PatientAccountForm;
import com.pharma.core.formBean.patient.PatientAccountJSonObj;
import com.pharma.core.formBean.physician.PhysicianDocFileJSonObj;
import com.pharma.core.model.notifications.Notification;
import com.pharma.core.model.patientaccount.PatientAccount;
import com.pharma.core.model.patientaccount.PatientFileUpload;
import com.pharma.core.model.patientaccount.PatientGroup;
import com.pharma.core.model.patientaccount.PatientPhysicians;
import com.pharma.core.model.patientaccount.PatientUpdateHistory;
import com.pharma.core.model.pharmacygroup.GroupMaster;
import com.pharma.core.model.physician.PhysicianAccount;
import com.pharma.core.model.physician.PhysicianProfileInfo;
import com.pharma.core.pharmaservices.mail.MailSendConfig;
import com.pharma.core.pharmaservices.notifications.NotificationService;
import com.pharma.core.pharmaservices.pharmacygroupservices.GroupMasterService;
import com.pharma.core.pharmaservices.physicianservices.PhysicianAccountService;
import com.pharma.core.pharmaservices.physicianservices.PhysicianGroupService;
import com.pharma.core.repository.admin.AdminAccountRepository;
import com.pharma.core.repository.clinic.ClinicRepository;
import com.pharma.core.repository.patient.PatientAccountRespository;
import com.pharma.core.repository.patient.PatientGroupRepository;
import com.pharma.core.repository.patient.PatientPhysiciansRepository;
import com.pharma.core.repository.patient.PatientUpdateHistoryRepository;
import com.pharma.core.repository.pharmacygroup.GroupDirectorAccountRespository;
import com.pharma.core.repository.pharmacygroup.GroupMasterRespository;
import com.pharma.core.repository.physician.PhysicianAccountRespository;
import com.pharma.core.repository.physician.PhysicianAssistantAccountRespository;
import com.pharma.core.repository.physician.PhysicianGroupRepository;
import com.pharma.core.util.PharmacyUtil;
/**
 * This is an implementation class that loads and stores the patient account data
 */
@Service("patientAccountService")
@PropertySource("classpath:webService.properties")
public class PatientAccountServiceImpl implements PatientAccountService  {

	@Autowired
	PatientAccountRespository patientAccountResp;

	@Autowired
	PhysicianAccountRespository physicianRep;

	@Autowired
	PatientFileUploadService fileUploadService;

	@Autowired
	PhysicianAssistantAccountRespository assistantRepo;

	@Autowired
	AdminAccountRepository adminRepo;

	@Autowired
	GroupMasterService groupService;

	@Autowired
	PhysicianGroupService phyGroupService;

	@Autowired
	PhysicianGroupRepository phyGroupRepo;

	@Autowired
	private ApplicationEventPublisher publisher;

	@Autowired
	GroupDirectorAccountRespository groupDirRepo;

	@Autowired
	ClinicRepository clinicRepo;

	@Autowired
	PhysicianAccountService phyAcctService;

	@Autowired
	PatientPhysiciansRepository patPhysiciansRepo;

	@Autowired
	PatientGroupRepository patGroupRepo;

	@Autowired
	GroupMasterRespository groupMasterAccount;

	@Autowired
	private Environment env;

	@Autowired
	private PatientUpdateHistoryRepository updatedHistoryRepo;

	@Autowired
	NotificationService notificationService;

	private boolean compareWithPreviousData(PatientAccountForm form, PatientAccount acc) {
		boolean isUpdated = false;

		// First Name
		if ( (form.getFirstName() == null && acc.getFirstName() != null) || (form.getFirstName() != null && acc.getFirstName() == null) ||
				(form.getFirstName() != null && acc.getFirstName() != null && !form.getFirstName().equalsIgnoreCase(acc.getFirstName())) )
			isUpdated=true;

		// Middle Name
		if ( (form.getMiddleName() == null && acc.getMiddleName() != null) || (form.getMiddleName() != null && acc.getMiddleName() == null) ||
				(form.getMiddleName() != null && acc.getMiddleName() != null && !form.getMiddleName().equalsIgnoreCase(acc.getMiddleName())) )
			isUpdated = true;

		// Last Name
		if ( (form.getLastName() == null && acc.getLastName() != null) || (form.getLastName() != null && acc.getLastName() == null) ||
				(form.getLastName() != null && acc.getLastName() != null && !form.getLastName().equalsIgnoreCase(acc.getLastName())) )
			isUpdated = true;

		// Street (Address1)
		if ( (form.getAddress()  == null && acc.getAddress() != null) || (form.getAddress() != null && acc.getAddress() == null) ||
				(form.getAddress() != null && acc.getAddress() != null && !form.getAddress().equalsIgnoreCase(acc.getAddress())) )
			isUpdated = true;

		// City
		if ( (form.getCity() == null && acc.getCity() != null) || (form.getCity() != null && acc.getCity() == null) ||
				(form.getCity() != null && acc.getCity() != null && !form.getCity().equalsIgnoreCase(acc.getCity())) )
			isUpdated = true;

		// State
		if ( (form.getState() == null && acc.getState() != null) || (form.getState() != null && acc.getState() == null) ||
				(form.getState() != null && acc.getState() != null && !form.getState().equalsIgnoreCase(acc.getState())) )
			isUpdated = true;

		// Zip code
		if ( (form.getZipCode() == null && acc.getZipCode() != null) || (form.getZipCode() != null && acc.getZipCode() == null) ||
				(form.getZipCode() != null && acc.getZipCode() != null && !form.getZipCode().equalsIgnoreCase(acc.getZipCode())) )
			isUpdated = true;

		// phone
		if ( (form.getPhone() == null && acc.getPhone() != null) || (form.getPhone() != null && acc.getPhone() == null) ||
				(form.getPhone() != null && acc.getPhone() != null && !form.getPhone().equalsIgnoreCase(acc.getPhone())) )
			isUpdated = true;

		// mobile
		if ( (form.getMobile() == null && acc.getMobile() != null) || (form.getMobile() != null && acc.getMobile() == null) ||
				(form.getMobile() != null && acc.getMobile() != null && !form.getMobile().equalsIgnoreCase(acc.getMobile())) )
			isUpdated = true;

		// email
		if ( (form.getEmail() == null && acc.getEmail() != null) || (form.getEmail() != null && acc.getEmail() == null) ||
				(form.getEmail() != null && acc.getEmail() != null && !form.getEmail().equalsIgnoreCase(acc.getEmail())) )
			isUpdated = true;

		// physician==>comes from physician id
		/*if ( form.getPhysicianId() != acc.getPhysicianId())
			isUpdated = true;	*/
		//Commented on Feb 7,2018-Multiple Groups to be handled
		/*if ( form.getGroupId() != acc.getGroupId())
			isUpdated = true;	*/

		// ssn
		if ( (form.getSsn() == null && acc.getSsn() != null) || (form.getSsn() != null && acc.getSsn() == null) ||
				(form.getSsn() != null && acc.getSsn() != null && !form.getSsn().equalsIgnoreCase(acc.getSsn())) )
			isUpdated = true;

		// licenseExpDate
		SimpleDateFormat dt = new SimpleDateFormat("MM/dd/yyyy");
		String accExpDate = "";
		if (acc.getLicenseExpDate() != null)
			accExpDate = dt.format(acc.getLicenseExpDate());

		if ( (form.getLicenseExpDate() == null && acc.getLicenseExpDate() != null) || (form.getLicenseExpDate() != null && acc.getLicenseExpDate() == null) ||
				(form.getLicenseExpDate() != null && acc.getLicenseExpDate() != null && !form.getLicenseExpDate().equalsIgnoreCase(accExpDate)) )
			isUpdated = true;


		// driversLicenseState
		if ( (form.getDriversLicenseState() == null && acc.getDriversLicenseState() != null) || (form.getDriversLicenseState() != null && acc.getDriversLicenseState() == null) ||
				(form.getDriversLicenseState() != null && acc.getDriversLicenseState() != null && !form.getDriversLicenseState().equalsIgnoreCase(acc.getDriversLicenseState())) )
			isUpdated = true;

		// driversLicense
		if ( (form.getDriversLicense() == null && acc.getDriversLicense() != null) || (form.getDriversLicense() != null && acc.getDriversLicense() == null) ||
				(form.getDriversLicense() != null && acc.getDriversLicense() != null && !form.getDriversLicense().equalsIgnoreCase(acc.getDriversLicense())) )
			isUpdated = true;

		// dateOfBirth
		if (acc.getDateOfBirth() != null) {
			String accDob = dt.format(acc.getDateOfBirth());
			if ( (form.getDateOfBirth() == null && acc.getDateOfBirth() != null) || (form.getDateOfBirth() != null && acc.getDateOfBirth() == null) ||
					(form.getDateOfBirth() != null && acc.getDateOfBirth() != null && !form.getDateOfBirth().equalsIgnoreCase(accDob)) )
				isUpdated = true;
		}

		//gender
		if ( (form.getGender() == null && acc.getGender() != null) || (form.getGender() != null && acc.getGender() == null) ||
				(form.getGender() != null && acc.getGender() != null && !form.getGender().equalsIgnoreCase(acc.getGender())) )
			isUpdated = true;

		return isUpdated;
	}





	public PatientAccount save(PatientAccountForm form, CommonsMultipartFile patientPhoto, String rootFilePath, LoginForm loggedInUser,
			HttpSession session, HttpServletRequest request, Environment env) {
		PatientAccount acc = null;
		int prevSyncStatus=-1;
		boolean isAnyUpdates = false;
		boolean isDenied = false;
		String previousStatus="";

		boolean isNeedApproval = false;
		boolean isCriticalCmtChanged = false;


		if (form.getPatientId() > 0) {
			acc = patientAccountResp.findOne(form.getPatientId());
			prevSyncStatus=acc.getSyncStatus();
			previousStatus=acc.getStatus();

			isNeedApproval = compareWithPreviousData(form, acc);
			if (!isNeedApproval && !acc.getStatus().equalsIgnoreCase(form.getStatus()))
					isNeedApproval = true;

			// Comparing previously stored value with newly entered values for changing Status to "New Modification"
			// work done by vijayakumar @ 20-jan-2018
			if (PharmacyUtil.statusApproved.equalsIgnoreCase(acc.getStatus()) ||
					(PharmacyUtil.statusDenied.equalsIgnoreCase(acc.getStatus())
							&& !PharmacyUtil.statusDenied.equalsIgnoreCase(form.getStatus())) )
				isAnyUpdates = compareWithPreviousData(form, acc);

			if (!PharmacyUtil.statusDenied.equalsIgnoreCase(acc.getStatus()) && (PharmacyUtil.statusDenied.equalsIgnoreCase(form.getStatus())) ) {
				acc.setDeniedBy(loggedInUser.getUserid());
				acc.setDeniedUser(loggedInUser.getType());
				acc.setDeniedDate(PharmacyUtil.getCurrentTimeStamp());
				isDenied = true;
			}
			if (!PharmacyUtil.statusApproved.equalsIgnoreCase(acc.getStatus()) && (PharmacyUtil.statusApproved.equalsIgnoreCase(form.getStatus())) ) {
				acc.setApprovedBy(loggedInUser.getUserid());
				acc.setApprovedUser(loggedInUser.getType());
				acc.setApprovedDate(PharmacyUtil.getCurrentTimeStamp());
			}

			// Critical Comments updated notifications purpose
			if (!isDenied && acc.getCriticalComments()!=null && form.getCriticalComments()!=null && !acc.getCriticalComments().equalsIgnoreCase(form.getCriticalComments()))
				isCriticalCmtChanged = true;

			if (isAnyUpdates)
				acc.setStatus(PharmacyUtil.statusNewModified);
			else
				acc.setStatus(form.getStatus());
		}
		else {
			acc = new PatientAccount();
			acc.setPassword(form.getPassword());
			acc.setStatus(form.getStatus());

			acc.setCreatedBy(loggedInUser.getUserid());
			acc.setCreatedUser(loggedInUser.getType());
			acc.setCreatedDate( PharmacyUtil.getCurrentTimeStamp() );
		}

		acc.setLastUpdatedBy(loggedInUser.getUserid());
		acc.setLastUpdatedUser(loggedInUser.getType());
		acc.setLastUpdatedDate( PharmacyUtil.getCurrentTimeStamp() );

		acc.setUpdatedBy(loggedInUser.getUserid());
		acc.setUpdatedByType(loggedInUser.getType());
		acc.setUpdatedDate(new java.sql.Date(new Date().getTime()));

		//Commented on jan 22,2018
		//acc.setPhysicianId(form.getPhysicianId());
		if (!PharmacyUtil.statusDenied.equalsIgnoreCase(acc.getStatus())) {
			//Commented on Feb 7,2018-Multiple Groups to be handled
			//acc.setGroupId(form.getGroupId());
			acc.setPatientCode(form.getPatientCode());

			if (form.getDateRegistered() != null && !"".equalsIgnoreCase(form.getDateRegistered()))
				acc.setDateRegistered( PharmacyUtil.getSqlDateFromString(form.getDateRegistered()));

			acc.setFirstName(form.getFirstName().trim());
			acc.setMiddleName(form.getMiddleName().trim());
			acc.setLastName(form.getLastName().trim());

			if(form.getMiddleName()!=null && form.getMiddleName().trim().length()>0)
				acc.setPatientName(form.getFirstName().trim() + " " + form.getMiddleName().trim() + " " + form.getLastName().trim());
			else
				acc.setPatientName(form.getFirstName().trim() + " " + form.getLastName().trim());

			acc.setUserLoginId(form.getUserLoginId());
			acc.setEmail(form.getEmail());
			acc.setMobile(form.getMobile());
			acc.setPhone(form.getPhone());
			acc.setNotifyNo(form.getNotifyNo());
			acc.setRxNotify(form.getRxNotify());
			acc.setRefillRenewal(form.getRefillRenewal());
			if (form.getDateOfBirth() != null && !"".equalsIgnoreCase(form.getDateOfBirth()))
				acc.setDateOfBirth( PharmacyUtil.getSqlDateFromString(form.getDateOfBirth()));
			acc.setGender(form.getGender());
			acc.setAllergies(form.getAllergies());
			acc.setOtherMedications(form.getOtherMedications());
			acc.setMedicalConditions(form.getMedicalConditions());
			acc.setCriticalComments(form.getCriticalComments());
			acc.setDriversLicense(form.getDriversLicense());
			if (form.getLicenseExpDate() != null && !"".equalsIgnoreCase(form.getLicenseExpDate()))
				acc.setLicenseExpDate( PharmacyUtil.getSqlDateFromString(form.getLicenseExpDate()));
			acc.setDriversLicenseState(form.getDriversLicenseState());
			acc.setSsn(form.getSsn());
			acc.setAddress(form.getAddress());
			acc.setCity(form.getCity());
			acc.setMobile(form.getMobile());
			acc.setState(form.getState());
			acc.setZipCode(form.getZipCode());
			acc.setCountry(form.getCountry());
			acc.setPregnancyprecaution(form.getPregnancyprecaution());

			acc.setRxNotifyProviderTypeID(form.getRxNotifyProviderTypeID());
			acc.setSyncStatus(form.getSyncStatus());
			if(prevSyncStatus!=form.getSyncStatus())
				acc.setSyncStatusChangedDate(new java.sql.Date(new Date().getTime()));

			acc.setCardCvcNumber(form.getCardCvcNumber());
			acc.setCardHolderName(form.getCardHolderName());
			acc.setCardNumber(form.getCardNumber());
			acc.setCardType(form.getCardType());
			acc.setExpMonth(form.getExpMonth());
			acc.setExpYear(form.getExpYear());
			acc.setSendMailPermission(form.getSendMailPermission());

			acc.setCommEmail(form.getCommEmail());
			acc.setCommPhone(form.getCommPhone());
			acc.setCommTrackingNo(form.getCommTrackingNo());
			acc.setCommShipped(form.getCommShipped());
			acc.setCommDelivered(form.getCommDelivered());
			acc.setCommDeliveryExceptions(form.getCommDeliveryExceptions());

			acc.setAlternateId(form.getAlternateId());
			acc.setAlternateIdTypeId(form.getAlternateIdTypeId());
			acc.setAlternateIdTypeText(form.getAlternateIdTypeText());

			acc.setCommentsUpdateInPioneer(form.getCommentsUpdateInPioneer());
		}
		PatientAccount rec = patientAccountResp.save(acc);

		/*
		 * Uploaded Files saving process
		 */
		if (patientPhoto.getSize() > 0) {
			String folderName="patient";
			String photoFileName="photo_"+rec.getId();
			boolean isUploaded=PharmacyUtil.userPhotoUpload(patientPhoto, folderName, photoFileName, rootFilePath, rec.getId(), "");
			if (isUploaded){
				String oname=patientPhoto.getOriginalFilename().toLowerCase();
				String extension = oname.substring(oname.lastIndexOf('.') + 1, oname.length()).toLowerCase();

				StringBuffer uploadedFile = new StringBuffer(PharmacyUtil.getFileUploadPath(rootFilePath, folderName, rec.getId(), ""));
				uploadedFile.append( File.separator + photoFileName + "." + extension);

				fileUploadService.photoFileSave(rec.getId(), patientPhoto, uploadedFile.toString(), loggedInUser);

				//System.out.println("Photo uploaded ok");
			} else {
				//System.out.println("Photo not uploaded");
			}
		}

		/*
		 * Saving records in Patient Physicians table
		 */
		StringBuilder physicianNameSb = new StringBuilder();
		try {

			if (!PharmacyUtil.statusDenied.equalsIgnoreCase(acc.getStatus())) {
				// Deleted existing Physician records
				List<PatientPhysicians> patientPhyList =  patPhysiciansRepo.findByPatientId(rec.getId());
				if (patientPhyList.size() > 0) {
					for (PatientPhysicians c: patientPhyList) {
						c.setDelFlag(PharmacyUtil.deleteFlagYes);
						c.setUpdatedBy(loggedInUser.getUserid());
						c.setUpdatedDate(PharmacyUtil.getCurrentTimeStamp());
						c.setUpdatedUserType(loggedInUser.getType());
						patPhysiciansRepo.save(c);
					}
				}

				if (!"".equalsIgnoreCase( form.getSelectedPhysicianId())) {
					String[] idList = form.getSelectedPhysicianId().split(",");
					if (idList.length > 0) {
						for (int i=0; i < idList.length; i++) {
							PatientPhysicians obj = null;
							obj = patPhysiciansRepo.findByPatientIdAndPhysicianId(rec.getId(), Integer.parseInt(idList[i]));
							if (obj == null) {
								obj = new PatientPhysicians();
								obj.setPatientId(rec.getId());
								obj.setCreatedBy(loggedInUser.getUserid());
								obj.setCreatedDate(PharmacyUtil.getCurrentTimeStamp());
								obj.setCreatedUserType(loggedInUser.getType());

								physicianNameSb.append( physicianRep.findOne(Integer.parseInt(idList[i])).getPhysicianName()).append(",");
							}
							obj.setPhysicianId(Integer.parseInt(idList[i]));
							obj.setDelFlag(PharmacyUtil.deleteFlagNo);

							obj.setUpdatedBy(loggedInUser.getUserid());
							obj.setUpdatedDate(PharmacyUtil.getCurrentTimeStamp());
							obj.setUpdatedUserType(loggedInUser.getType());

							patPhysiciansRepo.save(obj);
						}
					}
				}

				// Deleted existing Group records
				List<PatientGroup> patientGrpList =  patGroupRepo.findByPatientId(rec.getId());
				if (patientGrpList.size() > 0) {
					for (PatientGroup c: patientGrpList) {
						c.setDelFlag(PharmacyUtil.deleteFlagYes);
						c.setUpdatedBy(loggedInUser.getUserid());
						c.setUpdatedDate(PharmacyUtil.getCurrentTimeStamp());
						c.setUpdatedUserType(loggedInUser.getType());
						patGroupRepo.save(c);
					}
				}

				if (form.getSelectedGroupId()!=null && !"".equalsIgnoreCase( form.getSelectedGroupId())) {
					String[] idList = form.getSelectedGroupId().split(",");
					if (idList.length > 0) {
						for (int i=0; i < idList.length; i++) {
							PatientGroup obj = null;
							obj = patGroupRepo.findByPatientIdAndGroupId(rec.getId(), Integer.parseInt(idList[i]));
							if (obj == null) {
								obj = new PatientGroup();
								obj.setPatientId(rec.getId());
								obj.setCreatedBy(loggedInUser.getUserid());
								obj.setCreatedDate(PharmacyUtil.getCurrentTimeStamp());
								obj.setCreatedUserType(loggedInUser.getType());
							}
							obj.setGroupId(Integer.parseInt(idList[i]));
							obj.setDelFlag(PharmacyUtil.deleteFlagNo);

							obj.setUpdatedBy(loggedInUser.getUserid());
							obj.setUpdatedDate(PharmacyUtil.getCurrentTimeStamp());
							obj.setUpdatedUserType(loggedInUser.getType());

							patGroupRepo.save(obj);
						}
					}
				}
			}

			// Added on Jan 23,2018, Pioneer Uid updated if SSN value already existing in another group with pioneer uid...
			// update the same pioneer uid in the current record for the same SSN of different group
			// based on discussion with sudha madam on 18-jan-2018 updated by shalini
			if (rec.getPioneerUid() == null || "".equalsIgnoreCase(rec.getPioneerUid())) {
				String pioneerId = getPioneerUIdBySSN(form.getSsn());
				rec.setPioneerUid(pioneerId);
				rec.setPioneerResponse(0);
				patientAccountResp.save(rec);
			}

			// Storing updated history to patient updated table....
			// implemented by vijayakumar @ 23-jan-2018
			if (form.getPatientId() > 0) {
				PatientUpdateHistory history = new PatientUpdateHistory();

				history.setPatientId(form.getPatientId());
				history.setUpdatedDate(PharmacyUtil.getCurrentTimeStamp());
				history.setUpdatedBy(loggedInUser.getUserid());
				history.setUpdatedUser(loggedInUser.getType());
				history.setDescription("Patient record updated.");

				if (PharmacyUtil.statusApproved.equalsIgnoreCase(acc.getStatus())) {
					history.setApprovedBy(loggedInUser.getUserid());
					history.setApprovedDate(PharmacyUtil.getCurrentTimeStamp());
					history.setApprovedUser(loggedInUser.getType());
				}
				updatedHistoryRepo.save(history);

				if ("true".equalsIgnoreCase(env.getProperty("isMailSend")) && !PharmacyUtil.statusApproved.equalsIgnoreCase(acc.getStatus())
						&& !PharmacyUtil.statusDenied.equalsIgnoreCase(acc.getStatus())) {
					String fileName = null;
					String attachmentFileName = null;
					String loginUrl = PharmacyUtil.getCurrentSystemUrl(request)+"/patientlogin";

					MailSendConfig mail = new MailSendConfig();
					mail.MailSending(rec, env, "PatientApproval", loginUrl, fileName, attachmentFileName );
				}
			}

			/*SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
			StringBuilder sb = new StringBuilder();
			sb.append( form.getPatientName() ).append(",");
			sb.append( sdf.format( new Date() ));*/

			if (isNeedApproval) {
				approvalPatientNotification(form.getPatientId(), loggedInUser);
			}

			if (isDenied) {
				deniedPhysicianNotification(form.getPatientId(), loggedInUser);
			}

			if (physicianNameSb != null && !"".equalsIgnoreCase(physicianNameSb.toString().trim())) {
				newPhysicianAddedByPatient(form.getPatientId(), loggedInUser, physicianNameSb.toString());
			}

			if (isCriticalCmtChanged) {
				criticalCommentsChangedByPatient(form.getPatientId(), loggedInUser);
			} else if ("Yes".equalsIgnoreCase(form.getCommentsUpdateInPioneer())){
				cancelCriticalCommentsNotificationByPatient(form.getPatientId());
			}

			// Pushing data to Pioneer Server
			PublishPatientPushEvent(rec,previousStatus);

			// Sending mails to patient and admins / group directors
			String loginUrl = PharmacyUtil.getCurrentSystemUrl(request)+"/patientlogin";
			//Automatic mail triggered is commented out and called from Send Credential Mail button on 10/02/2021 by durgadevi

			// Only New Patient at the time of registration will get Mail about new patient registration
			/*if (form.getPatientId() == 0 && "true".equalsIgnoreCase(env.getProperty("isMailSend"))) {

				String fileName = null;
				String attachmentFileName = null;
				if ("true".equalsIgnoreCase(env.getProperty("includeAttachment"))) {
					// Include attachments here...

				}

				MailSendConfig mail = new MailSendConfig();
				mail.MailSending(rec, env, "PatientSignup", loginUrl, fileName, attachmentFileName );
			}*/
		} catch(Exception e) {
			e.printStackTrace();
		}

		return  rec;
	}
	/**
		Added on Feb 8,2018=>Check for Patient Name and DOB for already existing for other group or other physicians,
		if so add them to new group or new physician
	 */
	public void saveNewGroupAlreadyExistingPatient(PatientAccountForm form,int alreadyExistingPatientId, LoginForm loggedInUser)
	{
		try{

			if (!"".equalsIgnoreCase( form.getSelectedGroupId())) {
				String[] idList = form.getSelectedGroupId().split(",");
				if (idList.length > 0) {
					for (int i=0; i < idList.length; i++) {
						PatientGroup obj = null;
						obj = patGroupRepo.findByPatientIdAndGroupId(alreadyExistingPatientId, Integer.parseInt(idList[i]));
						if (obj == null) {
							obj = new PatientGroup();
							obj.setPatientId(alreadyExistingPatientId);
							obj.setCreatedBy(loggedInUser.getUserid());
							obj.setCreatedDate(PharmacyUtil.getCurrentTimeStamp());
							obj.setCreatedUserType(loggedInUser.getType());
						}
						obj.setGroupId(Integer.parseInt(idList[i]));
						obj.setDelFlag(PharmacyUtil.deleteFlagNo);

						obj.setUpdatedBy(loggedInUser.getUserid());
						obj.setUpdatedDate(PharmacyUtil.getCurrentTimeStamp());
						obj.setUpdatedUserType(loggedInUser.getType());

						patGroupRepo.save(obj);
					}
				}
			}

		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	/**
		Added on Feb 8,2018=>Check for Patient Name and DOB for already existing for other group or other physicians,
		if so add them to new group or new physician
	 */
	public void saveNewPhysicianAlreadyExistingPatient(PatientAccountForm form,int alreadyExistingPatientId, LoginForm loggedInUser)
	{
		try{

			if (!"".equalsIgnoreCase( form.getSelectedPhysicianId())) {
				String[] idList = form.getSelectedPhysicianId().split(",");
				if (idList.length > 0) {
					for (int i=0; i < idList.length; i++) {
						PatientPhysicians obj = null;
						obj = patPhysiciansRepo.findByPatientIdAndPhysicianId(alreadyExistingPatientId, Integer.parseInt(idList[i]));
						if (obj == null) {
							obj = new PatientPhysicians();
							obj.setPatientId(alreadyExistingPatientId);
							obj.setCreatedBy(loggedInUser.getUserid());
							obj.setCreatedDate(PharmacyUtil.getCurrentTimeStamp());
							obj.setCreatedUserType(loggedInUser.getType());
						}
						obj.setPhysicianId(Integer.parseInt(idList[i]));
						obj.setDelFlag(PharmacyUtil.deleteFlagNo);

						obj.setUpdatedBy(loggedInUser.getUserid());
						obj.setUpdatedDate(PharmacyUtil.getCurrentTimeStamp());
						obj.setUpdatedUserType(loggedInUser.getType());

						patPhysiciansRepo.save(obj);
					}
				}
			}

		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	private String getPioneerUIdBySSN(String ssn) {
		String pioneerId = "";
		try {
			List<PatientAccount> accList = patientAccountResp.getPioneerIdBySSN(ssn);
			if (accList.size() > 0)
				pioneerId = accList.get(0).getPioneerUid();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return pioneerId;
	}

	public PatientAccountForm getPatientData(int id, String filepath) {
        PatientAccount form = patientAccountResp.findOne(id);
        PatientAccountForm acc = new PatientAccountForm();

        //Commented on jan 22,2018
        //PhysicianAccount phyAcc = physicianRep.findOne(form.getPhysicianId());
		//PhysicianGroup phyGroup = phyGroupRepo.findRecordByPhysicianId(phyAcc.getId());


        acc.setPatientId(form.getId());
        //acc.setPhysicianId(form.getPhysicianId());
        //Commented on Feb 7,2018-Multiple Groups to be handled
        //acc.setGroupId(form.getGroupId());
        /*if (acc.getGroupId() > 0)
        	acc.setGroupName(groupService.getGroupMasterDetails(form.getGroupId()).getGroupName());
        else
        	acc.setGroupName("");*/
		acc.setPatientCode(form.getPatientCode());
		acc.setStatus(form.getStatus());
		acc.setFirstName(form.getFirstName());
		acc.setMiddleName(form.getMiddleName());
		acc.setLastName(form.getLastName());
		acc.setPatientName(form.getPatientName());
		acc.setUserLoginId(form.getUserLoginId());
		acc.setEmail(form.getEmail());
	    acc.setPassword(form.getPassword());
		acc.setMobile(form.getMobile());
		acc.setPhone(form.getPhone());
		acc.setPasswordResetDate( (form.getPasswordResetDate()));
		acc.setLoginAttempts(form.getLoginAttempts());
		acc.setLoginLockedDate(form.getLoginLockedDate());
		acc.setSecurityQuestionNumber(form.getSecurityQuestionNumber());
		acc.setSecurityQuestionNumber2(form.getSecurityQuestionNumber2());
		acc.setSecurityAnswer2(form.getSecurityAnswer2());
		acc.setSecurityAnswer(form.getSecurityAnswer());
		acc.setForgotPasswordAttempts(form.getForgotPasswordAttempts());
		acc.setForgotPwdLockedDate(form.getForgotPwdLockedDate());
		acc.setNotifyNo(form.getNotifyNo());
		acc.setRxNotify(form.getRxNotify());
		acc.setRefillRenewal(form.getRefillRenewal());

		if (form.getDateOfBirth() != null) {
			acc.setDateOfBirth(PharmacyUtil.getStringDateFromSqlDate(form.getDateOfBirth()));
			// 01/08/1972
			String dob[] = acc.getDateOfBirth().split("/");
			acc.setDobMonth(dob[0]);
			acc.setDobDate(dob[1]);
			acc.setDobYear(dob[2]);
		}

		acc.setGender(form.getGender());
		acc.setAllergies(form.getAllergies());
		acc.setOtherMedications(form.getOtherMedications());
		acc.setMedicalConditions(form.getMedicalConditions());
		acc.setCriticalComments(form.getCriticalComments());
		acc.setDriversLicense(form.getDriversLicense());
		if (form.getLicenseExpDate() != null)
			acc.setLicenseExpDate(PharmacyUtil.getStringDateFromSqlDate(form.getLicenseExpDate()));

		acc.setDriversLicenseState(form.getDriversLicenseState());

		acc.setSsn(form.getSsn());
		acc.setDateRegistered( PharmacyUtil.getStringDateFromSqlDate(form.getDateRegistered()) );



		acc.setUpdatedBy(form.getUpdatedBy());
		acc.setUpdatedByType(form.getUpdatedByType());
		acc.setUpdatedDate(form.getUpdatedDate());
		acc.setAddress(form.getAddress());
		acc.setCity(form.getCity());
		acc.setState(form.getState());
		acc.setZipCode(form.getZipCode());
		acc.setCountry(form.getCountry());
		acc.setPregnancyprecaution(form.getPregnancyprecaution());

		//Commented on jan 22,2018
		//acc.setPhysicianName( physicianRep.findOne(form.getPhysicianId()).getPhysicianName() );

		acc.setPhotoFile(fileUploadService.photoNameByPatientId(id, filepath));
		acc.setRxNotifyProviderTypeID(form.getRxNotifyProviderTypeID());
		acc.setSyncStatus(form.getSyncStatus());
		if (form.getSyncStatusChangedDate() != null)
			acc.setSyncStatusChangedDate(PharmacyUtil.getStringDateFromSqlDate(form.getSyncStatusChangedDate()));

		acc.setCardCvcNumber(form.getCardCvcNumber());
		acc.setCardHolderName(form.getCardHolderName());
		acc.setCardNumber(form.getCardNumber());
		acc.setCardType(form.getCardType());
		acc.setExpMonth(form.getExpMonth());
		acc.setExpYear(form.getExpYear());
		acc.setSendMailPermission(form.getSendMailPermission());

		acc.setCommEmail(form.getCommEmail());
		acc.setCommPhone(form.getCommPhone());
		acc.setCommTrackingNo(form.getCommTrackingNo());
		acc.setCommShipped(form.getCommShipped());
		acc.setCommDelivered(form.getCommDelivered());
		acc.setCommDeliveryExceptions(form.getCommDeliveryExceptions());

		acc.setAlternateId(form.getAlternateId());
		acc.setAlternateIdTypeId(form.getAlternateIdTypeId());
		acc.setAlternateIdTypeText(form.getAlternateIdTypeText());

		//Commented on jan 22,2018
		/*acc.setGroupId(0);
		 * acc.setGroupName("");
		 * acc.setClinicName("");
		if (phyAcc.getClinicId() > 0)
			acc.setClinicName(clinicRepo.findOne(phyAcc.getClinicId()).getClinicName());
		if (phyGroup != null) {
			acc.setGroupId(phyGroup.getGroupId());
			acc.setGroupName( groupService.getGroupMasterDetails(phyGroup.getGroupId()).getGroupName() );
		}*/

		acc.setCreatedBy(form.getCreatedBy());
		acc.setCreatedUser(form.getCreatedUser());
		acc.setCreatedDate(form.getCreatedDate());

		acc.setLastUpdatedBy(form.getLastUpdatedBy());
		acc.setLastUpdatedUser(form.getLastUpdatedUser());
		acc.setLastUpdatedDate(form.getLastUpdatedDate());

		StringBuilder selectedGroupId = new StringBuilder();
		List<Integer> groupList = new ArrayList<Integer>();
		List<GroupMaster> grpMastList = groupMasterAccount.getAllPatientGroupWiseListSelected(form.getId());
		if (grpMastList.size() > 0) {
			for (GroupMaster g: grpMastList) {
				groupList.add(g.getId());
				if (g.getId() > 0)
					selectedGroupId.append(g.getId()+"").append(",");
				else
					selectedGroupId.append(",");
			}
		}

		if (selectedGroupId.length() > 0)
			acc.setSelectedGroupId(selectedGroupId.toString().substring(0, selectedGroupId.length()-1) );
		else
			acc.setSelectedGroupId("");


		StringBuilder selectedPhysicianId = new StringBuilder();
		if(groupList!=null && groupList.size()>0){
		List<PhysicianAccount> phyList = physicianRep.getAllPatientPhysicianGroupWisePhysicianListSelected(groupList,form.getId());
			if (phyList.size() > 0) {
				for (PhysicianAccount g: phyList) {
					if (g.getId() > 0)
						selectedPhysicianId.append(g.getId()+"").append(",");
					else
						selectedPhysicianId.append(",");
				}
			}
		}

		if (selectedPhysicianId.length() > 0)
			acc.setSelectedPhysicianId(selectedPhysicianId.toString().substring(0, selectedPhysicianId.length()-1) );
		else
			acc.setSelectedPhysicianId("");

		// updated History
		SimpleDateFormat dt = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
		StringBuilder sb = new StringBuilder();
		List<PatientUpdateHistory> historyList =  updatedHistoryRepo.findByPatientId(id);
		for (PatientUpdateHistory h: historyList) {
			String updatedUser = "";
			if (PharmacyUtil.userSuperAdmin.equalsIgnoreCase(h.getUpdatedUser()) || PharmacyUtil.userAdmin.equalsIgnoreCase(h.getUpdatedUser()) ) {
				updatedUser = adminRepo.findOne(h.getUpdatedBy()).getName();
			} else if (PharmacyUtil.userPhysician.equalsIgnoreCase(h.getUpdatedUser())) {
				updatedUser = physicianRep.findOne(h.getUpdatedBy()).getPhysicianName();
			} else if (PharmacyUtil.userPhysicianAssistant.equalsIgnoreCase(h.getUpdatedUser())) {
				updatedUser = assistantRepo.findOne(h.getUpdatedBy()).getAssistantName();
			} else if (PharmacyUtil.userGroupDirector.equalsIgnoreCase(h.getUpdatedUser())) {
				updatedUser = groupDirRepo.findOne(h.getUpdatedBy()).getGroupDirectorName();
			} else if (PharmacyUtil.userPatient.equalsIgnoreCase(h.getUpdatedUser())) {
				updatedUser = patientAccountResp.findOne(h.getUpdatedBy()).getPatientName();
			}
			String upDt = dt.format(h.getUpdatedDate());
			sb.append( h.getUpdatedUser()).append(" - ").append(updatedUser).append(" - ").append( upDt);
			if (h.getApprovedBy() > 0)
				sb.append(" - Approved");
			else
				sb.append(" - Updated" );
			sb.append("\r\n");
		}
		acc.setUpdatedHistory(sb.toString());

		return acc;
	}

	public List<PatientAccount> getAllPatient() {
		return patientAccountResp.findAll();
	}

	public List<PatientAccount> getPatientAccountEmailAndNotId(String email, int id) {
		return patientAccountResp.findByEmailAndIdNot(email, id);
	}

	public List<PatientAccount> getPatientAccountByEmail(String email) {
		return patientAccountResp.findByEmail(email);
	}

	public List<PatientAccount> getPatientAccountUserIdAndNotId(String userId, int id) {
		return patientAccountResp.findByUserLoginIdAndIdNot(userId, id);
	}

	public List<PatientAccount> getPatientAccountByUserId(String userId) {
		return patientAccountResp.findByUserLoginId(userId);
	}


	public List<PatientAccount> getPatientAccountSsnAndNotId(String ssn, int groupId, int id) {
		return patientAccountResp.findBySsnAndGroupIdAndIdNot(ssn, groupId, id);
	}

	public List<PatientAccount> getPatientAccountBySSN(String ssn, int groupId) {
		return patientAccountResp.findBySsnAndGroupId(ssn, groupId);
	}




	public String getPatientAccountDataList(int id, int draw, int start,int length, String searchTerm, int orderColumn, String orderDir,
			String patientName, String phyname, String status, int groupId, HttpSession session) {

		try {
			if (patientName == null)  patientName = "";
			if (phyname == null)  phyname = "";
			if (status == null)  status = "";

			int total = 0;
			int pageNumber = 0;
			Pageable page = null;
			Page<PatientAccount> patientList = null;

			Direction dir = Direction.ASC;
			if (orderDir.equalsIgnoreCase("desc"))
				dir = Direction.DESC;

			LoginForm loggedInUser = (LoginForm) session.getAttribute("loginDetail");
			if (loggedInUser.getType().equalsIgnoreCase(PharmacyUtil.userGroupDirector)) {
				groupId = loggedInUser.getGroupid();
			}

			try {
				if (groupId == 0) {
					if (id > 0) {
						if ("".equalsIgnoreCase(patientName) && "".equalsIgnoreCase(phyname) && "".equalsIgnoreCase(status)) {
							total = (int) patientAccountResp.findByAllByPhysician(id);
						} else {
							if (!"".equalsIgnoreCase(phyname)) {
								if ("".equalsIgnoreCase(status))
									total = patientAccountResp.getSearchAndPhysicianFilterTotal(id, patientName, phyname);
								else
									total = patientAccountResp.getSearchAndPhysicianFilterTotal(id, patientName, phyname, status);
							} else {
								if ("".equalsIgnoreCase(status))
									total = patientAccountResp.getSearchTotal(id, patientName, phyname);
								else
									total = patientAccountResp.getSearchTotal(id, patientName, phyname, status);
							}
						}
					} else {
						if ("".equalsIgnoreCase(patientName) && "".equalsIgnoreCase(phyname) && "".equalsIgnoreCase(status)) {
							total = (int) patientAccountResp.findByAllByStatus();
						} else {
							if (!"".equalsIgnoreCase(phyname)) {
								if ("".equalsIgnoreCase(status))
									total = patientAccountResp.getSearchAndPhysicianFilterTotal(patientName, phyname);
								else
									total = patientAccountResp.getSearchAndPhysicianFilterTotal(patientName, phyname, status);
							} else {
								if ("".equalsIgnoreCase(status))
									total = patientAccountResp.getSearchTotal(patientName, phyname);
								else
									total = patientAccountResp.getSearchTotal(patientName, phyname, status).size();
							}
						}
					}
				} else {
					if (id > 0) {
						if ("".equalsIgnoreCase(patientName) && "".equalsIgnoreCase(phyname) && "".equalsIgnoreCase(status)) {
							total = (int) patientAccountResp.findByAllByPhysicianAndGroup(id, groupId);
						} else {
							if (!"".equalsIgnoreCase(phyname)) {
								if ("".equalsIgnoreCase(status))
									total = patientAccountResp.getSearchAndPhysicianFilterTotalByGroup(id, groupId, patientName, phyname);
								else
									total = patientAccountResp.getSearchAndPhysicianFilterTotalByGroup(id, groupId, patientName, phyname, status);
							} else {
								if ("".equalsIgnoreCase(status))
									total = patientAccountResp.getSearchTotalByGroup(id, groupId, patientName, phyname);
								else
									total = patientAccountResp.getSearchTotalByGroup(id, groupId, patientName, phyname, status);
							}
						}
					} else {
						if ("".equalsIgnoreCase(patientName) && "".equalsIgnoreCase(phyname) && "".equalsIgnoreCase(status)) {
							total = (int) patientAccountResp.findByAllByStatusAndGroup(groupId);
						} else {
							if (!"".equalsIgnoreCase(phyname)) {
								if ("".equalsIgnoreCase(status))
									total = patientAccountResp.getSearchAndPhysicianFilterTotalByGroup(groupId, patientName, phyname);
								else
									total = patientAccountResp.getSearchAndPhysicianFilterTotalByGroup(groupId, patientName, phyname, status);
							} else {
								if ("".equalsIgnoreCase(status))
									total = patientAccountResp.getSearchTotalByGroup(groupId, patientName, phyname);
								else
									total = patientAccountResp.getSearchTotalByGroup(groupId, patientName, phyname, status);
							}
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
					if (id > 0) {
						if ("".equalsIgnoreCase(patientName) && "".equalsIgnoreCase(phyname) && "".equalsIgnoreCase(status)) {
							patientList = patientAccountResp.findByAllByPhysician( id, page);
						} else {
							if (!"".equalsIgnoreCase(phyname)) {
								if ("".equalsIgnoreCase(status))
									patientList = patientAccountResp.getSearchAndPhysicianFilterRecords(id, patientName, phyname, page);
								else
									patientList = patientAccountResp.getSearchAndPhysicianFilterRecords(id, patientName, phyname, status, page);
							} else {
								if ("".equalsIgnoreCase(status))
									patientList = patientAccountResp.getSearchRecords(id, patientName, phyname, page);
								else
									patientList = patientAccountResp.getSearchRecords(id, patientName, phyname, status, page);
							}
						}
					} else {
						if ("".equalsIgnoreCase(patientName) && "".equalsIgnoreCase(phyname) && "".equalsIgnoreCase(status)) {
							patientList = patientAccountResp.findByAllByStatus(page);
						} else {
							if (!"".equalsIgnoreCase(phyname)) {
								if ("".equalsIgnoreCase(status))
									patientList = patientAccountResp.getSearchAndPhysicianFilterRecords(patientName, phyname, page);
								else
									patientList = patientAccountResp.getSearchAndPhysicianFilterRecords(patientName, phyname, status, page);
							} else {
								if ("".equalsIgnoreCase(status))
									patientList = patientAccountResp.getSearchRecords(patientName, phyname, page);
								else
									patientList = patientAccountResp.getSearchRecords(patientName, phyname, status, page);
							}
						}
					}
				} else {
					if (id > 0) {
						if ("".equalsIgnoreCase(patientName) && "".equalsIgnoreCase(phyname) && "".equalsIgnoreCase(status)) {
							patientList = patientAccountResp.findByAllByPhysicianAndGroup(id, groupId, page);
						} else {
							if (!"".equalsIgnoreCase(phyname)) {
								if ("".equalsIgnoreCase(status))
									patientList = patientAccountResp.getSearchAndPhysicianFilterRecordAndGroup(id, groupId, patientName, phyname, page);
								else
									patientList = patientAccountResp.getSearchAndPhysicianFilterRecordAndGroup(id, groupId, patientName, phyname, status, page);
							} else {
								if ("".equalsIgnoreCase(status))
									patientList = patientAccountResp.getSearchRecordAndGroup(id, groupId, patientName, phyname, page);
								else
									patientList = patientAccountResp.getSearchRecordAndGroup(id, groupId, patientName, phyname, status, page);
							}
						}
					} else {
						if ("".equalsIgnoreCase(patientName) && "".equalsIgnoreCase(phyname) && "".equalsIgnoreCase(status)) {
							patientList = patientAccountResp.findByAllByStatusAndGroup(groupId, page);
						} else {
							if (!"".equalsIgnoreCase(phyname)) {
								if ("".equalsIgnoreCase(status))
									patientList = patientAccountResp.getSearchAndPhysicianFilterRecordAndGroup(groupId, patientName, phyname, page);
								else
									patientList = patientAccountResp.getSearchAndPhysicianFilterRecordAndGroup(groupId, patientName, phyname, status, page);
							} else {
								if ("".equalsIgnoreCase(status))
									patientList = patientAccountResp.getSearchRecordAndGroup(groupId, patientName, phyname, page);
								else
									patientList = patientAccountResp.getSearchRecordAndGroup(groupId, patientName, phyname, status, page);
							}
						}
					}
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
			String addressInfo="";
			List<PatientAccountForm> phyAssObjList = new ArrayList<PatientAccountForm>();
			SimpleDateFormat dt = new SimpleDateFormat("MM/dd/yyyy");

			if (patientList != null && patientList.getSize() > 0 ) {
				for(PatientAccount a: patientList) {
					addressInfo="";
					PatientAccountForm obj = new PatientAccountForm();
					obj.setPatientId(a.getId());
					//Commented on jan 22,2018
					//obj.setPhysicianId(a.getPhysicianId());
					//Commented on Feb 7,2018-Multiple Groups to be handled
					//obj.setGroupId(a.getGroupId());
					obj.setCity(a.getCity());
					obj.setPhone(a.getPhone());
					obj.setMobile(a.getMobile());
					if (a.getDateRegistered() != null)
						obj.setDateRegistered(dt.format(a.getDateRegistered()));

					obj.setPatientName(a.getPatientName());
					obj.setEmail(a.getEmail());
					obj.setStatus(a.getStatus());
					obj.setUserLoginId(a.getUserLoginId());

					//Commented on jan 22,2018
					//obj.setPhysicianName( physicianRep.findOne(a.getPhysicianId()).getPhysicianName() );
					obj.setDT_RowId("row_" + a.getId());
					obj.setSendMailPermission(a.getSendMailPermission());

					if(a.getAddress()!=null && a.getAddress().length()>0)
						addressInfo=a.getAddress()+",";
					if(a.getCity()!=null && a.getCity().length()>0)
						addressInfo+=a.getCity()+",";
					if(a.getState()!=null && a.getState().length()>0)
						addressInfo+=a.getState()+",";
					if(a.getZipCode()!=null && a.getZipCode().length()>0)
						addressInfo+=a.getZipCode()+",";
					if (addressInfo.length() > 1)
						obj.setAddressInfo( addressInfo.substring(0, addressInfo.length()-1) );
					else
						obj.setAddressInfo("");

					//Commented on Feb 7,2018-Multiple Groups to be handled
					/*if (a.getGroupId() > 0)
						obj.setGroupName( groupService.getGroupMasterDetails(a.getGroupId()).getGroupName());
					else
						obj.setGroupName("");*/

					StringBuilder groupName = new StringBuilder();
					StringBuilder selectedGroupId = new StringBuilder();
					List<Integer> groupList = new ArrayList<Integer>();
					List<GroupMaster> groupMstTableList = null;
					if (id > 0) {
						groupMstTableList = groupMasterAccount.getAllPatientGroupWiseListSelected(a.getId(), id);
					} else {
						groupMstTableList = groupMasterAccount.getAllPatientGroupWiseListSelected(a.getId());
					}
					if (groupMstTableList.size() > 0) {
						for (GroupMaster g: groupMstTableList) {
							groupList.add(g.getId());
							if (g.getId() > 0)
							{
								selectedGroupId.append(g.getId()+"").append(",");
								groupName.append( groupService.getGroupMasterDetails(g.getId()).getGroupName()).append(",");
							}
							else
							{
								selectedGroupId.append(",");
								groupName.append(",");
							}
						}
					}

					if (groupName.length() > 0)
						obj.setGroupName( groupName.toString().substring(0, groupName.length()-1) );
					else
						obj.setGroupName("");

					StringBuilder physicianName = new StringBuilder();
					//ArrayList<Integer> physicianList = new ArrayList<Integer>();
					if(groupList!=null && groupList.size()>0){
						List<PhysicianAccount> groupTableList = physicianRep.getPatientGroupWisePhysicianListSelected(groupList,a.getId());
						if (groupTableList.size() > 0) {
							for (PhysicianAccount g: groupTableList) {
								//physicianList.add(a.getGroupId());
								if (g.getId() > 0)
									physicianName.append( phyAcctService.getPhysicianAccountDetails(g.getId()).getPhysicianName()).append(",");
								else
									physicianName.append(",");
							}
						}
					}
					if (physicianName.length() > 0)
						obj.setPhysicianName( physicianName.toString().substring(0, physicianName.length()-1) );
					else
						obj.setPhysicianName("");

					//Commented on jan 22,2018
					/*StringBuilder groupName = new StringBuilder();
					ArrayList<Integer> groupList = new ArrayList<Integer>();
					List<PhysicianGroup> groupTableList = phyGroupService.getActiveRecordsByPhysician(a.getPhysicianId());
					if (groupTableList.size() > 0) {
						for (PhysicianGroup g: groupTableList) {
							groupList.add(g.getGroupId());
							if (g.getGroupId() > 0)
								groupName.append( groupService.getGroupMasterDetails(g.getGroupId()).getGroupName()).append(", ");
							else
								groupName.append(", ");
						}
						if (groupName.toString().length() > 0)
							obj.setGroupName(groupName.toString().substring(0, groupName.toString().length()-2));
					}*/

					phyAssObjList.add(obj);
				}
			}

			PatientAccountJSonObj data = new PatientAccountJSonObj();
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
		return null;
	}


	public List<DocumentFileList> getUploadedDocuments(int id) {
		List<DocumentFileList> docList = new ArrayList<DocumentFileList>();
		List<PatientFileUpload> phyList = fileUploadService.getUploadedDocuments(id);

		SimpleDateFormat sm = new SimpleDateFormat("MM/dd/yyyy");

		for (PatientFileUpload a: phyList) {
			DocumentFileList d = new DocumentFileList();

			d.setFileId(a.getId());
			d.setFileType(a.getFileType());
			d.setOriginalFileName(a.getOriginalFileName());
			d.setStoredFielName(a.getStoredFielName());
			d.setUploadedDate( sm.format(a.getUploadedDate()) );
			d.setUserType(a.getUserType());

			String name = "";
			if (PharmacyUtil.userPhysician.equalsIgnoreCase(a.getUserType())) {
				name = physicianRep.findOne(a.getUploadedBy()).getPhysicianName();
			} else if (PharmacyUtil.userPhysicianAssistant.equalsIgnoreCase(a.getUserType())) {
				name = assistantRepo.findOne(a.getUploadedBy()).getAssistantName();
			} else if (PharmacyUtil.userPatient.equalsIgnoreCase(a.getUserType()))  {
				name = patientAccountResp.findOne(a.getUploadedBy()).getPatientName();
			} else {
				name = adminRepo.findOne( a.getUploadedBy()).getName();
			}
			d.setUploadedByName(name);

			docList.add(d);
		}

		return docList;
	}

	public boolean savePatientDocFiles(CommonsMultipartFile docFile, int patientId, String rootFilePath, LoginForm loggedInUser) {
		boolean isUploaded = false;
		if (docFile.getSize() > 0) {
			// Get last file id (primary key) for generating next id for append with Document file name
			int nextDocId = fileUploadService.getLastDocId();

			String folderName="patient";
			String photoFileName="document_"+(++nextDocId);
			isUploaded=PharmacyUtil.userPhotoUpload(docFile, folderName, photoFileName, rootFilePath, patientId, "Document");
			if (isUploaded){
				String oname=docFile.getOriginalFilename();
				String extension = oname.substring(oname.lastIndexOf('.') + 1, oname.length());

				StringBuffer uploadedFile = new StringBuffer(PharmacyUtil.getFileUploadPath(rootFilePath, folderName, patientId, "Document"));
				uploadedFile.append( File.separator + photoFileName + "." + extension);

				fileUploadService.documentFileSave(patientId, docFile, uploadedFile.toString(), loggedInUser);

				//System.out.println("Patient Document uploaded successfully");
			} else {
				//System.out.println("Patient Document not uploaded");
			}
		}

		return isUploaded;
	}

	public boolean deletePatientDocFiles(int fileId) {
		boolean isDeleted = false;
		if (fileId > 0) {
			fileUploadService.deleteFile(fileId);
			isDeleted = true;
		}
		return isDeleted;
	}

	public String getPatientDocFileName(int fileId) {
		String file = "";
		if (fileId > 0) {
			file = fileUploadService.downloadFileName(fileId,false);
		}
		return file;
	}

	public String getPatientDownloadDocFileName(int fileId) {
		String file = "";
		if (fileId > 0) {
			file = fileUploadService.downloadFileName(fileId, true);
		}
		return file;
	}

	public String getPatientPhotoFileName(int id, String filepath) {
		String file = "";
		if (id > 0) {
			file = fileUploadService.photoNameByPatientId(id, filepath);
		}
		return file;
	}

	public String getPatientDocumentDataList(int draw, int start, int length, String searchTerm, int orderColumn, String orderDir, String patientId) {
		int total = 0;
		int pageNumber = 0;
		try {
			Pageable page = null;
			Page<PatientFileUpload> documentList = null;

			Direction dir = Direction.ASC;
			if (orderDir.equalsIgnoreCase("desc"))
				dir = Direction.DESC;

			List<PatientFileUpload> phyDocList = fileUploadService.getUploadedDocuments(Integer.parseInt(patientId));
			total = phyDocList.size();
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

			documentList = fileUploadService.getUploadedDocuments(Integer.parseInt(patientId), page);
			List<DocumentFileList> phyAssObjList = new ArrayList<DocumentFileList>();
			SimpleDateFormat sm = new SimpleDateFormat("MM/dd/yyyy");

			if (documentList != null && documentList.getSize() > 0 ) {
				for(PatientFileUpload a: documentList) {
					DocumentFileList d = new DocumentFileList();
					d.setFileId(a.getId());
					d.setFileType(a.getFileType());
					d.setOriginalFileName(a.getOriginalFileName());
					d.setStoredFielName(a.getStoredFielName());
					d.setUploadedDate( sm.format(a.getUploadedDate()) );
					d.setUserType(a.getUserType());

					String name = "";
					if (PharmacyUtil.userPhysician.equalsIgnoreCase(a.getUserType())) {
						name = physicianRep.findOne(a.getUploadedBy()).getPhysicianName();
					} else if (PharmacyUtil.userPhysicianAssistant.equalsIgnoreCase(a.getUserType())) {
						name = assistantRepo.findOne(a.getUploadedBy()).getAssistantName();
					} else if (PharmacyUtil.userGroupDirector.equalsIgnoreCase(a.getUserType())) {
						name = groupDirRepo.findOne(a.getUploadedBy()).getGroupDirectorName();
					} else if (PharmacyUtil.userPatient.equalsIgnoreCase(a.getUserType()))  {
						name = patientAccountResp.findOne(a.getUploadedBy()).getPatientName();
					} else {
						name = adminRepo.findOne( a.getUploadedBy()).getName();
					}
					d.setUploadedByName(name);

					d.setDT_RowId("row_" + a.getId());

					phyAssObjList.add(d);
				}
			}

			PhysicianDocFileJSonObj data = new PhysicianDocFileJSonObj();
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
		return null;
	}

   //You must override this method; It will give you access to ApplicationEventPublisher
   public void setApplicationEventPublisher(ApplicationEventPublisher publisher) {
       this.publisher = publisher;
   }

	private void PublishPatientPushEvent(PatientAccount patientForm,String previousStatus)
	{
		//temp commented ==> Global VPN Connection not established in Server
		 String RESTfulURL=env.getProperty("webservice_url")+env.getProperty("PatientSave");
		 publisher.publishEvent(new PatientEvent(this, "ADD", patientForm,RESTfulURL,previousStatus));
	}

	public List<PatientAccount> getTop10Patients() {
		 return patientAccountResp.findAll();
		//return patientAccountResp.findAll().subList(0, 10);
	}

	public PatientAccount getPatientData(int id) {
		return patientAccountResp.findOne(id);
	}

	public List<PatientAccount> getAutoCompletePatientList(String term, String status, int id) {
		int MaxResults=Integer.parseInt(env.getProperty("MaxResults"));
		List<PatientAccount> physicianAcctObj = patientAccountResp.getAutoCompletePatientList(term,status,id, new PageRequest(0, MaxResults));
		return physicianAcctObj;
	}

	public List<PatientAccount> getAllAutoCompletePatientList(String term) {
		int MaxResults=Integer.parseInt(env.getProperty("MaxResults"));
		List<PatientAccount> physicianAcctObj = patientAccountResp.getAllAutoCompletePatientList(term, new PageRequest(0, MaxResults));
		return physicianAcctObj;
	}

	public List<PatientAccount> getAutoCompleteAllPatientListByPhysicianId(String term, int id, int physicianId) {
		int MaxResults=Integer.parseInt(env.getProperty("MaxResults"));
		List<PatientAccount> physicianAcctObj = null;
		if (id > 0)
			physicianAcctObj = patientAccountResp.getAutoCompleteAllPatientListByPhysicianId(physicianId, term, id, new PageRequest(0, MaxResults));
		else
			physicianAcctObj = patientAccountResp.getAutoCompleteAllPatientListByPhysicianId(physicianId, term, new PageRequest(0, MaxResults));

		return physicianAcctObj;

	}

	public List<PatientAccount> getAutoCompletePatientListByPhysicianId(String term, String status, int id, int physicianId) {
		//System.out.println("Physician Id " + physicianId);
		int MaxResults=Integer.parseInt(env.getProperty("MaxResults"));

		List<PatientAccount> physicianAcctObj = null;
		if (id > 0)
			physicianAcctObj = patientAccountResp.getAutoCompletePatientListByPhysicianId(physicianId, term, status,id, new PageRequest(0, MaxResults));
		else
			physicianAcctObj = patientAccountResp.getAutoCompletePatientListByPhysicianId(physicianId, term, status,new PageRequest(0, MaxResults));

		return physicianAcctObj;
	}

	public List<PatientAccount> getAutoCompletePrescriptionPatientListByPhysicianId(String term,  int id, int physicianId) {
		//System.out.println("Physician Id " + physicianId);
		int MaxResults=Integer.parseInt(env.getProperty("MaxResults"));

		List<PatientAccount> physicianAcctObj = null;
		if (id > 0)
			physicianAcctObj = patientAccountResp.getAutoCompletePrescriptionPatientListByPhysicianId(physicianId, term, id, new PageRequest(0, MaxResults));
		else
			physicianAcctObj = patientAccountResp.getAutoCompletePrescriptionPatientListByPhysicianId(physicianId, term, new PageRequest(0, MaxResults));

		return physicianAcctObj;
	}

	public List<PatientAccount> getAutoCompletePatientListByGroupId(String term, String status, int id, int groupId) {
		//System.out.println("Group Id " + groupId);
		int MaxResults=Integer.parseInt(env.getProperty("MaxResults"));
		List<PatientAccount> physicianAcctObj = patientAccountResp.getAutoCompletePatientListByGroupId(term, status,id, groupId, new PageRequest(0, MaxResults));
		return physicianAcctObj;
	}


	public List<PatientAccount> getAutoCompletePatientListByGroupId(String term, String status, int groupId) {
		//System.out.println("Group Id " + groupId);
		int MaxResults=Integer.parseInt(env.getProperty("MaxResults"));
		List<PatientAccount> physicianAcctObj = patientAccountResp.getAutoCompletePatientListByGroupId(term, status, groupId, new PageRequest(0, MaxResults));
		return physicianAcctObj;
	}


	public List<PatientAccount> getAutoCompletePatientListByGroupId(String term, int id, int groupId) {
		//System.out.println("Group Id " + groupId);
		int MaxResults=Integer.parseInt(env.getProperty("MaxResults"));
		List<PatientAccount> physicianAcctObj = patientAccountResp.getAutoCompletePatientListByGroupId(term, id, groupId, new PageRequest(0, MaxResults));
		return physicianAcctObj;
	}


	public void approvalPatientNotification(int patientId, LoginForm loggedInUser) {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		PatientAccountForm form = getPatientData(patientId, null);
		Notification notify = notificationService.getPatientReapprove(patientId);

		if (notify == null)
			notify = new Notification();

		StringBuilder sb = new StringBuilder();
		sb.append(form.getPatientName()).append(" info changed on ");
		sb.append(sdf.format(new Date()));

		notify.setDelFlag(PharmacyUtil.deleteFlagNo);
		notify.setNotificationMessage(sb.toString());
		notify.setNotifiedDate(PharmacyUtil.getCurrentTimeStamp());
		notify.setNotifyBy(loggedInUser.getUserid());
		notify.setNotifyRecordId(form.getPatientId());
		notify.setNotifyRecordType(PharmacyUtil.pagePatient);
		notify.setNotifyTypeId(16); // Patient has changed, need Reapproved
		notify.setNotifyUserType(loggedInUser.getType());
		notify.setPatientName(form.getPatientName());
		notify.setExpiryDate( PharmacyUtil.getNotificationExpirtyDate(7));

		notificationService.saveNotification(notify);

		if (PharmacyUtil.statusDenied.equalsIgnoreCase(form.getStatus()))
			notificationService.patientSetDeleteYes(form.getPatientId());
	}


	public void deniedPhysicianNotification(int patientId, LoginForm loggedInUser) {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		PatientAccountForm form = getPatientData(patientId, null);

		Notification notify = notificationService.getPatientDeactivated(form.getPatientId());
		if (notify == null)
			notify = new Notification();

		StringBuilder sb = new StringBuilder();
		sb.append( form.getPatientName() ).append(" was denied on ");
		sb.append( sdf.format( new Date() ));

		notify.setDelFlag(PharmacyUtil.deleteFlagNo);
		notify.setNotificationMessage(sb.toString());
		notify.setNotifiedDate(PharmacyUtil.getCurrentTimeStamp());
		notify.setNotifyBy(loggedInUser.getUserid());
		notify.setNotifyRecordId(form.getPatientId());
		notify.setNotifyRecordType(PharmacyUtil.pagePatient);
		notify.setNotifyTypeId(8);							// Patient Denied
		notify.setNotifyUserType(loggedInUser.getType());
		notify.setPatientName(form.getPatientName());
		notify.setExpiryDate( PharmacyUtil.getNotificationExpirtyDate(7));

		notificationService.saveNotification(notify);

		notificationService.patientSetDeleteYes(form.getPatientId());
	}


	public void newPhysicianAddedByPatient(int patientId, LoginForm loggedInUser, String physicianNames) {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		PatientAccountForm form = getPatientData(patientId, null);

		Notification notify = notificationService.getPatientNewPhysicianAdded(form.getPatientId());
		if (notify == null)
			notify = new Notification();

		StringBuilder sb = new StringBuilder();
		sb.append( form.getPatientName() ).append(" was added additional physician ");
		sb.append( physicianNames.substring(0, physicianNames.length()-1) );
		sb.append(" on ").append( sdf.format( new Date() ));

		notify.setDelFlag(PharmacyUtil.deleteFlagNo);
		notify.setNotificationMessage(sb.toString());
		notify.setNotifiedDate(PharmacyUtil.getCurrentTimeStamp());
		notify.setNotifyBy(loggedInUser.getUserid());
		notify.setNotifyRecordId(form.getPatientId());
		notify.setNotifyRecordType(PharmacyUtil.pagePatient);
		notify.setNotifyTypeId(9);							// Patient added new physicians
		notify.setNotifyUserType(loggedInUser.getType());
		notify.setPatientName(form.getPatientName());
		notify.setExpiryDate( PharmacyUtil.getNotificationExpirtyDate(7));

		notificationService.saveNotification(notify);

		if (PharmacyUtil.statusDenied.equalsIgnoreCase(form.getStatus()))
			notificationService.patientSetDeleteYes(form.getPatientId());
	}

	public void criticalCommentsChangedByPatient(int patientId, LoginForm loggedInUser) {

		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		PatientAccountForm form = getPatientData(patientId, null);

		Notification notify = notificationService.getPatientCriticalCommentsChanges(form.getPatientId());
		if (notify == null)
			notify = new Notification();

		StringBuilder sb = new StringBuilder();
		sb.append( form.getPatientName() ).append(" was changed critical comments on ");
		sb.append( sdf.format( new Date() ));

		notify.setDelFlag(PharmacyUtil.deleteFlagNo);
		notify.setNotificationMessage(sb.toString());
		notify.setNotifiedDate(PharmacyUtil.getCurrentTimeStamp());
		notify.setNotifyBy(loggedInUser.getUserid());
		notify.setNotifyRecordId(form.getPatientId());
		notify.setNotifyRecordType(PharmacyUtil.pagePatient);
		notify.setNotifyTypeId(10);							// Patient Critical Comments Changed
		notify.setNotifyUserType(loggedInUser.getType());
		notify.setPatientName(form.getPatientName());

		notificationService.saveNotification(notify);

		if (PharmacyUtil.statusDenied.equalsIgnoreCase(form.getStatus()))
			notificationService.patientSetDeleteYes(form.getPatientId());
	}

	public void cancelCriticalCommentsNotificationByPatient(int patientId) {
		try {
			Notification notify = notificationService.getPatientCriticalCommentsChanges(patientId);
    		if (notify != null) {
    			notify.setDelFlag(PharmacyUtil.deleteFlagYes);
    			notify.setExpiryDate(PharmacyUtil.getCurrentTimeStamp());

    			notificationService.saveNotification(notify);
    		}
    	} catch(Exception e){
    		e.printStackTrace();
    	}
    }





	public boolean sendPatientAccountMail(PatientAccountForm form,LoginForm loggedInUser, HttpSession session,HttpServletRequest request, Environment env) {
		boolean isSend = false;
						try{
							PatientAccount rec = patientAccountResp.findOne(form.getPatientId());
							String loginUrl = PharmacyUtil.getCurrentSystemUrl(request)+"/patientlogin";
							if (rec!=null && form.getPatientId() > 0 && "true".equalsIgnoreCase(env.getProperty("isMailSend"))) {

								String fileName = null;
								String attachmentFileName = null;
								if ("true".equalsIgnoreCase(env.getProperty("includeAttachment"))) {
									// Include attachments here...

								}

						MailSendConfig mail = new MailSendConfig();
						mail.MailSending(rec, env, "PatientSignup", loginUrl, fileName, attachmentFileName );
						 isSend = true;
					}
				} catch(Exception e) {
					e.printStackTrace();
					 isSend = false;
				}
						return isSend;

	}

}

