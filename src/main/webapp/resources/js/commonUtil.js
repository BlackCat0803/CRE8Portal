$(document).ready(function() {
	/*setTimeout(function() {
		$(".alert-success").hide();
		$(".alert-danger").hide();
	}, 3000);*/
	
	if (!$(".nav_menu").find(".fa-bars").hasClass("fa"))
		$(".nav_menu").find(".fa-bars").addClass("fa");
	
	if (!$(".nav_menu").find(".fa-angle-down").hasClass("fa"))
		$(".nav_menu").find(".fa-angle-down").addClass("fa");
	
	if (!$("#sidebar-menu").find("[class^='fa-']").hasClass("fa"))
		$("#sidebar-menu").find("[class^='fa-']").addClass("fa");
});


function phoneFormat(ele) {
	$(ele).mask("999-999-9999");
}

function mobileFormat(ele) {
	$(ele).mask("99999-99999");
}

function zipCodeFormat(ele) {
	$(ele).mask("99999-9999");
}

$(document).on("click", "a[data-href]", function() {
    var href = $(this).data("href");
    if (href) {
        location.href = href;
    }
});


function removeZero(ele) {
	if ($(ele).val() == "0") {
		$(ele).val("");
	}
}
function putZero(ele) {
	var n = 0;
	try {
		n = parseInt($(ele).val());
	} catch(e) {
		n = 0;
	} 
	if (isNaN(n))
		n = 0;
	if (n == undefined || n == "" || n == 0)
		$(ele).val("0");
}


/**
 * detect IE browser
 * returns version of IE or false, if browser is not Internet Explorer
 */
function detectIE() {
    var ua = window.navigator.userAgent;

    var msie = ua.indexOf('MSIE ');
    if (msie > 0) {
        // IE 10 or older => return version number
        return parseInt(ua.substring(msie + 5, ua.indexOf('.', msie)), 10);
    }

    var trident = ua.indexOf('Trident/');
    if (trident > 0) {
        // IE 11 => return version number
        var rv = ua.indexOf('rv:');
        return parseInt(ua.substring(rv + 3, ua.indexOf('.', rv)), 10);
    }

    var edge = ua.indexOf('Edge/');
    if (edge > 0) {
       // Edge (IE 12+) => return version number
       return parseInt(ua.substring(edge + 5, ua.indexOf('.', edge)), 10);
    }

    // other browser
    return false;
}

/*
 * For Keypress with Control (Cut, Copy and Paste) purpose
 */
var ctrlDown = false, ctrlKey = 17,cmdKey = 91;
$(document).ready(function() {
	
	$(document).keydown(function(e) {
        if (e.keyCode == ctrlKey || e.keyCode == cmdKey) ctrlDown = true;
    }).keyup(function(e) {
        if (e.keyCode == ctrlKey || e.keyCode == cmdKey) ctrlDown = false;
    });
	
});

function isNumber(evt) {
    evt = (evt) ? evt : window.event;
    var charCode = (evt.which) ? evt.which : evt.keyCode;

    // Keycode for cursor Navigations
    // 8 backspace, 9 tab, 36 home, 35 end, 38 up, 40 down, 37 left, 39 right, 46 delete,
    if ($.inArray(charCode, [8, 9, 36, 35, 38, 40, 37, 39, 46]) !== -1 ) {
    	return true;
    }
    //  Paste && Copy  && Cut operations
    if (ctrlDown  && (charCode == 118 || charCode == 99 || charCode == 120))  
    	return true;
    if (charCode > 31 && charCode != 46 && (charCode < 48 || charCode > 57)) {
        return false;
    }
    return true;
}


function blockedSearchText(evt) {
    evt = (evt) ? evt : window.event;
    var charCode = (evt.which) ? evt.which : evt.keyCode;
    // 64 --> @,  35 --> #, 36--> $, 38 --> &, 37 --> %, ^ --> 94, 33->!, 42-->*, 40-->(, 41--> )
    if ($.inArray(charCode, [64,33,35,36,38,37,40,41,42,94]) !== -1 ) {
        return false;
    }
    return true;
}


function displayFullname(first, middle, last, target) {
	var na="";
	if(($("#"+first).val()).trim()!=null && ($("#"+first).val()).trim()!="" && ($("#"+first).val()).trim().length>0)
		na+=($("#"+first).val()).trim();
	if(($("#"+middle).val()).trim()!=null && ($("#"+middle).val()).trim()!="" && ($("#"+middle).val()).trim().length>0)
		na+=" "+($("#"+middle).val()).trim();
	if(($("#"+last).val()).trim()!=null && ($("#"+last).val()).trim()!="" && ($("#"+last).val()).trim().length>0)
		na+=" "+($("#"+last).val()).trim();
	
	//na = ($("#"+first).val()).trim() + " " + ($("#"+middle).val()).trim() + " " + ($("#"+last).val()).trim();
	
	$("#"+target).val(na);
}

