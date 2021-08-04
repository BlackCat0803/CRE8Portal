package com.pharma.core.pharmaservices.physicianservices;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

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
import com.pharma.core.events.physician.PhysicianEvent;
import com.pharma.core.formBean.DocumentFileList;
import com.pharma.core.formBean.LoginForm;
import com.pharma.core.formBean.physician.PhysicianDocFileJSonObj;
import com.pharma.core.formBean.physician.PhysicianJSonObj;
import com.pharma.core.formBean.physician.PhysicianProfile;
import com.pharma.core.formBean.physician.PhysicianRegistration;
import com.pharma.core.model.clinic.Clinic;
import com.pharma.core.model.notifications.Notification;
import com.pharma.core.model.pharmacygroup.GroupMaster;
import com.pharma.core.model.physician.PhysicianAccount;
import com.pharma.core.model.physician.PhysicianClinic;
import com.pharma.core.model.physician.PhysicianCreditCardDetails;
import com.pharma.core.model.physician.PhysicianFileUpload;
import com.pharma.core.model.physician.PhysicianGroup;
import com.pharma.core.model.physician.PhysicianProfileInfo;
import com.pharma.core.model.physician.PhysicianUpdateHistory;
import com.pharma.core.pharmaservices.PrescriberTypeMasterService;
import com.pharma.core.pharmaservices.clinic.ClinicService;
import com.pharma.core.pharmaservices.mail.MailSendConfig;
import com.pharma.core.pharmaservices.notifications.NotificationService;
import com.pharma.core.pharmaservices.pharmacygroupservices.GroupMasterFileUploadService;
import com.pharma.core.pharmaservices.pharmacygroupservices.GroupMasterService;
import com.pharma.core.repository.admin.AdminAccountRepository;
import com.pharma.core.repository.clinic.ClinicRepository;
import com.pharma.core.repository.pharmacygroup.GroupDirectorAccountRespository;
import com.pharma.core.repository.pharmacygroup.GroupMasterRespository;
import com.pharma.core.repository.physician.PhysicianAccountRespository;
import com.pharma.core.repository.physician.PhysicianAssistantAccountRespository;
import com.pharma.core.repository.physician.PhysicianClinicRepository;
import com.pharma.core.repository.physician.PhysicianCreditCardRepository;
import com.pharma.core.repository.physician.PhysicianFileUploadRepository;
import com.pharma.core.repository.physician.PhysicianGroupRepository;
import com.pharma.core.repository.physician.PhysicianProfileRespository;
import com.pharma.core.repository.physician.PhysicianUpdateHistoryRepository;
import com.pharma.core.util.PharmacyUtil;
/**
 * This is an implementation class that loads and stores the physician account data
 */
@Service("physicianAccountService")
@PropertySource("classpath:webService.properties")
@PropertySource("classpath:mail.properties")
public class PhysicianAccountServiceImpl implements PhysicianAccountService  {

	@Autowired
	PhysicianAccountRespository phyAccount;

	@Autowired
	PhysicianAssistantAccountService assistantService;

	@Autowired
	PhysicianProfileRespository phyProfile;

	@Autowired
	PhysicianCreditCardRepository creditCardRepo;

	@Autowired
	PhysicianFileUploadService fileUploadService;

	@Autowired
	PhysicianAssistantAccountRespository assistantRepo;

	@Autowired
	AdminAccountRepository adminRepo;

	@Autowired
	GroupMasterService groupService;

	@Autowired
	GroupMasterRespository groupRepo;

	@Autowired
	PhysicianGroupService phyGroupService;

	@Autowired
	PhysicianGroupRepository phyGroupRepo;

	@Autowired
	PhysicianClinicRepository phyClinicRepo;

	@Autowired
	GroupDirectorAccountRespository groupDirRepo;

	@Autowired
	PrescriberTypeMasterService prescriberTypeMasterService;

	@Autowired
	private ApplicationEventPublisher publisher;

	@Autowired
	ClinicRepository clinicRepo;

	@Autowired
	PhysicianUpdateHistoryRepository updatedHistoryRepo;

	@Autowired
	private Environment env;

	@Autowired
	GroupMasterFileUploadService grpfileUploadService;

	@Autowired
	ClinicService clinicService;

	@Autowired
	PhysicianFileUploadRepository physicianFileRepo;

	@Autowired
	NotificationService notificationService;

