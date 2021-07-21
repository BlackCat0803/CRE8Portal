package com.pharma.core.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
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
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pharma.core.formBean.LoginForm;
import com.pharma.core.formBean.ResetPasswordForm;
import com.pharma.core.model.admin.AdminAccount;
import com.pharma.core.model.admin.AdminPermissions;
import com.pharma.core.model.patientaccount.PatientAccount;
import com.pharma.core.model.pharmacygroup.GroupDirector;
import com.pharma.core.model.physician.PhysicianAccount;
import com.pharma.core.model.physician.PhysicianAssistantAccount;
import com.pharma.core.pharmaservices.LoginService;
import com.pharma.core.pharmaservices.adminservices.AdminAccountService;
import com.pharma.core.pharmaservices.patientservices.PatientAccountService;
import com.pharma.core.pharmaservices.pharmacygroupservices.GroupDirectorService;
import com.pharma.core.pharmaservices.physicianservices.PhysicianAccountService;
import com.pharma.core.pharmaservices.physicianservices.PhysicianAssistantAccountService;
import com.pharma.core.pharmaservices.physicianservices.PhysicianFileUploadService;
import com.pharma.core.repository.admin.AdminPermissionsRepository;
import com.pharma.core.repository.pharmacygroup.GroupMasterRespository;
import com.pharma.core.util.HipaaPasswordValidator;
import com.pharma.core.util.PharmacyUtil;

/**
 * This class is a controller for the application Log In / Log out activities.
 *
 */
@Controller
@SessionAttributes("loginDetail")
@PropertySource("classpath:login.properties")
@PropertySource("classpath:hipaa.properties")
@PropertySource("classpath:physicianAccount.properties")
public class LoginController {

	@Autowired
	private LoginService loginService;

	@Autowired
	PhysicianAssistantAccountService phyAssistService;

	@Autowired
	AdminAccountService adminService;

	@Autowired
	PhysicianAccountService phyService;

	@Autowired
	PatientAccountService patientService;

	@Autowired
	GroupDirectorService grpDirectorService;

	@Autowired
	GroupMasterRespository groupMasterRepo;

	@Autowired
	private Environment env;

	@Autowired
	PhysicianFileUploadService fileUploadService;

	@Autowired
	AdminPermissionsRepository adminPermissionRepo;

