var rdate1 = $("#notifyStartDate");
var rdate2 = $("#notifyEndDate");

$(rdate1).datetimepicker({
	lang:'en',
	timepicker:false,
	format: 'm/d/Y'
});
$(rdate2).datetimepicker({
	lang:'en',
	timepicker:false,
	format: 'm/d/Y'
});

var patientname = "";
var phyname = "";
var physicianId = "";
var status = "";
var groupId = "";

var physicianName = "";
var prescriptionPatientName = "";
var presriptionDate = "";
var presriptionToDate = "";
var userId = "";
var userType = "";
var prescriptionNo="";
var prescriptionstatus = "";

var orderNo = "";
//var phyname = "";
var orderpatientName = "";
var fromDate = "";
var toDate = "";
var orderstatus = "";
//var physicianId = "";
//var patientId = "";


var invoiceNo = "";
var invfromDate = "";
var invtoDate = "";
var invoicepatientname = "";
var patientId="";
//var phyname = "";

var gdphysicianName = "";
var gddeaSearch = "";
var gdstatus = "";
var gdgroupId = "0";

try{
	patientname = $("#patientname").val();
	physicianId = $("#physicianId").val();
	status = $("#status").val();
	groupId = $("#groupId").val();
	

	prescriptionPatientName = $("#prescriptionPatientName").val();
	presriptionDate = "";
	presriptionToDate = "";
	userId = $("#userId").val();
	userType = $("#userType").val();
	prescriptionNo = $("#prescriptionNo").val();
	prescriptionstatus = $("#prescriptionstatus").val();
	

	
	orderpatientName= $("#orderpatientName").val();
	orderNo = $("#orderNo").val();
	fromDate = "";
	toDate  = "";
	orderstatus=  $("#orderstatus").val();
	physicianId = $("#physicianId").val();
	patientId =0;
	
	invoiceNo = $("#invoiceNo").val();
	
	invfromDate = "";
	invtoDate = "";
	invoicepatientname = $("#invoicepatientname").val();
	

	
}catch(e)
{
	
}



try	{
	if ($("#patientAccountSummaryTable").length > 0) {
		
		//alert(patientname+"===="+phyname+"========"+physicianId+"===="+status+"===="+groupId)
		
		// Physician login Summary works
		var summaryTable = $("#patientAccountSummaryTable").DataTable({
			"processing" : true,
			responsive:true,
			"serverSide" : true,
			"dom":'<"top">rt<"bottom"pl><"clear">',
			"lengthMenu" : [ [ 5, 10, 15], [ 5, 10, 15] ],
			/*"pagingType": "full_numbers",*/
			// numbers --> only nos, simple --> prev/next btns, simple_numbers --> prev/next btns & nos,
			// full --> prev/next/first/last btns, full_numbers --> prev/next/first/last btns & nos,
			// first_last_numbers ---> first/last btns and nos.
			"pagingType": "numbers",  
			"ajax" : {
				"url" : "getPhysicianPatientAccountSummaryData",
				"data": function ( d ) {
	                d.patientname = patientname;
	                d.phyname = phyname;
	                d.pid = physicianId;
	                d.status = status;
	                d.groupId = groupId;
	      		},
				"type" : "POST"
			},
			"oLanguage": {
		        "sEmptyTable":  "No records available"
		    },
			"columns" : [ 
			    { data : "patientId", "visible": false },
			    {
	                "class":"details-control",
	                "orderable":false,
	                "data":	null,
	                "defaultContent": ""
	            },
			    { // render
	            	"orderable":false, "sClass":"left_align", "data": function (data, type, row, meta) {
			    		return '<a onclick="editPatientRecord('+data.patientId+')" style="color:blue;font-weight:bold;cursor:pointer">' + data.patientName + '</a>';
			    	}
                },
                { data : "physicianName","orderable":false, "sClass":"left_align"  },
			    { data : "groupName","orderable":false, "sClass":"left_align" ,"visible": false  },
			    { data : "city","orderable":false, "sClass":"left_align"   },
			    { data : "email","orderable":false, "sClass":"left_align","visible": false },
			    { data : "mobile","orderable":false, "sClass":"left_align","visible": false },
			    { data : "status","orderable":false, "sClass":"left_align","visible": false  },
			    { data : "addressInfo","orderable":false, "sClass":"left_align","visible": false  }
			],
			"initComplete": function(settings, json) {
				$('#patientAccountSummaryTable_processing').hide();
				// heightAdjust();
			},
			"error" : "Error while processing data...."
		});
		
		
		// Array to track the ids of the details displayed rows
	    var patdetailRows = [];
	 
	    $('#patientAccountSummaryTable tbody').on( 'click', 'tr td.details-control', function () {
	        var tr = $(this).closest('tr');
	        var row = summaryTable.row( tr );
	        var idx = $.inArray( tr.attr('id'), patdetailRows );
	 
	        if ( row.child.isShown() ) {
	            tr.removeClass( 'details' );
	            row.child.hide();
	 
	            // Remove from the 'open' array
	            patdetailRows.splice( idx, 1 );
	        }
	        else {
	            tr.addClass( 'details' );
	            row.child( formatPatient( row.data() ) ).show();
	 
	            // Add to the 'open' array
	            if ( idx === -1 ) {
	                patdetailRows.push( tr.attr('id') );
	            }
	        }
	    } );
	 
	    // On each draw, loop over the `patdetailRows` array and show any child rows
	    summaryTable.on( 'draw', function () {
	        $.each( patdetailRows, function ( i, id ) {
	            $('#'+id+' td.details-control').trigger( 'click' );
	        } );
	    } );

	    function formatPatient ( d ) {
	    	return '<table cellpadding="0" cellspacing="0" style="padding:0px; border-collapse: collapse;"><tr><td style="padding:0px; border-collapse: collapse;">Address</td><td>:</td><td style="padding:0px; border-collapse: collapse;">'+d.addressInfo+'</td></tr><tr><td style="padding:0px; border-collapse: collapse;">Mobile</td><td>:</td><td style="padding:0px; border-collapse: collapse;">'+d.mobile+'</td></tr><tr><td style="padding:0px; border-collapse: collapse;">Email</td><td>:</td><td style="padding:0px; border-collapse: collapse;">'+d.email+
	    	'</td></tr></table>';
	    }
		function editPatientRecord(id) {
			$("#patientId").val(id);
			document.forms[0].action=urlPath+"patient/editPatientAccount";
			document.forms[0].submit();
		}
	
		$(document).on("click", "#newSearch", function() {
			$("div.tooltip").remove();
			patientname = $("#patientname").val();
			phyname = $("#phyname").val();
			physicianId = $("#physicianId").val();
			status = $("#status").val();
			groupId = $("#groupId").val();
			summaryTable.ajax.url("getPhysicianPatientAccountSummaryData").draw();
		});
		
		$(document).on("click", "#clearSearch", function() {
			$("div.tooltip").remove();
			$("#patientname").val("");
			$("#phyname").val("");
			$("#status").val("");
			
			document.getElementById("select2-autoCompleterPatientId-container").innerHTML='<span class="select2-selection__placeholder">Patient-All</span>';	
			
			patientname = "";
			phyname = "";
			status = "";
			if ($("#checkUserType").val() != "Group Director") {
				$("#groupId").val("0");
				groupId = "0";
			}
			summaryTable.ajax.url("getPhysicianPatientAccountSummaryData").draw();
		});
	}
} catch(e) {
	// alert(" Exception in redrawDataTable()     ======     " + e);
}

