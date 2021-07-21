 <%--
 Front End UI to save and load the Group Director details
 --%>
<%@ include file="../../../layout/taglib.jsp" %>

<c:if test="${loginDetail.type != 'Group Director'}">
	<div class="row">
		<div class="col-md-6 col-sm-6 col-xs-6">
			<img class="img-responsive goSummary" src="${pageContext.request.contextPath}/resources/images/back_icon-3.png" title="Go to Summary">
		</div>
		<div class="col-md-6 col-sm-6 col-xs-6">
			<%-- <img class="img-responsive goNewRec" src="${pageContext.request.contextPath}/resources/images/add.png" title="Create New Group"> --%>
			
			<c:choose>
		      <c:when test="${(loginDetail.type == 'Administrator' or loginDetail.type == 'Super Admin' or loginDetail.type == 'Admin')}">
			      	<c:if test="${(loginDetail.groupDirectorCreationPermission =='Yes')}">
			      		<button type="button" class="btn btn-primary goNewRec" >Create New Group Director</button>
			      	</c:if>
		      </c:when>
		      <c:otherwise>
		      			<button type="button" class="btn btn-primary goNewRec" >Create New Group Director</button>
		      </c:otherwise>
		    </c:choose>
		</div>
	</div>
</c:if>

<!-- page content -->
<form:form method="POST" action="saveGroupDirector" name="groupDirectorForm" commandName="groupDirectorForm" enctype="multipart/form-data">
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
				<div class="x_panel ">
                  	<div class="x_title">
                    	<h2>Group Director</h2>
                    	<div class="clearfix"></div>
                  	</div>				
					<div class="x_content x_title_padding">
						<div class="row">
							<div class="col-md-12 col-sm-12 col-xs-12 profile_left">
								<div class="row">
									<div class="col-md-12 col-sm-12 col-xs-12 profile_left">
										
										<div class="col-md-12 col-sm-12 col-xs-12">
											<label class="col-md-4 col-sm-6 col-xs-12 control-label required" for="group-name">Group</label>
											<div class="col-md-6 col-sm-6 col-xs-12 form-group">
												<c:choose>
				                					<c:when test="${groupUserLoggin == 'yes'}">
				                						<input type="text" value="${groupDirectorForm.groupName}" title="${groupDirectorForm.groupName}" class="form-control" readonly="readonly"  />
				                						<form:hidden path="groupId" />
				                					</c:when>
				                					<c:otherwise>
														<form:select path="groupId" class="select2_single form-control" >
															<form:option value="0">Select</form:option>
															<form:options items="${groupList}" itemLabel="groupName" itemValue="id" />
														</form:select>
													</c:otherwise>
												</c:choose>
											</div>
										</div>
										
										<div class="col-md-12 col-sm-12 col-xs-12">
											<label class="col-md-4 col-sm-6 col-xs-12 control-label required" for="status">Status</label>
											<div class="col-md-6 col-sm-6 col-xs-12 form-group">
												<c:choose>
				                					<c:when test="${groupUserLoggin == 'yes'}">
				                						<form:input path="status" class="form-control" title="${groupDirectorForm.status}" readonly="true" />
				                					</c:when>
				                					<c:otherwise>
														<form:select path="status" class="select2_single form-control" >
															<form:option value="">Select</form:option>
															<form:option value="Active">Active</form:option>
															<form:option value="Inactive">Inactive</form:option>
														</form:select>
				                					</c:otherwise>
				                				</c:choose>
											</div>
										</div>
										
										<div class="col-md-12 col-sm-12 col-xs-12">
											<label class="col-md-4 col-sm-6 col-xs-12 control-label required" for="dob">Registration Date</label>
											<div class='col-md-6 col-sm-6 col-xs-12 input-group form-group' style="padding: 0px 9px 0px; float: left;">
												<c:choose>
													<c:when test="${groupUserLoggin == 'yes'}">
														<form:input path="dateRegistrated" class="form-control calendarIcon" title="${groupDirectorForm.dateRegistrated}" readonly="true" />
													</c:when>
													<c:otherwise>
														<form:input path="dateRegistrated" class="form-control calendarIcon" />
													</c:otherwise>
												</c:choose>
											</div>
										</div>
										
										<div class="col-md-12 col-sm-12 col-xs-12">
											<label class="col-md-4 col-sm-6 col-xs-12 control-label required" for="first-name">First Name</label>
											<div class="col-md-6 col-sm-6 col-xs-12 form-group">
												<form:input path="firstName" class="form-control" maxlength="50" onkeyup="displayFullname('firstName', 'middleName', 'lastName', 'groupDirectorName' );" />
											</div>
										</div>
										<div class="col-md-12 col-sm-12 col-xs-12">
											<label class="col-md-4 col-sm-6 col-xs-12 control-label" for="middle-name">Middle Name</label>
											<div class="col-md-6 col-sm-6 col-xs-12 form-group">
												<form:input path="middleName" class="form-control" maxlength="10"  onkeyup="displayFullname('firstName', 'middleName', 'lastName', 'groupDirectorName' );" />
											</div>
										</div>
										<div class="col-md-12 col-sm-12 col-xs-12">
											<label class="col-md-4 col-sm-6 col-xs-12 control-label required" for="last-name">Last Name</label>
											<div class="col-md-6 col-sm-6 col-xs-12 form-group">
												<form:input path="lastName" class="form-control" maxlength="50"  onkeyup="displayFullname('firstName', 'middleName', 'lastName', 'groupDirectorName' );" />
											</div>
										</div>

										<div class="col-md-12 col-sm-12 col-xs-12">
											<label class="col-md-4 col-sm-6 col-xs-12 control-label" for="print-name">Name</label>
											<div class="col-md-6 col-sm-6 col-xs-12 form-group">
												<form:input path="groupDirectorName" class="form-control fullname" title="${groupDirectorForm.groupDirectorName}" readonly="true" />
											</div>
										</div>
										<div class="x_content x_title_padding" style="height:65px">
                						</div>
									</div>
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
				                	<c:when test="${groupDirectorForm.photoFile != null and groupDirectorForm.photoFile != ''}">
				                    	<img class="" src="${pageContext.request.contextPath}/resources/${groupDirectorForm.photoFile}" height="128" alt="Avatar">
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
		            			<input type="file" id="file" name="uploadPhotoFile" accept="image/gif, image/png"  onchange="checkValues();" />
								<div class=" file-upload-errors"></div>
							</div>
						</div>
                	</div>
				</div>
				
				<div class="x_panel">
                	
                  	<div class="x_content x_title_padding">
			            <div class="row">
							<div class="col-md-12 col-sm-12 col-xs-12 profile_left">
								<div class="col-md-12 col-sm-12 col-xs-12">
									<label class="col-md-3 col-sm-3 col-xs-12 control-label required" for="email">Email </label>
									<div class="col-md-9 col-sm-9 col-xs-12 form-group">
										<c:choose>
											<c:when test="${groupUserLoggin == 'yes'}">
												<form:input path="email" class="form-control" maxlength="255" title="${groupDirectorForm.email}" readonly="true" />
											<label style="color:maroon">(Enter a Unique Login Email ID)</label>
											</c:when>
											<c:otherwise>
												<form:input path="email" class="form-control" maxlength="255" />
											<label style="color:maroon">(Enter a Unique Login Email ID)</label>
											</c:otherwise>
										</c:choose>
									</div>
								</div>
								
							</div>
							
							
							<div class="col-md-12 col-sm-12 col-xs-12">
								<div class="col-md-12 col-sm-12 col-xs-12">
									<label class="col-md-3 col-sm-3 col-xs-12 control-label required" for="tempPassword">Password</label>
									<div class="col-md-8 col-sm-9 col-xs-12 form-group">
										<input type="text" value="${groupDirectorForm.password}" class="form-control" readonly="readonly" />
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
                  	<div class="x_content x_title_padding">
				 		<div class="row">
							<div class="col-md-12 col-sm-12 col-xs-12 profile_left">
								<div class="row form-group">
									<div class="col-md-6 col-sm-6 col-xs-12 text-right">
										
											<c:choose>
										      <c:when test="${(loginDetail.type == 'Administrator' or loginDetail.type == 'Super Admin' or loginDetail.type == 'Admin')}">
											      	<c:if test="${(loginDetail.groupDirectorCreationPermission =='Yes')}">
											      		<button type="submit"  class="btn btn-success">Save</button>
											      	</c:if>
										      </c:when>
										      <c:otherwise>
										      			<button type="submit"  class="btn btn-success">Save</button>
										      </c:otherwise>
										    </c:choose>
										<!-- <input type="submit" id="submitGroupdirectorHidden" style="display: none;"> -->
									</div>
									
									<!-- <div class="col-md-6 col-sm-6 col-xs-12 form-group text-left">
										<button type="button"  class="btn btn-success" onclick="sendLoginEmail();">Send Credentials Email</button>
									</div> -->
									
								</div>
							</div>
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
		
		<c:if test="${loginDetail.type != 'Group Director'}">
			<div class="row">
				<div class="col-md-12 col-sm-12 col-xs-12 formLink">
					<!-- <button type="button" id="goSummary" class="btn btn-primary" >go to Summary</button> -->
					<img class="img-responsive goSummary" src="${pageContext.request.contextPath}/resources/images/back_icon-3.png" title="Go to Summary">
				</div>
			</div>
		</c:if>
	</div>

	<form:hidden path="groupDirectorId" />
	<form:hidden path="password" />
	<!--  for moving to Admin summary page Admin and Super admin users  -->
	<input type="hidden" id="serverUrl" value="${pageContext.request.contextPath}" />
	
	<!-- Error messages getting from property file -->
	
	<input type="hidden" id="err_group_name" value='<spring:eval expression="@groupDirectorConfigurer.getProperty(\'error.group_name\')" />' />
	<input type="hidden" id="err_status" value='<spring:eval expression="@groupDirectorConfigurer.getProperty(\'error.status\')" />' />
	<input type="hidden" id="err_date_registration" value='<spring:eval expression="@groupDirectorConfigurer.getProperty(\'error.date_of_registration\')" />' />
	<input type="hidden" id="err_fname" value='<spring:eval expression="@groupDirectorConfigurer.getProperty(\'error.first_name\')" />' />
	<input type="hidden" id="err_lname" value='<spring:eval expression="@groupDirectorConfigurer.getProperty(\'error.last_name\')" />' />
	<input type="hidden" id="err_email" value='<spring:eval expression="@groupDirectorConfigurer.getProperty(\'error.email\')" />' />
	<input type="hidden" id="err_email_format" value='<spring:eval expression="@groupDirectorConfigurer.getProperty(\'error.email_format\')" />' />
	<input type="hidden" id="err_pwd" value='<spring:eval expression="@groupDirectorConfigurer.getProperty(\'error.password\')" />' />
	<input type="hidden" id="error_pwd_min_length" value='<spring:eval expression="@commonConfigurer.getProperty(\'error.password_min_length\')" />' />
	<input type="hidden" id="err_mobile" value='<spring:eval expression="@groupDirectorConfigurer.getProperty(\'error.mobile\')" />' />
	<input type="hidden" id="err_phone" value='<spring:eval expression="@groupDirectorConfigurer.getProperty(\'error.phone\')" />' />
	<input type="hidden" id="err_phone_format" value='<spring:eval expression="@groupDirectorConfigurer.getProperty(\'error.phone_format\')" />' />
	<input type="hidden" id="err_mobile_format" value='<spring:eval expression="@groupDirectorConfigurer.getProperty(\'error.mobile_format\')" />' />
	
	<!-- /page content -->
</form:form>
<%--includes the Front End UI validations--%>
<script src="${pageContext.request.contextPath}/resources/js/pages/pharmacygroup/groupDirector.js?v=1"></script>