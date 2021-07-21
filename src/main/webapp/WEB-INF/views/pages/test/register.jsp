<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/pages/test/register.js?v=1" ></script>
	
	<form id="regForm" modelAttribute="user" action="registerProcess" method="post" style="padding: 20px;">
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
									<div class="col-xs-12 col-sm-12 col-md-12 col-lg-offset-2 col-lg-9 ">
										<div class="row">
											<div class="col-xs-12 col-sm-12 col-md-offset-1 col-md-10 col-lg-12">
												<div class="container signupArea2">
													<div class="row">
														<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 sign_head13 bottomGap headunderline text-center">
															Physician Sign Up
														</div>
													</div>

													<div class="row">
														<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">

															<div class="row">

																<div class="col-xs-12 col-sm-12 col-md-6 col-lg-6 sign_design1 form-group has-feedback required-field-block topGap">
																	<input type="text" id="first-name"  placeholder="First Name" class="form-control has-feedback-left" required="required">
																	<span class="fa fa-user form-control-feedback left" aria-hidden="true"></span>
																</div>
																<div class="col-xs-12 col-sm-12 col-md-6 col-lg-6 sign_design1 form-group has-feedback required-field-block topGap">
																	<input type="text" id="last-name" placeholder="Last Name" class="form-control has-feedback-left" required="required">
																	<span class="fa fa-user form-control-feedback left" aria-hidden="true"></span>
																</div>
															</div>



															<div class="row">
																<div class="col-xs-12 col-sm-12 col-md-6 col-lg-6 sign_design1 form-group has-feedback required-field-block">
																	<input type="email" id="email" placeholder="Email" class="form-control has-feedback-left" required="required">
																	<span class="fa fa-envelope form-control-feedback left" aria-hidden="true"></span>
																</div>
																<div class="col-xs-12 col-sm-12 col-md-6 col-lg-6 sign_design1 form-group has-feedback required-field-block">
																	<input type="email" id="cemail" placeholder="Confirm Email" class="form-control has-feedback-left" required="required">
																	<span class="fa fa-envelope form-control-feedback left" aria-hidden="true"></span>
																</div>
															</div>



															<div class="row">
																<div class="col-xs-12 col-sm-12 col-md-6 col-lg-6 sign_design1 form-group has-feedback required-field-block">
																	<input type="password" id="Password" placeholder="Password" class="form-control has-feedback-left" required="required">
																	<span class="fa fa-gear form-control-feedback left" aria-hidden="true"></span>

																</div>
																<div class="col-xs-12 col-sm-12 col-md-6 col-lg-6 sign_design1 form-group has-feedback required-field-block">
																	<input type="password" id="cpassword" placeholder="Confirm Password" class="form-control has-feedback-left" required="required">
																	<span class="fa fa-gear form-control-feedback left" aria-hidden="true"></span>

																</div>
															</div>

															<div class="row">
																<div class="col-xs-12 col-sm-12 col-md-6 col-lg-6 sign_design1 form-group has-feedback">
																	<input type="text" id="phone" placeholder="Phone" data-mask="999-999-999"  class="form-control has-feedback-left">
																	<span class="fa fa-phone form-control-feedback left" aria-hidden="true"></span>
																</div>
																<div class="col-xs-12 col-sm-12 col-md-6 col-lg-6 sign_design1 form-group has-feedback">
																	<input type="text" id="mobile" placeholder="Mobile" class="form-control has-feedback-left" required="required">
																	<span class="fa fa-mobile form-control-feedback left" aria-hidden="true"></span>
																</div>
															</div>



															<!--  Address and City textboxes -->

															<div class="row">
																<div class="col-xs-12 col-sm-12 col-md-4 col-lg-4 sign_design1 form-group has-feedback">
																	<input type="text" id="city" placeholder="City" class="form-control has-feedback-left" required="required">
																	<span class="fa fa-home form-control-feedback left" aria-hidden="true"></span>
																</div>
																<div class="col-xs-12 col-sm-12 col-md-4 col-lg-4 sign_design1 form-group has-feedback required-field-block">
																	<select class="select2_single form-control has-feedback-left " id="state" required="required">
																		<option value="">State</option>
																		<option value="AK">Alaska</option>
																		<option value="HI">Hawaii</option>
																		<option value="CA">California</option>
																		<option value="NV">Nevada</option>
																		<option value="OR">Oregon</option>
																		<option value="WA">Washington</option>
																		<option value="AZ">Arizona</option>
																		<option value="CO">Colorado</option>
																		<option value="ID">Idaho</option>
																		<option value="MT">Montana</option>
																		<option value="NE">Nebraska</option>
																		<option value="NM">New Mexico</option>
																		<option value="ND">North Dakota</option>
																		<option value="UT">Utah</option>
																		<option value="WY">Wyoming</option>
																		<option value="AR">Arkansas</option>
																		<option value="IL">Illinois</option>
																		<option value="IA">Iowa</option>
																		<option value="KS">Kansas</option>
																		<option value="KY">Kentucky</option>
																		<option value="LA">Louisiana</option>
																		<option value="MN">Minnesota</option>
																		<option value="MS">Mississippi</option>
																		<option value="MO">Missouri</option>
																		<option value="OK">Oklahoma</option>
																		<option value="SD">South Dakota</option>
																		<option value="TX">Texas</option>
																	  </select>
																	<span class="fa fa-arrows form-control-feedback left" aria-hidden="true"></span>

																</div>
																<div class="col-xs-12 col-sm-12 col-md-4 col-lg-4 sign_design1 form-group has-feedback required-field-block">
																	<input type="text" id="zip" placeholder="Zip Code" class="form-control has-feedback-left" required="required">
																	<span class="fa fa-tasks form-control-feedback left" aria-hidden="true"></span>

																</div>
															</div>


															<div class="row">
																<div class="col-xs-offset-1 col-xs-10 col-sm-offset-1 col-sm-10 col-md-offset-1 col-md-10 col-lg-offset-1 col-lg-10 signup_btn2 text-center">
																	<a href="#" class="btn-lg btn-primary btn-primary"><span class="glyphicon glyphicon-thumbs-up"></span> Signup Now</a>
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
				    </div>
				</div>
				</form>
  <!-- /page content -->

		

