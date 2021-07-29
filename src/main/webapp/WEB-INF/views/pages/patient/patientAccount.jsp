 <%--
 Front End UI to save and load the Patient Account details
 --%>
<%@ include file="../../../layout/taglib.jsp" %>
<script src="${pageContext.request.contextPath}/resources/js/jquery.selectlistactions.js?v=1"></script>
<link href="${pageContext.request.contextPath}/resources/css/selectlistactions.css" rel="stylesheet" />
<c:if test="${loginDetail.type != 'Patient'}">
	<div class="row">
	
		<c:choose>
			<c:when test="${not empty patientAccount.prescriptionId and patientAccount.prescriptionId!='0'}">
				<div class="col-md-2 col-sm-3 col-xs-6">
					<img class="img-responsive goSummary" src="${pageContext.request.contextPath}/resources/images/back_icon-3.png" title="Go to Summary">
				</div>
				<div class="col-md-4 col-sm-3 col-xs-6">
					<button type="button" class="btn btn-primary backPrescription" >Back to Prescription</button>
				</div>
			</c:when>
			<c:otherwise>
				<div class="col-md-6 col-sm-6 col-xs-6">
					<img class="img-responsive goSummary" src="${pageContext.request.contextPath}/resources/images/back_icon-3.png" title="Go to Summary">
				</div>
			</c:otherwise>
		</c:choose>
		<div class="col-md-6 col-sm-6 col-xs-6">
			<c:if test="${loginDetail.type != 'Physician' or (loginDetail.type == 'Physician' and loginDetail.status != 'New Modifications') }">
			
				<c:choose>
				<c:when test="${(loginDetail.type == 'Administrator' or loginDetail.type == 'Super Admin' or loginDetail.type == 'Admin')}">
				<c:if test="${(loginDetail.patientCreationPermission =='Yes')}">
					<button type="button" id="newPatientAcc" class="btn btn-primary goNewRec" >Create New Patient</button>
				</c:if>
				</c:when>
				<c:otherwise>
					<button type="button" id="newPatientAcc" class="btn btn-primary goNewRec" >Create New Patient</button>
				</c:otherwise>
				</c:choose>
				
				
			</c:if>
		</div>
	</div>
</c:if>
		
