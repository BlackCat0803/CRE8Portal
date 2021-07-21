$(function() {
	$('body').removeAttr('class').attr('class', 'signupbg2');
    $('.required-icon').tooltip({
        placement: 'left',
        title: 'Required field'
    });
});

$(document).ready(function() {
	$('#loginForm').bootstrapValidator({
		message : 'This value is not valid',
		fields : {
			username : {
				message : 'The username is not valid',
				validators : {
					notEmpty : {
						message : 'Enter Username'
					}
				}
			},
			password : {
				validators : {
					notEmpty : {
						message : 'Enter Password'
					}
				}
			},
			type : {
				validators : {
					notEmpty : {
						message : 'Select Type'
					}
				}
			}
		}
	});
});