	public PhysicianAccount register(PhysicianRegistration form, HttpSession session, HttpServletRequest request) {
		try {
			PhysicianAccount acc = new PhysicianAccount();
			acc.setFirstName(form.getFirstName().trim());
			acc.setLastName(form.getLastName().trim());
			acc.setEmail(form.getEmail());
			acc.setPassword(form.getPassword());
			acc.setMobile(form.getMobile());
			acc.setPhone(form.getPhone());
			acc.setCity(form.getCity());
			acc.setState(form.getState());
			acc.setZipCode(form.getZipCode());
			acc.setCountry("USA");

			acc.setPhysicianName(acc.getFirstName().trim() + " " + acc.getLastName().trim());
			acc.setStatus("New");
			acc.setPreviousSignon(PharmacyUtil.getCurrentDateAndTime());
			acc.setDateRegistrated(new java.sql.Date(new Date().getTime()));
			// field to use physician name with Group name
			acc.setPhysicianNameWithGroupName(acc.getPhysicianName());

			acc.setCreatedUser(PharmacyUtil.userPhysician);
			acc.setCreatedDate( PharmacyUtil.getCurrentTimeStamp() );
			acc.setLastUpdatedUser(PharmacyUtil.userPhysician);
			acc.setLastUpdatedDate( PharmacyUtil.getCurrentTimeStamp() );
			acc.setDefaultphysician("N");

			int defaultNewGroup=0;
			List<GroupMaster> groupMastList = groupRepo.findByDefaultGroup();

			if (groupMastList.size() > 0) {
				for (GroupMaster g: groupMastList) {
					defaultNewGroup=g.getId();
				}
			}


			PhysicianAccount nacc = phyAccount.save(acc);
			nacc.setCreatedBy(nacc.getId());
			nacc.setLastUpdatedBy(nacc.getId());

			PhysicianAccount obj = phyAccount.save(nacc);


			/*
			 * Saving records in Physician Group table
			 */
			try {
				PhysicianGroup phyGrp= new PhysicianGroup();
				phyGrp.setPhysicianId(obj.getId());
				phyGrp.setCreatedBy(obj.getId());
				phyGrp.setCreatedDate(PharmacyUtil.getCurrentTimeStamp());
				phyGrp.setCreatedUserType(PharmacyUtil.userPhysician);
				phyGrp.setGroupId(defaultNewGroup);
				phyGrp.setStatus(PharmacyUtil.statusActive);

				phyGrp.setUpdatedBy(obj.getId());
				phyGrp.setUpdatedDate(PharmacyUtil.getCurrentTimeStamp());
				phyGrp.setUpdatedUserType(PharmacyUtil.userPhysician);

				phyGroupRepo.save(phyGrp);
			} catch(Exception e) {
				e.printStackTrace();
			}


			PhysicianProfileInfo accProfile = new PhysicianProfileInfo();
			accProfile.setPhysicianId(obj.getId());
			accProfile.setSendMailPermission("Yes");
			phyProfile.save(accProfile);

			PhysicianCreditCardDetails phycc  = new PhysicianCreditCardDetails();
			phycc.setPhysicianId(obj.getId());
			creditCardRepo.save(phycc);


			if ("true".equalsIgnoreCase(env.getProperty("isMailSend"))) {
				try {
					PhysicianProfile profile = getPhysicianData(nacc.getId(), env.getProperty("file.photo_path"), env.getProperty("file.logo_path"));
					MailSendConfig mail = new MailSendConfig();
					String loginUrl = PharmacyUtil.getCurrentSystemUrl(request)+"/login?s=1";  // 1 means User Type is Physician

					String fileName = null;
					String attachmentFileName = null;
					if ("true".equalsIgnoreCase(env.getProperty("includeAttachment"))) {
						// Include attachments here...
					}

					// Physician will get Welcome mail
					mail.MailSending(profile, env, "toPhysicianMail", loginUrl, fileName, attachmentFileName );
					// Admin will get about this new Physician signup
					mail.MailSending(profile, env, "PhysicianSignuptoAdmin", loginUrl, fileName, attachmentFileName );
				} catch(Exception e) {
					e.printStackTrace();
				}
			}

			return obj;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


	private boolean compareWithPreviousData(PhysicianProfile form, PhysicianAccount acc) {
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
		if ( (form.getAddress1()  == null && acc.getAddress1() != null) || (form.getAddress1() != null && acc.getAddress1() == null) ||
				(form.getAddress1() != null && acc.getAddress1() != null && !form.getAddress1().equalsIgnoreCase(acc.getAddress1())) )
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



		// Second Street (Address2)
		if ( (form.getAddress2()  == null && acc.getSecondaryAddress1() != null) || (form.getAddress2() != null && acc.getSecondaryAddress1() == null) ||
				(form.getAddress2() != null && acc.getSecondaryAddress1() != null && !form.getAddress2().equalsIgnoreCase(acc.getSecondaryAddress1())) )
			isUpdated = true;

		// Second City
		if ( (form.getCity2() == null && acc.getSecondaryCity() != null) || (form.getCity2() != null && acc.getSecondaryCity() == null) ||
				(form.getCity2() != null && acc.getSecondaryCity() != null && !form.getCity2().equalsIgnoreCase(acc.getSecondaryCity())) )
			isUpdated = true;

		// Second State
		if ( (form.getState2() == null && acc.getSecondaryState() != null) || (form.getState2() != null && acc.getSecondaryState() == null) ||
				(form.getState2() != null && acc.getSecondaryState() != null && !form.getState2().equalsIgnoreCase(acc.getSecondaryState())) )
			isUpdated = true;

		// Second Zip code
		if ( (form.getZipCode2() == null && acc.getSecondaryZipCode() != null) || (form.getZipCode2() != null && acc.getSecondaryZipCode() == null) ||
				(form.getZipCode2() != null && acc.getSecondaryZipCode() != null && !form.getZipCode2().equalsIgnoreCase(acc.getSecondaryZipCode())) )
			isUpdated = true;

		// phone
		if ( (form.getPhone() == null && acc.getPhone() != null) || (form.getPhone() != null && acc.getPhone() == null) ||
				(form.getPhone() != null && acc.getPhone() != null && !form.getPhone().equalsIgnoreCase(acc.getPhone())) )
			isUpdated = true;

		// fax
		if ( (form.getFax() == null && acc.getFax() != null) || (form.getFax() != null && acc.getFax() == null) ||
				(form.getFax() != null && acc.getFax() != null && !form.getFax().equalsIgnoreCase(acc.getFax())) )
			isUpdated = true;

		// home  (phone 2)
		if ( (form.getPhone2() == null && acc.getPhone2() != null) || (form.getPhone2() != null && acc.getPhone2() == null) ||
				(form.getPhone2() != null && acc.getPhone2() != null && !form.getPhone2().equalsIgnoreCase(acc.getPhone2())) )
			isUpdated = true;

		// office (phone 3)
		if ( (form.getPhone3() == null && acc.getPhone3() != null) || (form.getPhone3() != null && acc.getPhone3() == null) ||
				(form.getPhone3() != null && acc.getPhone3() != null && !form.getPhone3().equalsIgnoreCase(acc.getPhone3())) )
			isUpdated = true;

		// mobile
		if ( (form.getMobile() == null && acc.getMobile() != null) || (form.getMobile() != null && acc.getMobile() == null) ||
				(form.getMobile() != null && acc.getMobile() != null && !form.getMobile().equalsIgnoreCase(acc.getMobile())) )
			isUpdated = true;

		// email
		if ( (form.getEmail() == null && acc.getEmail() != null) || (form.getEmail() != null && acc.getEmail() == null) ||
				(form.getEmail() != null && acc.getEmail() != null && !form.getEmail().equalsIgnoreCase(acc.getEmail())) )
			isUpdated = true;

		// group
		PhysicianGroup phyGrp =  phyGroupRepo.findRecordByPhysicianId(form.getPhysicianId());
		if (phyGrp != null) {
			if ( form.getGroupId() != phyGrp.getGroupId())
				isUpdated = true;
		}

		PhysicianProfileInfo accProfile = phyProfile.findOne(form.getPhysicianId());
		// DEA
		if ( (form.getDea() == null && accProfile.getDea() != null) || (form.getDea() != null && accProfile.getDea() == null) ||
				(form.getDea() != null && accProfile.getDea() != null && !form.getDea().equalsIgnoreCase(accProfile.getDea())) )
			isUpdated = true;

		// NPI
		if ( (form.getNpi() == null && accProfile.getNpi() != null) || (form.getNpi() != null && accProfile.getNpi() == null) ||
				(form.getNpi() != null && accProfile.getNpi() != null && !form.getNpi().equalsIgnoreCase(accProfile.getNpi())) )
			isUpdated = true;

		// UPIN
		if ( (form.getUpin() == null && accProfile.getUpin() != null) || (form.getUpin() != null && accProfile.getUpin() == null) ||
				(form.getUpin() != null && accProfile.getUpin() != null && !form.getUpin().equalsIgnoreCase(accProfile.getUpin())) )
			isUpdated = true;

		// State License Number
		if ( (form.getStateLicense() == null && accProfile.getStateLicense() != null) || (form.getStateLicense() != null && accProfile.getStateLicense() == null) ||
				(form.getStateLicense() != null && accProfile.getStateLicense() != null && !form.getStateLicense().equalsIgnoreCase(accProfile.getStateLicense())) )
			isUpdated = true;

		// Medicaid (N/A)
		if ( (form.getMedicaid() == null && accProfile.getMedicaid() != null) || (form.getMedicaid() != null && accProfile.getMedicaid() == null) ||
				(form.getMedicaid() != null && accProfile.getMedicaid() != null && !form.getMedicaid().equalsIgnoreCase(accProfile.getMedicaid())) )
			isUpdated = true;

		// DPS
		if ( (form.getDps() == null && accProfile.getDps() != null) || (form.getDps() != null && accProfile.getDps() == null) ||
				(form.getDps() != null && accProfile.getDps() != null && !form.getDps().equalsIgnoreCase(accProfile.getDps())) )
			isUpdated = true;

		if (!isUpdated) {
			// Clinic List
			String[] formIdList = null;
			if (!"".equalsIgnoreCase( form.getSelectedClinicId()) && form.getSelectedClinicId() != null)
				formIdList = form.getSelectedClinicId().split(",");
			else
				formIdList = new String[0];

			List<PhysicianClinic> phyClinicList =  phyClinicRepo.findByPhysicianIdAndDelFlagOrderById(form.getPhysicianId(), PharmacyUtil.deleteFlagNo);
			String[] dbIdList = new String[phyClinicList.size()];
			if (phyClinicList.size() > 0) {
				int i=0;
				for (PhysicianClinic c: phyClinicList) {
					dbIdList[i++] = c.getClinicId() + "";
				}
			}
//			//System.out.println(Arrays.toString( formIdList ));
//			//System.out.println(Arrays.toString( dbIdList ));

			HashSet<String> set1 = new HashSet<String>(Arrays.asList(formIdList));
			HashSet<String> set2 = new HashSet<String>(Arrays.asList(dbIdList));
			if (!set1.equals(set2))
				isUpdated = true;
		}

		return isUpdated;
	}


	public PhysicianAccount savePhysicianAccountProfile(PhysicianProfile form, CommonsMultipartFile photoFile, CommonsMultipartFile[] docFiles,
			String rootFilePath1, LoginForm loggedInUser, HttpSession session, HttpServletRequest request, CommonsMultipartFile logoFile,String rootFilePath2) {

			try {
				boolean isDenied = false;
				boolean isAnyUpdates = false;

				boolean isNeedApproval = false;
				boolean isCriticalCmtChanged = false;

				PhysicianAccount acc = null;
				String previousStatus="";

				if(form.getPhysicianId() > 0) {
					acc = phyAccount.findOne(form.getPhysicianId());
					previousStatus=acc.getStatus();

					isNeedApproval = compareWithPreviousData(form, acc);
					if (!isNeedApproval && !acc.getStatus().equalsIgnoreCase(form.getStatus()) )
						isNeedApproval = true;

					// Comparing previously stored value with newly entered values for changing Status to "New Modification"
					// work done by vijayakumar @ 20-jan-2018
					if (!PharmacyUtil.statusDenied.equalsIgnoreCase(acc.getStatus()) && (PharmacyUtil.statusDenied.equalsIgnoreCase(form.getStatus())) ) {
						acc.setDeniedBy(loggedInUser.getUserid());
						acc.setDeniedUser(loggedInUser.getType());
						acc.setDeniedDate(PharmacyUtil.getCurrentTimeStamp());
						isDenied = true;
					}else
						if (PharmacyUtil.statusApproved.equalsIgnoreCase(acc.getStatus()) ||
							(PharmacyUtil.statusDenied.equalsIgnoreCase(acc.getStatus())
									&& !PharmacyUtil.statusDenied.equalsIgnoreCase(form.getStatus())) )
							isAnyUpdates = compareWithPreviousData(form, acc);

					// Critical Comments updated notifications purpose
					PhysicianProfileInfo accProfile = phyProfile.findOne(form.getPhysicianId());
					if (!isDenied && accProfile != null) {
						if (accProfile.getComments()!=null && (!accProfile.getComments().equalsIgnoreCase(form.getComments())))
							isCriticalCmtChanged = true;
					}

					if (!PharmacyUtil.statusApproved.equalsIgnoreCase(acc.getStatus()) && (PharmacyUtil.statusApproved.equalsIgnoreCase(form.getStatus())) ) {
						acc.setApprovedBy(loggedInUser.getUserid());
						acc.setApprovedUser(loggedInUser.getType());
						acc.setApprovedDate(PharmacyUtil.getCurrentTimeStamp());
					}
					if (isAnyUpdates)
						acc.setStatus(PharmacyUtil.statusNewModified);
					else
						acc.setStatus(form.getStatus());

					if ((loggedInUser.getType().equalsIgnoreCase(PharmacyUtil.userAdmin) || loggedInUser.getType().equalsIgnoreCase(PharmacyUtil.userSuperAdmin))
							&& (!PharmacyUtil.statusDenied.equalsIgnoreCase(acc.getStatus())) )
						acc.setEmail(form.getEmail());
				} else {
					acc = new PhysicianAccount();
					acc.setEmail(form.getEmail());
					acc.setPassword(form.getPassword());
					acc.setStatus(form.getStatus());

					acc.setCreatedBy(loggedInUser.getUserid());
					acc.setCreatedUser(loggedInUser.getType());
					acc.setCreatedDate( PharmacyUtil.getCurrentTimeStamp() );
					acc.setDefaultphysician("N");
				}

				// acc.setDateRegistrated(new java.sql.Date(new Date().getTime()));
				if (form.getDateRegistrated() != null && !"".equalsIgnoreCase(form.getDateRegistrated()))
					acc.setDateRegistrated( PharmacyUtil.getSqlDateFromString(form.getDateRegistrated()));
				acc.setLastUpdatedBy(loggedInUser.getUserid());
				acc.setLastUpdatedUser(loggedInUser.getType());
				acc.setLastUpdatedDate( PharmacyUtil.getCurrentTimeStamp() );
				//System.out.println("account ==="+acc);
				//System.out.println("acc.getStatus() ==="+acc.getStatus());
				if (!PharmacyUtil.statusDenied.equalsIgnoreCase(acc.getStatus())) {
					acc.setFirstName(form.getFirstName().trim());
					acc.setLastName(form.getLastName().trim());
					acc.setMiddleName(form.getMiddleName().trim());
					acc.setMobile(form.getMobile());
					acc.setPhone(form.getPhone());
					acc.setCity(form.getCity());
					acc.setState(form.getState());
					acc.setZipCode(form.getZipCode());
					acc.setClinicId(form.getClinicId());
					acc.setCountry("USA");

					if(form.getMiddleName()!=null && form.getMiddleName().trim().length()>0)
						acc.setPhysicianName(form.getFirstName().trim() + " " + form.getMiddleName().trim() + " " + form.getLastName().trim());
					else
						acc.setPhysicianName(form.getFirstName().trim() + " " + form.getLastName().trim());

					acc.setPrescriberType(form.getPrescriberType());
					acc.setPrescriberGroup(form.getPrescriberGroup());
					acc.setFax(form.getFax());
					acc.setPhone2(form.getPhone2());
					acc.setPhone3(form.getPhone3());
					acc.setAddress1(form.getAddress1());
					acc.setPhysicianCode(form.getPhysicianCode());

					acc.setSecondaryAddress1(form.getAddress2());
					acc.setSecondaryCity(form.getCity2());
					acc.setSecondaryCountry("USA");
					acc.setSecondaryState(form.getState2());
					acc.setSecondaryZipCode(form.getZipCode2());
					acc.setUseGroupLogo(form.getUseGroupLogo());
					// acc.setGroupId(form.getGroupId());

					acc.setPhysicianNameWithGroupName(form.getPhysicianNameWithGroupName());
				}

				PhysicianAccount doc = phyAccount.save(acc);

				if (!PharmacyUtil.statusDenied.equalsIgnoreCase(acc.getStatus())) {
					/*
					 * Saving record in phy_profile table
					 */
					try {
						PhysicianProfileInfo accProfile = null;
						if (form.getPhysicianId() > 0)
							accProfile = phyProfile.findOne(form.getPhysicianId());
						if (accProfile == null) {
							accProfile = new PhysicianProfileInfo();
							accProfile.setPhysicianId(doc.getId());
						}
						accProfile.setWebsite(form.getWebsite());
						accProfile.setMarketer(form.getMarketer());
						accProfile.setDea(form.getDea());
						accProfile.setNpi(form.getNpi());
						accProfile.setUpin(form.getUpin());
						accProfile.setStateLicense(form.getStateLicense());
						accProfile.setMedicaid(form.getMedicaid());
						accProfile.setDps(form.getDps());
						accProfile.setCommEmail(form.getCommEmail());
						accProfile.setCommFax(form.getCommFax());
						accProfile.setCommPhone(form.getCommPhone());
						accProfile.setComments(form.getComments());
						accProfile.setReqBeforeDays(form.getReqBeforeDays());
						accProfile.setSendMailPermission(form.getSendMailPermission());

						accProfile.setCommTrackingNo(form.getCommTrackingNo());
						accProfile.setCommShipped(form.getCommShipped());
						accProfile.setCommDelivered(form.getCommDelivered());
						accProfile.setCommDeliveryExceptions(form.getCommDeliveryExceptions());

						accProfile.setCommentsUpdateInPioneer(form.getCommentsUpdateInPioneer());

						phyProfile.save(accProfile);
					} catch(Exception e) {
						e.printStackTrace();
					}

					/*
					 * Saving record in phy_creditcard_details table
					 */
					try {
						PhysicianCreditCardDetails phycc  = null;
						if (form.getPhysicianId() > 0)
							phycc = creditCardRepo.findOne(form.getPhysicianId());
						if (phycc == null) {
							phycc = new PhysicianCreditCardDetails();
							phycc.setPhysicianId(doc.getId());
						}
						phycc.setCardType(form.getCardType());
						phycc.setCardNumber(form.getCardNumber());
						phycc.setCardCvcNumber(form.getCardCvcNumber());
						phycc.setCardHolderName(form.getCardHolderName());
						phycc.setExpMonth(form.getExpMonth());
						phycc.setExpYear(form.getExpYear());
						phycc.setBillingZipCode(form.getBillingZipCode());

						creditCardRepo.save(phycc);
					} catch (Exception e) {
						e.printStackTrace();
					}

					/*
					 * Saving records in Physician Group table
					 */
					try {
						PhysicianGroup phyGrp =  phyGroupRepo.findRecordByPhysicianId(doc.getId());
						if (phyGrp == null) {
							phyGrp = new PhysicianGroup();
							phyGrp.setPhysicianId(doc.getId());
							phyGrp.setCreatedBy(loggedInUser.getUserid());
							phyGrp.setCreatedDate(PharmacyUtil.getCurrentTimeStamp());
							phyGrp.setCreatedUserType(loggedInUser.getType());
						}
						phyGrp.setGroupId(form.getGroupId());
						phyGrp.setStatus(PharmacyUtil.statusActive);

						phyGrp.setUpdatedBy(loggedInUser.getUserid());
						phyGrp.setUpdatedDate(PharmacyUtil.getCurrentTimeStamp());
						phyGrp.setUpdatedUserType(loggedInUser.getType());

						phyGroupRepo.save(phyGrp);
					} catch(Exception e) {
						e.printStackTrace();
					}


					/*
					 * Saving records in Physician Clinic table
					 */
					try {
						// Deleted existing records
						List<PhysicianClinic> phyClinicList =  phyClinicRepo.findByPhysicianId(doc.getId());
						if (phyClinicList.size() > 0) {
							for (PhysicianClinic c: phyClinicList) {
								c.setDelFlag(PharmacyUtil.deleteFlagYes);
								c.setUpdatedBy(loggedInUser.getUserid());
								c.setUpdatedDate(PharmacyUtil.getCurrentTimeStamp());
								c.setUpdatedUserType(loggedInUser.getType());
								phyClinicRepo.save(c);
							}
						}

						if (!"".equalsIgnoreCase( form.getSelectedClinicId()) && form.getSelectedClinicId() != null) {
							String[] idList = form.getSelectedClinicId().split(",");
							if (idList.length > 0) {
								for (int i=0; i < idList.length; i++) {
									PhysicianClinic obj = null;
									obj = phyClinicRepo.findByPhysicianIdAndClinicId(doc.getId(), Integer.parseInt(idList[i]));
									if (obj == null) {
										obj = new PhysicianClinic();
										obj.setPhysicianId(doc.getId());
										obj.setCreatedBy(loggedInUser.getUserid());
										obj.setCreatedDate(PharmacyUtil.getCurrentTimeStamp());
										obj.setCreatedUserType(loggedInUser.getType());
									}
									obj.setClinicId(Integer.parseInt(idList[i]));
									obj.setDelFlag(PharmacyUtil.deleteFlagNo);

									obj.setUpdatedBy(loggedInUser.getUserid());
									obj.setUpdatedDate(PharmacyUtil.getCurrentTimeStamp());
									obj.setUpdatedUserType(loggedInUser.getType());

									phyClinicRepo.save(obj);
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
						String folderName="physician";
						String photoFileName="photo_"+doc.getId();
						boolean isUploaded=PharmacyUtil.userPhotoUpload(photoFile, folderName, photoFileName, rootFilePath1, doc.getId(), "");
						if (isUploaded){
							String oname=photoFile.getOriginalFilename().toLowerCase();
							String extension = oname.substring(oname.lastIndexOf('.') + 1, oname.length());
							/* String savedPhotoFile= rootFilePath + File.separator + folderName + File.separator + photoFileName + "."+extension; */

							StringBuffer uploadedFile = new StringBuffer(PharmacyUtil.getFileUploadPath(rootFilePath1, folderName, doc.getId(), ""));
							uploadedFile.append( File.separator + photoFileName + "." + extension);

							fileUploadService.photoFileSave(doc.getId(), photoFile, uploadedFile.toString(), loggedInUser);

							//System.out.println("Photo uploaded ok");
						} else {
							//System.out.println("Photo not uploaded");
						}
					}

					if (logoFile.getSize() > 0) {
						String folderName="physician";
						String photoFileName="logo_"+doc.getId();
						boolean isUploaded=PharmacyUtil.userPhotoUpload(logoFile, folderName, photoFileName, rootFilePath2, doc.getId(), "");
						if (isUploaded){
							String oname=logoFile.getOriginalFilename().toLowerCase();
							String extension = oname.substring(oname.lastIndexOf('.') + 1, oname.length());
							/* String savedPhotoFile= rootFilePath + File.separator + folderName + File.separator + photoFileName + "."+extension; */

							StringBuffer uploadedFile = new StringBuffer(PharmacyUtil.getFileUploadPath(rootFilePath2, folderName, doc.getId(), ""));
							uploadedFile.append( File.separator + photoFileName + "." + extension);

							fileUploadService.logoFileSave(doc.getId(), logoFile, uploadedFile.toString(), loggedInUser);

							//System.out.println("Logo uploaded ok");
						} else {
							//System.out.println("Logo not uploaded");
						}
					}
				}

				// 'Denied' physician's Assistants has been inactivated
				//temp commented on jan 19,2018
				/*if (isDenied) {
					assistantService.setInactiveByPhysicianId(doc.getId(), loggedInUser);
				}*/

				// Pioneer Uid updated if DEA value already existing...
				// based on discussion with sudha madam on 18-jan-2018 updated by Vijayakumar
				if (doc.getPioneerUid() == null || "".equalsIgnoreCase(doc.getPioneerUid())) {
					String pioneerId = getPioneerIdByDea(form.getDea());
					doc.setPioneerUid(pioneerId);
					doc.setPioneerResponse(0);
					phyAccount.save(doc);
				}


				// Storing updated history to physician updated table....
				// implemented by vijayakumar @ 23-jan-2018
				if (form.getPhysicianId() > 0) {
					PhysicianUpdateHistory history = new PhysicianUpdateHistory();

					history.setPhysicianId(form.getPhysicianId());
					history.setUpdatedDate(PharmacyUtil.getCurrentTimeStamp());
					history.setUpdatedBy(loggedInUser.getUserid());
					history.setUpdatedUser(loggedInUser.getType());
					history.setDescription("Physician record updated.");

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
						String loginUrl = PharmacyUtil.getCurrentSystemUrl(request)+"/login?s=1";  // 1 means User Type is Physician

						PhysicianProfile profile = getPhysicianData(acc.getId(), env.getProperty("file.photo_path"), env.getProperty("file.logo_path"));
						MailSendConfig mail = new MailSendConfig();
						mail.MailSending(profile, env, "PhysicianApproval", loginUrl, fileName, attachmentFileName );
					}
				}

				// Physician Notification Records added
				if (isNeedApproval) {
					approvalPhysicianNotification(form.getPhysicianId(), loggedInUser);
				}

				if (isDenied) {
					deniedPhysicianNotification(form.getPhysicianId(), loggedInUser);
				}

				if (isCriticalCmtChanged) {
					criticalCommentsChangedByPhysician(form.getPhysicianId(), loggedInUser);
				} else if ("Yes".equalsIgnoreCase(form.getCommentsUpdateInPioneer())){
					cancelCriticalCommentsNotificationByPhysician(form.getPhysicianId());
				}

				try {
					String loginUrl = PharmacyUtil.getCurrentSystemUrl(request)+"/login?s=1";  // 1 means User Type is Physician

					PhysicianProfile profile = getPhysicianData(acc.getId(), env.getProperty("file.photo_path"), env.getProperty("file.logo_path"));
					PublishPhysicianPushEvent(profile,previousStatus);
					//Automatic mail triggered is commented out and called from Send Credential Mail button on 10/02/2021 by durgadevi

					// Only New physician at the time of registration will get Mail about new physician registration
					/*if (form.getPhysicianId() == 0 && "Yes".equalsIgnoreCase(form.getSendMailPermission()) && "true".equalsIgnoreCase(env.getProperty("isMailSend"))) {
						String fileName = null;
						String attachmentFileName = null;
						if ("true".equalsIgnoreCase(env.getProperty("includeAttachment"))) {
							// Include attachments here...
						}

						MailSendConfig mail = new MailSendConfig();
						mail.MailSending(profile, env, "AdminForPhysicianSignup", loginUrl, fileName, attachmentFileName );
					}*/
				} catch (Exception e) {
					e.printStackTrace();
				}

				return doc;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
	}

	private String getPioneerIdByDea(String dea) {
		String pioneerId = "";
		try {
			List<PhysicianAccount> accList = phyAccount.getPioneerIdByDea(dea);
			if (accList.size() > 0)
				pioneerId = accList.get(0).getPioneerUid();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return pioneerId;
	}

	public PhysicianProfile getPhysicianData(int id,String filepath1,String filepath2) {
		try {
			PhysicianAccount acc = phyAccount.findOne(id);

			PhysicianProfile pro = new PhysicianProfile();

			pro.setPhysicianId(acc.getId());
			pro.setFirstName(acc.getFirstName());
			pro.setLastName(acc.getLastName());
			pro.setMiddleName(acc.getMiddleName());
			pro.setPhysicianName(acc.getPhysicianName());
			pro.setEmail(acc.getEmail());
			pro.setPassword(acc.getPassword());
			pro.setMobile(acc.getMobile());
			pro.setPhone(acc.getPhone());
			pro.setClinicId(acc.getClinicId());
			if (acc.getClinicId() > 0)
				pro.setClinicName(clinicRepo.findOne(acc.getClinicId())!=null?clinicRepo.findOne(acc.getClinicId()).getClinicName():"");
			else
				pro.setClinicName("");
			pro.setCity(acc.getCity());
			pro.setState(acc.getState());
			pro.setZipCode(acc.getZipCode());
			pro.setPreviousSignon(acc.getPreviousSignon());
			pro.setDateRegistrated( PharmacyUtil.getStringDateFromSqlDate(acc.getDateRegistrated()) );
			pro.setStatus(acc.getStatus());
			pro.setPasswordResetDate(acc.getPasswordResetDate());
			pro.setLoginAttempts(acc.getLoginAttempts());
			pro.setLoginLockedDate(acc.getLoginLockedDate());
			pro.setSecurityQuestionNumber2(acc.getSecurityQuestionNumber2());
			pro.setSecurityQuestionNumber(acc.getSecurityQuestionNumber());
			pro.setSecurityAnswer(acc.getSecurityAnswer());
			pro.setSecurityAnswer2(acc.getSecurityAnswer2());
			pro.setForgotPasswordAttempts(acc.getForgotPasswordAttempts());
			pro.setForgotPwdLockedDate(acc.getForgotPwdLockedDate());
			pro.setPrescriberType(acc.getPrescriberType());
			pro.setPrescriberGroup(acc.getPrescriberGroup());
			pro.setFax(acc.getFax());
			pro.setPhone2(acc.getPhone2());
			pro.setPhone3(acc.getPhone3());
			pro.setAddress1(acc.getAddress1());
			pro.setCountry(acc.getCountry());
			pro.setPhysicianCode(acc.getPhysicianCode());
			pro.setApprovedBy(acc.getApprovedBy());
			pro.setApprovedUser(acc.getApprovedUser());
			pro.setApprovedDate(acc.getApprovedDate());
			pro.setDeniedBy(acc.getDeniedBy());
			pro.setDeniedUser(acc.getDeniedUser());
			pro.setDeniedDate(acc.getDeniedDate());

			pro.setAddress2(acc.getSecondaryAddress1());
			pro.setCity2(acc.getSecondaryCity());
			pro.setState2(acc.getSecondaryState());
			pro.setZipCode2(acc.getSecondaryZipCode());
			// pro.setGroupId(acc.getGroupId());

			pro.setPioneerUid(acc.getPioneerUid());
			pro.setPioneerResponse(acc.getPioneerResponse());

			pro.setCreatedBy(acc.getCreatedBy());
			pro.setCreatedUser(acc.getCreatedUser());
			pro.setCreatedDate(acc.getCreatedDate());

			pro.setLastUpdatedBy(acc.getLastUpdatedBy());
			pro.setLastUpdatedUser(acc.getLastUpdatedUser());
			pro.setLastUpdatedDate(acc.getLastUpdatedDate());
			pro.setUseGroupLogo(acc.getUseGroupLogo());
			pro.setPhysicianNameWithGroupName(acc.getPhysicianNameWithGroupName());
			pro.setProfilestep(acc.getProfilestep());

			if (acc.getPrescriberType() > 0)
				pro.setPioneerPrescriberTypeId(prescriberTypeMasterService.getPrescriberTypeMasterDetails(acc.getPrescriberType()).getPioneerPrescriberTypeId());

			/*
			 * Getting data from phy_profile table
			 */
			PhysicianProfileInfo accProfile = phyProfile.findOne(id);

			if (accProfile != null) {
				pro.setWebsite(accProfile.getWebsite());
				pro.setMarketer(accProfile.getMarketer());
				pro.setDea(accProfile.getDea());
				pro.setNpi(accProfile.getNpi());
				pro.setUpin(accProfile.getUpin());
				pro.setStateLicense(accProfile.getStateLicense());
				pro.setMedicaid(accProfile.getMedicaid());
				pro.setDps(accProfile.getDps());
				pro.setCommEmail(accProfile.getCommEmail());
				pro.setCommFax(accProfile.getCommFax());
				pro.setCommPhone(accProfile.getCommPhone());
				pro.setComments(accProfile.getComments());
				pro.setReqBeforeDays(accProfile.getReqBeforeDays());
				pro.setSendMailPermission(accProfile.getSendMailPermission());

				pro.setCommTrackingNo(accProfile.getCommTrackingNo());
				pro.setCommShipped(accProfile.getCommShipped());
				pro.setCommDelivered(accProfile.getCommDelivered());
				pro.setCommDeliveryExceptions(accProfile.getCommDeliveryExceptions());
			}

			/*
			 * Getting data from phy_creditcard_details table
			 */
			PhysicianCreditCardDetails phycc = creditCardRepo.findOne(id);
			if (phycc != null) {
				pro.setCardType(phycc.getCardType());
				pro.setCardNumber(phycc.getCardNumber());
				pro.setCardCvcNumber(phycc.getCardCvcNumber());
				pro.setCardHolderName(phycc.getCardHolderName());
				pro.setExpMonth(phycc.getExpMonth());
				pro.setExpYear(phycc.getExpYear());

				pro.setBillingZipCode(phycc.getBillingZipCode());
			}
			pro.setPhotoFile(fileUploadService.photoNameByPhysicianId(id, filepath1));
			pro.setLogoFile(fileUploadService.logoNameByPhysicianId(id, filepath2));

			/*
			 * Physician Group data
			 */
			pro.setGroupId(0);
			StringBuilder groupName = new StringBuilder();
			ArrayList<Integer> groupList = new ArrayList<Integer>();
			List<PhysicianGroup> groupTableList = phyGroupService.getByPhysician(id);
			if (groupTableList.size() > 0) {
				for (PhysicianGroup g: groupTableList) {
					groupList.add(g.getGroupId());
					if (g.getGroupId() > 0)
						groupName.append( groupService.getGroupMasterDetails(g.getGroupId()).getGroupName()).append(",");
					else
						groupName.append(",");
				}
				pro.setGroupId(groupList.get(0));
			}
			if (groupName.toString().length() > 0)
				pro.setGroupName( groupName.toString().substring(0, groupName.length()-1) );
			else
				pro.setGroupName("");

			// Clinic Ids and Names are picked for form page loading
			StringBuilder clinicNameList = new StringBuilder();
			StringBuilder clinicIdList = new StringBuilder();
			List<PhysicianClinic> clinicTableList = phyClinicRepo.findByPhysicianIdAndDelFlagOrderById(id, PharmacyUtil.deleteFlagNo);
			if (clinicTableList.size() > 0) {
				for (PhysicianClinic pc: clinicTableList) {
					clinicIdList.append(pc.getClinicId()).append(",");
					if (pc.getClinicId() > 0)
						clinicNameList.append( clinicRepo.findOne(pc.getClinicId()).getClinicName()).append(",");
					else
						clinicNameList.append(",");
				}
			}
			if (clinicIdList.toString().length() > 0)
				pro.setSelectedClinicId( clinicIdList.toString().substring(0, clinicIdList.length()-1) );
			else
				pro.setSelectedClinicId("");

			if (clinicNameList.toString().length() > 0)
				pro.setClinicNameList( clinicNameList.toString().substring(0, clinicNameList.length()-1) );
			else
				pro.setClinicNameList("");

			if (!"".equalsIgnoreCase(filepath2)) {
				pro.setGroupLogoFile(grpfileUploadService.logoNameByGroupId(pro.getGroupId(), filepath2) );
			}
			// updated History
			SimpleDateFormat dt = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
			StringBuilder sb = new StringBuilder();
			List<PhysicianUpdateHistory> historyList =  updatedHistoryRepo.findByPhysicianId(id);
			for (PhysicianUpdateHistory h: historyList) {
				String updatedUser = "";
				if (PharmacyUtil.userSuperAdmin.equalsIgnoreCase(h.getUpdatedUser()) || PharmacyUtil.userAdmin.equalsIgnoreCase(h.getUpdatedUser()) ) {
					updatedUser = adminRepo.findOne(h.getUpdatedBy()).getName();
				} else if (PharmacyUtil.userPhysician.equalsIgnoreCase(h.getUpdatedUser())) {
					updatedUser = phyAccount.findOne(h.getUpdatedBy()).getPhysicianName();
				} else if (PharmacyUtil.userGroupDirector.equalsIgnoreCase(h.getUpdatedUser())) {
					updatedUser = groupDirRepo.findOne(h.getUpdatedBy()).getGroupDirectorName();
				}
				String upDt = dt.format(h.getUpdatedDate());
				sb.append( h.getUpdatedUser()).append(" - ").append(updatedUser).append(" - ").append( upDt);
				if (h.getApprovedBy() > 0)
					sb.append(" - Approved");
				else
					sb.append(" - Updated" );
				sb.append("\r\n");
			}
			pro.setUpdatedHistory(sb.toString());

			return pro;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean checkPhysicianClinicAlreadyExists(int physicianId,LoginForm loggedInUser,String physicianProfile,int groupId)
	{
		boolean alreadyClinicExist=false;
		try {
			PhysicianAccount acct = null;

			if(physicianId > 0) {
				acct = phyAccount.findOne(physicianId);
				if(acct!=null)
				{

					//List<Clinic> existingClinicRec=clinicRepo.findByClinicName(acct.getPhysicianName()+" - CLINIC");
					List<Clinic> existingClinicRec=clinicRepo.findByClinicNameAndGroupId(acct.getPhysicianName()+" - CLINIC",groupId);
					if(existingClinicRec!=null && existingClinicRec.size()>0)
					{
						alreadyClinicExist=true;

					}
				}
			}

		} catch(Exception e) {
			e.printStackTrace();
		}
		return alreadyClinicExist;
	}
	/**
	 *  Added on Feb 5,2018, Create Physician's Clinic
		Clinic Name => <Doctor Name> - CLINIC
		Contact Name -> Physician Name & all his Primary Addres & other details
		Branch -> City
		Display the clinic created in the list
	 */
	public boolean saveNewPhysicianClinic(int physicianId,LoginForm loggedInUser,String physicianProfile,int groupId)
	{
		boolean alreadyClinicExist=false;
		try {
			PhysicianAccount acct = null;

			if(physicianId > 0) {
				acct = phyAccount.findOne(physicianId);
				if(acct!=null)
				{

					List<Clinic> existingClinicRec=clinicRepo.findByClinicNameAndGroupId(acct.getPhysicianName()+" - CLINIC",groupId);
					if(existingClinicRec!=null && existingClinicRec.size()>0)
					{
						alreadyClinicExist=true;

					}else{

						Clinic acc = null;

						acc = new Clinic();
						//Clinic Name => <Doctor Name> - CLINIC
						acc.setClinicName(acct.getPhysicianName()+" - CLINIC");
						// all his Primary Addres & other details
						acc.setAddress(acct.getAddress1());
						acc.setCity(acct.getCity());
						acc.setState(acct.getState());
						acc.setZipCode(acct.getZipCode());
						acc.setPhone(acct.getPhone());
						if(physicianProfile.equalsIgnoreCase("true"))
							acc.setStatus(PharmacyUtil.statusInfoCompleted);
						else
							acc.setStatus(PharmacyUtil.statusNew);

						acc.setEmail(acct.getEmail());

						List<PhysicianGroup> groupTableList = phyGroupService.getByPhysician(physicianId);
						if (groupTableList.size() > 0) {
							for (PhysicianGroup g: groupTableList) {
								acc.setGroupId(g.getGroupId());
							}
						}else
						{

							int newGroupId=0;
							List<GroupMaster> groupMastList= groupRepo.findByNewGroup();
							if (groupMastList.size() > 0) {
								for (GroupMaster g: groupMastList) {
									newGroupId=g.getId();
								}
							}
							acc.setGroupId(newGroupId);

							/*
							 * Saving records in Physician Group table
							 */
							try {
								PhysicianGroup phyGrp= new PhysicianGroup();
								phyGrp.setPhysicianId(physicianId);
								phyGrp.setCreatedBy(loggedInUser.getUserid());
								phyGrp.setCreatedDate(PharmacyUtil.getCurrentTimeStamp());
								phyGrp.setCreatedUserType(loggedInUser.getType());
								phyGrp.setGroupId(newGroupId);
								phyGrp.setStatus(PharmacyUtil.statusActive);

								phyGrp.setUpdatedBy(loggedInUser.getUserid());
								phyGrp.setUpdatedDate(PharmacyUtil.getCurrentTimeStamp());
								phyGrp.setUpdatedUserType(PharmacyUtil.userPhysician);

								phyGroupRepo.save(phyGrp);
							} catch(Exception e) {
								e.printStackTrace();
							}


						}
						//Branch -> City
						acc.setLocation(acct.getCity());
						//Contact Name -> Physician Name
						acc.setContactName(acct.getPhysicianName());
						acc.setFax(acct.getFax());

						acc.setCreatedBy(loggedInUser.getUserid());
						acc.setCreatedUser(loggedInUser.getType());
						acc.setCreatedDate( PharmacyUtil.getCurrentTimeStamp() );


						acc.setLastUpdatedBy(loggedInUser.getUserid());
						acc.setLastUpdatedUser(loggedInUser.getType());
						acc.setLastUpdatedDate( PharmacyUtil.getCurrentTimeStamp() );

						Clinic rec = clinicRepo.save(acc);

						//Save the same clinic in the Clinic Selected List of Physician
						PhysicianClinic obj = null;
						obj = new PhysicianClinic();
						obj.setPhysicianId(physicianId);
						obj.setCreatedBy(loggedInUser.getUserid());
						obj.setCreatedDate(PharmacyUtil.getCurrentTimeStamp());
						obj.setCreatedUserType(loggedInUser.getType());
						obj.setClinicId(rec.getId());
						obj.setDelFlag(PharmacyUtil.deleteFlagNo);

						obj.setUpdatedBy(loggedInUser.getUserid());
						obj.setUpdatedDate(PharmacyUtil.getCurrentTimeStamp());
						obj.setUpdatedUserType(loggedInUser.getType());

						phyClinicRepo.save(obj);


						newClinicAddedByPhysician(physicianId, loggedInUser, rec.getId());
					}
				}
			}

		} catch(Exception e) {
			e.printStackTrace();
		}
		return alreadyClinicExist;
	}

	public String getPhysicianDataList(int draw, int start, int length, String searchTerm, int orderColumn, String orderDir, String physicianName,
			String dea, String status, int groupId) {

		if (physicianName == null) physicianName = "";
		if (dea == null) dea = "";
		if (status == null || status == "") status = "";

		int total = 0;
		int pageNumber = 0;
		try {
			Pageable page = null;
			Page<PhysicianAccount> physicianList = null;

			Direction dir = Direction.ASC;
			if (orderDir.equalsIgnoreCase("desc"))
				dir = Direction.DESC;

			try {
				if (groupId == 0) {
					if ("".equalsIgnoreCase(physicianName) && "".equalsIgnoreCase(dea) && "".equalsIgnoreCase(status)) {
						total = (int) phyAccount.getAllRecords();
					} else {
						if (!"".equalsIgnoreCase(dea) && !"".equalsIgnoreCase(status)) {
							total = phyAccount.countByClinicAndStatus(physicianName, dea, status);
						} else if (!"".equalsIgnoreCase(status)) {
							total = phyAccount.countByStatus(physicianName, status);
						} else if (!"".equalsIgnoreCase(dea)) {
							total = phyAccount.countByClinic(physicianName, dea);
						} else {
							total = phyAccount.countBySearch(physicianName);
						}
					}
				} else {
					if ("".equalsIgnoreCase(physicianName) && "".equalsIgnoreCase(dea) && "".equalsIgnoreCase(status)) {
						total = (int) phyAccount.getAllGroupRecords(groupId);
					} else {
						if (!"".equalsIgnoreCase(status)) {
							total = phyAccount.countByGroupStatusWithSearch(groupId, dea, physicianName,  status);
						} else {
							total = phyAccount.countByGroupNotDeniedWithSearch(groupId, dea, physicianName);
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
					if ("".equalsIgnoreCase(physicianName) && "".equalsIgnoreCase(dea) && "".equalsIgnoreCase(status)) {
						physicianList = phyAccount.getAllRecords(page);
					} else {
						if (!"".equalsIgnoreCase(dea) && !"".equalsIgnoreCase(status)) {
							physicianList = phyAccount.findByClinicAndStatus(physicianName, dea, status, page);
						} else if (!"".equalsIgnoreCase(status)) {
							physicianList = phyAccount.findByStatus(physicianName, status, page);
						} else if (!"".equalsIgnoreCase(dea)) {
							physicianList = phyAccount.findByClinic(physicianName, dea, page);
						} else {
							physicianList = phyAccount.findBySearch(physicianName, page);
						}
					}
				} else {
					if ("".equalsIgnoreCase(physicianName) && "".equalsIgnoreCase(dea) && "".equalsIgnoreCase(status)) {
						physicianList = phyAccount.getAllGroupRecords(groupId, page);
					} else {
						if (!"".equalsIgnoreCase(status)) {
							physicianList = phyAccount.findByGroupStatusWithSearch(groupId, dea, physicianName, status, page);
						} else {
							physicianList = phyAccount.findByGroupNotDeniedWithSearch(groupId, dea, physicianName, page);
						}
					}
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
			String addressInfo="";
			List<PhysicianProfile> phyAssObjList = new ArrayList<PhysicianProfile>();
			SimpleDateFormat dt = new SimpleDateFormat("MM/dd/yyyy");

			if (physicianList != null && physicianList.getSize() > 0 ) {
				for(PhysicianAccount a: physicianList) {
					addressInfo="";
					PhysicianProfile obj = new PhysicianProfile();
					obj.setPhysicianId(a.getId());
					obj.setPhysicianName(a.getPhysicianName());
					obj.setEmail(a.getEmail());
					obj.setCity(a.getCity());
					obj.setState(a.getState());
					obj.setPhone(a.getPhone());
					obj.setMobile(a.getMobile());
					obj.setStatus(a.getStatus());

					if(a.getAddress1()!=null && a.getAddress1().length()>0)
						addressInfo=a.getAddress1()+",";
					if(a.getCity()!=null && a.getCity().length()>0)
						addressInfo+=a.getCity()+",";
					if(a.getState()!=null && a.getState().length()>0)
						addressInfo+=a.getState()+",";
					if(a.getZipCode()!=null && a.getZipCode().length()>0)
						addressInfo+=a.getZipCode()+",";
					if (addressInfo.length() > 0)
						obj.setAddressInfo( addressInfo.substring(0, addressInfo.length()-1) );
					else
						obj.setAddressInfo("");

					//obj.setDateRegistrated( PharmacyUtil.getStringDateFromSqlDate(a.getDateRegistrated()) );
					if (a.getDateRegistrated() != null)
						obj.setDateRegistrated(dt.format(a.getDateRegistrated()));

					if (a.getClinicId() > 0)
						obj.setClinicName(clinicRepo.findOne(a.getClinicId())!=null?clinicRepo.findOne(a.getClinicId()).getClinicName():"");
					else
						obj.setClinicName("");
					obj.setPhysicianNameWithGroupName(a.getPhysicianNameWithGroupName());

					obj.setDea("");
					PhysicianProfileInfo accProfile = phyProfile.findOne(a.getId());
					if (accProfile != null) {
						if (accProfile.getDea() != null)
							obj.setDea( accProfile.getDea() );
					}

					int oldGroupId = 0;
					StringBuilder groupName = new StringBuilder();
					ArrayList<Integer> groupList = new ArrayList<Integer>();
					List<PhysicianGroup> groupTableList = phyGroupService.getByPhysician(a.getId());
					if (groupTableList.size() > 0) {
						for (PhysicianGroup g: groupTableList) {
							groupList.add(g.getGroupId());
							oldGroupId=g.getGroupId();
							if (g.getGroupId() > 0)
								groupName.append( groupService.getGroupMasterDetails(g.getGroupId()).getGroupName()).append(",");
//							else
//								groupName.append(",");
						}
					}
					if (groupName.length() > 0)
						obj.setGroupName( groupName.toString().substring(0, groupName.length()-1) );
					else
						obj.setGroupName("");
					obj.setGroupId(oldGroupId);


					// Clinic List added to summary list
					StringBuilder clinicNameList = new StringBuilder();
					StringBuilder clinicIdList = new StringBuilder();
					List<PhysicianClinic> clinicTableList = phyClinicRepo.findByPhysicianIdAndDelFlagOrderById(a.getId(), PharmacyUtil.deleteFlagNo);
					if (clinicTableList.size() > 0) {
						for (PhysicianClinic pc: clinicTableList) {
							clinicIdList.append(pc.getClinicId()).append(",");
							if (pc.getClinicId() > 0)
								clinicNameList.append( clinicRepo.findOne(pc.getClinicId()).getClinicName()).append(",");
							else
								clinicNameList.append(",");
						}
					}
					if (clinicIdList.toString().length() > 0)
						obj.setClinicIdString( clinicIdList.toString().substring(0, clinicIdList.length()-1) );
					else
						obj.setClinicIdString("");

					if (clinicNameList.toString().length() > 0)
						obj.setClinicNameList( clinicNameList.toString().substring(0, clinicNameList.length()-1) );
					else
						obj.setClinicNameList("");

					obj.setDT_RowId("row_" + a.getId());

					phyAssObjList.add(obj);
				}
			}
			PhysicianJSonObj data = new PhysicianJSonObj();
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

	public List<PhysicianAccount> getAllPhysician() {
		return phyAccount.findAll();
	}

	public List<PhysicianAccount> getApprovedPhysician() {
		return phyAccount.getAllApprovedPhysicians();
	}

	//Auto complete method
	public List<String> getPhysicianNameList(String term) {
		List<String> output = new ArrayList<String>();
		List<PhysicianAccount> list = phyAccount.findByAutoPhysicianNameAndStatus(term, PharmacyUtil.statusDenied);
		if (list.size()>0) {
			for (PhysicianAccount a: list) {
				output.add(a.getPhysicianName());
			}
		}
		return output;
	}

    public PhysicianAccount getPhysicianAccountDetails(int id) {
		return phyAccount.findOne(id);
	}

	public List<DocumentFileList> getUploadedDocuments(int id) {
		List<DocumentFileList> docList = new ArrayList<DocumentFileList>();
		List<PhysicianFileUpload> phyList = fileUploadService.getUploadedDocuments(id);

		SimpleDateFormat sm = new SimpleDateFormat("MM/dd/yyyy");

		for (PhysicianFileUpload a: phyList) {
			DocumentFileList d = new DocumentFileList();

			d.setFileId(a.getId());
			d.setFileType(a.getFileType());
			d.setOriginalFileName(a.getOriginalFileName());
			d.setStoredFielName(a.getStoredFielName());
			d.setFilePurpose(a.getFilePurpose());
			d.setUploadedDate( sm.format(a.getUploadedDate()) );
			d.setUserType(a.getUserType());

			String name = "";
			if (PharmacyUtil.userPhysician.equalsIgnoreCase(a.getUserType())) {
				name = phyAccount.findOne(a.getUploadedBy()).getPhysicianName();
			} else if (PharmacyUtil.userPhysicianAssistant.equalsIgnoreCase(a.getUserType())) {
				name = assistantRepo.findOne(a.getUploadedBy()).getAssistantName();
			} else if (PharmacyUtil.userGroupDirector.equalsIgnoreCase(a.getUserType())) {
				name = groupDirRepo.findOne(a.getUploadedBy()).getGroupDirectorName();
			} else {
				name = adminRepo.findOne( a.getUploadedBy()).getName();
			}
			d.setUploadedByName(name);

			docList.add(d);
		}

		return docList;
	}

	public boolean savePhysicianDocFiles(CommonsMultipartFile docFile, int physicianId, String rootFilePath, LoginForm loggedInUser,
			String docPurpose, String expiryDate, String fileName) {
		boolean isUploaded = false;
		if (docFile.getSize() > 0) {
			int nextDocId = 0;
			PhysicianFileUpload fileRec = null;
			if(!docPurpose.equalsIgnoreCase("Other"))
				fileRec = physicianFileRepo.findByPhysicianIdAndFilePurpose(physicianId, docPurpose);
			if (fileRec != null) {
				// Removed previously uploaded document
				String storedFileName = fileRec.getStoredFielName();
				if (!"".equalsIgnoreCase(storedFileName)) {
					File file = new File(storedFileName);
					if (file.exists()) {
						file.delete();
					}
				}
				nextDocId = fileRec.getId();
			} else {
				nextDocId = fileUploadService.getLastDocId();
				nextDocId++;
			}

			// Get last file id (primary key) for generating next id for append with Document file name
			String folderName="physician";
			String photoFileName="document_"+(nextDocId);
			isUploaded=PharmacyUtil.userPhotoUpload(docFile, folderName, photoFileName, rootFilePath, physicianId, "Document");
			if (isUploaded){
				String oname=docFile.getOriginalFilename();
				String extension = oname.substring(oname.lastIndexOf('.') + 1, oname.length());

				StringBuffer uploadedFile = new StringBuffer(PharmacyUtil.getFileUploadPath(rootFilePath, folderName, physicianId, "Document"));
				uploadedFile.append( File.separator + photoFileName + "." + extension);

				fileUploadService.documentFileSave(physicianId, docFile, uploadedFile.toString(), loggedInUser,
						docPurpose, expiryDate, fileName);

				//System.out.println("Photo uploaded ok");
			} else {
				//System.out.println("Photo not uploaded");
			}
		}

		return isUploaded;
	}

	public boolean deletePhysicianDocFiles(int fileId) {
		boolean isDeleted = false;
		if (fileId > 0) {
			fileUploadService.deleteFile(fileId);
			isDeleted = true;
		}
		return isDeleted;
	}

	public String getPhysicianDocFileName(int fileId) {
		String file = "";
		if (fileId > 0) {
			file = fileUploadService.downloadFileName(fileId,false);
		}
		return file;
	}

	public String getPhysicianDownloadDocFileName(int fileId) {
		String file = "";
		if (fileId > 0) {
			file = fileUploadService.downloadFileName(fileId, true);
		}
		return file;
	}

	public String getPhysicianPhotoFileName(int id, String filepath) {
		String file = "";
		if (id > 0) {
			file = fileUploadService.photoNameByPhysicianId(id, filepath);
		}
		return file;
	}


	public String getPhysicianLogoFileName(int id, String filepath) {
		String file = "";
		if (id > 0) {
			file = fileUploadService.logoNameByPhysicianId(id, filepath);
		}
		return file;
	}

	public String getPhysicianDocumentDataList(int draw, int start, int length, String searchTerm, int orderColumn, String orderDir, String physicianId) {

		int total = 0;
		int pageNumber = 0;
		try {
			Pageable page = null;
			Page<PhysicianFileUpload> documentList = null;

			Direction dir = Direction.ASC;
			if (orderDir.equalsIgnoreCase("desc"))
				dir = Direction.DESC;

			total = fileUploadService.getUploadedDocuments(Integer.parseInt(physicianId)).size();
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

			documentList = fileUploadService.getUploadedDocuments(Integer.parseInt(physicianId), page);
			List<DocumentFileList> phyAssObjList = new ArrayList<DocumentFileList>();
			SimpleDateFormat sm = new SimpleDateFormat("MM/dd/yyyy");

			if (documentList != null && documentList.getSize() > 0 ) {
				for(PhysicianFileUpload a: documentList) {
					DocumentFileList d = new DocumentFileList();
					d.setFileId(a.getId());
					d.setFileType(a.getFileType());
					d.setOriginalFileName(a.getOriginalFileName());
					d.setStoredFielName(a.getStoredFielName());
					d.setUploadedDate( sm.format(a.getUploadedDate()) );
					d.setUserType(a.getUserType());

					if (a.getDocExpiryDate() != null)
						d.setDocExpiryDate( PharmacyUtil.getStringDateFromSqlDate(a.getDocExpiryDate()) );
					else
						d.setDocExpiryDate("");
					d.setFilePurpose(a.getFilePurpose());
					if(a.getFilePurpose().equalsIgnoreCase("Other"))
						d.setFilePurpose(a.getFilePurpose()+" - "+a.getOtherDocFileName());
					d.setOtherDocFileName(a.getOtherDocFileName());

					String name = "";
					if (PharmacyUtil.userPhysician.equalsIgnoreCase(a.getUserType())) {
						name = phyAccount.findOne(a.getUploadedBy()).getPhysicianName();
					} else if (PharmacyUtil.userPhysicianAssistant.equalsIgnoreCase(a.getUserType())) {
						name = assistantRepo.findOne(a.getUploadedBy()).getAssistantName();
					} else if (PharmacyUtil.userGroupDirector.equalsIgnoreCase(a.getUserType())) {
						name = groupDirRepo.findOne(a.getUploadedBy()).getGroupDirectorName();
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

	 //You must override this method; It will give you acces to ApplicationEventPublisher
    public void setApplicationEventPublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

	private void PublishPhysicianPushEvent(PhysicianProfile profileForm,String previousStatus)
	{
		//temp commented ==> Global VPN Connection not established in Server
		String RESTfulURL=env.getProperty("webservice_url")+env.getProperty("PrescriberSave");
		 publisher.publishEvent(new PhysicianEvent(this, "ADD", profileForm,RESTfulURL,previousStatus));
	}

	public List<PhysicianAccount> getApprovedPhysicianAndId(int id) {
		return phyAccount.getAllApprovedPhysiciansAndId(id);
	}
	public List<PhysicianAccount> getTop10Physicians() {
		 return phyAccount.findAll();
		//return phyAccount.findAll().subList(0, 10);
	}

	public List<PhysicianAccount> getApprovedPhysicianByGroupIdAndId(int groupId, int id) {
		List<PhysicianAccount> list = null;
		if (id > 0)
			list = phyAccount.getAllApprovedPhysiciansByGroupIdAndId(groupId, id);
		else
			list = phyAccount.getAllApprovedPhysiciansByGroupId(groupId);

		return list;
	}

	public List<PhysicianAccount> getAutoCompletePhysicianList(String term,String status,int id) {
		int MaxResults=Integer.parseInt(env.getProperty("MaxResults"));
		// Status is not used due to we required Approved and Denied physicians. (updated by vijayakumar @ 14-12-2017)
		// List<PhysicianAccount> physicianAcctObj = phyAccount.fetchPhysicianNameByAutoComplete(term,status,id, new PageRequest(0, MaxResults));
		List<PhysicianAccount> physicianAcctObj = null;
		if (id == 0)
			physicianAcctObj = phyAccount.fetchAllPhysicianNameByAutoComplete(term, new PageRequest(0, MaxResults));
		else
			physicianAcctObj = phyAccount.fetchNotNewPhysicianNameByAutoComplete(term, id, new PageRequest(0, MaxResults));
		return physicianAcctObj;
	}

	public List<PhysicianAccount> getAllAutoCompletePhysicianList(String term) {
		int MaxResults=Integer.parseInt(env.getProperty("MaxResults"));
		List<PhysicianAccount> physicianAcctObj = phyAccount.fetchPhysicianNameByAutoComplete(term, new PageRequest(0, MaxResults));
		return physicianAcctObj;

	}

	public List<PhysicianAccount> getAutoCompletePhysicianList(String term,String status,int id,int groupid) {
		int MaxResults=Integer.parseInt(env.getProperty("MaxResults"));
		// Status is not used due to we required Approved and Denied physicians. (updated by vijayakumar @ 14-12-2017)
		List<PhysicianAccount> physicianAcctObj = null;
		if (id > 0)
			physicianAcctObj = phyAccount.fetchPhysicianNameByAutoComplete(term,groupid, id, new PageRequest(0, MaxResults));
		else
			physicianAcctObj = phyAccount.fetchPhysicianNameByAutoComplete(term,groupid, new PageRequest(0, MaxResults));

		return physicianAcctObj;

	}

	public List<PhysicianAccount> getAllAutoCompletePhysicianList(String term,int groupid) {
		int MaxResults=Integer.parseInt(env.getProperty("MaxResults"));
		List<PhysicianAccount> physicianAcctObj = phyAccount.fetchPhysicianNameByAutoComplete(term,groupid, new PageRequest(0, MaxResults));
		return physicianAcctObj;
	}

	public List<PhysicianAccount> getAutoCompletePhysicianListByPhysician(String term, String status, int id, int physicianId) {
		int MaxResults=Integer.parseInt(env.getProperty("MaxResults"));
		List<PhysicianAccount> physicianAcctObj = null;
		if (physicianId > 0)
			physicianAcctObj = phyAccount.getPhysicianListByPhysicianId(term, physicianId, new PageRequest(0, MaxResults));
		else
			physicianAcctObj = phyAccount.getPhysicianListByPhysicianId(term, new PageRequest(0, MaxResults));
		return physicianAcctObj;
	}

	public List<PhysicianAccount> getAllAutoCompletePhysicianListByPhysician(String term, int id, int physicianId) {
		int MaxResults=Integer.parseInt(env.getProperty("MaxResults"));
		List<PhysicianAccount> physicianAcctObj = null;
		if (physicianId > 0)
			physicianAcctObj = phyAccount.getAllPhysicianListByPhysicianId(term, physicianId, new PageRequest(0, MaxResults));
		else
			physicianAcctObj = phyAccount.getAllPhysicianListByPhysicianId(term, new PageRequest(0, MaxResults));
		return physicianAcctObj;
	}

	public List<PhysicianAccount> getAutoCompleteApprovedPhysicianList(String term, String status, int id, int groupid) {

		int MaxResults=Integer.parseInt(env.getProperty("MaxResults"));
		List<PhysicianAccount> physicianAcctObj = null;
		if (id > 0)
			physicianAcctObj = phyAccount.fetchApprovedPhysicianNameByAutoComplete(term,groupid, id, new PageRequest(0, MaxResults));
		else
			physicianAcctObj = phyAccount.fetchApprovedPhysicianNameByAutoComplete(term,groupid, new PageRequest(0, MaxResults));

		return physicianAcctObj;

	}

	public List<PhysicianAccount> getAutoCompleteApprovedPhysicianListByPhysician(String term, String status, int id, int physicianId) {
		int MaxResults=Integer.parseInt(env.getProperty("MaxResults"));
		List<PhysicianAccount> physicianAcctObj = null;
		if (physicianId > 0)
			physicianAcctObj = phyAccount.getApprovedPhysicianListByPhysicianId(term, physicianId, new PageRequest(0, MaxResults));
		else
			physicianAcctObj = phyAccount.getApprovedPhysicianListByPhysicianId(term, new PageRequest(0, MaxResults));
		return physicianAcctObj;
	}

	public List<PhysicianAccount> getAutoCompleteApprovedPhysicianList(String term, String status, int id) {
		int MaxResults=Integer.parseInt(env.getProperty("MaxResults"));
		// Status is not used due to we required Approved and Denied physicians. (updated by vijayakumar @ 14-12-2017)
		// List<PhysicianAccount> physicianAcctObj = phyAccount.fetchPhysicianNameByAutoComplete(term,status,id, new PageRequest(0, MaxResults));
		List<PhysicianAccount> physicianAcctObj = null;
		if (id == 0)
			physicianAcctObj = phyAccount.fetchNotNewApprovedPhysicianNameByAutoComplete(term, new PageRequest(0, MaxResults));
		else
			physicianAcctObj = phyAccount.fetchNotNewApprovedPhysicianNameByAutoComplete(term, id, new PageRequest(0, MaxResults));
		return physicianAcctObj;
	}


	public List<PhysicianAccount> getAutoCompletePhysicianList(String term, int id) {
		int MaxResults=Integer.parseInt(env.getProperty("MaxResults"));
		List<PhysicianAccount> physicianAcctObj = null;
		if (id > 0)
			physicianAcctObj = phyAccount.fetchAllPhysicianNameByAutoComplete(term, id, new PageRequest(0, MaxResults));
		else
			physicianAcctObj = phyAccount.fetchAllPhysicianNameByAutoComplete(term, new PageRequest(0, MaxResults));

		return physicianAcctObj;
	}


	public List<PhysicianAccount> getAutoCompleteGroupPhysicianList(String term, int id, int groupId) {
		int MaxResults=Integer.parseInt(env.getProperty("MaxResults"));
		List<PhysicianAccount> physicianAcctObj = null;
		if (id > 0)
			physicianAcctObj = phyAccount.fetchGroupPhysicianNameByAutoComplete(term, id, groupId, new PageRequest(0, MaxResults));
		else
			physicianAcctObj = phyAccount.fetchGroupPhysicianNameByAutoComplete(term, groupId, new PageRequest(0, MaxResults));

		return physicianAcctObj;
	}

	public String generatePdfFilePath(int physicianId) {
		String fileName = "";
		if (physicianId > 0) {
			String rootFilePath=env.getProperty("file.root_folder_path");
			String folderName= rootFilePath + File.separator + "physician";

			File f = new File(rootFilePath);
			if (!f.exists())
				f.mkdir();

			f = new File(folderName);
			if (!f.exists())
				f.mkdir();

			folderName= folderName + File.separator + physicianId;
			f = new File(folderName);
			if (!f.exists())
				f.mkdir();

			folderName= folderName + File.separator + "PDF";
			f = new File(folderName);
			if (!f.exists())
				f.mkdir();
			fileName= folderName + File.separator + "PhysicianPdf_"+physicianId+".pdf";
		}
		return fileName;
	}


	private PhysicianProfile getAdditionalDataForPdfGeneration(PhysicianProfile profile) {

		try {
			List<PhysicianClinic> clinicTableList = phyClinicRepo.findByPhysicianIdAndDelFlagOrderById(profile.getPhysicianId(), PharmacyUtil.deleteFlagNo);
			if (clinicTableList.size() > 0) {
				Clinic c = clinicRepo.findOne(clinicTableList.get(0).getClinicId());

				profile.setClinicName(c.getClinicName());
				profile.setClinicAddress(c.getAddress());
				profile.setClinicCity(c.getCity());
				profile.setClinicState(c.getState());
				profile.setClinicCountry("USA");
				profile.setClinicZipcode(c.getZipCode());
				profile.setClinicPhone(c.getPhone());
				profile.setClinicFax(c.getFax());
				profile.setClinicEmail1(c.getEmail());
				profile.setClinicEmail2("");

				profile.setBillingSameAsClinicAdd("Yes");
				profile.setBillingAddress(c.getAddress());
				profile.setBillingCity(c.getCity());
				profile.setBillingState(c.getState());
				profile.setBillingCountry("USA");
				profile.setBillingZipcode(c.getZipCode());
				profile.setPreferredShippingMethod(""); 	// Need to work after get response for clarifications

				profile.setClinicAuthorizedName1(c.getContactName());

				profile.setPaymentBillingName(c.getClinicName());
				profile.setPaymentBillingAddress(c.getAddress());
				profile.setPaymentBillingCity(c.getCity());
				profile.setPaymentBillingState(c.getState());
				profile.setPaymentBillingCountry("USA");
				profile.setPaymentBillingZipcode(c.getZipCode());

				profile.setReferenceBillClinic("YES");

				SimpleDateFormat dt = new SimpleDateFormat("MM/dd/yyyy");
				profile.setAgreeDate(dt.format( new java.sql.Date(new Date().getTime())));
				profile.setAgreePrintName(profile.getPhysicianName());

				String months[] = new String[13];
				 months[0] = null ;
			       months[1] = "January";
			       months[2] = "February";
			       months[3] = "March";
			       months[4] = "April";
			       months[5] = "May";
			       months[6] = "June";
			       months[7] = "July";
			       months[8] = "August";
			       months[9] = "September";
			       months[10] = "October";
			       months[11] = "November";
			       months[12] = "December";

				profile.setExpMonth(months[ Integer.parseInt(profile.getExpMonth()) ]);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}

		return profile;
	}



	public String getPhysicianPdfGenerated(int physicianId, String outputFileName, HttpSession session,String base64ImgString) {
		String fileNameDownload = "",imgPath="";

		try {
			int profileStatusUpdated=0;
			if (physicianId > 0) {
				String rootFilePath= PharmacyUtil.getRootFolderForPhysicianPDF(session, env);
				String folderName= rootFilePath + File.separator + physicianId;
				if(!new File(folderName).exists())
				{
					new File(folderName).mkdirs();
				}

				imgPath = folderName + File.separator + "physician_esign_"+physicianId+".png";

				if(base64ImgString.length()>0)
				{

					String value = base64ImgString.substring(base64ImgString.indexOf("base64,")+"base64,".length(),base64ImgString.length());
						//System.out.println("ESSSSSSSSIGN beforrrrrrrrrrrrreeeeeee value============"+value);
					//If the E-Sign is empty,it does not store the signature
					if(!value.equalsIgnoreCase("iVBORw0KGgoAAAANSUhEUgAAARQAAABGCAYAAAANQWsxAAAA90lEQVR4nO3UQQkAMAzAwPo3vZoIDMqdgrwyDyAyvwOAOwwFyBgKkDEUIGMoQMZQgIyhABlDATKGAmQMBcgYCpAxFCBjKEDGUICMoQAZQwEyhgJkDAXIGAqQMRQgYyhAxlCAjKEAGUMBMoYCZAwFyBgKkDEUIGMoQMZQgIyhABlDATKGAmQMBcgYCpAxFCBjKEDGUICMoQAZQwEyhgJkDAXIGAqQMRQgYyhAxlCAjKEAGUMBMoYCZAwFyBgKkDEUIGMoQMZQgIyhABlDATKGAmQMBcgYCpAxFCBjKEDGUICMoQAZQwEyhgJkDAXIGAqQMRQgYyhAZgGlTcO1TWDuNgAAAABJRU5ErkJggg==")
							&& !value.equalsIgnoreCase("iVBORw0KGgoAAAANSUhEUgAAARQAAABGCAYAAAANQWsxAAAAYUlEQVR4nO3BAQ0AAADCoPdPbQ8HFAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA8GUuNQABMDFUNQAAAABJRU5ErkJggg=="))
					{
						value = value.replaceAll(" ", "+");




			        	File af = new File(imgPath);
			        	if(af.exists())
			        		af.delete();

						byte[] btDataFile = new sun.misc.BASE64Decoder().decodeBuffer(value);
						File of = new File(imgPath);
						FileOutputStream osf = new FileOutputStream(of);
						osf.write(btDataFile);
						osf.flush();
					}

					profileStatusUpdated= phyAccount.updatePhysicianStatus(PharmacyUtil.statusProfileCompleted,physicianId);
					//System.out.println("Physician Profile Completed ======="+profileStatusUpdated);
				}

				PhysicianProfile profile = getPhysicianData(physicianId, "", "");
				profile = getAdditionalDataForPdfGeneration(profile);

				List<PhysicianProfile> profileList = new ArrayList<PhysicianProfile>();
				profileList.add(profile);
				JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(profileList);

				String realpath = session.getServletContext().getRealPath("/") + File.separator;

				if(!new File(imgPath).exists())
				{
					imgPath = realpath + "\\resources\\images\\spacer.png";
				}



				String checkedImagePath = realpath + "\\resources\\images\\checkbox_checked.jpg";
				String unCheckedImagePath = realpath + "\\resources\\images\\checkbox_unchecked.jpg";
				String SUB_REPORT_PATH = realpath + "\\JASP-RPT\\";

				String companyLogo = realpath + "\\resources\\images\\CRE8-Pharma-logo.png";
				//String companyLogo = realpath + "\\resources\\images\\cenegenics.png";

				Map<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("checkedImagePath", checkedImagePath);
				parameters.put("unCheckedImagePath", unCheckedImagePath);
				parameters.put("companyLogo", companyLogo);
				parameters.put("SUBREPORT_DIR",SUB_REPORT_PATH);
				parameters.put("signPath", imgPath);

				JasperPrint jasperPrint = JasperFillManager.fillReport(realpath + "\\JASP-RPT\\PhysicianPdf.jasper", parameters, itemsJRBean);

				OutputStream outputStream = new FileOutputStream(new File(outputFileName));
				JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);


				if(profileStatusUpdated==1)
					fileNameDownload = "PhysicianPdf_"+physicianId+"_ESigned.pdf";
				else
					fileNameDownload = "PhysicianPdf_"+physicianId+".pdf";

				//System.out.println("Physician PDF File Generated =="+fileNameDownload);

				String formattedFileName="";
				List<InputStream> list = new ArrayList<InputStream>();
				list.add(new FileInputStream(new File(outputFileName)));

				List<DocumentFileList> uploadFileList= getUploadedDocuments(physicianId);
				if(uploadFileList!=null && uploadFileList.size()>0)
				{
					for (int i = 0; i < uploadFileList.size(); i++) {

						if(uploadFileList.get(i).getFilePurpose().equalsIgnoreCase("DEA License"))
						{
							if(uploadFileList.get(i).getStoredFielName().indexOf(".pdf")==-1)
							{
								formattedFileName= folderName + File.separator + "DEA License_"+physicianId+".pdf";
								PharmacyUtil.ImageToPDF(uploadFileList.get(i).getStoredFielName(),formattedFileName);
								list.add(new FileInputStream(new File(formattedFileName)));
							}else
								list.add(new FileInputStream(new File(uploadFileList.get(i).getStoredFielName())));



						}else if(uploadFileList.get(i).getFilePurpose().equalsIgnoreCase("State License"))
						{
							if(uploadFileList.get(i).getStoredFielName().indexOf(".pdf")==-1)
							{
								formattedFileName= folderName + File.separator + "State License_"+physicianId+".pdf";
								PharmacyUtil.ImageToPDF(uploadFileList.get(i).getStoredFielName(),formattedFileName);

								list.add(new FileInputStream(new File(formattedFileName)));
							}else
								list.add(new FileInputStream(new File(uploadFileList.get(i).getStoredFielName())));

						}

					}

				}


				String outputFile=folderName + File.separator + fileNameDownload;
				// Resulting pdf
			    OutputStream out = new FileOutputStream(new File(outputFile));

			    PharmacyUtil.mergePdfFiles(list, out);

			    /*if(profileStatusUpdated==1)
			    {
			    	File source = new File(folderName + File.separator + "PhysicianPdf_"+physicianId+".pdf");
			        File destination = new File(folderName + File.separator + "PhysicianPdf_"+physicianId+"_ESigned.pdf");
			    	FileUtils.copyFile(source, destination);
			    }*/



			    imgPath = folderName + File.separator + "physician_esign_"+physicianId+".png";

				File af = new File(imgPath);
	        	if(af.exists())
	        		af.delete();

			}
		} catch(Exception e){
			e.printStackTrace();
		}

		return fileNameDownload;
	}
	 /**
     * Do you wish to re-assign all the Patients of this Pysician to a different Physician?
     * and deactivates the current physician in normal save
     * @param otherPhysicianId
     * @param physicianId
     * @return
     */
    public void reAssignPhysicianId( Integer otherPhysicianId, Integer physicianId,LoginForm loggedInUser)
    {
        try
        {
//    		//System.out.println("reAssignPhysicianId :: otherPhysicianId ==="+otherPhysicianId+" :: Current Physician Id :: "+physicianId);
			int reassignedRow= phyAccount.reAssignPatientPhysician(PharmacyUtil.deleteFlagNo,otherPhysicianId,loggedInUser.getUserid(),PharmacyUtil.getCurrentTimeStamp(),loggedInUser.getType(), physicianId);
//			//System.out.println("reAssignPatientPhysician ======="+reassignedRow);
			reassignedRow=phyAccount.deActivatePhysician(PharmacyUtil.statusDenied,loggedInUser.getUserid(),loggedInUser.getType(),PharmacyUtil.getCurrentTimeStamp(),loggedInUser.getUserid(),loggedInUser.getType(),PharmacyUtil.getCurrentTimeStamp(), physicianId);
//			//System.out.println("deActivatePhysician ======="+reassignedRow);

			deniedPhysicianNotification(physicianId, loggedInUser);
        }
        catch (Exception e)
        {
        	e.printStackTrace();

        }
    }

    /**
     * All Patients of this Physician will be DeActivated(Denied).
     * If you wish to re-assign them to different Physicians go to the Patient lists and re-assign
     * @param physicianId
     * @return
     */
    public void deActivatePhysician(Integer physicianId,LoginForm loggedInUser)
    {
        try
        {
			// //System.out.println("deActivatePhysician :: Current Physician Id :: "+physicianId);
			int deactivatedRow= phyAccount.deActivatePatientPhysician(PharmacyUtil.deleteFlagYes,loggedInUser.getUserid(),PharmacyUtil.getCurrentTimeStamp(),loggedInUser.getType(), physicianId);
			// //System.out.println("deActivatePatientPhysician ======="+deactivatedRow);
			deactivatedRow=phyAccount.deActivatePhysician(PharmacyUtil.statusDenied,loggedInUser.getUserid(),loggedInUser.getType(),PharmacyUtil.getCurrentTimeStamp(),loggedInUser.getUserid(),loggedInUser.getType(),PharmacyUtil.getCurrentTimeStamp(), physicianId);
			// //System.out.println("deActivatePhysician ======="+deactivatedRow);
			deniedPhysicianNotification(physicianId, loggedInUser);
        }
        catch (Exception e)
        {
        	e.printStackTrace();
        }
    }

    public void approvalPhysicianNotification(int physicianId, LoginForm loggedInUser) {
    	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		PhysicianProfile form = getPhysicianData(physicianId, null, null);

    	Notification notify = notificationService.getPhysicianReapprove(physicianId);
		if (notify == null)
			notify = new Notification();
		StringBuilder sb = new StringBuilder();
		sb.append("Dr. ").append( form.getPhysicianName() ).append(" info changed on ");
		sb.append( sdf.format( new Date() ));

		notify.setDelFlag(PharmacyUtil.deleteFlagNo);
		notify.setNotificationMessage(sb.toString());
		notify.setNotifiedDate(PharmacyUtil.getCurrentTimeStamp());
		notify.setNotifyBy(loggedInUser.getUserid());
		notify.setNotifyRecordId(form.getPhysicianId());
		notify.setNotifyRecordType(PharmacyUtil.pagePhysician);
		notify.setNotifyTypeId(1);							// Physician Reapproved
		notify.setNotifyUserType(loggedInUser.getType());
		notify.setPhysicianName(form.getPhysicianName());
		notify.setExpiryDate( PharmacyUtil.getNotificationExpirtyDate(7));

		notificationService.saveNotification(notify);

		if (PharmacyUtil.statusDenied.equalsIgnoreCase(form.getStatus()))
			notificationService.physicianSetDeleteYes(form.getPhysicianId());

    }


    public void deniedPhysicianNotification(int physicianId, LoginForm loggedInUser) {

    	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		PhysicianProfile form = getPhysicianData(physicianId, null, null);
		Notification notify = notificationService.getPhysicianDeactivated(form.getPhysicianId());
		if (notify == null)
			notify = new Notification();

		StringBuilder sb = new StringBuilder();
		sb.append("Dr. ").append( form.getPhysicianName() ).append(" was denied on ");
		sb.append( sdf.format( new Date() ));

		notify.setDelFlag(PharmacyUtil.deleteFlagNo);
		notify.setNotificationMessage(sb.toString());
		notify.setNotifiedDate(PharmacyUtil.getCurrentTimeStamp());
		notify.setNotifyBy(loggedInUser.getUserid());
		notify.setNotifyRecordId(form.getPhysicianId());
		notify.setNotifyRecordType(PharmacyUtil.pagePhysician);
		notify.setNotifyTypeId(2);							// Physician Denied
		notify.setNotifyUserType(loggedInUser.getType());
		notify.setPhysicianName(form.getPhysicianName());
		notify.setExpiryDate( PharmacyUtil.getNotificationExpirtyDate(7));

		notificationService.saveNotification(notify);

		notificationService.physicianSetDeleteYes(form.getPhysicianId());

    }


    public void newClinicAddedByPhysician(int physicianId, LoginForm loggedInUser, int clinicId) {

    	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		PhysicianProfile form = getPhysicianData(physicianId, null, null);

    	Notification notify = notificationService.getPhysicianCreateNewClinic(form.getPhysicianId());
		if (notify == null)
			notify = new Notification();

		StringBuilder sb = new StringBuilder();
		sb.append("Dr. ").append( form.getPhysicianName() ).append(" was added new clinic on ");
		sb.append( sdf.format( new Date() ));

		notify.setDelFlag(PharmacyUtil.deleteFlagNo);
		notify.setNotificationMessage(sb.toString());
		notify.setNotifiedDate(PharmacyUtil.getCurrentTimeStamp());
		notify.setNotifyBy(loggedInUser.getUserid());
		notify.setNotifyRecordId(form.getPhysicianId());
		notify.setNotifyRecordType(PharmacyUtil.pagePhysician);
		notify.setNotifyTypeId(3);							// Physician New Clinic added
		notify.setNotifyUserType(loggedInUser.getType());
		notify.setPhysicianName(form.getPhysicianName());
		notify.setClinicId(clinicId);

		notificationService.saveNotification(notify);

		if (PharmacyUtil.statusDenied.equalsIgnoreCase(form.getStatus()))
			notificationService.physicianSetDeleteYes(form.getPhysicianId());
    }


    public void criticalCommentsChangedByPhysician(int physicianId, LoginForm loggedInUser) {
    	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		PhysicianProfile form = getPhysicianData(physicianId, null, null);

    	Notification notify = notificationService.getPhysicianCriticalCommentsChanges(form.getPhysicianId());
		if (notify == null)
			notify = new Notification();

		StringBuilder sb = new StringBuilder();
		sb.append("Dr. ").append( form.getPhysicianName() ).append(" was changed critical comments on ");
		sb.append( sdf.format( new Date() ));

		notify.setDelFlag(PharmacyUtil.deleteFlagNo);
		notify.setNotificationMessage(sb.toString());
		notify.setNotifiedDate(PharmacyUtil.getCurrentTimeStamp());
		notify.setNotifyBy(loggedInUser.getUserid());
		notify.setNotifyRecordId(form.getPhysicianId());
		notify.setNotifyRecordType(PharmacyUtil.pagePhysician);
		notify.setNotifyTypeId(4);							// Physician Critical Comments Changed
		notify.setNotifyUserType(loggedInUser.getType());
		notify.setPhysicianName(form.getPhysicianName());

		notificationService.saveNotification(notify);

		if (PharmacyUtil.statusDenied.equalsIgnoreCase(form.getStatus()))
			notificationService.physicianSetDeleteYes(form.getPhysicianId());
    }


    public void cancelCriticalCommentsNotificationByPhysician(int physicianId) {
    	try {
    		Notification notify = notificationService.getPhysicianCriticalCommentsChanges(physicianId);
    		if (notify != null) {
    			notify.setDelFlag(PharmacyUtil.deleteFlagYes);
    			notify.setExpiryDate(PharmacyUtil.getCurrentTimeStamp());

    			notificationService.saveNotification(notify);
    		}
    	} catch(Exception e){
    		e.printStackTrace();
    	}



    }

		
	public boolean sendPhysicianAccountMail(PhysicianProfile form,LoginForm loggedInUser, HttpSession session,HttpServletRequest request, Environment env) {
		boolean isSend = false;
		try{
		String loginUrl = PharmacyUtil.getCurrentSystemUrl(request)+"/login?s=1";  // 1 means User Type is Physician
		PhysicianAccount rec = phyAccount.findOne(form.getPhysicianId());
		PhysicianProfile profile = getPhysicianData(rec.getId(), env.getProperty("file.photo_path"), env.getProperty("file.logo_path"));
		//PublishPhysicianPushEvent(profile,previousStatus);

		if (rec!=null && form.getPhysicianId() > 0 && "Yes".equalsIgnoreCase(form.getSendMailPermission()) && "true".equalsIgnoreCase(env.getProperty("isMailSend"))) {
			String fileName = null;
			String attachmentFileName = null;
			if ("true".equalsIgnoreCase(env.getProperty("includeAttachment"))) {
				// Include attachments here...
			}

			MailSendConfig mail = new MailSendConfig();
			mail.MailSending(profile, env, "AdminForPhysicianSignup", loginUrl, fileName, attachmentFileName );
			isSend = true;
		}
	
	} catch (Exception e) {
		e.printStackTrace();     
		isSend=false;
	}
		return isSend;
		}

}

