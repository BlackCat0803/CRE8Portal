 <%--
 Front End UI to view the Order details
 --%>

<%@ include file="../../../layout/taglib.jsp"%>
<div class="row">
	<div class="col-md-4 col-sm-4 col-xs-4">
		<img class="img-responsive goSummary" src="${pageContext.request.contextPath}/resources/images/back_icon-3.png" title="Go to Summary">
	</div>
	<div class="col-md-4 col-sm-4 col-xs-4 text-center pageHeading">
		Order Details
	</div>
	<div class="col-md-4 col-sm-4 col-xs-4 ">
		<img class="img-responsive pull-right pdfDownload" src="${pageContext.request.contextPath}/resources/images/pdf-icon-2.png" style="height:36px;" title="Download PDF">
	</div>
</div>

<!-- page content -->
<div class="">
	<form:form method="POST" name="orderForm" commandName="orderForm">
		<div class="row">
			<div class="col-md-12 col-sm-12 col-xs-12">
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

					<div class="row">
						<div class="col-md-6 col-sm-12 col-xs-12">
							<div class="x_panel">
								<div class="x_title">
				                	<h2>Order Info</h2>
				                  	<div class="clearfix"></div>
				               	</div>
				               	<div class="x_content x_title_padding">
									<div class="row">
										<div class="col-md-12 col-sm-12 col-xs-12">
											<label class="col-md-3 col-sm-2 col-xs-12 control-label" for="OrderNumber">Order #</label>
											<div class="col-md-9 col-sm-3 col-xs-12 form-group">
												<form:input path="orderNumber"  readonly="true" class="form-control"/>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-md-12 col-sm-12 col-xs-12">
											<label class="col-md-3 col-sm-2 col-xs-12 control-label" for="OrderDate">Order Date</label>
											<div class="col-md-9 col-sm-3 col-xs-12 form-group">
												<form:input path="orderDate" class="form-control" readonly="true"/>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-md-12 col-sm-12 col-xs-12">
											<label class="col-md-3 col-sm-2 col-xs-12 control-label" for="Patient">Patient</label>
											<div class="col-md-9 col-sm-3 col-xs-12 form-group">
												<form:input path="patientName" class="form-control" readonly="true"/>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-md-12 col-sm-12 col-xs-12">
											<label class="col-md-3 col-sm-2 col-xs-12 control-label" for="Physician">Physician</label>
											<div class="col-md-9 col-sm-3 col-xs-12 form-group">
												<form:input path="groupName" class="form-control" readonly="true"/>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-md-12 col-sm-12 col-xs-12">
											<label class="col-md-3 col-sm-2 col-xs-12 control-label" for="Group">Group</label>
											<div class="col-md-9 col-sm-3 col-xs-12 form-group">
												<form:input path="groupName" class="form-control" readonly="true"/>
											</div>
										</div>
									</div>
				               	</div>
							</div>
						</div>
						<div class="col-md-6 col-sm-12 col-xs-12">
							<div class="x_panel">
								<div class="x_title">
				                	<h2>Shipping Address</h2>
				                  	<div class="clearfix"></div>
				               	</div>
				               	<div class="x_content x_title_padding">
				               		
									<div class="row">
										<div class="col-md-12 col-sm-12 col-xs-12">
											<label class="col-md-4 col-sm-2 col-xs-12 control-label" for="shippingName">Shipping Name</label>
											<div class="col-md-8 col-sm-3 col-xs-12 form-group">
												<form:input path="shippingName" class="form-control " maxlength="100" readonly="true"/>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-md-12 col-sm-12 col-xs-12">
											<label class="col-md-4 col-sm-2 col-xs-12 control-label" for="shippingAddress">Address</label>
											<div class="col-md-8 col-sm-3 col-xs-12 form-group">
												<form:input path="shippingAddress" class="form-control" readonly="true"/>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-md-12 col-sm-12 col-xs-12">
											<label class="col-md-4 col-sm-2 col-xs-12 control-label" for="shippingCity">City</label>
											<div class="col-md-8 col-sm-3 col-xs-12 form-group">
												<form:input path="shippingCity" class="form-control" maxlength="25" readonly="true"/>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-md-12 col-sm-12 col-xs-12">
											<label class="col-md-4 col-sm-2 col-xs-12 control-label" for="shippingState">State</label>
											<div class="col-md-8 col-sm-3 col-xs-12 form-group">
												<form:input path="shippingState" class="form-control" maxlength="3" readonly="true"/>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-md-12 col-sm-12 col-xs-12">
											<label class="col-md-4 col-sm-2 col-xs-12 control-label" for="shippingZipCode">Zip Code</label>
											<div class="col-md-8 col-sm-3 col-xs-12 form-group">
												<form:input path="shippingZipCode" class="form-control" maxlength="12" readonly="true"/>
											</div>
										</div>
									</div>
				               	</div>
			               	</div>
						</div>
					</div>
							
					<div class="row">
						<div class="col-md-12 col-sm-12 col-xs-12">
				       		<div class="x_panel">
								<div class="x_title">
				                	<h2>Item detail:</h2>
				                  	<div class="clearfix"></div>
				               	</div>
								<c:if test="${not empty orderForm.transactions}">
									<c:forEach items="${orderForm.transactions}" var="i">
										<br />
										<div class="row">
											<div class="col-md-12 col-sm-12 col-xs-12">
												<div class="x_panel">
													<div class="x_title">
														<div class="row">
															<div class="col-md-6 col-sm-6 col-xs-6">
																<c:if test="${i.refillNumber eq 0}">
																	<span id="gotoPrescription" style="cursor:pointer;color:white" onclick="setPrescriptionId('${i.prescriptionId}')"><h2><u>Rx #:${i.rxNumber}</u></h2></span>
																</c:if>
																<c:if test="${i.refillNumber ne 0}">
																	<span id="gotoPrescription" style="cursor:pointer;color:white" onclick="setPrescriptionId('${i.prescriptionId}')"><h2><u>Rx #:${i.rxNumber}-${i.refillNumber}</u></h2></span>
																</c:if>
															</div>
															<div class="col-md-6 col-sm-6 col-xs-6">
																<span id="gotoInvoice" style="cursor:pointer;color:white" onclick="setInvoiceId('${i.invoiceId}')"><h2><u>Invoice #:${i.invoiceNumber}</u></h2></span>
															</div>
														</div>
														<div class="clearfix"></div>
													</div>
													<div class="x_content x_title_padding">
														<div class="row">
															<div class="col-md-6 col-sm-6 col-xs-12">
																<label class="col-md-5 col-sm-6 col-xs-12 control-label " for="rxItem">Reference #</label>
																<div class="col-md-7 col-sm-6 col-xs-12 form-group">
																	<input type="text" name="prescriptionNo" value="${i.prescriptionNo}" class="form-control " readonly="readonly" />
																	<input type="hidden" name="prescriberOrderNumber" value="${i.prescriberOrderNumber}" class="form-control " readonly="readonly" />
																</div>
															</div>
															<div class="col-md-6 col-sm-6 col-xs-12">
																<label class="col-md-4 col-sm-6 col-xs-12 control-label " for="rxItem">Status</label>
																<div class="col-md-8 col-sm-6 col-xs-12 form-group">
																	<input type="text" name="rxStatus" value="${i.rxStatus}" class="form-control " readonly="readonly" />
																</div>
															</div>
														</div>
			
														<div class="row">
															<div class="col-md-6 col-sm-6 col-xs-12">
																<label class="col-md-5 col-sm-6 col-xs-12 control-label " for="rxItem">Medication Prescribed</label>
																<div class="col-md-7 col-sm-6 col-xs-12 form-group">
																	<textarea rows="3" cols="25" name="medicationPrescribed" class="form-control " readonly="readonly">${i.medicationPrescribed}</textarea>
																</div>
															</div>
															<div class="col-md-6 col-sm-6 col-xs-12">
																<label class="col-md-4 col-sm-6 col-xs-12 control-label " for="rxItem">Dispensed Item</label>
																<div class="col-md-8 col-sm-6 col-xs-12 form-group">
																	<textarea rows="3" cols="25" name="medicationDispensed" class="form-control " readonly="readonly">${i.medicationDispensed}</textarea>
																</div>
															</div>
														</div>
			
														<div class="row">
															<div class="col-md-6 col-sm-6 col-xs-12">
																<label class="col-md-5 col-sm-6 col-xs-12 control-label " for="rxItem">Number Of Refills Filled</label>
																<div class="col-md-7 col-sm-6 col-xs-12 form-group">
																	<input type="text" name="refillsFilled" value="${i.refillsFilled}" class="form-control " readonly="readonly" />
																</div>
															</div>
															<div class="col-md-6 col-sm-6 col-xs-12">
																<label class="col-md-4 col-sm-6 col-xs-12 control-label" for="rxItem">Days Supply</label>
																<div class="col-md-8 col-sm-6 col-xs-12 form-group">
																	<input type="text" name="daysSupply" value="${i.daysSupply}" class="form-control" readonly="readonly" />
																</div>
															</div>
														</div>
			
														<div class="row">
															<div class="col-md-6 col-sm-6 col-xs-12">
																<label class="col-md-5 col-sm-6 col-xs-12 control-label" for="rxItem">Refills Remaining</label>
																<div class="col-md-7 col-sm-6 col-xs-12 form-group">
																	<input type="text" name="refillsRemaining" value="${i.refillsRemaining}" class="form-control" readonly="readonly" />
																</div>
															</div>
															<div class="col-md-6 col-sm-6 col-xs-12">
																<label class="col-md-4 col-sm-6 col-xs-12 control-label" for="rxItem">Quantity Remaining</label>
																<div class="col-md-8 col-sm-6 col-xs-12 form-group">
																	<input type="text" name="quantityRemaining" value="${i.quantityRemaining}" class="form-control" readonly="readonly" />
																</div>
															</div>
														</div>
			
														<div class="row">
															<div class="col-md-6 col-sm-6 col-xs-12">
																<label class="col-md-5 col-sm-6 col-xs-12 control-label" for="rxItem">Number Of Refills Allowed</label>
																<div class="col-md-7 col-sm-6 col-xs-12 form-group">
																	<input type="text" name="refillsAllowed" value="${i.refillsAllowed}" class="form-control" readonly="readonly" />
																</div>
															</div>
															<div class="col-md-6 col-sm-6 col-xs-12">
																<label class="col-md-4 col-sm-6 col-xs-12 control-label " for="rxItem">Last Refill Date</label>
																<div class="col-md-8 col-sm-6 col-xs-12 form-group">
																	<input type="text" name="lastFilledDate" value="${i.lastFilledDate}" class="form-control" readonly="readonly" />
																</div>
															</div>
														</div>
			
														<div class="row">
														
															<div class="col-md-6 col-sm-6 col-xs-12">
																<label class="col-md-5 col-sm-5 col-xs-12 control-label " for="rxItem">Priorty Type</label>
																<div class="col-md-7 col-sm-7 col-xs-12 form-group">
																	<input type="text" name="priorityType" value="${i.priorityType}" class="form-control " readonly="readonly" />
																</div>
															</div>
															<div class="col-md-6 col-sm-6 col-xs-12">
																<label class="col-md-4 col-sm-4 col-xs-12 control-label " for="rxItem">Tracking Number</label>
																<div class="col-md-4 col-sm-4 col-xs-12 form-group">
																	<input type="text" name="trackingNumber" value="${i.trackingNumber}" class="form-control " readonly="readonly" />
																</div>	
																		
																<div class="col-md-3 col-sm-3 col-xs-12 form-group">
																<c:if test="${not empty i.trackingNumber}">
																<img class="img-responsive trackorder" src="${pageContext.request.contextPath}/resources/images/track-order.png" height="78px" width="78px" title="Track Order"  onclick="callTrackInfo('${i.trackingurl}')">
																</c:if>
																</div>
																	
																
															</div>
															
														</div>
														<div class="row">
															<div class="col-md-6 col-sm-6 col-xs-12">
																<label class="col-md-5 col-sm-6 col-xs-12 control-label " for="shippingcompany">Shipping Company</label>
																<div class="col-md-7 col-sm-6 col-xs-12 form-group">
																	<input type="text" name="shippingcompany" value="${i.shippingcompany}" class="form-control" readonly="readonly" />
																</div>
															</div>
															<div class="col-md-6 col-sm-6 col-xs-12">
																<label class="col-md-4 col-sm-6 col-xs-12 control-label " for="shipmentstatus">Shipment Status</label>
																<div class="col-md-8 col-sm-6 col-xs-12 form-group">
																	<input type="text" name="shipmentstatus" value="${i.shipmentstatus}" class="form-control " readonly="readonly" />
																</div>
															</div>
														</div>
														<div class="row">
															<div class="col-md-6 col-sm-6 col-xs-12">
																<label class="col-md-5 col-sm-6 col-xs-12 control-label " for="rxItem">Lot Number</label>
																<div class="col-md-7 col-sm-6 col-xs-12 form-group">
																	<input type="text" name="lotNumber" value="${i.lotNumber}" class="form-control " readonly="readonly" />
																</div>
															</div>
															<div class="col-md-6 col-sm-6 col-xs-12">
																<label class="col-md-4 col-sm-6 col-xs-12 control-label " for="rxItem">Lot Expiration Date</label>
																<div class="col-md-8 col-sm-6 col-xs-12 form-group">
																	<input type="text" name="lotExpirationDate" value="${i.lotExpirationDate}" class="form-control" readonly="readonly" />
																</div>
															</div>
														</div>
			
														<div class="row">
															<div class="col-md-6 col-sm-6 col-xs-12">
																<label class="col-md-5 col-sm-6 col-xs-12 control-label" for="rxItem">Rx Comments</label>
																<div class="col-md-7 col-sm-6 col-xs-12 form-group">
																	<textarea rows="3" cols="25" name="rxComments" class="form-control " readonly="readonly">${i.rxComments}</textarea>
																</div>
															</div>
															<div class="col-md-6 col-sm-6 col-xs-12">
																<label class="col-md-4 col-sm-6 col-xs-12 control-label " for="rxItem">Completed Date</label>
																<div class="col-md-8 col-sm-6 col-xs-12 form-group">
																	<input type="text" name="completedDate" value="${i.completedDate}" class="form-control" readonly="readonly" />
																</div>
															</div>
														</div>
													</div>
												</div>
											</div>
										</div>
									</c:forEach>
								</c:if>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<form:hidden path="prescriptionId" />
		<form:hidden path="orderId" />
		<form:hidden path="invoiceId" />
	
	</form:form>
</div>
<!-- /page content -->

<div class="row">
	<div class="col-md-4 col-sm-4 col-xs-4">
		<img class="img-responsive goSummary" src="${pageContext.request.contextPath}/resources/images/back_icon-3.png" title="Go to Summary">
	</div>
	<div class="col-md-4 col-sm-4 col-xs-4 text-center pageHeading">
		
	</div>
	<div class="col-md-4 col-sm-4 col-xs-4 ">
		<img class="img-responsive pull-right pdfDownload" src="${pageContext.request.contextPath}/resources/images/pdf-icon-2.png" style="height:36px;" title="Download PDF">
	</div>
</div>

<script>
var urlPath="${pageContext.request.contextPath}/";
</script>
<%--includes the Front End UI validations--%>
<script src="${pageContext.request.contextPath}/resources/js/pages/order/order.js?v=3"></script>
