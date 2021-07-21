package com.pharma.core.pharmaservices.pharmacygroupservices;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.pharma.core.formBean.LoginForm;
import com.pharma.core.formBean.pharmacygroup.GroupDirectorForm;
import com.pharma.core.model.pharmacygroup.GroupDirector;


@Service
public interface GroupDirectorService {
	
	GroupDirector saveGroupDirectorAccount(GroupDirectorForm form, CommonsMultipartFile photoFile, CommonsMultipartFile[] docFiles, 
			String rootFilePath, LoginForm loggedInUser, HttpSession session, HttpServletRequest request, Environment env);

	List<GroupDirector> getGroupDirectorByEmail(String email);
	
	GroupDirectorForm getGroupDirectorById(int id, String filepath);
	
	List<GroupDirector> getGroupDirectorByEmailAndNotId(String email, int id);
	
	String getGroupDirectorDataList(int draw, int start, int len, String searchTerm, int orderColumn, String orderDir,String groupDirectorName, 
			int groupName, String status);
		
	GroupDirector getGroupDirectorDetails(int id);

	String getGroupDirectorPhotoFileName(int id, String filepath);

	boolean sendGroupDirectorMail(GroupDirectorForm form,LoginForm loggedInUser, HttpSession session,HttpServletRequest request, Environment env);
}
