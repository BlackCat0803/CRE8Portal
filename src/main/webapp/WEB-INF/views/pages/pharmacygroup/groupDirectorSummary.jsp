 <%--
 Front End UI to view the Group Director Summary
 --%>
<%@ include file="../../../layout/taglib.jsp"%>

<!-- page content -->
<form:form method="GET" name="groupDirectorSummaryForm" commandName="groupDirectorSummaryForm">
<div class="">
	<div class="row">
		<div class="col-md-12 col-sm-12 col-xs-12 formLink">
			
			<c:choose>
		      <c:when test="${(loginDetail.type == 'Administrator' or loginDetail.type == 'Super Admin' or loginDetail.type == 'Admin')}">
			      	<c:if test="${(loginDetail.groupDirectorCreationPermission =='Yes')}">
			      		<button type="button" id="newGroupdirector" class="btn btn-primary" >Create New Group Director</button>
			      	</c:if>
		      </c:when>
		      <c:otherwise>
		      			<button type="button" id="newGroupdirector" class="btn btn-primary" >Create New Group Director</button>
		      </c:otherwise>
		    </c:choose>
		</div>
	</div>
	
	<div class="row">
		<div class="col-md-12 col-sm-12 col-xs-12">
			<div class="x_panel profileBorder">
                <div class="x_title">
                  	<h2>Group Director Summary</h2>
                  	<div class="clearfix"></div>
                </div>
                
                <div class="x_content x_title_padding">
					<c:if test="${not empty message}">
						<div class="row">
							<div class="col-sm-offset-1 col-sm-10  fontSize14 text-center">
								<div class="alert alert-success fade in">
									<a href="#" class="close" data-dismiss="alert">&times;</a> &nbsp;&nbsp;&nbsp; ${message}
								</div>
							</div>
						</div>
					</c:if>

					
					<div class="row">
						<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 text-left">
							<div class="row form-group">
	                       		<div class="col-md-2 col-sm-6 col-xs-6 fieldTop">
									<input type="text" id="groupDirectorName" placeholder="Group Director" class="form-control" onkeypress="return blockedSearchText(event)" >
	                       		</div>
	                       		<div class="col-md-2 col-sm-6 col-xs-6 fieldTop">
									<form:select path="groupId" class="select2_single form-control" id="groupName">
										<form:option value="0">Select Group</form:option>
										<form:options items="${groupList}" itemLabel="groupName" itemValue="id" />
									</form:select>
	                       		</div>
	                       		<div class="col-md-2 col-sm-6 col-xs-6 fieldTop">
									<select id="status" class="form-control">
										<option value="">Select Status</option> 
										<option value="Active">Active</option>
										<option value="Inactive">Inactive</option>
									</select>
	                       		</div>
	                       		<div class="col-md-1 col-sm-3 col-xs-3 fieldTop text-center">
	                       			<button type="button" title="Search" id="newSearch" class="btn btn-primary" >Search</button>
	                       		</div>
	                       		<div class="col-md-1 col-sm-3 col-xs-3 fieldTop text-center">
	                       			<button type="button" id="clearSearch" title="Clear" class="btn btn-primary" >Clear</button>
	                       		</div>
	                     	</div>
						</div>
					</div>
					<div class="row">
						<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 text-left table-responsive">
							<table id="groupDirectorSummaryTable" class="table table-striped table-bordered">
								<thead>
									<tr class="heading_background">
										<th>Id</th>
										<th>Edit</th>
										<th>Group Director</th>
										<th>Email</th>
										<th>Group</th>
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
</form:form>
<!-- /page content -->


<form:form method="POST" name="editPage" commandName="groupDirectorSummaryForm" action="editGroupDirector"   >
	<form:hidden path="groupDirectorId" />
</form:form>
<%--includes the Front End UI validations--%>
<script src="${pageContext.request.contextPath}/resources/js/pages/pharmacygroup/groupDirectorSummary.js?v=6"></script>