try	{
	if ($("#physicianAccountSummaryTable").length > 0) {
		
		 gdphysicianName =  $("#gdphysicianName").val();
		 gddeaSearch = "";
		 gdstatus = "";
		 gdgroupId = groupId;
		// Physician login Summary works
		var physummaryTable = $("#physicianAccountSummaryTable").DataTable({
			"processing" : true,
			responsive:true,
			"serverSide" : true,
			"dom":'<"top">rt<"bottom"pl><"clear">',
			"lengthMenu" : [ [ 5, 10, 15], [ 5, 10, 15] ],
			/*"pagingType": "full_numbers",*/
			// numbers --> only nos, simple --> prev/next btns, simple_numbers --> prev/next btns & nos,
			// full --> prev/next/first/last btns, full_numbers --> prev/next/first/last btns & nos,
			// first_last_numbers ---> first/last btns and nos.
			"pagingType": "numbers",  
			"ajax" : {
				"url" : "getPhysicianAccountSummaryData",
				"data": function ( d ) {
	                d.physicianName = gdphysicianName;
	                d.dea = gddeaSearch;
	                d.status = gdstatus;
	                d.groupId = groupId;
	      		},
				"type" : "POST"
			},
			"oLanguage": {
		        "sEmptyTable":  "No records available"
		    },
			"columns" : [ 
			    { data : "physicianId", "visible": false },
			    {
	                "class":"details-control",
	                "orderable":false,
	                "data":	null,
	                "defaultContent": ""
	            },
			   { // render
	            	"orderable":false, "sClass":"left_align", "data": function (data, type, row, meta) {
			    		return '<a onclick="editPhysicianRecord('+data.physicianId+')" style="color:blue;font-weight:bold;cursor:pointer">' + data.physicianNameWithGroupName + '</a>';
			    	}
                },
                /*{ data : "physicianNameWithGroupName","orderable":false, "sClass":"left_align"   },*/
    			{ data : "dea","orderable":false, "sClass":"left_align"  },
    			{ data : "groupName","orderable":false, "sClass":"left_align","visible": false  },
    			{ data : "city","orderable":false, "sClass":"left_align","visible": false  },
    		    { data : "state","orderable":false, "sClass":"left_align","visible": false  },
    			{ data : "email","orderable":false, "sClass":"left_align", "visible": false  },
    		    { data : "mobile","orderable":false, "sClass":"left_align", "visible": false  },
    		    { data : "status","orderable":false, "sClass":"left_align" },
    		    { data : "addressInfo","orderable":false, "sClass":"left_align","visible": false  }
    		    
			],
			"initComplete": function(settings, json) {
				$('#physicianAccountSummaryTable_processing').hide();
				// heightAdjust();
			},
			"error" : "Error while processing data...."
		});
		
		
		// Array to track the ids of the details displayed rows
	    var phydetailRows = [];
	 
	    $('#physicianAccountSummaryTable tbody').on( 'click', 'tr td.details-control', function () {
	        var tr = $(this).closest('tr');
	        var row = physummaryTable.row( tr );
	        var idx = $.inArray( tr.attr('id'), phydetailRows );
	 
	        if ( row.child.isShown() ) {
	            tr.removeClass( 'details' );
	            row.child.hide();
	 
	            // Remove from the 'open' array
	            phydetailRows.splice( idx, 1 );
	        }
	        else {
	            tr.addClass( 'details' );
	            row.child( formatPhysician( row.data() ) ).show();
	 
	            // Add to the 'open' array
	            if ( idx === -1 ) {
	                phydetailRows.push( tr.attr('id') );
	            }
	        }
	    } );
	 
	    // On each draw, loop over the `phydetailRows` array and show any child rows
	    physummaryTable.on( 'draw', function () {
	        $.each( phydetailRows, function ( i, id ) {
	            $('#'+id+' td.details-control').trigger( 'click' );
	        } );
	    } );

	    function formatPhysician( d ) {
	    	return '<table cellpadding="0" cellspacing="0" style="padding:0px; border-collapse: collapse;"><tr><td style="padding:0px; border-collapse: collapse;">Address</td><td>:</td><td style="padding:0px; border-collapse: collapse;">'+d.addressInfo+'</td></tr><tr><td style="padding:0px; border-collapse: collapse;">Mobile</td><td>:</td><td style="padding:0px; border-collapse: collapse;">'+d.mobile+'</td></tr><tr><td style="padding:0px; border-collapse: collapse;">Email</td><td>:</td><td style="padding:0px; border-collapse: collapse;">'+d.email+
	    	'</td></tr></table>';
	    }
		function editPhysicianRecord(id) {
			$("#physicianId").val(id);
			document.forms[0].action=urlPath+"physician/editPhysicianProfile";
			document.forms[0].submit();
		}
	
		$(document).on("click", "#newPhysicianSearch", function() {
			$("div.tooltip").remove();
			
			 gdphysicianName = $("#gdphysicianName").val();
			 gddeaSearch = "";
			 gdstatus = "";
			 gdgroupId =  $("#groupId").val();
			 
			physummaryTable.ajax.url("getPhysicianAccountSummaryData").draw();
		});
		
		$(document).on("click", "#clearPhysicianSearch", function() {
			$("div.tooltip").remove();
			
			 gdphysicianName ="";
			 gddeaSearch = "";
			 gdstatus = "";
			 gdgroupId =  $("#groupId").val();
			 
			document.getElementById("select2-autoCompleterPhysicianId-container").innerHTML='<span class="select2-selection__placeholder">Physician-All</span>';	
			
			
		
			if ($("#checkUserType").val() != "Group Director") {
				$("#groupId").val("0");
				groupId = "0";
			}
			physummaryTable.ajax.url("getPhysicianAccountSummaryData").draw();
		});
	}
} catch(e) {
	// alert(" Exception in redrawDataTable()     ======     " + e);
}

