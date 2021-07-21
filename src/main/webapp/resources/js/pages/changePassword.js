

// By default deny the submit
var allowSubmit = false;
$(document).ready(function() {
	//alert(11111111111111111)
	
	
	
	document.getElementById("usernameLbl").innerHTML= $("#userName").val();
	
	
	var passwordminlength=$("#passwordminlength").val();
	var passwordmaxlength=$("#passwordmaxlength").val();
	var passwordTextMsg=$("#passwordTextMsg").val();
	passwordTextMsg=passwordTextMsg.replace(/#minPwdLen#/g,passwordminlength);
	passwordTextMsg=passwordTextMsg.replace(/#maxPwdLen#/g,passwordmaxlength);
	document.getElementById("passwordTxtLbl").innerHTML= passwordTextMsg;
	
	
	 $("#resetBtn").on("click", function(event) {
			var validator = $('#changePasswordForm').data('bootstrapValidator');
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
			resetForm["userId"] = $("#userId").val();
			resetForm["userType"] = $("#userType").val();
			
			if(document.getElementById("errorMsg")!=null)
				document.getElementById("errorMsg").innerHTML="";
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
						
						allowSubmit=false;
						 
						var message=errMsg;
						//alert(message)
						if(document.getElementById("errorMsg")!=null)
						 document.getElementById("errorMsg").innerHTML=message;
						if(document.getElementById("errorDiv")!=null)
						 document.getElementById("errorDiv").style.display="block";
						
						$(".alert-danger").show();
						
						 //$('#errorDiv').show('slow');
						// alert(document.getElementById("errorMsg").innerHTML)
						/* $('#errorMsg').html(message);
				         $('#errorMsg').show('slow');*/
						
						 //return false;
						 
						
							
	
					}else
					{
						if(document.getElementById("errorMsg")!=null)
						 document.getElementById("errorMsg").innerHTML="";
						if(document.getElementById("errorDiv")!=null)
						 document.getElementById("errorDiv").style.display="none";
						 //$("#changePasswordForm").submit();
						/* $('#errorMsg').html("");
				         $('#errorMsg').show('hide');*/
						 //$('#errorDiv').show('hide');
						 allowSubmit=true;
						 //alert("11111111111111111111111111111")
						 //return true;
						 document.forms[0].action = "saveNewPasswordDetails";
			    		 document.forms[0].submit();
						 //$("#changePasswordForm").submit();
					}
					
				},
				error : function(req, status, error) {
					console.log("ERROR: ", error);
				}
			});
		
		
	}
	
	var hipaaValidateMsg="";
    try {
		$('#changePasswordForm').bootstrapValidator(
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
									message : $("#error_passwordnotmatch").val()
								}
							}
						}

					}
				});
	} catch (e) {
		alert(e)
	}
});




