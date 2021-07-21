package com.pharma.core.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pharma.core.formBean.DocumentFileList;
import com.pharma.core.formBean.LoginForm;
import com.pharma.core.formBean.patient.PatientAccountForm;
import com.pharma.core.formBean.physician.PhysicianAssistantRegistration;
import com.pharma.core.formBean.prescription.PrescriptionForm;
import com.pharma.core.model.CardTypeMaster;
import com.pharma.core.model.States;
import com.pharma.core.model.patientaccount.PatientAccount;
import com.pharma.core.model.pharmacygroup.GroupDirector;
import com.pharma.core.model.pharmacygroup.GroupMaster;
import com.pharma.core.model.physician.PhysicianAccount;
import com.pharma.core.model.physician.PhysicianGroup;
import com.pharma.core.model.pioneer.PatientRxNotifyProviderType;
import com.pharma.core.model.pioneer.PatientRxNotifyType;
import com.pharma.core.model.pioneer.PatientSyncStatusType;
import com.pharma.core.pharmaservices.StatesService;
import com.pharma.core.pharmaservices.patientservices.PatientAccountService;
import com.pharma.core.pharmaservices.pharmacygroupservices.GroupMasterService;
import com.pharma.core.pharmaservices.pharmacygroupservices.GroupMasterServiceImpl;
import com.pharma.core.pharmaservices.physicianservices.PhysicianAccountService;
import com.pharma.core.pharmaservices.physicianservices.PhysicianAssistantAccountService;
import com.pharma.core.pharmaservices.pioneer.PatientRxNotifyProviderTypeService;
import com.pharma.core.pharmaservices.pioneer.PatientRxNotifyTypeService;
import com.pharma.core.pharmaservices.pioneer.PatientSyncStatusTypeService;
import com.pharma.core.pharmaservices.prescription.PrescriptionService;
import com.pharma.core.repository.CardTypeMasterRepository;
import com.pharma.core.repository.clinic.ClinicRepository;
import com.pharma.core.repository.patient.PatientAccountRespository;
import com.pharma.core.repository.pharmacygroup.GroupDirectorAccountRespository;
import com.pharma.core.repository.pharmacygroup.GroupMasterRespository;
import com.pharma.core.repository.physician.PhysicianAccountRespository;
import com.pharma.core.repository.physician.PhysicianAssistantAccountRespository;
import com.pharma.core.repository.physician.PhysicianGroupRepository;
import com.pharma.core.util.PharmacyUtil;
/**
 * This class is a controller to save and load the patient account details
 */
@Controller
@PropertySource("classpath:patientAccount.properties")
@PropertySource("classpath:physicianAccount.properties")
@PropertySource("classpath:mail.properties")
public class PatientAccountController extends PatientBaseController{
	
	@Autowired
	PatientAccountService patientAccountService;
	
	@Autowired
	PhysicianAccountService physicianService;
	
	@Autowired
	PhysicianAssistantAccountService assistService;
	
	@Autowired
	PhysicianAssistantAccountRespository assistantRepo;
	
	@Autowired
	PhysicianAccountRespository physicianRepo;
	
	@Autowired
	StatesService stateService;
	
	@Autowired
	GroupMasterService groupService;
	
	@Autowired
	GroupDirectorAccountRespository groupDirRepo;
	
	@Autowired
	private Environment env;
	
	@Autowired
	PatientRxNotifyProviderTypeService rxNotifyProviderService;
	
	@Autowired
	PatientRxNotifyTypeService rxNotifyTypeService;
	
	@Autowired
	PatientSyncStatusTypeService patientSyncStatusTypeService;
	
	@Autowired
	PrescriptionService prescriptionService;
	
	@Autowired
	CardTypeMasterRepository cardTypeMasterRepo;
	
	@Autowired
	ClinicRepository clinicRepo;
	
	@Autowired
	PhysicianAccountRespository physicianRep;
	
	@Autowired
	PhysicianGroupRepository phyGroupRepo;
	
	@Autowired
	GroupMasterRespository groupMasterRepo;
	
	@Autowired
	PatientAccountRespository patientAccountResp;
	
	@Autowired
	GroupMasterServiceImpl groupMasterService;
	
