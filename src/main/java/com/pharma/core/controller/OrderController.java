package com.pharma.core.controller;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pharma.core.formBean.LoginForm;
import com.pharma.core.formBean.invoice.InvoiceForm;
import com.pharma.core.formBean.order.OrderForm;
import com.pharma.core.formBean.physician.PhysicianProfile;
import com.pharma.core.formBean.prescription.PrescriptionForm;
import com.pharma.core.model.pioneer.PrescriptionRxTransactionStatusType;
import com.pharma.core.pharmaservices.invoice.InvoiceMasterService;
import com.pharma.core.pharmaservices.order.OrderMasterService;
import com.pharma.core.pharmaservices.order.OrderTransactionService;
import com.pharma.core.pharmaservices.patientservices.PatientAccountService;
import com.pharma.core.pharmaservices.pharmacygroupservices.GroupMasterService;
import com.pharma.core.pharmaservices.pioneer.PrescriptionRxTransactionStatusTypeService;
import com.pharma.core.repository.invoice.InvoiceMasterRepository;
import com.pharma.core.repository.pharmacygroup.GroupDirectorAccountRespository;
import com.pharma.core.repository.pharmacygroup.GroupMasterRespository;
import com.pharma.core.repository.physician.PhysicianAccountRespository;
import com.pharma.core.repository.physician.PhysicianAssistantAccountRespository;
import com.pharma.core.util.PharmacyUtil;
/**
 * This class is a controller to load the generated order details
 */
@Controller
public class OrderController extends OrderBaseController{
	
	@Autowired
	GroupMasterService groupService;

	@Autowired
	PhysicianAccountRespository physicianRepo;

	@Autowired
	PhysicianAssistantAccountRespository assistantRepo;
	
	@Autowired
	PatientAccountService patientAccountService;
	
	@Autowired
	OrderMasterService orderMasterService;	
	
	@Autowired
	OrderTransactionService orderTransactionService;	
	
	@Autowired
	PrescriptionRxTransactionStatusTypeService rxTranService;
	
	@Autowired
	InvoiceMasterService invoiceMasterService;
	
	@Autowired
	private Environment env;
	
	@Autowired
	GroupDirectorAccountRespository groupDirRepo;
	