try{
if ($("#adminPhysicianAsstSummaryTable").length > 0) {
	var gdphysicianAssistantName = "";
	var gdclinicName = "";
	var gdphyName = "";
	var gdstatus = "";
	var gdgroupId = "";
	
	gdphysicianAssistantName = $("#gdphysicianAssistantName").val();
	gdclinicName ="";
	gdphyName ="";
	gdstatus = "";
	gdgroupId = $("#groupId").val();
	if(gdgroupId=="")
		gdgroupId=0;
	
	
	var phyAssistsummaryTable = $("#adminPhysicianAsstSummaryTable").DataTable({
		"processing" : true,
		responsive:true,
		"serverSide" : true,
		"dom":'<"top">rt<"bottom"pl><"clear">',
		"lengthMenu" : [ [ 5, 10, 15], [ 5, 10, 15] ],
		/*"pagingType": "full_numbers",*/
		// numbers --> only nos, simple --> prev/next btns, simple_numbers --> prev/next btns & nos,
		// full --> prev/next/first/last btns, full_numbers --> prev/next/first/last btns & nos,
		// first_last_numbers ---> first/last btns and nos.
		"pagingType": "numbers",  
		"ajax" : {
			"url" : "getPhysicianAssistantData",
			"data": function ( d ) {
                d.assistant = gdphysicianAssistantName;
                d.phyName = gdphyName;
                d.status = gdstatus;
                d.clinic = gdclinicName;
                d.groupId = gdgroupId;
      		},
			"type" : "POST"
		},
		"oLanguage": {
	        "sEmptyTable":  "No records available"
	    },
	    "columns" : [ 
	     		    { data : "assistantId", "visible": false },
	     		    {
	                     "class":"details-control",
	                     "orderable":false,
	                     "data":	null,
	                     "defaultContent": ""
	                 },
	     		    { // render
	                	 "orderable":false, "sClass":"left_align", "data": function (data, type, row, meta) {
	     		    		return '<a onclick="editPhysicianAssistantRecord('+data.assistantId+')" style="color:blue;font-weight:bold;cursor:pointer">' + data.assistantName + '</a>';
	     		    	}
	                 },
	     		  /*  { data : "assistantName","orderable":false, "sClass":"left_align"   },*/
	     		    { data : "physicianName","orderable":false, "sClass":"left_align"   },
	     		    { data : "groupName","orderable":false, "sClass":"left_align" },
	     		    { data : "clinicName","orderable":false, "sClass":"left_align" ,  "visible": false },
	     		    { data : "email","orderable":false, "sClass":"left_align",  "visible": false   },
	     		    { data : "mobile","orderable":false, "sClass":"left_align",  "visible": false },
	     		    { data : "status","orderable":false, "sClass":"left_align" }
	     		],
		"initComplete": function(settings, json) {
			$('#adminPhysicianAsstSummaryTable_processing').hide();
			// heightAdjust();
		},
		"error" : "Error while processing data...."
	});
	
	
	// Array to track the ids of the details displayed rows
    var phyassistdetailRows = [];
 
    $('#adminPhysicianAsstSummaryTable tbody').on( 'click', 'tr td.details-control', function () {
        var tr = $(this).closest('tr');
        var row = phyAssistsummaryTable.row( tr );
        var idx = $.inArray( tr.attr('id'), phyassistdetailRows );
 
        if ( row.child.isShown() ) {
            tr.removeClass( 'details' );
            row.child.hide();
 
            // Remove from the 'open' array
            phyassistdetailRows.splice( idx, 1 );
        }
        else {
            tr.addClass( 'details' );
            row.child( formatPhysicianAssistant( row.data() ) ).show();
 
            // Add to the 'open' array
            if ( idx === -1 ) {
            	phyassistdetailRows.push( tr.attr('id') );
            }
        }
    } );
 
    // On each draw, loop over the `phydetailRows` array and show any child rows
    phyAssistsummaryTable.on( 'draw', function () {
        $.each( phyassistdetailRows, function ( i, id ) {
            $('#'+id+' td.details-control').trigger( 'click' );
        } );
    } );

    function formatPhysicianAssistant( d ) {
    	return '<table cellpadding="0" cellspacing="0" style="padding:0px; border-collapse: collapse;"><tr><td style="padding:0px; border-collapse: collapse;">Mobile</td><td>:</td><td style="padding:0px; border-collapse: collapse;">'+d.mobile+'</td></tr><tr><td style="padding:0px; border-collapse: collapse;">Email</td><td>:</td><td style="padding:0px; border-collapse: collapse;">'+d.email+
    	'</td></tr></table>';
    }
	function editPhysicianAssistantRecord(id) {
		$("#assistantId").val(id);
		document.forms[0].action=urlPath+"physician/editPhysicianassistantaccount";
		document.forms[0].submit();
	}
	
	
	
	$(document).on("click", "#newPhysicianAssistantSearch", function() {
		$("div.tooltip").remove();
		gdphysicianAssistantName = $("#gdphysicianAssistantName").val();
		gdclinicName ="";
		gdphyName = "";
		gdstatus ="";
		gdgroupId = $("#groupId").val();
		phyAssistsummaryTable.ajax.url("getPhysicianAssistantData").draw();
	});
	
	$(document).on("click", "#clearPhysicianAssistantSearch", function() {
		$("div.tooltip").remove();
		$("#gdphysicianAssistantName").val("");
		$("#gdclinicName").val("");
		$("#gdphyName").val("");
		$("#gdstatus").val("");
		gdgroupId = $("#groupId").val();
		
		if (document.getElementById("select2-autoCompleterPhysicianAssistantId-container"))
			document.getElementById("select2-autoCompleterPhysicianAssistantId-container").innerHTML='<span class="select2-selection__placeholder">Physician Assistant-All</span>';	
		gdphysicianAssistantName ="";
		gdclinicName ="";
		gdphyName = "";
		gdstatus ="";
	
		if ($("#checkUserType").val() != "Group Director") {
			$("#groupId").val("0");
			groupId = "0";
		}
		phyAssistsummaryTable.ajax.url("getPhysicianAssistantData").draw();
	});
}
} catch(e) {
alert(" Exception in redrawDataTable()     ======     " + e);
}

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
            {
                "class":"details-control",
                "orderable":false,
                "data":	null,
                "defaultContent": ""
            },
		    { // render
            	"orderable":false, "sClass":"left_align", "data": function (data, type, row, meta) {
		    		return '<a onclick="editPrescriptionRecord('+data.prescriptionId+')" style="color:blue;font-weight:bold;cursor:pointer">' + data.prescriptionOrderNumber + '</a>';
                }
            },
			{ data : "tranRXNumber","orderable":false, "sClass":"left_align"   },
		    { data : "physicianName","orderable":false, "sClass":"left_align", "visible": false   },
		    { data : "patientName","orderable":false, "sClass":"left_align" },
		    { data : "tranItemName","orderable":false, "sClass":"left_align" , "visible": false},
		    { data : "tranRXStatus","orderable":false, "sClass":"left_align", "visible": false },
		    { data : "tranQtyStr","orderable":false, "sClass":"left_align" , "visible": false},
		    { data : "tranTotalRefillsStr","orderable":false, "sClass":"left_align" , "visible": false},
		    { data : "tranRefillsFilledStr","orderable":false, "sClass":"left_align" , "visible": false},
		 
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
	    } );

	    function formatPrescription ( d ) {
	    	return '<table cellpadding="0" cellspacing="0" style="padding:0px; border-collapse: collapse;"><tr><td style="padding:0px; border-collapse: collapse;">Physician</td><td>:</td><td style="padding:0px; border-collapse: collapse;">'+d.physicianName+'</td></tr><tr><td style="padding:0px; border-collapse: collapse;">Item</td><td>:</td><td style="padding:0px; border-collapse: collapse;">'+d.tranItemName+'</td></tr><tr><td style="padding:0px; border-collapse: collapse;">Quantity</td><td>:</td><td style="padding:0px; border-collapse: collapse;">'+d.tranQtyStr+
	    	'</td></tr><tr><td width="100px" style="padding:0px; border-collapse: collapse;">Total Refills</td><td>:</td><td style="padding:0px; border-collapse: collapse;">'+d.tranTotalRefillsStr+'</td></tr><tr><td width="100px" style="padding:0px; border-collapse: collapse;">Refills Filled</td><td>:</td><td style="padding:0px; border-collapse: collapse;">'+d.tranRefillsFilledStr+'</td></tr><tr><td style="padding:0px; border-collapse: collapse;">Status</td><td>:</td><td style="padding:0px; border-collapse: collapse;">'+d.tranRXStatus+'</td></tr></table>';
	    }
	
	function editPrescriptionRecord(id)
	{
		$("#prescriptionId").val(id);
		document.forms[0].action=urlPath+"prescription/editPrescription";
		document.forms[0].submit();
	}
	
	
	
	$(document).on("click", "#newPrescriptionSearch", function() {
		$("div.tooltip").remove();
		physicianName = "";
		prescriptionPatientName = $("#prescriptionPatientName").val();
		presriptionDate ="";
		presriptionToDate = "";
		
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
		prescriptionNo="";
    	prescriptionstatus = "";

		document.getElementById("select2-autoCompleterPrescriptionPatientId-container").innerHTML='<span class="select2-selection__placeholder">Patient-All</span>';	
		
		$("#physicianName").val("");
		$("#prescriptionPatientName").val("");
		$("#prescriptionDate").val("");
		$("#prescriptionToDate").val("");
		$("#prescriptionNo").val("");
		$("#prescriptionstatus").val("");
		
		prescriptionSummaryTable.ajax.url("getPhysicianPrescriptionSummaryData").draw();
	});
} catch(e) {
	// alert(" Exception in redrawDataTable()     ======     " + e);
}


