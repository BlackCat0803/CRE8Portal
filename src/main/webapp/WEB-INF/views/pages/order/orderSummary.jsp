 <%--
 Front End UI to view the Order Summary
 --%>
 
<%@ include file="../../../layout/taglib.jsp"%>

<!-- page content -->
<div class="">
	
	<form:form method="POST" name="form" commandName="form">
		<div class="row">
			<div class="col-md-12 col-sm-12 col-xs-12">
				<div class="x_panel profileBorder">
	                <div class="x_title">
	                  	<h2>Order Summary</h2>
	                  	<div class="clearfix"></div>
	                </div>
	           	
		           	<div class="x_content x_title_padding">
						<div class="row">
							<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 text-left">
								<div class="row form-group">
		                       		<c:choose>
										<c:when test="${loginDetail.type != 'Patient'}">
				                       		<c:choose>
				                       			<c:when test="${loginDetail.type != 'Physician' and loginDetail.type != 'Physician Assistant'}">
				                   					<div class="col-md-2 col-sm-6 col-xs-6 fieldTop">
						                       			<select class="autoCompleterPhysicianId select2_single form-control" style="width:100%;color:black;" name="autoCompleterPhysicianId"  id="autoCompleterPhysicianId"></select>
														<input type="hidden" id="phyname" class="form-control" >
						                       		</div>
					                       		</c:when>
					                       		<c:otherwise>
					                       			<input type="hidden" id="phyname" value="" >
					                       		</c:otherwise>
				                       		</c:choose>
				                       		<div class="col-md-2 col-sm-6 col-xs-6 fieldTop select2-wrapper">
			                       				<select class="autoCompleterPatientId select2-single form-control" style="width:100%;color:black;" name="autoCompleterPatientId"  id="autoCompleterPatientId"></select>
			                       				<input type="hidden" id="patientname" class="form-control">
			                       			</div>
			                       		</c:when>
			                       		<c:otherwise>
			                       			<input type="hidden" id="phyname" value="" >
			                       			<input type="hidden" id="patientname" value="">
			                       		</c:otherwise>
			                       	</c:choose>		                       		

		                       		<div class="col-md-3 col-sm-6 col-xs-6 fieldTop">
										<form:select path="status" class="select2_single form-control" id="status">
											<form:option value="">Select Status</form:option>
											<form:options items="${statusList}" itemLabel="rxtransactionstatustypetext" itemValue="rxtransactionstatustypetext" />
										</form:select>
		                       		</div>
		                       		<div class="col-md-2 col-sm-6 col-xs-6 fieldTop">
										<input type="text" id="orderNo" placeholder="Order #" class="form-control" >
		                       		</div>
		                       		<div class="col-md-2 col-sm-6 col-xs-6 fieldTop">
										<input type="text" id="rxNo" placeholder="Rx #" class="form-control" >
									</div>
		                       	</div>
								<div class="row form-group">
		                       		<div class="col-md-2 col-sm-6 col-xs-6 fieldTop">
										<input type="text" id="fromDate" placeholder="Order From" class="form-control calendarIcon searchDate">
		                       		</div>
		                       		<div class="col-md-2 col-sm-6 col-xs-6 fieldTop">
										<input type="text" id="toDate" placeholder="Order To" class="form-control calendarIcon searchDate">
		                       		</div>
		                       		<div class="col-md-1 col-sm-3 col-xs-3 fieldTop text-center">
		                       			<button type="button" title="Search" id="newSearch" class="btn btn-primary">Search</button>
		                       		</div>
		                       		<div class="col-md-1 col-sm-3 col-xs-3 fieldTop text-center">
		                       			<button type="button" id="clearSearch" title="Clear" class="btn btn-primary">Clear</button>
		                       		</div>
		                     	</div>	                     	
							</div>
						</div>
						
						<div class="row">
							<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 text-left table-responsive">
								<table id="orderSummaryTable" class="table table-striped table-bordered">
									<thead>
										<tr class="heading_background">
											<th>Id</th>
											<th>View</th>
											<th>Order #</th>
											<th>Order Date</th>
											<th>Rx #</th>
											<th>Prescription #</th>
											<th>Physician</th>
											<th>Patient</th>
											<th>Status</th>
										</tr>
									</thead>
								</table>
								<form:hidden path="orderId" />
								<input type="hidden" id="physicianId" value="${physicianId}" />
								<input type="hidden" id="patientId" value="${patientId}" />
								<input type="hidden" id="groupId" value="${groupId}" />
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</form:form>
</div>
<!-- /page content -->
<script>
var urlPath="${pageContext.request.contextPath}/";
</script>
<%--includes the Front End UI validations--%>
<script src="${pageContext.request.contextPath}/resources/js/pages/order/orderSummary.js?v=5"></script>

