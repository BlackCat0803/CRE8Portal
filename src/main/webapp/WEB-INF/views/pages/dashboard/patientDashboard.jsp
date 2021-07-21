<%--
Front End UI to view the Patient Dashboard
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
.row.no-gutter {
  margin-left: 0;
  margin-right: 0;
}
.row.no-gutter [class*='col-']:not(:first-child),
.row.no-gutter [class*='col-']:not(:last-child) {
  padding-right: 0;
  padding-left: 0;
}
-->
</style>
<!-- page content -->
<form:form method="GET" name="form" commandName="form">
	<div class="text-center">
		<div class="row top_tiles">
		<div class="animated flipInY col-lg-3 col-md-3 col-sm-6 col-xs-6 text-center">
				<%-- <div class="tile-stats profileInfo" style="height:180px">
					<c:if test="${groupForm != null}">
						<div>
						 <c:choose>
                          	<c:when test="${groupForm.logoFile != ''}">
                          		<img  style="cursor: pointer" class="" src="${pageContext.request.contextPath}/resources/${groupForm.logoFile}" height="100"/>
                          	</c:when>
                          	<c:otherwise>
                          		<img  style="cursor: pointer" class="" src="${pageContext.request.contextPath}/resources/images/img.jpg" height="100" >
                          	</c:otherwise>
                          </c:choose>
						</div>
						<div>${groupForm.groupName}</div>
						<div>${groupForm.groupDirectorName}</div>
						
						<div><i class="fa fa-mobile" style="font-size:20px;color:black;"></i> ${groupForm.mobile}</div>
						<div><i class="fa fa-envelope" style="font-size:15px;color:red;"></i> ${groupForm.email}</div>
					</c:if> 
				</div>--%>
			</div>
			
			<%-- <div class="animated flipInY col-lg-3 col-md-3 col-sm-6 col-xs-6 text-center">
				<div class="tile-stats profileInfo" style="height:180px">
					<c:if test="${physician != null}">
						<div>
						 <c:choose>
                          	<c:when test="${physician.photoFile != ''}">
                          		<img  style="cursor: pointer" class="" src="${pageContext.request.contextPath}/resources/${physician.photoFile}" height="100"/>
                          	</c:when>
                          	<c:otherwise>
                          		<img  style="cursor: pointer" class="" src="${pageContext.request.contextPath}/resources/images/img.jpg" height="100" >
                          	</c:otherwise>
                          </c:choose>
						</div>
						<div>${physician.physicianName}</div>
						<div><i class="fa fa-mobile" style="font-size:20px;color:black;"></i> ${physician.mobile}</div>
						<div><i class="fa fa-envelope" style="font-size:15px;color:red;"></i> ${physician.email}</div>
					</c:if>
				</div>
			</div>
			<div class="animated flipInY col-lg-3 col-md-3 col-sm-6 col-xs-6">
				<div class="tile-stats profileInfo" style="height:180px">
					<c:if test="${clinic != null}">
						<div class="">
							<img  style="cursor: pointer" class="" src="${pageContext.request.contextPath}/resources/images/clinic.png" height="100" >						
						</div>
					
						<div>${clinic.clinicName}</div>
						<div><i class="fa fa-mobile" style="font-size:20px;color:black;"></i> ${clinic.phone}</div>
						<div><i class="fa fa-envelope" style="font-size:15px;color:red;"></i> ${clinic.email}</div>
					</c:if>
				</div>
			</div> --%>
			<div class="animated flipInY col-lg-3 col-md-3 col-sm-6 col-xs-6">
				<div class="tile-stats profileInfo" style="height:180px">
					<div class="">
						<img  style="cursor: pointer" class="" src="${pageContext.request.contextPath}/resources/images/CRE8-Pharma-logo.png" height="100" style="cursor:pointer" onclick="window.open('https://www.cre8pharmacy.com/','_blank');" />					
					</div>
					<div><spring:eval expression="@commonConfigurer.getProperty(\'patient.dashboard.cre_name\')" /></div>
					<div><spring:eval expression="@commonConfigurer.getProperty(\'patient.dashboard.add1\')" /></div>
					<div><spring:eval expression="@commonConfigurer.getProperty(\'patient.dashboard.add2\')" /></div>
				</div>
			</div>
			<div class="animated flipInY col-lg-3 col-md-3 col-sm-6 col-xs-6">
				<div class="tile-stats profileInfo" style="height:180px">
					<c:choose>
						<c:when test="${not empty docList}">
							<%-- <c:forEach items="${docList}" var="img">
	                       		<div class="clinicIcon docRecordArea">
	                       			<img src="${pageContext.request.contextPath}/resources/${img.displayImageName}" height="100" class="" />
	                       		</div>
	                       		<div class="docRecordArea">
	                        		<input type="hidden" value="${img.fileId}" class="idValue" >
	                        		${img.documentTitle}
	                       		</div>
	                        </c:forEach> --%>
	                        <div class="docRecordArea">
								<img  style="cursor: pointer" class="" src="${pageContext.request.contextPath}/resources/images/document.png" height="100" >						
							</div>
							<div  class="docRecordArea">Instruction Manuals</div>
						</c:when>
						<c:otherwise>
							<div class="">
								<img  style="cursor: pointer" class="" src="${pageContext.request.contextPath}/resources/images/document.png" height="100" >						
							</div>
							<div>No Document(s) available</div>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
		</div>
		<!-- <div class="clearfix"></div> -->

		<div class="row">
			<div class="col-md-12 col-sm-12 col-xs-12">
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
								<div class="row form-group #-gutter">
									<label class="col-md-2 col-sm-2 col-xs-12 text-right">Prescription #&nbsp;</label>
									<div class="col-md-2 col-sm-2 col-xs-12">
										<input type="text" id="prescriptionNo" class="form-control">
									</div>
								
									<label class="col-md-1 col-sm-1 col-xs-12 text-right">From Date&nbsp;</label>
		                       		<div class="col-md-2 col-sm-2 col-xs-12">
										<input type="text" id="prescriptionDate" class="form-control calendarIcon searchDate">
		                       		</div>
		                       		<label class="col-md-1 col-sm-1 col-xs-12 text-right">To Date&nbsp;</label>
		                       		<div class="col-md-2 col-sm-2 col-xs-12">
										<input type="text" id="prescriptionToDate" class="form-control calendarIcon searchDate">
		                       		</div>
									<div class="col-md-1 col-sm-1 col-xs-12 text-center">
										<img  style="cursor: pointer" class="img-responsive image-center" id="newPrescriptionSearch" src="${pageContext.request.contextPath}/resources/images/search3.png" title="Search">
									</div>
									<div class="col-md-1 col-sm-1 col-xs-12 text-center">
										<input type="hidden" id="" />
										<img  style="cursor: pointer" class="img-responsive image-center" id="clearPrescriptionSearch" src="${pageContext.request.contextPath}/resources/images/search-cancel2.png" title="Clear">
									</div>
								</div>
								<p></p>
								<div class="table-responsive" style="margin-left:150px">
									<div class="col-xs-12 col-sm-11 col-md-11 col-lg-11 text-left table-responsive">
										<table id="prescriptionSummaryTable" class="table table-striped jambo_table bulk_action">
											<thead>
												<tr class="heading_background text-center">
													<th>Id</th>
													<th>Prescription #</th>
													<th>Prescription Date</th>
													<th>Physician</th>
													<th>Patient</th>
													<th>Rx #</th>
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
								<h2>Refill Info</h2>
								<ul class="nav navbar-right panel_toolbox">
									<li><a class="collapse-link"><i class="fa fa-minus"></i></a></li>
									<li><a class="close-link"><i class="fa fa-close"></i></a></li>
								</ul>
								<div class="clearfix"></div>
							</div>
							<div class="x_content bgcolorblue1">
								<div class="row form-group">	
								<div class="col-md-2 col-sm-2 col-xs-12">
								
								</div>
									<label class="col-md-1 col-sm-2 col-xs-2">Status</label>
									<div class="col-md-3 col-sm-4 col-xs-4">
										<form:select path="prescriptionstatus" class="select2_single form-control" id="prescriptionstatus">
											<form:option value="">Select</form:option>
											<form:options items="${rxStatusList}" itemLabel="rxstatustypetext" itemValue="rxstatustypetext" />
										</form:select>
									</div>
									<div class="col-md-2 col-sm-3 col-xs-3 ">
										<img  style="cursor: pointer" class="img-responsive image-center" id="newItemSearch" src="${pageContext.request.contextPath}/resources/images/search3.png" title="Search">
									</div>
									<div class="col-md-2 col-sm-3 col-xs-3 ">
										<img  style="cursor: pointer" class="img-responsive image-center" id="clearItemSearch" src="${pageContext.request.contextPath}/resources/images/search-cancel2.png" title="Clear">
									</div>
								</div>
								<p></p>
								<div class="table-responsive">
									<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 text-left table-responsive">
										<table id="itemSummaryTable" class="table table-striped jambo_table bulk_action">
											<thead>
												<tr class="heading_background text-center">
													<th>Id</th>
													<th>Prescription #</th>
													<th>Rx #</th>
													<th>Item</th>
													<th>Status</th>
													<th>Qty</th>
													<th>Total Refills</th>
													<th>Refills Filled</th>
													<th>Days Supply</th>
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
</form:form>
<!-- /page content -->

<form:form method="POST" name="editPatientPage" commandName="form" action="editPatientAccount">
	<form:hidden path="patientId" value="${userId}" />
	<form:hidden path="prescriptionId" />
</form:form>

<script>
var urlPath="${pageContext.request.contextPath}/";
</script>
<%--includes the Front End UI validations--%>
<script src="${pageContext.request.contextPath}/resources/js/pages/dashboard/patientdashboard.js?v=13"></script>