	/**
	 * This method is called when the user clicks login page
	 * 
	 * @param model
	 * @param typeValue
	 * @param session
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView loadLoginForm(Model model,
			@RequestParam(value = "s", required = false) String typeValue,
			HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		LoginForm loginForm = new LoginForm();
		mv.addObject("loginForm", loginForm);
		boolean sessiontimeout = false;

		try {
			mv.addObject("dropValue", typeValue);

			Map<String, Object> modelMap = model.asMap();
			for (Object modelKey : modelMap.keySet()) {
				Object modelValue = modelMap.get(modelKey);
				if (modelKey != null
						&& modelKey.toString().equalsIgnoreCase(
								"sessiontimeout")) {
					if (modelValue != null
							&& modelValue.toString().equalsIgnoreCase(
									"sessiontimeout")) {
						sessiontimeout = true;
					}
				}
			}
			mv.setViewName("login");

			List<String> loginUserList = new ArrayList<String>();
			String[] loginList = getLoginCookies(request, response).split(",");
			for (int i = 0; i < loginList.length; i++) {
				if (loginList[i] != null && !"".equalsIgnoreCase(loginList[i])) {
					String[] s = loginList[i].split(":");
					loginUserList.add(s[0] + " - " + s[1]);
				}
			}
			loginUserList.add("Add an account");
			mv.addObject("loginUserList", loginUserList);

			if (sessiontimeout)
				model.addAttribute("error", env.getProperty("sessiontimeout"));
		} catch (Exception e) {
			e.printStackTrace();
			mv.setViewName("login");
		}
		return mv;
	}

	/**
	 * This method is called when the user clicks patient login page
	 * 
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/patientlogin", method = RequestMethod.GET)
	public ModelAndView loadPatientLoginForm(HttpSession session,
			HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		LoginForm loginForm = new LoginForm();
		mv.addObject("loginForm", loginForm);
		try {
			mv.setViewName("patientlogin");
		} catch (Exception e) {
			e.printStackTrace();
			mv.setViewName("patientlogin");
		}
		return mv;
	}

	/**
	 * This method validates when the user enters the login credential details
	 * If validated Session is created and redirected to home page else
	 * returned to the login page.
	 * 
	 * @param model
	 * @param loginForm
	 * @param session
	 * @param response
	 * @param request
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "/checkLogin", method = RequestMethod.POST)
	public ModelAndView checkLogin(Model model,
			@ModelAttribute("loginForm") LoginForm loginForm,
			HttpSession session, HttpServletResponse response,
			HttpServletRequest request,
			final RedirectAttributes redirectAttributes) {

		int flagType = 0, userid = 0;
		ModelAndView mv = new ModelAndView();
		HipaaPasswordValidator hipaaValidator = null;
		List<PhysicianAccount> phyAcct = null;
		PhysicianAssistantAccount phyAsstAcct = null;
		AdminAccount admAcct = null;
		PatientAccount patAcct = null;
		List<GroupDirector> groupDirAcc = null;
		boolean isNewPhysician = false;
		// boolean isNewModifiedPhysician = false;
		boolean isNewPatient = false;
		String userName = "", status = "", adminType = "", physicianGroupName = "";
		String errMsg = "";
		loginForm.setPhysicianGroupName("");

		String selectedUserType = loginForm.getType();

		try {
			// //System.out.println("username ===="+loginForm.getUserName());
			// //System.out.println("password ===="+loginForm.getPassword());
			// //System.out.println("user type ===="+loginForm.getType());

			if (loginForm != null && loginForm.getUserName() != null
					&& loginForm.getPassword() != null
					&& loginForm.getType() != null) {

				hipaaValidator = new HipaaPasswordValidator();

				if (loginForm.getType().equalsIgnoreCase(
						PharmacyUtil.userPhysician)) {
					phyAcct = loginService.findPhysicianByUsername(loginForm
							.getUserName());
				} else if (loginForm.getType().equalsIgnoreCase(
						PharmacyUtil.userPhysicianAssistant)) {
					phyAsstAcct = loginService
							.findByPhysicianAssistantUsername(loginForm
									.getUserName());
				} else if (loginForm.getType().equalsIgnoreCase(
						PharmacyUtil.userAdministrator)
						|| loginForm.getType().equalsIgnoreCase(
								PharmacyUtil.userSuperAdmin)
						|| loginForm.getType().equalsIgnoreCase(
								PharmacyUtil.userAdmin)) {
					admAcct = loginService.findByAdminUsername(loginForm
							.getUserName());
				} else if (loginForm.getType().equalsIgnoreCase(
						PharmacyUtil.userPatient)) {
					patAcct = loginService.findByPatientUserLoginId(loginForm
							.getUserName());
				} else if (loginForm.getType().equalsIgnoreCase(
						PharmacyUtil.userGroupDirector)) {
					groupDirAcc = loginService
							.findGroupDirectorByUsername(loginForm
									.getUserName());
				}

				if (loginForm.getType().equalsIgnoreCase(
						PharmacyUtil.userPhysician)) {

					if (phyAcct.size() > 0) {
						userid = phyAcct.get(0).getId();
						userName = phyAcct.get(0).getPhysicianName();
						status = phyAcct.get(0).getStatus();
						physicianGroupName = phyAcct.get(0)
								.getPhysicianNameWithGroupName();

						// if (
						// "New".equalsIgnoreCase(phyAcct.get(0).getStatus()) ||
						// PharmacyUtil.statusProfileCompleted.equalsIgnoreCase(phyAcct.get(0).getStatus())
						// )
						if ("New".equalsIgnoreCase(phyAcct.get(0).getStatus()))
							isNewPhysician = true;
						// if
						// (PharmacyUtil.statusNewModified.equalsIgnoreCase(phyAcct.get(0).getStatus()))
						// isNewModifiedPhysician = true;
						flagType = hipaaValidator
								.checkUserLoginHipaaValidation(model,
										phyAcct.get(0), loginForm, env,
										loginService);
					}

				} else if (loginForm.getType().equalsIgnoreCase(
						PharmacyUtil.userPhysicianAssistant)) {

					if (phyAsstAcct != null) {
						userid = phyAsstAcct.getId();
						userName = phyAsstAcct.getAssistantName();
						status = phyAsstAcct.getStatus();

						flagType = hipaaValidator
								.checkUserLoginHipaaValidation(model,
										phyAsstAcct, loginForm, env,
										loginService);
					}

				} else if (loginForm.getType().equalsIgnoreCase(
						PharmacyUtil.userAdministrator)
						|| loginForm.getType().equalsIgnoreCase(
								PharmacyUtil.userSuperAdmin)
						|| loginForm.getType().equalsIgnoreCase(
								PharmacyUtil.userAdmin)) {

					if (admAcct != null) {
						userid = admAcct.getId();
						userName = admAcct.getName();
						status = admAcct.getStatus();
						adminType = admAcct.getType();

						flagType = hipaaValidator
								.checkUserLoginHipaaValidation(model, admAcct,
										loginForm, env, loginService);
					}
				} else if (loginForm.getType().equalsIgnoreCase(
						PharmacyUtil.userPatient)) {

					if (patAcct != null) {
						userid = patAcct.getId();
						userName = patAcct.getPatientName();
						status = patAcct.getStatus();
						if ("New".equalsIgnoreCase(status)
								|| PharmacyUtil.statusProfileCompleted
										.equalsIgnoreCase(status))
							isNewPatient = true;

						flagType = hipaaValidator
								.checkUserLoginHipaaValidation(model, patAcct,
										loginForm, env, loginService);
					}
				} else if (loginForm.getType().equalsIgnoreCase(
						PharmacyUtil.userGroupDirector)) {
					if (groupDirAcc.size() > 0) {
						userid = groupDirAcc.get(0).getId();
						userName = groupDirAcc.get(0).getGroupDirectorName();
						status = groupDirAcc.get(0).getStatus();

						flagType = hipaaValidator
								.checkUserLoginHipaaValidation(model,
										groupDirAcc.get(0), loginForm, env,
										loginService);
					}
				}

				//System.out.println("flagType ==========" + flagType);
				if (phyAcct != null || phyAsstAcct != null || admAcct != null
						|| patAcct != null || groupDirAcc != null) {
					if (flagType == 3 || flagType == 8 || flagType == 9)// if(flagType==3)
					{

						if (flagType == 8)// passwordgoingtoexpire
						{
							redirectAttributes
									.addFlashAttribute(
											"passwordwarningmessage",
											env.getProperty("error.passwordgoingtoexpire")
													+ ""
													+ model.asMap()
															.get("passwordresetDate"));
							model.addAttribute(
									"passwordwarningmessage",
									env.getProperty("error.passwordgoingtoexpire")
											+ ""
											+ model.asMap().get(
													"passwordresetDate"));
						} else if (flagType == 9)// passwordexpiring
						{
							redirectAttributes
									.addFlashAttribute(
											"passwordwarningmessage",
											env.getProperty("error.passwordexpiretoday"));
							model.addAttribute("passwordwarningmessage", env
									.getProperty("error.passwordexpiretoday"));
						}

						if (loginForm.getType().equalsIgnoreCase(
								PharmacyUtil.userPhysician)) {
							loginForm.setDisplayName(userName);
							/*
							 * loginForm.setPhotoUrl(phyService.
							 * getPhysicianPhotoFileName(userid,
							 * env.getProperty("file.photo_path")));
							 * loginForm.setLogoUrl
							 * (phyService.getPhysicianLogoFileName(userid,
							 * env.getProperty("file.logo_path")));
							 */
							loginForm
									.setPhotoUrl(fileUploadService.photoNameByPhysicianId(
											userid,
											env.getProperty("file.photo_path")));
							loginForm.setLogoUrl(fileUploadService
									.logoNameByPhysicianId(userid,
											env.getProperty("file.logo_path")));
							loginForm.setUserid(userid);
							loginForm.setType(PharmacyUtil.userPhysician);

