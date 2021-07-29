var dateFormat = "MM/DD/YYYY";
var rdate = $("#dateOfBirth");

/*$(rdate).datetimepicker({
	lang:'en',
	timepicker:false,
	format: 'm/d/Y'
});
*/
var rdate2 = $("#syncStatusChangedDate");
$(rdate2).datetimepicker({
	lang:'en',
	timepicker:false,
	format: 'm/d/Y'
});
//$("#status option[value='New Modifications']").prop("disabled", true);//temp commented not saved at back-end

$("#licenseExpDate").datetimepicker({
	lang:'en',
	timepicker:false,
	format: 'm/d/Y'
});

$("#dateRegistered").datetimepicker({
	lang:'en',
	timepicker:false,
	format: 'm/d/Y'
});

$(document).on("click", "#cancelBtn, .goSummary", function() {
	location.href = "patientaccountsummary";
});

$(document).on("click", ".goNewRec", function() {
	location.href = "patientAccount";
});

$(document).on("click", ".backPrescription", function() {
	document.patientAccount.action = "backPrescription";
	document.patientAccount.submit();
});

function getAge(ele) {
	if (ele.val() != "")
		return moment(ele.val(), dateFormat).fromNow(true);
	else
		return "";
}

jQuery(function($){
   $("#dateOfBirth").mask("99/99/9999");
});


$(document).on("change", "#dobDate", function() {
	setDateOfBirth();
});
$(document).on("change", "#dobMonth", function() {
	setDateOfBirth();
});
$(document).on("change", "#dobYear", function() {
	setDateOfBirth();
});


function setDateOfBirth() {
	// MM/dd/yyyyy
	if (parseInt($("#dobDate").val()) > 0 && parseInt($("#dobMonth").val()) > 0 && parseInt($("#dobYear").val()) > 0 ) {
		var mm = parseInt($("#dobMonth").val());
		if (mm < 10) 
			mm = "0"+mm;
		
		var dd = parseInt($("#dobDate").val());
		if (dd < 10) 
			dd = "0"+dd;

		var str = mm + "/" +  dd + "/" + $("#dobYear").val();
		$("#dateOfBirth").val(str);
		
		try {
			var m1 = moment();
			var m2 = moment($("#dateOfBirth").val());
			var diff = m2.diff(m1, 'days', true); 
			if (Math.ceil(diff) < 0) {
				$(".displayAge").val( getAge(rdate, dateFormat) );	
			} else {
				$(".displayAge").val("");
			}
			$('#patientAccount').bootstrapValidator('revalidateField', 'dateOfBirth');
		}catch(e) {
		}
	}
}

$("#dateOfBirth").change(function(){
	var m1 = moment();
	var m2 = moment($(this).val());
	var diff = m2.diff(m1, 'days', true); 
	if (Math.ceil(diff) < 0) {
		$(".displayAge").val( getAge(rdate, dateFormat) );	
	} else {
		$(".displayAge").val("");
	}
});

jQuery(function($){
   $("#phone").mask("999-999-9999");
   $("#mobile").mask("999-999-9999");
   $("#zipCode").mask("99999-9999");
   $("#ssn").mask("999-99-9999");
   $("#billingZipCode").mask("99999-9999");
   
   $("#cardCvcNumber").mask("9999")
   $("#expYear").mask("9999");
   
   $("#dateOfBirth").mask("99/99/9999");
   $("#licenseExpDate").mask("99/99/9999");
});

setTimeout( function(){$(".displayAge").val( getAge($("#dateOfBirth"), "DD/MM/YYYY") );}, 1000);

