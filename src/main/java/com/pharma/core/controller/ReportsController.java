package com.pharma.core.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.pharma.core.formBean.LoginForm;
import com.pharma.core.formBean.reports.PatientsList;
import com.pharma.core.model.pharmacygroup.GroupMaster;
import com.pharma.core.model.physician.PhysicianAccount;
import com.pharma.core.model.physician.PhysicianAssistantAccount;
import com.pharma.core.model.pioneer.PrescriptionRxStatusType;
import com.pharma.core.model.pioneer.PrescriptionRxTransactionStatusType;
import com.pharma.core.pharmaservices.pharmacygroupservices.GroupMasterService;
import com.pharma.core.pharmaservices.pioneer.PrescriptionRxStatusTypeService;
import com.pharma.core.pharmaservices.pioneer.PrescriptionRxTransactionStatusTypeService;
import com.pharma.core.pharmaservices.reports.ReportService;
import com.pharma.core.repository.clinic.ClinicRepository;
import com.pharma.core.repository.pharmacygroup.GroupDirectorAccountRespository;
import com.pharma.core.repository.pharmacygroup.GroupMasterRespository;
import com.pharma.core.repository.physician.PhysicianAccountRespository;
import com.pharma.core.repository.physician.PhysicianAssistantAccountRespository;
import com.pharma.core.repository.physician.PhysicianGroupRepository;
import com.pharma.core.util.PharmacyUtil;
/**
 * This class is a controller to generates the various reports such as patient list ,physician list, prescription list,order list and invoice list
 */
@Controller
public class ReportsController extends ReportsBaseControllerr {

	@Autowired
	ReportService reportService;
	
	@Autowired
	PhysicianAccountRespository physicianRep;
	
	@Autowired
	PhysicianAssistantAccountRespository assistantRepo;
	
	@Autowired
	ClinicRepository clinicRepo;
	
	@Autowired
	GroupMasterRespository groupMasterRepo;
	
	@Autowired
	PhysicianGroupRepository phyGroupRepo;
	
	@Autowired
	GroupDirectorAccountRespository groupDirRepo;
	
	@Autowired
	GroupMasterService groupService;
	
	@Autowired
	PrescriptionRxStatusTypeService rxStatusService;
	
	@Autowired
	PrescriptionRxTransactionStatusTypeService rxTranService;
	
