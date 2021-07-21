package com.pharma.core.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pharma.core.formBean.LoginForm;
import com.pharma.core.formBean.dashboard.DashboardForm;
import com.pharma.core.formBean.manual.InstructionManualForm;
import com.pharma.core.formBean.patient.PatientAccountForm;
import com.pharma.core.model.pharmacygroup.GroupDirector;
import com.pharma.core.model.pharmacygroup.GroupMaster;
import com.pharma.core.model.physician.PhysicianAccount;
import com.pharma.core.model.pioneer.PrescriptionRxStatusType;
import com.pharma.core.model.pioneer.PrescriptionRxTransactionStatusType;
import com.pharma.core.pharmaservices.clinic.ClinicService;
import com.pharma.core.pharmaservices.invoice.InvoiceMasterService;
import com.pharma.core.pharmaservices.manual.InstructionManualService;
import com.pharma.core.pharmaservices.order.OrderMasterService;
import com.pharma.core.pharmaservices.patientservices.PatientAccountService;
import com.pharma.core.pharmaservices.pharmacygroupservices.GroupMasterService;
import com.pharma.core.pharmaservices.physicianservices.PhysicianAccountService;
import com.pharma.core.pharmaservices.physicianservices.PhysicianAssistantAccountService;
import com.pharma.core.pharmaservices.pioneer.PrescriptionRxStatusTypeService;
import com.pharma.core.pharmaservices.pioneer.PrescriptionRxTransactionStatusTypeService;
import com.pharma.core.pharmaservices.prescription.PrescriptionService;
import com.pharma.core.repository.invoice.InvoiceMasterRepository;
import com.pharma.core.repository.order.OrderMasterRepository;
import com.pharma.core.repository.patient.PatientAccountRespository;
import com.pharma.core.repository.pharmacygroup.GroupDirectorAccountRespository;
import com.pharma.core.repository.physician.PhysicianAccountRespository;
import com.pharma.core.repository.physician.PhysicianAssistantAccountRespository;
import com.pharma.core.repository.prescription.PrescriptionRepository;
import com.pharma.core.util.PharmacyUtil;
/**
 * This class is a controller to load the physician dashboard and patient dashaboard
 *
 */
@Controller
@PropertySource("classpath:physicianAccount.properties")
@PropertySource("classpath:patientAccount.properties")
public class DashboardController extends DashboardBaseController{
	
	@Autowired
	PatientAccountService patientAccountService;
	
	@Autowired
	PhysicianAssistantAccountRespository assistantRepo;
	
	@Autowired
	GroupMasterService groupService;
	
	@Autowired
	GroupDirectorAccountRespository groupDirRepo;
	
	@Autowired
	PrescriptionService prescriptionService;
	
	@Autowired
	OrderMasterService orderMasterService;	
	
	@Autowired
	InvoiceMasterService invoiceMasterService;
	
	@Autowired
	PrescriptionRxTransactionStatusTypeService rxTranService;
	
	@Autowired
	PrescriptionRxStatusTypeService rxService;
	
	@Autowired
	PatientAccountRespository patientRepo;
	
	@Autowired
	PrescriptionRepository prescriptionRepo;
	
	@Autowired
	OrderMasterRepository orderRepo;
	
	@Autowired
	InvoiceMasterRepository invoiceRepo;
	
	@Autowired
	PhysicianAccountService phyService;
	
	@Autowired
	private Environment env;
	
	@Autowired
	ClinicService clinicService;
	
	@Autowired
	InstructionManualService manualService;
	
	@Autowired
	PhysicianAccountRespository physicianRep;
	

