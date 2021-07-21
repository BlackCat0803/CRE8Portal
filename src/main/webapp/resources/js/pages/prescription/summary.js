var rdate1 = $("#prescriptionDate");
var pToDate = $("#prescriptionToDate");

$(rdate1).datetimepicker({
	lang:'en',
	timepicker:false,
	format: 'm/d/Y'
});
$(pToDate).datetimepicker({
	lang:'en',
	timepicker:false,
	format: 'm/d/Y'
});

var physicianName = "";
var patientName = "";
var presriptionDate = "";
var presriptionToDate = "";
var userId = "";
var userType = "";
var prescriptionNo = "";
var rxNo="";
var rxPrescriptionNo="";
var groupId=0;

try	{
	physicianName = $("#physicianName").val();
	patientName = $("#patientName").val();
	presriptionDate = $("#prescriptionDate").val();
	presriptionToDate = $("#prescriptionToDate").val();
	userId = $("#userId").val();
	userType = $("#userType").val();
	prescriptionNo = $("#prescriptionNo").val();
	rxNo = $("#rxNo").val();
	rxPrescriptionNo = $("#rxPrescriptionNo").val();
	groupId=$("#groupId").val();
	
	
	var summaryTable = $("#summaryTable").DataTable({
		"processing" : true,
		"serverSide" : true,
		"dom":'<"top">rt<"bottom"pl><"clear">',
		"lengthMenu" : [ [ 10, 15, 25], [ 10, 15, 25] ],
		"pagingType": "full_numbers",
		"ajax" : {
			"url" : "getPrescriptionData",
			"data": function ( d ) {
				d.physicianName = physicianName;
                d.patientName = patientName;
                d.presriptionDate = presriptionDate;
                d.presriptionToDate = presriptionToDate;
                d.userId = userId;
                d.userType = userType;
                d.prescriptionNo = prescriptionNo;
                d.rxNo = rxNo;
                d.rxPrescriptionNo=rxPrescriptionNo;
                d.groupId=groupId;
            },
			"type" : "POST"
		},
		"oLanguage": {
	        "sEmptyTable":  "No records available"
	    },
		"columns" : [ 
		    { data : "prescriptionId", "visible": false },
			 {
				 "data": null, "orderable": false,"defaultContent": "","render": function (data, type, row) {
					   	return '<img src="'+urlPath+'/resources/images/edit-3.png" style="cursor:pointer" onclick="callEditPrescription('+data.prescriptionId+')"/> ';
					  
			 } },
			 {
				 "data": null, "orderable": false,"defaultContent": "","render": function (data, type, row) {
					 //alert(data.esignedPDF)
					 if(data.esignedPDF==true)
					   	return '<img src="'+urlPath+'/resources/images/pdf-icon-2.png" style="cursor:pointer;height:30px" title="PDF Download"  onclick="callReportExport('+data.prescriptionId+')"/>';
					 else
						return '';
					  
			 } },
			 {
				 "data": null, "orderable": false,"defaultContent": "","render": function (data, type, row) {
					 //alert(data.csesignedPDF)
					 if(data.csesignedPDF==true)
					   	return '<img src="'+urlPath+'/resources/images/pdf-icon-2.png" style="cursor:pointer;height:30px" title="Controlled Substance PDF Download" onclick="callCSReportExport('+data.prescriptionId+')"/>';
					 else
						return '';
					  
			 } },
			{ data : "prescriptionOrderNumber","orderable":false, "sClass":"left_align"  },
			{ data : "tranPrescriptionNumber","orderable":false, "sClass":"left_align" },
			{ data : "tranRXNumber","orderable":false, "sClass":"left_align" },
			
			{ data : "prescriptionDateStr","orderable":false, "sClass":"left_align"},
			{ data : "groupName","orderable":false, "sClass":"left_align"},
			{ data : "physicianName","orderable":false, "sClass":"left_align" },
		    { data : "patientName","orderable":false, "sClass":"left_align"  }, 
		    { data : "esignedPDF","orderable":false, "sClass":"left_align", "visible":false},
		    { data : "csesignedPDF","orderable":false, "sClass":"left_align", "visible":false}
		],
		"columnDefs": [
		               { "width": "25px", "targets": [1] },
		               { "width": "10px", "targets": [2,3] },
		               { "width": "90px", "targets": [4,5,8] },
		               { "width": "120px", "targets": [7] },
		               { "width": "50px", "targets": [6, 10,11] },
		               { "width": "100px", "targets": [9] }
		],
        "fnDrawCallback": function( oSettings ) {
        	var targetOffset = $(".nav_menu").offset().top;
	        $("html,body").animate({scrollTop: targetOffset}, 300);
        },
		"initComplete": function(settings, json) {
			$('#summaryTable_processing').hide();
			// heightAdjust();
		},
		"error" : "Error while processing data...."
	});
	
	// For Edit Record.
  /*  $("thead tr .edit-control").removeClass("edit-control");
	$('#summaryTable tbody').on('click', 'tr td.edit-control', function() {
		var id=$(this).closest('tr').attr("id").replace("row_", "");
		$("#prescriptionId").val(id);
		document.editPage.submit();
	});*/
	
	
	// For Print Record.
	/*$("thead tr .pdf-control1").removeClass("pdf-control1");
	$('#summaryTable tbody').on( 'click', 'tr td.pdf-control1', function () 
	{
		var idx=$(this).closest('tr').attr("id").replace("row_", "");
	  	
    	callReportExport(idx);
    });*/
	 function callEditPrescription(idx)
	    {			
		 
		/* 	var hrefLink = urlPath+"prescription/prescriptionPrintPdfDownload?prescriptionId="+idx;
		 	window.location.href=hrefLink;*/
		 	
		 	$("#prescriptionId").val(idx);
		 	document.editPage.action="editPrescription";
			document.editPage.submit();
			
	
	   }
	 function callReportExport(idx)
	    {			
		 
		/* 	var hrefLink = urlPath+"prescription/prescriptionPrintPdfDownload?prescriptionId="+idx;
		 	window.location.href=hrefLink;*/
		 	
		 	$("#prescriptionId").val(idx);
		 	document.editPage.action="prescriptionPrintPdfDownload";
			document.editPage.submit();
			
	
	   }
	 function callCSReportExport(idx)
	    {			
		 
		/* 	var hrefLink = urlPath+"prescription/prescriptionPrintPdfDownload?prescriptionId="+idx;
		 	window.location.href=hrefLink;*/
		 	
		 	$("#prescriptionId").val(idx);
		 	document.editPage.action="csprescriptionPrintPdfDownload";
			document.editPage.submit();
			
	
	   }
	 
	$(document).on("click", "#newSearch", function() {
		$("div.tooltip").remove();
		physicianName = $("#physicianName").val();
		patientName = $("#patientName").val();
		presriptionDate = $("#prescriptionDate").val();
		presriptionToDate = $("#prescriptionToDate").val();
		prescriptionNo = $("#prescriptionNo").val();
		rxNo = $("#rxNo").val();
		rxPrescriptionNo= $("#rxPrescriptionNo").val();
		groupId=$("#groupId").val();
		
		summaryTable.ajax.url("getPrescriptionData").draw();
	});

	$(document).on("click", "#clearSearch", function() {
		$("div.tooltip").remove();
		physicianName = "";
		patientName = "";
		presriptionDate = "";
		presriptionToDate = "";
		prescriptionNo  = "";
		rxNo = "";
		rxPrescriptionNo="";
		if ($("#checkUserType").val() != "Group Director" && $("#checkUserType").val() != "Physician" &&  $("#checkUserType").val() != "Physician Assistant") {
			$("#groupId").val("0");
			groupId = "0";
		}
		
		$("#physicianName").val("");
		$("#patientName").val("");
		$("#prescriptionDate").val("");
		$("#prescriptionToDate").val("");
		$("#prescriptionNo").val("");
		$("#rxNo").val("");
		$("#rxPrescriptionNo").val("");
		
		if (document.getElementById("select2-autoCompleterPhysicianId-container"))
			document.getElementById("select2-autoCompleterPhysicianId-container").innerHTML='<span class="select2-selection__placeholder">Select Physician</span>';
		if (document.getElementById("select2-autoCompleterPatientId-container"))
			document.getElementById("select2-autoCompleterPatientId-container").innerHTML='<span class="select2-selection__placeholder">Select Patient</span>';	
		
		summaryTable.ajax.url("getPrescriptionData").draw();
	});
} catch(e) {
	// alert(" Exception in redrawDataTable()     ======     " + e);
}

$(document).on("click", "#newPrescription", function() {
	location.href="prescriptionView";
});


try {
	var phynameVar = $("#physicianName").val();
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
		$("#physicianName").val(this.value)
	});

} catch (e) {
	// alert(e)
}

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
		$("#patientName").val(this.value)
	});

} catch (e) {
	// alert(e)
}
