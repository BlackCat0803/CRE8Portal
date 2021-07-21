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
			userName : {
				message : 'The username is not valid',
				validators : {
					notEmpty : {
						message : 'Enter your Username'
					}
				}
			},
			password : {
				validators : {
					notEmpty : {
						message : 'Enter your Password'
					}
				}
			}
		}
	});
});