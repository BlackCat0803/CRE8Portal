package com.pharma.core.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

import com.pharma.core.formBean.LoginForm;
import com.pharma.core.formBean.physician.PhysicianAssistantRegistration;
import com.pharma.core.model.pharmacygroup.GroupDirector;
import com.pharma.core.model.pharmacygroup.GroupMaster;
import com.pharma.core.model.physician.PhysicianAccount;
import com.pharma.core.model.physician.PhysicianAssistantAccount;
import com.pharma.core.model.physician.PhysicianGroup;
import com.pharma.core.pharmaservices.clinic.ClinicService;
import com.pharma.core.pharmaservices.pharmacygroupservices.GroupMasterService;
import com.pharma.core.pharmaservices.pharmacygroupservices.GroupMasterServiceImpl;
import com.pharma.core.pharmaservices.physicianservices.PhysicianAccountService;
import com.pharma.core.pharmaservices.physicianservices.PhysicianAssistantAccountService;
import com.pharma.core.repository.clinic.ClinicRepository;
import com.pharma.core.repository.pharmacygroup.GroupDirectorAccountRespository;
import com.pharma.core.repository.pharmacygroup.GroupMasterRespository;
import com.pharma.core.repository.physician.PhysicianAccountRespository;
import com.pharma.core.repository.physician.PhysicianGroupRepository;
import com.pharma.core.util.PharmacyUtil;
/**
 * This class is a controller to save and load the physician assistant account details
 */
@Controller
@PropertySource("classpath:physicianAssistantAccount.properties")
@PropertySource("classpath:physicianAccount.properties")
public class PhysicianAssistantAccountController extends PhysicianBaseController{
	
	@Autowired
	PhysicianAssistantAccountService phyAssistService;
	
	@Autowired
	PhysicianAccountService phyService;
	
	@Autowired
	ClinicService clinicService;
	
	@Autowired
	private Environment env;
	
	@Autowired
	GroupMasterService groupService;
	
	@Autowired
	GroupDirectorAccountRespository groupDirRepo;
	
	@Autowired
	PhysicianAccountRespository physicianRep;

	@Autowired
	PhysicianGroupRepository phyGroupRepo;
	
	@Autowired
	ClinicRepository clinicRepo;
	
	@Autowired
	GroupMasterServiceImpl groupMasterService;
	
	@Autowired
	GroupMasterRespository groupMasterRepo;
	