try	{
	
	var orderSummaryTable = $("#orderSummaryTable").DataTable({
		"processing" : true,
		responsive:true,
		"serverSide" : true,
		"dom":'<"top">rt<"bottom"pl><"clear">',
		"lengthMenu" : [ [ 5, 10, 15], [ 5, 10, 15] ],
		/*"pagingType": "full_numbers",*/
		// numbers --> only nos, simple --> prev/next btns, simple_numbers --> prev/next btns & nos,
		// full --> prev/next/first/last btns, full_numbers --> prev/next/first/last btns & nos,
		// first_last_numbers ---> first/last btns and nos.
		"pagingType": "numbers",  
		"ajax" : {
			"url" : "getPhysicianPatientOrderSummaryData",
			"data": function ( d ) {
				d.orderNo = orderNo;
				d.phyname = phyname;
                d.patientname = orderpatientName;
                d.fromDate = fromDate;
                d.toDate = toDate;
                d.status = orderstatus;
                d.physicianId = physicianId;
                d.patientId=patientId;
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
                "class":"details-control",
                "orderable":false,
                "data":	null,
                "defaultContent": ""
            },
		    { // render
            	"orderable":false, "sClass":"left_align", "data": function (data, type, row, meta) {
		    		return '<a onclick="editOrderRecord('+data.orderId+')" style="color:blue;font-weight:bold;cursor:pointer">' + data.orderNumber + '</a>';
		    	}
		    },
			{ data : "orderDate","orderable":false, "sClass":"left_align"   },
			{ data : "rxNumbers","orderable":false, "sClass":"left_align"   },
			{ data : "orderNumbers","orderable":false, "sClass":"left_align" , "visible": false  },
			{ data : "patientName","orderable":false, "sClass":"left_align", "visible": false   },
			{ data : "physicianName","orderable":false, "sClass":"left_align" , "visible": false },
		    { data : "rxStatuses","orderable":false, "sClass":"left_align"   },
		    { data : "medicationDispensed","orderable":false, "sClass":"left_align", "visible": false},
		    { data : "refillsFilled","orderable":false, "sClass":"left_align" , "visible": false},
		    { data : "daysSupply","orderable":false, "sClass":"left_align" , "visible": false},
		    { data : "priorityType","orderable":false, "sClass":"left_align" , "visible": false},
		],
		"initComplete": function(settings, json) {
			$('#orderSummaryTable_processing').hide();
			// heightAdjust();
		},
		"error" : "Error while processing data...."
	});

	// Array to track the ids of the details displayed rows
    var orderdetailRows = [];
 
    $('#orderSummaryTable tbody').on( 'click', 'tr td.details-control', function () {
        var tr = $(this).closest('tr');
        var row = orderSummaryTable.row( tr );
        var idx = $.inArray( tr.attr('id'), orderdetailRows );
 
        if ( row.child.isShown() ) {
            tr.removeClass( 'details' );
            row.child.hide();
 
            // Remove from the 'open' array
            orderdetailRows.splice( idx, 1 );
        }
        else {
            tr.addClass( 'details' );
            row.child( formatOrder( row.data() ) ).show();
 
            // Add to the 'open' array
            if ( idx === -1 ) {
                orderdetailRows.push( tr.attr('id') );
            }
        }
    } );
 
    // On each draw, loop over the `orderdetailRows` array and show any child rows
    orderSummaryTable.on( 'draw', function () {
        $.each( orderdetailRows, function ( i, id ) {
            $('#'+id+' td.details-control').trigger( 'click' );
        } );
    } );

    function formatOrder ( d ) {
    	return '<table cellpadding="0" cellspacing="0" style="padding:0px; border-collapse: collapse;"><tr><td style="padding:0px; border-collapse: collapse;">Patient</td><td>:</td><td style="padding:0px; border-collapse: collapse;">'+d.patientName+'</td></tr><tr><td style="padding:0px; border-collapse: collapse;">Physician</td><td>:</td><td style="padding:0px; border-collapse: collapse;">'+d.physicianName+'</td></tr><tr><td style="padding:0px; border-collapse: collapse;">Medication Dispensed</td><td>:</td><td style="padding:0px; border-collapse: collapse;">'+d.medicationDispensed+'</td></tr><tr><td style="padding:0px; border-collapse: collapse;">Refills Filled</td><td>:</td><td style="padding:0px; border-collapse: collapse;">'+d.refillsFilled+
    	'</td></tr><tr><td width="150px" style="padding:0px; border-collapse: collapse;">Days Supply</td><td>:</td><td style="padding:0px; border-collapse: collapse;">'+d.daysSupply+'</td></tr><tr><td width="100px" style="padding:0px; border-collapse: collapse;">Priority Type</td><td>:</td><td style="padding:0px; border-collapse: collapse;">'+d.priorityType+'</td></tr></table>';
    }
	// For View Record.
 
	
	function editOrderRecord(id)
	{
		//alert(id)
		$("#orderId").val(id);
		document.forms[0].action=urlPath+"order/viewOrders";
		document.forms[0].submit();
	}
	
	
	$(document).on("click", "#newOrderSearch", function() {
		$("div.tooltip").remove();
		orderNo =  $("#orderNo").val();
		phyname = "";
		orderpatientName= $("#orderpatientName").val();
		fromDate =  "";
		toDate =  "";
		orderstatus=  $("#orderstatus").val();
		physicianId = $("#physicianId").val();
		patientId = 0;
		
		orderSummaryTable.ajax.url("getPhysicianPatientOrderSummaryData").draw();
	});

	$(document).on("click", "#clearOrderSearch", function() {
		$("div.tooltip").remove();
		$("#orderNo").val("");
		//$("#phyname").val("");
		$("#patientname").val("");
		//$("#fromDate").val("");
		//$("#toDate").val("");
		$("#orderstatus").val("");

		document.getElementById("select2-autoCompleterOrderPatientId-container").innerHTML='<span class="select2-selection__placeholder">Patient-All</span>';	
		
		orderNo = "";
		phyname = "";
		orderpatientName="";
		fromDate = "";
		toDate = "";
		orderstatus = "";
		orderSummaryTable.ajax.url("getPhysicianPatientOrderSummaryData").draw();
	});
} catch(e) {
	// alert(" Exception in redrawDataTable()     ======     " + e);
}


