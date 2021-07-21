
// Notes for future use
/*$(document).ready(function() {
    $('#noOnly').NumBox();      // default: $0.00 to $999,999.99
    $('#inputJPY').NumBox({ symbol: 'Â¥', places: 0 });          // number of decimal places: 0
    $('#inputCNY').NumBox({ symbol: 'å…ƒ', location: 'r' });      // symbol location: [l]eft or [r]ight
    $('#chlorine').NumBox({ symbol: ' nanometers', places: 3, location: 'r' });
    $('#inputPct').NumBox({   type: 'percent'  });              // convenient preset for 0% to 100%
    $('#inputSSN').NumBox({   type: 'ssn'      });              // convenient preset for US SSNs
    $('#credcard').NumBox({   type: 'ccard-16' });              // convenient preset for credit cards
    $('#amexcard').NumBox({   type: 'ccard-15' });              // convenient preset for credit cards
    $('#inputZIP').NumBox({   type: 'zip'      });              // use 'zip9' instead for a nine digit zip code
    $('.NumBox'  ).NumBox('autoScroll', false);                 // can use NumBox class to alter all NumBox fields
});*/  

function singleQuoteSolution(ele) {
	/*var t1 = ele.NumBox('getRaw');				// numbers without formats and currencies
	var t2 = ele.NumBox('getFormatted');		// numbers with formats and currencies
	
	ele.NumBox('destroy');
	
	alert( t1 );
	alert( t2 );*/
	
	var g = ele.val();
	
	var t = g.replace(/'/g, "\\'");
	t = t.replace(/"/g, '\\"');
	
	alert(" Given --: " + g + " \n Output -: " + t);
	return true;
}

function validateEmail(ele) {
    var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(ele.val());
}

/*function validateUrl(ele) {
 var regexp = "_^(?:(?:https?|ftp)://)(?:\S+(?::\S*)?@)?(?:(?!10(?:\.\d{1,3}){3})(?!127(?:\.\d{1,3}){3})(?!169\.254(?:\.\d{1,3}){2})(?!192\.168(?:\.\d{1,3}){2})(?!172\.(?:1[6-9]|2\d|3[0-1])(?:\.\d{1,3}){2})(?:[1-9]\d?|1\d\d|2[01]\d|22[0-3])(?:\.(?:1?\d{1,2}|2[0-4]\d|25[0-5])){2}(?:\.(?:[1-9]\d?|1\d\d|2[0-4]\d|25[0-4]))|(?:(?:[a-z\x{00a1}-\x{ffff}0-9]+-?)*[a-z\x{00a1}-\x{ffff}0-9]+)(?:\.(?:[a-z\x{00a1}-\x{ffff}0-9]+-?)*[a-z\x{00a1}-\x{ffff}0-9]+)*(?:\.(?:[a-z\x{00a1}-\x{ffff}]{2,})))(?::\d{2,5})?(?:/[^\s]*)?$_iuS";
 return regexp.test(ele.val());
}*/

/*function singleQuoteSolution() {
	var g = $("#strTest").val();
	
	var t = g.replace(/'/g, "\\'");
	t = t.replace(/"/g, '\\"');
	
	alert(" Given --: " + g + " \n Output -: " + t);
	
	alert("submitted...");
	// var t = $("#noOnly").NumBox('getRaw');
	var t = $('#noOnly').NumBox.raw();
	alert( t );
	return false;
}*/

/*$(".alphaOnly").keydown(function (e) {
    // Allow: backspace, delete, tab, escape, enter and .
    if ($.inArray(e.keyCode, [46, 8, 9, 27, 13, 110, 190]) !== -1 ||
         // Allow: Ctrl+A, Command+A
        (e.keyCode === 65 && (e.ctrlKey === true || e.metaKey === true)) || 
         // Allow: home, end, left, right, down, up
        (e.keyCode >= 35 && e.keyCode <= 40)) {
             // let it happen, don't do anything
             return;
    }
    // Ensure that it is a number and stop the keypress
    if ((e.shiftKey || (e.keyCode < 48 || e.keyCode > 57)) && (e.keyCode < 96 || e.keyCode > 105)) {
        e.preventDefault();
    }
});*/

$(".alphaOnly").keypress(function(event){
    var inputValue = event.which;
    // allow letters and whitespaces only.
    if(!(inputValue >= 65 && inputValue <= 120) && (inputValue != 32 && inputValue != 0)) { 
        event.preventDefault(); 
    }
});

var regex = /^[a-zA-Z]*$/;