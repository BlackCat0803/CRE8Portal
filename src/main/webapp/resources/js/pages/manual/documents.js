jQuery(function($){
   $("#displayOrder").mask("99999");
});

$(document).ready(function() {
    $('#manual').bootstrapValidator({
        fields: {
        	documentTitle: {
                validators: {
                    notEmpty: {
                    	message: $("#err_doc_title").val()
                    }
                }
        	},
        	displayOrder: {
                validators: {
                    notEmpty: {
                    	message: $("#err_display_order").val()
                    },
                    greaterThan: {
                        value: 0,
                        inclusive: false,
                        message: $("#err_display_order_valid").val()
                    }
                }
            },
            uploadImageFile: {
                validators: {
                    notEmpty: {
                        message: 'Please upload image'
                    },
                  file: {
                        extension: 'png,PNG',
                        type: 'image/png',
                        maxSize: 1048576,   // 2048 * 1024
                        message: 'PNG files only allowed in document image <br> and File Size should be less than 1 MB'
                    },
                    /*file: {
                        extension: 'png,PNG',
                        type: 'image/png',
                        message: 'PNG files only allowed in document image'
                    },
                    file: {
                        maxSize: 1048576,   // 2048 * 1024
                        message: 'File Size should be less than 1 MB'
                    }*/
                }
            },
            uploadDocFile: {
                validators: {
                    notEmpty: {
                        message: 'Please upload file'
                    },
                   file: {
                        extension: 'pdf,PDF',
                        type: 'application/pdf',
                        maxSize: 1048576 * 10,   // 2048 * 1024
                        message: 'PDF files only allowed in documents<br> and File Size should be less than 10 MB'
                    }
                    /*file: {
                        extension: 'pdf,PDF',
                        type: 'application/pdf',
                        maxSize: 1048576 * 10,   // 2048 * 1024
                        message: 'PDF files only allowed in documents'
                    },
                    file: {
                    	maxSize: 1048576 * 10,   // 2048 * 1024
                        message: 'File Size should be less than 10 MB'
                    }*/
                }
            }
        }
    }).on('error.form.bv', function(e) {
    	erorrMessageShowHide();
    })
    .on('success.form.bv', function(e) {
    	erorrMessageShowHide();
    })
    .on('error.field.bv', function(e, data) {
    	erorrMessageShowHide();
    })
    .on('success.field.bv', function(e, data) {
    	erorrMessageShowHide();
    })
    .on('status.field.bv', function(e, data) {
    	erorrMessageShowHide();
    });
});

function erorrMessageShowHide() {
	var isError = false;
	
	if (document.getElementById("documentTitle").value == "")
    	isError = true;
	if (document.getElementById("displayOrder").value == "")
    	isError = true;
	if (document.getElementById("thumbfile")) {
		if (document.getElementById("thumbfile").value == "")
			isError = true;
	}
	if (document.getElementById("file")) {
		if (document.getElementById("file").value == "")
			isError = true;
	}
	if (isError)
		$(".formErrorMsg").show();
	else
		$(".formErrorMsg").hide();
}