<form:form method="POST" action="SavePatientAccount" name="patientAccount" commandName="patientAccount"  enctype="multipart/form-data" >
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
	
    	<div class="row">
        	<div class="col-md-6 col-sm-6 col-xs-12">
          		<div class="x_panel">
               		<div class="x_title">
	                   	<h2>Patient Profile</h2>
	                   	<div class="clearfix"></div>
                 	</div>
                 	<div class="x_content x_title_padding">
                 	
						<div class="row">
                    		<div class="col-md-12 col-sm-12 col-xs-12 profile_left">
								<div class="row">
									<label class="col-md-4 col-sm-4 col-xs-12 control-label required" for="first-name">First Name </label>
	                        		<div class="col-md-8 col-sm-8 col-xs-12 form-group">
										<form:input path="firstName" class="form-control" maxlength="50"  required="required" onkeyup="displayFullname('firstName', 'middleName', 'lastName', 'patientName' );" />
									</div>		                        		
	                      		</div>
								<div class="row">
	                        		<label class="col-md-4 col-sm-4 col-xs-12 control-label" for="middle-name">Middle Name</label>
	                        		<div class="col-md-8 col-sm-8 col-xs-12 form-group">
										<form:input path="middleName" class="form-control" maxlength="10" onkeyup="displayFullname('firstName', 'middleName', 'lastName', 'patientName' );" />
	                        		</div>
	                      		</div>
	                      		<div class="row">
	                        		<label class="col-md-4 col-sm-4 col-xs-12 control-label required" for="lastName">Last Name </label>
	                        		<div class="col-md-8 col-sm-8 col-xs-12 form-group">
										<form:input path="lastName" class="form-control" maxlength="50"  required="required" onkeyup="displayFullname('firstName', 'middleName', 'lastName', 'patientName' );" />
	                        		</div>
	                      		</div>
	                    		<div class="row">
	                        		<label class="col-md-4 col-sm-4 col-xs-12 control-label" for="patientName">Name </label>
	                        		<div class="col-md-8 col-sm-8 col-xs-12 form-group">
										<form:input path="patientName" class="form-control fullname"  title="${patientAccount.patientName}"  maxlength="110" readonly="true" />
	                        		</div>
	                      		</div>


								<div class="row">
									<label class="col-md-4 col-sm-6 col-xs-12 control-label required" for="dateRegistered">Registration Date</label>
									<div class='col-md-8 col-sm-6 col-xs-12 input-group form-group' style="padding: 0px 9px 0px; float: left;">
										<c:choose>
											<c:when test="${loginDetail.type == 'Patient'}">
												<form:input path="dateRegistered" class="form-control" title="${patientAccount.dateRegistered}" readonly="true" />
											</c:when>
											<c:otherwise>
												<form:input path="dateRegistered" class="form-control calendarIcon" />
											</c:otherwise>
										</c:choose>
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
	                          			<form:input path="address" class="form-control" />
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
											<form:options items="${stateList}" itemLabel="stateName" itemValue="stateCode"/>
										</form:select>
	                        		</div>
	                      		</div>
	                    		<div class="row">
	                        		<label class="col-md-4 col-sm-3 col-xs-12 control-label required" for="zip1">Zip Code</label>
	                        		<div class="col-md-8 col-sm-9 col-xs-12 form-group">
	                          			<form:input path="zipCode" class="form-control" maxlength="12" onkeypress="return isNumber(event)" />
	                          			<form:hidden path="country" />
	                        		</div>
	                      		</div>
                    		</div>
						</div>
                  	</div>
				</div>

				<!-- Contact Information -->
				<div class="x_panel">
                	<div class="x_title">
                    	<h2>Contact Information</h2>
                    	<div class="clearfix"></div>
                  	</div>
                  	<div class="x_content x_title_padding">
						<div class="row">
	                    	<div class="col-md-12 col-sm-12 col-xs-12 profile_left">
								
								<div class="row">
	                        		<label class="col-md-4 col-sm-3 col-xs-12 control-label required" for="email">Email </label>
	                        		<div class="col-md-8 col-sm-9 col-xs-12 form-group">
										<c:choose>
											<c:when test="${loginDetail.type == 'Patient'}">
												<form:input path="email" class="form-control" maxlength="255" title="${patientAccount.email}" readonly="true" />
											</c:when>
											<c:otherwise>
												<form:input path="email" class="form-control" maxlength="255" />
											</c:otherwise>
										</c:choose>
	                        		</div>
	                      		</div>
	                      	
							 
							
	                      		
	                      		
	                      		<div class="row" style="display: none">
	                        		<label class="col-md-4 col-sm-12 col-xs-12 control-label required" for="userLoginId">Username</label>
	                        		<div class="col-md-8 col-sm-12 col-xs-12 form-group">
										<c:choose>
											<c:when test="${loginDetail.type == 'Patient'}">
												<form:input path="userLoginId" class="form-control" maxlength="50" title="${patientAccount.userLoginId}" readonly="true" />
											</c:when>
											<c:otherwise>
												<form:input path="userLoginId" class="form-control" maxlength="50" />
											</c:otherwise>
										</c:choose>
	                        		</div>
	                      		</div>
	                      		<div  style="display: none" class="col-md-offset-4 col-md-9 col-sm-12 col-xs-12 photoNotes">
									<label><b>Note:</b> Username used for patient login purpose</label>
								</div>
	                      		<div class="row" style="display: none">
	                        		<label class="col-md-4 col-sm-12 col-xs-12 control-label required" for="tempPassword">Password</label>
	                        		<div class="col-md-8 col-sm-12 col-xs-12 form-group">
									<input type="text" value="${patientAccount.password}" class="form-control" readonly="readonly" />

	                        		</div>
	                      		</div>
	                      		
	                      		
								
								<div class="row">
	                        		<label class="col-md-4 col-sm-3 col-xs-12 control-label required" for="phone">Phone </label>
	                        		<div class="col-md-8 col-sm-9 col-xs-12 form-group">
	                          			<form:input path="phone" class="form-control" maxlength="15" onkeypress="return isNumber(event)" />
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



				<!-- License Numbers -->
				<div class="x_panel">
                	<div class="x_title">
                    	<h2>Identification</h2>
                    	<div class="clearfix"></div>
                  	</div>
                  	<div class="x_content x_title_padding">
						<div class="row">
	                    	<div class="col-md-12 col-sm-12 col-xs-12 profile_left">
								<%--<div class="col-md-offset-4 col-md-6 col-sm-9 col-xs-9 photoNotes">
									<label><b>Enter Drivers License or Alternate ID</b></label>
								</div>--%>
								<div class="row">
									<label class="col-md-4 col-sm-3 col-xs-12 control-label" for="driversLicense">Drivers License</label>
									<div class="col-md-8 col-sm-9 col-xs-12 form-group">
										<form:input path="driversLicense" class="form-control" maxlength="15" />
									</div>	                        		
	                      		</div>
								<div class="row">
	                        		<label class="col-md-4 col-sm-3 col-xs-12 control-label" for="driversLicenseState">License State</label>
									<div class="col-md-8 col-sm-9 col-xs-12 form-group">
										<form:select path="driversLicenseState" class="select2_single form-control" id="driversLicenseState" >
											<form:option value="0">Select</form:option>
											<form:options items="${stateList}" itemLabel="stateName" itemValue="stateCode" />
										</form:select>
									</div>
	                      		</div>
	                      		<div class="row">
	                        		<label class="col-md-4 col-sm-3 col-xs-12 control-label" for="licenseExpDate">Expiration Date</label>
									<div class="col-md-8 col-sm-9 col-xs-12 input-group form-group" style="padding: 0px 8px 0px;">
										<form:input path="licenseExpDate" class="form-control calendarIcon" />
									</div>
	                      		</div>
	                    		<div class="row">
	                        		<label class="col-md-4 col-sm-3 col-xs-12 control-label" for="ssn" >SSN </label>
									<div class="col-md-8 col-sm-9 col-xs-12 form-group">
										<form:input path="ssn" class="form-control"  onkeypress="return isNumber(event)" />
									</div>
	                      		</div>
	                      		
	                    		<div class="row">
	                        		<label class="col-md-4 col-sm-3 col-xs-12 control-label" for="alternateId" >Alternate ID</label>
									<div class="col-md-8 col-sm-9 col-xs-12 form-group">
										<form:input path="alternateId" class="form-control"  onkeypress="return isNumber(event)" />
									</div>
	                      		</div>
	                    		<div class="row">
	                        		<label class="col-md-4 col-sm-3 col-xs-12 control-label" for="ssn" >Alternate ID Type</label>
									<div class="col-md-8 col-sm-9 col-xs-12 form-group">
										<form:select path="alternateIdTypeId" class="select2_single form-control" onchange="getAlterText(this);">
											<form:option value="0">Other</form:option>
											<form:option value="1">MilitaryID</form:option>
											<form:option value="2">Other State Issued ID</form:option>
											<form:option value="3">Passport ID</form:option>
											<form:option value="4">Permanent Resident Card</form:option>
										</form:select>
										<form:hidden path="alternateIdTypeText" />
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
	                        		<label class="col-md-4 col-sm-4 col-xs-12 control-label " for="cardType">Credit Card Type </label>
	                        		<div class="col-md-8 col-sm-8 col-xs-12 form-group">
	                          			<form:select path="cardType" class="select2_single form-control" >
											<form:option value="0">Select</form:option>
											<form:options items="${cardList}" itemLabel="cardType" itemValue="id"/>
										</form:select>
	                        		</div>
	                      		</div>
								<div class="row">
	                        		<label class="col-md-4 col-sm-4 col-xs-12 control-label " for="cardNumber">Credit Card Number </label>
	                        		<div class="col-md-8 col-sm-4 col-xs-12 form-group">
	                          			<form:input path="cardNumber" class="form-control" maxlength="20"  onkeypress="javascript:return isNumber(event)" />
	                        		</div>
	                      		</div>
								<div class="row">
	                        		<div class="col-md-4 col-sm-2 col-xs-12 rowGap">
	                          			<label class="control-label " for="cardCvcNumber">CVC # </label>
	                        		</div>
	                        		<div class="col-md-8 col-sm-2 col-xs-12 form-group rowGap">
	                          			<form:input path="cardCvcNumber" class="form-control" />
	                        		</div>
	                      		</div>
	                      		<div class="row">
	                        		<label for="cardHolderName" class="control-label col-md-4 col-sm-4 col-xs-12 ">Card Holder Name </label>
	                        		<div class="col-md-8 col-sm-8 col-xs-12 form-group">
	                          			<form:input path="cardHolderName" class="form-control" maxlength="120" />
	                        		</div>
	                      		</div>
								<div class="row ">
	                    			<div class="col-md-8 col-sm-6 col-xs-6">
	                    				<label class="col-md-6 col-sm-12 col-xs-12 control-label ccmonth" for="expMonth" >Card Expiry - Month</label>
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
	                        					<label for="ccyear" class="control-label">Year </label>
	                        				</div>
	                        				<div class="col-md-7 col-sm-12 col-xs-12 form-group">
		                        				<form:input path="expYear" class="form-control" maxlength="4" />
	                        				</div>
	                        			</div>
	                        		</div>
	                      		</div>
	                      		<div class="row">
	                        		<label class="col-md-4 col-sm-4 col-xs-12 control-label" for="billingZipCode">Billing Zip Code</label>
	                        		<div class="col-md-8 col-sm-8 col-xs-12 form-group">
	                          			<form:input path="billingZipCode" class="form-control" maxlength="12" onkeypress="return isNumber(event)" />
	                        		</div>
	                      		</div>
	                      		
	                      		<div class="patientCCBalance blackDiv"></div>
	                      		
                    		</div>
						</div>
                  	</div>
				</div>
				
				<c:if test="${loginDetail.type == 'Admin' or loginDetail.type == 'Super Admin'  or loginDetail.type == 'Group Director'}">
					<div class="x_panel">
	                	<div class="x_title">
	                    	<h2>Critical comments</h2>
	                    	<div class="clearfix"></div>
	                  	</div>
	
	                  	<div class="x_content x_title_padding">
							<div class="row">
		                    	<div class="col-md-12 col-sm-12 col-xs-12 profile_left checkboxDiv">
			                        <form:textarea path="criticalComments" cols="30" rows="5" class="form-control commentBlock" />
	                    		</div>
							</div>
	                  	</div>
	                  	
	                  	<c:if test="${loginDetail.type == 'Admin' or loginDetail.type == 'Super Admin'}">
							<div class="row">
		                    	<div class="col-md-12 col-sm-12 col-xs-12 profile_left checkboxDiv">
			                        <div class="clearfix">
			                        	<div class="checkboxbox"><form:checkbox path="commentsUpdateInPioneer" value="Yes" /></div>
			                        	<div class="checkboxValue">Updated in Pioneer RX</div>
			                        </div>
	                    		</div>
							</div>
						</c:if>
					</div>
				</c:if>
				
				<c:if test="${loginDetail.type == 'Admin' or loginDetail.type == 'Super Admin'  or loginDetail.type == 'Group Director'}">
					<div class="x_panel">
                  		<div class="x_title">
                    		<h2>Updated History</h2>
                    		<div class="clearfix"></div>
                  		</div>
                  		<div class="x_content x_title_padding">
                    		<div class="row">
                    			<div class="col-md-12 col-sm-12 col-xs-12 profile_left">
									<form:textarea path="updatedHistory" cols="100" rows="5" class="form-control" readonly="true" />
								</div>
							</div>
						</div>
					</div>
				</c:if>
			</div>
			
           	<div class="col-md-6 col-sm-6 col-xs-12">
           	
				<div class="x_panel">
	            	<div class="x_title">
	                	<h2>Profile Photo</h2>
	                    <div class="clearfix"></div>
					</div>
	                <div class="x_content x_title_padding">
						<div class="row form-group profile_img">
				        	<div class="col-md-12 col-sm-12 col-xs-12" id="crop-avatar">
			                    <c:choose>
                         			<c:when test="${patientAccount.photoFile != null and patientAccount.photoFile != ''}">
                         				<img class="" src="${pageContext.request.contextPath}/resources/${patientAccount.photoFile}" height="128" alt="Avatar">
                         			</c:when>
                         			<c:otherwise>
										<img class="" src="${pageContext.request.contextPath}/resources/images/img.jpg" alt="Avatar" >
                         			</c:otherwise>
                         		</c:choose>
							</div>
						</div>
						<div class="row col-md-12 col-sm-12 col-xs-12 photoNotes">
							<label><b>Note:</b> <spring:eval expression="@commonConfigurer.getProperty(\'profile.image.notes\')" /></label>
						</div>
						<div class="row form-group" >
				        	<div class="col-md-12 col-sm-12 col-xs-12" id="ProfilePhoto">
				            	<input type="file" id="file" name="patientPhoto" accept="image/gif, image/png"  onchange="checkValues();" />
				            	<div class=" file-upload-errors"></div>
							</div>
						</div>
					</div>
				</div>
					
				<div class="x_panel">
                	<div class="x_title">
                    	<h2>Patient Status</h2>
                    	<div class="clearfix"></div>
                  	</div>
                  	<div class="x_content x_title_padding">
						<div class="row">
							<div class="col-md-12 col-sm-12 col-xs-12">
								<c:choose>
									<c:when	test="${loginDetail.type == 'Administrator' or loginDetail.type == 'Admin' or loginDetail.type == 'Super Admin'}">
									
									<c:choose>
										      <c:when test="${(loginDetail.patientApprovalPermission =='Yes')}">
											      		<div class="row">
														<label
															class="col-md-3 col-sm-3 col-xs-12 control-label required"
															for="status">Status</label>
														<div class="col-md-9 col-sm-9 col-xs-12 form-group">
															<form:select path="status"
																class="select2_single form-control"
																onchange="setDisabled(this.value);">
																<form:option value="New">New</form:option>
																<form:option value="Profile Completed">Profile Completed</form:option>
																<form:option value="New Modifications">New Modifications</form:option>
																<form:option value="Approved">Approved</form:option>
																<form:option value="Denied">Denied</form:option>
															</form:select>
															<input type="hidden" id="previousStatus"
																value="${patientAccount.status}" />
														</div>
													</div>
										      </c:when>
										      <c:otherwise>
										      			<c:choose>
											      			<c:when	test="${patientAccount.status != 'New Modifications' and patientAccount.status != 'Approved' and patientAccount.status != 'Denied'}">
																<div class="row">
																	<label
																		class="col-md-3 col-sm-3 col-xs-12 control-label required"
																		for="status">Status</label>
																	<div class="col-md-9 col-sm-9 col-xs-12 form-group">
																		<form:select path="status"
																			class="select2_single form-control"
																			onchange="setDisabled(this.value);">
																			<form:option value="New">New</form:option>
																			<form:option value="Profile Completed">Profile Completed</form:option>
																		</form:select>
																		<input type="hidden" id="previousStatus"
																			value="${patientAccount.status}" />
																	</div>
																</div>
															</c:when>
															<c:otherwise>
																<div class="row">
																	<label
																		class="col-md-3 col-sm-3 col-xs-12 control-label required"
																		for="status">Status</label>
																	<div class="col-md-9 col-sm-9 col-xs-12 form-group">
																		<form:input path="status" class="form-control"
																			readonly="true" />
																	</div>
																</div>
															</c:otherwise>
														</c:choose>
										      			
										      </c:otherwise>
										    </c:choose>
										    
										
											<div class="row">
											<label
												class="col-md-3 col-sm-3 col-xs-12 control-label required"
												for="groupId"><b>Group</b></label>
												</div>
										
										<div class="row">
											
											<div class="col-md-12 col-sm-12 col-xs-12 form-group">
												<c:choose>
													<c:when test="${loginDetail.type != 'Group Director' and loginDetail.type != 'Patient'}">
														<%-- <form:select path="groupId"
															class="select2_single form-control" id="groupId">
															<form:option value="0">Select</form:option>
															<form:options items="${groupList}" itemLabel="groupName"
																itemValue="id" />
														</form:select> --%>
														
														<div class="row style-select">
															<div class="col-md-12">
																<div class="subject-info-box-1">
																	<label>Group List</label>
																	<form:select multiple="true" path="groupList"  id="grouplstBox1" style="width:100%" >
																		<form:options items="${groupList}" itemLabel="groupName" itemValue="id" />
																	</form:select>
																</div>
																<div class="subject-info-arrows text-center">
																	<br /><br />
																	<input type="button" id="groupbtnAllRight" value=">> " class="btn btn-default"/><br /><br />
																	<input type="button" id="groupbtnRight" value="> " class="btn btn-default" /><br /><br />
																	<input type="button" id="groupbtnLeft" value="< " class="btn btn-default" /><br /><br />
																	<input type="button" id="groupbtnAllLeft" value="<< " class="btn btn-default" />
																</div>
																<div class="subject-info-box-2">
																	<label>Group Selected</label>
																	<form:select multiple="true" path="groupSelectedList"  id="grouplstBox2" style="width:100%" >
																		<form:options items="${groupSelectedList}" itemLabel="groupName" itemValue="id" />
																	</form:select>
																	
																	<form:hidden path="selectedGroupId" id="selectedGroupId" name="selectedGroupId"/>
																	<form:hidden path="groupId" id="groupId" name="groupId"/>
																</div>
																<div class="clearfix"></div>
															</div>
														</div>
										
													</c:when>
													<c:otherwise>
														<c:choose>
														<c:when test="${loginDetail.type=='Patient'}">
															<div class="row style-select">
																<div class="col-md-12">
																		<form:select multiple="true" path="groupSelectedList"  id="grouplstBox2" style="width:100%" >
																		<form:options items="${groupSelectedList}" itemLabel="groupName" itemValue="id" />
																		</form:select>
																	
																		<form:hidden path="selectedGroupId" id="selectedGroupId" name="selectedGroupId"/>
																		<form:hidden path="groupId" id="groupId" />
																	
																</div>
															</div>	
														</c:when>
														<c:otherwise><div class="row style-select">
																<div class="col-md-12">
																<input type="text" value="${patientAccount.groupName}"
																	title="${patientAccount.groupName}"
																	class="form-control" readonly="readonly" id="groupName"
																	name="groupName">
																<form:hidden path="selectedGroupId" id="selectedGroupId" name="selectedGroupId"/>
																<form:hidden path="groupId" id="groupId" />
															</div>
															</div>
														</c:otherwise></c:choose>
										
													
													
													
														
													</c:otherwise>
												</c:choose>
											</div>
										</div>
									</c:when>
								<%--	<c:otherwise>
										<c:choose>
											<c:when
												test="${patientAccount.status != 'New Modifications' and patientAccount.status != 'Approved' and patientAccount.status != 'Denied'}">
												<div class="row">
													<label
														class="col-md-3 col-sm-3 col-xs-12 control-label required"
														for="status">Status</label>
													<div class="col-md-9 col-sm-9 col-xs-12 form-group">
														<form:select path="status"
															class="select2_single form-control"
															onchange="setDisabled(this.value);">
															<form:option value="New">New</form:option>
															<form:option value="Profile Completed">Profile Completed</form:option>
														</form:select>
														<input type="hidden" id="previousStatus"
															value="${patientAccount.status}" />
													</div>
												</div>
												<div class="row">
													<label
														class="col-md-3 col-sm-3 col-xs-12 control-label required"
														for="group">Group </label>
													<div class="col-md-9 col-sm-9 col-xs-12 form-group">
														<c:choose>
														<c:when test="${loginDetail.type=='Patient'}">
															<div class="row style-select">
																<div class="col-md-12">
																		<form:select multiple="true" path="groupSelectedList"  id="grouplstBox2" style="width:100%" >
																		<form:options items="${groupSelectedList}" itemLabel="groupName" itemValue="id" />
																		</form:select>
																	
																		<form:hidden path="selectedGroupId" id="selectedGroupId" name="selectedGroupId"/>
																		<form:hidden path="groupId" id="groupId" />
																	
																</div>
															</div>	
														</c:when>
														<c:otherwise><div class="row style-select">
																<div class="col-md-12">
																<input type="text" value="${patientAccount.groupName}"
																	title="${patientAccount.groupName}"
																	class="form-control" readonly="readonly" id="groupName"
																	name="groupName">
																<form:hidden path="selectedGroupId" id="selectedGroupId" name="selectedGroupId"/>
																<form:hidden path="groupId" id="groupId" />
															</div>
															</div>
														</c:otherwise></c:choose>
													</div>
												</div>
											</c:when>
											<c:otherwise>
												<div class="row">
													<label
														class="col-md-3 col-sm-3 col-xs-12 control-label required"
														for="status">Status</label>
													<div class="col-md-9 col-sm-9 col-xs-12 form-group">
														<form:input path="status" class="form-control"
															readonly="true" />
													</div>
												</div>
												
												<div class="row">
													<label class="col-md-3 col-sm-3 col-xs-12 control-label required" for="group">Group </label>
													<div class="col-md-9 col-sm-9 col-xs-12 form-group">
														<c:choose>
														<c:when test="${loginDetail.type=='Patient'}">
															<div class="row style-select">
																<div class="col-md-12">
																		<form:select multiple="true" path="groupSelectedList"  id="grouplstBox2" style="width:100%" >
																		<form:options items="${groupSelectedList}" itemLabel="groupName" itemValue="id" />
																		</form:select>
																	
																		<form:hidden path="selectedGroupId" id="selectedGroupId" name="selectedGroupId"/>
																		<form:hidden path="groupId" id="groupId" />
																	
																</div>
															</div>	
														</c:when>
														<c:otherwise><div class="row style-select">
																<div class="col-md-12">
																<input type="text" value="${patientAccount.groupName}"
																	title="${patientAccount.groupName}"
																	class="form-control" readonly="readonly" id="groupName"
																	name="groupName">
																<form:hidden path="selectedGroupId" id="selectedGroupId" name="selectedGroupId"/>
																<form:hidden path="groupId" id="groupId" />
															</div>
															</div>
														</c:otherwise></c:choose>
													</div>
												</div>
											</c:otherwise>
										</c:choose>
									</c:otherwise>--%>
								</c:choose>
							</div>
						</div>

						<div class="row">
							<label class="col-md-3 col-sm-3 col-xs-12 control-label required" for="physician-name">Physician</label>
							<c:choose>
								<c:when test="${loginDetail.type != 'Physician' and loginDetail.type != 'Physician Assistant' and loginDetail.type != 'Patient'}">
									<div class="col-md-12 col-sm-12 col-xs-12 form-group">
										<div class="subject-info-box-1">
											<label>Physicians List</label>
											<form:select multiple="true" path="physicianList"  id="lstBox1" style="width:100%" >
												<form:options items="${physicianList}" itemLabel="physicianName" itemValue="id" />
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
											<label>Physicians Selected</label>
											<form:select multiple="true" path="physicianSelectedList"  id="lstBox2" style="width:100%" >
												<form:options items="${physicianSelectedList}" itemLabel="physicianName" itemValue="id" />
											</form:select>
											
											<form:hidden path="selectedPhysicianId"/>
											<form:hidden path="physicianId"/>
										</div>
										<div class="clearfix"></div>
									</div>
								</c:when>
								<c:otherwise><c:choose>
									<c:when test="${loginDetail.type=='Patient'}">
										<div class="col-md-9 col-sm-9 col-xs-12 form-group">
											<form:select multiple="true" path="physicianSelectedList"  id="lstBox2" style="width:100%" >
												<form:options items="${physicianSelectedList}" itemLabel="physicianName" itemValue="id" />
											</form:select>
											<form:hidden path="selectedPhysicianId"/>
											<form:hidden path="physicianId"/>
										</div>
									</c:when>
									<c:otherwise>
										<div class="col-md-9 col-sm-9 col-xs-12 form-group">
											<input type="text" value="${patientAccount.physicianName}" title="${patientAccount.physicianName}" class="form-control" readonly="readonly" >
											<form:hidden path="selectedPhysicianId"/>
											<form:hidden path="physicianId"/>
										</div>
									</c:otherwise>
								</c:choose>
							</c:otherwise>
						</c:choose>
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
			                        	<form:hidden path="sendMailPermission" />
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
			                        	<div class="checkboxbox"><form:checkbox path="commTrackingNo"  id="commTrackingNo" value="Yes" onclick="setCheckBoxValues(1)"/></div>
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
			                        	<div class="checkboxbox"><form:checkbox path="commDelivered" id="commDelivered" value="Yes" onclick="setCheckBoxValues(3)" /></div>
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

           	
			<!-- Other Informations -->
           	<div class="x_panel">
				<div class="x_content x_title_padding">
					<div class="row">
                    	<div class="col-md-12 col-sm-12 col-xs-12 profile_left">
							<div class="row">
								<label class="col-md-3 col-sm-3 col-xs-12 control-label required" for="dob">Date of Birth <font size="-2" >(MM/DD/YYYY)</font></label>
								<div class='col-md-9 col-sm-3 col-xs-12 input-group form-group' >
									
									<div class='col-md-5 col-sm-4 col-xs-4' >
										<form:select path = "dobMonth" class="select2_single form-control">
	                     					<form:option value = "0" label = "Month"/>
	                     					<form:options items = "${dobMonthList}" />
	                  					</form:select>
									</div>
                  					
									<div class='col-md-3 col-sm-4 col-xs-4' >
										<form:select path = "dobDate" class="select2_single form-control">
	                     					<form:option value = "0" label = "Date"/>
	                     					<form:options items = "${dobDateList}" />
	                  					</form:select>
									</div>
					
									<div class='col-md-4 col-sm-4 col-xs-4' >
	                  					<form:select path = "dobYear" class="select2_single form-control">
	                     					<form:option value = "0" label = "Year"/>
	                     					<form:options items = "${dobYearList}" />
	                  					</form:select>
									</div>
                  					
									<form:hidden path="dateOfBirth" class="form-control" />
									<%-- <form:input path="dateOfBirth" class="form-control" /> --%>
									<form:hidden path="notifyNo"/>
									<form:hidden path="rxNotifyProviderTypeID"/>
									<form:hidden path="rxNotify"/>
									<form:hidden path="refillRenewal"/>
								</div>
							</div>
							
							<div class="row">
								<div class="col-md-3 col-sm-3 col-xs-12">
									<label class=" control-label ">Age </label>
								</div>
								<div class="col-md-5 col-sm-3 col-xs-12 form-group">
									<input type="text" class="form-control displayAge" id="displayAge" name="displayAge" readonly="readonly" >
								</div>
							</div>
							
							
							<div class="row">
								<label class="col-md-3 col-sm-3 col-xs-12 control-label required" for="gender">Gender</label>
								<div class="col-md-5 col-sm-3 col-xs-12 form-group" style="float: left">
									<form:select path="gender" class="select2_single form-control">
										<form:option value="">Select</form:option>
										<form:option value="M">Male</form:option>
										<form:option value="F">Female</form:option>
									</form:select>
								</div>
							</div>
							
							<div class="row">
								<label class="col-md-3 col-sm-3 col-xs-12 control-label"></label>
								<div class="col-md-9 col-sm-9 col-xs-12 form-group" style="float: left;">
									<div class="checkboxbox">
										<form:checkbox path="pregnancyprecaution" value="Yes" />
									</div>
									<div class="checkboxValue control-label marginn4">Ignore Pregnancy precautions</div>
								</div>
							</div>

							<div class="row">
								<label class="col-md-3 col-sm-3 col-xs-12 control-label" for="allergies">Allergies </label>
								<div class="col-md-9 col-sm-9 col-xs-12 form-group">
									<form:textarea path="allergies" rows="3" class="form-control" />
								</div>
							</div>
							
							<div class="row">
								<label class="col-md-3 col-sm-3 col-xs-12 control-label" for="otherMedical">Other Medications </label>
								<div class="col-md-9 col-sm-9 col-xs-12 form-group">
									<form:textarea path="otherMedications" rows="3" class="form-control" />
								</div>
							</div>
							
							<div class="row">
								<label class="col-md-3 col-sm-3 col-xs-12 control-label" for="medicalCondition">Medical Conditions </label>
								<div class="col-md-9 col-sm-9 col-xs-12 form-group">
									<form:textarea path="medicalConditions" rows="3" class="form-control" />
								</div>
							</div>
							<div class="row">
								<div class="col-md-3 col-sm-4 col-xs-12">
									<label for="status" class="control-label">Sync Status</label>
								</div>
								<div class="col-md-5 col-sm-8 col-xs-12 form-group">
									<form:select path="syncStatus" class="select2_single form-control">
										<form:options items="${patientSyncStatusTypeList}" itemLabel="syncStatusTypeText" itemValue="syncStatusTypeID" />
									</form:select>
								</div>
							</div>	  
							
							<div class="row">
								<div class="col-md-3 col-sm-4 col-xs-12">
									<label class="control-label" for="statusChangedDate">Status Changed Date</label>
								</div>
								<div class="col-md-5 col-sm-4 col-xs-12 form-group" >
									<form:input path="syncStatusChangedDate" id="syncStatusChangedDate" class="form-control calendarIcon" disabled="true"/>
								</div>
							</div>
							
							<div class="col-md-12 col-sm-12 col-xs-12 patientBalance blackDiv">

							</div>
							
                   		</div>
					</div>
				</div>
			</div>
			
			
			<c:if test="${loginDetail.type != 'Admin' and loginDetail.type != 'Super Admin' and loginDetail.type != 'Group Director'}">
				<div class="x_panel">
                	<div class="x_title">
                    	<h2>Critical comments</h2>
                    	<div class="clearfix"></div>
                  	</div>

                  	<div class="x_content x_title_padding">
						<div class="row">
	                    	<div class="col-md-12 col-sm-12 col-xs-12 profile_left checkboxDiv">
		                        <form:textarea path="criticalComments" cols="30" rows="5" class="form-control commentBlock" />
                    		</div>
						</div>
                  	</div>
				</div>
			</c:if>
			
		</div>
	</div>
		
		<div class="row">
			<div class="col-md-12 col-sm-12 col-xs-12">
				<div class="x_panel">
                	<div class="x_title">
                    	<h2>Documents</h2>
                    	<div class="clearfix"></div>
                  	</div>
                  	<div class="x_content x_title_padding documentBlock">
                  	
                  	<c:if test="${loginDetail.type != 'Physician' or (loginDetail.type == 'Physician' and loginDetail.status != 'New Modifications') }">
			
						<c:choose>
						<c:when test="${(loginDetail.type == 'Administrator' or loginDetail.type == 'Super Admin' or loginDetail.type == 'Admin')}">
						<c:if test="${(loginDetail.patientCreationPermission =='Yes')}">
							<div class="row form-group">
	                    	<div class="col-md-12 col-sm-12 col-xs-12 fileTagLoc">
	                    		<input type="file" name="docFiles" />
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
		                    	<div class="col-md-12 col-sm-12 col-xs-12">
		                    		<button type="button" class="btn btn-success uploadDocFile">Upload File</button>
		                    	</div>
		                    </div>
						</c:if>
						</c:when>
						<c:otherwise>
							<div class="row form-group">
	                    	<div class="col-md-12 col-sm-12 col-xs-12 fileTagLoc">
	                    		<input type="file" name="docFiles" />
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
		                    	<div class="col-md-12 col-sm-12 col-xs-12">
		                    		<button type="button" class="btn btn-success uploadDocFile">Upload File</button>
		                    	</div>
		                    </div>
						</c:otherwise>
						</c:choose>
						
						
					</c:if>
			
                  	
                  	
                  	
						
	                    
						<div class="row col-md-12 col-sm-12 col-xs-12 table-responsive">
							<table id="physicianDocFilesTable" class="table table-striped table-bordered">
								<thead>
									<tr class="heading_background">
										<th>File Id</th>
										<th>File Name</th>
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
		

        <div class="row form-group">
			<div class="col-md-12 col-sm-12 col-xs-12 profile_left">
				<div class="x_panel">
                 	<div class="x_content x_title_padding">
                   		<div class="row">
							<div class="col-md-6 col-sm-6 col-xs-12 text-right">
								<c:choose>
									<c:when test="${(loginDetail.type == 'Administrator' or loginDetail.type == 'Super Admin' or loginDetail.type == 'Admin')}">
										<c:if test="${(loginDetail.patientCreationPermission =='Yes')}">
											<button type="submit"  class="btn btn-success">Save</button>
										</c:if>
									</c:when>
									<c:otherwise>
										<button type="submit"  class="btn btn-success">Save</button>
									</c:otherwise>
								</c:choose>
								<!-- <input type="submit" id="submitpatientprofileHidden" style="display: none;"> -->
							</div>
							<!-- <div class="col-md-6 col-sm-6 col-xs-12 form-group text-left">
										<button type="button"  class="btn btn-success" onclick="sendLoginEmail();">Send Credentials Email</button>
									</div> -->
							<div class="col-md-12 col-sm-12 col-xs-12 text-center formErrorMsg" style="display:none;">
								<b>Error:</b> <spring:eval expression="@commonConfigurer.getProperty(\'error.form_fields_not_valid\')" />
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		
		
		
		
		
		
		
		<c:if test="${loginDetail.type != 'Patient'}">
			<div class="row">
				<c:choose>
					<c:when test="${not empty patientAccount.prescriptionId and patientAccount.prescriptionId!='0'}">
						<div class="col-md-3 col-sm-3 col-xs-6 formLink">
							<img class="img-responsive goSummary" src="${pageContext.request.contextPath}/resources/images/back_icon-3.png" title="Go to Summary">
						</div>
						<div class="col-md-9 col-sm-9 col-xs-6">
							<button type="button" class="btn btn-primary backPrescription" >Back to Prescription</button>
						</div>
					</c:when>
					<c:otherwise>
						<div class="col-md-12 col-sm-12 col-xs-12 formLink">
							<img class="img-responsive goSummary" src="${pageContext.request.contextPath}/resources/images/back_icon-3.png" title="Go to Summary">
						</div>
					</c:otherwise>
				</c:choose>
			</div>
		</c:if>		
	</div>
	
	
	
   	<form:hidden path="patientId" />
   	<form:hidden path="targetObject" />
   	<form:hidden path="prescriptionId" />
   	<form:hidden path="password" />
   	
   	
   	<input type="hidden" id="loggedUserType" value="${loginDetail.type}" />
   	
   	<input type="hidden" id="err_firstname" value='<spring:eval expression="@patientConfigurer.getProperty(\'error.first_name\')" />' />
	<input type="hidden" id="err_lastname" value='<spring:eval expression="@patientConfigurer.getProperty(\'error.last_name\')" />' />
	<input type="hidden" id="err_status" value='<spring:eval expression="@patientConfigurer.getProperty(\'error.status\')" />' />
	<input type="hidden" id="err_street" value='<spring:eval expression="@patientConfigurer.getProperty(\'error.street\')" />' />
	<input type="hidden" id="err_city" value='<spring:eval expression="@patientConfigurer.getProperty(\'error.city\')" />' />
	<input type="hidden" id="err_state" value='<spring:eval expression="@patientConfigurer.getProperty(\'error.state\')" />' />
	<input type="hidden" id="err_zip" value='<spring:eval expression="@physicianConfigurer.getProperty(\'error.zip\')" />' />
	<input type="hidden" id="err_zipcode" value='<spring:eval expression="@patientConfigurer.getProperty(\'error.zipcode\')" />' />
	<input type="hidden" id="err_ssn" value='<spring:eval expression="@patientConfigurer.getProperty(\'error.ssn\')" />' />
	<input type="hidden" id="err_phone" value='<spring:eval expression="@patientConfigurer.getProperty(\'error.phone\')" />' />
	<input type="hidden" id="err_email" value='<spring:eval expression="@patientConfigurer.getProperty(\'error.email\')" />' />
	<input type="hidden" id="err_emailformat" value='<spring:eval expression="@patientConfigurer.getProperty(\'error.email_format\')" />' />
	<input type="hidden" id="err_password" value='<spring:eval expression="@patientConfigurer.getProperty(\'error.password\')" />' />
	<input type="hidden" id="err_phone_format" value='<spring:eval expression="@patientConfigurer.getProperty(\'error.phone_format\')" />' />
	
	<input type="hidden" id="error_pwd_min_length" value='<spring:eval expression="@commonConfigurer.getProperty(\'error.password_min_length\')" />' />
	
	
	
	
	
	<input type="hidden" id="err_emailAlreadyExists" value='<spring:eval expression="@patientConfigurer.getProperty(\'error.emailAlreadyExists\')" />' />
	<input type="hidden" id="err_physician" value='<spring:eval expression="@patientConfigurer.getProperty(\'error.physician\')" />' />
	<input type="hidden" id="err_savingDB" value='<spring:eval expression="@patientConfigurer.getProperty(\'error.savingDB\')" />' />
	<input type="hidden" id="err_mobile" value='<spring:eval expression="@patientConfigurer.getProperty(\'error.mobile\')" />' />
	<input type="hidden" id="err_mobileformat" value='<spring:eval expression="@patientConfigurer.getProperty(\'error.mobile_format\')" />' />
	<input type="hidden" id="err_zip_format" value='<spring:eval expression="@physicianConfigurer.getProperty(\'error.zip_format\')" />' />
	<input type="hidden" id="err_status" value='<spring:eval expression="@physicianConfigurer.getProperty(\'error.state\')" />' />
	<input type="hidden" id="err_ssnformat" value='<spring:eval expression="@patientConfigurer.getProperty(\'error.ssnformat\')" />' />
	<input type="hidden" id="err_driving_license" value='<spring:eval expression="@patientConfigurer.getProperty(\'error.driving_license\')" />' />
	<input type="hidden" id="err_license_state" value='<spring:eval expression="@patientConfigurer.getProperty(\'error.license_state\')" />' />
	<input type="hidden" id="err_license_state_valid" value='<spring:eval expression="@patientConfigurer.getProperty(\'error.license_state_valid\')" />' />
	<input type="hidden" id="err_license_exp_date" value='<spring:eval expression="@patientConfigurer.getProperty(\'error.license_exp_date\')" />' />
	<input type="hidden" id="err_license_exp_date_exp" value='<spring:eval expression="@patientConfigurer.getProperty(\'error.license_exp_date_exp\')" />' />
	<input type="hidden" id="err_future_dob" value='<spring:eval expression="@patientConfigurer.getProperty(\'error.future_dob\')" />' />
	<input type="hidden" id="err_gender" value='<spring:eval expression="@patientConfigurer.getProperty(\'error.gender\')" />' />
	<input type="hidden" id="err_dob" value='<spring:eval expression="@patientConfigurer.getProperty(\'error.dob\')" />' />
	<input type="hidden" id="err_user_login_id" value='<spring:eval expression="@patientConfigurer.getProperty(\'error.user_login_id\')" />' />
	<input type="hidden" id="err_user_login_length" value='<spring:eval expression="@patientConfigurer.getProperty(\'error.user_login_length\')" />' />
	<input type="hidden" id="err_group" value='<spring:eval expression="@physicianConfigurer.getProperty(\'error.group\')" />' />
	
	
	<input type="hidden" id="err_dob_format" value='<spring:eval expression="@patientConfigurer.getProperty(\'error.dob_format\')" />' />
	
	
	<input type="hidden" id="err_cardType" value='<spring:eval expression="@physicianConfigurer.getProperty(\'error.cardType\')" />' />
	<input type="hidden" id="err_cardNo" value='<spring:eval expression="@physicianConfigurer.getProperty(\'error.card_number\')" />' />
	<input type="hidden" id="err_cardNo_format" value='<spring:eval expression="@physicianConfigurer.getProperty(\'error.card_number_format\')" />' />
	<input type="hidden" id="err_cvcNo" value='<spring:eval expression="@physicianConfigurer.getProperty(\'error.cvc_number\')" />' />
	<input type="hidden" id="err_cvcNo_format" value='<spring:eval expression="@physicianConfigurer.getProperty(\'error.cvc_number_format\')" />' />
	<input type="hidden" id="err_cardHolder" value='<spring:eval expression="@physicianConfigurer.getProperty(\'error.card_holder_name\')" />' />
	<input type="hidden" id="err_cardExpMonth" value='<spring:eval expression="@physicianConfigurer.getProperty(\'error.card_exp_month\')" />' />
	<input type="hidden" id="err_cardExpYear" value='<spring:eval expression="@physicianConfigurer.getProperty(\'error.card_exp_year\')" />' />
	<input type="hidden" id="err_cardExpYear_format" value='<spring:eval expression="@physicianConfigurer.getProperty(\'error.card_exp_year_format\')" />' />
	<input type="hidden" id="err_cc_expiry" value='<spring:eval expression="@physicianConfigurer.getProperty(\'error.credit_card_expiry\')" />' />
	<input type="hidden" id="err_cc_expiry_year" value='<spring:eval expression="@physicianConfigurer.getProperty(\'error.credit_card_expiry_year\')" />' />
	
	<input type="hidden" id="err_cardExpMonthYear_format" value='<spring:eval expression="@physicianConfigurer.getProperty(\'error.credit_card_expiry\')" />' />
	
	<input type="hidden" id="err_billing_zip" value='<spring:eval expression="@physicianConfigurer.getProperty(\'error.billing_zip\')" />' />
	<input type="hidden" id="err_billing_zip_format" value='<spring:eval expression="@physicianConfigurer.getProperty(\'error.billing_zip_format\')" />' />
	
	<input type="hidden" id="err_driving_or_alternate_id" value='<spring:eval expression="@patientConfigurer.getProperty(\'error.driving_or_alternate_id\')" />' />
</form:form>
<script>
var urlPath="${pageContext.request.contextPath}/";
</script>
<%--includes the Front End UI validations--%>
<script src="${pageContext.request.contextPath}/resources/js/pages/patient/patientAccount.js?v=1.2"></script>

<c:choose>
	<c:when test="${(loginDetail.type == 'Administrator' or loginDetail.type == 'Super Admin' or loginDetail.type == 'Admin')}">
	<c:if test="${(loginDetail.patientCreationPermission !='Yes')}">
	<script>
		fnShowHide(5);	
	</script>
	</c:if>
	</c:when>
</c:choose>