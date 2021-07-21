 <%--
 Front End UI to view the Admin Account Summary
 --%>
<%@ include file="../../../layout/taglib.jsp"%>

<!-- page content -->
<div class="">
	<div class="row">
		<div class="col-md-12 col-sm-12 col-xs-12 formLink">
			 <c:choose>
		      <c:when test="${(loginDetail.type == 'Administrator' or loginDetail.type == 'Super Admin' or loginDetail.type == 'Admin')}">
			      	<c:if test="${(loginDetail.adminAccCreationPermission =='Yes')}">
			      		<button type="button" id="newAdminAcc" class="btn btn-primary" >Create New Pharmacy Admin Account</button>
			      	</c:if>
		      </c:when>
		      <c:otherwise>
		      		<button type="button" id="newAdminAcc" class="btn btn-primary" >Create New Pharmacy Admin Account</button>
		      </c:otherwise>
		    </c:choose>
    
		</div>
	</div>

	<div class="row">
		<div class="col-md-12 col-sm-12 col-xs-12">
			<div class="x_panel profileBorder">
                <div class="x_title">
                  	<h2>Pharmacy Admin Summary</h2>
                  	<div class="clearfix"></div>
                </div>
           	
	           	<div class="x_content x_title_padding">
	           	<form:form method="POST" id="editPage" name="editPage" commandName="form" action="editAdminAccount"   >
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
									<input type="text" id="adminName" placeholder="Pharmacy Admin Name" class="form-control" onkeypress="return blockedSearchText(event)" >
	                       		</div>
	                       		<div class="col-md-2 col-sm-6 col-xs-6 fieldTop">
									<input type="text" id="pharmacyName" placeholder="Pharmacy Name" class="form-control" onkeypress="return blockedSearchText(event)" >
	                       		</div>
	                       		<div class="col-md-2 col-sm-6 col-xs-6 fieldTop">
									<select id="usertype" class="form-control">
										<option value="">Select Type</option> 
										<option value="Admin">Admin</option>
										<option value="Super Admin">Super Admin</option>
									</select>
	                       		</div>
	                       		<div class="col-md-2 col-sm-6 col-xs-6 fieldTop">
									<select id="status" class="form-control">
										<option value="">Select Status</option> 
										<option value="Active">Active</option>
										<option value="Inactive">Inactive</option>
									</select>
	                       		</div>
	                       		<div class="col-md-1 col-sm-2 col-xs-6 fieldTop text-center">
	                       			<button type="button" title="Search" id="newSearch" class="btn btn-primary" >Search</button>
	                       		</div>
	                       		<div class="col-md-1 col-sm-2 col-xs-6 fieldTop text-left">
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
										<th>Pharmacy Admin</th>
										<th>Pharmacy</th>
										<th>Type</th>
										<th>Email</th>
										<th>Mobile</th>
										<th>Status</th>
									</tr>
								</thead>
							</table>
						</div>
					</div>
					
						<form:hidden path="adminId" />
					</form:form>
				</div>
			</div>
		</div>
	</div>
</div>
<!-- /page content -->
<%--includes the Front End UI validations--%>
<script src="${pageContext.request.contextPath}/resources/js/pages/admin/adminSummary.js?v=7"></script>
