var adminName = "";
var pharmacyName = "";
var userType = "";
var status = "";
try	{
	adminName = $("#adminName").val();
	pharmacyName = $("#pharmacyName").val();
	userType = $("#usertype").val();
	status = $("#status").val();
	
	var summaryTable = $("#summaryTable").DataTable({
		"processing" : true,
		"serverSide" : true,
		"dom":'<"top">rt<"bottom"pl><"clear">',
		"lengthMenu" : [ [ 10, 15, 25], [ 10, 15, 25] ],
		"pagingType": "full_numbers",
		"ajax" : {
			//"url" : "getAdminData?adminName="+adminName+"&pharmacy="+pharmacyName+"&userType="+userType+"&status="+status,
			"url" : "getAdminData",
			"data": function ( d ) {
                d.adminName = adminName;
                d.pharmacy = pharmacyName;
                d.userType = userType;
                d.status = status;
			},
			"type" : "POST"
		},
		"oLanguage": {
	        "sEmptyTable":  "No records available"
	    },
		"columns" : [ 
		    { data : "adminId", "visible": false },
		    { 
				"class"			:	"edit-control", 
			    "orderable"		:	false, 
			    "data"			:	null, 
			    "defaultContent":	""
			}, 
		    { data : "adminName","orderable":false, "sClass":"left_align"   },
		    { data : "pharmacyName","orderable":false, "sClass":"left_align"},
		    { data : "type","orderable":false, "sClass":"left_align" },
		    { data : "email","orderable":false, "sClass":"left_align",  "visible": false   },
		    { data : "mobile","orderable":false, "sClass":"left_align",  "visible": false },
		    { data : "status","orderable":false, "sClass":"left_align" }
		],
		"columnDefs": [
		               { "width": "30px", "targets": [0,1] },
		               { "width": "70px", "targets": [2,3,4,7] },
		               { "width": "100px", "targets": [5,6] }
		],
        "fnDrawCallback": function( oSettings ) {
        	var targetOffset = $(".nav_menu").offset().top;
	        $("html,body").animate({scrollTop: targetOffset}, 300);
        },
		"initComplete": function(settings, json) {
			$('#summaryTable_processing').hide();
			//heightAdjust();
		},
		"error" : "Error while processing data...."
	});
	
	// For Edit Record.
    $("thead tr .edit-control").removeClass("edit-control");
	$('#summaryTable tbody').on('click', 'tr td.edit-control', function() {
		var id=$(this).closest('tr').attr("id").replace("row_", "");
		// location.href = "editAdminAccount?id="+id;
		$("#adminId").val(id);
		document.editPage.submit();
	});
	
	$(document).on("click", "#newSearch", function() {
		$("div.tooltip").remove();
		adminName = $("#adminName").val();
		pharmacyName = $("#pharmacyName").val();
		userType = $("#usertype").val();
		status = $("#status").val();
		
		//summaryTable.ajax.url("getAdminData?adminName="+adminName+"&pharmacy="+pharmacyName+"&userType="+userType+"&status="+status).draw();
		summaryTable.ajax.url("getAdminData").draw();
	});

	$(document).on("click", "#clearSearch", function() {
		$("div.tooltip").remove();
		$("#adminName").val("");
		$("#pharmacyName").val("");
		$("#usertype").val("");
		$("#status").val("");
		
		adminName = "";
		pharmacyName = "";
		userType = "";
		status = "";
		//summaryTable.ajax.url("getAdminData?adminName="+adminName+"&pharmacy="+pharmacyName+"&userType="+userType+"&status="+status).draw();
		summaryTable.ajax.url("getAdminData").draw();
	});
} catch(e) {
	// alert(" Exception in redrawDataTable()     ======     " + e);
}

$(document).on("click", "#newAdminAcc", function() {
	location.href="adminaccount";
});