	/**
	 * This method loads the order summary page
	 * @param model
	 * @param request
	 * @param response
	 * @param session
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping("/orderSummary")
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
				//temp commented on jan 19,2018
				int phyId=user.getPhysicianAssistantPhysicianId();
				//model.addAttribute("physicianId",  assistantRepo.findOne(user.getUserid()).getPhysicianId() );
				model.addAttribute("physicianId",phyId);
			}
			else
				model.addAttribute("physicianId",  0);
			if (PharmacyUtil.userPatient.equalsIgnoreCase(user.getType()))
				model.addAttribute("patientId",  user.getUserid());
			else
				model.addAttribute("patientId",  0);
			if (PharmacyUtil.userGroupDirector.equalsIgnoreCase(user.getType()))
				model.addAttribute("groupId", groupDirRepo.findOne( user.getUserid()).getGroupId() );
			else
				model.addAttribute("groupId", 0);

			List<PrescriptionRxTransactionStatusType> rxTranList = rxTranService.getAllActiveStatus();
			mv.addObject("statusList", rxTranList);
			
			OrderForm form = new OrderForm();
			mv.addObject("form", form);
			
			mv.setViewName("orderSummary");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return mv;
	}
	/**
	 * This method loads the order summary data
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
	 * @param fromDate
	 * @param toDate
	 * @param status
	 * @param physicianId
	 * @param patientId
	 * @param rxNo
	 * @param groupId
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/getPatientOrderSummaryData", method = RequestMethod.POST)
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
			@RequestParam("columns[4][search][regex]") String col4SearchRegex, @RequestParam("orderNo") String orderNo, 
			@RequestParam("phyname") String phyname, @RequestParam("patientname") String patientName, 
			@RequestParam("fromDate") String fromDate, @RequestParam("toDate") String toDate, @RequestParam("status") String status,
			@RequestParam("physicianId") String physicianId, @RequestParam("patientId") String patientId, @RequestParam("rxNo") String rxNo, 
			@RequestParam("groupId") String groupId,  HttpServletRequest request, HttpSession session) {
		
		return orderMasterService.getOrderDataList(draw, start, len, searchTerm, orderColumn, orderDir, orderNo, phyname, patientName, 
				fromDate, toDate, status, Integer.parseInt(physicianId), Integer.parseInt(patientId), Integer.parseInt(groupId), rxNo);
	}
	/**
	 *  This method loads the order data
	 * @param model
	 * @param request
	 * @param response
	 * @param session
	 * @param form
	 * @return
	 */
	@RequestMapping(value="/viewOrders", method = RequestMethod.POST)  
	public ModelAndView editOrderForm(Model model, HttpServletRequest request, HttpServletResponse response, HttpSession session,
			@ModelAttribute("form") OrderForm form){
		
		ModelAndView mv = new ModelAndView();
		try {
			if (form.getOrderId() > 0) {
				form = orderMasterService.getOrderDetails(form.getOrderId());
				mv.addObject("orderForm", form);
				mv.setViewName("viewOrders");
			} else {
				mv.setViewName("orderSummary");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mv;
	}
	/**
	 *  This method loads the order transaction list data
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
	 * @param orderId
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/getOrderTransactionSummaryData", method = RequestMethod.POST)
	public @ResponseBody String getOrderTransactionSummaryData( @RequestParam("draw") int draw,
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
			@RequestParam("columns[4][search][regex]") String col4SearchRegex, 
			@RequestParam("orderId") int orderId, HttpServletRequest request, HttpSession session) {
		
		return orderTransactionService.getOrderTransactionDataList(draw, start, len, searchTerm, orderColumn, orderDir,orderId);
	}
	/**
	 * This method downloads the order print pdf
	 * @param model
	 * @param request
	 * @param response
	 * @param session
	 * @param form
	 * @param redirectAttributes
	 */
	@RequestMapping(value = "/orderPdfDownload", method = RequestMethod.POST)
	public void orderPdfDownload(Model model, HttpServletRequest request, HttpServletResponse response, HttpSession session,
			 @ModelAttribute("form") OrderForm form, RedirectAttributes redirectAttributes) {
		
		try {
			//System.out.println("prescription PDF download File id ---> " + form.getOrderId());
			if (form.getOrderId() > 0) {
				String downloadName="Order_"+form.getOrderNumber()+".pdf";
				String rootFilePath= PharmacyUtil.getRootFolderForOrderPDF(session, env);
				String targetFolder = rootFilePath + File.separator + form.getOrderId();
			    String fileName = targetFolder + File.separatorChar + "Order_"+form.getOrderId()+".pdf";
				
			    boolean isPdfReady = orderMasterService.generateOrderPdf(form, rootFilePath, session, env);
			    if (isPdfReady)
			    	PharmacyUtil.downloadFile(fileName, downloadName, response);		
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 *  This method navigates to the order related prescription
	 * @param model
	 * @param form
	 * @param request
	 * @param response
	 * @param session
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "/orderPrescription", method = RequestMethod.POST)
	public ModelAndView backToPrescription(Model model, @ModelAttribute("form") OrderForm form, 
			HttpServletRequest request, HttpServletResponse response, HttpSession session, RedirectAttributes redirectAttributes) {
		ModelAndView mv = new ModelAndView();
		
		try {
			if (form.getPrescriptionId() > 0) {
				int PrescriptionId = form.getPrescriptionId();
				PrescriptionForm prescriptionForm = new PrescriptionForm();
				prescriptionForm.setPrescriptionId(PrescriptionId);
				prescriptionForm.setOrderId(form.getOrderId());
				redirectAttributes.addFlashAttribute("form", prescriptionForm);
				mv.setViewName("redirect:/prescription/editPrescription");
			}  else {
				mv.setViewName("error500");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return mv;
	}
	/**
	 *  This method navigates to the order related invoice
	 * @param model
	 * @param form
	 * @param request
	 * @param response
	 * @param session
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "/orderInvoice", method = RequestMethod.POST)
	public ModelAndView backToInvoice(Model model, @ModelAttribute("form") OrderForm form, 
			HttpServletRequest request, HttpServletResponse response, HttpSession session, RedirectAttributes redirectAttributes) {
		ModelAndView mv = new ModelAndView();
		try {
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
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return mv;
	}
}
