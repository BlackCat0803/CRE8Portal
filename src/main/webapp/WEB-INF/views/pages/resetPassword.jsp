<%--
Front End UI prompts the user to Change Password
 --%>
<%@ include file="../../layout/taglib.jsp" %>
<%--includes the Front End UI validations--%>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/pages/resetPassword.js?v=2"></script>

<c:url var="home" value="/" scope="request" />
<form:form method="POST" action="saveResetPasswordDetails" name="resetPasswordForm" commandName="resetPasswordForm">
	<div class="row">
		<div class="col-lg-offset-4 col-lg-8 col-md-offset-4 col-md-8 col-sm-12 col-xs-12" style="float: left; padding: 0px; background: transparent">
			<div style="float: left; padding: 0px">
				<div style="float: left; padding: 0px">
					<div class="row" style="float: left; padding: 0px">
						<div class="col-xs-12 col-sm-12 col-md-12 col-lg-offset-1 col-lg-12 ">
							<div class="row">
								<div class="col-xs-12 col-sm-12 col-md-offset-1 col-md-10 col-lg-12">
									<div class="signupArea2">
										<%-- <c:if test="${not empty message}">
											<div class="row">
												<div class="col-sm-offset-1 col-sm-10  fontSize14 text-center">
													<div class="alert alert-danger fade in">
													    <a href="#" class="close" data-dismiss="alert">&times;</a>
													    ${message}
													</div>											
												</div>
											</div>
										</c:if>   --%>
										<c:if test="${not empty error}">
											<div class="row">
												<div class="col-sm-12  fontSize14 text-center">
													<div class="alert alert-danger fade in">
													    <a href="#" class="close" data-dismiss="alert">&times;</a>
													   ${error}
													</div>											
												</div>
											</div>
										</c:if> 
										<div class="row" id="errorDiv" style="display: none">
												<div class="col-sm-12  fontSize14 text-center">
													<div class="alert alert-danger fade in">
													    <a href="#" class="close" data-dismiss="alert">&times;</a>
													    <div id="errorMsg"></div>
													</div>											
												</div>
										</div>
										<div class="row">
											<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 sign_head13 bottomGap headunderline text-center">
												 Change Password Required
												<h3 >
												    User Account :<label id="usernameLbl"></label>
												</h3>		
											</div>
										</div>
										<div class="row">
											<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 topGap">
										
												<div class="row">
														<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 sign_design1 form-group has-feedback required-field-block">
														<form:password path="newPassword"  class="form-control has-feedback-left" placeholder="New Password"  required="required"    onkeyup="stripHipaaSpChars(true,'~!@#$%^*&;?.+_',true,this);" onblur="stripHipaaSpChars(true,'~!@#$%^*&;?.+_',true,this);" id="newPassword"  autocomplete="new-password"/>
														<span class="fa fa-gear form-control-feedback left" aria-hidden="true"></span>
														</div>
												</div>
												
												<div class="row">
													<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 sign_design1 form-group has-feedback required-field-block">
														<form:password path="reenterPassword"  class="form-control has-feedback-left" placeholder="Re-Enter Password"   required="required"   onkeyup="stripHipaaSpChars(true,'~!@#$%^*&;?.+_',true,this);" onblur="stripHipaaSpChars(true,'~!@#$%^*&;?.+_',true,this);" id="reenterPassword"  autocomplete="new-password"/>
														<span class="fa fa-gear form-control-feedback left" aria-hidden="true"></span>
													</div>
												</div>
												<!-- <div class="row">
														<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 sign_design1 form-group has-feedback required-field-block">
															<label id="passwordTxtLbl" style="width:450px;color:white;text-align: center;"></label>
														</div>
												</div> -->
												
												<div class="row">
													
													<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 sign_design1 form-group has-feedback required-field-block">
														<form:select path="securityquestion" class="select2_single form-control has-feedback-left " id="securityquestion"  required="required" >
															<form:option value="">Select Security Question 1</form:option>
															<form:options items="${securityQuestions}" itemLabel="securityquestion" itemValue="rowId"/>
														</form:select>
														<span class="fa fa-arrows form-control-feedback left" aria-hidden="true"></span>
													</div>
													
												</div>
												
												<div class="row">
													<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 sign_design1 form-group has-feedback required-field-block">
														<form:input path="securityanswer" class="form-control has-feedback-left" placeholder="Security Answer" maxlength="50"  required="required" />
														<span class="fa fa-user form-control-feedback left" aria-hidden="true"></span>
													</div>
													
												</div>
												
												<div class="row">
													
													<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 sign_design1 form-group has-feedback required-field-block">
														<form:select path="securityquestion2" class="select2_single form-control has-feedback-left " id="securityquestion2"  required="required" >
															<form:option value="">Select Security Question 2</form:option>
															<form:options items="${securityQuestions}" itemLabel="securityquestion" itemValue="rowId"/>
														</form:select>
														<span class="fa fa-arrows form-control-feedback left" aria-hidden="true"></span>
													</div>
													
												</div>
												
												<div class="row">
													<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 sign_design1 form-group has-feedback required-field-block">
														<form:input path="securityanswer2" class="form-control has-feedback-left" placeholder="Security Answer" maxlength="50"  required="required" />
														<span class="fa fa-user form-control-feedback left" aria-hidden="true"></span>
													</div>
													
												</div>
												
												
												
												
												<!-- <div class="row">
													<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 signup_btn2 text-center" style="height: 70px;">
														<div id="errorMsg" style="font-size:14px;font-family:arial;font-weight:bold;color:white;"></div>
														
																									
													</div>
												</div> -->
												

												<div class="row">
													<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 signup_btn2 text-center" style="height: 70px;">
														<button type="button" id="resetBtn" class="btn-lg btn-primary">
														<span class="glyphicon glyphicon-thumbs-up"></span> Submit</button>
													</div>
												</div>
												
												<div class="row">
													<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 text-center signuplink">
														Do you have account? <a href="${pageContext.request.contextPath}/login">Login</a>
													</div>
												</div>												
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	 <!-- Small modal -->
                  <button type="button" id="buttonalert" class="btn btn-primary" data-toggle="modal" data-target=".bs-example-modal-lg" style="visibility:hidden">Small modal</button>

                  <div class="modal fade bs-example-modal-lg" tabindex="-1" role="dialog" aria-hidden="true">
                    <div class="modal-dialog modal-lg">
                      <div class="modal-content">

                        
                        <div class="modal-body text-center">
                          <h4><spring:eval expression="@hipaaConfigurer.getProperty(\'error.resetPassword\')" /></h4>
                        </div>
                        <div class="modal-footer">
                          <button type="button" id="closeDialog" class="btn btn-primary" data-dismiss="modal">Ok</button>
                        </div>

                      </div>
                    </div>
                  </div>
                  
	
	<form:hidden path="userId"/>
	<form:hidden path="userName"/>
	<form:hidden path="userType"/>
	<form:hidden path="userEmailId"/>
	<form:hidden path="rememberMe"/>
	
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
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/pages/HipaaPasswordValidator.js?v=2"></script>
	
</form:form>

