
$(document).on("click", ".goSummary", function() {
	location.href = "clinicSummary";
});

jQuery(function($){
   $("#phone").mask("999-999-9999");
   $("#zipCode").mask("99999-9999");
   $("#fax").mask("999-999-9999");
});

$(document).on("click", ".goNewRec", function() {
	location.href = "clinicAccount";
});

$(document).ready(function() {
	var status=$('#status').val();
	if(status!="Active")
		document.getElementById("groupId").style.pointerEvents="none";
	
	
	
    $('#clinicForm').bootstrapValidator({
        fields: {
        	clinicName: {
                validators: {
                    notEmpty: {
                    	message: $("#err_clinic_name").val()
                    }
                }
            },
           /* location: {
                validators: {
                    notEmpty: {
                    	message: $("#err_location").val()
                    }
                }
            },*/
            contactName: {
                /*validators: {
                    notEmpty: {
                    	message: $("#err_contactName").val()
                    }
                }*/
            	validators: {
            		callback: {
    	                message:  $("#err_contactName").val(),
    	                callback: function (value, validator, $field) {
                    		var status = $("#status").val();
                    		if (status != "New" && status != "Inactive") {
                    			if (value == "")
                    				return false;
                    			else
                    				return true;
                    		} else {
                    			return true;
                    		}
    	                }
    	            }
            	}
            },
            address: {
                validators: {
                    /*notEmpty: {
                    	message: $("#err_address").val()
                    }*/
                	callback: {
    	                message:  $("#err_address").val(),
    	                callback: function (value, validator, $field) {
                    		var status = $("#status").val();
                    		if (status != "New" && status != "Inactive") {
                    			if (value == "")
                    				return false;
                    			else
                    				return true;
                    		} else {
                    			return true;
                    		}
    	                }
    	            }
                }
            },            
            city: {
                validators: {
                    /*notEmpty: {
                    	message: $("#err_city").val()
                    }*/
                	callback: {
    	                message:  $("#err_city").val(),
    	                callback: function (value, validator, $field) {
                    		var status = $("#status").val();
                    		if (status != "New" && status != "Inactive") {
                    			if (value == "")
                    				return false;
                    			else
                    				return true;
                    		} else {
                    			return true;
                    		}
    	                }
    	            }
                }
            },
            state: {
            	validators: {
                    /*notEmpty: {
                        message: $("#err_state").val()
                    },
                    stringLength: {
                        min: 2,
                        message: $("#err_state").val()
                    }*/
            		callback: {
    	                message:  $("#err_state").val(),
    	                callback: function (value, validator, $field) {
                    		var status = $("#status").val();
                    		if ( (status != "New" && status != "Inactive") && value == "0") {
                    			return false;
                    		} else {
                    			return true;
                    		}
    	                }
    	            }
                }
            },
            zipCode: {
            	validators: {
            		/*notEmpty: {
                    	message: $("#err_zip").val()
                    },
            		zipCode: {
                        country: 'US',
                        message: $("#err_zip_format").val()
                    }*/
            		callback: {
		                message:  $("#err_zip").val(),
		                callback: function (value, validator, $field) {
		                	var status = $("#status").val();
		                	if (status != "New" && status != "Inactive") {
		                		if (value != ""){
		                			if (value.length == 5 || value.length == 10)
		                				return true;
		                			else {
		                				return {
	                        				valid:false,
	                        				message: $("#err_zip_format").val()
	                        			}
		                			}
		                		}   
			                    else {
			                    	return {
	                    				valid:false,
	                    				message: $("#err_zip").val()
	                    			}
			                    }
		                	} else {
			                	if (value == "" || value.length == 5 || value.length == 10)
			                		return true;
			                	else {
	                				return {
                        				valid:false,
                        				message: $("#err_zip_format").val()
                        			}
	                			}
			                }
		                }
		            }
                }
            },
			phone: {
                validators: {
                	/*notEmpty: {
                    	message: $("#err_phone").val()
                    },
                	phone: {
                        country: 'US',
                        message: $("#err_phoneformat").val()
                    } */ 
                	callback: {
    	                message:  $("#err_phone").val(),
    	                callback: function (value, validator, $field) {
                    		var status = $("#status").val();
                    		if ( (status != "New" && status != "Inactive")) {
                    			if (value == "") {
                    				return false;
                    			} else if (value.length != 12) {
                    				return {
	                    				valid:false,
	                    				message: $("#err_phoneformat").val()
	                    			}
                    			} else 
                    				return true;
                    		} else {
                    			if (value == "" || value.length == 12) {
                    				return true;
                    			} else {
                    				return {
	                    				valid:false,
	                    				message: $("#err_phoneformat").val()
	                    			}
                    			}
                    		}
    	                }
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
            groupId: {
                validators: {
                    /*notEmpty: {
                    	message: $("#err_group_name").val()
                    },
                    greaterThan: {
                        value: 0,
                        inclusive: false,
                        message: $("#err_group_name").val()
                    }*/
                	callback: {
    	                message:  $("#err_group_name").val(),
    	                callback: function (value, validator, $field) {
                    		var status = $("#status").val();
                    		if (status == "Active") {
                    			if (value == "0")
                    				return false;
                    			else
                				{
                					var newGroupId=$("#newGroupId").val();
                					var groupId=$("#groupId").val();
                					if(groupId==newGroupId)
                						{
	                						return {
	    	                    				valid:false,
	    	                    				message: $("#err_validgroup").val()
	    	                    			}
                						}else
                							return true;
                				}
                    			
                    				
                    		} else {
                    			return true;
                    		}
    	                }
    	            }
                }
            },
            email: {
                validators: {
                    /*notEmpty: {
                        message: $("#err_email").val()
                    },*/
                    emailAddress: {
                        message: $("#err_email_format").val()
                    },
                	callback: {
    	                message:  $("#err_email").val(),
    	                callback: function (value, validator, $field) {
                    		var status = $("#status").val();
                    		if ( (status != "New" && status != "Inactive") && value == "") {
                    			return false;
                    		} else {
                    			return true;
                    		}
    	                }
    	            }
                }
            },
            fax: {
                validators: {
                	/*notEmpty: {
                		message: $("#err_fax").val()
                    },
                    phone: {
                        country: 'US',
                        message: $("#err_fax_format").val()
                    },*/
                    callback: {
    	                message:  $("#err_fax").val(),
    	                callback: function (value, validator, $field) {
                    		var status = $("#status").val();
                    		if ( (status != "New" && status != "Inactive")) {
                    			if (value == "") {
                    				return false;
                    			} else if (value.length != 12) {
                    				return {
	                    				valid:false,
	                    				message: $("#err_fax_format").val()
	                    			}
                    			} else 
                    				return true;
                    		} else {
                    			if (value == "" || value.length == 12) {
                    				return true;
                    			} else {
                    				return {
	                    				valid:false,
	                    				message: $("#err_fax_format").val()
	                    			}
                    			}
                    		}
    	                }
    	            }
                }
            }
        }
    }).on('error.form.bv', function(e) {
    	$(".formErrorMsg").show();
    })
    .on('success.form.bv', function(e) {
        $(".formErrorMsg").hide();
    })
    .on('error.field.bv', function(e, data) {
        if (data.bv.getInvalidFields().length == 0)
        	$(".formErrorMsg").hide();
        else
        	$(".formErrorMsg").show();
    })
    .on('success.field.bv', function(e, data) {
        if (data.bv.getInvalidFields().length == 0)
        	$(".formErrorMsg").hide();
        else
        	$(".formErrorMsg").show();
    })
    .on('status.field.bv', function(e, data) {
        if (data.bv.getInvalidFields().length == 0)
        	$(".formErrorMsg").hide();
        else
        	$(".formErrorMsg").show();
    });
});


$(document).on("change", "#status", function() {
	$('#clinicForm').bootstrapValidator('revalidateField', 'clinicName');
	$('#clinicForm').bootstrapValidator('revalidateField', 'contactName');
	$('#clinicForm').bootstrapValidator('revalidateField', 'address');
	$('#clinicForm').bootstrapValidator('revalidateField', 'city');
	$('#clinicForm').bootstrapValidator('revalidateField', 'state');
	$('#clinicForm').bootstrapValidator('revalidateField', 'zipCode');
	$('#clinicForm').bootstrapValidator('revalidateField', 'phone');
	$('#clinicForm').bootstrapValidator('revalidateField', 'groupId');
	$('#clinicForm').bootstrapValidator('revalidateField', 'email');
	$('#clinicForm').bootstrapValidator('revalidateField', 'fax');
});




/*******************  Loading physician list for clinic *****************/
var clinicId= $("#clinicId").val();
var physicianName = "";
var physicianTable = $("#clinicPhysicianTable").DataTable({
	"processing" : true,
	"serverSide" : true,
	"dom":'<"top">rt<"bottom"pl><"clear">',
	"lengthMenu" : [ [ 10, 15, 25], [ 10, 15, 25] ],
	"pagingType": "full_numbers",
	"ajax" : {
		"url" : "getPhysicianListByClinicData",
		"data": function ( d ) {
            d.clinicId = clinicId;
            d.physicianName=physicianName;
		},
		"type" : "POST"
	},
	"oLanguage": {
        "sEmptyTable":  "No records available"
    },
	"columns" : [ 
	    { data : "physicianId", "visible": false },
	    { data : "physicianName","orderable":true, "sClass":"left_align edit_link" },
	    { data : "phone","orderable":false, "sClass":"left_align" },
	    { data : "email","orderable":false, "sClass":"left_align" }
	],
	"initComplete": function(settings, json) {
		$('#clinicPhysicianTable_processing').hide();
	},
	"error" : "Error while processing data...."
});

$("#clinicPhysicianTable thead tr .edit_link").removeClass("edit_link");
$('#clinicPhysicianTable tbody').on('click', 'tr td.edit_link', function() {
	var id=$(this).closest('tr').attr("id").replace("row_", "");
	// $("#physicianId").val(id);
	// document.physicianPage.submit();
	document.location = urlPath + "/physician/viewPhysicianProfile?id="+id;
});

$(document).on("click", "#phySearch", function() {
	$("div.tooltip").remove();
	
	physicianName = $("#physicianName").val();
	
	physicianTable.ajax.url("getPhysicianListByClinicData").draw();
});

$(document).on("click", "#phyClearSearch", function() {
	$("div.tooltip").remove();
	
	$("#physicianName").val("");
	
	physicianName = "";
	
	physicianTable.ajax.url("getPhysicianListByClinicData").draw();
});

/*******************  Loading physician assistant list for clinic *****************/
var assistantName = "";
var assistantTable = $("#clinicAssistantTable").DataTable({
	"processing" : true,
	"serverSide" : true,
	"dom":'<"top">rt<"bottom"pl><"clear">',
	"lengthMenu" : [ [ 10, 15, 25], [ 10, 15, 25] ],
	"pagingType": "full_numbers",
	"ajax" : {
		"url" : "getPhysicianAssistantListByClinicData",
		"data": function ( d ) {
            d.clinicId = clinicId;
            d.assistantName = assistantName;
		},
		"type" : "POST"
	},
	"oLanguage": {
        "sEmptyTable":  "No records available"
    },
	"columns" : [ 
	    { data : "assistantId", "visible": false },
	    { data : "assistantName","orderable":true, "sClass":"left_align edit_link" },
	    { data : "physicianName","orderable":false, "sClass":"left_align" },
	    { data : "phone","orderable":false, "sClass":"left_align" },
	    { data : "email","orderable":false, "sClass":"left_align" }
	],
	"initComplete": function(settings, json) {
		$('#clinicAssistantTable_processing').hide();
	},
	"error" : "Error while processing data...."
});
$("#clinicAssistantTable thead tr .edit_link").removeClass("edit_link");
$('#clinicAssistantTable tbody').on('click', 'tr td.edit_link', function() {
	var id=$(this).closest('tr').attr("id").replace("row_", "");
	$("#assistantId").val(id);
	// document.assistantPage.submit();
	document.location = urlPath + "/physician/viewAssistantPhysicianProfile?id="+id;
});

$(document).on("click", "#assSearch", function() {
	$("div.tooltip").remove();
	
	assistantName = $("#assistantName").val();
	
	assistantTable.ajax.url("getPhysicianAssistantListByClinicData").draw();
});

$(document).on("click", "#assClearSearch", function() {
	$("div.tooltip").remove();
	
	$("#assistantName").val("");
	
	assistantName = "";

	assistantTable.ajax.url("getPhysicianAssistantListByClinicData").draw();
});
