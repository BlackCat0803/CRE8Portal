var clinicName = "";
var status = "";
var state = "";
var email = "";
var groupName="0";

$(document).on("click", "#newClinicAcc", function() {
	location.href="clinicAccount";
}) ; 

function showSummary() {
	try	{
		clinicName = $("#clinicName").val();
		state = $("#state").val();
		status = $("#status").val();
		email = $("#email").val();
		groupName= $("#groupName").val();
		//alert(groupName)
		
		var summaryTable = $("#clinicSummaryTable").DataTable({
			"processing" : true,
			"serverSide" : true,
			"dom":'<"top">rt<"bottom"pl><"clear">',
			"lengthMenu" : [ [ 10, 15, 25], [ 10, 15, 25] ],
			"pagingType": "full_numbers",
			"ajax" : {
				"url" : "getClinicSummaryData",
				"data": function ( d ) {
	                d.clinicName = clinicName;
	                d.state = state;
	                d.status = status;
	                d.email = email;
	                d.groupName = groupName;
				},
				"type" : "POST"
			},
			"oLanguage": {
		        "sEmptyTable":  "No records available"
		    },
			"columns" : [ 
			    { data : "clinicId", "visible": false },
			    { 
					"class"			:	"edit-control", 
				    "orderable"		:	false, 
				    "data"			:	null, 
				    "defaultContent":	""
				}, 
				/*{ 
					"class"			:	"delete-control", 
				    "orderable"		:	false, 
				    "data"			:	null, 
				    "defaultContent":	""
				}, */
			    { data : "clinicName","orderable":false, "sClass":"left_align" },
			    { data : "email","orderable":false, "sClass":"left_align",  "visible": false },
			    
			    { data : "city","orderable":false, "sClass":"left_align" },
			    { data : "state","orderable":false, "sClass":"left_align" },

			    { data : "phone","orderable":false, "sClass":"left_align", "visible": false },
			    { data : "groupName","orderable":false, "sClass":"left_align" },
			    { data : "status","orderable":false, "sClass":"left_align" }
			],
			"columnDefs": [
			               { "width": "50px", "targets": [0,1] },
			               { "width": "100px", "targets": [2] },
			               { "width": "70px", "targets": [3,4,5,6,7,8] }
			],
			"createdRow": function ( row, data, index ) {
				if ( data['status'] == 'Inactive' ) {
					$(row).find(".delete-control").removeClass('delete-control');
	            }
				if ( data['status'] == 'Info Completed' ) {
					$(row).addClass('dataTableHighlight');
	            }
	        },
	        "fnDrawCallback": function( oSettings ) {
	        	var targetOffset = $(".nav_menu").offset().top;
		        $("html,body").animate({scrollTop: targetOffset}, 300);
	        },
			"initComplete": function(settings, json) {
				$('#clinicSummaryTable_processing').hide();
				// heightAdjust();
			},
			"error" : "Error while processing data...."
		});
	
		// For Edit Record.
	    $("thead tr .edit-control").removeClass("edit-control");
	    $("thead tr .delete-control").removeClass("delete-control");
		$('#clinicSummaryTable tbody').on('click', 'tr td.edit-control', function() {
			var id=$(this).closest('tr').attr("id").replace("row_", "");
			$("#clinicId").val(id);
			document.editPage.submit();
		});
		
		$('#clinicSummaryTable tbody').on('click', 'tr td.delete-control', function() {
			var id=$(this).closest('tr').attr("id").replace("row_", "");
			$("#clinicId").val(id);
			var clinicName = $(this).closest('tr').find("td:eq(2)").html();
			
			var alertTxt = "Are you sure do you want to delete this "+clinicName+" Clinic ? ";

			$.confirm({
				title: 'Confirm!',
			 	content: alertTxt,
				buttons: {
		 			confirm: function () {
		 				document.editPage.action = "deleteClinic";
		 				document.editPage.submit();			
		 			},
		 			cancel: function () {
		 				
		 			}
				}
			});
		});
		
		$(document).on("click", "#newSearch", function() {
			$("div.tooltip").remove();
			clinicName = $("#clinicName").val();
			state = $("#state").val();
			status = $("#status").val();
			email = $("#email").val();
			groupName = $("#groupName").val();
			summaryTable.ajax.url("getClinicSummaryData").draw();
		});
		
		$(document).on("click", "#clearSearch", function() {
			$("div.tooltip").remove();
			$("#clinicName").val("");
			$("#state").val("");
			$("#status").val("");
			$("#email").val("");
			
			clinicName = "";
			state = "";
			status = "";
			email = "";

			if(userType != "Physician" &&  userType != "Group Director") {
				$("#groupName").val("0");
				groupName="0";
			}
			summaryTable.ajax.url("getClinicSummaryData").draw();
		});
	} catch(e) {
		// alert(" Exception datatable     ======     " + e);
	}
}

showSummary();