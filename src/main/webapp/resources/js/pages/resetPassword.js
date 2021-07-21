
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

$(document).on("click", "#closeDialog", function() {
	$("#newPassword").focus();
});

// By default deny the submit
var allowSubmit = false;
$(document).ready(function() {
	//alert(11111111111111111)
	
	/*$("#newPassword").val("");
	$("#reenterPassword").val("");*/
	
	$("#buttonalert").click();
	if(document.getElementById("usernameLbl")!=null)
	document.getElementById("usernameLbl").innerHTML= $("#userName").val();
	
	
	var passwordminlength=$("#passwordminlength").val();
	var passwordmaxlength=$("#passwordmaxlength").val();
	var passwordTextMsg=$("#passwordTextMsg").val();
	passwordTextMsg=passwordTextMsg.replace(/#minPwdLen#/g,passwordminlength);
	passwordTextMsg=passwordTextMsg.replace(/#maxPwdLen#/g,passwordmaxlength);
	/*if(document.getElementById("passwordTxtLbl")!=null)
	document.getElementById("passwordTxtLbl").innerHTML= passwordTextMsg;*/
	
	   $("#newPassword").attr("title",error_categoriesnotmatch);
	   $("#reenterPassword").attr("title",error_categoriesnotmatch);
	
	 $("#resetBtn").on("click", function(event) {
			var validator = $('#resetPasswordForm').data('bootstrapValidator');
			validator.validate();
			 //alert(validator.isValid()+"==========="+allowSubmit)
			 if (validator.isValid()) {
		         if (!allowSubmit) {
		             event.preventDefault();
		             // Your code logic in here (maybe form validation or something)
		             // Then you set allowSubmit to true so this code is bypassed
		             submitViaAjax();
		             
		         }
			 }

     });
	 
	function submitViaAjax() {
		
			//alert("submitViaAjax")
			
			var resetForm = {}
			resetForm["newPassword"] = $("#newPassword").val();
			resetForm["reenterPassword"] = $("#reenterPassword").val();
			resetForm["securityquestion"] = $("#securityquestion").val();
			resetForm["securityanswer"] = $("#securityanswer").val();
			resetForm["securityquestion2"] = $("#securityquestion2").val();
			resetForm["securityanswer2"] = $("#securityanswer2").val();
			resetForm["userId"] = $("#userId").val();
			resetForm["userType"] = $("#userType").val();
			
			//var actionUrl = "alreadyUserPasswordExist?categoryId=" + catId;
			if(document.getElementById("errorMsg")!=null)
			document.getElementById("errorMsg").innerHTML="";
			//var actionUrl = "alreadyUserPasswordExist?newPassword=" + $("#newPassword").val()+"&userId="+$("#userId").val()+"&userType="+$("#userType").val()+"&oldPassword="+$("#oldPassword").val();
			var actionUrl = "alreadyUserPasswordExist";
			//alert(actionUrl)
			$.ajax({
				type : "POST",
				url : actionUrl,
				//url : "alreadyUserPasswordExist",
				//data : JSON.stringify(resetForm),
				data: {"newPassword": $("#newPassword").val(), "userId" : $("#userId").val(), "userType" : $("#userType").val()} ,
				dataType : "json",
				success : function(data) { 
					console.log("SUCCESS: ", data);
					//alert(data.returnStatus)
					var alreadyExist=data.returnStatus;
					var errMsg=data.errMsg;
					//alert("alreadyExist ===="+errMsg)
					if(alreadyExist){
						var message=errMsg;
						//alert(message)
						if(document.getElementById("errorMsg")!=null)
						 document.getElementById("errorMsg").innerHTML=message;
						if(document.getElementById("errorDiv")!=null)
						 document.getElementById("errorDiv").style.display="block";
						 //$('#errorDiv').show('slow');
						// alert(document.getElementById("errorMsg").innerHTML)
						/* $('#errorMsg').html(message);
				         $('#errorMsg').show('slow');*/
						 allowSubmit=false;
						 //return false;
						 
						
							
	
					}else
					{
						if(document.getElementById("errorMsg")!=null)
						 document.getElementById("errorMsg").innerHTML="";
						if(document.getElementById("errorDiv")!=null)
						 document.getElementById("errorDiv").style.display="none";
						 //$("#resetPasswordForm").submit();
						/* $('#errorMsg').html("");
				         $('#errorMsg').show('hide');*/
						 //$('#errorDiv').show('hide');
						 allowSubmit=true;
						 //return true;
						 document.forms[0].action = "saveResetPasswordDetails";
			    		 document.forms[0].submit();
						 //$("#resetPasswordForm").submit();
					}
					
				},
				error : function(req, status, error) {
					console.log("ERROR: ", error);
				}
			});
		
		
	}
	
	var hipaaValidateMsg="";
    try {
		$('#resetPasswordForm').bootstrapValidator(
				{
					fields : {

						newPassword : {
							validators : {
								notEmpty : {
									//message : 'The password is required and can\'t be empty'
									message : $("#error_newpassword").val()
								},
								identical : {
									field : 'reenterPassword',
									//message : 'The password and confirm password are not the same'
									message : $("#error_passwordnotmatch").val()
								},
							    callback: {
							    	message : hipaaValidateMsg,
		                            callback: function(value, validator, $field) {
		                            	var reenterPassword=$("#reenterPassword").val();
		                            	var userName=$("#userName").val();
		                            	var userEmailId=$("#userEmailId").val();
		                            	//alert(value+"============"+reenterPassword)
		                            	
		                                if (value != "" && reenterPassword!="") {
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
						
						reenterPassword : {
							validators : {
								notEmpty : {
									//message : 'The confirm password is required and can\'t be empty'
									message : $("#error_reenterpassword").val()
								},
								identical : {
									field : 'newPassword',
									//message : 'The password and confirm password are not the same'
									message : $("#error_passwordnotmatch")
											.val()
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
							
									
							},

						},
						securityanswer : {
							validators : {
								notEmpty : {
									//message: 'The city is required'
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
									//message: 'The city is required'
									message : $("#error_securityAnswer2").val()
								}
							}
						}

					}
				});
	} catch (e) {
		alert(e)
	}
});




