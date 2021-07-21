var groupName = "";
var status = "";
try	{
	groupName = $("#groupName_search").val();
	status = $("#status_search").val();
	
	var summaryTable = $("#summaryTable").DataTable({
		"processing" : true,
		"serverSide" : true,
		"dom":'<"top">rt<"bottom"pl><"clear">',
		"lengthMenu" : [ [ 10, 15, 25], [ 10, 15, 25] ],
		"pagingType": "full_numbers",
		"ajax" : {
			//"url" : "getInvoiceSummaryData?groupName="+groupName+"&status="+status,
			"url" : "getInvoiceSummaryData",
			"data": function ( d ) {
                d.groupName = groupName;
                d.status = status;
      		},
			"type" : "POST"/*,
			"success": function (){ alert("Callback ran!"); }*/
		},
		"oLanguage": {
	        "sEmptyTable":  "No records available"
	    },
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
		    { data : "orderStatus","orderable":false, "sClass":"left_align"   },
		    { data : "medicationPrescribed","orderable":false, "sClass":"left_align" },
		    { data : "daysSupply","orderable":false, "sClass":"left_align" },
		    { data : "quantityRemaining","orderable":false, "sClass":"left_align" },
		    { data : "noOfRefillsAllowed","orderable":false, "sClass":"left_align" },
		    { data : "noOfRefillsFilled","orderable":false, "sClass":"left_align" },
		    { data : "refillsRemaining","orderable":false, "sClass":"left_align" },
		    { data : "lastFilledDate","orderable":false, "sClass":"left_align" },
		    { data : "trackingNumber","orderable":false, "sClass":"left_align" },
			],
		
		"error" : "Error while processing data...."
	});
	
	// For Edit Record.
    $("thead tr .edit-control").removeClass("edit-control");
	$('#summaryTable tbody').on('click', 'tr td.edit-control', function() {
		
		var id=$(this).closest('tr').attr("id").replace("row_", "");
		
		//Edit Group Master Account changed to Ajax Call
		$.ajax({
			type : "POST",
			"data": function ( d ) {
                d.id = id;
      		},
			url : "ajaxEditGroupMasterAccount",
			success : function(data) {
				//alert(data)
				var dataArr=$.parseJSON(data);
				
				$("#groupId").val(dataArr["groupId"]);
				$("#groupName").val(dataArr["groupName"]);
				$("#groupDirectorName").val(dataArr["groupDirectorName"]);
				$("#contactName").val(dataArr["contactName"]);
				$("#email").val(dataArr["email"]);
				$("#mobile").val(dataArr["mobile"]);
				$("#status").val(dataArr["status"]);
				
				//console.log("SUCCESS: ", data);
				
			},
			error : function(e) {
				//console.log("ERROR: ", e);
				
			},
			done : function(e) {
				//console.log("DONE");
			}
		});
		
		/**
		 * 
		location.href = "editGroupMasterAccount?id="+id;
		 */
		
		
	
		
	});
	
	$(document).on("click", "#newSearch", function() {
		groupName = $("#groupName_search").val();
		status = $("#status_search").val();
		
		$(".alert-success").remove();
		$(".alert-danger").remove();
		
		//summaryTable.ajax.url("getGroupMasterData?groupName="+groupName+"&status="+status).draw();
		summaryTable.ajax.url("getGroupMasterData").draw();
	});
	
	$(document).on("click", "#clearSearch", function() {
		groupName = "";
		status = "";
		
		$("#groupName_search").val("");
		$("#status_search").val("");
		
		$(".alert-success").remove();
		$(".alert-danger").remove();
		
		//summaryTable.ajax.url("getGroupMasterData?groupName="+groupName+"&status="+status).draw();
		summaryTable.ajax.url("getGroupMasterData").draw();
	});	
} catch(e) {
	// alert(" Exception in redrawDataTable()     ======     " + e);
}

$(document).on("click", ".goSummary", function(){
	document.location = "invoiceSummary";
});


$(document).on("click", ".pdfDownload", function() {
	var invId = $("#invoiceId").val();
	if (invId != "" && parseInt(invId) > 0) {
		document.invoiceForm.action = urlPath+"invoice/invoicePdfDownload";
		document.invoiceForm.submit();
	}
});

$(document).on("click", "#gotoPrescription", function() {

	var pid = $("#prescriptionId").val();
	if (pid != null && pid != undefined && pid != "" && parseInt(pid) > 0) {
		document.invoiceForm.action = urlPath+"invoice/backInvoicePrescription";
		document.invoiceForm.submit();
	}

});
$(document).on("click", "#gotoOrder", function() {

	var pid = $("#orderId").val();
	if (pid != null && pid != undefined && pid != "" && parseInt(pid) > 0) {
		document.invoiceForm.action = urlPath+"invoice/backInvoiceOrder";
		document.invoiceForm.submit();
	}

});
function setPrescriptionId(prescriptionId)
{
	$("#prescriptionId").val(prescriptionId);
}

function setOrderId(orderId)
{
	$("#orderId").val(orderId);
}