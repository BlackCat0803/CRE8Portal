 <%--
 Front End UI to view the Physician Assistant Account Summary
 --%>
<%@ include file="../../../layout/taglib.jsp"%>

<!-- page content -->
<div class="">
	<c:if test="${loginDetail.type != 'Physician' or (loginDetail.type == 'Physician' and loginDetail.status != 'New') }">
		<div class="row">
			<div class="col-md-12 col-sm-12 col-xs-12 formLink">
				
				
				<c:choose>
				<c:when test="${(loginDetail.type == 'Administrator' or loginDetail.type == 'Super Admin' or loginDetail.type == 'Admin')}">
				<c:if test="${(loginDetail.assistantCreationPermission =='Yes')}">
				<button type="button" id="newAssistAcc" class="btn btn-primary" >Create New Physician Assistant</button>
				</c:if>
				</c:when>
				<c:otherwise>
				<button type="button" id="newAssistAcc" class="btn btn-primary" >Create New Physician Assistant</button>
				</c:otherwise>
				</c:choose>
				
				
			</div>
		</div>
	</c:if>
	
	<form:form method="GET" name="form" commandName="form">
		<div class="row">
			<div class="col-md-12 col-sm-12 col-xs-12">
				<div class="x_panel profileBorder">
	                <div class="x_title">
	                  	<h2>Physician Assistant Summary</h2>
	                  	<div class="clearfix"></div>
	                </div>
	                
	                <div class="x_content x_title_padding">
						<c:if test="${not empty message}">
							<div class="row">
								<div class="col-sm-offset-1 col-sm-10  fontSize14 text-center">
									<div class="alert alert-success fade in">
										<a href="#" class="close" data-dismiss="alert">&times;</a> &nbsp;&nbsp;&nbsp; ${message}
									</div>
								</div>
							</div>
						</c:if>
	
						<c:if test="${loginDetail.type == 'Physician'}">
							<div class="row">
								<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 text-left">
									<div class="row form-group">
			                       		<div class="col-md-2 col-sm-6 col-xs-6 fieldTop">
			                       			<select class="autoCompleterPhysicianAssistantId select2_single form-control" style="width:100%;color:black;" name="autoCompleterPhysicianAssistantId"  id="autoCompleterPhysicianAssistantId"></select>
											<input type="hidden" id="assistantName" class="form-control" >
											<input type="hidden" id="clinicName" value="" />
			                       		</div>
			                       		<c:choose>
											<c:when test="${loginDetail.type == 'Group Director' or loginDetail.type == 'Physician'}">
												<input type="hidden" id="groupId" value="${groupId}" />
											</c:when>
											<c:otherwise>
					                       		<div class="col-md-3 col-sm-6 col-xs-6 fieldTop">
													<form:select path="groupId" class="select2_single form-control" id="groupId">
														<form:option value="0">Select Group</form:option>
														<form:options items="${groupList}" itemLabel="groupName" itemValue="id" />
													</form:select>
					                       		</div>
											</c:otherwise>
										</c:choose>
			                       		<div class="col-md-2 col-sm-6 col-xs-6 fieldTop">
											<select id="status" class="form-control">
												<option value="">Select Status</option> 
												<option value="Active">Active</option>
												<option value="Inactive">Inactive</option>
											</select>
			                       		</div>
			                       		<div class="col-md-1 col-sm-6 col-xs-6 fieldTop text-center">
			                       			<button type="button" title="Search" id="newSearch" class="btn btn-primary" >Search</button>
			                       		</div>
			                       		<div class="col-md-1 col-sm-6 col-xs-6 fieldTop text-center">
			                       			<button type="button" id="clearSearch" title="Clear" class="btn btn-primary" >Clear</button>
			                       		</div>
			                     	</div>
								</div>
							</div>
						</c:if>
					
						<c:if test="${loginDetail.type != 'Physician'}">
							<div class="row">
								<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 text-left">
									<div class="row form-group">
			                       		<div class="col-md-2 col-sm-6 col-xs-6 fieldTop">
			                       			<select class="autoCompleterPhysicianAssistantId select2_single form-control" style="width:100%;color:black;" name="autoCompleterPhysicianAssistantId"  id="autoCompleterPhysicianAssistantId"></select>
											<input type="hidden" id="assistantName" class="form-control" >
											<input type="hidden" id="clinicName" value="" />
			                       		</div>
			                       		<div class="col-md-2 col-sm-6 col-xs-6 fieldTop">
			                       			<select class="autoCompleterPhysicianId select2_single form-control" style="width:100%;color:black;" name="autoCompleterPhysicianId"  id="autoCompleterPhysicianId"></select>
											<input type="hidden" id="phyName" class="form-control" >
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
			                       		<div class="col-md-2 col-sm-6 col-xs-6 fieldTop">
											<select id="status" class="form-control">
												<option value="">Select Status</option> 
												<option value="Active">Active</option>
												<option value="Inactive">Inactive</option>
											</select>
			                       		</div>
			                       		<div class="col-md-1 col-sm-6 col-xs-6 fieldTop text-center">
			                       			<button type="button" title="Search" id="newSearch" class="btn btn-primary" >Search</button>
			                       		</div>
			                       		<div class="col-md-1 col-sm-6 col-xs-6 fieldTop text-center">
			                       			<button type="button" id="clearSearch" title="Clear" class="btn btn-primary" >Clear</button>
			                       		</div>
			                       	</div>
								</div>
							</div>					
						</c:if>
	
						<div class="row">
							<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 text-left table-responsive">
								<c:if test="${loginDetail.type == 'Physician'}">
									<table id="phyPhysicianAsstSummaryTable" class="table table-striped table-bordered">
										<thead>
											<tr class="heading_background">
												<th>Id</th>
												<th>Edit</th>
												<th>Assistant</th>
												<th>Group</th>
												<th>Change Group</th>
												<th>Clinic</th>
												<th>Email</th>
												<th>Mobile</th>
												<th>Status</th>
												<th>Active/ Inactive</th>
											</tr>
										</thead>
									</table>						
								</c:if>
								<c:if test="${loginDetail.type != 'Physician'}">
									<table id="adminPhysicianAsstSummaryTable" class="table table-striped table-bordered">
										<thead>
											<tr class="heading_background">
												<th>Id</th>
												<th>Edit</th>
												<th>Assistant</th>
												<th>Physician</th>
												<th>Group</th>
												<th>Change Group</th>
												<th>Clinic</th>
												<th>Email</th>
												<th>Mobile</th>
												<th>Status</th>
												<th>Active/ Inactive</th>
											</tr>
										</thead>
									</table>
								</c:if>
								<input type="hidden" id="userId" value="${userid}" />
							</div>
						</div>
						<input type="hidden" id="checkUserType" value="${loginDetail.type}" />
					</div>
				</div>
			</div>
		</div>
	</form:form>
	
	<form:form method="POST" name="editPage" commandName="form" action="editPhysicianassistantaccount"   >
		<form:hidden path="assistantId" />
		<form:select path="dropDownGroupId" style="display:none;">
			<form:option value="0">Select</form:option>
			<form:options items="${changegroupList}" itemLabel="groupName" itemValue="id" />
		</form:select>
	</form:form>
</div>
<!-- /page content -->
<script>
var urlPath="${pageContext.request.contextPath}/";
</script>
<%--includes the Front End UI validations--%>
<script src="${pageContext.request.contextPath}/resources/js/pages/physician/physicianAssistantAccountSummary.js?v=3"></script>

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