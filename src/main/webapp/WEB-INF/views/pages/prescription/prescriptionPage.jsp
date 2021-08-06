 <%--
 Front End UI to save and load the Precriptions of the Patient by Physician
 --%>
<%@ include file="../../../layout/taglib.jsp" %>
   <link href="${pageContext.request.contextPath}/resources/css/popup.css?v=2" rel="stylesheet" />


<!-- page content -->
<div class="">
	<form:form method="POST" action="savePrescription" name="prescription" commandName="prescription" enctype="multipart/form-data"  >
	<div class="row">
		<div class="col-md-3 col-sm-3 col-xs-3">
			<img class="img-responsive goSummary" src="${pageContext.request.contextPath}/resources/images/back_icon-3.png" title="Go to Summary">
		</div>
		
		<div class="col-md-2 col-sm-2 col-xs-2 text-right ">
			<c:if test="${loginDetail.type == 'Super Admin' or loginDetail.type == 'Admin' or (loginDetail.type == 'Physician' and loginDetail.status != 'New Modifications') 
						or loginDetail.type == 'Physician Assistant' or loginDetail.type == 'Group Director' }" >
				
				<c:choose>
				<c:when test="${(loginDetail.type == 'Administrator' or loginDetail.type == 'Super Admin' or loginDetail.type == 'Admin')}">
				<c:if test="${(loginDetail.prescriptionCreationPermission =='Yes')}">
					<button type="button" class="btn btn-primary goNewRec" >Create New Prescription</button>
				</c:if>
				</c:when>
				<c:otherwise>
					<button type="button" class="btn btn-primary goNewRec" >Create New Prescription</button>
				</c:otherwise>
				</c:choose>
				
				
			</c:if>
		</div>
		<div class="col-md-2 col-sm-2 col-xs-2 text-right">
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
			<%-- <div class="col-md-3 col-sm-3 col-xs-3">
		<c:choose>
						    	<c:when test="${loginDetail.type == 'Physician'}">
						        	<div class="col-md-6 col-sm-6 col-xs-12 text-left">
										<button type="button" class="btn btn-primary" id="myModal" onclick="signatureClear();div_show(this)" title="Generate PDF">
									   		Generate PDF
										</button>
									</div>
						       	</c:when>
								<c:when test="${loginDetail.type != 'Patient' and loginDetail.type != 'Physician'}">
						        	<div class="col-md-6 col-sm-6 col-xs-12 text-left">
										<button type="button" class="btn btn-primary" id="myModal" onclick="showEsignWarningAlert()">
									   		Generate PDF
										</button>
									</div>
								</c:when>
						       	<c:otherwise>
						        	<div class="col-md-6 col-sm-6 col-xs-12 text-right"></div>
						       	</c:otherwise>
						  	</c:choose>
						  	</div> --%>
		<div class="col-md-3 col-sm-3 col-xs-3">
			<c:if test="${prescription.orderId != 0}">
				<div class="row">
					<div class="col-md-2 col-sm-3 col-xs-6"></div>
					<div class="col-md-4 col-sm-3 col-xs-6">
						<button type="button" class="btn btn-primary backOrder" >Back to Order</button>
					</div>
				</div>
			</c:if>
			<c:if test="${prescription.invoiceId != 0}">
				<div class="row">
					<div class="col-md-2 col-sm-3 col-xs-6"></div>
					<div class="col-md-4 col-sm-3 col-xs-6">
						<button type="button" class="btn btn-primary backInvoice" >Back to Invoice</button>
					</div>
				</div>
			</c:if>
		</div>
		
		<c:if test="${loginDetail.type != 'Patient' and prescription.prescriptionId > 0}">
			<div class="col-md-3 col-sm-3 col-xs-3 text-right">
				<div class="row">
					<div class="col-md-4 col-sm-4 col-xs-4 text-right">
					<c:if test="${prescription.esignedPDF}">
						 <img class=" pdfDownload" src="${pageContext.request.contextPath}/resources/images/pdf-icon-2.png" title="PDF Download" style="height:36px;" style="cursor:pointer" />
					</c:if>
					</div>
					<div class="col-md-3 col-sm-3 col-xs-3 text-right">
					<c:if test="${prescription.csesignedPDF}">
						 <img class=" cspdfDownload" src="${pageContext.request.contextPath}/resources/images/pdf-icon-2.png" title="Controlled Substance PDF Download" style="height:36px;" style="cursor:pointer" />
					</c:if>
					</div>
					<div class="col-md-3 col-sm-3 col-xs-3 text-right">
							<c:choose>
							<c:when test="${(loginDetail.type == 'Administrator' or loginDetail.type == 'Super Admin' or loginDetail.type == 'Admin')}">
							<c:if test="${(loginDetail.remoteFileUploadPermission =='Yes')}">
<%-- 								<img class=" pdfFtpUpload" src="${pageContext.request.contextPath}/resources/images/ftp_upload.png" title="Remote File Upload" style="cursor:pointer"/>
 --%>							<button type="button" class="btn btn-primary pdfFtpUpload" >Send RX to CRE8 Pharmacy</button>
							</c:if>
							</c:when>
							<c:otherwise>
