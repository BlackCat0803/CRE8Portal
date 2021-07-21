var fromdate = $("#fromRegDate");
var todate = $("#toRegDate");

$(fromdate).datetimepicker({
	lang:'en',
	timepicker:false,
	format: 'm/d/Y'
});

$(todate).datetimepicker({
	lang:'en',
	timepicker:false,
	format: 'm/d/Y'
});


/*************************  Physician name for autocomplete fetch *************************/
try {
	var physicianNameVar = $("#physician").val();
	if (physicianNameVar == null || physicianNameVar == "") {
		physicianNameVar = "Select Physician";
	}
	var results = [];
	$('#autoCompleterPhysicianId').select2({
		placeholder : physicianNameVar,
		minimumInputLength : 0,
		ajax : {
			url : urlPath + 'getAllAutoCompletePhysiciansList',
			dataType : 'json',
			data : function(params) {
				return {
					term : $.trim(params.term)
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
		$("#physician").val(this.value)
	});
} catch (e) {
	// alert(e)
}


/*********************** Patient List autocomplete fetch  *************************/
try {
	var patientnameVar = $("#patientName").val();
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
		$("#patient").val(this.value)
	});

} catch (e) {
	// alert(e)
}


/*********************** Summary List Fetch data ******************/
var isPdfDownload = false;
var isExcelDownload = false;

var physicianName = "";
var patientName = "";
var fromDate = "";
var toDate = "";
var group = $("#group").val();
var invoiceNo = "";
var rxNumber = "";

try	{
	physicianName = $("#physician").val();
	patientName = $("#patient").val();
	fromDate = $("#fromRegDate").val();
	toDate = $("#toRegDate").val();
	invoiceNo = $("#invoiceNo").val();
	rxNumber = $("#rxNumber").val();
	
	var summaryTable = $("#invoiceListReport").DataTable({
		"processing" : true,
		"serverSide" : true,
		"dom":'<"top">rt<"bottom"pl><"clear">',
		"lengthMenu" : [ [ 10, 15, 25], [ 10, 15, 25] ],
		"pagingType": "full_numbers",
		"ajax" : {
			"url" : "getInvoiceListReportData",
			"data": function ( d ) {
				d.physicianName = physicianName;
                d.patientName = patientName;
                d.fromDate = fromDate;
                d.toDate = toDate;
                d.group = group;
                d.invoiceNo = invoiceNo;
                d.rxNumber = rxNumber;
            },
			"type" : "POST"
		},
		"oLanguage": {
	        "sEmptyTable":  "No records available"
	    },
		"columns" : [ 
		    { data : "invoiceId","orderable":false, "sClass":"left_align", "visible": false },
		    { data : "invoiceNo","orderable":false },
			{ data : "invoiceDateStr","orderable":false },
			{ data : "rxNumber","orderable":false},
			{ data : "physicianName","orderable":false  },
		    { data : "patientName","orderable":false  },
		    { data : "itemInvoiced","orderable":false },
		    {
		    	"data": null, "orderable": false, "sClass":"right_text ", "visible": false, "defaultContent": "","render": function (data, type, row) {
		    		return parseInt(data.quantity);
		    	}
		    },
		    { data : "paymentstatus", "orderable":false, "visible": false },
		    { data : "paymenttype", "orderable":false, "visible": false },
		    {
		    	"data": null, "orderable": false, "sClass":"right_text ", "visible": false, "defaultContent": "","render": function (data, type, row) {
		    		return parseInt(data.subtotal).toFixed(2);
		    	}
		    },
		    {
		    	"data": null, "orderable": false, "sClass":"right_text ", "visible": false, "defaultContent": "","render": function (data, type, row) {
		    		return parseInt(data.tax).toFixed(2);
		    	}
		    },
		    {
		    	"data": null, "orderable": false, "sClass":"right_text ", "visible": false, "defaultContent": "","render": function (data, type, row) {
		    		return parseInt(data.total).toFixed(2);
		    	}
		    }
		],
		"columnDefs": [
		    { "width": "10px", "targets": [0] },
		    { "width": "55px", "targets": [1,2,3,7,11,12] },
		    { "width": "80px", "targets": [8,9,10] },
		    { "width": "120px", "targets": [4,5] },
		    { "width": "200px", "targets": [6] }
		],
        "fnDrawCallback": function( oSettings ) {
        	var targetOffset = $(".nav_menu").offset().top;
	        $("html,body").animate({scrollTop: targetOffset}, 300);
			
	        if (isPdfDownload)
				downloadPdfFile(true);
			if (isExcelDownload)
				downloadPdfFile(false);	
		},
		"initComplete": function(settings, json) {
			$('#summaryTable_processing').hide();
			// heightAdjust();
		},
		"error" : "Error while processing data...."
	});
	
	$(document).on("click", "#newSearch", function() {
		$("div.tooltip").remove();
		physicianName = $("#physician").val();
		patientName = $("#patient").val();
		fromDate = $("#fromRegDate").val();
		toDate = $("#toRegDate").val();
		invoiceNo = $("#invoiceNo").val();
		rxNumber = $("#rxNumber").val();
		
		summaryTable.ajax.url("getInvoiceListReportData").draw();
	});

	$(document).on("click", "#clearSearch", function() {
		$("div.tooltip").remove();
		
		$("#patient").val("");
		$("#fromRegDate").val("");
		$("#toRegDate").val("");
		$("#invoiceNo").val("");
		$("#rxNumber").val("");
		
		patientName = "";
		fromDate = "";
		toDate = "";
		status = "";
		invoiceNo="";
		rxNumber = "";
		if ($("#checkUserType").val() != "Physician" && $("#checkUserType").val() != "Physician Assistant") {
			$("#physician").val("");
			physicianName = "";
		}

		if (document.getElementById("select2-autoCompleterPhysicianId-container"))
			document.getElementById("select2-autoCompleterPhysicianId-container").innerHTML='<span class="select2-selection__placeholder">Select Physician</span>';
		if (document.getElementById("select2-autoCompleterPatientId-container"))
			document.getElementById("select2-autoCompleterPatientId-container").innerHTML='<span class="select2-selection__placeholder">Select Patient</span>';	
		
		summaryTable.ajax.url("getInvoiceListReportData").draw();
	});
} catch(e) {
	// alert(" Exception in redrawDataTable()     ======     " + e);
}

function downloadPdfFile(isPdf) {
	isPdfDownload = false;
	isExcelDownload = false;
	$("div.tooltip").remove();
	
	var table = summaryTable;
	var data = table.rows().data();	
	if(data.length==0){
		// alert("No records available !")
		$.alert({
 		    title: '',
 		    content: 'No records available !',
 		});
	}else{
		if (isPdf)
			document.form.action = "invoiceReportPdfDownload";
		else
			document.form.action = "invoiceReportExcelDownload";	
		document.form.submit();
	}
}

/**************** Report export to PDF *****************/
$(document).on("click", "#pdfReport", function() {
	isPdfDownload = true;
	isExcelDownload = false;
	
	physicianName = $("#physician").val();
	patientName = $("#patient").val();
	fromDate = $("#fromRegDate").val();
	toDate = $("#toRegDate").val();
	invoiceNo = $("#invoiceNo").val();
	rxNumber = $("#rxNumber").val();
	
	summaryTable.ajax.url("getInvoiceListReportData").draw();
});

/**************** Report export to Excel *****************/
$(document).on("click", "#xlsReport", function() {
	isPdfDownload = false;
	isExcelDownload = true;
	
	physicianName = $("#physician").val();
	patientName = $("#patient").val();
	fromDate = $("#fromRegDate").val();
	toDate = $("#toRegDate").val();
	invoiceNo = $("#invoiceNo").val();
	rxNumber = $("#rxNumber").val();
	
	summaryTable.ajax.url("getInvoiceListReportData").draw();
});