/*$("#thumbfile").change(function(){
	var imgErr="";
	var isError = false;
	
	var x = document.getElementById("thumbfile");
	if ('files' in x) {
		if (x.files.length > 0) {
			for (var i = 0; i < x.files.length; i++) {
	            var file = x.files[i];
	            if ('size' in file) {
	            	// txt += "size: " + file.size + " bytes <br>";
	            	if (file.size > 1048576) {
	            		imgErr += 'File Size should be less than 1 MB';
	            		isError = true;
	            	}	
	            }
	            var fileExtension = file.name.substr(file.name.lastIndexOf('.') + 1);
	    		// txt += "ext : " + fileExtension + " <br>";
	    		if (fileExtension != 'png' && fileExtension != 'PNG') {
	    			if (imgErr !="")  
	    				imgErr += " and ";
	    			imgErr += 'PNG files only allowed in document image';
            		isError = true;
	    		}
			}
		} else {
			imgErr += 'Please upload image';
			isError = true;
		}
	} else {
		imgErr += 'Please upload image';
		isError = true;
	}
	$(".img_file-upload-errors").html(imgErr);
	
	if (imgErr != "")
		$("#thumbfile").focus();
	erorrMessageShowHide();
});

$("#file").change(function(){
	var docErr="";
	var isError = false;
	
	var x = document.getElementById("file");
	if ('files' in x) {
		if (x.files.length > 0) {
			for (var i = 0; i < x.files.length; i++) {
	            var file = x.files[i];
	            if ('size' in file) {
	            	if (file.size > (1048576 * 10)) {
	            		docErr += 'File Size should be less than 10 MB';
	            		isError = true;
	            	}	
	            }
	            var fileExtension = file.name.substr(file.name.lastIndexOf('.') + 1);
	    		if (fileExtension != 'pdf' && fileExtension != 'PDF') {
	    			if (docErr !="")  
	    				docErr += " and ";
	    			docErr += 'PDF files only allowed in documents';
            		isError = true;
	    		}
			}
		} else {
			docErr += 'Please upload file';
			isError = true;
		}
	} else {
		docErr += 'Please upload file';
		isError = true;
	}
	$(".file-upload-errors").html(docErr);
	
	if (docErr != "")
		$("#file").focus();
	erorrMessageShowHide();
});*/

function checkValues() {
	// var txt = "";
	var docErr="";
	var imgErr="";
	var isError = false;
	
	var x = document.getElementById("thumbfile");
	if ('files' in x) {
		if (x.files.length > 0) {
			for (var i = 0; i < x.files.length; i++) {
	            var file = x.files[i];
	            if ('size' in file) {
	            	// txt += "size: " + file.size + " bytes <br>";
	            	if (file.size > 1048576) {
	            		imgErr += 'File Size should be less than 1 MB';
	            		isError = true;
	            	}	
	            }
	            var fileExtension = file.name.substr(file.name.lastIndexOf('.') + 1);
	    		// txt += "ext : " + fileExtension + " <br>";
	    		if (fileExtension != 'png' && fileExtension != 'PNG') {
	    			if (imgErr !="")  
	    				imgErr += " and ";
	    			imgErr += 'PNG files only allowed in document image';
            		isError = true;
	    		}
			}
		} else {
			imgErr += 'Please upload image';
			isError = true;
		}
	} else {
		imgErr += 'Please upload image';
		isError = true;
	}
	
	var x = document.getElementById("file");
	if ('files' in x) {
		if (x.files.length > 0) {
			for (var i = 0; i < x.files.length; i++) {
	            var file = x.files[i];
	            if ('size' in file) {
	            	if (file.size > (1048576 * 10)) {
	            		docErr += 'File Size should be less than 10 MB';
	            		isError = true;
	            	}	
	            }
	            var fileExtension = file.name.substr(file.name.lastIndexOf('.') + 1);
	    		if (fileExtension != 'pdf' && fileExtension != 'PDF') {
	    			if (docErr !="")  
	    				docErr += " and ";
	    			docErr += 'PDF files only allowed in documents';
            		isError = true;
	    		}
			}
		} else {
			docErr += 'Please upload file';
			isError = true;
		}
	} else {
		docErr += 'Please upload file';
		isError = true;
	}

	
	alert(isError+"====="+imgErr)
	if(isError || imgErr!="")
		{
			$('button[type="submit"]').prop('disabled','disabled');
			//$("#file").val('');
		}else
		{
			 $('button[type="submit"]').removeAttr('disabled');
		}
	
	$(".file-upload-errors").html(err);
	$(".img_file-upload-errors").html(imgErr);
	
	if(isError || imgErr!="")
	{
		erorrMessageShowHide();
	}
	
	
	/*if (!isError || $("#fileId").val() > 0) {
		$("#submitfileDocHidden").click();
		var validatorObj = $('#manual').data('bootstrapValidator');
	    validatorObj.validate();
	    return validatorObj.isValid();
	} else {
		$(".file-upload-errors").html(docErr);
		$(".img_file-upload-errors").html(imgErr);
		
		if (imgErr != "")
			$("#thumbfile").focus();
		else
			$("#file").focus();
		erorrMessageShowHide();
	}*/
}