	/**
	 * This method loads the physician assistant account summary page
	 * @param model
	 * @param request
	 * @param response
	 * @param session
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping("/physicianassistantaccountsummary")
	public ModelAndView loadPhyAssistantAccountSummary(Model model, HttpServletRequest request, HttpServletResponse response, 
			HttpSession session,RedirectAttributes redirectAttributes){
		
		ModelAndView mv = new ModelAndView();
		try {
			model.addAttribute("message", model.asMap().get("message"));

			LoginForm usr = (LoginForm) session.getAttribute("loginDetail");
			mv.addObject("userid", usr.getUserid());
			mv.addObject("usertype", usr.getType());
			
			if (!usr.getType().equalsIgnoreCase(PharmacyUtil.userGroupDirector) && !usr.getType().equalsIgnoreCase(PharmacyUtil.userPhysician)) {
				List<GroupMaster> groupList = groupService.getAllGroupMaster();
				mv.addObject("groupList", groupList);
			} else {
				if(usr.getType().equalsIgnoreCase(PharmacyUtil.userGroupDirector)){
					GroupDirector gp = groupDirRepo.findOne(usr.getUserid());
					mv.addObject("groupId", gp.getGroupId());
				}else
				{
					PhysicianGroup phyGroup = phyGroupRepo.findRecordByPhysicianId(usr.getUserid());
					if (phyGroup != null) {
						mv.addObject("groupId", phyGroup.getGroupId());
					}
					
					
				}
			}
			PhysicianAssistantRegistration form = new PhysicianAssistantRegistration();
			mv.addObject("form", form);
			
			List<GroupMaster> groupMasterList = groupMasterService.getAllGroupMaster();
			mv.addObject("groupList", groupMasterList);
			
			List<GroupMaster> groupList = groupMasterRepo.getAllGroupList(PharmacyUtil.statusActive);
			mv.addObject("changegroupList", groupList);
			
			if (PharmacyUtil.userPhysicianAssistant.equalsIgnoreCase(usr.getType())) {
				redirectAttributes.addFlashAttribute("message", model.asMap().get("message"));
				redirectAttributes.addFlashAttribute("saveStatus", model.asMap().get("saveStatus"));
				
				form.setAssistantId(usr.getUserid());
				redirectAttributes.addFlashAttribute("assistantId", form);
				mv.setViewName("redirect:editPhysicianassistantaccount");
				//mv.setViewName("redirect:editPhysicianassistantaccount?id="+usr.getUserid());
			}
			else
				mv.setViewName("physicianassistantaccountsummary");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return mv;
	}
	/**
	 * This method loads the physician assistant account summary data
	 * @param id
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
	 * @param groupId
	 * @param assistant
	 * @param clinic
	 * @param status
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/getPhysicianAssistantData", method = RequestMethod.POST)
	public @ResponseBody String getPhysicianAssistantData(@RequestParam("pid") String id, @RequestParam("draw") int draw,
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
			@RequestParam("columns[4][search][regex]") String col4SearchRegex, @RequestParam("groupId") int groupId,
			@RequestParam("assistant") String assistant, @RequestParam("clinic") String clinic, @RequestParam("status") String status,
			HttpServletRequest request, HttpSession session) {
		
		return phyAssistService.getPhysicianAssistantDataList(Integer.parseInt(id), draw, start, len, searchTerm, orderColumn, orderDir, assistant, 
				clinic, status, groupId);
	}
	
	/**
	 * This method loads the physician assistant account summary data
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
	@RequestMapping(value = "/getAdminPhysicianAssistantData", method = RequestMethod.POST)
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
	
	
	/**
	 * This method loads the physician assistant account page
	 * @param model
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping("/physicianassistantaccount")  
	public ModelAndView loadPhyAssistantAccount(Model model, HttpServletRequest request, HttpServletResponse response, HttpSession session){
		//List<Clinic> clinicList = clinicService.getClinicList(true);
		ModelAndView mv = new ModelAndView();
		PhysicianAssistantRegistration assist = null;
		try {
			LoginForm loggedInUser = (LoginForm) session.getAttribute("loginDetail");
			try {
				assist = (PhysicianAssistantRegistration) model.asMap().get("form");
			} catch(Exception e){
				e.printStackTrace();
			}
			if (assist == null) {
				assist = new PhysicianAssistantRegistration();
				assist.setStatus(PharmacyUtil.statusActive);
			}
			
			SimpleDateFormat inDate = new SimpleDateFormat("MM/dd/yyyy");
			String strDt = inDate.format(new Date());
			assist.setDateRegistrated(strDt);
			
			
			
			model.addAttribute("message", model.asMap().get("message"));
			/*if (PharmacyUtil.userPhysician.equalsIgnoreCase(usr.getType())) { 
				assist.setPhysicianId( usr.getUserid()  );
				
				mv.addObject("userid", usr.getUserid());
				mv.addObject("usertype", usr.getType());
			} else {
				List<PhysicianAccount> physicianList = phyService.getApprovedPhysician();
				mv.addObject("physicianList", physicianList);
				mv.addObject("usertype", usr.getType());
			}*/
			
			//Added on Dec 16,2017-Temp Password generated in New Account by default
			if(assist.getAssistantId()==0)
			{
				String randomPwd = PharmacyUtil.randomPasswordGenerator();
				assist.setPassword(randomPwd);
				
			}
			