$(document).on("mouseover", ".fullname", function() {
	if ($(".fullname").val() != "") {
	$(".fullname").attr('title', $(".fullname").val()).tooltip('fixTitle').tooltip('show');
	} else {
		$(".fullname").attr('title', '').tooltip('fixTitle').tooltip('hide');
	}
});


//slight update to account for browsers not supporting e.which
/*function disableF5(e) {
	var code = (e.which) ? e.which : e.keyCode;

	if (code == 116) 
		e.preventDefault(); 
};

$(document).on("keydown", disableF5);*/


//To disable f5
/* jQuery < 1.7 */
//$(document).bind("keydown", disableF5);
/* OR jQuery >= 1.7 */



// To re-enable f5
    /* jQuery < 1.7 */
// $(document).unbind("keydown", disableF5);
/* OR jQuery >= 1.7 */
// $(document).off("keydown", disableF5);


/*
$(document).ready(function() {  
	$("#zipCode").mask("99999-9999");		// after ? it is optional one.
	$("#phone").mask("999-999-9999");
	$("#mobile").mask("99999-99999");
});*/


/*function textValidation() {
	alert("checked");
	return false;
}*/

/*
 * 
 * // Phone number with multiple formats ex. a phone number have either (2) 4-4 or (2) 5-4 format.
 *
 *
 * HTML CODE:
 * -------------
 *		<input type="text" class="sp_celphones" id="sp_celphones" maxlength="15" autocomplete="off">
 *
 * JS CODE:
 * --------------
var SPMaskBehavior = function (val) {
	return val.replace(/\D/g, '').length === 11 ? '(00) 00000-0000' : '(00) 0000-00009';
},
spOptions = {
    onKeyPress: function(val, e, field, options) {
    	field.mask(SPMaskBehavior.apply({}, arguments), options);
    }
};

$('.sp_celphones').mask(SPMaskBehavior, spOptions);

 *
 */


/********************  Bootstrap form validation events *************/
/*
$('#admin').bootstrapValidator({
    fields: {
    	// List of required fields... 			
    }
})
.on('error.form.bv', function(e) {
    // Reference: ---> https://github.com/nghuuphuoc/bootstrapvalidator/blob/master/demo/event.html
	$(".formErrorMsg").show();
	//    console.log('error.form.bv');
    // You can get the form instance and then access API
    //    var $form = $(e.target);
    //    console.log($form.data('bootstrapValidator').getInvalidFields());
    // If you want to prevent the default handler (bootstrapValidator._onError(e))
    // e.preventDefault();
})
.on('success.form.bv', function(e) {
    // console.log('success.form.bv');
    $(".formErrorMsg").hide();
    // If you want to prevent the default handler (bootstrapValidator._onSuccess(e))
    // e.preventDefault();
})
.on('error.field.bv', function(e, data) {
    / *console.log('error.field.bv -->', data);
    if (data.bv.getInvalidFields().length == 0) {    // There is invalid field
    	console.log('form completed -->', data);
    }* /
    if (data.bv.getInvalidFields().length == 0) {    // There is invalid field
    	//console.log('form completed 1 -->', data);
    	$(".formErrorMsg").hide();
    } else {
    	$(".formErrorMsg").show();
    }
})
.on('success.field.bv', function(e, data) {
    // console.log('success.field.bv -->', data);
    if (data.bv.getInvalidFields().length == 0) {    // There is invalid field
    	//console.log('form completed 1 -->', data);
    	$(".formErrorMsg").hide();
    } else {
    	$(".formErrorMsg").show();
    }
})
.on('status.field.bv', function(e, data) {
	 / * console.log('status.field.bv -->', data);
	 if (data.bv.getInvalidFields().length == 0) {    // There is invalid field
     	console.log('form completed 2 -->', data);
     } * /
    if (data.bv.getInvalidFields().length == 0) {    // There is invalid field
    	//console.log('form completed 1 -->', data);
    	$(".formErrorMsg").hide();
    } else {
    	$(".formErrorMsg").show();
    }
    // I don't want to add has-success class to valid field container
   // data.element.parents('.form-group').removeClass('has-success');
    // I want to enable the submit button all the time
   // data.bv.disableSubmitButtons(false);
});
*/
