 <%--
 Front End UI to view the Group Master Summary
 --%>
<%@ include file="../../../layout/taglib.jsp"%>

<!-- page content -->
<div class="summaryTop" id="topPage">
	<div class="row">
		<div class="col-md-12 col-sm-12 col-xs-12 formLink">
			<c:choose>
		      <c:when test="${(loginDetail.type == 'Administrator' or loginDetail.type == 'Super Admin' or loginDetail.type == 'Admin')}">
			      	<c:if test="${(loginDetail.groupCreationPermission =='Yes')}">
			      		<button type="button" id="newGroup" class="btn btn-primary" >Create New Group</button>
			      	</c:if>
		      </c:when>
		      <c:otherwise>
		      			<button type="button" id="newGroup" class="btn btn-primary" >Create New Group</button>
		      </c:otherwise>
		    </c:choose>
		</div>
	</div>	
	
	<div class="row">
		<div class="col-md-12 col-sm-12 col-xs-12">

			<c:if test="${not empty message}">
				<div class="row">
					<div class="col-sm-offset-1 col-sm-10 fontSize14 text-center">
						<div class="alert alert-success fade in">
							<a href="#" class="close" data-dismiss="alert">&times;</a>  &nbsp;&nbsp;&nbsp; ${message}
						</div>
					</div>
				</div>
			</c:if>
		
			 <div class="x_panel profileBorder">
                <div class="x_title">
                  	<h2>Group Master Summary</h2>
                  	<div class="clearfix"></div>
                </div>
	           	<div class="x_content x_title_padding">
					<div class="row">
						<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 text-left">
							<div class="row form-group">
	                       		<div class="col-md-2 col-sm-6 col-xs-6">
									<input type="text" id="groupName_search" placeholder="Group Name" class="form-control" onkeypress="return blockedSearchText(event)" />
	                       		</div>
	                       		<div class="col-md-2 col-sm-6 col-xs-6">
									<select id="status_search" class="form-control">
										<option value="">Select Status</option> 
										<option value="Active">Active</option>
										<option value="Inactive">Inactive</option>
									</select>
	                       		</div>
	                       		<div class="col-md-1 col-sm-6 col-xs-6 text-center">
	                       			<button type="button" title="Search" id="newSearch" class="btn btn-primary" >Search</button>
	                       		</div>
	                       		<div class="col-md-1 col-sm-6 col-xs-6 text-center">
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
										<th>Group</th>
										<th>Group Director</th>
										<th>Contact Name</th>
										<th>Email</th>
										<th>Mobile</th>
										<th>Status</th>
									</tr>
								</thead>
							</table>
						</div>
					</div>
				</div>
			</div>
			
			<form:form method="POST" id="editPage" name="editPage" commandName="groupMasterForm"  action="editGroupMasterAccount" >
				<form:hidden path="groupId" />
			</form:form>
		</div>
	</div>
</div>
<!-- /page content -->
<%--includes the Front End UI validations--%>
<script src="${pageContext.request.contextPath}/resources/js/pages/pharmacygroup/groupMasterSummary.js?v=1"></script>
