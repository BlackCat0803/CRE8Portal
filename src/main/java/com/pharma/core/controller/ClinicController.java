package com.pharma.core.controller;

import java.util.List;

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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pharma.core.formBean.LoginForm;
import com.pharma.core.formBean.clinic.ClinicForm;
import com.pharma.core.formBean.physician.PhysicianAssistantRegistration;
import com.pharma.core.model.States;
import com.pharma.core.model.clinic.Clinic;
import com.pharma.core.model.pharmacygroup.GroupMaster;
import com.pharma.core.model.physician.PhysicianAccount;
import com.pharma.core.pharmaservices.StatesService;
import com.pharma.core.pharmaservices.clinic.ClinicService;
import com.pharma.core.pharmaservices.pharmacygroupservices.GroupMasterService;
import com.pharma.core.pharmaservices.physicianservices.PhysicianAccountService;
import com.pharma.core.repository.pharmacygroup.GroupDirectorAccountRespository;
import com.pharma.core.repository.pharmacygroup.GroupMasterRespository;
import com.pharma.core.repository.physician.PhysicianAccountRespository;
import com.pharma.core.util.PharmacyUtil;
/**
 *	This class is a controller to save and load the clinic details
 */
@Controller
@PropertySource("classpath:physicianAccount.properties")
public class ClinicController extends ClinicBaseController {

	@Autowired
	ClinicService clinicService;
	
	@Autowired
	StatesService statesService;
	
	@Autowired
	GroupMasterService groupMasterService;
	
	@Autowired
	GroupMasterRespository groupRepo;
	
	@Autowired
	PhysicianAccountService phyService;
	
	@Autowired
	GroupDirectorAccountRespository groupDirRepo;
	
	@Autowired
	PhysicianAccountRespository phyAccRepo;
	
	@Autowired
	private Environment env;
	
