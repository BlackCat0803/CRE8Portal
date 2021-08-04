 <%--
 Front End UI to save and load the Physician Profile details
 --%>
<%@ include file="../../../layout/taglib.jsp" %>
<%--includes the Front End CSS and UI validations--%>
<link href="${pageContext.request.contextPath}/resources/css/smartWizard.css" rel="stylesheet" />
<link href="${pageContext.request.contextPath}/resources/css/popup.css?v=2" rel="stylesheet" />
<script src="${pageContext.request.contextPath}/resources/js/jquery.selectlistactions.js?v=1"></script>
<link href="${pageContext.request.contextPath}/resources/css/selectlistactions.css" rel="stylesheet" />
<script src="${pageContext.request.contextPath}/resources/js/esignature.js?v=6"></script>
<script>
var physicianStep=1;
</script>
<form:form method="POST" action="SavePhysicianProfile" name="physicianAccount" commandName="physicianAccount" enctype="multipart/form-data"  >
		<c:if test="${loginDetail.type == 'Admin' or loginDetail.type == 'Super Admin'  or loginDetail.type == 'Group Director'}">
			<div class="row">
				<c:choose>
					<c:when test="${not empty physicianAccount.prescriptionId}">
						<div class="col-md-2 col-sm-4 col-xs-4">
							<img class="img-responsive goSummary" src="${pageContext.request.contextPath}/resources/images/back_icon-3.png" title="Go to Summary">
						</div>
						<div class="col-md-4 col-sm-4 col-xs-4">
							<button type="button" class="btn btn-primary backPrescription" >Back to Prescription</button>
						</div>
					</c:when>
					<c:otherwise>
						<div class="col-md-6 col-sm-4 col-xs-4">
							<img class="img-responsive goSummary" src="${pageContext.request.contextPath}/resources/images/back_icon-3.png" title="Go to Summary">
						</div>
					</c:otherwise>
				</c:choose>
				<div class="col-md-3 col-sm-6 col-xs-6">
					<c:choose>
			      		<c:when test="${(loginDetail.type == 'Administrator' or loginDetail.type == 'Super Admin' or loginDetail.type == 'Admin')}">
					      	<c:if test="${(loginDetail.physicianCreationPermission =='Yes')}">
					      		<button type="button" class="btn btn-primary goNewRec" >Create New Physician</button>
					      	</c:if>
			      		</c:when>
			      		<c:otherwise>
			      			<button type="button" class="btn btn-primary goNewRec" >Create New Physician</button>
			      		</c:otherwise>
			    	</c:choose>
				</div>
				
				<div class="col-md-3 col-sm-6 col-xs-6">
					<c:choose>
				    	<c:when test="${(loginDetail.type == 'Administrator' or loginDetail.type == 'Super Admin' or loginDetail.type == 'Admin')}">
					      	<c:if test="${(loginDetail.physicianCreationPermission =='Yes')}">
					      		<button type="button"  class="btn btn-success createNewClinic">Create Physician's Clinic</button>
					      	</c:if>
				      	</c:when>
				      	<c:otherwise>
				      		<button type="button"  class="btn btn-success createNewClinic">Create Physician's Clinic</button>
				      	</c:otherwise>
				    </c:choose>
				</div>
			</div>
		</c:if>

        <!-- page content -->
          <div class="">

			<c:if test="${not empty message}">
				<c:choose>
					<c:when test="${saveStatus == 'true'}">
						<div class="row">
							<div class="col-sm-offset-1 col-sm-10  fontSize14 text-center">
								<div class="alert alert-success fade in">
									<a href="#" class="close" data-dismiss="alert">&times;</a> 
										 &nbsp;&nbsp;&nbsp; ${message}
								</div>
							</div>
						</div>
					</c:when>
					<c:otherwise>
						<div class="row">
							<div class="col-sm-offset-1 col-sm-10  fontSize14 text-center">
								<div class="alert alert-danger fade in">
									<a href="#" class="close" data-dismiss="alert">&times;</a> 
									${message}
								</div>
							</div>
						</div>
					</c:otherwise>
				</c:choose>
			</c:if>



                    <!-- Smart Wizard -->
                   
                    <div id="wizard" class="form_wizard wizard_horizontal">
                      <ul class="wizard_steps anchor">
                        <li>
                          <a href="#step-1" class="selected" isdone="1" rel="1" style="text-decoration: none;" onclick="setButtonEnabled(0)">
                            <span class="step_no">1</span>
                            <span class="step_descr">
                                              Step 1<br>
                                              <small>Fill the details</small>
                                          </span>
                          </a>
                        </li>
                       <%-- <li>
                          <a href="#step-2" class="disabled" isdone="0" rel="2" onclick="setButtonEnabled(1)">
                            <span class="step_no">2</span>
                            <span class="step_descr">
                                              Step 2<br>
                                              <small>Setup Clinic</small>
                                          </span>
                          </a>
                        </li>--%>
                        <li>
                          <a href="#step-2" class="disabled" isdone="0" rel="2" onclick="setButtonEnabled(1)">
                            <span class="step_no">2</span>
                            <span class="step_descr">
                                              Step 2<br>
                                              <small>Upload Licenses</small>
                                          </span>
                          </a>
                        </li>
                        <li>
                          <a href="#step-3" class="disabled" isdone="0" rel="3" onclick="setButtonDisabled(2)">
                            <span class="step_no">3</span>
                            <span class="step_descr">
                                              Step 3<br>
                                              <small>E-Sign Profile PDF</small>
                                          </span>
                          </a>
                        </li>
                      </ul>
                      
                      
                      
                      

             <div class="stepContainer" style="height:auto;">
             <div id="step-1" class="content" style="display: block;">
             	<h2 class="StepTitle">Step 1 Fill the details</h2>
                <div class="row">
            	<div class="col-md-6 col-sm-6 col-xs-12">
            		
            		<div class="x_panel">
	                	<div class="x_title">
	                    	<h2>Physician Profile</h2>
	                    	<div class="clearfix"></div>
	                  	</div>
	                  	<div class="x_content x_title_padding">
							<div class="row">
		                    	<div class="col-md-12 col-sm-12 col-xs-12 profile_left">
									<div class="row">
										<label class="col-md-4 col-sm-3 col-xs-12 control-label required" for="first-name">First Name </label>
		                        		<div class="col-md-8 col-sm-9 col-xs-12 form-group">
											<form:input path="firstName" class="form-control" maxlength="50"  required="required" onkeyup="displayFullname('firstName', 'middleName', 'lastName', 'physicianName' );setPhysicianGroupName()" />
										</div>		                        		
		                      		</div>
									<div class="row">
		                        		<label class="col-md-4 col-sm-3 col-xs-12 control-label" for="middle-name">Middle Name</label>
		                        		<div class="col-md-8 col-sm-9 col-xs-12 form-group">
											<form:input path="middleName" class="form-control" maxlength="10" onkeyup="displayFullname('firstName', 'middleName', 'lastName', 'physicianName' );setPhysicianGroupName()" />
		                        		</div>
		                      		</div>
		                      		<div class="row">
		                        		<label class="col-md-4 col-sm-3 col-xs-12 control-label required" for="lastName">Last Name </label>
		                        		<div class="col-md-8 col-sm-9 col-xs-12 form-group">
											<form:input path="lastName" class="form-control" maxlength="50"  required="required" onkeyup="displayFullname('firstName', 'middleName', 'lastName', 'physicianName' );setPhysicianGroupName()" />
		                        		</div>
		                      		</div>
		                    		<div class="row">
		                        		<label class="col-md-4 col-sm-3 col-xs-12 control-label" for="physicianName">Name </label>
		                        		<div class="col-md-8 col-sm-9 col-xs-12 form-group">
											<form:input path="physicianName" class="form-control fullname" title="${physicianAccount.physicianName}" maxlength="110" readonly="true" />
		                        		</div>
		                      		</div>
		                      		<div class="row">
		                        		<label class="col-md-4 col-sm-3 col-xs-12 control-label required" for="dateRegistrated">Registration Date </label>
		                        		<div class="col-md-8 col-sm-9 col-xs-12 form-group">
											<form:input path="dateRegistrated" class="form-control calendarIcon" disabled="disabled"/>
		                        		</div>
		                      		</div>
									
									
		                      		<div class="row">
		                        		<label class="col-md-4 col-sm-3 col-xs-12 control-label" for="prescriberType">Prescriber Type</label>
		                        		<div class="col-md-8 col-sm-9 col-xs-12 form-group">
											<form:select path="prescriberType" class="select2_single form-control" id="prescriberType"  >
												<form:option value="0">Select</form:option>
												<form:options items="${perscriberList}" itemLabel="prescriberType" itemValue="typeId"/>
											</form:select>
		                        		</div>
		                      		</div>
		                    		<%-- <div class="row">
		                        		<label class="col-md-4 col-sm-3 col-xs-12 control-label" for="prescriberGroup">Prescriber Group</label>
		                        		<div class="col-md-8 col-sm-9 col-xs-12 form-group">
											<form:input path="prescriberGroup" class="form-control" />
		                        		</div>
		                      		</div> --%>
		                      		
		                    		<div class="row">
		                        		<label class="col-md-4 col-sm-3 col-xs-12 control-label" for="physicianNameWithGroupName">Display Name</label>
		                        		<div class="col-md-8 col-sm-9 col-xs-12 form-group">
											<form:input path="physicianNameWithGroupName" class="form-control" title="${physicianAccount.physicianNameWithGroupName}"  readonly="true" />
		                        		</div>
		                      		</div>
	                    		</div>
							</div>
	                  	</div>
					</div>
            		
            		
            		<!-- Primary Location -->
	            	<div class="x_panel">
	                	<div class="x_title">
	                    	<h2>Primary Address</h2>
	                    	<div class="clearfix"></div>
	                  	</div>
	                  	<div class="x_content x_title_padding">
							<div class="row">
		                    	<div class="col-md-12 col-sm-12 col-xs-12 profile_left">
									<div class="row">
		                        		<label class="col-md-4 col-sm-3 col-xs-12 control-label required" for="street1">Street </label>
		                        		<div class="col-md-8 col-sm-9 col-xs-12 form-group">
		                          			<form:input path="address1" class="form-control"  />
		                        		</div>
		                      		</div>
									<div class="row">
		                        		<label class="col-md-4 col-sm-3 col-xs-12 control-label required" for="city1">City </label>
		                        		<div class="col-md-8 col-sm-9 col-xs-12 form-group">
		                          			<form:input path="city" class="form-control" maxlength="25" />
		                        		</div>
		                      		</div>
		                      		<div class="row">
		                        		<label class="col-md-4 col-sm-3 col-xs-12 control-label required" for="state1">State</label>
		                        		<div class="col-md-8 col-sm-9 col-xs-12 form-group">
											<form:select path="state" class="select2_single form-control" id="state" >
												<form:option value="">Select</form:option>
												<form:options items="${usStates}" itemLabel="stateName" itemValue="stateCode"/>
											</form:select>		                          			
		                        		</div>
		                      		</div>
		                    		<div class="row">
		                        		<label class="col-md-4 col-sm-3 col-xs-12 control-label required" for="zip1">Zip Code</label>
		                        		<div class="col-md-8 col-sm-9 col-xs-12 form-group">
		                          			<form:input path="zipCode" class="form-control" maxlength="12"  onkeypress="return isNumber(event)" />
		                        		</div>
		                      		</div>
	                    		</div>
							</div>
	                  	</div>
					</div>

					<!-- Second Location -->
					<%--<div class="x_panel">
	                	<div class="x_title">
	                    	<h2>Secondary Location</h2>
	                    	<div class="clearfix"></div>
	                  	</div>
	                  	<div class="x_content x_title_padding">
							<div class="row">
		                    	<div class="col-md-12 col-sm-12 col-xs-12 profile_left">
									<div class="row">
		                        		<label class="col-md-4 col-sm-3 col-xs-12 control-label " for="street2">Street </label>
		                        		<div class="col-md-8 col-sm-9 col-xs-12 form-group">
		                          			<form:input path="address2" class="form-control"   />
		                        		</div>
		                      		</div>
									<div class="row">
		                        		<label class="col-md-4 col-sm-3 col-xs-12 control-label " for="city2">City </label>
		                        		<div class="col-md-8 col-sm-9 col-xs-12 form-group">
		                          			<form:input path="city2" class="form-control" maxlength="25" />
		                        		</div>
		                      		</div>
		                      		<div class="row">
		                        		<label class="col-md-4 col-sm-3 col-xs-12 control-label" for="state2">State </label>
		                        		<div class="col-md-8 col-sm-9 col-xs-12 form-group">
											<form:select path="state2" class="select2_single form-control" id="state2" >
												<form:option value="">Select</form:option>
												<form:options items="${usStates}" itemLabel="stateName" itemValue="stateCode"/>
											</form:select>		                          			
		                          		</div>
		                      		</div>
		                    		<div class="row">
		                        		<label class="col-md-4 col-sm-3 col-xs-12 control-label" for="zip2">Zip Code</label>
		                        		<div class="col-md-8 col-sm-9 col-xs-12 form-group">
		                          			<form:input path="zipCode2" class="form-control" maxlength="12"  onkeypress="return isNumber(event)" />
		                        		</div>
		                      		</div>
	                    		</div>
							</div>
	                  	</div>
					</div>--%>

					<!-- Phone -->
					<div class="x_panel">
	                	<div class="x_title">
	                    	<h2>Phone Numbers</h2>
	                    	<div class="clearfix"></div>
	                  	</div>
	                  	<div class="x_content x_title_padding">
							<div class="row">
		                    	<div class="col-md-12 col-sm-12 col-xs-12 profile_left">
									<div class="row">
		                        		<label class="col-md-4 col-sm-3 col-xs-12 control-label required" for="primaryPhone">Primary Phone </label>
		                        		<div class="col-md-8 col-sm-9 col-xs-12 form-group">
		                          			<form:input path="phone" class="form-control" maxlength="15" onkeypress="return isNumber(event)"    />
		                        		</div>
		                      		</div>
									<div class="row">
		                        		<label class="col-md-4 col-sm-3 col-xs-12 control-label" for="fax">Primary Fax </label>
		                        		<div class="col-md-8 col-sm-9 col-xs-12 form-group">
		                          			<form:input path="fax" class="form-control" maxlength="15" onkeypress="return isNumber(event)" />
		                          			<form:hidden path="phone2" />
		                          			<form:hidden path="phone3" />
		                        		</div>
		                      		</div>
									<div class="row">
		                        		<label class="col-md-4 col-sm-3 col-xs-12 control-label" for="mobile">Mobile </label>
		                        		<div class="col-md-8 col-sm-9 col-xs-12 form-group">
		                          			<form:input path="mobile" class="form-control" maxlength="15" onkeypress="return isNumber(event)" />
		                        		</div>
		                      		</div>
	                    		</div>
							</div>
	                  	</div>
					</div>
					
            	
					<!-- Email Info -->
	            	<div class="x_panel">
	                  	<div class="x_content x_title_padding">
							<div class="row">
		                    	<div class="col-md-12 col-sm-12 col-xs-12 profile_left">
									<div class="row">
		                        		<label class="col-md-4 col-sm-3 col-xs-12 control-label required" for="email">Email </label>
		                        		<div class="col-md-8 col-sm-9 col-xs-12 form-group">
		                          			<c:choose>
		                          				<c:when test="${loginDetail.type == 'Physician'}">
		                          					<form:input path="email" class="form-control" maxlength="255" title="${physicianAccount.email}" readonly="true" />
		                          				</c:when>
		                          				<c:otherwise>
													<form:input path="email" class="form-control" maxlength="255"  />
		                          				</c:otherwise>
		                          			</c:choose>
		                        		</div>
		                      		</div>
		                      		<div class="row">
		                        		<label class="col-md-4 col-sm-3 col-xs-12 control-label required" for="tempPassword">Password</label>
		                        		<div class="col-md-8 col-sm-9 col-xs-12 form-group">		                          			
		                          			<input type="text" value="${physicianAccount.password}" class="form-control" readonly="readonly" />
		                        		</div>
		                      		</div>
		                      		
									<div class="row">
		                        		<label class="col-md-4 col-sm-3 col-xs-12 control-label" for="website">Web Site </label>
		                        		<div class="col-md-8 col-sm-9 col-xs-12 form-group">
		                          			<form:input path="website" class="form-control" />
		                        		</div>
		                      		</div>
		                      		<div class="row">
		                        		<label class="col-md-4 col-sm-3 col-xs-12 control-label" for="marketer">Marketer </label>
		                        		<div class="col-md-8 col-sm-9 col-xs-12 form-group">
		                          			<form:input path="marketer" class="form-control" />
		                        		</div>
		                      		</div>
		                      		<div class="row" style="height:25px">
		                        		
		                      		</div>
		                      		
	                    		</div>
							</div>
	                  	</div>
					</div>
					
				
				</div>
				
            	<div class="col-md-6 col-sm-6 col-xs-12">
					<div class="x_panel">
	                	
	                  	<div class="x_content x_title_padding">
							<div class="row form-group profile_img">
				            	<div class="col-md-6 col-sm-6 col-xs-12" id="crop-avatar" style="border:1px;border-color:aqua;">
					            	<div class="x_title">
				                    	<h2>Profile Photo</h2>
				                    	<div class="clearfix"></div>
				                  	</div><br>
				                    <c:choose>
                          				<c:when test="${loginDetail.type == 'Physician' and loginDetail.photoUrl != null and loginDetail.photoUrl != ''}">
                          					<img class="img-responsive avatar-view" src="${pageContext.request.contextPath}/resources/${loginDetail.photoUrl}" height="128" alt="Avatar">
                          				</c:when>
                          				<c:when test="${physicianAccount.photoFile != null and physicianAccount.photoFile != ''}">
                          					<img class="img-responsive avatar-view" src="${pageContext.request.contextPath}/resources/${physicianAccount.photoFile}" height="128" alt="Avatar">
                          				</c:when>
                          				<c:otherwise>
											<img class="img-responsive avatar-view" src="${pageContext.request.contextPath}/resources/images/img.jpg" alt="Avatar" >
                          				</c:otherwise>
                          			</c:choose>
								</div>
								<div class="col-md-6 col-sm-6 col-xs-12" id="crop-logo" style="border:1px">
									<div class="x_title">
				                    	<h2>Physician Logo</h2><br>
				                    	<div class="clearfix"></div>
				                  	</div><br>
				                    <c:choose>
                          				<c:when test="${loginDetail.type == 'Physician' and loginDetail.logoUrl != null and loginDetail.logoUrl != ''}">
                          					<img class="img-responsive avatar-view" src="${pageContext.request.contextPath}/resources/${loginDetail.logoUrl}" height="128" alt="Logo" id="phyLogo">
                          				</c:when>
                          				<c:when test="${physicianAccount.logoFile != null and physicianAccount.logoFile != ''}">
                          					<img class="img-responsive avatar-view" src="${pageContext.request.contextPath}/resources/${physicianAccount.logoFile}" height="128" alt="Logo" id="phyLogo">
                          				</c:when>
                          				<c:otherwise>
											<img class="img-responsive avatar-view" src="${pageContext.request.contextPath}/resources/images/default_logo.jpg" alt="Logo" id="phyLogo">
                          				</c:otherwise>
                          			</c:choose>
								</div>
							</div>
							<div class="row form-group" >
			            		<div class="col-md-6 col-sm-6 col-xs-12" id="ProfilePhoto">
			            			<input type="file" id="file" name="uploadPhotoFile" accept="image/gif, image/png"   onchange="checkValues();" />
			            			<div class="file-upload-errors"></div>
								</div>
								<div class="col-md-6 col-sm-6 col-xs-12" id="LogoPhoto">
			            			<input type="file" id="logofile" name="uploadLogoFile" accept="image/gif, image/png"   onchange="checkLogoFileValues();" />
			            			<div class="logofile-upload-errors" style="color: #a94442;font-size: 85%;"></div>
								</div>
							</div>
							
							<div class="row col-md-12 col-sm-12 col-xs-12 photoNotes">
								<label><b>Note:</b> <spring:eval expression="@commonConfigurer.getProperty(\'profile.image.notes\')" /></label>
							</div>
				           <div class="col-md-12 col-sm-12 col-xs-12 checkboxDiv" style="display:block" >
				           		<div class="col-md-12 col-sm-12 col-xs-12"  style="margin-left: 50%;visibility:hidden">
									<div class="clearfix">
										<div class="checkboxbox"  title="Use Group Logo" >
										<form:checkbox path="useGroupLogo" value="Yes" id="useGroupLogo" onclick="setGroupLogo()"/></div>
										<div class="checkboxValue" title="Use Group Logo">Use Group Logo</div>
										<form:hidden path="groupLogoFile" id="groupLogoFile"/>
									</div>
								</div>
							</div>
	                  	</div>
	                  </div>

					<!-- Status -->
					<div class="x_panel" style="display: none">
	                	<div class="x_title">
	                    	<h2>Physician Status</h2>
	                    	<div class="clearfix"></div>
	                  	</div>
	                  	<div class="x_content x_title_padding">
							<div class="row">
		                    	<div class="col-md-12 col-sm-12 col-xs-12 profile_left">
			
									<c:choose>
									
										<c:when	test="${loginDetail.type == 'Administrator' or loginDetail.type == 'Admin' or loginDetail.type == 'Super Admin'}">
											
											<c:choose>
										      <c:when test="${(loginDetail.physicianApprovalPermission =='Yes')}">
											      		<div class="row">
															<label class="col-md-3 col-sm-3 col-xs-12 control-label required" for="status">Status</label>
															<div class="col-md-9 col-sm-9 col-xs-12 form-group">
																<form:select path="status" class="select2_single form-control" onchange="setDisabled(this.value);">
																	<form:option value="New">New</form:option>
																	<form:option value="Profile Completed">Profile Completed</form:option>
																	<form:option value="New Modifications">New Modifications</form:option>
																	<form:option value="Approved">Approved</form:option>
																	<form:option value="Denied">Denied</form:option>
																</form:select>
																<input type="hidden" id="previousStatus" name="previousStatus" value="${physicianAccount.status}" />
															</div>
														</div>
										      </c:when>
										      <c:otherwise>
										      			<c:choose>
										      			<c:when test="${physicianAccount.status != 'New Modifications' and physicianAccount.status != 'Approved' and physicianAccount.status != 'Denied'}">
															<div class="row">
																<label class="col-md-3 col-sm-3 col-xs-12 control-label required" for="status">Status</label>
																<div class="col-md-9 col-sm-9 col-xs-12 form-group">
																	<form:select path="status" class="select2_single form-control" onchange="setDisabled(this.value);">
																		<form:option value="New">New</form:option>
																		<form:option value="Profile Completed">Profile Completed</form:option>
																	</form:select>
																	<input type="hidden" id="previousStatus" name="previousStatus" value="${physicianAccount.status}" />
																</div>
															</div>
														</c:when>
														<c:otherwise>
															<div class="row">
																<label class="col-md-3 col-sm-3 col-xs-12 control-label required" for="status">Status</label>
																<div class="col-md-9 col-sm-9 col-xs-12 form-group">
																	<form:input path="status" class="form-control" readonly="true" />
																</div>
															</div>
														</c:otherwise>
														</c:choose>
										      			
										      </c:otherwise>
										    </c:choose>
		    
											
											
											
											
											<div class="row">
												<label class="col-md-3 col-sm-3 col-xs-12 control-label required" for="groupId">Group</label>
												<div class="col-md-9 col-sm-9 col-xs-12 form-group">
													<c:choose>
														<c:when test="${loginDetail.type != 'Group Director'}">
															<form:select path="groupId" class="select2_single form-control" id="groupId">
																<form:option value="0">Select</form:option>
																<form:options items="${groupList}" itemLabel="groupName" itemValue="id" />
															</form:select>
														</c:when>
														<c:otherwise>
															<input type="text" value="${physicianAccount.groupName}" title="${physicianAccount.groupName}" 
																	class="form-control" readonly="readonly" id="groupName" name="groupName">
															<form:hidden path="groupId" id="groupId" />
														</c:otherwise>
													</c:choose>
												</div>
											</div>
										</c:when>
										<c:otherwise>
											<c:choose>
												<c:when test="${physicianAccount.status != 'New Modifications' and physicianAccount.status != 'Approved' and physicianAccount.status != 'Denied'}">
													<%-- <div class="row">
														<label class="col-md-3 col-sm-3 col-xs-12 control-label required" for="status">Status</label>
														<div class="col-md-9 col-sm-9 col-xs-12 form-group">
															<form:select path="status" class="select2_single form-control" onchange="setDisabled(this.value);">
																<form:option value="New">New</form:option>
																<form:option value="Profile Completed">Profile Completed</form:option>
															</form:select>
															<input type="hidden" id="previousStatus" name="previousStatus" value="${physicianAccount.status}" />
														</div>
													</div> --%>
													<div class="row">
														<label class="col-md-3 col-sm-3 col-xs-12 control-label required" for="group">Group </label>
														<div class="col-md-9 col-sm-9 col-xs-12 form-group">
															<input type="text" value="${physicianAccount.groupName}" title="${physicianAccount.groupName}"
																class="form-control" readonly="readonly" id="groupName" name="groupName">
															<form:hidden path="groupId" id="groupId" />
															<input type="hidden" id="previousStatus" name="previousStatus" value="${physicianAccount.status}" />
															<input type="hidden" id="status" name="status" value="${physicianAccount.status}" />
														</div>
													</div>
												</c:when>
												<c:otherwise>
													<div class="row">
														<label class="col-md-3 col-sm-3 col-xs-12 control-label required" for="status">Status</label>
														<div class="col-md-9 col-sm-9 col-xs-12 form-group">
															<form:input path="status" class="form-control" readonly="true" />
														</div>
													</div>
													<div class="row">
														<label class="col-md-3 col-sm-3 col-xs-12 control-label required" for="group">Group </label>
														<div class="col-md-9 col-sm-9 col-xs-12 form-group">
															<input type="text" value="${physicianAccount.groupName}" title="${physicianAccount.groupName}"
																class="form-control" readonly="readonly" id="groupName" name="groupName">
															<form:hidden path="groupId" id="groupId" />
														</div>
													</div>
												</c:otherwise>
											</c:choose>
										</c:otherwise>
									</c:choose>

									
	                    		</div>
							</div>
	                  	</div>
					</div>


					<!-- License Numbers -->
					<div class="x_panel">
	                	<div class="x_title">
	                    	<h2>License Numbers</h2>
	                    	<div class="clearfix"></div>
	                  	</div>
	                  	<div class="x_content x_title_padding">
							<div class="row">
		                    	<div class="col-md-12 col-sm-12 col-xs-12 profile_left">
									<div class="row">
		                        		<label class="col-md-3 col-sm-3 col-xs-12 control-label required" for="dea">DEA </label>
		                        		<div class="col-md-9 col-sm-9 col-xs-12 form-group">
		                          			<form:input  path="dea"  class="form-control"  maxlength="25"  />
		                        		</div>
		                      		</div>
									<div class="row">
		                        		<label class="col-md-3 col-sm-3 col-xs-12 control-label required" for="state">State License # </label>
		                        		<div class="col-md-9 col-sm-9 col-xs-12 form-group">
		                          			<form:input path="stateLicense" class="form-control" maxlength="25" />
		                        		</div>
		                      		</div>
									<div class="row">
		                        		<label class="col-md-3 col-sm-3 col-xs-12 control-label required" for="npi">NPI </label>
		                        		<div class="col-md-9 col-sm-9 col-xs-12 form-group">
		                          			<form:input path="npi" class="form-control" maxlength="25"  />
		                          			<form:hidden path="upin" />
		                          			<form:hidden path="medicaid" />
		                          			<form:hidden path="dps" />
		                          			
		                          			<form:hidden path="sendMailPermission" />
		                          			<form:hidden path="commFax" />
		                          			<form:hidden path="reqBeforeDays" />
		                        		</div>
		                      		</div>
	                    		</div>
							</div>
	                  	</div>
					</div>

					<!--  CRE8 Notification -->
					<div class="x_panel">
	                	<div class="x_title">
	                    	<h2>CRE8 Notification</h2>
	                    	<div class="clearfix"></div>
	                  	</div>
	                  	<div class="x_content x_title_padding">
							<div class="row">
								<div class="col-md-6 col-sm-12 col-xs-12">
									<div class="row">
				                    	<div class="col-md-12 col-sm-12 col-xs-12 profile_left checkboxDiv">
					                        <div class="clearfix">
					                        	<div class="checkboxbox"><form:checkbox path="commEmail" value="Yes" /></div>
					                        	<div class="checkboxValue">By Email</div>
					                        </div>
			                    		</div>
									</div>
									<div class="row">
				                    	<div class="col-md-12 col-sm-12 col-xs-12 profile_left checkboxDiv">
					                        <div class="clearfix">
					                        	<div class="checkboxbox"><form:checkbox path="commPhone" value="Yes" /></div>
					                        	<div class="checkboxValue">By Phone</div>
					                        </div>
			                    		</div>
									</div>
								</div>
								<div class="col-md-6 col-sm-12 col-xs-12">
									<div class="row">
				                    	<div class="col-md-12 col-sm-12 col-xs-12">
											<label class="control-label"><b>Send Alerts For Order Processing:</b></label>
										</div>
									</div>
									<div class="row">
				                    	<div class="col-md-12 col-sm-12 col-xs-12 profile_left checkboxDiv">
					                        <div class="clearfix">
					                        	<div class="checkboxbox"><input type="checkbox" id="sendalerts" name="sendalerts" onclick="setCheckBoxValues(0)"/></div>
					                        	<div class="checkboxValue">All</div>
					                        </div>
			                    		</div>
									</div>
									<div class="row">
				                    	<div class="col-md-12 col-sm-12 col-xs-12 profile_left checkboxDiv">
					                        <div class="clearfix">
					                        	<div class="checkboxbox"><form:checkbox path="commTrackingNo" id="commTrackingNo" value="Yes" onclick="setCheckBoxValues(1)"/></div>
					                        	<div class="checkboxValue">Tracking No. Generated</div>
					                        </div>
			                    		</div>
									</div>
									<div class="row">
				                    	<div class="col-md-12 col-sm-12 col-xs-12 profile_left checkboxDiv">
					                        <div class="clearfix">
					                        	<div class="checkboxbox"><form:checkbox path="commShipped" id="commShipped" value="Yes" onclick="setCheckBoxValues(2)"/></div>
					                        	<div class="checkboxValue">Shipped</div>
					                        </div>
			                    		</div>
									</div>
									<div class="row">
				                    	<div class="col-md-12 col-sm-12 col-xs-12 profile_left checkboxDiv">
					                        <div class="clearfix">
					                        	<div class="checkboxbox"><form:checkbox path="commDelivered" id="commDelivered" value="Yes" onclick="setCheckBoxValues(3)"/></div>
					                        	<div class="checkboxValue">Delivered</div>
					                        </div>
			                    		</div>
									</div>
									<div class="row">
				                    	<div class="col-md-12 col-sm-12 col-xs-12 profile_left checkboxDiv">
					                        <div class="clearfix">
					                        	<div class="checkboxbox"><form:checkbox path="commDeliveryExceptions" id="commDeliveryExceptions" value="Yes" onclick="setCheckBoxValues(4)"/></div>
					                        	<div class="checkboxValue">Delivery Exceptions</div>
					                        </div>
			                    		</div>
									</div>
								</div>
							</div>
	                  	</div>
					</div>
					

					<!-- Credit Card Details -->
					<div class="x_panel">
	                	<div class="x_title">
	                    	<h2>Credit Card Details</h2>
	                    	<div class="clearfix"></div>
	                  	</div>
	                  	<div class="x_content x_title_padding">
							<div class="row">
		                    	<div class="col-md-12 col-sm-12 col-xs-12 profile_left">
									<div class="row">
		                        		<label class="col-md-4 col-sm-4 col-xs-12 control-label required" for="cctype">Credit Card Type </label>
		                        		<div class="col-md-8 col-sm-8 col-xs-12 form-group">
		                          			<form:select path="cardType" class="select2_single form-control" >
												<form:option value="0">Select</form:option>
												<form:options items="${cardList}" itemLabel="cardType" itemValue="id"/>
											</form:select>
		                        		</div>
		                      		</div>
									<div class="row">
		                        		<label class="col-md-4 col-sm-4 col-xs-12 control-label required" for="ccno">Credit Card Number </label>
		                        		<div class="col-md-8 col-sm-4 col-xs-12 form-group">
		                          			<form:input path="cardNumber" class="form-control" maxlength="20"  onkeypress="javascript:return isNumber(event)" />
		                        		</div>
		                      		</div>
									<div class="row">
		                        		<div class="col-md-4 col-sm-2 col-xs-12 rowGap">
		                          			<label class="control-label required" for="cvcno">CVC # </label>
		                        		</div>
		                        		<div class="col-md-8 col-sm-2 col-xs-12 form-group rowGap">
		                          			<form:input path="cardCvcNumber" class="form-control" />
		                        		</div>
		                      		</div>
		                      		<div class="row">
		                        		<label class="col-md-4 col-sm-4 col-xs-12 control-label required" for="cardHolderName">Card Holder Name </label>
		                        		<div class="col-md-8 col-sm-8 col-xs-12 form-group">
		                          			<form:input path="cardHolderName" class="form-control" maxlength="120" />
		                        		</div>
		                      		</div>
		                    		<div class="row ">
		                    			<div class="col-md-8 col-sm-6 col-xs-6">
		                    				<label class="col-md-6 col-sm-12 col-xs-12 control-label ccmonth required" for="cardExpiry" >Card Expiry - Month</label>
		                    				<div class="col-md-6 col-sm-12 col-xs-12 form-group">
												<form:select path="expMonth" class="select2_single form-control" >
													<form:option value="">Select</form:option>
													<form:option value="1">January</form:option>
													<form:option value="2">February</form:option>
													<form:option value="3">March</form:option>
													<form:option value="4">April</form:option>
													<form:option value="5">May</form:option>
													<form:option value="6">June</form:option>
													<form:option value="7">July</form:option>
													<form:option value="8">August</form:option>
													<form:option value="9">September</form:option>
													<form:option value="10">October</form:option>
													<form:option value="11">November</form:option>
													<form:option value="12">December</form:option>
												</form:select>		                        		
		                        			</div>
		                    			</div>
		                        		<div class="col-md-4 col-sm-6 col-xs-6">
		                        			<div class="row">
		                        				<div class="col-md-5 col-sm-12 col-xs-12">
		                        					<label for="ccyear" class="control-label required">Year </label>
		                        				</div>
		                        				<div class="col-md-7 col-sm-12 col-xs-12 form-group">
			                        				<form:input path="expYear" class="form-control" maxlength="4" />
		                        				</div>
		                        			</div>
		                        		</div>
		                      		</div>
		                      		<div class="row">
		                        		<label class="col-md-4 col-sm-4 col-xs-12 control-label required" for="billingZipCode">Billing Zip Code</label>
		                        		<div class="col-md-8 col-sm-8 col-xs-12 form-group">
		                          			<form:input path="billingZipCode" class="form-control" maxlength="12" onkeypress="return isNumber(event)" />
		                        		</div>
		                      		</div>
	                    		</div>
							</div>
	                  	</div>
					</div>
	
					

            	</div>
            </div>
					<div class="row">
						<div class="col-md-12 col-sm-12 col-xs-12">
	
						<!--  comments -->
							<div class="x_panel">
						<div class="x_title">
						<h2>Critical comments</h2>
						<div class="clearfix"></div>
						</div>
						<div class="x_content x_title_padding">
						<div class="row">
							<div class="col-md-12 col-sm-12 col-xs-12 profile_left">
											<form:textarea path="comments" cols="100" rows="5" class="form-control" style="height:150px" />
										</div>
									</div>
								</div>
							</div>
						
							<c:if test="${loginDetail.type == 'Admin' or loginDetail.type == 'Super Admin'  or loginDetail.type == 'Group Director'}">
								<div class="x_panel">
							<div class="x_title">
							<h2>Updated History</h2>
							<div class="clearfix"></div>
							</div>
							<div class="x_content x_title_padding">
							<div class="row">
								<div class="col-md-12 col-sm-12 col-xs-12 profile_left">
												<form:textarea path="updatedHistory" cols="100" rows="13" class="form-control" readonly="true" />
											</div>
										</div>
									</div>
								</div>
							</c:if>
						</div>
					  </div>
                    </div>
                    <%--<div id="step-2" class="content" style="display: none;">
                        <h2 class="StepTitle">Step 2 Setup Clinic</h2>
                        <p>
                        	 <button type="button"  class="btn btn-success createNewClinic">Create Physician's Clinic</button>
                        </p>
                     	<div class="x_panel">
							<div class="x_title">
							<h2>Clinic</h2>
							<div class="clearfix"></div>
							</div>
							<div class="x_content x_title_padding">
										
										<div class="row">
										<div class="col-md-1 col-sm-1 col-xs-12 form-group">
										</div>
                          				<!-- <label class="col-md-3 col-sm-3 col-xs-12 control-label required" for="clinic-name">Clinic</label> -->
										<div class="col-md-9 col-sm-9 col-xs-12 form-group">
											&lt;%&ndash; <form:select path="clinicId" class="select2_single form-control" >
												<form:option value="0">Select</form:option>
												<form:options items="${clinicList}" itemLabel="clinicName" itemValue="id" />
											</form:select> &ndash;%&gt;
											
											<div class="row style-select text-center">
												<div class="col-md-12 text-center">
													<div class="subject-info-box-1">
														<label>Clinics List</label>
														<form:select multiple="true" path="clinicList"  id="lstBox1" style="width:100%" >
															<form:options items="${clinicList}" itemLabel="clinicName" itemValue="id" />
														</form:select>
													</div>
									
													<div class="subject-info-arrows text-center">
														<br /><br />
														<input type="button" id="btnAllRight" value=">> " class="btn btn-default"/><br /><br />
														<input type="button" id="btnRight" value="> " class="btn btn-default" /><br /><br />
														<input type="button" id="btnLeft" value="< " class="btn btn-default" /><br /><br />
														<input type="button" id="btnAllLeft" value="<< " class="btn btn-default" />
													</div>
									
													<div class="subject-info-box-2">
														<label>Clinics Selected</label>
														<form:select multiple="true" path="clinicSelectedList"  id="lstBox2" style="width:100%" >
															<form:options items="${clinicSelectedList}" itemLabel="clinicName" itemValue="id" />
														</form:select>
														
														<form:hidden path="selectedClinicId"/>
														<form:hidden path="clinicId"/>
													</div>
									
													<div class="clearfix"></div>
												</div>
											</div>	
											
											
										</div>
										<div class="col-md-2 col-sm-2 col-xs-12 form-group">
										</div>
									</div>
							</div>
						</div>
                      </div>--%>

                     <div id="step-2" class="content" style="display: none;">
                        <h2 class="StepTitle">Step 2 Upload Licenses</h2>
                      	<div class="x_panel">
							<div class="x_title">
							<h2>Documents</h2>
							<div class="clearfix"></div>
							</div>
							<div class="x_content x_title_padding">
										
										<div class="row">
	                          				<div class="col-md-12 col-sm-12 col-xs-12 form-group">
												
												
											
												<div class="row form-group">
													<div class="col-md-2 col-sm-2 col-xs-12 ">
													</div>
													<div class="col-md-2 col-sm-2 col-xs-12 ">
														<label for="uploadedDocFilePurpose" class="control-label required">File Type</label>
													</div>
													<div class="col-md-4 col-sm-4 col-xs-12 ">
														<form:select path="uploadedDocFilePurpose" class="select2_single form-control" >
																		<form:option value="">Select</form:option>
																		<form:option value="DEA License">DEA License</form:option>
																		<form:option value="State License">State License</form:option>
																		<form:option value="Other">Other</form:option>
																	</form:select>
																	<div class="file-type-errors error_fields"></div>
													</div>
									    		</div>
											
												<div class="row form-group otherFileNameBlock">
													<div class="col-md-2 col-sm-2 col-xs-12 ">
													</div>
													<div class="col-md-2 col-sm-2 col-xs-12 ">
																	<label class="control-label required" for="uploadedOtherDocFileName">Name</label>
																</div>
													<div class="col-md-4 col-sm-4 col-xs-12 "> 
														<form:input path="uploadedOtherDocFileName" class="form-control" maxlength="100" />
														<div class="file-name-errors error_fields"></div>
													</div>
											    </div>
											
												<div class="row form-group expiryDateBlock">
												<div class="col-md-2 col-sm-2 col-xs-12 ">
													</div>
													<div class="col-md-2 col-sm-2 col-xs-12 ">
																	<label class="control-label required" for="uploadedDocExpiryDate">Expiry Date</label>
																</div>
													<div class="col-md-4 col-sm-4 col-xs-12">
														<form:input path="uploadedDocExpiryDate" class="form-control calendarIcon"  />
														<div class="expiry-date-errors error_fields"></div>
													</div>
											    </div>
												<div class="row form-group">
													<div class="col-md-2 col-sm-2 col-xs-12 ">
													</div>
													<div class="col-md-2 col-sm-2 col-xs-12 ">
														<label class="control-label required" for="uploadFile">File to Upload</label>
													</div>
													<div class="col-md-4 col-sm-4 col-xs-12 fileTagLoc">
														<input type="file" name="docFiles" id="docFiles" accept="image/gif, image/png, application/pdf" onchange="checkDocumentValues()"/>
														
													</div>
											    </div>
											    <div class="row form-group">
											    	<div class="col-md-2 col-sm-2 col-xs-12 ">
													</div>
											    	<div class="col-md-2 col-sm-2 col-xs-12 ">
														
													</div>
													<div class="col-md-6 col-sm-6 col-xs-12">
														<div class="docFiles-upload-errors" style="color:#a94442"></div>
													</div>
											    </div>
												<div class="row form-group">
													<div class="col-md-4 col-sm-4 col-xs-12 ">
													</div>
													<div class="col-md-6 col-sm-6 col-xs-12 text-left">
														<button type="button" class="btn btn-success uploadDocFile">Upload File</button>
														<br>
														<input type="text" name="uploadLicense" id="uploadLicense" style="visibility:hidden"/>
														<!-- <div class="license_records-errors error_fields"></div> -->
													</div>
											    </div>
											
												<div class="row col-md-12 col-sm-12 col-xs-12 table-responsive">
													<table id="physicianDocFilesTable" class="table-striped table-bordered">
														<thead>
															<tr class="heading_background">
																<th>File Id</th>
																<th>File Name</th>
									
																<th>Document Type</th>
																<th>Expiry Date</th>
																<th>Name</th>
									
																<th>Uploaded on</th>
																<th>User</th>
																<th>User Type</th>
																<th>Delete</th>
															</tr>
														</thead>
														<tbody>
														</tbody>
													</table>	
												</div>
											</div>
										</div>
								  </div>
							</div>
						</div>
                     
                      <div id="step-3" class="content" style="display: none;">
                        <h2 class="StepTitle">Step 3 E-Sign Profile PDF</h2>
                     	<div class="x_panel">
							<div class="x_title">
							<h2>E-Sign</h2>
							<div class="clearfix"></div>
							</div>
							<div class="x_content x_title_padding">
										<div class="row">
                          				
										<div class="col-md-12 col-sm-12 col-xs-12 form-group text-center">
										
										<div  id="eigninfo1"><b style="color:maroon">Click on the Profile PDF icon below to View your Information and Beyond Use Date Notice forms <br>and then Click E-Sign to sign the forms</b><br/><br/></div>
										<div  id="eigninfo2" style="display:none"><b style="color:maroon">All Steps to Complete Your Profile is Finished.<br>Pharmacy Admins will now check and Approve your Profile.</b><br/><br/></div>
										
										</div>
										
										</div>
										
										<div class="row">
                          				
										<div class="col-md-6 col-sm-6 col-xs-12 form-group text-right">
                          						<a href="generatePhysicianPdf?physicianId=${physicianAccount.physicianId}"><button type="button" id="profilePDF" class="btn btn-success PdfGenerator">View your Profile PDF</button></a>
										</div>
										
										
										<div class="col-md-6 col-sm-6 col-xs-12 form-group text-left">
                          					<button type="button" class="btn btn-primary" id="myModal"  name="myModal" onclick="signatureClear();div_show(this)" title="E-Sign Profile PDF">
									   			E-Sign Profile PDF
											</button>
                          				</div>
										
										
									</div>
							</div>
							
							
						</div>
                      </div>
                      
                      
                     </div>
                    <!-- End SmartWizard Content -->
                   
         
	</div>
	<!-- Popup to get Physician Signature  -->
	<div id="dialog-overlay6"></div>
	<div id="dialog-box6">
		<div class="dialog-content6">
			<div id="dialog-message6"></div>
               <div class="row">
                   <div class="col-md-12" style="border-right: 1px dotted #C2C2C2;padding-right: 30px;background-repeat: no-repeat; background-position: 0 0; background-attachment: fixed;">
                       <div class="form-group text-center">
                         	<h4 class="modal-title" id="myModalLabel">Dr Signature</h4>
                      		<div id="canvas">
				 			<canvas class="roundCorners" id="newSignature"
									style="position: relative; margin: 0; padding: 0; border: 1px solid #c4caac;" ></canvas>			
						</div>
						<div class="form-group">
							<div class="col-sm-2"></div>
							
							<div class="col-sm-3 text-right">
								<button type="button" onclick="signatureClear()" class="btn btn-primary btn-sm">
									Clear signature</button>
							</div>
							<div class="col-sm-3 text-center">
								<button id="savesignature" type="button" onclick="signaturePhysicianSave()"  class="btn btn-primary btn-sm">
									Save signature</button>
							</div>
							<div class="col-sm-3 text-left">
								<button type="button" class="btn btn-primary btn-sm" onclick="div_hide()">
                                   	Cancel</button>
							</div>
							<div class="col-sm-1"></div>
						</div>
						<div class="form-group"><br/></div>
						<div class="form-group">
							<div class="col-sm-4 text-center"></div>
							<div class="col-sm-4 text-center">
								<input type="file" id="imageLoader" name="imageLoader"  class="btn btn-primary btn-sm"/>
							</div>
							<div class="col-sm-4 text-center"></div>
							</div>
						</div>
                  		</div>
              		</div>
			</div>
		</div>
    	<div class="row form-group">
				<div class="col-md-12 col-sm-12 col-xs-12 profile_left">
					<div class="x_panel">
                  		<div class="x_content x_title_padding">
                    		<div class="row">
								<div class="col-md-12 col-sm-12 col-xs-12 text-center">
									<c:choose>
								      <c:when test="${(loginDetail.type == 'Administrator' or loginDetail.type == 'Super Admin' or loginDetail.type == 'Admin')}">
									      	<c:if test="${(loginDetail.physicianCreationPermission =='Yes')}">
									      		<button type="submit"  class="btn btn-success formSubmit">Save</button>
									      	</c:if>
								      </c:when>
								      <c:otherwise>
								      			<button type="submit"  class="btn btn-success formSubmit">Save</button>
								      </c:otherwise>
								    </c:choose>
									<!-- <input type="submit" id="submitphyprofileHidden" style="display: none;"> -->
								</div>
								
								<div class="col-md-12 col-sm-12 col-xs-12 text-center formErrorMsg" style="display:none;">
									<b>Error:</b> <spring:eval expression="@commonConfigurer.getProperty(\'error.form_fields_not_valid\')" />
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<c:if test="${loginDetail.type == 'Admin' or loginDetail.type == 'Super Admin'  or loginDetail.type == 'Group Director'}">
				<div class="row">
					<c:choose>
						<c:when test="${not empty physicianAccount.prescriptionId}">
							<div class="col-md-2 col-sm-6 col-xs-6">
								<img class="img-responsive goSummary" src="${pageContext.request.contextPath}/resources/images/back_icon-3.png" title="Go to Summary">
							</div>
							<div class="col-md-4 col-sm-6 col-xs-6">
								<button type="button" class="btn btn-primary backPrescription" >Back to Prescription</button>
							</div>
						</c:when>
						<c:otherwise>
							<div class="col-md-12 col-sm-12 col-xs-12">
								<img class="img-responsive goSummary" src="${pageContext.request.contextPath}/resources/images/back_icon-3.png" title="Go to Summary">
							</div>
						</c:otherwise>
					</c:choose>					
				</div>
			</c:if>
    <!-- /page content -->
 	<input type="hidden" id="serverUrl" value="${pageContext.request.contextPath}" />
    <form:hidden path="physicianId" />
    <form:hidden path="targetObject" />
   	<form:hidden path="prescriptionId" />
    <form:hidden path="password" />
    <form:hidden path="prescriberGroup" />
    <form:hidden path="base64ImgString" />
    <form:hidden path="profilestep" />
    <form:hidden path="screenflag" />
    <form:hidden path="eignedPdf" />
    
    
	<!-- Error messages getting from property file -->
	<input type="hidden" id="err_fname" value='<spring:eval expression="@physicianConfigurer.getProperty(\'error.first_name\')" />' />
	<input type="hidden" id="err_lname" value='<spring:eval expression="@physicianConfigurer.getProperty(\'error.last_name\')" />' />
	
	<input type="hidden" id="err_email" value='<spring:eval expression="@physicianConfigurer.getProperty(\'error.email\')" />' />
	<input type="hidden" id="err_email_format" value='<spring:eval expression="@physicianConfigurer.getProperty(\'error.email_format\')" />' />
	<input type="hidden" id="err_email_confirm" value='<spring:eval expression="@physicianConfigurer.getProperty(\'error.email_and_confirm\')" />' />
	<input type="hidden" id="err_conf_email" value='<spring:eval expression="@physicianConfigurer.getProperty(\'error.confirm_email\')" />' />
	<input type="hidden" id="err_conf_email_format" value='<spring:eval expression="@physicianConfigurer.getProperty(\'error.confirm_email_format\')" />' />
	
	<input type="hidden" id="err_password" value='<spring:eval expression="@physicianConfigurer.getProperty(\'error.password\')" />' />
	<input type="hidden" id="err_pass_conf_pass" value='<spring:eval expression="@physicianConfigurer.getProperty(\'error.password_and_confirm_password\')" />' />
	<input type="hidden" id="err_conf_pass" value='<spring:eval expression="@physicianConfigurer.getProperty(\'error.confirm_password\')" />' />	
	<input type="hidden" id="error_password_min_length" value='<spring:eval expression="@commonConfigurer.getProperty(\'error.password_min_length\')" />' />
	
	<input type="hidden" id="err_date_registration" value='<spring:eval expression="@physicianConfigurer.getProperty(\'error.date_of_registration\')" />' />
	<input type="hidden" id="err_phone" value='<spring:eval expression="@physicianConfigurer.getProperty(\'error.phone\')" />' />
	<input type="hidden" id="err_phone_format" value='<spring:eval expression="@physicianConfigurer.getProperty(\'error.phone_format\')" />' />
	<input type="hidden" id="err_fax_format" value='<spring:eval expression="@physicianConfigurer.getProperty(\'error.fax_format\')" />' />
	<input type="hidden" id="err_fax" value='<spring:eval expression="@physicianConfigurer.getProperty(\'error.fax\')" />' />
	<input type="hidden" id="err_home_format" value='<spring:eval expression="@physicianConfigurer.getProperty(\'error.home_format\')" />' />
	<input type="hidden" id="err_office_format" value='<spring:eval expression="@physicianConfigurer.getProperty(\'error.office_format\')" />' />
	<input type="hidden" id="err_mobile" value='<spring:eval expression="@physicianConfigurer.getProperty(\'error.mobile\')" />' />
	<input type="hidden" id="err_mobile_format" value='<spring:eval expression="@physicianConfigurer.getProperty(\'error.mobile_format\')" />' />
	
	<input type="hidden" id="err_street" value='<spring:eval expression="@physicianConfigurer.getProperty(\'error.street\')" />' />
	<input type="hidden" id="err_city" value='<spring:eval expression="@physicianConfigurer.getProperty(\'error.city\')" />' />
	<input type="hidden" id="err_state" value='<spring:eval expression="@physicianConfigurer.getProperty(\'error.state\')" />' />
	<input type="hidden" id="err_zip" value='<spring:eval expression="@physicianConfigurer.getProperty(\'error.zip\')" />' />
	<input type="hidden" id="err_zip_format" value='<spring:eval expression="@physicianConfigurer.getProperty(\'error.zip_format\')" />' />

	<input type="hidden" id="err_dea" value='<spring:eval expression="@physicianConfigurer.getProperty(\'error.dea\')" />' />
	<input type="hidden" id="err_dea_format" value='<spring:eval expression="@physicianConfigurer.getProperty(\'error.dea_format\')" />' />
	<input type="hidden" id="err_npi_format" value='<spring:eval expression="@physicianConfigurer.getProperty(\'error.npi_format\')" />' />
	<input type="hidden" id="err_upin_format" value='<spring:eval expression="@physicianConfigurer.getProperty(\'error.upin_format\')" />' />
	<input type="hidden" id="err_lic_state" value='<spring:eval expression="@physicianConfigurer.getProperty(\'error.lic_state\')" />' />
	<input type="hidden" id="err_lic_state_format" value='<spring:eval expression="@physicianConfigurer.getProperty(\'error.lic_state_format\')" />' />
	<input type="hidden" id="err_med_format" value='<spring:eval expression="@physicianConfigurer.getProperty(\'error.med_format\')" />' />
	<input type="hidden" id="err_dps_format" value='<spring:eval expression="@physicianConfigurer.getProperty(\'error.dps_format\')" />' />
	
	<input type="hidden" id="err_cardType" value='<spring:eval expression="@physicianConfigurer.getProperty(\'error.cardType\')" />' />
	<input type="hidden" id="err_cardNo" value='<spring:eval expression="@physicianConfigurer.getProperty(\'error.card_number\')" />' />
	<input type="hidden" id="err_cardNo_format" value='<spring:eval expression="@physicianConfigurer.getProperty(\'error.card_number_format\')" />' />
	<input type="hidden" id="err_cvcNo" value='<spring:eval expression="@physicianConfigurer.getProperty(\'error.cvc_number\')" />' />
	<input type="hidden" id="err_cvcNo_format" value='<spring:eval expression="@physicianConfigurer.getProperty(\'error.cvc_number_format\')" />' />
	<input type="hidden" id="err_cardHolder" value='<spring:eval expression="@physicianConfigurer.getProperty(\'error.card_holder_name\')" />' />
	<input type="hidden" id="err_cardExpMonth" value='<spring:eval expression="@physicianConfigurer.getProperty(\'error.card_exp_month\')" />' />
	<input type="hidden" id="err_cardExpYear" value='<spring:eval expression="@physicianConfigurer.getProperty(\'error.card_exp_year\')" />' />
	<input type="hidden" id="err_cardExpYear_format" value='<spring:eval expression="@physicianConfigurer.getProperty(\'error.card_exp_year_format\')" />' />
	<input type="hidden" id="err_group" value='<spring:eval expression="@physicianConfigurer.getProperty(\'error.group\')" />' />
	<input type="hidden" id="err_cc_expiry" value='<spring:eval expression="@physicianConfigurer.getProperty(\'error.credit_card_expiry\')" />' />
	<input type="hidden" id="err_cc_expiry_year" value='<spring:eval expression="@physicianConfigurer.getProperty(\'error.credit_card_expiry_year\')" />' />
	
	<input type="hidden" id="err_clinic" value='<spring:eval expression="@physicianConfigurer.getProperty(\'error.clinic\')" />' />
	<input type="hidden" id="err_phy_group_logo" value='<spring:eval expression="@physicianConfigurer.getProperty(\'error.phy_group_logo\')" />' />
	
	<input type="hidden" id="err_billing_zip" value='<spring:eval expression="@physicianConfigurer.getProperty(\'error.billing_zip\')" />' />
	<input type="hidden" id="err_billing_zip_format" value='<spring:eval expression="@physicianConfigurer.getProperty(\'error.billing_zip_format\')" />' />
	<input type="hidden" id="error_upload_license" value='<spring:eval expression="@physicianConfigurer.getProperty(\'error.upload_license\')" />' />
	
	
	
<script>

var physicianLogo="${physicianAccount.logoFile}";
var profilestep="${physicianAccount.profilestep}";
var status="${physicianAccount.status}";
physicianStep=parseInt(physicianStep)+parseInt(profilestep);
//alert(profilestep)
if(profilestep==2)
	$('button[type="submit"]').prop('disabled','disabled');
else
	$('button[type="submit"]').removeAttr('disabled');
	//alert(profilestep)
</script>
</div>
</form:form>
<%--includes the Front End UI validations--%>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/pages/physician/physicianProfile.js?v=2"></script> 
<script src="${pageContext.request.contextPath}/resources/js/jquery.smartWizard_physician.js?v=7"></script>
 <script>
//	alert(status)
	if(status!='New')
		fnShowHide(8);	
</script>