			if (PharmacyUtil.userPhysician.equalsIgnoreCase(loggedInUser.getType())) {
				
				PhysicianAccount phyAcc = physicianRep.findOne(loggedInUser.getUserid());
				PhysicianGroup phyGroup = phyGroupRepo.findRecordByPhysicianId(phyAcc.getId());

				assist.setPhysicianId( loggedInUser.getUserid()  );
				if(assist.getAssistantId()==0)
				{
					assist.setSelectedPhysicianId( loggedInUser.getUserid() +"" );
				}
				mv.addObject("userid", loggedInUser.getUserid());
				mv.addObject("usertype", loggedInUser.getType());
				
				assist.setPhysicianName(loggedInUser.getDisplayName());
				//assist.setClinicName(clinicRepo.findOne(phyAcc.getClinicId()).getClinicName());
				
				mv.addObject("physicianFullName", loggedInUser.getDisplayName());
				
				if (phyGroup != null) {
					assist.setGroupId(phyGroup.getGroupId());
					assist.setGroupName( groupService.getGroupMasterDetails(phyGroup.getGroupId()).getGroupName() );
				}
				
				
			} else {
				if (PharmacyUtil.userGroupDirector.equalsIgnoreCase(loggedInUser.getType())) {
					GroupDirector gp = groupDirRepo.findOne(loggedInUser.getUserid());
					List<PhysicianAccount> physicianList = phyService.getApprovedPhysicianByGroupIdAndId(gp.getGroupId(), assist.getPhysicianId());
					mv.addObject("physicianList", physicianList);
					
					if(assist==null || assist.getAssistantId()==0)
					{
						assist.setGroupId(loggedInUser.getGroupid());
						assist.setGroupName(loggedInUser.getGroupName());
						
					}
					
				} else {
					List<PhysicianAccount> physicianList = phyService.getApprovedPhysicianAndId(assist.getPhysicianId());
					mv.addObject("physicianList", physicianList);
				}
			}
			
			//Multiple select lit box
			List<PhysicianAccount> physicianList =null;
			List<PhysicianAccount> physicianSelectedList =null;
			
			List<Integer> phyAsstGroupList = new ArrayList<Integer>();
			
			if(assist.getGroupId()>0)
			{
				String selectedGroupId=assist.getGroupId()+"";
				String[] selectedGroupIdArr=selectedGroupId.split(",");
				
		
				for (String i : selectedGroupIdArr) {
					phyAsstGroupList.add(Integer.valueOf(i));
				}

			}
			
			
			if (assist.getGroupId()>0 && assist.getAssistantId()>0) {
				physicianList = physicianRep.getAllPhysicianAssistantGroupWisePhysicianListNotSelected(assist.getGroupId(),assist.getAssistantId());
				physicianSelectedList = physicianRep.getAllPhysicianAssistantGroupWisePhysicianListSelected(assist.getGroupId(),assist.getAssistantId());
			}else if (assist.getGroupId()>0) {
				physicianList = physicianRep.getAllGroupWisePhysicianList(phyAsstGroupList,0);
			}
			
			mv.addObject("physicianList", physicianList);
			mv.addObject("physicianSelectedList", physicianSelectedList);
			
			List<GroupMaster> groupList = groupService.getAllGroupMaster(PharmacyUtil.statusActive);
			mv.addObject("groupList", groupList);
			
			
			//January 31, 2018 Rohini
			//Put a variable and random number in the image url to refresh the image after uploading
			//example : photoUrl?x=123456
			int randomNumber = PharmacyUtil.randomNumberGenerator();
			
			String photoUrl = assist.getPhotoFile();
			if(photoUrl==null || photoUrl.length()==0)
			{
				
				photoUrl="images/img.jpg";
			}
			
			photoUrl = photoUrl+"?x="+randomNumber;
			assist.setPhotoFile(photoUrl);
			//System.out.println("photoUrl ===  "+photoUrl);
			//January 31, 2018 Rohini
			
