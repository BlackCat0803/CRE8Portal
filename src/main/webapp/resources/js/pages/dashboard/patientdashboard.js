var physicianName = "";
var prescriptionPatientName = "";
var presriptionDate = "";
var presriptionToDate = "";
var userId = $("#userId").val();
var userType = $("#userType").val();
var groupId = "0";
var prescriptionNo="";
var prescriptionstatus = "";

var pdate1 = $("#prescriptionDate");
var pToDate = $("#prescriptionToDate");

$(pdate1).datetimepicker({
	lang:'en',
	timepicker:false,
	format: 'm/d/Y'
});
$(pToDate).datetimepicker({
	lang:'en',
	timepicker:false,
	format: 'm/d/Y'
});

try	{
	var prescriptionSummaryTable = $("#prescriptionSummaryTable").DataTable({
		"processing" : true,
		responsive:true,
		"serverSide" : true,
		"dom":'<"top">rt<"bottom"pl><"clear">',
		"lengthMenu" : [ [ 5, 10, 15], [  5, 10, 15] ],
		/*"pagingType": "full_numbers",*/
		// numbers --> only nos, simple --> prev/next btns, simple_numbers --> prev/next btns & nos,
		// full --> prev/next/first/last btns, full_numbers --> prev/next/first/last btns & nos,
		// first_last_numbers ---> first/last btns and nos.
		"pagingType": "numbers",  
		"ajax" : {
			"url" : "getPhysicianPrescriptionSummaryData",
			"data": function ( d ) {
				d.physicianName = physicianName;
                d.patientName = prescriptionPatientName;
                d.presriptionDate = presriptionDate;
                d.presriptionToDate = presriptionToDate;
                d.userId = userId;
                d.userType = userType;
                d.groupId = groupId;
                d.prescriptionNo = prescriptionNo;
                d.prescriptionstatus = prescriptionstatus;
			},
			"type" : "POST"
		},
		"oLanguage": {
	        "sEmptyTable":  "No records available"
	    },
		"columns" : [ 
		    { data : "prescriptionId", "visible": false },
		    { // render
		    	"orderable":false, "sClass":"left_align","data": function (data, type, row, meta) {
		    		return '<a onclick="editPrescriptionRecord('+data.prescriptionId+')" style="color:blue;font-weight:bold;cursor:pointer">' + data.prescriptionOrderNumber + '</a>';
                }, 
                "width": "150px"
            },
            { data : "prescriptionDateStr", "width": "150px" },
            { data : "physicianName","orderable":false, "sClass":"left_align", "width": "200px" },
		    { data : "patientName","orderable":false, "sClass":"left_align", "visible": false },
			{ data : "tranRXNumber","orderable":false, "sClass":"left_align"}
		],
		"initComplete": function(settings, json) {
			$('#prescriptionSummaryTable_processing').hide();
			// heightAdjust();
		},
		"error" : "Error while processing data...."
	});	
			
			
	// Array to track the ids of the details displayed rows
    var detailRows = [];
 
    $('#prescriptionSummaryTable tbody').on( 'click', 'tr td.details-control', function () {
        var tr = $(this).closest('tr');
        var row = prescriptionSummaryTable.row( tr );
        var idx = $.inArray( tr.attr('id'), detailRows );
 
        if ( row.child.isShown() ) {
            tr.removeClass( 'details' );
            row.child.hide();
 
            // Remove from the 'open' array
            detailRows.splice( idx, 1 );
        }
        else {
            tr.addClass( 'details' );
            row.child( formatPrescription( row.data() ) ).show();
 
            // Add to the 'open' array
            if ( idx === -1 ) {
                detailRows.push( tr.attr('id') );
            }
        }
    } );
	 
    // On each draw, loop over the `detailRows` array and show any child rows
    prescriptionSummaryTable.on( 'draw', function () {
        $.each( detailRows, function ( i, id ) {
            $('#'+id+' td.details-control').trigger( 'click' );
        } );
    });

    function formatPrescription ( d ) {
    	return '<table cellpadding="0" cellspacing="0" style="padding:0px; border-collapse: collapse;"><tr><td style="padding:0px; border-collapse: collapse;">Quantity</td><td>:</td><td style="padding:0px; border-collapse: collapse;">'+d.tranQtyStr+
    	'</td></tr><tr><td width="100px" style="padding:0px; border-collapse: collapse;">Total Refills</td><td>:</td><td style="padding:0px; border-collapse: collapse;">'+d.tranTotalRefillsStr+'</td></tr><tr><td width="100px" style="padding:0px; border-collapse: collapse;">Refills Filled</td><td>:</td><td style="padding:0px; border-collapse: collapse;">'+d.tranRefillsFilledStr+'</td></tr><tr><td style="padding:0px; border-collapse: collapse;">Status</td><td>:</td><td style="padding:0px; border-collapse: collapse;">'+d.tranRXStatus+'</td></tr></table>';
    }

	function editPrescriptionRecord(id) {
		$("#prescriptionId").val(id);
		document.editPatientPage.action=urlPath+"prescription/editPrescription";
		document.editPatientPage.submit();
	}
	
	$(document).on("click", "#newPrescriptionSearch", function() {
		$("div.tooltip").remove();
		physicianName = "";
		prescriptionPatientName = ""
		presriptionDate = $("#prescriptionDate").val();
		presriptionToDate = $("#prescriptionToDate").val();
		prescriptionNo = $("#prescriptionNo").val();
		prescriptionstatus = $("#prescriptionstatus").val();
		
		prescriptionSummaryTable.ajax.url("getPhysicianPrescriptionSummaryData").draw();
	});

	$(document).on("click", "#clearPrescriptionSearch", function() {
		$("div.tooltip").remove();
		physicianName = "";
		prescriptionPatientName = "";
		presriptionDate = "";
		presriptionToDate = "";
		prescriptionNo ="";
    	prescriptionstatus = "";

		$("#physicianName").val("");
		$("#prescriptionPatientName").val("");
		$("#prescriptionDate").val("");
		$("#prescriptionToDate").val("");
		$("#prescriptionNo").val("");
		$("#prescriptionstatus").val("");
		
		prescriptionSummaryTable.ajax.url("getPhysicianPrescriptionSummaryData").draw();
	});
} catch(e) {
	 alert(" Exception  " + e);
}