	@Autowired
	PhysicianAssistantAccountService phyAssistService;
	/**
	 * Physician Dashboard  loads the details of Physician, Physician Assistant and Group Director data related to Physician.
	 * @param model
	 * @param request
	 * @param response
	 * @param session
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping("/physiciandashboard")
	public ModelAndView loadPhysicianDashboard(Model model, HttpServletRequest request, HttpServletResponse response, 
			HttpSession session,RedirectAttributes redirectAttributes){
	
		ModelAndView mv = new ModelAndView();
		
		try {
			mv=setDashboardAttributes(model,mv,request,response,session);
			
			mv.setViewName("physiciandashboard");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		return mv;
	}

	/**
	 * Patient Dashboard  loads the details of Patient data.
	 * @param model
	 * @param request
	 * @param response
	 * @param session
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping("/patientdashboard")
	public ModelAndView loadPatientDashboard(Model model, HttpServletRequest request, HttpServletResponse response, 
			HttpSession session,RedirectAttributes redirectAttributes){
		
		ModelAndView mv = new ModelAndView();
		try {
			LoginForm loggedInUser = (LoginForm) session.getAttribute("loginDetail");

			
			mv.addObject("userType", loggedInUser.getType());
			mv.addObject("userId", loggedInUser.getUserid());

			List<PrescriptionRxStatusType> rxStatusList = rxService.getAllPrescriptionRxStatusType();
			mv.addObject("rxStatusList", rxStatusList);
			
			PatientAccountForm patient = patientAccountService.getPatientData(loggedInUser.getUserid(), env.getProperty("file.photo_path"));
			/*PhysicianProfile physician = phyService.getPhysicianData(patient.getPhysicianId(),  env.getProperty("file.photo_path"), env.getProperty("file.logo_path"));
			ClinicForm clinicForm = clinicService.getClinicAccountById(physician.getClinicId());*/
			
			//Commented on Feb 7,2018-Multiple Groups to be handled
			//GroupMasterForm groupForm=groupService.getGroupMasterData(patient.getGroupId(),  env.getProperty("file.logo_path"));

			List<InstructionManualForm> manual = null;
			try {
				Pageable latest = new PageRequest(0, 1);
				manual = manualService.getLatestManual(latest, env.getProperty("file.instruction_manual_folder"));
			} catch(Exception e) {
				e.printStackTrace();
			}

			DashboardForm form = new DashboardForm();
			mv.addObject("form", form);
			
			mv.addObject("patient", patient);
			/*mv.addObject("physician", physician);
			mv.addObject("clinic", clinicForm);*/
			//mv.addObject("groupForm", groupForm);
			mv.addObject("docList", manual);
			
