package com.pharma.core.pharmaservices.prescription;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.pharma.core.model.pharmacygroup.GroupMaster;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
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
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import com.pharma.core.formBean.LoginForm;
import com.pharma.core.formBean.physician.PhysicianProfile;
import com.pharma.core.formBean.prescription.PrescriptionForm;
import com.pharma.core.formBean.prescription.PrescriptionJSonObj;
import com.pharma.core.formBean.prescription.PrescriptionTransactionForm;
import com.pharma.core.model.clinic.Clinic;
import com.pharma.core.model.notifications.Notification;
import com.pharma.core.model.patientaccount.PatientAccount;
import com.pharma.core.model.physician.PhysicianAccount;
import com.pharma.core.model.physician.PhysicianClinic;
import com.pharma.core.model.physician.PhysicianGroup;
import com.pharma.core.model.physician.PhysicianProfileInfo;
import com.pharma.core.model.pioneer.PrescriptionItem;
import com.pharma.core.model.pioneer.PrescriptionMedication;
import com.pharma.core.model.prescription.PrescriptionMaster;
import com.pharma.core.model.prescription.PrescriptionTransaction;
import com.pharma.core.model.prescription.SubtanceItemDocuments;
import com.pharma.core.pharmaservices.PrescriberTypeMasterService;
import com.pharma.core.pharmaservices.clinic.ClinicService;
import com.pharma.core.pharmaservices.notifications.NotificationService;
import com.pharma.core.pharmaservices.patientservices.PatientAccountService;
import com.pharma.core.pharmaservices.pharmacygroupservices.GroupMasterFileUploadService;
import com.pharma.core.pharmaservices.pharmacygroupservices.GroupMasterService;
import com.pharma.core.pharmaservices.physicianservices.PhysicianAccountService;
import com.pharma.core.pharmaservices.physicianservices.PhysicianFileUploadService;
import com.pharma.core.pharmaservices.physicianservices.PhysicianGroupService;
import com.pharma.core.pharmaservices.pioneer.PrescriptionItemDEAOverrideService;
import com.pharma.core.repository.clinic.ClinicRepository;
import com.pharma.core.repository.order.OrderMasterRepository;
import com.pharma.core.repository.pharmacygroup.GroupDirectorAccountRespository;
import com.pharma.core.repository.physician.PhysicianAssistantAccountRespository;
import com.pharma.core.repository.physician.PhysicianClinicRepository;
import com.pharma.core.repository.physician.PhysicianGroupRepository;
import com.pharma.core.repository.physician.PhysicianProfileRespository;
import com.pharma.core.repository.pioneer.PrescribedItemTypeRepository;
import com.pharma.core.repository.pioneer.PrescriptionDiagnosisICD10Repository;
import com.pharma.core.repository.pioneer.PrescriptionItemDispensingUnitRepository;
import com.pharma.core.repository.pioneer.PrescriptionItemRepository;
import com.pharma.core.repository.pioneer.PrescriptionMedicationRepository;
import com.pharma.core.repository.pioneer.PrescriptionSigCodesRepository;
import com.pharma.core.repository.prescription.PrescriptionRepository;
import com.pharma.core.repository.prescription.PrescriptionTranRepository;
import com.pharma.core.repository.prescription.SubtanceItemsDocumentsRepository;
import com.pharma.core.util.PharmacyUtil;
/**
 * This is an implementation class that loads and stores the prescription data
 *
 */
@Service("prescriptionService")
@PropertySource("classpath:prescription.properties") 
public class PrescriptionServiceImpl implements PrescriptionService {

	@Autowired
	PatientAccountService patientService;
	
	@Autowired
	PhysicianAccountService phyService;
	
	@Autowired
	PhysicianGroupService phyGroupService;
	
	@Autowired
	GroupMasterService groupService;
	
	@Autowired
	PrescriptionRepository prescriptionRepo;

	@Autowired
	PrescriptionTranService prescriptionTranService; 

	@Autowired
	PrescriptionTranRepository prescriptionTranRepo;
	
	@Autowired
	ClinicService clinicService;
	
	@Autowired
	PrescribedItemTypeRepository itemTypeRepo;
	
	@Autowired
	PrescriptionDiagnosisICD10Repository icd10Repo;
	
	@Autowired
	PrescriptionSigCodesRepository directionRepo;
	
	@Autowired
	PrescriptionItemRepository itemRepo;
	
	@Autowired
	PrescriptionItemDispensingUnitRepository unitMasterRepo;
	
	@Autowired
	PhysicianAssistantAccountRespository assistantRepo;
	
	@Autowired
	GroupDirectorAccountRespository groupDirectorRepo;
	
	@Autowired
	PhysicianProfileRespository phyProfile;
	
	@Autowired
	PrescriptionItemDEAOverrideService itemDEAService;
	
	@Autowired
	PrescriptionMedicationRepository medicationRepo;
	
	@Autowired
	SubtanceDocumentsService subtanceDocService;
	
	@Autowired
	SubtanceItemsDocumentsRepository substanceDocRepo;
	
	@Autowired
	OrderMasterRepository orderMasterRepo;
	
	@Autowired
	PrescriberTypeMasterService prescriberTypeMasterService;
	
	@Autowired
	private Environment env;
	
	@Autowired
	GroupMasterFileUploadService groupfileUploadService;
	
	@Autowired
	PhysicianFileUploadService physicianfileUploadService;
	
	@Autowired
	ClinicRepository clinicRepo;
	
	@Autowired
	PhysicianClinicRepository phyClinicRepo;
	
	@Autowired
	PhysicianGroupRepository phyGroupRepo;
	
	@Autowired
	NotificationService notificationService;
	
