package com.pharma.core.pharmaservices.adminservices;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.pharma.core.formBean.LoginForm;
import com.pharma.core.formBean.admin.AdminForm;
import com.pharma.core.model.admin.AdminAccount;

@Service
public interface AdminAccountService {

	String getAdminDataList(int draw, int start, int len, String searchTerm, int orderColumn, String orderDir, String admin, String pharmacy, String userType, String status);
	
	AdminAccount findAccountByUsernamePassword(String userName, String password);	
	
	List<AdminAccount> getAdminAccountByEmailAndNotId(String email, int adminId);
	
	List<AdminAccount> getAdminAccountByEmail(String email);
	
	AdminAccount saveAdminAccount(AdminForm form, CommonsMultipartFile photoFile, CommonsMultipartFile[] docFiles, String rootFilePath, LoginForm loggedInUser, 
			HttpSession session, HttpServletRequest request, Environment env);
	
	AdminForm getAdminAccountById(int id, String filepath);
	
	String getAdminPhotoFileName(int id, String filepath);

	boolean sendPharmacyAdminAccountMail(AdminForm form, LoginForm loggedInUser,HttpSession session, HttpServletRequest request, Environment env);
}