/*************  SUMMARY ***************/
var displayName = "";
var doctype = "";
try	{
	displayName = $("#displayName").val();
	
	var summaryTable = $("#docListTable").DataTable({
		"processing" : true,
		"serverSide" : true,
		"dom":'<"top">rt<"bottom"pl><"clear">',
		"lengthMenu" : [ [ 10, 15, 25], [ 10, 15, 25] ],
		"pagingType": "full_numbers",
		"ajax" : {
			"url" : "getAdminManualDocData",
			"data": function ( d ) {
                d.displayName = displayName;
                d.doctype = doctype;
      		},
			"type" : "POST"
		},
		"oLanguage": {
	        "sEmptyTable":  "No records available"
	    },
		"columns" : [ 
		    { data : "fileId", "visible": false },
		    { 
				"class"			:	"edit-control", 
			    "orderable"		:	false, 
			    "data"			:	null, 
			    "defaultContent":	""
			}, 
		    { 
				"class"			:	"delete-control", 
			    "orderable"		:	false, 
			    "data"			:	null, 
			    "defaultContent":	""
			}, 
		    { data : "documentTitle","orderable":false, "sClass":"left_align" },
		    { data : "originalThumbFileName","orderable":false, "sClass":"left_align" },
		    { data : "originalFileName","orderable":false, "sClass":"left_align" },
		    { data : "displayOrder","orderable":false, "sClass":"left_align" }
		],
		"columnDefs": [{
			   "render": function ( data, type, row ) {
				   data = '<a href="manualDocFileDownload?i=y&f='+row.fileId+'">'+row.originalThumbFileName+'</a>';
				    return data;
			   },
			   "targets": 4,
			},{ 
			   "render": function ( data, type, row ) {
				   data = '<a href="manualDocFileDownload?i=n&f='+row.fileId+'">'+row.originalFileName+'</a>';
				    return data;
			   },
			   "targets": 5
			},
			{ "width": "40px", "targets": [0,1,2] },
            { "width": "100px", "targets": [3] },
            { "width": "70px", "targets": [4,5] },
            { "width": "50px", "targets": [6] }
		],
        "fnDrawCallback": function( oSettings ) {
        	var targetOffset = $(".nav_menu").offset().top;
	        $("html,body").animate({scrollTop: targetOffset}, 300);
        },
		"initComplete": function(settings, json) {
			$('#docListTable_processing').hide();
			// heightAdjust();
		},
		"error" : "Error while processing data...."
	});
	
	// For Edit Record.
    $("thead tr .edit-control").removeClass("edit-control");
	$('#docListTable tbody').on('click', 'tr td.edit-control', function() {
		var id=$(this).closest('tr').attr("id").replace("row_", "");
		//location.href = "editManualDocument?id="+id;
		$("#tempId").val(id);
		document.editPage.submit();
	});
	
    $("thead tr .delete-control").removeClass("delete-control");
	$('#docListTable tbody').on('click', 'tr td.delete-control', function() {
		var id=$(this).closest('tr').attr("id").replace("row_", "");
		var docName = $("#row_"+id).find("td").eq(0).find("a").text();
		
		var alertTxt = "Are you sure do you want to delete this "+docName + " ? ";
		$.confirm({
			title: 'Confirm!',
			content: alertTxt,
			buttons: {
	 			confirm: function () {
	 				location.href = "deleteManualDocument?id="+id;
	 			},
	 			cancel: function () {
	 				
	 			}
			}
		});
	});
	
	$(document).on("click", "#docSearch", function() {
		$("div.tooltip").remove();
		displayName = $("#displayName").val();
		//summaryTable.ajax.url("getAdminManualDocData?displayName="+displayName+"&doctype="+doctype).draw();
		summaryTable.ajax.url("getAdminManualDocData").draw();
	});

	$(document).on("click", "#clearDocSearch", function() {
		$("div.tooltip").remove();
		$("#displayName").val("");
		displayName = "";
		//summaryTable.ajax.url("getAdminManualDocData?displayName="+displayName+"&doctype="+doctype).draw();
		summaryTable.ajax.url("getAdminManualDocData").draw();
	});
	
} catch(e) {
	// alert(" Exception in redrawDataTable()     ======     " + e);
}