	public String getPatientById(int id,int physicianId) {
		PrescriptionForm form = new PrescriptionForm();
		try {
			PatientAccount acc = patientService.getPatientData(id);
			SimpleDateFormat sm = new SimpleDateFormat("MM/dd/yyyy");
			if (acc.getAddress() != null)
				form.setBillingAddress(acc.getAddress()+"");
			else
				form.setBillingAddress("");
			form.setBillingCity(acc.getCity()+"");
			form.setBillingName(acc.getPatientName()+"");
			form.setBillingState(acc.getState()+"");
			form.setBillingZipCode(acc.getZipCode()+"");
			form.setBillingCountry(acc.getCountry());
			if (acc.getDateOfBirth() != null)
				form.setPatientDateOfBirth( sm.format(acc.getDateOfBirth()) );
			else
				form.setPatientDateOfBirth("");
			form.setDiagnosis("");

			//Modified on Feb 7,2018-Since Multiple Groups handled==>In Prescription, Witten By Physician Grooup is assigned
			PhysicianGroup phyGroup = phyGroupRepo.findRecordByPhysicianId(physicianId);
			int groupId =0;
			if (phyGroup != null) {
				groupId = phyGroup.getGroupId();
			}
			
			//int groupId = acc.getGroupId();
			
			//Commented on jan 22,2018
			/*String groupName = "";
			List<PhysicianGroup> list = phyGroupService.getByPhysician(acc.getPhysicianId());
			if (list.size() > 0) {
				groupId	= list.get(0).getGroupId();
				if (groupId > 0)
					groupName = groupService.getGroupMasterDetails(groupId).getGroupName();
			} */
			
			form.setGroupId( groupId );
			GroupMaster groupMaster = groupService.getGroupMasterDetails(groupId);
			if(groupMaster!=null)
				form.setGroupName(groupMaster.getGroupName());
			
			form.setRxICD10("");
			// form.setPhyPhone(acc.getMobile()+"");
			if (acc.getAddress() != null)
				form.setPatientAddress(acc.getAddress()+"");
			else
				form.setPatientAddress("");
			form.setPatientCity(acc.getCity()+"");
			form.setPatientId(acc.getId());
			form.setPatientName(acc.getPatientName()+"");
			form.setPatientState(acc.getState()+"");
			form.setPatientZipCode(acc.getZipCode()+"");
			form.setPatientCountry(acc.getCountry());
			form.setPatientMobile(acc.getMobile());
			form.setPatientPhone(acc.getPhone());
			//Commented on jan 22,2018
			//form.setPhysicianId(acc.getPhysicianId());
			//form.setPhysicianName( phyService.getPhysicianAccountDetails(acc.getPhysicianId()).getPhysicianName() +"");
			form.setPrescriptionId(0);
			if (acc.getAddress() != null)
				form.setShippingAddress(acc.getAddress()+"");
			else
				form.setShippingAddress("");
			form.setShippingCity(acc.getCity()+"");
			form.setShippingName(acc.getPatientName()+"");
			form.setShippingState(acc.getState()+"");
			form.setShippingZipCode(acc.getZipCode()+"");
			form.setShippingCountry(acc.getCountry());
			
			if(acc.getSsn()!=null && acc.getSsn().length()>=4)
				form.setPatientSsn(acc.getSsn().substring(acc.getSsn().length()-4, acc.getSsn().length())+"");
			else
				form.setPatientSsn(acc.getSsn());
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json2 = gson.toJson(form);	
		
		return json2; 
	}

	
	public String getPhysicianById(int physicinId, int prescriptionId) {
		
		PrescriptionForm form = new PrescriptionForm();
		form = getPhysicianDataById(physicinId, prescriptionId, form);
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json2 = gson.toJson(form);	
		
		return json2; 
	}

	public PrescriptionForm getPhysicianDataById(int id, int prescriptionId, PrescriptionForm form) {
		try {
			int savedClinicId=0;
			PrescriptionMaster prescriptionObj = null;
			if (prescriptionId > 0) { 
				prescriptionObj = prescriptionRepo.findOne(prescriptionId);
				savedClinicId = prescriptionObj.getClinicId();
				
				Clinic clinicObj = clinicRepo.findOne(savedClinicId);
				form.setClinicName(clinicObj.getClinicName());
				form.setClinicId(clinicObj.getId());
			}
			
			PhysicianProfile acc = phyService.getPhysicianData(id, null, null);
			/*List<Clinic> clinicObj=clinicService.getClinicList(acc.getClinicId());
			form.setClinicName(clinicObj.get(0).getClinicName());
			form.setClinicId(clinicObj.get(0).getId());*/

			String status= PharmacyUtil.statusActive;
			
			// physicianClinicList
			List<Clinic> physicianClinicList = clinicRepo.getClinicListByPhysicianIdAndClinicId(id, savedClinicId, status);
			form.setClinicList(physicianClinicList);
			
			int groupId = 0;
			String groupName = "";
			List<PhysicianGroup> list = phyGroupService.getByPhysician(acc.getPhysicianId());
			if (list.size() > 0) {
				groupId	= list.get(0).getGroupId();
				if (groupId > 0)
					groupName = groupService.getGroupMasterDetails(groupId).getGroupName();
			} 
			form.setPhysicianId( acc.getPhysicianId() );
			form.setGroupId( groupId );
			form.setGroupName(groupName);

			form.setPhyAddress(acc.getAddress1());
			form.setPhyCity(acc.getCity());
			form.setPhyCountry(acc.getCountry());
			form.setPhyPhone(acc.getMobile());
			form.setPhyState(acc.getState());
			form.setPhyZipCode(acc.getZipCode());
			
			form.setPhyDea(acc.getDea());
			form.setPhyNpi(acc.getNpi());
			form.setPhyUpin(acc.getUpin());
			form.setPhyStateLicense(acc.getStateLicense());
			form.setPhyMedicaid(acc.getMedicaid());
			form.setUseGroupLogo(acc.getUseGroupLogo());
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return form;
	}
	
	
	
	// update patient address when updating patient data using link from prescription view patient page.
	public boolean updatePatientData(int PrescriptionId, PatientAccount patientAcc,LoginForm loggedInUser) {
		boolean isSaved = false;
		PrescriptionMaster acc = null;
		try  {
			if (PrescriptionId > 0) {
				acc = prescriptionRepo.findOne(PrescriptionId);
			
				acc.setPatientAddress(patientAcc.getAddress());
				acc.setPatientCity(patientAcc.getCity());
				acc.setPatientCountry(patientAcc.getCountry());
				if (patientAcc.getDateOfBirth() != null)
					acc.setPatientDateOfBirth(patientAcc.getDateOfBirth());
				acc.setPatientId(patientAcc.getId());
				acc.setPatientMobile(patientAcc.getMobile());
				acc.setPatientName(patientAcc.getPatientName());
				acc.setPatientSsn(patientAcc.getSsn().substring(patientAcc.getSsn().length()-4, patientAcc.getSsn().length())+"" );
				acc.setPatientState(patientAcc.getState());
				acc.setPatientZipCode(patientAcc.getZipCode());
				
				acc.setLastUpdatedBy(loggedInUser.getUserid());
				acc.setLastUpdatedUser(loggedInUser.getType());
				acc.setLastUpdatedDate( PharmacyUtil.getCurrentTimeStamp() );
				
				prescriptionRepo.save(acc);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return isSaved;
	}

	public PrescriptionMaster savePrescription(PrescriptionForm form, String rootFilePath, HttpSession session, boolean isPdfGenerate, LoginForm loggedInUser) {
		PrescriptionMaster acc = null;
		PrescriptionMaster out = null;
		try {
			
			boolean isControlSubstance = false;
			// boolean isProcessStarts = false;
			
			int defaultPrescriptionNo=Integer.parseInt(env.getProperty("default.prescription_number"));
			String defaultPrescriptionFormat = env.getProperty("default.prescription_number_format");
			if (form.getPrescriptionId() > 0)
			{
				acc = prescriptionRepo.findOne(form.getPrescriptionId());
				form.setPrescriptionNumber(acc.getPrescriptionNumber());
			}
			
			if (acc == null) {
				acc = new PrescriptionMaster();
				acc.setPrescriptionDate(new java.sql.Date(new Date().getTime()));
				
				acc.setCreatedBy(loggedInUser.getUserid());
				acc.setCreatedUser(loggedInUser.getType());
				acc.setCreatedDate( PharmacyUtil.getCurrentTimeStamp() );
				
				int presNumber=prescriptionRepo.getMaxPrescriptionNumber();
				if(presNumber==0)
					presNumber=defaultPrescriptionNo;
				else
					++presNumber;
				acc.setPrescriptionNumber(presNumber);
				acc.setCre8PrescriptionNo(defaultPrescriptionFormat + presNumber);
				form.setPrescriptionNumber(presNumber);
				acc.setStatusId(1);//1==>New Prescription Created
			}
				
			try {
				List<PrescriptionTransactionForm> formList = form.getMedications();
				if (formList != null && formList.size() > 0) {
					for (PrescriptionTransactionForm f: formList) {
						if(f.getControlSubstance()!=null && !f.getControlSubstance().equalsIgnoreCase("N") && f.getControlSubstance().equalsIgnoreCase("2"))
						{
							if(acc.getStatusId()==1)
							{
								acc.setStatusId(2);//2==>Controlled Subtance of Class 2
								isControlSubstance = true;
							}
						}
					}
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			
			if(form.getFax()==null)
				acc.setFax("N");
			else
				acc.setFax(form.getFax());
			if(form.getScan()==null)
				acc.setScan("N");
			else
				acc.setScan(form.getScan());
			
			//if(acc.getStatusId()==3)
			{
				try {
					if(acc.getScan().equalsIgnoreCase("Y") || acc.getFax().equalsIgnoreCase("Y"))
						acc.setStatusId(4);//4==>Prescription Scanned / Prescription Faxed
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (acc.getScan().equalsIgnoreCase("Y") || acc.getFax().equalsIgnoreCase("Y"))
				isControlSubstance = false;
			
			
			acc.setLastUpdatedBy(loggedInUser.getUserid());
			acc.setLastUpdatedUser(loggedInUser.getType());
			acc.setLastUpdatedDate( PharmacyUtil.getCurrentTimeStamp() );
			
			acc.setAllergies(form.getAllergies());
			acc.setBillingAddress(form.getBillingAddress());
			acc.setBillingAddressId(form.getBillingAddressId());
			acc.setBillingCity(form.getBillingCity());
			acc.setBillingCountry(form.getBillingCountry());
			acc.setBillingName(form.getBillingName());
			acc.setBillingState(form.getBillingState());
			acc.setBillingZipCode(form.getBillingZipCode());
			acc.setClinicId(form.getClinicId());
			acc.setClinicName(form.getClinicName());
			acc.setClinicPo(form.getClinicPo());
			acc.setCreditCardNumber(form.getCreditCardNumber());
			acc.setCreditCardType(form.getCreditCardType());
			acc.setCvcCode(form.getCvcCode());
			acc.setDiagnosis(form.getDiagnosis());
			acc.setGroupId(form.getGroupId());
			
			acc.setNameOnCard(form.getNameOnCard());
			acc.setOtherShippingCompany(form.getOtherShippingCompany());
			acc.setOtherShippingMethod(form.getOtherShippingMethod());
			acc.setPatientAddress(form.getPatientAddress());
			acc.setPatientBillToId(form.getPatientBillToId());
			acc.setPatientCity(form.getPatientCity());
			acc.setPatientCountry(form.getPatientCountry());
			if (form.getPatientDateOfBirth() != null && !"".equalsIgnoreCase(form.getPatientDateOfBirth()))
				acc.setPatientDateOfBirth( PharmacyUtil.getSqlDateFromString(form.getPatientDateOfBirth()));
			acc.setPatientId(form.getPatientId());
			acc.setPatientPhone(form.getPatientPhone());
			acc.setPatientMobile(form.getPatientMobile());
			acc.setPatientName(form.getPatientName());
			acc.setPatientShipToId(form.getPatientShipToId());
			acc.setPatientSsn(form.getPatientSsn());
			acc.setPatientState(form.getPatientState());
			acc.setPatientZipCode(form.getPatientZipCode());
			acc.setPaymentTerms(form.getPaymentTerms());
			acc.setPaymentType(form.getPaymentType());
			acc.setPhyAddress(form.getPhyAddress());
			acc.setPhyCity(form.getPhyCity());
			acc.setPhyCountry(form.getPhyCountry());
			acc.setPhyDea(form.getPhyDea());
			acc.setPhyMedicaid(form.getPhyMedicaid());
			acc.setPhyNpi(form.getPhyNpi());
			acc.setPhyPhone(form.getPhyPhone());
			acc.setPhysicianId(form.getPhysicianId());
			acc.setPhyState(form.getPhyState());
			acc.setPhyStateLicense(form.getPhyStateLicense());
			acc.setPhyUpin(form.getPhyUpin());
			acc.setPhyZipCode(form.getPhyZipCode());
		
			acc.setRemoveSignature(form.getRemoveSignature());
			acc.setShippingAccountNo(form.getShippingAccountNo());
			acc.setShippingAddress(form.getShippingAddress());
			acc.setShippingAddressId(form.getShippingAddressId());
			acc.setShippingCity(form.getShippingCity());
			acc.setShippingCompany(form.getShippingCompany());
			acc.setShippingCountry(form.getShippingCountry());
			acc.setShippingMethod(form.getShippingMethod());
			acc.setShippingName(form.getShippingName());
			acc.setShippingState(form.getShippingState());
			acc.setShippingZipCode(form.getShippingZipCode());
			acc.setWrittenBy(form.getWrittenBy());
			acc.setWrittenByName(form.getWrittenByName());
			acc.setUseGroupLogo(form.getUseGroupLogo());
			
			
			out = prescriptionRepo.save(acc);
			form.setPrescriptionId(out.getId());
			
			savePrescriptionTransaction(form, loggedInUser, isControlSubstance, acc.getPatientName(), session);
			/*if (isTranSaved && isPdfGenerate) {
				generatePrescriptionPdf(out.getId(), rootFilePath, session);
			}*/
			
			/*SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
			StringBuilder sb = new StringBuilder();
			sb.append( form.getCre8PrescriptionNo()).append(",");
			sb.append(form.getRxNumber()).append(",");
			sb.append(acc.getPatientName()).append(",");
			sb.append( sdf.format( new Date() ));
			
			if (isTranSaved && isControlSubstance) {
				Notification notify = notificationService.getPrescriptionControlledSubstance(form.getPrescriptionId());
				if (notify == null) 
					notify = new Notification();

				notify.setDelFlag(PharmacyUtil.deleteFlagNo);
				notify.setNotificationMessage(sb.toString());
				notify.setNotifiedDate(PharmacyUtil.getCurrentTimeStamp());
				notify.setNotifyBy(loggedInUser.getUserid());
				notify.setNotifyRecordId(form.getPrescriptionId());
				notify.setNotifyRecordType(PharmacyUtil.pagePrescription);
				notify.setNotifyTypeId(5);							// Controlled subtance of Class 2  Upload documents of either Prescription Scanned / Prescription
				notify.setNotifyUserType(loggedInUser.getType());
				notify.setPatientName(form.getPatientName());
				notify.setPhysicianName( phyService.getPhysicianAccountDetails(form.getPhysicianId()).getPhysicianName() );
				
				notificationService.saveNotification(notify);
			}*/
			
			
		} catch(Exception e){ 
			e.printStackTrace();
		}
		
		return out;
	}

	public boolean savePrescriptionTransaction(PrescriptionForm form, LoginForm loggedInUser, boolean isControlSubstance, String patientName, HttpSession session) {
		boolean isSaved = true;
		
		try {
			String defaultPrescriptionFormat = env.getProperty("default.prescription_number_format");
			// set all previously stored records to delete
			List<PrescriptionTransaction> accList = prescriptionTranRepo.findByPrescriptionId(form.getPrescriptionId());
			if (accList.size() > 0) {
				for (PrescriptionTransaction a: accList) {
					a.setDelFlag("Y");
					prescriptionTranRepo.save(a);
				}
			}
			
			// Records inserted / updated 
			List<PrescriptionTransactionForm> formList = form.getMedications();
			if (formList != null && formList.size() > 0) {
				for (PrescriptionTransactionForm f: formList) {
					PrescriptionTransaction acc = null;
					if (f.getItemname() != null && !"".equalsIgnoreCase(f.getItemname()) || f.getMedicationdescription() != null && !"".equalsIgnoreCase(f.getMedicationdescription())) {
						if (f.getId() > 0) {
							acc = prescriptionTranRepo.findOne(f.getId());
							
							/*if (!acc.getItemname().equalsIgnoreCase(f.getItemname())) {
								prescriptionMedicationChangesNotification(acc.getId(), loggedInUser, session);
							}else if (!acc.getMedicationdescription().equalsIgnoreCase(f.getMedicationdescription())) {
								prescriptionMedicationChangesNotification(acc.getId(), loggedInUser, session);
							}*/
							
							
							
							if(acc!=null && acc.getType().equalsIgnoreCase("1")){
								if (!acc.getItemname().equalsIgnoreCase(f.getItemname())) {
									prescriptionItemChangesNotification(acc.getId(), loggedInUser, session);
								}
							}
							
							if(acc!=null && acc.getType().equalsIgnoreCase("2")){
								 if (!acc.getMedicationdescription().equalsIgnoreCase(f.getMedicationdescription())) {
									prescriptionMedicationChangesNotification(acc.getId(), loggedInUser, session);
								}	
							}
							
							
						} else {
							acc = new PrescriptionTransaction();
							
							int presNumber=prescriptionRepo.getMaxPrescriptionNumberCount(form.getPrescriptionId());
							if(presNumber==0)
								presNumber=1;
							else
								++presNumber;
							acc.setPrescriptionNo(presNumber);
							acc.setCre8PrescriptionNo(defaultPrescriptionFormat+""+form.getPrescriptionNumber()+"-"+presNumber);
						}
						
						acc.setAuto(f.getAuto());
						acc.setDwa(f.getDwa());
						if (f.getExpireDate() != null && !"".equalsIgnoreCase(f.getExpireDate()) )
							acc.setExpireDate( PharmacyUtil.getSqlDateFromString(f.getExpireDate()) );
						acc.setIcd10(f.getIcd10());
						/*acc.setItemid(f.getItemid());
						acc.setItemname(f.getItemname());
						acc.setMedicationid(f.getMedicationid());
						acc.setMedicationdescription(f.getMedicationdescription());*/
						
						if(f.getType().equalsIgnoreCase("1")){
							acc.setItemid(f.getItemid());
							acc.setItemname(f.getItemname());
							acc.setMedicationid(0);
							acc.setMedicationdescription(null);
							
						}
						else if(f.getType().equalsIgnoreCase("2")){
							acc.setMedicationid(PharmacyUtil.getIntAmount(f.getItemid()));
							acc.setMedicationdescription(f.getItemname());
							acc.setItemid(null);
							acc.setItemname(null);
						}
						
						if (f.getLastFilledDate() != null && !"".equalsIgnoreCase(f.getLastFilledDate()) )
							acc.setLastFilledDate( PharmacyUtil.getSqlDateFromString(f.getLastFilledDate()) );
						acc.setOrigin(f.getOrigin());
						acc.setPrescriptionId(form.getPrescriptionId());
						acc.setPrn(f.getPrn());
						acc.setQuantity(f.getQuantity());
						acc.setRefills(f.getRefills());
						acc.setRefillsFilled(f.getRefillsFilled());
						//until rxnumber is filled, the refills remaining will be the same as refills entered
						if(acc.getRxNumber()==null || acc.getRxNumber().length()==0)
						{
							acc.setRefillsRemaining(f.getRefills());
						}
						acc.setRxNumber(f.getRxNumber());
						acc.setRxStatus(f.getRxStatus());
						acc.setSigCodes(f.getSigCodes());
						acc.setTrackingNumber(f.getTrackingNumber());
						acc.setType(f.getType());
						acc.setUnitName(f.getUnitName());
						acc.setDelFlag("N");
						acc.setDaysSupply(f.getDaysSupply());
						acc.setControlSubstance(f.getControlSubstance());
						
						acc.setFutureFill(f.getFutureFill());
						acc.setPriortyType(f.getPriortyType());
						acc.setScriptType(f.getScriptType());
						acc.setPreviousRxNumber(f.getPreviousRxNumber());
						acc.setPrescriberOrderNumber(f.getPrescriberOrderNumber());
						acc.setComments(f.getComments());
						
						if (f.getWrittenDate() != null && !"".equalsIgnoreCase(f.getWrittenDate()) )
							acc.setWrittenDate( PharmacyUtil.getSqlDateFromString(f.getWrittenDate()) );

						prescriptionTranRepo.save(acc);
						
						if (isControlSubstance && "Y".equalsIgnoreCase(acc.getControlSubstance())) {
							prescriptionControlSubstanceItemsNotification(acc.getId(), loggedInUser, session);
						}
					}
				}
			}
			
			// Physicially deleted from deleted records.
			accList = prescriptionTranRepo.findByPrescriptionIdAndDelFlag(form.getPrescriptionId(), PharmacyUtil.deleteFlagYes);
			if (accList.size() > 0) {
				for (PrescriptionTransaction a: accList) {
					prescriptionTranRepo.delete(a);
				}
			}
			
		} catch(Exception e) {
			isSaved=false;
			e.printStackTrace();
		}
		return isSaved;
	}
	
	private boolean generatePrescriptionPdf(int id, String rootFilePath, HttpSession session, String signPath,boolean csFlag) {
		boolean isGenerated = false;
		
		try {
			int csRowCnt=0;
			int nRowCnt=0;
			if (id > 0) {
				PrescriptionForm form = getPrescriptionData(id,session);
				if (form != null) {
					List<PrescriptionForm> formData = new ArrayList<PrescriptionForm>();
					
					//if other shipping method, stamp in the shipping method
					
					if(form.getShippingMethod()!=null && !form.getShippingMethod().equalsIgnoreCase("0") && form.getShippingMethod().equalsIgnoreCase("Other"))
					{
						form.setShippingMethod(form.getOtherShippingMethod());
					}
					if(form.getShippingMethod()!=null && form.getShippingMethod().equalsIgnoreCase("0"))
						form.setShippingMethod("");
					
					List<PrescriptionTransactionForm> formMedList=new ArrayList<PrescriptionTransactionForm>();
					formMedList=form.getMedications();
					
					List<PrescriptionTransactionForm> nformMedList=new ArrayList<PrescriptionTransactionForm>();
					
					
					//System.out.println("1111111111111size ========================="+formMedList.size());
					
					for (int i = 0; i < formMedList.size(); i++) {
						//System.out.println(("0000000000000==="+formMedList.get(i).getControlSubstance()+"===="+formMedList.get(i).getCre8PrescriptionNo()));
						
						if(csFlag){
							if(!formMedList.get(i).getControlSubstance().equalsIgnoreCase("N"))
							{
								List<PrescriptionTransactionForm> csformMedList=new ArrayList<PrescriptionTransactionForm>();
								csRowCnt++;
								formMedList.get(i).setSlNo(csRowCnt);
								csformMedList.add(formMedList.get(i));
								
								form.setMedications(csformMedList);
							
								formData.add(form);
								
								//Individual Stamp of E-Sign in Controlled Substance
								generatePrintPDF( id,  rootFilePath,  session,  signPath, csFlag, formData, form,form.getMedications().get(0).getPrescriptionNo());
							
								
							}
						}
						
						if(!csFlag){
							if(formMedList.get(i).getControlSubstance().equalsIgnoreCase("N"))
							{
								nRowCnt++;
								formMedList.get(i).setSlNo(nRowCnt);
								nformMedList.add(formMedList.get(i));
							}
						}
					}
					
				
					if(!csFlag)
					{
						//Cummulative Stamp of E-Sign in Non-Controlled Substance
						form.setMedications(nformMedList);
						
					}
					
					
					//System.out.println("22222222222size ========================="+form.getMedications().size());
					
					if(form.getMedications()!=null && form.getMedications().size()>0){
						if(!csFlag)
						{
							
							formData.add(form);
							generatePrintPDF( id,  rootFilePath,  session,  signPath, csFlag, formData, form,"");
							
						}else
						{
							String targetFolder = rootFilePath + File.separator + id;
							String outputFile = targetFolder + File.separatorChar + "PrescriptionControlSubstance_"+form.getPrescriptionOrderNumber()+".pdf";
							 List<InputStream> list = new ArrayList<InputStream>();
						        try {
						        	
						        	String inputFile="";
						        	for (int i = 0; i < formMedList.size(); i++) {
										//System.out.println(("0000000000000==="+formMedList.get(i).getControlSubstance()+"===="+formMedList.get(i).getCre8PrescriptionNo()));
										
										if(!formMedList.get(i).getControlSubstance().equalsIgnoreCase("N"))
										{
												
											
												// Source pdfs
												inputFile = targetFolder + File.separatorChar + "PrescriptionControlSubstance_"+formMedList.get(i).getCre8PrescriptionNo()+".pdf";
												list.add(new FileInputStream(new File(inputFile)));
											
												
										}
									}
						        	if(list!=null && list.size()>0){
							        	// Resulting pdf
							            OutputStream out = new FileOutputStream(new File(outputFile));
	
							            //PharmacyUtil.doMerge(list, out);
							            PharmacyUtil.mergePdfFiles(list, out);
						        	}
						            
						            
						            
							      
						        } catch (Exception e) {
						            e.printStackTrace();
						        } 
						        
						}
					
						
					}
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return isGenerated;
	}
	public void generatePrintPDF(int id, String rootFilePath, HttpSession session, String signPath,boolean csFlag,List<PrescriptionForm> formData,PrescriptionForm form,String prescriptionNo)
	{

		try {
			
			
			if(csFlag)
			{
				//For Controlled Subtance, to stamp each substance in one pdf, the formdata should be limit to one
				for (int i = 0; i < formData.size(); i++) {
					if(i>0)
					{
						formData.remove(i);
					}
					
				}
				
			}
			
			JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(formData);
			
			String targetFolder = rootFilePath + File.separator + id;
			File f = new File(rootFilePath);
			if (!f.exists())
				f.mkdir();
			
			f = new File(targetFolder);
			if (!f.exists())
				f.mkdir();
			
			String outputFile = targetFolder + File.separatorChar + "Prescription_"+form.getPrescriptionOrderNumber()+".pdf";
			if(csFlag){	
				if (form.getMedications().size()> 0){
					for (int i = 0; i < form.getMedications().size(); i++) {
							//if(form.getMedications().get(i).getPrescriptionNo().equalsIgnoreCase(prescriptionNo))
							{
								if (!"N".equalsIgnoreCase(form.getMedications().get(i).getControlSubstance())) {
									outputFile = targetFolder + File.separatorChar + "PrescriptionControlSubstance_"+prescriptionNo+".pdf";
								}
							}
						}
				}
			}
			
			f = new File(outputFile);
			if (f.exists())
				f.delete();
			
			String realpath = session.getServletContext().getRealPath("/") + File.separator;
			
			String checkedImagePath = realpath + "\\resources\\images\\checkbox_checked.jpg";
			String unCheckedImagePath = realpath + "\\resources\\images\\checkbox_unchecked.jpg";
			String SUB_REPORT_PATH = realpath + "\\JASP-RPT\\";
			
			String companyLogo = realpath + "\\resources\\images\\CRE8-Pharma-logo.png";
			//String companyLogo = realpath + "\\resources\\images\\cenegenics.png";
			String logopath=realpath + "\\resources\\";
			String phyGroupLogoPath="";
			String useGroupLogo=form.getUseGroupLogo();
			//Stamp the Group Logo in Prescription Header, if the Use Group Logo is "Yes",else stamp the Physician Logo if exists else stamp the cre8 logo
			if(useGroupLogo!=null && useGroupLogo.equalsIgnoreCase("yes")){
				phyGroupLogoPath=groupfileUploadService.logoNameByGroupId(form.getGroupId(), env.getProperty("file.logo_path")) ;
				//System.out.println("group logopath ============"+phyGroupLogoPath);
			}else{
				phyGroupLogoPath=physicianfileUploadService.logoNameByPhysicianId(form.getPhysicianId(), env.getProperty("file.logo_path")) ;
				//System.out.println("physician logopath ============"+phyGroupLogoPath);
			}
			
			if(phyGroupLogoPath!=null && phyGroupLogoPath.length()>0){
				
				logopath=logopath+phyGroupLogoPath;
				
				File flNew=new File(logopath);
				if(flNew.exists())
				{
					companyLogo=logopath;
				}
			
			}
			
			
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("checkedImagePath", checkedImagePath);
			parameters.put("unCheckedImagePath", unCheckedImagePath);
			parameters.put("companyLogo", companyLogo);
			parameters.put("SUBREPORT_DIR",SUB_REPORT_PATH);
			parameters.put("signPath", signPath);

			JasperPrint jasperPrint = JasperFillManager.fillReport(realpath + "\\JASP-RPT\\Prescription.jasper", parameters, itemsJRBean);

			OutputStream outputStream = new FileOutputStream(new File(outputFile));
			JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);

			//System.out.println("File Generated");
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}
	public PrescriptionForm getPrescriptionData(int id, HttpSession session) {
		PrescriptionForm form = new PrescriptionForm();
		String defaultPrescriptionNoFormat=(env.getProperty("default.prescription_number_format"));
		String patientAddress="",physicianAddress="";
		if (id > 0) {
			PrescriptionMaster acc = prescriptionRepo.findOne(id);
			if (acc != null) {
				
				form.setPrescriptionId(acc.getId());
				
				form.setAllergies(acc.getAllergies());
				form.setBillingAddress(acc.getBillingAddress());
				form.setBillingAddressId(acc.getBillingAddressId());
				form.setBillingCity(acc.getBillingCity());
				form.setBillingCountry(acc.getBillingCountry());
				form.setBillingName(acc.getBillingName());
				form.setBillingState(acc.getBillingState());
				form.setBillingZipCode(acc.getBillingZipCode());
				form.setClinicId(acc.getClinicId());
				form.setClinicName(acc.getClinicName());
				form.setClinicPo(acc.getClinicPo());
				form.setCreditCardNumber(acc.getCreditCardNumber());
				form.setCreditCardType(acc.getCreditCardType());
				form.setCvcCode(acc.getCvcCode());
				form.setDiagnosis(acc.getDiagnosis());
				form.setPhysicianId(acc.getPhysicianId());
				form.setGroupId(acc.getGroupId());
				if (acc.getPhysicianId() > 0) {
					PhysicianAccount phyAcc = phyService.getPhysicianAccountDetails(acc.getPhysicianId());
					form.setPhysicianName(phyAcc.getPhysicianName());
					form.setPhyFirstName(phyAcc.getFirstName());
					form.setPhyMiddleName(phyAcc.getMiddleName());
					form.setPhyLastName(phyAcc.getLastName());
					form.setPhyName(phyAcc.getLastName()+", "+phyAcc.getFirstName());
					String type = "";
					if (phyAcc.getPrescriberType() > 0)
						type = prescriberTypeMasterService.getPrescriberTypeMasterDetails(phyAcc.getPrescriberType()).getPrescriberType();
					form.setPhysicianPrintname(phyAcc.getLastName()+", "+phyAcc.getFirstName()+", "+type);
				}
					
				form.setCreatedBy(acc.getCreatedBy());
				form.setCreatedUser(acc.getCreatedUser());
				form.setCreatedDate(acc.getCreatedDate());
				
				form.setLastUpdatedBy(acc.getLastUpdatedBy());
				form.setLastUpdatedUser(acc.getLastUpdatedUser());
				form.setLastUpdatedDate(acc.getLastUpdatedDate());
				
				String groupName = "";
				if (acc.getGroupId() > 0) {
					groupName = groupService.getGroupMasterDetails(acc.getGroupId()).getGroupName();
				}
				form.setGroupName(groupName);
				
				form.setNameOnCard(acc.getNameOnCard());
				form.setOtherShippingCompany(acc.getOtherShippingCompany());
				form.setOtherShippingMethod(acc.getOtherShippingMethod());
				form.setPatientAddress(acc.getPatientAddress());
				form.setPatientBillToId(acc.getPatientBillToId());
				form.setPatientCity(acc.getPatientCity());
				form.setPatientCountry(acc.getPatientCountry());
				
				if(acc.getPatientAddress()!=null && acc.getPatientAddress().length()>0)
					patientAddress+=acc.getPatientAddress()+", ";
				if(acc.getPatientCity()!=null && acc.getPatientCity().length()>0) 
					patientAddress+=acc.getPatientCity()+", ";
				if(acc.getPatientState()!=null && acc.getPatientState().length()>0) 
					patientAddress+=acc.getPatientState()+", ";
				if(acc.getPatientZipCode()!=null && acc.getPatientZipCode().length()>0) 
					patientAddress+=acc.getPatientZipCode()+", ";
	
				if(patientAddress.length()>2)
					form.setPatientAddressInfo(patientAddress.substring(0, patientAddress.length()-2));
				else
					form.setPatientAddressInfo("");
				
				if (acc.getPatientDateOfBirth() != null)
					form.setPatientDateOfBirth( PharmacyUtil.getStringDateFromSqlDate(acc.getPatientDateOfBirth()) );

				form.setPatientId(acc.getPatientId());
				
				PatientAccount patientObj = patientService.getPatientData(acc.getPatientId());
				if (patientObj != null) {
					form.setPatientFirstName(patientObj.getFirstName());
					form.setPatientMiddleName(patientObj.getMiddleName());
					form.setPatientLastName(patientObj.getLastName());
					form.setPatientPhone(patientObj.getPhone());
					form.setPatientPrintname(patientObj.getLastName()+", "+patientObj.getFirstName());
					
				}
				form.setPatientPhone(acc.getPatientPhone());
				form.setPatientMobile(acc.getPatientMobile());
				form.setPatientName(acc.getPatientName());
				form.setPatientShipToId(acc.getPatientShipToId());
				form.setPatientSsn(acc.getPatientSsn());
				form.setPatientState(acc.getPatientState());
				form.setPatientZipCode(acc.getPatientZipCode());
				form.setPaymentTerms(acc.getPaymentTerms());
				form.setPaymentType(acc.getPaymentType());
				form.setPhyAddress(acc.getPhyAddress());
				form.setPhyCity(acc.getPhyCity());
				form.setPhyCountry(acc.getPhyCountry());
				form.setPhyDea(acc.getPhyDea());
				form.setPhyMedicaid(acc.getPhyMedicaid());
				form.setPhyNpi(acc.getPhyNpi());
				form.setPhyPhone(acc.getPhyPhone());
				
				if(acc.getPhyAddress()!=null && acc.getPhyAddress().length()>0)
					physicianAddress+=acc.getPhyAddress()+", ";
				if(acc.getPhyCity()!=null && acc.getPhyCity().length()>0) 
					physicianAddress+=acc.getPhyCity()+", ";
				if(acc.getPhyState()!=null && acc.getPhyState().length()>0) 
					physicianAddress+=acc.getPhyState()+", ";
				if(acc.getPhyZipCode()!=null && acc.getPhyZipCode().length()>0) 
					physicianAddress+=acc.getPhyZipCode()+", ";
	
				if(physicianAddress.length()>2)
					form.setPhysicianAddressInfo(physicianAddress.substring(0, physicianAddress.length()-2));
				else
					form.setPhysicianAddressInfo("");
				
				form.setPhyState(acc.getPhyState());
				form.setPhyStateLicense(acc.getPhyStateLicense());
				form.setPhyUpin(acc.getPhyUpin());
				form.setPhyZipCode(acc.getPhyZipCode());
				form.setPrescriptionNumber(acc.getPrescriptionNumber());
				form.setCre8PrescriptionNo(acc.getCre8PrescriptionNo());
				form.setPrescriptionOrderNumber(defaultPrescriptionNoFormat+acc.getPrescriptionNumber());
				form.setPrescriptionDate(acc.getPrescriptionDate());
				form.setRemoveSignature(acc.getRemoveSignature());
				form.setShippingAccountNo(acc.getShippingAccountNo());
				form.setShippingAddress(acc.getShippingAddress());
				form.setShippingAddressId(acc.getShippingAddressId());
				form.setShippingCity(acc.getShippingCity());
				form.setShippingCompany(acc.getShippingCompany());
				form.setShippingCountry(acc.getShippingCountry());
				form.setShippingMethod(acc.getShippingMethod());
				form.setShippingName(acc.getShippingName());
				form.setShippingState(acc.getShippingState());
				form.setShippingZipCode(acc.getShippingZipCode());
				form.setWrittenBy(acc.getWrittenBy());
				if (acc.getWrittenBy() > 0) {
					PhysicianAccount phyAcc = phyService.getPhysicianAccountDetails(acc.getWrittenBy());
					form.setWrittenByName(phyAcc.getPhysicianName());
				}
				form.setUseGroupLogo(acc.getUseGroupLogo());
				
				if ("Y".equalsIgnoreCase(acc.getScan()))
					form.setScan("Y");
				else
					form.setScan("N");
				if ("Y".equalsIgnoreCase(acc.getFax()))
					form.setFax("Y");
				else
					form.setFax("N");
				form.setStatusId(acc.getStatusId());
				try {
					form.setMedications( getPrescriptionTransactionById(id,form.getPrescriptionNumber())  );
				} catch(Exception e) { 
					e.printStackTrace();
				}
				
				String rootFilePath= PharmacyUtil.getRootFolderForPrescriptionPDF(session, env);
				String targetFolder = rootFilePath + File.separator + acc.getId();
				String fileName = targetFolder + File.separatorChar + "Prescription_"+form.getPrescriptionOrderNumber()+"_esigned.pdf";
				
				
				File file = new File(fileName);
				if(file.exists()){
					form.setEsignedPDF(true);
				}else
					form.setEsignedPDF(false);
				
				
				if (form.getMedications() !=null && form.getMedications().size()> 0){
					for (int i = 0; i < form.getMedications().size(); i++) {
						if (!"N".equalsIgnoreCase(form.getMedications().get(i).getControlSubstance())) {
						fileName = targetFolder + File.separatorChar + "PrescriptionControlSubstance_"+form.getPrescriptionOrderNumber()+"_esigned.pdf";
						
						file = new File(fileName);
						if(file.exists()){
							form.setCsesignedPDF(true);
						}else
							form.setCsesignedPDF(false);
					}}
				}
			}
		}
		
		return form;
	}

	public List<PrescriptionTransactionForm> getPrescriptionTransactionById(int id,int prescriptionNumber) {
//		String defaultPrescriptionNoFormat=(env.getProperty("default.prescription_number_format"));
		List<PrescriptionTransactionForm> formList = new ArrayList<PrescriptionTransactionForm>();
		
		try {
			int slNo = 0;
			if (id > 0) {
				List<PrescriptionTransaction> accList = prescriptionTranService.getTransactionByPrescription(id);
				
				if (accList.size() > 0) {
					for (PrescriptionTransaction a : accList) {
						PrescriptionTransactionForm f = new PrescriptionTransactionForm();
						
						f.setSlNo(++slNo);
						f.setAuto(a.getAuto());
						f.setDwa(a.getDwa());
						if (a.getExpireDate() != null)
							f.setExpireDate( PharmacyUtil.getStringDateFromSqlDate(a.getExpireDate()));
						f.setIcd10(a.getIcd10());
						f.setId(a.getId());
						
						
						if(a.getType().equalsIgnoreCase("1")){
							f.setItemid(a.getItemid());
							f.setItemname(a.getItemname());
							
						}
						else if(a.getType().equalsIgnoreCase("2")){
							f.setItemid(String.valueOf(a.getMedicationid()));
							f.setItemname(a.getMedicationdescription());
						}
						
						/*f.setMedicationid(a.getMedicationid());
						f.setMedicationdescription(a.getMedicationdescription());
						
						f.setItemid(a.getItemid());
						f.setItemname(a.getItemname());*/
						
						
						if (a.getLastFilledDate() != null)
							f.setLastFilledDate( PharmacyUtil.getStringDateFromSqlDate(a.getLastFilledDate()) );
						f.setOrigin(a.getOrigin());
						f.setPrescriptionId(a.getPrescriptionId());
						f.setPrn(a.getPrn());
						f.setQuantity(a.getQuantity());
						f.setRefills(a.getRefills());
						f.setRefillsFilled(a.getRefillsFilled());
						f.setRefillsRemaining(a.getRefillsRemaining());
						f.setRxNumber(a.getRxNumber());
						f.setRxStatus(a.getRxStatus());
						f.setSigCodes(a.getSigCodes());
						f.setTrackingNumber(a.getTrackingNumber());
						f.setType(a.getType());
						f.setUnitName(a.getUnitName());
						if (a.getWrittenDate() != null)
							f.setWrittenDate( PharmacyUtil.getStringDateFromSqlDate(a.getWrittenDate()) );
						f.setDaysSupply(a.getDaysSupply());
						
						f.setFutureFill(a.getFutureFill());
						f.setPriortyType(a.getPriortyType());
						f.setScriptType(a.getScriptType());
						f.setPreviousRxNumber(a.getPreviousRxNumber());
						f.setPrescriberOrderNumber(a.getPrescriberOrderNumber());
						f.setControlSubstance(a.getControlSubstance());

						f.setRxItemType("");
						f.setIcd10Text("");
						f.setDirectionText("");
						f.setOriginText("");
						f.setDispensedItemName(a.getDispensedItemName());
						f.setDispensedItemId(a.getDispensedItemId());
						
						f.setPrescriptionNo(a.getCre8PrescriptionNo()+"");
						f.setCre8PrescriptionNo(a.getCre8PrescriptionNo()+"");
						
						f.setComments(a.getComments());
						
						if (f.getType() != null && !"".equalsIgnoreCase(f.getType().trim()) 
								&& !"0".equalsIgnoreCase(f.getType().trim())) {
							f.setRxItemType( itemTypeRepo.findOne(Integer.parseInt(f.getType())).getPrescribedItemTypeText());
						}
						
						
						if (f.getIcd10() != null && !"".equalsIgnoreCase(f.getIcd10().trim()) 
								&& !"0".equalsIgnoreCase(f.getIcd10().trim())) {
							f.setIcd10Text( icd10Repo.findOne(f.getIcd10()).getShortdescription());
						}
						if (f.getSigCodes() != null && !"".equalsIgnoreCase(f.getSigCodes().trim()) 
								&& !"0".equalsIgnoreCase(f.getSigCodes().trim())) {
							try {
								//f.setDirectionText( directionRepo.findBySigid(f.getSigCodes()).getEnglishtext());
								f.setDirectionText(f.getSigCodes() );
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
						/*if (f.getOrigin() != null && !"".equalsIgnoreCase(f.getOrigin().trim()) 
								&& !"0".equalsIgnoreCase(f.getOrigin().trim())) {
							try {
								f.setOriginText( originTypeRepo.findByOriginId( Integer.parseInt(f.getOrigin()) ).getOrigintypetext());
							} catch (Exception e) {
								e.printStackTrace();
							}
						}*/
						if (f.getUnitName() != null && !"".equalsIgnoreCase(f.getUnitName().trim()) 
								&& !"0".equalsIgnoreCase(f.getUnitName().trim())) {
							try {
								f.setUnitText( unitMasterRepo.findByDispensingunitid( Integer.parseInt(f.getUnitName()) ).getDispensingunittext());
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
						
						formList.add(f);
					}
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return formList;
	}
	
	
	

	public String getPrescriptionDataList(int draw, int start, int length, String searchTerm, int orderColumn, String orderDir, String physicianName, 
			String patientName, String presriptionDate, String presriptionToDate, String userId, String userType, int groupId, String prescriptionNo, 
			String prescriptionstatus, String rxNo,String rxPrescriptionNo, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		
//		String defaultPrescriptionNoFormat=(env.getProperty("default.prescription_number_format"));
		
		if (physicianName == null) physicianName = "";
		if (patientName == null) patientName = "";
		if (presriptionDate == null) presriptionDate = "";
		if (presriptionToDate == null) presriptionToDate = "";
		if (rxNo == null) rxNo = "";
		if (rxPrescriptionNo == null) rxPrescriptionNo="";
		
		int total = 0;
		int pageNumber = 0;
		try {
			Pageable page = null;
			Page<PrescriptionMaster> prescriptionList = null;
			
			Direction dir = Direction.ASC;
			if (orderDir.equalsIgnoreCase("desc"))
				dir = Direction.DESC;

			int physicianId = 0;
			// int groupId = 0;
			int patientId=0;
			LoginForm loggedInUser = (LoginForm) session.getAttribute("loginDetail");
			if (PharmacyUtil.userPhysicianAssistant.equalsIgnoreCase(userType))
			{
				//temp commented on jan 19,2018
				//physicianId = assistantRepo.findOne(Integer.parseInt(userId)).getPhysicianId();
				physicianId =loggedInUser.getPhysicianAssistantPhysicianId();
			}
			else if (PharmacyUtil.userPhysician.equalsIgnoreCase(userType))
				physicianId = Integer.parseInt(userId);			
			else if (PharmacyUtil.userGroupDirector.equalsIgnoreCase(userType))
				groupId = groupDirectorRepo.findOne(Integer.parseInt(userId)).getGroupId();
			else if (PharmacyUtil.userPatient.equalsIgnoreCase(userType))
				patientId = Integer.parseInt(userId);	
			
			try {
				if (PharmacyUtil.userAdmin.equalsIgnoreCase(userType) || PharmacyUtil.userSuperAdmin.equalsIgnoreCase(userType)) {
					if ("".equalsIgnoreCase(physicianName) && "".equalsIgnoreCase(patientName) && "".equalsIgnoreCase(presriptionDate) 
							&& "".equalsIgnoreCase(presriptionToDate) && "".equalsIgnoreCase(prescriptionNo) && "".equalsIgnoreCase(prescriptionstatus) 
							&& "".equalsIgnoreCase(rxNo) && "".equalsIgnoreCase(rxPrescriptionNo) && groupId == 0) {
						total = prescriptionRepo.findAll().size();	
					} else {
						if(groupId > 0) {
							if ("".equalsIgnoreCase(presriptionDate) && "".equalsIgnoreCase(presriptionToDate))
								total = prescriptionRepo.countByFilter(physicianName, patientName, prescriptionNo, prescriptionstatus, rxNo, rxPrescriptionNo, groupId);
							else if (!"".equalsIgnoreCase(presriptionDate) && "".equalsIgnoreCase(presriptionToDate))
								total = prescriptionRepo.countByFilterFromDate(physicianName, patientName, PharmacyUtil.getSqlDateFromString(presriptionDate),
										prescriptionNo, prescriptionstatus, rxNo, rxPrescriptionNo, groupId);
							else if ("".equalsIgnoreCase(presriptionDate) && !"".equalsIgnoreCase(presriptionToDate))
								total = prescriptionRepo.countByFilterToDate(physicianName, patientName, PharmacyUtil.getSqlDateFromString(presriptionToDate),
										prescriptionNo, prescriptionstatus, rxNo, rxPrescriptionNo, groupId);
							else if (!"".equalsIgnoreCase(presriptionDate) && !"".equalsIgnoreCase(presriptionToDate))
								total = prescriptionRepo.countByFilter(physicianName, patientName, PharmacyUtil.getSqlDateFromString(presriptionDate),
										PharmacyUtil.getSqlDateFromString(presriptionToDate), prescriptionNo, prescriptionstatus, rxNo, rxPrescriptionNo, groupId);
						} else {
							if ("".equalsIgnoreCase(presriptionDate) && "".equalsIgnoreCase(presriptionToDate))
								total = prescriptionRepo.countByFilter(physicianName, patientName, prescriptionNo, prescriptionstatus, rxNo, rxPrescriptionNo);
							else if (!"".equalsIgnoreCase(presriptionDate) && "".equalsIgnoreCase(presriptionToDate))
								total = prescriptionRepo.countByFilterFromDate(physicianName, patientName, PharmacyUtil.getSqlDateFromString(presriptionDate),
										prescriptionNo, prescriptionstatus, rxNo, rxPrescriptionNo);
							else if ("".equalsIgnoreCase(presriptionDate) && !"".equalsIgnoreCase(presriptionToDate))
								total = prescriptionRepo.countByFilterToDate(physicianName, patientName, PharmacyUtil.getSqlDateFromString(presriptionToDate),
										prescriptionNo, prescriptionstatus, rxNo, rxPrescriptionNo);
							else if (!"".equalsIgnoreCase(presriptionDate) && !"".equalsIgnoreCase(presriptionToDate))
								total = prescriptionRepo.countByFilter(physicianName, patientName, PharmacyUtil.getSqlDateFromString(presriptionDate),
										PharmacyUtil.getSqlDateFromString(presriptionToDate), prescriptionNo, prescriptionstatus, rxNo, rxPrescriptionNo);
						}
						
						
						
						if (groupId > 0) {
							if ("".equalsIgnoreCase(presriptionDate) && "".equalsIgnoreCase(presriptionToDate))
								total = prescriptionRepo.countByFilterGroup(physicianName, patientName, prescriptionNo, prescriptionstatus, rxNo, rxPrescriptionNo, groupId).size();
							else if (!"".equalsIgnoreCase(presriptionDate) && "".equalsIgnoreCase(presriptionToDate))
								total = prescriptionRepo.countByFilterFromDateGroup(physicianName, patientName, PharmacyUtil.getSqlDateFromString(presriptionDate),
										prescriptionNo, prescriptionstatus, rxNo, rxPrescriptionNo, groupId).size();
							else if ("".equalsIgnoreCase(presriptionDate) && !"".equalsIgnoreCase(presriptionToDate))
								total = prescriptionRepo.countByFilterToDateGroup(physicianName, patientName, PharmacyUtil.getSqlDateFromString(presriptionToDate),
										prescriptionNo, prescriptionstatus, rxNo, rxPrescriptionNo, groupId).size();
							else if (!"".equalsIgnoreCase(presriptionDate) && !"".equalsIgnoreCase(presriptionToDate))
								total = prescriptionRepo.countByFilterGroup(physicianName, patientName, PharmacyUtil.getSqlDateFromString(presriptionDate),
										PharmacyUtil.getSqlDateFromString(presriptionToDate), prescriptionNo, prescriptionstatus, rxNo, rxPrescriptionNo, groupId).size();
						} else {
							if (total > 0) {
								if ("".equalsIgnoreCase(presriptionDate) && "".equalsIgnoreCase(presriptionToDate))
									total = prescriptionRepo.countByFilterGroup(physicianName, patientName, prescriptionNo, prescriptionstatus, rxNo, rxPrescriptionNo).size();
								else if (!"".equalsIgnoreCase(presriptionDate) && "".equalsIgnoreCase(presriptionToDate))
									total = prescriptionRepo.countByFilterFromDateGroup(physicianName, patientName, PharmacyUtil.getSqlDateFromString(presriptionDate),
											prescriptionNo, prescriptionstatus, rxNo, rxPrescriptionNo).size();
								else if ("".equalsIgnoreCase(presriptionDate) && !"".equalsIgnoreCase(presriptionToDate))
									total = prescriptionRepo.countByFilterToDateGroup(physicianName, patientName, PharmacyUtil.getSqlDateFromString(presriptionToDate),
											prescriptionNo, prescriptionstatus, rxNo, rxPrescriptionNo).size();
								else if (!"".equalsIgnoreCase(presriptionDate) && !"".equalsIgnoreCase(presriptionToDate))
									total = prescriptionRepo.countByFilterGroup(physicianName, patientName, PharmacyUtil.getSqlDateFromString(presriptionDate),
											PharmacyUtil.getSqlDateFromString(presriptionToDate), prescriptionNo, prescriptionstatus, rxNo, rxPrescriptionNo).size();
							}
						}
					}
				} else if (PharmacyUtil.userPhysician.equalsIgnoreCase(userType) || PharmacyUtil.userPhysicianAssistant.equalsIgnoreCase(userType)) {
					if ("".equalsIgnoreCase(physicianName) && "".equalsIgnoreCase(patientName) && "".equalsIgnoreCase(presriptionDate) 
							&& "".equalsIgnoreCase(presriptionToDate)  && "".equalsIgnoreCase(prescriptionNo) && "".equalsIgnoreCase(prescriptionstatus)
							&& "".equalsIgnoreCase(rxNo) && "".equalsIgnoreCase(rxPrescriptionNo)) {
						total = prescriptionRepo.findByPhysicianId(physicianId);
					} else {
						if ("".equalsIgnoreCase(presriptionDate) && "".equalsIgnoreCase(presriptionToDate))
							total = prescriptionRepo.countByPhysicianFilter(physicianId, physicianName, patientName, prescriptionNo, prescriptionstatus, rxNo,rxPrescriptionNo);
						else if (!"".equalsIgnoreCase(presriptionDate) && "".equalsIgnoreCase(presriptionToDate))
							total = prescriptionRepo.countByPhysicianFilterFromDate(physicianId, physicianName, patientName, PharmacyUtil.getSqlDateFromString(presriptionDate),prescriptionNo,prescriptionstatus, rxNo,rxPrescriptionNo);
						else if ("".equalsIgnoreCase(presriptionDate) && !"".equalsIgnoreCase(presriptionToDate))
							total = prescriptionRepo.countByPhysicianFilterToDate(physicianId, physicianName, patientName, PharmacyUtil.getSqlDateFromString(presriptionToDate),prescriptionNo,prescriptionstatus, rxNo,rxPrescriptionNo);
						else if (!"".equalsIgnoreCase(presriptionDate) && !"".equalsIgnoreCase(presriptionToDate))
							total = prescriptionRepo.countByPhysicianFilter(physicianId, physicianName, patientName, PharmacyUtil.getSqlDateFromString(presriptionDate), 
									PharmacyUtil.getSqlDateFromString(presriptionToDate),prescriptionNo,prescriptionstatus, rxNo,rxPrescriptionNo);

						if (total > 0) {
							if ("".equalsIgnoreCase(presriptionDate) && "".equalsIgnoreCase(presriptionToDate))
								total = prescriptionRepo.countByPhysicianFilterGroup(physicianId, physicianName, patientName, prescriptionNo, prescriptionstatus, rxNo,rxPrescriptionNo).size();
							else if (!"".equalsIgnoreCase(presriptionDate) && "".equalsIgnoreCase(presriptionToDate))
								total = prescriptionRepo.countByPhysicianFilterFromDateGroup(physicianId, physicianName, patientName, PharmacyUtil.getSqlDateFromString(presriptionDate),prescriptionNo,prescriptionstatus, rxNo,rxPrescriptionNo).size();
							else if ("".equalsIgnoreCase(presriptionDate) && !"".equalsIgnoreCase(presriptionToDate))
								total = prescriptionRepo.countByPhysicianFilterToDateGroup(physicianId, physicianName, patientName, PharmacyUtil.getSqlDateFromString(presriptionToDate),prescriptionNo,prescriptionstatus, rxNo,rxPrescriptionNo).size();
							else if (!"".equalsIgnoreCase(presriptionDate) && !"".equalsIgnoreCase(presriptionToDate))
								total = prescriptionRepo.countByPhysicianFilterGroup(physicianId, physicianName, patientName, PharmacyUtil.getSqlDateFromString(presriptionDate), 
										PharmacyUtil.getSqlDateFromString(presriptionToDate),prescriptionNo,prescriptionstatus, rxNo,rxPrescriptionNo).size();
						}
					}
				} else if (PharmacyUtil.userGroupDirector.equalsIgnoreCase(userType)) {
					if ("".equalsIgnoreCase(physicianName) && "".equalsIgnoreCase(patientName) && "".equalsIgnoreCase(presriptionDate) 
							&& "".equalsIgnoreCase(presriptionToDate)  && "".equalsIgnoreCase(prescriptionNo) && "".equalsIgnoreCase(prescriptionstatus) && "".equalsIgnoreCase(rxNo)  && "".equalsIgnoreCase(rxPrescriptionNo)) {
						total = prescriptionRepo.findByGroupId(groupId);
					} else {
						if ("".equalsIgnoreCase(presriptionDate) && "".equalsIgnoreCase(presriptionToDate))
							total = prescriptionRepo.countByGroupFilter(groupId, physicianName, patientName, prescriptionNo, prescriptionstatus, rxNo, rxPrescriptionNo);
						else if (!"".equalsIgnoreCase(presriptionDate) && "".equalsIgnoreCase(presriptionToDate))
							total = prescriptionRepo.countByGroupFilterFromDate(groupId, physicianName, patientName, PharmacyUtil.getSqlDateFromString(presriptionDate),prescriptionNo,prescriptionstatus, rxNo, rxPrescriptionNo);
						else if ("".equalsIgnoreCase(presriptionDate) && !"".equalsIgnoreCase(presriptionToDate))
							total = prescriptionRepo.countByGroupFilterToDate(groupId, physicianName, patientName, PharmacyUtil.getSqlDateFromString(presriptionToDate),prescriptionNo,prescriptionstatus, rxNo, rxPrescriptionNo);
						else if (!"".equalsIgnoreCase(presriptionDate) && !"".equalsIgnoreCase(presriptionToDate))
							total = prescriptionRepo.countByGroupFilter(groupId, physicianName, patientName, PharmacyUtil.getSqlDateFromString(presriptionDate), 
									PharmacyUtil.getSqlDateFromString(presriptionToDate),prescriptionNo,prescriptionstatus, rxNo, rxPrescriptionNo);

						if (total > 0) {
							if ("".equalsIgnoreCase(presriptionDate) && "".equalsIgnoreCase(presriptionToDate))
								total = prescriptionRepo.countByGroupFilterGroup(groupId, physicianName, patientName, prescriptionNo, prescriptionstatus, rxNo, rxPrescriptionNo).size();
							else if (!"".equalsIgnoreCase(presriptionDate) && "".equalsIgnoreCase(presriptionToDate))
								total = prescriptionRepo.countByGroupFilterFromDateGroup(groupId, physicianName, patientName, PharmacyUtil.getSqlDateFromString(presriptionDate),prescriptionNo,prescriptionstatus, rxNo, rxPrescriptionNo).size();
							else if ("".equalsIgnoreCase(presriptionDate) && !"".equalsIgnoreCase(presriptionToDate))
								total = prescriptionRepo.countByGroupFilterToDateGroup(groupId, physicianName, patientName, PharmacyUtil.getSqlDateFromString(presriptionToDate),prescriptionNo,prescriptionstatus, rxNo, rxPrescriptionNo).size();
							else if (!"".equalsIgnoreCase(presriptionDate) && !"".equalsIgnoreCase(presriptionToDate))
								total = prescriptionRepo.countByGroupFilterGroup(groupId, physicianName, patientName, PharmacyUtil.getSqlDateFromString(presriptionDate), 
										PharmacyUtil.getSqlDateFromString(presriptionToDate),prescriptionNo,prescriptionstatus, rxNo, rxPrescriptionNo).size();
						}
					}
				} else if (PharmacyUtil.userPatient.equalsIgnoreCase(userType)) {
					if ("".equalsIgnoreCase(presriptionDate) && "".equalsIgnoreCase(presriptionToDate)  && "".equalsIgnoreCase(prescriptionNo)
							&& "".equalsIgnoreCase(prescriptionstatus) && "".equalsIgnoreCase(rxNo)  && "".equalsIgnoreCase(rxPrescriptionNo)) {
						total = prescriptionRepo.findByPatientId(patientId);
					} else {
						if ("".equalsIgnoreCase(presriptionDate) && "".equalsIgnoreCase(presriptionToDate))
							total = prescriptionRepo.countByPatientFilterWithoutDate(patientId, prescriptionNo, prescriptionstatus, rxNo, rxPrescriptionNo);
						else if (!"".equalsIgnoreCase(presriptionDate) && "".equalsIgnoreCase(presriptionToDate))
							total = prescriptionRepo.countByPatientFilterFromDate(patientId, PharmacyUtil.getSqlDateFromString(presriptionDate),
									prescriptionNo,prescriptionstatus, rxNo, rxPrescriptionNo);
						else if ("".equalsIgnoreCase(presriptionDate) && !"".equalsIgnoreCase(presriptionToDate))
							total = prescriptionRepo.countByPatientFilterToDate(patientId, PharmacyUtil.getSqlDateFromString(presriptionToDate),
									prescriptionNo,prescriptionstatus, rxNo, rxPrescriptionNo);
						else if (!"".equalsIgnoreCase(presriptionDate) && !"".equalsIgnoreCase(presriptionToDate))
							total = prescriptionRepo.countByPatientFilter(patientId, PharmacyUtil.getSqlDateFromString(presriptionDate), 
									PharmacyUtil.getSqlDateFromString(presriptionToDate),prescriptionNo,prescriptionstatus, rxNo, rxPrescriptionNo);
						
						if (total > 0) {
							if ("".equalsIgnoreCase(presriptionDate) && "".equalsIgnoreCase(presriptionToDate))
								total = prescriptionRepo.countByPatientFilterWithoutDateGroup(patientId, prescriptionNo, prescriptionstatus, rxNo, rxPrescriptionNo).size();
							else if (!"".equalsIgnoreCase(presriptionDate) && "".equalsIgnoreCase(presriptionToDate))
								total = prescriptionRepo.countByPatientFilterFromDateGroup(patientId, PharmacyUtil.getSqlDateFromString(presriptionDate),
										prescriptionNo,prescriptionstatus, rxNo, rxPrescriptionNo).size();
							else if ("".equalsIgnoreCase(presriptionDate) && !"".equalsIgnoreCase(presriptionToDate))
								total = prescriptionRepo.countByPatientFilterToDateGroup(patientId, PharmacyUtil.getSqlDateFromString(presriptionToDate),
										prescriptionNo,prescriptionstatus, rxNo, rxPrescriptionNo).size();
							else if (!"".equalsIgnoreCase(presriptionDate) && !"".equalsIgnoreCase(presriptionToDate))
								total = prescriptionRepo.countByPatientFilterGroup(patientId, PharmacyUtil.getSqlDateFromString(presriptionDate), 
										PharmacyUtil.getSqlDateFromString(presriptionToDate),prescriptionNo,prescriptionstatus, rxNo, rxPrescriptionNo).size();
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
				if (PharmacyUtil.userAdmin.equalsIgnoreCase(userType) || PharmacyUtil.userSuperAdmin.equalsIgnoreCase(userType)) {
					if ("".equalsIgnoreCase(physicianName) && "".equalsIgnoreCase(patientName) && "".equalsIgnoreCase(presriptionDate) 
							&& "".equalsIgnoreCase(presriptionToDate)  && "".equalsIgnoreCase(prescriptionNo) && "".equalsIgnoreCase(prescriptionstatus)
							&& "".equalsIgnoreCase(rxNo) && "".equalsIgnoreCase(rxPrescriptionNo) && groupId == 0) {
						prescriptionList = prescriptionRepo.getAllRecords(page);
					} else {
						if(groupId > 0) {
							if ("".equalsIgnoreCase(presriptionDate) && "".equalsIgnoreCase(presriptionToDate))
								prescriptionList = prescriptionRepo.getByFilter(physicianName, patientName, prescriptionNo, prescriptionstatus, 
										rxNo, rxPrescriptionNo, groupId, page);
							else if (!"".equalsIgnoreCase(presriptionDate) && "".equalsIgnoreCase(presriptionToDate))
								prescriptionList = prescriptionRepo.getByFilterFromDate(physicianName, patientName, 
										PharmacyUtil.getSqlDateFromString(presriptionDate), prescriptionNo, prescriptionstatus, rxNo, rxPrescriptionNo, groupId, page);
							else if ("".equalsIgnoreCase(presriptionDate) && !"".equalsIgnoreCase(presriptionToDate))
								prescriptionList = prescriptionRepo.getByFilterToDate(physicianName, patientName, 
										PharmacyUtil.getSqlDateFromString(presriptionToDate),prescriptionNo,prescriptionstatus, rxNo, rxPrescriptionNo, groupId, page);
							else if (!"".equalsIgnoreCase(presriptionDate) && !"".equalsIgnoreCase(presriptionToDate))
								prescriptionList = prescriptionRepo.getByFilter(physicianName, patientName, PharmacyUtil.getSqlDateFromString(presriptionDate), 
									PharmacyUtil.getSqlDateFromString(presriptionToDate), prescriptionNo,prescriptionstatus, rxNo, rxPrescriptionNo, groupId, page);
						} else {
							if ("".equalsIgnoreCase(presriptionDate) && "".equalsIgnoreCase(presriptionToDate))
								prescriptionList = prescriptionRepo.getByFilter(physicianName, patientName, prescriptionNo, prescriptionstatus, rxNo, rxPrescriptionNo, page);
							else if (!"".equalsIgnoreCase(presriptionDate) && "".equalsIgnoreCase(presriptionToDate))
								prescriptionList = prescriptionRepo.getByFilterFromDate(physicianName, patientName, 
										PharmacyUtil.getSqlDateFromString(presriptionDate), prescriptionNo,prescriptionstatus, rxNo, rxPrescriptionNo, page);
							else if ("".equalsIgnoreCase(presriptionDate) && !"".equalsIgnoreCase(presriptionToDate))
								prescriptionList = prescriptionRepo.getByFilterToDate(physicianName, patientName, 
										PharmacyUtil.getSqlDateFromString(presriptionToDate),prescriptionNo,prescriptionstatus, rxNo, rxPrescriptionNo, page);
							else if (!"".equalsIgnoreCase(presriptionDate) && !"".equalsIgnoreCase(presriptionToDate))
								prescriptionList = prescriptionRepo.getByFilter(physicianName, patientName, PharmacyUtil.getSqlDateFromString(presriptionDate), 
									PharmacyUtil.getSqlDateFromString(presriptionToDate), prescriptionNo,prescriptionstatus, rxNo, rxPrescriptionNo, page);
						}
					}
				} else if (PharmacyUtil.userPhysician.equalsIgnoreCase(userType) || PharmacyUtil.userPhysicianAssistant.equalsIgnoreCase(userType)) {
					if ("".equalsIgnoreCase(physicianName) && "".equalsIgnoreCase(patientName) && "".equalsIgnoreCase(presriptionDate) 
							&& "".equalsIgnoreCase(presriptionToDate)  && "".equalsIgnoreCase(prescriptionNo) && "".equalsIgnoreCase(prescriptionstatus)
							&& "".equalsIgnoreCase(rxNo) && "".equalsIgnoreCase(rxPrescriptionNo)) {
						prescriptionList = prescriptionRepo.findByPhysicianId(physicianId, page);
					} else {
						if ("".equalsIgnoreCase(presriptionDate) && "".equalsIgnoreCase(presriptionToDate))
							prescriptionList = prescriptionRepo.findByPhysicianFilter(physicianId, physicianName, patientName,prescriptionNo,prescriptionstatus, rxNo, rxPrescriptionNo, page);
						else if (!"".equalsIgnoreCase(presriptionDate) && "".equalsIgnoreCase(presriptionToDate))
							prescriptionList = prescriptionRepo.findByPhysicianFilterFromDate(physicianId, physicianName, patientName, PharmacyUtil.getSqlDateFromString(presriptionDate),prescriptionNo,prescriptionstatus, rxNo, rxPrescriptionNo, page);
						else if ("".equalsIgnoreCase(presriptionDate) && !"".equalsIgnoreCase(presriptionToDate))
							prescriptionList = prescriptionRepo.findByPhysicianFilterToDate(physicianId, physicianName, patientName, PharmacyUtil.getSqlDateFromString(presriptionToDate),prescriptionNo,prescriptionstatus, rxNo, rxPrescriptionNo, page);
						else if (!"".equalsIgnoreCase(presriptionDate) && !"".equalsIgnoreCase(presriptionToDate))
							prescriptionList = prescriptionRepo.findByPhysicianFilter(physicianId, physicianName, patientName, PharmacyUtil.getSqlDateFromString(presriptionDate),  
									PharmacyUtil.getSqlDateFromString(presriptionToDate), prescriptionNo,prescriptionstatus, rxNo, rxPrescriptionNo,page);
					}
				} else if (PharmacyUtil.userGroupDirector.equalsIgnoreCase(userType)) {
					if ("".equalsIgnoreCase(physicianName) && "".equalsIgnoreCase(patientName) && "".equalsIgnoreCase(presriptionDate) 
							&& "".equalsIgnoreCase(presriptionToDate)  && "".equalsIgnoreCase(prescriptionNo) && "".equalsIgnoreCase(prescriptionstatus) && "".equalsIgnoreCase(rxNo)  && "".equalsIgnoreCase(rxPrescriptionNo)) {
						prescriptionList = prescriptionRepo.findByGroupId(groupId, page);
					} else {
						if ("".equalsIgnoreCase(presriptionDate) && "".equalsIgnoreCase(presriptionToDate))
							prescriptionList = prescriptionRepo.findByGroupFilter(groupId, physicianName, patientName,prescriptionNo,prescriptionstatus, rxNo, rxPrescriptionNo, page);
						else if (!"".equalsIgnoreCase(presriptionDate) && "".equalsIgnoreCase(presriptionToDate))
							prescriptionList = prescriptionRepo.findByGroupFilterFromDate(groupId, physicianName, patientName, PharmacyUtil.getSqlDateFromString(presriptionDate),prescriptionNo,prescriptionstatus, rxNo, rxPrescriptionNo, page);
						else if ("".equalsIgnoreCase(presriptionDate) && !"".equalsIgnoreCase(presriptionToDate))
							prescriptionList = prescriptionRepo.findByGroupFilterToDate(groupId, physicianName, patientName, PharmacyUtil.getSqlDateFromString(presriptionDate), prescriptionNo,prescriptionstatus, rxNo, rxPrescriptionNo,page);
						else if (!"".equalsIgnoreCase(presriptionDate) && !"".equalsIgnoreCase(presriptionToDate))
							prescriptionList = prescriptionRepo.findByGroupFilter(groupId, physicianName, patientName, PharmacyUtil.getSqlDateFromString(presriptionDate),
									PharmacyUtil.getSqlDateFromString(presriptionToDate),prescriptionNo,prescriptionstatus, rxNo, rxPrescriptionNo, page);
					}
				} else if (PharmacyUtil.userPatient.equalsIgnoreCase(userType)) {
					if ("".equalsIgnoreCase(presriptionDate) && "".equalsIgnoreCase(presriptionToDate)  && "".equalsIgnoreCase(prescriptionNo) 
							&& "".equalsIgnoreCase(prescriptionstatus) && "".equalsIgnoreCase(rxNo)  && "".equalsIgnoreCase(rxPrescriptionNo)) {
						prescriptionList = prescriptionRepo.findByPatientId(patientId, page);
					} else {
						if ("".equalsIgnoreCase(presriptionDate) && "".equalsIgnoreCase(presriptionToDate))
							prescriptionList = prescriptionRepo.findByPatientIdWithoutDate(patientId, prescriptionNo, prescriptionstatus, rxNo, rxPrescriptionNo,page);								
						else if (!"".equalsIgnoreCase(presriptionDate) && "".equalsIgnoreCase(presriptionToDate))
							prescriptionList = prescriptionRepo.findByPatientIdFromDate(patientId, PharmacyUtil.getSqlDateFromString(presriptionDate), prescriptionNo,prescriptionstatus, rxNo, rxPrescriptionNo,page);
						else if ("".equalsIgnoreCase(presriptionDate) && !"".equalsIgnoreCase(presriptionToDate))
							prescriptionList = prescriptionRepo.findByPatientIdToDate(patientId, PharmacyUtil.getSqlDateFromString(presriptionToDate),prescriptionNo,prescriptionstatus, rxNo, rxPrescriptionNo, page);
						else if (!"".equalsIgnoreCase(presriptionDate) && !"".equalsIgnoreCase(presriptionToDate))
							prescriptionList = prescriptionRepo.findByPatientId(patientId, PharmacyUtil.getSqlDateFromString(presriptionDate), PharmacyUtil.getSqlDateFromString(presriptionToDate), prescriptionNo,prescriptionstatus, rxNo, rxPrescriptionNo,page);
					}
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
			
			List<PrescriptionForm> prescriptionObjList = new ArrayList<PrescriptionForm>();
			List<PrescriptionTransaction> accList =null;
			SimpleDateFormat sm = new SimpleDateFormat("MM/dd/yyyy");
			
			StringBuilder tempPrescriptionStr = new StringBuilder();
			StringBuilder tempRxStr = new StringBuilder();
			StringBuilder tempRxStatus = new StringBuilder();
			StringBuilder tempItems = new StringBuilder();
			
			StringBuilder tempQty = new StringBuilder();
			StringBuilder tempTotRefills = new StringBuilder();
			StringBuilder tempRefillsFilled = new StringBuilder();
			StringBuilder tempDaysSupply = new StringBuilder();
			String rootFilePath= PharmacyUtil.getRootFolderForPrescriptionPDF(session, env);
			String targetFolder ="", fileName ="";
			
			if (prescriptionList != null && prescriptionList.getSize() > 0 ) {
				for(PrescriptionMaster a: prescriptionList) {
					PrescriptionForm obj = new PrescriptionForm();
					obj.setPrescriptionId(a.getId());
					obj.setPatientName(a.getPatientName());
					obj.setPhysicianName( phyService.getPhysicianAccountDetails(a.getPhysicianId()).getPhysicianName() +"");
					obj.setPrescriptionDate(a.getPrescriptionDate());
					obj.setPrescriptionDateStr( sm.format(a.getPrescriptionDate()) );
					//obj.setPrescriptionOrderNumber(defaultPrescriptionNoFormat+a.getPrescriptionNumber());
					obj.setPrescriptionOrderNumber(a.getCre8PrescriptionNo());
					String groupName = "";
					if (a.getGroupId() > 0) {
						groupName = groupService.getGroupMasterDetails(a.getGroupId()).getGroupName();
					}
					obj.setGroupId( a.getGroupId() );
					obj.setGroupName(groupName);					
					
					obj.setDT_RowId("row_" + a.getId());
					
					accList = prescriptionTranService.getTransactionByPrescription(a.getId());
					
					tempPrescriptionStr=new StringBuilder();
					tempRxStr = new StringBuilder();
					tempRxStatus = new StringBuilder();
					tempItems = new StringBuilder();
					tempQty = new StringBuilder();
					tempTotRefills = new StringBuilder();
					tempRefillsFilled = new StringBuilder();
					tempDaysSupply = new StringBuilder();
					
					obj.setTranPrescriptionNumber("");
					obj.setTranRXNumber("");
					obj.setTranRXStatus("");
					obj.setTranItemName("");
					obj.setTranQtyStr("");
					obj.setTranTotalRefillsStr("");
					obj.setTranRefillsFilledStr("");
					obj.setTranDaySupply("");
					
					if (accList.size() > 0) {
						for (PrescriptionTransaction b : accList) {
							/*obj.setTranItemName(b.getItemName());
							obj.setTranQty(b.getQuantity());
							obj.setTranTotalRefills(b.getRefills());
							obj.setTranRefillsFilled(b.getRefillsFilled());
							obj.setTranRXNumber();
							obj.setTranRXStatus();*/
							tempPrescriptionStr.append(b.getCre8PrescriptionNo()).append(", ");
							
							if(b.getItemname()!=null && b.getItemname().length()>0)
								tempItems.append(b.getItemname()).append(",");
							else if(b.getMedicationdescription()!=null && b.getMedicationdescription().length()>0)
								tempItems.append(b.getMedicationdescription()).append(",");
							
							if(!"".equalsIgnoreCase(b.getRxNumber())) {
								tempRxStr.append(b.getRxNumber()).append(", ");
								tempRxStatus.append(b.getRxStatus()).append(", ");
								tempTotRefills.append(b.getRefills()).append(", ");
								tempRefillsFilled.append(b.getRefillsFilled()).append(", ");
								tempDaysSupply.append(b.getDaysSupply()).append(", ");
							}
							tempQty.append(b.getQuantity()).append(", ");
						}
						if (tempItems.toString().length() > 0)
							obj.setTranItemName(tempItems.toString().substring(0, tempItems.toString().length()-2));
						if (tempRxStr.toString().length() > 0)
							obj.setTranRXNumber(tempRxStr.toString().substring(0, tempRxStr.toString().length()-2));
						if (tempRxStatus.toString().length() > 0)
							obj.setTranRXStatus(tempRxStatus.toString().substring(0, tempRxStatus.toString().length()-2));
						if (tempQty.toString().length() > 0)
							obj.setTranQtyStr(tempQty.toString().substring(0, tempQty.toString().length()-2));
						if (tempTotRefills.toString().length() > 0)
							obj.setTranTotalRefillsStr(tempTotRefills.toString().substring(0, tempTotRefills.toString().length()-2));
						if (tempRefillsFilled.toString().length() > 0)
							obj.setTranRefillsFilledStr(tempRefillsFilled.toString().substring(0, tempRefillsFilled.toString().length()-2));
						if (tempDaysSupply.toString().length() > 0)
							obj.setTranDaySupply(tempDaysSupply.toString().substring(0, tempDaysSupply.toString().length()-2));
						if (tempPrescriptionStr.toString().length() > 0)
							obj.setTranPrescriptionNumber(tempPrescriptionStr.toString().substring(0, tempPrescriptionStr.toString().length()-2));
					}
					
					
					/*targetFolder = rootFilePath + File.separator + obj.getPrescriptionId();
					fileName = targetFolder + File.separatorChar + "Prescription_"+obj.getPrescriptionOrderNumber()+"_esigned.pdf";
					
					try {
						obj.setMedications( getPrescriptionTransactionById(obj.getPrescriptionId(),a.getPrescriptionNumber() ) );
					} catch(Exception e) { 
						e.printStackTrace();
					}
					if (obj.getMedications() !=null && obj.getMedications().size()> 0){
						if ("Y".equalsIgnoreCase(obj.getMedications().get(0).getControlSubstance())) {
							fileName = targetFolder + File.separatorChar + "PrescriptionControlSubstance_"+obj.getPrescriptionOrderNumber()+"_esigned.pdf";
						}
					}
					File file = new File(fileName);
					if(file.exists()){
						obj.setEsignedPDF(true);
					}else
						obj.setEsignedPDF(false);*/
					
					
					targetFolder = rootFilePath + File.separator + obj.getPrescriptionId();
					fileName = targetFolder + File.separatorChar + "Prescription_"+obj.getPrescriptionOrderNumber()+"_esigned.pdf";
					
					
					File file = new File(fileName);
					if(file.exists()){
						obj.setEsignedPDF(true);
					}else
						obj.setEsignedPDF(false);
					
					try {
						obj.setMedications( getPrescriptionTransactionById(obj.getPrescriptionId(),a.getPrescriptionNumber() ) );
					} catch(Exception e) { 
						e.printStackTrace();
					}
					if (obj.getMedications() !=null && obj.getMedications().size()> 0){
						for (int i = 0; i < obj.getMedications().size(); i++) {
							if (!"N".equalsIgnoreCase(obj.getMedications().get(i).getControlSubstance())) {
							fileName = targetFolder + File.separatorChar + "PrescriptionControlSubstance_"+obj.getPrescriptionOrderNumber()+"_esigned.pdf";
							
							File file2 = new File(fileName);
							if(file2.exists()){
								obj.setCsesignedPDF(true);
							}else
								obj.setCsesignedPDF(false);
						}}
					}
					
					
					/*SubtanceItemDocuments file = null;
					List<SubtanceItemDocuments> docList = substanceDocRepo.findAllByPrescriptionId(obj.getPrescriptionId());
					
					if (docList.size() > 0) {
						for (SubtanceItemDocuments doc: docList) {
							if ( ("Y".equalsIgnoreCase(doc.getScan()) ||  "Y".equalsIgnoreCase(doc.getFax())) && "Y".equalsIgnoreCase(doc.getIsHardCopy()) ){
								file = subtanceDocService.getSubtanceFile(doc.getId());
								
								if(file!=null)
								{
									
									if (file != null) {
										fileName = file.getStoredFielName();
									}
								
									File file1 = new File(fileName);
									if(file1.exists()){
										obj.setEsignedPDF(true);
									}else
										obj.setEsignedPDF(false);
								
									
								}
							}
						}
					}else
						obj.setEsignedPDF(false);*/
					
					prescriptionObjList.add(obj);
				}
			}
			
			PrescriptionJSonObj data = new PrescriptionJSonObj();
			data.setData(prescriptionObjList);
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
	private void showServerReply(FTPClient ftpClient) {
        String[] replies = ftpClient.getReplyStrings();
        if (replies != null && replies.length > 0) {
            for (String aReply : replies) {
                //System.out.println("SERVER: " + aReply);
            }
        }
    }
	public boolean pdfFtpTransfer(String serverName, String userName, String password, String targetFolder, String sourceFile, String targetFileName,int prescriptionId,int serverPort) {
		boolean store = false;
		try {
			
			File sourceLocalFile = new File(sourceFile);
			if (!sourceLocalFile.exists())
				return store;
			else {
				
				//System.out.println("serverName :: "+serverName+" :: serverPort :: "+serverPort+ " :: userName :: "+userName+" :: password :: "+password+" :: targetFolder :: "+targetFolder);
				FTPClient ftp = new FTPClient();
				ftp.connect(serverName,serverPort);
				showServerReply(ftp);
				if (!ftp.login(userName, password)) {
					//System.out.println("username / password is not correct");
					ftp.logout();
					return false;
				}
				ftp.enterLocalPassiveMode();
				 
				ftp.setFileType(FTP.BINARY_FILE_TYPE);
				showServerReply(ftp);
				int reply = ftp.getReplyCode();
				if (!FTPReply.isPositiveCompletion(reply)) {
					ftp.disconnect();
					return false;
				}
				ftp.changeWorkingDirectory(targetFolder);
				showServerReply(ftp);
				
				//Commented on Nov 18,2019-to set the prescription upload directly into /incoming folder in pioneer rx
				// Creates a directory
	            /*String dirToCreate = PharmacyUtil.getCurrentDate();
	            ftp.makeDirectory(dirToCreate);
	            showServerReply(ftp);
	            
	            ftp.changeWorkingDirectory(dirToCreate);
				showServerReply(ftp);*/
	            
				String remoteFile = targetFileName;
				
				// APPROACH #1: uploads file using an InputStream
				/*InputStream in = new FileInputStream(sourceLocalFile);
				
				//System.out.println("Start uploading file");
	            boolean done = ftp.storeFile(remoteFile, in);
	            in.close();
	            if (done) {
	            	store=true;
	                //System.out.println("The file is uploaded successfully.");
	            }*/
	            
					
				// APPROACH #2: uploads file using an OutputStream
	            File localFile = new File(sourceFile);
	            
	            InputStream inputStream = new FileInputStream(localFile);
	 
	            //System.out.println("Start uploading file");
	            OutputStream outputStream = ftp.storeFileStream(remoteFile);
	            byte[] bytesIn = new byte[4096];
	            int read = 0;
	 
	            while ((read = inputStream.read(bytesIn)) != -1) {
	                outputStream.write(bytesIn, 0, read);
	            }
	            inputStream.close();
	            outputStream.close();
	 
	            boolean completed = ftp.completePendingCommand();
	            if (completed) {
	            	store=true;
	                //System.out.println("The  file is uploaded successfully.");
	            }
				
	            showServerReply(ftp);
				
				
				
				ftp.logout();
				ftp.disconnect();
				
				try {
					
					PrescriptionMaster acc = null;

					if (prescriptionId > 0) {
						acc = prescriptionRepo.findOne(prescriptionId);
						if (acc != null) {
							acc.setStatusId(5);//5==>Remote File Uploaded
						}
						prescriptionRepo.save(acc);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return store;
	}


	public String getPatientAddress(int patientId, int addressOpt, boolean isBilling) {
		PrescriptionForm form = new PrescriptionForm();
		try {
			PatientAccount acc = patientService.getPatientData(patientId);
			if (addressOpt == 3) {
				if (isBilling) {
					form.setBillingName(acc.getPatientName()+"");
					if (acc.getAddress() != null)
						form.setBillingAddress(acc.getAddress()+"");
					else
						form.setBillingAddress("");
					if (acc.getCity() != null)
						form.setBillingCity(acc.getCity()+"");
					else
						form.setBillingCity("");
					if (acc.getState() != null || !"".equalsIgnoreCase(acc.getState()))
						form.setBillingState(acc.getState()+"");
					else
						form.setBillingState("");
					if (acc.getCountry() != null)
						form.setBillingCountry(acc.getCountry());
					else
						form.setBillingCountry("");
					if (acc.getZipCode() != null)
						form.setBillingZipCode(acc.getZipCode()+"");
					else
						form.setBillingZipCode("");
				} else {
					form.setShippingName(acc.getPatientName()+"");
					if (acc.getAddress() != null)
						form.setShippingAddress(acc.getAddress()+"");
					else
						form.setShippingAddress("");
					if (acc.getCity() != null)
						form.setShippingCity(acc.getCity()+"");
					else
						form.setShippingCity("");
					if (acc.getState() != null || !"".equalsIgnoreCase(acc.getState()))
						form.setShippingState(acc.getState()+"");
					else
						form.setShippingState("");
					if (acc.getCountry() != null)
						form.setShippingCountry(acc.getCountry());
					else
						form.setShippingCountry("");
					if (acc.getZipCode() != null)
						form.setShippingZipCode(acc.getZipCode()+"");
					else
						form.setShippingZipCode("");
				}
			} else if (addressOpt == 4) {
				if (isBilling) {
					form.setBillingName(acc.getPatientName()+"");
					form.setBillingAddress("");
					form.setBillingCity("");
					form.setBillingState("");
					form.setBillingCountry("");
					form.setBillingZipCode("");
				} else {
					form.setShippingName(acc.getPatientName()+"");
					form.setShippingAddress("");
					form.setShippingCity("");
					form.setShippingState("");
					form.setShippingCountry("");
					form.setShippingZipCode("");
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json2 = gson.toJson(form);	
		
		return json2; 
	}
	
	
	public String getPhysicianAddress(int physicianId, int addressOpt, boolean isBilling) {
		PrescriptionForm form = new PrescriptionForm();
		try {
			PhysicianProfile acc = phyService.getPhysicianData(physicianId, null,null);
			
			if (addressOpt == 1) {
				if (isBilling) {
					form.setBillingName(acc.getPhysicianName()+"");
					if (acc.getAddress1() != null)
						form.setBillingAddress(acc.getAddress1()+"");
					else
						form.setBillingAddress("");
					if (acc.getCity() != null)
						form.setBillingCity(acc.getCity()+"");
					else 
						form.setBillingCity("");
					
					if (acc.getState() != null && !"0".equalsIgnoreCase(acc.getState()))
						form.setBillingState(acc.getState()+"");
					else
						form.setBillingState("");
					
					if (acc.getCountry() != null)
						form.setBillingCountry(acc.getCountry());
					else
						form.setBillingCountry("");
					if (acc.getZipCode() != null)
						form.setBillingZipCode(acc.getZipCode()+"");
					else
						form.setBillingZipCode("");
				} else {
					form.setShippingName(acc.getPhysicianName()+"");
					if (acc.getAddress1() != null)
						form.setShippingAddress(acc.getAddress1()+"");
					else
						form.setShippingAddress("");
					if (acc.getCity() != null)
						form.setShippingCity(acc.getCity()+"");
					else 
						form.setShippingCity("");
					
					if (acc.getState() != null && !"0".equalsIgnoreCase(acc.getState()))
						form.setShippingState(acc.getState()+"");
					else
						form.setShippingState("");
					
					if (acc.getCountry() != null)
						form.setShippingCountry(acc.getCountry());
					else
						form.setShippingCountry("");
					if (acc.getZipCode() != null)
						form.setShippingZipCode(acc.getZipCode()+"");
					else
						form.setShippingZipCode("");
				}
			} else if (addressOpt == 2){
				if (isBilling) {
					form.setBillingName(acc.getPhysicianName()+"");
					
					if (acc.getAddress2() != null)
						form.setBillingAddress(acc.getAddress2()+"");
					else
						form.setBillingAddress("");
					if (acc.getCity2() != null)
						form.setBillingCity(acc.getCity2()+"");
					else
						form.setBillingCity("");
					
					if (acc.getState2() != null && !"0".equalsIgnoreCase(acc.getState2().trim()))
						form.setBillingState(acc.getState2()+"");
					else
						form.setBillingState("");
					if (acc.getCountry2() != null)
						form.setBillingCountry(acc.getCountry2());
					else
						form.setBillingCountry("");
					if (acc.getZipCode2() != null)
						form.setBillingZipCode(acc.getZipCode2()+"");
					else
						form.setBillingZipCode("");
				} else {
					form.setShippingName(acc.getPhysicianName()+"");
					
					if (acc.getAddress2() != null)
						form.setShippingAddress(acc.getAddress2()+"");
					else
						form.setShippingAddress("");
					if (acc.getCity2() != null)
						form.setShippingCity(acc.getCity2()+"");
					else
						form.setShippingCity("");
					
					if (acc.getState2() != null && !"0".equalsIgnoreCase(acc.getState2().trim()))
						form.setShippingState(acc.getState2()+"");
					else
						form.setShippingState("");
					if (acc.getCountry2() != null)
						form.setShippingCountry(acc.getCountry2());
					else
						form.setShippingCountry("");
					if (acc.getZipCode2() != null)
						form.setShippingZipCode(acc.getZipCode2()+"");
					else
						form.setShippingZipCode("");
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json2 = gson.toJson(form);	
		
		return json2; 
		
	}
	
	public String getClinicAddress(int physicianId,int clinicId, boolean isBilling) {
		PrescriptionForm form = new PrescriptionForm();
		try {
			// PhysicianProfile acc = phyService.getPhysicianData(physicianId, null,null);
			Clinic clinicObj=clinicRepo.findOne(clinicId);

			if (isBilling) {
				form.setBillingName(clinicObj.getClinicName()+"");
				if (clinicObj.getAddress() != null)
					form.setBillingAddress(clinicObj.getAddress()+"");
				else
					form.setBillingAddress("");
				if (clinicObj.getCity() != null)
					form.setBillingCity(clinicObj.getCity()+"");
				else
					form.setBillingCity("");
				if (clinicObj.getState() != null && !"0".equalsIgnoreCase(clinicObj.getState()))
					form.setBillingState(clinicObj.getState()+"");
				else
					form.setBillingState("");
				if (clinicObj.getCity() != null)
					form.setBillingZipCode(clinicObj.getZipCode()+"");
				else
					form.setBillingZipCode("");
				
				form.setBillingCountry("");
			} else {
				form.setShippingName(clinicObj.getClinicName()+"");
				if (clinicObj.getAddress() != null)
					form.setShippingAddress(clinicObj.getAddress()+"");
				else
					form.setShippingAddress("");
				if (clinicObj.getCity() != null)
					form.setShippingCity(clinicObj.getCity()+"");
				else
					form.setShippingCity("");
				if (clinicObj.getState() != null && !"0".equalsIgnoreCase(clinicObj.getState()))
					form.setShippingState(clinicObj.getState()+"");
				else
					form.setShippingState("");
				if (clinicObj.getCity() != null)
					form.setShippingZipCode(clinicObj.getZipCode()+"");
				else
					form.setShippingZipCode("");
				
				form.setShippingCountry("");
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json2 = gson.toJson(form);	
		
		return json2; 
		
	}
	
	public String getTemporaryAddress() {
		PrescriptionForm form = new PrescriptionForm();
		
		form.setBillingName("");
		form.setBillingAddress("");
		form.setBillingCity("");
		form.setBillingState("");
		form.setBillingCountry("");
		form.setBillingZipCode("");
		
		form.setShippingName("");
		form.setShippingAddress("");
		form.setShippingCity("");
		form.setShippingState("");
		form.setShippingCountry("");
		form.setShippingZipCode("");
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json2 = gson.toJson(form);	
		
		return json2;
	}


	public boolean updatePhysicianData(int PrescriptionId, PhysicianAccount phyAcc, LoginForm loggedInUser) {
		boolean isSaved = false;
		PrescriptionMaster acc = null;
		try  {
			if (PrescriptionId > 0) {
				acc = prescriptionRepo.findOne(PrescriptionId);
				List<PhysicianClinic>  physicianClinicObj= phyClinicRepo.getAllPhysicianWiseClinicList(phyAcc.getId());
				//set default first clinic when updated in profile page
				List<Clinic> clinicObj=clinicService.getClinicList(physicianClinicObj.get(0).getClinicId());
				acc.setClinicName(clinicObj.get(0).getClinicName());
				acc.setClinicId(clinicObj.get(0).getId());

				int groupId = 0;
				List<PhysicianGroup> list = phyGroupService.getByPhysician(phyAcc.getId());
				if (list.size() > 0) {
					groupId	= list.get(0).getGroupId();
					// groupName = groupService.getGroupMasterDetails(groupId).getGroupName();
				} 
				acc.setPhysicianId( acc.getPhysicianId() );
				acc.setGroupId( groupId );

				acc.setPhyAddress(phyAcc.getAddress1());
				acc.setPhyCity(phyAcc.getCity());
				acc.setPhyCountry(phyAcc.getCountry());
				acc.setPhyPhone(phyAcc.getMobile());
				acc.setPhyState(phyAcc.getState());
				acc.setPhyZipCode(phyAcc.getZipCode());
				
				acc.setLastUpdatedBy(loggedInUser.getUserid());
				acc.setLastUpdatedUser(loggedInUser.getType());
				acc.setLastUpdatedDate( PharmacyUtil.getCurrentTimeStamp() );
				acc.setUseGroupLogo(phyAcc.getUseGroupLogo());
				
				PhysicianProfileInfo accProfile = phyProfile.findOne(phyAcc.getId());
				if (accProfile != null) {
					acc.setPhyDea(accProfile.getDea());
					acc.setPhyNpi(accProfile.getNpi());
					acc.setPhyUpin(accProfile.getUpin());
					acc.setPhyStateLicense(accProfile.getStateLicense());
					acc.setPhyMedicaid(accProfile.getMedicaid());
				}
				prescriptionRepo.save(acc);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return isSaved;	
	}

	

	public String pineerItemControlSubstanceCheck(String itemId) {
		String output="N";
		
		try {
			PrescriptionMedication  medi = medicationRepo.findOne(Integer.parseInt(itemId));
			if (medi != null) {
				if(Integer.parseInt(medi.getReferencefederaldeaclasscode())>1 && Integer.parseInt(medi.getReferencefederaldeaclasscode())<6)
				{
					output = medi.getReferencefederaldeaclasscode();
				}
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return output;
	}


	@SuppressWarnings("restriction")
	public String savePhysicianPrescriptionSignature(String base64String,
			int prescriptionid, int physicianid,HttpSession session,PrescriptionForm form) {		
		try 
		{
			String imgPath = "";
			String rootFilePath= PharmacyUtil.getRootFolderForPrescriptionPDF(session, env);
			
			try {
				PrescriptionMaster acc = null;

				if (prescriptionid > 0) {
					acc = prescriptionRepo.findOne(prescriptionid);
					if (acc != null) {
						acc.setStatusId(3);//3==>Physician E-Signed
					}
					prescriptionRepo.save(acc);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			//System.out.println("base64String ============= "+base64String);
			if(base64String.length()>0)
			{		
				String value = base64String.substring(base64String.indexOf("base64,")+"base64,".length(),base64String.length());
				//System.out.println("ESSSSSSSSIGN beforrrrrrrrrrrrreeeeeee value============"+value);
				//If the E-Sign is empty,it does not store the signature
				if(!value.equalsIgnoreCase("iVBORw0KGgoAAAANSUhEUgAAARQAAABGCAYAAAANQWsxAAAA90lEQVR4nO3UQQkAMAzAwPo3vZoIDMqdgrwyDyAyvwOAOwwFyBgKkDEUIGMoQMZQgIyhABlDATKGAmQMBcgYCpAxFCBjKEDGUICMoQAZQwEyhgJkDAXIGAqQMRQgYyhAxlCAjKEAGUMBMoYCZAwFyBgKkDEUIGMoQMZQgIyhABlDATKGAmQMBcgYCpAxFCBjKEDGUICMoQAZQwEyhgJkDAXIGAqQMRQgYyhAxlCAjKEAGUMBMoYCZAwFyBgKkDEUIGMoQMZQgIyhABlDATKGAmQMBcgYCpAxFCBjKEDGUICMoQAZQwEyhgJkDAXIGAqQMRQgYyhAZgGlTcO1TWDuNgAAAABJRU5ErkJggg==")
						&& !value.equalsIgnoreCase("iVBORw0KGgoAAAANSUhEUgAAARQAAABGCAYAAAANQWsxAAAAYUlEQVR4nO3BAQ0AAADCoPdPbQ8HFAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA8GUuNQABMDFUNQAAAABJRU5ErkJggg=="))
				{
					value = value.replaceAll(" ", "+");
		    		//System.out.println("ESSSSSSSSIGN afterrrrrrrrrrrrrrrrrrrr value============"+value);
		       	
		        	imgPath = rootFilePath + File.separator + "physician_esign_"+prescriptionid+".png";
		    		File f = new File(rootFilePath);
		    		if (!f.exists())
		    			f.mkdir();
		    		
		    		f = new File(imgPath);
		    		if (!f.exists())
		    			f.mkdir();
		    		
		        	//System.out.println("imgPath in savePhysicianPrescriptionSignature ===="+imgPath);    
		        	
		        	File af = new File(imgPath); 
		        	if(af.exists())
		        		af.delete();
		        	
					byte[] btDataFile = new sun.misc.BASE64Decoder().decodeBuffer(value);  
					File of = new File(imgPath);  
					FileOutputStream osf = new FileOutputStream(of);  
					osf.write(btDataFile);  
					osf.flush();  
					
					String targetFolder = rootFilePath + File.separator + prescriptionid;
					String outputFile1 = targetFolder + File.separatorChar + "Prescription_"+form.getPrescriptionOrderNumber()+".pdf";
		    		String outputFile2="",esignedOutputFile2="";
		    		String esignedOutputFile1=targetFolder + File.separatorChar + "Prescription_"+form.getPrescriptionOrderNumber()+"_esigned.pdf";
					if (form.getMedications().size()> 0){
						for (int i = 0; i < form.getMedications().size(); i++) {
							if (!"N".equalsIgnoreCase(form.getMedications().get(i).getControlSubstance())) {
								outputFile2 = targetFolder + File.separatorChar + "PrescriptionControlSubstance_"+form.getPrescriptionOrderNumber()+".pdf";
								esignedOutputFile2=targetFolder + File.separatorChar + "PrescriptionControlSubstance_"+form.getPrescriptionOrderNumber()+"_esigned.pdf";
							}
						}
					}
					File fl1=new File(outputFile1);
					if(fl1.exists())
						fl1.delete();
				    File fl2=new File(outputFile2);
				    if(fl2.exists())
						fl2.delete();
				    
				    //System.out.println("********* Generate Prescription Normal Print PDF *****************");
					generatePrescriptionPdf(prescriptionid, rootFilePath, session, imgPath,false);
					//System.out.println("********* Generate Prescription Controlled Substance Print PDF *****************");
					generatePrescriptionPdf(prescriptionid, rootFilePath, session, imgPath,true);
					
				

		    		
		    		f = new File(targetFolder);
		    		if (!f.exists())
		    			f.mkdir();
			    		
			    		
			        List<String> inputFileList=new ArrayList<String>(); 
			        fl1=new File(outputFile1);
			        fl2=new File(outputFile2);
			        if(outputFile1!=null && outputFile1.length()>0 && fl1.exists())
			        	inputFileList.add(outputFile1);
			        if(outputFile2!=null && outputFile2.length()>0 && fl2.exists())
			        	inputFileList.add(outputFile2);	
			        
			        List<String> outputFileList=new ArrayList<String>();    
			        if(esignedOutputFile1!=null && esignedOutputFile1.length()>0 && fl1.exists())
			        	outputFileList.add(esignedOutputFile1);
			        if(esignedOutputFile2!=null && esignedOutputFile2.length()>0 && fl2.exists())
			        	outputFileList.add(esignedOutputFile2);	
		           
			        
			        for(int i=0;i< inputFileList.size();i++){
			        
			            PdfReader reader = new PdfReader(inputFileList.get(i).toString());
						ByteArrayOutputStream output = new ByteArrayOutputStream();
						PdfStamper stamper = new PdfStamper(reader, output);
						//System.out.println("reader.getNumberOfPages() ==="+reader.getNumberOfPages());
						PdfContentByte content = stamper.getOverContent(reader.getNumberOfPages());
						content = stamper.getOverContent(reader.getNumberOfPages());
						//String imgPath = SystemConfig.getInstance().getProperty("physicianprescription") + physicianid + "\\" + primaryPrescriptionid + "\\physician_esign.png";
						
						//System.out.println();
						//System.out.println("     Esign Image Path in stampPrescriptionFormESign===="+imgPath);
						//System.out.println();
						
						reader.close();
					   	stamper.close();
					   	
					   	FileOutputStream os = new FileOutputStream(outputFileList.get(i).toString());
					   
					   	byte bb[] = output.toByteArray();
					   	os.write(bb);
					   	os.close();
			        }
			        File af1 = new File(imgPath); 
		        	if(af1.exists())
		        		af1.delete();
		        	
		        	// Send PDF files to FTP
		        	boolean isTransferSuccess = prescriptionPdfFTPUpload(String.valueOf(prescriptionid), session);
		        	if (isTransferSuccess)
						System.out.println("Prescription PDF successfully uploaded to Remote FTP"); 
					else
						System.out.println("Failed to transfer Prescription PDF");
		        	
		        	osf.close();
				}
			}
		} 
		catch (Exception e) 
		{
			//System.out.println("[ Exception [ ESignModel::savePhysicianPrescriptionSignature]] :: "+e);
			e.printStackTrace();
		}
		return null;
	}

	public boolean getClass2ControlSubtance(int prescriptionId) {
		boolean class2Subtance = false;
		
		try {
			List<PrescriptionTransaction> accList = null;
			accList = prescriptionTranService.getTransactionByPrescription(prescriptionId);	
			if (accList.size() > 0) {
				for (PrescriptionTransaction b : accList) {
					if ("2".equalsIgnoreCase(b.getControlSubstance().trim())) {
						class2Subtance = true;
					}
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}

		return class2Subtance;
	}
	
	public boolean getFileFtpAllowed(int prescriptionId) {
		boolean isFtpAllow = false;
		boolean class2Subtance = false;
		String scan="",fax="";
		try {
			
			List<PrescriptionTransaction> accList = null;
			accList = prescriptionTranService.getTransactionByPrescription(prescriptionId);	
			if (accList.size() > 0) {
				for (PrescriptionTransaction b : accList) {
					if ("2".equalsIgnoreCase(b.getControlSubstance().trim())) {
						class2Subtance = true;
					}
				}
			}
			
			PrescriptionMaster acc = prescriptionRepo.findOne(prescriptionId);
			if (acc != null) {
				scan=acc.getScan();
				fax=acc.getFax();
			}
			
			if (class2Subtance) {
				List<SubtanceItemDocuments> docList = substanceDocRepo.findAllByPrescriptionId(prescriptionId);
				if (docList.size() > 0) {
					for (SubtanceItemDocuments doc: docList) {
						if ((scan.equalsIgnoreCase("Y") ||  fax.equalsIgnoreCase("Y")) && doc.getIsHardCopy().equalsIgnoreCase("Y")){
							// if (doc.getVerifyBy() > 0)  // uncomment if admin verification also need to considered
								isFtpAllow = true;
						}
					}
				}
			} else
				isFtpAllow = true;
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return isFtpAllow;
	}
	
	
	public boolean saveSubtanceDocFiles(CommonsMultipartFile docFile, int prescriptionid, String rootFilePath, LoginForm loggedInUser,
			String scan, String fax, String isHardCopy, String description) {
		boolean isUploaded = false;
		if (docFile.getSize() > 0) {
			// Get last file id (primary key) for generating next id for append with Document file name 
			int nextDocId = substanceDocRepo.getLastDocId();
			
			String folderName="subtanceItems";
			String photoFileName="document_"+(++nextDocId);
			isUploaded=PharmacyUtil.userPhotoUpload(docFile, folderName, photoFileName, rootFilePath, prescriptionid, "Document");
			if (isUploaded){
				String oname=docFile.getOriginalFilename();
				String extension = oname.substring(oname.lastIndexOf('.') + 1, oname.length());
				
				StringBuffer uploadedFile = new StringBuffer(PharmacyUtil.getFileUploadPath(rootFilePath, folderName, prescriptionid, "Document"));
				uploadedFile.append( File.separator + photoFileName + "." + extension);
				
				subtanceDocService.documentFileSave(prescriptionid, docFile, uploadedFile.toString(), loggedInUser, scan, fax, isHardCopy, description);
				
				//System.out.println("Photo uploaded ok");
			} else {
				//System.out.println("Photo not uploaded");
			}
		}
		
		return isUploaded;
	}
	
	public String[] duplicateRXAlreadyExists(String rxItemFormId,int patientFormId, int writtenByFormId) {
		String[] output=new String[8];
		
		try {
			List<Object[]>  orderList =orderMasterRepo.getFilterRecordsByPatientIdandPhysicianId(patientFormId,writtenByFormId,rxItemFormId);
			
			if(orderList!=null && orderList.size()>0)
			{
				//t.rxNumber,pt.itemName,t.rxStatus,pt.rxStatus
				Object[] obj=(Object[])orderList.get(0);
				output[0] = "Y";
				output[1] =obj[0].toString();//rxnumber
				output[2] =obj[1].toString();//itemname
				output[3] =obj[2].toString();//order Status
				output[4] =obj[3].toString();//rx status
				output[5] =patientService.getPatientData(patientFormId).getPatientName();
				output[6] =phyService.getPhysicianData(writtenByFormId, null,null).getPhysicianName();
				output[7] = obj[4].toString();//cre8PrescriptionNo
			}else
			{
				output[0]="N";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return output;
	}
	
	
	public boolean prescriptionPdfFTPUpload(String prescriptionId, HttpSession session) {
		boolean isTransferSuccess1 =false,isTransferSuccess2=false,isTransferSuccess=false;
		
		try {
			File fl1=null,fl2=null;
			
			String prescriptionOrderNumber=getPrescriptionData(Integer.parseInt(prescriptionId), session).getPrescriptionOrderNumber();
			String targetFileName1="Prescription_"+prescriptionOrderNumber+".pdf";
			String targetFileName2="PrescriptionControlSubstance_"+prescriptionOrderNumber+".pdf";
			String rootFilePath= PharmacyUtil.getRootFolderForPrescriptionPDF(session, env);
			
	        String sourceFile1 = rootFilePath + File.separator + Integer.parseInt(prescriptionId) + File.separatorChar + "Prescription_"+prescriptionOrderNumber+"_esigned.pdf";
	        String sourceFile2 = rootFilePath + File.separator + Integer.parseInt(prescriptionId) + File.separatorChar + "PrescriptionControlSubstance_"+prescriptionOrderNumber+"_esigned.pdf";
			
	        //System.out.println("sourceFile1 ============"+sourceFile1);
	        //System.out.println("sourceFile2 ============"+sourceFile2);
	        
			String serverName = env.getProperty("ftp.server.name");
			String serverUserName = env.getProperty("ftp.server.user_name");
			String serverPassword = env.getProperty("ftp.server.password");
			String targetFolder = env.getProperty("ftp.target.folder");
			String serverPort = env.getProperty("ftp.target.port");
			
			fl1=new File(sourceFile1);
			fl2=new File(sourceFile2);
			
			if(fl1.exists())
				isTransferSuccess1 = pdfFtpTransfer(serverName, serverUserName, serverPassword, targetFolder, sourceFile1, targetFileName1,Integer.parseInt(prescriptionId),Integer.parseInt(serverPort));
			if(fl2.exists())
				isTransferSuccess2 = pdfFtpTransfer(serverName, serverUserName, serverPassword, targetFolder, sourceFile2, targetFileName2,Integer.parseInt(prescriptionId),Integer.parseInt(serverPort));
			
			if(fl1.exists() && fl2.exists())
			{
				if(isTransferSuccess1 && isTransferSuccess2)
					isTransferSuccess=true;
			}else if(fl1.exists())
			{
				if(isTransferSuccess1)
					isTransferSuccess=true;
			}else if(fl2.exists())
			{
				if(isTransferSuccess2)
					isTransferSuccess=true;
			}
			
			//System.out.println("Ftp Transfer status 111111111" + isTransferSuccess1);
			//System.out.println("Ftp Transfer status 222222222" + isTransferSuccess2);
			//System.out.println("Ftp Transfer status 333333333" + isTransferSuccess);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return isTransferSuccess;
	}

	
	public void prescriptionMedicationChangesNotification(int tranId, LoginForm loggedInUser, HttpSession session) {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		PrescriptionTransaction acc = prescriptionTranRepo.findOne(tranId);
		PrescriptionForm form = getPrescriptionData(acc.getPrescriptionId(), session);
		
		StringBuilder sb = new StringBuilder();
		sb.append("Reference # :");
		sb.append(acc.getCre8PrescriptionNo()).append(",");
		sb.append("Rx # :");
		sb.append(acc.getRxNumber()).append(",");
		sb.append(form.getPatientName()).append(",");
		sb.append( sdf.format( new Date() ));
		if(acc.getItemname()!=null && acc.getItemname().length()>0)
			sb.append(acc.getItemname()).append(",");
		else if(acc.getMedicationdescription()!=null && acc.getMedicationdescription().length()>0)
			sb.append(acc.getMedicationdescription()).append(",");
		sb.append(acc.getDispensedItemName());

		Notification notify = notificationService.getPrescriptionMedicationChanged(form.getPrescriptionId());
		if (notify == null) 
			notify = new Notification();

		notify.setDelFlag(PharmacyUtil.deleteFlagNo);
		notify.setNotificationMessage(sb.toString());
		notify.setNotifiedDate(PharmacyUtil.getCurrentTimeStamp());
		notify.setNotifyBy(loggedInUser.getUserid());
		notify.setNotifyRecordId(form.getPrescriptionId());
		notify.setNotifyRecordType(PharmacyUtil.pagePrescription);
		notify.setNotifyTypeId(6);							// Medication has changed
		notify.setNotifyUserType(loggedInUser.getType());
		notify.setPatientName(form.getPatientName());
		notify.setPhysicianName( phyService.getPhysicianAccountDetails(form.getPhysicianId()).getPhysicianName() );
		
		notificationService.saveNotification(notify);
		
	}
	
	public void prescriptionControlSubstanceItemsNotification(int tranId, LoginForm loggedInUser, HttpSession session) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		PrescriptionTransaction acc = prescriptionTranRepo.findOne(tranId);
		PrescriptionForm form = getPrescriptionData(acc.getPrescriptionId(), session);
		
		StringBuilder sb = new StringBuilder();
		sb.append( form.getCre8PrescriptionNo()).append(",");
		sb.append(form.getRxNumber()).append(",");
		sb.append(form.getPatientName()).append(",");
		sb.append( sdf.format( new Date() ));
		
		Notification notify = notificationService.getPrescriptionControlledSubstance(form.getPrescriptionId());
		if (notify == null) 
			notify = new Notification();

		notify.setDelFlag(PharmacyUtil.deleteFlagNo);
		notify.setNotificationMessage(sb.toString());
		notify.setNotifiedDate(PharmacyUtil.getCurrentTimeStamp());
		notify.setNotifyBy(loggedInUser.getUserid());
		notify.setNotifyRecordId(form.getPrescriptionId());
		notify.setNotifyRecordType(PharmacyUtil.pagePrescription);
		notify.setNotifyTypeId(5);							// Controlled subtance of Class 2  Upload documents of either Prescription Scanned / Prescription
		notify.setNotifyUserType(loggedInUser.getType());
		notify.setPatientName(form.getPatientName());
		notify.setPhysicianName( phyService.getPhysicianAccountDetails(form.getPhysicianId()).getPhysicianName() );
		
		notificationService.saveNotification(notify);	
		
	
	}
	

	public void prescriptionItemChangesNotification(int tranId, LoginForm loggedInUser, HttpSession session) {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		PrescriptionTransaction acc = prescriptionTranRepo.findOne(tranId);
		PrescriptionForm form = getPrescriptionData(acc.getPrescriptionId(), session);
		
		StringBuilder sb = new StringBuilder();
		sb.append("Reference # :");
		sb.append(acc.getCre8PrescriptionNo()).append(",");
		sb.append("Rx # :");
		sb.append(acc.getRxNumber()).append(",");
		sb.append(form.getPatientName()).append(",");
		sb.append( sdf.format( new Date() ));
		if(acc.getItemname()!=null && acc.getItemname().length()>0)
			sb.append(acc.getItemname()).append(",");
		else if(acc.getMedicationdescription()!=null && acc.getMedicationdescription().length()>0)
			sb.append(acc.getMedicationdescription()).append(",");
		sb.append(acc.getDispensedItemName());

		Notification notify = notificationService.getPrescriptionMedicationChanged(form.getPrescriptionId());
		if (notify == null) 
			notify = new Notification();

		notify.setDelFlag(PharmacyUtil.deleteFlagNo);
		notify.setNotificationMessage(sb.toString());
		notify.setNotifiedDate(PharmacyUtil.getCurrentTimeStamp());
		notify.setNotifyBy(loggedInUser.getUserid());
		notify.setNotifyRecordId(form.getPrescriptionId());
		notify.setNotifyRecordType(PharmacyUtil.pagePrescription);
		notify.setNotifyTypeId(6);							// Medication has changed
		notify.setNotifyUserType(loggedInUser.getType());
		notify.setPatientName(form.getPatientName());
		notify.setPhysicianName( phyService.getPhysicianAccountDetails(form.getPhysicianId()).getPhysicianName() );
		
		notificationService.saveNotification(notify);
		
	}
	
	public String pineerSubstancePrescriptionItemheck(String itemId) {
		String output="N";
		
		try {
			PrescriptionItem  prescriptionItem = itemRepo.findOne(itemId);
			if (prescriptionItem != null) {
				if(prescriptionItem.getDeaschedule()>1 && prescriptionItem.getDeaschedule()<6)
				{
					output = String.valueOf(prescriptionItem.getDeaschedule());
				}
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return output;
	}

	
}
