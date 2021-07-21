 <%--
 Front End UI to view the Patient Account Summary
 --%>
<%@ include file="../../../layout/taglib.jsp"%>

<!-- page content -->
<div class="">
	<c:if test="${loginDetail.type != 'Physician' or (loginDetail.type == 'Physician' and loginDetail.status != 'New') }">
		<div class="row">
			<div class="col-md-12 col-sm-12 col-xs-12 formLink">
				<c:choose>
				<c:when test="${(loginDetail.type == 'Administrator' or loginDetail.type == 'Super Admin' or loginDetail.type == 'Admin')}">
				<c:if test="${(loginDetail.patientCreationPermission =='Yes')}">
					<button type="button" id="newPatientAcc" class="btn btn-primary" >Create New Patient</button>
				</c:if>
				</c:when>
				<c:otherwise>
					<button type="button" id="newPatientAcc" class="btn btn-primary" >Create New Patient</button>
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
	                  	<h2>Patient Account Summary</h2>
	                  	<div class="clearfix"></div>
	                </div>
	           	
		           	<div class="x_content x_title_padding">
						<c:if test="${not empty message}">
							<div class="row">
								<div class="col-sm-offset-1 col-sm-10 fontSize14 text-center">
									<div class="alert alert-success fade in">
										<a href="#" class="close" data-dismiss="alert">&times;</a>  &nbsp;&nbsp;&nbsp; ${message}
									</div>
								</div>
							</div>
						</c:if>
						
						<div class="row">
							<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 text-left">
								<div class="row form-group">
		                       		<div class="col-md-2 col-sm-6 col-xs-6 fieldTop">
		                       			<div class="select2-wrapper">
		                       				<select class="autoCompleterPatientId select2-single form-control" style="width:100%;color:black;" name="autoCompleterPatientId"  id="autoCompleterPatientId"></select>
		                       			</div>
										<input type="hidden" id="patientname" class="form-control">
		                       		</div>
		                       		<c:choose>
										<c:when test="${loginDetail.type == 'Physician' or loginDetail.type == 'Physician Assistant'}">
											<input type="hidden" id="phyname" class="form-control" >
										</c:when>
										<c:otherwise>
				                       		<div class="col-md-2 col-sm-6 col-xs-6 fieldTop">
				                       			<select class="autoCompleterPhysicianId select2_single form-control" style="width:100%;color:black;" name="autoCompleterPhysicianId"  id="autoCompleterPhysicianId"></select>
												<input type="hidden" id="phyname" class="form-control" >
				                       		</div>
										</c:otherwise>
									</c:choose>
		                       		
									<c:choose>
										<c:when test="${loginDetail.type == 'Physician' or loginDetail.type == 'Physician Assistant' or loginDetail.type == 'Group Director'}">
											<form:hidden path="groupId"  />
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
											<option value="New">New</option>
											<option value="Profile Completed">Profile Completed</option>											
											<option value="New Modifications">New Modifications</option>
											<option value="Approved">Approved</option>
											<option value="Denied">Denied</option>
										</select>
		                       		</div>
		                       		<div class="col-md-1 col-sm-6 col-xs-6 fieldTop text-center">
		                       			<input type="hidden" id="checkUserType" value="${loginDetail.type}" />
		                       			<button type="button" title="Search" id="newSearch" class="btn btn-primary">Search</button>
		                       		</div>
		                       		<div class="col-md-1 col-sm-6 col-xs-6 fieldTop text-center">
		                       			<button type="button" id="clearSearch" title="Clear" class="btn btn-primary">Clear</button>
		                       		</div>
		                     	</div>
							</div>
						</div>
						
						<div class="row">
							<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 text-left table-responsive">
								<table id="patientAccountSummaryTable" class="table table-striped table-bordered">
									<thead>
										<tr class="heading_background">
											<th>Id</th>
											<th>Edit</th>
											<th>Patient</th>
											<th>Physician</th>
											<th>Change Physician</th>
											<th>Group</th>
											<th>City</th>
											<th>Email</th>
											<th>Mobile</th>
											<th>Status</th>
											<th>Approve / Deny</th>
										</tr>
									</thead>
								</table>
								<input type="hidden" id="physicianId" value="${physicianId}" />
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<form:select path="groupPhysicianId" style="display:none;">
			<form:option value="0">Select</form:option>
			<form:options items="${physicianList}" itemLabel="physicianName" itemValue="id" />
			<%-- <form:options items="${physicianList}" itemLabel="physicianNameWithGroupName" itemValue="id" /> --%>
		</form:select>
	</form:form>
	
	<form:form method="POST" name="editPage" commandName="form" action="editPatientAccount"   >
		<form:hidden path="patientId" />
	</form:form>	
</div>
<!-- /page content -->
<script>
var urlPath="${pageContext.request.contextPath}/";
</script>
<%--includes the Front End UI validations--%>
<script src="${pageContext.request.contextPath}/resources/js/pages/patient/patientAccountSummary.js?v=5"></script>

<c:choose>
	<c:when test="${(loginDetail.type == 'Administrator' or loginDetail.type == 'Super Admin' or loginDetail.type == 'Admin')}">
	<c:if test="${(loginDetail.patientApprovalPermission !='Yes')}">
	<script>
		fnShowHide(10);	
	</script>
	</c:if>
	</c:when>
</c:choose>
<c:choose>
	<c:when test="${(loginDetail.type == 'Physician')}">
	<script>
		fnShowHide(3);
		
	</script>
	</c:when>
</c:choose>