<%-- 								<img class=" pdfFtpUpload" src="${pageContext.request.contextPath}/resources/images/ftp_upload.png" title="Remote File Upload" style="cursor:pointer"/>
 --%>							<button type="button" class="btn btn-primary pdfFtpUpload" >Send RX to CRE8 Pharmacy</button>
							</c:otherwise>
							</c:choose>
						 
					</div>
				</div>
			</div>
		</c:if>
	</div>
	
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
	<script src="${pageContext.request.contextPath}/resources/js/esignature.js?v=6"></script>
		<div class="row">
	       	<div class="col-md-12 col-sm-12 col-xs-12">
	       		<div class="x_panel">
					<div class="x_title">
	                	<h2>Prescription</h2>
	                  	<div class="clearfix"></div>
	               	</div>
	               	<div class="x_content x_title_padding">
						<div class="row">
							<div class="col-md-12 col-sm-12 col-xs-12">
								<div class="row">
									<c:choose>
										<c:when test="${loginDetail.type == 'Physician' or loginDetail.type == 'Physician Assistant'}">
											<div class="col-md-4 col-sm-12 col-xs-12">
												<label class="col-md-4 col-sm-4 col-xs-4 control-label paddingRight5 required" for="writtenByName">Written By</label>
												<div class="col-md-8 col-sm-8 col-xs-8 form-group">
													<form:hidden path="physicianId" value="${writtenById}" />
													<form:hidden path="physicianName" value="${writtenByName}" />
													<form:hidden path="writtenBy" value="${writtenById}" />
													<form:input path="writtenByName" value="${writtenByName}" class="form-control" readonly="true" />
												</div>
											</div>
										</c:when>
										<c:otherwise>
											<div class="col-md-4 col-sm-12 col-xs-12">
											
												<label class="col-md-4 col-sm-4 col-xs-4 control-label paddingRight9 required" for="physicianId">Written By</label>
												<div class="col-md-8 col-sm-8 col-xs-8 form-group">
													<select class="autoCompleterPhysicianId select2_single form-control" style="width:100%;color:black;" name="autoCompleterPhysicianId"  id="autoCompleterPhysicianId"></select>
													<form:hidden path="physicianId" id="physicianId"/>
													<form:hidden path="physicianName" id="physicianName" />
													<form:hidden path="writtenBy" id="writtenBy"/>
													<form:hidden path="writtenByName" id="writtenByName" /> 
												</div>
											</div>
										</c:otherwise>
									</c:choose>
									<div class="col-md-4 col-sm-12 col-xs-12">
										<label class="col-md-3 col-sm-4 col-xs-4 control-label paddingRight9 required" for="patientId">Patient</label>
										<div class="col-md-8 col-sm-8 col-xs-8 form-group">
											<c:choose>
												<c:when test="${loginDetail.type != 'Patient'}">
													<select class="autoCompleterPatientId select2-single form-control" style="width:100%;color:black;" name="autoCompleterPatientId"  id="autoCompleterPatientId"></select>
													<form:hidden path="patientId" id="patientId"/>
												</c:when>
												<c:otherwise>
													<form:input path="patientName" class="form-control" readonly="true" />
													<form:hidden path="patientId" />
												</c:otherwise>
											</c:choose>
										</div>
									</div>
									<div class="col-md-3 col-sm-12 col-xs-12">
										<label class="col-md-3 col-sm-4 col-xs-4 control-label" for="groupName">Group</label>
										<div class="col-md-8 col-sm-8 col-xs-8 form-group">
											<form:input path="groupName" class="form-control" readonly="true" />
											<form:hidden path="groupId" />
										</div>
									</div>
								</div>
								
								<div class="row">
									<div class="col-md-4 col-sm-12 col-xs-12">
										<label class="col-md-4 col-sm-4 col-xs-4 control-label" for="prescriptionNumber">Prescription #</label>
										<div class="col-md-8 col-sm-8 col-xs-8 form-group">
											<form:input path="prescriptionOrderNumber" class="form-control" readonly="true" />
										</div>
									</div>
									 <div class="col-md-7 col-sm-12 col-xs-12">
										<label class="col-md-3 col-sm-12 col-xs-12 control-label" for="groupName">Prescription Status</label>
										<div class="col-md-7 col-sm-12 col-xs-12 form-group">
											<form:select path="statusId" class="select2_single form-control">
												<form:options items="${prescriptionStatusList}" itemLabel="status" itemValue="id" />
											</form:select>
										</div>
									</div> 
									<div class="col-md-5 col-sm-12 col-xs-12">
										<div class="col-md-5 col-sm-12 col-xs-12 form-group">
											<div class="checkboxbox"><form:checkbox path="scan" id="scan" value="Y" onchange="setPrescriptionScanorFax(1)" /></div>
											<div class="checkboxValue">Prescription Scanned</div>
										</div>
										<div class="col-md-6 col-sm-12 col-xs-12 form-group">
											<div class="checkboxbox"><form:checkbox path="fax"  id="fax" value="Y" onchange="setPrescriptionScanorFax(2)" /></div>
											<div class="checkboxValue">Prescription Faxed</div>
										</div>
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
	       		<div class="x_panel">
					<div class="x_title">
	                	<h2>Physician Info</h2>
	                  	<div class="clearfix"></div>
	               	</div>
	               	<div class="x_content x_title_padding">
						<div class="row">
							<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
								<div class="row">
										 <div class="col-md-6 col-sm-12 col-xs-12">
											<label class="col-md-3 col-sm-6 col-xs-6 control-label" for="clinicName">Clinic</label>
											<div class="col-md-9 col-sm-6 col-xs-6 form-group">
												<c:choose>
							    					<c:when test="${loginDetail.type != 'Patient' and clinicOptNotShow == 'false'}">
														<form:select path="clinicId" id="clinicId" class="select2_single form-control" >
															<form:option value="0">Select</form:option>
															<form:options items="${clinicList}" itemLabel="clinicName" itemValue="id" />
														</form:select>
							    					</c:when>
							    					<c:otherwise>
														<form:input path="clinicName" class="form-control" readonly="true" />
														<form:hidden path="clinicId" id="clinicId"/>
							    					</c:otherwise>
							    				</c:choose>
											</div>
										</div> 
									<div class="col-md-6 col-sm-12 col-xs-12 text-right">
										<c:if test="${loginDetail.type != 'Patient' and prescription.prescriptionId > 0}">
											<button type="button" id="goPhysicianProfile" class="btn btn-primary">View Physician's Profile</button>
										</c:if>
									</div>
								</div>
								<div class="row">
									<div class="col-md-6 col-sm-12 col-xs-12">
										<label class="col-md-3 col-sm-6 col-xs-6 control-label " for="clinicAddress">Address</label>
										<div class="col-md-9 col-sm-6 col-xs-6 form-group">
											<form:input path="phyAddress" class="form-control" readonly="true" />
										</div>
									</div>
									<div class="col-md-6 col-sm-12 col-xs-12">
										<label class="col-md-3 col-sm-6 col-xs-6 control-label fieldTop" for="physicianDea">DEA </label>
		                        		<div class="col-md-9 col-sm-6 col-xs-6">
		                          			<form:input path="phyDea" class="form-control" maxlength="25"  readonly="true" />
		                        		</div>
									</div>
								</div>
								<div class="row">
									<div class="col-md-6 col-sm-12 col-xs-12">
										<label class="col-md-3 col-sm-6 col-xs-6 control-label fieldTop" for="clinicCity">City</label>
										<div class="col-md-9 col-sm-6 col-xs-6 form-group">
											<form:input path="phyCity" class="form-control" readonly="true" />
										</div>
									</div>
									<div class="col-md-6 col-sm-12 col-xs-12">
										<label class="col-md-3 col-sm-6 col-xs-6 control-label fieldTop" for="state">State License #</label>
		                        		<div class="col-md-9 col-sm-6 col-xs-6">
		                          			<form:input path="phyStateLicense" class="form-control" maxlength="25"  readonly="true" />
		                        		</div>
									</div>
									
								</div>
								<div class="row">
									<div class="col-md-6 col-sm-12 col-xs-12">
										<label class="col-md-3 col-sm-6 col-xs-6 control-label fieldTop" for="clinicState">State</label>
										<div class="col-md-9 col-sm-6 col-xs-6 form-group">
											<form:input path="phyState" class="form-control" readonly="true" />
										</div>
									</div>
									<div class="col-md-6 col-sm-12 col-xs-12">
										<label class="col-md-3 col-sm-6 col-xs-6 control-label fieldTop" for="physicianNpi">NPI </label>
		                        		<div class="col-md-9 col-sm-6 col-xs-6">
		                          			<form:input path="phyNpi" class="form-control" maxlength="25"  readonly="true"  />
		                        		</div>
									</div>
								</div>
								<div class="row">
									<div class="col-md-6 col-sm-12 col-xs-12">
										<label class="col-md-3 col-sm-6 col-xs-6 control-label fieldTop" for="clinicZipCode">Zip Code</label>
										<div class="col-md-9 col-sm-6 col-xs-6 form-group">
											<form:input path="phyZipCode" class="form-control" readonly="true" />
											<form:hidden path="phyCountry" />
										</div>
									</div>
									<div class="col-md-6 col-sm-12 col-xs-12">
										<label class="col-md-3 col-sm-6 col-xs-6 control-label fieldTop" for="clinicPhone">Phone</label>
										<div class="col-md-9 col-sm-6 col-xs-6 form-group">
											<form:input path="phyPhone" class="form-control" readonly="true" />
											<form:hidden path="phyUpin"/>
											<form:hidden path="phyMedicaid"/>
										</div>
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
	       		<div class="x_panel">
					<div class="x_title">
	                	<h2>Patient Info</h2>
	                  	<div class="clearfix"></div>
	               	</div>
	               	<div class="x_content x_title_padding">
						<div class="row">
							<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
								<div class="row">
									<div class="col-md-6 col-sm-12 col-xs-12">
										<label class="col-md-3 col-sm-6 col-xs-6 control-label" for="patientName">Patient</label>
										<div class="col-md-9 col-sm-6 col-xs-6 form-group">
											<form:input path="patientName" class="form-control" readonly="true" />
										</div>
									</div>
									<div class="col-md-6 col-sm-12 col-xs-12 text-right">
										<c:if test="${loginDetail.type != 'Patient' and prescription.prescriptionId > 0}">
											<button type="button" id="goProfile" class="btn btn-primary">View Patient's Profile</button>
										</c:if>
									</div>
								</div>
								<div class="row">
									<div class="col-md-6 col-sm-12 col-xs-12">
										<label class="col-md-3 col-sm-6 col-xs-6 control-label " for="patientAddress">Address</label>
										<div class="col-md-9 col-sm-6 col-xs-6 form-group">
											<form:input path="patientAddress" class="form-control" readonly="true" />
										</div>
									</div>
									<div class="col-md-6 col-sm-12 col-xs-12">
										<label class="col-md-4 col-sm-6 col-xs-6 control-label " for="dateOfBirth">Date of Birth</label>
										<div class="col-md-8 col-sm-6 col-xs-6 form-group">
											<form:input path="patientDateOfBirth" class="form-control" readonly="true" />
										</div>
									</div>
								</div>
								<div class="row">
									<div class="col-md-6 col-sm-12 col-xs-12">
										<label class="col-md-3 col-sm-6 col-xs-6 control-label " for="patientCity">City</label>
										<div class="col-md-9 col-sm-6 col-xs-6 form-group">
											<form:input path="patientCity" class="form-control" readonly="true" />
										</div>
									</div>
									<div class="col-md-6 col-sm-12 col-xs-12">
										<label class="col-md-4 col-sm-6 col-xs-6 control-label " for="patientPhone">Phone</label>
										<div class="col-md-8 col-sm-6 col-xs-6 form-group">
											<form:input path="patientPhone" class="form-control" readonly="true" />
										</div>
									</div>
								</div>
								<div class="row">
									<div class="col-md-6 col-sm-12 col-xs-12">
										<label class="col-md-3 col-sm-6 col-xs-6 control-label " for="patientState">State</label>
										<div class="col-md-9 col-sm-6 col-xs-6 form-group">
											<form:input path="patientState" class="form-control" readonly="true" />
										</div>
									</div>
									<div class="col-md-6 col-sm-12 col-xs-12">
										<label class="col-md-4 col-sm-6 col-xs-6 control-label " for="mobile">Mobile</label>
										<div class="col-md-8 col-sm-6 col-xs-6 form-group">
											<form:input path="patientMobile" class="form-control" readonly="true" />
										</div>
									</div>
								</div>
								<div class="row">
									<div class="col-md-6 col-sm-12 col-xs-12">
										<label class="col-md-3 col-sm-6 col-xs-6 control-label" for="patientZipcode">Zip Code</label>
										<div class="col-md-9 col-sm-6 col-xs-6 form-group">
											<form:input path="patientZipCode" class="form-control" readonly="true" />
											<form:hidden path="patientCountry" />
										</div>
									</div>
									<div class="col-md-6 col-sm-12 col-xs-12">
										<label class="col-md-4 col-sm-6 col-xs-6 control-label " for="ssnNo">Last 4 digits of SSN #</label>
										<div class="col-md-8 col-sm-6 col-xs-6 form-group">
											<form:input path="patientSsn" class="form-control" readonly="true" />
										</div>
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
	       		<div class="x_panel">
					<div class="x_title">
	                	<h2>Condition</h2>
	                  	<div class="clearfix"></div>
	               	</div>
	               	<div class="x_content x_title_padding">
						<div class="row">
							<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
								<label class="col-md-12 col-sm-12 col-xs-12 control-label" for="allergies">Allergies</label>
								<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
									<form:textarea path="allergies" rows="5" cssClass="form-control" />
								</div>
							</div>
							<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
								<label class="col-md-12 col-sm-12 col-xs-12 control-label" for="clinicPo">Diagnosis</label>
								<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
									<form:textarea path="diagnosis" rows="5" cssClass="form-control" />
								</div>
							</div>
						</div>
	               	</div>
				</div>
			</div>
		</div>

		<div class="row" >
	       	<div class="col-md-6 col-sm-12 col-xs-12">
	       		<div class="x_panel" style="height: 186px;">
					<div class="x_title">
	                	<h2>Billing Info</h2>
	                  	<div class="clearfix"></div>
	               	</div>
	               	<div class="x_content x_title_padding">
						<div class="row">
							<div class="col-md-6 col-sm-12 col-xs-12 form-group" >
								<div class="btn-group">
									<label class="btn required">
					                    <form:radiobutton path="patientBillToId" value="1" /> Bill Clinic
					                </label>
					                <label class="btn ">
					                    <form:radiobutton path="patientBillToId" value="2" /> Bill Patient
					                </label>
				                </div>
							</div>
							<div class="col-md-6 col-sm-12 col-xs-12">
								<label class="col-md-6 col-sm-6 col-xs-6 control-label" for="clinicPo">Clinic's PO #</label>
								<div class="col-md-6 col-sm-6 col-xs-6 form-group">
									<form:input path="clinicPo" class="form-control " maxlength="25" />
								</div>
							</div>							
						</div>						
						<div class="row">
							<div class="col-md-12 col-sm-12 col-xs-12">
								<label class="col-md-4 col-sm-6 col-xs-6 control-label  required" for="billingAddress">Payment Type</label>
								<div class="col-md-8 col-sm-6 col-xs-6 form-group">
									<form:select path="paymentType" class="select2_single form-control">
									<%--    <form:option value="0" label="Select Payment Type"/>
									   <form:option value="creditcard" label="Credit Card"/>
									   <form:option value="terms" label="Terms"/>
									   <form:option value="payonpickup" label="Pay on Pickup"/>  --%>
									 <form:option value="0" label="Select Payment Type"/>
									   <form:option value="dailyARAccount" label="Daily A/R Account"/>
									   <form:option value="weeklyARAccount" label="Weekly A/R Account"/>
									   <form:option value="payatpickup" label="Pay at Pickup"/>
									    <form:option value="creditcard" label="Credit Card"/> 
									</form:select>
								</div>
							</div>
						</div>
						<%-- <div class="row paymentTerms" style="display:none;">
							<div class="col-md-12 col-sm-12 col-xs-12">
								<label class="col-md-4 col-sm-6 col-xs-6 control-label required" for="paymentTerms">Payment Terms</label>
								<div class="col-md-8 col-sm-6 col-xs-6 form-group">
									<form:input path="paymentTerms" class="form-control " />
								</div>
							</div>
						</div> --%>
						
						
					</div>
				</div>
			</div>
			
			<div class="col-md-6 col-sm-12 col-xs-12">
	       		<div class="x_panel">
					<div class="x_title">
	                	<h2>Shipping Info</h2>
	                  	<div class="clearfix"></div>
	               	</div>
	               	<div class="x_content x_title_padding">
						<div class="row">
							<div class="col-md-12 col-sm-12 col-xs-12 form-group" >
								<div class="btn-group">
									<label class="btn required">
					                    <form:radiobutton path="patientShipToId" value="1" /> Ship To Clinic
					                </label>
					                <label class="btn ">
					                    <form:radiobutton path="patientShipToId" value="2" /> Ship To Patient
					                </label>
				                </div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-12 col-sm-12 col-xs-12">
								<label class="col-md-5 col-sm-6 col-xs-6 control-label" for="shippingMethod">Method of Shipping</label>
								<div class="col-md-7 col-sm-6 col-xs-6 form-group">
									<form:select path="shippingMethod" class="select2_single form-control">
									   <form:option value="0" label="Select Method of Shipping"/>
									   <form:option value="Next Day Air - $40.00" label="Next Day Air - $40.00"/>
									   <%-- <form:option value="Priority AM" label="Overnight Priority"/> --%>
									   <form:option value="2nd Day - $24.00" label="2nd Day - $24.00"/>
									  <%--  <form:option value="3-day" label="3rd Day Delivery"/>
									   <form:option value="Saturday" label="Saturday"/> --%>
									   <form:option value="Ground - $15.00" label="Ground - $15.00"/>
									   <form:option value="Other" label="Other"/>
									</form:select>
								</div>
							</div>
						</div>
						
						<div class="col-md-12 col-md-12 col-sm-12 col-xs-12 photoNotes">
						<label><b>Note:</b>Any refrigerated formula MUST ship via Next Day Air/Overnight delivery when shipping outside the state of Florida</label>
						</div>   
						
							
							
						
						<div class="row divShippingMethod" style="display:none;">
							<div class="col-md-12 col-sm-12 col-xs-12">
								<label class="col-md-5 col-sm-6 col-xs-6 control-label" for="otherShippingMethod">Other Shipping Method</label>
								<div class="col-md-7 col-sm-6 col-xs-6 form-group">
									<form:input path="otherShippingMethod" class="form-control " maxlength="50" />
									<form:hidden path="shippingCompany" />
									<form:hidden path="otherShippingCompany" />
									<form:hidden path="shippingAccountNo" />
								</div>
							</div>
						</div>
						
						
					</div>
				</div>
			</div>			
		</div>
		
		<div class="row">
			<div class="col-md-6 col-sm-12 col-xs-12">
				<div class="x_panel">
					<div class="x_title">
	                	<h2>Billing Address</h2>
	                  	<div class="clearfix"></div>
	               	</div>
	               	<div class="x_content x_title_padding">
	               		<div class="row">
							<div class="col-md-12 col-sm-12 col-xs-12 form-group checkboxDiv">
								<div class="btn-group">
									<label class="btn ">
					                    <form:checkbox path="billingAddressId" value="1" /> Same As Physician Address 1
					                </label>
					                <label class="btn ">
					                    <form:checkbox path="billingAddressId" value="2" /> Same As Physician Address 2
					                </label>
					                <label class="btn ">
					                	<form:checkbox path="billingAddressId" value="3" /> Same As Patient Address
					                </label>
					                <label class="btn ">
					                	<form:checkbox path="billingAddressId" value="4" /> Same As Clinic Location
					                </label>
				                </div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-12 col-sm-12 col-xs-12">
								<label class="col-md-3 col-sm-6 col-xs-6 control-label" for="billingName">Billing Name</label>
								<div class="col-md-9 col-sm-6 col-xs-6 form-group">
									<form:input path="billingName" class="form-control " maxlength="100"  />
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-12 col-sm-12 col-xs-12">
								<label class="col-md-3 col-sm-6 col-xs-6 control-label" for="billingAddress">Address</label>
								<div class="col-md-9 col-sm-6 col-xs-6 form-group">
									<form:input path="billingAddress" class="form-control " />
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-12 col-sm-12 col-xs-12">
								<label class="col-md-3 col-sm-6 col-xs-6 control-label" for="billingCity">City</label>
								<div class="col-md-9 col-sm-6 col-xs-6 form-group">
									<form:input path="billingCity" class="form-control " maxlength="25" />
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-12 col-sm-12 col-xs-12">
								<label class="col-md-3 col-sm-6 col-xs-6 control-label" for="billingState">State</label>
								<div class="col-md-9 col-sm-6 col-xs-6 form-group">
									<form:input path="billingState" class="form-control " maxlength="3" />
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-12 col-sm-12 col-xs-12">
								<label class="col-md-3 col-sm-6 col-xs-6 control-label" for="billingZipCode">Zip Code</label>
								<div class="col-md-9 col-sm-6 col-xs-6 form-group">
									<form:input path="billingZipCode" class="form-control " maxlength="12" onkeypress="return isNumber(event)" />
									<form:hidden path="billingCountry"  />
								</div>
							</div>
						</div>
	               	</div>
				</div>
			</div>
			<div class="col-md-6 col-sm-12 col-xs-12">
			
				<div class="x_panel">
					<div class="x_title">
	                	<h2>Shipping Address</h2>
	                  	<div class="clearfix"></div>
	               	</div>
	               	<div class="x_content x_title_padding">
	               		<div class="row">
							<div class="col-md-12 col-sm-12 col-xs-12 form-group checkboxDiv">
		                        <div class="btn-group">
									<label class="btn ">
					                    <form:checkbox path="shippingAddressId" value="1" /> Same As Physician Address 1
					                </label>
					                <label class="btn ">
					                    <form:checkbox path="shippingAddressId" value="2" /> Same As Physician Address 2
					                </label>
					                <label class="btn ">
					                	<form:checkbox path="shippingAddressId" value="3" /> Same As Patient Address 1
					                </label>
					                <label class="btn ">
					                	<form:checkbox path="shippingAddressId" value="4" /> Same As Patient Address 2
					                </label>
					                <label class="btn ">
					                	<form:checkbox path="shippingAddressId" value="5" /> Temporary Address
					                </label>
					                <label class="btn ">
					                	<form:checkbox path="shippingAddressId" value="6" /> Same As Clinic Location
					                </label>
				                </div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-12 col-sm-12 col-xs-12">
								<label class="col-md-4 col-sm-6 col-xs-6 control-label required" for="shippingName">Shipping Name</label>
								<div class="col-md-8 col-sm-6 col-xs-6 form-group">
									<form:input path="shippingName" class="form-control " maxlength="100" />
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-12 col-sm-12 col-xs-12">
								<label class="col-md-4 col-sm-6 col-xs-6 control-label required" for="shippingAddress">Address</label>
								<div class="col-md-8 col-sm-6 col-xs-6 form-group">
									<form:input path="shippingAddress" class="form-control " />
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-12 col-sm-12 col-xs-12">
								<label class="col-md-4 col-sm-6 col-xs-6 control-label required" for="shippingCity">City</label>
								<div class="col-md-8 col-sm-6 col-xs-6 form-group">
									<form:input path="shippingCity" class="form-control " maxlength="25" />
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-12 col-sm-12 col-xs-12">
								<label class="col-md-4 col-sm-6 col-xs-6 control-label required" for="shippingState">State</label>
								<div class="col-md-8 col-sm-6 col-xs-6 form-group">
									<form:input path="shippingState" class="form-control " maxlength="3" />
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-12 col-sm-12 col-xs-12">
								<label class="col-md-4 col-sm-6 col-xs-6 control-label required" for="shippingZipCode">Zip Code</label>
								<div class="col-md-8 col-sm-6 col-xs-6 form-group">
									<form:input path="shippingZipCode" class="form-control " maxlength="12" onkeypress="return isNumber(event)" />
									<form:hidden path="shippingCountry" />
								</div>
							</div>
						</div>
	               	</div>
               	</div>
			</div>
		</div>

		<div class="row medicationTop">
			<div class="col-md-12 col-sm-12 col-xs-12">
	       		<div class="x_panel">
					<div class="x_title">
	                	<h2>Medications Dispensed</h2>
	                  	<div class="clearfix"></div>
	               	</div>
	               	<div class="x_panel">
		               	<c:if test="${loginDetail.type != 'Patient'}">
		               	
							<div class="x_content x_title_padding">
								
								<div class="row">
									<div class="col-md-12 col-sm-12 col-xs-12">
										<label class="col-md-2 col-sm-6 col-xs-6 control-label" for="prescriptionNo">Reference#</label>
										<div class='col-md-2 col-sm-6 col-xs-6 input-group form-group' style="padding: 0px 8px 0px; float: left;">
											<form:input path="prescriptionNo"  class="form-control" readonly="true"/>
										</div>
										<label class="col-md-1 col-sm-6 col-xs-6 control-label required" for="rxWrittenOn">Written</label>
										<div class='col-md-2 col-sm-6 col-xs-6 input-group form-group' style="padding: 0px 8px 0px; float: left;">
											<form:input path="rxWrittenOn"  class="form-control calendarIcon" />
											<div class="rxwrittern-errors error_fields"></div>
										</div>
									
										<label class="col-md-1 col-sm-6 col-xs-6 control-label required" for="rxExpireOn">Expire</label>
										<div class='col-md-2 col-sm-6 col-xs-6 input-group form-group' style="padding: 0px 8px 0px; float: left;">
											<form:input path="rxExpireOn"  class="form-control calendarIcon" />
											<div class="rxexpire-errors error_fields"></div>
										</div>
									</div>
								</div>
								<div class="row">
									<div class="col-md-12 col-sm-12 col-xs-12">
										<div class="col-md-3 col-sm-6 col-xs-6">
											<label class="col-md-4 col-sm-6 col-xs-6 control-label required" for="rxType">Type</label>
											<div class='col-md-8 col-sm-6 col-xs-6 input-group form-group' style="padding: 0px 8px 0px; float: left;">
												<form:select path="rxType"  class="select2_single form-control" onchange="restRxItems();">
													<form:options items="${prescribedItemTypeList}" itemLabel="prescribedItemTypeText" itemValue="prescribedItemTypeID" />
												</form:select>
												<form:hidden path="rxOrigin"/>
											</div>
										</div>

										<div class="col-md-5 col-sm-6 col-xs-6">
											<label class="col-md-4 col-sm-2 col-xs-12 control-label required" for="rxItem">Prescribed Item</label>
											<div class="col-md-8 col-sm-4 col-xs-12 form-group">
												<select class="autoCompleterRxItem select2_single form-control" style="width:100%;color:black;" name="autoCompleterRxItem"  id="autoCompleterRxItem" ></select>
												<form:hidden path="rxItem" id="rxItem"/>
												<form:hidden path="rxItemName" id="rxItemName" /> 
												<div class="item-errors error_fields"></div>
											</div>
											
											<div style="display: none">
										<form:select path="rxItem" id="rxItemHidden"
											class="select2_single form-control">
											<form:option value="">All</form:option>
											<c:forEach var="prescriptionItems" items="${prescriptionItemsList}">
												<form:option value="${prescriptionItems.itemid}">
													<c:out value="${prescriptionItems.itemname}" />
												</form:option>
											</c:forEach>
										</form:select>
									</div>
									
										</div>
										
										<div class="col-md-4 col-sm-6 col-xs-6">
											<label class="col-md-5 col-sm-2 col-xs-12 control-label" for="rxItem">Dispensed Item</label>
											<div class="col-md-7 col-sm-12 col-xs-12 form-group">
												<form:textarea path="rxDispensedItemName" id="rxDispensedItemName" rows="2" cssClass="form-control" readonly="true" />
											</div>
										</div>
									</div>
								</div>
								
								<div class="row">
									<div class="col-md-12 col-sm-12 col-xs-12">
										<label class="col-md-1 col-sm-6 col-xs-6 control-label required" for="rxQuantity">Quantity</label>
										<div class="col-md-1 col-sm-3 col-xs-3 form-group">
											<form:input path="rxQuantity" class="form-control " onkeypress="return isNumber(event)" />
											<div class="rxqty-errors error_fields"></div>
										</div>
										<div class="col-md-1 col-sm-3 col-xs-3 form-group">
											<form:select path="rxItemUnit" class="select2_single form-control required" style="padding:0px">
											   <form:option value="0" label="Select"/>
											   <form:options items="${unitList}" itemLabel="dispensingunittext" itemValue="dispensingunitid" />
											</form:select>
											<div class="rxunit-errors error_fields"></div>
										</div>
										<label class="col-md-1 col-sm-1 col-xs-6 control-label" for="rxRefills">Refills</label>
										<div class="col-md-1 col-sm-1 col-xs-6 form-group">
											<form:input path="rxRefills" class="form-control " maxlength="5"  onfocus="removeZero(this);" onblur="putZero(this)" onkeypress="return isNumber(event)" />
										</div>
										<label class="col-md-1 col-sm-4 col-xs-4 control-label" for="daysSupply" style="padding:0px;padding-top:5px">Days Supply</label>
										<div class="col-md-1 col-sm-3 col-xs-3 form-group">
											<form:input path="daysSupply" class="form-control " maxlength="3" onfocus="removeZero(this);" onblur="putZero(this)" onkeypress="return isNumber(event)" />
											<div class="daysSupply-errors error_fields"></div>
										</div>
										<label class="col-md-1 col-sm-5 col-xs-5 control-label" >Days</label>
										<div class="col-md-1 col-sm-1 col-xs-12 checkboxValue">
				                        	<div class="checkboxbox"><form:checkbox path="rxDAW" value="DAW" /></div>
				                        	<div class="checkboxValue">DAW</div>
										</div>
										<div class="col-md-1 col-sm-1 col-xs-12 checkboxValue">
			                                <div class="checkboxbox"><form:checkbox path="rxAuto" value="Auto" /></div>
				                        	<div class="checkboxValue">Auto</div>
			                            </div>
			                            <div class="col-md-1 col-sm-1 col-xs-12 checkboxValue">
			                                <div class="checkboxbox"><form:checkbox path="rxPRN" value="PRN" /></div>
				                        	<div class="checkboxValue">PRN</div>
			                            </div>
									</div>
									
								</div>
	
								<div class="row">
									<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
										<label class="col-md-4 col-sm-6 col-xs-6 control-label required" for="Directions">Directions</label>
										<div class="col-lg-4 col-md-6 col-sm-6 col-xs-6 form-group">
											<form:textarea path="rxSigCodes" id="rxSigCodes" rows="5" cssClass="form-control" style="width:320px"/>
											<form:hidden path="directionText" id="directionText" /> 
											<div class="rxsigcode-errors error_fields"></div>
										</div>
									</div>
								
								
									<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
										<label class="col-md-4 col-sm-6 col-xs-6 control-label" for="allergies">Comments</label>
										<div class="col-lg-4 col-md-6 col-sm-6 col-xs-6 form-group">
											<form:textarea path="comments" rows="5" cssClass="form-control" style="width:320px"/>
										</div>
									</div>
									<div class="col-md-6 col-sm-6 col-xs-12">
										<label class="col-md-4 col-sm-6 col-xs-6 control-label " for="rxICD10">ICD 10</label>
										<div class="col-md-8 col-sm-6 col-xs-6 form-group">
											<select class="autoCompleterRxICD10 select2_single form-control" style="width:100%;color:black;" name="autoCompleterRxICD10"  id="autoCompleterRxICD10"></select>
											<form:hidden path="rxICD10" id="rxICD10"/>
											<form:hidden path="icd10Text" id="icd10Text" /> 
										</div>
									</div>
								</div>
								<div class="row">
									<div class="col-md-6 col-sm-6 col-xs-12">
										<label class="col-md-4 col-sm-6 col-xs-6 control-label " for="rxItem">Rx #</label>
										<div class="col-md-8 col-sm-6 col-xs-6 form-group">
											<form:input path="rxNumber" class="form-control " readonly="true"  />
										</div>
									</div>
									<div class="col-md-6 col-sm-6 col-xs-12">
										<label class="col-md-4 col-sm-6 col-xs-6 control-label " for="rxItem">Rx Status</label>
										<div class="col-md-8 col-sm-6 col-xs-6 form-group">
											<form:input path="rxStatus" class="form-control " readonly="true" />
										</div>
									</div>
								</div>
	
								<div class="row">
									<div class="col-md-6 col-sm-6 col-xs-12">
										<label class="col-md-4 col-sm-6 col-xs-6 control-label " for="rxItem">Refill Remaining</label>
										<div class="col-md-8 col-sm-6 col-xs-6 form-group">
											<form:input path="refillRemaining" class="form-control " readonly="true" />
										</div>
									</div>
									<div class="col-md-6 col-sm-6 col-xs-12">
										<label class="col-md-4 col-sm-6 col-xs-6 control-label " for="rxItem">Refills Filled</label>
										<div class="col-md-8 col-sm-6 col-xs-6 form-group">
											<form:input path="refillsFilled" class="form-control " readonly="true" />
										</div>
									</div>
								</div>
								
								<div class="row">
									<div class="col-md-6 col-sm-6 col-xs-12">
										<label class="col-md-4 col-sm-6 col-xs-6 control-label " for="rxItem">Last Filled Date</label>
										<div class="col-md-8 col-sm-6 col-xs-6 form-group">
											<form:input path="lastFilledDate" class="form-control " readonly="true" />
										</div>
									</div>
									<div class="col-md-6 col-sm-6 col-xs-12">
										<label class="col-md-4 col-sm-6 col-xs-6 control-label " for="rxItem">Future Fill</label>
										<div class="col-md-8 col-sm-6 col-xs-6 form-group">
											<form:input path="futureFill" class="form-control " readonly="true" />
											<form:hidden path="trackingNumber" />
										</div>
									</div>
								</div>
								
								<div class="row">
									<div class="col-md-6 col-sm-6 col-xs-12">
										<label class="col-md-4 col-sm-6 col-xs-6 control-label " for="rxItem">Priority Type</label>
										<div class="col-md-8 col-sm-6 col-xs-6 form-group">
											<form:input path="priortyType" class="form-control " readonly="true" />
										</div>
									</div>
									<div class="col-md-6 col-sm-6 col-xs-12">
										<label class="col-md-4 col-sm-6 col-xs-6 control-label " for="rxItem">Script Type</label>
										<div class="col-md-8 col-sm-6 col-xs-6 form-group">
											<form:input path="scriptType" class="form-control " readonly="true" />
										</div>
									</div>
								</div>
								
								<div class="row">
									<div class="col-md-6 col-sm-6 col-xs-12">
										<label class="col-md-4 col-sm-6 col-xs-6 control-label " for="rxItem">Previous Rx #</label>
										<div class="col-md-8 col-sm-6 col-xs-6 form-group">
											<form:input path="previousRxNumber" class="form-control " readonly="true" />
										</div>
									</div>
									<div class="col-md-6 col-sm-6 col-xs-12">
										<label class="col-md-4 col-sm-6 col-xs-6 control-label " for="rxItem">Prescriber Order #</label>
										<div class="col-md-8 col-sm-6 col-xs-6 form-group">
											<form:input path="prescriberOrderNumber" class="form-control " readonly="true" />
										</div>
									</div>
								</div>							
	
								<c:if test="${showSaveBtn == 'true'}">
									<div class="row">
										<div class="col-md-12 col-sm-12 col-xs-12 text-center marginTop20">
											<form:hidden path="rxTranId" class="form-control " />
											
											<c:choose>
											<c:when test="${(loginDetail.type == 'Administrator' or loginDetail.type == 'Super Admin' or loginDetail.type == 'Admin')}">
											<c:if test="${(loginDetail.prescriptionCreationPermission =='Yes')}">
												<button type="button" id="addLine" class="btn btn-primary">Add</button>
												<button type="button" id="clearLine" class="btn btn-primary">Clear</button>
											</c:if>
											</c:when>
											<c:otherwise>
												<button type="button" id="addLine" class="btn btn-primary">Add</button>
												<button type="button" id="clearLine" class="btn btn-primary">Clear</button>
											</c:otherwise>
											</c:choose>
										
											
										</div>
									</div>
								</c:if>
			               	</div>
		               	</c:if>
	               	</div>
	               	
	               	<div class="x_content x_title_padding table-responsive">
	               		<div class="medications_records-errors error_fields"></div>
	               		<table id="medicationTable" class="table table-striped table-bordered">
	               			<thead>
		               			<tr class="heading_background">
		               				<td style="width: 40px;">Edit</td>
		               				<c:if test="${loginDetail.type != 'Patient' and showSaveBtn == 'true'}">
		               					<c:choose>
										<c:when test="${(loginDetail.type == 'Administrator' or loginDetail.type == 'Super Admin' or loginDetail.type == 'Admin')}">
										<c:if test="${(loginDetail.prescriptionCreationPermission =='Yes')}">
											<td style="width: 40px;">Delete</td>
										</c:if>
										</c:when>
										<c:otherwise>
											<td style="width: 40px;">Delete</td>
										</c:otherwise>
										</c:choose>
			               				
			               			</c:if>
		               				<td style="width: 90px;">Reference #</td>
		               				<td style="width: 70px;">Rx #</td>
		               				<td style="width: 75px;">Written Date</td>
		               				<td style="width: 70px;">Rx Status</td>
		               				<td style="width: 100px;">Item</td>
		               				<td style="width: 100px;">Dispensed Item</td>
		               				<td style="width: 40px;">Qty</td>
		               				<td style="width: 50px;">Refills</td>
		               				<td style="width: 50px;">Refills Remain</td>
		               				<td style="width: 50px;">Refills Filled</td>
		               				<!-- <td style="width: 90px;">Last Filled Date</td> -->
		               				<!-- <td style="width: 70px;">Tracking Number</td> -->
		               			</tr>
	               			</thead>
	               			<tbody>
               					<c:forEach items="${prescription.medications}" varStatus="i">
               						<tr id="R-${i.index}">
               							<td><img class="img-responsive editPre" src="${pageContext.request.contextPath}/resources/images/edit-3.png" /></td>
			               				<c:if test="${loginDetail.type != 'Patient' and showSaveBtn == 'true'}">
			               					
			               					<c:choose>
											<c:when test="${(loginDetail.type == 'Administrator' or loginDetail.type == 'Super Admin' or loginDetail.type == 'Admin')}">
											<c:if test="${(loginDetail.prescriptionCreationPermission =='Yes')}">
												<td><img class="img-responsive deletePre" src="${pageContext.request.contextPath}/resources/images/delete2.png" /></td>
											</c:if>
											</c:when>
											<c:otherwise>
												<td><img class="img-responsive deletePre" src="${pageContext.request.contextPath}/resources/images/delete2.png" /></td>
											</c:otherwise>
											</c:choose>
										
				               				
			               				</c:if>
			               				<td>
			               					<form:input path="medications[${i.index}].prescriptionNo" alt="medications${i.index}prescriptionNo" class="form-control prescriptionNo showTitle leftAdjust" readonly="true" style="width: 90px;"  />
			               					<form:hidden path="medications[${i.index}].id" alt="medications${i.index}id"  />
			               					<form:hidden path="medications[${i.index}].type" alt="medications${i.index}type" />
			               					<form:hidden path="medications[${i.index}].origin" alt="medications${i.index}origin" />
			               					<form:hidden path="medications[${i.index}].dwa" alt="medications${i.index}dwa" />
			               					<form:hidden path="medications[${i.index}].auto" alt="medications${i.index}auto" />
			               					<form:hidden path="medications[${i.index}].prn" alt="medications${i.index}prn" />
			               					<form:hidden path="medications[${i.index}].icd10" alt="medications${i.index}icd10" />
			               					<form:hidden path="medications[${i.index}].icd10Text" alt="medications${i.index}icd10Text" />
			               					<form:hidden path="medications[${i.index}].sigCodes" alt="medications${i.index}sigCodes" />
			               					<form:hidden path="medications[${i.index}].daysSupply" alt="medications${i.index}daysSupply" />
			               					<form:hidden path="medications[${i.index}].directionText" alt="medications${i.index}directionText" />
			               					<form:hidden path="medications[${i.index}].controlSubstance" alt="medications${i.index}controlSubstance" />
			               					
			               					<form:hidden path="medications[${i.index}].futureFill" alt="medications${i.index}futureFill" />
			               					<form:hidden path="medications[${i.index}].priortyType" alt="medications${i.index}priortyType" />
			               					<form:hidden path="medications[${i.index}].scriptType" alt="medications${i.index}scriptType" />
			               					<form:hidden path="medications[${i.index}].previousRxNumber" alt="medications${i.index}previousRxNumber" />
			               					<form:hidden path="medications[${i.index}].prescriberOrderNumber" alt="medications${i.index}prescriberOrderNumber" />
			               					<form:hidden path="medications[${i.index}].trackingNumber" alt="medications${i.index}trackingNumber"  />
			               					<form:hidden path="medications[${i.index}].comments" alt="medications${i.index}comments" />
			               				</td>
			               				<td>
			               					<form:input path="medications[${i.index}].rxNumber" alt="medications${i.index}rxNumber" class="form-control rxNumber showTitle leftAdjust" readonly="true" style="width: 70px;"  />
			               				</td>
			               				<td><form:input path="medications[${i.index}].writtenDate" alt="medications${i.index}writtenDate" class="form-control leftAdjust" readonly="true" style="width: 75px;" />
			               					<form:hidden path="medications[${i.index}].expireDate" alt="medications${i.index}expireDate" /></td>
			               				<td><form:input path="medications[${i.index}].rxStatus" alt="medications${i.index}rxStatus" class="form-control showTitle leftAdjust" readonly="true" style="width: 70px;"  /></td>
			               				<td><form:hidden path="medications[${i.index}].itemid" alt="medications${i.index}itemid" />
			               					<form:textarea path="medications[${i.index}].itemname" alt="medications${i.index}itemname" class="form-control showTitle leftAdjust" readonly="true" style="width: 100px;" ></form:textarea>
			               					<form:hidden path="medications[${i.index}].dispensedItemId" alt="medications${i.index}dispensedItemId" />
			               				</td>
			               				
			               				<td><form:textarea path="medications[${i.index}].dispensedItemName" alt="medications${i.index}dispensedItemName" class="form-control showTitle leftAdjust" readonly="true" style="width: 100px;" /></td>
			               				
			               				
			               				<td><form:input path="medications[${i.index}].quantity" alt="medications${i.index}quantity" class="form-control leftAdjust" readonly="true" style="width: 40px;" />
			               					<form:hidden path="medications[${i.index}].unitName" alt="medications${i.index}unitName" /></td>
			               				<td><form:input path="medications[${i.index}].refills" alt="medications${i.index}refills" class="form-control leftAdjust" readonly="true" style="width: 50px;" /></td>
			               				<td><form:input path="medications[${i.index}].refillsRemaining" alt="medications${i.index}refillsRemaining" class="form-control leftAdjust" readonly="true" style="width: 50px;" /></td>
			               				<td><form:input path="medications[${i.index}].refillsFilled" alt="medications${i.index}refillsFilled" class="form-control leftAdjust" readonly="true" style="width: 50px;" />
			               					<form:hidden path="medications[${i.index}].lastFilledDate" alt="medications${i.index}lastFilledDate" />
			               				</td>
			               				<%-- <td><form:input path="medications[${i.index}].lastFilledDate" alt="medications${i.index}lastFilledDate" class="form-control " readonly="true" style="width: 90px;" /></td> --%>
			               				<%-- <td><form:input path="medications[${i.index}].trackingNumber" alt="medications${i.index}trackingNumber" class="form-control " readonly="true" style="width: 70px;" /></td> --%>
			               			</tr>
               					</c:forEach>
	               			</tbody>
	               		</table>
	               	</div>
				</div>
			</div>
		</div>
		
		

		

		<div class="row form-group">
			<div class="col-md-12 col-sm-12 col-xs-12 profile_left">
				<div class="x_panel">
					<div class="x_content x_title_padding">
						<div class="row">
		               		<c:choose>
						    	<c:when test="${loginDetail.type != 'Patient' and showSaveBtn == 'true'}">
						        	<div class="col-md-6 col-sm-12 col-xs-12 text-right">
										
										
										<c:choose>
										<c:when test="${(loginDetail.type == 'Administrator' or loginDetail.type == 'Super Admin' or loginDetail.type == 'Admin')}">
										<c:if test="${(loginDetail.prescriptionCreationPermission =='Yes')}">
											<button type="button" class="btn btn-primary" id="myModal" onClick="signatureClear();div_show(this);">Save & eSign</button>
										</c:if>
										</c:when>
										<c:otherwise>
											<button type="button" class="btn btn-primary" id="myModal" onClick="signatureClear();div_show(this);">Save & eSign</button>
										</c:otherwise>
										</c:choose>
				
									</div>
									
						       	</c:when>
						       	<c:otherwise>
						        	<div class="col-md-6 col-sm-6 col-xs-12 text-right"></div>
						       	</c:otherwise>
						  	</c:choose>
						   <%--	<c:choose>
						    	<c:when test="${loginDetail.type == 'Physician'}">
						        	<div class="col-md-6 col-sm-12 col-xs-12 text-left">
										<button type="button" class="btn btn-primary" id="myModal" onclick="signatureClear();div_show(this)" title="Click to eSign">
									   		Save & eSign
										</button>
									</div>
						       	</c:when>
								<c:when test="${loginDetail.type != 'Patient' and loginDetail.type != 'Physician'}">
						        	<div class="col-md-6 col-sm-12 col-xs-12 text-left">
										<button type="button" class="btn btn-primary" id="myModal" onclick="showEsignWarningAlert()">
									   		Click to eSign
										</button>
									</div>
								</c:when>
						       	<c:otherwise>
						        	<div class="col-md-6 col-sm-12 col-xs-12 text-right"></div>
						       	</c:otherwise>
						  	</c:choose>--%>
						</div>
						<div class="row">
							<div class="col-md-12 col-sm-12 col-xs-12 text-center formErrorMsg" style="display:none;">
								<b>Error:</b> <spring:eval expression="@commonConfigurer.getProperty(\'error.form_fields_not_valid\')" />
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		
		<c:if test="${loginDetail.type == 'Super Admin' || loginDetail.type == 'Admin'  || loginDetail.type == 'Physician'}" >
			<div class="x_panel">
				<div class="x_title">
					<h2>Documents</h2>
					<div class="clearfix"></div>
				</div>
				<div class="x_content x_title_padding">
					<c:choose>
						<c:when test="${(loginDetail.type == 'Administrator' or loginDetail.type == 'Super Admin' or loginDetail.type == 'Admin')}">
							<c:if test="${(loginDetail.prescriptionCreationPermission =='Yes')}">
								<div class="row">
								<label class="col-lg-2 control-label">Document Type</label>
								<div class="col-lg-2 form-group">
			                		<form:select path="isHardCopy" class="select2_single form-control">
										<form:option value="" label="Select"/>
										<form:option value="Y" label="Hard Copy"/>
										<form:option value="N" label="Others"/>
									</form:select>
			                	</div>
								</div>
								<div class="row">
									<label class="col-lg-2 control-label">Description</label>
									<div class="col-lg-4 col-md-4 col-sm-4 col-xs-12">
										<form:textarea path="description" rows="3" cssClass="form-control" />							
									</div>
								</div>
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
							<div class="row">
							<label class="col-lg-2 control-label">Document Type</label>
							<div class="col-lg-2 form-group">
		                		<form:select path="isHardCopy" class="select2_single form-control">
									<form:option value="" label="Select"/>
									<form:option value="Y" label="Hard Copy"/>
									<form:option value="N" label="Others"/>
								</form:select>
		                	</div>
							</div>
							<div class="row">
								<label class="col-lg-2 control-label">Description</label>
								<div class="col-lg-4 col-md-4 col-sm-4 col-xs-12">
									<form:textarea path="description" rows="3" cssClass="form-control" />							
								</div>
							</div>
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
					
					<div class="row col-md-12 col-sm-12 col-xs-12 table-responsive">
						<table id="subtanceItemsFilesTable" class="table table-striped table-bordered dataGrid">
							<thead>
								<tr class="heading_background">
								<c:choose>
								<c:when test="${(loginDetail.type == 'Administrator' or loginDetail.type == 'Super Admin' or loginDetail.type == 'Admin')}">
								<c:if test="${(loginDetail.prescriptionCreationPermission =='Yes')}">
									<th>Delete</th>
								</c:if>
								</c:when>
								<c:otherwise>
									<th>Delete</th>
								</c:otherwise>
								</c:choose>
											
									
									<th>File Name</th>
									<!-- <th>Scan</th>
									<th>Fax</th> -->
									<th>Document Type</th>
									<th>Uploaded on</th>
									<th>Approved By</th>
									<th>Approved Date</th>
								</tr>
							</thead>
							<tbody></tbody>
						</table>
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
												<button id="savesignature" type="submit" onclick="signatureSave()"  class="btn btn-primary btn-sm">
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
				</div>
			</div>
		</c:if>
		
		<form:hidden path="prescriptionId" />
		<form:hidden path="controlSubstance" />
		<form:hidden path="prescriptionNumber" />
		<form:hidden path="base64ImgString" />
		<form:hidden path="esignedPDF" />
		<form:hidden path="csesignedPDF" />
		<form:hidden path="class2ControlSubtance" />
		<form:hidden path="fileFtpAllowed" />
		<form:hidden path="orderId" />
		<form:hidden path="invoiceId" />
		<form:hidden path="useGroupLogo" />
		
		<input type="hidden" id="serverUrl" value="${pageContext.request.contextPath}" />
		
		<input type="hidden" id="err_physician_id" value='<spring:eval expression="@prescriptionConfigurer.getProperty(\'error.physician\')" />' />
		<input type="hidden" id="err_patient_id" value='<spring:eval expression="@prescriptionConfigurer.getProperty(\'error.patient\')" />' />
		<input type="hidden" id="err_written_by" value='<spring:eval expression="@prescriptionConfigurer.getProperty(\'error.written_by\')" />' />
		<input type="hidden" id="err_patient_bill_id" value='<spring:eval expression="@prescriptionConfigurer.getProperty(\'error.patient_bill_to\')" />' />
		<input type="hidden" id="err_clinic_id" value='<spring:eval expression="@prescriptionConfigurer.getProperty(\'error.clinic_id\')" />' />
		
		<input type="hidden" id="err_clinic_po" value='<spring:eval expression="@prescriptionConfigurer.getProperty(\'error.clinic_po\')" />' />
		<input type="hidden" id="err_patient_ship_id" value='<spring:eval expression="@prescriptionConfigurer.getProperty(\'error.patient_ship_to\')" />' />
		<input type="hidden" id="err_ship_account_no" value='<spring:eval expression="@prescriptionConfigurer.getProperty(\'error.ship_account_no\')" />' />
		<input type="hidden" id="err_bill_address_id" value='<spring:eval expression="@prescriptionConfigurer.getProperty(\'error.bill_address_id\')" />' />
		<input type="hidden" id="err_ship_address_id" value='<spring:eval expression="@prescriptionConfigurer.getProperty(\'error.ship_address_id\')" />' />

		<input type="hidden" id="err_bill_name" value='<spring:eval expression="@prescriptionConfigurer.getProperty(\'error.billing_name\')" />' />
		<input type="hidden" id="err_bill_address" value='<spring:eval expression="@prescriptionConfigurer.getProperty(\'error.billing_address\')" />' />
		<input type="hidden" id="err_bill_city" value='<spring:eval expression="@prescriptionConfigurer.getProperty(\'error.billing_city\')" />' />
		<input type="hidden" id="err_bill_state" value='<spring:eval expression="@prescriptionConfigurer.getProperty(\'error.billing_state\')" />' />
		<input type="hidden" id="err_bill_zipcode" value='<spring:eval expression="@prescriptionConfigurer.getProperty(\'error.billing_zipcode\')" />' />
		
		<input type="hidden" id="err_ship_name" value='<spring:eval expression="@prescriptionConfigurer.getProperty(\'error.ship_name\')" />' />
		<input type="hidden" id="err_ship_address" value='<spring:eval expression="@prescriptionConfigurer.getProperty(\'error.ship_address\')" />' />
		<input type="hidden" id="err_ship_city" value='<spring:eval expression="@prescriptionConfigurer.getProperty(\'error.ship_city\')" />' />
		<input type="hidden" id="err_ship_state" value='<spring:eval expression="@prescriptionConfigurer.getProperty(\'error.ship_state\')" />' />
		<input type="hidden" id="err_ship_zipcode" value='<spring:eval expression="@prescriptionConfigurer.getProperty(\'error.ship_zipcode\')" />' />

		<input type="hidden" id="err_payment_type" value='<spring:eval expression="@prescriptionConfigurer.getProperty(\'error.payment_type\')" />' />