	/**
	 * This method loads the patient account summary page
	 * @param model
	 * @param request
	 * @param response
	 * @param session
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping("/patientaccountsummary")
	public ModelAndView patientaccountsummary(Model model, HttpServletRequest request, HttpServletResponse response, 
			HttpSession session,RedirectAttributes redirectAttributes){
		
		ModelAndView mv = new ModelAndView();
		try {
			model.addAttribute("message", model.asMap().get("message"));

			LoginForm user = (LoginForm) session.getAttribute("loginDetail");
			if (PharmacyUtil.userPhysician.equalsIgnoreCase(user.getType())) 
				model.addAttribute("physicianId", user.getUserid());
			else if (PharmacyUtil.userPhysicianAssistant.equalsIgnoreCase(user.getType()))
			{
				
				int phyId=user.getPhysicianAssistantPhysicianId();
				model.addAttribute("physicianId",phyId);
				
				//temp commented on jan 19,2018
				//model.addAttribute("physicianId",  assistantRepo.findOne(user.getUserid()).getPhysicianId() );
			}
			else
				model.addAttribute("physicianId",  0);
			
			LoginForm loggedInUser = (LoginForm) session.getAttribute("loginDetail");
			if (!loggedInUser.getType().equalsIgnoreCase(PharmacyUtil.userGroupDirector)) {
				List<GroupMaster> groupList = groupService.getAllGroupMaster();
				mv.addObject("groupList", groupList);
			} else {
				GroupDirector gp = groupDirRepo.findOne(loggedInUser.getUserid());
				mv.addObject("groupId", gp.getGroupId());
			}
			
			PatientAccountForm form = new PatientAccountForm();
			mv.addObject("form", form);
			
			mv.addObject("physicianList", groupMasterService.getPhysicianListForGroupChange());
			
			mv.setViewName("patientaccountsummary");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return mv;
	}
	
	/**
	 * This method save and loads the patient account page
	 * @param form
	 * @param bindingResult
	 * @param model
	 * @param licenseExpDate
	 * @param dateOfBirth
	 * @param request
	 * @param response
	 * @param session
	 * @param redirectAttributes
	 * @param patientPhoto
	 * @return
	 */
	@RequestMapping(value = "/SavePatientAccount", method = RequestMethod.POST)
	public ModelAndView SavePatientAccount(@ModelAttribute("patientAccount") PatientAccountForm form,  BindingResult bindingResult, Model model,
			@Param("licenseExpDate") String licenseExpDate,  @Param("dateOfBirth") String dateOfBirth, HttpServletRequest request, 
			HttpServletResponse response, HttpSession session,RedirectAttributes redirectAttributes,
			@RequestParam CommonsMultipartFile patientPhoto) {
		
		ModelAndView mv = new ModelAndView();
		try {
			LoginForm loggedInUser = (LoginForm) session.getAttribute("loginDetail");
			boolean isUserIdExist = false;
			boolean isSsnExist = false;
			
			try {
				form.setDateOfBirth( dateOfBirth);
				form.setLicenseExpDate(licenseExpDate);
				
				if (!"".equalsIgnoreCase(form.getUserLoginId())) {
					List<PatientAccount> exObj = null;
					if (form.getPatientId() > 0) {
						exObj = patientAccountService.getPatientAccountUserIdAndNotId(form.getUserLoginId(), form.getPatientId());
					} else {
						exObj = patientAccountService.getPatientAccountByUserId(form.getUserLoginId());
					}
					if (exObj.size() > 0 ) {
						isUserIdExist = true;
					}
				}
				// Checking user id Duplication
				/*if (exObj.size() == 0 && form.getUserLoginId() != null && !"".equalsIgnoreCase(form.getUserLoginId())) {
					if (form.getPatientId() > 0) {
						exObj = patientAccountService.getPatientAccountUserIdAndNotId(form.getUserLoginId(), form.getPatientId());
					} else {
						exObj = patientAccountService.getPatientAccountByUserId(form.getUserLoginId());
					}
					if (exObj.size() > 0 ) {
						isUserIdExist = true;
					}
				} */ /*else {
					isUserIdExist = true;
				}*/
				
				// Checking SSN Duplication
				/*if (!isUserIdExist && form.getGroupId() > 0) {
					if (form.getPatientId() > 0) {
						exObj = patientAccountService.getPatientAccountSsnAndNotId(form.getSsn(), form.getGroupId(), form.getPatientId());
					} else {
						exObj = patientAccountService.getPatientAccountBySSN(form.getSsn(), form.getGroupId());
					}
					if (exObj.size() > 0 ) {
						isSsnExist = true;
					}
				}*/
				/**
				 * Added on Feb 8,2018=>Check for Patient Name and DOB for already existing for other group or other physicians,
				 * if so add them to new group or new physician
				 */
				boolean patientAlreadyExists = false;
				int alreadyExistingPatientId=0;
				// Check for Patient Name and DOB for already existing
				if (form.getPatientId()==0) {
					
					List<PatientAccount> exObj = null;
					Date dob=null;
					if (form.getDateOfBirth() != null && !"".equalsIgnoreCase(form.getDateOfBirth()))
						dob = PharmacyUtil.getSqlDateFromString(form.getDateOfBirth());
					
					exObj = patientAccountResp.findByPatientNameAndDOB(form.getPatientName(),dob);
				
					if (exObj.size() > 0 ) {
						alreadyExistingPatientId=exObj.get(0).getId();
						patientAlreadyExists = true;
					}
				}
				
				boolean isPhysicianApproved = true;
				StringBuffer physicianName = new StringBuffer();
				if (PharmacyUtil.statusApproved.equalsIgnoreCase(form.getStatus())) {
					PhysicianAccount phyInfo = null;
					String[] pIds = form.getSelectedPhysicianId().split(",");
					if (pIds.length > 0) {
						for (int i = 0; i < pIds.length; i++) {
							phyInfo = physicianRepo.findOne(Integer.parseInt(pIds[i]));
							if (!PharmacyUtil.statusApproved.equalsIgnoreCase(phyInfo.getStatus())) {
								isPhysicianApproved = false;
								physicianName.append( phyInfo.getPhysicianName()).append(", ");
							}
						}
					}
				}

				if (!isUserIdExist && !isSsnExist && isPhysicianApproved && !patientAlreadyExists) {
					String rootFilePath= PharmacyUtil.getRootFolderForPhoto(session, env);
					PatientAccount acc = patientAccountService.save(form, patientPhoto, rootFilePath, loggedInUser, session, request, env);
					
					if (acc != null) {
						redirectAttributes.addFlashAttribute("saveStatus", "true");
						if (form.getPatientId() > 0) 
							redirectAttributes.addFlashAttribute("message", "Patient Account updated successfully");
						else
							redirectAttributes.addFlashAttribute("message", "Patient Account created successfully");
						
						if (PharmacyUtil.userPatient.equalsIgnoreCase(loggedInUser.getType()) && acc.getId() == loggedInUser.getUserid()) {
							LoginForm login = new LoginForm();
							login.setDisplayName(acc.getPatientName());
							login.setPhotoUrl(patientAccountService.getPatientPhotoFileName(acc.getId(), env.getProperty("file.photo_path")));
							login.setStatus(acc.getStatus());
							login.setType(PharmacyUtil.userPatient);
							login.setUserid(acc.getId());
							login.setUserName(acc.getEmail());
							
							session.setAttribute("loginDetail", login);
						}
						
						if (form.getTargetObject() != null && "prescription".equalsIgnoreCase(form.getTargetObject())) {
							String PrescriptionId = form.getPrescriptionId();
//						PrescriptionForm prescriptionForm = new PrescriptionForm();
//						prescriptionForm.setPrescriptionId(Integer.parseInt(PrescriptionId));
							
							prescriptionService.updatePatientData(Integer.parseInt(PrescriptionId), acc, loggedInUser);
							/*redirectAttributes.addFlashAttribute("form", prescriptionForm);
							mv.setViewName("redirect:/prescription/editPrescription");*/
							
							form = new PatientAccountForm();
							form.setPatientId(acc.getId());
							
							if (!"".equalsIgnoreCase(PrescriptionId) && Integer.parseInt(PrescriptionId) > 0) {
								form.setTargetObject("prescription");
								form.setPrescriptionId(PrescriptionId);
							}
							redirectAttributes.addFlashAttribute("form", form);
							mv.setViewName("redirect:editPatientAccount");
						} else { 
							String PrescriptionId = form.getPrescriptionId();
						
							form = new PatientAccountForm();
							form.setPatientId(acc.getId());
							
							if (!"".equalsIgnoreCase(PrescriptionId) && Integer.parseInt(PrescriptionId) > 0) {
								form.setTargetObject("prescription");
								form.setPrescriptionId(PrescriptionId);
							}
							
							redirectAttributes.addFlashAttribute("form", form);
							mv.setViewName("redirect:editPatientAccount");
						}
					} else {
						model.addAttribute("message", "Patient Account failed to create");
						mv.setViewName("error400");
					}
				} else {
					/*if (form.getTargetObject() != null && "prescription".equalsIgnoreCase(form.getTargetObject())) {
						String PrescriptionId = form.getPrescriptionId();
						PrescriptionForm prescriptionForm = new PrescriptionForm();
						prescriptionForm.setPrescriptionId(Integer.parseInt(PrescriptionId));
						redirectAttributes.addFlashAttribute("form", prescriptionForm);
						mv.setViewName("redirect:/prescription/editPrescription");
					} else {*/
						if (!isPhysicianApproved) {
							redirectAttributes.addFlashAttribute("saveStatus", "false");
							redirectAttributes.addFlashAttribute("message", "Physician(s) "+physicianName.toString().substring(0, physicianName.length()-2)+" still not Approved. Patient cannot be Approved");
						} else if (isSsnExist) {
							redirectAttributes.addFlashAttribute("saveStatus", "false");
							redirectAttributes.addFlashAttribute("message", "SSN "+form.getSsn()+" already exists");
						} else if (patientAlreadyExists) {
							/**
							 * Added on Feb 8,2018=>Check for Patient Name and DOB for already existing for other group or other physicians,
							 * if so add them to new group or new physician
							 */
							if(form.getSelectedGroupId()!=null && form.getSelectedGroupId().length()>0)
							{
								patientAccountService.saveNewGroupAlreadyExistingPatient( form, alreadyExistingPatientId, loggedInUser);
							}
							if(form.getSelectedPhysicianId()!=null && form.getSelectedPhysicianId().length()>0)
							{
								patientAccountService.saveNewPhysicianAlreadyExistingPatient( form, alreadyExistingPatientId, loggedInUser);
							}
							//Set the Already Existing Patient Id
							form.setPatientId(alreadyExistingPatientId);
							redirectAttributes.addFlashAttribute("saveStatus", "true");
							redirectAttributes.addFlashAttribute("message", "Patient : "+form.getPatientName()+" already exists");
						} else {
							redirectAttributes.addFlashAttribute("saveStatus", "false");
							redirectAttributes.addFlashAttribute("message", "Username already exists");
						}
						
						if (form.getPatientId() > 0) {
							redirectAttributes.addFlashAttribute("form", form);
							mv.setViewName("redirect:editPatientAccount");
						}
						else {
							redirectAttributes.addFlashAttribute("form", form);
							mv.setViewName("redirect:patientAccount");
						}
//				}
				}
			} catch (Exception e) {
				e.printStackTrace();
				model.addAttribute("message", "Patient Account failed to create.");
				mv.setViewName("error500");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mv;
	}	
	
	/**
	 * This method loads the patient account page
	 * @param model
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping("/patientAccount")  
	public ModelAndView loadPatientAccount(Model model, HttpServletRequest request, HttpServletResponse response, HttpSession session){
		LoginForm loggedInUser = (LoginForm) session.getAttribute("loginDetail");
		List<States> statelist = stateService.getAllStates();
		List<PatientRxNotifyProviderType> rxNotifyProviderTypeList = rxNotifyProviderService.getAllPatientRxNotifyProviderType();
		List<PatientRxNotifyType> rxNotifyTypeList = rxNotifyTypeService.getAllPatientRxNotifyType();
		List<PatientSyncStatusType> patientSyncStatusTypeList = patientSyncStatusTypeService.getAllPatientSyncStatusType();
		
		ModelAndView mv = new ModelAndView();
		
		try {
			PatientAccountForm patient = null;
			try {
				patient = (PatientAccountForm) model.asMap().get("form");	
			} catch(Exception e) {
				e.printStackTrace();
			}
			if (patient == null) {
				SimpleDateFormat dt = new SimpleDateFormat("MM/dd/yyyy");
				patient = new PatientAccountForm();
				patient.setStatus(PharmacyUtil.statusNew);
				patient.setDateRegistered(dt.format( new java.sql.Date(new Date().getTime())) );
			}

			patient.setCountry("USA");
			int groupId = 0;
			if (loggedInUser.getType().equalsIgnoreCase(PharmacyUtil.userPhysician)) {
				
				PhysicianAccount phyAcc = physicianRep.findOne(loggedInUser.getUserid());
				PhysicianGroup phyGroup = phyGroupRepo.findRecordByPhysicianId(phyAcc.getId());
				
				patient.setPhysicianId( loggedInUser.getUserid()  );
				if(patient.getPatientId()==0)
				{
					patient.setSelectedPhysicianId( loggedInUser.getUserid() +"" );
					if (phyGroup != null) {
						patient.setSelectedGroupId(phyGroup.getGroupId()+"");
					}
				}
				mv.addObject("userid", loggedInUser.getUserid());
				mv.addObject("usertype", loggedInUser.getType());
				
				patient.setPhysicianName(loggedInUser.getDisplayName());
				//Commented on jan 22,2018
				//patient.setClinicName(clinicRepo.findOne(phyAcc.getClinicId()).getClinicName());
				
				mv.addObject("physicianFullName", loggedInUser.getDisplayName());
				
				if (phyGroup != null) {
					groupId = phyGroup.getGroupId();
					patient.setGroupId(phyGroup.getGroupId());
					patient.setGroupName( groupService.getGroupMasterDetails(phyGroup.getGroupId()).getGroupName() );
				}
			
			} else {
				if (loggedInUser.getType().equalsIgnoreCase(PharmacyUtil.userPhysicianAssistant)) {
					//temp commented on jan 19,2018
					//int physicianId = assistantRepo.findOne( loggedInUser.getUserid()).getPhysicianId();
					
					int physicianId = loggedInUser.getPhysicianAssistantPhysicianId();
					
					PhysicianAccount phyAcc = physicianRep.findOne(physicianId);
					PhysicianGroup phyGroup = phyGroupRepo.findRecordByPhysicianId(phyAcc.getId());
					
					patient.setPhysicianId(physicianId);
					if(patient.getPatientId()==0)
					{
						patient.setSelectedPhysicianId(physicianId +"" );
						
						if (phyGroup != null) {
							patient.setSelectedGroupId(phyGroup.getGroupId()+"");
						}
					}
					mv.addObject("userid", loggedInUser.getUserid());
					mv.addObject("usertype", loggedInUser.getType());
					
					
					
					patient.setPhysicianName(phyAcc.getPhysicianName());
					//Commented on jan 22,2018
					//patient.setClinicName(clinicRepo.findOne(phyAcc.getClinicId()).getClinicName());
					
					mv.addObject("physicianFullName", phyAcc.getPhysicianName());
					
					if (phyGroup != null) {
						groupId = phyGroup.getGroupId();
						patient.setGroupId(phyGroup.getGroupId());
						patient.setGroupName(groupService.getGroupMasterDetails(phyGroup.getGroupId()).getGroupName());
					}
				} else if (loggedInUser.getType().equalsIgnoreCase(PharmacyUtil.userGroupDirector)) {
					
					groupId = loggedInUser.getGroupid();
					GroupDirector gp = groupDirRepo.findOne(loggedInUser.getUserid());
					List<PhysicianAccount> physicianList = physicianService.getApprovedPhysicianByGroupIdAndId(gp.getGroupId(), 0);
					mv.addObject("physicianList", physicianList);
					
					if(patient==null || patient.getPatientId()==0)
					{
						groupId =loggedInUser.getGroupid();
						patient.setSelectedGroupId(loggedInUser.getGroupid()+"");
						patient.setGroupId(loggedInUser.getGroupid());
						patient.setGroupName(loggedInUser.getGroupName());
						
					}

					
				} else {
					List<PhysicianAccount> physicianList = physicianService.getApprovedPhysician();
					mv.addObject("physicianList", physicianList);
					
				}	
			}
			
			//Added on Dec 16,2017-Temp Password generated in New Account by default
			if(patient.getPatientId()==0)
			{
				String randomPwd = PharmacyUtil.randomPasswordGenerator();
				patient.setPassword(randomPwd);
			}
			mv.addObject("groupId", groupId);
			patient.setGroupId(groupId);
			patient.setSendMailPermission("Yes");
			mv.addObject("userType", loggedInUser.getType());
			model.addAttribute("message", model.asMap().get("message"));
			
			mv.addObject("stateList", statelist);
			List<CardTypeMaster> cardList = cardTypeMasterRepo.findAll();
			mv.addObject("cardList", cardList);
			
			mv.addObject("rxNotifyProviderTypeList", rxNotifyProviderTypeList);
			mv.addObject("rxNotifyTypeList", rxNotifyTypeList);
			mv.addObject("patientSyncStatusTypeList", patientSyncStatusTypeList);
			mv.addObject("prescriptionId", "");
			
			//List<GroupMaster> groupList = groupService.getAllGroupMaster(PharmacyUtil.statusActive);
			
			//Multiple Group select list box
			List<GroupMaster> groupList =null;
			List<GroupMaster> groupSelectedList =null;
			
			if (patient.getPatientId()>0) {
				groupList = groupMasterRepo.getAllPatientGroupWiseListNotSelected(patient.getPatientId());
				groupSelectedList = groupMasterRepo.getAllPatientGroupWiseListSelected(patient.getPatientId());
			}else {
				groupList = groupService.getAllGroupMaster(PharmacyUtil.statusActive);
			}
						
			//Multiple Physician select list box for Multiple Group
			List<PhysicianAccount> physicianList =null;
			List<PhysicianAccount> physicianSelectedList =null;
			
			List<Integer> patGroupList = new ArrayList<Integer>();
			String patselectedGroupId=patient.getSelectedGroupId();
			if(patselectedGroupId!=null && patselectedGroupId.length()>0)
			{

				String[] patselectedGroupIdArr=patselectedGroupId.split(",");
				

				for (String i : patselectedGroupIdArr) {
					patGroupList.add(Integer.valueOf(i));
				}

			}

			if(patGroupList!=null && patGroupList.size()>0){
			if (patient.getSelectedGroupId()!=null && patient.getSelectedGroupId().length()>0 && patient.getPatientId()>0) {
				physicianList = physicianRep.getAllPatientPhysicianGroupWisePhysicianListNotSelected(patGroupList,patient.getPatientId());
				physicianSelectedList = physicianRep.getAllPatientPhysicianGroupWisePhysicianListSelected(patGroupList,patient.getPatientId());
			}else if (patient.getSelectedGroupId()!=null && patient.getSelectedGroupId().length()>0) {
				physicianList = physicianRep.getAllGroupWisePhysicianList(patGroupList,0);
			}
			}

			//Multiple Physician select list box for Single Group
			/*List<PhysicianAccount> physicianList =null;
			List<PhysicianAccount> physicianSelectedList =null;
			
			if (patient.getGroupId()>0 && patient.getPatientId()>0) {
				physicianList = physicianRep.getAllPatientPhysicianGroupWisePhysicianListNotSelected(patient.getGroupId(),patient.getPatientId());
				physicianSelectedList = physicianRep.getAllPatientPhysicianGroupWisePhysicianListSelected(patient.getGroupId(),patient.getPatientId());
			}else if (patient.getGroupId()>0) {
				physicianList = physicianRep.getAllGroupWisePhysicianList(patient.getGroupId(),0);
			}*/
			
			mv.addObject("groupList", groupList);
			mv.addObject("groupSelectedList", groupSelectedList);
			
			mv.addObject("physicianList", physicianList);
			mv.addObject("physicianSelectedList", physicianSelectedList);
			
			
			mv.addObject("groupList", groupList);
			
			model.addAttribute("patientAccount", patient);
			mv.addObject("physicianFullName", loggedInUser.getDisplayName());
			
			mv.setViewName("patientAccount");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return mv;
	}
	
	
	/**
	 * This method loads the patient account summary data
	 * @param draw
	 * @param start
	 * @param len
	 * @param searchTerm
	 * @param orderColumn
	 * @param orderDir
	 * @param col0Data
	 * @param col0Name
	 * @param col0Search
	 * @param col0Order
	 * @param col0SearchValue
	 * @param col0SearchRegex
	 * @param col1Data
	 * @param col1Name
	 * @param col1Search
	 * @param col1Order
	 * @param col1SearchValue
	 * @param col1SearchRegex
	 * @param col2Data
	 * @param col2Name
	 * @param col2Search
	 * @param col2Order
	 * @param col2SearchValue
	 * @param col2SearchRegex
	 * @param col3Data
	 * @param col3Name
	 * @param col3Search
	 * @param col3Order
	 * @param col3SearchValue
	 * @param col3SearchRegex
	 * @param col4Data
	 * @param col4Name
	 * @param col4Search
	 * @param col4Order
	 * @param col4SearchValue
	 * @param col4SearchRegex
	 * @param col5Data
	 * @param col5Name
	 * @param col5Search
	 * @param col5Order
	 * @param col5SearchValue
	 * @param col5SearchRegex
	 * @param col6Search
	 * @param col6Order
	 * @param col6SearchValue
	 * @param col6SearchRegex
	 * @param col7Search
	 * @param col7Order
	 * @param col7SearchValue
	 * @param col7SearchRegex
	 * @param patientname
	 * @param phyname
	 * @param pid
	 * @param status
	 * @param groupId
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/getPatientAccountSummaryData", method = RequestMethod.POST)
	public @ResponseBody String getPatientAccountSummaryData( @RequestParam("draw") int draw,
			@RequestParam("start") int start, @RequestParam("length") int len,
			@RequestParam("search[value]") String searchTerm,
			@RequestParam("order[0][column]") int orderColumn, @RequestParam("order[0][dir]") String orderDir, @RequestParam("columns[0][data]") String col0Data,
			@RequestParam("columns[0][name]") String col0Name, @RequestParam("columns[0][searchable]") String col0Search, @RequestParam("columns[0][orderable]") String col0Order,
			@RequestParam("columns[0][search][value]") String col0SearchValue, @RequestParam("columns[0][search][regex]") String col0SearchRegex,
			@RequestParam("columns[1][data]") String col1Data, @RequestParam("columns[1][name]") String col1Name, @RequestParam("columns[1][searchable]") String col1Search,
			@RequestParam("columns[1][orderable]") String col1Order, @RequestParam("columns[1][search][value]") String col1SearchValue,
			@RequestParam("columns[1][search][regex]") String col1SearchRegex,
			@RequestParam("columns[2][data]") String col2Data, @RequestParam("columns[2][name]") String col2Name, @RequestParam("columns[2][searchable]") String col2Search,
			@RequestParam("columns[2][orderable]") String col2Order, @RequestParam("columns[2][search][value]") String col2SearchValue, @RequestParam("columns[2][search][regex]") String col2SearchRegex,
			@RequestParam("columns[3][data]") String col3Data, @RequestParam("columns[3][name]") String col3Name, @RequestParam("columns[3][searchable]") String col3Search,
			@RequestParam("columns[3][orderable]") String col3Order, @RequestParam("columns[3][search][value]") String col3SearchValue,
			@RequestParam("columns[3][search][regex]") String col3SearchRegex,
			@RequestParam("columns[4][data]") String col4Data, @RequestParam("columns[4][name]") String col4Name, @RequestParam("columns[4][searchable]") String col4Search,
			@RequestParam("columns[4][orderable]") String col4Order, @RequestParam("columns[4][search][value]") String col4SearchValue,
			@RequestParam("columns[4][search][regex]") String col4SearchRegex, @RequestParam("columns[5][data]") String col5Data, @RequestParam("columns[5][name]") String col5Name,
			@RequestParam("columns[5][searchable]") String col5Search, @RequestParam("columns[5][orderable]") String col5Order,
			@RequestParam("columns[5][search][value]") String col5SearchValue, @RequestParam("columns[5][search][regex]") String col5SearchRegex, 
			@RequestParam("columns[6][searchable]") String col6Search, @RequestParam("columns[6][orderable]") String col6Order,
			@RequestParam("columns[6][search][value]") String col6SearchValue, @RequestParam("columns[6][search][regex]") String col6SearchRegex, 
			@RequestParam("columns[7][searchable]") String col7Search, @RequestParam("columns[7][orderable]") String col7Order,
			@RequestParam("columns[7][search][value]") String col7SearchValue, @RequestParam("columns[7][search][regex]") String col7SearchRegex, 
			@RequestParam("patientname") String patientname, @RequestParam("phyname") String phyname, @RequestParam("pid") String pid,
			@RequestParam("status") String status, @RequestParam("groupId") int groupId, HttpServletRequest request, HttpSession session) {
		
		return patientAccountService.getPatientAccountDataList(Integer.parseInt(pid), draw, start, len, searchTerm, orderColumn, orderDir, 
				patientname, phyname, status, groupId, session);
	}
	/**
	 * This method loads the patient account page through links
	 * @param model
	 * @param request
	 * @param response
	 * @param session
	 * @param id
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "/viewPatientAccount", method = {RequestMethod.POST, RequestMethod.POST } ) 
	public ModelAndView viewPatientAccount(Model model, HttpServletRequest request, HttpServletResponse response, HttpSession session,
			@RequestParam("id") int id, RedirectAttributes redirectAttributes){
		
		ModelAndView mv = new ModelAndView();
		try {
			if (id > 0) {
				PatientAccountForm form = new PatientAccountForm();
				form.setPatientId(id);
				redirectAttributes.addFlashAttribute("form", form);
				mv.setViewName("redirect:editPatientAccount");
			} else {
				mv.setViewName("error500");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return mv;
	}
	
	/**
	 * This method loads the patient account page
	 * @param model
	 * @param request
	 * @param response
	 * @param session
	 * @param form
	 * @param redirectAttributes
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("/editPatientAccount")  
	public ModelAndView editPatientAccount(Model model, HttpServletRequest request, HttpServletResponse response, HttpSession session,
			@ModelAttribute("form") PatientAccountForm form, RedirectAttributes redirectAttributes, BindingResult bindingResult){
		
		ModelAndView mv = new ModelAndView();
		try {
			int id = 0;
			if (form != null && form.getPatientId()>0)
				id = form.getPatientId(); 
			
			LoginForm loggedInUser = (LoginForm) session.getAttribute("loginDetail");
			List<States> statelist = stateService.getAllStates();
			List<PatientRxNotifyProviderType> rxNotifyProviderTypeList = rxNotifyProviderService.getAllPatientRxNotifyProviderType();
			List<PatientRxNotifyType> rxNotifyTypeList = rxNotifyTypeService.getAllPatientRxNotifyType();
			List<PatientSyncStatusType> patientSyncStatusTypeList = patientSyncStatusTypeService.getAllPatientSyncStatusType();
			String prescriptionId = "";
			
			if (id > 0) {
				PatientAccountForm patient = null;
				if (form != null && form.getEmail() != null && !"".equalsIgnoreCase(form.getEmail()))
					patient = form;
				else
					patient = patientAccountService.getPatientData(id, env.getProperty("file.photo_path"));
				if (model.asMap().get("target") != null) {
					String targetObject = model.asMap().get("target").toString();
					prescriptionId = model.asMap().get("PrescriptionId").toString();
					patient.setTargetObject(targetObject);
					patient.setPrescriptionId(prescriptionId);
				} else {
					patient.setTargetObject(form.getTargetObject());
					patient.setPrescriptionId(form.getPrescriptionId());
					prescriptionId = form.getPrescriptionId();
				}
				
				//January 31, 2018 Rohini
				//Put a variable and random number in the image url to refresh the image after uploading
				//example : photoUrl?x=123456
				
				int randomNumber = PharmacyUtil.randomNumberGenerator();
				
				String photoUrl = form.getPhotoFile();
				if(photoUrl==null || photoUrl.length()==0) {
					photoUrl="images/img.jpg";
				}
				photoUrl = photoUrl+"?x="+randomNumber;
				form.setPhotoFile(photoUrl);
				//System.out.println("photoUrl ===  "+photoUrl);
				//January 31, 2018 Rohini
				
				if (PharmacyUtil.userPhysician.equalsIgnoreCase(loggedInUser.getType()) || PharmacyUtil.userPhysicianAssistant.equalsIgnoreCase(loggedInUser.getType())) {
					if (PharmacyUtil.userPhysician.equalsIgnoreCase(loggedInUser.getType())){
						patient.setPhysicianId( loggedInUser.getUserid()  );
						PhysicianGroup phyGroup = phyGroupRepo.findRecordByPhysicianId( loggedInUser.getUserid()  );
						if(patient.getPatientId()==0) {
							patient.setSelectedPhysicianId( loggedInUser.getUserid() +"" );
							
							if (phyGroup != null) {
								patient.setSelectedGroupId(phyGroup.getGroupId()+"");
							}
							
						
						}else{
						
						if (phyGroup != null) {
							patient.setGroupId(phyGroup.getGroupId());
							patient.setGroupName( groupService.getGroupMasterDetails(phyGroup.getGroupId()).getGroupName() );
						}
						
						}
//					PhysicianAccount phyAcc = physicianRep.findOne(loggedInUser.getUserid());
						patient.setPhysicianName(loggedInUser.getDisplayName());
						//patient.setClinicName(clinicRepo.findOne(phyAcc.getClinicId()).getClinicName());
						
						mv.addObject("physicianFullName", loggedInUser.getDisplayName());
						mv.addObject("userid", loggedInUser.getUserid());
					}else {
						patient.setPhysicianId( loggedInUser.getPhysicianAssistantPhysicianId()  );
						PhysicianGroup phyGroup = phyGroupRepo.findRecordByPhysicianId( loggedInUser.getPhysicianAssistantPhysicianId()  );
						
						if(patient.getPatientId()==0) {
							patient.setSelectedPhysicianId( loggedInUser.getPhysicianAssistantPhysicianId() +"" );
							if (phyGroup != null) {
								patient.setSelectedGroupId(phyGroup.getGroupId()+"");
							}
						}else{
							
							if (phyGroup != null) {
								patient.setGroupId(phyGroup.getGroupId());
								patient.setGroupName( groupService.getGroupMasterDetails(phyGroup.getGroupId()).getGroupName() );
							}
							
							}
						
						PhysicianAccount phyAcc = physicianRep.findOne(loggedInUser.getPhysicianAssistantPhysicianId());
						patient.setPhysicianName(phyAcc.getPhysicianName());
						//patient.setClinicName(clinicRepo.findOne(phyAcc.getClinicId()).getClinicName());
						
						mv.addObject("physicianFullName", phyAcc.getPhysicianName());
						mv.addObject("userid", loggedInUser.getPhysicianAssistantPhysicianId());
					}
				}
				else {
					if (PharmacyUtil.userGroupDirector.equalsIgnoreCase(loggedInUser.getType())) {
						GroupDirector gp = groupDirRepo.findOne(loggedInUser.getUserid());
						List<PhysicianAccount> physicianList = physicianService.getApprovedPhysicianByGroupIdAndId(gp.getGroupId(), patient.getPhysicianId());
						mv.addObject("physicianList", physicianList);
						
						patient.setGroupId(loggedInUser.getGroupid());
						patient.setGroupName(loggedInUser.getGroupName());
					
						
					} else {
						List<PhysicianAccount> physicianList = physicianService.getApprovedPhysicianAndId(patient.getPhysicianId());
						mv.addObject("physicianList", physicianList);
					}
				}

				
				/*List<GroupMaster> groupList = groupService.getByGroupMaster(patient.getGroupId());
				mv.addObject("groupList", groupList);	*/
				
				//Multiple Group select list box
				List<GroupMaster> groupList =null;
				List<GroupMaster> groupSelectedList =null;

				if (patient.getPatientId()>0) {
					groupList = groupMasterRepo.getAllPatientGroupWiseListNotSelected(patient.getPatientId());
					groupSelectedList = groupMasterRepo.getAllPatientGroupWiseListSelected(patient.getPatientId());
				}else {
					groupList = groupService.getAllGroupMaster(PharmacyUtil.statusActive);
				}

				//Multiple Physician select list box for Multiple Group
				List<PhysicianAccount> physicianList =null;
				List<PhysicianAccount> physicianSelectedList =null;

				List<Integer> patGroupList = new ArrayList<Integer>();
				String patselectedGroupId=patient.getSelectedGroupId();
				if(patselectedGroupId!=null && patselectedGroupId.length()>0)
				{

					String[] patselectedGroupIdArr=patselectedGroupId.split(",");
					
			
					for (String i : patselectedGroupIdArr) {
						patGroupList.add(Integer.valueOf(i));
					}

				}
				
				if(patGroupList!=null && patGroupList.size()>0){
					if (patient.getSelectedGroupId()!=null && patient.getSelectedGroupId().length()>0 && patient.getPatientId()>0) {
						physicianList = physicianRep.getAllPatientPhysicianGroupWisePhysicianListNotSelected(patGroupList,patient.getPatientId());
						physicianSelectedList = physicianRep.getAllPatientPhysicianGroupWisePhysicianListSelected(patGroupList,patient.getPatientId());
					}else if (patient.getSelectedGroupId()!=null && patient.getSelectedGroupId().length()>0 ) {
						physicianList = physicianRep.getAllGroupWisePhysicianList(patGroupList,0);
					}
				}
				
				/*//Multiple select list box for Single Group
				List<PhysicianAccount> physicianList =null;
				List<PhysicianAccount> physicianSelectedList =null;
				
				if (patient.getGroupId()>0 && patient.getPatientId()>0) {
					physicianList = physicianRep.getAllPatientPhysicianGroupWisePhysicianListNotSelected(patient.getGroupId(),patient.getPatientId());
					physicianSelectedList = physicianRep.getAllPatientPhysicianGroupWisePhysicianListSelected(patient.getGroupId(),patient.getPatientId());
				}else if (patient.getGroupId()>0) {
					physicianList = physicianRep.getAllGroupWisePhysicianList(patient.getGroupId(),0);
				}*/
				
				mv.addObject("groupList", groupList);
				mv.addObject("groupSelectedList", groupSelectedList);
				
				mv.addObject("physicianList", physicianList);
				mv.addObject("physicianSelectedList", physicianSelectedList);
				
				model.addAttribute("patientAccount", patient);
				
				List<DocumentFileList> uploadedDocList = patientAccountService.getUploadedDocuments(id);
				mv.addObject("uploadedDocList", uploadedDocList);
				
				mv.addObject("userType", loggedInUser.getType());
				model.addAttribute("message", model.asMap().get("message"));
				model.addAttribute("saveStatus", model.asMap().get("saveStatus"));
				
				mv.addObject("stateList", statelist);
				mv.addObject("prescriptionId", prescriptionId);
				
				mv.addObject("rxNotifyProviderTypeList", rxNotifyProviderTypeList);
				mv.addObject("rxNotifyTypeList", rxNotifyTypeList);
				mv.addObject("patientSyncStatusTypeList", patientSyncStatusTypeList);
				List<CardTypeMaster> cardList = cardTypeMasterRepo.findAll();
				mv.addObject("cardList", cardList);
				
				if(model.asMap().get("profileclick")!=null) {
					mv.setViewName("patientProfile");
				} else if(model.asMap().get("profileclick")!=null) {
					mv.setViewName("patientLeftProfile");
				} else {
					mv.setViewName("patientAccount");	
				}
			} else {
				mv.setViewName("redirect:patientAccount");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return mv;
	}
	
