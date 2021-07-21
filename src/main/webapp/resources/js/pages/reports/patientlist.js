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


/**************  Report Data display ***************/
var dttable = "";
var isPdfDownload = false;
var isExcelDownload = false;

var physician = "";
var group = "";
var status = "";
var fromDate = "";
var toDate = "";
try	{
	physician = $("#physician").val();
	group = $("#group").val();
	status = $("#status").val();
	fromDate = $("#fromRegDate").val();
	toDate = $("#toRegDate").val();

	dttable = $("#patientListReport").DataTable({
		"processing" : true,
		"serverSide" : true,
		"dom":'<"top">rt<"bottom"pl><"clear">',
		"lengthMenu" : [ [ 10, 15, 25], [ 10, 15, 25] ],
		"pagingType": "full_numbers",
		"ajax" : {
			"url" : "getPatientListReportData",
			"data": function ( d ) {
                d.physician = physician;
                d.group = group;
                d.status = status;
                d.fromDate = fromDate;
                d.toDate = toDate;
      		},
			"type" : "POST"
		},
		"oLanguage": {
	        "sEmptyTable":  "No records available"
	    },
		"columns" : [ 
		    { data : "patientId","orderable":false, "sClass":"left_align", "visible": false },
			{ data : "patientName","orderable":false, "sClass":"left_align" },
			{ data : "dateRegistered","orderable":false, "sClass":"left_align"  },
			{ data : "groupName","orderable":false, "sClass":"left_align"},
			{ data : "physicianName","orderable":false, "sClass":"left_align", "visible": false },
		    { data : "phone","orderable":false, "sClass":"left_align"},
			{ data : "email","orderable":false, "sClass":"left_align"},
			{ data : "addressInfo","orderable":false, "sClass":"left_align"},
		    { data : "ssn","orderable":false, "sClass":"left_align"},
		    { data : "driversLicense","orderable":false, "sClass":"left_align" },
		    { data : "alternateId","orderable":false, "sClass":"left_align" },
		    { data : "alternateIdTypeText","orderable":false, "sClass":"left_align" },
		    { data : "status","orderable":false, "sClass":"left_align"}
	    ],
		"columnDefs": [
		               { "width": "10px", "targets": [0,4] },
		               { "width": "150px", "targets": [1,6] },
		               { "width": "100px", "targets": [3,10,11,12] },
		               { "width": "120px", "targets": [2,5] },
		               { "width": "80px", "targets": [7,8,9] }
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
		physician = $("#physician").val();
		group = $("#group").val();
		status = $("#status").val();
		fromDate = $("#fromRegDate").val();
		toDate = $("#toRegDate").val();
		dttable.ajax.url("getPatientListReportData").draw();
	});
	
	$(document).on("click", "#clearSearch", function() {
		$("div.tooltip").remove();

		$("#status").val("");
		$("#fromRegDate").val("");
		$("#toRegDate").val("");
		
		if ($("#checkUserType").val() == "Admin" || $("#checkUserType").val() == "Super Admin") {
			$("#group").val("");
			$("#physician").val("");

			physician = "";
			group = "";
		}
		if ($("#checkUserType").val() == "Group Director") {
			$("#physician").val("");
			physician = "";
		}
		status = "";
		fromDate = "";
		toDate = "";
		
		if (document.getElementById("select2-autoCompleterPhysicianId-container"))
			document.getElementById("select2-autoCompleterPhysicianId-container").innerHTML='<span class="select2-selection__placeholder">Select Physician</span>';

		dttable.ajax.url("getPatientListReportData").draw();
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
		// alert("No records available !")
		$.alert({
 		    title: '',
 		    content: 'No records available !',
 		});
	}else{
		if (isPdf)
			document.form.action = "patientReportPdfDownload";
		else
			document.form.action = "patientReportExcelDownload";	
		document.form.submit();
	}
} 

/**************** Report export to PDF *****************/
$(document).on("click", "#pdfReport", function() {
	isPdfDownload = true;
	isExcelDownload = false;
	
	physician = $("#physician").val();
	group = $("#group").val();
	status = $("#status").val();
	fromDate = $("#fromRegDate").val();
	toDate = $("#toRegDate").val();
	dttable.ajax.url("getPatientListReportData").draw();
});

/**************** Report export to Excel *****************/
$(document).on("click", "#xlsReport", function() {
	isPdfDownload = false;
	isExcelDownload = true;
	
	physician = $("#physician").val();
	group = $("#group").val();
	status = $("#status").val();
	fromDate = $("#fromRegDate").val();
	toDate = $("#toRegDate").val();
	dttable.ajax.url("getPatientListReportData").draw();
});
