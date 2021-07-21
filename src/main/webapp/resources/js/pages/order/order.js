

var orderId = "";
var status = "";
try	{
	orderId = $("#orderId").val();

	var orderDetailTable = $("#orderDetailTable").DataTable({
		"processing" : true,
		"serverSide" : true,
		"dom":'<"top">rt<"bottom"pl><"clear">',
		//"lengthMenu" : [ [ 10, 15, 25], [ 10, 15, 25] ],
		//"lengthMenu":false,
		"pagingType": "full_numbers",
		"ajax" : {
			//"url" : "getOrderTransactionSummaryData?groupName="+groupName+"&status="+status,
			"url" : "getOrderTransactionSummaryData",
			"data": function ( d ) {
                d.orderId = orderId;
			},
			"type" : "POST" /*,
			"success": function (){ alert("Callback ran!"); }*/
		},
		"oLanguage": {
	        "sEmptyTable":  "No records available"
	    },
	    "searching": false,
        "paging": true, 
        "info": false,         
        //"lengthChange":false ,
		"columns" : [ 
		    { data : "orderTranId", "visible": false },
		    { 
				"class"			:	"edit-control", 
				"width"			:	"8%", 
			    "orderable"		:	false, 
			    "data"			:	null, 
			    "defaultContent":	""
			}, 
			{ data : "rxNumber","orderable":false, "sClass":"left_align"   },
		    { data : "prescriberOrderNumber","orderable":false, "sClass":"left_align"   },
		    { data : "rxStatus","orderable":false, "sClass":"left_align"   },
		    { data : "medicationPrescribed","orderable":false, "sClass":"left_align" },
		    { data : "medicationDispensed","orderable":false, "sClass":"left_align" },
		    { data : "daysSupply","orderable":false, "sClass":"left_align" },
		    { data : "quantityRemaining","orderable":false, "sClass":"left_align" },
		    { data : "refillsAllowed","orderable":false, "sClass":"left_align" },
		    { data : "refillsFilled","orderable":false, "sClass":"left_align" },
		    { data : "refillsRemaining","orderable":false, "sClass":"left_align" },
		    { data : "lastFilledDate","orderable":false, "sClass":"left_align" },
		    { data : "trackingNumber","orderable":false, "sClass":"left_align" },
		    { data : "priorityType","orderable":false, "sClass":"left_align" },
		    { data : "lotNumber","orderable":false, "sClass":"left_align" },
		    { data : "lotExpirationDate","orderable":false, "sClass":"left_align" },
		    { data : "rxComments","orderable":false, "sClass":"left_align" },
		    { data : "completedDate","orderable":false, "sClass":"left_align" },
			],
		
		"error" : "Error while processing data...."
	});
	
	// For Edit Record.
    $("thead tr .edit-control").removeClass("edit-control");
	$('#orderDetailTable tbody').on('click', 'tr td.edit-control', function() {
		
		var id=$(this).closest('tr').attr("id").replace("row_", "");
		
		$("#prescriptionId").val(id);
		document.forms[0].action = urlPath+"prescription/editPrescription";
		document.forms[0].method="POST";
		document.forms[0].submit();

		
	});
	
	$(document).on("click", "#newSearch", function() {
		groupName = $("#groupName_search").val();
		status = $("#status_search").val();
		
		$(".alert-success").remove();
		$(".alert-danger").remove();
		
		//orderDetailTable.ajax.url("getOrderTransactionSummaryData?groupName="+groupName+"&status="+status).draw();
		orderDetailTable.ajax.url("getOrderTransactionSummaryData");
	});
	
	$(document).on("click", "#clearSearch", function() {
		groupName = "";
		status = "";
		
		$("#groupName_search").val("");
		$("#status_search").val("");
		
		$(".alert-success").remove();
		$(".alert-danger").remove();
		
		//orderDetailTable.ajax.url("getOrderTransactionSummaryData?groupName="+groupName+"&status="+status).draw();
		orderDetailTable.ajax.url("getOrderTransactionSummaryData");
	});	
} catch(e) {
	// alert(" Exception in redrawDataTable()     ======     " + e);
}


function callTrackInfo(trackingurl)
{
	/*window.location=trackingurl
	window.target = "_blank";*/
	
	window.open(trackingurl,'_blank');
	
}

$(document).on("click", ".goSummary", function(){
	document.location = "orderSummary";
});


$(document).on("click", ".pdfDownload", function() {
	var invId = $("#orderId").val();
	if (invId != "" && parseInt(invId) > 0) {
		document.orderForm.action = urlPath+"order/orderPdfDownload";
		document.orderForm.submit();
	}
});
$(document).on("click", "#gotoPrescription", function() {

	var pid = $("#prescriptionId").val();
	if (pid != null && pid != undefined && pid != "" && parseInt(pid) > 0) {
		document.orderForm.action = urlPath+"order/orderPrescription";
		document.orderForm.submit();
	}

});
$(document).on("click", "#gotoInvoice", function() {

	var pid = $("#invoiceId").val();
	if (pid != null && pid != undefined && pid != "" && parseInt(pid) > 0) {
		document.orderForm.action = urlPath+"order/orderInvoice";
		document.orderForm.submit();
	}

});
function setPrescriptionId(prescriptionId)
{
	$("#prescriptionId").val(prescriptionId);
}

function setInvoiceId(invoiceId)
{
	$("#invoiceId").val(invoiceId);
}

