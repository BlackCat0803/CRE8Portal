<%--
Front End UI prompts the user to enter the login credential details
 --%>
<%@ include file="../../layout/taglib.jsp"%>
<%--includes the Front End UI validations--%>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/pages/login.js?v=1"></script>
<form:form name="submitForm" method="POST" modelAttribute="loginForm"
	action="checkLogin">
	<!-- page content -->
	<div class="row">
		<div
			class="col-lg-offset-5 col-lg-5 col-md-offset-7 col-md-5 col-sm-12 col-xs-12"
			style="float: middle; padding: 0px; background: transparent">
			<div class="row" style="padding: 0px">
				<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 ">
					<div class="row">
						<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
							<div class="signupArea2">
								<c:if test="${not empty error}">
									<div class="row">
										<div class="col-sm-12 fontSize14 text-center">
											<div class="alert alert-danger fade in">
												<a href="#" class="close" data-dismiss="alert">&times;</a>${error}
											</div>
										</div>
									</div>
								</c:if>
								<div class="row">
									<div
										class="col-xs-12 col-sm-12 col-md-12 col-lg-12 sign_head13 bottomGap headunderline text-center">
										<h4 class="modal-title fontColor">Login</h4>
									</div>
								</div>
								<div class="row">
									<div
										class="col-xs-12 col-sm-12 col-md-12 col-lg-7 col-lg-offset-3">
										<div class="row">
											<div
												class="col-xs-12 col-sm-12 col-md-12 col-lg-12 sign_design1 form-group has-feedback required-field-block topGap">
												<form:input path="userName" id="userName"
													placeholder="Email" class="form-control has-feedback-left"
													required="required" />
												<span class="fa fa-user form-control-feedback left"
													aria-hidden="true"></span>
											</div>
										</div>
										<div class="row">
											<div
												class="col-xs-12 col-sm-12 col-md-12 col-lg-12 sign_design1 form-group has-feedback required-field-block">
												<form:input path="password" type="password" id="password"
													placeholder="Password"
													class="form-control has-feedback-left" required="required" />
												<span class="fa fa-unlock form-control-feedback left"
													aria-hidden="true"></span>
											</div>
										</div>
										<div class="row">
											<div
												class="col-xs-12 col-sm-12 col-md-12 col-lg-12 sign_design1 form-group has-feedback required-field-block">
												<form:select path="type" id="type" placeholder="Select"
													class="select2_single form-control has-feedback-left"
													required="required">
													<form:option value="" label="Select User Type" />
													<form:option value="Administrator"
														label="Pharmacy Administrator" />
													<form:option value="Physician" label="Physician" />
													<form:option value="Physician Assistant"
														label="Physician Assistant" />
													<form:option value="Group Director" label="Group Director" />
												</form:select>
												<span class="fa fa-users form-control-feedback left"
													aria-hidden="true"></span>
											</div>
										</div>

										<div class="row">
											<div
												class="col-md-12 col-sm-12 col-xs-12 profile_left checkboxDiv">
												<div class="clearfix">
													<div class="checkboxbox">
														<form:checkbox path="rememberMe" value="Yes" />
													</div>
													<div class="checkboxValue">Remember me</div>
												</div>
											</div>
										</div>

										<div class="row">
											<div
												class="col-xs-12 col-sm-12 col-md-12 col-lg-12 sign_design1 form-group has-feedback required-field-block text-center">
												<input type="submit" id="btn-login"
													class="btn-lg btn-primary btn-primary" value="Login">
											</div>
										</div>
										<div class="row">
											<div
												class="col-xs-12 col-sm-6 col-md-6 col-lg-6 text-center signuplink">
												<a href="forgotPassword" style="color: white;"
													title="Forgot Password?">Forgot Password?</a>
											</div>

											<div
												class="col-xs-12 col-sm-6 col-md-6 col-lg-6 text-left signuplink">
												<a href="physician/PhysicianRegistration"
													title="Physician Registration">Physician Registration</a>
											</div>
										</div>
										<br>
										<br>
									</div>
								</div>
							</div>
						</div>
					</div>

					<c:if test="${not empty loginUserList}">
						<div class="row">
							<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 ">
								<div class="signupArea2">
									<div class="row">
										<div
											class="col-xs-12 col-sm-12 col-md-12 col-lg-12 sign_head13 bottomGap headunderline text-center">
											<h4 class="modal-title fontColor">Recent logins</h4>
										</div>
									</div>
									<c:forEach items="${loginUserList}" var="u">
										<div class="row">
											<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 linkFont">
												<span>${u}</span> <img class="img-responsive delete-cookies"
													title="Remove"
													src="${pageContext.request.contextPath}/resources/images/del-cookies1.png"
													alt="" />
											</div>
										</div>
									</c:forEach>

									<center>
										<span class="copyright" style="color: white">2017
											&#9400; Copyright - CRE8 Pharmacy Group LLC. </span>
									</center>
								</div>
							</div>
						</div>
					</c:if>
				</div>
			</div>
		</div>
	</div>
	<input type="hidden" id="dropValue" value="${dropValue}" />
	<form:hidden path="tmpUserName" />
	<form:hidden path="tmpType" />

</form:form>
<!-- /page content -->
