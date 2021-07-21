var groupName = "";
var status = "";
try	{
	groupName = $("#groupName_search").val();
	status = $("#status_search").val();
	
	var summaryTable = $("#summaryTable").DataTable({
		"processing" : true,
		"serverSide" : true,
		"dom":'<"top">rt<"bottom"pl><"clear">',
		"lengthMenu" : [ [ 10, 15, 25], [ 10, 15, 25] ],
		"pagingType": "full_numbers",
		"ajax" : {
			"url" : "getGroupMasterData",
			"data": function ( d ) {
                d.groupName = groupName;
                d.status = status;
      		},
			"type" : "POST"
		},
		"oLanguage": {
	        "sEmptyTable":  "No records available"
	    },
		"columns" : [ 
		    { data : "groupId", "visible": false },
		    { 
				"class"			:	"edit-control", 
			    "orderable"		:	false, 
			    "data"			:	null, 
			    "defaultContent":	""
			},
			{ data : "groupName","orderable":false, "sClass":"left_align"   },
		    { data : "groupDirectorName","orderable":false, "sClass":"left_align" },
		    { data : "contactName","orderable":false, "sClass":"left_align" },
		    { data : "email","orderable":false, "sClass":"left_align", "visible": false },
		    { data : "mobile","orderable":false, "sClass":"left_align", "visible": false },
		    { data : "status","orderable":false, "sClass":"left_align" },
		],
		"columnDefs": [
		               { "width": "50px", "targets": [0,1] },
		               { "width": "100px", "targets": [2,3] },
		               { "width": "70px", "targets": [4,5,6,7] }
		],
		"initComplete": function(settings, json) {
			$('#summaryTable_processing').hide();
		},
        "fnDrawCallback": function( oSettings ) {
        	var targetOffset = $(".summaryTop").offset().top;
	        $("html,body").animate({scrollTop: targetOffset}, 300);
        },
		"createdRow": function ( row, data, index ) {
			if ( data['status'] == 'Inactive' ) {
				$(row).find(".delete-control").removeClass('delete-control');
            }
        },
		"error" : "Error while processing data...."
	});
	$("thead tr .edit-control").removeClass("edit-control");
    $("thead tr .delete-control").removeClass("delete-control");
    
    // For Edit Record.
    $('#summaryTable tbody').on('click', 'tr td.edit-control', function() {
		var id=$(this).closest('tr').attr("id").replace("row_", "");
		$("#groupId").val(id);
		document.editPage.submit();
	});
	
	$(document).on("click", "#newSearch", function() {
		$("div.tooltip").remove();
		groupName = $("#groupName_search").val();
		status = $("#status_search").val();
		
		$(".alert-success").remove();
		$(".alert-danger").remove();
		
		summaryTable.ajax.url("getGroupMasterData").draw();
	});
	
	$(document).on("click", "#clearSearch", function() {
		$("div.tooltip").remove();
		groupName = "";
		status = "";
		
		$("#groupName_search").val("");
		$("#status_search").val("");
		
		$(".alert-success").remove();
		$(".alert-danger").remove();
		
		summaryTable.ajax.url("getGroupMasterData").draw();
	});	
} catch(e) {
	// alert(" Exception in redrawDataTable()     ======     " + e);
}

$(document).on("click", "#newGroup", function() {
	$("#groupId").val("0");
	document.editPage.action="newGroupMasterAccount";	
	document.editPage.submit();
});


/*
$(document).on("click", "#newGroupAcc", function() {
	location.href="groupMasterSummary";
});


jQuery(function($){
   $("#phone").mask("999-999-9999");
   $("#mobile").mask("999-999-9999");
});

$(document).ready(function() {
	try{
		if(errmessage!=null && errmessage.length>0 && errmessage.indexOf("Group name already exist")!=-1)
			{
				//set back the form values in screen when form validation fails
				$("#groupName").val(errgroupName);
				$("#groupDirectorName").val(errgroupDirectorId);
				$("#status").val(errstatus);
				$("#contactName").val(errcontactName);
				$("#email").val(erremail);
				$("#mobile").val(errmobile);
				$("#groupId").val(errgroupId);
				
			}
	
	}catch(e)
	{
		
	}
	
    $('#groupMasterForm').bootstrapValidator({
        fields: {
        	 groupName: {
                 validators: {
                     notEmpty: {
                     	message: $("#err_group_name").val()
                     }
                 }
             },
            status: {
                validators: {
                    notEmpty: {
                    	message: $("#err_status").val()
                    }
                }
            },
            contactName: {
                validators: {
                    notEmpty: {
                    	message: $("#err_contact_name").val()
                    }
                }
            },            
            email: {
                validators: {
                    notEmpty: {
                    	message: $("#err_email").val()
                    },
                    emailAddress: {
                        message: $("#err_email_format").val()
                    }
                }
            },
			mobile: {
                validators: {
                	 notEmpty: {
                     	message: $("#err_mobile").val()
                     },
                    phone: {
                        country: 'US',
                        message: $("#err_mobile_format").val()
                    }                    
                }
			}			
        }
    });
});

function checkValues() {
	// $("#submitgroupmasterHidden").click();	
	var validatorObj = $('#groupMasterForm').data('bootstrapValidator');
    validatorObj.validate();
    return validatorObj.isValid();
}

*/

	
	