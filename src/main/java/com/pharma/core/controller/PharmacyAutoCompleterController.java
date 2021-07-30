package com.pharma.core.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pharma.core.formBean.LoginForm;
import com.pharma.core.formBean.prescription.PrescriptionTransactionForm;
import com.pharma.core.model.clinic.Clinic;
import com.pharma.core.model.patientaccount.PatientAccount;
import com.pharma.core.model.physician.PhysicianAccount;
import com.pharma.core.model.physician.PhysicianAssistantAccount;
import com.pharma.core.model.pioneer.PrescriptionDiagnosisICD10;
import com.pharma.core.model.pioneer.PrescriptionItem;
import com.pharma.core.model.pioneer.PrescriptionMedication;
import com.pharma.core.model.pioneer.PrescriptionSigCodes;
import com.pharma.core.pharmaservices.clinic.ClinicService;
import com.pharma.core.pharmaservices.patientservices.PatientAccountService;
import com.pharma.core.pharmaservices.physicianservices.PhysicianAccountService;
import com.pharma.core.pharmaservices.physicianservices.PhysicianAssistantAccountService;
import com.pharma.core.pharmaservices.pioneer.PrescriptionDiagnosisICD10Service;
import com.pharma.core.pharmaservices.pioneer.PrescriptionItemService;
import com.pharma.core.pharmaservices.pioneer.PrescriptionMedicationService;
import com.pharma.core.pharmaservices.pioneer.PrescriptionSigCodesService;
import com.pharma.core.util.PharmacyUtil;
/**
 * This class is a controller to load the autocomplete drop down data
 *
 */
@Controller
public class PharmacyAutoCompleterController {
	
	@Autowired
	PhysicianAccountService physicianService;
	
	@Autowired
	PhysicianAssistantAccountService phyAssistService;
	
	@Autowired
	ClinicService clinicService;
	
	@Autowired
	PatientAccountService patientAccountService;
	
	@Autowired
	PrescriptionMedicationService prescriptionMedicationService;
	
	@Autowired
	PrescriptionDiagnosisICD10Service prescriptionDiagnosisICD10Service;
	
	@Autowired
	PrescriptionSigCodesService prescriptionSigCodesService;
	
