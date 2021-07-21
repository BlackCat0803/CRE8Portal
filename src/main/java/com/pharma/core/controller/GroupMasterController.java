package com.pharma.core.controller;

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
import com.pharma.core.formBean.LoginForm;
import com.pharma.core.formBean.patient.PatientAccountForm;
import com.pharma.core.formBean.pharmacygroup.GroupMasterForm;
import com.pharma.core.formBean.physician.PhysicianAssistantRegistration;
import com.pharma.core.formBean.physician.PhysicianProfile;
import com.pharma.core.model.pharmacygroup.GroupMaster;
import com.pharma.core.pharmaservices.pharmacygroupservices.GroupMasterServiceImpl;
import com.pharma.core.util.PharmacyUtil;
/**
 * This class is a controller to save and load the group master details
 */
@Controller
@PropertySource("classpath:groupMaster.properties")

public class GroupMasterController extends GroupBaseController{
	
	@Autowired
	GroupMasterServiceImpl groupMasterService;
	
	@Autowired
	private Environment env;
	/**
	 * This method loads the group master summary page
	 * @param model
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping("/groupMasterSummary")
	public ModelAndView loadGroupMasterSummary(Model model, HttpServletRequest request, HttpServletResponse response, 
			HttpSession session){
		ModelAndView mv = new ModelAndView();
		
		try {
			GroupMasterForm form = new GroupMasterForm();
			mv.addObject("groupMasterForm", form);
			
			model.addAttribute("message", model.asMap().get("message"));
			mv.setViewName("groupMasterSummary");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mv;
	}
	/**
	 * This method loads the group master summary data
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
	 * @param groupName
	 * @param status
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getGroupMasterData", method = RequestMethod.POST)
	public @ResponseBody String getGroupMasterData(@RequestParam("draw") int draw,
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
			@RequestParam("groupName") String groupName,
			@RequestParam("status") String status, HttpServletRequest request) {
		
		return groupMasterService.getGroupMasterDataList(draw, start, len, searchTerm, orderColumn, orderDir, groupName, status);
	}	
	/**
	 * This method loads the group master page
	 * @param model
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/newGroupMasterAccount",  method = {RequestMethod.POST, RequestMethod.GET})
	public ModelAndView newGroupMasterAccount(Model model, HttpServletRequest request, HttpServletResponse response, HttpSession session){

		ModelAndView mv = new ModelAndView();
		
		try {
			GroupMasterForm form = null;
			try {
				form = (GroupMasterForm) model.asMap().get("form");	
			} catch(Exception e) {
				e.printStackTrace();
			}
			if (form == null) {
				form = new GroupMasterForm();
				form.setStatus(PharmacyUtil.statusActive);
			}
			mv.addObject("groupMasterForm", form);
			
			model.addAttribute("message", model.asMap().get("message"));
			mv.setViewName("groupMasterPage");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return mv;
	}
	
	/**
	 * This method save and loads the group master page
	 * @param form
	 * @param model
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/editGroupMasterAccount", method = {RequestMethod.POST, RequestMethod.GET})
	public ModelAndView editGroupMasterAccount(@ModelAttribute("groupMasterForm") GroupMasterForm form, Model model, 
			HttpServletRequest request, HttpServletResponse response, HttpSession session){
		
		ModelAndView mv = new ModelAndView();
		
		try {
			form = groupMasterService.getGroupMasterData(form.getGroupId(), env.getProperty("file.logo_path"));
			
			//January 31, 2018 Rohini
			//Put a variable and random number in the image url to refresh the image after uploading
			//example : photoUrl?x=123456
			
			int randomNumber = PharmacyUtil.randomNumberGenerator();
			
			String logoUrl = form.getLogoFile();
			
			if(logoUrl==null || logoUrl.length()==0)
			{
				
				logoUrl="images/default_logo.jpg";
			}
			
			logoUrl = logoUrl+"?x="+randomNumber;
			form.setLogoFile(logoUrl);
			//		//System.out.println("logoUrl ===  "+logoUrl);
			//January 31, 2018 Rohini
			mv.addObject("groupMasterForm", form);
			
			mv.addObject("physicianForm", new PhysicianProfile());
			mv.addObject("assistantForm", new PhysicianAssistantRegistration());
			mv.addObject("patientForm", new PatientAccountForm());
			
			//		List<GroupMaster> groupMasterList = groupMasterService.getAllGroupMasterExceptGroupId(form.getGroupId());
			//		mv.addObject("groupList", groupMasterList);
			
			List<GroupMaster> otherGroupMasterList = groupMasterService.getAllOtherGroupMaster(PharmacyUtil.statusActive,form.getGroupId());
			mv.addObject("othergroupList", otherGroupMasterList);
			
			mv.addObject("physicianList", groupMasterService.getPhysicianListForGroupChange());
			
			model.addAttribute("message", model.asMap().get("message"));
			mv.setViewName("groupMasterPage");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return mv;
	}
	/**
	 * This method loads the group master page
	 * @param model
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/loadGroupMaster", method = {RequestMethod.POST, RequestMethod.GET})
	public ModelAndView loadGroupMasterAccount(Model model, HttpServletRequest request, HttpServletResponse response, HttpSession session){
		
		ModelAndView mv = new ModelAndView();
		try {
			LoginForm loggedInUser = (LoginForm) session.getAttribute("loginDetail");
			
			GroupMasterForm form = groupMasterService.getGroupMasterData(loggedInUser.getGroupid(), env.getProperty("file.logo_path"));
			mv.addObject("groupMasterForm", form);
			
			mv.addObject("physicianForm", new PhysicianProfile());
			mv.addObject("assistantForm", new PhysicianAssistantRegistration());
			mv.addObject("patientForm", new PatientAccountForm());
			
			GroupMaster gp = groupMasterService.getGroupMasterDetails(form.getGroupId());
			
			List<GroupMaster> groupMasterList = new ArrayList<GroupMaster>();
			groupMasterList.add(gp);
			mv.addObject("groupList", groupMasterList);
			
			List<GroupMaster> otherGroupMasterList = groupMasterService.getAllOtherGroupMaster(PharmacyUtil.statusActive,gp.getId());
			mv.addObject("othergroupList", otherGroupMasterList);
			
			mv.addObject("physicianList", groupMasterService.getPhysicianListForGroupChange(form.getGroupId()));
			
			
			model.addAttribute("message", model.asMap().get("message"));
			mv.setViewName("groupMasterPage");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return mv;
	}

	/**
	 * This method save and loads the group master page
	 * @param form
	 * @param bindingResult
	 * @param model
	 * @param request
	 * @param response
	 * @param session
	 * @param uploadLogoFile
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "/saveGroupMaster", method = RequestMethod.POST)
	public ModelAndView saveGroupMaster(@ModelAttribute("groupMasterForm") GroupMasterForm form,  BindingResult bindingResult, Model model,
			  HttpServletRequest request, HttpServletResponse response, HttpSession session, @RequestParam CommonsMultipartFile uploadLogoFile,
			  RedirectAttributes redirectAttributes) {
		
		ModelAndView mv = new ModelAndView();
		
		 try {
			 LoginForm loggedInUser = (LoginForm) session.getAttribute("loginDetail");
			 GroupMaster exObj = null; 
			 if (form.getGroupId() > 0) {
				 exObj = groupMasterService.getGroupMasterByGroupNameAndNotId(form.getGroupName(), form.getGroupId());
			 } else {
				 exObj = groupMasterService.getGroupMasterByGroupName(form.getGroupName());
			 }
			 
			if (exObj == null) {
				GroupMaster acc =null;
				
				/**
				 *  Added on Feb 9,2018 by shalini
				 *  Changing of Groups or Deactivating a Group
					Alert: Do you wish to re-assign the Group for all Physicians, Assistants, Patients of this Group to a different Group?
					Display Group: <Group dropdown>
					       Yes and No buttons in the Alert
					
					If they Click No, the Show the Alert:
					All Group Directors, Physicians and Assistants of this Group will be DeActivated(Denied). If you wish to re-assign them to different Groups go to lists shown an re-assign"
				 */
				String reAssignGroup="",deactivateGroup="";
				