			//mv.addObject("clinicList", clinicList);
			mv.addObject("assistant", assist);
			mv.addObject("physicianFullName", loggedInUser.getDisplayName());
			
			// model.addAttribute("assistant", assist);
			mv.setViewName("physicianassistantaccount");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return mv;
	}

	/**
	 *  This method save and loads the physician assistant account page
	 * @param form
	 * @param bindingResult
	 * @param model
	 * @param regDate
	 * @param uploadPhotoFile
	 * @param request
	 * @param response
	 * @param session
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "/savePhysicianAssistantRegistration", method = RequestMethod.POST)
	public ModelAndView saveNewPhysicianAssistant(@ModelAttribute("assistant") PhysicianAssistantRegistration form,  BindingResult bindingResult, Model model,
			@Param("dateRegistrated") String regDate, @RequestParam CommonsMultipartFile uploadPhotoFile,  HttpServletRequest request, 
			HttpServletResponse response, HttpSession session, RedirectAttributes redirectAttributes) {
		ModelAndView mv = new ModelAndView();
		LoginForm loggedInUser = (LoginForm) session.getAttribute("loginDetail");
		
		try {
			 List<PhysicianAssistantAccount> exObj = null; 
			 if (form.getAssistantId() > 0) {
				 exObj = phyAssistService.getPhysicianAssistantByEmailAndNotId(form.getEmail(), form.getAssistantId());
			 } else {
				 exObj = phyAssistService.getPhysicianAssistantByEmail(form.getEmail());
			 }
			 
			if (exObj.size() == 0) {
				// String rootFilePath=env.getProperty("file.root_folder_path");
				String rootFilePath= PharmacyUtil.getRootFolderForPhoto(session, env);
				PhysicianAssistantAccount acc = phyAssistService.saveAssistantAccount(form, uploadPhotoFile, null, rootFilePath, loggedInUser, session, request, env);
				
				if (acc != null) {
					redirectAttributes.addFlashAttribute("saveStatus", "true");
					if (form.getAssistantId() > 0) 
						redirectAttributes.addFlashAttribute("message", "Physician Assistant Account updated successfully");
					else
						redirectAttributes.addFlashAttribute("message", "Physician Assistant Account created successfully");
					
					//temp commented on jan 19,2018
					/*PhysicianProfile phyAcc = phyService.getPhysicianData(acc.getPhysicianId(), env.getProperty("file.photo_path"), env.getProperty("file.logo_path"));
					
					redirectAttributes.addFlashAttribute("physicianName", phyAcc.getPhysicianName());
					redirectAttributes.addFlashAttribute("id", phyAcc.getPhysicianId());*/
					
					/*if (PharmacyUtil.userPhysicianAssistant.equalsIgnoreCase(loggedInUser.getType()) && acc.getId() == loggedInUser.getUserid()) {
						LoginForm login = new LoginForm();
						login.setDisplayName(acc.getAssistantName());
						login.setPhotoUrl(phyAssistService.getAssistantPhotoFileName(acc.getId(), env.getProperty("file.photo_path")));
						login.setStatus(acc.getStatus());
						login.setType(PharmacyUtil.userPhysicianAssistant);
						login.setUserid(acc.getId());
						login.setUserName(acc.getEmail());
						
						session.setAttribute("loginDetail", login);
					}*/
					 form.setAssistantId(acc.getId());
					 redirectAttributes.addFlashAttribute("form", form);
					 mv.setViewName("redirect:editPhysicianassistantaccount");
					 
				} else {
					model.addAttribute("message", "Physician Assistant Account failed to create");
					mv.setViewName("error400");
				}
			} else {
				redirectAttributes.addFlashAttribute("message", "Email already exist.");
				if (form.getAssistantId() > 0) {
					 form.setAssistantId(form.getAssistantId());
					 redirectAttributes.addFlashAttribute("form", form);
					 mv.setViewName("redirect:editPhysicianassistantaccount");
					// mv.setViewName("redirect:editPhysicianassistantaccount?assistantId="+form.getAssistantId());
				}	
				else {
					redirectAttributes.addFlashAttribute("form", form);
					mv.setViewName("redirect:physicianassistantaccount");
				}	
			}
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("message", "Physician Assistant Account failed to create");
			mv.setViewName("error500");
		}
		return mv;
	}	
	
	
	/**
	 * This method loads the physician assistant account data throught link
	 * @param model
	 * @param request
	 * @param response
	 * @param session
	 * @param id
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "/viewAssistantPhysicianProfile", method = {RequestMethod.POST, RequestMethod.GET } ) 
	public ModelAndView viewAssistantPhysicianProfile(Model model, HttpServletRequest request, HttpServletResponse response, HttpSession session,
			@RequestParam("id") int id, RedirectAttributes redirectAttributes){
		
		ModelAndView mv = new ModelAndView();
		
		try {
			if (id > 0) {
				PhysicianAssistantRegistration form = new PhysicianAssistantRegistration();
				form.setAssistantId(id);
				redirectAttributes.addFlashAttribute("form", form);
				mv.setViewName("redirect:editPhysicianassistantaccount");
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
	 * This method loads the physician assistant account page
	 * @param model
	 * @param request
	 * @param response
	 * @param session
	 * @param form
	 * @return
	 */
	@RequestMapping( value = "/editPhysicianassistantaccount", method = {RequestMethod.POST, RequestMethod.GET } )  
	public ModelAndView editPhyAssistantAccount(Model model, HttpServletRequest request, HttpServletResponse response, HttpSession session,
			@ModelAttribute("form") PhysicianAssistantRegistration form ){

		ModelAndView mv = new ModelAndView();
		try {
			int id = 0;
			if (form != null && form.getAssistantId() > 0) {
				id = form.getAssistantId(); 
			}
			if (id > 0){
				PhysicianAssistantRegistration assist = phyAssistService.getPhysicianAssistantById(id, env.getProperty("file.photo_path"));

				//temp commented on jan 19,2018
				//String physicianName = phyAssistService.getPhysicianNameById(id);
				LoginForm loggedInUser = (LoginForm) session.getAttribute("loginDetail");
				
			
				
				

				if (PharmacyUtil.userPhysician.equalsIgnoreCase(loggedInUser.getType()) || PharmacyUtil.userPhysicianAssistant.equalsIgnoreCase(loggedInUser.getType())) 
				{
					if (PharmacyUtil.userPhysician.equalsIgnoreCase(loggedInUser.getType())){
						assist.setPhysicianId( loggedInUser.getUserid()  );
						if(assist.getAssistantId()==0)
						{
							assist.setSelectedPhysicianId( loggedInUser.getUserid() +"" );
						}
						
						PhysicianAccount phyAcc = physicianRep.findOne(loggedInUser.getUserid());
						assist.setPhysicianName(loggedInUser.getDisplayName());
						//assist.setClinicName(clinicRepo.findOne(phyAcc.getClinicId()).getClinicName());
						
						mv.addObject("physicianFullName", loggedInUser.getDisplayName());
						
					
						
						mv.addObject("userid", loggedInUser.getUserid());
					}else
					{
						
						assist.setPhysicianId( loggedInUser.getPhysicianAssistantPhysicianId()  );
						if(assist.getAssistantId()==0)
						{
							assist.setSelectedPhysicianId( loggedInUser.getPhysicianAssistantPhysicianId() +"" );
						}
						
						PhysicianAccount phyAcc = physicianRep.findOne(loggedInUser.getPhysicianAssistantPhysicianId());
						assist.setPhysicianName(phyAcc.getPhysicianName());
						//assist.setClinicName(clinicRepo.findOne(phyAcc.getClinicId()).getClinicName());
						
						mv.addObject("physicianFullName", phyAcc.getPhysicianName());
						
					
						
						mv.addObject("userid", loggedInUser.getPhysicianAssistantPhysicianId());
						
					}
				}
				else {
					
					if (PharmacyUtil.userGroupDirector.equalsIgnoreCase(loggedInUser.getType())) {
						GroupDirector gp = groupDirRepo.findOne(loggedInUser.getUserid());
						List<PhysicianAccount> physicianList = phyService.getApprovedPhysicianByGroupIdAndId(gp.getGroupId(), assist.getPhysicianId());
						mv.addObject("physicianList", physicianList);
					} else {
						List<PhysicianAccount> physicianList = phyService.getApprovedPhysicianAndId(assist.getPhysicianId());
						mv.addObject("physicianList", physicianList);
					}
				}
				
			
				mv.addObject("physicianId", assist.getPhysicianId());
				//temp commented on jan 19,2018
				//mv.addObject("physicianFullName", physicianName);
				mv.addObject("usertype", loggedInUser.getType());
				
				List<GroupMaster> groupList = groupService.getByGroupMaster(assist.getGroupId());
				mv.addObject("groupList", groupList);	
				
				List<Integer> phyAsstGroupList = new ArrayList<Integer>();
				
				if(assist.getGroupId()>0)
				{
					String selectedGroupId=assist.getGroupId()+"";
					String[] selectedGroupIdArr=selectedGroupId.split(",");
					
			
					for (String i : selectedGroupIdArr) {
						phyAsstGroupList.add(Integer.valueOf(i));
					}

				}
				
				//Multiple select lit box
				List<PhysicianAccount> physicianList =null;
				List<PhysicianAccount> physicianSelectedList =null;
				
				if (assist.getGroupId()>0 && assist.getAssistantId()>0) {
					physicianList = physicianRep.getAllPhysicianAssistantGroupWisePhysicianListNotSelected(assist.getGroupId(),assist.getAssistantId());
					physicianSelectedList = physicianRep.getAllPhysicianAssistantGroupWisePhysicianListSelected(assist.getGroupId(),assist.getAssistantId());
				}else if (assist.getGroupId()>0) {
					physicianList = physicianRep.getAllGroupWisePhysicianList(phyAsstGroupList,0);
				}
				
				
				//January 31, 2018 Rohini
				//Put a variable and random number in the image url to refresh the image after uploading
				//example : photoUrl?x=123456
				int randomNumber = PharmacyUtil.randomNumberGenerator();
				
				String photoUrl = assist.getPhotoFile();
				if(photoUrl==null || photoUrl.length()==0)
				{
					
					photoUrl="images/img.jpg";
				}
				
				photoUrl = photoUrl+"?x="+randomNumber;
				assist.setPhotoFile(photoUrl);
				//System.out.println("photoUrl ===  "+photoUrl);
				//January 31, 2018 Rohini
				

				
				mv.addObject("physicianList", physicianList);
				mv.addObject("physicianSelectedList", physicianSelectedList);
				mv.addObject("assistant", assist);
			}

			model.addAttribute("message", model.asMap().get("message"));
			model.addAttribute("saveStatus", model.asMap().get("saveStatus"));
			
			
			if(model.asMap().get("leftprofileclicked")!=null) {
				mv.setViewName("physicianassistantprofile");
			} else {
				mv.setViewName("physicianassistantaccount");	
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return mv;
	}
	
	/**
	 * This method loads the physician assistant profile data
	 * @param model
	 * @param session
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping("/assistantProfile")
	public ModelAndView loadAssistantProfile(Model model, HttpSession session, RedirectAttributes redirectAttributes) {
		ModelAndView mv = new ModelAndView();
		try {
			LoginForm loggedInUser = (LoginForm) session.getAttribute("loginDetail");
			model.addAttribute("message", model.asMap().get("message"));
			model.addAttribute("saveStatus", model.asMap().get("saveStatus"));
			
			if (loggedInUser != null) {
				PhysicianAssistantRegistration form = new PhysicianAssistantRegistration();
				form.setAssistantId(loggedInUser.getUserid());
				redirectAttributes.addFlashAttribute("form", form);
				redirectAttributes.addFlashAttribute("leftprofileclicked", "true");
				mv.setViewName("redirect:editPhysicianassistantaccount");
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
	 * This method loads the physician assistant profile data
	 * @param model
	 * @param session
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping("/assistantLeftProfile")
	public ModelAndView loadAssistantLeftProfile(Model model, HttpSession session, RedirectAttributes redirectAttributes) {
		ModelAndView mv = new ModelAndView();
		try {
			LoginForm loggedInUser = (LoginForm) session.getAttribute("loginDetail");
			model.addAttribute("message", model.asMap().get("message"));
			model.addAttribute("saveStatus", model.asMap().get("saveStatus"));
			
			if (loggedInUser != null) {
				PhysicianAssistantRegistration form = new PhysicianAssistantRegistration();
				form.setAssistantId(loggedInUser.getUserid());
				redirectAttributes.addFlashAttribute("form", form);
				mv.setViewName("redirect:editPhysicianassistantaccount");
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
	 * This method returns the group wise physician list
	 * @param request
	 * @param response
	 * @param session
	 * @param groupId
	 * @param physicianId
	 * @return
	 */
	@RequestMapping(value="/fetchGroupWisePhysicians", method=RequestMethod.POST)
	public @ResponseBody List<PhysicianAccount> getGroupWisePhysicianDataById(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			 @RequestParam("groupId") int groupId,@RequestParam("physicianId") int physicianId) {
		List<PhysicianAccount> physicianList=null;
		try {
			if (groupId>0) {
				List<Integer> phyAsstGroupList = new ArrayList<Integer>();
				
				if(groupId>0)
				{
					String selectedGroupId=groupId+"";
					String[] selectedGroupIdArr=selectedGroupId.split(",");
					
			
					for (String i : selectedGroupIdArr) {
						phyAsstGroupList.add(Integer.valueOf(i));
					}

				}
				physicianList = physicianRep.getAllGroupWisePhysicianList(phyAsstGroupList,physicianId);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return physicianList;
	}
	/**
	 *  This method sends the physician assistant account credentail email
	 * @param form
	 * @param bindingResult
	 * @param model
	 * @param request
	 * @param response
	 * @param session
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "/sendPhysicianAsstAccountCredentialsEmail", method = RequestMethod.POST)
	public ModelAndView sendPhysicianAsstAccountCredentialsEmail(@ModelAttribute("assistant") PhysicianAssistantRegistration form,  BindingResult bindingResult, Model model,
			HttpServletRequest request, HttpServletResponse response, HttpSession session, RedirectAttributes redirectAttributes) {
		
	System.out.print("sendPhysicianAsstAccountCredentialsEmail");
	ModelAndView mv = new ModelAndView();
	
	try {
		LoginForm loggedInUser = (LoginForm) session.getAttribute("loginDetail");

			if (form.getAssistantId() > 0){
				
				boolean isMailSent = phyAssistService.sendPhysicianAsstAccountMail(form, loggedInUser, session, request,env);
				
				if (isMailSent) {
					redirectAttributes.addFlashAttribute("saveStatus", "true");
					redirectAttributes.addFlashAttribute("message", "User Account Credentials sent successfully!!");
				} else {
					redirectAttributes.addFlashAttribute("saveStatus", "false");
					redirectAttributes.addFlashAttribute("message", "Failed to send User Account Credentials!!");
				}
				
				
				redirectAttributes.addFlashAttribute("form", form);
				mv.setViewName("redirect:editPhysicianassistantaccount");
			}else {
				redirectAttributes.addFlashAttribute("form", form);
				mv.setViewName("redirect:physicianassistantaccount");
			}
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}	
		return mv;
	}

}