	/**
	 *  This method navigates to the patient account related prescription
	 * @param model
	 * @param form
	 * @param request
	 * @param response
	 * @param session
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "/backPrescription", method = RequestMethod.POST)
	public ModelAndView backToPrescription(Model model, @ModelAttribute("patientAccount") PatientAccountForm form, 
			HttpServletRequest request, HttpServletResponse response, HttpSession session, RedirectAttributes redirectAttributes) {
		ModelAndView mv = new ModelAndView();
		
		try {
			if (form.getPrescriptionId() != null && Integer.parseInt(form.getPrescriptionId()) > 0) {
				String PrescriptionId = form.getPrescriptionId();
				PrescriptionForm prescriptionForm = new PrescriptionForm();
				prescriptionForm.setPrescriptionId(Integer.parseInt(PrescriptionId));
				redirectAttributes.addFlashAttribute("form", prescriptionForm);
				mv.setViewName("redirect:/prescription/editPrescription");
			} else if (form.getPatientId() > 0) {
				redirectAttributes.addFlashAttribute("form", form);
				mv.setViewName("redirect:editPatientAccount");
			} else {
				mv.setViewName("patientProfile");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		
		return mv;
	}
	/**
	 * This method deletes the patient account profile data
	 * @param model
	 * @param session
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping("/patientProfile")
	public ModelAndView loadPatientProfile(Model model, HttpSession session, RedirectAttributes redirectAttributes) {
		ModelAndView mv = new ModelAndView();
		try {
			LoginForm loggedInUser = (LoginForm) session.getAttribute("loginDetail");
			model.addAttribute("message", model.asMap().get("message"));
			model.addAttribute("saveStatus", model.asMap().get("saveStatus"));
			
			if (loggedInUser != null) {
				PatientAccountForm form = new PatientAccountForm();
				form.setPatientId(loggedInUser.getUserid());
				redirectAttributes.addFlashAttribute("form", form);
				redirectAttributes.addFlashAttribute("profileclick", "true");
				mv.setViewName("redirect:editPatientAccount");
			} else {
				// Change to login page
				mv.setViewName("error500");
			}
		} catch(Exception e) {
			e.printStackTrace();
			mv.setViewName("error500");
		}
		
		return mv;
	}
	
	/**
	 * This method deletes the patient account profile data
	 * @param model
	 * @param session
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping("/patientLeftProfile")
	public ModelAndView loadPatientLeftProfile(Model model, HttpSession session, RedirectAttributes redirectAttributes) {
		ModelAndView mv = new ModelAndView();
		try {
			LoginForm loggedInUser = (LoginForm) session.getAttribute("loginDetail");
			model.addAttribute("message", model.asMap().get("message"));
			model.addAttribute("saveStatus", model.asMap().get("saveStatus"));
			
			if (loggedInUser != null) {
				PatientAccountForm form = new PatientAccountForm();
				form.setPatientId(loggedInUser.getUserid());
				redirectAttributes.addFlashAttribute("form", form);
				redirectAttributes.addFlashAttribute("profileleftclick", "true");
				mv.setViewName("redirect:editPatientAccount");
			} else {
				// Change to login page
				mv.setViewName("error500");
			}
		} catch(Exception e) {
			e.printStackTrace();
			mv.setViewName("error500");
		}
		
		return mv;
	}
	
	/**
	 * This method returns the states list
	 * @param searchName
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getStatesList", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody List<States> getStatesList(@RequestParam String searchName, HttpServletResponse response) {
		return simulateSearchResult(searchName);
	}
	
	/**
	 * This method returns the searched states list 
	 * @param searchName
	 * @return
	 */
	private List<States> simulateSearchResult(String searchName) {
		
		List<States> result = new ArrayList<States>();
		
		try {
			List<States> statelist = stateService.getAllStates();
			// iterate a list and filter by tagName
			for (States obj : statelist) {
				if (obj.getStateName().contains(searchName) || obj.getStateCode().contains(searchName)) {
					result.add(obj);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}
	 
		/**
		 * This method stores the patient related documents
		 * @param request
		 * @param response
		 * @param session
		 * @param uploadDocFile
		 * @param patientId
		 * @return
		 */
		@RequestMapping(value = "/patientDocFileUpload", method = RequestMethod.POST)
		public @ResponseBody String saveDocFiles(HttpServletRequest request, HttpServletResponse response, HttpSession session,
				@RequestParam("docFile") CommonsMultipartFile uploadDocFile, @RequestParam("patientId") String patientId){
		
			try {
				//System.out.println("uploadDocFile --> " + uploadDocFile.getOriginalFilename());
				//System.out.println("physicianId --> " + patientId);
				
				LoginForm loggedInUser = (LoginForm) session.getAttribute("loginDetail");
				
				String rootFilePath=env.getProperty("file.root_folder_path");
				boolean isSaved = patientAccountService.savePatientDocFiles(uploadDocFile, Integer.parseInt(patientId), rootFilePath, loggedInUser);
				if (isSaved) {
					//System.out.println("Document uploaded successfully");
				} else {
					//System.out.println("Fail to upload Document");
				}
				
				List<DocumentFileList> uploadedDocList = patientAccountService.getUploadedDocuments( Integer.parseInt(patientId) );
				Gson gson = new GsonBuilder().setPrettyPrinting().create();
				String json2= gson.toJson(uploadedDocList);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return null;
		}
		
		/**
		 * This method deletes the patient uploaded documents
		 * @param request
		 * @param response
		 * @param session
		 * @param fileId
		 * @param patientId
		 * @return
		 */
		@RequestMapping(value = "/patientDocFileDelete", method = RequestMethod.POST)
		public @ResponseBody String deleteDocFiles(HttpServletRequest request, HttpServletResponse response, HttpSession session,
				 @RequestParam("fileId") String fileId, @RequestParam("patientId") String patientId){
			try {
				//System.out.println("deleteDocFiles ---> " + fileId);
				boolean isDeleted = patientAccountService.deletePatientDocFiles( Integer.parseInt(fileId) );
				if (isDeleted) {
					//System.out.println("Document deleted successfully");
				} else {
					//System.out.println("Fail to delete Document");
				}
				List<DocumentFileList> uploadedDocList = patientAccountService.getUploadedDocuments( Integer.parseInt(patientId) );
				Gson gson = new GsonBuilder().setPrettyPrinting().create();
				String json2 = gson.toJson(uploadedDocList);
				
				return json2;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		
		/**
		 * This method downloads the patient uploaded documents
		 * @param request
		 * @param response
		 * @param session
		 * @param fileId
		 * @param patientId
		 */
		@RequestMapping(value = "/patientDocFileDownload", method = RequestMethod.GET)
		public void downloadDocFiles(HttpServletRequest request, HttpServletResponse response, HttpSession session,
				 @RequestParam("f") String fileId, @RequestParam("p") String patientId){
			try {
				//System.out.println("downloadDocFiles ---> " + fileId);
				String fileName = patientAccountService.getPatientDocFileName( Integer.parseInt(fileId) );
				String downloadName = patientAccountService.getPatientDownloadDocFileName( Integer.parseInt(fileId) );
				PharmacyUtil.downloadFile(fileName, downloadName, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	

		
		/**
		 * This method loads the patient uploaded document list data
		 * @param draw
		 * @param start
		 * @param len
		 * @param searchTerm
		 * @param orderColumn
		 * @param orderDir
		 * @param col0Data
		 * @param col0Name
		 * @param col0Search
		 * @param col0Order
		 * @param col0SearchValue
		 * @param col0SearchRegex
		 * @param col1Data
		 * @param col1Name
		 * @param col1Search
		 * @param col1Order
		 * @param col1SearchValue
		 * @param col1SearchRegex
		 * @param col2Data
		 * @param col2Name
		 * @param col2Search
		 * @param col2Order
		 * @param col2SearchValue
		 * @param col2SearchRegex
		 * @param col3Data
		 * @param col3Name
		 * @param col3Search
		 * @param col3Order
		 * @param col3SearchValue
		 * @param col3SearchRegex
		 * @param col4Data
		 * @param col4Name
		 * @param col4Search
		 * @param col4Order
		 * @param col4SearchValue
		 * @param col4SearchRegex
		 * @param physicianId
		 * @param request
		 * @return
		 */
		@RequestMapping(value = "/getPatientDocumentsData", method = RequestMethod.POST)
		public @ResponseBody String getPatientDocumentData(@RequestParam("draw") int draw,
				@RequestParam("start") int start, @RequestParam("length") int len,
				@RequestParam("search[value]") String searchTerm,
				@RequestParam("order[0][column]") int orderColumn, @RequestParam("order[0][dir]") String orderDir, @RequestParam("columns[0][data]") String col0Data,
				@RequestParam("columns[0][name]") String col0Name, @RequestParam("columns[0][searchable]") String col0Search, @RequestParam("columns[0][orderable]") String col0Order,
				@RequestParam("columns[0][search][value]") String col0SearchValue, @RequestParam("columns[0][search][regex]") String col0SearchRegex,
				@RequestParam("columns[1][data]") String col1Data, @RequestParam("columns[1][name]") String col1Name, @RequestParam("columns[1][searchable]") String col1Search,
				@RequestParam("columns[1][orderable]") String col1Order, @RequestParam("columns[1][search][value]") String col1SearchValue,
				@RequestParam("columns[1][search][regex]") String col1SearchRegex,
				@RequestParam("columns[2][data]") String col2Data, @RequestParam("columns[2][name]") String col2Name, @RequestParam("columns[2][searchable]") String col2Search,
				@RequestParam("columns[2][orderable]") String col2Order, @RequestParam("columns[2][search][value]") String col2SearchValue, @RequestParam("columns[2][search][regex]") String col2SearchRegex,
				@RequestParam("columns[3][data]") String col3Data, @RequestParam("columns[3][name]") String col3Name, @RequestParam("columns[3][searchable]") String col3Search,
				@RequestParam("columns[3][orderable]") String col3Order, @RequestParam("columns[3][search][value]") String col3SearchValue,
				@RequestParam("columns[3][search][regex]") String col3SearchRegex,
				@RequestParam("columns[4][data]") String col4Data, @RequestParam("columns[4][name]") String col4Name, @RequestParam("columns[4][searchable]") String col4Search,
				@RequestParam("columns[4][orderable]") String col4Order, @RequestParam("columns[4][search][value]") String col4SearchValue,
				@RequestParam("columns[4][search][regex]") String col4SearchRegex, @RequestParam("p") String physicianId,
				HttpServletRequest request) {
			
			return patientAccountService.getPatientDocumentDataList(draw, start, len, searchTerm, orderColumn, orderDir, physicianId );
		}
		/**
		 * This method returns the group wise physicians
		 * @param request
		 * @param response
		 * @param session
		 * @param groupId
		 * @param physicianId
		 * @return
		 */
		@RequestMapping(value="/fetchGroupWisePhysicians", method=RequestMethod.POST)
		public @ResponseBody List<PhysicianAccount> getGroupWisePhysicianDataById(HttpServletRequest request, HttpServletResponse response, HttpSession session,
				 @RequestParam("groupId") String groupId,@RequestParam("physicianId") int physicianId) {
			List<PhysicianAccount> physicianList=null;
			if (groupId!=null && groupId.length()>0) {
				List<Integer> patGroupList = new ArrayList<Integer>();
				String patselectedGroupId=groupId;
				if(patselectedGroupId!=null && patselectedGroupId.length()>0)
				{

					String[] patselectedGroupIdArr=patselectedGroupId.split(",");
					
			
					for (String i : patselectedGroupIdArr) {
						patGroupList.add(Integer.valueOf(i));
					}

				}
			
				physicianList = physicianRep.getAllGroupWisePhysicianList(patGroupList,physicianId);
			}
			return physicianList;
		}
		
		/**
		 * This method loads the date list
		 * @return
		 */
		@ModelAttribute("dobDateList")
		public Map<String, String> getDobDateList() {
			Map<String, String> dateList = new LinkedHashMap<String, String>();
			
			for (int i=1;i<32;i++) {
				String str = "00"+i;
				dateList.put(str.substring(Math.max(str.length() - 2, 0)), i+"");
			}
			return dateList;
		}
		/**
		 *  This method loads the month list
		 * @return
		 */
		@ModelAttribute("dobMonthList")
		public Map<String, String> getDobMonthList() {
			Map<String, String> monthList = new LinkedHashMap<String, String>();
			
			/*for (int i=1;i<13;i++) {
				String str = "00"+i;
				monthList.put(str.substring(Math.max(str.length() - 2, 0)), i+"");
			}*/
			monthList.put("01", "Janaury");
			monthList.put("02", "February");
			monthList.put("03", "March");
			monthList.put("04", "April");
			monthList.put("05", "May");
			monthList.put("06", "June");
			monthList.put("07", "July");
			monthList.put("08", "August");
			monthList.put("09", "September");
			monthList.put("10", "October");
			monthList.put("11", "November");
			monthList.put("12", "December");
			
			return monthList;
		}
		/**
		 *  This method loads the year list
		 * @return
		 */
		@ModelAttribute("dobYearList")
		public Map<String, String> getDobYearList() {
			Map<String, String> yearList = new LinkedHashMap<String, String>();
			
			int currentYear = Calendar.getInstance().get(Calendar.YEAR);
			
			for (int i=1900;i<=currentYear;i++) {
				yearList.put(i+"", i+"");
			}
			return yearList;
		}		
		/**
		 * This method returns the group wise physician list
		 * @param request
		 * @param response
		 * @param session
		 * @param groupId
		 * @param physicianId
		 * @return
		 */
		@RequestMapping(value="/fetchMultipleGroupWisePhysicians", method=RequestMethod.POST)
		public @ResponseBody List<PhysicianAccount> fetchMultipleGroupWisePhysicians(HttpServletRequest request, HttpServletResponse response, HttpSession session,
				 @RequestParam("groupId") String groupId,@RequestParam("physicianId") int physicianId) {
			List<PhysicianAccount> physicianList=null;
			if (groupId!=null && groupId.length()>0) {
				List<Integer> patGroupList = new ArrayList<Integer>();
				String patselectedGroupId=groupId;
				if(patselectedGroupId!=null && patselectedGroupId.length()>0)
				{

					String[] patselectedGroupIdArr=patselectedGroupId.split(",");
					
			
					for (String i : patselectedGroupIdArr) {
						patGroupList.add(Integer.valueOf(i));
					}

				}
				physicianList = physicianRep.getAllGroupWisePhysicianList(patGroupList,physicianId);
			}
			return physicianList;
		}
		/**
		 * This method sends the patient account credentail email
		 * @param form
		 * @param bindingResult
		 * @param model
		 * @param request
		 * @param response
		 * @param session
		 * @param redirectAttributes
		 * @return
		 */
		@RequestMapping(value = "/sendPatientAccountCredentialsEmail", method = RequestMethod.POST)
		public ModelAndView sendPatientAccountCredentialsEmail(@ModelAttribute("form") PatientAccountForm form,  BindingResult bindingResult, Model model,
				HttpServletRequest request, HttpServletResponse response, HttpSession session, RedirectAttributes redirectAttributes) {
			
			System.out.print("sendPatientAccountCredentialsEmail");
		ModelAndView mv = new ModelAndView();
		
		LoginForm loggedInUser = (LoginForm) session.getAttribute("loginDetail");

			if (form.getPatientId() > 0){
				
				boolean isMailSent = patientAccountService.sendPatientAccountMail(form, loggedInUser, session, request,env);
				
				if (isMailSent) {
					redirectAttributes.addFlashAttribute("saveStatus", "true");
					redirectAttributes.addFlashAttribute("message", "User Account Credentials sent successfully!!");
				} else {
					redirectAttributes.addFlashAttribute("saveStatus", "false");
					redirectAttributes.addFlashAttribute("message", "Failed to send User Account Credentials!!");
				}
				
				
				redirectAttributes.addFlashAttribute("form", form);
				mv.setViewName("redirect:editPatientAccount");
			}else {
				redirectAttributes.addFlashAttribute("form", form);
				mv.setViewName("redirect:patientAccount");
			}	
			return mv;
		}
	
}