	/**
	 * This method generates the patient list report page
	 * @param model
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping("/patientsListReport")
	public ModelAndView loadPatientListReport(Model model, HttpServletRequest request, HttpServletResponse response, HttpSession session){
		ModelAndView mv = new ModelAndView();
		try {
			model.addAttribute("message", model.asMap().get("message"));

			LoginForm user = (LoginForm) session.getAttribute("loginDetail");
			
			PatientsList report = new PatientsList();
			
			PhysicianAccount physicianAcc = null;
			if (PharmacyUtil.userPhysician.equalsIgnoreCase(user.getType())) { 
				physicianAcc = physicianRep.findOne(user.getUserid());
				
				report.setPhysician(physicianAcc.getPhysicianName());
				if (physicianAcc.getClinicId() > 0)
					report.setClinic( clinicRepo.findOne(physicianAcc.getClinicId()).getClinicName() );
				if (phyGroupRepo.findByPhysicianId(physicianAcc.getId()).get(0).getGroupId() > 0)
					report.setGroup( groupMasterRepo.findOne( phyGroupRepo.findByPhysicianId(physicianAcc.getId()).get(0).getGroupId()).getGroupName() );
			} else if (PharmacyUtil.userPhysicianAssistant.equalsIgnoreCase(user.getType())) {
				PhysicianAssistantAccount assistant = assistantRepo.findOne(user.getUserid());
				//temp commented on jan 19,2018
				//physicianAcc = physicianRep.findOne( assistant.getPhysicianId() );
				int phyId=user.getPhysicianAssistantPhysicianId();
				physicianAcc = physicianRep.findOne(phyId);
				
				report.setPhysician(physicianAcc.getPhysicianName());
				if (physicianAcc.getClinicId() > 0)
					report.setClinic( clinicRepo.findOne(physicianAcc.getClinicId()).getClinicName() );
				if (phyGroupRepo.findByPhysicianId(physicianAcc.getId()).get(0).getGroupId() > 0)
					report.setGroup( groupMasterRepo.findOne( phyGroupRepo.findByPhysicianId(physicianAcc.getId()).get(0).getGroupId()).getGroupName() );
			} else if (PharmacyUtil.userGroupDirector.equalsIgnoreCase(user.getType())) {
				report.setGroup( groupMasterRepo.findOne( groupDirRepo.findOne( user.getUserid()).getGroupId() ).getGroupName() );
			}
			
			List<GroupMaster> groupList = groupService.getAllGroupMaster();
			mv.addObject("groupList", groupList);
			
			mv.addObject("form", report);
			mv.setViewName("patientsListReport");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return mv;
	}
	
	/**
	 * This method generates the patient list report data
	 * @param draw
	 * @param start
	 * @param length
	 * @param searchTerm
	 * @param orderColumn
	 * @param orderDir
	 * @param physician
	 * @param group
	 * @param status
	 * @param fromDate
	 * @param toDate
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
	 * @param col6Data
	 * @param col6Name
	 * @param col6Search
	 * @param col6Order
	 * @param col6SearchValue
	 * @param col6SearchRegex
	 * @param col7Data
	 * @param col7Name
	 * @param col7Search
	 * @param col7Order
	 * @param col7SearchValue
	 * @param col7SearchRegex
	 * @param col8Data
	 * @param col8Name
	 * @param col8Search
	 * @param col8Order
	 * @param col8SearchValue
	 * @param col8SearchRegex
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/getPatientListReportData", method = RequestMethod.POST)
	public @ResponseBody String getPatientListReportData( @RequestParam("draw") int draw, @RequestParam("start") int start, 
			@RequestParam("length") int length, @RequestParam("search[value]") String searchTerm,  @RequestParam("order[0][column]") int orderColumn,
			@RequestParam("order[0][dir]") String orderDir, @RequestParam("physician") String physician, @RequestParam("group") String group,   
			@RequestParam("status") String status, @RequestParam("fromDate") String fromDate, @RequestParam("toDate") String toDate, 
			@RequestParam("columns[0][data]") String col0Data, @RequestParam("columns[0][name]") String col0Name, 
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
			@RequestParam("columns[5][data]") String col5Data, @RequestParam("columns[5][name]") String col5Name,
			@RequestParam("columns[5][searchable]") String col5Search, @RequestParam("columns[5][orderable]") String col5Order,
			@RequestParam("columns[5][search][value]") String col5SearchValue, @RequestParam("columns[5][search][regex]") String col5SearchRegex, 
			@RequestParam("columns[6][data]") String col6Data, @RequestParam("columns[6][name]") String col6Name,
			@RequestParam("columns[6][searchable]") String col6Search, @RequestParam("columns[6][orderable]") String col6Order,
			@RequestParam("columns[6][search][value]") String col6SearchValue, @RequestParam("columns[6][search][regex]") String col6SearchRegex, 
			@RequestParam("columns[7][data]") String col7Data, @RequestParam("columns[7][name]") String col7Name,
			@RequestParam("columns[7][searchable]") String col7Search, @RequestParam("columns[7][orderable]") String col7Order,
			@RequestParam("columns[7][search][value]") String col7SearchValue, @RequestParam("columns[7][search][regex]") String col7SearchRegex,
			@RequestParam("columns[8][data]") String col8Data, @RequestParam("columns[8][name]") String col8Name,
			@RequestParam("columns[8][searchable]") String col8Search, @RequestParam("columns[8][orderable]") String col8Order,
			@RequestParam("columns[8][search][value]") String col8SearchValue, @RequestParam("columns[8][search][regex]") String col8SearchRegex, 
			HttpServletRequest request, HttpSession session) {
		
		return reportService.getPatientListData(draw, start, length, searchTerm, orderColumn, orderDir, physician, group, status, fromDate, toDate, session);
	}
	
	
	/**
	 * This method generates the physician list report page
	 * @param model
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping("/physicianListReport")
	public ModelAndView loadPhysicianListReport(Model model, HttpServletRequest request, HttpServletResponse response, HttpSession session){
		ModelAndView mv = new ModelAndView();
		try {
			model.addAttribute("message", model.asMap().get("message"));

			LoginForm user = (LoginForm) session.getAttribute("loginDetail");
			
			PatientsList report = new PatientsList();
				
			if (PharmacyUtil.userGroupDirector.equalsIgnoreCase(user.getType())) {
				report.setGroup( groupMasterRepo.findOne( groupDirRepo.findOne( user.getUserid()).getGroupId() ).getGroupName() );
				mv.setViewName("physicianListReport");
			} else {
				mv.setViewName("physicianListReport");
			}
			
			List<GroupMaster> groupList = groupService.getAllGroupMaster();
			mv.addObject("groupList", groupList);
			
			mv.addObject("form", report);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mv;
	}
	/**
	 * This method generates the physician list report data
	 * @param draw
	 * @param start
	 * @param length
	 * @param searchTerm
	 * @param orderColumn
	 * @param orderDir
	 * @param physician
	 * @param clinic
	 * @param group
	 * @param status
	 * @param fromDate
	 * @param toDate
	 * @param dea
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
	 * @param col6Data
	 * @param col6Name
	 * @param col6Search
	 * @param col6Order
	 * @param col6SearchValue
	 * @param col6SearchRegex
	 * @param col7Data
	 * @param col7Name
	 * @param col7Search
	 * @param col7Order
	 * @param col7SearchValue
	 * @param col7SearchRegex
	 * @param col8Data
	 * @param col8Name
	 * @param col8Search
	 * @param col8Order
	 * @param col8SearchValue
	 * @param col8SearchRegex
	 * @param col9Data
	 * @param col9Name
	 * @param col9Search
	 * @param col9Order
	 * @param col9SearchValue
	 * @param col9SearchRegex
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/getPhysicianListReportData", method = RequestMethod.POST)
	public @ResponseBody String getPhysicianListReportData( @RequestParam("draw") int draw, @RequestParam("start") int start, 
			@RequestParam("length") int length, @RequestParam("search[value]") String searchTerm,  @RequestParam("order[0][column]") int orderColumn,
			@RequestParam("order[0][dir]") String orderDir, @RequestParam("physician") String physician, @RequestParam("clinic") String clinic, 
			@RequestParam("group") String group, @RequestParam("status") String status, @RequestParam("fromDate") String fromDate, 
			@RequestParam("toDate") String toDate, @RequestParam("dea") String dea,  
			@RequestParam("columns[0][data]") String col0Data, @RequestParam("columns[0][name]") String col0Name, 
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
			@RequestParam("columns[5][data]") String col5Data, @RequestParam("columns[5][name]") String col5Name,
			@RequestParam("columns[5][searchable]") String col5Search, @RequestParam("columns[5][orderable]") String col5Order,
			@RequestParam("columns[5][search][value]") String col5SearchValue, @RequestParam("columns[5][search][regex]") String col5SearchRegex, 
			@RequestParam("columns[6][data]") String col6Data, @RequestParam("columns[6][name]") String col6Name,
			@RequestParam("columns[6][searchable]") String col6Search, @RequestParam("columns[6][orderable]") String col6Order,
			@RequestParam("columns[6][search][value]") String col6SearchValue, @RequestParam("columns[6][search][regex]") String col6SearchRegex, 
			@RequestParam("columns[7][data]") String col7Data, @RequestParam("columns[7][name]") String col7Name,
			@RequestParam("columns[7][searchable]") String col7Search, @RequestParam("columns[7][orderable]") String col7Order,
			@RequestParam("columns[7][search][value]") String col7SearchValue, @RequestParam("columns[7][search][regex]") String col7SearchRegex,
			@RequestParam("columns[8][data]") String col8Data, @RequestParam("columns[8][name]") String col8Name,
			@RequestParam("columns[8][searchable]") String col8Search, @RequestParam("columns[8][orderable]") String col8Order,
			@RequestParam("columns[8][search][value]") String col8SearchValue, @RequestParam("columns[8][search][regex]") String col8SearchRegex, 
			@RequestParam("columns[9][data]") String col9Data, @RequestParam("columns[9][name]") String col9Name,
			@RequestParam("columns[9][searchable]") String col9Search, @RequestParam("columns[9][orderable]") String col9Order,
			@RequestParam("columns[9][search][value]") String col9SearchValue, @RequestParam("columns[9][search][regex]") String col9SearchRegex, 
			HttpServletRequest request, HttpSession session) {
		
		return reportService.getPhysicianListData(draw, start, length, searchTerm, orderColumn, orderDir, physician, clinic, group, status, dea, 
				fromDate, toDate, session);
	}
	
	
	/**
	 * This method generates the prescription list report page
	 * @param model
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping("/prescriptionListReport")
	public ModelAndView loadPrescriptionListReport(Model model, HttpServletRequest request, HttpServletResponse response, HttpSession session){
		ModelAndView mv = new ModelAndView();
		try {
			model.addAttribute("message", model.asMap().get("message"));

			LoginForm user = (LoginForm) session.getAttribute("loginDetail");
			PatientsList report = new PatientsList();
				
			PhysicianAccount physicianAcc = null;
			if (PharmacyUtil.userPhysician.equalsIgnoreCase(user.getType())) { 
				physicianAcc = physicianRep.findOne(user.getUserid());
				
				report.setPhysicianId(user.getUserid());
				report.setPhysician(physicianAcc.getPhysicianName());
				if (physicianAcc.getClinicId() > 0)
					report.setClinic( clinicRepo.findOne(physicianAcc.getClinicId()).getClinicName() );
				if (phyGroupRepo.findByPhysicianId(physicianAcc.getId()).get(0).getGroupId() > 0)
					report.setGroup( groupMasterRepo.findOne( phyGroupRepo.findByPhysicianId(physicianAcc.getId()).get(0).getGroupId()).getGroupName() );
			} else if (PharmacyUtil.userPhysicianAssistant.equalsIgnoreCase(user.getType())) {
				PhysicianAssistantAccount assistant = assistantRepo.findOne(user.getUserid());
				//temp commented on jan 19,2018
				//physicianAcc = physicianRep.findOne( assistant.getPhysicianId() );
				int phyId=user.getPhysicianAssistantPhysicianId();
				physicianAcc = physicianRep.findOne(phyId);
				
				report.setPhysicianId(physicianAcc.getId());
				report.setPhysician(physicianAcc.getPhysicianName());
				if (physicianAcc.getClinicId() > 0)
					report.setClinic( clinicRepo.findOne(physicianAcc.getClinicId()).getClinicName() );
				if (phyGroupRepo.findByPhysicianId(physicianAcc.getId()).get(0).getGroupId() > 0)
					report.setGroup( groupMasterRepo.findOne( phyGroupRepo.findByPhysicianId(physicianAcc.getId()).get(0).getGroupId()).getGroupName() );
			} else if (PharmacyUtil.userGroupDirector.equalsIgnoreCase(user.getType())) {
				report.setGroup( groupMasterRepo.findOne( groupDirRepo.findOne( user.getUserid()).getGroupId() ).getGroupName() );
			} else if (PharmacyUtil.userPatient.equalsIgnoreCase(user.getType())) {
				report.setPatient( user.getDisplayName());
				report.setPatientId(user.getUserid());
			}
			
			List<PrescriptionRxStatusType> statusList = rxStatusService.getAllPrescriptionRxStatusType();
			mv.addObject("statusList", statusList);
			
			mv.setViewName("prescriptionListReport");
			mv.addObject("form", report);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mv;
	}
	
	/**
	 * This method generates the prescription list report data
	 * @param draw
	 * @param start
	 * @param length
	 * @param searchTerm
	 * @param orderColumn
	 * @param orderDir
	 * @param physician
	 * @param patient
	 * @param fromDate
	 * @param toDate
	 * @param status
	 * @param group
	 * @param rxNumber
	 * @param prescriptionNo
	 * @param rxPrescriptionNo
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/getPrescriptionListReportData", method = RequestMethod.POST)
	public @ResponseBody String getPrescriptionListReportData( @RequestParam("draw") int draw, @RequestParam("start") int start, 
			@RequestParam("length") int length, @RequestParam("search[value]") String searchTerm,  @RequestParam("order[0][column]") int orderColumn,
			@RequestParam("order[0][dir]") String orderDir, @RequestParam("physicianName") String physician, @RequestParam("patientName") String patient, 
			@RequestParam("fromDate") String fromDate, @RequestParam("toDate") String toDate,  @RequestParam("status") String status,
			@RequestParam("group") String group, @RequestParam("rxNo") String rxNumber,  @RequestParam("prescriptionNo") String prescriptionNo, 
			@RequestParam("rxPrescriptionNo") String rxPrescriptionNo, HttpServletRequest request, HttpSession session) {
		
		return reportService.getPrescriptionListData(draw, start, length, searchTerm, orderColumn, orderDir, 
				physician, patient, fromDate, toDate, status, group, rxNumber, prescriptionNo,rxPrescriptionNo, session);
	}
	
	
	/**
	 * This method generates the order list report page
	 * @param model
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping("/orderListReport")
	public ModelAndView loadOrderListReport(Model model, HttpServletRequest request, HttpServletResponse response, HttpSession session){
		ModelAndView mv = new ModelAndView();
		try {
			model.addAttribute("message", model.asMap().get("message"));

			LoginForm user = (LoginForm) session.getAttribute("loginDetail");
			PatientsList report = new PatientsList();
				
			PhysicianAccount physicianAcc = null;
			if (PharmacyUtil.userPhysician.equalsIgnoreCase(user.getType())) { 
				physicianAcc = physicianRep.findOne(user.getUserid());
				
				report.setPhysicianId(user.getUserid());
				report.setPhysician(physicianAcc.getPhysicianName());
				if (physicianAcc.getClinicId() > 0 )
					report.setClinic( clinicRepo.findOne(physicianAcc.getClinicId()).getClinicName() );
				if ( phyGroupRepo.findByPhysicianId(physicianAcc.getId()).get(0).getGroupId() > 0)
					report.setGroup( groupMasterRepo.findOne( phyGroupRepo.findByPhysicianId(physicianAcc.getId()).get(0).getGroupId()).getGroupName() );
			} else if (PharmacyUtil.userPhysicianAssistant.equalsIgnoreCase(user.getType())) {
				PhysicianAssistantAccount assistant = assistantRepo.findOne(user.getUserid());
				//temp commented on jan 19,2018
				//physicianAcc = physicianRep.findOne( assistant.getPhysicianId() );
				int phyId=user.getPhysicianAssistantPhysicianId();
				physicianAcc = physicianRep.findOne(phyId);
				
				report.setPhysicianId(physicianAcc.getId());
				report.setPhysician(physicianAcc.getPhysicianName());
				if (physicianAcc.getClinicId() > 0 )
					report.setClinic( clinicRepo.findOne(physicianAcc.getClinicId()).getClinicName() );
				if ( phyGroupRepo.findByPhysicianId(physicianAcc.getId()).get(0).getGroupId() > 0)
					report.setGroup( groupMasterRepo.findOne( phyGroupRepo.findByPhysicianId(physicianAcc.getId()).get(0).getGroupId()).getGroupName() );
			} else if (PharmacyUtil.userGroupDirector.equalsIgnoreCase(user.getType())) {
				report.setGroup( groupMasterRepo.findOne( groupDirRepo.findOne( user.getUserid()).getGroupId() ).getGroupName() );
			} else if (PharmacyUtil.userPatient.equalsIgnoreCase(user.getType())) {
				report.setPatient( user.getDisplayName());
				report.setPatientId(user.getUserid());
			}
			
			List<PrescriptionRxTransactionStatusType> rxTranList = rxTranService.getAllActiveStatus();
			mv.addObject("statusList", rxTranList);

			mv.setViewName("orderListReport");
			mv.addObject("form", report);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mv;
	}
	
	/**
	 * This method generates the order list report data
	 * @param draw
	 * @param start
	 * @param length
	 * @param searchTerm
	 * @param orderColumn
	 * @param orderDir
	 * @param physician
	 * @param patient
	 * @param fromDate
	 * @param toDate
	 * @param status
	 * @param group
	 * @param orderNo
	 * @param rxNumber
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/getOrderListReportData", method = RequestMethod.POST)
	public @ResponseBody String getOrderListReportData( @RequestParam("draw") int draw, @RequestParam("start") int start, 
			@RequestParam("length") int length, @RequestParam("search[value]") String searchTerm,  @RequestParam("order[0][column]") int orderColumn,
			@RequestParam("order[0][dir]") String orderDir, @RequestParam("physicianName") String physician, @RequestParam("patientName") String patient, 
			@RequestParam("fromDate") String fromDate, @RequestParam("toDate") String toDate,  @RequestParam("status") String status,
			@RequestParam("group") String group, @RequestParam("orderNo") String orderNo, @RequestParam("rxNumber") String rxNumber,  
			HttpServletRequest request, HttpSession session) {
		
		return reportService.getOrderListData(draw, start, length, searchTerm, orderColumn, orderDir, 
				physician, patient, fromDate, toDate, status, group, orderNo, rxNumber, session);
	}
	
	/**
	 * This method generates the invoice list report page
	 * @param model
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping("/invoiceListReport")
	public ModelAndView loadInvoiceListReport(Model model, HttpServletRequest request, HttpServletResponse response, HttpSession session){
		ModelAndView mv = new ModelAndView();
		try {
			model.addAttribute("message", model.asMap().get("message"));

			LoginForm user = (LoginForm) session.getAttribute("loginDetail");
			PatientsList report = new PatientsList();
				
			PhysicianAccount physicianAcc = null;
			if (PharmacyUtil.userPhysician.equalsIgnoreCase(user.getType())) { 
				physicianAcc = physicianRep.findOne(user.getUserid());
				
				report.setPhysician(physicianAcc.getPhysicianName());
				if (physicianAcc.getClinicId() > 0 )
					report.setClinic( clinicRepo.findOne(physicianAcc.getClinicId()).getClinicName() );
				if ( phyGroupRepo.findByPhysicianId(physicianAcc.getId()).get(0).getGroupId() > 0)
					report.setGroup( groupMasterRepo.findOne( phyGroupRepo.findByPhysicianId(physicianAcc.getId()).get(0).getGroupId()).getGroupName() );
			} else if (PharmacyUtil.userPhysicianAssistant.equalsIgnoreCase(user.getType())) {
				PhysicianAssistantAccount assistant = assistantRepo.findOne(user.getUserid());
				//temp commented on jan 19,2018
				//physicianAcc = physicianRep.findOne( assistant.getPhysicianId() );
				int phyId=user.getPhysicianAssistantPhysicianId();
				physicianAcc = physicianRep.findOne(phyId);
				
				report.setPhysician(physicianAcc.getPhysicianName());
				if (physicianAcc.getClinicId() > 0 )
					report.setClinic( clinicRepo.findOne(physicianAcc.getClinicId()).getClinicName() );
				if ( phyGroupRepo.findByPhysicianId(physicianAcc.getId()).get(0).getGroupId() > 0)
					report.setGroup( groupMasterRepo.findOne( phyGroupRepo.findByPhysicianId(physicianAcc.getId()).get(0).getGroupId()).getGroupName() );
			} else if (PharmacyUtil.userGroupDirector.equalsIgnoreCase(user.getType())) {
				report.setGroup( groupMasterRepo.findOne( groupDirRepo.findOne( user.getUserid()).getGroupId() ).getGroupName() );
			}

			mv.setViewName("invoiceListReport");
			mv.addObject("form", report);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mv;
	}
	
	/**
	 * This method generates the invoice list report page
	 * @param draw
	 * @param start
	 * @param length
	 * @param searchTerm
	 * @param orderColumn
	 * @param orderDir
	 * @param physician
	 * @param patient
	 * @param fromDate
	 * @param toDate
	 * @param group
	 * @param invoiceNo
	 * @param rxNumber
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/getInvoiceListReportData", method = RequestMethod.POST)
	public @ResponseBody String getInvoiceListReportData( @RequestParam("draw") int draw, @RequestParam("start") int start, 
			@RequestParam("length") int length, @RequestParam("search[value]") String searchTerm,  @RequestParam("order[0][column]") int orderColumn,
			@RequestParam("order[0][dir]") String orderDir, @RequestParam("physicianName") String physician, @RequestParam("patientName") String patient, 
			@RequestParam("fromDate") String fromDate, @RequestParam("toDate") String toDate, @RequestParam("group") String group, 
			@RequestParam("invoiceNo") String invoiceNo, @RequestParam("rxNumber") String rxNumber,  
			HttpServletRequest request, HttpSession session) {
		
		return reportService.getInvoiceListData(draw, start, length, searchTerm, orderColumn, orderDir, 
				physician, patient, fromDate, toDate, group, invoiceNo, rxNumber, session);
	}
	
	
	
	/**
	 * This method downloads the patient report print pdf
	 * @param request
	 * @param response
	 * @param session
	 * @param form
	 */
	@RequestMapping(value = "/patientReportPdfDownload", method = RequestMethod.POST)
	public void downloadPatientListPDFFiles(HttpServletRequest request, HttpServletResponse response, HttpSession session, 
			@ModelAttribute("form") PatientsList form) {
		
		String fileName = "Patient_List.xlsx";
		reportService.getPatientListExcelFile(form, fileName, session, request, response, true);
	}
	/**
	 * This method downloads the physician report print pdf
	 * @param request
	 * @param response
	 * @param session
	 * @param form
	 */
	@RequestMapping(value = "/physicianReportPdfDownload", method = RequestMethod.POST)
	public void downloadPhysicianListPDFFiles(HttpServletRequest request, HttpServletResponse response, HttpSession session, 
			@ModelAttribute("form") PatientsList form) {
		
		String fileName = "Physician_List.xlsx";
		reportService.getPhysicianListExcelFile(form, fileName, session, request, response, true);
	}
	
