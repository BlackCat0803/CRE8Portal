package com.pharma.core.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pharma.core.formBean.LoginForm;
import com.pharma.core.formBean.invoice.InvoiceForm;
import com.pharma.core.formBean.order.OrderForm;
import com.pharma.core.formBean.patient.PatientAccountForm;
import com.pharma.core.formBean.physician.PhysicianProfile;
import com.pharma.core.formBean.prescription.PrescriptionForm;
import com.pharma.core.formBean.prescription.PrescriptionTransactionForm;
import com.pharma.core.model.clinic.Clinic;
import com.pharma.core.model.pharmacygroup.GroupDirector;
import com.pharma.core.model.pharmacygroup.GroupMaster;
import com.pharma.core.model.physician.PhysicianAssistantAccount;
import com.pharma.core.model.physician.PhysicianGroup;
import com.pharma.core.model.pioneer.PrescribedItemType;
import com.pharma.core.model.pioneer.PrescriptionItem;
import com.pharma.core.model.pioneer.PrescriptionMedication;
import com.pharma.core.model.prescription.PrescriptionMaster;
import com.pharma.core.model.prescription.SubtanceItemDocuments;
import com.pharma.core.pharmaservices.invoice.InvoiceMasterService;
import com.pharma.core.pharmaservices.order.OrderMasterService;
import com.pharma.core.pharmaservices.patientservices.PatientAccountService;
import com.pharma.core.pharmaservices.pharmacygroupservices.GroupMasterService;
import com.pharma.core.pharmaservices.physicianservices.PhysicianAccountService;
import com.pharma.core.pharmaservices.physicianservices.PhysicianAssistantAccountService;
import com.pharma.core.pharmaservices.pioneer.PrescribedItemTypeService;
import com.pharma.core.pharmaservices.pioneer.PrescriptionDiagnosisICD10Service;
import com.pharma.core.pharmaservices.pioneer.PrescriptionItemDispensingUnitService;
import com.pharma.core.pharmaservices.pioneer.PrescriptionItemService;
import com.pharma.core.pharmaservices.pioneer.PrescriptionMedicationService;
import com.pharma.core.pharmaservices.pioneer.PrescriptionOriginTypeService;
import com.pharma.core.pharmaservices.pioneer.PrescriptionSigCodesService;
import com.pharma.core.pharmaservices.prescription.PrescriptionService;
import com.pharma.core.pharmaservices.prescription.SubtanceDocumentsService;
import com.pharma.core.repository.clinic.ClinicRepository;
import com.pharma.core.repository.pharmacygroup.GroupDirectorAccountRespository;
import com.pharma.core.repository.physician.PhysicianClinicRepository;
import com.pharma.core.repository.physician.PhysicianGroupRepository;
import com.pharma.core.repository.prescription.PrescriptionStatusRepository;
import com.pharma.core.repository.prescription.SubtanceItemsDocumentsRepository;
import com.pharma.core.util.PharmacyUtil;
/**
 * This class is a controller to save and load the patient prescription details
 */
@Controller
@PropertySource("classpath:common.properties") 
public class PrescriptionController extends PrescriptionBaseController {

	@Autowired
	PatientAccountService patientAccountService;
	
	@Autowired
	PhysicianAccountService physicianAccountService;
	
	@Autowired
	PrescriptionService prescriptionService;
	
	@Autowired
	PrescribedItemTypeService prescribedItemTypeService;
	
	@Autowired
	PrescriptionMedicationService prescriptionMedicationService;
	
	@Autowired
	PrescribedItemTypeService prescriptionItemTypeService;
	
	@Autowired
	PrescriptionOriginTypeService originTypeServie;
	
	@Autowired
	PrescriptionItemDispensingUnitService unitService;
	
	@Autowired
	PrescriptionDiagnosisICD10Service icd10Service;
	
	@Autowired
	PrescriptionSigCodesService sigCodeService;
	
	@Autowired
	PhysicianAssistantAccountService physicianAssistantService;
	
	@Autowired
	SubtanceDocumentsService subtanceDocService;
	
	@Autowired
	OrderMasterService orderMasterService;	
	
	@Autowired
	InvoiceMasterService invoiceMasterService;
	
	@Autowired
	SubtanceItemsDocumentsRepository subtanceRepo;
	
	@Autowired
	PrescriptionStatusRepository  prescriptionStatusRepo;
	
	@Autowired
	PhysicianClinicRepository phyClinicRepo;
	
	@Autowired
	private Environment env;
	
	@Autowired
	ClinicRepository clinicRepo;
	
	@Autowired
	GroupMasterService groupService;

	@Autowired
	GroupDirectorAccountRespository groupDirRepo;

	@Autowired
	PhysicianGroupRepository phyGroupRepo;
	
	@Autowired
	PrescriptionItemService prescriptionItemService;

