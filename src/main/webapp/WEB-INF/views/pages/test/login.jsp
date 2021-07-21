<%@ include file="../../../layout/taglib.jsp" %>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/pages/test/login.js?v=1" ></script>
 <form:form name="submitForm" method="POST"  modelAttribute="loginForm" action="checkLogin">
        <!-- page content -->
	<div class="row">
					<div class="col-md-4 col-sm-12 col-xs-12">
						<!--<div class="imageArea ">
                   			<img src="../build/images/Sign_up_Now.jpeg" alt="..." >
						</div>-->
					</div>
				    <div class="col-md-8 col-sm-12 col-xs-12"  style="float:left;padding:0px;background:transparent">
						<div style="float:left;padding:0px" >
			            	<div  style="float:left;padding:0px">

				        		<div class="row" style="float:left;padding:0px">
									<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 ">
										<div class="row">
											<div class="col-xs-12 col-sm-12 col-md-offset-1 col-md-10 col-lg-12">
												<div class="container signupArea2">
													<div class="row">
														<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 sign_head13 bottomGap headunderline text-center">
															 <h4 class="modal-title fontColor">Login with your account</h4>
														</div>
													</div>

													<div class="row">
														<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">


															<c:if test="${not empty error}">
																<div class="row">
																	<div class="col-sm-offset-1 col-sm-10 errorMsg">
																		<div class="alert alert-danger fade in">
																		    <a href="#" class="close" data-dismiss="alert">&times;</a>
																		    <strong>Error!</strong> ${error}
																		</div>											
																	</div>
																</div>
															</c:if>


															<div class="row">

																<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 sign_design1 form-group has-feedback required-field-block topGap">
																	<form:input path="username"  id="username" placeholder="Username" class="form-control has-feedback-left" required="required"/>
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
																<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 sign_design1 form-group has-feedback required-field-block">
																	<form:select path="type" id="type" placeholder="Select" class="select2_single form-control has-feedback-left"  required="required">
																		  <form:option value="" label="Select" />
																		  <form:option value="Super Admin" label="Super Admin" />
																		  <form:option value="Admin" label="Admin" />
																		  <form:option value="Physician" label="Physician" />
																		  <form:option value="Physician Assistant" label="Physician Assistant" />
																	</form:select>
																	<span class="fa fa-users form-control-feedback left" aria-hidden="true"></span>
																</div>
															</div>

															<div class="row">
																<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 sign_design1 form-group has-feedback required-field-block text-center">
																	<input type="submit" id="btn-login" class="btn-lg btn-primary btn-primary" value="Login">
																</div>
															</div>
															
															
															<div class="row">
																<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 text-center signuplink">
																	New user? <a href="physician/PhysicianRegistration">Physician Registration</a>
																</div>
															</div>
															
															<%-- <div class="row">
																<div class="col-xs-12 col-xs-offset-2">
																	<div style="color:white"><b>${error}</b></div>
																</div>
															</div> --%>
															
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
				    </div>
				</div>
				</form:form>
  <!-- /page content -->