	/**
	 * This method loads the clinic summary page
	 * @param model
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping("/clinicSummary")
	public ModelAndView loadClinicSummary(Model model, HttpServletRequest request, 
			HttpServletResponse response, HttpSession session){
		ModelAndView mv = new ModelAndView();
		try {
			List<GroupMaster> groupList = groupMasterService.getAllGroupMaster();
			model.addAttribute("message", model.asMap().get("message"));
			List<States> statesList = statesService.getAllStates();
			ClinicForm form = new ClinicForm();
			int groupId=0;
			LoginForm loggedInUser = (LoginForm) session.getAttribute("loginDetail");
			
			if(loggedInUser.getType().equalsIgnoreCase(PharmacyUtil.userPhysician)) {
				groupId=phyService.getPhysicianData(loggedInUser.getUserid(), env.getProperty("file.photo_path"), env.getProperty("file.logo_path")).getGroupId();
				form.setGroupId(groupId);
			}
			if(loggedInUser.getType().equalsIgnoreCase(PharmacyUtil.userPhysicianAssistant)) {
				int phyId=loggedInUser.getPhysicianAssistantPhysicianId();
				groupId=phyService.getPhysicianData(phyId, env.getProperty("file.photo_path"), env.getProperty("file.logo_path")).getGroupId();
				form.setGroupId(groupId);
			}
			if (PharmacyUtil.userGroupDirector.equalsIgnoreCase(loggedInUser.getType())) {
				groupId = groupDirRepo.findOne( loggedInUser.getUserid()).getGroupId();
				form.setGroupId(groupId);
			}
			
			
			
			mv.addObject("groupList",groupList);
			mv.addObject("form", form);
			mv.addObject("statesList", statesList);
			mv.setViewName("clinicSummary");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return mv;
	}
	/**
	 * This method loads the clinic summary data
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
	 * @param email
	 * @param clinicName
	 * @param state
	 * @param status
	 * @param groupName
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/getClinicSummaryData", method = RequestMethod.POST)
	public @ResponseBody String getClinicData(@RequestParam("draw") int draw, @RequestParam("start") int start, @RequestParam("length") int len,
			@RequestParam("search[value]") String searchTerm,
			@RequestParam("order[0][column]") int orderColumn, @RequestParam("order[0][dir]") String orderDir, 
			@RequestParam("columns[0][data]") String col0Data,
			@RequestParam("columns[0][name]") String col0Name, @RequestParam("columns[0][searchable]") String col0Search, @RequestParam("columns[0][orderable]") String col0Order,
			@RequestParam("columns[0][search][value]") String col0SearchValue, @RequestParam("columns[0][search][regex]") String col0SearchRegex,
			@RequestParam("columns[1][data]") String col1Data, @RequestParam("columns[1][name]") String col1Name, @RequestParam("columns[1][searchable]") String col1Search,
			@RequestParam("columns[1][orderable]") String col1Order, @RequestParam("columns[1][search][value]") String col1SearchValue,
			@RequestParam("columns[1][search][regex]") String col1SearchRegex,
			@RequestParam("columns[2][data]") String col2Data, @RequestParam("columns[2][name]") String col2Name, @RequestParam("columns[2][searchable]") String col2Search,
			@RequestParam("columns[2][orderable]") String col2Order, @RequestParam("columns[2][search][value]") String col2SearchValue, 
			@RequestParam("columns[2][search][regex]") String col2SearchRegex, 
			@RequestParam("columns[3][data]") String col3Data, @RequestParam("columns[3][name]") String col3Name, @RequestParam("columns[3][searchable]") String col3Search,
			@RequestParam("columns[3][orderable]") String col3Order, @RequestParam("columns[3][search][value]") String col3SearchValue, 
			@RequestParam("columns[3][search][regex]") String col3SearchRegex,
			@RequestParam("columns[4][data]") String col4Data, @RequestParam("columns[4][name]") String col4Name, @RequestParam("columns[4][searchable]") String col4Search,
			@RequestParam("columns[4][orderable]") String col4Order, @RequestParam("columns[4][search][value]") String col4SearchValue, 
			@RequestParam("columns[4][search][regex]") String col4SearchRegex,
			@RequestParam("columns[5][data]") String col5Data, @RequestParam("columns[5][name]") String col5Name, @RequestParam("columns[5][searchable]") String col5Search,
			@RequestParam("columns[5][orderable]") String col5Order, @RequestParam("columns[5][search][value]") String col5SearchValue, 
			@RequestParam("columns[5][search][regex]") String col5SearchRegex, @RequestParam("email") String email, @RequestParam("clinicName") String clinicName, 
			@RequestParam("state") String state, @RequestParam("status") String status, @RequestParam("groupName") int groupName,
			HttpServletRequest request,HttpSession session ) {
		
		LoginForm loggedInUser = (LoginForm) session.getAttribute("loginDetail");
		
		if(loggedInUser.getType().equalsIgnoreCase(PharmacyUtil.userPhysician)) {
			groupName=phyService.getPhysicianData(loggedInUser.getUserid(), env.getProperty("file.photo_path") , env.getProperty("file.logo_path")).getGroupId();
		}
		return clinicService.getClinicDataList(draw, start, len, searchTerm, orderColumn, orderDir, clinicName, state, status, email,groupName);
	}
	
	
	/**
	 * This method returns the clinic address details
	 * @param model
	 * @param request
	 * @param response
	 * @param session
	 * @param clinicId
	 * @return
	 */
	@RequestMapping(value = "/getClinicAddress", method = RequestMethod.POST)
	public @ResponseBody String getClinicAddress(Model model, HttpServletRequest request, 
			HttpServletResponse response, HttpSession session, @RequestParam("clinicId") String clinicId) {
		
		return clinicService.getClinicAddressById(clinicId);
	}
	