	@Autowired
	PrescriptionItemService prescriptionItemService;
	/**
	 * This method loads the autocomplete physician list summary page
	 * @param query
	 * @param formId
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/getAutoCompletePhysiciansList", method = RequestMethod.GET)
	public @ResponseBody List<PhysicianAccount> getAutoCompletePhysiciansList(@RequestParam("term") String query,@RequestParam("formId") int formId,
			HttpServletResponse response,HttpSession session) {
		
		List<PhysicianAccount> physicianList = null;
		
		try {
			LoginForm loggedInUser = (LoginForm) session.getAttribute("loginDetail");
			String userType=loggedInUser.getType();
			if (userType.equalsIgnoreCase(PharmacyUtil.userGroupDirector)) {
				//physicianList = physicianService.getAutoCompletePhysicianList(query,PharmacyUtil.statusApproved,formId,loggedInUser.getGroupid());
				physicianList = physicianService.getAutoCompleteGroupPhysicianList(query, formId, loggedInUser.getGroupid());
			} else if (userType.equalsIgnoreCase(PharmacyUtil.userPhysician)) {
				physicianList = physicianService.getAutoCompletePhysicianListByPhysician(query,PharmacyUtil.statusApproved,formId,loggedInUser.getUserid());
			} else if (userType.equalsIgnoreCase(PharmacyUtil.userPhysicianAssistant)) {
//			PhysicianAssistantAccount assistant = phyAssistService.getPhysicianAssistantAccountDetails(loggedInUser.getUserid());
				//temp commented on jan 19,2018
				//physicianList = physicianService.getAutoCompletePhysicianListByPhysician(query,PharmacyUtil.statusApproved,formId, assistant.getPhysicianId());
				physicianList = physicianService.getAutoCompletePhysicianListByPhysician(query,PharmacyUtil.statusApproved,formId, loggedInUser.getPhysicianAssistantPhysicianId());
			} else {
				physicianList = physicianService.getAutoCompletePhysicianList(query, formId);
				// physicianList = physicianService.getAutoCompletePhysicianList(query,PharmacyUtil.statusApproved,formId);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		return physicianList;
	}
	/**
	 * This method loads the autocomplete physician summary list
	 * @param query
	 * @param formId
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/getAutoCompletePhysiciansSummaryList", method = RequestMethod.GET)
	public @ResponseBody List<PhysicianAccount> getAutoCompletePhysiciansSummaryList(@RequestParam("term") String query,@RequestParam("formId") int formId,
			HttpServletResponse response,HttpSession session) {
		
		List<PhysicianAccount> physicianList = null;
		
		try {
			LoginForm loggedInUser = (LoginForm) session.getAttribute("loginDetail");
			String userType=loggedInUser.getType();
			if (userType.equalsIgnoreCase(PharmacyUtil.userGroupDirector)) {
				physicianList = physicianService.getAutoCompleteGroupPhysicianList(query, formId, loggedInUser.getGroupid());
			} else if (userType.equalsIgnoreCase(PharmacyUtil.userPhysician)) {
				physicianList = physicianService.getAllAutoCompletePhysicianListByPhysician(query,formId,loggedInUser.getUserid());
			} else if (userType.equalsIgnoreCase(PharmacyUtil.userPhysicianAssistant)) {
				physicianList = physicianService.getAllAutoCompletePhysicianListByPhysician(query,formId, loggedInUser.getPhysicianAssistantPhysicianId());
			} else {
				physicianList = physicianService.getAutoCompletePhysicianList(query, formId);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		return physicianList;
	}
	/**
	 * This method loads the autocomplete approved physician list
	 * @param query
	 * @param formId
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/getAutoCompleteApprovedPhysiciansList", method = RequestMethod.GET)
	public @ResponseBody List<PhysicianAccount> getAutoCompleteApprovedPhysiciansList(@RequestParam("term") String query,@RequestParam("formId") int formId,
			HttpServletResponse response,HttpSession session) {
		
		List<PhysicianAccount> physicianList = null;
		
		try {
			LoginForm loggedInUser = (LoginForm) session.getAttribute("loginDetail");
			String userType=loggedInUser.getType();
			if (userType.equalsIgnoreCase(PharmacyUtil.userGroupDirector)) {
				physicianList = physicianService.getAutoCompleteApprovedPhysicianList(query,PharmacyUtil.statusApproved,formId,loggedInUser.getGroupid());
			} else if (userType.equalsIgnoreCase(PharmacyUtil.userPhysician)) {
				physicianList = physicianService.getAutoCompleteApprovedPhysicianListByPhysician(query,PharmacyUtil.statusApproved,formId,loggedInUser.getUserid());
			} else if (userType.equalsIgnoreCase(PharmacyUtil.userPhysicianAssistant)) {
//			PhysicianAssistantAccount assistant = phyAssistService.getPhysicianAssistantAccountDetails(loggedInUser.getUserid());
				//temp commented on jan 19,2018
				//physicianList = physicianService.getAutoCompleteApprovedPhysicianListByPhysician(query,PharmacyUtil.statusApproved,formId, assistant.getPhysicianId());
				physicianList = physicianService.getAutoCompleteApprovedPhysicianListByPhysician(query,PharmacyUtil.statusApproved,formId,loggedInUser.getPhysicianAssistantPhysicianId());
			} else
				physicianList = physicianService.getAutoCompleteApprovedPhysicianList(query,PharmacyUtil.statusApproved,formId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		return physicianList;
	}
	
	/**
	 * This method loads the autocomplete physician list
	 * @param query
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/getAllAutoCompletePhysiciansList", method = RequestMethod.GET)
	public @ResponseBody List<PhysicianAccount> getAllAutoCompletePhysiciansList(@RequestParam("term") String query,
			HttpServletResponse response,HttpSession session) {
		
		
		List<PhysicianAccount> physicianList = null;
		
		try {
			LoginForm loggedInUser = (LoginForm) session.getAttribute("loginDetail");
			String userType=loggedInUser.getType();
			if (userType.equalsIgnoreCase(PharmacyUtil.userGroupDirector)) {
				physicianList = physicianService.getAllAutoCompletePhysicianList(query,loggedInUser.getGroupid());
			}else
				physicianList = physicianService.getAllAutoCompletePhysicianList(query);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		return physicianList;
	}
	
	/**
	 * This method loads the autocomplete physician assistant list
	 * @param query
	 * @param formId
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/getAutoCompletePhysicianAssistantsList", method = RequestMethod.GET)
	public @ResponseBody List<PhysicianAssistantAccount> getAutoCompletePhysicianAssistantsList(@RequestParam("term") String query,@RequestParam("formId") int formId,
			HttpServletResponse response,HttpSession session) {
		
		List<PhysicianAssistantAccount> physicianAsstList=null;
		
		try {
			LoginForm loggedInUser = (LoginForm) session.getAttribute("loginDetail");
			String userType=loggedInUser.getType();
			if (userType.equalsIgnoreCase(PharmacyUtil.userGroupDirector)) {
				
				physicianAsstList = phyAssistService.getAutoCompleteGroupWisePhysicianAssistantList(query,PharmacyUtil.statusActive,formId,loggedInUser.getGroupid());
				
			} else if (userType.equalsIgnoreCase(PharmacyUtil.userPhysician)) {
				physicianAsstList = phyAssistService.getAutoCompletePhysicianWisePhysicianAssistantList(query,PharmacyUtil.statusActive,formId,loggedInUser.getUserid());
			}else
				physicianAsstList = phyAssistService.getAutoCompletePhysicianAssistantList(query,PharmacyUtil.statusActive,formId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
				
		return physicianAsstList;
	}
	/**
	 * This method loads the autocomplete physician list
	 * @param query
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/getAllAutoCompletePhysicianAssistantsList", method = RequestMethod.GET)
	public @ResponseBody List<PhysicianAssistantAccount> getAllAutoCompletePhysicianAssistantsList(@RequestParam("term") String query,
			HttpServletResponse response,HttpSession session) {
		List<PhysicianAssistantAccount> physicianAsstList=null;
		try {
			LoginForm loggedInUser = (LoginForm) session.getAttribute("loginDetail");
			String userType=loggedInUser.getType();
			
			if (userType.equalsIgnoreCase(PharmacyUtil.userGroupDirector)) {
				physicianAsstList = phyAssistService.getAllAutoCompleteGroupWisePhysicianAssistantList(query,loggedInUser.getGroupid());
			} else if (userType.equalsIgnoreCase(PharmacyUtil.userPhysician)) {
				
				physicianAsstList = phyAssistService.getAllAutoCompletePhysicianWisePhysicianAssistantList(query,loggedInUser.getUserid());
			} else			
				physicianAsstList = phyAssistService.getAllAutoCompletePhysicianAssistantList(query);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		return physicianAsstList;
	}
	/**
	 * This method loads the autocomplete clinic list
	 * @param query
	 * @param formId
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getAutoCompleteClinicList", method = RequestMethod.GET)
	public @ResponseBody List<Clinic> getAutoCompleteClinicList(@RequestParam("term") String query,@RequestParam("formId") int formId,
			HttpServletResponse response) {
		
		try {
			List<Clinic> clinicList = clinicService.getAutoCompleteClinicList(query,PharmacyUtil.statusActive,formId);
					
			return clinicList;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * This method loads the autocomplete clinic list
	 * @param query
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getAllAutoCompleteClinicList", method = RequestMethod.GET)
	public @ResponseBody List<Clinic> getAllAutoCompleteClinicList(@RequestParam("term") String query,
			HttpServletResponse response) {
		
		try {
			List<Clinic> clinicList = clinicService.getAllAutoCompleteClinicList(query);
					
			return clinicList;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * This method loads the autocomplete patient list
	 * @param query
	 * @param formId
	 * @param physicianId
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getAutoCompletePatientList", method = RequestMethod.GET)
	public @ResponseBody List<PatientAccount> getAutoCompletePatientList(@RequestParam("term") String query,@RequestParam("formId") int formId,
			@RequestParam("physicianId") int physicianId, HttpServletResponse response) {
		
		try {
			List<PatientAccount> patientList = patientAccountService.getAutoCompletePatientList(query,PharmacyUtil.statusApproved,formId);
					
			return patientList;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * This method loads the autocomplete physician's patient list
	 * @param query
	 * @param formId
	 * @param physicianId
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/getAutoCompletePatientListByPhysician", method = RequestMethod.GET)
	public @ResponseBody List<PatientAccount> getAutoCompletePatientListByPhysician(@RequestParam("term") String query,@RequestParam("formId") int formId,
			@RequestParam("physicianId") int physicianId, HttpServletResponse response, HttpSession session) {
		
		try {
			List<PatientAccount> patientList = null;
			patientList = patientAccountService.getAutoCompletePatientListByPhysicianId(query,PharmacyUtil.statusApproved,formId, physicianId);
					
			return patientList;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * This method loads the autocomplete patient prescription list by physician
	 * @param query
	 * @param formId
	 * @param physicianId
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/getAutoCompletePrescriptionPatientListByPhysician", method = RequestMethod.GET)
	public @ResponseBody List<PatientAccount> getAutoCompletePrescriptionPatientListByPhysician(@RequestParam("term") String query,@RequestParam("formId") int formId,
			@RequestParam("physicianId") int physicianId, HttpServletResponse response, HttpSession session) {
		
		try {
			List<PatientAccount> patientList = null;
			patientList = patientAccountService.getAutoCompletePrescriptionPatientListByPhysicianId(query,formId, physicianId);
					
			return patientList;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * This method loads the autocomplete patient list
	 * @param query
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/getAllAutoCompletePatientList", method = RequestMethod.GET)
	public @ResponseBody List<PatientAccount> getAllAutoCompletePatientList(@RequestParam("term") String query,
			HttpServletResponse response, HttpSession session) {
		LoginForm loggedInUser = (LoginForm) session.getAttribute("loginDetail");
		List<PatientAccount> patientList = null;

		if (loggedInUser.getType().equalsIgnoreCase(PharmacyUtil.userGroupDirector)) {
			patientList = patientAccountService.getAutoCompletePatientListByGroupId(query, 0, loggedInUser.getGroupid());
		} else if (loggedInUser.getType().equalsIgnoreCase(PharmacyUtil.userPhysician)) {
			patientList = patientAccountService.getAutoCompleteAllPatientListByPhysicianId(query, 0, loggedInUser.getUserid());
		} else if (loggedInUser.getType().equalsIgnoreCase(PharmacyUtil.userPhysicianAssistant)) {
			patientList = patientAccountService.getAutoCompleteAllPatientListByPhysicianId(query, 0, loggedInUser.getPhysicianAssistantPhysicianId());
		} else
			patientList = patientAccountService.getAllAutoCompletePatientList(query);

		return patientList;
	}
	
	/**
	 * This method loads the autocomplete prescription item list
	 * @param query
	 * @param formId
	 * @param typeId
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getAutoCompletePrescriptionItemList", method = RequestMethod.GET)
	public @ResponseBody List<PrescriptionMedication> getAutoCompletePrescriptionItemList(@RequestParam("term") String query,@RequestParam("formId") int formId,@RequestParam("typeId") int typeId,HttpServletResponse response) {
		
		try {
			List<PrescriptionMedication> medicationList = prescriptionMedicationService.getAutoCompletePrescriptionMedicationList(query,formId,typeId);
					
			return medicationList;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		return null;
	}
	/**
	 *  This method loads the autocomplete prescription item list
	 * @param query
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getAllAutoCompletePrescriptionItemList", method = RequestMethod.GET)
	public @ResponseBody List<PrescriptionMedication> getAllAutoCompletePrescriptionItemList(@RequestParam("term") String query,
			HttpServletResponse response) {
		
		try {
			List<PrescriptionMedication> medicationList = prescriptionMedicationService.getAllAutoCompletePrescriptionMedicationList(query);
					
			return medicationList;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	/**
	 *  This method loads the autocomplete diagnosis icd10 list
	 * @param query
	 * @param formId
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getAutoCompleteDiagnosisICD10List", method = RequestMethod.GET)
	public @ResponseBody List<PrescriptionDiagnosisICD10> getAutoCompleteDiagnosisICD10List(@RequestParam("term") String query,@RequestParam("formId") String formId,HttpServletResponse response) {
		
		try {
			List<PrescriptionDiagnosisICD10> icd10List = prescriptionDiagnosisICD10Service.getAutoCompleteDiagnosisICD10List(query,formId);
					
			return icd10List;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	/**
	 *   This method loads the autocomplete diagnosis icd10 list
	 * @param query
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getAllAutoCompleteDiagnosisICD10List", method = RequestMethod.GET)
	public @ResponseBody List<PrescriptionDiagnosisICD10> getAllAutoCompleteDiagnosisICD10List(@RequestParam("term") String query,HttpServletResponse response) {
		
		try {
			List<PrescriptionDiagnosisICD10> icd10List = prescriptionDiagnosisICD10Service.getAllAutoCompleteDiagnosisICD10List(query);
					
			return icd10List;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * This method loads the autocomplete prescription Sig list
	 * @param query
	 * @param formId
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getAutoCompletePrescriptionSigList", method = RequestMethod.GET)
	public @ResponseBody List<PrescriptionSigCodes> getAutoCompletePrescriptionSigList(@RequestParam("term") String query,@RequestParam("formId") String formId,HttpServletResponse response) {
		
		try {
			List<PrescriptionSigCodes> sigList = prescriptionSigCodesService.getAutoCompletePrescriptionSigList(query,formId);
			return sigList;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * This method loads the autocomplete prescription Sig list
	 * @param query
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getAllAutoCompletePrescriptionSigList", method = RequestMethod.GET)
	public @ResponseBody List<PrescriptionSigCodes> getAllAutoCompletePrescriptionSigList(@RequestParam("term") String query,HttpServletResponse response) {
		
		try {
			List<PrescriptionSigCodes> sigList = prescriptionSigCodesService.getAllAutoCompletePrescriptionSigList(query);
			return sigList;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * This method loads the autocomplete prescription item list
	 * @param query
	 * @param formId
	 * @param typeId
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/getAutoCompleteItemsList", method = RequestMethod.GET)
	public @ResponseBody List<PrescriptionTransactionForm> getAutoCompleteItemList(@RequestParam("term") String query,@RequestParam("formId") int formId,@RequestParam("typeId") int typeId,
			HttpServletResponse response, HttpSession session) {
		
		
		
		List<PrescriptionTransactionForm> prescriptionTransactionFormList = new ArrayList(); 
		List<Object[]> resultList = null;
	
		try {
			LoginForm loggedInUser = (LoginForm) session.getAttribute("loginDetail");
			if(typeId>0){
				
				if(typeId==1 || typeId==3){
					List<PrescriptionItem> itemList = prescriptionItemService.getAutoCompletePrescriptionItemsList(query,String.valueOf(formId));
				
					if(itemList!=null && itemList.size()>0)
					{
						for (PrescriptionItem prescriptionItemObj :itemList) {
							PrescriptionTransactionForm obj=new PrescriptionTransactionForm();
							obj.setItemid(prescriptionItemObj.getItemid());
							obj.setItemname(prescriptionItemObj.getItemname());
							prescriptionTransactionFormList.add(obj);
							
						}
					}
					
				}
				
				if(typeId==2){
					List<PrescriptionMedication> medicationList = prescriptionMedicationService.getAutoCompletePrescriptionMedicationsList(query,formId);
					if(medicationList!=null && medicationList.size()>0)
					{
						for (PrescriptionMedication prescriptionMedicationObj :medicationList) {
							PrescriptionTransactionForm obj=new PrescriptionTransactionForm();
							obj.setMedicationid(prescriptionMedicationObj.getMedicationid());
							obj.setMedicationdescription(prescriptionMedicationObj.getMedicationdescription());
							prescriptionTransactionFormList.add(obj);
							
						}
					}
				}
				
				
				
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return prescriptionTransactionFormList;
	}
	
}
