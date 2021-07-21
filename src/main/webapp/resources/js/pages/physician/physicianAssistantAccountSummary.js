
$(document).on("click", "#newAssistAcc", function() {
	location.href="physicianassistantaccount";
});

var assistantName = "";
var clinicName = "";
var phyName = "";
var status = "";
var groupId = "";


try {
	var phyNameVar = $("#phyName").val();
	if (phyNameVar == null || phyNameVar == "") {
		phyNameVar = "Select Physician";
	}
	var results = [];
	$('#autoCompleterPhysicianId').select2({
		placeholder : phyNameVar,
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
		$("#phyName").val(this.value)
	});

} catch (e) {
	// alert(e)
}

try {
	var assistantNameVar = $("#assistantName").val();
	if (assistantNameVar == null || assistantNameVar == "") {
		assistantNameVar = "Select Assistant";
	}
	var aresults = [];
	$('#autoCompleterPhysicianAssistantId').select2({
		placeholder : assistantNameVar,
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
		$("#assistantName").val(this.value)
	});

} catch (e) {
	// alert(e)
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
}

try	{
	if ($("#phyPhysicianAsstSummaryTable").length > 0) {
		assistantName = $("#assistantName").val();
		clinicName = $("#clinicName").val();
		status = $("#status").val();
		groupId = $("#groupId").val();
		
		// Physician login Summary works
		var summaryTable = $("#phyPhysicianAsstSummaryTable").DataTable({
			"processing" : true,
			"serverSide" : true,
			"dom":'<"top">rt<"bottom"pl><"clear">',
			"lengthMenu" : [ [ 10, 15, 25], [ 10, 15, 25] ],
			"pagingType": "full_numbers",
			"ajax" : {
				"url" : "getPhysicianAssistantData",
				"data": function ( d ) {
	                d.pid = $("#userId").val();
	                d.assistant = assistantName;
	                d.status = status;
	                d.clinic = clinicName;
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
			    { data : "assistantName","orderable":false, "sClass":"left_align assname"   },
			    { data : "groupName","orderable":false, "sClass":"left_align"},
			    {
			    	"data": null, "orderable": false,"render": function (data, type, row) {
			    		var ddl = "<select name='newGroupSet' class='newGroupSet' style='width:120px;' >";
		                for (var i = 0; i < $("#dropDownGroupId option").length; i++) {
		                	ddl = ddl + "<option value='"+$("#dropDownGroupId option").eq(i).val()+"'>"+$("#dropDownGroupId option").eq(i).text()+"</option>";
		                }
		                ddl += "</select>";
		                ddl += ' <button type="button" class="btn btn-primary saveAssistantNewGroup" >Save</button>';
		                ddl += ' <input type="hidden" class="btn btn-primary oldGroupId" value="'+row.groupId+'" />';
		                return ddl;
			    	}
			    },
			    { data : "clinicName","orderable":false, "sClass":"left_align", "visible": false  },
			    { data : "email","orderable":false, "sClass":"left_align",  "visible": false  },
			    { data : "mobile","orderable":false, "sClass":"left_align", "visible": false  },
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
			               { "width": "30px", "targets": [1] },
			               { "width": "150px", "targets": [2] },
			               
			               { "width": "100px", "targets": [3] },
			               { "width": "150px", "targets": [4] },
			               
			               { "width": "80px", "targets": [5,6,7] },
			               { "width": "70px", "targets": [8] },
			               { "width": "70px", "targets": [9] }
			],
	        "fnDrawCallback": function( oSettings ) {
	        	var targetOffset = $(".nav_menu").offset().top;
		        $("html,body").animate({scrollTop: targetOffset}, 300);
	        },
			"initComplete": function(settings, json) {
				$('#phyPhysicianAsstSummaryTable_processing').hide();
				// heightAdjust();
			},
			"error" : "Error while processing data...."
		});

		// For Edit Record.
	    $("thead tr .edit-control").removeClass("edit-control");
		$('#phyPhysicianAsstSummaryTable tbody').on('click', 'tr td.edit-control', function() {
			var id=$(this).closest('tr').attr("id").replace("row_", "");
			$("#assistantId").val(id);
			document.editPage.submit();
		});
		
		$(document).on("click", "#newSearch", function() {
			$("div.tooltip").remove();
			assistantName = $("#assistantName").val();
			clinicName = $("#clinicName").val();
			status = $("#status").val();
			groupId = $("#groupId").val();
			summaryTable.ajax.url("getPhysicianAssistantData").draw();
		});
		
		$(document).on("click", "#clearSearch", function() {
			$("div.tooltip").remove();
			$("#assistantName").val("");
			$("#clinicName").val("");
			$("#status").val("");
			
			if (document.getElementById("select2-autoCompleterPhysicianAssistantId-container"))
				document.getElementById("select2-autoCompleterPhysicianAssistantId-container").innerHTML='<span class="select2-selection__placeholder">Select Physician</span>';	
			if (document.getElementById("select2-autoCompleterClinicId-container"))
				document.getElementById("select2-autoCompleterClinicId-container").innerHTML='<span class="select2-selection__placeholder">Select Clinic</span>';	
			
			assistantName = "";
			clinicName = "";
			status = "";
			if ($("#checkUserType").val() != "Group Director" && $("#checkUserType").val() != "Physician") {
				$("#groupId").val("0");
				groupId = "0";
			}
			summaryTable.ajax.url("getPhysicianAssistantData").draw();
		});
		
		
		
		
		
		
		
		
		$(document).on("click", ".saveAssistantNewGroup", function() {
			var assistantId=$(this).closest('tr').attr("id").replace("row_", "");
			var newGroupId=$(this).closest('tr').find("option:selected").val();
			var assistantName = $(this).closest('tr').find(".assname").html();
			var groupId = $(this).closest('tr').find(".oldGroupId").val();
			
			
			if (newGroupId == groupId) {
				$.alert({
		 		    title: 'Alert Info',
		 		    content: "Both Group and Change Group are same",
		 		});
			} else {
				if (parseInt(assistantId) > 0 && parseInt(newGroupId) > 0 && parseInt(groupId) > 0) {
					var data = new FormData();
					data.append('assistantId',  assistantId);
					data.append('groupId',  groupId);
					data.append('assistantName',  assistantName);
					data.append('newGroupId',  newGroupId);
					
					var xhr = new XMLHttpRequest();
					xhr.open('POST', urlPath + '/group/changeAssistantGroup', true);
					xhr.send(data);
					xhr.onload = function () {
						if (this.responseText != "") {
							$.alert({
					 		    title: 'Save Info',
					 		    content: this.responseText,
					 		});
							summaryTable.ajax.url("getPhysicianAssistantData").draw();
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


		$(document).on("click", ".inActPhysician", function() {
			var assistantId=$(this).closest('tr').attr("id").replace("row_", "");
			var assistantName = $(this).closest('tr').find(".assname").html();
			
			if (parseInt(assistantId) > 0) {
				var data = new FormData();
				data.append('assistantId',  assistantId);
				data.append('assistantName',  assistantName);
				data.append('status',  "Inactive");
				
				var xhr = new XMLHttpRequest();
				xhr.open('POST', urlPath + '/group/changeAssistantStatusFromGroup', true);
				xhr.send(data);
				xhr.onload = function () {
					if (this.responseText != "") {
						$.alert({
				 		    title: 'Save Info',
				 		    content: this.responseText,
				 		});
						summaryTable.ajax.url("getPhysicianAssistantData").draw();
					}
				}
			}
		});


		$(document).on("click", ".actPhysician", function() {
			var assistantId=$(this).closest('tr').attr("id").replace("row_", "");
			var assistantName = $(this).closest('tr').find(".assname").html();
			
			if (parseInt(assistantId) > 0) {
				var data = new FormData();
				data.append('assistantId',  assistantId);
				data.append('assistantName',  assistantName);
				data.append('status',  "Active");
				
				var xhr = new XMLHttpRequest();
				xhr.open('POST', urlPath + '/group/changeAssistantStatusFromGroup', true);
				xhr.send(data);
				xhr.onload = function () {
					if (this.responseText != "") {
						$.alert({
				 		    title: 'Save Info',
				 		    content: this.responseText,
				 		});
						summaryTable.ajax.url("getPhysicianAssistantData").draw();
					}
				}
			}
		});
	}
	
	if ($("#adminPhysicianAsstSummaryTable").length > 0) {
		assistantName = $("#assistantName").val();
		clinicName = $("#clinicName").val();
		phyName = $("#phyName").val();
		status = $("#status").val();
		groupId = $("#groupId").val();
		if(groupId=="")
			groupId=0;
		
		// Admin and Super admin Login summary works
		var summaryTable = $("#adminPhysicianAsstSummaryTable").DataTable({
			"processing" : true,
			"serverSide" : true,
			"dom":'<"top">rt<"bottom"pl><"clear">',
			"lengthMenu" : [ [ 10, 15, 25], [ 10, 15, 25] ],
			"pagingType": "full_numbers",
			"ajax" : {
				"url" : "getAdminPhysicianAssistantData",
				"data": function ( d ) {
	                d.assistant = assistantName;
	                d.phyName = phyName;
	                d.status = status;
	                d.clinic = clinicName;
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
			    { data : "assistantName","orderable":false, "sClass":"left_align  assname"   },
			    { data : "physicianName","orderable":false, "sClass":"left_align"   },
			    { data : "groupName","orderable":false, "sClass":"left_align" },
			    {
			    	"data": null, "orderable": false,"render": function (data, type, row) {
			    		var ddl = "<select name='newGroupSet' class='newGroupSet' style='width:120px;' >";
		                for (var i = 0; i < $("#dropDownGroupId option").length; i++) {
		                	ddl = ddl + "<option value='"+$("#dropDownGroupId option").eq(i).val()+"'>"+$("#dropDownGroupId option").eq(i).text()+"</option>";
		                }
		                ddl += "</select>";
		                ddl += ' <button type="button" class="btn btn-primary saveAssistantNewGroup" >Save</button>';
		                ddl += ' <input type="hidden" class="btn btn-primary oldGroupId" value="'+row.groupId+'" />';
		                return ddl;
			    	}
			    },
			    { data : "clinicName","orderable":false, "sClass":"left_align" ,  "visible": false },
			    { data : "email","orderable":false, "sClass":"left_align",  "visible": false   },
			    { data : "mobile","orderable":false, "sClass":"left_align",  "visible": false },
			    { data : "status","orderable":false, "sClass":"left_align" },
			    {
			    	"data": null, "orderable": false, "sClass":"left_align", "render": function (data, type, row) {
			    		if (row.status == 'Active') {
			    			return ' <button type="button" class="btn btn-primary inActPhysician">Inactive</button>';
			    		} else if (row.status == 'Inactive') {
			    			return ' <button type="button" class="btn btn-primary actPhysician">Active</button>';
			    		}
			    	}
			    }
			],
			"columnDefs": [
			               { "width": "10px", "targets": [0] },
			               { "width": "30px", "targets": [1] },
			               { "width": "100px", "targets": [2] },
			               
			               { "width": "100px", "targets": [3] },
			               { "width": "100px", "targets": [4] },
			               { "width": "170px", "targets": [5] },
			               
			               { "width": "80px", "targets": [6,7,8] },
			               { "width": "60px", "targets": [9,10] }
			],
	        "fnDrawCallback": function( oSettings ) {
	        	var targetOffset = $(".nav_menu").offset().top;
		        $("html,body").animate({scrollTop: targetOffset}, 300);
	        },
			"initComplete": function(settings, json) {
				$('#adminPhysicianAsstSummaryTable_processing').hide();
				// heightAdjust();
			},
			"error" : "Error while processing data...."
		});

		// For Edit Record.
	    $("thead tr .edit-control").removeClass("edit-control");
		$('#adminPhysicianAsstSummaryTable tbody').on('click', 'tr td.edit-control', function() {
			var id=$(this).closest('tr').attr("id").replace("row_", "");
			//location.href = "editPhysicianassistantaccount?id="+id;
			$("#assistantId").val(id);
			document.editPage.submit();
		});
		
		$(document).on("click", "#newSearch", function() {
			$("div.tooltip").remove();
			assistantName = $("#assistantName").val();
			clinicName = $("#clinicName").val();
			phyName = $("#phyName").val();
			status = $("#status").val();
			groupId = $("#groupId").val();
			summaryTable.ajax.url("getAdminPhysicianAssistantData").draw();
		});
		
		$(document).on("click", "#clearSearch", function() {
			$("div.tooltip").remove();
			$("#assistantName").val("");
			$("#clinicName").val("");
			$("#phyName").val("");
			$("#status").val("");
			
			if (document.getElementById("select2-autoCompleterPhysicianId-container"))
				document.getElementById("select2-autoCompleterPhysicianId-container").innerHTML='<span class="select2-selection__placeholder">Select Physician</span>';	
			if (document.getElementById("select2-autoCompleterPhysicianAssistantId-container"))
				document.getElementById("select2-autoCompleterPhysicianAssistantId-container").innerHTML='<span class="select2-selection__placeholder">Select Assistant</span>';	
			if (document.getElementById("select2-autoCompleterClinicId-container"))	
				document.getElementById("select2-autoCompleterClinicId-container").innerHTML='<span class="select2-selection__placeholder">Select Clinic</span>';

			assistantName = "";
			clinicName = "";
			phyName = "";
			status = "";
			if ($("#checkUserType").val() != "Group Director") {
				$("#groupId").val("0");
				groupId = "0";
			}
			summaryTable.ajax.url("getAdminPhysicianAssistantData").draw();
		});
		
		
		

		
		
		$(document).on("click", ".saveAssistantNewGroup", function() {
			var assistantId=$(this).closest('tr').attr("id").replace("row_", "");
			var newGroupId=$(this).closest('tr').find("option:selected").val();
			var assistantName = $(this).closest('tr').find(".assname").html();
			var groupId = $(this).closest('tr').find(".oldGroupId").val();
			
			if (newGroupId == groupId) {
				$.alert({
		 		    title: 'Alert Info',
		 		    content: "Both Group and Change Group are same",
		 		});
			} else {
				if (parseInt(assistantId) > 0 && parseInt(newGroupId) > 0 && parseInt(groupId) > 0) {
					
					var data = new FormData();
					data.append('assistantId',  assistantId);
					data.append('groupId',  groupId);
					data.append('assistantName',  assistantName);
					data.append('newGroupId',  newGroupId);
					
					var xhr = new XMLHttpRequest();
					xhr.open('POST', urlPath + '/group/changeAssistantGroup', true);
					xhr.send(data);
					xhr.onload = function () {
						if (this.responseText != "") {
							$.alert({
					 		    title: 'Save Info',
					 		    content: this.responseText,
					 		});
							assistantName = $("#assistantName").val();
							clinicName = $("#clinicName").val();
							phyName = $("#phyName").val();
							status = $("#status").val();
							groupId = $("#groupId").val();
							summaryTable.ajax.url("getAdminPhysicianAssistantData").draw();
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


		$(document).on("click", ".inActPhysician", function() {
			var assistantId=$(this).closest('tr').attr("id").replace("row_", "");
			var assistantName = $(this).closest('tr').find(".assname").html();
			
			if (parseInt(assistantId) > 0) {
				var data = new FormData();
				data.append('assistantId',  assistantId);
				data.append('assistantName',  assistantName);
				data.append('status',  "Inactive");
				
				var xhr = new XMLHttpRequest();
				xhr.open('POST', urlPath + '/group/changeAssistantStatusFromGroup', true);
				xhr.send(data);
				xhr.onload = function () {
					if (this.responseText != "") {
						$.alert({
				 		    title: 'Save Info',
				 		    content: this.responseText,
				 		});
						assistantName = $("#assistantName").val();
						clinicName = $("#clinicName").val();
						phyName = $("#phyName").val();
						status = $("#status").val();
						groupId = $("#groupId").val();
						summaryTable.ajax.url("getAdminPhysicianAssistantData").draw();
					}
				}
			}
		});


		$(document).on("click", ".actPhysician", function() {
			var assistantId=$(this).closest('tr').attr("id").replace("row_", "");
			var assistantName = $(this).closest('tr').find(".assname").html();
			
			if (parseInt(assistantId) > 0) {
				var data = new FormData();
				data.append('assistantId',  assistantId);
				data.append('assistantName',  assistantName);
				data.append('status',  "Active");
				
				var xhr = new XMLHttpRequest();
				xhr.open('POST', urlPath + '/group/changeAssistantStatusFromGroup', true);
				xhr.send(data);
				xhr.onload = function () {
					if (this.responseText != "") {
						$.alert({
				 		    title: 'Save Info',
				 		    content: this.responseText,
				 		});
						assistantName = $("#assistantName").val();
						clinicName = $("#clinicName").val();
						phyName = $("#phyName").val();
						status = $("#status").val();
						groupId = $("#groupId").val();
						summaryTable.ajax.url("getAdminPhysicianAssistantData").draw();
					}
				}
			}
		});
	}
} catch(e) {
	alert(" Exception in redrawDataTable()     ======     " + e);
}

function fnShowHide( iCol )
{
		
	 // Get the column API object
    var column = summaryTable.column(iCol);

    // Toggle the visibility
    column.visible( ! column.visible() )
}