<%-- 		<input type="hidden" id="err_payment_term" value='<spring:eval expression="@prescriptionConfigurer.getProperty(\'error.payment_term\')" />' />
 --%>		<input type="hidden" id="err_ship_method" value='<spring:eval expression="@prescriptionConfigurer.getProperty(\'error.ship_method\')" />' />
		<input type="hidden" id="err_other_ship_method" value='<spring:eval expression="@prescriptionConfigurer.getProperty(\'error.other_ship_method\')" />' />
		<input type="hidden" id="err_ship_company" value='<spring:eval expression="@prescriptionConfigurer.getProperty(\'error.ship_company\')" />' />
		<input type="hidden" id="err_other_ship_company" value='<spring:eval expression="@prescriptionConfigurer.getProperty(\'error.other_ship_company\')" />' />
		
		<input type="hidden" id="err_written_date" value='<spring:eval expression="@prescriptionConfigurer.getProperty(\'error.written_date\')" />' />
		<input type="hidden" id="err_expiry_date" value='<spring:eval expression="@prescriptionConfigurer.getProperty(\'error.expiry_date\')" />' />
		<input type="hidden" id="err_expiry_before" value='<spring:eval expression="@prescriptionConfigurer.getProperty(\'error.expiry_before\')" />' />
		<input type="hidden" id="err_origin" value='<spring:eval expression="@prescriptionConfigurer.getProperty(\'error.origin\')" />' />
		<input type="hidden" id="err_item" value='<spring:eval expression="@prescriptionConfigurer.getProperty(\'error.item\')" />' />
		<input type="hidden" id="err_quantity" value='<spring:eval expression="@prescriptionConfigurer.getProperty(\'error.quantity\')" />' />
		<input type="hidden" id="err_unit" value='<spring:eval expression="@prescriptionConfigurer.getProperty(\'error.unit\')" />' />
		<input type="hidden" id="err_directions" value='<spring:eval expression="@prescriptionConfigurer.getProperty(\'error.directions\')" />' />
		<input type="hidden" id="err_day_supply" value='<spring:eval expression="@prescriptionConfigurer.getProperty(\'error.day_supply\')" />' />
		
		<input type="hidden" id="err_no_transaction" value='<spring:eval expression="@prescriptionConfigurer.getProperty(\'error.no_medications\')" />' />
		
		<div class="row">
			<div class="col-md-3 col-sm-3 col-xs-3">
				<img class="img-responsive goSummary" src="${pageContext.request.contextPath}/resources/images/back_icon-3.png" title="Go to Summary">
			</div>
			<%-- <div class="col-md-3 col-sm-3 col-xs-3 text-right ">
				<c:if test="${loginDetail.type != 'Patient'}">
					<button type="button" class="btn btn-primary goNewRec" >Create New Prescription</button>
				</c:if>
			</div> --%>

			<div class="col-md-3 col-sm-3 col-xs-3">
				<c:if test="${prescription.orderId != 0}">
					<div class="row">
						<div class="col-md-2 col-sm-3 col-xs-6"></div>
						<div class="col-md-4 col-sm-3 col-xs-6">
							<button type="button" class="btn btn-primary backOrder" >Back to Order</button>
						</div>
					</div>
				</c:if>
				<c:if test="${prescription.invoiceId != 0}">
					<div class="row">
						<div class="col-md-2 col-sm-3 col-xs-6"></div>
						<div class="col-md-4 col-sm-3 col-xs-6">
							<button type="button" class="btn btn-primary backInvoice" >Back to Invoice</button>
						</div>
					</div>
				</c:if>
			</div>

					
			<c:if test="${loginDetail.type != 'Patient' and prescription.prescriptionId > 0}">
				<div class="col-md-5 col-sm-5 col-xs-5 text-right">
					<div class="row">
						<div class="col-md-3 col-sm-3 col-xs-3 text-right">
						 <c:if test="${prescription.esignedPDF}">
							 <img class=" pdfDownload" src="${pageContext.request.contextPath}/resources/images/pdf-icon-2.png" title="PDF Download" style="height:36px;" style="cursor:pointer" />
					 	</c:if> 
						</div>
						<div class="col-md-6 col-sm-6 col-xs-6 text-right">
						<c:if test="${prescription.csesignedPDF}">
							 <img class=" cspdfDownload" src="${pageContext.request.contextPath}/resources/images/pdf-icon-2.png" title="Controlled Substance PDF Download" style="height:36px;" style="cursor:pointer" />
						</c:if>
						</div>
						<div class="col-md-2 col-sm-2 col-xs-2 text-right">
									<c:choose>
									<c:when test="${(loginDetail.type == 'Administrator' or loginDetail.type == 'Super Admin' or loginDetail.type == 'Admin')}">
									<c:if test="${(loginDetail.remoteFileUploadPermission =='Yes')}">
