 <%--
 Front End UI to upload the Instruction Documents
 --%>
<%@ include file="../../../layout/taglib.jsp" %>

<form:form method="POST" action="saveDocuments" name="manual" commandName="manual" enctype="multipart/form-data">
	<c:if test="${not empty message}">
		<c:choose>
			<c:when test="${saveStatus == 'true'}">
				<div class="row">
					<div class="col-sm-offset-1 col-sm-10  fontSize14 text-center">
						<div class="alert alert-success fade in">
							<a href="#" class="close" data-dismiss="alert">&times;</a> 
								 &nbsp;&nbsp;&nbsp; ${message}
						</div>
					</div>
				</div>
			</c:when>
			<c:otherwise>
				<div class="row">
					<div class="col-sm-offset-1 col-sm-10  fontSize14 text-center">
						<div class="alert alert-danger fade in">
							<a href="#" class="close" data-dismiss="alert">&times;</a> 
							${message}
						</div>
					</div>
				</div>
			</c:otherwise>
		</c:choose>
	</c:if>


	<div class="row">
		<div class="col-md-12 col-sm-12 col-xs-12">
			<div class="x_panel profileBorder">
				<div class="x_title">
                   	<h2>Instruction Manual Documents</h2>
                   	<div class="clearfix"></div>
				</div>				
				<div class="x_content x_title_padding">
					
					<div class="row">
						<div class="col-md-12 col-sm-12 col-xs-12 profile_left">

							<div class="row">
								<div class="col-md-8 col-sm-12 col-xs-12 profile_left">
								
									<div class="col-md-12 col-sm-12 col-xs-12">
										<label class="col-md-3 col-sm-4 col-xs-4 control-label required" for="documentTitle">Document Title</label>
										<div class="col-md-6 col-sm-8 col-xs-8 form-group">
											<form:input path="documentTitle" class="form-control" />
										</div>
									</div>

									<div class="col-md-9 col-md-offset-3 col-sm-6 col-sm-offset-6 col-xs-12 photoNotes photoPaddingNotes">
										<label><b>Note:</b> <spring:eval expression="@commonConfigurer.getProperty(\'manual.image.notes\')" /></label>
									</div>
									<div class="col-md-12 col-sm-12 col-xs-12">
										<label class="col-md-3 col-sm-6 col-xs-12 control-label required" for="uploadImageFile">Upload Image</label>
										<div class="col-md-9 col-sm-6 col-xs-12 form-group">
											<input type="file" id="thumbfile" name="uploadImageFile" accept="image/x-png" >
											<div class="img_file-upload-errors"></div>
										</div>
									</div>

									<div class="col-md-9 col-md-offset-3 col-sm-6 col-sm-offset-6 col-xs-12 photoNotes photoPaddingNotes">
										<label><b>Note:</b> <spring:eval expression="@commonConfigurer.getProperty(\'document.image.notes\')" /></label>
									</div>
									<div class="col-md-12 col-sm-12 col-xs-12">
										<label class="col-md-3 col-sm-6 col-xs-12 control-label required" for="uploadDocFile">Upload File</label>
										<div class="col-md-9 col-sm-6 col-xs-12 form-group">
											<input type="file" id="file" name="uploadDocFile" accept="application/pdf">
											<div class=" file-upload-errors"></div>
										</div>
									</div>
								
									<div class="col-md-12 col-sm-12 col-xs-12">
										<label class="col-md-3 col-sm-4 col-xs-4 control-label required" for="mobile">Display Order</label>
										<div class="col-md-6 col-sm-8 col-xs-8 form-group">
											<form:input path="displayOrder" class="form-control" onkeypress="return isNumber(event)" />
										</div>
									</div>
									<input type="hidden" id="tmpDisplayOrder" value="${manual.displayOrder}" />
								</div>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-md-6 col-sm-12 col-xs-12 profile_left text-center">
							<button type="submit"  class="btn btn-success">Save</button>
							<!-- <input type="submit" id="submitfileDocHidden" style="display: none;"> -->
						</div>
					</div>
					<div class="row">
						<div class="col-md-6 col-sm-12 col-xs-12 text-center formErrorMsg" style="display:none;">
							<b>Error:</b> <spring:eval expression="@commonConfigurer.getProperty(\'error.form_fields_not_valid\')" />
						</div>
					</div>
					<hr class="lineBreak">
					
					<div class="row form-group">
                     	<div class="col-md-3 col-sm-6 col-xs-6">
							<input type="text" id="displayName" placeholder="Document Title" class="form-control" onkeypress="return blockedSearchText(event)" >
                     	</div>
                     	<div class="col-md-1 col-sm-3 col-xs-3 text-center">
                       		<button type="button" title="Search" id="docSearch" class="btn btn-primary" >Search</button>
                       	</div>
                       	<div class="col-md-1 col-sm-3 col-xs-3 text-center">
                       		<button type="button" id="clearDocSearch" title="Clear" class="btn btn-primary" >Clear</button>
                       	</div>
					</div>
					
					<div class="row form-group">
						<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 text-left table-responsive">
							<table id="docListTable" class="table table-striped table-bordered">
								<thead>
									<tr class="heading_background">
										<th>Id</th>
										<th>Edit</th>
										<th>Delete</th>
										<th>Document Title</th>
										<th>Image </th>
										<th>File</th>
										<th>Display Order</th>
									</tr>
								</thead>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<form:hidden path="fileId" />

	<input type="hidden" id="err_doc_title" value='<spring:eval expression="@instructionConfigurer.getProperty(\'error.document_title\')" />' />
	<input type="hidden" id="err_display_order" value='<spring:eval expression="@instructionConfigurer.getProperty(\'error.display_order\')" />' />
	<input type="hidden" id="err_display_order_valid" value='<spring:eval expression="@instructionConfigurer.getProperty(\'error.display_order_valid\')" />' />
	
</form:form>

<form:form method="POST" name="editPage" commandName="manual" action="editManualDocument"   >
	<form:hidden path="tempId" />
</form:form>
<%--includes the Front End UI validations--%>					
<script src="${pageContext.request.contextPath}/resources/js/pages/manual/documents.js?v=5"></script>