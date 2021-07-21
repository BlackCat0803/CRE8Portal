$(function() {
	
	$('body').removeAttr('class').attr('class', 'signupbg2');
	
    $('.required-icon').tooltip({
        placement: 'left',
        title: 'Required field'
        });
});

jQuery(function($){
   $("#phone").mask("999-999-9999");
   $("#mobile").mask("999-999-9999");
});




/****  bootstrap sample validation  **********/




//-------------------

$(document)
		.ready(
				function() {
					// Generate a simple captcha
					function randomNumber(min, max) {
						return Math
								.floor(Math.random() * (max - min + 1) + min);
					}
					;
					$('#captchaOperation').html(
							[ randomNumber(1, 100), '+', randomNumber(1, 200),
									'=' ].join(' '));

					$('#defaultForm')
							.bootstrapValidator(
									{
										message : 'This value is not valid',
										fields : {
											username : {
												message : 'The username is not valid',
												validators : {
													notEmpty : {
														message : 'The username is required and can\'t be empty'
													},
													stringLength : {
														min : 6,
														max : 30,
														message : 'The username must be more than 6 and less than 30 characters long'
													},
													regexp : {
														regexp : /^[a-zA-Z0-9_\.]+$/,
														message : 'The username can only consist of alphabetical, number, dot and underscore'
													},
													different : {
														field : 'password',
														message : 'The username and password can\'t be the same as each other'
													}
												}
											},
											email : {
												validators : {
													notEmpty : {
														message : 'The email address is required and can\'t be empty'
													},
													emailAddress : {
														message : 'The input is not a valid email address'
													}
												}
											},
											password : {
												validators : {
													notEmpty : {
														message : 'The password is required and can\'t be empty'
													},
													identical : {
														field : 'confirmPassword',
														message : 'The password and its confirm are not the same'
													},
													different : {
														field : 'username',
														message : 'The password can\'t be the same as username'
													}
												}
											},
											confirmPassword : {
												validators : {
													notEmpty : {
														message : 'The confirm password is required and can\'t be empty'
													},
													identical : {
														field : 'password',
														message : 'The password and its confirm are not the same'
													},
													different : {
														field : 'username',
														message : 'The password can\'t be the same as username'
													}
												}
											},
											captcha : {
												validators : {
													callback : {
														message : 'Wrong answer',
														callback : function(
																value,
																validator) {
															var items = $(
																	'#captchaOperation')
																	.html()
																	.split(' '), sum = parseInt(items[0])
																	+ parseInt(items[2]);
															return value == sum;
														}
													}
												}
											}
										}
									});
				});
