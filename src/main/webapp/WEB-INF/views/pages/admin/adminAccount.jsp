<%--
Front End UI to save and load the Admin Account details
 --%>
<%@ include file="../../../layout/taglib.jsp" %>
<style>
<!--
.table {
	table-layout:auto;
	word-wrap:break-word;
}
-->
</style>

<c:if test="${loginDetail.type == 'Super Admin'}">
	<div class="row">
		<div class="col-md-6 col-sm-6 col-xs-6">
			<img class="img-responsive goSummary" src="${pageContext.request.contextPath}/resources/images/back_icon-3.png" title="Go to Summary">
		</div>
		<div class="col-md-6 col-sm-6 col-xs-6">
			 <c:choose>
		      <c:when test="${(loginDetail.type == 'Administrator' or loginDetail.type == 'Super Admin' or loginDetail.type == 'Admin')}">
			      	<c:if test="${(loginDetail.adminAccCreationPermission =='Yes')}">
			      		<button type="button" class="btn btn-primary goNewRec" >Create New Pharmacy Admin Account</button>
			      	</c:if>
		      </c:when>
		      <c:otherwise>
		      		<button type="button" class="btn btn-primary goNewRec" >Create New Pharmacy Admin Account</button>
		      </c:otherwise>
		    </c:choose>
		    
		</div>
	</div>	
</c:if>

