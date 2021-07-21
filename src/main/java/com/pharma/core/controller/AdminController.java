package com.pharma.core.controller;

import java.text.SimpleDateFormat;
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
import com.pharma.core.formBean.admin.AdminForm;
import com.pharma.core.model.Pharmacy;
import com.pharma.core.model.admin.AdminAccount;
import com.pharma.core.model.admin.AdminPermissions;
import com.pharma.core.pharmaservices.PharmacyService;
import com.pharma.core.pharmaservices.adminservices.AdminAccountService;
import com.pharma.core.repository.admin.AdminPermissionsRepository;
import com.pharma.core.util.PharmacyUtil;
/**
 * This class is a controller to save and load the admin account details
 *
 */
@Controller
@PropertySource("classpath:adminAccount.properties")
@PropertySource("classpath:physicianAccount.properties")
public class AdminController extends AdminBaseController {
	
	@Autowired
	AdminAccountService adminService;
	
	@Autowired
	PharmacyService pharmachyService;
	
	@Autowired
	AdminPermissionsRepository adminPermissionRepo;
	
	@Autowired
	private Environment env;
	
	/**
	 * This method loads the admin account summary page
	 * @param model
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping("/adminAccountSummary")
	public ModelAndView loadAdminAccountSummary(Model model, HttpServletRequest request, HttpServletResponse response, 
			HttpSession session){
		ModelAndView mv = new ModelAndView();
		try {
			mv.setViewName("adminaccountsummary");
			
			AdminForm form = new AdminForm();
			mv.addObject("form", form);
			
			model.addAttribute("message", model.asMap().get("message"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return mv;
	}
	/**
	 * This method loads the admin account summary data
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
	 * @param adminName
	 * @param pharmacy
	 * @param userType
	 * @param status
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getAdminData", method = RequestMethod.POST)
	public @ResponseBody String getAdminData(@RequestParam("draw") int draw,
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
			@RequestParam("adminName") String adminName, @RequestParam("pharmacy") String pharmacy, @RequestParam("userType") String userType,
			@RequestParam("status") String status, HttpServletRequest request) {
		
		return adminService.getAdminDataList(draw, start, len, searchTerm, orderColumn, orderDir, adminName, pharmacy, userType, status);
	}	
	
	/**
	 * This method loads the admin account page
	 * @param model
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping("/adminaccount")  
	public ModelAndView loadAdminAccount(Model model, HttpServletRequest request, HttpServletResponse response, HttpSession session){
		List<Pharmacy> pharmacyList = pharmachyService.getPharamcyList();
		ModelAndView mv = new ModelAndView();
		try {
			model.addAttribute("message", model.asMap().get("message"));
			
			AdminForm form = null;
			try {
				form = (AdminForm) model.asMap().get("form");	
			} catch(Exception e) {
				e.printStackTrace();
			}
			if (form == null) {
				form = new AdminForm();
				form.setStatus(PharmacyUtil.statusActive);
			}
			
			SimpleDateFormat inDate = new SimpleDateFormat("MM/dd/yyyy");
			String strDt = inDate.format(new Date());
			form.setDateRegistrated(strDt);
			
			//Added on Dec 16,2017-Temp Password generated in New Account by default
			if(form.getAdminId()==0)
			{
				String randomPwd = PharmacyUtil.randomPasswordGenerator();
				form.setPassword(randomPwd);
			}
			
			
			
			
			
			mv.addObject("pharmacyList", pharmacyList);
			mv.addObject("admin", form);
			
			model.addAttribute("message", model.asMap().get("message"));
			model.addAttribute("saveStatus", model.asMap().get("saveStatus"));
			
			mv.setViewName("adminaccount");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return mv;
	}
	/**
	 * This method save and loads the admin account page
	 * @param form
	 * @param bindingResult
	 * @param model
	 * @param regDate
	 * @param request
	 * @param response
	 * @param session
	 * @param uploadPhotoFile
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "/saveAdminAccount", method = RequestMethod.POST)
	public ModelAndView saveAdminAccount(@ModelAttribute("admin") AdminForm form,  BindingResult bindingResult, Model model,
			@Param("dateRegistrated") String regDate,  HttpServletRequest request, HttpServletResponse response, HttpSession session, 
			@RequestParam CommonsMultipartFile uploadPhotoFile, RedirectAttributes redirectAttributes) {
		ModelAndView mv = new ModelAndView();
		LoginForm loggedInUser = (LoginForm) session.getAttribute("loginDetail");
		 try {
			 List<AdminAccount> exObj = null; 
			 if (form.getAdminId() > 0) {
				 exObj = adminService.getAdminAccountByEmailAndNotId(form.getEmail(), form.getAdminId());
			 } else {
				 exObj = adminService.getAdminAccountByEmail(form.getEmail());
			 }
			 
			if (exObj.size() == 0) {
				//String rootFilePath=env.getProperty("file.root_folder_path");
				String rootFilePath= PharmacyUtil.getRootFolderForPhoto(session, env);
				AdminAccount acc = adminService.saveAdminAccount(form, uploadPhotoFile, null, rootFilePath, loggedInUser, session, request, env);
				
				if (acc != null) {
					if (acc.getId() == loggedInUser.getUserid()) {
						LoginForm loginForm = new LoginForm();
						loginForm.setDisplayName(acc.getName());
						loginForm.setPhotoUrl(adminService.getAdminPhotoFileName(acc.getId(), env.getProperty("file.photo_path")));
						loginForm.setStatus(acc.getStatus());
						if (loggedInUser.getType().equalsIgnoreCase("Admin"))
							loginForm.setType(PharmacyUtil.userAdmin);
						else
							loginForm.setType(PharmacyUtil.userSuperAdmin);
						loginForm.setUserid(acc.getId());
						loginForm.setUserName(acc.getEmail());
						
						//Load Admin Persmission List
						AdminPermissions adminPerm = null;
						adminPerm = adminPermissionRepo.findById(form.getAdminId());
						if (adminPerm != null) {
						
							loginForm.setAdminAccCreationPermission(adminPerm.getAdminAccCreation());
							loginForm.setGroupCreationPermission(adminPerm.getGroupCreation());
							loginForm.setGroupDirectorCreationPermission(adminPerm.getGroupDirectorCreation());
							loginForm.setClinicCreationPermission(adminPerm.getClinicCreation());
							loginForm.setPhysicianCreationPermission(adminPerm.getPhysicianCreation());
							loginForm.setPhysicianApprovalPermission(adminPerm.getPhysicianApproval());
							loginForm.setAssistantCreationPermission(adminPerm.getAssistantCreation());
							loginForm.setPatientCreationPermission(adminPerm.getPatientCreation());
							loginForm.setPatientApprovalPermission(adminPerm.getPatientApproval());
							loginForm.setPrescriptionCreationPermission(adminPerm.getPrescriptionCreation());
							loginForm.setControlSubstanceDocUploadPermission(adminPerm.getControlSubstanceDocUpload());
							loginForm.setRemoteFileUploadPermission(adminPerm.getRemoteFileUpload());
							
						}
						session.setAttribute("loginDetail", loginForm);
					}
					redirectAttributes.addFlashAttribute("saveStatus", "true");
					if (form.getAdminId() > 0) 
						redirectAttributes.addFlashAttribute("message", "Pharmacy Admin Account updated successfully");
					else
						redirectAttributes.addFlashAttribute("message", "Pharmacy Admin Account created successfully");
					
					form.setAdminId(acc.getId());
					redirectAttributes.addFlashAttribute("form", form);
					mv.setViewName("redirect:editAdminAccount");
					
					/*if (loggedInUser.getType().equalsIgnoreCase("Admin"))
						mv.setViewName("redirect:editAdminAccount?id="+form.getAdminId());
					else
						mv.setViewName("redirect:editAdminAccount?id="+acc.getId());*/
				} else {
					model.addAttribute("message", "Pharmacy Admin Account failed to create");
					mv.setViewName("error400");
				}
			} else {
				redirectAttributes.addFlashAttribute("saveStatus", "false");
				redirectAttributes.addFlashAttribute("message", "Email already exists");
				
				if (form.getAdminId() > 0){
					form.setAdminId(form.getAdminId());
					redirectAttributes.addFlashAttribute("form", form);
					mv.setViewName("redirect:editAdminAccount");
					//mv.setViewName("redirect:editAdminAccount?id="+form.getAdminId());
				}else {
					redirectAttributes.addFlashAttribute("form", form);
					mv.setViewName("redirect:adminaccount");
				}	
			}
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("message", "Pharmacy Admin Account failed to create");
			mv.setViewName("error500");
		}
		return mv;
	}	
	/**
	 * This method loads the admin account data
	 * @param model
	 * @param request
	 * @param response
	 * @param session
	 * @param form
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping( value = "/editAdminAccount")  
	public ModelAndView editAdminAccount(Model model, HttpServletRequest request, HttpServletResponse response, HttpSession session,
			@ModelAttribute("form") AdminForm form, RedirectAttributes redirectAttributes){
		
		ModelAndView mv = new ModelAndView();
		try {
			List<Pharmacy> pharmacyList = pharmachyService.getPharamcyList();

			int id = 0;
			if (form != null && form.getAdminId()>0)
				id = form.getAdminId(); 
			//System.out.println("getHideUserPermissionList 111===="+form.getHideUserPermissionList());
			String hideUserPermissionList=form.getHideUserPermissionList();
			
			form = adminService.getAdminAccountById(id, env.getProperty("file.photo_path"));
			form.setHideUserPermissionList(hideUserPermissionList);
			
			//AdminForm form1=(AdminForm)redirectAttributes.addFlashAttribute("form");
			
			/*//System.out.println(request.getRequestURL());
			//System.out.println(request.getRequestURI());*/
			//System.out.println("getHideUserPermissionList 222===="+form.getHideUserPermissionList());
			
			//January 31, 2018 Rohini
			//Put a variable and random number in the image url to refresh the image after uploading
			//example : photoUrl?x=123456
			
			int randomNumber = PharmacyUtil.randomNumberGenerator();
			
			String photoUrl = form.getPhotoUrl();
			
			if(photoUrl==null || photoUrl.length()==0)
			{
				
				photoUrl="images/img.jpg";
			}
			
			photoUrl = photoUrl+"?x="+randomNumber;
			form.setPhotoUrl(photoUrl);
			//System.out.println("photoUrl ===  "+photoUrl);
			//January 31, 2018 Rohini
			
			
			if(model.asMap().get("passwordwarningmessage")!=null)
				redirectAttributes.addFlashAttribute("passwordwarningmessage", model.asMap().get("passwordwarningmessage"));
			
			
			mv.addObject("pharmacyList", pharmacyList);
			mv.addObject("admin", form);
			
			model.addAttribute("message", model.asMap().get("message"));
			model.addAttribute("saveStatus", model.asMap().get("saveStatus"));

			if(model.asMap().get("profileclick")!=null) {
				mv.setViewName("adminprofile");
			} else if (model.asMap().get("profileleft")!=null) {
				mv.setViewName("adminprofileleft");
			} else {
				mv.setViewName("adminaccount");	
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return mv;
	}
	
	/**
	 * This method loads the admin account profile data
	 * @param model
	 * @param session
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping("/adminProfile")
	public ModelAndView loadAdminProfile(Model model, HttpSession session, RedirectAttributes redirectAttributes) {
		ModelAndView mv = new ModelAndView();
		try {
			LoginForm loggedInUser = (LoginForm) session.getAttribute("loginDetail");
			model.addAttribute("message", model.asMap().get("message"));
			model.addAttribute("saveStatus", model.asMap().get("saveStatus"));
			
			if (loggedInUser != null) {
				AdminForm form = new AdminForm();
				form.setAdminId(loggedInUser.getUserid());
				
				if(model.asMap().get("passwordwarningmessage")!=null)
				{
					redirectAttributes.addFlashAttribute("passwordwarningmessage", model.asMap().get("passwordwarningmessage"));
					model.addAttribute("passwordwarningmessage", model.asMap().get("passwordwarningmessage"));
				}
				
				form.setHideUserPermissionList("true");
				redirectAttributes.addFlashAttribute("form", form);
				redirectAttributes.addFlashAttribute("profileclick", "true");
				mv.setViewName("redirect:editAdminAccount");
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
	 * This method loads the admin account profile data
	 * @param model
	 * @param session
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping("/adminLeftProfile")
	public ModelAndView loadAdminLeftProfile(Model model, HttpSession session, RedirectAttributes redirectAttributes) {
		ModelAndView mv = new ModelAndView();
		try {
			LoginForm loggedInUser = (LoginForm) session.getAttribute("loginDetail");
			model.addAttribute("message", model.asMap().get("message"));
			model.addAttribute("saveStatus", model.asMap().get("saveStatus"));
			
			if (loggedInUser != null) {
				AdminForm form = new AdminForm();
				form.setAdminId(loggedInUser.getUserid());
				
				if(model.asMap().get("passwordwarningmessage")!=null)
				{
					redirectAttributes.addFlashAttribute("passwordwarningmessage", model.asMap().get("passwordwarningmessage"));
					model.addAttribute("passwordwarningmessage", model.asMap().get("passwordwarningmessage"));
				}
				
				form.setHideUserPermissionList("true");
				redirectAttributes.addFlashAttribute("form", form);
				redirectAttributes.addFlashAttribute("profileleft", "true");
				mv.setViewName("redirect:editAdminAccount");
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
	 * This method sends the admin account credentail email
	 * @param form
	 * @param bindingResult
	 * @param model
	 * @param request
	 * @param response
	 * @param session
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "/sendPharmacyAdminCredentialsEmail", method = RequestMethod.POST)
	public ModelAndView sendPharmacyAdminCredentialsEmail(@ModelAttribute("form") AdminForm form,  BindingResult bindingResult, Model model,
			HttpServletRequest request, HttpServletResponse response, HttpSession session, RedirectAttributes redirectAttributes) {
		
		System.out.print("sendPharmacyAdminCredentialsEmail");
	ModelAndView mv = new ModelAndView();
	
	try {
		LoginForm loggedInUser = (LoginForm) session.getAttribute("loginDetail");

			if (form.getAdminId() > 0){
				
				boolean isMailSent = adminService.sendPharmacyAdminAccountMail(form, loggedInUser, session, request,env);
				
				if (isMailSent) {
					redirectAttributes.addFlashAttribute("saveStatus", "true");
					redirectAttributes.addFlashAttribute("message", "User Account Credentials sent successfully!!");
				} else {
					redirectAttributes.addFlashAttribute("saveStatus", "false");
					redirectAttributes.addFlashAttribute("message", "Failed to send User Account Credentials!!");
				}
				
				
				redirectAttributes.addFlashAttribute("form", form);
				mv.setViewName("redirect:editAdminAccount");
			}else {
				redirectAttributes.addFlashAttribute("form", form);
				mv.setViewName("redirect:adminaccount");
			}
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}	
		return mv;
	}
}