			mv.setViewName("patientdashboard");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return mv;
	}
	/**
	 * Group Director Dashboard loads the details of Physician, Physician Assistant and Group Director data related to Group Director
	 * @param model
	 * @param request
	 * @param response
	 * @param session
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping("/groupdirectordashboard")
	public ModelAndView loadGroupDirectorDashboard(Model model, HttpServletRequest request, HttpServletResponse response, 
			HttpSession session,RedirectAttributes redirectAttributes){
		
		
		
			ModelAndView mv = new ModelAndView();
			
			try {
				mv=setDashboardAttributes(model,mv,request,response,session);
				
				mv.setViewName("groupdirectordashboard");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		

		return mv;
	}
	/**
	 * Admin Dashboard loads the details of Physician, Physician Assistant and Group Director data.
	 * @param model
	 * @param request
	 * @param response
	 * @param session
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping("/adminDashboard")
	public ModelAndView loadAdminDashboard(Model model, HttpServletRequest request, HttpServletResponse response, 
			HttpSession session,RedirectAttributes redirectAttributes){
		
			ModelAndView mv = new ModelAndView();
			
			try {
				mv=setDashboardAttributes(model,mv,request,response,session);
				
				mv.setViewName("admindashboard");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		return mv;
	}
	/**
	 * Loads the dashboard  details of Physician, Physician Assistant and Group Director data
	 * @param model
	 * @param mv
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	public ModelAndView setDashboardAttributes(Model model,ModelAndView mv,HttpServletRequest request, HttpServletResponse response, HttpSession session)
	{
			try {
			
			LoginForm loggedInUser = (LoginForm) session.getAttribute("loginDetail");
			int physicianId=0;
			
			if(request.getParameter("physicianId")!=null)
			{
				
				physicianId=Integer.parseInt(request.getParameter("physicianId")+"");
			}
			
			//System.out.println("physicianId ================"+request.getParameter("physicianId"));
			
			model.addAttribute("message", model.asMap().get("message"));
			int phyId=0,groupid=0;
			
			if (PharmacyUtil.userPhysician.equalsIgnoreCase(loggedInUser.getType())) {
				phyId=loggedInUser.getUserid();
				model.addAttribute("physicianId", loggedInUser.getUserid());
				mv.addObject("userType", loggedInUser.getType());
				mv.addObject("userId", loggedInUser.getUserid());
			} else if (PharmacyUtil.userPhysicianAssistant.equalsIgnoreCase(loggedInUser.getType())) {
				
				if(physicianId>0){
					String physicianGroupName=phyService.getPhysicianData(physicianId, null,  null).getPhysicianNameWithGroupName();
					
					loggedInUser.setPhysicianGroupName(physicianGroupName);
					loggedInUser.setPhysicianAssistantPhysicianId(physicianId);
					loggedInUser.setPhysicianAssistantId(loggedInUser.getUserid());
					
					session.setAttribute("loginDetail", loggedInUser);
				}
				
				//temp commented on jan 19,2018
				//phyId = assistantRepo.findOne(loggedInUser.getUserid()).getPhysicianId() ;
				phyId=loggedInUser.getPhysicianAssistantPhysicianId();
				
				mv.addObject("userType", PharmacyUtil.userPhysician);
				mv.addObject("userId", phyId);
				
			} else if (PharmacyUtil.userGroupDirector.equalsIgnoreCase(loggedInUser.getType())) {
				groupid=loggedInUser.getUserid();
				model.addAttribute("groupId", loggedInUser.getUserid());
				mv.addObject("userType", loggedInUser.getType());
				mv.addObject("userId", loggedInUser.getUserid());
			} else if (PharmacyUtil.userAdmin.equalsIgnoreCase(loggedInUser.getType()) || PharmacyUtil.userSuperAdmin.equalsIgnoreCase(loggedInUser.getType()) 
					|| PharmacyUtil.userAdministrator.equalsIgnoreCase(loggedInUser.getType())) {
				mv.addObject("userType", loggedInUser.getType());
				mv.addObject("userId", loggedInUser.getUserid());
			}
			
			
			
			
			model.addAttribute("physicianId", phyId );
			if (!loggedInUser.getType().equalsIgnoreCase(PharmacyUtil.userGroupDirector)) {
				List<GroupMaster> groupList = groupService.getAllGroupMaster();
				mv.addObject("groupList", groupList);
			} else {
				GroupDirector gp = groupDirRepo.findOne(loggedInUser.getUserid());
				mv.addObject("groupId", gp.getGroupId());
			}
			List<PrescriptionRxTransactionStatusType> rxTranList = rxTranService.getAllActiveStatus();
			mv.addObject("rxTranStatusList", rxTranList);
			
			List<PrescriptionRxStatusType> rxStatusList = rxService.getAllPrescriptionRxStatusType();
			mv.addObject("rxStatusList", rxStatusList);
			
			int newPatientsCount_today=0,newPrescriptionsCount_today=0,noOfRefillsFilled_today=0;
			int newPatientsCount_week=0,newPrescriptionsCount_week=0,noOfRefillsFilled_week=0;
			int newPatientsCount_month=0,newPrescriptionsCount_month=0,noOfRefillsFilled_month=0;
			double totalInvoicePaid_today=0,totalInvoicePaid_week=0,totalInvoicePaid_month=0;
			
			if(phyId>0){
			
				try {
					newPatientsCount_today= patientRepo.fetchNewPatientCountByStatusandDateRegistered(phyId,PharmacyUtil.getPreviousDateByDays(2),PharmacyUtil.getPreviousDateByDays(0));
				} catch(Exception e) {
					//System.out.println("newPatientsCount_today:"+e.getMessage());
				}
				try {
					newPatientsCount_week= patientRepo.fetchNewPatientWeekCountByStatusandDateRegistered(phyId,PharmacyUtil.getPreviousDateByDays(9),PharmacyUtil.getPreviousDateByDays(0));
				} catch(Exception e) {
					//System.out.println("newPatientsCount_week:"+e.getMessage());
				}
				try {
					newPatientsCount_month= patientRepo.fetchNewPatientMonthCountByStatusandDateRegistered(phyId,PharmacyUtil.getPreviousDateByDays(32),PharmacyUtil.getPreviousDateByDays(0));
				} catch(Exception e) {
					//System.out.println("newPatientsCount_month:"+e.getMessage());
				}
				try {
					newPrescriptionsCount_today= prescriptionRepo.fetchNewPrescriptionCountByPrescriptionDate(phyId,PharmacyUtil.getPreviousDateByDays(2),PharmacyUtil.getPreviousDateByDays(0));
				} catch(Exception e) {
					//System.out.println("newPrescriptionsCount_today:"+e.getMessage());
				}
				try {
					newPrescriptionsCount_week= prescriptionRepo.fetchNewPrescriptionWeekCountByPrescriptionDate(phyId,PharmacyUtil.getPreviousDateByDays(9),PharmacyUtil.getPreviousDateByDays(0));
				} catch(Exception e) {
					//System.out.println("newPrescriptionsCount_week:"+e.getMessage());
				}
				try {
					newPrescriptionsCount_month= prescriptionRepo.fetchNewPrescriptionMonthCountByPrescriptionDate(phyId,PharmacyUtil.getPreviousDateByDays(32),PharmacyUtil.getPreviousDateByDays(0));
				} catch(Exception e) {
					//System.out.println("newPrescriptionsCount_month:"+e.getMessage());
				}
				try {
					noOfRefillsFilled_today= orderRepo.fetchTotalRefillsCountByOrderDate(phyId,PharmacyUtil.getPreviousDateByDays(2),PharmacyUtil.getPreviousDateByDays(0));
				} catch(Exception e) {
					//System.out.println("noOfRefillsFilled_today:"+e.getMessage());
				}
				try {
					noOfRefillsFilled_week= orderRepo.fetchTotalRefillsWeekCountByOrderDate(phyId,PharmacyUtil.getPreviousDateByDays(9),PharmacyUtil.getPreviousDateByDays(0));
				} catch(Exception e) {
					//System.out.println("noOfRefillsFilled_week:"+e.getMessage());
				}
				try {
					noOfRefillsFilled_month= orderRepo.fetchTotalRefillsMonthCountByOrderDate(phyId,PharmacyUtil.getPreviousDateByDays(32),PharmacyUtil.getPreviousDateByDays(0));
				} catch(Exception e) {
					//System.out.println("noOfRefillsFilled_month:"+e.getMessage());
				}
				try {
					totalInvoicePaid_today= invoiceRepo.fetchTotalInvoicePaidByInvoiceDate(phyId,PharmacyUtil.getPreviousDateByDays(2),PharmacyUtil.getPreviousDateByDays(0));
				} catch(Exception e) {
					//System.out.println("totalInvoicePaid_today:"+e.getMessage());
				}
				try {
					totalInvoicePaid_week= invoiceRepo.fetchWeekTotalInvoicePaidByInvoiceDate(phyId,PharmacyUtil.getPreviousDateByDays(9),PharmacyUtil.getPreviousDateByDays(0));
				} catch(Exception e) {
					//System.out.println("totalInvoicePaid_week:"+e.getMessage());
				}
				try {
					totalInvoicePaid_month= invoiceRepo.fetchMonthTotalInvoicePaidByInvoiceDate(phyId,PharmacyUtil.getPreviousDateByDays(32),PharmacyUtil.getPreviousDateByDays(0));
				} catch(Exception e) {
					//System.out.println("totalInvoicePaid_month:"+e.getMessage());
				}
			}else
			{
				
				try {
					newPatientsCount_today= patientRepo.fetchGroupNewPatientCountByStatusandDateRegistered(groupid,PharmacyUtil.getPreviousDateByDays(2),PharmacyUtil.getPreviousDateByDays(0));
				} catch(Exception e) {
					//System.out.println("newPatientsCount_today:"+e.getMessage());
				}
				try {
					newPatientsCount_week= patientRepo.fetchGroupNewPatientWeekCountByStatusandDateRegistered(groupid,PharmacyUtil.getPreviousDateByDays(9),PharmacyUtil.getPreviousDateByDays(0));
				} catch(Exception e) {
					//System.out.println("newPatientsCount_week:"+e.getMessage());
				}
				try {
					newPatientsCount_month= patientRepo.fetchGroupNewPatientMonthCountByStatusandDateRegistered(groupid,PharmacyUtil.getPreviousDateByDays(32),PharmacyUtil.getPreviousDateByDays(0));
				} catch(Exception e) {
					//System.out.println("newPatientsCount_month:"+e.getMessage());
				}
				try {
					newPrescriptionsCount_today= prescriptionRepo.fetchGroupNewPrescriptionCountByPrescriptionDate(groupid,PharmacyUtil.getPreviousDateByDays(2),PharmacyUtil.getPreviousDateByDays(0));
				} catch(Exception e) {
					//System.out.println("newPrescriptionsCount_today:"+e.getMessage());
				}
				try {
					newPrescriptionsCount_week= prescriptionRepo.fetchGroupNewPrescriptionWeekCountByPrescriptionDate(groupid,PharmacyUtil.getPreviousDateByDays(9),PharmacyUtil.getPreviousDateByDays(0));
				} catch(Exception e) {
					//System.out.println("newPrescriptionsCount_week:"+e.getMessage());
				}
				try {
					newPrescriptionsCount_month= prescriptionRepo.fetchGroupNewPrescriptionMonthCountByPrescriptionDate(groupid,PharmacyUtil.getPreviousDateByDays(32),PharmacyUtil.getPreviousDateByDays(0));
				} catch(Exception e) {
					//System.out.println("newPrescriptionsCount_month:"+e.getMessage());
				}
				try {
					noOfRefillsFilled_today= orderRepo.fetchGroupTotalRefillsCountByOrderDate(groupid,PharmacyUtil.getPreviousDateByDays(2),PharmacyUtil.getPreviousDateByDays(0));
				} catch(Exception e) {
					//System.out.println("noOfRefillsFilled_today:"+e.getMessage());
				}
				try {
					noOfRefillsFilled_week= orderRepo.fetchGroupTotalRefillsWeekCountByOrderDate(groupid,PharmacyUtil.getPreviousDateByDays(9),PharmacyUtil.getPreviousDateByDays(0));
				} catch(Exception e) {
					//System.out.println("noOfRefillsFilled_week:"+e.getMessage());
				}
				try {
					noOfRefillsFilled_month= orderRepo.fetchGroupTotalRefillsMonthCountByOrderDate(groupid,PharmacyUtil.getPreviousDateByDays(32),PharmacyUtil.getPreviousDateByDays(0));
				} catch(Exception e) {
					//System.out.println("noOfRefillsFilled_month:"+e.getMessage());
				}
				try {
					totalInvoicePaid_today= invoiceRepo.fetchGroupTotalInvoicePaidByInvoiceDate(groupid,PharmacyUtil.getPreviousDateByDays(2),PharmacyUtil.getPreviousDateByDays(0));
				} catch(Exception e) {
					//System.out.println("totalInvoicePaid_today:"+e.getMessage());
				}
				try {
					totalInvoicePaid_week= invoiceRepo.fetchGroupWeekTotalInvoicePaidByInvoiceDate(groupid,PharmacyUtil.getPreviousDateByDays(9),PharmacyUtil.getPreviousDateByDays(0));
				} catch(Exception e) {
					//System.out.println("totalInvoicePaid_week:"+e.getMessage());
				}
				try {
					totalInvoicePaid_month= invoiceRepo.fetchGroupMonthTotalInvoicePaidByInvoiceDate(groupid,PharmacyUtil.getPreviousDateByDays(32),PharmacyUtil.getPreviousDateByDays(0));
				} catch(Exception e) {
					//System.out.println("totalInvoicePaid_month:"+e.getMessage());
				}
				
			}
			mv.addObject("newPatientsCount_today", newPatientsCount_today);
			mv.addObject("newPatientsCount_week", newPatientsCount_week);
			mv.addObject("newPatientsCount_month", newPatientsCount_month);
			
			mv.addObject("newPrescriptionsCount_today", newPrescriptionsCount_today);
			mv.addObject("newPrescriptionsCount_week", newPrescriptionsCount_week);
			mv.addObject("newPrescriptionsCount_month", newPrescriptionsCount_month);
			
			mv.addObject("noOfRefillsFilled_today", noOfRefillsFilled_today);
			mv.addObject("noOfRefillsFilled_week", noOfRefillsFilled_week);
			mv.addObject("noOfRefillsFilled_month", noOfRefillsFilled_month);
			
			mv.addObject("totalInvoicePaid_today", totalInvoicePaid_today);
			mv.addObject("totalInvoicePaid_week", totalInvoicePaid_week);
			mv.addObject("totalInvoicePaid_month", totalInvoicePaid_month);

			DashboardForm form = new DashboardForm();
			mv.addObject("form", form);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return mv;
	}
	/**
	 * Loads the Physician's Patient data
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
	@RequestMapping(value = "/getPhysicianPatientAccountSummaryData", method = RequestMethod.POST)
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
		
		if ("".equalsIgnoreCase(pid))
			pid = "0";

		return patientAccountService.getPatientAccountDataList(Integer.parseInt(pid), draw, start, len, searchTerm, orderColumn, orderDir, 
				patientname, phyname, status, groupId, session);
	}
	/**
	 * Loads the Physician's Patient Prescription data
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
	 * @param presriptionToDate
	 * @param userId
	 * @param userType
	 * @param groupId
	 * @param prescriptionNo
	 * @param prescriptionstatus
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/getPhysicianPrescriptionSummaryData", method = RequestMethod.POST)
	public @ResponseBody String getPhysicianPrescriptionSummaryData(@RequestParam("draw") int draw,
			@RequestParam("start") int start, @RequestParam("length") int len, @RequestParam("search[value]") String searchTerm,
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
			@RequestParam("columns[4][search][regex]") String col4SearchRegex,  @RequestParam("physicianName") String physicianName, 
			@RequestParam("patientName") String patientName, @RequestParam("presriptionDate") String presriptionDate, 
			@RequestParam("presriptionToDate") String presriptionToDate, @RequestParam("userId") String userId, @RequestParam("userType") String userType,
			@RequestParam("groupId") int groupId, @RequestParam("prescriptionNo") String prescriptionNo, @RequestParam("prescriptionstatus") String prescriptionstatus, 
			HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		
		String rxNo = "";
		String rxPrescriptionNo="";
		return prescriptionService.getPrescriptionDataList(draw, start, len, searchTerm, orderColumn, orderDir, physicianName, patientName, 
				presriptionDate, presriptionToDate, userId, userType, groupId, prescriptionNo, prescriptionstatus, rxNo, rxPrescriptionNo, request,  response,  session);
	}	
	/**
	 *  Loads the Physician's Patient Order data
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
	 * @param orderNo
	 * @param phyname
	 * @param patientName
	 * @param groupId
	 * @param fromDate
	 * @param toDate
	 * @param status
	 * @param physicianId
	 * @param patientId
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/getPhysicianPatientOrderSummaryData", method = RequestMethod.POST)
	public @ResponseBody String getPhysicianPatientOrderSummaryData( @RequestParam("draw") int draw,
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
			@RequestParam("columns[4][search][regex]") String col4SearchRegex, @RequestParam("orderNo") String orderNo, 
			@RequestParam("phyname") String phyname, @RequestParam("patientname") String patientName, @RequestParam("groupId") int groupId, 
			@RequestParam("fromDate") String fromDate, @RequestParam("toDate") String toDate, @RequestParam("status") String status,
			@RequestParam("physicianId") String physicianId, @RequestParam("patientId") String patientId, HttpServletRequest request, HttpSession session) {
		
		if ("".equalsIgnoreCase(physicianId))
			physicianId = "0";
		String rxNo = "";
		return orderMasterService.getOrderDataList(draw, start, len, searchTerm, orderColumn, orderDir, 
				orderNo, phyname, patientName, fromDate, toDate, status, Integer.parseInt(physicianId), Integer.parseInt(patientId), groupId, rxNo );
	}
	/**
	 * Loads the Physician's Patient Invoice data
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
	 * @param patientName
	 * @param phyName
	 * @param invoiceNo
	 * @param fromDate
	 * @param toDate
	 * @param physicianId
	 * @param patientId
	 * @param groupId
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/getPhysicianPatientInvoiceSummaryData", method = RequestMethod.POST)
	public @ResponseBody String getPatientInvoiceSummaryData( @RequestParam("draw") int draw,
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
			@RequestParam("patientname") String patientName, @RequestParam("phyname") String phyName, @RequestParam("invoiceNo") String invoiceNo,
			@RequestParam("fromDate") String fromDate, @RequestParam("toDate") String toDate,
			@RequestParam("physicianId") String physicianId, @RequestParam("patientId") String patientId, @RequestParam("groupId") int groupId,  HttpServletRequest request, HttpSession session) {
		
		if ("".equalsIgnoreCase(physicianId))
			physicianId = "0";
		String rxNo = "";
		return invoiceMasterService.getInvoiceSummaryData(draw, start, len, searchTerm, orderColumn, orderDir, patientName, phyName, invoiceNo,
				fromDate, toDate, Integer.parseInt(physicianId), Integer.parseInt(patientId), groupId, rxNo);
	}
	
	/**
	 * Loads the home page of physician assistant login
	 * @param model
	 * @param request
	 * @param response
	 * @param session
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping("/physicianassistanthome")
	public ModelAndView loadPhysicianAssistantHome(Model model, HttpServletRequest request, HttpServletResponse response, 
			HttpSession session,RedirectAttributes redirectAttributes){
		
		List<PhysicianAccount> physicianSelectedList =null;
		ModelAndView mv = new ModelAndView();
		try {
			LoginForm loggedInUser = (LoginForm) session.getAttribute("loginDetail");
			DashboardForm form = new DashboardForm();
			mv.addObject("form", form);
			physicianSelectedList = physicianRep.getAllPhysicianAssistantPhysicianListSelected(loggedInUser.getUserid());
			
			mv.addObject("physicianSelectedList", physicianSelectedList);
			mv.setViewName("physicianassistanthome");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return mv;
	}
	/**
	 * Loads the Physician data
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
	@RequestMapping(value = "/getPhysicianAccountSummaryData", method = RequestMethod.POST)
	public @ResponseBody String getGroupDirectorPhysicianAccountSummaryData(@RequestParam("draw") int draw,
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
	 * Loads the Physician Assistant data
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
	 * @param groupId
	 * @param assistant
	 * @param clinic
	 * @param phyName
	 * @param status
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/getPhysicianAssistantData", method = RequestMethod.POST)
	public @ResponseBody String getAdminPhysicianAssistantData(@RequestParam("draw") int draw, @RequestParam("start") int start, @RequestParam("length") int len,
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
			@RequestParam("columns[4][search][regex]") String col4SearchRegex, 
			@RequestParam("columns[5][data]") String col5Data, @RequestParam("columns[5][name]") String col5Name, @RequestParam("columns[5][searchable]") String col5Search,
			@RequestParam("columns[5][orderable]") String col5Order, @RequestParam("columns[5][search][value]") String col5SearchValue,
			@RequestParam("columns[5][search][regex]") String col5SearchRegex, 
			@RequestParam("columns[6][data]") String col6Data, @RequestParam("columns[6][name]") String col6Name, @RequestParam("columns[6][searchable]") String col6Search,
			@RequestParam("columns[6][orderable]") String col6Order, @RequestParam("columns[6][search][value]") String col6SearchValue,
			@RequestParam("columns[6][search][regex]") String col6SearchRegex, 
			@RequestParam("columns[7][data]") String col7Data, @RequestParam("columns[7][name]") String col7Name, @RequestParam("columns[7][searchable]") String col7Search,
			@RequestParam("columns[7][orderable]") String col7Order, @RequestParam("columns[7][search][value]") String col7SearchValue,
			@RequestParam("columns[7][search][regex]") String col7SearchRegex, 
			@RequestParam("groupId") int groupId,
			@RequestParam("assistant") String assistant, @RequestParam("clinic") String clinic, @RequestParam("phyName") String phyName, @RequestParam("status") String status,
			HttpServletRequest request, HttpSession session) {
		
		return phyAssistService.getAdminPhysicianAssistantDataList(draw, start, len, searchTerm, orderColumn, orderDir, assistant, 
				clinic, phyName, status, groupId);
	}	
}
