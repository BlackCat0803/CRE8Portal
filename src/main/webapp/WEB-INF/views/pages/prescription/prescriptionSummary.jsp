 <%--
 Front End UI to View the Precriptions of the Patient by Physician
 --%>
<%@ include file="../../../layout/taglib.jsp"%>
<style>
<!--
table.dataTable tbody td {
    padding: 4px 10px;
    padding-top: 4px;
    padding-right: 6px;
    padding-bottom: 4px;
    padding-left: 10px;
}
-->
</style>
<!-- page content -->
<div class="">
	<c:if test="${loginDetail.type == 'Super Admin' or loginDetail.type == 'Admin' or (loginDetail.type == 'Physician' and loginDetail.status != 'New') 
				or loginDetail.type == 'Physician Assistant' or loginDetail.type == 'Group Director' }" >
		<div class="row">
			<div class="col-md-12 col-sm-12 col-xs-12 formLink">
				<c:choose>
				<c:when test="${(loginDetail.type == 'Administrator' or loginDetail.type == 'Super Admin' or loginDetail.type == 'Admin')}">
				<c:if test="${(loginDetail.prescriptionCreationPermission =='Yes')}">
					<button type="button" id="newPrescription" class="btn btn-primary" >Create New Prescription</button>
				</c:if>
				</c:when>
				<c:otherwise>
					<button type="button" id="newPrescription" class="btn btn-primary" >Create New Prescription</button>
				</c:otherwise>
				</c:choose>
			</div>
		</div>
	</c:if>

	<div class="row">
		<div class="col-md-12 col-sm-12 col-xs-12">
			<div class="x_panel profileBorder">
                <div class="x_title">
                  	<h2>Prescription Summary</h2>
                  	<div class="clearfix"></div>
                </div>
           	
	           	<div class="x_content x_title_padding">
		           	<form:form method="POST" id="editPage" name="editPage" commandName="form" action="editPrescription"   >
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
		                       		<c:choose>
										<c:when test="${loginDetail.type == 'Group Director' or loginDetail.type == 'Physician' or loginDetail.type == 'Physician Assistant'}">
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
		                       		<c:choose>
										<c:when test="${loginDetail.type != 'Patient'}">
											<c:choose>
												<c:when test="${loginDetail.type == 'Physician' or loginDetail.type == 'Physician Assistant'}">
													<input type="hidden" id="physicianName" value="" >
												</c:when>
												<c:otherwise>
													<div class="col-md-2 col-sm-6 col-xs-6 fieldTop">
						                       			<select class="autoCompleterPhysicianId select2_single form-control" style="width:100%;color:black;" name="autoCompleterPhysicianId"  id="autoCompleterPhysicianId"></select>
														<input type="hidden" id="physicianName" class="form-control" >
						                       		</div>
												</c:otherwise>
											</c:choose>
		                       				<div class="col-md-2 col-sm-6 col-xs-6 fieldTop select2-wrapper">
			                       				<select class="autoCompleterPatientId select2-single form-control" style="width:100%;color:black;" name="autoCompleterPatientId"  id="autoCompleterPatientId"></select>
			                       				<input type="hidden" id="patientName" class="form-control">
			                       			</div>
										</c:when>
										<c:otherwise>
											<input type="hidden" id="patientName" value="">
											<input type="hidden" id="physicianName" value="">
										</c:otherwise>
									</c:choose>
		                       		<div class="col-md-2 col-sm-2 col-xs-6 fieldTop">
										<input type="text" id="prescriptionNo" placeholder="Prescription #" class="form-control" />
									</div>
								
									<div class="col-md-2 col-sm-2 col-xs-6 fieldTop">
										<input type="text" id="rxPrescriptionNo" placeholder="Reference #" class="form-control" >
									</div>
		                       		
		                       		<div class="col-md-2 col-sm-2 col-xs-6 fieldTop">
										<input type="text" id="rxNo" placeholder="Rx #" class="form-control" >
									</div>
		                       	</div>
								
								<div class="row form-group">
									<div class="col-md-2 col-sm-2 col-xs-6">
										<input type="text" id="prescriptionDate" placeholder="Prescription From" class="form-control calendarIcon searchDate" style="width:100%;color:black;">
		                       		</div>
		                       		<div class="col-md-2 col-sm-2 col-xs-6">
										<input type="text" id="prescriptionToDate" placeholder="Prescription To" class="form-control calendarIcon searchDate" style="width:100%;color:black;">
		                       		</div>
		                       		<div class="col-md-1 col-sm-6 col-xs-6  text-center">
										<input type="hidden" id="userType" value="${userType}" />
										<input type="hidden" id="userId" value="${userId}" />
		                       			<button type="button" title="Search" id="newSearch" class="btn btn-primary" >Search</button>
		                       		</div>
		                       		<div class="col-md-1 col-sm-6 col-xs-6  text-center">
		                       			<button type="button" id="clearSearch" title="Clear" class="btn btn-primary" >Clear</button>
		                       		</div>
		                     	</div>
		                     	
							</div>
						</div>
					
						<div class="row">
							<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 text-left table-responsive">
								<table id="summaryTable" class="table table-striped table-bordered">
									<thead>
										<tr class="heading_background">
											<th>Id</th>
											<th>Edit</th>
											<th></th>
											<th></th>
											<th>Prescription #</th>
											<th>Reference #</th>
											<th>Rx #</th>
											<th>Prescription Date</th>
											<th>Group</th>
											<th>Physician</th>
											<th>Patient</th>
											<th>ESigned PDF</th>
											<th>CS ESigned PDF</th>
										</tr>
									</thead>
								</table>
							</div>
						</div>
						<form:hidden path="prescriptionId" />
						<input type="hidden" id="checkUserType" value="${loginDetail.type}" />
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
<script src="${pageContext.request.contextPath}/resources/js/pages/prescription/summary.js?v=13"></script>
