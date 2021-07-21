package com.pharma.core.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
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
import org.springframework.web.servlet.support.RequestContextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pharma.core.formBean.DocumentFileList;
import com.pharma.core.formBean.LoginForm;
import com.pharma.core.formBean.pharmacygroup.GroupMasterForm;
import com.pharma.core.formBean.physician.PhysicianProfile;
import com.pharma.core.formBean.physician.PhysicianRegistration;
import com.pharma.core.formBean.prescription.PrescriptionForm;
import com.pharma.core.model.CardTypeMaster;
import com.pharma.core.model.PrescriberTypeMaster;
import com.pharma.core.model.States;
import com.pharma.core.model.clinic.Clinic;
import com.pharma.core.model.pharmacygroup.GroupDirector;
import com.pharma.core.model.pharmacygroup.GroupMaster;
import com.pharma.core.model.physician.PhysicianAccount;
import com.pharma.core.model.physician.PhysicianGroup;
import com.pharma.core.model.physician.PhysicianProfileInfo;
import com.pharma.core.pharmaservices.PrescriberTypeMasterService;
import com.pharma.core.pharmaservices.StatesService;
import com.pharma.core.pharmaservices.clinic.ClinicService;
import com.pharma.core.pharmaservices.pharmacygroupservices.GroupMasterService;
import com.pharma.core.pharmaservices.pharmacygroupservices.GroupMasterServiceImpl;
import com.pharma.core.pharmaservices.physicianservices.PhysicianAccountService;
import com.pharma.core.pharmaservices.physicianservices.PhysicianAccountServiceImpl;
import com.pharma.core.pharmaservices.physicianservices.PhysicianGroupService;
import com.pharma.core.pharmaservices.prescription.PrescriptionService;
import com.pharma.core.repository.CardTypeMasterRepository;
import com.pharma.core.repository.clinic.ClinicRepository;
import com.pharma.core.repository.pharmacygroup.GroupDirectorAccountRespository;
import com.pharma.core.repository.pharmacygroup.GroupMasterRespository;
import com.pharma.core.repository.physician.PhysicianAccountRespository;
import com.pharma.core.repository.physician.PhysicianClinicRepository;
import com.pharma.core.repository.physician.PhysicianGroupRepository;
import com.pharma.core.repository.physician.PhysicianProfileRespository;
import com.pharma.core.util.PharmacyUtil;
/**
 * This class is a controller to save and load the physician account details
 */
@Controller
@PropertySource("classpath:physicianAccount.properties")
@PropertySource("classpath:hipaa.properties") 
public class PhysicianAccountController extends PhysicianBaseController{
	
	@Autowired
	PhysicianAccountService phyService;
	
	@Autowired
	PhysicianAccountServiceImpl phyServiceImpl;
	
	@Autowired
	PhysicianAccountRespository phyRepo;
	
	@Autowired
	PrescriberTypeMasterService prescriberMasterService;
	
	@Autowired
	StatesService statesService;
	
	@Autowired
	CardTypeMasterRepository cardTypeMasterRepo;
	
	@Autowired
	GroupMasterService groupService;
	
	@Autowired
	GroupMasterRespository groupMasterRepo;
	
	@Autowired
	GroupDirectorAccountRespository groupDirRepo;
	
	@Autowired
	PhysicianProfileRespository phyProfileRepo;
	
	@Autowired
	PhysicianGroupService phyGroupService;
	
	@Autowired
	private Environment env;
	
	@Autowired
	ClinicService clinicService;
	
	@Autowired
	ClinicRepository clinicRepo;
	
	@Autowired
	PrescriptionService prescriptionService;
	
	@Autowired
	PhysicianClinicRepository phyClinicRepo;

	@Autowired
	GroupMasterServiceImpl groupMasterService;
	
	@Autowired
	PhysicianGroupRepository phyGroupRepo;
	/**
	 * This method loads the physician registration page
	 * @return
	 */
	@RequestMapping("/PhysicianRegistration")
	public ModelAndView loadPhysicianRegistration() {
		ModelAndView mv = new ModelAndView();
		
		try {
			PhysicianRegistration acc = new PhysicianRegistration();
			List<States> statesList = statesService.getAllStates();
			mv.addObject("physicianAccount", acc);
			mv.addObject("usStates", statesList);
			mv.setViewName("phyRegPage");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return mv;
	}
	/**
	 *  This method save and loads the physician registration page
	 * @param form
	 * @param model
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/SavePhysicianRegistration", method = RequestMethod.POST)
	public ModelAndView saveNewPhysician(@ModelAttribute("physicianAccount") PhysicianRegistration form, Model model,
			HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		
		ModelAndView mv = new ModelAndView();
		try {
			List<PhysicianAccount> exObj = phyRepo.findByEmail(form.getEmail());
			
			if (exObj.size() == 0) {
				PhysicianAccount acc = phyService.register(form, session, request);
				
				LoginForm login = new LoginForm();
				login.setDisplayName(acc.getPhysicianName());
				login.setPhotoUrl(null);
				login.setLogoUrl(null);
				login.setStatus(acc.getStatus());
				login.setType(PharmacyUtil.userPhysician);
				login.setUserid(acc.getId());
				login.setUserName(acc.getEmail());
				
				session = request.getSession();
				session.setAttribute("loginDetail", login);

				mv.setViewName("redirect:PhysicianProfile");
			} else {
				List<States> statesList = statesService.getAllStates();
				mv.addObject("usStates", statesList);
				model.addAttribute("message",env.getProperty("error.emailAlreadyExists") );
				mv.setViewName("phyRegPage");
			}
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("message", env.getProperty("error.savingDB"));
			mv.setViewName("error500");
		}
		
		return mv;
	}	
	
	/**
	 * This method save and loads the physician profile data
	 * @param model
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping("/PhysicianProfile")
	public ModelAndView loadPhysicianProfile(Model model, HttpSession session,HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		try {
			LoginForm loggedInUser = (LoginForm) session.getAttribute("loginDetail");
			model.addAttribute("message", model.asMap().get("message"));
			
		
			String SaveStepProcess = "";
			Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
			  if (inputFlashMap != null) {
				  if(inputFlashMap.get("SaveStepProcess")!=null){
				    SaveStepProcess = (String) inputFlashMap.get("SaveStepProcess");
				    //System.out.println("22222222222222 ==="+SaveStepProcess);
				  }
			  }
			  
			
			
			if (loggedInUser != null) {
				List<States> statesList = statesService.getAllStates();
				List<PrescriberTypeMaster> prescriberMasterList = prescriberMasterService.getMasterList();
				List<CardTypeMaster> cardList = cardTypeMasterRepo.findAll();
				List<GroupMaster> groupList = groupService.getAllGroupMaster(PharmacyUtil.statusActive);
				
				PhysicianProfile profile = phyService.getPhysicianData(loggedInUser.getUserid(), env.getProperty("file.photo_path"), env.getProperty("file.logo_path"));
				if ("".equalsIgnoreCase(profile.getSendMailPermission()))
					profile.setSendMailPermission("Yes");
				
				int newGroupId=0;
				List<GroupMaster> groupMastList= groupMasterRepo.findByNewGroup();
				if (groupMastList!=null && groupMastList.size() > 0) {
					
					for (GroupMaster g: groupMastList) {
						newGroupId=g.getId();
						profile.setNewGroupId(newGroupId);
					}
				}
				//Assigning Default New Group when the group is not allotted
				if (newGroupId>0 && profile.getGroupId()==0) {
					

					/*
					 * Saving records in Physician Group table
					 */
					try {
						PhysicianGroup phyGrp= new PhysicianGroup();
						phyGrp.setPhysicianId(profile.getPhysicianId());
						phyGrp.setCreatedBy(profile.getPhysicianId());
						phyGrp.setCreatedDate(PharmacyUtil.getCurrentTimeStamp());
						phyGrp.setCreatedUserType(PharmacyUtil.userPhysician);
						phyGrp.setGroupId(newGroupId);
						phyGrp.setStatus(PharmacyUtil.statusActive);

						phyGrp.setUpdatedBy(profile.getPhysicianId());
						phyGrp.setUpdatedDate(PharmacyUtil.getCurrentTimeStamp());
						phyGrp.setUpdatedUserType(PharmacyUtil.userPhysician);

						phyGroupRepo.save(phyGrp);
					} catch(Exception e) {
						e.printStackTrace();
					}
					
					profile.setGroupId(newGroupId);
					
					if (newGroupId > 0)
						profile.setGroupName(groupService.getGroupMasterDetails(newGroupId).getGroupName());
					else
						profile.setGroupName("");
				}

				
				
