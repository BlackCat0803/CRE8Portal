$("#fromDate, #toDate").datetimepicker({
	lang:'en',
	timepicker:false,
	format: 'm/d/Y'
});

var invoiceNo = "";
var fromDate = "";
var toDate = "";
var patientname = "";
var phyname = "";
var physicianId = "";
var patientId = "";
var rxNo = "";
var groupId = $("#groupId").val();

try	{
	if ($("#invoiceSummaryTable").length > 0) {
		invoiceNo = $("#invoiceNo").val();
		fromDate = $("#fromDate").val();
		toDate = $("#toDate").val();
		patientname = $("#patientname").val();
		phyname = $("#phyname").val();
		physicianId = $("#physicianId").val();
		patientId = $("#patientId").val();
		rxNo = $("#rxNo").val();
		
		// Invoice Summary works
		var summaryTable = $("#invoiceSummaryTable").DataTable({
			"processing" : true,
			"serverSide" : true,
			"dom":'<"top"f>rt<"bottom"pl><"clear">',
			"dom":'<"top">rt<"bottom"pl><"clear">',
			"lengthMenu" : [ [ 10, 15, 25], [ 10, 15, 25] ],
			"pagingType": "full_numbers",
			"ajax" : {
				"url" : "getPatientInvoiceSummaryData",
				"data": function ( d ) {
					d.invoiceNo = invoiceNo;
	                d.fromDate = fromDate;
	                d.toDate = toDate;
	                d.patientname = patientname;
	                d.phyname = phyname;
	                d.rxNo = rxNo;
	                d.physicianId = physicianId;
	                d.patientId=patientId;
	                d.groupId=groupId;
	      		},
				"type" : "POST"
			},
			"oLanguage": {
		        "sEmptyTable":  "No records available"
		    },
			"columns" : [ 
			    { data : "invoiceId", "visible": false },
			    { 
					"class"			:	"edit-control", 
				    "orderable"		:	false, 
				    "data"			:	null, 
				    "defaultContent":	""
				}, 
			    { data : "invoiceNo","orderable":false, "sClass":"left_align","width":"80px" },
			    { data : "invoiceDateStr","orderable":false, "sClass":"left_align" ,"width":"80px" },
			    { data : "rxNumber","orderable":false, "sClass":"left_align","width":"80px"  },
			    { data : "physicianName","orderable":false, "sClass":"left_align"   },
			    { data : "patientName","orderable":false, "sClass":"left_align"   }
			    
			],
			"columnDefs": [
			               { "width": "30px", "targets": [0,1] },
			               { "width": "50px", "targets": [2,3,4] },
			               { "width": "100px", "targets": [5,6] }
			],			
	        "fnDrawCallback": function( oSettings ) {
	        	var targetOffset = $(".nav_menu").offset().top;
		        $("html,body").animate({scrollTop: targetOffset}, 300);
	        },
			"initComplete": function(settings, json) {
				$('#invoiceSummaryTable_processing').hide();
				// heightAdjust();
			},
			"error" : "Error while processing data...."
		});

		// For Edit Record.
	    $("thead tr .edit-control").removeClass("edit-control");
		$('#invoiceSummaryTable tbody').on('click', 'tr td.edit-control', function() {
			var id=$(this).closest('tr').attr("id").replace("row_", "");
			$("#invoiceId").val(id);
			document.invoiceForm.action = "viewInvoice";
			document.invoiceForm.submit();
		});
		
		$(document).on("click", "#newSearch", function() {
			$("div.tooltip").remove();
			invoiceNo = $("#invoiceNo").val();
			fromDate = $("#fromDate").val();
			toDate = $("#toDate").val();
			patientname = $("#patientname").val();
			phyname = $("#phyname").val();
			physicianId = $("#physicianId").val();
			patientId = $("#patientId").val();
			rxNo = $("#rxNo").val();
			
			summaryTable.ajax.url("getPatientInvoiceSummaryData").draw();
		});
		
		$(document).on("click", "#clearSearch", function() {
			$("div.tooltip").remove();
			$("#invoiceNo").val("");
			$("#fromDate").val("");
			$("#toDate").val("");
			$("#patientname").val("");
			$("#phyname").val("");
			$("#rxNo").val("");
			
			invoiceNo = "";
			fromDate = "";
			toDate = "";
			patientname = "";
			phyname = "";
			rxNo = "";
			
			if (document.getElementById("select2-autoCompleterPhysicianId-container"))
				document.getElementById("select2-autoCompleterPhysicianId-container").innerHTML='<span class="select2-selection__placeholder">Select Physician</span>';
			if (document.getElementById("select2-autoCompleterPatientId-container"))
				document.getElementById("select2-autoCompleterPatientId-container").innerHTML='<span class="select2-selection__placeholder">Select Patient</span>';	

			summaryTable.ajax.url("getPatientInvoiceSummaryData").draw();
		});
	}
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