$(document).on("change", "#autoCompleterPhysicianId", function() {
	$('#patientAccount').bootstrapValidator('revalidateField', 'autoCompleterPhysicianId');
	
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

/*var m1 = new moment("10/23/2017", 'MM/DD/YYYY', true);
var m2 = new moment("10/22/2017", 'MM/DD/YYYY', true);
alert(m1.diff(m2, 'days')); */
$(document).on("change", "#autoCompleterClinicId", function() {
	$('#patientAccount').bootstrapValidator('revalidateField', 'autoCompleterClinicId');
});




function setDisabled(value) {
	if (value == "Denied") {
		$("#firstName").prop( "disabled", true );
		$("#middleName").prop( "disabled", true );
		$("#lastName").prop( "disabled", true );
		$("#dateRegistered").prop("disabled", true);
		$("#address").prop( "disabled", true );
		$("#city").prop( "disabled", true );
		$("#state").prop( "disabled", true );
		$("#zipCode").prop( "disabled", true );
		$("#phone").prop( "disabled", true );
		$("#mobile").prop( "disabled", true );
		$("#email").prop( "disabled", true );
		$("#userLoginId").prop("disabled", true);
		$("#driversLicense").prop("disabled", true);
		$("#driversLicenseState").prop("disabled", true);
		$("#licenseExpDate").prop("disabled", true);
		$("#ssn").prop("disabled", true);
		$("#cardType").prop("disabled", true);
		$("#cardNumber").prop( "disabled", true );
		$("#cardCvcNumber").prop( "disabled", true );
		$("#cardHolderName").prop( "disabled", true );
		$("#billingZipCode").prop("disabled", true);
		
		$("#expMonth").prop( "disabled", true );
		$("#expYear").prop( "disabled", true );
		$("#criticalComments").prop("disabled", true);
		$("#groupId").prop("disabled", true);
		$("#physicianList").prop("disabled", true);
		$("#physicianSelectedList").prop("disabled", true);
		
		
		$("#dateOfBirth").prop("disabled", true);
		$("#gender").prop("disabled", true);
		$(".displayAge").prop("disabled", true);
		$("#pregnancyprecaution1").prop("disabled", true);
		$("#allergies").prop("disabled", true);
		$("#otherMedications").prop("disabled", true);
		$("#medicalConditions").prop("disabled", true);
		$("#syncStatus").prop("disabled", true);
		$("#syncStatusChangedDate").prop("disabled", true);
		
		$("#commEmail1").prop("disabled", true);
		$("#commPhone1").prop("disabled", true);
		$("#commTrackingNo1").prop("disabled", true);
		$("#commShipped1").prop("disabled", true);
		$("#commDelivered1").prop("disabled", true);
		$("#commDeliveryExceptions1").prop("disabled", true);
		
		$("#alternateIdTypeId").prop("disabled", true);
		$("#alternateId").prop("disabled", true);
		$("#dobMonth").prop("disabled", true);
		$("#dobDate").prop("disabled", true);
		$("#dobYear").prop("disabled", true);
		
		
		formValidation(false);
	} else {
		$("#firstName").prop( "disabled", false );
		$("#middleName").prop( "disabled", false );
		$("#lastName").prop( "disabled", false );
		$("#dateRegistered").prop("disabled", false);
		$("#address").prop( "disabled", false );
		$("#city").prop( "disabled", false );
		$("#state").prop( "disabled", false );
		$("#zipCode").prop( "disabled", false );
		$("#mobile").prop( "disabled", false );
		$("#phone").prop( "disabled", false );
		$("#email").prop( "disabled", false );
		$("#userLoginId").prop("disabled", false);
		$("#driversLicense").prop("disabled", false);
		$("#driversLicenseState").prop("disabled", false);
		$("#licenseExpDate").prop("disabled", false);
		$("#ssn").prop("disabled", false);
		$("#cardType").prop("disabled", false);
		$("#cardNumber").prop( "disabled", false );
		$("#cardCvcNumber").prop( "disabled", false);
		$("#cardHolderName").prop( "disabled", false );
		$("#expMonth").prop( "disabled", false );
		$("#expYear").prop( "disabled", false );
		$("#billingZipCode").prop("disabled", false);
		
		$("#criticalComments").prop("disabled", false);
		$("#groupId").prop("disabled", false);
		$("#physicianList").prop("disabled", false);
		$("#physicianSelectedList").prop("disabled", false);
		
		$("#dateOfBirth").prop("disabled", false);
		$("#gender").prop("disabled", false);
		$(".displayAge").prop("disabled", false);
		$("#pregnancyprecaution1").prop("disabled", false);
		$("#allergies").prop("disabled", false);
		$("#otherMedications").prop("disabled", false);
		$("#medicalConditions").prop("disabled", false);
		$("#syncStatus").prop("disabled", false);
		$("#syncStatusChangedDate").prop("disabled", false);
		
		$("#commEmail1").prop("disabled", false);
		$("#commPhone1").prop("disabled", false);
		$("#commTrackingNo1").prop("disabled", false);
		$("#commShipped1").prop("disabled", false);
		$("#commDelivered1").prop("disabled", false);
		$("#commDeliveryExceptions1").prop("disabled", false);
		
		$("#alternateIdTypeId").prop("disabled", false);
		$("#alternateId").prop("disabled", false);
		$("#dobMonth").prop("disabled", false);
		$("#dobDate").prop("disabled", false);
		$("#dobYear").prop("disabled", false);

		
		formValidation(false);
	}
}

$(document).on("change", "#status", function() {

	$('#patientAccount').bootstrapValidator('revalidateField', 'firstName');
	$('#patientAccount').bootstrapValidator('revalidateField', 'lastName');
	$('#patientAccount').bootstrapValidator('revalidateField', 'address');
	$('#patientAccount').bootstrapValidator('revalidateField', 'city');
	$('#patientAccount').bootstrapValidator('revalidateField', 'state');
	$('#patientAccount').bootstrapValidator('revalidateField', 'zipCode');
	$('#patientAccount').bootstrapValidator('revalidateField', 'phone');
	$('#patientAccount').bootstrapValidator('revalidateField', 'email');
	$('#patientAccount').bootstrapValidator('revalidateField', 'userLoginId');
	try {
		$('#patientAccount').bootstrapValidator('revalidateField', 'groupId');
	} catch (e) {
		// TODO: handle exception
	}
	$('#patientAccount').bootstrapValidator('revalidateField', 'userLoginId');
	$('#patientAccount').bootstrapValidator('revalidateField', 'dateOfBirth');
	$('#patientAccount').bootstrapValidator('revalidateField', 'gender');
	$('#patientAccount').bootstrapValidator('revalidateField', 'cardNumber');
	$('#patientAccount').bootstrapValidator('revalidateField', 'cardCvcNumber');
	$('#patientAccount').bootstrapValidator('revalidateField', 'cardHolderName');
	$('#patientAccount').bootstrapValidator('revalidateField', 'expMonth');
	$('#patientAccount').bootstrapValidator('revalidateField', 'expYear');
	$('#patientAccount').bootstrapValidator('revalidateField', 'billingZipCode');
	
	try {
		$('#patientAccount').bootstrapValidator('revalidateField',
				'physicianSelectedList');
	} catch (e) {
		// TODO: handle exception
	}
});

/*$(document).ready(function() {
	formValidation(true);
});*/


function isFieldValidate() {
	if ($("#status").val() == "Approved" || $("#status").val() == "Profile Completed" ) 
		return true;
	else
		return false;
}

function formValidation(onLoad) {
	createSelectedGroupLst();
	createSelectedPhysicianLst();
	
    $('#patientAccount').bootstrapValidator({
    	excluded: [':disabled', ':hidden', ':not(:visible)'],
    	fields: {
        	firstName: {
                validators: {
                    notEmpty: {
                    	message: $("#err_firstname").val()
                    }
                }
            },        	
            lastName: {
                validators: {
                    notEmpty: {
                    	message: $("#err_lastname").val()
                    }
                }
            },
            address: {
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
		        	/*callback: {
		                message:  $("#err_state").val(),
		                callback: function (value, validator, $field) {
		                	return value != 0;
		                }
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
           /* groupId: {
            	validators: {
		            callback: {
		                message:  $("#err_group").val(),
		                callback: function (value, validator, $field) {
		                	if (isFieldValidate()) {
			                	if (value > 0)
			                        return true;
			                    else
			                    	return false;
		                	} else {
		                		return true;
		                	}
		                }
		            }
            	}
            },*/
            /*driversLicense: {
            	validators: {
                	callback: {
                        message: $("#err_driving_license").val(),
                        callback: function (value, validator, $field) {
                        	var lic=$("#driversLicense").val();
                        	if (isFieldValidate() && lic == "") {
                        		return false;
                        	} else {
                        		return true;
                        	} 
                        }
                    }
                }
            },
            driversLicenseState: {
                validators: {
                	callback: {
                        message: $("#err_license_state_valid").val(),
                        callback: function (value, validator, $field) {
                        	var licState = $("#driversLicenseState").val();
                        	if (isFieldValidate() && licState == "0") {
                        		return false;
                        	} else {
                        		return true;
                        	} 
                        }
                    }
                }
            },
            licenseExpDate: {
				validators : {
					callback: {
                        message: $("#err_license_exp_date_exp").val(),
                        callback: function (value, validator, $field) {
                        	var expDate = $("#licenseExpDate").val();
                        	if (isFieldValidate() && expDate == "") {
                        		return false;
                        	} else {
                        		return true;
                        	} 
                        	
                        }
                    }
				}
            },
			ssn : {
				validators : {
					callback: {
		                message:  $("#err_ssn").val(),
		                callback: function (value, validator, $field) {
		                	if (isFieldValidate()) {
		                		if (value!=null && value.length == 11)
			                        return true;
		                		else if (value == "") {
		                			return {
                        				valid:false,
                        				message: $("#err_ssn").val()
                        			}
		                		}
		                		else if (value.length != 11) {
			                    	return {
                        				valid:false,
                        				message: $("#err_ssnformat").val()
                        			}
			                    }
		                	} else
		                		return true;
		                }
		            }
				}
			},*/
			phone : {
				 validators: {
					callback: {
						message:  $("#err_phone").val(),
			            callback: function (value, validator, $field) {
		            		if (value!=null && value.length == 12 ) {
		            			return true;
		                	}
		            		else if (value == null || value.length == 0) {
		            			return {
                        			valid:false,
                        			message: $("#err_phone").val()
                        		}
		            		}
		                	else if (value.length != 12) {
			                   	return {
                        			valid:false,
                        			message: $("#err_phone_format").val()
                        		}
			                }
			            }
					}
                }
			},
			email : {
				validators : {
					notEmpty : {
						 message: $("#err_email").val()
					},
	                emailAddress: {
	                  	 message: $("#err_emailformat").val()
	               }
				}
			},
			userLoginId : {
				validators : {
					/*notEmpty : {
						 message: $("#err_user_login_id").val()
					},
					stringLength: {
		                min: 5,
		            	message: $("#err_user_login_length").val()
		            }*/
					callback: {
						message:  $("#err_user_login_id").val(),
			            callback: function (value, validator, $field) {
			            	if (isFieldValidate()) {
			            		if (value!=null && value.length > 4 ) {
			            			return true;
			                	}
			            		else if (value == null || value == "") {
			            			return {
	                        			valid:false,
	                        			message: $("#err_user_login_id").val()
	                        		}
			            		}
			            		else if (value.length < 4) {
				                   	return {
	                        			valid:false,
	                        			message: $("#err_user_login_length").val()
	                        		}
				                }
			                } else
			                	return true;
			            }
					}
				}
			},
			
			/*driversLicense: {
				validators: {
                	callback: {
                        message: $("#err_driving_or_alternate_id").val(),
                        callback: function(value, validator, $field) {
                        	if ($("#driversLicense").val() == "" && $("#alternateId").val() == "") {
                        		return {
                    				valid:false,
                    				message: $("#err_driving_or_alternate_id").val()
                    			}
                        	} else {
                        		return true;
                        	}
                        }
                	}
				}
			},

			alternateId : {
				validators: {
                	callback: {
                        message: $("#err_driving_or_alternate_id").val(),
                        callback: function(value, validator, $field) {
                        	if ($("#driversLicense").val() == "" && $("#alternateId").val() == "") {
                        		return {
                    				valid:false,
                    				message: $("#err_driving_or_alternate_id").val()
                    			}
                        	} else {
                        		return true;
                        	}
                        }
                	}
				}

			},*/
			
			/*alternateIdTypeId : {
				validators: {
                	callback: {
                        message: $("#err_physician").val(),
                        callback: function(value, validator, $field) {
                        	if ($("#alternateId").val() != "") {
                        		
                        	} else 
                        		return true;
                        }
                	}
				}
			},*/
			
			autoCompleterPhysicianId :{
                validators: {
                	callback: {
                        message: $("#err_physician").val(),
                        callback: function(value, validator, $field) {
                        	try {
								  //alert($(".autoCompleterPhysicianId option:selected").text())
                                  //alert(value)           	
                                  var value=$(".autoCompleterPhysicianId option:selected").text();
                                  //alert(value)
                                  var physicianNameVar2= $("#physicianName").val();
                              		if(physicianNameVar2==null || physicianNameVar2=="")
                              		{
                              			physicianNameVar2="Select";
                              		}
                              	
                              		if((value=="" || value=="Select")   && (physicianNameVar2=="Select" || physicianNameVar2==""))
	                    			{
	                        			return {
	                        				valid:false,
	                        				message: $("#err_physician").val()
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
			dateOfBirth: {
				excluded: false,
				validators : {
					/*notEmpty: {
						message: $("#err_dob").val()
					},*/
	                date: {
	                    format: 'MM/DD/YYYY',
	                    message: $("#err_dob_format").val()
	                },
					callback: {
                        message: $("#err_future_dob").val(),
                        callback: function (value, validator, $field) {
                        	if (isFieldValidate()) {
	                        	if (value != "") {
	                            	var m1 = moment();
	                            	var m2 = moment(value);
	                           		var n = m1.diff(m2, 'days', true);
	                           		if (n > 0)
	                           			return true;
	                           		else {
	                           			return {
		                        			valid:false,
		                        			message: $("#err_future_dob").val()
		                        		}
	                           		}
	                        	} else {
	                        		return {
	                        			valid:false,
	                        			message: $("#err_dob").val()
	                        		}
	                        	}
                        	} else
                        		return true;
                        }
                    }
				}
			},
			gender: {
				validators: {
                    /*notEmpty: {
                    	message: $("#err_gender").val()
                    }*/
					callback: {
						message:  $("#err_gender").val(),
			            callback: function (value, validator, $field) {
			            	if (isFieldValidate()) {
			            		if (value!=null && value.length > 0) {
			            			return true;
			                	}
			                	else {
				                   	return {
	                        			valid:false,
	                        			message: $("#err_gender").val()
	                        		}
				                }
			                } else
			                	return true;
			            }
					}
                }
			},
			/*cardType: {
				validators: {
                	callback: {
		                message:  $("#err_cardType").val(),
		                callback: function (value, validator, $field) {
		                	if ($("#cardType").val() != "" || $("#cardNumber").val() != "" || $("#cardCvcNumber").val() != "" 
		                		||  $("#cardHolderName").val() != "" || $("#expMonth").val() != "" || $("#expYear").val() != "" ) {
		                		
		                		if ($("#cardType").val() != 0)
		                			return true;
		                		else {
		                			return {
                        				valid:false,
                        				message: $("#err_cardType").val()
                        			}
		                		}
		                	} else
		                		return true;
		                }
		            }
                }
            },*/
            cardNumber: {
                validators: {
                    creditCard: {
                        message: $("#err_card_no_format").val()
                    },
                	/*callback: {
		                message:  $("#err_cardNo").val(),
		                callback: function (value, validator, $field) {
		                	if ($("#cardType").val() != "" || $("#cardNumber").val() != "" || $("#cardCvcNumber").val() != "" 
		                		||  $("#cardHolderName").val() != "" || $("#expMonth").val() != "" || $("#expYear").val() != "" ) {
		                		
		                		if ($("##cardNumber").val() != ""){
		                			
		                		}
		                		else {
		                			return {
                        				valid:false,
                        				message: $("#err_cardNo").val()
                        			}
		                		}
		                	} else
		                		return true;
		                }
		            }*/
                }
            },
            cardCvcNumber: {
                validators: {
                    stringLength: {
                        min: 3,
                    	max: 4,
                    	message: $("#err_cvcNo_format").val()
                    }                    
                }
            },
            /*cardHolderName: {
                validators: {
                    notEmpty: {
                        message: $("#err_cardHolder").val()
                    }
                }
            },*/
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
		                	} else {
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
                    },
                    stringLength: {
                        min: 4,
                    	max: 4,
                        message: $("#err_cardExpYear_format").val()
                    },*/
		            callback: {
		                message:  $("#err_cc_expiry_year").val(),
		                callback: function (value, validator, $field) {
		                	var expMonth1 = $("#expMonth").val().trim();
		                	if ((value == "" && expMonth1 != "") || (value != "" && expMonth1 == "") ) {
		                		return false;
		                	}
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
		                	if (value != ""){
	                			if (value.length == 5 || value.length == 10)
	                				return true;
	                			else {
	                				return {
                        				valid:false,
                        				message: $("#err_billing_zip_format").val()
                        			}
	                			}
	                		} else {
	                			return true;
	                		}   
		                }
		            }
                }
            },
            physicianSelectedList: {
            	validators: {
		        	callback: {
		                message:  $("#err_physician").val(),
		                callback: function (value, validator, $field) {
		                	if (isFieldValidate()) {
		                		if (value!=null && value.length > 0)
			                        return true;
			                    else
			                    	return false;
		                	} else
		                		return true;
		                }
		            }
            	}
            },
            groupSelectedList:{
            	validators: {
		        	callback: {
		                message:  $("#err_group").val(),
		                callback: function (value, validator, $field) {
		                	if (isFieldValidate()) {
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
}

$(document).ready(function() {
	setDisabled($("#status").val());
});

function getAlterText(ele) {
	var txt = $(ele).find("option:selected").text();
	$("#alternateIdTypeText").val(txt);
}
getAlterText($("#alternateIdTypeId"));

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
	
	if (exMonth != "" && exYear != "") {
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
	} else {
		return true;
	}
}

$(document).on("change, blur", "#expMonth, #expYear", function() {
	$('#patientAccount').bootstrapValidator('revalidateField', 'expMonth');
	$('#patientAccount').bootstrapValidator('revalidateField', 'expYear')
});



$(document).on("change", "#state", function() {
	$('#patientAccount').bootstrapValidator('revalidateField', 'state');
});

$(document).on("change", "#driversLicense", function() {
	setTimeout(function(){
		$('#patientAccount').bootstrapValidator('revalidateField', 'driversLicense');
		// $('#patientAccount').bootstrapValidator('revalidateField', 'driversLicenseState');
		// $('#patientAccount').bootstrapValidator('revalidateField', 'licenseExpDate');
		$('#patientAccount').bootstrapValidator('revalidateField', 'alternateId');
	}, 500);
});

$(document).on("change", "#alternateId", function() {
	setTimeout(function(){
		$('#patientAccount').bootstrapValidator('revalidateField', 'driversLicense');
		$('#patientAccount').bootstrapValidator('revalidateField', 'alternateId');
	}, 500);
});

/*$(document).on("change", "#driversLicenseState", function() {
	setTimeout(function(){
		$('#patientAccount').bootstrapValidator('revalidateField', 'driversLicense');
		// $('#patientAccount').bootstrapValidator('revalidateField', 'driversLicenseState');
		// $('#patientAccount').bootstrapValidator('revalidateField', 'licenseExpDate');
	}, 500);	
});

$(document).on("blur", "#licenseExpDate", function() {
	setTimeout(function(){
		$('#patientAccount').bootstrapValidator('revalidateField', 'driversLicense');
		// $('#patientAccount').bootstrapValidator('revalidateField', 'driversLicenseState');
		// $('#patientAccount').bootstrapValidator('revalidateField', 'licenseExpDate');
	}, 500);
});*/


$(document).on("blur", "#dateOfBirth", function() {
	setTimeout(function(){$('#patientAccount').bootstrapValidator('revalidateField', 'dateOfBirth')}, 500);	
});

/*new InputMask().Initialize(document.querySelectorAll("#SSN"),{
	  mask: InputMaskDefaultMask.Ssn, 
	  placeHolder: "SSN: 999-99-9999" 
	});*/



/******************  DOCUMENT FILE UPLOAD PROCESSES STARTS *******************************/
//Variable to store user file
var files;
$(document).on("change", "input[name=docFiles]", function(event) {
	files = event.target.files;
});


/****************************** FILE DISPLAY (PAGINATION) IN profile page **********************/ 
try	{
	var dttable = $("#physicianDocFilesTable").DataTable({
		"processing" : true,
		"serverSide" : true,
		"dom":'<"top">rt<"bottom"pl><"clear">',
		"lengthMenu" : [ [ 5, 10, 15, 25], [ 5, 10, 15, 25] ],
		"pagingType": "simple_numbers",
		"ajax" : {
			"url" : "getPatientDocumentsData?p="+$("#patientId").val(),
			"type" : "POST"
		},
		"oLanguage": {
	        "sEmptyTable":  "No records available"
	    },
		"columns" : [ 
		    { data : "fileId","orderable":false, "sClass":"left_align", "visible": false },
			{ data : "originalFileName","orderable":false, "sClass":"left_align"  },
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
				   data = '<a href="patientDocFileDownload?f='+row.fileId+'&p='+$("#patientId").val()+'">'+row.originalFileName+'</a>';
				    return data;
			   },
			   "targets": 0,
			},
			{ "width": "10px", "targets": [0] },
			{ "width": "100px", "targets": [1] },
			{ "width": "60px", "targets": [2] },
			{ "width": "50px", "targets": [3] },
			{ "width": "40px", "targets": [4] },
			{ "width": "40px", "targets": [5] }
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
    	
    	var data = new FormData();
    	var error = 0;
    	var phyId = $("#patientId").val();
    	if (phyId == 0) {
    		$.alert({
     		    title: '',
     		    content: 'Save patient then upload Documents !',
     		});
    	} else {
    		
	    		try {
					if (files != null) {
						for (var i = 0; i < files.length; i++) {
							var file = files[i];
							console.log(file.size);
							data.append('docFile', file, file.name);
							data.append('patientId', $("#patientId").val() + "");
						}
						
						$(".docFiles-upload-errors").html("");
					} else {
						$(".docFiles-upload-errors").html(
								"Please select File to Upload");
						error = 1;
					}
				} catch (e) {
					alert(e)
					$(".docFiles-upload-errors").html(
							"Please select File to Upload");
					error = 1;
				}
				
				if(!error){
	        	 	var xhr = new XMLHttpRequest();
	        	 	xhr.open('POST', 'patientDocFileUpload', true);
	        	 	xhr.send(data);
	        	 	xhr.onload = function () {
	        	 		var fileTag= "<input type='file' name='docFiles' />";
	        	 		$(".fileTagLoc").html(fileTag);
	        	 		files = null;
	        	 		if (xhr.readyState == 4 && xhr.status == 200) {
	        	 			dttable.ajax.url("getPatientDocumentsData?p="+$("#patientId").val()).draw();
	        	 		}
	        		};
	        	}
    	}
    });
    /****************** DOCUMENT UPLOAD PROCESSES *****************/
    
    /****************** DOCUMENT DELETE PROCESSES *****************/
    $(document).on("click", ".fileDeleteBtn", function(){
    	var fileId=$(this).closest('tr').attr("id").replace("row_", "");
    	var patientId = $("#patientId").val();
    	var docName=$(this).closest('tr').find("td a").html();
    	
    	var alertTxt = "Are you sure do you want to delete this "+docName + " ? ";
    	$.confirm({
    		title: 'Confirm!',
    		content: alertTxt,
    		buttons: {
     			confirm: function () {
     				
     				if (fileId > 0) {
     					var data = new FormData();
     					data.append('fileId', fileId);
     					data.append('patientId', patientId+"");
     					
     				 	var xhr = new XMLHttpRequest();
     				 	xhr.open('POST', 'patientDocFileDelete', true);
     				 	xhr.send(data);
     				 	xhr.onload = function () {
     				 		dttable.ajax.url("getPatientDocumentsData?p="+patientId).draw();
     					};
     				}
     			},
     			cancel: function () {
     				
     			}
    		}
    	});
    });
    /****************** DOCUMENT DELETE PROCESSES *****************/


	function fnShowHide( iCol )
	{
			
		 // Get the column API object
        var column = dttable.column(iCol);
 
        // Toggle the visibility
        column.visible( ! column.visible() )
	}
    
	var physicianNameVar= $("#physicianName").val();
	if(physicianNameVar==null || physicianNameVar=="")
		{
			physicianNameVar="Select";
		}
	var formId= $("#physicianId").val();
	var results=[];
	$('.autoCompleterPhysicianId').select2({
        placeholder: physicianNameVar,
        minimumInputLength: 0,
        ajax: {
          url: urlPath+'getAutoCompleteApprovedPhysiciansList',
          dataType: 'json',
          data: function (params) {
              return {
            	  term: $.trim(params.term),
            	  formId:formId
              };
          },
          processResults: function (data) {
        	  //alert(data)
        	  results = [];
              data.forEach(function makeResults(element) {
            	//alert(element)
                results.push({
                  id: element.id,
                  text: element.physicianName
                });
              });
              return {
                results: results
              };
              
        	/*  
            return {
              results: data
            }; */
          },
          cache: true
        }
      }).on('change', function (e) {
    	   //alert(this.value)
    	    $("#physicianId").val(this.value)
    		var phyvalue=$(".autoCompleterPhysicianId option:selected").text();
    	    if(phyvalue!=null && phyvalue!="" && phyvalue!="Select")
    	    	$("#physicianName").val(phyvalue) ;
    	   
    	});
	
} catch(e) {
	// alert(" Exception in redrawDataTable()     ======     " + e);
}

function commentBlockHeightChanges() {
	if ($("#updatedHistory").length == 0) {
		var h = $(".documentBlock").height();
		$(".commentBlock").css("height", (h-78));
		$(".patientBalance").hide();
	} else {
		$(".patientCCBalance").hide();
	}
	
	if ($("#loggedUserType").val() == "Group Director") {
		$(".patientBalance").css("height", "291px");
	}
	if ($("#loggedUserType").val() == "Patient") {
		$(".patientCCBalance").css("height", "172px");
	}
}
commentBlockHeightChanges();

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

function onChangeofGroupList()
{
	try{
		
		var selectedGroupId = $("#selectedGroupId").val();
		var physicianId = $("#physicianId").val();
		//alert(selectedGroupId)
		if (selectedGroupId!=null && selectedGroupId!="") {
			
			var data = new FormData();
			data.append('groupId', selectedGroupId+"");
			data.append('physicianId', physicianId+"");
			
		 	var xhr = new XMLHttpRequest();
		 	xhr.open('POST', 'fetchMultipleGroupWisePhysicians', true);
		 	xhr.send(data);
		 	xhr.onload = function () {
		 		var data = this.responseText;
		 		var responseData = $.parseJSON(data);
		 		//alert(data)
		 		
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
		
		
	}catch(e)
	{
		alert(e)
	}
	
}

try {
	function createSelectedPhysicianLst() {
		var physicianArray = [];
		// physicianArray.length=0;
		var selectedPhysicianId = document.forms[0].physicianSelectedList;
		if(selectedPhysicianId!=null && selectedPhysicianId!=undefined && selectedPhysicianId!="undefined"){
			for (i = 0; i < selectedPhysicianId.options.length; i++) {
	
				var current = selectedPhysicianId.options[i];
				var txt = current.text;
				var val = current.value;
				current.selected = true;
				//alert(txt+"============"+val);
				physicianArray.push(val);
			}
			document.forms[0].selectedPhysicianId.value = physicianArray;
		}
	}

	$('#btnRight').click(
			function(e) {
				moveToListAndDelete('#lstBox1', '#lstBox2');

				createSelectedPhysicianLst();

				$('#patientAccount').bootstrapValidator('revalidateField',
						'physicianSelectedList');
				e.preventDefault();
			});

	$('#btnAllRight').click(
			function(e) {
				moveAllToListAndDelete('#lstBox1', '#lstBox2');

				createSelectedPhysicianLst();

				$('#patientAccount').bootstrapValidator('revalidateField',
						'physicianSelectedList');
				e.preventDefault();
			});

	$('#btnLeft').click(
			function(e) {
				moveToListAndDelete('#lstBox2', '#lstBox1');

				createSelectedPhysicianLst();

				$('#patientAccount').bootstrapValidator('revalidateField',
						'physicianSelectedList');
				e.preventDefault();
			});

	$('#btnAllLeft').click(
			function(e) {
				moveAllToListAndDelete('#lstBox2', '#lstBox1');

				createSelectedPhysicianLst();

				$('#patientAccount').bootstrapValidator('revalidateField',
						'physicianSelectedList');
				e.preventDefault();
			});

	$(document).on(
			"change",
			"#status",
			function() {
				setTimeout(function() {
					try {
						$('#patientAccount').bootstrapValidator('revalidateField',
							'physicianSelectedList')
					} catch (e) {
						// TODO: handle exception
					}
					
					try {
						$('#patientAccount').bootstrapValidator('revalidateField',
							'groupSelectedList')
					} catch (e) {
						// TODO: handle exception
					}
					
				}, 500);
			});
} catch (e) {
}

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
		//$("#submitpatientprofileHidden").click();	
		var validatorObj = $('#patientAccount').data('bootstrapValidator');
	    validatorObj.validate();
	    return validatorObj.isValid();
	} else {
		$("#file").focus();
	}*/
	
}
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

try {
	function createSelectedGroupLst() {
		var groupArray = [];
		// groupArray.length=0;
		var selectedGroupId = document.forms[0].groupSelectedList;
		if(selectedGroupId!=null && selectedGroupId!=undefined && selectedGroupId!="undefined"){
			for (i = 0; i < selectedGroupId.options.length; i++) {
	
				var current = selectedGroupId.options[i];
				var txt = current.text;
				var val = current.value;
				current.selected = true;
				//alert(txt+"============"+val);
				groupArray.push(val);
			}
			document.forms[0].selectedGroupId.value = groupArray;
			//alert(groupArray)
			
		}
	}

	$('#groupbtnRight').click(
			function(e) {
				moveToListAndDelete('#grouplstBox1', '#grouplstBox2');

				createSelectedGroupLst();
				
				onChangeofGroupList();
				$('#patientAccount').bootstrapValidator('revalidateField',
						'groupSelectedList');
				e.preventDefault();
			});

	$('#groupbtnAllRight').click(
			function(e) {
				moveAllToListAndDelete('#grouplstBox1', '#grouplstBox2');

				createSelectedGroupLst();
				onChangeofGroupList();
				$('#patientAccount').bootstrapValidator('revalidateField',
						'groupSelectedList');
				e.preventDefault();
			});

	$('#groupbtnLeft').click(
			function(e) {
				moveToListAndDelete('#grouplstBox2', '#grouplstBox1');

				createSelectedGroupLst();
				onChangeofGroupList();
				$('#patientAccount').bootstrapValidator('revalidateField',
						'groupSelectedList');
				e.preventDefault();
			});

	$('#groupbtnAllLeft').click(
			function(e) {
				moveAllToListAndDelete('#grouplstBox2', '#grouplstBox1');

				createSelectedGroupLst();
				onChangeofGroupList();
				$('#patientAccount').bootstrapValidator('revalidateField',
						'groupSelectedList');
				e.preventDefault();
			});

} catch (e) {
}
function sendLoginEmail()
{
	var patientId=document.forms[0].patientId.value;
	if(patientId>0) {
		document.forms[0].method="POST";
		document.forms[0].action = "sendPatientAccountCredentialsEmail";
		document.forms[0].submit();
	} else {
		$.alert({
 		    title: '',
 		    content: 'Save User Account to Send Credentials Email !',
 		});
	}
}