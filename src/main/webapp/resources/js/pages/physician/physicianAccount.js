var rdate = $("#dateRegistrated");
$(rdate).datetimepicker({
	lang:'en',
	timepicker:false,
	format: 'm/d/Y'
});

$("#uploadedDocExpiryDate").datetimepicker({
	lang:'en',
	timepicker:false,
	format: 'm/d/Y',
	minDate:new Date()
});
$("#uploadedDocExpiryDate").on('keypress paste', function (e) {
    e.preventDefault();
    return false;
});

var formSave=1;
jQuery(function($){
   $("#phone").mask("999-999-9999");
   $("#mobile").mask("999-999-9999");
   $("#zipCode").mask("99999-9999");
   $("#billingZipCode").mask("99999-9999");
   
//   $("#phone2").mask("999-999-9999");
//   $("#phone3").mask("999-999-9999");
   $("#fax").mask("999-999-9999");
   $("#zipCode2").mask("99999-9999");
   
   $("#cardCvcNumber").mask("9999")
   $("#expYear").mask("9999");
   //$("#stateLicense").mask("AA 999999");
   $("#stateLicense").mask("SS 999999");
   //$("#status option[value='New Modifications']").prop("disabled", true);//temp commented not saved at back-end
   
   $(".otherFileNameBlock").hide();
   //$(".expiryDateBlock").hide();
});



$(document).on("change", "#uploadedDocFilePurpose", function() {
	var type = $(this).val();
	
	if (type == "DEA License" || type == "State License") {
		$(".expiryDateBlock").show();
		$("#uploadedOtherDocFileName").val("");
		$(".otherFileNameBlock").hide();
	} else if (type == "Other") {
		$(".otherFileNameBlock").show();
		$("#uploadedDocExpiryDate").val("");
		$(".expiryDateBlock").hide();
	} else {
		$("#uploadedOtherDocFileName").val("");
		$("#uploadedDocExpiryDate").val("");
		$(".otherFileNameBlock").hide();
		$(".expiryDateBlock").show();
	}
});


function clearDefaultValue(obj) {
	if (obj.value == "0")
		obj.value = "";
}
  
$(document).on("click", ".goSummary", function() {
	location.href = $("#serverUrl").val() + "/physician/physicianSummary";
});

$(document).on("click", ".goNewRec", function() {
	location.href = $("#serverUrl").val() + "/physician/createPhysicianAccount";
});

$(document).on("click", ".backPrescription", function() {
	document.physicianAccount.action = "backPhysicianPrescription";
	document.physicianAccount.submit();
});

$(document).on("keyup", "#firstName, #middleName, #lastName", function() {
	var f = ($("#firstName").val()).trim();
	var m = ($("#middleName").val()).trim();
	var l = ($("#lastName").val()).trim();
	var g = $("#groupName").val();
	if (g == undefined || g == null || g == "") {
		if (parseInt($("#groupId").val()) > 0)
			g = " - " + ($("#groupId").find("option:selected").text()).trim();
		else
			g = " ";
	} else {
		g = " - " + g;
	}
	g = " " + (g).trim();
	
	$("#physicianNameWithGroupName").val(f + " " + m + " " + l + g);
});


$(document).on("change", "#clinicId", function() {
	var cId = $(this).val();
	
	if (parseInt(cId) > 0) {
		var data = new FormData();
		data.append('clinicId',  cId);
		
	 	var xhr = new XMLHttpRequest();
	 	xhr.open('POST', $("#serverUrl").val() +'/clinic/getClinicAddress', true);
	 	xhr.send(data);
	 	xhr.onload = function () {
	 		// alert(this.responseText);
	 		var data = this.responseText;
	 		var responseData = $.parseJSON(data);

	 		if ($("#address1").val().trim() == "" && $("#city").val().trim() == "" && $("#state").val().trim() == "" && $("#zipCode").val().trim() == "") {
	 			$("#address1").val(responseData.address );
		 		$("#city").val(responseData.city );
		 		$("#state").val(responseData.state );
		 		$("#zipCode").val(responseData.zipCode );	
	 		}
	 	}
	}
});


function setYearDropDown(ele) {
	var min = new Date().getFullYear(),
    max = min + 14,
    select = document.getElementById(ele);

	for (var i = min; i<=max; i++){
	    var opt = document.createElement('option');
	    opt.value = i;
	    opt.innerHTML = i;
	    select.appendChild(opt);
	}
}

/*$(document).ready(function() {
	formValidation(true);
});
*/
var isPhysicianClinicBtnClicked=false;
function isFieldValidate() {
	/*if(isPhysicianClinicBtnClicked)
		{
			return true;
		}else
		{*/
			if ($("#status").val() == "Approved" || $("#status").val() == "Profile Completed" ) 
				return true;
			else
				return false;
		//}
}

