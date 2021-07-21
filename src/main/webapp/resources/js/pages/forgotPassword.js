
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
   $("#zipCode").mask("99999?-9999");
});

function submitForm()
{
	
	try {
		 var showsecurityques=document.getElementById("showsecurityques").value;
		 var validator = $('#forgotPasswordForm').data('bootstrapValidator');
		 if(showsecurityques==null || showsecurityques==""){
			 validator.removeField('securityquestion');
			 validator.removeField('securityanswer');
	         validator.removeField('securityquestion2');
	         validator.removeField('securityanswer2');
		 }else
		 {
			validator.validateField('securityquestion');
	        validator.validateField('securityanswer');
	        validator.validateField('securityquestion2');
	        validator.validateField('securityanswer2');
		 }
	        
	        
		 validator.validate();
		 if (validator.isValid()) {
	            //alert("yes valid")
	           
	    		//alert(showsecurityques)
	    		if(showsecurityques==null || showsecurityques==""){
	    			document.forms[0].action = "checkForgotPasswordEmailidExists";
	    			document.forms[0].submit();
	    		}else
	    		{
	    			document.forms[0].action = "validateUserSecurityQuestionsandAnswers";
	    			document.forms[0].submit();
	    		}
	        }
		
	} catch (e) {
		alert(e)
	}
	
}

$(document).ready(function() {
   //alert(11111111111111111)
   var showsecurityques=document.getElementById("showsecurityques").value;
   //alert($("#error_email").val())
   try {
	$('#forgotPasswordForm').bootstrapValidator({

		fields : {

			email : {
				validators : {
					notEmpty : {
						// message: 'The email address is required'
						message : $("#error_email").val()
					},
					emailAddress : {
						// message: 'The email address is not valid'
						message : $("#error_email_format").val()
					}

				}
			},
			username : {
				validators : {
					notEmpty : {
						// message: 'The username is required'
						message : $("#error_username").val()
					}

				}
			},
			type : {
				validators : {
					notEmpty : {
						//message: 'The state is required'
						message : $("#error_usertype").val()
					}
				}
			},
		
			securityquestion : {
				validators : {
					notEmpty : {
						//message: 'The state is required'
						message : $("#error_securityQuestion1").val()
					},
					different: {
	                    field: 'securityquestion2',
	                    message: $("#error_questionsnotmatch").val()
	                }
				}
			},
		
			securityanswer : {
				validators : {
					notEmpty : {
						//message: 'The state is required'
						message : $("#error_securityAnswer1").val()
					}
				}
			},
		
			securityquestion2 : {
				validators : {
					notEmpty : {
						//message: 'The state is required'
						message : $("#error_securityQuestion2").val()
					},
					different: {
	                    field: 'securityquestion',
	                    message: $("#error_questionsnotmatch").val()
	                }
				}
			},
		
			securityanswer2 : {
				validators : {
					notEmpty : {
						//message: 'The state is required'
						message : $("#error_securityAnswer2").val()
					}
				}
			}
		},
	});
} catch (e) {
	alert(e)
}

});




