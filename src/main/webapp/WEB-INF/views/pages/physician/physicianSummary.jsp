 <%--
 Front End UI to view the Physician Account Summary
 --%>
<%@ include file="../../../layout/taglib.jsp"%>
<link href="${pageContext.request.contextPath}/resources/css/popup.css?v=3" rel="stylesheet" />
<!-- page content -->
<div class="">
	<div class="row">
		<div class="col-md-12 col-sm-12 col-xs-12 formLink">
			<%-- <a href="${pageContext.request.contextPath}/physician/createPhysicianAccount">Create new physician</a> --%>
			
			<c:choose>
		      <c:when test="${(loginDetail.type == 'Administrator' or loginDetail.type == 'Super Admin' or loginDetail.type == 'Admin')}">
			      	<c:if test="${(loginDetail.physicianCreationPermission =='Yes')}">
			      		<button type="button" id="newPhysicianAcc" class="btn btn-primary" >Create New Physician</button>
			      	</c:if>
		      </c:when>
		      <c:otherwise>
		      			<button type="button" id="newPhysicianAcc" class="btn btn-primary" >Create New Physician</button>
		      </c:otherwise>
		    </c:choose>
		</div>
	</div>

	<div class="row">
		<div class="col-md-12 col-sm-12 col-xs-12">
			<div class="x_panel profileBorder">
                <div class="x_title">
                  	<h2>Physician Summary</h2>
                  	<div class="clearfix"></div>
                </div>
                
                <div class="x_content x_title_padding">
					<c:if test="${not empty message}">
						<div class="row">
							<div class="col-sm-offset-1 col-sm-10  fontSize14 text-center">
								<div class="alert alert-success fade in">
									<a href="#" class="close" data-dismiss="alert">&times;</a>  &nbsp;&nbsp;&nbsp; ${message}
								</div>
							</div>
						</div>
					</c:if>
				
					<form:form method="GET" name="form" commandName="form">
					
						<div class="row">
							<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 text-left">
								<div class="row form-group">
		                       		<div class="col-md-2 col-sm-6 col-xs-6 fieldTop">
										<select class="autoCompleterPhysicianId select2_single form-control" style="width:100%;color:black;" name="autoCompleterPhysicianId"  id="autoCompleterPhysicianId"></select>
										<input type="hidden" id="physicianName" class="form-control"/>
		                       		</div>
		                       		<div class="col-md-2 col-sm-6 col-xs-6 fieldTop">
										<input type="text" id="deaSearch" name="deaSearch" placeholder="DEA" class="form-control" />
		                       		</div>
		                       		<div class="col-md-2 col-sm-6 col-xs-6 fieldTop">
										<select id="status" class="form-control">
											<option value="">Select Status</option> 
											<option value="New">New</option>
											<option value="Profile Completed">Profile Completed</option>
											<option value="New Modifications">New Modifications</option>
											<option value="Approved">Approved</option>
											<option value="Denied">Denied</option>
										</select>
		                       		</div>
									<c:choose>
										<c:when test="${loginDetail.type == 'Group Director'}">
											<input type="hidden" id="groupId" value="${groupId}" />
										</c:when>
										<c:otherwise>
				                       		<div class="col-md-2 col-sm-6 col-xs-6 fieldTop">
												<form:select path="groupId" class="select2_single form-control" id="groupId">
													<form:option value="0">Select Group</form:option>
													<form:options items="${groupList}" itemLabel="groupName" itemValue="id" />
												</form:select>
											</div>
										</c:otherwise>
									</c:choose>
		                     		<div class="col-md-1 col-sm-6 col-xs-6 fieldTop text-center">
		                       			<button type="button" title="Search" id="newSearch" class="btn btn-primary" >Search</button>
		                       		</div>
		                       		<div class="col-md-1 col-sm-6 col-xs-6 fieldTop text-center">
		                       			<input type="hidden" id="checkUserType" value="${loginDetail.type}" />
		                       			<button type="button" id="clearSearch" title="Clear" class="btn btn-primary" >Clear</button>
		                       		</div>
		                     	</div>
							</div>
						</div>
						
						<!-- Popup to get Physician Signature  -->
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
													<!-- <div class="col-sm-2 text-left">
														<button id="cancel" type="button" class="btn btn-primary btn-sm" onclick="div_hide8()" title="Click 'Cancel' to undo">
					                                    	Cancel</button>
													</div> -->
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
					</form:form>

					<div class="row">
						<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 text-left table-responsive">
							<table id="summaryTable" class="table table-striped table-bordered">
								<thead>
									<tr class="heading_background">
										<th>Id</th>
										<th>Edit</th>
										<th>Name</th>
										<th>DEA</th>
										<th>Group</th>
										<th>Change Group</th>
										<th>City</th>
										<th>Email</th>
										<th>Mobile</th>
										<th>Status</th>
										<th>Approve / Deny</th>
									</tr>
								</thead>
							</table>
						</div>
					</div>
					
					<form:form method="POST" name="editPage" commandName="form" action="editPhysicianProfile"   >
						<form:hidden path="physicianId" />
						
						<form:select path="dropDownGroupId" style="display:none;">
							<form:option value="0">Select</form:option>
							<form:options items="${changegroupList}" itemLabel="groupName" itemValue="id" />
						</form:select>
					</form:form>
					
				</div>
			</div>
		</div>
	</div>
</div>
<!-- /page content -->
<script>
var urlPath="${pageContext.request.contextPath}/";
</script>
<%--includes the Front End UI validations--%>
<script src="${pageContext.request.contextPath}/resources/js/pages/physician/physicianAccountSummary.js?v=4"></script>
<c:choose>
	<c:when test="${(loginDetail.type == 'Administrator' or loginDetail.type == 'Super Admin' or loginDetail.type == 'Admin')}">
	<c:if test="${(loginDetail.physicianCreationPermission !='Yes')}">
	<script>
		fnShowHide(5);	
	</script>
	</c:if>
	<c:if test="${(loginDetail.physicianApprovalPermission !='Yes')}">
	<script>
		fnShowHide(10);		
	</script>
	</c:if>
	</c:when>
</c:choose>