	/**
	 * This method downloads the prescription report print pdf
	 * @param request
	 * @param response
	 * @param session
	 * @param form
	 */
	@RequestMapping(value = "/prescriptionReportPdfDownload", method = RequestMethod.POST)
	public void downloadprescriptionListPDFFiles(HttpServletRequest request, HttpServletResponse response, HttpSession session, 
			@ModelAttribute("form") PatientsList form) {
		
		String fileName = "Physician_List.xlsx";
		reportService.getPrescriptionListExcelFile(form, fileName, session, request, response, true);
	}
	
	/**
	 *  This method downloads the order report print pdf
	 * @param request
	 * @param response
	 * @param session
	 * @param form
	 */
	@RequestMapping(value = "/orderReportPdfDownload", method = RequestMethod.POST)
	public void downloadOrderListPDFFiles(HttpServletRequest request, HttpServletResponse response, HttpSession session, 
			@ModelAttribute("form") PatientsList form) {
		
		String fileName = "Order_List.xlsx";
		reportService.getOrderListExcelFile(form, fileName, session, request, response, true);
	}
	
	/**
	 *  This method downloads the invoice report print pdf
	 * @param request
	 * @param response
	 * @param session
	 * @param form
	 */
	@RequestMapping(value = "/invoiceReportPdfDownload", method = RequestMethod.POST)
	public void downloadInvoiceListPDFFiles(HttpServletRequest request, HttpServletResponse response, HttpSession session, 
			@ModelAttribute("form") PatientsList form) {
		
		String fileName = "Invoice_List.xlsx";
		reportService.getInvoiceListExcelFile(form, fileName, session, request, response, true);
	}
	
	
	/**
	 *  This method downloads the patient report print excel
	 * @param request
	 * @param response
	 * @param session
	 * @param form
	 */
	@RequestMapping(value = "/patientReportExcelDownload", method = RequestMethod.POST)
	public void downloadPatientListExcelFiles(HttpServletRequest request, HttpServletResponse response, HttpSession session, 
			@ModelAttribute("form") PatientsList form) {
		
		String fileName = "Patient_List.xlsx";
		reportService.getPatientListExcelFile(form, fileName, session, request, response, false);
	}
	/**
	 *  This method downloads the physician report print excel
	 * @param request
	 * @param response
	 * @param session
	 * @param form
	 */
	@RequestMapping(value = "/physicianReportExcelDownload", method = RequestMethod.POST)
	public void downloadPhysicianListExcelFiles(HttpServletRequest request, HttpServletResponse response, HttpSession session, 
			@ModelAttribute("form") PatientsList form) {
		
		String fileName = "Physician_List.xlsx";
		reportService.getPhysicianListExcelFile(form, fileName, session, request, response, false);
	}
	
