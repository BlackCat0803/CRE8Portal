 <%--
 Front End UI to save and load the Group Master details
 --%>
<%@ include file="../../../layout/taglib.jsp"%>
   <link href="${pageContext.request.contextPath}/resources/css/popup.css?v=2" rel="stylesheet" />
<c:if test="${loginDetail.type != 'Group Director'}">
	<div class="row">
		<div class="col-md-6 col-sm-6 col-xs-6">
			<img class="img-responsive goSummary" src="${pageContext.request.contextPath}/resources/images/back_icon-3.png" title="Go to Summary">
		</div>
		<div class="col-md-6 col-sm-6 col-xs-6">
			
			<c:choose>
		      <c:when test="${(loginDetail.type == 'Administrator' or loginDetail.type == 'Super Admin' or loginDetail.type == 'Admin')}">
			      	<c:if test="${(loginDetail.groupCreationPermission =='Yes')}">
			      		<button type="button" class="btn btn-primary newGroup">Create New Group</button>
			      	</c:if>
		      </c:when>
		      <c:otherwise>
		      			<button type="button" class="btn btn-primary newGroup">Create New Group</button>
		      </c:otherwise>
		    </c:choose>
		</div>
	</div>
</c:if>
		
<!-- page content -->
<div class="summaryTop" id="topPage">
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

	<form:form method="POST" action="saveGroupMaster" name="groupMasterForm" commandName="groupMasterForm"  enctype="multipart/form-data">
		<div class="row">
			<div class="col-md-6 col-sm-6 col-xs-12">
				<div class="x_panel" >
	                <div class="x_title">
	                  	<h2>Group Master</h2>
	                  	<div class="clearfix"></div>
	                </div>
		           	<div class="x_content x_title_padding">
					
						<div class="row">
							<div class="col-md-12 col-sm-12 col-xs-12 profile_left">
								<div class="col-md-12 col-sm-12 col-xs-12">
									<label class="col-md-4 col-sm-6 col-xs-12 control-label required" for="groupName">Group Name</label>
									<div class="col-md-8 col-sm-6 col-xs-12 form-group">
										<form:input path="groupName" class="form-control" maxlength="80" />
									</div>
								</div>
								
								<div class="col-md-12 col-sm-12 col-xs-12">
									<label class="col-md-4 col-sm-6 col-xs-12 control-label" for="groupDirectorName">Group Director</label>
									<div class="col-md-8 col-sm-6 col-xs-12 form-group">
										<form:textarea path="groupDirectorName" cssClass="form-control" title="${groupMasterForm.groupDirectorName}" rows="2" readonly="true" />
									</div>
								</div>								
								
								<div class="col-md-12 col-sm-12 col-xs-12">
									<label class="col-md-4 col-sm-6 col-xs-12 control-label required" for="status">Status</label>
									<div class="col-md-8 col-sm-6 col-xs-12 form-group">
										<c:choose>
											<c:when test="${loginDetail.type != 'Group Director'}">
													<c:choose>
												      <c:when test="${(loginDetail.type == 'Administrator' or loginDetail.type == 'Super Admin' or loginDetail.type == 'Admin')}">
													      	<c:choose>
														      	<c:when test="${(loginDetail.groupCreationPermission =='Yes')}">
														      		<form:select path="status" class="select2_single form-control" onchange="validateFormStatusEvent();">
																		<form:option value="">Select Status</form:option>
																		<form:option value="Active">Active</form:option>
																		<form:option value="Inactive">Inactive</form:option>
																	</form:select>
														      	</c:when>
														      	 <c:otherwise>
														      	 	<form:select path="status" class="select2_single form-control">
																		<form:option value="">Select Status</form:option>
																		<form:option value="Active">Active</form:option>
																		<form:option value="Inactive">Inactive</form:option>
																	</form:select>
														      	 </c:otherwise>
													      	</c:choose>
												      </c:when>
												      <c:otherwise>
												      			<form:select path="status" class="select2_single form-control" onchange="validateFormStatusEvent();">
																	<form:option value="">Select Status</form:option>
																	<form:option value="Active">Active</form:option>
																	<form:option value="Inactive">Inactive</form:option>
																</form:select>
												      </c:otherwise>
												    </c:choose>
		    
											
											
											
												
											</c:when>
											<c:otherwise>
												<form:input path="status" class="form-control" readonly="true" />
											</c:otherwise>
										</c:choose>
									</div>
								</div>
								
								<div class="col-md-12 col-sm-12 col-xs-12">
									<label class="col-md-4 col-sm-6 col-xs-12 control-label required" for="contactName">Contact Name</label>
									<div class="col-md-8 col-sm-6 col-xs-12 form-group">
										<form:input path="contactName" class="form-control" maxlength="110" />
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
										<form:input path="mobile" class="form-control" onkeypress="return isNumber(event)" />
									</div>
								</div>
							</div>
						</div>
						<!-- /page content -->
					</div>
				</div>
			</div>
			
			
			<div class="col-md-6 col-sm-6 col-xs-12">
				<div class="x_panel">
                	<div class="x_title">
                    	<h2>Group Logo</h2>
                    	<div class="clearfix"></div>
                  	</div>
                  	<div class="x_content x_title_padding">
						<div class="row form-group profile_img">
			            	<div class="col-md-12 col-sm-12 col-xs-12" id="crop-avatar">
			                    <c:choose>
									<c:when test="${groupMasterForm.logoFile != null and groupMasterForm.logoFile != ''}">
                        				<img class="img-responsive avatar-view" src="${pageContext.request.contextPath}/resources/${groupMasterForm.logoFile}" height="128" alt="Avatar">
                        			</c:when>
                        			<c:otherwise>
										<img class="img-responsive avatar-view" src="${pageContext.request.contextPath}/resources/images/default_logo.jpg" alt="Avatar" >
                        			</c:otherwise>
                         		</c:choose>
							</div>
						</div>
						<div class="row col-md-12 col-sm-12 col-xs-12 photoNotes">
							<label><b>Note:</b> <spring:eval expression="@commonConfigurer.getProperty(\'profile.image.notes\')" /></label>
						</div>
			            <div class="row form-group" >
		            		<div class="col-md-12 col-sm-12 col-xs-12" id="ProfileLogo">
		            		<label class="col-md-1 col-sm-1 col-xs-12 control-label required" for="logo"></label>
		            			<input type="file" name="uploadLogoFile" id="uploadLogoFile" accept="image/gif, image/png"/>
		            			<div class="file-upload-errors"></div>
		            			<input type="text" id="groupLogoFile" name="groupLogoFile" value='${groupMasterForm.logoFile}' style="visibility:hidden"/>
							</div>
						</div>
									
			           	<form:hidden path="groupId" />
						<!-- Error messages getting from property file -->
						<input type="hidden" id="err_group_name" value='<spring:eval expression="@groupMasterConfigurer.getProperty(\'error.group_name\')" />' />
						<input type="hidden" id="err_group_director" value='<spring:eval expression="@groupMasterConfigurer.getProperty(\'error.group_director\')" />' />
						
						<input type="hidden" id="err_contact_name" value='<spring:eval expression="@groupMasterConfigurer.getProperty(\'error.contact_name\')" />' />
						
						<input type="hidden" id="err_email" value='<spring:eval expression="@groupMasterConfigurer.getProperty(\'error.email\')" />' />
						<input type="hidden" id="err_email_format" value='<spring:eval expression="@groupMasterConfigurer.getProperty(\'error.email_format\')" />' />
						<input type="hidden" id="err_phone" value='<spring:eval expression="@groupMasterConfigurer.getProperty(\'error.phone\')" />' />
						<input type="hidden" id="err_phone_format" value='<spring:eval expression="@groupMasterConfigurer.getProperty(\'error.phone_format\')" />' />
						<input type="hidden" id="err_emailAlreadyExists" value='<spring:eval expression="@groupMasterConfigurer.getProperty(\'error.emailAlreadyExists\')" />' />
						<input type="hidden" id="err_status" value='<spring:eval expression="@groupMasterConfigurer.getProperty(\'error.status\')" />' />
						<input type="hidden" id="err_pwd" value='<spring:eval expression="@groupMasterConfigurer.getProperty(\'error.password\')" />' />
						<input type="hidden" id="err_mobile" value='<spring:eval expression="@groupMasterConfigurer.getProperty(\'error.mobile\')" />' />
						<input type="hidden" id="err_mobile_format" value='<spring:eval expression="@groupMasterConfigurer.getProperty(\'error.mobile_format\')" />' />
						<input type="hidden" id="err_group_logo" value='<spring:eval expression="@groupMasterConfigurer.getProperty(\'err.group_logo\')" />' />
                	</div>
                	<div class="x_content x_title_padding" style="height:30px"></div>
				</div>
			</div>
		</div>
		
		<div class="row">
			<div class="col-md-12 col-sm-12 col-xs-12">
				<div class="x_panel">
                  	<div class="x_content x_title_padding">
						<div class="row">
							<div class="col-md-12 col-sm-12 col-xs-12 text-center profile_left">
								<c:choose>
							      <c:when test="${(loginDetail.type == 'Administrator' or loginDetail.type == 'Super Admin' or loginDetail.type == 'Admin')}">
								      	<c:if test="${(loginDetail.groupCreationPermission =='Yes')}">
								      		<button type="submit" class="btn btn-success" >Save</button>
								      	</c:if>
							      </c:when>
							      <c:otherwise>
							      			<button type="submit" class="btn btn-success" >Save</button>
							      </c:otherwise>
							    </c:choose>
		    
								<!-- <input type="submit" id="submitgroupmasterHidden" style="display: none;"> --> 
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
                       		<div class="col-md-2 col-sm-6 col-xs-6 fieldTop">
								<select id="physicianStatus" class="form-control">
									<option value="">Select Status</option> 
									<option value="New">New</option>
									<option value="Profile Completed">Profile Completed</option>
									<option value="New Modifications">New Modifications</option>
									<option value="Approved">Approved</option>
									<option value="Denied">Denied</option>
								</select>
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
								<table id="groupPhysicianTable" class="table table-striped table-bordered">
									<thead>
										<tr class="Sort_heading_background">
											<th>Id</th>
											<th>Physician Name</th>
											<th>Phone</th>
											<th>Email</th>
											<th>Group</th>
											<th>Status</th>
											<th>Approve/Deny</th>
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
                       		<div class="col-md-2 col-sm-6 col-xs-6 fieldTop">
								<select id="assistantStatus" class="form-control">
									<option value="">Select Status</option> 
									<option value="Active">Active</option>
									<option value="Inactive">Inactive</option>
								</select>
                       		</div>
                       		<div class="col-md-1 col-sm-2 col-xs-6 fieldTop text-center">
                       			<button type="button" title="Search" id="assSearch" class="btn btn-primary" >Search</button>
                       		</div>
                       		<div class="col-md-1 col-sm-2 col-xs-6 fieldTop text-center">
                       			<button type="button" title="Clear" id="assClearSearch" class="btn btn-primary" >Clear</button>
                       		</div>
                     	</div>						
						
						
						<div class="row">
							<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 text-left table-responsive">
								<table id="groupAssistantTable" class="table table-striped table-bordered">
									<thead>
										<tr class="Sort_heading_background">
											<th>Id</th>
											<th>Assistant Name</th>
											<th>Physician Name</th>
											<th>Phone</th>
											<th>Email</th>
											<th>Group</th>
											<th>Status</th>
											<th>Active/ Inactive</th>
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
                    	<h2>Patient List</h2>
                    	<div class="clearfix"></div>
                  	</div>				
					<div class="x_content x_title_padding">
						
						<div class="row form-group">
                       		<div class="col-md-2 col-sm-6 col-xs-6 fieldTop">
								<input type="text" id="patientName" placeholder="Patient" class="form-control" >
                       		</div>
                       		<div class="col-md-2 col-sm-6 col-xs-6 fieldTop">
								<select id="patientStatus" class="form-control">
									<option value="">Select Status</option> 
									<option value="New">New</option>
									<option value="Profile Completed">Profile Completed</option>
									<option value="New Modifications">New Modifications</option>
									<option value="Approved">Approved</option>
									<option value="Denied">Denied</option>
								</select>
                       		</div>
                       		<div class="col-md-1 col-sm-2 col-xs-6 fieldTop text-center">
                       			<button type="button" title="Search" id="patientSearch" class="btn btn-primary" >Search</button>
                       		</div>
                       		<div class="col-md-1 col-sm-2 col-xs-6 fieldTop text-center">
                       			<button type="button" title="Clear" id="patientClearSearch" class="btn btn-primary" >Clear</button>
                       		</div>
                     	</div>
						
						
						<div class="row">
							<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 text-left table-responsive">
								<table id="groupPatientTable" class="table table-striped table-bordered">
									<thead>
										<tr class="Sort_heading_background">
											<th>Id</th>
											<th>Patient Name</th>
											<th>Physician Name</th>
											<th>Phone</th>
											<th>Email</th>
											<th>New Physician</th>
											<th>Status</th>
											<th>Approve /Deny</th>
										</tr>
									</thead>
								</table>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		
		
		<!-- Popup to Changing or Deactivating Group  -->
		<div id="dialog-overlay6"></div>
		<div id="dialog-box6">
			<div class="dialog-content6">
				<div id="dialog-message6"></div>
                <div class="row">
                    <div class="col-md-12" style="border-right: 1px dotted #C2C2C2;padding-right: 30px;background-repeat: no-repeat; background-position: 0 0; background-attachment: fixed;">
                        <div class="form-group text-center">
                          	<h4 class="modal-title" id="myModalLabel">Changing of Groups or Deactivating a Group</h4>
                       		
							<div class="form-group">
								<div class="col-sm-12">Do you wish to re-assign the Group for all Physicians, Assistants, Patients of this Group to a different Group?</div>
								<div class="row">
									<div class="col-sm-6 text-center">
										Group:
									</div>
									<div class="col-sm-6 text-center">
										<form:select path="otherGroupId" id="otherGroupId"  class="select2_single form-control">
												<form:option value="0">Select</form:option>
												<form:options items="${othergroupList}" itemLabel="groupName" itemValue="id" />
										</form:select>
										<div id="errOtherGroupMsg" style="color:maroon;display:none">Please select the Group to re-assign</div>
									</div>
								</div>
								<div class="row">
									<br>
								</div>
								<div class="row">
									<div class="col-sm-3 text-right">
									
									</div>
									<div class="col-sm-2 text-right">
										<button id="reassign" type="button" onclick="callReassignGroup()"  class="btn btn-primary btn-sm" title="Click 'Yes' to re-assign the Group">
											Yes</button>
									</div>
									<div class="col-sm-2 text-center">
										<button id="nottoreassign" type="button" onclick="callDeactivateGroup()" class="btn btn-primary btn-sm" onclick="div_hide()" title="Click 'No' to de-activate the Group">
	                                    	No</button>
									</div>
									<div class="col-sm-2 text-left">
										<button id="cancel" type="button" class="btn btn-primary btn-sm" onclick="div_hide()" title="Click 'Cancel' to undo">
	                                    	Cancel</button>
									</div>
									<div class="col-sm-3 text-right">
									
									</div>
								</div>
								<div class="col-sm-1"></div>
							</div>
							<div class="form-group"><br/></div>
							
						</div>
                   	</div>
               	</div>
			</div>
		</div>
		<input type="hidden" name="reAssignGroup" id="reAssignGroup"/>
		<input type="hidden" name="deactivateGroup" id="deactivateGroup"/>
		
		
		<!-- Popup to get Deactivating Physician -->
		<div id="dialog-overlay8"></div>
		<div id="dialog-box8">
			<div class="dialog-content8">
				<div id="dialog-message8"></div>
                <div class="row">
                    <div class="col-md-12" style="border-right: 1px dotted #C2C2C2;padding-right: 30px;background-repeat: no-repeat; background-position: 0 0; background-attachment: fixed;">
                        <div class="form-group text-center">
                          	<h4 class="modal-title" id="myModalLabel">Deactivating a Physician</h4>
                       		
							<div class="form-group">
								<div class="col-sm-12">
								Do you wish to re-assign all the Patients of this Physician to a different Physician?
								</div>
								<div class="row">
									<div class="col-sm-6 text-center">
										Physician:
									</div>
									<div class="col-sm-6 text-center">
										<form:select path="otherPhysicianId" id="otherPhysicianId"  class="select2_single form-control">
												<form:option value="0">Select</form:option>
												<form:options items="${otherPhysicianList}" itemLabel="physicianName" itemValue="id" />
										</form:select>
										<div id="errOtherPhysicianMsg" style="color:maroon;display:block">Please select the Physician to re-assign</div>
									</div>
								</div>
								<div class="row">
									<br>
								</div>
								<div class="row">
									<div class="col-sm-3 text-right">
									
									</div>
									<div class="col-sm-2 text-right">
										<button id="reassign" type="button" onclick="callReassignPhysician()"  class="btn btn-primary btn-sm" title="Click 'Yes' to re-assign the Physician">
											Yes</button>
									</div>
									<div class="col-sm-2 text-center">
										<button id="nottoreassign" type="button" onclick="callDeactivatePhysician()" class="btn btn-primary btn-sm"  title="Click 'No' to de-activate the Physician">
	                                    	No</button>
									</div>
									<div class="col-sm-2 text-left">
										<button id="cancel" type="button" class="btn btn-primary btn-sm" onclick="div_hide8()" title="Click 'Cancel' to undo">
	                                    	Cancel</button>
									</div>
									<div class="col-sm-3 text-right">
									
									</div>
								</div>
								<div class="col-sm-1"></div>
							</div>
							<div class="form-group"><br/></div>
							
						</div>
                   	</div>
               	</div>
			</div>
		</div>
		<input type="hidden" name="reAssignPhysician" id="reAssignPhysician"/>
		<input type="hidden" name="deactivatePhysician" id="deactivatePhysician"/>
		<input type="hidden" id="info_reassignphysician" value='<spring:eval expression="@physicianConfigurer.getProperty(\'info.reassignphysician\')" />' />
		<input type="hidden" id="info_deactivatephysician" value='<spring:eval expression="@physicianConfigurer.getProperty(\'info.deactivatephysician\')" />' />
				

		<form:hidden path="physicianId" />
		<form:select path="dropDownGroupId" style="display:none;">
			<form:option value="0">Select</form:option>
			<form:options items="${othergroupList}" itemLabel="groupName" itemValue="id" />
		</form:select>
		
		<form:hidden path="assistantId" />

		<form:hidden path="patientId" />
		<form:select path="groupPhysicianId" style="display:none;">
			<form:option value="0">Select</form:option>
			<form:options items="${physicianList}" itemLabel="physicianName" itemValue="id" />
		</form:select>
	</form:form>
	
	<%-- <form:form method="POST" name="physicianPage" commandName="physicianForm" action="${pageContext.request.contextPath}/physician/editPhysicianProfile"  >
		<form:hidden path="physicianId" />
		<form:select path="dropDownGroupId" style="display:none;">
			<form:option value="0">Select</form:option>
			<form:options items="${groupList}" itemLabel="groupName" itemValue="id" />
		</form:select>
	</form:form> --%>

	<%-- <form:form method="POST" name="assistantPage" commandName="assistantForm" action="${pageContext.request.contextPath}/physician/editPhysicianassistantaccount"   >
		<form:hidden path="assistantId" />
	</form:form> --%>

	<%--<form:form method="POST" name="patientPage" commandName="patientForm" action="${pageContext.request.contextPath}/patient/editPatientAccount"   >
		<form:hidden path="patientId" />
		<form:select path="groupPhysicianId" style="display:none;">
			<form:option value="0">Select</form:option>
			<form:options items="${physicianList}" itemLabel="physicianName" itemValue="id" />
		</form:select>
	</form:form> --%>
	
