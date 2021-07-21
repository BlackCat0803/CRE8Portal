<%--
Front End UI prompts the user to enter the patient login credential details
 --%>
<%@ include file="../../layout/taglib.jsp" %>
<%--includes the Front End UI validations--%>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/pages/patientLogin.js?v=3" ></script>
 <form:form name="submitForm" method="POST"  modelAttribute="loginForm" action="checkLogin">
        <!-- page content -->
	<div class="row">
					<div class="col-lg-offset-5 col-lg-5 col-md-offset-7 col-md-5 col-sm-12 col-xs-12" style="float: middle; padding: 0px; background: transparent">
								<div class="row" style="padding:0px">
									<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 ">
										<div class="row">
											<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
												<div class="container signupArea2">
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
															 <h4 class="modal-title fontColor">Login</h4>
														</div>
													</div>

													<div class="row">
														<div class="col-xs-12 col-sm-12 col-md-12 col-lg-7 col-lg-offset-3">

															<div class="row">

																<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 sign_design1 form-group has-feedback required-field-block topGap">
																	<form:input path="userName"  id="userName" placeholder="Username" class="form-control has-feedback-left" required="required"/>
																	<span class="fa fa-user form-control-feedback left" aria-hidden="true"></span>
																</div>
															</div>

															<div class="row">

																<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 sign_design1 form-group has-feedback required-field-block">
																	<form:input path="password"  type="password" id="password" placeholder="Password" class="form-control has-feedback-left" required="required"/>
																	<span class="fa fa-unlock form-control-feedback left" aria-hidden="true"></span>
																</div>
															</div>

															<div class="row">
																<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 sign_design1 form-group has-feedback required-field-block text-center">
																	<input type="submit" id="btn-login" class="btn-lg btn-primary btn-primary" value="Login">
																</div>
															</div>
															
															<div class="row">
																<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 sign_design1 form-group has-feedback required-field-block text-center">
																	<a href="patientforgotPassword" style="color:white;">Forgot Password?</a>
																</div>
															</div>
															
															<br><br>

														</div>
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
							
				    </div>
				</div>
				<form:hidden path="type" value="Patient"/>
				</form:form>
  <!-- /page content -->

