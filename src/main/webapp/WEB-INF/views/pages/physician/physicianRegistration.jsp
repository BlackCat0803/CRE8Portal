 <%--
 Front End UI to Register the Physician Account details
 --%>
<%@ include file="../../../layout/taglib.jsp" %>
<%--includes the Front End UI validations--%>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.mask/1.14.10/jquery.mask.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/pages/physician/physicianRegistration.js?v=3"></script>

<form:form method="POST" action="SavePhysicianRegistration" name="physicianAccount" commandName="physicianAccount"  >
	<div class="row">
		<div class="col-lg-offset-4 col-lg-8 col-md-offset-4 col-md-8 col-sm-12 col-xs-12" style="float: left; padding: 0px; background: transparent">
			<div style="float: left; padding: 0px">
				<div style="float: left; padding: 0px">
					<div class="row" style="float: left; padding: 0px">
						<div class="col-xs-12 col-sm-12 col-md-12 col-lg-offset-2 col-lg-9 ">
							<div class="row">
								<div class="col-xs-12 col-sm-12 col-md-offset-1 col-md-10 col-lg-12">
									<div class="signupArea2">
										 
										<c:if test="${not empty message}">
											<div class="row">
												<div class="col-sm-offset-1 col-sm-10  fontSize14 text-center">
													<div class="alert alert-danger fade in">
													    <a href="#" class="close" data-dismiss="alert">&times;</a>
													    ${message}
													</div>											
												</div>
											</div>
										</c:if>
									
										<div class="row">
											<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 sign_head13 bottomGap headunderline text-center">
												Physician SignUp
											</div>
										</div>
										<div class="row">
											<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
												<div class="row">
													<div class="col-xs-12 col-sm-12 col-md-6 col-lg-6 sign_design1 form-group has-feedback required-field-block topGap">
														<form:input path="firstName" class="form-control has-feedback-left" placeholder="First Name" maxlength="50"  required="required" />
														<span class="fa fa-user form-control-feedback left" aria-hidden="true"></span>
													</div>
													<div class="col-xs-12 col-sm-12 col-md-6 col-lg-6 sign_design1 form-group has-feedback required-field-block topGap">
														<form:input path="lastName" class="form-control has-feedback-left" placeholder="Last Name" maxlength="50"  required="required" />
														<span class="fa fa-user form-control-feedback left" aria-hidden="true"></span>
													</div>
												</div>
												<div class="row">
													<div class="col-xs-12 col-sm-12 col-md-6 col-lg-6 sign_design1 form-group has-feedback required-field-block">
														<form:input path="email" class="form-control has-feedback-left" placeholder="Email" maxlength="255" required="required" />
														<span class="fa fa-envelope form-control-feedback left" aria-hidden="true"></span>
													</div>
													<div class="col-xs-12 col-sm-12 col-md-6 col-lg-6 sign_design1 form-group has-feedback required-field-block">
														<form:input path="confirmEmail" class="form-control has-feedback-left" placeholder="Confirm Email" maxlength="255" required="required" />
														<span class="fa fa-envelope form-control-feedback left" aria-hidden="true"></span>
													</div>
												</div>
												<div class="row">
													<div class="col-xs-12 col-sm-12 col-md-6 col-lg-6 sign_design1 form-group has-feedback required-field-block">
														<form:password path="password"  class="form-control has-feedback-left" placeholder="Password" required="required"  onkeyup="stripHipaaSpChars(true,'~!@#$%^*&;?.+_',true,this);" onblur="stripHipaaSpChars(true,'~!@#$%^*&;?.+_',true,this);"/>
														<span class="fa fa-gear form-control-feedback left" aria-hidden="true"></span>
													</div>
													<div class="col-xs-12 col-sm-12 col-md-6 col-lg-6 sign_design1 form-group has-feedback required-field-block">
														<form:password path="confirmPassword"  class="form-control has-feedback-left" placeholder="Confirm Password" required="required"   onkeyup="stripHipaaSpChars(true,'~!@#$%^*&;?.+_',true,this);" onblur="stripHipaaSpChars(true,'~!@#$%^*&;?.+_',true,this);"/>
														<span class="fa fa-gear form-control-feedback left" aria-hidden="true"></span>
													</div>
												</div>
												
												<!-- <div class="row">
													<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 sign_design1 form-group has-feedback required-field-block">
														<label id="passwordTxtLbl" style="width:500px;color:white;text-align: center;"></label>
													</div>
												</div> -->

												<div class="row">
													<div class="col-xs-12 col-sm-12 col-md-6 col-lg-6 sign_design1 form-group has-feedback">
														<form:input path="phone" class="form-control has-feedback-left" placeholder="Phone" maxlength="15" onkeypress="return isNumber(event)" required="required" />
														<span class="fa fa-phone form-control-feedback left" aria-hidden="true"></span>
													</div>
													<div class="col-xs-12 col-sm-12 col-md-6 col-lg-6 sign_design1 form-group has-feedback">
														<form:input path="mobile" class="form-control has-feedback-left" placeholder="Mobile" maxlength="15" onkeypress="return isNumber(event)" />
														<span class="fa fa-mobile form-control-feedback left" aria-hidden="true"></span>
													</div>
												</div>

												<div class="row">
													<div class="col-xs-12 col-sm-12 col-md-4 col-lg-4 sign_design1 form-group has-feedback">
														<form:input path="city" class="form-control has-feedback-left" placeholder="City" maxlength="25" required="required" />
														<span class="fa fa-home form-control-feedback left" aria-hidden="true"></span>
													</div>
													<div class="col-xs-12 col-sm-12 col-md-4 col-lg-4 sign_design1 form-group has-feedback required-field-block">
														<form:select path="state" class="select2_single form-control has-feedback-left " id="state" required="required" >
															<form:option value="">Select</form:option>
															<form:options items="${usStates}" itemLabel="stateName" itemValue="stateCode"/>
														</form:select>
														<span class="fa fa-arrows form-control-feedback left" aria-hidden="true"></span>
													</div>
													<div class="col-xs-12 col-sm-12 col-md-4 col-lg-4 sign_design1 form-group has-feedback required-field-block">
														<form:input path="zipCode" class="form-control has-feedback-left" placeholder="Zip Code" maxlength="12"  onkeypress="return isNumber(event)" />
														<span class="fa fa-tasks form-control-feedback left" aria-hidden="true"></span>
													</div>
												</div>

												<div class="row">
													<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 signup_btn2 text-center" style="height: 70px;">
														<button type="submit" id="signupBtn" class="btn-lg btn-primary">
														<span class="glyphicon glyphicon-thumbs-up"></span> Signup Now</button>
													</div>
												</div>
												
												<div class="row">
													<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 text-center signuplink">
														Do you have account? <a href="${pageContext.request.contextPath}/login?s=1" title="Login">Login</a>
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
	
	<!-- Error messages getting from property file -->
	<input type="hidden" id="err_fname" value='<spring:eval expression="@physicianConfigurer.getProperty(\'error.first_name\')" />' />
	<input type="hidden" id="err_lname" value='<spring:eval expression="@physicianConfigurer.getProperty(\'error.last_name\')" />' />
	<input type="hidden" id="err_email" value='<spring:eval expression="@physicianConfigurer.getProperty(\'error.email\')" />' />
	<input type="hidden" id="err_email_format" value='<spring:eval expression="@physicianConfigurer.getProperty(\'error.email_format\')" />' />
	<input type="hidden" id="err_email_and_conf" value='<spring:eval expression="@physicianConfigurer.getProperty(\'error.email_and_confirm\')" />' />
	<input type="hidden" id="err_conf_email" value='<spring:eval expression="@physicianConfigurer.getProperty(\'error.confirm_email\')" />' />
	<input type="hidden" id="err_conf_email_format" value='<spring:eval expression="@physicianConfigurer.getProperty(\'error.confirm_email_format\')" />' />
	<input type="hidden" id="err_pwd" value='<spring:eval expression="@physicianConfigurer.getProperty(\'error.password\')" />' />
	<input type="hidden" id="err_pwd_conf" value='<spring:eval expression="@physicianConfigurer.getProperty(\'error.password_and_confirm_password\')" />' />
	<input type="hidden" id="err_conf_pwd" value='<spring:eval expression="@physicianConfigurer.getProperty(\'error.confirm_password\')" />' />
	
	<input type="hidden" id="err_phone" value='<spring:eval expression="@physicianConfigurer.getProperty(\'error.phone\')" />' />
	<input type="hidden" id="err_mobile" value='<spring:eval expression="@physicianConfigurer.getProperty(\'error.mobile\')" />' />

	<input type="hidden" id="err_city" value='<spring:eval expression="@physicianConfigurer.getProperty(\'error.city\')" />' />
	<input type="hidden" id="err_state" value='<spring:eval expression="@physicianConfigurer.getProperty(\'error.state\')" />' />
	<input type="hidden" id="err_zip_format" value='<spring:eval expression="@physicianConfigurer.getProperty(\'error.zip_format\')" />' />
	<input type="hidden" id="err_phone_format" value='<spring:eval expression="@physicianConfigurer.getProperty(\'error.phone_format\')" />' />
	<input type="hidden" id="err_mobile_format" value='<spring:eval expression="@physicianConfigurer.getProperty(\'error.mobile_format\')" />' />
	
	
	
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
