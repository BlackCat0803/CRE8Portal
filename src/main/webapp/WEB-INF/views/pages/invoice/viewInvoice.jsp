<%@ include file="../../../layout/taglib.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix = "fmt" %>


<div class="row">
	<div class="col-md-4 col-sm-2 col-xs-2">
		<img class="img-responsive goSummary" src="${pageContext.request.contextPath}/resources/images/back_icon-3.png" title="Go to Summary">
	</div>
	<div class="col-md-4 col-sm-8 col-xs-8 text-center pageHeading">
		Invoice Details
	</div>
	<div class="col-md-4 col-sm-2 col-xs-2 ">
		<img class="img-responsive pull-right pdfDownload" src="${pageContext.request.contextPath}/resources/images/pdf-icon-2.png" style="height:36px;" title="Download PDF">
	</div>
</div>

<!-- page content -->
<div class="">
	<form:form method="POST" action="SaveInvoice" name="invoiceForm" commandName="invoiceForm" >
	
		<div class="row">
        	<div class="col-md-12 col-sm-12 col-xs-12">
				<div class="x_panel">
                	<div class="x_title">
                    	<h2>Invoice</h2>
                    	<div class="clearfix"></div>
                  	</div>
                  	<div class="x_content x_title_padding">
						<div class="row">
	                    	<div class="col-md-12 col-sm-12 col-xs-12 profile_left">
								
								<div class="row">
									<div class="col-md-6 col-sm-6 col-xs-12 form-group">
										<label class="col-md-3 col-sm-4 col-xs-4 control-label" for="invoiceNo">Invoice #</label>
										<div class="col-md-9 col-sm-8 col-xs-8 ">
											<form:input path="invoiceNo" class="form-control" readonly="true" />
										</div>
									</div>
									
								</div>
								
								<div class="row">
									<div class="col-md-6 col-sm-12 col-xs-12 form-group">
										<label class="col-md-3 col-sm-4 col-xs-4 control-label" for="invoiceDateStr">Invoice Date </label>
		                        		<div class="col-md-9 col-sm-8 col-xs-8">
		                          			<form:input path="invoiceDateStr" class="form-control" readonly="true"/>
		                        		</div>
									</div>
								</div>
								
								<div class="row">
									<div class="col-md-6 col-sm-12 col-xs-12 form-group">
										<label class="col-md-3 col-sm-4 col-xs-4 control-label " for="rxNumber">Order #</label>
										<div class="col-md-9 col-sm-8 col-xs-8 ">
										<form:input path="rxNumber" class="form-control" readonly="true" />
										</div>
									</div>
									
									<div class="col-md-6 col-sm-12 col-xs-12 form-group">
										<label class="col-md-3 col-sm-4 col-xs-4 control-label" for="writtenDate">Written Date </label>
		                        		<div class="col-md-9 col-sm-8 col-xs-8">
		                          			<form:input path="writtenDateStr" class="form-control" readonly="true"/>
		                        		</div>
									</div>
								</div>
								
								<div class="row">
									<div class="col-md-6 col-sm-12 col-xs-12 form-group">
										<label class="col-md-3 col-sm-4 col-xs-4 control-label " for="patientName">Patient</label>
										<div class="col-md-9 col-sm-8 col-xs-8 form-group">
											<form:input path="patientName" class="form-control" readonly="true" />
										</div>
									</div>									

									<div class="col-md-6 col-sm-12 col-xs-12 form-group">
										<label class="col-md-3 col-sm-4 col-xs-4 control-label" for="physicianName">Prescriber </label>
		                        		<div class="col-md-9 col-sm-8 col-xs-8">
		                          			<form:input path="physicianName" class="form-control" maxlength="25" readonly="true"  />
		                        		</div>
									</div>
								</div>
                    		</div>
						</div>
                  	</div>
				</div>        	
        	</div>
        </div>
        
        
       	<div class="row">
        	<div class="col-md-6 col-sm-12 col-xs-12">
				<div class="x_panel">
                	<div class="x_title">
                    	<h2>Billing Address</h2>
                    	<div class="clearfix"></div>
                  	</div>
                  	<div class="x_content x_title_padding">
						<div class="row">
							<div class="col-md-12 col-sm-12 col-xs-12">
								<label class="col-md-3 col-sm-4 col-xs-4 control-label " for="billingName">Billing Name</label>
								<div class="col-md-9 col-sm-8 col-xs-8 form-group">
									<form:input path="billingName" class="form-control " readonly="true" />
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-12 col-sm-12 col-xs-12">
								<label class="col-md-3 col-sm-4 col-xs-4 control-label " for="billingAddress">Address</label>
								<div class="col-md-9 col-sm-8 col-xs-8 form-group">
									<form:input path="billingAddress" class="form-control " readonly="true" />
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-12 col-sm-12 col-xs-12">
								<label class="col-md-3 col-sm-4 col-xs-4 control-label " for="billingCity">City</label>
								<div class="col-md-9 col-sm-8 col-xs-8 form-group">
									<form:input path="billingCity" class="form-control " readonly="true" />
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-12 col-sm-12 col-xs-12">
								<label class="col-md-3 col-sm-4 col-xs-4 control-label " for="billingState">State</label>
								<div class="col-md-9 col-sm-8 col-xs-8 form-group">
									<form:input path="billingState" class="form-control " readonly="true" />
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-12 col-sm-12 col-xs-12">
								<label class="col-md-3 col-sm-4 col-xs-4 control-label " for="billingZipcode">Zip</label>
								<div class="col-md-9 col-sm-8 col-xs-8 form-group">
									<form:input path="billingZipcode" class="form-control " readonly="true" />
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
								<label class="col-md-3 col-sm-4 col-xs-4 control-label " for="shippingName">Shipping Name</label>
								<div class="col-md-9 col-sm-8 col-xs-8 form-group">
									<form:input path="shippingName" class="form-control " readonly="true" />
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-12 col-sm-12 col-xs-12">
								<label class="col-md-3 col-sm-4 col-xs-4 control-label " for="shippingAddress">Address</label>
								<div class="col-md-9 col-sm-8 col-xs-8 form-group">
									<form:input path="shippingAddress" class="form-control " readonly="true" />
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-12 col-sm-12 col-xs-12">
								<label class="col-md-3 col-sm-4 col-xs-4 control-label " for="shippingCity">City</label>
								<div class="col-md-9 col-sm-8 col-xs-8 form-group">
									<form:input path="shippingCity" class="form-control " readonly="true" />
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-12 col-sm-12 col-xs-12">
								<label class="col-md-3 col-sm-4 col-xs-4 control-label " for="shippingState">State</label>
								<div class="col-md-9 col-sm-8 col-xs-8 form-group">
									<form:input path="shippingState" class="form-control " readonly="true" />
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-12 col-sm-12 col-xs-12">
								<label class="col-md-3 col-sm-4 col-xs-4 control-label " for="shippingZipcode">Zip</label>
								<div class="col-md-9 col-sm-8 col-xs-8 form-group">
									<form:input path="shippingZipcode" class="form-control " readonly="true" />
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
						<div class="row">
							<div class="col-md-4 col-sm-6 col-xs-6 form-group">
								<h2>Item detail:</h2>
							</div>				
							<div class="col-md-4 col-sm-6 col-xs-6 form-group">
								<span id="gotoPrescription" style="cursor:pointer;color:white;" onclick="setPrescriptionId('${invoiceForm.prescriptionId}')"><h2 style="text-decoration: underline;">Prescription #:${invoiceForm.prescriptionNumber}</h2></span>
							</div>
							<div class="col-md-4 col-sm-6 col-xs-6 form-group">
								<span id="gotoOrder" style="cursor:pointer;color:white;" onclick="setOrderId('${invoiceForm.orderId}')"><h2 style="text-decoration: underline;">Order #:${invoiceForm.orderNumber}</h2></span>
							</div>
						</div>
						<div class="clearfix"></div>
					</div>             	
	                <div class="x_content x_title_padding">
	                	<div class="x_content x_title_padding table-responsive">
		               		<table id="medicationTable" class="table table-striped table-bordered">
		               			<thead>
			               			<tr class="heading_background">
			               				<td style="width:10%;">Reference #</td>
			               				<td style="width:10%;">Order #</td>
			               				<td style="width:28%;">Item</td>
			               				<td style="width: 10%;" >Number of Refills Filled</td>
			               				<td style="width: 8%;" >Quantity</td>
			               				<td style="width: 10%;" >Lot No</td>
			               				<td style="width: 12%;" >Expiration Date</td>
			               				<td style="width: 12%;" >Total $</td>
			               				<td style="width: 10%;" >Payment Status</td>
			               				<td style="width: 10%;" >Payment Type</td>
		               				</tr>
		               			</thead>
		               			<tbody>
			               			<c:forEach items="${invoiceForm.items}" varStatus="i">
			               			
				               			<tr>
				               				<td><form:input path="items[${i.index}].prescriptionNo" readonly="true" class="form-control " /></td>
				               				<td><form:input path="items[${i.index}].rxNumber" readonly="true" class="form-control " /></td>
				               				<td><form:textarea path="items[${i.index}].rxItem" readonly="true" class="form-control " /></td>
				               				<td><form:input path="items[${i.index}].noOfRefillsFilled" readonly="true" class="form-control " /></td>
				               				<td><form:input path="items[${i.index}].rxQuantity" readonly="true" class="form-control " /></td>
				               				<td><form:input path="items[${i.index}].rxLotNo" readonly="true" class="form-control " /></td>
				               				<td><form:input path="items[${i.index}].rxExpirationdate" readonly="true" class="form-control " /></td>
				               			
				               				<td>
				               					<input type="text" value='<fmt:formatNumber type = "currency" pattern = "##########0.00" 
	         										maxFractionDigits ="2"  value = "${invoiceForm.items[i.index].rxTotal}"  />'  
	         										class="form-control  text-right" readonly="readonly" />
				               				</td>
				               				<td><form:input path="items[${i.index}].paymentstatus" readonly="true" class="form-control " /></td>
				               				<td><form:input path="items[${i.index}].paymenttype" readonly="true" class="form-control " /></td>
				               			</tr>
			               			</c:forEach>
		               			</tbody>
		               		</table>
		               	
			               	<div class="row form-group">
								<label class="col-md-10 col-sm-6 col-xs-6 control-label text-right" for="writtenBy">Sub Total $</label>
	                       		<div class="col-md-2 col-sm-6 col-xs-6">
	                         		<%-- <form:input path="subtotal" class="form-control" readonly="true"/> --%>
         							<input type="text" id="subtotal" value='<fmt:formatNumber type = "currency" pattern = "##########0.00" 
         								maxFractionDigits ="2"  value = "${invoiceForm.subtotal}"  />'  class="form-control  text-right" readonly="readonly" />
	     	                  	</div>
	     	                  	
							</div>
							<div class="row form-group">
								<label class="col-md-10 col-sm-6 col-xs-6 control-label text-right" for="writtenDate">Tax $</label>
	                       		<div class="col-md-2 col-sm-6 col-xs-6">
	                         		<%-- <form:input path="tax" class="form-control" readonly="true"/> --%>
	                         		<input type="text" id="tax" value='<fmt:formatNumber type = "currency" pattern = "##########0.00" 
         								maxFractionDigits ="2"  value = "${invoiceForm.tax}"  />'  class="form-control  text-right" readonly="readonly" />
	                       		</div>
	                       		
							</div>
							<div class="row form-group">
								<label class="col-md-10 col-sm-6 col-xs-6 control-label text-right" for="physicianName">Total $</label>
	                       		<div class="col-md-2 col-sm-6 col-xs-6">
	                         		<%-- <form:input path="total" class="form-control" maxlength="25" readonly="true"/> --%>
	                         		<input type="text" id="total" value='<fmt:formatNumber type = "currency" pattern = "##########0.00" 
         								maxFractionDigits ="2"  value = "${invoiceForm.total}"  />'  class="form-control  text-right" readonly="readonly" />
	                       		</div>
	                       		
							</div>
		               	</div>
		               	
					</div>
				</div>
			</div>
		</div>
		<form:hidden path="invoiceId" />
		<form:hidden path="prescriptionId" />
		<form:hidden path="orderId" />
	</form:form>
</div>

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
<script src="${pageContext.request.contextPath}/resources/js/pages/invoice/invoice.js?v=1"></script>