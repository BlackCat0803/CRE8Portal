$(document).on("keydown", "#dateRegistrated, #licenseExpDate, #dateOfBirth, #rxWrittenOn, #rxExpireOn, #lastFilledDate, .searchDate", function(e) {
	// 8 backspace, 9 tab, 36 home, 35 end, 38 up, 40 down, 37 left, 39 right, 46 delete, 
	// 48->0, 49->1,50->2,... 57-9 (in keyboard first line of alphabets
	// 96->0,97->1,..105->9  (In keypad)
	// alert(e.keyCode);
	if ($.inArray(e.keyCode, [8,9,35,36,37,38,39,40,46,48,49,50,51,52,53,54,55,56,57,96,97,98,99,100,101,102,103,104,105]) !== -1 )
		return true;
	else
		return false;
});

function dateEmptyCheck(ele) {
	if (ele.val() == "") {
		return true;
	}
	return false;
}

function getAge(ele) {
	return moment(ele.val(), dateFormat).fromNow();
}

function dateValidFormat(ele, format) {
	return moment(ele.val(), format).isValid();
}

function checkDateStatus(ele, format) {
	var givenDate = moment(ele.val(), format);
	return moment().diff(givenDate, 'days') ;
}

function getYear(ele) {
	return moment(ele.val(), dateTimeFormat).get('year');
}

function getFullMonth(ele) {
	return moment(ele.val(), dateTimeFormat).format("MMMM");  // January, Feburary....
}

function getMonthStr(ele) {
	return moment(ele.val(), dateTimeFormat).format("MMM");  // Jan, Feb....
}

function getMonthNo(ele) {
	return moment(ele2.val(), dateTimeFormat).get('month');  // 0 to 11
}

function getDate(ele) {
	return moment(ele.val(), dateTimeFormat).get('date');
}

function getHours(ele) {
	return moment(ele.val(), dateTimeFormat).get('hour');
}

function getMinutes(ele) {
	return moment(ele.val(), dateTimeFormat).get('minute');
}

function getSeconds(ele) {
	return moment(ele.val(), dateTimeFormat).get('second');
}

function getMilliSeconds(ele) {
	return moment(ele2.val(), dateTimeFormat).get('millisecond');
}

function getDaysDifference(startDate, endDate, dateFormat) {
	var stDate = moment(startDate.val(), dateFormat);
	var edDate = moment(endDate.val(), dateFormat);
	
	return edDate.diff(stDate, 'days') ;
}

function getMonthsDifference(startDate, endDate, dateFormat) {
	var stDate = moment(startDate.val(), dateFormat);
	var edDate = moment(endDate.val(), dateFormat);
	
	return edDate.diff(stDate, 'months') +1 ;
}


function getYearsDifference(startDate, endDate, dateFormat) {
	var stDate = moment(startDate.val(), dateFormat);
	var edDate = moment(endDate.val(), dateFormat);

	var no = edDate.diff(stDate, 'years') ;
	// no= no + 1  // +1 for include current year (last year)
	return no;
}


/************ Notes ********************/
/*
 *   This is for reference
 *   Ref: http://momentjs.com/docs/#/get-set/
 *   Ref: https://xdsoft.net/jqplugins/datetimepicker/  
 * 
Token	---- Output
Month	---- M	1 2 ... 11 12
Mo		---- 1st 2nd ... 11th 12th
MM		---- 01 02 ... 11 12
MMM		---- Jan Feb ... Nov Dec
MMMM	---- January February ... November December
Quarter	---- Q	1 2 3 4
Qo		---- 	1st 2nd 3rd 4th
Day of Month ---- 	D	1 2 ... 30 31
Do		---- 1st 2nd ... 30th 31st
DD		---- 01 02 ... 30 31
Day of Year	---- DDD	1 2 ... 364 365
DDDo	---- 1st 2nd ... 364th 365th
DDDD	---- 001 002 ... 364 365
Day of Week	---- d	0 1 ... 5 6
do		---- 0th 1st ... 5th 6th
dd		---- Su Mo ... Fr Sa
ddd		---- Sun Mon ... Fri Sat
dddd	---- Sunday Monday ... Friday Saturday
Day of Week (Locale) ---- e	0 1 ... 5 6
Day of Week (ISO)	 ---- E	1 2 ... 6 7
Week of Year	---- w	1 2 ... 52 53
wo		---- 1st 2nd ... 52nd 53rd
ww		---- 01 02 ... 52 53
Week of Year (ISO)	---- W	1 2 ... 52 53
Wo		---- 1st 2nd ... 52nd 53rd
WW		---- 01 02 ... 52 53
Year	---- YY	70 71 ... 29 30
YYYY	---- 1970 1971 ... 2029 2030
Y		---- 1970 1971 ... 9999 +10000 +10001 
Note: This complies with the ISO 8601 standard for dates past the year 9999
Week Year	gg	70 71 ... 29 30
gggg	1970 1971 ... 2029 2030
Week Year (ISO)	GG	70 71 ... 29 30
GGGG	1970 1971 ... 2029 2030
AM/PM	A	AM PM
a	am pm
Hour	H	0 1 ... 22 23
HH	00 01 ... 22 23
h	1 2 ... 11 12
hh	01 02 ... 11 12
k	1 2 ... 23 24
kk	01 02 ... 23 24
Minute	m	0 1 ... 58 59
mm	00 01 ... 58 59
Second	s	0 1 ... 58 59
ss	00 01 ... 58 59
Fractional Second	S	0 1 ... 8 9
SS	00 01 ... 98 99
SSS	000 001 ... 998 999
SSSS ... SSSSSSSSS	000[0..] 001[0..] ... 998[0..] 999[0..]
Time Zone	z or zz	EST CST ... MST PST 
Note: as of 1.6.0, the z/zz format tokens have been deprecated from plain moment objects. Read more about it here. However, they do work if you are using a specific time zone with the moment-timezone addon.
Z	-07:00 -06:00 ... +06:00 +07:00
ZZ	-0700 -0600 ... +0600 +0700
Unix Timestamp	X	1360013296
Unix Millisecond Timestamp	x	1360013296123
*/