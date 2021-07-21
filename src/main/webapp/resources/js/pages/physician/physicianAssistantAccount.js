var dateFormat = "MM/DD/YYYY";
var rdate = $("#dateRegistrated");

$(rdate).datetimepicker({
	lang:'en',
	timepicker:false,
	format: 'm/d/Y'
});

$(document).on("click", "#cancelBtn, .goSummary", function() {
	location.href = "physicianassistantaccountsummary";
});

$(document).on("click", ".goNewRec", function() {
	location.href = "physicianassistantaccount";
});

jQuery(function($){
   $("#phone").mask("999-999-9999");
   $("#mobile").mask("999-999-9999");
});

/*$(document).on("change", "#autoCompleterPhysicianId", function() {
	 $('#assistant').bootstrapValidator('revalidateField', 'autoCompleterPhysicianId');
	 var physicianId = $(this).val();
	 
	 if (parseInt(physicianId) > 0) {
		 var data = new FormData();
		 data.append('physicianId',  physicianId);
		
		 var xhr = new XMLHttpRequest();
		 xhr.open('POST', urlPath + '/prescription/fetchPhysicianInfo', true);
		 xhr.send(data);
		 xhr.onload = function () {
			 var data = this.responseText;
			 var responseData = $.parseJSON(data);
			 
			 $("#clinicName").val(responseData.clinicName);
			 $("#groupName").val(responseData.groupName);
		 }
	 }
});

$(document).on("change", "#autoCompleterClinicId", function() {
	 $('#assistant').bootstrapValidator('revalidateField', 'autoCompleterClinicId');
});*/


