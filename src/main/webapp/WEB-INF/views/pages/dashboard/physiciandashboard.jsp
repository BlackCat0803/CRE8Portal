<%--
Front End UI to view the Physician Dashboard
 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles-extras" prefix="tilesx" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix = "fmt" %>
<style type="text/css">
<!--
.tile-stats .count_green {
    font-size: 18px;
    font-weight: bold;
    line-height: 1.65857;
    color:#196F3D;
}
.tile-stats .count_blue {
    font-size: 18px;
    font-weight: bold;
    line-height: 1.65857;
    color:#3498DB;
}
.tile-stats .count_maroon {
    font-size: 18px;
    font-weight: bold;
    line-height: 1.65857;
    color:#7B241C;
}
.shadow {
 /*  -moz-box-shadow:    inset 0 0 10px #000000;
  -webkit-box-shadow: inset 0 0 10px #000000;
   box-shadow:         inset 0 0 10px #000000; */
   background-color:#EAECEE;
   padding:7px;
   min-width: 40px;
   text-align:center;
   

}
-->
</style>
<!-- page content -->
<form:form method="POST" name="form" commandName="form">
	<div class="">
		<div class="row top_tiles">
			<div class="row">
					<div class="col-md-6 col-sm-6 col-xs-12">
						<div class="animated flipInY col-lg-12 col-md-12 col-sm-6 col-xs-12">
							<div class="tile-stats">
							<table align="center">
									<tr>
									<td align="center"><br><br><br>
										<h1 style="font-weight:bold;color:#BAB8B8">CRE8 Notifications</h1><br><br><br>
									</td>
									</tr>
							</table>
							
								<%-- <div class="icon">
									<i class="fa fa-users" style="color: #50C1CF"></i>
								</div>
								<table>
									<tr>
										<td><div class="count_green">Today</div></td>
										<td class="shadow"><div class="count_green">${newPatientsCount_today}</div></td>
									</tr>
										<tr><td style="padding:1px"></td></tr>
										<tr><td><div class="count_blue">This Week</div></td>
											<td class="shadow"><div class="count_blue">${newPatientsCount_week}</div></td>
									</tr>
									<tr><td style="padding:1px"></td></tr>
									<tr><td><div class="count_maroon">This Month</div></td>
										<td class="shadow"><div class="count_maroon">${newPatientsCount_month}</div></td>
									</tr>
								</table>
								<h3 style="font-weight:bold">New Patients</h3>
								<p></p> --%>
							</div>
						</div>
					
					</div>
					
					<div class="col-md-6 col-sm-6 col-xs-12">
							
						<div class="animated flipInY col-lg-4 col-md-4 col-sm-4 col-xs-12">
							<div class="tile-stats">
								<!-- <div class="icon">
									<i class="fa fa-newspaper-o" style="color: #2ECC71"></i>
								</div> -->
								<table>
									<tr>
										<td><div class="count_green">Today</div></td>
										<td class="shadow"><div class="count_green" >${newPrescriptionsCount_today}</div></td>
									</tr>
									<tr><td style="padding:1px"></td></tr>
									<tr>
										<td><div class="count_blue">This Week</div></td>
										<td class="shadow"><div class="count_blue" >${newPrescriptionsCount_week}</div></td>
									</tr>
									<tr><td style="padding:1px"></td></tr>
									<tr>
										<td><div class="count_maroon">This Month</div></td>
										<td class="shadow"><div class="count_maroon" >${newPrescriptionsCount_month}</div></td>
									</tr>
								</table>
								<h4 style="font-weight:bold;color:#BAB8B8">New Prescriptions</h4>
								<p></p>
							</div>
						</div>
						<div class="animated flipInY col-lg-4 col-md-4 col-sm-4 col-xs-12">
							<div class="tile-stats">
								<!-- <div class="icon">
									<i class="fa fa-medkit" style="color: violet"></i>
								</div> -->
								<table>
								<tr>
									<td><div class="count_green">Today</div>
									</td>
									
									<td class="shadow"><div class="count_green" >${noOfRefillsFilled_today}</div>
									</td>
									
								</tr>
								<tr><td style="padding:1px"></td></tr>
								<tr>
									<td><div class="count_blue">This Week</div>
									</td>
									
									<td class="shadow"><div class="count_blue" >${noOfRefillsFilled_week}</div>
									</td>
									
								</tr>
								<tr><td style="padding:1px"></td></tr>
								<tr>
									<td><div class="count_maroon">This Month</div>
									</td>
									
									<td class="shadow"><div class="count_maroon" >${noOfRefillsFilled_month}</div>
									</td>
									
								</tr>
								</table>
								<h4 style="font-weight:bold;color:#BAB8B8">Total Refills</h4>
								<p></p>
							</div>
						</div>
						<div class="animated flipInY col-lg-4 col-md-4 col-sm-4 col-xs-12">
							<div class="tile-stats">
								<!-- <div class="icon">
									<i class="fa fa-usd" style="color: #F5B041"></i>
								</div> -->
								<table>
								<tr>
									<td><div class="count_green">Today</div>
									</td>
									
									<td class="shadow"><div class="count_green" >$<fmt:formatNumber type = "currency" pattern = "##########0.00" 
				         										maxFractionDigits ="2"  value = "${totalInvoicePaid_today}"  /></div>
									</td>
									
								</tr>
								<tr><td style="padding:1px"></td></tr>
								<tr>
									<td><div class="count_blue">This Week</div>
									</td>
									
									<td class="shadow"><div class="count_blue" >$<fmt:formatNumber type = "currency" pattern = "##########0.00" 
				         										maxFractionDigits ="2"  value = "${totalInvoicePaid_week}"  /></div>
									</td>
									
								</tr>
								<tr><td style="padding:1px"></td></tr>
								<tr>
									<td><div class="count_maroon">This Month</div>
									</td>
									
									<td class="shadow"><div class="count_maroon" >$<fmt:formatNumber type = "currency" pattern = "##########0.00" 
				         										maxFractionDigits ="2"  value = "${totalInvoicePaid_month}"  /></div>
									</td>
									
								</tr>
								</table>
								<h4 style="font-weight:bold;color:#BAB8B8">Total Invoice Paid</h4>
								<p></p>
							</div>
						</div>
								
					</div>
			</div>
			
			
			
		</div>
		<!-- <div class="clearfix"></div> -->

		<div class="row">
			<div class="col-md-6 col-sm-6 col-xs-12">
				<div class="row">
					<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 text-left dashboardBox">
						<!-- Patient panel -->
						<div class="x_panel bgcolorblue2 dashboardHeight">
							<div class="x_title">
								<h2>Patient</h2>
								<ul class="nav navbar-right panel_toolbox">
									<li><a class="collapse-link"><i class="fa fa-minus"></i></a></li>
									<li><a class="close-link"><i class="fa fa-close"></i></a></li>
								</ul>
								<div class="clearfix"></div>
							</div>
							<div class="x_content bgcolorblue1">
								<div class="row">
									<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 text-left">
										<div class="row form-group">
											<div class="col-md-2 col-sm-2 col-xs-12">
											
											</div>
											<div class="col-md-1 col-sm-1 col-xs-12">
												<!-- <button type="button" class="btn btn-primary">Create New Patient</button> -->
												<img  style="cursor: pointer" class="img-responsive image-center" id="newPatientAcc" src="${pageContext.request.contextPath}/resources/images/add.png"  title="New Patient">
											</div>
											<div class="col-md-5 col-sm-5 col-xs-12">
												<div class="select2-wrapper">
													<select class="autoCompleterPatientId select2-single form-control" name="autoCompleterPatientId" id="autoCompleterPatientId"></select>
													<input type="hidden" id="patientname" class="form-control">
													<input type="hidden" id="phyname" class="form-control">
												</div>
											</div>
											<div class="col-md-1 col-sm-1 col-xs-12 text-center">
												<input type="hidden" id="checkUserType" value="${loginDetail.type}" />
												<!-- <button type="button" title="Search" id="newSearch" class="btn btn-primary">Search</button> -->
												<img  style="cursor: pointer" class="img-responsive image-center" id="newSearch" src="${pageContext.request.contextPath}/resources/images/search3.png"  title="Search">
											</div>
											<div class="col-md-1 col-sm-1 col-xs-12 text-center">
												<!-- <button type="button" id="clearSearch" title="Clear" class="btn btn-primary">Clear</button> -->
												<img  style="cursor: pointer" class="img-responsive image-center" id="clearSearch" src="${pageContext.request.contextPath}/resources/images/search-cancel2.png"  title="Clear">
												
												<c:choose>
													<c:when test="${loginDetail.type == 'Group Director'}">
														<input type="hidden" id="groupId" value="${groupId}" />
													</c:when>
													<c:otherwise>
														<form:hidden path="groupId" id="groupId" />
													</c:otherwise>
												</c:choose>
												<input type="hidden" id="status" />
												
											</div>
										</div>
										
									</div>
								</div>
								
								<div class="table-responsive">
									<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 text-left table-responsive">
										<table id="patientAccountSummaryTable" class="table table-striped jambo_table bulk_action">
											<thead>
												<tr class="heading_background">
													<th>Id</th>
													<th></th>
													<th>Patient</th>
													<th>Physician</th>
													<th>Group</th>
													<th>City</th>
													<th>Email</th>
													<th>Mobile</th>
													<th>Status</th>
													<th>Address</th>
												</tr>
											</thead>
										</table>
									</div>
								</div>
							</div>
						</div>
					</div>

					<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 text-left dashboardBox">
						<!-- Order panel -->
						<div class="x_panel bgcolorblue2 dashboardHeight">
							<div class="x_title">
								<h2>Order</h2>
								<ul class="nav navbar-right panel_toolbox">
									<li><a class="collapse-link"><i class="fa fa-minus"></i></a></li>
									<li><a class="close-link"><i class="fa fa-close"></i></a></li>
								</ul>
								<div class="clearfix"></div>
							</div>
							<div class="x_content bgcolorblue1">
							
								<div class="row form-group">	
									
									<div class="col-md-4 col-sm-4 col-xs-12">
										<div class="select2-wrapper">
											<select class="autoCompleterOrderPatientId select2-single form-control" name="autoCompleterOrderPatientId" id="autoCompleterOrderPatientId"></select>
											<input type="hidden" id="orderpatientName" class="form-control">
										</div>
									</div>
									<div class="col-md-3 col-sm-3 col-xs-12">
										<input type="text" id="orderNo" class="form-control" placeholder="Order #">
									</div>
									<div class="col-md-3 col-sm-3 col-xs-12">
										<form:select path="orderstatus" class="select2_single form-control" id="orderstatus">
											<form:option value="">Status-All</form:option>
											<form:options items="${rxTranStatusList}" itemLabel="rxtransactionstatustypetext" itemValue="rxtransactionstatustypetext" />
										</form:select>
									</div>
								
									<div class="col-md-1 col-sm-1 col-xs-12 text-center">
										<!-- <button type="button" title="Search" id="newOrderSearch" class="btn btn-primary">Search</button> -->
										<img  style="cursor: pointer" class="img-responsive image-center" id="newOrderSearch" src="${pageContext.request.contextPath}/resources/images/search3.png"   title="Search">
									</div>
									<div class="col-md-1 col-sm-1 col-xs-12 text-center">
										<!-- <button type="button" id="clearOrderSearch" title="Clear" class="btn btn-primary">Clear</button> -->
										<img  style="cursor: pointer" class="img-responsive image-center" id="clearOrderSearch" src="${pageContext.request.contextPath}/resources/images/search-cancel2.png"   title="Clear">
									</div>
								</div>
								<p></p>
								<div class="table-responsive">
									<div class="col-md-12 col-sm-12 col-xs-12 text-left table-responsive">
										<table id="orderSummaryTable" class="table table-striped jambo_table bulk_action">
											<thead>
												<tr class="heading_background text-center">
													<th>Id</th>
													<th></th>
													<th>Order #</th>
													<th>Order Date</th>
													<th>Rx #</th>
													<th>Prescription #</th>
													<th>Patient</th>
													<th>Physician</th>
													<th>Order Status</th>
													<th>Medication Dispensed</th>
													<th>Refills Filled</th>
													<th>Days Supply</th>
													<th>Priority Type</th>
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
			
			<div class="col-md-6 col-sm-6 col-xs-12">
				<div class="row">
					<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 text-left dashboardBox">
						<!-- Prescription panel -->
						<div class="x_panel bgcolorblue2 dashboardHeight">
							<div class="x_title">
								<h2>Prescription</h2>
								<ul class="nav navbar-right panel_toolbox">
									<li><a class="collapse-link"><i class="fa fa-minus"></i></a></li>
									<li><a class="close-link"><i class="fa fa-close"></i></a></li>
								</ul>
								<div class="clearfix"></div>
							</div>
							<div class="x_content bgcolorblue1">
								<div class="row form-group">
									<div class="col-md-1 col-sm-1 col-xs-12">
										<!-- <button type="button" id="newPrescription" class="btn btn-primary">Create New Prescription</button> -->
										<img  style="cursor: pointer" class="img-responsive image-center" id="newPrescription" src="${pageContext.request.contextPath}/resources/images/add.png"   title="New Prescription">
									</div>
									<div class="col-md-3 col-sm-3 col-xs-12">
										<div class="select2-wrapper">
											<select class="autoCompleterPrescriptionPatientId select2-single form-control" name="autoCompleterPrescriptionPatientId" id="autoCompleterPrescriptionPatientId"></select>
										</div>
										<input type="hidden" id="prescriptionPatientName" class="form-control">
									</div>
									<div class="col-md-3 col-sm-3 col-xs-12">
										<input type="text" id="prescriptionNo" class="form-control" placeholder="Prescription #" onkeypress="return isNumber(event)" >
									</div>
									<div class="col-md-3 col-sm-3 col-xs-12">
										<form:select path="prescriptionstatus" class="select2_single form-control" id="prescriptionstatus">
											<form:option value="">Status-All</form:option>
											<form:options items="${rxStatusList}" itemLabel="rxstatustypetext" itemValue="rxstatustypetext" />
										</form:select>
									</div>
									<div class="col-md-1 col-sm-1 col-xs-12 text-center">
										<!-- <button type="button" title="Search" id="newPrescriptionSearch" class="btn btn-primary">Search</button> -->
										<img  style="cursor: pointer" class="img-responsive image-center" id="newPrescriptionSearch" src="${pageContext.request.contextPath}/resources/images/search3.png"   title="Search">
									</div>
									<div class="col-md-1 col-sm-1 col-xs-12 text-center">
										<!-- <button type="button" id="clearPrescriptionSearch" title="Clear" class="btn btn-primary">Clear</button> -->
										<img  style="cursor: pointer" class="img-responsive image-center" id="clearPrescriptionSearch" src="${pageContext.request.contextPath}/resources/images/search-cancel2.png"   title="Clear">
									</div>
								</div>
								<p></p>
								<div class="table-responsive">
									<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 text-left table-responsive">
										<table id="prescriptionSummaryTable" class="table table-striped jambo_table bulk_action">
											<thead>
												<tr class="heading_background text-center">
													<th>Id</th>
													<th></th>
													<th>Prescription #</th>
													<th>Rx #</th>
													<th>Physician</th>
													<th>Patient</th>
													<th>Item</th>
													<th>Status</th>
													<th>Qty</th>
													<th>Total Refills</th>
													<th>Refills Filled</th>
												</tr>
											</thead>
										</table>
									</div>
								</div>
							</div>
						</div>
					</div>

					<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 text-left dashboardBox">
						<div class="x_panel bgcolorblue2 dashboardHeight">
							<div class="x_title">
								<h2>Invoice</h2>
								<ul class="nav navbar-right panel_toolbox">
									<li><a class="collapse-link"><i class="fa fa-minus"></i></a></li>
									<li><a class="close-link"><i class="fa fa-close"></i></a></li>
								</ul>
								<div class="clearfix"></div>
							</div>
							<div class="x_content bgcolorblue1">
							
								<div class="row form-group">
									<div class="col-md-1 col-sm-1 col-xs-12">
									</div>
									<div class="col-md-4 col-sm-4 col-xs-12">
										<div class="select2-wrapper">
											<select class="autoCompleterInvoicePatientId select2-single form-control" name="autoCompleterInvoicePatientId" id="autoCompleterInvoicePatientId"></select>
										</div>
										<input type="hidden" id="invoicepatientname" class="form-control">
									</div>
									<div class="col-md-3 col-sm-3 col-xs-12">
										<input type="text" id="invoiceNo" class="form-control" placeholder="Invoice #">
									</div>
								
									<div class="col-md-1 col-sm-1 col-xs-12 ">
										<!-- <button type="button" title="Search" id="newInvoiceSearch" class="btn btn-primary">Search</button> -->
										<img  style="cursor: pointer" class="img-responsive image-center" id="newInvoiceSearch" src="${pageContext.request.contextPath}/resources/images/search3.png"   title="Search">
									</div>
									<div class="col-md-1 col-sm-1 col-xs-12 ">
										<!-- <button type="button" id="clearInvoiceSearch" title="Clear" class="btn btn-primary">Clear</button> -->
										<img  style="cursor: pointer" class="img-responsive image-center" id="clearInvoiceSearch" src="${pageContext.request.contextPath}/resources/images/search-cancel2.png"   title="Clear">
									</div>
								</div>
								<p></p>
								<div class="table-responsive">
									<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 text-left table-responsive">
										<table id="invoiceSummaryTable" class="table table-striped jambo_table bulk_action">
											<thead>
												<tr class="heading_background text-center">
													<th>Id</th>
													<th></th>
													<th>Invoice #</th>
													<th>Invoice Date</th>
													<th>Rx #</th>
													<th>Patient</th>
													<th>Physician</th>
													<th>Total</th>
													<th>Invoice Status</th>
													<th>Item Invoiced</th>
													<th>Refills Filled</th>
													<th>Quantity</th>
													<th>Lot No</th>
													<th>Lot Exp Date</th>
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
		</div>
	</div>

	<input type="hidden" id="userType" value="${userType}" />
	<input type="hidden" id="userId" value="${userId}" />
	<form:hidden path="patientId" id="patientId" />
	<form:hidden path="prescriptionId" id="prescriptionId" />
	<form:hidden path="orderId" id="orderId" />
	<form:hidden path="invoiceId" id="invoiceId" />
	
	<c:choose>
		<c:when test="${(loginDetail.type == 'Physician' or loginDetail.type == 'Physician Assistant')}">
			<form:hidden path="physicianId" id="physicianId"  value="${userId}"/>
		</c:when>
		<c:otherwise>
				<form:hidden path="physicianId" id="physicianId"/>
		</c:otherwise>
	</c:choose>
	
</form:form>
<!-- /page content -->
<script>
var urlPath="${pageContext.request.contextPath}/";
</script>
<%--includes the Front End UI validations--%>
<script src="${pageContext.request.contextPath}/resources/js/pages/dashboard/physiciandashboard.js?v=3"></script>