try	{
	var itemSummaryTable = $("#itemSummaryTable").DataTable({
		"processing" : true,
		responsive:true,
		"serverSide" : true,
		"dom":'<"top">rt<"bottom"pl><"clear">',
		"lengthMenu" : [ [ 5, 10, 15], [  5, 10, 15] ],
		"pagingType": "numbers",  
		"ajax" : {
			"url" : "getPhysicianPrescriptionSummaryData",
			"data": function ( d ) {
				d.physicianName = physicianName;
                d.patientName = prescriptionPatientName;
                d.presriptionDate = presriptionDate;
                d.presriptionToDate = presriptionToDate;
                d.userId = userId;
                d.userType = userType;
                d.groupId = groupId;
                d.prescriptionNo = prescriptionNo;
                d.prescriptionstatus = prescriptionstatus;
			},
			"type" : "POST"
		},
		"oLanguage": {
	        "sEmptyTable":  "No records available"
	    },
		"columns" : [ 
		    { data : "prescriptionId", "visible": false },
		    { // render
		    	"orderable":false, "sClass":"left_align","data": function (data, type, row, meta) {
		    		return '<a onclick="editPrescriptionRecord('+data.prescriptionId+')" style="color:blue;font-weight:bold;cursor:pointer">' + data.prescriptionOrderNumber + '</a>';
                }, 
                "width": "120px"
            },
            { data : "tranRXNumber","orderable":false, "sClass":"left_align", "width": "100px"},
            { data : "tranItemName","orderable":false, "sClass":"left_align", "width": "200px" },
            { data : "tranRXStatus","orderable":false, "sClass":"left_align", "width": "100px" },
            { data : "tranQtyStr","orderable":false, "sClass":"left_align", "width": "60px"},
            { data : "tranTotalRefillsStr","orderable":false, "sClass":"left_align", "width": "60px"},
            { data : "tranRefillsFilledStr","orderable":false, "sClass":"left_align", "width": "60px"},
            { data : "tranDaySupply","orderable":false, "sClass":"left_align", "width": "60px"}
		],
		"initComplete": function(settings, json) {
			$('#itemSummaryTable_processing').hide();
			// heightAdjust();
		},
		"error" : "Error while processing data...."
	});	

	function editPrescriptionRecord(id) {
		$("#prescriptionId").val(id);
		document.editPatientPage.action=urlPath+"prescription/editPrescription";
		document.editPatientPage.submit();
	}
	
	$(document).on("click", "#newItemSearch", function() {
		$("div.tooltip").remove();
		prescriptionstatus = $("#prescriptionstatus").val();
		
		itemSummaryTable.ajax.url("getPhysicianPrescriptionSummaryData").draw();
	});

	$(document).on("click", "#clearItemSearch", function() {
		$("div.tooltip").remove();
    	prescriptionstatus = "";
		$("#prescriptionstatus").val("");
		
		itemSummaryTable.ajax.url("getPhysicianPrescriptionSummaryData").draw();
	});
} catch(e) {
	 alert(" Exception  " + e);
}

$(document).on("click", ".docRecordArea", function(){
	document.editPatientPage.action=urlPath+"manual/manualDocListView";
	document.editPatientPage.submit();
});