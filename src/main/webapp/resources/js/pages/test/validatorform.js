var ele1 = $("#datetimepicker1");
var ele2 = $("#datetimepicker2");
var strTest = $("#strTest");
var startDate = $("#startDate");
var endDate = $("#endDate");
var zipCode = $("#zipCode");
var phone = $("#phone");
var mobile = $("#mobile");
var noOnly = $("#noOnly");
var alpha = $("#alpha");
var ElemText = $("#ElemText");
var ElemTextArea = $("#ElemTextArea");

var dateFormat = "MM/DD/YYYY";
var dateTimeFormat = "MM/DD/YYYY HH:mm";


$(ele2).datetimepicker({
	lang:'en',
	step: 15,				//	 Every 15 min. (00, 15, 30 and 45 of every hour.)
	format: 'm/d/Y H:i'
});

$(ele1).datetimepicker({
	lang:'en',
	timepicker:false,
	format: 'm/d/Y'
});

$(startDate).datetimepicker({
	lang:'en',
	timepicker:false,
	format: 'm/d/Y'
});

$(endDate).datetimepicker({
	lang:'en',
	timepicker:false,
	format: 'm/d/Y'
});


$(document).on("click", "#clearForm", function() {
	ele1.val("");
	ele2.val("");
	startDate.val("");
	endDate.val("");
	zipCode.val("");
	phone.val("");
	mobile.val("");
	strTest.val("");
	noOnly.val("");
	alpha.val("");
	ElemText.val("");
	ElemTextArea.val("");
});

function dateValidation() {
	/*if (dateEmptyCheck(ele1)) {
		alert("select Date of Birth");
	}
	if (dateEmptyCheck(ele2)) {
		alert("select Date With Time");
	}
	$(".displayAge").html( getAge(ele1, dateFormat) );
	
	var diff = checkDateStatus(ele1, dateFormat);
	if (diff > 0) {
		alert("this is past date");
	} else if (diff < 0) {
		alert("this is Future Date");
	} else {
		alert("Given date is Current date");
	}
	
	var noofDays = getDaysDifference(startDate, endDate, dateFormat);
	alert("No.of Days " + noofDays);
	var noofMonths = getMonthsDifference(startDate, endDate, dateFormat);
	alert("No.of Months " + noofMonths);
	var noofYears = getYearsDifference(startDate, endDate, dateFormat);
	alert("No.of Years " + noofYears);*/
	
	// singleQuoteSolution(strTest);
	
	onlyAlphabets(strTest);
	
	alert("checked");
	return false;
}


$(document).ready(function() {
    $('#noOnly').NumBox( { symbol: '', places: 0 } );              // convenient preset for 0% to 100%
    
    phoneFormat(phone);
    mobileFormat(mobile);
    zipCodeFormat(zipCode);
    
    /*$("#zipCode").mask("99999-9999");		// after ? it is optional one.
	$("#phone").mask("999-999-9999");
	$("#mobile").mask("99999-99999");*/
    
    
    $('#formSubmit').click(function(event){
      /*  $('#inputUSD').value = $('#inputUSD').NumBox.raw(); // or
        $('#otherWay').value = $('#inputUSD').NumBox.raw();*/
    	
    	// alert("submitted 2222...");
    		
    	/*var t1 = $("#noOnly").NumBox('getRaw');			// numbers without formats and currencies
    	var t2 = $("#noOnly").NumBox('getFormatted');		// numbers with formats and currencies
    	
    	$('#noOnly').NumBox('destroy');
    	
    	alert( t1 );
    	alert( t2 );
    	
    	return true;*/
    	
    	//singleQuoteSolution();
    	dateValidation();
    });
    
});