							loginForm.setPhysicianGroupName(physicianGroupName);
							loginForm.setStatus(phyAcct.get(0).getStatus());

							session.setAttribute("loginDetail", loginForm);

							// redirected to profile page (if new stautus, else
							// dashboard)
							if (isNewPhysician)
								mv.setViewName("redirect:physician/PhysicianProfile");
							else
								mv.setViewName("redirect:dashboard/physiciandashboard");

						} else if (loginForm.getType().equalsIgnoreCase(
								PharmacyUtil.userPhysicianAssistant)) {
							loginForm.setDisplayName(userName);
							loginForm
									.setPhotoUrl(phyAssistService
											.getAssistantPhotoFileName(
													userid,
													env.getProperty("file.photo_path")));
							loginForm.setUserid(userid);
							loginForm
									.setType(PharmacyUtil.userPhysicianAssistant);

							if (PharmacyUtil.statusActive
									.equalsIgnoreCase(status))
								loginForm.setStatus(PharmacyUtil.statusActive);
							else
								loginForm
										.setStatus(PharmacyUtil.statusInactive);

							session.setAttribute("loginDetail", loginForm);

							// redirected to profile page
							// mv.setViewName("redirect:dashboard/physiciandashboard");

							// Modified on Jan 20,2018 - redirected to physician
							// assistant 's Physicians list screen
							mv.setViewName("redirect:dashboard/physicianassistanthome");

						} else if (loginForm.getType().equalsIgnoreCase(
								PharmacyUtil.userAdministrator)
								|| loginForm.getType().equalsIgnoreCase(
										PharmacyUtil.userSuperAdmin)
								|| loginForm.getType().equalsIgnoreCase(
										PharmacyUtil.userAdmin)) {
							// Load Admin Persmission List
							AdminPermissions adminPerm = null;
							adminPerm = adminPermissionRepo.findById(userid);
							if (adminPerm != null) {

								loginForm
										.setAdminAccCreationPermission(adminPerm
												.getAdminAccCreation());
								loginForm.setGroupCreationPermission(adminPerm
										.getGroupCreation());
								loginForm
										.setGroupDirectorCreationPermission(adminPerm
												.getGroupDirectorCreation());
								loginForm.setClinicCreationPermission(adminPerm
										.getClinicCreation());
								loginForm
										.setPhysicianCreationPermission(adminPerm
												.getPhysicianCreation());
								loginForm
										.setPhysicianApprovalPermission(adminPerm
												.getPhysicianApproval());
								loginForm
										.setAssistantCreationPermission(adminPerm
												.getAssistantCreation());
								loginForm
										.setPatientCreationPermission(adminPerm
												.getPatientCreation());
								loginForm
										.setPatientApprovalPermission(adminPerm
												.getPatientApproval());
								loginForm
										.setPrescriptionCreationPermission(adminPerm
												.getPrescriptionCreation());
								loginForm
										.setControlSubstanceDocUploadPermission(adminPerm
												.getControlSubstanceDocUpload());
								loginForm
										.setRemoteFileUploadPermission(adminPerm
												.getRemoteFileUpload());

							}

							if (adminType
									.equalsIgnoreCase(PharmacyUtil.userSuperAdmin)) {
								loginForm.setDisplayName(userName);
								loginForm
										.setPhotoUrl(adminService
												.getAdminPhotoFileName(
														userid,
														env.getProperty("file.photo_path")));
								loginForm.setUserid(userid);
								loginForm.setType(PharmacyUtil.userSuperAdmin);

								if (PharmacyUtil.statusActive
										.equalsIgnoreCase(status))
									loginForm
											.setStatus(PharmacyUtil.statusActive);
								else
									loginForm
											.setStatus(PharmacyUtil.statusInactive);

								session.setAttribute("loginDetail", loginForm);

								// redirected to profile page
								// mv.setViewName("redirect:admin/adminAccountSummary");
								mv.setViewName("redirect:dashboard/adminDashboard");
							} else if (adminType
									.equalsIgnoreCase(PharmacyUtil.userAdmin)) {
								loginForm.setDisplayName(userName);
								loginForm
										.setPhotoUrl(adminService
												.getAdminPhotoFileName(
														userid,
														env.getProperty("file.photo_path")));
								loginForm.setUserid(userid);
								loginForm.setType(PharmacyUtil.userAdmin);

								if (PharmacyUtil.statusActive
										.equalsIgnoreCase(status))
									loginForm
											.setStatus(PharmacyUtil.statusActive);
								else
									loginForm
											.setStatus(PharmacyUtil.statusInactive);

								session.setAttribute("loginDetail", loginForm);

								// redirected to profile page
								// mv.setViewName("redirect:admin/adminAccountSummary");
								// mv.setViewName("redirect:admin/adminProfile");
								mv.setViewName("redirect:dashboard/adminDashboard");
							}

						} else if (loginForm.getType().equalsIgnoreCase(
								PharmacyUtil.userPatient)) {
							loginForm.setDisplayName(userName);
							loginForm
									.setPhotoUrl(patientService
											.getPatientPhotoFileName(
													userid,
													env.getProperty("file.photo_path")));
							loginForm.setUserid(userid);
							loginForm.setType(PharmacyUtil.userPatient);

							/*
							 * if
							 * (PharmacyUtil.statusApproved.equalsIgnoreCase(status
							 * ))
							 * loginForm.setStatus(PharmacyUtil.statusApproved);
							 * else if
							 * (PharmacyUtil.statusNew.equalsIgnoreCase(status))
							 * loginForm.setStatus(PharmacyUtil.statusNew); else
							 * if
							 * (PharmacyUtil.statusDenied.equalsIgnoreCase(status
							 * ))
							 * loginForm.setStatus(PharmacyUtil.statusDenied);
							 * else if
							 * (PharmacyUtil.statusNewModified.equalsIgnoreCase
							 * (status))
							 * loginForm.setStatus(PharmacyUtil.statusNewModified
							 * );
							 */
							loginForm.setStatus(status);

							session.setAttribute("loginDetail", loginForm);

							// redirected to profile page
							if (isNewPatient)
								mv.setViewName("redirect:patient/patientProfile");
							else
								mv.setViewName("redirect:dashboard/patientdashboard");
						} else if (loginForm.getType().equalsIgnoreCase(
								PharmacyUtil.userGroupDirector)) {
							loginForm.setDisplayName(userName);
							loginForm
									.setPhotoUrl(grpDirectorService.getGroupDirectorPhotoFileName(
											userid,
											env.getProperty("file.photo_path")));
							loginForm.setUserid(userid);
							loginForm.setType(PharmacyUtil.userGroupDirector);
							loginForm.setGroupid(groupDirAcc.get(0)
									.getGroupId());
							loginForm.setGroupName(groupMasterRepo.findOne(
									groupDirAcc.get(0).getGroupId())
									.getGroupName());

							if (PharmacyUtil.statusActive
									.equalsIgnoreCase(status))
								loginForm.setStatus(PharmacyUtil.statusActive);
							else
								loginForm
										.setStatus(PharmacyUtil.statusInactive);
							session.setAttribute("loginDetail", loginForm);

							// redirected to profile page
							mv.setViewName("redirect:dashboard/groupdirectordashboard");
						}
						if ("Yes".equalsIgnoreCase(loginForm.getRememberMe())) {
							setLoginCookies(loginForm.getUserName(),
									selectedUserType, request, response);
						}
					} else {
						if (flagType == 7) {
							errMsg = env
									.getProperty("error.accountLockoutThreshold");
							model.addAttribute("loginForm", loginForm);
							model.addAttribute("error", errMsg
									+ model.asMap().get("elapsingtime"));
							if (loginForm.getType().equalsIgnoreCase(
									PharmacyUtil.userPatient))
								mv.setViewName("patientlogin");
							else
								mv.setViewName("login");

						} else if (flagType == 0) {
							if (loginForm.getType().equalsIgnoreCase(
									PharmacyUtil.userPatient))
								errMsg = env
										.getProperty("error.credentialsNotMatched");
							else
								errMsg = env
										.getProperty("error.invalidEmailID");
							model.addAttribute("loginForm", loginForm);
							model.addAttribute("error", errMsg);
							if (loginForm.getType().equalsIgnoreCase(
									PharmacyUtil.userPatient))
								mv.setViewName("patientlogin");
							else
								mv.setViewName("login");
						} else if (flagType == 1) {
							errMsg = env.getProperty("error.inactiveAccount");
							model.addAttribute("loginForm", loginForm);
							model.addAttribute("error", errMsg);
							if (loginForm.getType().equalsIgnoreCase(
									PharmacyUtil.userPatient))
								mv.setViewName("patientlogin");
							else
								mv.setViewName("login");
						} else if (flagType == 4 || flagType == 6)// else
																	// if(flagType==4
																	// ||
																	// flagType==6
																	// ||
																	// flagType==8
																	// ||
																	// flagType==9)
						{
							ResetPasswordForm resetPasswordForm = new ResetPasswordForm();
							resetPasswordForm.setUserId(userid);
							resetPasswordForm.setUserName(userName);
							resetPasswordForm.setUserType(loginForm.getType());
							resetPasswordForm.setUserEmailId(loginForm
									.getUserName());
							resetPasswordForm.setOldPassword(loginForm
									.getPassword());
							resetPasswordForm.setRememberMe(loginForm
									.getRememberMe());
							redirectAttributes.addFlashAttribute(
									"resetPasswordForm", resetPasswordForm);
							errMsg = env.getProperty("error.resetPassword");
							model.addAttribute("error", errMsg);
							mv.setViewName("redirect:/loadResetPasswordForm");
						}
					}
				} else {
					errMsg = env.getProperty("error.invalidEmailID");
					model.addAttribute("loginForm", loginForm);
					model.addAttribute("error", errMsg);
					if (loginForm.getType().equalsIgnoreCase(
							PharmacyUtil.userPatient))
						mv.setViewName("patientlogin");
					else
						mv.setViewName("login");
				}
			} else {
				model.addAttribute("loginForm", loginForm);
				model.addAttribute("error", env.getProperty("error.notEmpty"));
				if (loginForm.getType().equalsIgnoreCase(
						PharmacyUtil.userPatient))
					mv.setViewName("patientlogin");
				else
					mv.setViewName("login");
			}
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("message",
					env.getProperty("error.internalError"));
			mv.setViewName("error500");
		}
		return mv;
	}

	/**
	 * This method is called when the user clicks Logout button. Session
	 * destroyed and redirected to login page.
	 * 
	 * @param model
	 * @param request
	 * @param session
	 * @param sessionStatus
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public ModelAndView loadLogout(Model model,
			final HttpServletRequest request, HttpSession session,
			SessionStatus sessionStatus, RedirectAttributes redirectAttributes) {
		ModelAndView mv = new ModelAndView();
		String viewName = "";

		LoginForm loggedInUser = (LoginForm) session
				.getAttribute("loginDetail");
		if (loggedInUser != null
				&& loggedInUser.getType().equalsIgnoreCase(
						PharmacyUtil.userPatient)) {
			mv.setViewName("redirect:patientlogout");
		} else {
			if (session != null
					&& session.getAttribute("sessiontimeout") != null) {
				request.setAttribute("sessiontimeout", "sessiontimeout");
				model.addAttribute("sessiontimeout", "sessiontimeout");
				redirectAttributes.addFlashAttribute("sessiontimeout",
						"sessiontimeout");
			}
			viewName = "redirect:login";

			HttpSession oldSession = request.getSession();
			oldSession.removeAttribute("loginDetail");
			oldSession.removeAttribute("userType");
			oldSession.invalidate();
			sessionStatus.setComplete();

			mv.setViewName(viewName);
		}

		return mv;
	}

	/**
	 * This method is called when the user clicks Logout button. Session
	 * destroyed and redirected to patient login page.
	 * 
	 * @param request
	 * @param sessionStatus
	 * @return
	 */
	@RequestMapping(value = "/patientlogout", method = RequestMethod.GET)
	public ModelAndView loadPatientLogout(final HttpServletRequest request,
			SessionStatus sessionStatus) {
		ModelAndView mv = new ModelAndView();

		HttpSession oldSession = request.getSession();
		oldSession.removeAttribute("loginDetail");
		oldSession.removeAttribute("userType");
		oldSession.invalidate();
		sessionStatus.setComplete();

		mv.setViewName("redirect:patientlogin");
		return mv;
	}

	/**
	 * This method is called when the user clicks Logout button. Session
	 * destroyed and redirected to physician assistant page page.
	 * 
	 * @param model
	 * @param request
	 * @param session
	 * @param sessionStatus
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "/exitphysicianlogout", method = RequestMethod.GET)
	public ModelAndView exitphysicianlogout(Model model,
			final HttpServletRequest request, HttpSession session,
			SessionStatus sessionStatus, RedirectAttributes redirectAttributes) {
		ModelAndView mv = new ModelAndView();
		String viewName = "redirect:dashboard/physicianassistanthome";

		LoginForm loggedInUser = (LoginForm) session
				.getAttribute("loginDetail");

		HttpSession oldSession = request.getSession();
		oldSession.removeAttribute("physicianGroupName");
		oldSession.removeAttribute("physicianAssistantPhysicianId");
		oldSession.removeAttribute("physicianAssistantId");

		mv.setViewName(viewName);
		return mv;
	}

	/**
	 * If the login details matched the cookies is created to remember the login
	 * details in the browser
	 * 
	 * @param userName
	 * @param userType
	 * @param request
	 * @param response
	 * @return
	 */
	public static String setLoginCookies(String userName, String userType,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			boolean isFound = false;
			Cookie[] cookies = request.getCookies();
			String[] userArr = null;
			if (cookies.length > 1) {
				for (int i = 0; i < cookies.length; i++) {
					Cookie c = cookies[i];
					if (c.getName().equals("usersList")) {
						userArr = c.getValue().split(",");
						for (int j = 0; j < userArr.length; j++) {
							String[] data = userArr[j].split(":");
							if (data[0].equalsIgnoreCase(userName + "")) {
								isFound = true;
							}
						}
						if (!isFound) {
							String data = userName + ":" + userType;
							String val = c.getValue();
							val = val + "," + data;
							response.addCookie(new Cookie("usersList", val));
						}
					}
				}
			} else {
				String data = userName + ":" + userType;
				response.addCookie(new Cookie("usersList", data));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * If the remove cookie is clicked the login cookies stored in browser is
	 * removed
	 * 
	 * @param model
	 * @param typeValue
	 * @param session
	 * @param loginForm
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/loginRemoveCookie", method = RequestMethod.POST)
	public ModelAndView removeCookie(Model model,
			@RequestParam(value = "s", required = false) String typeValue,
			HttpSession session,
			@ModelAttribute("loginForm") LoginForm loginForm,
			HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		try {
			loginForm.setUserName(loginForm.getTmpUserName().trim());
			loginForm.setType(loginForm.getTmpType().trim());
			if (loginForm.getUserName().equalsIgnoreCase("Add an account")) {
				loginForm.setUserName("");
				loginForm.setType("");
			}

			String newCookiesValues = "";
			Cookie[] cookies = request.getCookies();
			String[] userArr = null;
			if (cookies.length > 1) {
				for (int i = 0; i < cookies.length; i++) {
					Cookie c = cookies[i];
					if (c.getName().equals("usersList")) {
						userArr = c.getValue().split(",");
						for (int j = 0; j < userArr.length; j++) {
							String[] data = userArr[j].split(":");
							if (!data[0].equalsIgnoreCase(loginForm
									.getUserName())) {
								newCookiesValues = newCookiesValues + ","
										+ userArr[j];
							}
						}
						response.addCookie(new Cookie("usersList",
								newCookiesValues));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		mv.setViewName("redirect:login");
		return mv;
	}

	/**
	 * Return the stored login cookies stored in the browser
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	private String getLoginCookies(HttpServletRequest request,
			HttpServletResponse response) {
		String loginArr = "";
		Cookie[] cookies = request.getCookies();

		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				Cookie c = cookies[i];
				if (c.getName().equals("usersList")) {
					loginArr = c.getValue();
				}
			}
		}

		return loginArr;
	}

	/**
	 * Resets the login cookies in the browser if the remove cookie is clicked
	 * 
	 * @param model
	 * @param typeValue
	 * @param session
	 * @param loginForm
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/loginWithId", method = RequestMethod.POST)
	public ModelAndView loginWithId(Model model,
			@RequestParam(value = "s", required = false) String typeValue,
			HttpSession session,
			@ModelAttribute("loginForm") LoginForm loginForm,
			HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();

		loginForm.setUserName(loginForm.getTmpUserName().trim());
		loginForm.setType(loginForm.getTmpType().trim());
		loginForm.setRememberMe("Yes");
		if (loginForm.getUserName().equalsIgnoreCase("Add an account")) {
			loginForm.setUserName("");
			loginForm.setType("");
		}

		mv.addObject("loginForm", loginForm);
		boolean sessiontimeout = false;

		try {
			mv.addObject("dropValue", typeValue);

			Map<String, Object> modelMap = model.asMap();
			for (Object modelKey : modelMap.keySet()) {
				Object modelValue = modelMap.get(modelKey);
				if (modelKey != null
						&& modelKey.toString().equalsIgnoreCase(
								"sessiontimeout")) {
					if (modelValue != null
							&& modelValue.toString().equalsIgnoreCase(
									"sessiontimeout")) {
						sessiontimeout = true;
					}
				}
			}
			mv.setViewName("login");

			List<String> loginUserList = new ArrayList<String>();
			String[] loginList = getLoginCookies(request, response).split(",");
			for (int i = 0; i < loginList.length; i++) {
				if (loginList[i] != null && !"".equalsIgnoreCase(loginList[i])) {
					String[] s = loginList[i].split(":");
					loginUserList.add(s[0] + " - " + s[1]);
				}
			}
			loginUserList.add("Add an account");
			mv.addObject("loginUserList", loginUserList);

			if (sessiontimeout)
				model.addAttribute("error", env.getProperty("sessiontimeout"));
		} catch (Exception e) {
			e.printStackTrace();
			mv.setViewName("login");
		}
		return mv;

	}

}