try	{
	if ($("#invoiceSummaryTable").length > 0) {
			
		// Invoice Summary works
		var invoiceSummaryTable = $("#invoiceSummaryTable").DataTable({
			"processing" : true,
			responsive:true,
			"serverSide" : true,
			"dom":'<"top">rt<"bottom"pl><"clear">',
			"lengthMenu" : [ [5, 10, 15], [5, 10, 15] ],
			/*"pagingType": "full_numbers",*/
			// numbers --> only nos, simple --> prev/next btns, simple_numbers --> prev/next btns & nos,
			// full --> prev/next/first/last btns, full_numbers --> prev/next/first/last btns & nos,
			// first_last_numbers ---> first/last btns and nos.
			"pagingType": "numbers",  
			"ajax" : {
				"url" : "getPhysicianPatientInvoiceSummaryData",
				"data": function ( d ) {
					d.invoiceNo = invoiceNo;
	                d.fromDate = fromDate;
	                d.toDate = toDate;
	                d.patientname = invoicepatientname;
	                d.phyname = phyname;
	                d.patientId = patientId;
	                d.physicianId = physicianId;
	                d.groupId = groupId;
	      		},
				"type" : "POST"
			},
			"oLanguage": {
		        "sEmptyTable":  "No records available"
		    },
			"columns" : [ 
			    { data : "invoiceId", "visible": false },
			    {
	                "class":"details-control",
	                "orderable":false,
	                "data":	null,
	                "defaultContent": ""
	            },
				{ // render
	            	"orderable":false, "sClass":"left_align","data": function (data, type, row, meta) {
	            		return '<a onclick="editInvoiceRecord('+data.invoiceId+')" style="color:blue;font-weight:bold;cursor:pointer">' + data.invoiceNo + '</a>';
			    	}
				},
			    { data : "invoiceDateStr","orderable":false, "sClass":"left_align"   },
			    { data : "rxNumber","orderable":false, "sClass":"left_align"   },
			    { data : "patientName","orderable":false, "sClass":"left_align"  ,"visible": false  },
			    { data : "physicianName","orderable":false, "sClass":"left_align" ,"visible": false  },
			    { // render
			    	"data": function (data, type, row, meta) {
			    		return '$ ' + data.total + '';
			    	}
			    },
			    { data : "invoiceStatus","orderable":false, "sClass":"left_align" ,"visible": false   },
			    { data : "itemInvoiced","orderable":false, "sClass":"left_align" ,"visible": false  },
			    { data : "refillsFilled","orderable":false, "sClass":"left_align" ,"visible": false  },
			    { data : "quantity","orderable":false, "sClass":"left_align" ,"visible": false  },
			    { data : "lotNo","orderable":false, "sClass":"left_align" ,"visible": false  },
			    { data : "lotExpDate","orderable":false, "sClass":"left_align" ,"visible": false  },
			],
			"initComplete": function(settings, json) {
				$('#invoiceSummaryTable_processing').hide();
				// heightAdjust();
			},
			"error" : "Error while processing data...."
		});

		// Array to track the ids of the details displayed rows
	    var invoicedetailRows = [];
	 
	    $('#invoiceSummaryTable tbody').on( 'click', 'tr td.details-control', function () {
	        var tr = $(this).closest('tr');
	        var row = invoiceSummaryTable.row( tr );
	        var idx = $.inArray( tr.attr('id'), invoicedetailRows );
	 
	        if ( row.child.isShown() ) {
	            tr.removeClass( 'details' );
	            row.child.hide();
	 
	            // Remove from the 'open' array
	            invoicedetailRows.splice( idx, 1 );
	        }
	        else {
	            tr.addClass( 'details' );
	            row.child( formatOrder( row.data() ) ).show();
	 
	            // Add to the 'open' array
	            if ( idx === -1 ) {
	                invoicedetailRows.push( tr.attr('id') );
	            }
	        }
	    } );
	 
	    // On each draw, loop over the `invoicedetailRows` array and show any child rows
	    invoiceSummaryTable.on( 'draw', function () {
	        $.each( invoicedetailRows, function ( i, id ) {
	            $('#'+id+' td.details-control').trigger( 'click' );
	        } );
	    } );

	    function formatOrder ( d ) {
	    	return '<table cellpadding="0" cellspacing="0" style="padding:0px; border-collapse: collapse;"><tr><td style="padding:0px; border-collapse: collapse;">Patient</td><td>:</td><td style="padding:0px; border-collapse: collapse;">'+d.patientName+'</td></tr><tr><td style="padding:0px; border-collapse: collapse;">Item</td><td>:</td><td style="padding:0px; border-collapse: collapse;">'+d.itemInvoiced+'</td></tr><tr><td style="padding:0px; border-collapse: collapse;">Refills Filled</td><td>:</td><td style="padding:0px; border-collapse: collapse;">'+d.refillsFilled+
	    	'</td></tr><tr><td width="150px" style="padding:0px; border-collapse: collapse;">Quantity</td><td>:</td><td style="padding:0px; border-collapse: collapse;">'+d.quantity+'</td></tr><tr><td width="100px" style="padding:0px; border-collapse: collapse;">Lot No</td><td>:</td><td style="padding:0px; border-collapse: collapse;">'+d.lotNo+'</td></tr>'+
	    	'<tr><td width="100px" style="padding:0px; border-collapse: collapse;">Lot Expiration Date</td><td>:</td><td style="padding:0px; border-collapse: collapse;">'+d.lotExpDate+'</td></tr><tr><td style="padding:0px; border-collapse: collapse;">Invoice Status</td><td>:</td><td style="padding:0px; border-collapse: collapse;">'+d.invoiceStatus+'</td></tr></table>';
	    }
		// For Edit Record.
		
		function editInvoiceRecord(id)
		{
			//alert(id)
			$("#invoiceId").val(id);
			document.forms[0].action=urlPath+"invoice/viewInvoice";
			document.forms[0].submit();
		}
		
		$(document).on("click", "#newInvoiceSearch", function() {
			$("div.tooltip").remove();
			
			fromDate =  "";
			toDate =  "";
			invoicepatientname = $("#invoicepatientname").val();
			phyname =  "";
			invoiceNo = $("#invoiceNo").val();
       	
			invoiceSummaryTable.ajax.url("getPhysicianPatientInvoiceSummaryData").draw();
		});
		
		$(document).on("click", "#clearInvoiceSearch", function() {
			$("div.tooltip").remove();
			$("#invoiceNo").val("");
			//$("#fromDate").val("");
			//$("#toDate").val("");
			$("#invoicepatientname").val("");
			//$("#phyname").val("");
			
			document.getElementById("select2-autoCompleterInvoicePatientId-container").innerHTML='<span class="select2-selection__placeholder">Patient-All</span>';	
			
			invoiceNo = "";
			fromDate = "";
			toDate = "";
			invoicepatientname = "";
			phyname = "";
			invoiceSummaryTable.ajax.url("getPhysicianPatientInvoiceSummaryData").draw();
		});
	}
} catch(e) {
	// alert(" Exception in redrawDataTable()     ======     " + e);
}

