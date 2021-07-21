<%--
Front End UI prompts the user to get the forgottten password
 --%>
<%@ include file="../../layout/taglib.jsp" %>
<%--includes the Front End UI validations--%>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/pages/forgotPassword.js?v=3"></script>
<c:url var="home" value="/" scope="request" />
<form:form method="POST" action="validateUserSecurityQuestionsandAnswers" name="forgotPasswordForm" commandName="forgotPasswordForm"  >
	<div class="row">
		
		<div class="col-lg-offset-5 col-lg-5 col-md-offset-7 col-md-5 col-sm-12 col-xs-12" style="float: middle; padding: 0px; background: transparent">
		
					<div class="row" style="padding: 0px">
						<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 ">
							<div class="row">
								<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
									<div class="signupArea2">
										 
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
									
										<div class="row">
											<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 sign_head13 bottomGap headunderline text-center">
												Forgot Password
											</div>
										</div>
										<div class="row">
											<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 ">
												<c:choose>
													<c:when test="${not empty showsecurityques}">
														
														
														<c:choose>
															<c:when test="${not empty showusertype}">
																<div class="row">
																	<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 sign_design1 form-group has-feedback required-field-block topGap">
																	<form:input path="email" class="form-control has-feedback-left" placeholder="Email" maxlength="250" readonly="true" />
																	<span class="fa fa-user form-control-feedback left" aria-hidden="true"></span>
																	</div>
																</div>
																<div class="row" style="display:none">
																		<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 sign_design1 form-group has-feedback required-field-block">
																	
																	
																			<form:select path="type" id="type" placeholder="Select" class="select2_single form-control has-feedback-left" cssStyle="pointer-events:none;" >
																				  <form:option value="" label="Select" />
																				  <form:option value="Administrator" label="Pharmacy Administrator" />
																				  <form:option value="Physician" label="Physician" />
																				  <form:option value="Physician Assistant" label="Physician Assistant" />
																				  <form:option value="Group Director" label="Group Director" />
																			</form:select>
																			
																			
																			<span class="fa fa-users form-control-feedback left" aria-hidden="true"></span>
		
																		</div>
																				
																</div>
																
															</c:when>
															<c:otherwise>
															
															
															
																<div class="row">
																	<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 sign_design1 form-group has-feedback required-field-block topGap">
																		<form:input path="username" class="form-control has-feedback-left" placeholder="Username" maxlength="250"/>
																		<span class="fa fa-user form-control-feedback left" aria-hidden="true"></span>
																	</div>
																</div>
															</c:otherwise>
														</c:choose>
													
													</c:when>
													<c:otherwise>
													
														
														<c:choose>
														<c:when test="${not empty showusertype}">
															<div class="row">
																<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 sign_design1 form-group has-feedback required-field-block topGap">
																<form:input path="email" class="form-control has-feedback-left" placeholder="Email" maxlength="250"/>
																<span class="fa fa-user form-control-feedback left" aria-hidden="true"></span>
																</div>
															</div>
															<div class="row">
																			<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 sign_design1 form-group has-feedback required-field-block">
																		
																		
																				<form:select path="type" id="type" placeholder="Select" class="select2_single form-control has-feedback-left" >
																					  <form:option value="" label="Select" />
																					  <form:option value="Administrator" label="Pharmacy Administrator" />
																					  <form:option value="Physician" label="Physician" />
																					  <form:option value="Physician Assistant" label="Physician Assistant" />
																					  <form:option value="Group Director" label="Group Director" />
																				</form:select>
																				
																				
																				<span class="fa fa-users form-control-feedback left" aria-hidden="true"></span>
			
																			</div>
																			
															</div>
														</c:when>
														<c:otherwise>
														
															<div class="row">
																<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 sign_design1 form-group has-feedback required-field-block topGap">
																	<form:input path="username" class="form-control has-feedback-left" placeholder="Username" maxlength="250"/>
																	<span class="fa fa-user form-control-feedback left" aria-hidden="true"></span>
																</div>
															</div>
														</c:otherwise>
														</c:choose>
													</c:otherwise>
												</c:choose>
								
												
												<c:if test="${not empty showsecurityques}">
													<div class="row">
														<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 sign_design1 form-group has-feedback required-field-block">
															Please answer the following Security Question(s):
														</div>
													</div>
													
													<div class="row">
														
														<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 sign_design1 form-group has-feedback required-field-block">
															<form:select path="securityquestion" class="select2_single form-control has-feedback-left " id="securityquestion"  required="required"  cssStyle="pointer-events:none;">
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
															<form:select path="securityquestion2" class="select2_single form-control has-feedback-left " id="securityquestion2"  required="required"  cssStyle="pointer-events:none;">
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
												</c:if>
												
												<div class="row">
													<div class="col-sm-offset-1 col-sm-10">
														<div id="error" style="font-size:14px;font-family:arial;font-weight:bolder;"></div>
													</div>
												</div>
												

												<div class="row">
													<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 signup_btn2 text-center" style="height: 70px;">
														<button type="button" id="forgotBtn" class="btn-lg btn-primary" onclick="submitForm()">
														<span class="glyphicon glyphicon-thumbs-up"></span> Submit</button>
													</div>
												</div>
												
												<c:if test="${not empty notshowusertype}">
													<div class="row">
														<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 text-center signuplink">
															Back to <a href="${pageContext.request.contextPath}/patientlogin" title="Patient Login">Login</a>
														</div>
													</div>	
												</c:if>
												<c:if test="${not empty showusertype}">
													<div class="row">
														<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 text-center signuplink">
															Back to <a href="${pageContext.request.contextPath}/login" title="Login">Login</a>
														</div>
													</div>	
												</c:if>
												
												
												
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

                        
                        <div class="modal-body">
                          <h4><spring:eval expression="@forgotPasswordConfigurer.getProperty(\'error.resetPassword\')" /></h4>
                        </div>
                        <div class="modal-footer">
                          <button type="button" class="btn btn-primary" data-dismiss="modal">Ok</button>
                        </div>

                      </div>
                    </div>
                  </div>

		<c:if test="${not empty notshowusertype}">
			<form:hidden path="type" value="Patient"/>
		</c:if>
	<!-- Error messages getting from property file -->
	<input type="hidden" id="error_oldpasswordreused"  name="error_oldpasswordreused" value='<spring:eval expression="@forgotPasswordConfigurer.getProperty(\'error.oldpasswordreused\')" />' />
	<input type="hidden" id="error_resetPassword"  name="error_resetPassword" value='<spring:eval expression="@forgotPasswordConfigurer.getProperty(\'error.resetPassword\')" />' />
	<input type="hidden" id="error_newPasswordNotSameasOldPassword"  name="error_newPasswordNotSameasOldPassword" value='<spring:eval expression="@forgotPasswordConfigurer.getProperty(\'error.newPasswordNotSameasOldPassword\')" />' />
	<input type="hidden" id="showsecurityques" name="showsecurityques" value="${showsecurityques}"/>
	<input type="hidden" id="error_email_format" name="error_email_format" value='<spring:eval expression="@forgotPasswordConfigurer.getProperty(\'error.email_format\')" />'/>
	<input type="hidden" id="error_email" name="error_email" value='<spring:eval expression="@forgotPasswordConfigurer.getProperty(\'error.email\')" />'/>
	<input type="hidden" id="error_usertype" name="error_usertype" value='<spring:eval expression="@forgotPasswordConfigurer.getProperty(\'error.usertype\')" />'/>
	<input type="hidden" id="error_securityQuestion1" name="error_securityQuestion1" value='<spring:eval expression="@forgotPasswordConfigurer.getProperty(\'error.securityQuestion1\')" />'/>
	<input type="hidden" id="error_securityAnswer1" name="error_securityAnswer1" value='<spring:eval expression="@forgotPasswordConfigurer.getProperty(\'error.securityAnswer1\')" />'/>
	<input type="hidden" id="error_securityQuestion2" name="error_securityQuestion2" value='<spring:eval expression="@forgotPasswordConfigurer.getProperty(\'error.securityQuestion2\')" />'/>
	<input type="hidden" id="error_securityAnswer2" name="error_securityAnswer2" value='<spring:eval expression="@forgotPasswordConfigurer.getProperty(\'error.securityAnswer2\')" />'/>
	<input type="hidden" id="error_username" name="error_username" value='<spring:eval expression="@forgotPasswordConfigurer.getProperty(\'error.username\')" />'/>
	
</form:form>