	/**
	 * This method downloads the prescription report print excel
	 * @param request
	 * @param response
	 * @param session
	 * @param form
	 */
	@RequestMapping(value = "/prescriptionReportExcelDownload", method = RequestMethod.POST)
	public void downloadPrescriptionListExcelFiles(HttpServletRequest request, HttpServletResponse response, HttpSession session, 
			@ModelAttribute("form") PatientsList form) {
		
		String fileName = "Physician_List.xlsx";
		reportService.getPrescriptionListExcelFile(form, fileName, session, request, response, false);
	}
	/**
	 * This method downloads the order report print excel
	 * @param request
	 * @param response
	 * @param session
	 * @param form
	 */
	@RequestMapping(value = "/orderReportExcelDownload", method = RequestMethod.POST)
	public void downloadOrderListExcelFiles(HttpServletRequest request, HttpServletResponse response, HttpSession session, 
			@ModelAttribute("form") PatientsList form) {
		
		String fileName = "Order_List.xlsx";
		reportService.getOrderListExcelFile(form, fileName, session, request, response, false);
	}
	/**
	 * This method downloads the invoice report print excel
	 * @param request
	 * @param response
	 * @param session
	 * @param form
	 */
	@RequestMapping(value = "/invoiceReportExcelDownload", method = RequestMethod.POST)
	public void downloadInvoiceListExcelFiles(HttpServletRequest request, HttpServletResponse response, HttpSession session, 
			@ModelAttribute("form") PatientsList form) {
		
		String fileName = "Invoice_List.xlsx";
		reportService.getInvoiceListExcelFile(form, fileName, session, request, response, false);
	}
}