</div>

<%-- <c:if test="${loginDetail.type != 'Group Director'}">
	<div class="row">
		<div class="col-md-2 col-sm-3 col-xs-6">
			<img class="img-responsive goSummary" src="${pageContext.request.contextPath}/resources/images/back_icon-3.png" title="Go to Summary">
		</div>
	</div>
</c:if> --%>

<script>
	var groupLogo="${groupMasterForm.logoFile}";
	var urlPath="${pageContext.request.contextPath}";
</script>	
<!-- /page content -->
<%--includes the Front End UI validations--%>
<script src="${pageContext.request.contextPath}/resources/js/pages/pharmacygroup/groupMaster.js?v=1"></script>

<c:choose>
	<c:when test="${(loginDetail.type == 'Administrator' or loginDetail.type == 'Super Admin' or loginDetail.type == 'Admin')}">
	<c:if test="${(loginDetail.physicianCreationPermission !='Yes')}">
	<script>
	fnPhysicianShowHide(4);	
	</script>
	</c:if>
	<c:if test="${(loginDetail.physicianApprovalPermission !='Yes')}">
	<script>
	fnPhysicianShowHide(6);		
	</script>
	</c:if>
	<c:if test="${(loginDetail.assistantCreationPermission !='Yes')}">
	<script>
	fnAssistantShowHide(5);	
	fnAssistantShowHide(7);		
	</script>
	</c:if>
	<c:if test="${(loginDetail.patientCreationPermission !='Yes')}">
	<script>
	fnPatientShowHide(5);		
	</script>
	</c:if>
	<c:if test="${(loginDetail.patientApprovalPermission !='Yes')}">
	<script>
	fnPatientShowHide(7);		
	</script>
	</c:if>
	</c:when>
</c:choose>
