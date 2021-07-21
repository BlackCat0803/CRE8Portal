$("#fromDate, #toDate").datetimepicker({
	lang:'en',
	timepicker:false,
	format: 'm/d/Y'
});

var orderNo = "";
var phyname = "";
var patientname = "";
var fromDate = "";
var toDate = "";
var status = "";
var physicianId = "";
var patientId = "";
var rxNo = "";
var groupId = $("#groupId").val();

try	{
	orderNo = $("#orderNo").val();
	phyname = $("#phyname").val();
	patientname = $("#patientname").val();
	fromDate = $("#fromDate").val();
	toDate = $("#toDate").val();
	status = $("#status").val();
	physicianId = $("#physicianId").val();
	patientId = $("#patientId").val();
	rxNo = $("#rxNo").val();

	var summaryTable = $("#orderSummaryTable").DataTable({
		"processing" : true,
		"serverSide" : true,
		"dom":'<"top">rt<"bottom"pl><"clear">',
		"lengthMenu" : [ [ 10, 15, 25], [ 10, 15, 25] ],
		"pagingType": "full_numbers",
		"ajax" : {
			"url" : "getPatientOrderSummaryData",
			"data": function ( d ) {
				d.orderNo = orderNo;
				d.phyname = phyname;
                d.patientname = patientname;
                d.fromDate = fromDate;
                d.toDate = toDate;
                d.status = status;
                d.physicianId = physicianId;
                d.patientId=patientId;
                d.rxNo = rxNo;
                d.groupId = groupId;
      		},
			"type" : "POST"
		},
		"oLanguage": {
	        "sEmptyTable":  "No records available"
	    },
		"columns" : [ 
		    { data : "orderId", "visible": false },
		    { 
				"class"			:	"edit-control", 
			    "orderable"		:	false, 
			    "data"			:	null, 
			    "defaultContent":	""
			}, 
			{ data : "orderNumber","orderable":false, "sClass":"left_align" },
			{ data : "orderDate","orderable":false, "sClass":"left_align" },
			{ data : "rxNumbers","orderable":false, "sClass":"left_align" },
			{ data : "orderNumbers","orderable":false, "sClass":"left_align", "visible": false },
			{ data : "physicianName","orderable":false, "sClass":"left_align" },
			{ data : "patientName","orderable":false, "sClass":"left_align" },
		    { data : "rxStatuses","orderable":false, "sClass":"left_align" }
		],
		"columnDefs": [
		               { "width": "50px", "targets": [1,4] },
		               { "width": "60px", "targets": [2] },
		               { "width": "70px", "targets": [3] },
		               { "width": "150px", "targets": [8] },
		               { "width": "100px", "targets": [6,7] }
		],
        "fnDrawCallback": function( oSettings ) {
        	var targetOffset = $(".nav_menu").offset().top;
	        $("html,body").animate({scrollTop: targetOffset}, 300);
        },
		"initComplete": function(settings, json) {
			$('#orderSummaryTable_processing').hide();
			// heightAdjust();
		},
		"error" : "Error while processing data...."
	});

	// For View Record.
    $("thead tr .edit-control").removeClass("edit-control");
	$('#orderSummaryTable tbody').on('click', 'tr td.edit-control', function() {
		var id=$(this).closest('tr').attr("id").replace("row_", "");
		$("#orderId").val(id);
		document.form.action="viewOrders";
		document.form.submit();
	});
	
	$(document).on("click", "#newSearch", function() {
		$("div.tooltip").remove();
		orderNo = $("#orderNo").val();
		phyname = $("#phyname").val();
		patientname = $("#patientname").val();
		fromDate = $("#fromDate").val();
		toDate = $("#toDate").val();
		status = $("#status").val();
		physicianId = $("#physicianId").val();
		patientId = $("#patientId").val();
		rxNo = $("#rxNo").val();
		
		summaryTable.ajax.url("getPatientOrderSummaryData").draw();
	});

	$(document).on("click", "#clearSearch", function() {
		$("div.tooltip").remove();
		$("#orderNo").val("");
		$("#phyname").val("");
		$("#patientname").val("");
		$("#fromDate").val("");
		$("#toDate").val("");
		$("#status").val("");
		$("#rxNo").val("");

		orderNo = "";
		phyname = "";
		patientname = "";
		fromDate = "";
		toDate = "";
		status = "";
		rxNo = "";
		
		if (document.getElementById("select2-autoCompleterPhysicianId-container"))
			document.getElementById("select2-autoCompleterPhysicianId-container").innerHTML='<span class="select2-selection__placeholder">Select Physician</span>';
		if (document.getElementById("select2-autoCompleterPatientId-container"))
			document.getElementById("select2-autoCompleterPatientId-container").innerHTML='<span class="select2-selection__placeholder">Select Patient</span>';	

		summaryTable.ajax.url("getPatientOrderSummaryData").draw();
	});
} catch(e) {
	// alert(" Exception in redrawDataTable()     ======     " + e);
}

try {
	var phynameVar = $("#phyname").val();
	if (phynameVar == null || phynameVar == "") {
		phynameVar = "Select Physician";
	}
	var results = [];
	$('#autoCompleterPhysicianId').select2({
		placeholder : phynameVar,
		minimumInputLength : 0,
		ajax : {
			url : urlPath + 'getAutoCompletePhysiciansSummaryList',
			dataType : 'json',
			data : function(params) {
				return {
					term : $.trim(params.term),
	            	  formId:0
				};
			},
			processResults : function(data) {
				results = [];
				data.forEach(function makeResults(element) {
					results.push({
						id : element.physicianName,
						text : element.physicianName
					});
				});
				return {
					results : results
				};
			},
			cache : true
		}
	}).on('change', function(e) {
		//alert(this.value)
		$("#phyname").val(this.value)
	});

} catch (e) {
	// alert(e)
}

try {
	var patientnameVar = $("#patientname").val();
	if (patientnameVar == null || patientnameVar == "") {
		patientnameVar = "Select Patient";
	}
	var aresults = [];
	$('#autoCompleterPatientId').select2({
		placeholder : patientnameVar,
		minimumInputLength : 0,
		ajax : {
			url : urlPath + 'getAllAutoCompletePatientList',
			dataType : 'json',
			data : function(params) {
				return {
					term : $.trim(params.term)
				};
			},
			processResults : function(data) {
				aresults = [];
				data.forEach(function makeResults(element) {
					aresults.push({
						id : element.patientName,
						text : element.patientName
					});
				});
				return {
					results : aresults
				};
			},
			cache : true
		}
	}).on('change', function(e) {
		$("#patientname").val(this.value)
	});

} catch (e) {
	// alert(e)
}