	/**
	 * This method loads the clinic page
	 * @param model
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping("/clinicAccount")  
	public ModelAndView loadClinicAccount(Model model, HttpServletRequest request, HttpServletResponse response, HttpSession session){
		
		ModelAndView mv = new ModelAndView();
		try {
			List<States> statesList = statesService.getAllStates();
			
			List<GroupMaster> groupList = groupMasterService.getAllGroupMaster("Active");
			int groupId=0;
			model.addAttribute("message", model.asMap().get("message"));
			
			ClinicForm form = null;
			try {
				form = (ClinicForm) model.asMap().get("form");	
			} catch(Exception e) {
				e.printStackTrace();
			}
			if (form == null) {
				form = new ClinicForm();
				form.setStatus(PharmacyUtil.statusActive);
				
				LoginForm loggedInUser = (LoginForm) session.getAttribute("loginDetail");
				
				if(loggedInUser.getType().equalsIgnoreCase(PharmacyUtil.userPhysician)) {
					groupId=phyService.getPhysicianData(loggedInUser.getUserid(), env.getProperty("file.photo_path"),  env.getProperty("file.logo_path")).getGroupId();
					form.setGroupId(groupId);
				}
				if(loggedInUser.getType().equalsIgnoreCase(PharmacyUtil.userPhysicianAssistant)) {
					int phyId=loggedInUser.getPhysicianAssistantPhysicianId();
					groupId=phyService.getPhysicianData(phyId, env.getProperty("file.photo_path"), env.getProperty("file.logo_path")).getGroupId();
					form.setGroupId(groupId);
				}
				if (PharmacyUtil.userGroupDirector.equalsIgnoreCase(loggedInUser.getType())) {
					groupId = groupDirRepo.findOne( loggedInUser.getUserid()).getGroupId();
					form.setGroupId(groupId);
				}
			}
			int newGroupId=0;
			List<GroupMaster> groupMastList= groupRepo.findByNewGroup();
			if (groupMastList.size() > 0) {
				for (GroupMaster g: groupMastList) {
					newGroupId=g.getId();
					form.setNewGroupId(newGroupId);
				}
			}
			
			mv.addObject("groupList", groupList);
			mv.addObject("statesList", statesList);
			mv.addObject("form", form);
			
			model.addAttribute("message", model.asMap().get("message"));
			model.addAttribute("saveStatus", model.asMap().get("saveStatus"));
			
			mv.setViewName("clinicPage");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return mv;
	}
	/**
	 *  This method loads the clinic data
	 * @param model
	 * @param request
	 * @param response
	 * @param session
	 * @param form
	 * @return
	 */
	@RequestMapping("/editClinicAccount")  
	public ModelAndView editClinicAccount(Model model, HttpServletRequest request, HttpServletResponse response, HttpSession session,
			@ModelAttribute("form") ClinicForm form){
		
		ModelAndView mv = new ModelAndView();
		
		try {
			List<States> statesList = statesService.getAllStates();
			List<GroupMaster> groupList = groupMasterService.getAllGroupMaster("Active");
			
			model.addAttribute("message", model.asMap().get("message"));

			int id = 0;
			if (form != null && form.getClinicId()>0)
				id = form.getClinicId(); 
			if (id > 0) {
				if (model.asMap().get("saveStatus") == null)
					form = clinicService.getClinicAccountById(id);
				
				int newGroupId=0;
				List<GroupMaster> groupMastList= groupRepo.findByNewGroup();
				if (groupMastList.size() > 0) {
					for (GroupMaster g: groupMastList) {
						newGroupId=g.getId();
						form.setNewGroupId(newGroupId);
					}
				}
				
				mv.addObject("groupList", groupList);
				mv.addObject("statesList", statesList);
				mv.addObject("form", form);
				
				model.addAttribute("message", model.asMap().get("message"));
				model.addAttribute("saveStatus", model.asMap().get("saveStatus"));
				
				
				// pick physicians list for selected clinic
				List<PhysicianAccount> physicianList = phyAccRepo.getPhysicianListByClinicId(id);
				model.addAttribute("physicianList", physicianList);
				
				// pick physician Assistants list for selected clinic
				List<PhysicianAssistantRegistration> physicianAssistantList = clinicService.getPhysicianAssistantByClinicId(id);
				model.addAttribute("physicianAssistantList", physicianAssistantList);

				mv.setViewName("clinicPage");
			} else {
				mv.setViewName("redirect:clinicSummary");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		return mv;
	}
	
	
	/**
	 * This method save and loads the clinic page
	 * @param form
	 * @param bindingResult
	 * @param model
	 * @param request
	 * @param response
	 * @param session
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "/saveClinicAccount", method = RequestMethod.POST)
	public ModelAndView saveNewClinic(@ModelAttribute("form") ClinicForm form,  BindingResult bindingResult, Model model,
			HttpServletRequest request, HttpServletResponse response, HttpSession session, RedirectAttributes redirectAttributes) {
		ModelAndView mv = new ModelAndView();
		
		try {
			LoginForm loggedInUser = (LoginForm) session.getAttribute("loginDetail");
			
			 List<Clinic> exObj = null;
			 List<Clinic> emailObj = null;
			 if (form.getClinicId() > 0) {
				 exObj = clinicService.getClinicByNameAndNotId(form.getClinicName(), form.getClinicId());
			 } else {
				 exObj = clinicService.getClinicByName(form.getClinicName());
			 }
			 if (!"".equalsIgnoreCase(form.getEmail())) {
				 if (form.getClinicId() > 0) {
					 emailObj = clinicService.getClinicByEmailAndNotId(form.getEmail(), form.getClinicId());	 
				 } else {
					 emailObj = clinicService.getClinicByEmail(form.getEmail());	 
				 }
			 }
			 int newGroupId=0;
				List<GroupMaster> groupMastList= groupRepo.findByNewGroup();
				if (groupMastList.size() > 0) {
					for (GroupMaster g: groupMastList) {
						newGroupId=g.getId();
						form.setNewGroupId(newGroupId);
					}
				}
			 
			if (exObj.size() == 0 && (emailObj == null || emailObj.size() == 0)) {
				Clinic acc = clinicService.saveClinic(form, loggedInUser);
				
				if (acc != null) {
					redirectAttributes.addFlashAttribute("saveStatus", "true");
					if (form.getClinicId() > 0) 
						redirectAttributes.addFlashAttribute("message", "Clinic Account updated successfully");
					else
						redirectAttributes.addFlashAttribute("message", "Clinic Account created successfully");
					
					form.setClinicId(acc.getId());
					redirectAttributes.addFlashAttribute("form", form);
					mv.setViewName("redirect:editClinicAccount");
				} else {
					model.addAttribute("message", "Clinic Account failed to create");
					mv.setViewName("error400");
				}
			} else {
				redirectAttributes.addFlashAttribute("saveStatus", "false");
				if (emailObj.size() > 0)
					redirectAttributes.addFlashAttribute("message", "Clinic email already exists");
				else
					redirectAttributes.addFlashAttribute("message", "Clinic name already exists");

				if (form.getClinicId() > 0){
					form.setClinicId(form.getClinicId());
					redirectAttributes.addFlashAttribute("form", form);
					mv.setViewName("redirect:editClinicAccount");
				}else {
					redirectAttributes.addFlashAttribute("form", form);
					mv.setViewName("redirect:clinicAccount");
				}	
			}
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("message", "Clinic Account failed to create");
			mv.setViewName("error500");
		}
		return mv;
	}
	
	/**
	 * This method deletes the clinic data
	 * @param form
	 * @param bindingResult
	 * @param model
	 * @param request
	 * @param response
	 * @param session
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "/deleteClinic", method = RequestMethod.POST)
	public ModelAndView deleteClinic(@ModelAttribute("form") ClinicForm form,  BindingResult bindingResult, Model model,
			HttpServletRequest request, HttpServletResponse response, HttpSession session, RedirectAttributes redirectAttributes) {
		
		ModelAndView mv = new ModelAndView();
		try {
			LoginForm loggedInUser = (LoginForm) session.getAttribute("loginDetail");
			
			int id = 0;
			if (form != null && form.getClinicId()>0)
				id = form.getClinicId(); 
			
			int newGroupId=0;
			List<GroupMaster> groupMastList= groupRepo.findByNewGroup();
			if (groupMastList.size() > 0) {
				for (GroupMaster g: groupMastList) {
					newGroupId=g.getId();
					form.setNewGroupId(newGroupId);
				}
			}
			if (id > 0) {
				boolean isDeleted = clinicService.deleteClinicAccountById(id, loggedInUser);
				
				if (isDeleted) {
					model.addAttribute("message", "Clinic Deleted Successfully");
					model.addAttribute("saveStatus", "Clinic Deleted Successfully");	
				} else {
					model.addAttribute("message", "Failed to delete Clinic");
					model.addAttribute("saveStatus", "Failed to delete Clinic");
				}
			}
			
			mv.setViewName("redirect:clinicSummary");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mv;
	}
	
	/**
	 * This method loads the physician list by clinic
	 * @param draw
	 * @param start
	 * @param len
	 * @param searchTerm
	 * @param orderColumn
	 * @param orderDir
	 * @param col0Data
	 * @param clinicId
	 * @param physicianName
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/getPhysicianListByClinicData", method = RequestMethod.POST)
	public @ResponseBody String getPhysicianListByClinicData(@RequestParam("draw") int draw, @RequestParam("start") int start, @RequestParam("length") int len,
			@RequestParam("search[value]") String searchTerm, @RequestParam("order[0][column]") int orderColumn, @RequestParam("order[0][dir]") String orderDir, 
			@RequestParam("columns[0][data]") String col0Data, @RequestParam("clinicId") int clinicId, @RequestParam("physicianName") String physicianName, HttpServletRequest request,HttpSession session ) {
		
		return clinicService.getPhysicianDataByClinicId(draw, start, len, searchTerm, orderColumn, orderDir, clinicId,physicianName);
	}
	
	/**
	 * This method loads the physician assistant list by clinic
	 * @param draw
	 * @param start
	 * @param len
	 * @param searchTerm
	 * @param orderColumn
	 * @param orderDir
	 * @param col0Data
	 * @param clinicId
	 * @param assistantName
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/getPhysicianAssistantListByClinicData", method = RequestMethod.POST)
	public @ResponseBody String getPhysicianAssistantListByClinicData(@RequestParam("draw") int draw, @RequestParam("start") int start, @RequestParam("length") int len,
			@RequestParam("search[value]") String searchTerm, @RequestParam("order[0][column]") int orderColumn, @RequestParam("order[0][dir]") String orderDir, 
			@RequestParam("columns[0][data]") String col0Data, @RequestParam("clinicId") int clinicId, @RequestParam("assistantName") String assistantName, HttpServletRequest request,HttpSession session ) {
		
		return clinicService.getPhysicianAssistantDataByClinicId(draw, start, len, searchTerm, orderColumn, orderDir, clinicId,assistantName);
	}

	
}
