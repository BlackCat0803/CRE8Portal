 <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
	 <%@ include file="../../../layout/taglib.jsp" %>
        
    
	<form:form method="POST" action="SaveAdminAccountProfile" name="adminAccount" commandName="adminAccount"  >    

    
   
        <!-- page content -->
       
          <div class="">
            <div class="page-title">
              <div class="title_left">
                <h3>Admin Account Setup</h3>
              </div>
            </div>

            <div class="clearfix"></div>

            <div class="row">
              <div class="col-md-12 col-sm-12 col-xs-12">
                <div class="x_panel">
                  <div class="x_title">
                    <h2>Dr. John Doe</h2>
                    <div class="clearfix"></div>
                  </div>
                  <div class="x_content">
                    <div class="row">
                    	<div class="col-md-10 col-sm-12 col-xs-12 profile_left">
							<div class="row form-group">
                        		<label class="col-md-2 col-sm-2 col-xs-12 control-label required" for="physician-name">Pharmacy Name</label>
                        		<div class="col-md-3 col-sm-2 col-xs-12">
                        		<select class="select2_single form-control" >
			                             <option value="Select">Select</option>
			                         </select>
                         		</div>
                         		<div class="col-md-2 col-sm-2 col-xs-12">
                          			<select class="select2_single form-control" >
			                             <option value="Active">Active</option>
			                            <option value="Inactive">Inactive</option>
			                         </select>
                        		</div>
                      		</div>
                      	
                      		
                      		<div class="row form-group">
                        		<label for="status" class="control-label col-md-2 col-sm-2 col-xs-12 control-label required">Type</label>
                        		<div class="col-md-3 col-sm-2 col-xs-12">
                          			<select class="select2_single form-control" >
			                            <option value=""> Select</option>
			                            <option value="SuperAdmin">SuperAdmin</option>
			                            <option value="Admin">Admin</option>
			                         </select>
                        		</div>
                        		
                        		<label class="col-md-2 col-sm-2 col-xs-12 control-label required" for="dob">Registration Date</label>
				                <div class='col-md-3 col-sm-2 col-xs-12 input-group' style="padding: 0px 9px 0px;float:left;">
				                	<form:input path="registrationDate" class="form-control" />
				                </div>
                      		</div>

                      		<div class="row form-group">
                        		<label for="first-name" class="control-label col-md-2 col-sm-2 col-xs-12 control-label required">First Name</label>
                        		<div class="col-md-3 col-sm-2 col-xs-12">
                          			<form:input path="firstName" class="form-control" />
                        		</div>
                      	
                        		<label for="middle-name" class="control-label col-md-2 col-sm-2 col-xs-12">Middle Name</label>
                        		<div class="col-md-1 col-sm-2 col-xs-12">
                          			<form:input path="middleName" class="form-control" />
                        		</div>
                        		
                        		<label for="last-name" class="control-label col-md-2 col-sm-2 col-xs-12">Last Name</label>
                      		   	<div class="col-md-2 col-sm-2 col-xs-12">
                          			<form:input path="lastName" class="form-control" />
                        		</div>
                      		</div>
	
							<div class="row form-group">
                        		<label class="col-md-2 col-sm-2 col-xs-12 control-label" for="print-name">Name</label>
                        		<div class="col-md-3 col-sm-2 col-xs-12">
                          			<form:input path="name" class="form-control" />
                        		</div>
                      		</div>
                    		<div class="row form-group">
                        		<label class="col-md-2 col-sm-2 col-xs-12 control-label required" for="email">Email </label>
                        		<div class="col-md-3 col-sm-2 col-xs-12">
                          			<form:input path="email" class="form-control" />
                        		</div>
                        		
                        		
                        		<label class="col-md-2 col-sm-2 col-xs-12 control-label required" for="email">Password </label>
                        		<div class="col-md-2 col-sm-2 col-xs-12">
                          			<form:input path="password" class="form-control" />
                        		</div>
		                     </div>
		                    
		                      	<div class="row form-group">
	                        		<label class="col-md-2 col-sm-2 col-xs-12 control-label required" for="mobile">Phone </label>
	                        		<div class="col-md-3 col-sm-2 col-xs-12">
	                          			<form:input path="phone" class="form-control" />
	                        		</div>
	                        		
	                        		<label class="col-md-2 col-sm-2 col-xs-12 control-label required" for="mobile">Mobile </label>
	                        		<div class="col-md-2 col-sm-2 col-xs-12">
	                          			<form:input path="mobile" class="form-control" />
	                        		</div>
	                      		</div>
	                      			
	                      			<div class="row form-group">	
	                      		<div class="col-md-12 col-sm-12 col-xs-12 text-center">
								<button type="submit" class="btn btn-success">Save</button>
								<button type="button" class="btn btn-primary">Cancel</button>
							</div>
                      	</div>
                    	</div>
                    	
					</div>
                  </div>
                </div>
              </div>
            </div>


           

          
 




          </div>
      
        
     
        <!-- Error messages getting from property file -->
	<input type="hidden" id="err_pharmacy_name" value='<spring:eval expression="@environment.getProperty(\'error.pharmacy_name\')" />' />
	<input type="hidden" id="err_status" value='<spring:eval expression="@environment.getProperty(\'error.status\')" />' />
	<input type="hidden" id="err_date_registration" value='<spring:eval expression="@environment.getProperty(\'error.date_of_registration\')" />' />
	<input type="hidden" id="err_fname" value='<spring:eval expression="@environment.getProperty(\'error.first_name\')" />' />
	<input type="hidden" id="err_lname" value='<spring:eval expression="@environment.getProperty(\'error.last_name\')" />' />
	<input type="hidden" id="err_conf_email" value='<spring:eval expression="@environment.getProperty(\'error.email\')" />' />
	<input type="hidden" id="err_email" value='<spring:eval expression="@environment.getProperty(\'error.email_format\')" />' />
	<input type="hidden" id="err_pwd" value='<spring:eval expression="@environment.getProperty(\'error.password\')" />' />
       <!-- /page content -->
 		
    <script src="${pageContext.request.contextPath}/resources/js/pages/test/adminAccount.js"></script>
</form:form>