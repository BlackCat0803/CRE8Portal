$(function() {
	$('body').removeAttr('class').attr('class', 'signupbg2');
    $('.required-icon').tooltip({
        placement: 'left',
        title: 'Required field'
    });
});

$(document).ready(function() {
	if ($("#dropValue").val() == 1) {
		$("#type").val("Physician");
	} else if ($("#dropValue").val() == 2) {
		$("#type").val("Administrator");
	} else if ($("#dropValue").val() == 3) {
		$("#type").val("Physician Assistant");
	} else if ($("#dropValue").val() == 4) {
		$("#type").val("Group Director");
	}

	$(".linkFont:even").css("background-color", "#BBD7EF");
	$(".linkFont:odd").css("background-color", "#f9f9f9");
	
	$(".delete-cookies:even").css("background-color", "#BBD7EF");
	$(".delete-cookies:odd").css("background-color", "#f9f9f9");
	
	$(".delete-cookies:last").remove();
	

	//if (window.location.href.indexOf("loginWithId") !== -1) {
	if ($("#userName").val().trim().length != 0) {
		$("#password").focus();
	} else {
		$("#userName").focus();
	}
	
	$('#loginForm').bootstrapValidator({
		message : 'This value is not valid',
		fields : {
			userName : {
				message : 'The Email Id is not valid',
				validators : {
					notEmpty : {
						message : 'Please enter your Email Id'
					}, emailAddress: {
                         message: 'The Email Id is not valid'
                        
                    }
				}
			},
			password : {
				validators : {
					notEmpty : {
						message : 'Please enter your Password'
					}
				}
			},
			type : {
				validators : {
					notEmpty : {
						message : 'Please select User Type'
					}
				}
			}
		}
	});
});

$(document).on("click", ".linkFont span", function() {
	var t = $(this).html();
	var arr = t.split(" - ");
	$("#tmpUserName").val(arr[0]);
	$("#tmpType").val(arr[1]);
	document.submitForm.action = "loginWithId";
	document.submitForm.submit();
});

$(document).on("click", ".delete-cookies", function(){
	var t = $(this).parent().find("span").html();
	var arr = t.split(" - ");
	$("#tmpUserName").val(arr[0]);
	$("#tmpType").val(arr[1]);
	document.submitForm.action = "loginRemoveCookie";
	document.submitForm.submit();
});
