var patientname = "";
var phyname = "";
var physicianId = "";
var status = "";
var groupId = "";

try	{
	if ($("#patientAccountSummaryTable").length > 0) {
		patientname = $("#patientname").val();
		phyname = $("#phyname").val();
		physicianId = $("#physicianId").val();
		status = $("#status").val();
		groupId = $("#groupId").val();
		
		// Physician login Summary works
		var summaryTable = $("#patientAccountSummaryTable").DataTable({
			"processing" : true,
			"serverSide" : true,
			"dom":'<"top">rt<"bottom"pl><"clear">',
			"lengthMenu" : [ [ 10, 15, 25], [ 10, 15, 25] ],
			"pagingType": "full_numbers",
			"ajax" : {
				"url" : "getPatientAccountSummaryData",
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
					"class"			:	"edit-control", 
				    "orderable"		:	false, 
				    "data"			:	null, 
				    "defaultContent":	""
				}, 
			    { data : "patientName","orderable":false, "sClass":"left_align patientName"   },
			    { data : "physicianName","orderable":false, "sClass":"left_align" },
			    {
			    	"data": null, "visible": false, "orderable": false,"render": function (data, type, row) {
			    		if (row.physicianName != "") {
			    			var ddl = "<select name='newPhysicianSet' class='newGroupSet' style='width:120px;' >";
			                for (var i = 0; i < $("#groupPhysicianId option").length; i++) {
			                	ddl = ddl + "<option value='"+$("#groupPhysicianId option").eq(i).val()+"'>"+$("#groupPhysicianId option").eq(i).text()+"</option>";
			                }
			                ddl += "</select>";
			                ddl += ' <button type="button" class="btn btn-primary savePatientNewGroup" >Save</button>';
			                return ddl;	
			    		} else {
			    			return "";
			    		}
			    	}
			    },
			    { data : "groupName","orderable":false, "sClass":"left_align" },
			    
			    { data : "city","orderable":false, "sClass":"left_align",  "visible": false   },
			    { data : "email","orderable":false, "sClass":"left_align",  "visible": false  },
			    { data : "mobile","orderable":false, "sClass":"left_align",  "visible": false  },
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
			               { "width": "10px", "targets": [0,6,7,8] },
			               { "width": "30px", "targets": [1] },
			               { "width": "100px", "targets": [2,3] },
			               { "width": "170px", "targets": [4] },
			               
			               { "width": "100px", "targets": [5] },
			               
			               { "width": "80px", "targets": [9,10] }
			               
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
				$('#patientAccountSummaryTable_processing').hide();
			},
			"error" : "Error while processing data...."
		});
		
		// For Edit Record.
	    $("thead tr .edit-control").removeClass("edit-control");
		$('#patientAccountSummaryTable tbody').on('click', 'tr td.edit-control', function() {
			var id=$(this).closest('tr').attr("id").replace("row_", "");
			$("#patientId").val(id);
			document.editPage.submit();
		});
		
		$(document).on("click", "#newSearch", function() {
			$("div.tooltip").remove();
			patientname = $("#patientname").val();
			phyname = $("#phyname").val();
			physicianId = $("#physicianId").val();
			status = $("#status").val();
			groupId = $("#groupId").val();
			summaryTable.ajax.url("getPatientAccountSummaryData").draw();
		});
		
		$(document).on("click", "#clearSearch", function() {
			$("div.tooltip").remove();
			$("#patientname").val("");
			$("#phyname").val("");
			$("#status").val("");
			
			if (document.getElementById("select2-autoCompleterPhysicianId-container"))
				document.getElementById("select2-autoCompleterPhysicianId-container").innerHTML='<span class="select2-selection__placeholder">Select Physician</span>';	
			if (document.getElementById("select2-autoCompleterPatientId-container"))
				document.getElementById("select2-autoCompleterPatientId-container").innerHTML='<span class="select2-selection__placeholder">Select Patient</span>';	
			
			patientname = "";
			phyname = "";
			status = "";
			if ($("#checkUserType").val() != "Group Director") {
				$("#groupId").val("0");
				groupId = "0";
			}
			summaryTable.ajax.url("getPatientAccountSummaryData").draw();
		});
		
		
		
		$(document).on("click", ".savePatientNewGroup", function() {
			var patientId=$(this).closest('tr').attr("id").replace("row_", "");
			var newPhysicianId=$(this).closest('tr').find("option:selected").val();
			var patientName = $(this).closest('tr').find(".patientName").html();
			
			if (parseInt(patientId) > 0 && parseInt(newPhysicianId) > 0) {
				
				var data = new FormData();
				data.append('patientId',  patientId);
				data.append('groupId',  1);
				data.append('patientName',  patientName);
				data.append('newPhysicianId',  newPhysicianId);
				
				var xhr = new XMLHttpRequest();
				xhr.open('POST', urlPath + '/group/changePatientPhysician', true);
				xhr.send(data);
				xhr.onload = function () {
					if (this.responseText != "") {
						$.alert({
				 		    title: 'Save Info',
				 		    content: this.responseText,
				 		});
						summaryTable.ajax.url("getPatientAccountSummaryData").draw();
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
			var patientName = $(this).closest('tr').find(".patientName").html();
			
			if (parseInt(patientId) > 0) {
				
				var data = new FormData();
				data.append('patientId',  patientId);
				data.append('patientName',  patientName);
				data.append('status',  "Denied");
				
				var xhr = new XMLHttpRequest();
				xhr.open('POST', urlPath + '/group/changePatientStatusFromGroup', true);
				xhr.send(data);
				xhr.onload = function () {
					if (this.responseText != "") {
						$.alert({
				 		    title: 'Save Info',
				 		    content: this.responseText,
				 		});
						summaryTable.ajax.url("getPatientAccountSummaryData").draw();
					}
				}
			}
		});


		$(document).on("click", ".approvedPatient", function() {
			var patientId=$(this).closest('tr').attr("id").replace("row_", "");
			var patientName = $(this).closest('tr').find(".patientName").html();
			
			if (parseInt(patientId) > 0) {
				
				var data = new FormData();
				data.append('patientId',  patientId);
				data.append('patientName',  patientName);
				data.append('status',  "Approved");
				
				var xhr = new XMLHttpRequest();
				xhr.open('POST', urlPath + '/group/changePatientStatusFromGroup', true);
				xhr.send(data);
				xhr.onload = function () {
					if (this.responseText != "") {
						$.alert({
				 		    title: 'Save Info',
				 		    content: this.responseText,
				 		});
						summaryTable.ajax.url("getPatientAccountSummaryData").draw();
					}
				}
			}
		});
		
		function fnShowHide( iCol )
		{
				
			 // Get the column API object
		    var column = summaryTable.column(iCol);

		    // Toggle the visibility
		    column.visible( ! column.visible() )
		}

	}
} catch(e) {
	// alert(" Exception in redrawDataTable()     ======     " + e);
}


try {
	var phynameVar = $("#phyname").val();
	if (phynameVar == null || phynameVar == "") {
		phynameVar = "Select Physician";
	}
	var results = [];
	$('#autoCompleterPhysicianId').select2({
		placeholder : phynameVar,
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
				results = [];
				data.forEach(function makeResults(element) {
					results.push({
						id : element.physicianName,
						text : element.physicianName
					});
				});
				return {
					results : results
				};
			},
			cache : true
		}
	}).on('change', function(e) {
		$("#phyname").val(this.value)
	});

} catch (e) {
	// alert(e)
}

try {
	var patientnameVar = $("#patientname").val();
	if (patientnameVar == null || patientnameVar == "") {
		patientnameVar = "Select Patient";
	}
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
				aresults = [];
				data.forEach(function makeResults(element) {
					aresults.push({
						id : element.patientName,
						text : element.patientName
					});
				});
				return {
					results : aresults
				};
			},
			cache : true
		}
	}).on('change', function(e) {
		$("#patientname").val(this.value)
	});

} catch (e) {
	// alert(e)
}

$(document).on("click", "#newPatientAcc", function() {
	location.href="patientAccount";
} ) ; 