function formValidation(onLoad) {
	$('#physicianAccount').bootstrapValidator({
		excluded: [':disabled', ':hidden', ':not(:visible)'],
		fields: {
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
            dateRegistrated: {
                validators: {
                    notEmpty: {
                    	message: $("#err_date_registration").val()
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
            phone: {
                validators: {
                    /*phone: {
                        country: 'US',
                        message: $("#err_phone_format").val()
                    },*/
                    callback: {
		                message:  $("#err_phone").val(),
		                callback: function (value, validator, $field) {
	                		if (value == "") {
	                			return {
                                    valid: false,
                                    message: $("#err_phone").val()
                                }	
	                		} else if (value.length != 12) {
	                			return {
                                    valid: false,
                                    message: $("#err_phone_format").val()
                                }
	                		}
	                		else
		                    	return true;
		                }
		            }
                }
            },
            fax: {
                validators: {
                    /*phone: {
                        country: 'US',
                        message: $("#err_fax_format").val()
                    }*/
                	callback: {
		                message:  $("#err_fax").val(),
		                callback: function (value, validator, $field) {
		                	if (isFieldValidate()) {
		                		if (value == "") {
		                			return {
	                                    valid: false,
	                                    message: $("#err_fax").val()
	                                }	
		                		} else if (value.length != 12) {
		                			return {
	                                    valid: false,
	                                    message: $("#err_fax_format").val()
	                                }
		                		}
		                		else
			                    	return true;
		                	} else
		                		return true;
		                }
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
            address1: {
                validators: {
                    /*notEmpty: {
                        message: $("#err_city").val()
                    }*/
                	callback: {
		                message:  $("#err_street").val(),
		                callback: function (value, validator, $field) {
		                	if (isFieldValidate()) {
		                		if (value != "")
			                        return true;
			                    else
			                    	return false;
		                	} else
		                		return true;
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
		                	if (isFieldValidate()) {
		                		if (value != "")
			                        return true;
			                    else
			                    	return false;
		                	} else
		                		return true;
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
		                	if (isFieldValidate()) {
		                		if (value != "")
			                        return true;
			                    else
			                    	return false;
		                	} else
		                		return true;
		                }
		            }
                }
            },
            zipCode: {
                validators: {
                	/* zipCode: {
                        country: 'US',
                        message: $("#err_zip_format").val()
                    }*/
                	callback: {
		                message:  $("#err_zip").val(),
		                callback: function (value, validator, $field) {
		                	if (isFieldValidate()) {
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
		                	} else
		                		return true;
		                }
		            }
                }
            },
            zipCode2: {
                validators: {
                    zipCode: {
                        country: 'US',
                        message: $("#err_zip_format").val()
                    }
                }
            },
            groupId: {
            	validators: {
		        	callback: {
		                message:  $("#err_group").val(),
		                callback: function (value, validator, $field) {
		                	//alert(value)
		                	//if (isFieldValidate()) {
			                	if (value > 0)
			                        return true;
			                    else
			                    	return false;
		                	/*} else {
		                		return true;
		                	}*/
		                }
		            }
            	}
            },
            clinicSelectedList: {
            	validators: {
		        	callback: {
		                message:  $("#err_clinic").val(),
		                callback: function (value, validator, $field) {
		                	if (isFieldValidate() && !isPhysicianClinicBtnClicked) {
		                		if (value!=null && value.length > 0)
		                		{
		                			/*var groupId=$("#groupId").val();
		                			var newGroupId=$("#newGroupId").val();
		                			if(groupId==newGroupId)
	                				{
		                				return {
	                        				valid:false,
	                        				message: $("#err_approve_clinic").val()
	                        			}
		                				
	                				
	                				}else
	                					return true;*/
		                			//alert(document.forms[0].selectedClinicId.value)
		                			if(document.forms[0].selectedClinicId.value!=null && document.forms[0].selectedClinicId.value.length>0)
		                				{
		                					var clnId = 0;
		                					var cliniValue=document.forms[0].selectedClinicId.value;
		                					var cliniArray=cliniValue.split(",");
		                					for (j=0;j<cliniArray.length;j++){
		                						
		                						
		                						var ClinicStatusId=document.forms[0].clinicSelectedStatusList;
		                				          for (i=0;i<ClinicStatusId.options.length;i++)
		                				          {
		                				  	   
		                				  	        var current = ClinicStatusId.options[i];
		                				  	        var txt = current.text;
		                				  	        var val = current.value;
		                				  	        //alert(txt + " : " + val);
		                				  	       if(cliniArray[j]==val){
		                								if (txt != "Active") {
		                									clnId = val;
		                									break;
		                								}
		                							}
		                				  	        
		                				          }
		                				      
		                				          if(clnId>0)
			                						{
		                				        	  break;
			                						}
		                					}
		                					
		                					if(clnId>0)
		                						{
			                						return {
				                        				valid:false,
				                        				message: $("#err_approve_clinic").val()
				                        			}
		                						
		                						}else
		    	                					return true;
		                			}
		                			
		                			
		                			
		                			
		                		}
			                    else
			                    	{
				                    	return {
	                        				valid:false,
	                        				message: $("#err_clinic").val()
	                        			}
			                    	}
		                	} else
		                		return true;
		                }
		            }
            	}
            },
            dea: {
                validators: {
                	/*notEmpty : {
						 message: $("#err_dea").val()
					},
                	stringLength: {
		            	min: 5,
		            	message: $("#err_dea_format").val()
		            }*/
                	
                	callback: {
		                message:  $("#err_dea").val(),
		                callback: function (value, validator, $field) {
		                	//alert(value)
		                	if (isFieldValidate()) {
		                		if (value!=null && value.length > 4)
			                        return true;
		                		else if (value == "") {
		                			return {
                        				valid:false,
                        				message: $("#err_dea").val()
                        			}
		                		}
		                		else if (value.length < 5) {
			                    	return {
                        				valid:false,
                        				message: $("#err_dea_format").val()
                        			}
			                    }
		                	} else {
		                		if (value != "") {
		                			if (value.length > 4) {
		                				 return true;
		                			} else {
		                				return {
	                        				valid:false,
	                        				message: $("#err_dea_format").val()
	                        			}	
		                			}
		                		} else {
		                			return true;
		                		} 
		                	}
		                }
		            }
                }
            },
            stateLicense: {
                validators: {
		            /*stringLength: {
		            	min: 2,
		            	message: $("#err_lic_state").val()
		            }*/
                	callback: {
		                message:  $("#err_lic_state").val(),
		                callback: function(value, validator, $field) {
	                		if (isFieldValidate()) {
	                			if (value!=null && value.length > 4)
			                        return true;
		                		else if (value == "") {
		                			return {
                        				valid:false,
                        				message: $("#err_lic_state").val()
                        			}
		                		}
		                		else if (value.length < 5) {
			                    	return {
                        				valid:false,
                        				message: $("#err_lic_state_format").val()
                        			}
			                    }
	                		} else {
		                		if (value != "") {
		                			if (value.length > 4)
		                				return true;
		                			else {
		                				return {
	                        				valid:false,
	                        				message: $("#err_lic_state_format").val()
	                        			}	
		                			}
		                		} else {
		                			return true;
		                		} 
		                	}
		                }
                	}
                }
            }, 
            npi: {
                validators: {
		            /*stringLength: {
		            	min: 5,
		            	message: $("#err_npi_format").val()
		            }*/
                	callback: {
		                message:  $("#err_npi_format").val(),
	                	callback: function(value, validator, $field) {
	                		if (isFieldValidate()) {
	                			if ((value == null || value == "") || (value.length > 5))  
	                				return true;
	                			else {
	                				return {
	                    				valid:false,
	                    				message: $("#err_npi_format").val()
	                    			}	
	                			}
	                		} else {
	                			if (value != "") {
		                			if (value.length > 4)
		                				return true;
		                			else {
		                				return {
	                        				valid:false,
	                        				message: $("#err_lic_state_format").val()
	                        			}	
		                			}
		                		} else {
		                			return true;
		                		} 
	                		}
	                	}
                	}
                }
            },
            cardType: {
                validators: {
                    callback: {
		                message:  $("#err_cardType").val(),
		                callback: function (value, validator, $field) {
		                	if (isFieldValidate()) {
		                		if (value > 0)
			                        return true;
			                    else
			                    	return false;
		                	} else
		                		return true;
		                }
		            }
                }
            },
            cardNumber: {
                validators: {
                    creditCard: {
                        message: $("#err_card_no_format").val()
                    },
                    callback: {
		                message:  $("#err_cardNo").val(),
		                callback: function (value, validator, $field) {
		                	if (isFieldValidate()) {
		                		if (value != "")
			                        return true;
			                    else
			                    	return false;
		                	} else
		                		return true;
		                }
		            }
                }
            },
            cardCvcNumber: {
                validators: {
                    stringLength: {
                        min: 3,
                    	max: 4,
                    	message: $("#err_cvcNo_format").val()
                    },
                    callback: {
		                message:  $("#err_cvcNo").val(),
		                callback: function (value, validator, $field) {
		                	if (isFieldValidate()) {
		                		if (value != "")
			                        return true;
			                    else
			                    	return false;
		                	} else
		                		return true;
		                }
		            }
                }
            },
            cardHolderName: {
                validators: {
                    callback: {
		                message:  $("#err_cardHolder").val(),
		                callback: function (value, validator, $field) {
		                	if (isFieldValidate()) {
		                		if (value != "")
			                        return true;
			                    else
			                    	return false;
		                	} else
		                		return true;
		                }
		            }
                }
            },
            expMonth: {
                validators: {
                    /*notEmpty: {
                        message: $("#err_cardExpMonth").val()
                    },*/
		            callback: {
		                message:  $("#err_cc_expiry").val(),
		                callback: function (value, validator, $field) {
		                	var expyear1 = $("#expYear").val();
		                	if ((value == "" && expyear1 != "") || (value != "" && expyear1 == "") )
		                		return false;
		                	else if (isFieldValidate() || value != "") {
			                	var m = cardExpiryValidationMonth();
			                	return m;
		                	} else if(isFieldValidate() && value == "" ){
		                		return false;
		                	}
		                	else{
		                		return true;
		                	}
		                }
		            }
                }
            },
            expYear: {
                validators: {
                    /*notEmpty: {
                        message: $("#err_cardExpYear").val()
                    },*/
                    stringLength: {
                        min: 4,
                    	max: 4,
                        message: $("#err_cardExpYear_format").val()
                    },
		            callback: {
		                message:  $("#err_cc_expiry_year").val(),
		                callback: function (value, validator, $field) {
		                	var expMonth1 = $("#expMonth").val();
		                	if ((value == "" && expMonth1 != "") || (value != "" && expMonth1 == "") )
		                		return false;
		                	else if (isFieldValidate() || value != "") {
			                	var m = cardExpiryValidationYear(); 
			                	return m;
		                	} else {
		                		return true;
		                	}
		                }
		            }
                }
            },
            billingZipCode: {
            	validators: {
                	callback: {
		                message:  $("#err_billing_zip").val(),
		                callback: function (value, validator, $field) {
		                	if (isFieldValidate()) {
		                		if (value != ""){
		                			if (value.length == 5 || value.length == 10)
		                				return true;
		                			else {
		                				return {
	                        				valid:false,
	                        				message: $("#err_billing_zip_format").val()
	                        			}
		                			}
		                		}   
			                    else {
			                    	return {
	                    				valid:false,
	                    				message: $("#err_billing_zip").val()
	                    			}
			                    }
		                	} else
		                		return true;
		                }
		            }
                }
            },
            useGroupLogo: {
                validators: {
                    callback: {
		                message:  $("#err_phy_group_logo").val(),
		                callback: function (value, validator, $field) {
		                	//if (isFieldValidate()) {
		                		//alert(physicianLogo)
		                		if((physicianLogo=="" || physicianLogo.indexOf("images/default_logo.jpg?")!=-1)  && document.getElementById('useGroupLogo').checked==false) {
		                			return false;
	                			} else
			                    	return true;
		                	/*} else
		                		return true;*/
		                }
		            }
                }
            },
            deniedStatusFlg: {
                validators: {
                    callback: {
		                message:  " ",
		                callback: function (value, validator, $field) {
		                		var status=$("#status").val();
		                		var deniedStatusFlg=$("#deniedStatusFlg").val();
		                		//alert("errrrrrrrrrrrrrrrrrrrrrrr"+deniedStatusFlg)
		                		if(previousStatus!="Denied" && status=="Denied" && deniedStatusFlg=="1")
	                			{
		                			var statflag=validateFormStatusEvent();
		                			if(!statflag)
		                				return false;
		                			else
		                				return true;
	                			}else
	                				return true;
		                		
		                		//return true;
		                 	
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
}

function cardExpiryValidationMonth() {
	var today, someday;
	var exMonth=document.getElementById("expMonth").value;
	var exYear=document.getElementById("expYear").value;
	today = new Date();
	someday = new Date();
	someday.setFullYear(exYear, exMonth, 0);
	

	if (someday < today) {
		
		if(exYear >= today.getFullYear()){
			return false;
		}
		else return true;
	} else {
		return true;
	}
}

function cardExpiryValidationYear() {
	var today, someday;
	var exMonth=document.getElementById("expMonth").value;
	var exYear=document.getElementById("expYear").value;
	today = new Date();
	someday = new Date();
	someday.setFullYear(exYear, exMonth, 0);
	

	if (someday < today) {
		
		if(exYear >= today.getFullYear()){
			return true;
		}
			return false;
		
	} else {
		return true;
	}
}

$(document).on("click", "#useGroupLogo", function() {
	$("div.tooltip").remove();
});

$(document).on("blur", "#dateRegistrated", function() {
	setTimeout(function(){$('#physicianAccount').bootstrapValidator('revalidateField', 'dateRegistrated')}, 500);	
});

$(document).on("change, blur", "#expMonth, #expYear", function() {
	$('#physicianAccount').bootstrapValidator('revalidateField', 'expMonth');
	$('#physicianAccount').bootstrapValidator('revalidateField', 'expYear');
});

$(document).on("change", "#status", function() {
	if ($(this).val() == "Denied") {
		$("#deniedStatusFlg").val("1");
		var alertTxt = "Are you sure do you want to deny the Physician "+$("#physicianName").val();
		alertTxt += "\nNote: All of his Assistant Profile will be Inactive automatically";
		$.alert({
 		    title: '',
 		    content: alertTxt,
 		});
	}
	
	$('#physicianAccount').bootstrapValidator('revalidateField', 'firstName');
	$('#physicianAccount').bootstrapValidator('revalidateField', 'lastName');
	$('#physicianAccount').bootstrapValidator('revalidateField', 'email');
	$('#physicianAccount').bootstrapValidator('revalidateField', 'phone');
	$('#physicianAccount').bootstrapValidator('revalidateField', 'fax');
	
	$('#physicianAccount').bootstrapValidator('revalidateField', 'address1');
	$('#physicianAccount').bootstrapValidator('revalidateField', 'state');
	$('#physicianAccount').bootstrapValidator('revalidateField', 'city');
	$('#physicianAccount').bootstrapValidator('revalidateField', 'mobile');
	
	$('#physicianAccount').bootstrapValidator('revalidateField', 'dea');
	//$('#physicianAccount').bootstrapValidator('revalidateField', 'clinicId');
	$('#physicianAccount').bootstrapValidator('revalidateField', 'clinicSelectedList');
	
	$('#physicianAccount').bootstrapValidator('revalidateField', 'groupId');
	$('#physicianAccount').bootstrapValidator('revalidateField', 'zipCode2');
	$('#physicianAccount').bootstrapValidator('revalidateField', 'zipCode');
	
	$('#physicianAccount').bootstrapValidator('revalidateField', 'npi');
	$('#physicianAccount').bootstrapValidator('revalidateField', 'stateLicense');
	
	$('#physicianAccount').bootstrapValidator('revalidateField', 'expYear');
	$('#physicianAccount').bootstrapValidator('revalidateField', 'expMonth');
	$('#physicianAccount').bootstrapValidator('revalidateField', 'cardHolderName');
	$('#physicianAccount').bootstrapValidator('revalidateField', 'cardCvcNumber');
	$('#physicianAccount').bootstrapValidator('revalidateField', 'cardNumber');
	$('#physicianAccount').bootstrapValidator('revalidateField', 'cardType');
	$('#physicianAccount').bootstrapValidator('revalidateField', 'useGroupLogo');
	$('#physicianAccount').bootstrapValidator('revalidateField', 'billingZipCode');
});


function setDisabled(value) {
	if (value == "Denied") {
		$("#firstName").prop( "disabled", true );
		$("#middleName").prop( "disabled", true );
		$("#lastName").prop( "disabled", true );
		$("#prescriberType").prop( "disabled", true );
		$("#prescriberGroup").prop( "disabled", true );
		$("#dateRegistrated").prop("disabled", true);
		$("#address1").prop( "disabled", true );
		$("#city").prop( "disabled", true );
		$("#state").prop( "disabled", true );
		$("#zipCode").prop( "disabled", true );
		$("#address2").prop( "disabled", true );
		$("#city2").prop( "disabled", true );
		$("#state2").prop( "disabled", true );
		$("#zipCode2").prop( "disabled", true );
		$("#phone").prop( "disabled", true );
		$("#fax").prop( "disabled", true );
		$("#mobile").prop( "disabled", true );
		$("#email").prop( "disabled", true );
		
		$("#website").prop( "disabled", true );
		$("#marketer").prop( "disabled", true );
		$("#comments").prop( "disabled", true );
		$("#groupId").prop( "disabled", true );
		$("#clinicId").prop( "disabled", true );
		
		$("#dea").prop( "disabled", true );
		$("#npi").prop( "disabled", true );
		$("#stateLicense").prop( "disabled", true );
		$(".uploadDocFile").prop( "disabled", true );
		
		$("#cardType").prop( "disabled", true );
		$("#cardNumber").prop( "disabled", true );
		$("#cardCvcNumber").prop( "disabled", true );
		$("#cardHolderName").prop( "disabled", true );
		$("#expMonth").prop( "disabled", true );
		$("#expYear").prop( "disabled", true );
		$("#billingZipCode").prop("disabled", true);
		
		$("#commEmail1").prop("disabled", true);
		$("#commPhone1").prop("disabled", true);
		$("#commTrackingNo1").prop("disabled", true);
		$("#commShipped1").prop("disabled", true);
		$("#commDelivered1").prop("disabled", true);
		$("#commDeliveryExceptions1").prop("disabled", true);
		
		formValidation(false);
	} else {
		$("#firstName").prop( "disabled", false );
		$("#middleName").prop( "disabled", false );
		$("#lastName").prop( "disabled", false );
		$("#prescriberType").prop( "disabled", false );
		$("#prescriberGroup").prop( "disabled", false );
		$("#dateRegistrated").prop("disabled", false);
		$("#address1").prop( "disabled", false );
		$("#city").prop( "disabled", false );
		$("#state").prop( "disabled", false );
		$("#zipCode").prop( "disabled", false );
		$("#address2").prop( "disabled", false );
		$("#city2").prop( "disabled", false );
		$("#state2").prop( "disabled", false );
		$("#zipCode2").prop( "disabled", false );
		$("#phone").prop( "disabled", false );
		$("#fax").prop( "disabled", false );
		$("#mobile").prop( "disabled", false );
		$("#email").prop( "disabled", false );
		
		$("#website").prop( "disabled", false );
		$("#marketer").prop( "disabled", false );
		$("#comments").prop( "disabled", false );
		$("#groupId").prop( "disabled", false );
		$("#clinicId").prop( "disabled", false );
		
		$("#dea").prop( "disabled", false );
		$("#npi").prop( "disabled", false );
		$("#stateLicense").prop( "disabled", false );
		$(".uploadDocFile").prop( "disabled", false );
		
		$("#cardType").prop( "disabled", false );
		$("#cardNumber").prop( "disabled", false );
		$("#cardCvcNumber").prop( "disabled", false );
		$("#cardHolderName").prop( "disabled", false );
		$("#expMonth").prop( "disabled", false );
		$("#expYear").prop( "disabled", false );
		$("#billingZipCode").prop("disabled", false);
		
		$("#commEmail1").prop("disabled", false);
		$("#commPhone1").prop("disabled", false);
		$("#commTrackingNo1").prop("disabled", false);
		$("#commShipped1").prop("disabled", false);
		$("#commDelivered1").prop("disabled", false);
		$("#commDeliveryExceptions1").prop("disabled", false);
		
		formValidation(false);
	}
}









/******************  DOCUMENT FILE UPLOAD PROCESSES STARTS *******************************/
//Variable to store user file
var docfiles;
$(document).on("change", "input[name=docFiles]", function(event) {
	docfiles = event.target.files;
});

var logoFileList;
$(document).on("change", "input[name=uploadLogoFile]", function(event) {
	logoFileList = event.target.files;
	//alert(logoFileList.length)
	physicianLogo=logoFileList.length;
	$('#physicianAccount').bootstrapValidator('revalidateField', 'useGroupLogo');
	
	if(physicianLogo!=null && physicianLogo>0)
		document.getElementById('useGroupLogo').checked=false;
	
	
});

/****************************** FILE DISPLAY (PAGINATION) IN profile page **********************/ 
	var dttable = $("#physicianDocFilesTable").DataTable({
		"processing" : true,
		"serverSide" : true,
		"dom":'<"top">rt<"bottom"pl><"clear">',
		"lengthMenu" : [ [ 5, 10, 15, 25], [ 5, 10, 15, 25] ],
		"pagingType": "simple_numbers",
		"ajax" : {
			"url" : "getPhysicianDocumentsData?p="+$("#physicianId").val(),
			"type" : "POST"
		},
		"oLanguage": {
	        "sEmptyTable":  "No records available"
	    },
		"columns" : [ 
		    { data : "fileId","orderable":false, "sClass":"left_align", "visible": false },
			{ data : "originalFileName","orderable":false, "sClass":"left_align"  },
			
			{ data : "filePurpose","orderable":false, "sClass":"left_align"  },
			{ data : "docExpiryDate","orderable":false, "sClass":"left_align"  },
			{ data : "otherDocFileName","orderable":false, "sClass":"left_align"  },
			
			{ data : "uploadedDate","orderable":false, "sClass":"left_align"  },
			{ data : "uploadedByName","orderable":false, "sClass":"left_align"   },
		    { data : "userType","orderable":false, "sClass":"left_align"   },
		    { 
				"class"			:	"delete-control fileDeleteBtn", 
			    "orderable"		:	false, 
			    "data"			:	null, 
			    "defaultContent":	""
			}
		],
		"columnDefs": [{
			   "render": function ( data, type, row ) {
				   data = '<a href="phyDocFileDownload?f='+row.fileId+'&p='+$("#physicianId").val()+'">'+row.originalFileName+'</a>';
				   // data = '<a href="javascript:void(0);" class="downloadFile">'+row.originalFileName+'</a>';
				    return data;
			   },
			   "targets": 0,
			},
			{ "width": "10px", "targets": [0] },
			{ "width": "100px", "targets": [1] },
			{ "width": "60px", "targets": [2,3,4,5] },
			{ "width": "50px", "targets": [6] },
			{ "width": "40px", "targets": [7] },
			{ "width": "40px", "targets": [8] }
		],
		"fnDrawCallback": function( oSettings ) {
			commentBlockHeightChanges();	
		},
		"error" : "Error while processing data...."
	});
	
	// For Delete Record.
    $("thead tr .delete-control").removeClass("delete-control");
	
    /****************** DOCUMENT UPLOAD PROCESSES *****************/
    $(document).on("click", ".uploadDocFile", function(event) {
    	event.stopPropagation(); // Stop stuff happening
    	event.preventDefault(); // Totally stop stuff happening
    	
    	var phyId = $("#physicianId").val();
    	var error = 0;
    	var data = new FormData();
    	if (phyId == 0) {
    		$.alert({
     		    title: '',
     		    content: 'Save physician then upload Documents !',
     		});
    	} else {
    		var type = $("#uploadedDocFilePurpose").val();
    		if (type == "") {
    			error = 1;
    			$(".file-type-errors").html("Please select file type");
    		}else
    			{
    			$(".file-type-errors").html("");
    			}
    		if (type == "DEA License" || type == "State License") {
    			if ($("#uploadedDocExpiryDate").val() == "") {
    				$(".expiry-date-errors").html("Please select expiry date");
    				error = 1;
    			}else
				{
    				$(".expiry-date-errors").html("");
				}
    		} else if (type == "Other") {
    			if ($("#uploadedOtherDocFileName").val() == "") {
    				$(".file-name-errors").html("Please enter file name");
    				error = 1;	
    			}else
				{
    				$(".file-name-errors").html("");
				}
    		}
    		
    		try {
    			if(docfiles!=null){
    	    		for (var i = 0; i < docfiles.length; i++) {
    	        		var file = docfiles[i];
    	        		console.log(file.size);
    	    	  		data.append('docFile', file, file.name);
    	    	  		data.append('phyId', $("#physicianId").val()+"");
    	    	  		
    	    	  		data.append('docPurpose', $("#uploadedDocFilePurpose").val()+"");
    	    	  		data.append('expiryDate', $("#uploadedDocExpiryDate").val()+"");
    	    	  		data.append('fileName', $("#uploadedOtherDocFileName").val()+"");
    	         	}
        		}else
        			{
        				$(".docFiles-upload-errors").html("Please select File to Upload");
        				error = 1;	
        			}
			} catch (e) {
				alert(e)
				$(".docFiles-upload-errors").html("Please select File to Upload");
				error = 1;	
			}
			if(!error){
         		var phyId = $("#physicianId").val();
        	 	var xhr = new XMLHttpRequest();
        	 	xhr.open('POST', 'phyDocFileUpload', true);
        	 	xhr.send(data);
        	 	xhr.onload = function () {
        	 		var fileTag= "<input type='file' name='docFiles' id='docFiles' accept='image/gif, image/png, application/pdf' onchange='checkDocumentValues()'/>";
        	 		$(".fileTagLoc").html(fileTag);
        	 		docfiles = null;
        	 		if (xhr.readyState == 4 && xhr.status == 200) {
        	 			dttable.ajax.url("getPhysicianDocumentsData?p="+$("#physicianId").val()).draw();
        	 			
        	 			$("#uploadedDocFilePurpose").val("");
            	  		$("#uploadedDocExpiryDate").val("");
            	  		$("#uploadedOtherDocFileName").val("");
            	  		
            	  		$(".file-type-errors").html("");
            	  		$(".expiry-date-errors").html("");
            	  		$(".file-name-errors").html("");
            	  		//alert(999999999999999999)
            	  		//$('#physicianAccount').bootstrapValidator('revalidateField', 'uploadLicense');
            	  		
        	 		}
        		};
        	}
    	}
    });
    
    /****************** DOCUMENT UPLOAD PROCESSES *****************/
    
    /****************** DOCUMENT DELETE PROCESSES *****************/
    $(document).on("click", ".fileDeleteBtn", function(){
    	var fileId=$(this).closest('tr').attr("id").replace("row_", "");
    	var phyId = $("#physicianId").val();
    	var docName=$(this).closest('tr').find("td").html();
    	
    	var alertTxt = "Are you sure do you want to delete this "+docName + " ? ";
    	$.confirm({
    		title: 'Confirm!',
    		content: alertTxt,
    		buttons: {
     			confirm: function () {
     				
     				if (fileId > 0) {
     					var data = new FormData();
     					data.append('fileId', fileId);
     					data.append('phyId', phyId+"");
     					
     				 	var xhr = new XMLHttpRequest();
     				 	xhr.open('POST', 'phyDocFileDelete', true);
     				 	xhr.send(data);
     				 	xhr.onload = function () {
     				 		dttable.ajax.url("getPhysicianDocumentsData?p="+$("#physicianId").val()).draw();
     					};
     				}
     			},
     			cancel: function () {
     				
     			}
    		}
    	});
    });
    

	function fnShowHide( iCol )
	{
			
		 // Get the column API object
        var column = dttable.column(iCol);
 
        // Toggle the visibility
        column.visible( ! column.visible() )
	}
    /****************** DOCUMENT DELETE PROCESSES *****************/
    var previousStatus="";
    $(document).ready(function() {
    	var groupId=$("#groupId").val();
    	var newGroupId=$("#newGroupId").val();
    	if(groupId!=newGroupId)
    		$("#groupId option[value='"+newGroupId+"']").remove();
    	
    	previousStatus=$("#previousStatus").val();
    	setDisabled($("#status").val());
    	createSelectedClinicLst();
    	var physicianId= $("#physicianId").val();
    	//alert(document.getElementById('groupLogoFile').value)
    	if(physicianId==0)
    		{
    			document.getElementById('useGroupLogo').checked=true;
    		}else{
    	
		    	
		    	if(groupId!=null && groupId!="" && groupId!="0"){
			    	if(document.getElementById('useGroupLogo').checked)
			    		{
				    		if(physicianId>0)
				    		{
				    			if(document.getElementById('groupLogoFile').value!="")
				    				document.getElementById('phyLogo').src="/CRE8Portal/resources/"+document.getElementById('groupLogoFile').value;
				    			else
				    				document.getElementById('useGroupLogo').checked=false;
				    		}
			    		}
		    	}else
				{
		    		document.getElementById('useGroupLogo').checked=false;
				}
    		}
    });
    
    //To set the Display Name in the Group Director Login
    function setPhysicianGroupName()
    {
    	
    	try {
			var groupName = $(this).find("option:selected").text();
			if (groupName == "") {
				groupName = $("#groupName").val();
			}
			//alert(groupName)
			if (groupName != null && groupName != undefined
					&& groupName != "undefined") {
				var name = $("#physicianName").val();
				$("#physicianNameWithGroupName").val(name + " - " + groupName);
			}
		} catch (e) {
		}
    }
    
    $(document).on("change", "#groupId", function() {
    	var groupId = $(this).val();
    	var physicianId = $("#physicianId").val();
    	var clinicId = $("#clinicId").val();
    	var groupName = $(this).find("option:selected").text();
    	var name = $("#physicianName").val();
    	
    	if (groupId > 0) {
    		document.forms[0].selectedClinicId.value="";
    		$("#physicianNameWithGroupName").val(name + " - " + groupName);
    		
    		var data = new FormData();
    		data.append('groupId', groupId+"");
    		data.append('clinicId', clinicId+"");
    		
    	 	var xhr = new XMLHttpRequest();
    	 	xhr.open('POST', 'fetchGroupWiseClinic', true);
    	 	xhr.send(data);
    	 	xhr.onload = function () {
    	 		var data = this.responseText;
    	 		var responseData = $.parseJSON(data);
    	 		
    	 		
    	 	// To clear dropdown values we need to write code like as shown below
	 		$('#lstBox1').empty();
	 		$('#lstBox2').empty();
	 		$('#lstBox3').empty();
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
         		//alert(obj.id+"==========="+obj.clinicName)
        		var option = $("<option />");
    	 		option.attr("value", obj.id).text(obj.clinicName);
    	 		$('#lstBox1').append(option);
    	 		
         	
         	}
             
             for ( var i = 0; i < responseData.length; i++) {
          		var obj = responseData[i];
          		//alert(obj);
          		//alert(obj.id+"==========="+obj.clinicName)
         		var option = $("<option />");
     	 		option.attr("value", obj.id).text(obj.status);
     	 		$('#lstBox3').append(option);
     	 		
          	
          	}
             
             //$(".infoCompletedClinic-errors").html("");     
    	};
    }else
    	{
			$("#physicianNameWithGroupName").val(name);
		
    		// To clear dropdown values we need to write code like as shown below
	 		$('#lstBox1').empty();
	 		$('#lstBox2').empty();
	 		$('#lstBox3').empty();
	 		// Bind new values to dropdown
	 		/*$('#lstBox1').each(function() {
		 		// Create option
		 		var option = $("<option />");
		 		option.attr("value", '0').text('Select');
		 		$('#lstBox1').append(option);
	 		});*/
		
		}
    	
    	//$('#physicianAccount').bootstrapValidator('revalidateField', 'clinicSelectedList');
});
    
    
    
    function commentBlockHeightChanges() {
    	if ($("#updatedHistory").length == 0) {
    		var h = $(".ccBlock").height();
        	$(".commentBlock").css("height", (h+22));	
    	}
    }
    
    commentBlockHeightChanges();
    
    function createSelectedClinicLst()
    {
    	 var clinicArray=[];
    	 // clinicArray.length=0;
          var selectedClinicId=document.forms[0].clinicSelectedList;
          for (i=0;i<selectedClinicId.options.length;i++)
          {
  	   
  	        var current = selectedClinicId.options[i];
  	        var txt = current.text;
  	        var val = current.value;
  	        current.selected=true;
  	        //alert(txt+"============"+val);
  	        clinicArray.push(val);
          }
          document.forms[0].selectedClinicId.value=clinicArray;
          //alert(document.forms[0].selectedClinicId.value)
          
         
          
         /* try {
			var clnId = 0;
			var infoCompletedClinicName = "";
			$("#lstBox3 option").each(function(i) {
				//alert($(this).text() + " : " + $(this).val());
				if ($(this).text() != "Active") {
					clnId = $(this).val();
				}

			});
			$("#lstBox2 option").each(function(i) {
				//alert($(this).text() + " : " + $(this).val());
				if ($(this).val() == clnId) {
					infoCompletedClinicName = $(this).text();
				}

			});
			//alert(clnId)

			if (clnId > 0) {
				$.alert({
					title : '',
					content : 'Please Approve the Clinic : '
							+ infoCompletedClinicName,
				});
				
				if(infoCompletedClinicName!=null && infoCompletedClinicName.length>0)
					$(".infoCompletedClinic-errors").html('Please Approve the Clinic');
				else
					$(".infoCompletedClinic-errors").html('');
				 
				
			} 
		} catch (e) {
			alert(e)
		}*/
          
    }
   
    
  
    $('#btnRight').click(function (e) {
        moveToListAndDelete('#lstBox1', '#lstBox2');
        
        createSelectedClinicLst();
        
        $('#physicianAccount').bootstrapValidator('revalidateField', 'clinicSelectedList');
        $('button[type="submit"]').removeAttr('disabled');
        e.preventDefault();
    });

    $('#btnAllRight').click(function (e) {
        moveAllToListAndDelete('#lstBox1', '#lstBox2');
        
        createSelectedClinicLst();
        
        $('#physicianAccount').bootstrapValidator('revalidateField', 'clinicSelectedList');
        $('button[type="submit"]').removeAttr('disabled');
        e.preventDefault();
    });

    $('#btnLeft').click(function (e) {
        moveToListAndDelete('#lstBox2', '#lstBox1');
       
        createSelectedClinicLst();
        
        $('#physicianAccount').bootstrapValidator('revalidateField', 'clinicSelectedList');
        $('button[type="submit"]').removeAttr('disabled');
        e.preventDefault();
    });

    $('#btnAllLeft').click(function (e) {
        moveAllToListAndDelete('#lstBox2', '#lstBox1');
       
        createSelectedClinicLst();
        
        $('#physicianAccount').bootstrapValidator('revalidateField', 'clinicSelectedList');
        $('button[type="submit"]').removeAttr('disabled');
        e.preventDefault();
    });

    $(document).on("change", "#status", function() {
    	setTimeout(function(){$('#physicianAccount').bootstrapValidator('revalidateField', 'clinicSelectedList')}, 500);	
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
    			$("#submitphyprofileHidden").click();
    			var validatorObj = $('#physicianAccount').data('bootstrapValidator');
    		    validatorObj.validate();
    		    return validatorObj.isValid();
    		} else {
    			$("#file").focus();
    		}*/
    	// }
    }
    function checkLogoFileValues() {
    	
		var x = document.getElementById("logofile");
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
	            		err += 'GIF or PNG files only allowed in logo image';
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
		
		$(".logofile-upload-errors").html(err);
		
}
   /* $(document).on("change", "#file", function(event) {
    	$(".file-upload-errors").html("");
    });*/

    function setCheckBoxValues(ind)
    {
    	if(ind==0)
    		{
    			if(document.getElementById('sendalerts').checked)
    				{
    				
	    				document.getElementById('commTrackingNo').checked=true;
						document.getElementById('commShipped').checked=true;
						document.getElementById('commDelivered').checked=true;
						document.getElementById('commDeliveryExceptions').checked=true;
					
    				}else
    				{
    					document.getElementById('commTrackingNo').checked=false;
    					document.getElementById('commShipped').checked=false;
    					document.getElementById('commDelivered').checked=false;
    					document.getElementById('commDeliveryExceptions').checked=false;
    				}
    			
    		}else if(ind==1)
    		{
    			
    			if(!document.getElementById('commTrackingNo').checked)
    				document.getElementById('sendalerts').checked=false;
    			else
    				{
    					if(document.getElementById('commShipped').checked && document.getElementById('commDelivered').checked && document.getElementById('commDeliveryExceptions').checked)
    						document.getElementById('sendalerts').checked=true;
    				}
    			
    		}else if(ind==2)
    		{
    			
    			if(!document.getElementById('commShipped').checked)
    				document.getElementById('sendalerts').checked=false;
    			else
				{
					if(document.getElementById('commTrackingNo').checked && document.getElementById('commDelivered').checked && document.getElementById('commDeliveryExceptions').checked)
						document.getElementById('sendalerts').checked=true;
				}
    			
    		}else if(ind==3)
    		{
    			
    			if(!document.getElementById('commDelivered').checked)
    				document.getElementById('sendalerts').checked=false;
    			else
				{
					if(document.getElementById('commShipped').checked && document.getElementById('commTrackingNo').checked && document.getElementById('commDeliveryExceptions').checked)
						document.getElementById('sendalerts').checked=true;
				}
    			
    		}else if(ind==4)
    		{
    			
    			if(!document.getElementById('commDeliveryExceptions').checked)
    				document.getElementById('sendalerts').checked=false;
    			else
				{
					if(document.getElementById('commShipped').checked && document.getElementById('commDelivered').checked && document.getElementById('commTrackingNo').checked)
						document.getElementById('sendalerts').checked=true;
				}
    			
    		}
    	
    }
    $(document).on("click", "#myModal", function(event) {
    	event.stopPropagation(); // Stop stuff happening
    	event.preventDefault(); // Totally stop stuff happening
    	var phyId = $("#physicianId").val();
    	if (phyId == 0) {
    		$.alert({
     		    title: '',
     		    content: 'Save physician to E-Sign Profile PDF !',
     		});
    	}else
		{
    		signatureClear();div_show(this);
		}
      }
    );
    $(document).on("click", "#profilePDF", function(event) {
    	event.stopPropagation(); // Stop stuff happening
    	event.preventDefault(); // Totally stop stuff happening
    	var phyId = $("#physicianId").val();
    	if (phyId == 0) {
    		$.alert({
     		    title: '',
     		    content: 'Save physician to E-Sign Profile PDF !',
     		});
    		return false;
    	}else
		{
    		//alert("11111111111111111111")
    		document.physicianAccount.method="POST";
    		document.physicianAccount.action="generatePhysicianPdf?physicianId="+phyId;	
    		document.physicianAccount.submit();
    		return true;
		}
      }
    );
   
    
    $(document).on("click", ".createNewClinic", function(event) {
    	event.stopPropagation(); // Stop stuff happening
    	event.preventDefault(); // Totally stop stuff happening
    	isPhysicianClinicBtnClicked=true;
    	//alert(11111111111111111)
    	var phyId = $("#physicianId").val();
    	if (phyId == 0) {
    		$.alert({
     		    title: '',
     		    content: 'Save physician to Create Physician\'s Clinic !',
     		});
    	}else
    		{
    		
    		var groupId=$("#groupId").val();
			var newGroupId=$("#newGroupId").val();
			if(groupId==0)
				{
					$("#groupId").val(newGroupId);
					groupId=$("#groupId").val();
				}
			
			    var data = new FormData();
	    		data.append('physicianId',  phyId);
	    		data.append('groupId',  groupId);
	    		data.append('physicianProfile',  "false");
	    		
	    	 	var xhr = new XMLHttpRequest();
	    	 	xhr.open('POST', $("#serverUrl").val() +'/physician/checkPhysicianClinicAlreadyExists', true);
	    	 	xhr.send(data);
	    	 	xhr.onload = function () {
	    	 		var data = this.responseText;
	    	 		
	    	 		var msg=data;
	    	 	
		    	 	//alert(msg)	 
		    	 	if(msg=="alreadyexist"){
		    	 		  $.alert({
				      		    title: '',
				      		    content: 'Physician\'s Clinic already created!',
				      		});
		    	 		 $('#physicianAccount').bootstrapValidator('revalidateField', 'clinicSelectedList');
		    	 	}else{
	    	 		
	    	 			var isFormValid=true;
		    			var validatorObj = $('#physicianAccount').data('bootstrapValidator');
		    			validatorObj.validate();
		    			isFormValid=validatorObj.isValid();
	    		
	    				if(isFormValid){
	    				
	    						$("#newClinicFlag").val("true");
			    				document.physicianAccount.newClinicFlag.value="true";
			    				//alert(document.physicianAccount.newClinicFlag.value)
				    			document.physicianAccount.action="SavePhysicianProfile";	
				    			document.physicianAccount.submit();
		    			
			    	 	 }
		    	 	}
	    			
    			}
   		
    		}
    	
    	}
    );
  
    
    $(document).on("testclick", ".createNewTestClinic", function(event) {
    	event.stopPropagation(); // Stop stuff happening
    	event.preventDefault(); // Totally stop stuff happening
    	isPhysicianClinicBtnClicked=true;
    	var phyId = $("#physicianId").val();
    	var groupId = $("#groupId").val();
    	var error = 0;
    	var data = new FormData();
    	var status=$("#status").val();
    	
    	if (phyId == 0) {
    		$.alert({
     		    title: '',
     		    content: 'Save physician to Create Physician\'s Clinic !',
     		});
    	}else
    		{
    		
    		/*$('#physicianAccount').bootstrapValidator('revalidateField', 'groupId');
    		$('#physicianAccount').bootstrapValidator('revalidateField', 'firstName');
    		$('#physicianAccount').bootstrapValidator('revalidateField', 'lastName');
    		$('#physicianAccount').bootstrapValidator('revalidateField', 'email');
    		$('#physicianAccount').bootstrapValidator('revalidateField', 'phone');
    		$('#physicianAccount').bootstrapValidator('revalidateField', 'fax');

    		$('#physicianAccount').bootstrapValidator('revalidateField', 'address1');
    		$('#physicianAccount').bootstrapValidator('revalidateField', 'state');
    		$('#physicianAccount').bootstrapValidator('revalidateField', 'city');
    		$('#physicianAccount').bootstrapValidator('revalidateField', 'zipCode');
    		//alert("11111="+$('#physicianAccount').has('.has-error').length)
    		
    		 if ($('#physicianAccount').has('.has-error').length==0) {
    			 
    			 if(status=="New" || status=="New Modifications" || status=="Denied"){
     				
     				$.alert({
     	     		    title: '',
     	     		    content: 'Clinic can be created only for Profile Completed !',
     	     		});
     				
     			}else{
    			 
     				var prevStatus=$("#previousStatus").val();
     				if((prevStatus=="New" || prevStatus=="New Modifications" || prevStatus=="Denied") && prevStatus!=status)
     					{
     					
	     					$.alert({
	         	     		    title: '',
	         	     		    content: 'Save physician to Create Physician\'s Clinic',
	         	     		});
     					}else{*/
     				
    				var groupId=$("#groupId").val();
    				var newGroupId=$("#newGroupId").val();
    				if(groupId==0)
    					{
    						$("#groupId").val(newGroupId);
    						groupId=$("#groupId").val();
    					}
    				
    			    var data = new FormData();
		    		data.append('physicianId',  phyId);
		    		data.append('groupId',  groupId);
		    		data.append('physicianProfile',  "false");
		    		
		    	 	var xhr = new XMLHttpRequest();
		    	 	xhr.open('POST', $("#serverUrl").val() +'/physician/createNewPhysicianClinic', true);
		    	 	xhr.send(data);
		    	 	xhr.onload = function () {
		    	 		var data = this.responseText;
		    	 		var responseData = $.parseJSON(data);
		    	 		var msg="";
		    	 		
		    	 		for ( var i = 0; i < responseData.length; i++) {
			         		var obj = responseData[i];
			         		msg=obj.clinicName;
		    	 		}
			    	 	//alert(msg)	 
			    	 	if(msg=="alreadyexist"){
			    	 		  $.alert({
					      		    title: '',
					      		    content: 'Physician\'s Clinic already created!',
					      		});
			    	 		
			    	 	}else{
				    	 	// To clear dropdown values we need to write code like as shown below
					 		$('#lstBox2').empty();
					 		$('#lstBox3').empty();
					 		// Bind new values to dropdown
					 	
				             //alert("Success: " + responseData );
				             //alert("data length  ==="+responseData.length)
				             
				             for ( var i = 0; i < responseData.length; i++) {
				         		var obj = responseData[i];
				         		//alert(obj);
				         		//alert(obj.id+"==========="+obj.clinicName)
				        		var option = $("<option />");
				    	 		option.attr("value", obj.id).text(obj.clinicName);
				    	 		option.attr("selected","selected");
				    	 		$('#lstBox2').append(option);
				    	 		
				         	
				         		}
				             for ( var i = 0; i < responseData.length; i++) {
					         		var obj = responseData[i];
					         		//alert(obj);
					         		//alert(obj.id+"==========="+obj.status)
					        		var option = $("<option />");
					    	 		option.attr("value", obj.id).text(obj.status);
					    	 		option.attr("selected","selected");
					    	 		$('#lstBox3').append(option);
					    	 		
					         	
					         		}
				             createSelectedClinicLst();
				             $('#physicianAccount').bootstrapValidator('revalidateField', 'clinicSelectedList');
					             $.alert({
					      		    title: '',
					      		    content: 'Physician\'s Clinic added successfully!',
					      		});
			     			}
		    	 	  }
		    	// 	}
		    //	}//else
    		// }//if ($('#physicianAccount').has('.has-error').length==0) {
    		
    		
    	 	
    	}
    });
  

	function popup6(message) {
		// get the screen height and width  
		var maskHeight = $(document).height();
		//var maskHeight = 1661;  
		var maskWidth = $(window).width();
		
		// calculate the values for center alignment
		var dialogTop =  (maskHeight/3) - ($('#dialog-box6').height());  
		var dialogLeft = (maskWidth/2) - ($('#dialog-box6').width()/2); 
		
		dialogTop=dialogTop+800;
		dialogLeft=dialogLeft+200;
		/*dialogTop=440;
		dialogLeft=50;*/
		
		// assign values to the overlay and dialog box
		$('#dialog-overlay6').css({height:maskHeight, width:maskWidth}).show();
		$('#dialog-box6').css({top:dialogTop, left:dialogLeft}).show();
		
		// display the message
		$('#dialog-message6').html(message);
				
	}
	
  //function to display Popup
  function div_show(el){ 
	
			popup6("");
		
	}

	//function to hide Popup
	function div_hide(){ 
		//$('#myModal').modal('hide');
		$('#dialog-overlay6, #dialog-box6').hide();
	}
	
	
	$(document).ready(function(){
		var imageLoader = document.getElementById('imageLoader');
		
		if (imageLoader != null) {
			imageLoader.addEventListener('change', handleImage, false);
			var canvas = document.getElementById('newSignature');
			var ctx = canvas.getContext('2d');
			

			function handleImage(e){
			    var reader = new FileReader();
			    reader.onload = function(event){
			        var img = new Image();
			        img.onload = function(){
			            canvas.width = img.width;
			            canvas.height = img.height;
			            ctx.drawImage(img,0,0);
			        }
			        img.src = event.target.result;
			    }
			    reader.readAsDataURL(e.target.files[0]);     
			}

			signatureCapture();
			var canvas = document.getElementById("newSignature");
			var context = canvas.getContext("2d");
			base_image = new Image();
				
			base_image.src = "";
			//alert(base_image.src)
			base_image.onload = function(){
				context.drawImage(base_image, 0, 0);
			}
			
			document.onkeydown = function(e) {return on_keyboard_action(e); }
			document.onkeyup = function(e) {return on_keyboardup_action(e); }

			var canvas = document.getElementById("newSignature");
			//alert(canvas)
			var ctx = canvas.getContext("2d");
			var ctrl_pressed = false;
			var k="";
			
			
			function on_keyboard_action(event){
			    k = event.keyCode;  
			    //ctrl
			   	if(k==17){
			   		if(ctrl_pressed == false)
			   			ctrl_pressed = true;
					/* if (!window.Clipboard)
						 pasteCatcher.focus(); */
				}
		    }
			

			function on_keyboardup_action(event){
				//ctrl
				if(k==17)
					ctrl_pressed = false;
			}
			
			
			//=== Clipboard ================================================================

			//firefox
			var pasteCatcher;
			if (!window.Clipboard){
				pasteCatcher = document.createElement("div");
				pasteCatcher.setAttribute("id", "paste_ff");
				pasteCatcher.setAttribute("contenteditable", "");
				pasteCatcher.style.cssText = 'opacity:0;position:fixed;top:1600px;left:0px;';
				pasteCatcher.style.marginLeft = "-20px";
				document.body.appendChild(pasteCatcher);
				pasteCatcher.focus();
				document.addEventListener("click", function(){
					//pasteCatcher.focus();
					});
				document.getElementById('paste_ff').addEventListener('DOMSubtreeModified',function(){
					if(pasteCatcher.children.length == 1){
						img = pasteCatcher.firstElementChild.src;
			            
			            var img2 = new Image();
			            img2.onload = function(){
			                ctx.drawImage(img2, 0, 0);
			                }
			            img2.src = img;
			            //ctx.drawImage(img, 0, 0);
			         
			            
			            //ctx.drawImage(img, 0, 0);
						pasteCatcher.innerHTML = '';
						}
					},false);
				}
			//chrome
				window.addEventListener("paste", pasteHandler);
				function pasteHandler(e){
					if(e.clipboardData) {
						var items = e.clipboardData.items;
						if (items){
							for (var i = 0; i < items.length; i++) {
								if (items[i].type.indexOf("image") !== -1) {
									var blob = items[i].getAsFile();
									var URLObj = window.URL || window.webkitURL;
									var source = URLObj.createObjectURL(blob);
									paste_createImage(source);
									}
								}
							}
					// If we can't handle clipboard data directly (Firefox),
					// we need to read what was pasted from the contenteditable element
					else{
						}
					}
				else{
					setTimeout(paste_check_Input, 1);
					}
				}
				
				 
				function restoreEsignImage()
				{
				try{
						signatureClear();
						/*var context = canvas.getContext("2d");
						canvas.width = 276;
						canvas.height = 70;
						context.fillStyle = "#fff";
						context.strokeStyle = "#444";
						context.lineWidth = 2.6;
						context.lineCap = "round";
						context.fillRect(0, 0, canvas.width, canvas.height);*/
						base_image = new Image();
						
						base_image.src = "";
						//alert(base_image.src)
						context.drawImage(base_image, 0, 0);
						document.getElementById("imageLoader").value="";
						/*var fld = document.getElementById("imageLoader");
						fld.form.reset();*/
					}catch(e)
					{
						alert(e)
					}

				}
		}


		
	});	
	
	function signaturePhysicianSave() {

		var canvas = document.getElementById("newSignature");// save canvas image as data url (png format by default)
		var dataURL = canvas.toDataURL("image/png");
		//alert(dataURL);
		
		var physicianId=document.forms[0].physicianId.value;
		//alert(physicianId);
		if(physicianId>0)
		{
			
			document.physicianAccount.base64ImgString.value=dataURL;
			//alert(document.physicianAccount.base64ImgString.value)
			document.physicianAccount.action = "savePhysicianProfileSignature";
			document.physicianAccount.submit();
			

		}
	};

	function popup8(message) {
		// get the screen height and width  
		var maskHeight = $(document).height();
		//var maskHeight = 1261;  
		var maskWidth = $(window).width();
		
		// calculate the values for center alignment
		var dialogTop =  (maskHeight/3) - ($('#dialog-box8').height());  
		var dialogLeft = (maskWidth/2) - ($('#dialog-box8').width()/2); 
	
		
		dialogTop=dialogTop+800;
		dialogLeft=dialogLeft+200;
		
		// assign values to the overlay and dialog box
		$('#dialog-overlay8').css({height:maskHeight, width:maskWidth}).show();
		$('#dialog-box8').css({top:dialogTop, left:dialogLeft}).show();
		
		// display the message
		$('#dialog-message8').html(message);
	}

	//function to hide Popup
	function div_hide8(){ 
		//$('#myModal').modal('hide');
		$("#deniedStatusFlg").val("");
		$('#physicianAccount').bootstrapValidator('revalidateField', 'deniedStatusFlg');
		$('button[type="submit"]').removeAttr('disabled');
		
		$('#dialog-overlay8, #dialog-box8').hide();
		//$("#status").val("Active")
		return true;
	}
   
	
	$("form").on("submit",function(event) {
		isPhysicianClinicBtnClicked = false;	
		
	    	/*try {
	    		if (event.isDefaultPrevented()) {
	    		    // handle the invalid form...
	    			
	    		  } else {
	    		    // everything looks good!
	    			    //alert("222222222222222222222")
	    				var isFormValid = true;
	    				
	    				var validatorObj = $('#physicianAccount')
	    						.data('bootstrapValidator');
	    				validatorObj.validate();
	    				isFormValid = validatorObj.isValid();
	    				//alert(isFormValid)
	    				var statflag=true;
	    				if (isFormValid) {
	    					statflag=validateFormStatusEvent();
	    					//alert(statflag)
	    					if(!statflag)
	    						{
	    							$('button[type="submit"]').prop('disabled','disabled');
	    							//$(".deniedFormSubmission-errors").html("Denied");
	    							//alert("2222=="+data.bv.getInvalidFields().length)
	    							event.preventDefault(); //prevent submit
	    							return false;
	    							
	    						}else
	    							$('button[type="submit"]').removeAttr('disabled');
	    				}
	    		         
	    		  }
	    		
			} catch (e) {
				alert(e)
			}*/
			
	    });
    
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
				$("#deniedStatusFlg").val("");
				$('#physicianAccount').bootstrapValidator('revalidateField', 'deniedStatusFlg');
				document.physicianAccount.reAssignPhysician.value="true";
				document.getElementById("errOtherPhysicianMsg").style.display="none";
				document.physicianAccount.action="SavePhysicianProfile";	
				document.physicianAccount.submit();
			}
			
		}catch(e)
		{
			alert(e)
		}
	}


	function callDeactivatePhysician() {
		try{
			$("#deniedStatusFlg").val("");
			$('#physicianAccount').bootstrapValidator('revalidateField', 'deniedStatusFlg');
			document.physicianAccount.deactivatePhysician.value="true";
			document.physicianAccount.action="SavePhysicianProfile";	
			document.physicianAccount.submit();
			
		}catch(e)
		{
			alert(e)
		}
	}
	function checkDocumentValues() {
		try {
			var type = $("#uploadedDocFilePurpose").val();
			var x = document.getElementById("docFiles");
			var txt = "";
			var err = "";
			var isError = false;
			if ('files' in x) {
				if (x.files.length > 0) {
					for (var i = 0; i < x.files.length; i++) {
						var file = x.files[i];
						/*if ('size' in file) {
							txt += "size: " + file.size + " bytes <br>";
							// 1 MB is equal to 1048576 bytes
							// 2.5 MB is equal to 2621440 bytes 
							if (file.size > 2621440) {
								err += 'File Size should be less than 2.5 MB';
								isError = true;
							}	
						}*/
						if (type != "Other") {
							var fileExtension = file.name.substr(file.name
									.lastIndexOf('.') + 1);
							txt += "ext : " + fileExtension + " <br>";
							if (fileExtension != 'gif'
									&& fileExtension != 'png'
									&& fileExtension != 'pdf') {
								if (err != "")
									err += " and ";
								err += 'PDF or PNG or GIF files only allowed in Document Upload';
								isError = true;
							}
						}
					}
				}
			}
			//alert(isError)
			if (isError) {
				$('button[type="submit"]').prop('disabled', 'disabled');
				$('button[type="button"]').prop('disabled', 'disabled');
				$('.buttonNext').attr('disabled', true);

			} else {
				$('button[type="submit"]').removeAttr('disabled');
				$('button[type="button"]').removeAttr('disabled');
				$('.buttonNext').removeAttr('disabled');
			}
			$(".docFiles-upload-errors").html(err);
			/*if (!isError) {
				$("#submitadminprofileHidden").click();	
				var validatorObj = $('#admin').data('bootstrapValidator');
			    validatorObj.validate();
			    return validatorObj.isValid();
			} else {
				$("#file").focus();
			}*/
		} catch (e) {
			alert(e)
		}
		
	}
	function sendLoginEmail()
	{
		var physicianId=document.forms[0].physicianId.value;
		if(physicianId>0) {
			document.forms[0].method="POST";
			document.forms[0].action = "sendPhysicianAccountCredentialsEmail";
			document.forms[0].submit();
		} else {
			$.alert({
	 		    title: '',
	 		    content: 'Save User Account to Send Credentials Email !',
	 		});
		}
	}