<form:form method="POST" action="saveAdminAccount" name="admin" commandName="admin" enctype="multipart/form-data" >
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
			<div class="col-md-6 col-sm-6 col-xs-12">
				<div class="x_panel ">
                  	<div class="x_title">
                    	<h2>Pharmacy Admin Profile</h2>
                    	<div class="clearfix"></div>
                  	</div>				
					<div class="x_content x_title_padding">
						
						<div class="row">
							<div class="col-md-12 col-sm-12 col-xs-12 profile_left">
								<div class="col-md-12 col-sm-12 col-xs-12">
									<label class="col-md-4 col-sm-6 col-xs-12 control-label required" for="pharmacyId">Pharmacy</label>
									<div class="col-md-6 col-sm-6 col-xs-12 form-group">
										<form:select path="pharmacyId" class="select2_single form-control" >
											<form:option value="0">Select</form:option>
											<form:options items="${pharmacyList}" itemLabel="pharmacyName" itemValue="id"/>
										</form:select>										
									</div>
								</div>
								
								<div class="col-md-12 col-sm-12 col-xs-12">
									<label class="col-md-4 col-sm-6 col-xs-12 control-label required" for="status">Status</label>
									<div class="col-md-6 col-sm-6 col-xs-12 form-group">
										<c:choose>
											<c:when test="${loginDetail.type == 'Super Admin'}">
												<c:if test="${loginDetail.userid == admin.adminId}">
													<form:input path="status" class="form-control" title="${admin.status}" readonly="true" />
												</c:if>
												<c:if test="${loginDetail.userid != admin.adminId}">
													<form:select path="status" class="select2_single form-control" >
														<form:option value="">Select</form:option>
														<form:option value="Active">Active</form:option>
														<form:option value="Inactive">Inactive</form:option>
													</form:select>
												</c:if>
											</c:when>
											<c:otherwise>
												<form:input path="status" class="form-control" title="${admin.status}" readonly="true" />
											</c:otherwise>
										</c:choose>
										
									</div>
								</div>
								
								<div class="col-md-12 col-sm-12 col-xs-12">
									<label class="col-md-4 col-sm-6 col-xs-12 control-label required" for="type">Type</label>
									<div class="col-md-6 col-sm-6 col-xs-12 form-group">
										<c:choose>
											<c:when test="${loginDetail.type == 'Super Admin'}">
												<c:if test="${loginDetail.userid == admin.adminId}">
													<form:input path="type" class="form-control" title="${admin.type}" readonly="true" />
												</c:if>
												<c:if test="${loginDetail.userid != admin.adminId}">
													<form:select path="type" class="select2_single form-control" >
														<form:option value="">Select</form:option>
														<form:option value="Super Admin">Super Admin</form:option>
														<form:option value="Admin">Admin</form:option>
													</form:select>
												</c:if>
											</c:when>
											<c:otherwise>
												<form:input path="type" class="form-control" title="${admin.type}" readonly="true" />
											</c:otherwise>
										</c:choose>
									</div>
								</div>
								
								<div class="col-md-12 col-sm-12 col-xs-12">
									<label class="col-md-4 col-sm-6 col-xs-12 control-label required" for="dateRegistrated">Registration Date</label>
									<div class='col-md-6 col-sm-6 col-xs-12 form-group' style="padding: 0px 9px 0px; float: left;">
										<c:choose>
											<c:when test="${loginDetail.type == 'Admin' and loginDetail.userid == admin.adminId}">
												<form:input path="dateRegistrated" class="form-control calendarIcon" title="${admin.dateRegistrated}" readonly="true" />
											</c:when>
											<c:otherwise>
												<c:choose>
													<c:when test="${loginDetail.type == 'Super Admin' and loginDetail.userid == admin.adminId}">
														<form:input path="dateRegistrated" class="form-control calendarIcon" title="${admin.dateRegistrated}" readonly="true" />
													</c:when>
													<c:otherwise>
														<form:input path="dateRegistrated" class="form-control calendarIcon"  />
													</c:otherwise>
												</c:choose>
											</c:otherwise>
										</c:choose>
									</div>
								</div>
								
								<div class="col-md-12 col-sm-12 col-xs-12">
									<label class="col-md-4 col-sm-6 col-xs-12 control-label required" for="firstName">First Name</label>
									<div class="col-md-6 col-sm-6 col-xs-12 form-group">
										<form:input path="firstName" class="form-control" maxlength="50" onkeyup="displayFullname('firstName', 'middleName', 'lastName', 'adminName' );" />
									</div>
								</div>
									
								<div class="col-md-12 col-sm-12 col-xs-12">
									<label class="col-md-4 col-sm-6 col-xs-12 control-label" for="middleName">Middle Name</label>
									<div class="col-md-6 col-sm-6 col-xs-12 form-group">
										<form:input path="middleName" class="form-control" maxlength="10" onkeyup="displayFullname('firstName', 'middleName', 'lastName', 'adminName' );" />
									</div>
								</div>
								
								<div class="col-md-12 col-sm-12 col-xs-12">
									<label class="col-md-4 col-sm-6 col-xs-12 control-label required" for="lastName">Last Name</label>
									<div class="col-md-6 col-sm-6 col-xs-12 form-group">
										<form:input path="lastName" class="form-control" maxlength="50" onkeyup="displayFullname('firstName', 'middleName', 'lastName', 'adminName' );" />
									</div>
								</div>
									
								<div class="col-md-12 col-sm-12 col-xs-12">
									<label class="col-md-4 col-sm-6 col-xs-12 control-label" for="adminName">Name</label>
									<div class="col-md-6 col-sm-6 col-xs-12 form-group">
										<form:input path="adminName" class="form-control fullname" title="${admin.adminName}" maxlength="110" readonly="true" />
									</div>
								</div>
								<div class="col-md-12 col-sm-12 col-xs-12 blackDiv">
								
								</div>
							</div>
						</div>
						
					</div>
				</div>
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
                          			<c:when test="${admin.photoUrl != null and admin.photoUrl != ''}">
                          				<img class="img-responsive avatar-view" src="${pageContext.request.contextPath}/resources/${admin.photoUrl}" height="128" alt="Avatar">
                          			</c:when>
                          			<c:otherwise>
										<img class="img-responsive avatar-view" src="${pageContext.request.contextPath}/resources/images/img.jpg" alt="Avatar" >
                          			</c:otherwise>
                          		</c:choose>
							</div>
						</div>
						<div class="row col-md-12 col-sm-12 col-xs-12 photoNotes">
							<label><b>Note:</b> <spring:eval expression="@commonConfigurer.getProperty(\'profile.image.notes\')" /></label>
						</div>
			            <div class="row form-group" >
		            		<div class="col-md-12 col-sm-12 col-xs-12" id="ProfilePhoto">
		            			<input type="file" id="file" name="uploadPhotoFile" accept="image/gif, image/png" onchange="checkValues();" />
								<div class=" file-upload-errors"></div>
							</div>
						</div>
                	</div>
				</div>
				
				
				<div class="x_panel">
                	<div class="x_title">
                    	<h2>Contact Information</h2>
                    	<div class="clearfix"></div>
                  	</div>
                  	<div class="x_content x_title_padding">
						
			            <div class="row">
							<div class="col-md-12 col-sm-12 col-xs-12 profile_left">
								<div class="col-md-12 col-sm-12 col-xs-12">
									<label class="col-md-3 col-sm-3 col-xs-12 control-label required" for="email">Email </label>
									<div class="col-md-9 col-sm-9 col-xs-12 form-group">
										<c:choose>
	                          				<c:when test="${loginDetail.userid == admin.adminId}">
	                          					<form:input path="email" class="form-control" maxlength="255" title="${admin.email}" readonly="true" />
	                          					<label style="color:maroon">(Enter a Unique Login Email ID)</label>
	                          				</c:when>
	                          				<c:otherwise>
												<form:input path="email" class="form-control" maxlength="255" />	
												<label style="color:maroon">(Enter a Unique Login Email ID)</label>	                          				
	                          				</c:otherwise>
	                          			</c:choose>
									</div>
								</div>
								<%-- <c:if test="${admin.adminId eq 0}">
									<div class="col-md-12 col-sm-12 col-xs-12">
										<label class="col-md-3 col-sm-3 col-xs-12 control-label required" for="email">Password </label>
										<div class="col-md-9 col-sm-9 col-xs-12 form-group">
											<form:password path="password" class="form-control" />
										</div>
									</div>
								</c:if> --%>
							</div>
							<div class="col-md-12 col-sm-12 col-xs-12">
								<div class="col-md-12 col-sm-12 col-xs-12">
									<label class="col-md-3 col-sm-3 col-xs-12 control-label required" for="tempPassword">Password</label>
									<div class="col-md-9 col-sm-9 col-xs-12 form-group">
										<input type="text" value="${admin.password}" class="form-control" readonly="readonly" />
									</div>
								</div>				
							</div>
							
							<div class="col-md-12 col-sm-12 col-xs-12">
								<div class="col-md-12 col-sm-12 col-xs-12">
									<label class="col-md-3 col-sm-3 col-xs-12 control-label required" for="phone">Phone </label>
									<div class="col-md-9 col-sm-9 col-xs-12 form-group">
										<form:input path="phone" class="form-control" onkeypress="return isNumber(event)"  />
									</div>
								</div>
								<div class="col-md-12 col-sm-12 col-xs-12">
									<label class="col-md-3 col-sm-3 col-xs-12 control-label" for="mobile">Mobile </label>
									<div class="col-md-9 col-sm-9 col-xs-12 form-group">
										<form:input path="mobile" class="form-control" onkeypress="return isNumber(event)"  />
										<form:hidden path="hideUserPermissionList"/>
									</div>
								</div>							
							</div>
						</div>
                	</div>
				</div>
			</div>
		</div>
		
 		<c:choose>
			<c:when test="${(admin.hideUserPermissionList =='true')}">
				<form:hidden path="adminAccCreation"/>
				<form:hidden path="groupCreation"/>
				<form:hidden path="groupDirectorCreation"/>
				<form:hidden path="clinicCreation"/>
				<form:hidden path="physicianCreation"/>
				<form:hidden path="assistantCreation"/>
				<form:hidden path="physicianApproval"/>
				<form:hidden path="patientCreation"/>
				<form:hidden path="patientApproval"/>
				<form:hidden path="prescriptionCreation"/>
				<form:hidden path="controlSubstanceDocUpload"/>
				<form:hidden path="remoteFileUpload"/>
				      
	        </c:when>
	      	<c:otherwise>
	      			<div class="row" style="width:100%;">
				      <div class="col-md-12 col-sm-12 col-xs-12">
						<div class="x_panel">
		                  	<div class="x_content x_title_padding">
								<div class="row"> 
									<div class="col-md-offset-3 col-md-6 col-sm-12 col-xs-12">
							           
										  <table class="table table-striped table-bordered permissionTable" style="width:100%;">
							            	<tr>
							            		<td class="col-md-1 col-sm-2 col-xs-2 x_title"></td>
							            		<td class="col-md-1 col-sm-8 col-xs-2 x_title"><div class="checkboxValue"><h2>User Permission List</h2></div></td>
							            		<td class="col-md-1 col-sm-2 col-xs-2 x_title"><input type="checkbox" id="allPermission" class="checkbox" /></td>
							            	</tr>
							            	<tr>
							            		<td class="col-md-1 col-sm-2 col-xs-2 text-center">1</td>
							            		<td class="col-md-9 col-sm-8 col-xs-8"><div class="checkboxValue">Pharmacy Admin Account Creation</div></td>
							            		<td class="col-md-2 col-sm-2 col-xs-2"><div class="checkboxbox"><form:checkbox path="adminAccCreation" value="Yes" /></div></td>
							            	</tr>
							            	<tr>
							            		<td class="col-md-1 col-sm-2 col-xs-2 text-center">2</td>
							            		<td class="col-md-9 col-sm-8 col-xs-8"><div class="checkboxValue">Group Creation</div></td>
							            		<td class="col-md-2 col-sm-2 col-xs-2"><div class="checkboxbox"><form:checkbox path="groupCreation" value="Yes" /></div></td>
							            	</tr>
							            	<tr>
							            		<td class="col-md-1 col-sm-2 col-xs-2 text-center">3</td>
							            		<td class="col-md-9 col-sm-8 col-xs-8"><div class="checkboxValue">Group Director Creation</div></td>
							            		<td class="col-md-2 col-sm-2 col-xs-2"><div class="checkboxbox"><form:checkbox path="groupDirectorCreation" value="Yes" /></div></td>
							            	</tr>
							            	<tr>
							            		<td class="col-md-1 col-sm-2 col-xs-2 text-center">4</td>
							            		<td class="col-md-9 col-sm-8 col-xs-8"><div class="checkboxValue">Clinic Creation</div></td>
							            		<td class="col-md-2 col-sm-2 col-xs-2"><div class="checkboxbox"><form:checkbox path="clinicCreation" value="Yes" /></div></td>
							            	</tr>
							            	<tr>
							            		<td class="col-md-1 col-sm-2 col-xs-2 text-center">5</td>
							            		<td class="col-md-9 col-sm-8 col-xs-8"><div class="checkboxValue">Physician Creation</div></td>
							            		<td class="col-md-2 col-sm-2 col-xs-2"><div class="checkboxbox"><form:checkbox path="physicianCreation" value="Yes" /></div></td>
							            	</tr>
							            	<tr>
							            		<td class="col-md-1 col-sm-2 col-xs-2 text-center">6</td>
							            		<td class="col-md-9 col-sm-8 col-xs-8"><div class="checkboxValue">Physician Assistant Creation</div></td>
							            		<td class="col-md-2 col-sm-2 col-xs-2"><div class="checkboxbox"><form:checkbox path="assistantCreation" value="Yes" /></div></td>
							            	</tr>
							            	<tr>
							            		<td class="col-md-1 col-sm-2 col-xs-2 text-center">7</td>
							            		<td class="col-md-9 col-sm-8 col-xs-8"><div class="checkboxValue">Physician Approval</div></td>
							            		<td class="col-md-2 col-sm-2 col-xs-2"><div class="checkboxbox"><form:checkbox path="physicianApproval" value="Yes" /></div></td>
							            	</tr>
							            	<tr>
							            		<td class="col-md-1 col-sm-2 col-xs-2 text-center">8</td>
							            		<td class="col-md-9 col-sm-8 col-xs-8"><div class="checkboxValue">Patient Creation</div></td>
							            		<td class="col-md-2 col-sm-2 col-xs-2"><div class="checkboxbox"><form:checkbox path="patientCreation" value="Yes" /></div></td>
							            	</tr>
							            	<tr>
							            		<td class="col-md-1 col-sm-2 col-xs-2 text-center">9</td>
							            		<td class="col-md-9 col-sm-8 col-xs-8"><div class="checkboxValue">Patient Approval</div></td>
							            		<td class="col-md-2 col-sm-2 col-xs-2"><div class="checkboxbox"><form:checkbox path="patientApproval" value="Yes" /></div></td>
							            	</tr>
							            	<tr>
							            		<td class="col-md-1 col-sm-2 col-xs-2 text-center">10</td>
							            		<td class="col-md-9 col-sm-8 col-xs-8"><div class="checkboxValue">Prescription Creation</div></td>
							            		<td class="col-md-2 col-sm-2 col-xs-2"><div class="checkboxbox"><form:checkbox path="prescriptionCreation" value="Yes" /></div></td>
							            	</tr>
							            	<tr>
							            		<td class="col-md-1 col-sm-2 col-xs-2 text-center">11</td>
							            		<td class="col-md-9 col-sm-8 col-xs-8"><div class="checkboxValue">Prescription Control Substance Documents Upload</div></td>
							            		<td class="col-md-2 col-sm-2 col-xs-2"><div class="checkboxbox"><form:checkbox path="controlSubstanceDocUpload" value="Yes" /></div></td>
							            	</tr>
							            	<tr>
							            		<td class="col-md-1 col-sm-2 col-xs-2 text-center">12</td>
							            		<td class="col-md-9 col-sm-8 col-xs-8"><div class="checkboxValue">Remote File Upload</div></td>
							            		<td class="col-md-2 col-sm-2 col-xs-2"><div class="checkboxbox"><form:checkbox path="remoteFileUpload" value="Yes" /></div></td>
							            	</tr>
							            </table>
									</div>
								</div>
		                	</div>
						</div>
					</div>
				</div>
	     	</c:otherwise>
	    </c:choose>
	    
		
			

		
		<div class="row">
			<div class="col-md-12 col-sm-12 col-xs-12">
				<div class="x_panel">
                  	<div class="x_content x_title_padding"> 
                  					
				 		<div class="row">
							<div class="col-md-12 col-sm-12 col-xs-12 profile_left">
								<div class="row form-group">
									<div class="col-md-6 col-sm-6 col-xs-12 text-right">
										
										<c:choose>
										<c:when test="${(loginDetail.type == 'Administrator' or loginDetail.type == 'Super Admin' or loginDetail.type == 'Admin')}">
										<c:if test="${(loginDetail.adminAccCreationPermission =='Yes')}">
											<button type="submit"  class="btn btn-success">Save</button>
										</c:if>
										</c:when>
										<c:otherwise>
											<button type="submit"  class="btn btn-success">Save</button>
										</c:otherwise>
										</c:choose>
								
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
			</div>
		</div>
		
		<c:if test="${loginDetail.type == 'Super Admin'}">
			<div class="row">
				<div class="col-md-12 col-sm-12 col-xs-12 formLink">
					<!-- <button type="button" id="goSummary" class="btn btn-primary" >go to Summary</button> -->
					<img class="img-responsive goSummary" src="${pageContext.request.contextPath}/resources/images/back_icon-3.png" title="Go to Summary">
				</div>
			</div>
		</c:if>
	</div>

	<form:hidden path="adminId" />
	<form:hidden path="password" />
	<!-- Error messages getting from property file -->
		<input type="hidden" id="err_pharmacy_name" value='<spring:eval expression="@adminConfigurer.getProperty(\'error.pharmacy_name\')" />' />
	<input type="hidden" id="err_status" value='<spring:eval expression="@adminConfigurer.getProperty(\'error.status\')" />' />
	
	<input type="hidden" id="err_userType" value='<spring:eval expression="@adminConfigurer.getProperty(\'error.userType\')" />' />
	
	<input type="hidden" id="err_date_registration" value='<spring:eval expression="@adminConfigurer.getProperty(\'error.date_of_registration\')" />' />
	<input type="hidden" id="err_fname" value='<spring:eval expression="@adminConfigurer.getProperty(\'error.first_name\')" />' />
	<input type="hidden" id="err_lname" value='<spring:eval expression="@adminConfigurer.getProperty(\'error.last_name\')" />' />
	<input type="hidden" id="err_email" value='<spring:eval expression="@adminConfigurer.getProperty(\'error.email\')" />' />
	<input type="hidden" id="err_emailformat" value='<spring:eval expression="@adminConfigurer.getProperty(\'error.email_format\')" />' />
	<input type="hidden" id="err_pwd" value='<spring:eval expression="@adminConfigurer.getProperty(\'error.password\')" />' />
	<input type="hidden" id="error_pwd_min_length" value='<spring:eval expression="@commonConfigurer.getProperty(\'error.password_min_length\')" />' />
	<input type="hidden" id="err_mobile" value='<spring:eval expression="@adminConfigurer.getProperty(\'error.mobile\')" />' />
	<input type="hidden" id="err_mobileformat" value='<spring:eval expression="@adminConfigurer.getProperty(\'error.mobile_format\')" />' />
	<input type="hidden" id="err_phoneformat" value='<spring:eval expression="@adminConfigurer.getProperty(\'error.phone_format\')" />' />
	<input type="hidden" id="err_phone" value='<spring:eval expression="@adminConfigurer.getProperty(\'error.phone\')" />' />
	
	
	<!-- /page content -->
	<%--includes the Front End UI validations--%>
	<script src="${pageContext.request.contextPath}/resources/js/pages/admin/adminAccount.js?v=1"></script>
	
	
	<script>
		displayFullname('firstName', 'middleName', 'lastName', 'name' );
	</script>
	
</form:form>