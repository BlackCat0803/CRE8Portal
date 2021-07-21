$(document).on("click", ".goSummary", function() {
	location.href = "groupMasterSummary";
});

$(document).on("click", ".newGroup", function() {
	$("#groupId").val("0");
	document.groupMasterForm.action="newGroupMasterAccount";	
	document.groupMasterForm.submit();
});

jQuery(function($){
   $("#phone").mask("999-999-9999");
   $("#mobile").mask("999-999-9999");
});

var logoFileList;
$(document).on("change", "input[name=uploadLogoFile]", function(event) {
	logoFileList = event.target.files;
	//alert(logoFileList.length)
	groupLogo=logoFileList.length;
	$("#groupLogoFile").val(groupLogo);
	$('#groupMasterForm').bootstrapValidator('revalidateField', 'groupLogoFile');
});
var previousStatus="";
$(document).ready(function() {
	previousStatus=$("#status").val();
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
                     	message: $("#err_phone").val()
                     },
                    phone: {
                        country: 'US',
                        message: $("#err_phone_format").val()
                    }                    
                }
			},
			groupLogoFile: {
				 validators: {
	                 callback: {
			                message:  $("#err_group_logo").val(),
			                callback: function (value, validator, $field) {
			                	//alert("groupLogoFile==="+value+"====uploadLogoFile ===="+$("#uploadLogoFile").val())
			                	if ($("#status").val() == "Active") {
			                		
			                		if(($("#groupLogoFile").val()=="" || $("#groupLogoFile").val().indexOf("images/default_logo.jpg?")!=-1) && $("#uploadLogoFile").val()=="")
		                			{
			                			return false;
		                			
		                			} else
	                				{
		                				
		                				var x = document.getElementById("uploadLogoFile");
		                				//alert("============="+x.value)
		                				if(x.value!=""){
		                				
			                				var txt = "";
			                				var err = "";
			                				var isNotError = true;
			                				if ('files' in x) {
			                					//alert(x.files.length)
			                					if (x.files.length > 0) {
			                						for (var i = 0; i < x.files.length; i++) {
			                							var file = x.files[i];
			                							if ('size' in file) {
			                								txt += "size: " + file.size + " bytes <br>";
			                								// 1 MB is equal to 1048576 bytes
			                								// 2.5 MB is equal to 2621440 bytes 
			                								if (file.size > 2621440) {
			                									err += 'File Size should be less than 2.5 MB';
			                									isNotError = false;
			                									return {
			                	                                    valid: isNotError,
			                	                                    message: 'File Size should be less than 2.5 MB'
			                	                                };
			                								}
			                							}
			                							var fileExtension = file.name.substr(file.name
			                									.lastIndexOf('.') + 1);
			                							txt += "ext : " + fileExtension + " <br>";
			                							if (fileExtension != 'png' && fileExtension != 'PNG'
			                									&& fileExtension != 'gif' && fileExtension != 'GIF') {
			                								if (err != "")
			                									err += " and ";
			                								err += 'GIF or PNG files only allowed in logo image';
			                								isNotError = false;
			                								return {
		                	                                    valid: isNotError,
		                	                                    message: 'GIF or PNG files only allowed in logo image'
		                	                                };
			                							}
			                						}
			                					}
			                				}
		                				}
		                				
			                    		return true;
			                    		
	                				}
			                		
			                	} else
			                		return true;
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

/**************  Loading Physician list for group *************/ 
var groupId= $("#groupId").val();

var physicianName = "";
var physicianStatus = "";

var physicianTable = $("#groupPhysicianTable").DataTable({
	"processing" : true,
	"serverSide" : true,
	"dom":'<"top">rt<"bottom"pl><"clear">',
	"lengthMenu" : [ [ 10, 15, 25], [ 10, 15, 25] ],
	"pagingType": "full_numbers",
	"ajax" : {
		"url" : "getPhysicianListByGroupData",
		"data": function ( d ) {
            d.groupId = groupId;
            d.physicianName=physicianName;
            d.status=physicianStatus;
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
	    { data : "email","orderable":false, "sClass":"left_align" },
	    {
	    	"data": null, "orderable": false,"render": function (data, type, row) {
	    		var ddl = "<select name='newGroupSet' class='newGroupSet'  style='width:120px;' >";
                for (var i = 0; i < $("#dropDownGroupId option").length; i++) {
                	ddl = ddl + "<option value='"+$("#dropDownGroupId option").eq(i).val()+"'>"+$("#dropDownGroupId option").eq(i).text()+"</option>";
                }
                ddl += "</select>";
                ddl += ' <button type="button" class="btn btn-primary savePhysicianNewGroup" >Save</button>';
                return ddl;
	    	}
	    },
	    { data : "status","orderable":false, "sClass":"left_align" },
	    {
	    	"data": null, "orderable": false, "sClass":"left_align", "render": function (data, type, row) {
	    		if (row.status == 'Approved') {
	    			return ' <button type="button" class="btn btn-primary deniedPhysician">Deny</button>';
	    		} else if (row.status == 'Denied') {
	    			return ' <button type="button" class="btn btn-primary approvedPhysician">Approve</button>';
	    		} else {
	    			return "";
	    		}
	    	}
	    }
	],
	"columnDefs": [
	       { "width": "10px", "targets": [0] },
	       { "width": "100px", "targets": [1] },
	       { "width": "80px", "targets": [5] },
	       { "width": "80px", "targets": [3] },
	       { "width": "140px", "targets": [4] },
	       { "width": "70px", "targets": [2] },
	       { "width": "70px", "targets": [6] },
	],
	"initComplete": function(settings, json) {
		$('#groupPhysicianTable_processing').hide();
	},
	"error" : "Error while processing data...."
});

$("#groupPhysicianTable thead tr .edit_link").removeClass("edit_link");
$('#groupPhysicianTable tbody').on('click', 'tr td.edit_link', function() {
	var id=$(this).closest('tr').attr("id").replace("row_", "");
	// $("#physicianId").val(id);
	// document.physicianPage.submit();
	document.location = urlPath + "/physician/viewPhysicianProfile?id="+id;
});

$(document).on("click", "#phySearch", function() {
	$("div.tooltip").remove();
	
	physicianName = $("#physicianName").val();
	physicianStatus = $("#physicianStatus").val();
	
	physicianTable.ajax.url("getPhysicianListByGroupData").draw();
});

$(document).on("click", "#phyClearSearch", function() {
	$("div.tooltip").remove();
	
	$("#physicianName").val("");
	$("#physicianStatus").val("");
	
	physicianName = "";
	physicianStatus = "";
	physicianTable.ajax.url("getPhysicianListByGroupData").draw();
});

function fnPhysicianShowHide( iCol )
{
		
	 // Get the column API object
    var column = physicianTable.column(iCol);

    // Toggle the visibility
    column.visible( ! column.visible() )
}

$(document).on("click", ".savePhysicianNewGroup", function() {
	var physicianId=$(this).closest('tr').attr("id").replace("row_", "");
	var newGroupId=$(this).closest('tr').find("option:selected").val();
	var physicianName = $(this).closest('tr').find(".edit_link").html();
	var groupId = $("#groupId").val();
	
	if (parseInt(physicianId) > 0 && parseInt(newGroupId) > 0) {
		
		var data = new FormData();
		data.append('physicianId',  physicianId);
		data.append('groupId',  groupId);
		data.append('physicianName',  physicianName);
		data.append('newGroupId',  newGroupId);
		
		var xhr = new XMLHttpRequest();
		xhr.open('POST', 'changePhysicianGroup', true);
		xhr.send(data);
		xhr.onload = function () {
			if (this.responseText != "") {
				$.alert({
		 		    title: 'Save Info',
		 		    content: this.responseText,
		 		});
				physicianTable.ajax.url("getPhysicianListByGroupData").draw();
			}
		}
	} else { 
		$.alert({
 		    title: 'Save Info',
 		    content: "Select new Group to save",
 		});
	}
});



$(document).on("click", ".approvedPhysician", function() {
	var physicianId=$(this).closest('tr').attr("id").replace("row_", "");
	var physicianName = $(this).closest('tr').find(".edit_link").html();
	
	if (parseInt(physicianId) > 0) {
		var data = new FormData();
		data.append('physicianId',  physicianId);
		data.append('physicianName',  physicianName);
		data.append('status',  "Approved");
		
		var xhr = new XMLHttpRequest();
		xhr.open('POST', 'changePhysicianStatusFromGroup', true);
		xhr.send(data);
		xhr.onload = function () {
			if (this.responseText != "") {
				$.alert({
		 		    title: 'Save Info',
		 		    content: this.responseText,
		 		});
				physicianTable.ajax.url("getPhysicianListByGroupData").draw();
			}
		}
	}
});


/*$(document).on("click", ".deniedPhysician", function() {
	var physicianId=$(this).closest('tr').attr("id").replace("row_", "");
	var physicianName = $(this).closest('tr').find(".edit_link").html();
	
	if (parseInt(physicianId) > 0) {
		var data = new FormData();
		data.append('physicianId',  physicianId);
		data.append('physicianName',  physicianName);
		data.append('status',  "Denied");
		
		var xhr = new XMLHttpRequest();
		xhr.open('POST', 'changePhysicianStatusFromGroup', true);
		xhr.send(data);
		xhr.onload = function () {
			if (this.responseText != "") {
				$.alert({
		 		    title: 'Save Info',
		 		    content: this.responseText,
		 		});
				physicianTable.ajax.url("getPhysicianListByGroupData").draw();
			}
		}
	}
});*/

var $deniedThis="";
var $approvedThis="";
$(document).on("click", ".deniedPhysician", function() {
	$deniedThis=$(this);
	var physicianId=$(this).closest('tr').attr("id").replace("row_", "");
	var physicianName = $(this).closest('tr').find(".edit_link").html();
	
	if (parseInt(physicianId) > 0) {
		
		var data = new FormData();
		data.append('physicianId',  physicianId);
		data.append('physicianName',  physicianName);
		data.append('status',  "Denied");
		
	 	var xhr = new XMLHttpRequest();
	 	xhr.open('POST', urlPath+'/physician/fetchOtherPhysiciansofGroupbyAjax', true);
	 	xhr.send(data);
	 	xhr.onload = function () {
	 		
	 		var data = this.responseText;
	 		var responseData = $.parseJSON(data);
	 		$('#otherPhysicianId').empty();
	 		var option = $("<option />");
	 		option.attr("value", "0").text("Select");
	 		$('#otherPhysicianId').append(option);
	 		//alert(responseData)
	 		for ( var i = 0; i < responseData.length; i++) {
	 	  		var obj = responseData[i];
         		//alert(obj);
         		//alert(obj.id+"==========="+obj.physicianName)
        		var option = $("<option />");
    	 		option.attr("value", obj.id).text(obj.physicianName);
    	 		$('#otherPhysicianId').append(option);
	 	    }
	 	    
	 	    popup8();
	 	   
	 	};
	}
	
});



function callReassignPhysician() {
	try{
		var otherPhysicianId=$("#otherPhysicianId").val();	
		//alert(otherPhysicianId)
		if(otherPhysicianId=="0")
		{	
			document.getElementById("errOtherPhysicianMsg").style.display="block";
			
		}else{
			
			var physicianId=$deniedThis.closest('tr').attr("id").replace("row_", "");
			var physicianName = $deniedThis.closest('tr').find(".edit_link").html();
			
			if (parseInt(physicianId) > 0) {
				
				var data = new FormData();
				data.append('otherPhysicianId',  otherPhysicianId);
				data.append('physicianId',  physicianId);
	    		
	    	 	var xhr = new XMLHttpRequest();
	    	 	xhr.open('POST', urlPath+'/physician/reAssignPhysicianbyAjax', true);
	    	 	xhr.send(data);
	    	 	xhr.onload = function () {
	    	 		if (this.responseText != "") {
	    	 			
	    	 			$.alert({
				 		    title: '',
				 		    content: $("#info_reassignphysician").val(),
				 		});
	    	 			
	    	 			//physicianTable.ajax.url("getPhysicianListByGroupData").draw();
						div_hide8();
	    	 		}
	    	 	};
					
				
				
			}
		}
		
	}catch(e)
	{
		alert(e)
	}
}


function callDeactivatePhysician() {
	try{
		
		var physicianId=$deniedThis.closest('tr').attr("id").replace("row_", "");
		var physicianName = $deniedThis.closest('tr').find(".edit_link").html();
		
		if (parseInt(physicianId) > 0) {
			
			var data = new FormData();
			data.append('physicianId',  physicianId);
    		
    	 	var xhr = new XMLHttpRequest();
    	 	xhr.open('POST', urlPath+'/physician/deActivatePhysicianbyAjax', true);
    	 	xhr.send(data);
    	 	xhr.onload = function () {
    	 		if (this.responseText != "") {
    	 			
    	 			$.alert({
			 		    title: '',
			 		    content: $("#info_deactivatephysician").val(),
			 		});
    	 			
    	 			//physicianTable.ajax.url("getPhysicianListByGroupData").draw();
					div_hide8();
    	 		}
    	 	};
			
		}
		
	}catch(e)
	{
		alert(e)
	}
}


function popup8(message) {
	// get the screen height and width  
	var maskHeight = $(document).height();
	//var maskHeight = 1261;  
	var maskWidth = $(window).width();
	
	// calculate the values for center alignment
	var dialogTop =  (maskHeight/3) - ($('#dialog-box8').height());  
	var dialogLeft = (maskWidth/2) - ($('#dialog-box8').width()/2); 

	
	dialogTop=dialogTop-400;
	dialogLeft=dialogLeft-100;
	
	// assign values to the overlay and dialog box
	$('#dialog-overlay8').css({height:maskHeight, width:maskWidth}).show();
	$('#dialog-box8').css({top:dialogTop, left:dialogLeft}).show();
	
	// display the message
	$('#dialog-message8').html(message);
}

//function to hide Popup
function div_hide8(){
	physicianTable.ajax.url("getPhysicianListByGroupData").draw();
	$('#dialog-overlay8, #dialog-box8').hide();
	return true;
}




/*******************  Loading physician assistant list for group *****************/
var assistantName = "";
var assistantStatus = "";

var assistantTable = $("#groupAssistantTable").DataTable({
	"processing" : true,
	"serverSide" : true,
	"dom":'<"top">rt<"bottom"pl><"clear">',
	"lengthMenu" : [ [ 10, 15, 25], [ 10, 15, 25] ],
	"pagingType": "full_numbers",
	"ajax" : {
		"url" : "getPhysicianAssistantListByGroupData",
		"data": function ( d ) {
			d.groupId = groupId;
			d.status = assistantStatus;
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
	    { data : "email","orderable":false, "sClass":"left_align" },
	    {
	    	"data": null, "orderable": false,"render": function (data, type, row) {
	    		var ddl = "<select name='newGroupSet' class='newGroupSet' style='width:120px;' >";
                for (var i = 0; i < $("#dropDownGroupId option").length; i++) {
                	ddl = ddl + "<option value='"+$("#dropDownGroupId option").eq(i).val()+"'>"+$("#dropDownGroupId option").eq(i).text()+"</option>";
                }
                ddl += "</select>";
                ddl += ' <button type="button" class="btn btn-primary saveAssistantNewGroup" >Save</button>';
                return ddl;
	    	}
	    },
	    { data : "status","orderable":false, "sClass":"left_align" },
	    {
	    	"data": null, "orderable": false, "sClass":"left_align", "render": function (data, type, row) {
	    		if (row.status == 'Active') {
	    			return ' <button type="button" class="btn btn-primary inActPhysician">Inactivate</button>';
	    		} else if (row.status == 'Inactive') {
	    			return ' <button type="button" class="btn btn-primary actPhysician">Activate</button>';
	    		}
	    	}
	    }
	],
	"columnDefs": [
	       { "width": "10px", "targets": [0] },
	       { "width": "100px", "targets": [1] },
	       { "width": "100px", "targets": [2] },
	       { "width": "70px", "targets": [3] },
	       { "width": "100px", "targets": [4] },
	       { "width": "175px", "targets": [5] },
	       { "width": "45px", "targets": [6] },
	       { "width": "60px", "targets": [7] }
	],
	"initComplete": function(settings, json) {
		$('#groupAssistantTable_processing').hide();
	},
	"error" : "Error while processing data...."
});

$("#groupAssistantTable thead tr .edit_link").removeClass("edit_link");
$('#groupAssistantTable tbody').on('click', 'tr td.edit_link', function() {
	var id=$(this).closest('tr').attr("id").replace("row_", "");
	$("#assistantId").val(id);
	// document.assistantPage.submit();
	document.location = urlPath + "/physician/viewAssistantPhysicianProfile?id="+id;
});

$(document).on("click", "#assSearch", function() {
	$("div.tooltip").remove();
	
	assistantName = $("#assistantName").val();
	assistantStatus = $("#assistantStatus").val();
	
	assistantTable.ajax.url("getPhysicianAssistantListByGroupData").draw();
});

$(document).on("click", "#assClearSearch", function() {
	$("div.tooltip").remove();
	
	$("#assistantName").val("");
	$("#assistantStatus").val("");
	
	assistantName = "";
	assistantStatus = "";
	assistantTable.ajax.url("getPhysicianAssistantListByGroupData").draw();
});
function fnAssistantShowHide( iCol )
{
		
	 // Get the column API object
    var column = assistantTable.column(iCol);

    // Toggle the visibility
    column.visible( ! column.visible() )
}
$(document).on("click", ".saveAssistantNewGroup", function() {
	var assistantId=$(this).closest('tr').attr("id").replace("row_", "");
	var newGroupId=$(this).closest('tr').find("option:selected").val();
	var assistantName = $(this).closest('tr').find(".edit_link").html();
	var groupId = $("#groupId").val();
	
	if (parseInt(assistantId) > 0 && parseInt(newGroupId) > 0) {
		
		var data = new FormData();
		data.append('assistantId',  assistantId);
		data.append('groupId',  groupId);
		data.append('assistantName',  assistantName);
		data.append('newGroupId',  newGroupId);
		
		var xhr = new XMLHttpRequest();
		xhr.open('POST', 'changeAssistantGroup', true);
		xhr.send(data);
		xhr.onload = function () {
			if (this.responseText != "") {
				$.alert({
		 		    title: 'Save Info',
		 		    content: this.responseText,
		 		});
				assistantTable.ajax.url("getPhysicianAssistantListByGroupData").draw();
			}
		}
	} else { 
		$.alert({
 		    title: 'Save Info',
 		    content: "Select new Group to save",
 		});
	}
});


$(document).on("click", ".inActPhysician", function() {
	var assistantId=$(this).closest('tr').attr("id").replace("row_", "");
	var assistantName = $(this).closest('tr').find(".edit_link").html();
	
	if (parseInt(assistantId) > 0) {
		var data = new FormData();
		data.append('assistantId',  assistantId);
		data.append('assistantName',  assistantName);
		data.append('status',  "Inactive");
		
		var xhr = new XMLHttpRequest();
		xhr.open('POST', 'changeAssistantStatusFromGroup', true);
		xhr.send(data);
		xhr.onload = function () {
			if (this.responseText != "") {
				$.alert({
		 		    title: 'Save Info',
		 		    content: this.responseText,
		 		});
				assistantTable.ajax.url("getPhysicianAssistantListByGroupData").draw();
			}
		}
	}
});


$(document).on("click", ".actPhysician", function() {
	var assistantId=$(this).closest('tr').attr("id").replace("row_", "");
	var assistantName = $(this).closest('tr').find(".edit_link").html();
	
	if (parseInt(assistantId) > 0) {
		var data = new FormData();
		data.append('assistantId',  assistantId);
		data.append('assistantName',  assistantName);
		data.append('status',  "Active");
		
		var xhr = new XMLHttpRequest();
		xhr.open('POST', 'changeAssistantStatusFromGroup', true);
		xhr.send(data);
		xhr.onload = function () {
			if (this.responseText != "") {
				$.alert({
		 		    title: 'Save Info',
		 		    content: this.responseText,
		 		});
				assistantTable.ajax.url("getPhysicianAssistantListByGroupData").draw();
			}
		}
	}
});







/*******************  Loading Patient list for group *****************/
var patientName = "";
var patientStatus = "";

var patientTable = $("#groupPatientTable").DataTable({
	"processing" : true,
	"serverSide" : true,
	"dom":'<"top">rt<"bottom"pl><"clear">',
	"lengthMenu" : [ [ 10, 15, 25], [ 10, 15, 25] ],
	"pagingType": "full_numbers",
	"ajax" : {
		"url" : "getPatientListByGroupData",
		"data": function ( d ) {
			d.groupId = groupId;
			d.patientName = patientName;
			d.status = patientStatus;
		},
		"type" : "POST"
	},
	"oLanguage": {
        "sEmptyTable":  "No records available"
    },
	"columns" : [ 
	    { data : "patientId", "visible": false },
	    { data : "patientName","orderable":true, "sClass":"left_align edit_link" },
	    { data : "physicianName","orderable":false, "sClass":"left_align" },
	    { data : "phone","orderable":false, "sClass":"left_align" },
	    { data : "email","orderable":false, "sClass":"left_align" },
	    {
	    	"data": null, "orderable": false,"render": function (data, type, row) {
	    		var ddl = "<select name='newPhysicianSet' class='newGroupSet' style='width:120px;' >";
                for (var i = 0; i < $("#groupPhysicianId option").length; i++) {
                	ddl = ddl + "<option value='"+$("#groupPhysicianId option").eq(i).val()+"'>"+$("#groupPhysicianId option").eq(i).text()+"</option>";
                }
                ddl += "</select>";
                ddl += ' <button type="button" class="btn btn-primary savePatientNewGroup" >Save</button>';
                return ddl;
	    	}
	    },
	    { data : "status","orderable":false, "sClass":"left_align" },
	    {
	    	"data": null, "orderable": false, "sClass":"left_align", "render": function (data, type, row) {
	    		if (row.status == 'Approved') {
	    			return ' <button type="button" class="btn btn-primary deniedPatient">Deny</button>';
	    		} else if (row.status == 'Denied') {
	    			return ' <button type="button" class="btn btn-primary approvedPatient">Approve</button>';
	    		} else {
	    			return "";
	    		}
	    	}
	    }
	],
	"columnDefs": [
	       { "width": "10px", "targets": [0] },
	       { "width": "90px", "targets": [1] },
	       { "width": "90px", "targets": [2] },
	       { "width": "70px", "targets": [3] },
	       { "width": "90px", "targets": [4] },
	       { "width": "180px", "targets": [5] },
	       { "width": "90px", "targets": [6] },
	       { "width": "60px", "targets": [7] },
	],
	"initComplete": function(settings, json) {
		$('#groupPatientTable_processing').hide();
	},
	"error" : "Error while processing data...."
});

$("#groupPatientTable thead tr .edit_link").removeClass("edit_link");
$('#groupPatientTable tbody').on('click', 'tr td.edit_link', function() {
	var id=$(this).closest('tr').attr("id").replace("row_", "");
	$("#patientId").val(id);
	// document.patientPage.submit();
	document.location = urlPath + "/patient/viewPatientAccount?id="+id;
});

$(document).on("click", "#patientSearch", function() {
	$("div.tooltip").remove();
	
	patientName = $("#patientName").val();
	patientStatus = $("#patientStatus").val();
	
	patientTable.ajax.url("getPatientListByGroupData").draw();
});

$(document).on("click", "#patientClearSearch", function() {
	$("div.tooltip").remove();
	
	$("#patientName").val("");
	$("#patientStatus").val("");
	
	patientName = "";
	patientStatus = "";
	patientTable.ajax.url("getPatientListByGroupData").draw();
});
function fnPatientShowHide( iCol )
{
		
	 // Get the column API object
    var column = patientTable.column(iCol);

    // Toggle the visibility
    column.visible( ! column.visible() )
}
$(document).on("click", ".savePatientNewGroup", function() {
	var patientId=$(this).closest('tr').attr("id").replace("row_", "");
	var newPhysicianId=$(this).closest('tr').find("option:selected").val();
	var patientName = $(this).closest('tr').find(".edit_link").html();
	var groupId = $("#groupId").val();
	
	if (parseInt(patientId) > 0 && parseInt(newPhysicianId) > 0) {
		
		var data = new FormData();
		data.append('patientId',  patientId);
		data.append('groupId',  groupId);
		data.append('patientName',  patientName);
		data.append('newPhysicianId',  newPhysicianId);
		
		var xhr = new XMLHttpRequest();
		xhr.open('POST', 'changePatientPhysician', true);
		xhr.send(data);
		xhr.onload = function () {
			if (this.responseText != "") {
				$.alert({
		 		    title: 'Save Info',
		 		    content: this.responseText,
		 		});
				patientTable.ajax.url("getPatientListByGroupData").draw();
			}
		}
	} else { 
		$.alert({
 		    title: 'Save Info',
 		    content: "Select new Physician to save",
 		});
	}
});

$(document).on("click", ".deniedPatient", function() {
	var patientId=$(this).closest('tr').attr("id").replace("row_", "");
	var patientName = $(this).closest('tr').find(".edit_link").html();
	
	if (parseInt(patientId) > 0) {
		
		var data = new FormData();
		data.append('patientId',  patientId);
		data.append('patientName',  patientName);
		data.append('status',  "Denied");
		
		var xhr = new XMLHttpRequest();
		xhr.open('POST', 'changePatientStatusFromGroup', true);
		xhr.send(data);
		xhr.onload = function () {
			if (this.responseText != "") {
				$.alert({
		 		    title: 'Save Info',
		 		    content: this.responseText,
		 		});
				patientTable.ajax.url("getPatientListByGroupData").draw();
			}
		}
	}
});


$(document).on("click", ".approvedPatient", function() {
	var patientId=$(this).closest('tr').attr("id").replace("row_", "");
	var patientName = $(this).closest('tr').find(".edit_link").html();
	
	if (parseInt(patientId) > 0) {
		
		var data = new FormData();
		data.append('patientId',  patientId);
		data.append('patientName',  patientName);
		data.append('status',  "Approved");
		
		var xhr = new XMLHttpRequest();
		xhr.open('POST', 'changePatientStatusFromGroup', true);
		xhr.send(data);
		xhr.onload = function () {
			if (this.responseText != "") {
				$.alert({
		 		    title: 'Save Info',
		 		    content: this.responseText,
		 		});
				patientTable.ajax.url("getPatientListByGroupData").draw();
			}
		}
	}
});

function popup6(message) {
	// get the screen height and width  
	var maskHeight = $(document).height();
	//var maskHeight = 1261;  
	var maskWidth = $(window).width();
	
	// calculate the values for center alignment
	var dialogTop =  (maskHeight/3) - ($('#dialog-box6').height());  
	var dialogLeft = (maskWidth/2) - ($('#dialog-box6').width()/2); 
	
	/*dialogTop=dialogTop-800;
	dialogLeft=dialogLeft-200;*/
	dialogTop=dialogTop-540;
	dialogLeft=400;
	
	// assign values to the overlay and dialog box
	$('#dialog-overlay6').css({height:maskHeight, width:maskWidth}).show();
	$('#dialog-box6').css({top:dialogTop, left:dialogLeft}).show();
	
	// display the message
	$('#dialog-message6').html(message);
}

//function to hide Popup
function div_hide(){ 
	//$('#myModal').modal('hide');
	$('#dialog-overlay6, #dialog-box6').hide();
	$("#status").val("Active")
	return true;
}

function validateFormStatusEvent() {
	try{
		var status="";
		status=$("#status").val();
		
		if(status=="Inactive" && status!=previousStatus) {
			popup6("");
		}
		return false;
	} catch(e) {
		alert(e)
	}
}

function callReassignGroup() {
	try{
		var otherGroupId=$("#otherGroupId").val();	
		//alert(otherGroupId)
		if(otherGroupId=="0")
		{	
			document.getElementById("errOtherGroupMsg").style.display="block";
		}else{
			document.groupMasterForm.reAssignGroup.value="true";
			document.getElementById("errOtherGroupMsg").style.display="none";
			document.groupMasterForm.action="saveGroupMaster";	
			document.groupMasterForm.submit();
		}
		
	}catch(e)
	{
		alert(e)
	}
}


function callDeactivateGroup() {
	try{
		document.groupMasterForm.deactivateGroup.value="true";
		document.groupMasterForm.action="saveGroupMaster";	
		document.groupMasterForm.submit();
		
	}catch(e)
	{
		alert(e)
	}
}