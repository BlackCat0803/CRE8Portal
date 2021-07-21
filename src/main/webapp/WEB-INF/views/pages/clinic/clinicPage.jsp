<%--
Front End UI to save and load the Clinic Account details
 --%>
<%@ include file="../../../layout/taglib.jsp" %>

<div class="row">
	<div class="col-md-6 col-sm-6 col-xs-6">
		<img class="img-responsive goSummary" src="${pageContext.request.contextPath}/resources/images/back_icon-3.png" title="Go to Summary">
	</div>
	<div class="col-md-6 col-sm-6 col-xs-6">
		<c:if test="${loginDetail.type == 'Super Admin' or loginDetail.type == 'Admin' or (loginDetail.type == 'Physician' and loginDetail.status != 'New Modifications') 
			or loginDetail.type == 'Physician Assistant' or loginDetail.type == 'Group Director' }" >
			
				<c:choose>
			      <c:when test="${(loginDetail.type == 'Administrator' or loginDetail.type == 'Super Admin' or loginDetail.type == 'Admin')}">
				      	<c:if test="${(loginDetail.clinicCreationPermission =='Yes')}">
				      		<button type="button" class="btn btn-primary goNewRec" >Create New Clinic</button>
				      	</c:if>
			      </c:when>
			      <c:otherwise>
			      			<button type="button" class="btn btn-primary goNewRec" >Create New Clinic</button>
			      </c:otherwise>
			    </c:choose>
			    
		</c:if>
	</div>	
</div>

