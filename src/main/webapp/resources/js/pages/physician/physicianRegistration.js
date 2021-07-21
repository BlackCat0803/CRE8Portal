
$(function() {
	$('body').removeAttr('class').attr('class', 'signupbg2');
   /* $('.required-icon').tooltip({
        placement: 'left',
        title: 'Required field'
    });*/
});

jQuery(function($){
   $("#phone").mask("999-999-9999");
   $("#mobile").mask("999-999-9999");
   $("#zipCode").mask("99999-9999");
  
});
var hipaaValidateMsg="";
$(document).ready(function() {
	
	   $("#password").attr("title",error_categoriesnotmatch);
	   $("#confirmPassword").attr("title",error_categoriesnotmatch);
    $('#physicianAccount').bootstrapValidator({
        fields: {
        	firstName: {
                validators: {
                    notEmpty: {
                        //message: 'The first name is required'
                    	message: $("#err_fname").val()
                    }
                }
            },        	
        	lastName: {
                validators: {
                    notEmpty: {
                        //message: 'The last name is required'
                    	message: $("#err_lname").val()
                    }
                }
            },
            email: {
                validators: {
                    notEmpty: {
                        // message: 'The email address is required'
                        message: $("#err_email").val()	
                    },
                    emailAddress: {
                        // message: 'The email address is not valid'
                        message: $("#err_email_format").val()
                    },
					identical : {
						field : 'confirmEmail',
						//message : 'The email and confirm email address are not the same'
						message: $("#err_email_and_conf").val()
					}
                }
            },
            confirmEmail: {
                validators: {
                    notEmpty: {
                        // message: 'The confirm email address is required'
                    	message: $("#err_conf_email").val()
                    },
                    emailAddress: {
                        //message: 'The confirm email address is not valid'
                    	message: $("#err_conf_email_format").val()
                    },
					identical : {
						field : 'email',
						//message : 'The email and confirm email address are not the same'
						message: $("#err_email_and_conf").val()
					}
                }
            },
            password : {
				validators : {
					notEmpty : {
						//message : 'The password is required and can\'t be empty'
						message: $("#err_pwd").val()
					},
					identical : {
						field : 'confirmPassword',
						//message : 'The password and confirm password are not the same'
						message: $("#err_pwd_conf").val()
					},
				    callback: {
				    	message : hipaaValidateMsg,
                        callback: function(value, validator, $field) {
                        	var confirmPassword=$("#confirmPassword").val();
                        	var userName=$("#firstName").val()+" "+$("#lastName").val();
                        	var userEmailId=$("#email").val();
                        	//alert(value+"============"+reenterPassword)
                        	
                            if (value != "" && confirmPassword!="") {
                            	hipaaValidateMsg=checkHipaaValidation(value,userName,userEmailId);
                            }
                   
                        	//alert("hipaaValidateMsg ========="+hipaaValidateMsg)
                    		if(hipaaValidateMsg!="")
                			{
                    			
                    			return {
                    				valid:false,
                    				message:hipaaValidateMsg
                    			}
                				
                			}else
            				{
                				return true;
            				}
                          }

                        	
                        }
				}
			},
			confirmPassword : {
				validators : {
					notEmpty : {
						//message : 'The confirm password is required and can\'t be empty'
						message: $("#err_conf_pwd").val()
					},
					identical : {
						field : 'password',
						//message : 'The password and confirm password are not the same'
						message: $("#err_pwd_conf").val()
					}
				}
			}, 
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
                    phone: {
                        country: 'US',
                        message: $("#err_mobile_format").val()
                    }                    
                }
            },				
            city: {
                validators: {
                    notEmpty: {
                    	message: $("#err_city").val()
                    }
                }
            },
            state: {
                validators: {
                    notEmpty: {
                    	message: $("#err_state").val()
                    }
                }
            },
            zipCode: {
                validators: {
                    zipCode: {
                        country: 'US',
                        message: $("#err_zip_format").val()
                    }
                }
            }
        }
    });
});

/*function isNumber(evt) {
    evt = (evt) ? evt : window.event;
    var charCode = (evt.which) ? evt.which : evt.keyCode;
    // 8 backspace, 9 tab, 36 home, 35 end, 38 up, 40 down, 37 left, 39 right, 46 delete,
    if (charCode > 31 && charCode != 8 && charCode != 9 && charCode != 46 && (charCode < 35 || charCode > 41) && (charCode < 48 || charCode > 57)) {
        return false;
    }
    return true;
}*/

/*$(document).on("blur", "#mobile", function() {
	$("#mobile").unmask();
	alert("85236");
	$('#physicianAccount').bootstrapValidator('enableFieldValidators', mobile, true, 'notEmpty', true);	
});*/