<%-- 										<img class=" pdfFtpUpload" src="${pageContext.request.contextPath}/resources/images/ftp_upload.png" title="Remote File Upload" style="cursor:pointer"/>
 --%>										<button type="button" class="btn btn-primary pdfFtpUpload" >Send RX to CRE8 Pharmacy</button>
									
									</c:if>
									</c:when>
									<c:otherwise>
<%-- 										<img class=" pdfFtpUpload" src="${pageContext.request.contextPath}/resources/images/ftp_upload.png" title="Remote File Upload" style="cursor:pointer"/>
 --%>										<button type="button" class="btn btn-primary pdfFtpUpload" >Send RX to CRE8 Pharmacy</button>
									
									</c:otherwise>
									</c:choose>
						</div>
						
					</div>
					<!-- <div class="row">
						<div class="col-md-2 col-sm-2 col-xs-12">
						</div>
						<div class="col-md-10 col-sm-10 col-xs-12 text-center">
							 <label class="col-md-12 col-sm-12 col-xs-12" for="remoteFileUpload"><b>Remote File Upload</b></label>
						</div>
					</div> -->
				</div>
			</c:if>
		
		</div>
	</form:form>
</div>
	
<script>
var urlPath="${pageContext.request.contextPath}/";
</script>
<%--includes the Front End UI validations--%>
<script src="${pageContext.request.contextPath}/resources/js/pages/prescription/prescription.js?v=5"></script>