	/**
	 * This method loads the prescription account summary page
	 * @param model
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping("/prescriptionSummary")
	public ModelAndView loadPrescriptionSummary(Model model, HttpServletRequest request, HttpServletResponse response, HttpSession session){
		ModelAndView mv = new ModelAndView();
		try {
			mv.setViewName("prescriptionSummary");
			LoginForm loggedInUser = (LoginForm) session.getAttribute("loginDetail");
			
			PrescriptionForm form = new PrescriptionForm();
			mv.addObject("form", form);
			mv.addObject("userType", loggedInUser.getType());
			mv.addObject("userId", loggedInUser.getUserid());
			
			if (!loggedInUser.getType().equalsIgnoreCase(PharmacyUtil.userGroupDirector) && !loggedInUser.getType().equalsIgnoreCase(PharmacyUtil.userPhysician) 
					&& !loggedInUser.getType().equalsIgnoreCase(PharmacyUtil.userPhysicianAssistant)) {
				List<GroupMaster> groupList = groupService.getAllGroupMaster();
				mv.addObject("groupList", groupList);
			} else if (loggedInUser.getType().equalsIgnoreCase(PharmacyUtil.userPhysician)) {
				mv.addObject("groupId", loggedInUser.getGroupid()); 
			} else if (loggedInUser.getType().equalsIgnoreCase(PharmacyUtil.userPhysicianAssistant)) {
				int phyId=loggedInUser.getPhysicianAssistantPhysicianId();
				PhysicianGroup phyGroup = phyGroupRepo.findRecordByPhysicianId(phyId);
				mv.addObject("groupId", phyGroup.getGroupId()); 
			} else if (loggedInUser.getType().equalsIgnoreCase(PharmacyUtil.userGroupDirector)) { 
				GroupDirector gp = groupDirRepo.findOne(loggedInUser.getUserid());
				mv.addObject("groupId", gp.getGroupId());
			}
			
			model.addAttribute("message", model.asMap().get("message"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mv;
	}
	/**
	 * This method loads the prescription account summary data
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
	 * @param patientName
	 * @param presriptionDate
	 * @param prescriptionNo
	 * @param presriptionToDate
	 * @param userId
	 * @param userType
	 * @param rxNo
	 * @param rxPrescriptionNo
	 * @param group
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/getPrescriptionData", method = RequestMethod.POST)
	public @ResponseBody String getPhysicianAssistantData(@RequestParam("draw") int draw, @RequestParam("start") int start, 
			@RequestParam("length") int len, @RequestParam("search[value]") String searchTerm, @RequestParam("order[0][column]") int orderColumn, 
			@RequestParam("order[0][dir]") String orderDir, @RequestParam("columns[0][data]") String col0Data, @RequestParam("columns[0][name]") String col0Name,
			@RequestParam("columns[0][searchable]") String col0Search, @RequestParam("columns[0][orderable]") String col0Order,
			@RequestParam("columns[0][search][value]") String col0SearchValue, @RequestParam("columns[0][search][regex]") String col0SearchRegex,
			@RequestParam("columns[1][data]") String col1Data, @RequestParam("columns[1][name]") String col1Name, 
			@RequestParam("columns[1][searchable]") String col1Search, @RequestParam("columns[1][orderable]") String col1Order, 
			@RequestParam("columns[1][search][value]") String col1SearchValue, @RequestParam("columns[1][search][regex]") String col1SearchRegex,
			@RequestParam("columns[2][data]") String col2Data, @RequestParam("columns[2][name]") String col2Name, 
			@RequestParam("columns[2][searchable]") String col2Search, @RequestParam("columns[2][orderable]") String col2Order, 
			@RequestParam("columns[2][search][value]") String col2SearchValue, @RequestParam("columns[2][search][regex]") String col2SearchRegex,
			@RequestParam("columns[3][data]") String col3Data, @RequestParam("columns[3][name]") String col3Name, 
			@RequestParam("columns[3][searchable]") String col3Search, @RequestParam("columns[3][orderable]") String col3Order, 
			@RequestParam("columns[3][search][value]") String col3SearchValue, @RequestParam("columns[3][search][regex]") String col3SearchRegex,
			@RequestParam("columns[4][data]") String col4Data, @RequestParam("columns[4][name]") String col4Name, 
			@RequestParam("columns[4][searchable]") String col4Search, @RequestParam("columns[4][orderable]") String col4Order, 
			@RequestParam("columns[4][search][value]") String col4SearchValue, @RequestParam("columns[4][search][regex]") String col4SearchRegex, 
			@RequestParam("physicianName") String physicianName, @RequestParam("patientName") String patientName, 
			@RequestParam("presriptionDate") String presriptionDate, @RequestParam("prescriptionNo") String prescriptionNo, 
			@RequestParam("presriptionToDate") String presriptionToDate, @RequestParam("userId") String userId, @RequestParam("userType") String userType, 
			@RequestParam("rxNo") String rxNo,@RequestParam("rxPrescriptionNo") String rxPrescriptionNo,@RequestParam("groupId") String group, 
			HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		
		int groupId = 0;
		if (group != null && !"".equalsIgnoreCase(group))
			groupId = Integer.parseInt(group);

		String prescriptionstatus = "";
		return prescriptionService.getPrescriptionDataList(draw, start, len, searchTerm, orderColumn, orderDir, physicianName, patientName, 
				presriptionDate, presriptionToDate, userId, userType, groupId, prescriptionNo, prescriptionstatus, rxNo,rxPrescriptionNo, 
				request, response, session);
	}
	
	/**
	 * This method loads the prescription account page
	 * @param model
	 * @param request
	 * @param response
	 * @param session
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping("/prescriptionView")
	public ModelAndView showPrescriptionPage(Model model,HttpServletRequest request, HttpServletResponse response, HttpSession session, 
			RedirectAttributes redirectAttributes) {
		ModelAndView mv = new ModelAndView();
		try {
			List<PrescribedItemType> prescribedItemTypeList = prescribedItemTypeService.getAllPrescribedItemType();
			
			PrescriptionForm form = new PrescriptionForm();
			
			
			mv.addObject("prescribedItemTypeList", prescribedItemTypeList);
			
			mv.addObject("typeList", prescriptionItemTypeService.getAllPrescribedItemType());
			mv.addObject("originTypeList", originTypeServie.getAllPrescriptionOriginType());
			mv.addObject("unitList", unitService.getAllPrescriptionItemDispensingUnit());
			mv.addObject("prescriptionStatusList", prescriptionStatusRepo.getAllPrescriptionStatusList());
			mv.addObject("showSaveBtn", "true");
			mv.addObject("clinicOptNotShow", "false");
			form.setClass2ControlSubtance(false);
			form.setFileFtpAllowed(false);
			
			LoginForm loggedInUser = (LoginForm) session.getAttribute("loginDetail");
			
			if (PharmacyUtil.userPhysician.equalsIgnoreCase(loggedInUser.getType())) {
				mv.addObject("writtenById", loggedInUser.getUserid());
				mv.addObject("writtenByName", loggedInUser.getDisplayName());
				
				form = prescriptionService.getPhysicianDataById(loggedInUser.getUserid(), 0, form);
				
			} else  if (PharmacyUtil.userPhysicianAssistant.equalsIgnoreCase(loggedInUser.getType())) {
				PhysicianAssistantAccount assistant = physicianAssistantService.getPhysicianAssistantAccountDetails(loggedInUser.getUserid());
				//temp commented on jan 19,2018
				//String physicianName = physicianAccountService.getPhysicianAccountDetails(assistant.getPhysicianId()).getPhysicianName();
				int phyId=loggedInUser.getPhysicianAssistantPhysicianId();
				String physicianName = physicianAccountService.getPhysicianAccountDetails(phyId).getPhysicianName();
				
				form = prescriptionService.getPhysicianDataById(phyId, 0, form);
				
				mv.addObject("writtenById", phyId);
				mv.addObject("writtenByName", physicianName);
			}
			
			List<Clinic> clinicSelectedList =null;
			clinicSelectedList = clinicRepo.getAllPhysicianGroupWiseClinicListSelected(form.getGroupId(),form.getPhysicianId());
			mv.addObject("clinicList", clinicSelectedList);
			
			
			mv.addObject("prescription", form);
			mv.setViewName("prescriptionView");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mv;
	}
	/**
	 * This method loads the  prescription patient data
	 * @param request
	 * @param response
	 * @param session
	 * @param patientId
	 * @param physicianId
	 * @return
	 */
	@RequestMapping(value="/prescriptionPatientData", method=RequestMethod.POST)
	public @ResponseBody String getPatientDataById(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			 @RequestParam("patientId") String patientId,@RequestParam("physicianId") String physicianId) {
		String resp = "";
		try {
			if (patientId != null && !"".equalsIgnoreCase(patientId) && physicianId != null && !"".equalsIgnoreCase(physicianId)) {
				resp = prescriptionService.getPatientById(Integer.parseInt(patientId),Integer.parseInt(physicianId));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resp;
	}
	/**
	 * This method loads the  prescription physician data
	 * @param request
	 * @param response
	 * @param session
	 * @param physicianId
	 * @param prescriptionId
	 * @return
	 */
	@RequestMapping(value="/fetchPhysicianInfo", method=RequestMethod.POST)
	public @ResponseBody String getPhysicianDataById(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			 @RequestParam("physicianId") String physicianId, @RequestParam("prescriptionId") String prescriptionId) {
		String resp = "";
		try {
			if (physicianId != null && !"".equalsIgnoreCase(physicianId)) {
				resp = prescriptionService.getPhysicianById(Integer.parseInt(physicianId), Integer.parseInt(prescriptionId));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resp;
	}
	/**
	 * This method loads the billing address of patient/physician/clinic
	 * @param request
	 * @param response
	 * @param session
	 * @param patientId
	 * @param physicianId
	 * @param addOpt
	 * @param clinicId
	 * @return
	 */
	@RequestMapping(value="/getBillingAddress", method=RequestMethod.POST)
	public @ResponseBody String getBillingAddress(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			 @RequestParam("patientId") String patientId, @RequestParam("physicianId") String physicianId, @RequestParam("addressOption") String addOpt,@RequestParam("clinicId") String clinicId) {
		
		
		String resp = "";
		try {
			int addressOption = Integer.parseInt(addOpt);
			if (addressOption == 3) {
				resp = prescriptionService.getPatientAddress(Integer.parseInt(patientId), addressOption, true);
			} else if (addressOption == 1 || addressOption == 2) {
				resp = prescriptionService.getPhysicianAddress(Integer.parseInt(physicianId), addressOption, true);
			} else if (addressOption == 4) { 
				resp = prescriptionService.getClinicAddress(Integer.parseInt(physicianId),Integer.parseInt(clinicId),  true);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return resp;
	}
	/**
	 * This method loads the shipping address of patient/physician/clinic
	 * @param request
	 * @param response
	 * @param session
	 * @param patientId
	 * @param physicianId
	 * @param addressOption
	 * @param clinicId
	 * @return
	 */
	@RequestMapping(value="/getShippingAddress", method=RequestMethod.POST)
	public @ResponseBody String getShippingAddress(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			 @RequestParam("patientId") String patientId, @RequestParam("physicianId") String physicianId, @RequestParam("addressOption") int addressOption,@RequestParam("clinicId") String clinicId) {
		
		String resp = "";
		try {
			if (addressOption == 3) {
				resp = prescriptionService.getPatientAddress(Integer.parseInt(patientId), addressOption, false);
			} else if (addressOption == 1 || addressOption == 2) {
				resp = prescriptionService.getPhysicianAddress(Integer.parseInt(physicianId), addressOption, false);
			} else if (addressOption == 6) { 
				resp = prescriptionService.getClinicAddress(Integer.parseInt(physicianId),Integer.parseInt(clinicId),  false);
			} else if (addressOption == 4 || addressOption == 5) {
				resp = prescriptionService.getTemporaryAddress();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return resp;
	}
	
	/**
	 * This method loads the physician account data
	 * @param model
	 * @param request
	 * @param response
	 * @param session
	 * @param form
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "/viewPhysicianAccount", method=RequestMethod.POST)
	public ModelAndView viewPhysicianAccount(Model model, HttpServletRequest request, HttpServletResponse response, HttpSession session,
			@ModelAttribute("prescription") PrescriptionForm form, RedirectAttributes redirectAttributes){
		
		ModelAndView mv = new ModelAndView();
		
		try {
			mv.setViewName("redirect:prescriptionView");
			LoginForm loggedInUser = (LoginForm) session.getAttribute("loginDetail");
			String rootFilePath= PharmacyUtil.getRootFolderForPrescriptionPDF(session, env);
			PrescriptionMaster acc=prescriptionService.savePrescription(form, rootFilePath, session, false, loggedInUser);
			if (form.getPhysicianId() > 0) {
				PhysicianProfile pform = new PhysicianProfile();
				pform.setPhysicianId(form.getPhysicianId());
				redirectAttributes.addFlashAttribute("physicianAccount", pform);
				redirectAttributes.addFlashAttribute("target", "prescription");
				redirectAttributes.addFlashAttribute("PrescriptionId", acc.getId());
				
				mv.setViewName("redirect:/physician/editPhysicianProfile");
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return mv;
	}

	/**
	 * This method loads the patient account data
	 * @param model
	 * @param request
	 * @param response
	 * @param session
	 * @param form
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "/viewPatientAccount", method=RequestMethod.POST)
	public ModelAndView viewPatientAccount(Model model, HttpServletRequest request, HttpServletResponse response, HttpSession session,
			@ModelAttribute("prescription") PrescriptionForm form, RedirectAttributes redirectAttributes){
		
		ModelAndView mv = new ModelAndView();
		
		
		try {
			mv.setViewName("redirect:prescriptionView");
			LoginForm loggedInUser = (LoginForm) session.getAttribute("loginDetail");
			String rootFilePath= PharmacyUtil.getRootFolderForPrescriptionPDF(session, env);
			PrescriptionMaster acc =prescriptionService.savePrescription(form, rootFilePath, session, false, loggedInUser);
			if (form.getPatientId() > 0) {
				PatientAccountForm pform = new PatientAccountForm();
				pform.setPatientId(form.getPatientId());
				redirectAttributes.addFlashAttribute("form", pform);
				redirectAttributes.addFlashAttribute("target", "prescription");
				redirectAttributes.addFlashAttribute("PrescriptionId", acc.getId());
				
				mv.setViewName("redirect:/patient/editPatientAccount");
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return mv;
	}
	/**
	 *  This method save and loads the prescription page
	 * @param model
	 * @param request
	 * @param response
	 * @param session
	 * @param form
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "/savePrescription", method = RequestMethod.POST)
	public ModelAndView savePrescriptionData(Model model, HttpServletRequest request, HttpServletResponse response, HttpSession session,
			 @ModelAttribute("prescription") PrescriptionForm form, RedirectAttributes redirectAttributes) {
		
		ModelAndView mv = new ModelAndView();
		
		try {
			LoginForm loggedInUser = (LoginForm) session.getAttribute("loginDetail");
			boolean isUpdate = false;
			if (form.getPrescriptionId() > 0) 
				isUpdate = true;
			String rootFilePath= PharmacyUtil.getRootFolderForPrescriptionPDF(session, env);
			PrescriptionMaster acc = prescriptionService.savePrescription(form, rootFilePath, session, true, loggedInUser);
			if (acc != null) {
				redirectAttributes.addFlashAttribute("saveStatus", "true");
				if (isUpdate)
					redirectAttributes.addFlashAttribute("message", "Prescription updated successfully");
				else
					redirectAttributes.addFlashAttribute("message", "Prescription created successfully");

				form.setPrescriptionId(acc.getId());
				redirectAttributes.addFlashAttribute("form", form);
				mv.setViewName("redirect:editPrescription");
			} else {
				model.addAttribute("message", "Prescription failed to create");
				mv.setViewName("error400");
			}
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("message", "Prescription failed to create");
			mv.setViewName("error500");
		}

		return mv;
	}
	/**
	 * This method loads the prescription account page
	 * @param model
	 * @param request
	 * @param response
	 * @param session
	 * @param form
	 * @return
	 */
	@RequestMapping( value = "/editPrescription")  
	public ModelAndView editPrescriptionAccount(Model model, HttpServletRequest request, HttpServletResponse response, HttpSession session,
			@ModelAttribute("form") PrescriptionForm form){
		ModelAndView mv = new ModelAndView();
		try {
			int id = 0, orderid=0,invoiceid=0;
			if (form != null && form.getPrescriptionId()>0)
				id = form.getPrescriptionId(); 
			
			if (form != null && form.getOrderId()>0)
				orderid = form.getOrderId(); 
			
			if (form != null && form.getInvoiceId()>0)
				invoiceid = form.getInvoiceId(); 
			
			LoginForm loggedInUser = (LoginForm) session.getAttribute("loginDetail");
			if (PharmacyUtil.userPhysician.equalsIgnoreCase(loggedInUser.getType())) {
				mv.addObject("writtenById", loggedInUser.getUserid());
			}else if (PharmacyUtil.userPhysicianAssistant.equalsIgnoreCase(loggedInUser.getType()))
			{
				mv.addObject("writtenById", loggedInUser.getPhysicianAssistantPhysicianId());
			}
			List<PrescribedItemType> prescribedItemTypeList = prescribedItemTypeService.getAllPrescribedItemType();
			
			mv.addObject("prescribedItemTypeList", prescribedItemTypeList);
			mv.addObject("typeList", prescriptionItemTypeService.getAllPrescribedItemType());
			mv.addObject("unitList", unitService.getAllPrescriptionItemDispensingUnit());
			mv.addObject("originTypeList", originTypeServie.getAllPrescriptionOriginType());
			mv.addObject("prescriptionStatusList", prescriptionStatusRepo.getAllPrescriptionStatusList());
			
			
			/*List<PrescriptionTransactionForm> prescriptionTransactionFormList = new ArrayList(); 
			
			try {
				//if(form.getRxType()!=null && form.getRxType().length()>0){
					
					//if(form.getRxType().equalsIgnoreCase("1")){
						List<PrescriptionItem> itemList = prescriptionItemService.getAllAutoCompletePrescriptionItemsList();
					
						if(itemList!=null && itemList.size()>0)
						{
							for (PrescriptionItem prescriptionItemObj :itemList) {
								PrescriptionTransactionForm obj=new PrescriptionTransactionForm();
								obj.setItemid(prescriptionItemObj.getItemid());
								obj.setItemname(prescriptionItemObj.getItemname());
								prescriptionTransactionFormList.add(obj);
								
							}
						}
						
					//}
					
					//if(form.getRxType().equalsIgnoreCase("2")){
						List<PrescriptionMedication> medicationList = prescriptionMedicationService.getAllAutoCompletePrescriptionMedicationsList();
						if(medicationList!=null && medicationList.size()>0)
						{
							for (PrescriptionMedication prescriptionMedicationObj :medicationList) {
								PrescriptionTransactionForm obj=new PrescriptionTransactionForm();
								obj.setItemid(String.valueOf(prescriptionMedicationObj.getMedicationid()));
								obj.setItemname(prescriptionMedicationObj.getMedicationdescription());
								prescriptionTransactionFormList.add(obj);
								
							}
						}
					//}
					
					
					
					
				//}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			mv.addObject("prescriptionItemsList", prescriptionTransactionFormList);*/
			
			mv.addObject("prescriptionStatusList", prescriptionStatusRepo.getAllPrescriptionStatusList());
			
			form = prescriptionService.getPrescriptionData(id,session);
			form.setOrderId(orderid);
			form.setInvoiceId(invoiceid);
			
			List<Clinic> clinicSelectedList =null;
			clinicSelectedList = clinicRepo.getAllPhysicianGroupWiseClinicListSelected(form.getGroupId(),form.getPhysicianId());
			mv.addObject("clinicList", clinicSelectedList);
			
			
			boolean isRxNoPresents = false;
			boolean isClinicDropDownNotShow = false;
			
			if (form.getMedications().size() > 0) {
				for (PrescriptionTransactionForm tran : form.getMedications()) {
					if (tran.getRxNumber() == null || "".equalsIgnoreCase(tran.getRxNumber()))
						isRxNoPresents = true;

					if (tran.getRxNumber() != null && !"".equalsIgnoreCase(tran.getRxNumber()))
						isClinicDropDownNotShow = true;
				}
			} else {
				isRxNoPresents = true;
			}
			mv.addObject("showSaveBtn", isRxNoPresents);
			mv.addObject("clinicOptNotShow", isClinicDropDownNotShow);
			

			form.setClass2ControlSubtance( prescriptionService.getClass2ControlSubtance(form.getPrescriptionId()) );
			form.setFileFtpAllowed( prescriptionService.getFileFtpAllowed(form.getPrescriptionId()) );
			form.setClinicList(clinicSelectedList);
			
			mv.addObject("prescription", form);
			
			model.addAttribute("message", model.asMap().get("message"));
			model.addAttribute("saveStatus", model.asMap().get("saveStatus"));
			mv.setViewName("prescriptionView");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return mv;
	}
	/**
	 *   This method downloads the prescription print pdf
	 * @param model
	 * @param request
	 * @param response
	 * @param session
	 * @param prescriptionId
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "/prescriptionPrintPdfDownload", method = RequestMethod.POST)
	public @ResponseBody String pdfDownloadPrescription(Model model, HttpServletRequest request, HttpServletResponse response, HttpSession session,
			@RequestParam("prescriptionId") String prescriptionId, RedirectAttributes redirectAttributes) {
		String output="";
		try {
			//System.out.println("Prescription PDF download File id ---> " + prescriptionId);
			if (Integer.parseInt(prescriptionId)  > 0) {
				PrescriptionForm form = prescriptionService.getPrescriptionData(Integer.parseInt(prescriptionId),session);
				
				String downloadName="Prescription_"+form.getPrescriptionOrderNumber()+"_esigned.pdf";
				String rootFilePath= PharmacyUtil.getRootFolderForPrescriptionPDF(session, env);
				String targetFolder = rootFilePath + File.separator + form.getPrescriptionId();
				String fileName = targetFolder + File.separatorChar + "Prescription_"+form.getPrescriptionOrderNumber()+"_esigned.pdf";

				boolean fileAvailable = PharmacyUtil.downloadFile(fileName, downloadName, response);
				if(!fileAvailable){
					output = "Prescription has not been E-Signed by the Prescriber";
					return output;
				}else{
					output = "success";
					return output;
				}
						
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	/**
	 *   This method downloads the controlled substance prescription print pdf
	 * @param model
	 * @param request
	 * @param response
	 * @param session
	 * @param prescriptionId
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "/csprescriptionPrintPdfDownload", method = RequestMethod.POST)
	public @ResponseBody String csprescriptionPrintPdfDownload(Model model, HttpServletRequest request, HttpServletResponse response, HttpSession session,
			@RequestParam("prescriptionId") String prescriptionId, RedirectAttributes redirectAttributes) {
		String output="";
		try {
			//System.out.println("Controlled Substance Prescription PDF download File id ---> " + prescriptionId);
			if (Integer.parseInt(prescriptionId)  > 0) {
				PrescriptionForm form = prescriptionService.getPrescriptionData(Integer.parseInt(prescriptionId),session);
				
				String downloadName="PrescriptionControlSubstance_"+form.getPrescriptionOrderNumber()+"_esigned.pdf";
				String rootFilePath= PharmacyUtil.getRootFolderForPrescriptionPDF(session, env);
				String targetFolder = rootFilePath + File.separator + form.getPrescriptionId();
				String fileName = targetFolder + File.separatorChar + "PrescriptionControlSubstance_"+form.getPrescriptionOrderNumber()+"_esigned.pdf";

				boolean fileAvailable = PharmacyUtil.downloadFile(fileName, downloadName, response);
				if(!fileAvailable){
					output = "Prescription has not been E-Signed by the Prescriber";
					return output;
				}else{
					output = "success";
					return output;
				}
						
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	/**
	 *  This method downloads the prescription print pdf
	 * @param model
	 * @param request
	 * @param response
	 * @param session
	 * @param form
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "/prescriptionPdfDownload", method = RequestMethod.POST)
	public ModelAndView pdfDownloadPrescription(Model model, HttpServletRequest request, HttpServletResponse response, HttpSession session,
			 @ModelAttribute("prescription") PrescriptionForm form, RedirectAttributes redirectAttributes) {
		ModelAndView mv = new ModelAndView();
		try {
			//System.out.println("prescription PDF download File id ---> " + form.getPrescriptionId());
			if (form.getPrescriptionId() > 0) {
				form = prescriptionService.getPrescriptionData(form.getPrescriptionId(),session);
				
				String downloadName="Prescription_"+form.getPrescriptionOrderNumber()+"_esigned.pdf";
				String rootFilePath= PharmacyUtil.getRootFolderForPrescriptionPDF(session, env);
				String targetFolder = rootFilePath + File.separator + form.getPrescriptionId();
				String fileName = targetFolder + File.separatorChar + "Prescription_"+form.getPrescriptionOrderNumber()+"_esigned.pdf";
				
				boolean fileAvailable = PharmacyUtil.downloadFile(fileName, downloadName, response);
				if(!fileAvailable){
					redirectAttributes.addFlashAttribute("message", "Prescription has not been E-Signed by the Prescriber");
					redirectAttributes.addFlashAttribute("form", form);
					mv.setViewName("redirect:editPrescription");
					return mv;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 *  This method downloads the controlled substance prescription print pdf
	 * @param model
	 * @param request
	 * @param response
	 * @param session
	 * @param form
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "/prescriptionCSPdfDownload", method = RequestMethod.POST)
	public ModelAndView cspdfDownloadPrescription(Model model, HttpServletRequest request, HttpServletResponse response, HttpSession session,
			 @ModelAttribute("prescription") PrescriptionForm form, RedirectAttributes redirectAttributes) {
		ModelAndView mv = new ModelAndView();
		try {
			//System.out.println("prescription PDF download File id ---> " + form.getPrescriptionId());
			if (form.getPrescriptionId() > 0) {
				form = prescriptionService.getPrescriptionData(form.getPrescriptionId(),session);
				
				String downloadName="Prescription_"+form.getPrescriptionOrderNumber()+"_esigned.pdf";
				String rootFilePath= PharmacyUtil.getRootFolderForPrescriptionPDF(session, env);
				String targetFolder = rootFilePath + File.separator + form.getPrescriptionId();
				String fileName = targetFolder + File.separatorChar + "Prescription_"+form.getPrescriptionOrderNumber()+"_esigned.pdf";
				
				if (form.getMedications().size()> 0){
					for (int i = 0; i < form.getMedications().size(); i++) {
						if (!"N".equalsIgnoreCase(form.getMedications().get(i).getControlSubstance())) {
							fileName = targetFolder + File.separatorChar + "PrescriptionControlSubstance_"+form.getPrescriptionOrderNumber()+"_esigned.pdf";
							downloadName="PrescriptionControlSubstance_"+form.getPrescriptionOrderNumber()+"_esigned.pdf";
						}
					}
				}
				
				boolean fileAvailable = PharmacyUtil.downloadFile(fileName, downloadName, response);
				if(!fileAvailable){
					redirectAttributes.addFlashAttribute("message", "Prescription has not been E-Signed by the Prescriber");
					redirectAttributes.addFlashAttribute("form", form);
					mv.setViewName("redirect:editPrescription");
					return mv;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	/**
	 *  This method uploads the prescription print pdf to the remote FTP
	 * @param model
	 * @param request
	 * @param response
	 * @param session
	 * @param prescriptionId
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "/prescriptionPdfFtpUpload", method = RequestMethod.POST)
	public @ResponseBody String pdfFtpUploadPrescription(Model model, HttpServletRequest request, HttpServletResponse response, HttpSession session,
			@RequestParam("prescriptionId") String prescriptionId, RedirectAttributes redirectAttributes) {
		String output="";
		//System.out.println("prescription FTP Upload PDF File id ---> " + prescriptionId);
		if (Integer.parseInt(prescriptionId) > 0) {
			boolean isTransferSuccess = prescriptionService.prescriptionPdfFTPUpload(prescriptionId, session);
			if (isTransferSuccess)
				output = "Prescription PDF successfully uploaded to Remote FTP";
			else
				//output = "Failed to transfer Prescription PDF";
				output ="Click to eSign below and Sign the Prescription before sending the RX to CRE8 Pharmacy";
		}
		return output;
	}
	/**
	 *  This method validates whether the given item in the prescription is controlled substance or not
	 * @param model
	 * @param request
	 * @param response
	 * @param session
	 * @param itemId
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value="/pineerSubstanceItemCheck", method=RequestMethod.POST)
	public @ResponseBody String pineerControlSubstanceItemCheck(Model model, HttpServletRequest request, HttpServletResponse response, HttpSession session,
			@RequestParam("itemId") String itemId, RedirectAttributes redirectAttributes) {
		
		String output="";
		try {
			if (itemId != null && !"".equalsIgnoreCase(itemId)) {
				output = prescriptionService.pineerItemControlSubstanceCheck(itemId);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return output;
	}
	/**
	 *  This method stamps the esignature of the physician to the prescription
	 * @param model
	 * @param request
	 * @param response
	 * @param session
	 * @param form
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value="/savePhysicianPrescriptionSignature", method=RequestMethod.POST)
	public ModelAndView savePhysicianPrescriptionSignature(Model model,HttpServletRequest request, HttpServletResponse response, HttpSession session,
														   @ModelAttribute("prescription") PrescriptionForm form, RedirectAttributes redirectAttributes) {
		ModelAndView mv = new ModelAndView();
		try {
			if (form.getBase64ImgString() != null && form.getBase64ImgString() .length()>0 && form.getPrescriptionId()>0 && form.getPhysicianId()>0) {
				prescriptionService.savePhysicianPrescriptionSignature(form.getBase64ImgString(),form.getPrescriptionId(),form.getPhysicianId(),session,form);
				redirectAttributes.addFlashAttribute("saveStatus", "true");
				redirectAttributes.addFlashAttribute("message", "Prescription have been successfully E-Signed by the Prescriber");
				redirectAttributes.addFlashAttribute("form", form);
				mv.setViewName("redirect:editPrescription");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}
	/**
	 *  This method saves prescription and stamps the esignature of the physician to the prescription
	 * @param model
	 * @param request
	 * @param response
	 * @param session
	 * @param form
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value="/savePhysicianPrescriptionAndSignature", method=RequestMethod.POST)
	public ModelAndView savePhysicianPrescriptionAndSignature(Model model,HttpServletRequest request, HttpServletResponse response, HttpSession session,
			 @ModelAttribute("prescription") PrescriptionForm form, RedirectAttributes redirectAttributes) {
		ModelAndView mv = new ModelAndView();
		try {
			if (form.getBase64ImgString() != null && form.getBase64ImgString() .length()>0 && form.getPrescriptionId()>0 && form.getPhysicianId()>0) {
				String rootFilePath= PharmacyUtil.getRootFolderForPrescriptionPDF(session, env);
				LoginForm loggedInUser = (LoginForm) session.getAttribute("loginDetail");
				prescriptionService.savePrescription(form, rootFilePath, session, false, loggedInUser);
				prescriptionService.savePhysicianPrescriptionSignature(form.getBase64ImgString(),form.getPrescriptionId(),form.getPhysicianId(),session,form);
				redirectAttributes.addFlashAttribute("saveStatus", "true");
				redirectAttributes.addFlashAttribute("message", "Prescription have been successfully Saved & E-Signed by the Prescriber");
				redirectAttributes.addFlashAttribute("form", form);
				mv.setViewName("redirect:editPrescription");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}
	
	
	/**
	 * This method uploads the controlled substance document
	 * @param request
	 * @param response
	 * @param session
	 * @param uploadDocFile
	 * @param prescriptionId
	 * @param scan
	 * @param fax
	 * @param isHardCopy
	 * @param description
	 * @return
	 */
	@RequestMapping(value = "/subtanceDocFileUpload", method = RequestMethod.POST)
	public @ResponseBody String saveSubtanceDocFiles(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			@RequestParam("docFile") CommonsMultipartFile uploadDocFile, @RequestParam("prescriptionId") String prescriptionId,
			@RequestParam("scan") String scan, @RequestParam("fax") String fax, @RequestParam("isHardCopy") String isHardCopy,
			@RequestParam("description") String description) {
		
		LoginForm loggedInUser = (LoginForm) session.getAttribute("loginDetail");
		
		try {
			String rootFilePath=env.getProperty("file.root_folder_path");
			boolean isSaved = prescriptionService.saveSubtanceDocFiles(uploadDocFile, Integer.parseInt(prescriptionId), rootFilePath, loggedInUser,
					scan, fax, isHardCopy, description);
			if (isSaved) {
				//System.out.println("Document uploaded successfully");
			} else {
				//System.out.println("Fail to upload Document");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}	
	
	
	/**
	 * This method loads the controlled substance document data
	 * @param draw
	 * @param start
	 * @param len
	 * @param orderColumn
	 * @param orderDir
	 * @param searchTerm
	 * @param prescriptionId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getSubtanceItemsDocumentsData", method = RequestMethod.POST)
	public @ResponseBody String getPhysicianDocumentData(@RequestParam("draw") int draw, @RequestParam("start") int start, 
			@RequestParam("length") int len,@RequestParam("order[0][column]") int orderColumn, @RequestParam("order[0][dir]") String orderDir,
			
			@RequestParam("search[value]") String searchTerm, @RequestParam("p") String prescriptionId, HttpServletRequest request) {
		
		return subtanceDocService.getSubtanceDocumentDataList(draw, start, len, orderColumn, orderDir, searchTerm, prescriptionId );
	}
	
	/**
	 * This method deletes the controlled substance document 
	 * @param request
	 * @param response
	 * @param session
	 * @param fileId
	 * @param prescriptionId
	 * @return
	 */
	@RequestMapping(value = "/subtanceDocFileDelete", method = RequestMethod.POST)
	public @ResponseBody String deleteDocFiles(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			 @RequestParam("fileId") String fileId, @RequestParam("preId") String prescriptionId){
		//System.out.println("deleteDocFiles ---> " + fileId);
		try {
			boolean isDeleted = subtanceDocService.deletePrescriptionDocFiles( Integer.parseInt(fileId) );
			if (isDeleted) {
				//System.out.println("Document deleted successfully");
			} else {
				//System.out.println("Fail to delete Document");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * This method downloads the controlled substance document data
	 * @param request
	 * @param response
	 * @param session
	 * @param fileId
	 * @param prescriptionId
	 */
	@RequestMapping(value = "/subtanceDocFileDownload", method = RequestMethod.GET)
	public void downloadDocFiles(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			 @RequestParam("f") String fileId, @RequestParam("p") String prescriptionId){
		
		try {
			SubtanceItemDocuments file = subtanceDocService.getSubtanceFile(Integer.parseInt(fileId));
			String fileName = "";
			String downloadName = "";
			if (file != null) {
				fileName = file.getStoredFielName();
				downloadName = file.getOriginalFileName();
			}
			PharmacyUtil.downloadFile(fileName, downloadName, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 *  This method navigates to the order page related to prescription
	 * @param model
	 * @param form
	 * @param request
	 * @param response
	 * @param session
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "/backToPrescriptionOrder", method = RequestMethod.POST)
	public ModelAndView backToPrescriptionOrder(Model model, @ModelAttribute("prescription")  PrescriptionForm form, 
			HttpServletRequest request, HttpServletResponse response, HttpSession session, RedirectAttributes redirectAttributes) {
		ModelAndView mv = new ModelAndView();
		
		if (form.getOrderId() > 0) {
			OrderForm orderForm = orderMasterService.getOrderDetails(form.getOrderId());
			mv.addObject("orderForm", orderForm);
			mv.setViewName("viewOrders");
		}else {
			mv.setViewName("error500");
		}
		
		return mv;
	}
	/**
	 * This method navigates to the invoice page related to prescription
	 * @param model
	 * @param form
	 * @param request
	 * @param response
	 * @param session
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "/backToPrescriptionInvoice", method = RequestMethod.POST)
	public ModelAndView backToPrescriptionInvoice(Model model, @ModelAttribute("prescription")  PrescriptionForm form, 
			HttpServletRequest request, HttpServletResponse response, HttpSession session, RedirectAttributes redirectAttributes) {
		ModelAndView mv = new ModelAndView();
		
		int invoiceId = form.getInvoiceId();
		if (form.getInvoiceId() > 0) {
				InvoiceForm invoiceform = invoiceMasterService.getInvoiceData(invoiceId);
				mv.addObject("invoiceForm", invoiceform);
				model.addAttribute("message", model.asMap().get("message"));
				mv.setViewName("viewInvoice");
			}
			
		else {
			mv.setViewName("error500");
		}
		
		return mv;
	}
	/**
	 * This method validates whether the RX already created in the prescription
	 * @param model
	 * @param request
	 * @param response
	 * @param session
	 * @param rxItemFormId
	 * @param patientFormId
	 * @param writtenByFormId
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value="/duplicateRXAlreadyExists", method=RequestMethod.POST)
	public @ResponseBody String[] duplicateRXAlreadyExists(Model model, HttpServletRequest request, HttpServletResponse response, HttpSession session,
			@RequestParam("rxItemFormId") String rxItemFormId, @RequestParam("patientFormId") int patientFormId, @RequestParam("writtenByFormId") int writtenByFormId, RedirectAttributes redirectAttributes) {
		
		String[] output=new String[8];
		if (rxItemFormId!=null && rxItemFormId.length()>0) {
			output = prescriptionService.duplicateRXAlreadyExists(rxItemFormId,patientFormId,writtenByFormId);
		}

		return output;
	}
	
	/**
	 * This method validates whether the prscrption item is a controlled substance or not
	 * @param model
	 * @param request
	 * @param response
	 * @param session
	 * @param itemId
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value="/pineerSubstancePrescriptionItemheck", method=RequestMethod.POST)
	public @ResponseBody String pineerSubstancePrescriptionItemheck(Model model, HttpServletRequest request, HttpServletResponse response, HttpSession session,
			@RequestParam("itemId") String itemId, RedirectAttributes redirectAttributes) {
		
		String output="";
		if (itemId != null && !"".equalsIgnoreCase(itemId)) {
			output = prescriptionService.pineerSubstancePrescriptionItemheck(itemId);
		}

		return output;
	}
}


