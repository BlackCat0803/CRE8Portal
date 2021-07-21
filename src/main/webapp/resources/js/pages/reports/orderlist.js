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
var status="";
var group = $("#group").val();
var orderNo = "";
var rxNumber = "";
try	{
	physicianName = $("#physician").val();
	patientName = $("#patient").val();
	fromDate = $("#fromRegDate").val();
	toDate = $("#toRegDate").val();
	status = $("#status").val();
	orderNo = $("#orderNo").val();
	rxNumber = $("#rxNumber").val();
	
	var summaryTable = $("#orderListReport").DataTable({
		"processing" : true,
		"serverSide" : true,
		"dom":'<"top">rt<"bottom"pl><"clear">',
		"lengthMenu" : [ [ 10, 15, 25], [ 10, 15, 25] ],
		"pagingType": "full_numbers",
		"ajax" : {
			"url" : "getOrderListReportData",
			"data": function ( d ) {
				d.physicianName = physicianName;
                d.patientName = patientName;
                d.fromDate = fromDate;
                d.toDate = toDate;
                d.status = status;
                d.group = group;
                d.orderNo = orderNo;
                d.rxNumber = rxNumber;
            },
			"type" : "POST"
		},
		"oLanguage": {
	        "sEmptyTable":  "No records available"
	    },
		"columns" : [ 
		    { data : "orderId","orderable":false, "sClass":"left_align", "visible": false },
		    { data : "orderNumber","orderable":false },
			{ data : "orderDate","orderable":false },
			{ data : "rxNumbers","orderable":false},
			{ data : "physicianName","orderable":false  },
		    { data : "patientName","orderable":false  },
		    
		    { data : "medicationDispensed","orderable":false },
		    { data : "refillsFilled","orderable":false, "visible": false },
		    { data : "daysSupply","orderable":false, "visible": false},
		    { data : "refillRemaining","orderable":false, "visible": false },
		    { data : "status","orderable":false }
		],
		"columnDefs": [
		               { "width": "10px", "targets": [0] },
		               { "width": "70px", "targets": [1,2,3] },
		               { "width": "80px", "targets": [7,8,9] },
		               { "width": "100px", "targets": [4,5,10] },
		               { "width": "150px", "targets": [6] }
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
		status = $("#status").val();
		orderNo = $("#orderNo").val();
		rxNumber = $("#rxNumber").val();
		
		
		summaryTable.ajax.url("getOrderListReportData").draw();
	});

	$(document).on("click", "#clearSearch", function() {
		$("div.tooltip").remove();

		$("#fromRegDate").val("");
		$("#toRegDate").val("");
		$("#status").val("");
		$("#orderNo").val("");
		$("#rxNumber").val("");

		fromDate = "";
		toDate = "";
		status = "";
		orderNo="";
		rxNumber = "";
		if ($("#checkUserType").val() != "Physician" && $("#checkUserType").val() != "Physician Assistant") {
			$("#physician").val("");
			physicianName = "";
		}
		if ($("#checkUserType").val() != "Patient") {
			patientName = "";	
			$("#patient").val("");	
		}

		if (document.getElementById("select2-autoCompleterPhysicianId-container"))
			document.getElementById("select2-autoCompleterPhysicianId-container").innerHTML='<span class="select2-selection__placeholder">Select Physician</span>';
		if (document.getElementById("select2-autoCompleterPatientId-container"))
			document.getElementById("select2-autoCompleterPatientId-container").innerHTML='<span class="select2-selection__placeholder">Select Patient</span>';	
		
		summaryTable.ajax.url("getOrderListReportData").draw();
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
		//alert("No records available !")
		$.alert({
 		    title: '',
 		    content: 'No records available !',
 		});
	}else{
		if (isPdf)
			document.form.action = "orderReportPdfDownload";
		else
			document.form.action = "orderReportExcelDownload";	
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
	status = $("#status").val();
	orderNo = $("#orderNo").val();
	rxNumber = $("#rxNumber").val();
	
	summaryTable.ajax.url("getOrderListReportData").draw();
});

/**************** Report export to Excel *****************/
$(document).on("click", "#xlsReport", function() {
	isPdfDownload = false;
	isExcelDownload = true;
	
	physicianName = $("#physician").val();
	patientName = $("#patient").val();
	fromDate = $("#fromRegDate").val();
	toDate = $("#toRegDate").val();
	status = $("#status").val();
	orderNo = $("#orderNo").val();
	rxNumber = $("#rxNumber").val();
	
	summaryTable.ajax.url("getOrderListReportData").draw();
});
