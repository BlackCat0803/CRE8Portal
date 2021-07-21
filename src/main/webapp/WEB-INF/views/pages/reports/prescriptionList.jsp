 <%--
 Front End UI to generates the Prescription List Report
 --%>
<%@ include file="../../../layout/taglib.jsp"%>

<!-- page content -->
<div class="">
	<div class="row">
		<div class="col-md-12 col-sm-12 col-xs-12">
			<div class="x_panel profileBorder">
                <div class="x_title">
                  	<h2>Prescription List Report</h2>
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
								<c:when test="${loginDetail.type == 'Patient'}">
									<form:hidden path="patient" />
								</c:when>
								<c:otherwise>
									<div class="col-md-2 col-sm-6 col-xs-6">
										<select name="autoCompleterPatientId" id="autoCompleterPatientId" style="width:100%;color:black;" 
												class="autoCompleterPatientId select2_single form-control" ></select>
										<form:hidden path="patient" />
									</div>
								</c:otherwise>
							</c:choose>
							
							<div class="col-md-2 col-sm-2 col-xs-6 fieldTop">
								<input type="text" id="prescriptionNo" placeholder="Prescription #" class="form-control" />
							</div>
						
							<div class="col-md-2 col-sm-2 col-xs-6 fieldTop">
								<input type="text" id="rxPrescriptionNo" placeholder="Reference #" class="form-control" >
							</div>

							<div class="col-md-2 col-sm-6 col-xs-6 fieldTop">
								<form:input path="rxNumber" placeholder="Rx #" class="form-control"  />
								<form:hidden path="physician" />
							</div>
						</div>
						<div class="row form-group">
							<div class="col-md-2 col-sm-6 col-xs-6">
								<form:input path="fromRegDate" placeholder="Written From" class="form-control calendarIcon"  />
							</div>
							<div class="col-md-2 col-sm-6 col-xs-6">
								<form:input path="toRegDate" placeholder="Written To" class="form-control calendarIcon"  />
							</div>
							<div class="col-md-1 col-sm-3 col-xs-3 text-center">
								<input type="hidden" id="checkUserType" value="${loginDetail.type}" />
								<form:hidden path="group"  />
								<form:hidden path="status"  />
								<button type="button" id="newSearch" title="Search" class="btn btn-primary">Search</button>
							</div>
	                     	<div class="col-md-1 col-sm-3 col-xs-3 text-center">
	                     		<button type="button" id="clearSearch" title="Clear" class="btn btn-primary">Clear</button>
							</div>
	                     	<div class="col-md-2 col-sm-3 col-xs-3 text-center">
	                     		<button type="button" id="pdfReport" title="PDF Download" class="btn btn-primary">PDF Download</button>
							</div>
	                     	<div class="col-md-2 col-sm-3 col-xs-3 text-center">
	                     		<button type="button" id="xlsReport" title="Excel Download" class="btn btn-primary">Excel Download</button>
							</div>
						</div>
					</form:form>
					
					<div class="row">
						<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 text-left table-responsive">
							<table id="prescriptionListReport" class="table table-striped table-bordered">
								<thead>
									<tr class="heading_background">
										<th>Id</th>
										<th>Patient</th>
										<th>Prescription #</th>
										<th>Reference #</th>
										<th>Rx #</th>
										<th>Written Date</th>
										<th>Expiry Date</th>
										<th>Physician</th>
										<th>Prescribed Item</th>
										<th>Dispensed Items</th>
										<th>Origin</th>
										<th>Quantity</th>
										<th>Days Supply</th>
										<th>Refills</th>
										<th>Refills Remaining</th>
										<th>Refills Filled</th>
										<th>Rx Status</th>
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
<script src="${pageContext.request.contextPath}/resources/js/pages/reports/prescriptionlist.js?v=2"></script>

