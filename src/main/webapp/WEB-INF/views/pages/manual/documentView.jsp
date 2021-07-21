 <%--
 Front End UI to list the Instruction Documents Uploaded
 --%>
<%@ include file="../../../layout/taglib.jsp" %>

<div class="row">
	<div class="col-md-12 col-sm-12 col-xs-12">
		<div class="x_panel profileBorder">
			<div class="x_title">
               	<h2>Instruction Manual Documents</h2>
               	<div class="clearfix"></div>
			</div>				
			<div class="x_content x_title_padding">
				<div class="row">
					<c:choose>
						<c:when test="${not empty docList}">
							<div class="col-md-2 col-sm-8 col-xs-12">
								<ul class="nav nav-tabs tabs-left">
			                        <c:forEach items="${docList}" var="img">
			                        	<li class="viewFull">
			                        		<img src="${pageContext.request.contextPath}/resources/${img.displayImageName}" height="128" class="img-responsive avatar-view" />
			                        		<input type="hidden" value="${img.fileId}" class="idValue" >
			                        		${img.documentTitle}
			                        	</li>
			                        </c:forEach>
		                      	</ul>
		                      	<input type="hidden" id="rootPath" value="${pageContext.request.contextPath}/resources/" />
							</div>
							<div class="col-md-10 col-sm-8 col-xs-12 documentDisplayArea">
									
							</div>
						</c:when>
						<c:otherwise>
							<div class="col-md-12 col-sm-12 col-xs-12 text-center">
								No Document(s) available
							</div>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
		</div>
	</div>
</div>
<%--includes the Front End UI validations--%>
<script src="${pageContext.request.contextPath}/resources/js/pages/manual/documentView.js"></script>