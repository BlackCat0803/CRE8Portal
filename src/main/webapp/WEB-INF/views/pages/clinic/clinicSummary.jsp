<%--
Front End UI to view the Clinic Account summary
 --%>
<%@ include file="../../../layout/taglib.jsp"%>

<!-- page content -->
<div class="">
	<c:if test="${loginDetail.type == 'Super Admin' or loginDetail.type == 'Admin' or (loginDetail.type == 'Physician' and loginDetail.status != 'New') 
			or loginDetail.type == 'Physician Assistant' or loginDetail.type == 'Group Director' }" >
		<div class="row">
			<div class="col-md-12 col-sm-12 col-xs-12 formLink">
				
				<c:choose>
			      <c:when test="${(loginDetail.type == 'Administrator' or loginDetail.type == 'Super Admin' or loginDetail.type == 'Admin')}">
				      	<c:if test="${(loginDetail.clinicCreationPermission =='Yes')}">
				      		<button type="button" id="newClinicAcc" class="btn btn-primary" >Create New Clinic</button>
				      	</c:if>
			      </c:when>
			      <c:otherwise>
			      			<button type="button" id="newClinicAcc" class="btn btn-primary" >Create New Clinic</button>
			      </c:otherwise>
			    </c:choose>
			</div>
		</div>
	</c:if>

	<div class="row">
		<div class="col-md-12 col-sm-12 col-xs-12">
			<div class="x_panel profileBorder">
                <div class="x_title">
                  	<h2>Clinic Summary</h2>
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
					
					<form:form method="POST" name="search" commandName="form" >
						<div class="row">
							<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 text-left">
								<div class="row form-group">
		                       		<div class="col-md-2 col-sm-6 col-xs-6 fieldTop">
										<input type="text" id="clinicName" placeholder="Clinic" class="form-control" onkeypress="return blockedSearchText(event)" >
		                       		</div>
		                       		<div class="col-md-2 col-sm-6 col-xs-6 fieldTop">
										<form:select path="state" class="select2_single form-control" id="state" >
											<form:option value="">Select State</form:option>
											<form:options items="${statesList}" itemLabel="stateName" itemValue="stateCode"/>
										</form:select>
		                       		</div>
		                       		<div class="col-md-2 col-sm-6 col-xs-6 fieldTop">
										<c:choose>
											<c:when test="${loginDetail.type == 'Physician' or loginDetail.type == 'Physician Assistant' or loginDetail.type == 'Group Director'}">
												<form:select path="groupId" class="select2_single form-control" id="groupName" style="pointer-events: none;">
													<form:option value="0">Select Group</form:option>
													<form:options items="${groupList}" itemLabel="groupName" itemValue="id" />
												</form:select>
											</c:when>
											<c:otherwise>
												<form:select path="groupId" class="select2_single form-control" id="groupName">
													<form:option value="0">Select Group</form:option>
													<form:options items="${groupList}" itemLabel="groupName" itemValue="id" />
												</form:select>
											</c:otherwise>
										</c:choose>
										
	                       			</div>
		                       		<div class="col-md-2 col-sm-6 col-xs-6 fieldTop">
										<select id="status" class="form-control">
											<option value="">Select Status</option>
											<option value="New" >New</option>
											<option value="Info Completed" >Info Completed</option>
											<option value="Active">Active</option>
											<option value="Inactive">Inactive</option>
										</select>
		                       		</div>
		                       		<div class="col-md-1 col-sm-6 col-xs-6 text-center">
		                       			<input type="hidden" id="email" >
		                       			<button type="button" title="Search" id="newSearch" class="btn btn-primary" >Search</button>
		                       		</div>
		                       		<div class="col-md-1 col-sm-6 col-xs-6 text-center">
		                       			<button type="button" id="clearSearch" title="Clear" class="btn btn-primary" >Clear</button>
		                       			<input type="hidden" id="clinicCreationPermission" name="clinicCreationPermission" value="${loginDetail.clinicCreationPermission}"/>
		                       		</div>
		                     	</div>
							</div>
						</div>
					</form:form>
					
					
					<div class="row">
						<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 text-left table-responsive">
							
							<table id="clinicSummaryTable" class="table table-striped table-bordered">
								<thead>
					      		<tr class="heading_background">
									<th>Id</th>
									<th>Edit</th>
									<!-- <th>Delete</th> -->
									<th>Clinic</th>
									<th>Email</th>
									<th>City</th>
									<th>State</th>
									<th>Phone</th>
									<th>Group</th>
									<th>Status</th>
								</tr>
								</thead>
							</table>
				
						</div>
					</div>
					<form:form method="POST" name="editPage" commandName="form" action="editClinicAccount"   >
						<form:hidden path="clinicId" />
					</form:form>
				</div>
			</div>
		</div>
	</div>
</div>
<!-- /page content -->
<script type="text/javascript">

var userType="${loginDetail.type}";
</script>
<%--includes the Front End UI validations--%>
<script src="${pageContext.request.contextPath}/resources/js/pages/clinic/clinicSummary.js?v=3"></script>
