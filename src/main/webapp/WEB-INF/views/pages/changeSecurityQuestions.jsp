 <%--
Front End UI allows the user to Change the Security Questions and Answers prompts to validate the Forgot Password and Change Password
 --%>
<%@ include file="../../layout/taglib.jsp" %>
<%--includes the Front End UI validations--%>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/pages/changeSecurityQuestions.js?v=2"></script>

<c:url var="home" value="/" scope="request" />
<form:form method="POST" action="saveSecurityQuestionsDetails" name="changeSecurityQuestionForm" commandName="changeSecurityQuestionForm">
<!-- page content -->

<div class="">

		<c:if test="${not empty error}">
			<div class="row">
				<div class="col-md-12 col-sm-12 col-xs-12 col-lg-7 col-lg-offset-2 fontSize14 text-center">
					<div class="alert alert-danger fade in">
					    <a href="#" class="close" data-dismiss="alert">&times;</a>
					   ${error}
					</div>											
				</div>
			</div>
		</c:if> 
		<c:if test="${not empty message}">
			<div class="row">
				<div class="col-md-12 col-sm-12 col-xs-12 col-lg-7 col-lg-offset-2 fontSize14 text-center">
					<div class="alert alert-success fade in">
						<a href="#" class="close" data-dismiss="alert">&times;</a>  &nbsp;&nbsp;&nbsp; ${message}
					</div>
				</div>
			</div>
		</c:if>
		<div class="row" id="errorDiv" style="display: none">
				<div class="col-md-12 col-sm-12 col-xs-12 col-lg-7 col-lg-offset-2 fontSize14 text-center">
					<div class="alert alert-danger fade in">
					    <a href="#" class="close" data-dismiss="alert">&times;</a>
					    <div id="errorMsg"></div>
					</div>											
				</div>
		</div>
		
		<div class="row">
			<div class="col-md-12 col-sm-12 col-xs-12 col-lg-11 col-lg-offset-0">
				<div class="x_panel profileBorder">
                  	<div class="x_title">
                    	<h3>Security Questions</h3>
                    	<div class="clearfix"></div>
                    	<h2 style="color:black;">
						    User Account :<label id="usernameLbl"></label>
						</h2>
                  	</div>				
					<div class="x_content ">

						<div class="row">
							<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
								<label class="col-md-2 col-sm-6 col-xs-12 control-label required" for="securityquestion">Security Question 1</label>
								<div class="col-md-8 col-sm-6 col-xs-12 form-group">
									<form:select path="securityquestion" class="select2_single form-control" id="securityquestion"  required="required" >
										<form:option value="">Select Security Question 1</form:option>
										<form:options items="${securityQuestions}" itemLabel="securityquestion" itemValue="rowId"/>
									</form:select>
								</div>
							</div>
						</div>
						
						<div class="row">
							<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
								<label class="col-md-2 col-sm-6 col-xs-12 control-label required" for="securityanswer">Security Answer 1</label>
								<div class="col-md-8 col-sm-6 col-xs-12 form-group">
									<form:input path="securityanswer" class="form-control" placeholder="Security Answer" maxlength="50"  required="required" />									
								</div>
							</div>
						</div>
						
						<div class="row">
							<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
								<label class="col-md-2 col-sm-6 col-xs-12 control-label required" for="securityquestion2">Security Question 2</label>
								<div class="col-md-8 col-sm-6 col-xs-12 form-group">
									<form:select path="securityquestion2" class="select2_single form-control" id="securityquestion2"  required="required" >
										<form:option value="">Select Security Question 2</form:option>
										<form:options items="${securityQuestions}" itemLabel="securityquestion" itemValue="rowId"/>
									</form:select>
								</div>
							</div>
						</div>

						<div class="row">
							<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
								<label class="col-md-2 col-sm-6 col-xs-12 control-label required" for="securityanswer2">Security Answer 2</label>
								<div class="col-md-8 col-sm-6 col-xs-12 form-group">
									<form:input path="securityanswer2" class="form-control" placeholder="Security Answer" maxlength="50"  required="required" />
								</div>
							</div>
						</div>
					
						<div class="row">
							<div class="col-md-12 col-sm-12 col-xs-12 profile_left">
								<div class="row form-group">
									<div class="col-md-12 col-sm-12 col-xs-12 text-center">
										<button type="submit" id="submitBtn" class="btn-lg btn-primary">
										<span class="glyphicon glyphicon-thumbs-up"></span> Submit</button>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		
	
	</div>

             
	
	<form:hidden path="userId"/>
	<form:hidden path="userName"/>
	<form:hidden path="userType"/>
	<form:hidden path="userEmailId"/>

	
	<!-- Error messages getting from property file -->
	<input type="hidden" id="error_oldpasswordreused"  name="error_oldpasswordreused" value='<spring:eval expression="@hipaaConfigurer.getProperty(\'error.oldpasswordreused\')" />' />
	<input type="hidden" id="error_resetPassword"  name="error_resetPassword" value='<spring:eval expression="@hipaaConfigurer.getProperty(\'error.resetPassword\')" />' />
	<input type="hidden" id="error_newPasswordNotSameasOldPassword"  name="error_newPasswordNotSameasOldPassword" value='<spring:eval expression="@hipaaConfigurer.getProperty(\'error.newPasswordNotSameasOldPassword\')" />' />
	
	<input type="hidden" id="error_newpassword"  name="error_newpassword" value='<spring:eval expression="@resetPasswordConfigurer.getProperty(\'error.newpassword\')" />' />
	<input type="hidden" id="error_reenterpassword"  name="error_reenterpassword" value='<spring:eval expression="@resetPasswordConfigurer.getProperty(\'error.reenterpassword\')" />' />
	<input type="hidden" id="error_passwordnotmatch"  name="error_passwordnotmatch" value='<spring:eval expression="@resetPasswordConfigurer.getProperty(\'error.passwordnotmatch\')" />' />
	<input type="hidden" id="error_securityQuestion1"  name="error_securityQuestion1" value='<spring:eval expression="@resetPasswordConfigurer.getProperty(\'error.securityQuestion1\')" />' />
	<input type="hidden" id="error_securityAnswer1"  name="error_securityAnswer1" value='<spring:eval expression="@resetPasswordConfigurer.getProperty(\'error.securityAnswer1\')" />' />
	<input type="hidden" id="error_securityQuestion2"  name="error_securityQuestion2" value='<spring:eval expression="@resetPasswordConfigurer.getProperty(\'error.securityQuestion2\')" />' />
	<input type="hidden" id="error_securityAnswer2"  name="error_securityAnswer2" value='<spring:eval expression="@resetPasswordConfigurer.getProperty(\'error.securityAnswer2\')" />' />
	<input type="hidden" id="error_questionsnotmatch"  name="error_questionsnotmatch" value='<spring:eval expression="@resetPasswordConfigurer.getProperty(\'error.questionsnotmatch\')" />' />
	
	
	
	
	
	<!-- Hipaa Error Messages -->
	<input type="hidden" id="passwordTextMsg"  name="passwordTextMsg" value='<spring:eval expression="@hipaaConfigurer.getProperty(\'info.passwordTextMsg\')" />' />
	<input type="hidden" id="passwordminlength"  name="passwordminlength" value='<spring:eval expression="@hipaaConfigurer.getProperty(\'password.minlength\')" />' />
	<input type="hidden" id="passwordmaxlength"  name="passwordmaxlength" value='<spring:eval expression="@hipaaConfigurer.getProperty(\'password.maxlength\')" />' />
	<input type="hidden" id="error_passwordminmax"  name="error_passwordminmax" value='<spring:eval expression="@hipaaConfigurer.getProperty(\'error.passwordminmax\')" />' />
	<input type="hidden" id="error_categoriesnotmatch"  name="error_categoriesnotmatch" value='<spring:eval expression="@hipaaConfigurer.getProperty(\'error.categoriesnotmatch\')" />' />
	<input type="hidden" id="error_sequencematch"  name="error_sequencematch" value='<spring:eval expression="@hipaaConfigurer.getProperty(\'error.sequencematch\')" />' />
	<input type="hidden" id="error_temporarypassword"  name="error_temporarypassword" value='<spring:eval expression="@hipaaConfigurer.getProperty(\'error.temporarypassword')" />' />
	<!-- Hipaa Error Messages -->
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/pages/HipaaPasswordValidator.js?v=3"></script>
	
</form:form>

