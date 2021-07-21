
$(document).on("click", "#newGroupdirector", function() {
	location.href="groupDirector";
});


var groupName = "";
var groupDirectorName = "";
var status = "";
try	{
	if ($("#groupDirectorSummaryTable").length > 0) {
		groupName = $("#groupName").val();
		groupDirectorName = $("#groupDirectorName").val();
		status = $("#status").val();
		
		// Group Director Summary works
		var summaryTable = $("#groupDirectorSummaryTable").DataTable({
			"processing" : true,
			"serverSide" : true,
			"dom":'<"top">rt<"bottom"pl><"clear">',
			"lengthMenu" : [ [ 10, 15, 25], [ 10, 15, 25] ],
			"pagingType": "full_numbers",
			"ajax" : {
				"url" : "getGroupDirectorDataList",
				"data": function ( d ) {
	                d.groupDirectorName = groupDirectorName;
	                d.groupName = groupName;
	                d.status = status;
	      		},
				"type" : "POST"
			},
			"oLanguage": {
		        "sEmptyTable":  "No records available"
		    },
			"columns" : [ 
			    { data : "groupDirectorId", "visible": false },
			    { 
					"class"			:	"edit-control", 
				    "orderable"		:	false, 
				    "data"			:	null, 
				    "defaultContent":	""
				}, 
			    { data : "groupDirectorName","orderable":false, "sClass":"left_align"   },
			    { data : "email","orderable":false, "sClass":"left_align",  "visible": false  },
			    { data : "groupName","orderable":false, "sClass":"left_align" },
			    { data : "status","orderable":false, "sClass":"left_align"  }
			],
			"columnDefs": [
			               { "width": "30px", "targets": [0,1] },
			               { "width": "100px", "targets": [2,4] },
			               { "width": "70px", "targets": [3,5] }
			],			
	        "fnDrawCallback": function( oSettings ) {
	        	var targetOffset = $(".nav_menu").offset().top;
		        $("html,body").animate({scrollTop: targetOffset}, 300);
	        },
			"initComplete": function(settings, json) {
				$('#groupDirectorSummaryTable_processing').hide();
				// heightAdjust();
			},
			"error" : "Error while processing data...."
		});

		// For Edit Record.
	    $("thead tr .edit-control").removeClass("edit-control");
		$('#groupDirectorSummaryTable tbody').on('click', 'tr td.edit-control', function() {
			var id=$(this).closest('tr').attr("id").replace("row_", "");
			$("#groupDirectorId").val(id);
			document.editPage.submit();
		});
		
		$(document).on("click", "#newSearch", function() {
			$("div.tooltip").remove();
			groupName = $("#groupName").val();
			groupDirectorName = $("#groupDirectorName").val();
			status = $("#status").val();
			summaryTable.ajax.url("getGroupDirectorDataList").draw();
		});
		
		$(document).on("click", "#clearSearch", function() {
			$("div.tooltip").remove();
			$("#groupName").val("0");
			$("#groupDirectorName").val("");
			$("#status").val("");
			groupName = "0";
			groupDirectorName = "";
			status = "";
			summaryTable.ajax.url("getGroupDirectorDataList").draw();
		});
	}
	
} catch(e) {
	// alert(" Exception in redrawDataTable()     ======     " + e);
}
