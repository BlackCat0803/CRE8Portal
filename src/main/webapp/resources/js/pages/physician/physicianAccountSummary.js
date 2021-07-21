
$(document).on("click", "#newPhysicianAcc", function() {
	location.href="createPhysicianAccount";
});

var physicianName = "";
var deaSearch = "";
var status = "";
var groupId = "0";

try {
	var physicianNameVar = $("#physicianName").val();
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
		$("#physicianName").val(this.value)
	});

} catch (e) {
	// alert(e)
	// TODO: handle exception
}



try {
	var clinicNameVar = $("#clinicName").val();
	if (clinicNameVar == null || clinicNameVar == "") {
		clinicNameVar = "Select Clinic";
	}
	var cresults = [];
	$('#autoCompleterClinicId').select2({
		placeholder : clinicNameVar,
		minimumInputLength : 0,
		ajax : {
			url : urlPath + 'getAutoCompleteClinicList',
			dataType : 'json',
			data : function(params) {
				return {
					term : $.trim(params.term),
	            	  formId:0
				};
			},
			processResults : function(data) {
				//alert(data)
				cresults = [];
				data.forEach(function makeResults(element) {
					//alert(element)
					cresults.push({
						id : element.clinicName,
						text : element.clinicName
					});
				});
				return {
					results : cresults
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
		$("#clinicName").val(this.value)
	});

} catch (e) {
	// alert(e)
	// TODO: handle exception
}



try	{
	physicianName = $("#physicianName").val();
	deaSearch = $("#deaSearch").val();
	status = $("#status").val();
	groupId = $("#groupId").val();
	
	var dttable = $("#summaryTable").DataTable({
		"processing" : true,
		"serverSide" : true,
		"dom":'<"top">rt<"bottom"pl><"clear">',
		"lengthMenu" : [ [ 10, 15, 25], [ 10, 15, 25] ],
		"pagingType": "full_numbers",
		"ajax" : {
			//"url" : "getPhysicianSummaryData?physicianName="+physicianName+"&clinicName="+clinicName+"&status="+status+"&groupId="+groupId,
			"url" : "getPhysicianSummaryData",
			"data": function ( d ) {
                d.physicianName = physicianName;
                d.dea = deaSearch;
                d.status = status;
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
				"class"			:	"edit-control", 
			    "orderable"		:	false, 
			    "data"			:	null, 
			    "defaultContent":	""
			},
			/*{ data : "physicianName","orderable":false, "sClass":"left_align"   },*/
			{ data : "physicianNameWithGroupName","orderable":false, "sClass":"left_align phyname"   },
			{ data : "dea","orderable":false, "sClass":"left_align"  },
			{ data : "groupName","orderable":false, "sClass":"left_align" },
			{
		    	"data": null, "orderable": false,"render": function (data, type, row) {
		    		if (row.groupName != "") {
			    		var ddl = "<select name='newGroupSet' class='newGroupSet'  style='width:120px;' >";
		                for (var i = 0; i < $("#dropDownGroupId option").length; i++) {
		                	ddl = ddl + "<option value='"+$("#dropDownGroupId option").eq(i).val()+"'>"+$("#dropDownGroupId option").eq(i).text()+"</option>";
		                }
		                ddl += "</select>";
		                ddl += ' <button type="button" class="btn btn-primary savePhysicianNewGroup" >Save</button>';
		                ddl += ' <input type="hidden" class="btn btn-primary oldGroupId" value="'+row.groupId+'" />';
		                return ddl;
		    		} else {
		    			return "";
		    		}
		    	}
		    },
		    { data : "city", "visible": false  },
			{ data : "email","orderable":false, "sClass":"left_align", "visible": false  },
		    { data : "mobile","orderable":false, "sClass":"left_align", "visible": false  },
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
		               { "width": "30px", "targets": [1] },
		               { "width": "100px", "targets": [2] },
		               { "width": "70px", "targets": [3] },
		               { "width": "140px", "targets": [5] },
		               { "width": "80px", "targets": [4,6,7,8,9] },
		               { "width": "50px", "targets": [10] }
		],
		"createdRow": function ( row, data, index ) {
			if ( data['status'] == 'Profile Completed' ) {
				$(row).addClass('dataTableHighlight');
            }
        },
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
    $("thead tr .edit-control").removeClass("edit-control");
	$('#summaryTable tbody').on('click', 'tr td.edit-control', function() {
		var id=$(this).closest('tr').attr("id").replace("row_", "");
		$("#physicianId").val(id);
		document.editPage.submit();
		//location.href = "editPhysicianProfile?id="+id;
	});
	
	$(document).on("click", "#newSearch", function() {
		$("div.tooltip").remove();
		physicianName = $("#physicianName").val();
		deaSearch = $("#deaSearch").val();
		status = $("#status").val();
		groupId = $("#groupId").val();
		//dttable.ajax.url("getPhysicianSummaryData?physicianName="+physicianName+"&clinicName="+clinicName+"&status="+status+"&groupId="+groupId).draw();
		dttable.ajax.url("getPhysicianSummaryData").draw();
	});
	
	$(document).on("click", "#clearSearch", function() {
		$("div.tooltip").remove();
		$("#physicianName").val("");
		$("#physicianName").val("");
		$("#status").val("");
		$("#deaSearch").val("");
		if (document.getElementById("select2-autoCompleterPhysicianId-container"))
			document.getElementById("select2-autoCompleterPhysicianId-container").innerHTML='<span class="select2-selection__placeholder">Select Physician</span>';
		if (document.getElementById("select2-autoCompleterClinicId-container"))
			document.getElementById("select2-autoCompleterClinicId-container").innerHTML='<span class="select2-selection__placeholder">Select Clinic</span>';
		
		physicianName = "";
		deaSearch = "";
		status = "";
		if ($("#checkUserType").val() != "Group Director") {
			$("#groupId").val("0");
			groupId = "0";
		}
			
		//dttable.ajax.url("getPhysicianSummaryData?physicianName="+physicianName+"&clinicName="+clinicName+"&status="+status+"&groupId="+groupId).draw();
		dttable.ajax.url("getPhysicianSummaryData").draw();
	});
	
	function fnShowHide( iCol )
	{
			
		 // Get the column API object
        var column = dttable.column(iCol);
 
        // Toggle the visibility
        column.visible( ! column.visible() )
	}
	
	$(document).on("click", ".savePhysicianNewGroup", function() {
		var physicianId=$(this).closest('tr').attr("id").replace("row_", "");
		var newGroupId=$(this).closest('tr').find("option:selected").val();
		var physicianName = $(this).closest('tr').find(".phyname").html();
		var groupId = $(this).closest('tr').find(".oldGroupId").val();
		
		if (newGroupId == groupId) {
			$.alert({
	 		    title: 'Alert Info',
	 		    content: "Both Group and Change Group are same",
	 		});
		} else {
			if (parseInt(physicianId) > 0 && parseInt(newGroupId) > 0 && parseInt(groupId) > 0) {
				var data = new FormData();
				data.append('physicianId',  physicianId);
				data.append('groupId',  groupId);
				data.append('physicianName',  physicianName);
				data.append('newGroupId',  newGroupId);
				
				var xhr = new XMLHttpRequest();
				xhr.open('POST', urlPath + '/group/changePhysicianGroup', true);
				xhr.send(data);
				xhr.onload = function () {
					if (this.responseText != "") {
						$.alert({
				 		    title: 'Save Info',
				 		    content: this.responseText,
				 		});
						dttable.ajax.url("getPhysicianSummaryData").draw();
					}
				}
			} else { 
				$.alert({
		 		    title: 'Save Info',
		 		    content: "Select new Group to save",
		 		});
			}
		}
	});
	
	var $deniedThis="";
	var $approvedThis="";
	$(document).on("click", ".deniedPhysician", function() {
		$deniedThis=$(this);
		var physicianId=$(this).closest('tr').attr("id").replace("row_", "");
		var physicianName = $(this).closest('tr').find(".phyname").html();
		
		if (parseInt(physicianId) > 0) {
			
			var data = new FormData();
			data.append('physicianId',  physicianId);
    		
    	 	var xhr = new XMLHttpRequest();
    	 	xhr.open('POST', 'fetchOtherPhysiciansofGroupbyAjax', true);
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
				
			/*var data = new FormData();
			data.append('physicianId',  physicianId);
			data.append('physicianName',  physicianName);
			data.append('status',  "Denied");
			
			var xhr = new XMLHttpRequest();
			xhr.open('POST', urlPath + '/group/changePhysicianStatusFromGroup', true);
			xhr.send(data);
			xhr.onload = function () {
				if (this.responseText != "") {
					$.alert({
			 		    title: '',
			 		    content: this.responseText,
			 		});
					dttable.ajax.url("getPhysicianSummaryData").draw();
				}
			}*/
			
			
		}
		
	});


	$(document).on("click", ".approvedPhysician", function() {
		var $approvedThis=$(this);
		var physicianId=$(this).closest('tr').attr("id").replace("row_", "");
		var physicianName = $(this).closest('tr').find(".phyname").html();
		
		if (parseInt(physicianId) > 0) {
			var data = new FormData();
			data.append('physicianId',  physicianId);
			data.append('physicianName',  physicianName);
			data.append('status',  "Approved");
			
			var xhr = new XMLHttpRequest();
			xhr.open('POST', urlPath + '/group/changePhysicianStatusFromGroup', true);
			xhr.send(data);
			xhr.onload = function () {
				if (this.responseText != "") {
					$.alert({
			 		    title: '',
			 		    content: this.responseText,
			 		});
					dttable.ajax.url("getPhysicianSummaryData").draw();
				}
			}
		}
	});
	
	
	function popup8(message) {
		// get the screen height and width  
		var maskHeight = $(document).height();
		//var maskHeight = 1261;  
		var maskWidth = $(window).width();
		
		// calculate the values for center alignment
		var dialogTop =  (maskHeight/3) - ($('#dialog-box8').height());  
		var dialogLeft = (maskWidth/2) - ($('#dialog-box8').width()/2); 
	
		
		dialogTop=dialogTop-600;
		dialogLeft=dialogLeft-100;
		
		// assign values to the overlay and dialog box
		$('#dialog-overlay8').css({height:maskHeight, width:maskWidth}).show();
		$('#dialog-box8').css({top:dialogTop, left:dialogLeft}).show();
		
		// display the message
		$('#dialog-message8').html(message);
	}

	//function to hide Popup
	function div_hide8(){ 
		$('#dialog-overlay8, #dialog-box8').hide();
		return true;
	}
	
	function validateFormStatusEvent() {
		try{
			var status="";
			status=$("#status").val();
			//alert(status+"============"+previousStatus)
			if(status=="Denied" && status!=previousStatus) {
				popup8("");
				return false;
			}else
				return true;
			
		} catch(e) {
			alert(e)
		}
	}

	function callReassignPhysician() {
		try{
			var otherPhysicianId=$("#otherPhysicianId").val();	
			//alert(otherPhysicianId)
			if(otherPhysicianId=="0")
			{	
				document.getElementById("errOtherPhysicianMsg").style.display="block";
				
			}else{
				
				var physicianId=$deniedThis.closest('tr').attr("id").replace("row_", "");
				var physicianName = $deniedThis.closest('tr').find(".phyname").html();
				
				if (parseInt(physicianId) > 0) {
					
					var data = new FormData();
					data.append('otherPhysicianId',  otherPhysicianId);
					data.append('physicianId',  physicianId);
		    		
		    	 	var xhr = new XMLHttpRequest();
		    	 	xhr.open('POST', 'reAssignPhysicianbyAjax', true);
		    	 	xhr.send(data);
		    	 	xhr.onload = function () {
		    	 		if (this.responseText != "") {
		    	 			
		    	 			$.alert({
					 		    title: '',
					 		    content: $("#info_reassignphysician").val(),
					 		});
		    	 			
							dttable.ajax.url("getPhysicianSummaryData").draw();
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
			var physicianName = $deniedThis.closest('tr').find(".phyname").html();
			
			if (parseInt(physicianId) > 0) {
				
				var data = new FormData();
				data.append('physicianId',  physicianId);
	    		
	    	 	var xhr = new XMLHttpRequest();
	    	 	xhr.open('POST', 'deActivatePhysicianbyAjax', true);
	    	 	xhr.send(data);
	    	 	xhr.onload = function () {
	    	 		if (this.responseText != "") {
	    	 			
	    	 			$.alert({
				 		    title: '',
				 		    content: $("#info_deactivatephysician").val(),
				 		});
	    	 			
						dttable.ajax.url("getPhysicianSummaryData").draw();
						div_hide8();
	    	 		}
	    	 	};
				
			}
			
		}catch(e)
		{
			alert(e)
		}
	}
} catch(e) {
	// alert(" Exception in redrawDataTable()     ======     " + e);
}