$(document).ready(function() {
	  createSelectedPhysicianLst();
	  $(".targetDiv").css("height", $(".sourceDiv").css("height"));
	  
	  $('#assistant').bootstrapValidator({
        fields: {
        	autoCompleterPhysicianId :{
                validators: {
                	callback: {
                        message: $("#err_phyname").val(),
                        callback: function(value, validator, $field) {
                        	try {
								                        	//alert($(".autoCompleterPhysicianId option:selected").text())
                                  //alert(value)           	
                                  var value=$(".autoCompleterPhysicianId option:selected").text();
                                  
                                  var physicianNameVar2 = $("#physicianName").val();
                              	if (physicianNameVar2 == null || physicianNameVar2 == "") {
                              		physicianNameVar2 = "Select";
                              	}
                              	//alert("111"+physicianNameVar2)
                                //alert(value)
	                        	if((value=="" || value=="Select")   && (physicianNameVar2=="Select" || physicianNameVar2==""))
	                    			{
	                        			return {
	                        				valid:false,
	                        				message: $("#err_phyname").val()
	                        			}
	                    				
	                    			}else
	                				{
	                    				return true;
	                				}
							} catch (e) {
								alert(e)
							}
                        	
                        }
                    }
                }
        	},
        	/*autoCompleterClinicId :{
                validators: {
                	callback: {
                        message: $("#err_clinic").val(),
                        callback: function(value, validator, $field) {
                        	try {
								                        	//alert($(".autoCompleterPhysicianId option:selected").text())
                                  //alert(value)           	
                                  var value=$(".autoCompleterClinicId option:selected").text();
                                  $("#autoCompleterClinicId").val("test");
                                  //alert(value)
	                        	if(value=="" || value=="Select")
	                    			{
	                        			return {
	                        				valid:false,
	                        				message: $("#err_clinic").val()
	                        			}
	                    				
	                    			}else
	                				{
	                    				return true;
	                				}
							} catch (e) {
								alert(e)
							}
                        	
                        }
                    }
                }
        	},*/
        	/*physicianId :{
                validators: {
                    notEmpty: {
                    	
                          	message: $("#err_phyname").val()
                    },
                    greaterThan: {
                        value: 0,
                        inclusive: false,
                        message: $("#err_phyname").val()
                    }
                }
        	},
        	clinicId: {
                validators: {
                    notEmpty: {
                          	message: $("#err_clinic").val()
                    },
                    greaterThan: {
                        value: 0,
                        inclusive: false,
                        message: $("#err_clinic").val()
                    }
                }
            },*/      	
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
            
            
           /* password : {
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
            },
            groupId : {
            	validators: {
            		callback: {
    	        		message: $("#err_group_id").val(),
    	        		callback: function(value, validator, $field) {
    	        			if (value != "0")
    	        				return true;
    	        			else
    	        				return false;
    	        		}
            		}
                }
            },
            physicianSelectedList: {
            	validators: {
		        	callback: {
		                message:  $("#err_phyname").val(),
		                callback: function (value, validator, $field) {
		                	//alert(value)
		                	if ($("#status").val() == "Active") {
		                		if (value!=null && value.length > 0)
			                        return true;
			                    else
			                    	return false;
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

$(document).on("blur", "#dateRegistrated", function() {
	setTimeout(function(){$('#assistant').bootstrapValidator('revalidateField', 'dateRegistrated')}, 500);	
});


try {
	var physicianNameVar = $("#physicianName").val();
	if (physicianNameVar == null || physicianNameVar == "") {
		physicianNameVar = "Select";
	}
	var formId = $("#physicianId").val();
	//alert(formId)
	var results = [];
	$('.autoCompleterPhysicianId').select2({
		placeholder : physicianNameVar,
		minimumInputLength : 0,
		ajax : {
			url : urlPath + 'getAutoCompleteApprovedPhysiciansList',
			dataType : 'json',
			data : function(params) {
				return {
					term : $.trim(params.term),
					formId : formId
				};
			},
			processResults : function(data) {
				//alert(data)
				results = [];
				data.forEach(function makeResults(element) {
					//alert(element)
					results.push({
						id : element.id,
						text : element.physicianName
					});
				});
				return {
					results : results
				};

				  
				return {
				  results: data
				}; 
			},
			cache : true
		}
	}).on('change', function(e) {
		//alert(this.value)
		$("#physicianId").val(this.value)
		var phyvalue=$(".autoCompleterPhysicianId option:selected").text();
	    if(phyvalue!=null && phyvalue!="" && phyvalue!="Select")
	    	$("#physicianName").val(phyvalue) ;
	});
} catch (e) {
	alert(e)
}

/*
try {
	var clinicNameVar = $("#clinicName").val();
	if (clinicNameVar == null || clinicNameVar == "") {
		clinicNameVar = "Select";
	}
	var formId = $("#clinicId").val();
	//alert(formId)
	var cresults = [];
	$('.autoCompleterClinicId').select2({
		placeholder : clinicNameVar,
		minimumInputLength : 0,
		ajax : {
			url : urlPath + 'getAutoCompleteClinicList',
			dataType : 'json',
			data : function(params) {
				return {
					term : $.trim(params.term),
					formId : formId
				};
			},
			processResults : function(data) {
				//alert(data)
				cresults = [];
				data.forEach(function makeResults(element) {
					//alert(element)
					cresults.push({
						id : element.id,
						text : element.clinicName
					});
				});
				return {
					results : cresults
				};

				  
				return {
				  results: data
				}; 
			},
			cache : true
		}
	}).on('change', function(e) {
		//alert(this.value)
		$("#clinicId").val(this.value)
	});
} catch (e) {
	alert(e)
}*/
//Multiple select lit box
$(document).on("change", "#groupId", function() {
	var groupId = $(this).val();
	var physicianId = $("#physicianId").val();
	
	if (groupId > 0) {
		
		var data = new FormData();
		data.append('groupId', groupId+"");
		data.append('physicianId', physicianId+"");
		
	 	var xhr = new XMLHttpRequest();
	 	xhr.open('POST', 'fetchGroupWisePhysicians', true);
	 	xhr.send(data);
	 	xhr.onload = function () {
	 		var data = this.responseText;
	 		var responseData = $.parseJSON(data);
	 		
	 		
	 	// To clear dropdown values we need to write code like as shown below
 		$('#lstBox1').empty();
 		$('#lstBox2').empty();
 		// Bind new values to dropdown
 		/*	$('#lstBox1').each(function() {
	 		// Create option
	 		var option = $("<option />");
	 		option.attr("value", '0').text('Select');
	 		$('#lstBox1').append(option);
 		});*/
 		
         //alert("Success: " + responseData );
         //alert("data length  ==="+responseData.length)
         
         for ( var i = 0; i < responseData.length; i++) {
     		var obj = responseData[i];
     		//alert(obj);
     		//alert(obj.id+"==========="+obj.physicianName)
    		var option = $("<option />");
	 		option.attr("value", obj.id).text(obj.physicianName);
	 		$('#lstBox1').append(option);
	 		
     	
     	}
             
	 		
		};
	}else
		{
		
    		// To clear dropdown values we need to write code like as shown below
	 		$('#lstBox1').empty();
	 		$('#lstBox2').empty();
	 		// Bind new values to dropdown
	 		/*$('#lstBox1').each(function() {
		 		// Create option
		 		var option = $("<option />");
		 		option.attr("value", '0').text('Select');
		 		$('#lstBox1').append(option);
	 		});*/
		
		}
});
function createSelectedPhysicianLst()
{
	 var physicianArray=[];
	 // physicianArray.length=0;
	 
	 if (document.forms[0].physicianSelectedList) {
		 var selectedPhysicianId=document.forms[0].physicianSelectedList;
	     for (i=0;i<selectedPhysicianId.options.length;i++) {
	    	 var current = selectedPhysicianId.options[i];
	    	 var txt = current.text;
	    	 var val = current.value;
	    	 current.selected=true;
	    	 physicianArray.push(val);
	      }
	      document.forms[0].selectedPhysicianId.value=physicianArray; 
	 }
}



$('#btnRight').click(function (e) {
    moveToListAndDelete('#lstBox1', '#lstBox2');
    
    createSelectedPhysicianLst();
    
    $('#assistant').bootstrapValidator('revalidateField', 'physicianSelectedList');
    e.preventDefault();
});

$('#btnAllRight').click(function (e) {
    moveAllToListAndDelete('#lstBox1', '#lstBox2');
    
    createSelectedPhysicianLst();
    
    $('#assistant').bootstrapValidator('revalidateField', 'physicianSelectedList');
    e.preventDefault();
});

$('#btnLeft').click(function (e) {
    moveToListAndDelete('#lstBox2', '#lstBox1');
    
    createSelectedPhysicianLst();
    
    $('#assistant').bootstrapValidator('revalidateField', 'physicianSelectedList');
    e.preventDefault();
});

$('#btnAllLeft').click(function (e) {
    moveAllToListAndDelete('#lstBox2', '#lstBox1');
    
    createSelectedPhysicianLst();
    
    $('#assistant').bootstrapValidator('revalidateField', 'physicianSelectedList');
    e.preventDefault();
});

$(document).on("change", "#status", function() {
	setTimeout(function(){$('#assistant').bootstrapValidator('revalidateField', 'physicianSelectedList')}, 500);	
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
		//$("#submitAssistprofileHidden").click();	
		var validatorObj = $('#assistant').data('bootstrapValidator');
	    validatorObj.validate();
	    return validatorObj.isValid();
	} else {
		$("#file").focus();
	}*/
	
}
function sendLoginEmail()
{
	alert("Check");
	var assistantId=document.forms[0].assistantId.value;
	if(assistantId>0) {
		document.forms[0].method="POST";
		document.forms[0].action = "sendPhysicianAsstAccountCredentialsEmail";
		document.forms[0].submit();
	} else {
		$.alert({
 		    title: '',
 		    content: 'Save User Account to Send Credentials Email !',
 		});
	}
}