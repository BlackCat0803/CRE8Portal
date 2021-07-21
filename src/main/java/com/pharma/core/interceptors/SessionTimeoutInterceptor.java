package com.pharma.core.interceptors;


import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.pharma.core.formBean.LoginForm;
/**
 * This class checks to see if the user's session has not timed out and if it has, reloads it from the database otherwise logs out and redirects to the login page
 */
public class SessionTimeoutInterceptor implements HandlerInterceptor  {
	
	@Autowired
	private ServletContext servletContext;
	  

	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		
		/*//System.out.println("Pre-handle");
		//System.out.println("Inside Interceptor 1111111111111111"+request.getRequestURL());
		//System.out.println("Inside Interceptor 1111111111111111"+request.getRequestURI());*/
		HttpSession session = request.getSession();
		 try
	        {
			 	
			 
			 	String reqURL=request.getRequestURL().toString();
			 	String reqURI=request.getRequestURI().toString();
			 	
			 	
			 	//The URL Map exempt from the Authenticated Session Flow
			 	if(reqURL.indexOf("/login")!=-1 || reqURL.indexOf("/patientlogin")!=-1 || reqURL.indexOf("/logout")!=-1 || reqURL.indexOf("/patientlogout")!=-1 
			 			|| reqURL.indexOf("/checkLogin")!=-1 || reqURL.indexOf("/forgotPassword")!=-1 || reqURL.indexOf("/patientforgotPassword")!=-1 || reqURL.indexOf("/physician/PhysicianRegistration")!=-1 
			 			|| reqURL.indexOf("/checkForgotPasswordEmailidExists")!=-1 || reqURL.indexOf("/validateUserSecurityQuestionsandAnswers")!=-1  || reqURL.indexOf("/alreadyUserPasswordExist")!=-1  
			 			|| reqURL.indexOf("/saveResetPasswordDetails")!=-1 || reqURL.indexOf("/loadResetPasswordForm")!=-1 || reqURL.indexOf("/physician/SavePhysicianRegistration")!=-1) {
			 			//System.out.println("session value aaaaaaaaaaaaaaaaaa============="+session.isNew()+"=========="+session.getId());
			 			return true;
			 	}else
				 {
					 if(session!=null && session.getAttribute("loginDetail")!=null)
					 	{
						 	//System.out.println("session value bbbbbbbbbbbbbbbbbb============="+session.isNew()+"=========="+session.getId());
						 	return true;
						 
						 
					 	}else if(session!=null && session.getAttribute("loginDetail")==null)
					 	{
					 		/*//System.out.println("session value ccccccccccccccccccc============="+session.isNew()+"=========="+session.getId());
							//System.out.println("Auth Token time Out 11111111111111111");*/
					 		session.setAttribute("sessiontimeout", "sessiontimeout");
			            	response.sendRedirect(servletContext.getContextPath()+"/logout");
			                return false;
						 
						 
					 	}else
					 	{
					 		String userType = (String) session.getAttribute("userType");
				            LoginForm sessLoginForm=(LoginForm) session.getAttribute("loginDetail");
				           /* //System.out.println("Interceptor invoked For Auth token == "+userType);
				            //System.out.println("Interceptor invoked For Auth token sessLoginForm== "+sessLoginForm);*/
				            
				            if(userType==null || userType.equals(""))
				            {
				            	//System.out.println("Auth Token time Out 2222222222222");
				            	session.setAttribute("sessiontimeout", "sessiontimeout");
				            	response.sendRedirect(servletContext.getContextPath()+"/logout");
				                return false;
				            }
				            else
				            {
				            	/*//System.out.println("*********** inside session *****************");
				            	//System.out.println("Interceptor invoked For sessLoginForm name == "+sessLoginForm.getUserName()+":: userType ::"+userType);*/
				            	return true;
				            }
					 	}
					 
					 
				 }
			 
			 		
	        }catch(Exception ex)
	        {
		         ex.getMessage();
		         ex.printStackTrace();
		         session.setAttribute("sessiontimeout", "sessiontimeout");
		         response.sendRedirect(servletContext.getContextPath()+"/logout");
	             return false;
	        }
		
	}
	

	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		//System.out.println("Post-handle");
	}
	

	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		//System.out.println("After completion handle");
	}
}