				List<PhysicianAccount> otherPhysicianList = phyRepo.getAllOtherPhysicians(PharmacyUtil.statusApproved,profile.getPhysicianId());
				mv.addObject("otherPhysicianList", otherPhysicianList);
				mv.addObject("physicianAccount", profile);
				mv.addObject("usStates", statesList);
				mv.addObject("perscriberList", prescriberMasterList);
				mv.addObject("cardList", cardList);
				mv.addObject("userType", loggedInUser.getType());
				mv.addObject("groupList", groupList);
				
				/*if (profile.getGroupId()>0) {
					clinicList = clinicRepo.getAllGroupWiseClinicList(profile.getGroupId(),profile.getClinicId());
				}
				
				mv.addObject("clinicList", clinicList);*/
				
				

				//January 31, 2018 Rohini
				//Put a variable and random number in the image url to refresh the image after uploading
				//example : photoUrl?x=123456
				
				int randomNumber = PharmacyUtil.randomNumberGenerator();
				
				String photoUrl = profile.getPhotoFile();
				if(photoUrl==null || photoUrl.length()==0)
				{
					
					photoUrl="images/img.jpg";
				}
				
				photoUrl = photoUrl+"?x="+randomNumber;
				profile.setPhotoFile(photoUrl);
				//System.out.println("photoUrl ===  "+photoUrl);
				
				randomNumber = PharmacyUtil.randomNumberGenerator();
				
				String logoUrl = profile.getLogoFile();
				
				if(logoUrl==null || logoUrl.length()==0)
				{
					
					logoUrl="images/default_logo.jpg";
				}
				
				logoUrl = logoUrl+"?x="+randomNumber;
				profile.setLogoFile(logoUrl);
				//System.out.println("logoUrl ===  "+logoUrl);
				
				//January 31, 2018 Rohini
				
				//Multiple select lit box
				List<Clinic> clinicList =null;
				List<Clinic> clinicSelectedList =null;
				List<Clinic> groupClinicStatusList =null;
				
				if (profile.getGroupId()>0 && profile.getPhysicianId()>0) {
					clinicList = clinicRepo.getAllPhysicianGroupWiseClinicListNotSelected(profile.getGroupId(),profile.getPhysicianId());
					clinicSelectedList = clinicRepo.getAllPhysicianGroupWiseClinicListSelected(profile.getGroupId(),profile.getPhysicianId());
				}else if (profile.getGroupId()>0) {
					clinicList = clinicRepo.getAllGroupWiseClinicList(profile.getGroupId(),profile.getClinicId());
				}
				if (profile.getGroupId()>0)
					groupClinicStatusList=clinicRepo.getGroupWiseAllClinicList(profile.getGroupId());
				
				mv.addObject("groupClinicStatusList", groupClinicStatusList);
				mv.addObject("clinicList", clinicList);
				mv.addObject("clinicSelectedList", clinicSelectedList);
				
				List<DocumentFileList> uploadedDocList = phyService.getUploadedDocuments(profile.getPhysicianId());
				mv.addObject("uploadedDocList", uploadedDocList);
				mv.addObject("prescriptionId", "");
				
