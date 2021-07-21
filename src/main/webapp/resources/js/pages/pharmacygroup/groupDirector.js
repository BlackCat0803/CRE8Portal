
var dateFormat = "MM/DD/YYYY";
var rdate = $("#dateRegistrated");


$(rdate).datetimepicker({
	lang:'en',
	timepicker:false,
	format: 'm/d/Y'
});

$(document).on("click", "#cancelBtn, .goSummary", function() {
	location.href = "groupDirectorSummary";
});

$(document).on("click", ".goNewRec", function() {
	location.href = "groupDirector";
});


jQuery(function($){
   $("#phone").mask("999-999-9999");
   $("#mobile").mask("999-999-9999");
});

$(document).ready(function() {
	var groupDirectorId="";
	groupDirectorId= $("#groupDirectorId").val();
	
	/*if(groupDirectorId==null || groupDirectorId=="" || groupDirectorId=="0"){
		$("#email").val("");
		$("#password").val("");
	}*/
	
    $('#groupDirectorForm').bootstrapValidator({
        fields: {
        	groupId: {
                validators: {
                    notEmpty: {
                          	message: $("#err_group_name").val()
                    },
                    greaterThan: {
                        value: 0,
                        inclusive: false,
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
            dateRegistrated: {
                validators: {
                    notEmpty: {
                    	message: $("#err_date_registration").val()
                    }
                }
            },
            firstName: {
                validators: {
                    notEmpty: {
                    	message: $("#err_fname").val()
                    }
                }
            },
            lastName: {
                validators: {
                    notEmpty: {
                    	message: $("#err_lname").val()
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
            /*password : {
				validators : {
					notEmpty : {
						 message: $("#err_pwd").val()
					},
					stringLength: {
                        min: 6,
                        message: $("#error_pwd_min_length").val()
                    }
				}
			},*/
			phone: {
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
			mobile: {
                validators: {
                	/*notEmpty: {
                    	message: $("#err_mobile").val()
                    },*/
                    phone: {
                        country: 'US',
                        message: $("#err_mobile_format").val()
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

$(document).on("blur", "#dateRegistrated", function() {
	setTimeout(function(){$('#groupDirectorForm').bootstrapValidator('revalidateField', 'dateRegistrated')}, 500);	
});
function checkValues() {
	var x = document.getElementById("file");
	var txt = "";
	var err="";
	var isError = false;
	if ('files' in x) {
		if (x.files.length > 0) {
			for (var i = 0; i < x.files.length; i++) {
	            var file = x.files[i];
	            if ('size' in file) {
	            	txt += "size: " + file.size + " bytes <br>";
	            	// 1 MB is equal to 1048576 bytes
	            	// 2.5 MB is equal to 2621440 bytes 
	            	if (file.size > 2621440) {
	            		err += 'File Size should be less than 2.5 MB';
	            		isError = true;
	            	}	
	            }
	            var fileExtension = file.name.substr(file.name.lastIndexOf('.') + 1);
	    		txt += "ext : " + fileExtension + " <br>";
	    		if (fileExtension != 'png' && fileExtension != 'PNG' &&
	    				fileExtension != 'gif' && fileExtension != 'GIF' ) {
	    			if (err !="")  
	    				err += " and ";
	    			err += 'GIF or PNG files only allowed in profile image';
            		isError = true;
	    		}
			}
		}
	} 
	//alert(isError)
	if(isError)
		{
			$('button[type="submit"]').prop('disabled','disabled');
			//$("#file").val('');
		}else
		{
			 $('button[type="submit"]').removeAttr('disabled');
		}
	
	$(".file-upload-errors").html(err);
	/*if (!isError) {
		$("#submitGroupdirectorHidden").click();	
		var validatorObj = $('#groupDirectorForm').data('bootstrapValidator');
	    validatorObj.validate();
	    return validatorObj.isValid();
	} else {
		$("#file").focus();
	}*/
	
}

function sendLoginEmail()
{
	var groupDirectorId=document.forms[0].groupDirectorId.value;
	if(groupDirectorId>0) {
		document.forms[0].method="POST";
		document.forms[0].action = "sendGroupDirectorCredentialsEmail";
		document.forms[0].submit();
	} else {
		$.alert({
 		    title: '',
 		    content: 'Save User Account to Send Credentials Email !',
 		});
	}
}