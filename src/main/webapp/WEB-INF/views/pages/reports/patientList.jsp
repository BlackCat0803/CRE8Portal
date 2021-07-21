 <%--
 Front End UI to generates the Patient List Report
 --%>
<%@ include file="../../../layout/taglib.jsp"%>

<!-- page content -->
<div class="">
	<div class="row">
		<div class="col-md-12 col-sm-12 col-xs-12">
			<div class="x_panel profileBorder">
                <div class="x_title">
                  	<h2>Patient List Report</h2>
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
					
					<form:form method="POST" name="form" commandName="form" >
						<div class="row form-group">
							<c:choose>
								<c:when test="${loginDetail.type == 'Physician' or loginDetail.type == 'Physician Assistant'}">
									<form:hidden path="physician" />
								</c:when>
								<c:otherwise>
	                       			<div class="col-md-2 col-sm-6 col-xs-6 fieldTop">
		                       			<select name="autoCompleterPhysicianId" id="autoCompleterPhysicianId" style="width:100%;color:black;"
		                       					class="autoCompleterPhysicianId select2_single form-control" > </select>
										<form:hidden path="physician" />
									</div>
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${loginDetail.type == 'Physician' or loginDetail.type == 'Physician Assistant' or loginDetail.type == 'Group Director'}">
									<form:hidden path="group"  />
								</c:when>
								<c:otherwise>
									<div class="col-md-2 col-sm-6 col-xs-6 fieldTop">
										<form:select path="group" class="select2_single form-control">
											<form:option value="">Select Group</form:option>
											<form:options items="${groupList}" itemLabel="groupName" itemValue="groupName" />
										</form:select>
									</div>
								</c:otherwise>
							</c:choose>
							<div class="col-md-2 col-sm-6 col-xs-6 fieldTop">
								<form:select path="status" class="select2_single form-control">
									<form:option value="">Select Status</form:option>
									<form:option value="New">New</form:option>
									<form:option value="Profile Completed">Profile Completed</form:option>
									<form:option value="New Modifications">New Modifications</form:option>
									<form:option value="Approved">Approved</form:option>
									<form:option value="Denied">Denied</form:option>
								</form:select>
	                      	</div>							
							<div class="col-md-2 col-sm-6 col-xs-6 fieldTop">
								<form:input path="fromRegDate" placeholder="Registration From" class="form-control calendarIcon"  />
							</div>
							<div class="col-md-2 col-sm-6 col-xs-6 fieldTop">
								<form:input path="toRegDate" placeholder="Registration To" class="form-control calendarIcon"  />
							</div>
						</div>
						<div class="row form-group">
							<div class="col-md-1 col-sm-6 col-xs-6 text-center fieldTop">
								<input type="hidden" id="checkUserType" value="${loginDetail.type}" />
								<button type="button" id="newSearch" title="Search" class="btn btn-primary">Search</button>
							</div>
	                     	<div class="col-md-1 col-sm-6 col-xs-6 text-center fieldTop">
	                     		<button type="button" id="clearSearch" title="Clear" class="btn btn-primary">Clear</button>
							</div>
	                     	<div class="col-md-2 col-sm-6 col-xs-6 text-center fieldTop">
	                     		<button type="button" id="pdfReport" title="PDF Download" class="btn btn-primary">PDF Download</button>
							</div>
	                     	<div class="col-md-2 col-sm-6 col-xs-6 text-center fieldTop">
	                     		<button type="button" id="xlsReport" title="Excel Download" class="btn btn-primary">Excel Download</button>
							</div>
						</div>
					</form:form>
					
					<div class="row">
						<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 text-left table-responsive">
							<table id="patientListReport" class="table table-striped table-bordered">
								<thead>
									<tr class="heading_background">
										<th>Id</th>
										<th>Patient</th>
										<th>Registered Date</th>
										<th>Group</th>
										<th>Physician</th>
										<th>Phone</th>
										<th>Email</th>
										<th>Address</th>
										<th>SSN</th>
										<th>Drivers License</th>
										<th>Alternate ID</th>
										<th>Alternate ID Type</th>
										<th>Status</th>
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
<!-- /page content -->
<script>
var urlPath="${pageContext.request.contextPath}/";
</script>
<%--includes the Front End UI validations--%>
<script src="${pageContext.request.contextPath}/resources/js/pages/reports/patientlist.js?v=4"></script>

