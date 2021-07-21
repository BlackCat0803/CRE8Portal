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

/***************  Clinic Data for autocomplete fetch ********************/ 
try {
	var clinicNameVar = $("#clinic").val();
	if (clinicNameVar == null || clinicNameVar == "") {
		clinicNameVar = "Select Clinic";
	}
	var cresults = [];
	$('#autoCompleterClinicId').select2({
		placeholder : clinicNameVar,
		minimumInputLength : 0,
		ajax : {
			url : urlPath + 'getAutoCompleteClinicList',
			dataType : 'json',
			data : function(params) {
				return {
					term : $.trim(params.term),
	            	  formId:0
				};
			},
			processResults : function(data) {
				cresults = [];
				data.forEach(function makeResults(element) {
					cresults.push({
						id : element.clinicName,
						text : element.clinicName
					});
				});
				return {
					results : cresults
				};
			},
			cache : true
		}
	}).on('change', function(e) {
		$("#clinic").val(this.value)
	});

} catch (e) {
	// alert(e)
}

/**************  Report Data display ***************/
var isPdfDownload = false;
var isExcelDownload = false;

var physician = "";
var clinic = "";
var group = "";
var status = "";
var dea = "";
var fromDate = "";
var toDate = "";
try	{
	// physician = $("#physician").val();
	clinic = $("#clinic").val();
	group = $("#group").val();
	status = $("#status").val();
	fromDate = $("#fromRegDate").val();
	toDate = $("#toRegDate").val();
	dea = $("#deaSearch").val();

	var dttable = $("#physicianListReport").DataTable({
		"processing" : true,
		"serverSide" : true,
		"dom":'<"top">rt<"bottom"pl><"clear">',
		"lengthMenu" : [ [ 10, 15, 25], [ 10, 15, 25] ],
		"pagingType": "full_numbers",
		"ajax" : {
			"url" : "getPhysicianListReportData",
			"data": function ( d ) {
                d.physician = physician;
                d.clinic = clinic;
                d.group = group;
                d.status = status;
                d.fromDate = fromDate;
                d.toDate = toDate;
                d.dea = dea;
      		},
			"type" : "POST"
		},
		"oLanguage": {
	        "sEmptyTable":  "No records available"
	    },
		"columns" : [ 
		    { data : "physicianId","orderable":false, "sClass":"left_align", "visible": false },
		    { data : "physicianName","orderable":false, "sClass":"left_align" },
			{ data : "groupName","orderable":false, "sClass":"left_align"},
			{ data : "clinicName","orderable":false, "sClass":"left_align"},
			{ data : "dea","orderable":false, "sClass":"left_align" },
		    { data : "phone","orderable":false, "sClass":"left_align", "visible": false},
		    { data : "mobile","orderable":false, "sClass":"left_align", "visible": false},
			{ data : "email","orderable":false, "sClass":"left_align", "visible": false},
		    { data : "city","orderable":false, "sClass":"left_align", "visible": false},
		    { data : "state","orderable":false, "sClass":"left_align", "visible": false},
		    { data : "dateRegistrated","orderable":false, "sClass":"left_align"  },
		    { data : "status","orderable":false, "sClass":"left_align" }
		],
		"columnDefs": [
		               { "width": "10px", "targets": [0] },
		               { "width": "140px", "targets": [1,2,3] },
		               { "width": "100px", "targets": [4,5,6,8] },
		               { "width": "70px", "targets": [9,10] },
		               { "width": "160px", "targets": [7] },
		               { "width": "65px", "targets": [11] }
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
			$('#patientListReport_processing').hide();
		},
		"error" : "Error while processing data...."
	});
	
	$(document).on("click", "#newSearch", function() {
		$("div.tooltip").remove();
		//physician = $("#physician").val();
		clinic = $("#clinic").val();
		group = $("#group").val();
		status = $("#status").val();
		fromDate = $("#fromRegDate").val();
		toDate = $("#toRegDate").val();
		dea = $("#deaSearch").val();
		dttable.ajax.url("getPhysicianListReportData").draw();
	});
	
	$(document).on("click", "#clearSearch", function() {
		$("div.tooltip").remove();
		
		$("#status").val("");
		$("#fromRegDate").val("");
		$("#toRegDate").val("");
		$("#deaSearch").val("");
		if ($("#checkUserType").val() != "Group Director") {
			$("#group").val("");
		}
		if ($("#checkUserType").val() != "Physician" && $("#checkUserType").val() != "Physician Assistant") {
			//$("#physician").val("");
			$("#clinic").val("");
		}
		//physician = $("#physician").val();
		clinic = $("#clinic").val();
		group = $("#group").val();
		status = $("#status").val();
		fromDate = $("#fromRegDate").val();
		toDate = $("#toRegDate").val();
		dea = $("#deaSearch").val();
		
		// if (document.getElementById("select2-autoCompleterPhysicianId-container"))
		//	document.getElementById("select2-autoCompleterPhysicianId-container").innerHTML='<span class="select2-selection__placeholder">Select Physician</span>';
		if (document.getElementById("select2-autoCompleterClinicId-container"))
			document.getElementById("select2-autoCompleterClinicId-container").innerHTML='<span class="select2-selection__placeholder">Select Clinic</span>';

		dttable.ajax.url("getPhysicianListReportData").draw();
	});
} catch(e) {
	// alert(" Exception in redrawDataTable()     ======     " + e);
}

function downloadPdfFile(isPdf) {
	isPdfDownload = false;
	isExcelDownload = false;
	$("div.tooltip").remove();
	
	var table = dttable;
	var data = table.rows().data();	
	if(data.length==0){
		//alert("No records available !")
		$.alert({
 		    title: '',
 		    content: 'No records available !',
 		});
	}else{
		if (isPdf)
			document.form.action = "physicianReportPdfDownload";
		else
			document.form.action = "physicianReportExcelDownload";	
		document.form.submit();
	}
}

/**************** Report export to PDF *****************/
$(document).on("click", "#pdfReport", function() {
	isPdfDownload = true;
	isExcelDownload = false;
	
	clinic = $("#clinic").val();
	group = $("#group").val();
	status = $("#status").val();
	fromDate = $("#fromRegDate").val();
	toDate = $("#toRegDate").val();
	dea = $("#deaSearch").val();
	dttable.ajax.url("getPhysicianListReportData").draw();
});

/**************** Report export to Excel *****************/
$(document).on("click", "#xlsReport", function() {
	isPdfDownload = false;
	isExcelDownload = true;
	
	clinic = $("#clinic").val();
	group = $("#group").val();
	status = $("#status").val();
	fromDate = $("#fromRegDate").val();
	toDate = $("#toRegDate").val();
	dea = $("#deaSearch").val();
	dttable.ajax.url("getPhysicianListReportData").draw();
});