				try {
					String rootFilePath= PharmacyUtil.getRootFolderForPhysicianPDF(session, env);
					String folderName= rootFilePath + File.separator + profile.getPhysicianId();

					String pdfFileName=folderName + File.separator + "PhysicianPdf_"+profile.getPhysicianId()+"_ESigned.pdf";
					if(new File(pdfFileName).exists())
					{
						profile.setEignedPdf(true);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				if(loggedInUser.getType().equalsIgnoreCase("Physician"))
				{
					if(profile!=null)
					{
						if(profile.getStatus().equalsIgnoreCase(PharmacyUtil.statusNew) || profile.getStatus().equalsIgnoreCase(PharmacyUtil.statusProfileCompleted))
						{
							
							
								String ProfileESigned="";
								if(session.getAttribute("ProfileESigned")!=null)
								{
									ProfileESigned=session.getAttribute("ProfileESigned")+"";
									//System.out.println("ProfileESigned ======"+session.getAttribute("ProfileESigned"));
									session.setAttribute("ProfileESigned",null);
								}
	
	
								if(ProfileESigned.equalsIgnoreCase("ProfileESigned"))
								{
									profile.setProfilestep(3);
									
								}else
								{
									if(profile.getProfilestep()==3 && profile.isEignedPdf())
									{
										profile.setProfilestep(0);
									}
									
								}
								
								if(SaveStepProcess!=null && SaveStepProcess.length()>0)
								{
									profile.setProfilestep(Integer.parseInt(SaveStepProcess));
								}
							
							mv.setViewName("physicianprofile");
						}
						else
							mv.setViewName("physicianaccount");
					}
				}
				else
					mv.setViewName("physicianaccount");
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
	 *  This method save and loads the physician profile page
	 * @param form
	 * @param model
	 * @param request
	 * @param response
	 * @param session
	 * @param uploadPhotoFile
	 * @param uploadLogoFile
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "/SavePhysicianProfile", method = RequestMethod.POST)
	public ModelAndView savePhysicianProfile(@ModelAttribute("physicianAccount") PhysicianProfile form, Model model,
			HttpServletRequest request, HttpServletResponse response, HttpSession session, 
			@RequestParam CommonsMultipartFile uploadPhotoFile, @RequestParam CommonsMultipartFile uploadLogoFile, RedirectAttributes redirectAttributes	) {
		
		ModelAndView mv = new ModelAndView();
		LoginForm loggedInUser = (LoginForm) session.getAttribute("loginDetail");
		boolean isDeaDuplicate = false;
		boolean isLicenseDuplicate = false;
		try {
			//System.out.println("clinicid ==========="+form.getClinicId());
			//System.out.println("selected clinicid ==========="+form.getSelectedClinicId());
			List<PhysicianAccount> exObj = null;
			if (form.getPhysicianId()  > 0) {
				exObj = phyRepo.findByEmailAndIdNot(form.getEmail(), form.getPhysicianId());
			} else {
				exObj = phyRepo.findByEmail(form.getEmail());
			}
			List<PhysicianProfileInfo> deaObj = null;
			if (exObj.size() == 0 && (form.getDea() != null && !"".equalsIgnoreCase(form.getDea()))) {
				if (form.getPhysicianId() > 0) {
					deaObj = phyProfileRepo.findByDeaWithGroup(form.getPhysicianId(), form.getDea(), form.getGroupId());
				} else {
					deaObj = phyProfileRepo.findByDeaWithGroup(form.getDea(), form.getGroupId());
				}
				if (deaObj!=null && deaObj.size() > 0)
					isDeaDuplicate = true;
			}
			List<PhysicianProfileInfo> stateLicenseObj = null;
			if (form.getStateLicense() != null && !"".equalsIgnoreCase(form.getStateLicense())) {
				if (exObj.size() == 0 && (deaObj == null || deaObj.size() == 0)) {
					if (form.getPhysicianId()  > 0) {
						stateLicenseObj = phyProfileRepo.findByStateLicenseAndPhysicianIdNot(form.getStateLicense(), form.getState(), form.getPhysicianId());
					} else {
						stateLicenseObj = phyProfileRepo.findByStateLicense(form.getStateLicense(), form.getState());
					}
					if (stateLicenseObj != null && stateLicenseObj.size() > 0)
						isLicenseDuplicate = true;
				}
			}
		
			if (exObj.size() == 0 && (deaObj == null || deaObj.size() == 0) && (stateLicenseObj == null || stateLicenseObj.size() == 0) ){
				String rootFilePath1= PharmacyUtil.getRootFolderForPhoto(session, env);
				String rootFilePath2= PharmacyUtil.getRootFolderForLogo(session, env);
				
				/**
				 *  Added on Feb 17,2018 by shalini
				 *  Changing the Group of Physician or Deactivating a Physician
					a Physician is Denied or his Group is changed what actions
					If Pysician is Denied
					Alert: Do you wish to re-assign all the Patients of thisPysician to a different Physician?
					Display Physician: <Physicians of that Group only>
					 Yes and No buttons in the Alert
					
					If they Click No, the Show the Alert:
					"All Patients of this Physician will be DeActivated(Denied). If you wish to re-assign them to different Physicians go to the Patient lists and re-assign"
				 */
				String reAssignPhysician="",deactivatePhysician="";
				
				if(request.getParameter("reAssignPhysician")!=null)
					reAssignPhysician=request.getParameter("reAssignPhysician")+"";
				
				if(request.getParameter("deactivatePhysician")!=null)
					deactivatePhysician=request.getParameter("deactivatePhysician")+"";
				
				//System.out.println(reAssignPhysician+"======"+deactivatePhysician);
				//Re-assign new Physician and deactivate the current Physician
				if(reAssignPhysician.length()>0 || deactivatePhysician.length()>0)
				{
					if(reAssignPhysician.length()>0 && reAssignPhysician.equalsIgnoreCase("true"))
					{
						int otherPhysicianId = form.getOtherPhysicianId();
						if (otherPhysicianId > 0) {
							//reAssignPhysician
							phyServiceImpl.reAssignPhysicianId( otherPhysicianId,  form.getPhysicianId(),loggedInUser);
							
						}
					}
					else if(deactivatePhysician.length()>0 && deactivatePhysician.equalsIgnoreCase("true"))
					{
						
						//deactivate Physician
						phyServiceImpl.deActivatePhysician( form.getPhysicianId(),loggedInUser);
					}
				}
				boolean newRecFlag=false,newClinicFlag=false;
				PhysicianAccount acc = phyService.savePhysicianAccountProfile(form, uploadPhotoFile, null, rootFilePath1, loggedInUser, session, request,uploadLogoFile,rootFilePath2);
				if (acc != null) {
					
					if(form.getPhysicianId()==0)
						newRecFlag=true;
					
					if(form.getNewClinicFlag()!=null && form.getNewClinicFlag().equalsIgnoreCase("true"))
					{
						int groupId=0;
						PhysicianGroup phyGrp =  phyGroupRepo.findRecordByPhysicianId(acc.getId());
						if (phyGrp != null) {
							groupId=phyGrp.getGroupId();
						}
						
						phyService.saveNewPhysicianClinic(acc.getId(),loggedInUser,"false",groupId);
						newClinicFlag=true;
						
					}
					
					
					if (form.getTargetObject() != null && "prescription".equalsIgnoreCase(form.getTargetObject())) {
						String PrescriptionId = form.getPrescriptionId();
						PrescriptionForm prescriptionForm = new PrescriptionForm();
						prescriptionForm.setPrescriptionId(Integer.parseInt(PrescriptionId));
						
						prescriptionService.updatePhysicianData(Integer.parseInt(PrescriptionId), acc, loggedInUser);
						
						/*redirectAttributes.addFlashAttribute("form", prescriptionForm);
						mv.setViewName("redirect:/prescription/editPrescription");*/
						form = new PhysicianProfile();
						form.setPhysicianId(acc.getId());
						if (!"".equalsIgnoreCase(PrescriptionId) && Integer.parseInt(PrescriptionId) > 0) {
							form.setTargetObject("prescription");
							form.setPrescriptionId(PrescriptionId);
						}

						redirectAttributes.addFlashAttribute("physicianAccount", form);
						mv.setViewName("redirect:editPhysicianProfile");
					} else {
						if (PharmacyUtil.userAdmin.equalsIgnoreCase(loggedInUser.getType()) 
								|| PharmacyUtil.userSuperAdmin.equalsIgnoreCase(loggedInUser.getType()) 
								|| PharmacyUtil.userGroupDirector.equalsIgnoreCase(loggedInUser.getType()) )
						{
							String PrescriptionId = form.getPrescriptionId();
							
							form = new PhysicianProfile();
							form.setPhysicianId(acc.getId());
							if (!"".equalsIgnoreCase(PrescriptionId) && Integer.parseInt(PrescriptionId) > 0) {
								form.setTargetObject("prescription");
								form.setPrescriptionId(PrescriptionId);
							}

							redirectAttributes.addFlashAttribute("physicianAccount", form);
							mv.setViewName("redirect:editPhysicianProfile");
						}
						else {
							LoginForm login = new LoginForm();
							login.setDisplayName(acc.getPhysicianName());
							login.setPhotoUrl(phyService.getPhysicianPhotoFileName(acc.getId(), env.getProperty("file.photo_path")));
							login.setLogoUrl(phyService.getPhysicianLogoFileName(acc.getId(), env.getProperty("file.logo_path")));
							login.setStatus(acc.getStatus());
							login.setType(PharmacyUtil.userPhysician);
							login.setUserid(acc.getId());
							login.setUserName(acc.getEmail());
							
							session.setAttribute("loginDetail", login);
								
							mv.setViewName("redirect:PhysicianProfile");
						}					
						redirectAttributes.addFlashAttribute("saveStatus", "true");
						//Added on Feb 16,2018-To retain the step process of profile completion by physician
						if(form!=null)
						{
							if(form.getScreenflag().equalsIgnoreCase("savenext") || form.getScreenflag().equalsIgnoreCase("save")){
								if(loggedInUser.getType().equalsIgnoreCase("Physician"))
								{
											int stepProcess=0,SaveStepProcess=0;
										
											stepProcess=form.getProfilestep();
											SaveStepProcess=stepProcess;
											
											if(form.getScreenflag().equalsIgnoreCase("savenext") || form.getScreenflag().equalsIgnoreCase("save"))
											{
												if(stepProcess<3){
													stepProcess++;
												}
												
												if(form.getScreenflag().equalsIgnoreCase("savenext"))
													SaveStepProcess=stepProcess;
											}
											
											request.setAttribute("SaveStepProcess", SaveStepProcess);
											redirectAttributes.addFlashAttribute("SaveStepProcess", SaveStepProcess+"");
										
											
											form.setProfilestep(stepProcess);
											
											if(form.getStatus().equalsIgnoreCase(PharmacyUtil.statusNew) || form.getStatus().equalsIgnoreCase(PharmacyUtil.statusProfileCompleted))
											{
												phyRepo.updatePhysicianProfileStep(stepProcess, form.getPhysicianId(),stepProcess);
												
											}
										}
									
								}
						}
						if(form.getScreenflag()==null || form.getScreenflag().equals("") || (!form.getScreenflag().equalsIgnoreCase("savenext") && form.getScreenflag().equalsIgnoreCase("save"))){
							
							if(reAssignPhysician.length()>0 || deactivatePhysician.length()>0)
							{
								if(reAssignPhysician.length()>0 && reAssignPhysician.equalsIgnoreCase("true"))
								{
									int otherPhysicianId = form.getOtherPhysicianId();
									if (otherPhysicianId > 0) {
										//reAssignPhysician
										redirectAttributes.addFlashAttribute("message", env.getProperty("info.reassignphysician"));
										
									}
								}
								else if(deactivatePhysician.length()>0 && deactivatePhysician.equalsIgnoreCase("true"))
								{
									
										//deactivate Physician
										redirectAttributes.addFlashAttribute("message",  env.getProperty("info.deactivatephysician"));
										
									
								}
							}else
							{
								
								if(newRecFlag)
								{
									redirectAttributes.addFlashAttribute("message", "Physician Account created successfully");
								}else
									redirectAttributes.addFlashAttribute("message", "Physician Account updated successfully");
								
								if(newClinicFlag)
									redirectAttributes.addFlashAttribute("message", "Physician\'s Clinic added successfully!");
								
							}
						}
						
					
					}
				}
			} else {
				redirectAttributes.addFlashAttribute("saveStatus", "false");
				if (isDeaDuplicate) {
					// Error message from Sudha madam on 18-jan-2018 when discussion about duplication DEA with Group  
					// DEA <DEA NUMBER> For this group <Group Name> already exists
//					String groupName = groupMasterRepo.findOne(form.getGroupId()).getGroupName();
					redirectAttributes.addFlashAttribute("message", "DEA "+form.getDea()+" already exists");
				}
				else if (isLicenseDuplicate)
					redirectAttributes.addFlashAttribute("message", "State License already exists");
				else
					redirectAttributes.addFlashAttribute("message", "Email already exists");
				
				form.setProfilestep(0);
				
				if (form.getPhysicianId() > 0) {
					redirectAttributes.addFlashAttribute("physicianAccount", form);
					mv.setViewName("redirect:editPhysicianProfile");
				}	
				else {
					redirectAttributes.addFlashAttribute("form", form);
					mv.setViewName("redirect:createPhysicianAccount");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("message", "Physician Account failed to update");
			mv.setViewName("error500");
		}
		//System.out.println("11111111111111==="+request.getAttribute("SaveStepProcess"));
		return mv;
	}
	
	/**
	 * This method loads the physician account summary page
	 * @param model
	 * @param request
	 * @param response
	 * @param session
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping("/physicianSummary")
	public ModelAndView loadPhysicianAccountSummary(Model model, HttpServletRequest request, HttpServletResponse response, 
			HttpSession session,RedirectAttributes redirectAttributes){
		
		ModelAndView mv = new ModelAndView();
		try {
			model.addAttribute("message", model.asMap().get("message"));
			
			LoginForm loggedInUser = (LoginForm) session.getAttribute("loginDetail");
			if (!loggedInUser.getType().equalsIgnoreCase(PharmacyUtil.userGroupDirector)) {
				List<GroupMaster> groupList = groupService.getAllGroupMaster();
				mv.addObject("groupList", groupList);
			} else {
				GroupDirector gp = groupDirRepo.findOne(loggedInUser.getUserid());
				mv.addObject("groupId", gp.getGroupId());
			}
			mv.addObject("form", new PhysicianProfile());
			
			List<Clinic> clinicList = clinicService.getClinicList(true);
			mv.addObject("clinicList", clinicList);
			
			List<GroupMaster> groupMasterList = groupMasterService.getAllGroupMaster();
			mv.addObject("groupList", groupMasterList);
			
			
			List<GroupMaster> groupList = groupMasterRepo.getAllGroupList(PharmacyUtil.statusActive);
			mv.addObject("changegroupList", groupList);
			
			mv.setViewName("physicianaccountsummary");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return mv;
	}
	/**
	 * This method loads the physician account summary data
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
	 * @param physicianName
	 * @param dea
	 * @param status
	 * @param groupId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getPhysicianSummaryData", method = RequestMethod.POST)
	public @ResponseBody String getPhysicianData(@RequestParam("draw") int draw,
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
			@RequestParam("columns[4][search][regex]") String col4SearchRegex, @RequestParam("physicianName") String physicianName,
			@RequestParam("dea") String dea, @RequestParam("status") String status, @RequestParam("groupId") int groupId,
			HttpServletRequest request) {
		
		return phyService.getPhysicianDataList(draw, start, len, searchTerm, orderColumn, orderDir, physicianName, dea, status , groupId);
	}
	/**
	 * This method loads the physician account page
	 * @param model
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping("/createPhysicianAccount")  
	public ModelAndView createPhyAssistantAccount(Model model, HttpServletRequest request, 
				HttpServletResponse response, HttpSession session) {
		ModelAndView mv = new ModelAndView();
		try {
			LoginForm loggedInUser = (LoginForm) session.getAttribute("loginDetail");
			model.addAttribute("message", model.asMap().get("message"));
			model.addAttribute("saveStatus", model.asMap().get("saveStatus"));
			
			List<States> statesList = statesService.getAllStates();
			List<PrescriberTypeMaster> prescriberMasterList = prescriberMasterService.getMasterList();
			List<GroupMaster> groupList = groupMasterRepo.getAllGroupList(PharmacyUtil.statusActive);
			
			List<CardTypeMaster> cardList = cardTypeMasterRepo.findAll();
			
			// List<Clinic> clinicList = clinicService.getClinicList(true);
			// mv.addObject("clinicList", clinicList);
			
			model.addAttribute("message", model.asMap().get("message"));
			PhysicianProfile form = null;
			try {
				form = (PhysicianProfile) model.asMap().get("form");	
			} catch(Exception e) {
				e.printStackTrace();
			}
			if (form != null) {
				//Added on Dec 16,2017-Temp Password generated in New Account by default
				if(form.getPhysicianId()==0)
				{
					String randomPwd = PharmacyUtil.randomPasswordGenerator();
					form.setPassword(randomPwd);
				}
				int newGroupId=0;
				List<GroupMaster> groupMastList= groupMasterRepo.findByNewGroup();
				if (groupMastList.size() > 0) {
					for (GroupMaster g: groupMastList) {
						newGroupId=g.getId();
						form.setNewGroupId(newGroupId);
					}
				}
				mv.addObject("physicianAccount", form);
			} else {
				PhysicianProfile profile = new PhysicianProfile();
				SimpleDateFormat dt = new SimpleDateFormat("MM/dd/yyyy");
				profile.setDateRegistrated(dt.format( new java.sql.Date(new Date().getTime())) );
				//Added on Dec 16,2017-Temp Password generated in New Account by default
				if(profile.getPhysicianId()==0)
				{
					String randomPwd = PharmacyUtil.randomPasswordGenerator();
					profile.setPassword(randomPwd);
				}
				profile.setSendMailPermission("Yes");
				
				if (loggedInUser.getType().equalsIgnoreCase(PharmacyUtil.userGroupDirector)) {
					GroupDirector gp = groupDirRepo.findOne(loggedInUser.getUserid());
					profile.setGroupId(gp.getGroupId());
					if (gp.getGroupId() > 0)
						profile.setGroupName(groupService.getGroupMasterDetails(gp.getGroupId()).getGroupName());
					else
						profile.setGroupName("");
					profile.setStatus("New");
					
					
					
					//Multiple select lit box
					List<Clinic> clinicList =null;
					List<Clinic> clinicSelectedList =null;
					List<Clinic> groupClinicStatusList =null;
					
					if (profile.getGroupId()>0 && profile.getPhysicianId()>0) {
						clinicList = clinicRepo.getAllPhysicianGroupWiseClinicListNotSelected(profile.getGroupId(),profile.getPhysicianId());
						clinicSelectedList = clinicRepo.getAllPhysicianGroupWiseClinicListSelected(profile.getGroupId(),profile.getPhysicianId());
					}else if (profile.getGroupId()>0) {
						clinicList = clinicRepo.getAllGroupWiseClinicList(profile.getGroupId(),profile.getClinicId());
					}
					
					if (profile.getGroupId()>0)
						groupClinicStatusList=clinicRepo.getGroupWiseAllClinicList(profile.getGroupId());
					
					mv.addObject("groupClinicStatusList", groupClinicStatusList);
					
					mv.addObject("clinicList", clinicList);
					mv.addObject("clinicSelectedList", clinicSelectedList);
					
				}
				int newGroupId=0;
				List<GroupMaster> groupMastList= groupMasterRepo.findByNewGroup();
				if (groupMastList.size() > 0) {
					for (GroupMaster g: groupMastList) {
						newGroupId=g.getId();
						profile.setNewGroupId(newGroupId);
					}
				}
				mv.addObject("physicianAccount", profile);
			}
			List<PhysicianAccount> otherPhysicianList = new ArrayList<PhysicianAccount>();
			if(form!=null && form.getPhysicianId()>0){
				otherPhysicianList = phyRepo.getAllOtherPhysicians(PharmacyUtil.statusApproved,form.getPhysicianId());
			}
			mv.addObject("otherPhysicianList", otherPhysicianList);
			
			
			mv.addObject("usStates", statesList);
			mv.addObject("perscriberList", prescriberMasterList);
			mv.addObject("cardList", cardList);
			mv.addObject("userType", loggedInUser.getType());
			mv.addObject("groupList", groupList);
			mv.addObject("prescriptionId", "");
			
			if(loggedInUser.getType().equalsIgnoreCase("Physician"))
				{
					if(form!=null)
					{
						if(form.getStatus().equalsIgnoreCase(PharmacyUtil.statusNew) || form.getStatus().equalsIgnoreCase(PharmacyUtil.statusProfileCompleted))
						{
							mv.setViewName("physicianprofile");
						}
						else
							mv.setViewName("physicianaccount");
					}
				}
			else
				mv.setViewName("physicianaccount");
		} catch(Exception e) {
			e.printStackTrace();
			mv.setViewName("error500");
		}
		
		return mv;
	}
	
	
	/**
	 * This method loads the physician profile page through llink
	 * @param model
	 * @param request
	 * @param response
	 * @param session
	 * @param id
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "/viewPhysicianProfile", method = {RequestMethod.POST, RequestMethod.GET } ) 
	public ModelAndView viewPhysicianProfile(Model model, HttpServletRequest request, HttpServletResponse response, HttpSession session,
			@RequestParam("id") int id, RedirectAttributes redirectAttributes){
		
		ModelAndView mv = new ModelAndView();
		
		try {
			if (id > 0) {
				PhysicianProfile form = new PhysicianProfile();
				form.setPhysicianId(id);
				redirectAttributes.addFlashAttribute("physicianAccount", form);
				mv.setViewName("redirect:editPhysicianProfile");
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
	 *  This method loads the physician account data
	 * @param model
	 * @param request
	 * @param response
	 * @param session
	 * @param form
	 * @return
	 */
	@RequestMapping(value = "/editPhysicianProfile", method = {RequestMethod.POST, RequestMethod.GET } )  
	public ModelAndView editPhyAssistantAccount(Model model, HttpServletRequest request, HttpServletResponse response, HttpSession session,
			@ModelAttribute("physicianAccount") PhysicianProfile form){
		
		ModelAndView mv = new ModelAndView();
		
		try {
			LoginForm loggedInUser = (LoginForm) session.getAttribute("loginDetail");
			int groupId = 0;
			model.addAttribute("message", model.asMap().get("message"));
			int id = 0;
			if (form != null)
				id = form.getPhysicianId();
			else
				id = Integer.parseInt((String) model.asMap().get("physicianId") );
			
			List<States> statesList = statesService.getAllStates();
			List<PrescriberTypeMaster> prescriberMasterList = prescriberMasterService.getMasterList();
			List<CardTypeMaster> cardList = cardTypeMasterRepo.findAll();
			List<PhysicianAccount> otherPhysicianList = phyRepo.getAllOtherPhysicians(PharmacyUtil.statusApproved,form.getPhysicianId());
			mv.addObject("otherPhysicianList", otherPhysicianList);
			mv.addObject("usStates", statesList);
			mv.addObject("perscriberList", prescriberMasterList);
			mv.addObject("cardList", cardList);
			mv.addObject("showBackToPrescription", "false"); 
			
			PhysicianProfile profile=null;
			String prescriptionId = "";
			if (id > 0) {
				if (form.getFirstName() == null || "".equalsIgnoreCase(form.getFirstName())) {
					 profile = phyService.getPhysicianData(id, env.getProperty("file.photo_path"), env.getProperty("file.logo_path"));
					
					if (model.asMap().get("target") != null) {
						String targetObject = model.asMap().get("target").toString();
						prescriptionId = model.asMap().get("PrescriptionId").toString();
						profile.setTargetObject(targetObject);
						profile.setPrescriptionId(prescriptionId);
						mv.addObject("showBackToPrescription", "true");
					} else {
						profile.setTargetObject(form.getTargetObject()!=null?form.getTargetObject():"");
						profile.setPrescriptionId(form.getPrescriptionId());
						prescriptionId = form.getPrescriptionId();
					}
					int newGroupId=0;
					List<GroupMaster> groupMastList= groupMasterRepo.findByNewGroup();
					if (groupMastList.size() > 0) {
						for (GroupMaster g: groupMastList) {
							newGroupId=g.getId();
							profile.setNewGroupId(newGroupId);
						}
					}
					mv.addObject("physicianAccount", profile);
					groupId = profile.getGroupId();
					
					/*if (profile.getGroupId()>0) {
						clinicList = clinicRepo.getAllGroupWiseClinicList(profile.getGroupId(),profile.getClinicId());
					}*/
					//Multiple select lit box
					List<Clinic> clinicList =null;
					List<Clinic> clinicSelectedList =null;
					List<Clinic> groupClinicStatusList =null;
					
					if (profile.getGroupId()>0 && profile.getPhysicianId()>0) {
						clinicList = clinicRepo.getAllPhysicianGroupWiseClinicListNotSelected(profile.getGroupId(),profile.getPhysicianId());
						clinicSelectedList = clinicRepo.getAllPhysicianGroupWiseClinicListSelected(profile.getGroupId(),profile.getPhysicianId());
					}else if (profile.getGroupId()>0) {
						clinicList = clinicRepo.getAllGroupWiseClinicList(profile.getGroupId(),profile.getClinicId());
					}
					
					if (profile.getGroupId()>0)
						groupClinicStatusList=clinicRepo.getGroupWiseAllClinicList(profile.getGroupId());
					
					mv.addObject("groupClinicStatusList", groupClinicStatusList);
					mv.addObject("clinicList", clinicList);
					mv.addObject("clinicSelectedList", clinicSelectedList);
					
					//January 31, 2018 Rohini
					//Put a variable and random number in the image url to refresh the image after uploading
					//example : photoUrl?x=123456
					int randomNumber = PharmacyUtil.randomNumberGenerator();
					
					String photoUrl = profile.getPhotoFile();
					if(photoUrl==null || photoUrl.length()==0) {
						photoUrl="images/img.jpg";
					}
					
					photoUrl = photoUrl+"?x="+randomNumber;
					profile.setPhotoFile(photoUrl);
					//System.out.println("photoUrl ===  "+photoUrl);
					
					randomNumber = PharmacyUtil.randomNumberGenerator();
					
					String logoUrl = profile.getLogoFile();
					
					if(logoUrl==null || logoUrl.length()==0) {
						logoUrl="images/default_logo.jpg";
					}
					
					logoUrl = logoUrl+"?x="+randomNumber;
					profile.setLogoFile(logoUrl);
					//System.out.println("logoUrl ===  "+logoUrl);
					
					//January 31, 2018 Rohini
				} else {
					if (model.asMap().get("target") != null) {
						String targetObject = model.asMap().get("target").toString();
						prescriptionId = model.asMap().get("PrescriptionId").toString();
						form.setTargetObject(targetObject);
						form.setPrescriptionId(prescriptionId);
						mv.addObject("showBackToPrescription", "true");
					}
					int newGroupId=0;
					List<GroupMaster> groupMastList= groupMasterRepo.findByNewGroup();
					if (groupMastList.size() > 0) {
						for (GroupMaster g: groupMastList) {
							newGroupId=g.getId();
							form.setNewGroupId(newGroupId);
						}
					}
					mv.addObject("physicianAccount", form);
					groupId = form.getGroupId();
					
					/*if (form.getGroupId()>0) {
						clinicList = clinicRepo.getAllGroupWiseClinicList(form.getGroupId(),form.getClinicId());
					}
					mv.addObject("clinicList", clinicList);
					*/
					
					//Multiple select lit box
					List<Clinic> clinicList =null;
					List<Clinic> clinicSelectedList =null;
					List<Clinic> groupClinicStatusList =null;
					
					if (form.getGroupId()>0 && form.getPhysicianId()>0) {
						clinicList = clinicRepo.getAllPhysicianGroupWiseClinicListNotSelected(form.getGroupId(),form.getPhysicianId());
						clinicSelectedList = clinicRepo.getAllPhysicianGroupWiseClinicListSelected(form.getGroupId(),form.getPhysicianId());
					}else if (form.getGroupId()>0) {
						clinicList = clinicRepo.getAllGroupWiseClinicList(form.getGroupId(),form.getClinicId());
					}
					
					if (form.getGroupId()>0)
						groupClinicStatusList=clinicRepo.getGroupWiseAllClinicList(form.getGroupId());
					
					mv.addObject("groupClinicStatusList", groupClinicStatusList);
					mv.addObject("clinicList", clinicList);
					mv.addObject("clinicSelectedList", clinicSelectedList);
				}
				
				List<DocumentFileList> uploadedDocList = phyService.getUploadedDocuments(id);
				mv.addObject("uploadedDocList", uploadedDocList);
				
				try {
					String rootFilePath= PharmacyUtil.getRootFolderForPhysicianPDF(session, env);
					String folderName= rootFilePath + File.separator + form.getPhysicianId();

					String pdfFileName=folderName + File.separator + "PhysicianPdf_"+form.getPhysicianId()+"_ESigned.pdf";
					if(new File(pdfFileName).exists())
					{
						form.setEignedPdf(true);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				if(loggedInUser.getType().equalsIgnoreCase("Physician"))
				{
					if(form!=null)
					{
						if(form.getStatus().equalsIgnoreCase(PharmacyUtil.statusNew) || form.getStatus().equalsIgnoreCase(PharmacyUtil.statusProfileCompleted))
						{
							
							String ProfileESigned="";
							if(session.getAttribute("ProfileESigned")!=null)
							{
								ProfileESigned=session.getAttribute("ProfileESigned")+"";
								//System.out.println("ProfileESigned ======"+session.getAttribute("ProfileESigned"));
								session.setAttribute("ProfileESigned",null);
							}


							if(ProfileESigned.equalsIgnoreCase("ProfileESigned"))
							{
								form.setProfilestep(3);
							}
							mv.setViewName("physicianprofile");
						}
						else
							mv.setViewName("physicianaccount");
					}
					
				}
				else
					mv.setViewName("physicianaccount");
			} else {
				// Change to login page
				mv.setViewName("error500");
			}
			
			List<GroupMaster> groupList = groupService.getByGroupMaster(groupId);
			mv.addObject("groupList", groupList);
			mv.addObject("prescriptionId", prescriptionId);
			
		} catch(Exception e) {
			e.printStackTrace();
			mv.setViewName("error500");
		}
		
		return mv;		
	}
	
	/**
	 * This method navigates to the physician account related prescription
	 * @param model
	 * @param form
	 * @param request
	 * @param response
	 * @param session
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "/backPhysicianPrescription", method = RequestMethod.POST)
	public ModelAndView backToPrescription(Model model, @ModelAttribute("physicianAccount") PhysicianProfile form, 
			HttpServletRequest request, HttpServletResponse response, HttpSession session, RedirectAttributes redirectAttributes) {
		ModelAndView mv = new ModelAndView();
		
		if (form.getPrescriptionId() != null && Integer.parseInt(form.getPrescriptionId()) > 0) {
			String PrescriptionId = form.getPrescriptionId();
			PrescriptionForm prescriptionForm = new PrescriptionForm();
			prescriptionForm.setPrescriptionId(Integer.parseInt(PrescriptionId));
			redirectAttributes.addFlashAttribute("form", prescriptionForm);
			mv.setViewName("redirect:/prescription/editPrescription");
		} else if (form.getPhysicianId() > 0) {
			redirectAttributes.addFlashAttribute("physicianAccount", form);
			mv.setViewName("redirect:editPhysicianProfile");
		} else {
			mv.setViewName("error500");
		}
		
		return mv;
	}
	
	
	/**
	 * This method loads the autocomplete physician name list
	 */
	@RequestMapping(value = "/getAutoPhysicianName", method = RequestMethod.GET, headers = "Accept=*/*")
	public @ResponseBody List<String> getPhysicianList(@RequestParam("term") String query) {
		List<String> physicianList = phyService.getPhysicianNameList(query);
		return physicianList;
	}
	
	
	/**
	 * This method stores the physician related documents
	 * @param request
	 * @param response
	 * @param session
	 * @param uploadDocFile
	 * @param physicianId
	 * @param docPurpose
	 * @param expiryDate
	 * @param fileName
	 * @return
	 */
	@RequestMapping(value = "/phyDocFileUpload", method = RequestMethod.POST)
	public @ResponseBody String saveDocFiles(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			@RequestParam("docFile") CommonsMultipartFile uploadDocFile, @RequestParam("phyId") String physicianId,
			@RequestParam("docPurpose") String docPurpose, @RequestParam("expiryDate") String expiryDate, @RequestParam("fileName") String fileName) {
		
		try {
			LoginForm loggedInUser = (LoginForm) session.getAttribute("loginDetail");
			
			String rootFilePath=env.getProperty("file.root_folder_path");
			boolean isSaved = phyService.savePhysicianDocFiles(uploadDocFile, Integer.parseInt(physicianId), rootFilePath, loggedInUser, 
					docPurpose, expiryDate, fileName);
			if (isSaved) {
				//System.out.println("Document uploaded successfully");
			} else {
				//System.out.println("Fail to upload Document");
			}
			
			List<DocumentFileList> uploadedDocList = phyService.getUploadedDocuments( Integer.parseInt(physicianId) );
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
	 * This method deletes the physician related documents
	 * @param request
	 * @param response
	 * @param session
	 * @param fileId
	 * @param physicianId
	 * @return
	 */
	@RequestMapping(value = "/phyDocFileDelete", method = RequestMethod.POST)
	public @ResponseBody String deleteDocFiles(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			 @RequestParam("fileId") String fileId, @RequestParam("phyId") String physicianId){
		try {
			//System.out.println("deleteDocFiles ---> " + fileId);
			boolean isDeleted = phyService.deletePhysicianDocFiles( Integer.parseInt(fileId) );
			if (isDeleted) {
				//System.out.println("Document deleted successfully");
			} else {
				//System.out.println("Fail to delete Document");
			}
			List<DocumentFileList> uploadedDocList = phyService.getUploadedDocuments( Integer.parseInt(physicianId) );
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
	 * This method downloads the physician documents
	 * @param request
	 * @param response
	 * @param session
	 * @param fileId
	 * @param physicianId
	 */
	@RequestMapping(value = "/phyDocFileDownload", method = RequestMethod.GET)
	public void downloadDocFiles(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			 @RequestParam("f") String fileId, @RequestParam("p") String physicianId){
		try {
			String fileName = phyService.getPhysicianDocFileName( Integer.parseInt(fileId) );
			String downloadName = phyService.getPhysicianDownloadDocFileName( Integer.parseInt(fileId) );
			PharmacyUtil.downloadFile(fileName, downloadName, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
	
	/**
	 * This method loads the autocomplete states list
	 * @param query
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getStatesList", method = RequestMethod.GET)
	public @ResponseBody List<States> getStatesList(@RequestParam("term") String query, HttpServletResponse response) {
		try {
			List<States> stateList = statesService.getStateNameList(query);
			return stateList;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * This method loads the physician account summary data
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
	@RequestMapping(value = "/getPhysicianDocumentsData", method = RequestMethod.POST)
	public @ResponseBody String getPhysicianDocumentData(@RequestParam("draw") int draw,
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
		
		return phyService.getPhysicianDocumentDataList(draw, start, len, searchTerm, orderColumn, orderDir, physicianId );
	}
	/**
	 * This method loads the group wise clinic
	 * @param request
	 * @param response
	 * @param session
	 * @param groupId
	 * @param clinicId
	 * @return
	 */
	@RequestMapping(value="/fetchGroupWiseClinic", method=RequestMethod.POST)
	public @ResponseBody List<Clinic> getGroupWiseClinicDataById(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			 @RequestParam("groupId") int groupId,@RequestParam("clinicId") int clinicId) {
		List<Clinic> clinicList=null;
		try {
			if (groupId>0) {
				clinicList = clinicRepo.getAllGroupWiseClinicList(groupId,clinicId);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return clinicList;
	}
	/**
	 * This method loads the physician account page
	 * @param model
	 * @param request
	 * @param response
	 * @param session
	 * @param physicianId
	 * @param groupId
	 * @param physicianProfile
	 * @return
	 */
	@RequestMapping(value = "/createNewPhysicianClinic", method = RequestMethod.POST)
	public @ResponseBody List<Clinic> createNewPhysicianClinic(Model model, HttpServletRequest request, 
			HttpServletResponse response, HttpSession session, @RequestParam("physicianId") int physicianId, @RequestParam("groupId") int groupId,@RequestParam("physicianProfile") String physicianProfile) {
		List<Clinic> clinicSelectedList =null;
		
		try {
			if(physicianId>0){
				LoginForm loggedInUser = (LoginForm) session.getAttribute("loginDetail");
				boolean alreadyClinicExist=phyService.saveNewPhysicianClinic(physicianId,loggedInUser,physicianProfile,groupId);
				if(alreadyClinicExist)
				{
					clinicSelectedList=new ArrayList<Clinic>();
					Clinic obj=new Clinic();
					obj.setId(0);
					obj.setClinicName("alreadyexist");
					clinicSelectedList.add(obj);
					
				}else
					clinicSelectedList = clinicRepo.getAllPhysicianGroupWiseClinicListSelected(groupId,physicianId);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return clinicSelectedList;
	}
	/**
	 * This method validates whether the physician clinic already exists
	 * @param model
	 * @param request
	 * @param response
	 * @param session
	 * @param physicianId
	 * @param groupId
	 * @param physicianProfile
	 * @return
	 */
	@RequestMapping(value = "/checkPhysicianClinicAlreadyExists", method = RequestMethod.POST)
	public @ResponseBody String checkPhysicianClinicAlreadyExists(Model model, HttpServletRequest request, 
			HttpServletResponse response, HttpSession session, @RequestParam("physicianId") int physicianId, @RequestParam("groupId") int groupId,@RequestParam("physicianProfile") String physicianProfile) {
	
		try {
			boolean alreadyClinicExist=false;
			if(physicianId>0){
				LoginForm loggedInUser = (LoginForm) session.getAttribute("loginDetail");
				alreadyClinicExist=phyService.checkPhysicianClinicAlreadyExists(physicianId,loggedInUser,physicianProfile,groupId);
			}
			if(alreadyClinicExist)
				return "alreadyexist";
			else
				return "";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * This method downloads the physician print pdf
	 * @param pId
	 * @param session
	 * @param response
	 */
	@RequestMapping(value = "/generatePhysicianPdf", method = RequestMethod.POST)
	public void generatePhysicianPdf(@RequestParam("physicianId") String pId, HttpSession session, HttpServletResponse response){
	
		try {
			int physicianId = Integer.parseInt(pId);
			if (physicianId > 0) {
				
				String rootFilePath= PharmacyUtil.getRootFolderForPhysicianPDF(session, env);
				String folderName= rootFilePath + File.separator + physicianId;
				String pdfFileName="";
				//String fileName ="";
				String outputFileName = phyService.generatePdfFilePath(physicianId);
				String fileName = phyService.getPhysicianPdfGenerated(physicianId, outputFileName, session,"");  // name to be download
				
				String pdfFileName1=folderName + File.separator + "PhysicianPdf_"+physicianId+".pdf";
				String pdfFileName2=folderName + File.separator + "PhysicianPdf_"+physicianId+"_ESigned.pdf";
				if(new File(pdfFileName2).exists())
				{
					pdfFileName=pdfFileName2;
					fileName="PhysicianPdf_"+physicianId+"_ESigned.pdf";
				}
				else if(new File(pdfFileName1).exists())
				{
					pdfFileName=pdfFileName1;
					fileName="PhysicianPdf_"+physicianId+".pdf";
				}
				
				PharmacyUtil.downloadFile(pdfFileName, fileName, response);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/**
	 * This method stores the physician profile signature
	 * @param model
	 * @param request
	 * @param response
	 * @param session
	 * @param form
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value="/savePhysicianProfileSignature", method=RequestMethod.POST)
	public ModelAndView savePhysicianProfileSignature(Model model,HttpServletRequest request, HttpServletResponse response, HttpSession session,
			@ModelAttribute("physicianAccount") PhysicianProfile form, RedirectAttributes redirectAttributes) {
		String resp = "";
		ModelAndView mv = new ModelAndView();
		try {
			LoginForm loggedInUser = (LoginForm) session.getAttribute("loginDetail");
			if (form.getBase64ImgString() != null && form.getBase64ImgString() .length()>0 && form.getPhysicianId()>0) {
				String rootFilePath= PharmacyUtil.getRootFolderForPhysicianPDF(session, env);
				String folderName= rootFilePath + File.separator + form.getPhysicianId();
				String pdfFileName=folderName + File.separator + "PhysicianPdf_"+form.getPhysicianId()+".pdf";
				String outputFileName = phyService.generatePdfFilePath(form.getPhysicianId());
				resp = phyService.getPhysicianPdfGenerated(form.getPhysicianId(), outputFileName, session,form.getBase64ImgString());
				form.setStatus(PharmacyUtil.statusProfileCompleted);
				redirectAttributes.addFlashAttribute("saveStatus", "true");
				redirectAttributes.addFlashAttribute("message", "Physician Profile have been successfully E-Signed by the Physician");
				redirectAttributes.addFlashAttribute("physicianAccount", form);
				session.setAttribute("ProfileESigned", "ProfileESigned");
				
				if (PharmacyUtil.userAdmin.equalsIgnoreCase(loggedInUser.getType()) 
						|| PharmacyUtil.userSuperAdmin.equalsIgnoreCase(loggedInUser.getType()) 
						|| PharmacyUtil.userGroupDirector.equalsIgnoreCase(loggedInUser.getType()) )
				{
				
					mv.setViewName("redirect:editPhysicianProfile");
				}
				else {
					mv.setViewName("redirect:PhysicianProfile");
				}
				
				
				
				
				mv.setViewName("redirect:editPhysicianProfile");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mv;
	}
	
	/**
	 * This method loads the autocomplete other physician list other than current physician in the group 
	 * @param physicianId
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/fetchOtherPhysiciansofGroupbyAjax", method = RequestMethod.POST)
	public @ResponseBody List<PhysicianAccount> fetchOtherPhysiciansofGroupbyAjax(@RequestParam("physicianId") int physicianId,HttpSession session) {
		
		int groupId=0;
		List<PhysicianAccount> otherPhysicianList =new ArrayList<PhysicianAccount>();
		
		try {
			PhysicianGroup phyGrp =  phyGroupRepo.findRecordByPhysicianId(physicianId);
			if (phyGrp != null) {
				groupId=phyGrp.getGroupId();
			}
			
			otherPhysicianList = phyRepo.getOtherPhysicianListByActiveGroupId(groupId,physicianId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return otherPhysicianList;
		
	}
	
	/**
	 * This method re-assign all the Patients of the Pysician to a different Physician
	 * @param otherPhysicianId
	 * @param physicianId
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/reAssignPhysicianbyAjax", method = RequestMethod.POST)
	public @ResponseBody String reAssignPhysicianbyAjax(@RequestParam("otherPhysicianId") int otherPhysicianId,
			@RequestParam("physicianId") int physicianId,HttpSession session) {
		
		try {
			LoginForm loggedInUser = (LoginForm) session.getAttribute("loginDetail");

			//reAssignPhysician
			phyServiceImpl.reAssignPhysicianId( otherPhysicianId,  physicianId,loggedInUser);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "reassigned";
		
	}
	
	/**
	 * This method deactivates all Patients of this Physician
	 * @param physicianId
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/deActivatePhysicianbyAjax", method = RequestMethod.POST)
	public @ResponseBody String deActivatePhysicianbyAjax(@RequestParam("physicianId") int physicianId,HttpSession session) {
		
		try {
			LoginForm loggedInUser = (LoginForm) session.getAttribute("loginDetail");
			
			//deactivate Physician
			phyServiceImpl.deActivatePhysician( physicianId,loggedInUser);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "deactivated";
		
	}
	/**
	 *  This method sends the physician account credentail email
	 * @param form
	 * @param bindingResult
	 * @param model
	 * @param request
	 * @param response
	 * @param session
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "/sendPhysicianAccountCredentialsEmail", method = RequestMethod.POST)
	public ModelAndView sendPhysicianAccountCredentialsEmail(@ModelAttribute("physicianAccount") PhysicianProfile form,  BindingResult bindingResult, Model model,
			HttpServletRequest request, HttpServletResponse response, HttpSession session, RedirectAttributes redirectAttributes) {
		
	
	ModelAndView mv = new ModelAndView();
	
	try {
		System.out.print("sendPhysicianAccountCredentialsEmail");
		LoginForm loggedInUser = (LoginForm) session.getAttribute("loginDetail");

			if (form.getPhysicianId() > 0){
				
				boolean isMailSent = phyService.sendPhysicianAccountMail(form, loggedInUser, session, request,env);
				
				if (isMailSent) {
					redirectAttributes.addFlashAttribute("saveStatus", "true");
					redirectAttributes.addFlashAttribute("message", "User Account Credentials sent successfully!!");
				} else {
					redirectAttributes.addFlashAttribute("saveStatus", "false");
					redirectAttributes.addFlashAttribute("message", "Failed to send User Account Credentials!!");
				}
				
				
				redirectAttributes.addFlashAttribute("physicianAccount", form);
				mv.setViewName("redirect:editPhysicianProfile");
			}else {
				redirectAttributes.addFlashAttribute("form", form);
				mv.setViewName("redirect:physicianAccount");
			}
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}	
		return mv;
	}
}