				if(request.getParameter("reAssignGroup")!=null)
					reAssignGroup=request.getParameter("reAssignGroup")+"";
				
				if(request.getParameter("deactivateGroup")!=null)
					deactivateGroup=request.getParameter("deactivateGroup")+"";
				
				//System.out.println(reAssignGroup+"======"+deactivateGroup);
				//Re-assign new group and deactivate the current group
				if(reAssignGroup.length()>0 || deactivateGroup.length()>0)
				{
					if(reAssignGroup.length()>0 && reAssignGroup.equalsIgnoreCase("true"))
					{
						int otherGroupId = form.getOtherGroupId();
						if (otherGroupId > 0) {
							//reAssignGroup
							groupMasterService.reAssignGroup( otherGroupId,  form.getGroupId(),loggedInUser);
							
						}
					}
					else if(deactivateGroup.length()>0 && deactivateGroup.equalsIgnoreCase("true"))
					{
						
							//deactivate Group
							groupMasterService.deActivateGroup( form.getGroupId(),loggedInUser);
							
						
					}
				}
				
				String rootFilePath= PharmacyUtil.getRootFolderForLogo(session, env);
				acc = groupMasterService.saveGroupMaster(form, loggedInUser,uploadLogoFile, rootFilePath);
				
				
				if (acc != null) {
					
					redirectAttributes.addFlashAttribute("saveStatus", "true");
					
					if(reAssignGroup.length()>0 || deactivateGroup.length()>0)
					{
						if(reAssignGroup.length()>0 && reAssignGroup.equalsIgnoreCase("true"))
						{
							int otherGroupId = form.getOtherGroupId();
							if (otherGroupId > 0) {
								//reAssignGroup
								redirectAttributes.addFlashAttribute("message", env.getProperty("info.reassigngroup"));
								
							}
						}
						else if(deactivateGroup.length()>0 && deactivateGroup.equalsIgnoreCase("true"))
						{
							
								//deactivate Group
								redirectAttributes.addFlashAttribute("message",  env.getProperty("info.deactivategroup"));
								
							
						}
					}else
					{
					
						if (form.getGroupId() > 0) 
							redirectAttributes.addFlashAttribute("message", "Group updated successfully");
						else
							redirectAttributes.addFlashAttribute("message", "Group created successfully");
					}
					
					form.setGroupId(acc.getId());
					redirectAttributes.addFlashAttribute("groupMasterForm", form);
					mv.setViewName("redirect:editGroupMasterAccount");
				} else {
					model.addAttribute("message", "Group failed to create");
					mv.setViewName("error400");
				}
				
				
				
				
			} else {
				redirectAttributes.addFlashAttribute("saveStatus", "false");
				redirectAttributes.addFlashAttribute("message", "Group name already exists");
				
				if (form.getGroupId() > 0){
					form.setGroupId(form.getGroupId());
					redirectAttributes.addFlashAttribute("groupMasterForm", form);
					mv.setViewName("redirect:editGroupMasterAccount");
				}else {
					redirectAttributes.addFlashAttribute("form", form);
					mv.setViewName("redirect:newGroupMasterAccount");
				}	
				
				//redirectAttributes.addFlashAttribute("message", "Email address already exist.");
				/*mv.addObject("groupMasterForm", form);*/
				/*if(loggedInUser.getType().equalsIgnoreCase(PharmacyUtil.userGroupDirector))
				{
					mv.setViewName("redirect:editGroupMasterAccount");
				}else
					mv.setViewName("redirect:groupMasterSummary");*/
			}
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("message", "Group failed to create");
			mv.setViewName("error500");
		}
		return mv;
	}	
	
	
	
	
	/**
	 * This method gets the group master data
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/ajaxEditGroupMasterAccount", method = RequestMethod.POST)
	public @ResponseBody String fetchGroupMasterAccountbyAjax(@RequestParam("id") int id) {
		
		String json2=null;;
		try {
			GroupMasterForm form = groupMasterService.getGroupMasterData(id, env.getProperty("file.logo_path"));
			
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			json2 = gson.toJson(form);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json2;
	}
	
	/**
	 * This method deletes the group master data
	 * @param model
	 * @param request
	 * @param response
	 * @param session
	 * @param form
	 * @return
	 */
	@RequestMapping(value = "/deleteGroup", method = RequestMethod.POST)
	public ModelAndView deleteGroupMasterAccount(Model model, HttpServletRequest request, HttpServletResponse response, HttpSession session,
			@ModelAttribute("groupMasterForm") GroupMasterForm form) {
		
		ModelAndView mv = new ModelAndView();
		try {
			LoginForm loggedInUser = (LoginForm) session.getAttribute("loginDetail");
			
			if (form.getGroupId() > 0) {
				boolean isDeleted = groupMasterService.deleteClinicAccountById(form.getGroupId(), loggedInUser);
				
				if (isDeleted) {
					model.addAttribute("message", "Group Master Deleted Successfully");
					model.addAttribute("saveStatus", "Group Master Deleted Successfully");	
				} else {
					model.addAttribute("message", "Failed to delete Group Master");
					model.addAttribute("saveStatus", "Failed to delete Group Master");
				}
			}
			
			mv.setViewName("redirect:groupMasterSummary");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mv;
	}
	/**
	 *  This method returns the physician list by group
	 * @param draw
	 * @param start
	 * @param len
	 * @param searchTerm
	 * @param orderColumn
	 * @param orderDir
	 * @param col0Data
	 * @param groupId
	 * @param physicianName
	 * @param status
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/getPhysicianListByGroupData", method = RequestMethod.POST)
	public @ResponseBody String getPhysicianListByGroupData(@RequestParam("draw") int draw, @RequestParam("start") int start, @RequestParam("length") int len,
			@RequestParam("search[value]") String searchTerm, @RequestParam("order[0][column]") int orderColumn, @RequestParam("order[0][dir]") String orderDir, 
			@RequestParam("columns[0][data]") String col0Data, @RequestParam("groupId") int groupId, @RequestParam("physicianName") String physicianName, 
			@RequestParam("status") String status, HttpServletRequest request, HttpSession session ) {
		
		return groupMasterService.getPhysicianDataByGroupId(draw, start, len, searchTerm, orderColumn, orderDir, groupId, physicianName, status );
	}
	/**
	 *  This method returns the physician assistant list by group
	 * @param draw
	 * @param start
	 * @param len
	 * @param searchTerm
	 * @param orderColumn
	 * @param orderDir
	 * @param col0Data
	 * @param groupId
	 * @param assistantName
	 * @param status
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/getPhysicianAssistantListByGroupData", method = RequestMethod.POST)
	public @ResponseBody String getPhysicianAssistantListByGroupData(@RequestParam("draw") int draw, @RequestParam("start") int start, @RequestParam("length") int len,
			@RequestParam("search[value]") String searchTerm, @RequestParam("order[0][column]") int orderColumn, @RequestParam("order[0][dir]") String orderDir, 
			@RequestParam("columns[0][data]") String col0Data, @RequestParam("groupId") int groupId, @RequestParam("assistantName") String assistantName, 
			@RequestParam("status") String status, HttpServletRequest request,HttpSession session ) {
		
		return groupMasterService.getPhysicianAssistantDataByGroupId(draw, start, len, searchTerm, orderColumn, orderDir, groupId, assistantName, status);
	}
	/**
	 * This method returns the patient list by group
	 * @param draw
	 * @param start
	 * @param len
	 * @param searchTerm
	 * @param orderColumn
	 * @param orderDir
	 * @param col0Data
	 * @param groupId
	 * @param patientName
	 * @param status
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/getPatientListByGroupData", method = RequestMethod.POST)
	public @ResponseBody String getPatientListByGroupData(@RequestParam("draw") int draw, @RequestParam("start") int start, @RequestParam("length") int len,
			@RequestParam("search[value]") String searchTerm, @RequestParam("order[0][column]") int orderColumn, @RequestParam("order[0][dir]") String orderDir, 
			@RequestParam("columns[0][data]") String col0Data, @RequestParam("groupId") int groupId, @RequestParam("patientName") String patientName, 
			@RequestParam("status") String status, HttpServletRequest request,HttpSession session ) {
		
		return groupMasterService.getPatientDataByGroupId(draw, start, len, searchTerm, orderColumn, orderDir, groupId, patientName, status);
	}
	
	/**
	 *  This method changes the group of one physician to another group
	 * @param request
	 * @param response
	 * @param session
	 * @param physicianId
	 * @param groupId
	 * @param physicianName
	 * @param newGroupId
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "/changePhysicianGroup", method = RequestMethod.POST)
	public @ResponseBody String changePhysicianGroup(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			@RequestParam("physicianId") int physicianId, @RequestParam("groupId") int groupId, @RequestParam("physicianName") String physicianName,  
			@RequestParam("newGroupId") int newGroupId, RedirectAttributes redirectAttributes) {
		String output="";
		try {
			if (physicianId > 0 && groupId > 0) {
				LoginForm loggedInUser = (LoginForm) session.getAttribute("loginDetail");
				boolean isSaved = groupMasterService.changePhysicianGroup(physicianId, newGroupId, groupId, loggedInUser);
				if (isSaved) 
					output="Group of physician "+ physicianName +" changed successfully";
				else
					output="Failed to change group of physician " +physicianName;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return output;
	}
	
	/**
	 *  This method changes the group of one physician assistant to another group
	 * @param request
	 * @param response
	 * @param session
	 * @param assistantId
	 * @param groupId
	 * @param assistantName
	 * @param newGroupId
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "/changeAssistantGroup", method = RequestMethod.POST)
	public @ResponseBody String changeAssistantGroup(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			@RequestParam("assistantId") int assistantId, @RequestParam("groupId") int groupId, @RequestParam("assistantName") String assistantName,  
			@RequestParam("newGroupId") int newGroupId, RedirectAttributes redirectAttributes) {
		String output="";
		try {
			if (assistantId > 0 && groupId > 0) {
				LoginForm loggedInUser = (LoginForm) session.getAttribute("loginDetail");
				boolean isSaved = groupMasterService.changeAssistantGroup(assistantId, newGroupId, groupId, loggedInUser);
				if (isSaved) 
					output="Group of physician assistant "+ assistantName +" changed successfully";
				else
					output="Failed to change group of physician assistant " +assistantName;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return output;
	}

	/**
	 * This method changes the physician of one patient to another physician
	 * @param request
	 * @param response
	 * @param session
	 * @param patientId
	 * @param groupId
	 * @param patientName
	 * @param newPhysicianId
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "/changePatientPhysician", method = RequestMethod.POST)
	public @ResponseBody String changePatientPhysician(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			@RequestParam("patientId") int patientId, @RequestParam("groupId") int groupId, @RequestParam("patientName") String patientName,  
			@RequestParam("newPhysicianId") int newPhysicianId, RedirectAttributes redirectAttributes) {
		String output="";
		try {
			if (patientId > 0 && groupId > 0) {
				LoginForm loggedInUser = (LoginForm) session.getAttribute("loginDetail");
				boolean isSaved = groupMasterService.changePatientPhysicianInGroupMaster(patientId, newPhysicianId, groupId, loggedInUser);
				if (isSaved) 
					output="Physician of patient "+ patientName +" changed successfully";
				else
					output="Failed to change physician of patient " +patientName;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return output;
	}

	/**
	 * This method changes the physician status
	 * @param request
	 * @param response
	 * @param session
	 * @param physicianId
	 * @param status
	 * @param physicianName
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "/changePhysicianStatusFromGroup", method = RequestMethod.POST)
	public @ResponseBody String changePhysicianStatusFromGroup(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			@RequestParam("physicianId") int physicianId, @RequestParam("status") String status, @RequestParam("physicianName") String physicianName, 
			RedirectAttributes redirectAttributes) {
		
		String output="";
		try {
			if (physicianId > 0) {
				LoginForm loggedInUser = (LoginForm) session.getAttribute("loginDetail");
				boolean isSaved = groupMasterService.changePhysicianStatusFromGroupMaster(physicianId, status, loggedInUser, request);
				if (isSaved) 
					output="Physician "+ physicianName +" status changed successfully";
				else
					output="Failed to change staut of physician " +physicianName;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return output;
		
	}
	
	/**
	 * This method changes the physician assistant status
	 * @param request
	 * @param response
	 * @param session
	 * @param assistantId
	 * @param status
	 * @param assistantName
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "/changeAssistantStatusFromGroup", method = RequestMethod.POST)
	public @ResponseBody String changeAssistantStatusFromGroup(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			@RequestParam("assistantId") int assistantId, @RequestParam("status") String status, @RequestParam("assistantName") String assistantName, 
			RedirectAttributes redirectAttributes) {
		
		String output="";
		try {
			if (assistantId > 0) {
				LoginForm loggedInUser = (LoginForm) session.getAttribute("loginDetail");
				boolean isSaved = groupMasterService.changeAssistantStatusFromGroupMaster(assistantId, status, loggedInUser, request);
				if (isSaved) 
					output="Physician assistant "+ assistantName +" status changed successfully";
				else
					output="Failed to change staut of physician assistant " +assistantName;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return output;
	}
	
	/**
	 * This method changes the patient status
	 * @param request
	 * @param response
	 * @param session
	 * @param patientId
	 * @param status
	 * @param patientName
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "/changePatientStatusFromGroup", method = RequestMethod.POST)
	public @ResponseBody String changePatientStatusFromGroup(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			@RequestParam("patientId") int patientId, @RequestParam("status") String status, @RequestParam("patientName") String patientName, 
			RedirectAttributes redirectAttributes) {
		
		String output="";
		try {
			if (patientId > 0) {
				LoginForm loggedInUser = (LoginForm) session.getAttribute("loginDetail");
				boolean isSaved = groupMasterService.changePatientStatusFromGroupMaster(patientId, status, loggedInUser, request);
				if (isSaved) 
					output="Patient "+ patientName +" status changed successfully";
				else
					output="Failed to change staut of patient " +patientName;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return output;
		
	}
	/**
	 * This method re-assigns new group and deactivates the current group
	 * @param model
	 * @param request
	 * @param response
	 * @param session
	 * @param form
	 * @return
	 */
	@RequestMapping(value = "/reAssignGroup", method = RequestMethod.POST)
	public ModelAndView reAssignGroup(Model model, HttpServletRequest request, HttpServletResponse response, HttpSession session,
			@ModelAttribute("groupMasterForm") GroupMasterForm form) {
		ModelAndView mv = new ModelAndView();
		
		try {
			int otherGroupId = form.getOtherGroupId();
			if (otherGroupId > 0) {
					//reAssignGroup
					mv.setViewName("editGroupMasterAccount");
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
	/**
	 * This method deactivates the current group
	 * @param model
	 * @param request
	 * @param response
	 * @param session
	 * @param form
	 * @return
	 */
	@RequestMapping(value = "/deactivateGroup", method = RequestMethod.POST)
	public ModelAndView deactivateGroup(Model model, HttpServletRequest request, HttpServletResponse response, HttpSession session,
			@ModelAttribute("groupMasterForm") GroupMasterForm form) {
		ModelAndView mv = new ModelAndView();
		
		try {
			int groupId = form.getGroupId();
			if (groupId > 0) {
					//deactivateGroup
					mv.setViewName("editGroupMasterAccount");
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
