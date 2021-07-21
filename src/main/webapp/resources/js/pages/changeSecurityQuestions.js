
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
	
	
	try {
		$('#changeSecurityQuestionForm').bootstrapValidator(
				{
					fields : {

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