try {
	var patientnameVar = $("#patientname").val();
	if (patientnameVar == null || patientnameVar == "") {
		patientnameVar = "Patient-All";
	}
	var patFormId= $("#patientId").val();
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
				//alert(data)
				aresults = [];
				data.forEach(function makeResults(element) {
					//alert(element)
					aresults.push({
						id : element.patientName,
						text : element.patientName
					});
				});
				return {
					results : aresults
				};

				/*  
				return {
				  aresults: data
				}; */
			},
			cache : true
		}
	}).on('change', function(e) {
		//alert(this.value)
		$("#patientname").val(this.value)
	});

} catch (e) {
	// alert(e)
	// TODO: handle exception
}

try {
	var prescriptionPatientNameVar = $("#prescriptionPatientName").val();
	if (prescriptionPatientNameVar == null || prescriptionPatientNameVar == "") {
		prescriptionPatientNameVar = "Patient-All";
	}
	var patFormId= $("#patientId").val();
	var aresults = [];
	$('#autoCompleterPrescriptionPatientId').select2({
		placeholder : prescriptionPatientNameVar,
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
				//alert(data)
				aresults = [];
				data.forEach(function makeResults(element) {
					//alert(element)
					aresults.push({
						id : element.patientName,
						text : element.patientName
					});
				});
				return {
					results : aresults
				};

				/*  
				return {
				  aresults: data
				}; */
			},
			cache : true
		}
	}).on('change', function(e) {
		//alert(this.value)
		$("#prescriptionPatientName").val(this.value)
	});

} catch (e) {
	// alert(e)
	// TODO: handle exception
}

