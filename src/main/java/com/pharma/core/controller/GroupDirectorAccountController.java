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
import com.pharma.core.formBean.pharmacygroup.GroupDirectorForm;
import com.pharma.core.model.pharmacygroup.GroupDirector;
import com.pharma.core.model.pharmacygroup.GroupMaster;
import com.pharma.core.pharmaservices.pharmacygroupservices.GroupDirectorService;
import com.pharma.core.pharmaservices.pharmacygroupservices.GroupMasterService;
import com.pharma.core.util.PharmacyUtil;
/**
 * This class is a controller to save and load the group director account details
 *
 */
@Controller
@PropertySource("classpath:groupDirector.properties")
public class GroupDirectorAccountController extends GroupBaseController{
	
	@Autowired
	GroupDirectorService grpDirectorService;

	@Autowired
	GroupMasterService groupMasterService;
	
	@Autowired
	private Environment env;
	
	/**
	 * This method loads the group director account summary page
	 * @param model
	 * @param request
	 * @param response
	 * @param session
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping("/groupDirectorSummary")
	public ModelAndView loadGroupDirectorSummary(Model model, HttpServletRequest request, HttpServletResponse response, 
			HttpSession session,RedirectAttributes redirectAttributes){
		
		ModelAndView mv = new ModelAndView();
		try {
			model.addAttribute("message", model.asMap().get("message"));
			List<GroupMaster> groupList = groupMasterService.getAllGroupMaster();
			LoginForm usr = (LoginForm) session.getAttribute("loginDetail");
			
				mv.addObject("groupList",groupList);
				mv.addObject("userid", usr.getUserid());
				mv.addObject("usertype", usr.getType());
				mv.addObject("groupDirectorSummaryForm", new GroupDirectorForm());
				
			if (PharmacyUtil.userGroupDirector.equalsIgnoreCase(usr.getType())) {
				redirectAttributes.addFlashAttribute("message", model.asMap().get("message"));
				redirectAttributes.addFlashAttribute("saveStatus", model.asMap().get("saveStatus"));
				
				GroupDirectorForm form = new GroupDirectorForm();
				form.setGroupDirectorId(usr.getUserid());
				redirectAttributes.addFlashAttribute("groupDirectorSummaryForm", form);
				mv.setViewName("redirect:editGroupDirector");
				
				//mv.setViewName("redirect:editGroupDirector?id="+usr.getUserid());
			}
			else
				mv.setViewName("groupDirectorSummary");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return mv;
	}
	
	/**
	 * This method loads the group director account summary data
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
	 * @param groupDirectorName
	 * @param groupName
	 * @param status
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/getGroupDirectorDataList", method = RequestMethod.POST)
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
			@RequestParam("columns[4][search][regex]") String col4SearchRegex, @RequestParam("columns[5][data]") String col5Data, @RequestParam("columns[5][name]") String col5Name,
			@RequestParam("columns[5][searchable]") String col5Search, @RequestParam("columns[5][orderable]") String col5Order,
			@RequestParam("columns[5][search][value]") String col5SearchValue, @RequestParam("columns[5][search][regex]") String col5SearchRegex, 
			@RequestParam("groupDirectorName") String groupDirectorName, @RequestParam("groupName") int groupName, @RequestParam("status") String status,
			HttpServletRequest request, HttpSession session) {
		
		return grpDirectorService.getGroupDirectorDataList(draw, start, len, searchTerm, orderColumn, orderDir, groupDirectorName, groupName, status);
	}	
	
	
	/**
	 * This method loads the group director account page
	 * @param model
	 * @param request
	 * @param session
	 * @param response
	 * @return
	 */
	@RequestMapping("/groupDirector")  
	public ModelAndView loadGroupDirectorAccount(Model model, HttpServletRequest request, HttpSession session, 
			HttpServletResponse response){

		ModelAndView mv = new ModelAndView();
	
		try {
			List<GroupMaster> groupList = groupMasterService.getAllGroupMaster(PharmacyUtil.statusActive);
			GroupDirectorForm groupDirectorForm = null;
			try {
				groupDirectorForm = (GroupDirectorForm) model.asMap().get("groupDirectorSummaryForm");	
			} catch(Exception e){
				e.printStackTrace();
			}
			if(groupDirectorForm == null) {
				groupDirectorForm = new GroupDirectorForm();
				groupDirectorForm.setEmail("");
				groupDirectorForm.setPassword("");
				groupDirectorForm.setStatus(PharmacyUtil.statusActive);
			}
			//Added on Dec 16,2017-Temp Password generated in New Account by default
			if(groupDirectorForm.getGroupDirectorId()==0)
			{
				String randomPwd = PharmacyUtil.randomPasswordGenerator();
				groupDirectorForm.setPassword(randomPwd);
			}

			SimpleDateFormat inDate = new SimpleDateFormat("MM/dd/yyyy");
			String strDt = inDate.format(new Date());
			groupDirectorForm.setDateRegistrated(strDt);

			model.addAttribute("message", model.asMap().get("message"));
			mv.addObject("groupList", groupList);
			mv.addObject("groupDirectorForm", groupDirectorForm);
			
			mv.setViewName("groupDirector");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return mv;
	}

	/**
	 * This method save and loads the group director account page
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
	@RequestMapping(value = "/saveGroupDirector", method = RequestMethod.POST)
	public ModelAndView saveGroupDirector(@ModelAttribute("groupDirectorForm") GroupDirectorForm form,  BindingResult bindingResult, Model model,
			@Param("dateRegistrated") String regDate, @RequestParam CommonsMultipartFile uploadPhotoFile,  HttpServletRequest request, 
			HttpServletResponse response, HttpSession session, RedirectAttributes redirectAttributes) {
		ModelAndView mv = new ModelAndView();
		
		
		try {
			LoginForm loggedInUser = (LoginForm) session.getAttribute("loginDetail");
			 List<GroupDirector> exObj = null; 
			 if (form.getGroupDirectorId() > 0) {
				 exObj = grpDirectorService.getGroupDirectorByEmailAndNotId(form.getEmail(), form.getGroupDirectorId());
			 } else {
				 exObj = grpDirectorService.getGroupDirectorByEmail(form.getEmail());
			 }
			 
			if (exObj.size() == 0) {
				// String rootFilePath=env.getProperty("file.root_folder_path");
				String rootFilePath= PharmacyUtil.getRootFolderForPhoto(session, env);
				GroupDirector acc = grpDirectorService.saveGroupDirectorAccount(form, uploadPhotoFile, null, rootFilePath, loggedInUser, 
						session, request, env);
				
				if (acc != null) {
					redirectAttributes.addFlashAttribute("saveStatus", "true");
					if (form.getGroupDirectorId() > 0) 
						redirectAttributes.addFlashAttribute("message", "Group Director Account updated successfully");
					else
						redirectAttributes.addFlashAttribute("message", "Group Director Account created successfully");
					
					
					if (PharmacyUtil.userGroupDirector.equalsIgnoreCase(loggedInUser.getType()) && acc.getId() == loggedInUser.getUserid()) {
						LoginForm login = new LoginForm();
						login.setDisplayName(acc.getGroupDirectorName());
						login.setPhotoUrl(grpDirectorService.getGroupDirectorPhotoFileName(acc.getId(), env.getProperty("file.photo_path")));
						login.setStatus(acc.getStatus());
						login.setType(PharmacyUtil.userGroupDirector);
						login.setUserid(acc.getId());
						login.setUserName(acc.getEmail());
						login.setGroupid(acc.getGroupId());
						session.setAttribute("loginDetail", login);
					}
					form = new GroupDirectorForm();
					form.setGroupDirectorId(acc.getId());
					redirectAttributes.addFlashAttribute("groupDirectorSummaryForm", form);
					mv.setViewName("redirect:editGroupDirector");
					
					// mv.setViewName("redirect:editGroupDirector?id="+acc.getId());
				} else {
					model.addAttribute("message", "Group Director Account failed to create");
					mv.setViewName("error400");
				}
			} else {
				redirectAttributes.addFlashAttribute("message", "Email already exists");
				/*form = new GroupDirectorForm();
				form.setGroupDirectorId(form.getGroupDirectorId());*/
				redirectAttributes.addFlashAttribute("groupDirectorSummaryForm", form);

				if (form.getGroupDirectorId() > 0)  {
					mv.setViewName("redirect:editGroupDirector");
				} else
					mv.setViewName("redirect:groupDirector");
			}
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("message", "Group Director Account failed to create");
			mv.setViewName("error500");
		}
		return mv;
	}	
	
	/**
	 * This method loads the group director account data
	 * @param model
	 * @param request
	 * @param response
	 * @param session
	 * @param form
	 * @return
	 */
	@RequestMapping("/editGroupDirector")  
	public ModelAndView editGroupDirectorAccount(Model model, HttpServletRequest request, HttpServletResponse response, HttpSession session,
			@ModelAttribute("groupDirectorSummaryForm") GroupDirectorForm form){

		ModelAndView mv = new ModelAndView();
		
		try {
			int id = 0;
			if (form != null)
				id = form.getGroupDirectorId();
			if (id == 0) {
				LoginForm loggedInUser = (LoginForm) session.getAttribute("loginDetail");
				id = loggedInUser.getUserid();
			}
			GroupDirectorForm groupDirectorForm = null;
			if (form.getGroupName() != null && !"".equalsIgnoreCase(form.getGroupName()))
				groupDirectorForm = form;
			else
				groupDirectorForm = grpDirectorService.getGroupDirectorById(id, env.getProperty("file.photo_path"));
			
			//January 31, 2018 Rohini
			//Put a variable and random number in the image url to refresh the image after uploading
			//example : photoUrl?x=123456
			
			int randomNumber = PharmacyUtil.randomNumberGenerator();
			
			String photoUrl = form.getPhotoFile();
			
			if(photoUrl==null || photoUrl.length()==0)
			{
				
				photoUrl="images/img.jpg";
			}
			
			photoUrl = photoUrl+"?x="+randomNumber;
			form.setPhotoFile(photoUrl);
			//System.out.println("photoUrl ===  "+photoUrl);
			//January 31, 2018 Rohini
					
			List<GroupMaster> groupList = groupMasterService.getAllGroupMaster(PharmacyUtil.statusActive,groupDirectorForm.getGroupId());
			
			mv.addObject("groupList", groupList);
			mv.addObject("groupDirectorForm", groupDirectorForm);
			
			LoginForm loggedInUser = (LoginForm) session.getAttribute("loginDetail");
			if (PharmacyUtil.userGroupDirector.equalsIgnoreCase(loggedInUser.getType()) && id == loggedInUser.getUserid()) {
				mv.addObject("groupUserLoggin", "yes");
			} else {
				mv.addObject("groupUserLoggin", "no");
			}
			
			model.addAttribute("message", model.asMap().get("message"));
			model.addAttribute("saveStatus", model.asMap().get("saveStatus"));
			mv.setViewName("groupDirector");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return mv;
	}
	/**
	 * This method sends the group director account credentail email
	 * @param form
	 * @param bindingResult
	 * @param model
	 * @param request
	 * @param response
	 * @param session
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "/sendGroupDirectorCredentialsEmail", method = RequestMethod.POST)
	public ModelAndView sendGroupDirectorCredentialsEmail(@ModelAttribute("groupDirectorSummaryForm") GroupDirectorForm form,  BindingResult bindingResult, Model model,
			HttpServletRequest request, HttpServletResponse response, HttpSession session, RedirectAttributes redirectAttributes) {
		
		System.out.print("sendGroupDirectorCredentialsEmail");
		ModelAndView mv = new ModelAndView();
	
		try {
			LoginForm loggedInUser = (LoginForm) session.getAttribute("loginDetail");

			if (form.getGroupDirectorId() > 0){
				
				boolean isMailSent = grpDirectorService.sendGroupDirectorMail(form, loggedInUser, session, request,env);
				
				if (isMailSent) {
					redirectAttributes.addFlashAttribute("saveStatus", "true");
					redirectAttributes.addFlashAttribute("message", "User Account Credentials sent successfully!!");
				} else {
					redirectAttributes.addFlashAttribute("saveStatus", "false");
					redirectAttributes.addFlashAttribute("message", "Failed to send User Account Credentials!!");
				}
				
				
				redirectAttributes.addFlashAttribute("form", form);
				mv.setViewName("redirect:editGroupDirector");
			}else {
				redirectAttributes.addFlashAttribute("form", form);
				mv.setViewName("redirect:groupDirector");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return mv;
	}
	
}