<form:form method="POST" id="clinicForm" action="saveClinicAccount" name="form" commandName="form" >
	<!-- page content -->
	<div class="">
		<c:if test="${not empty message}">
			<c:choose>
				<c:when test="${saveStatus == 'true'}">
					<div class="row">
						<div class="col-sm-offset-1 col-sm-10 fontSize14 text-center">
							<div class="alert alert-success fade in">
								<a href="#" class="close" data-dismiss="alert">&times;</a> 
									 &nbsp;&nbsp;&nbsp; ${message}
							</div>
						</div>
					</div>
				</c:when>
				<c:otherwise>
					<div class="row">
						<div class="col-sm-offset-1 col-sm-10 fontSize14 text-center">
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
			<div class="col-md-6 col-sm-12 col-xs-12">
				<div class="x_panel ">
                  	<div class="x_title">
                    	<h2>Clinic Details</h2>
                    	<div class="clearfix"></div>
                  	</div>				
					<div class="x_content x_title_padding">
						
						<div class="row">
							<div class="col-md-12 col-sm-12 col-xs-12 profile_left">
								<div class="col-md-12 col-sm-12 col-xs-12">
									<label class="col-md-4 col-sm-6 col-xs-12 control-label required" for="address">Group</label>
									<div class="col-md-8 col-sm-6 col-xs-12 form-group">
									
										<c:choose>
											<c:when test="${loginDetail.type == 'Physician' or loginDetail.type == 'Physician Assistant' or loginDetail.type == 'Group Director'}">
												<form:select path="groupId" class="select2_single form-control"  style="pointer-events: none;">
													<form:option value="0">Select</form:option>
													<form:options items="${groupList}" itemLabel="groupName" itemValue="id" />
												</form:select>
											</c:when>
											<c:otherwise>
												<form:select path="groupId" class="select2_single form-control" id="groupId">
													<form:option value="0">Select</form:option>
													<form:options items="${groupList}" itemLabel="groupName" itemValue="id" />
												</form:select>
											</c:otherwise>
										</c:choose>
									</div>
								</div>
								
								<div class="col-md-12 col-sm-12 col-xs-12">
									<label class="col-md-4 col-sm-6 col-xs-12 control-label required" for="status">Status</label>
									<div class="col-md-8 col-sm-6 col-xs-12 form-group">
										<form:select path="status" class="select2_single form-control" >
											<form:option value="New">New</form:option>
											<form:option value="Info Completed">Info Completed</form:option>
											<form:option value="Active">Active</form:option>
											<form:option value="Inactive">Inactive</form:option>
										</form:select>
									</div>
								</div>
								
								<div class="col-md-12 col-sm-12 col-xs-12">
									<label class="col-md-4 col-sm-6 col-xs-12 control-label required" for="clinicName">Clinic Name</label>
									<div class="col-md-8 col-sm-6 col-xs-12 form-group">
										<form:input path="clinicName" class="form-control" maxlength="100"  />
										<form:hidden path="clinicId" />
										<form:hidden path="newGroupId" />
									</div>
								</div>
								
								<div class="col-md-12 col-sm-12 col-xs-12">
									<label class="col-md-4 col-sm-6 col-xs-12 control-label required" for="contactName">Contact Person</label>
									<div class="col-md-8 col-sm-6 col-xs-12 form-group">
										<form:input path="contactName" class="form-control" maxlength="50"  />
									</div>
								</div>

								<div class="col-md-12 col-sm-12 col-xs-12">
									<label class="col-md-4 col-sm-6 col-xs-12 control-label required" for="email">Email</label>
									<div class="col-md-8 col-sm-6 col-xs-12 form-group">
										<form:input path="email" class="form-control" maxlength="255"  />
									</div>
								</div>
								
								<div class="col-md-12 col-sm-12 col-xs-12">
									<label class="col-md-4 col-sm-6 col-xs-12 control-label required" for="phone">Phone</label>
									<div class="col-md-8 col-sm-6 col-xs-12 form-group">
										<form:input path="phone" class="form-control" maxlength="15" onkeypress="return isNumber(event)" />
									</div>
								</div>
								<div class="col-md-12 col-sm-12 col-xs-12">
									<label class="col-md-4 col-sm-6 col-xs-12 control-label required" for="fax">Fax</label>
									<div class="col-md-8 col-sm-6 col-xs-12 form-group">
										<form:input path="fax" class="form-control" maxlength="15" onkeypress="return isNumber(event)" />
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			
			<div class="col-md-6 col-sm-12 col-xs-12">
				<div class="x_panel ">
                  	<div class="x_title">
                    	<h2>Address</h2>
                    	<div class="clearfix"></div>
                  	</div>				
					<div class="x_content x_title_padding">
						<div class="row">
							<div class="col-md-12 col-sm-12 col-xs-12 profile_left">
								<div class="col-md-12 col-sm-12 col-xs-12">
									<label class="col-md-3 col-sm-6 col-xs-12 control-label" for="branch">Branch</label>
									<div class="col-md-9 col-sm-6 col-xs-12 form-group">
										<form:input path="location" class="form-control" maxlength="50" />
										 (For Multiple Locations only)
									</div>
								</div>
							
								<div class="col-md-12 col-sm-12 col-xs-12">
									<label class="col-md-3 col-sm-6 col-xs-12 control-label required" for="address">Address</label>
									<div class="col-md-9 col-sm-6 col-xs-12 form-group">
										<form:input path="address" class="form-control" maxlength="100" />
									</div>
								</div>
									
								<div class="col-md-12 col-sm-12 col-xs-12">
									<label class="col-md-3 col-sm-6 col-xs-12 control-label required" for="city">City</label>
									<div class="col-md-9 col-sm-6 col-xs-12 form-group">
										<form:input path="city" class="form-control" maxlength="25" />
									</div>
								</div>
								
								<div class="col-md-12 col-sm-12 col-xs-12">
									<label class="col-md-3 col-sm-6 col-xs-12 control-label required" for="state">State</label>
									<div class="col-md-9 col-sm-6 col-xs-12 form-group">
										<form:select path="state" class="select2_single form-control" >
											<form:option value="0">Select</form:option>
											<form:options items="${statesList}" itemLabel="stateName" itemValue="stateCode"/>
										</form:select>
									</div>
								</div>
									
								<div class="col-md-12 col-sm-12 col-xs-12">
									<label class="col-md-3 col-sm-6 col-xs-12 control-label required" for="zipCode">Zip code</label>
									<div class="col-md-9 col-sm-6 col-xs-12 form-group">
										<form:input path="zipCode" class="form-control" maxlength="12" onkeypress="return isNumber(event)" />
									</div>
								</div>
								
								<div class="col-md-12 col-sm-12 col-xs-12 blackDiv">
								
								</div>
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
								      	<c:if test="${(loginDetail.clinicCreationPermission =='Yes')}">
								      		<button type="submit" class="btn btn-success">Save</button>
								      	</c:if>
							      </c:when>
							      <c:otherwise>
							      			<button type="submit" class="btn btn-success">Save</button>
							      </c:otherwise>
							    </c:choose>
							</div>
							<div class="col-md-12 col-sm-12 col-xs-12 text-center formErrorMsg" style="display:none;">
								<b>Error:</b> <spring:eval expression="@commonConfigurer.getProperty(\'error.form_fields_not_valid\')" />
							</div>
						</div>			
					</div>
				</div>
			</div>
		</div>


		
		<div class="row form-group">
			<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
				<div class="x_panel ">
                  	<div class="x_title">
                    	<h2>Physician List</h2>
                    	<div class="clearfix"></div>
                  	</div>				
					<div class="x_content x_title_padding">
						<div class="row form-group">
                       		<div class="col-md-2 col-sm-6 col-xs-6 fieldTop">
								<input type="text" id="physicianName" placeholder="Physician" class="form-control" >
                       		</div>
                       		<div class="col-md-1 col-sm-2 col-xs-6 fieldTop text-center">
                       			<button type="button" title="Search" id="phySearch" class="btn btn-primary" >Search</button>
                       		</div>
                       		<div class="col-md-1 col-sm-2 col-xs-6 fieldTop text-center">
                       			<button type="button" title="Clear" id="phyClearSearch" class="btn btn-primary" >Clear</button>
                       		</div>
                     	</div>
                     	
						<div class="row">
							<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 text-left table-responsive">
								<table id="clinicPhysicianTable" class="table table-striped table-bordered">
									<thead>
										<tr class="Sort_heading_background">
											<th>Id</th>
											<th>Physician Name</th>
											<th>Phone</th>
											<th>Email</th>
										</tr>
									</thead>
								</table>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		
		<div class="row form-group">
			<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
				<div class="x_panel ">
                  	<div class="x_title">
                    	<h2>Physician Assistant List</h2>
                    	<div class="clearfix"></div>
                  	</div>				
					<div class="x_content x_title_padding">
						<div class="row form-group">
                       		<div class="col-md-2 col-sm-6 col-xs-6 fieldTop">
								<input type="text" id="assistantName" placeholder="Physician Assistant" class="form-control" >
                       		</div>
                       		<div class="col-md-1 col-sm-2 col-xs-6 fieldTop text-center">
                       			<button type="button" title="Search" id="assSearch" class="btn btn-primary">Search</button>
                       		</div>
                       		<div class="col-md-1 col-sm-2 col-xs-6 fieldTop text-center">
                       			<button type="button" title="Clear" id="assClearSearch" class="btn btn-primary">Clear</button>
                       		</div>
                     	</div>	
						<div class="row">
							<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 text-left table-responsive">
								<table id="clinicAssistantTable" class="table table-striped table-bordered">
									<thead>
										<tr class="Sort_heading_background">
											<th>Id</th>
											<th>Assistant Name</th>
											<th>Physician Name</th>
											<th>Phone</th>
											<th>Email</th>
										</tr>
									</thead>
								</table>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<input type="hidden" id="physicianId"/>
	<input type="hidden" id="assistantId"/>
	<input type="hidden" id="serverUrl" value="${pageContext.request.contextPath}" />
	<script>
		var urlPath="${pageContext.request.contextPath}";
	</script>
	<!-- Error messages getting from property file -->
	<input type="hidden" id="err_group_name" value='<spring:eval expression="@clinicConfigurer.getProperty(\'error.group_name\')" />' />
	<input type="hidden" id="err_clinic_name" value='<spring:eval expression="@clinicConfigurer.getProperty(\'error.clinic_name\')" />' />
	<input type="hidden" id="err_location" value='<spring:eval expression="@clinicConfigurer.getProperty(\'error.location\')" />' />
	<input type="hidden" id="err_contactName" value='<spring:eval expression="@clinicConfigurer.getProperty(\'error.contactName\')" />' />
	<input type="hidden" id="err_address" value='<spring:eval expression="@clinicConfigurer.getProperty(\'error.address\')" />' />
	<input type="hidden" id="err_city" value='<spring:eval expression="@clinicConfigurer.getProperty(\'error.city\')" />' />
	<input type="hidden" id="err_state" value='<spring:eval expression="@clinicConfigurer.getProperty(\'error.state\')" />' />
	<input type="hidden" id="err_zip_format" value='<spring:eval expression="@clinicConfigurer.getProperty(\'error.zip_format\')" />' />
	<input type="hidden" id="err_phoneformat" value='<spring:eval expression="@clinicConfigurer.getProperty(\'error.phone_format\')" />' />
	<input type="hidden" id="err_status" value='<spring:eval expression="@clinicConfigurer.getProperty(\'error.status\')" />' />
	<input type="hidden" id="err_zip" value='<spring:eval expression="@clinicConfigurer.getProperty(\'error.zip\')" />' />
	<input type="hidden" id="err_phone" value='<spring:eval expression="@clinicConfigurer.getProperty(\'error.phone\')" />' />
	<input type="hidden" id="err_email" value='<spring:eval expression="@clinicConfigurer.getProperty(\'error.email\')" />' />
	<input type="hidden" id="err_email_format" value='<spring:eval expression="@clinicConfigurer.getProperty(\'error.email_format\')" />' />
	<input type="hidden" id="err_fax_format" value='<spring:eval expression="@clinicConfigurer.getProperty(\'error.fax_format\')" />' />
	<input type="hidden" id="err_fax" value='<spring:eval expression="@clinicConfigurer.getProperty(\'error.fax\')" />' />
	<input type="hidden" id="err_validgroup" value='<spring:eval expression="@clinicConfigurer.getProperty(\'err.validgroup\')" />' />
	

	<!-- /page content -->
</form:form>
<%--includes the Front End UI validations--%>
<script src="${pageContext.request.contextPath}/resources/js/pages/clinic/clinicPage.js?v=2"></script>