try {
	var orderpatientNameVar = $("#orderpatientName").val();
	if (orderpatientNameVar == null || orderpatientNameVar == "") {
		orderpatientNameVar = "Patient-All";
	}
	var patFormId= $("#patientId").val();
	var aresults = [];
	$('#autoCompleterOrderPatientId').select2({
		placeholder : orderpatientNameVar,
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
				//alert(data)
				aresults = [];
				data.forEach(function makeResults(element) {
					//alert(element)
					aresults.push({
						id : element.patientName,
						text : element.patientName
					});
				});
				return {
					results : aresults
				};

				/*  
				return {
				  aresults: data
				}; */
			},
			cache : true
		}
	}).on('change', function(e) {
		//alert(this.value)
		$("#orderpatientName").val(this.value)
	});

} catch (e) {
	 alert(e)
	// TODO: handle exception
}



try {
	var invoicepatientnameVar = $("#invoicepatientname").val();
	if (invoicepatientnameVar == null || invoicepatientnameVar == "") {
		invoicepatientnameVar = "Patient-All";
	}
	var patFormId= $("#patientId").val();
	var aresults = [];
	$('#autoCompleterInvoicePatientId').select2({
		placeholder : invoicepatientnameVar,
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
				//alert(data)
				aresults = [];
				data.forEach(function makeResults(element) {
					//alert(element)
					aresults.push({
						id : element.patientName,
						text : element.patientName
					});
				});
				return {
					results : aresults
				};

				/*  
				return {
				  aresults: data
				}; */
			},
			cache : true
		}
	}).on('change', function(e) {
		//alert(this.value)
		$("#invoicepatientname").val(this.value)
	});

} catch (e) {
	// alert(e)
	// TODO: handle exception
}



try {
	var gdphysicianNameVar = $("#gdphysicianName").val();
	if (gdphysicianNameVar == null || gdphysicianNameVar == "") {
		gdphysicianNameVar = "Physician-All";
	}
	var results = [];
	$('#autoCompleterPhysicianId').select2({
		placeholder : gdphysicianNameVar,
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
				//alert(data)
				results = [];
				data.forEach(function makeResults(element) {
					//alert(element)
					results.push({
						id : element.physicianName,
						text : element.physicianName
					});
				});
				return {
					results : results
				};

				/*  
				return {
				  results: data
				}; */
			},
			cache : true
		}
	}).on('change', function(e) {
		//alert(this.value)
		$("#gdphysicianName").val(this.value)
	});

} catch (e) {
	// alert(e)
	// TODO: handle exception
}

try {
	var gdphysicianAssistantNameVar = $("#gdphysicianAssistantName").val();
	if (gdphysicianAssistantNameVar == null || gdphysicianAssistantNameVar == "") {
		gdphysicianAssistantNameVar = "Physician Assistant-All";
	}
	var aresults = [];
	$('#autoCompleterPhysicianAssistantId').select2({
		placeholder : gdphysicianAssistantNameVar,
		minimumInputLength : 0,
		ajax : {
			url : urlPath + 'getAllAutoCompletePhysicianAssistantsList',
			dataType : 'json',
			data : function(params) {
				return {
					term : $.trim(params.term)
				};
			},
			processResults : function(data) {
				//alert(data)
				aresults = [];
				data.forEach(function makeResults(element) {
					//alert(element)
					aresults.push({
						id : element.assistantName,
						text : element.assistantName
					});
				});
				return {
					results : aresults
				};

				/*  
				return {
				  aresults: data
				}; */
			},
			cache : true
		}
	}).on('change', function(e) {
		//alert(this.value)
		$("#gdphysicianAssistantName").val(this.value)
	});

} catch (e) {
	// alert(e)
}

$(document).on("click", "#newPatientAcc", function() {
	location.href=urlPath+"patient/patientAccount";
} ) ; 

$(document).on("click", "#newPrescription", function() {
	location.href=urlPath+"prescription/prescriptionView";
});

$(document).on("click", "#newPhysicianAcc", function() {
	location.href=urlPath+"physician/createPhysicianAccount";
});

$(document).on("click", "#newAssistAcc", function() {
	location.href=urlPath+"physician/physicianassistantaccount";
});


try	{
	if ($("#notificationSummaryTable").length > 0) 
	{
		var notifyStartDate=$("#notifyStartDate").val();
		var notifyEndDate=$("#notifyEndDate").val();
		var notifyRxNo=$("#notifyRxNo").val();
		var notifyReferenceNo=$("#notifyReferenceNo").val();
		var notifyPatientName=$("#notifyPatientName").val();
		var notifyPhysicianName=$("#notifyPhysicianName").val();
		var notificationtype=$("#notificationtype").val();
		
		//alert("Test===="+urlPath+"notification/getAdminNotificationData")
		
		// Physician login Summary works
		var notificationSummaryTable = $("#notificationSummaryTable").DataTable({
			"processing" : true,
			responsive:true,
			"serverSide" : true,
			"dom":'<"top">rt<"bottom"pl><"clear">',
			"lengthMenu" : [ [ 3, 5, 10, 15], [ 3, 5, 10, 15] ],
			/*"pagingType": "full_numbers",*/
			// numbers --> only nos, simple --> prev/next btns, simple_numbers --> prev/next btns & nos,
			// full --> prev/next/first/last btns, full_numbers --> prev/next/first/last btns & nos,
			// first_last_numbers ---> first/last btns and nos.
			"pagingType": "numbers",  
			"ajax" : {
				"url" : urlPath+"/notification/getAdminNotificationData",
				"data": function ( d ) {
					d.notifyStartDate=notifyStartDate;
					d.notifyEndDate=notifyEndDate;
					d.notifyRxNo=notifyRxNo;
					d.notifyReferenceNo=notifyReferenceNo;
					d.notifyPatientName=notifyPatientName;
					d.notifyPhysicianName=notifyPhysicianName;
					d.notificationtype=notificationtype;
				},
				"type" : "POST"
			},
			"oLanguage": {
		        "sEmptyTable":  "No records available"
		    },
			"columns" : [ 
			    { data : "notifyId", "visible": false },
			   
			 
	            { data : "notificationMessage","orderable":false, "sClass":"left_align","width":"380px"  },
	          /* { // render
	            	"orderable":false, "sClass":"left_align", "visible": true, "data": function (data, type, row, meta) {
			    		return '<a onclick="editNotificationRecord('+data.notifyRecordId+','+data.notifyRecordType+')" style="color:blue;font-weight:bold;cursor:pointer">' + data.patientName + '</a>';
			    	}
                },*/
	            { 
					"class"			:	"edit-control", 
				    "orderable"		:	false, 
				    "data"			:	null, 
				    "defaultContent":	""
				    	,"width":"20px"
				},
                { data : "notifyRecordId", "visible": false },
	            { data : "notifyRecordType", "visible": false }
			  
			],
			 "fnDrawCallback": function( oSettings ) {
		        	var targetOffset = $(".nav_menu").offset().top;
			        $("html,body").animate({scrollTop: targetOffset}, 300);
		        },
				"initComplete": function(settings, json) {
					//alert(json)
					//$('#notificationSummaryTable_processing').hide();
					// heightAdjust();
				},
			"error" : "Error while processing data...."
		});
		
		$("thead tr .edit-control").removeClass("edit-control");
	    
	    // For Edit Record.
	    $('#notificationSummaryTable tbody').on('click', 'tr td.edit-control', function() {
			var rowData=$(this).closest('tr').attr("id").replace("row_", "");
			var rowDataArr=rowData.split("_");
			var id=rowDataArr[0];
			var recordType=rowDataArr[1];
			
			if(recordType=="Patient"){
			$("#patientId").val(id);
			document.forms[0].action=urlPath+"patient/editPatientAccount";
			document.forms[0].submit();
			}
			if(recordType=="Physician"){
			$("#physicianId").val(id);
			document.forms[0].action=urlPath+"physician/editPhysicianProfile";
			document.forms[0].submit();
			}
			if(recordType=="Prescription"){
			$("#prescriptionId").val(id);
			document.forms[0].action=urlPath+"prescription/editPrescription";
			document.forms[0].submit();
			}
			if(recordType=="Order"){
			$("#orderId").val(id);
			document.forms[0].action=urlPath+"order/viewOrders";
			document.forms[0].submit();
			}
			if(recordType=="Invoice"){
			$("#invoiceId").val(id);
			document.forms[0].action=urlPath+"invoice/viewInvoice";
			document.forms[0].submit();
			}
			
		});
	    
		function editNotificationRecord(notifyRecordId,notifyRecordType) {
			/*$("#patientId").val(id);
			document.forms[0].action=urlPath+"patient/editPatientAccount";
			document.forms[0].submit();*/
		}
	
		$(document).on("click", "#newNotifySearch", function() {
			
			$("div.tooltip").remove();
			
			notifyStartDate=$("#notifyStartDate").val();
			notifyEndDate=$("#notifyEndDate").val();
			notifyRxNo=$("#notifyRxNo").val();
			notifyReferenceNo=$("#notifyReferenceNo").val();
			notifyPatientName=$("#notifyPatientName").val();
			notifyPhysicianName=$("#notifyPhysicianName").val();
			notificationtype=$("#notificationtype").val();
			
			notificationSummaryTable.ajax.url(urlPath+"/notification/getAdminNotificationData").draw();
		});
		
		$(document).on("click", "#clearNotifySearch", function() {
			$("div.tooltip").remove();
			
			$("#notifyStartDate").val("");
			$("#notifyEndDate").val("");
			$("#notifyRxNo").val("");
			$("#notifyReferenceNo").val("");
			$("#notifyPatientName").val("");
			$("#notifyPhysicianName").val("");
			$("#notificationtype").val("");
			
			
			notifyStartDate="";
			notifyEndDate="";
			notifyRxNo="";
			notifyReferenceNo="";
			notifyPatientName="";
			notifyPhysicianName="";
			notificationtype="";
		
			
			notificationSummaryTable.ajax.url(urlPath+"/notification/getAdminNotificationData").draw();
		});
	}
} catch(e) {
	 alert(" Exception in notificationSummaryTable()     ======     " + e);
}

function showOrHideNotificationSearch()
{
	try{
			var notificationtype=$("#notificationtype").val();
			
			if(notificationtype=="" || notificationtype=="All" || notificationtype=="Physician")
			{
				document.getElementById("notifyRxNoDiv").style.display="none";
				document.getElementById("notifyReferenceNoDiv").style.display="none";
				document.getElementById("notifyPatientNameDiv").style.display="none";
				
				notifyRxNo="";
				notifyReferenceNo="";
				notifyPatientName="";
				notifyPhysicianName="";
				
				$("#notifyRxNo").val("");
				$("#notifyReferenceNo").val("");
				$("#notifyPatientName").val("");
				$("#notifyPhysicianName").val("");
			
			}else if(notificationtype=="Patient")
			{
				document.getElementById("notifyRxNoDiv").style.display="none";
				document.getElementById("notifyReferenceNoDiv").style.display="none";
				document.getElementById("notifyPatientNameDiv").style.display="block";
				
				notifyRxNo="";
				notifyReferenceNo="";
				notifyPhysicianName="";
				
				$("#notifyRxNo").val("");
				$("#notifyReferenceNo").val("");
				$("#notifyPhysicianName").val("");
			
			}else if(notificationtype=="Prescription" || notificationtype=="Order" || notificationtype=="Invoice")
			{
			
				
				document.getElementById("notifyRxNoDiv").style.display="block";
				document.getElementById("notifyReferenceNoDiv").style.display="block";
				document.getElementById("notifyPatientNameDiv").style.display="block";
				
				notifyPhysicianName="";
				
				$("#notifyPhysicianName").val("");
			}
		
			$("#newNotifySearch").trigger("click");
			
		
	}catch(e)
	{
		alert(e)
	}
}